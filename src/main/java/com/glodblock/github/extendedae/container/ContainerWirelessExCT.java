package com.glodblock.github.extendedae.container;

import com.glodblock.github.extendedae.common.me.itemhost.HostWirelessExCT;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

import appeng.api.networking.IGridNode;
import appeng.api.upgrades.IUpgradeInventory;
import appeng.menu.ToolboxMenu;
import appeng.menu.implementations.MenuTypeBuilder;

public class ContainerWirelessExCT extends ContainerExCraftingTerminal {

    public static final MenuType<ContainerWirelessExCT> TYPE = MenuTypeBuilder
            .create(ContainerWirelessExCT::new, HostWirelessExCT.class)
            .build("wireless_ex_ct");

    private final ToolboxMenu toolbox;

    public ContainerWirelessExCT(int id, Inventory playerInventory, HostWirelessExCT host) {
        super(TYPE, id, playerInventory, host, true);
        this.toolbox = new ToolboxMenu(this);
        // MEStorageMenu already adds one slot per host upgrade slot.
    }

    @Override
    public void broadcastChanges() {
        this.toolbox.tick();
        super.broadcastChanges();
    }

    @Override
    public IGridNode getNetworkNode() {
        return ((HostWirelessExCT) this.getHost()).getActionableNode();
    }

    public final IUpgradeInventory getUpgrades() {
        return this.getHost().getUpgrades();
    }

    public ToolboxMenu getToolbox() {
        return this.toolbox;
    }
}
