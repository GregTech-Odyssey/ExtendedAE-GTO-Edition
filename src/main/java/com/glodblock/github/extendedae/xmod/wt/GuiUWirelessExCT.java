package com.glodblock.github.extendedae.xmod.wt;

import com.glodblock.github.extendedae.client.gui.GuiExCraftingTerminal;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import appeng.client.gui.style.ScreenStyle;
import appeng.client.gui.widgets.BackgroundPanel;

import de.mari_023.ae2wtlib.wut.CycleTerminalButton;
import de.mari_023.ae2wtlib.wut.IUniversalTerminalCapable;

public class GuiUWirelessExCT extends GuiExCraftingTerminal<ContainerUWirelessExCT> implements IUniversalTerminalCapable {

    public GuiUWirelessExCT(ContainerUWirelessExCT container, Inventory playerInventory, Component title, ScreenStyle style) {
        super(container, playerInventory, title, style);
        if (this.getMenu().isWUT()) {
            this.addToLeftToolbar(new CycleTerminalButton((btn) -> this.cycleTerminal()));
        }

        // MEStorageScreen already adds the "upgrades" and "toolbox" panels for us.
        this.widgets.add("singularityBackground", new BackgroundPanel(style.getImage("singularityBackground")));
    }

    public void storeState() {}
}
