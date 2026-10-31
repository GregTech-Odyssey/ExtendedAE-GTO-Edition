package com.glodblock.github.extendedae.common.me.modlist;

import com.glodblock.github.extendedae.util.FCUtil;

import appeng.api.stacks.AEKey;
import appeng.util.Platform;
import appeng.util.prioritylist.IPartitionList;

import com.gto.fastcollection.fastutil.OpenCacheHashSet;

import java.util.Collections;

public class ModPriorityList implements IPartitionList {

    private final OpenCacheHashSet<String> modids = new OpenCacheHashSet<>();

    public ModPriorityList(String modid) {
        Collections.addAll(this.modids, FCUtil.trimSplit(modid));
    }

    @Override
    public boolean isListed(AEKey input) {
        return this.modids.contains(input.getModId()) || this.modids.contains(Platform.getModName(input.getModId()));
    }

    @Override
    public boolean isEmpty() {
        return this.modids.isEmpty();
    }

    @Override
    public Iterable<AEKey> getItems() {
        return Collections.emptyList();
    }
}
