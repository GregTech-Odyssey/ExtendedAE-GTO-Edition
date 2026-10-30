package com.glodblock.github.extendedae.common.me.wireless;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import appeng.api.networking.IGridNode;

public interface WirelessNode {

    long getFrequency();

    Level getLevel();

    BlockPos getBlockPos();

    IGridNode getGridNode();

    BlockEntity getBlockEntity();
}
