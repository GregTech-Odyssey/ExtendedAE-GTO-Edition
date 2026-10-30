package com.glodblock.github.extendedae.common.blocks;

import com.glodblock.github.extendedae.common.tileentities.TileExInterface;

import net.minecraft.world.entity.player.Player;

import appeng.menu.locator.MenuLocators;

public class BlockExInterface extends BlockBaseGui<TileExInterface> {

    @Override
    public void openGui(TileExInterface tile, Player p) {
        tile.openMenu(p, MenuLocators.forBlockEntity(tile));
    }
}
