package com.glodblock.github.extendedae.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import appeng.client.gui.AEBaseScreen;

import com.gregtechceu.gtceu.uipro.UIInput;
import com.gregtechceu.gtceu.uiwidgets.structure.StructureScene;
import com.lowdragmc.lowdraglib.gui.modular.IUIHolder;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.utils.BlockInfo;
import com.lowdragmc.lowdraglib.utils.Position;
import com.lowdragmc.lowdraglib.utils.Size;
import com.lowdragmc.lowdraglib.utils.TrackedDummyWorld;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

final class GTWorldDisplay extends WorldDisplay {

    private final AEBaseScreen<?> screen;
    private final StructureScene scene;
    private final ModularUI ui;
    private boolean ready;

    GTWorldDisplay(AEBaseScreen<?> screen, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.screen = screen;
        this.visible = true;
        this.active = true;
        this.scene = new StructureScene("extendedae_wireless_connector", width, height, false);
        this.scene.setOrbit(true);
        this.scene.setSelectionBox(false);
        this.scene.setSelectable(false);
        this.scene.setRememberView(false);
        this.ui = new ModularUI(width, height, IUIHolder.EMPTY, Minecraft.getInstance().player);
        this.scene.setGui(this.ui);
        refreshBounds();
    }

    @Override
    public void locate(BlockPos pos) {
        unload();
        var level = Minecraft.getInstance().level;
        if (level == null || level.getBlockState(pos).isAir() || level.getBlockEntity(pos) == null) {
            return;
        }
        var positions = new BlockPos[7];
        positions[0] = pos;
        for (var direction : Direction.values()) {
            positions[direction.ordinal() + 1] = pos.relative(direction);
        }
        var snapshot = new TrackedDummyWorld(level);
        var blocks = new Long2ObjectOpenHashMap<BlockState>(positions.length);
        var entities = new Long2ObjectOpenHashMap<BlockEntity>(positions.length);
        for (var blockPos : positions) {
            var state = level.getBlockState(blockPos);
            if (state.isAir()) continue;
            blocks.put(blockPos.asLong(), state);
            var info = new BlockInfo(state, state.hasBlockEntity());
            var entity = level.getBlockEntity(blockPos);
            if (entity != null) info.setTag(entity.saveWithFullMetadata());
            snapshot.addBlock(blockPos, info);
        }
        for (var blockPos : positions) {
            var entity = snapshot.getBlockEntity(blockPos);
            if (entity != null) entities.put(blockPos.asLong(), entity);
        }
        this.scene.setGui(this.ui);
        this.scene.recenter(null);
        this.scene.showLive(blocks, entities, StructureScene.fitZoom(3, 3, 3));
        this.ready = true;
    }

    @Override
    public void unload() {
        if (!this.ready) return;
        this.ready = false;
        this.scene.setGui(null);
        this.scene.dispose();
    }

    @Override
    public void refreshBounds() {
        this.scene.setSelfPosition(new Position(getX(), getY()));
        this.scene.setSize(new Size(this.width, this.height));
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if (!this.ready) return;
        graphics.flush();
        float fogStart = RenderSystem.getShaderFogStart();
        float fogEnd = RenderSystem.getShaderFogEnd();
        RenderSystem.setShaderFogStart(1.0e9f);
        RenderSystem.setShaderFogEnd(2.0e9f);
        // Third-party block renderers must not leave world fog changed after a failure.
        try {
            this.scene.drawInBackground(graphics, mouseX, mouseY, partialTick);
        } finally {
            RenderSystem.setShaderFogStart(fogStart);
            RenderSystem.setShaderFogEnd(fogEnd);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.ready || !this.scene.mouseClicked(mouseX, mouseY, button)) return false;
        this.screen.setFocused(this);
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return this.ready && UIInput.captured() == this.scene && this.scene.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        // Unclaimed releases enter LDLib item dropping, which requires a full ModularUI screen.
        return this.ready && UIInput.captured() == this.scene && this.scene.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        return this.ready && this.scene.mouseWheelMove(mouseX, mouseY, delta);
    }
}
