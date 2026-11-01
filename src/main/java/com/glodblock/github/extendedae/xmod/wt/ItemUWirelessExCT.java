package com.glodblock.github.extendedae.xmod.wt;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

import de.mari_023.ae2wtlib.terminal.ItemWT;
import org.jetbrains.annotations.NotNull;

public class ItemUWirelessExCT extends ItemWT {

    public ItemUWirelessExCT() {
        // NO-OP
    }

    @Override
    public @NotNull MenuType<?> getMenuType(@NotNull ItemStack stack) {
        return ContainerUWirelessExCT.TYPE;
    }
}
