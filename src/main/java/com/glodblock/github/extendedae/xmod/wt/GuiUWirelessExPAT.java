package com.glodblock.github.extendedae.xmod.wt;

import com.glodblock.github.extendedae.client.gui.GuiExPatternTerminal;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import appeng.client.gui.style.ScreenStyle;
import appeng.client.gui.widgets.BackgroundPanel;
import appeng.client.gui.widgets.UpgradesPanel;
import appeng.menu.SlotSemantics;

import de.mari_023.ae2wtlib.wut.CycleTerminalButton;
import de.mari_023.ae2wtlib.wut.IUniversalTerminalCapable;

public class GuiUWirelessExPAT extends GuiExPatternTerminal<ContainerUWirelessExPAT> implements IUniversalTerminalCapable {

    public GuiUWirelessExPAT(ContainerUWirelessExPAT container, Inventory playerInventory, Component title, ScreenStyle style) {
        super(container, playerInventory, title, style);
        if (this.getMenu().isWUT()) {
            this.addToLeftToolbar(new CycleTerminalButton((btn) -> this.cycleTerminal()));
        }

        this.widgets.add("upgrades", new UpgradesPanel(this.getMenu().getSlots(SlotSemantics.UPGRADE), this.getMenu().getHost()));

        this.widgets.add("singularityBackground", new BackgroundPanel(style.getImage("singularityBackground")));
    }

    public void storeState() {}
}
