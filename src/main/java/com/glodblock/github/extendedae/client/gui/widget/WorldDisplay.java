package com.glodblock.github.extendedae.client.gui.widget;

import com.glodblock.github.extendedae.xmod.LoadList;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import appeng.client.gui.AEBaseScreen;

import org.jetbrains.annotations.NotNull;

public class WorldDisplay extends AbstractWidget {

    protected WorldDisplay(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
        this.visible = false;
        this.active = false;
    }

    public static WorldDisplay create(AEBaseScreen<?> screen, int x, int y, int width, int height) {
        return LoadList.GT ? new GTWorldDisplay(screen, x, y, width, height) : new WorldDisplay(x, y, width, height);
    }

    public void unload() {}

    public void locate(BlockPos pos) {}

    public void refreshBounds() {}

    @Override
    public void playDownSound(@NotNull SoundManager manager) {}

    @Override
    protected void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {}

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {}
}
