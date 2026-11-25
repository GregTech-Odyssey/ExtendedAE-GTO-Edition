package com.glodblock.github.extendedae.xmod.wt;

import com.glodblock.github.extendedae.client.gui.GuiExCraftingTerminal;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import appeng.client.gui.style.ScreenStyle;

import de.mari_023.ae2wtlib.wut.CycleTerminalButton;
import de.mari_023.ae2wtlib.wut.IUniversalTerminalCapable;

public class GuiUWirelessExCT extends GuiExCraftingTerminal<ContainerUWirelessExCT> implements IUniversalTerminalCapable {

    public GuiUWirelessExCT(ContainerUWirelessExCT container, Inventory playerInventory, Component title, ScreenStyle style) {
        super(container, playerInventory, title, style);
        if (this.getMenu().isWUT()) {
            this.addToLeftToolbar(new CycleTerminalButton((btn) -> this.cycleTerminal()));
        }
    }

    public void storeState() {}
}
