package com.glodblock.github.extendedae.client.gui.widget;

import com.glodblock.github.extendedae.container.TagSelectionSlots;
import com.glodblock.github.extendedae.util.MutableSlot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

import appeng.api.stacks.AEFluidKey;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.client.Point;
import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.Tooltip;
import appeng.menu.slot.FakeSlot;

import com.gto.ae.client.gui.widgets.AEListBox;
import com.gto.ae.client.gui.widgets.AESlotWidget;

public final class TagSelectionWidget {

    private TagSelectionWidget() {}

    public static void install(AEBaseScreen<?> screen, TagSelectionSlots slots, MultilineTextFieldWidget whitelist,
                               MultilineTextFieldWidget blacklist, int whitelistY) {
        var list = new AEListBox(screen) {

            @Override
            public void drawForegroundLayer(GuiGraphics graphics, Rect2i bounds, Point mouse) {
                graphics.fill(getX(), getY(), getX() + width(), getY() + height(), 0xcc7777aa);
                super.drawForegroundLayer(graphics, bounds, mouse);
            }
        };
        list.setVisible(false);
        list.setCatchScrollbar(false);
        screen.getWidgets().add("tag_selection_list", list);
        setupSlot(screen, slots.whitelist(), whitelistY, "whitelist");
        setupSlot(screen, slots.blacklist(), 70, "blacklist");
        slots.onWhitelistChanged(key -> update(list, slots.whitelist(), whitelist, key));
        slots.onBlacklistChanged(key -> update(list, slots.blacklist(), blacklist, key));
    }

    private static void setupSlot(AEBaseScreen<?> screen, FakeSlot slot, int y, String name) {
        var mutable = (MutableSlot) slot;
        mutable.setXPos(146);
        mutable.setYPos(y);
        var widget = new AESlotWidget(slot, screen);
        widget.setTooltip(Component.translatable("gui.expatternprovider.tag_selection." + name));
        screen.getWidgets().add("tag_selection_" + name, widget);
    }

    private static void update(AEListBox list, FakeSlot slot, MultilineTextFieldWidget field, AEKey key) {
        if (key == null) {
            list.setVisible(false);
            return;
        }
        list.clearItems();
        list.setPosition(new Point(slot.x + 20, slot.y));
        if (key instanceof AEItemKey item) {
            item.getItem().getDefaultInstance().getTags().forEach(tag -> addTag(list, field, tag.location().toString()));
        } else if (key instanceof AEFluidKey fluid) {
            fluid.getFluid().defaultFluidState().getTags().forEach(tag -> addTag(list, field, tag.location().toString()));
        } else {
            list.setVisible(false);
            return;
        }
        list.setVisible(true);
    }

    private static void addTag(AEListBox list, MultilineTextFieldWidget field, String tag) {
        var label = Component.literal(tag);
        var font = Minecraft.getInstance().font;
        list.addItem(new AEListBox.ListItem() {

            private boolean visible = true;
            private Point position = new Point(0, 0);

            @Override
            public void setVisible(boolean visible) {
                this.visible = visible;
            }

            @Override
            public void setPosition(Point position) {
                this.position = position;
            }

            @Override
            public Rect2i getBounds() {
                return new Rect2i(position.getX(), position.getY(), font.width(label), font.lineHeight);
            }

            @Override
            public void drawForegroundLayer(GuiGraphics graphics, Rect2i bounds, Point mouse) {
                if (visible) graphics.drawString(font, label, bounds.getX(), bounds.getY(), 0xFFFFFF);
            }

            @Override
            public boolean onMouseUp(Point mouse, int button) {
                if (!visible || !getBounds().contains(mouse.getX(), mouse.getY())) return false;
                if (button == 0) field.setValue(tag);
                else if (button == 1) Minecraft.getInstance().keyboardHandler.setClipboard(tag);
                else return false;
                return true;
            }

            @Override
            public Tooltip getTooltip(int mouseX, int mouseY) {
                return new Tooltip(Component.translatable("gui.expatternprovider.tag_selection.entry"));
            }
        });
    }
}
