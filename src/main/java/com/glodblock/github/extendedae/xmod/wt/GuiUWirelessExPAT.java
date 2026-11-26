package com.glodblock.github.extendedae.xmod.wt;

import com.glodblock.github.extendedae.client.gui.GuiExPatternTerminal;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import appeng.client.gui.style.ScreenStyle;
import appeng.client.gui.widgets.ScrollableUpgradesPanel;
import appeng.core.definitions.AEItems;
import appeng.menu.SlotSemantics;

import de.mari_023.ae2wtlib.AE2wtlibSlotSemantics;
import de.mari_023.ae2wtlib.wut.CycleTerminalButton;
import de.mari_023.ae2wtlib.wut.IUniversalTerminalCapable;

import java.util.List;

public class GuiUWirelessExPAT extends GuiExPatternTerminal<ContainerUWirelessExPAT> implements IUniversalTerminalCapable {

    public GuiUWirelessExPAT(ContainerUWirelessExPAT container, Inventory playerInventory, Component title, ScreenStyle style) {
        super(container, playerInventory, title, style);
        if (this.getMenu().isWUT()) {
            this.addToLeftToolbar(new CycleTerminalButton((btn) -> this.cycleTerminal()));
        }

        this.widgets.add("upgrades", new ScrollableUpgradesPanel(List.of(
                ScrollableUpgradesPanel.SlotGroup.ofUpgrades(this.getMenu().getSlots(SlotSemantics.UPGRADE), this.getMenu().getHost()),
                new ScrollableUpgradesPanel.SlotGroup(
                        this.getMenu().getSlots(AE2wtlibSlotSemantics.SINGULARITY),
                        () -> List.of(AEItems.QUANTUM_ENTANGLED_SINGULARITY.asItem().getDescription())))));
    }

    public void storeState() {}
}
