package com.glodblock.github.extendedae.xmod.wt;

import com.glodblock.github.extendedae.container.ContainerExCraftingTerminal;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

import appeng.api.storage.ITerminalHost;
import appeng.menu.ToolboxMenu;
import appeng.menu.implementations.MenuTypeBuilder;
import appeng.menu.slot.RestrictedInputSlot;

import de.mari_023.ae2wtlib.AE2wtlibSlotSemantics;
import de.mari_023.ae2wtlib.wct.WCTMenuHost;
import de.mari_023.ae2wtlib.wut.ItemWUT;

public class ContainerUWirelessExCT extends ContainerExCraftingTerminal {

    public static final MenuType<ContainerUWirelessExCT> TYPE = MenuTypeBuilder
            .create(ContainerUWirelessExCT::new, HostUWirelessExCT.class)
            .build("u_wireless_ex_crafting_terminal");
    private final HostUWirelessExCT host;
    private final ToolboxMenu toolboxMenu;

    public ContainerUWirelessExCT(int id, Inventory ip, HostUWirelessExCT host) {
        super(TYPE, id, ip, host, true);
        this.host = host;
        this.toolboxMenu = new ToolboxMenu(this);
        // MEStorageMenu already adds one slot per host upgrade slot.
        this.addSlot(new RestrictedInputSlot(RestrictedInputSlot.PlacableItemType.QE_SINGULARITY, this.host.getSubInventory(WCTMenuHost.INV_SINGULARITY), 0), AE2wtlibSlotSemantics.SINGULARITY);
    }

    @Override
    public void broadcastChanges() {
        this.toolboxMenu.tick();
        super.broadcastChanges();
    }

    public boolean isWUT() {
        return this.host.getItemStack().getItem() instanceof ItemWUT;
    }

    public ITerminalHost getHost() {
        return this.host;
    }

    public ToolboxMenu getToolbox() {
        return this.toolboxMenu;
    }
}
