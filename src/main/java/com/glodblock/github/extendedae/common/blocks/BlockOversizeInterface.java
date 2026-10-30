package com.glodblock.github.extendedae.common.blocks;

import com.glodblock.github.extendedae.common.tileentities.TileOversizeInterface;

import net.minecraft.world.entity.player.Player;

import appeng.menu.locator.MenuLocators;

public class BlockOversizeInterface extends BlockBaseGui<TileOversizeInterface> {

    @Override
    public void openGui(TileOversizeInterface tile, Player p) {
        tile.openMenu(p, MenuLocators.forBlockEntity(tile));
    }
}
