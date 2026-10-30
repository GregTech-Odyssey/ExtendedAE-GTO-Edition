package com.glodblock.github.extendedae.xmod.appliede.blocks;

import com.glodblock.github.extendedae.api.ISpecialDrop;
import com.glodblock.github.extendedae.common.blocks.BlockBaseGui;
import com.glodblock.github.extendedae.xmod.appliede.tileentities.TileExEMCInterface;

import net.minecraft.world.entity.player.Player;

import appeng.menu.locator.MenuLocators;

public class BlockExEMCInterface extends BlockBaseGui<TileExEMCInterface> implements ISpecialDrop {

    @Override
    public void openGui(TileExEMCInterface tile, Player p) {
        tile.openMenu(p, MenuLocators.forBlockEntity(tile));
    }
}
