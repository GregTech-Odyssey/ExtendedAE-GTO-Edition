package com.glodblock.github.extendedae.xmod.emi;

import com.glodblock.github.extendedae.ExtendedAE;
import com.glodblock.github.extendedae.api.CraftingMode;
import com.glodblock.github.extendedae.container.ContainerExCraftingTerminal;
import com.glodblock.github.extendedae.network.EPPNetworkHandler;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

import appeng.core.localization.ItemModText;
import appeng.integration.modules.emi.AbstractRecipeHandler;
import appeng.integration.modules.emi.EmiUseCraftingRecipeHandler;
import appeng.util.CraftingRecipeUtil;

import com.glodblock.github.glodium.network.packet.CGenericPacket;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.Nullable;

public class ExCraftingTransferHandler<T extends ContainerExCraftingTerminal> extends AbstractRecipeHandler<T> {

    public ExCraftingTransferHandler(Class<T> containerClass) {
        super(containerClass);
    }

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        var category = recipe.getCategory();
        return category == VanillaEmiRecipeCategories.CRAFTING || category == VanillaEmiRecipeCategories.STONECUTTING || category == VanillaEmiRecipeCategories.SMITHING;
    }

    @Override
    public Result transferRecipe(T menu, @Nullable Recipe<?> recipe, EmiRecipe display, boolean doTransfer) {
        CraftingMode mode;
        int recipeSize;
        Int2ObjectOpenHashMap<Ingredient> ingredients;
        // AE2's generic crafting predicate also accepts stonecutting and smithing recipes.
        if (recipe != null && (recipe.getType() == RecipeType.STONECUTTING || recipe.getType() == RecipeType.SMITHING)) {
            mode = recipe.getType() == RecipeType.STONECUTTING ? CraftingMode.STONECUTTER : CraftingMode.SMITHING;
            recipeSize = mode == CraftingMode.STONECUTTER ? 1 : 3;
            var inputs = CraftingRecipeUtil.getIngredients(recipe);
            ingredients = new Int2ObjectOpenHashMap<>(inputs.size());
            for (int i = 0; i < inputs.size(); i++) {
                ingredients.put(i, inputs.get(i));
            }
        } else if (isCraftingRecipe(recipe, display)) {
            if (!fitsIn3x3Grid(recipe, display)) {
                return Result.createFailed(ItemModText.RECIPE_TOO_LARGE.text());
            }
            if (recipe == null) {
                recipe = createCraftingRecipe(display);
            }
            mode = CraftingMode.CRAFTING;
            recipeSize = 9;
            ingredients = EmiUseCraftingRecipeHandler.getGuiSlotToIngredientMap(recipe);
        } else {
            return Result.createNotApplicable();
        }

        var missing = menu.findMissingIngredients(ingredients);
        if (mode == CraftingMode.CRAFTING && missing.missingSlots().size() == ingredients.size()) {
            return Result.createFailed(ItemModText.NO_ITEMS.text(), missing.missingSlots());
        }
        if (!doTransfer) {
            return missing.anyMissingOrCraftable() ? new Result.PartiallyCraftable(missing) : Result.createSuccessful();
        }

        EPPNetworkHandler.INSTANCE.sendToServer(new CGenericPacket("set_mode", mode.ordinal()));
        ExCraftingHelper.performTransfer(menu, recipe, recipeSize, AbstractContainerScreen.hasControlDown());
        if (mode == CraftingMode.STONECUTTER) {
            EPPNetworkHandler.INSTANCE.sendToServer(new CGenericPacket("stonecutter_select", recipe.getId().toString()));
        }
        return Result.createSuccessful();
    }

    private static Recipe<?> createCraftingRecipe(EmiRecipe display) {
        var ingredients = NonNullList.withSize(9, Ingredient.EMPTY);
        var inputs = display.getInputs();
        for (int i = 0; i < Math.min(inputs.size(), ingredients.size()); i++) {
            var alternatives = inputs.get(i).getEmiStacks();
            var stacks = new ObjectArrayList<ItemStack>(alternatives.size());
            for (var alternative : alternatives) {
                var stack = alternative.getItemStack();
                if (!stack.isEmpty()) {
                    stacks.add(stack);
                }
            }
            ingredients.set(i, Ingredient.of(stacks.toArray(ItemStack[]::new)));
        }
        return new ShapedRecipe(ExtendedAE.id("emi_crafting"), "", CraftingBookCategory.MISC, 3, 3, ingredients, ItemStack.EMPTY);
    }
}
