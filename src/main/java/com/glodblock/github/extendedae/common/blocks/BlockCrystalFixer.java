package com.glodblock.github.extendedae.common.blocks;

import com.glodblock.github.extendedae.common.tileentities.TileCrystalFixer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import appeng.api.orientation.IOrientationStrategy;
import appeng.api.orientation.OrientationStrategies;

import org.jetbrains.annotations.NotNull;

public class BlockCrystalFixer extends BlockBaseGui<TileCrystalFixer> {

    public BlockCrystalFixer() {
        super(metalProps().noOcclusion());
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getLightBlock(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return 2;
    }

    @Override
    public IOrientationStrategy getOrientationStrategy() {
        return OrientationStrategies.facing();
    }

    @Override
    public void openGui(TileCrystalFixer tile, Player p) {
        tile.refuel(p);
    }
}
