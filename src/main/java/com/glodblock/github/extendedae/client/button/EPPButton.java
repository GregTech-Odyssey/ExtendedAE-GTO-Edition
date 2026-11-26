package com.glodblock.github.extendedae.client.button;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

import appeng.client.gui.Icon;
import appeng.client.gui.style.Blitter;
import appeng.client.gui.widgets.IconButton;

public abstract class EPPButton extends IconButton {

    public EPPButton(OnPress onPress) {
        super(onPress);
    }

    abstract Blitter getBlitterIcon();

    @Override
    protected final Icon getIcon() {
        return null;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partial) {
        if (this.visible) {
            Blitter blitter = getBlitterIcon();
            var item = this.getItemOverlay();

            if (this.isHalfSize()) {
                this.width = 8;
                this.height = 8;
            }

            var yOffset = isHovered() ? 1 : 0;

            if (this.isHalfSize()) {
                var pose = guiGraphics.pose();
                pose.pushPose();
                pose.translate(getX(), getY(), 0.0F);
                pose.scale(0.5f, 0.5f, 1.f);
                if (!isDisableBackground()) {
                    Icon bgIcon = isHovered() ? Icon.TOOLBAR_BUTTON_BACKGROUND_HOVER : isFocused() ? Icon.TOOLBAR_BUTTON_BACKGROUND_FOCUS : Icon.TOOLBAR_BUTTON_BACKGROUND;
                    bgIcon.getBlitter().dest(0, 0, 16, 16).zOffset(10).blit(guiGraphics);
                }
                if (item != null) {
                    pose.popPose();
                    guiGraphics.renderItem(new ItemStack(item), getX(), getY(), 0, 20);
                } else {
                    if (blitter != null) {
                        if (!this.active) {
                            blitter.opacity(0.5f);
                        }
                        blitter.dest(0, 0).zOffset(20).blit(guiGraphics);
                    }
                    pose.popPose();
                }
            } else {
                if (!isDisableBackground()) {
                    Icon bgIcon = isHovered() ? Icon.TOOLBAR_BUTTON_BACKGROUND_HOVER : isFocused() ? Icon.TOOLBAR_BUTTON_BACKGROUND_FOCUS : Icon.TOOLBAR_BUTTON_BACKGROUND;
                    bgIcon.getBlitter()
                            .dest(getX() - 1, getY() + yOffset, 18, 20)
                            .zOffset(2)
                            .blit(guiGraphics);
                }
                if (item != null) {
                    guiGraphics.renderItem(new ItemStack(item), getX(), getY() + 1 + yOffset, 0, 3);
                } else if (blitter != null) {
                    if (!this.active) {
                        blitter.opacity(0.5f);
                    }
                    blitter.dest(getX(), getY() + 1 + yOffset).zOffset(3).blit(guiGraphics);
                }
            }
        }
    }
}
