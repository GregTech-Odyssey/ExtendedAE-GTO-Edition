package com.glodblock.github.extendedae.client.gui;

import com.glodblock.github.extendedae.container.ContainerExDrive;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.style.ScreenStyle;

public class GuiExDrive extends AEBaseScreen<ContainerExDrive> {

    public GuiExDrive(ContainerExDrive menu, Inventory playerInventory, Component title, ScreenStyle style) {
        super(menu, playerInventory, title, style);
        widgets.addOpenPriorityButton();
    }
}
