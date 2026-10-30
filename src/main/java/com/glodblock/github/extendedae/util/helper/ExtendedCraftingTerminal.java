package com.glodblock.github.extendedae.util.helper;

import com.glodblock.github.extendedae.api.CraftingMode;

import net.minecraft.resources.ResourceLocation;

import appeng.api.inventories.ISegmentedInventory;
import appeng.api.networking.security.IActionHost;
import appeng.api.storage.ITerminalHost;

public interface ExtendedCraftingTerminal extends ISegmentedInventory, ITerminalHost, IActionHost {

    CraftingMode getCurrentMode();

    void setCurrentMode(CraftingMode mode);

    ResourceLocation getStonecuttingRecipe();

    void setStonecuttingRecipe(ResourceLocation stonecuttingRecipe);
}
