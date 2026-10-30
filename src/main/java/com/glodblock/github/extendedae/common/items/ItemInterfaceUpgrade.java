package com.glodblock.github.extendedae.common.items;

import com.glodblock.github.extendedae.common.EPPItemAndBlock;
import com.glodblock.github.extendedae.common.tileentities.TileExInterface;

import net.minecraft.world.item.Item;

import appeng.blockentity.misc.InterfaceBlockEntity;
import appeng.parts.misc.InterfacePart;

public class ItemInterfaceUpgrade extends ItemUpgrade {

    public ItemInterfaceUpgrade() {
        super(new Item.Properties());
        this.addTile(InterfaceBlockEntity.class, EPPItemAndBlock.EX_INTERFACE, TileExInterface.class);
        this.addPart(InterfacePart.class, EPPItemAndBlock.EX_INTERFACE_PART);
    }
}
