package com.glodblock.github.extendedae.recipe.util;

import com.glodblock.github.extendedae.recipe.CircuitCutterRecipe;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import com.google.gson.JsonObject;

public interface MachineRecipe<T extends Recipe<?>> extends RecipeSerializer<CircuitCutterRecipe> {

    void toJson(JsonObject jsonObject, T recipe);
}
