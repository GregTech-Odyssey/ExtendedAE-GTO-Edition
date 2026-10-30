package com.glodblock.github.extendedae.common.tileentities.matrix;

import com.glodblock.github.extendedae.common.EPPItemAndBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import com.glodblock.github.glodium.util.GlodUtil;

public class TileAssemblerMatrixGlass extends TileAssemblerMatrixWall {

    public TileAssemblerMatrixGlass(BlockPos pos, BlockState blockState) {
        super(GlodUtil.getTileType(TileAssemblerMatrixGlass.class, TileAssemblerMatrixGlass::new, EPPItemAndBlock.ASSEMBLER_MATRIX_GLASS), pos, blockState);
    }
}
