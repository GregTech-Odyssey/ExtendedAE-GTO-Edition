package com.glodblock.github.extendedae.xmod.mega;

import com.glodblock.github.extendedae.common.EPPItemAndBlock;

import appeng.api.upgrades.Upgrades;

import gripe._90.megacells.definition.MEGAItems;

public class MEGACommonLoad {

    public static void init() {
        Upgrades.add(MEGAItems.GREATER_ENERGY_CARD, EPPItemAndBlock.WIRELESS_CONNECTOR, 4);
        Upgrades.add(MEGAItems.GREATER_ENERGY_CARD, EPPItemAndBlock.WIRELESS_HUB, 4);
    }
}
