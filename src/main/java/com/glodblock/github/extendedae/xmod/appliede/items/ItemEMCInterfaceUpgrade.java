package com.glodblock.github.extendedae.xmod.appliede.items;

import com.glodblock.github.extendedae.common.EPPItemAndBlock;
import com.glodblock.github.extendedae.common.items.ItemUpgrade;
import com.glodblock.github.extendedae.xmod.appliede.tileentities.TileExEMCInterface;

import net.minecraft.world.item.Item;

import gripe._90.appliede.block.EMCInterfaceBlockEntity;
import gripe._90.appliede.part.EMCInterfacePart;

public class ItemEMCInterfaceUpgrade extends ItemUpgrade {

    public ItemEMCInterfaceUpgrade() {
        super(new Item.Properties());
        this.addTile(EMCInterfaceBlockEntity.class, EPPItemAndBlock.EX_EMC_INTERFACE, TileExEMCInterface.class);
        this.addPart(EMCInterfacePart.class, EPPItemAndBlock.EX_EMC_INTERFACE_PART);
    }
}
