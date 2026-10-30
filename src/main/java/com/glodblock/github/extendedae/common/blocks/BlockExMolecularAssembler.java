package com.glodblock.github.extendedae.common.blocks;

import com.glodblock.github.extendedae.common.tileentities.TileExMolecularAssembler;
import com.glodblock.github.extendedae.container.ContainerExMolecularAssembler;

import net.minecraft.world.entity.player.Player;

import appeng.menu.MenuOpener;
import appeng.menu.locator.MenuLocators;

public class BlockExMolecularAssembler extends BlockBaseGui<TileExMolecularAssembler> {

    public BlockExMolecularAssembler() {
        super(metalProps().noOcclusion());
    }

    @Override
    public void openGui(TileExMolecularAssembler tile, Player p) {
        MenuOpener.open(ContainerExMolecularAssembler.TYPE, p, MenuLocators.forBlockEntity(tile));
    }
}
