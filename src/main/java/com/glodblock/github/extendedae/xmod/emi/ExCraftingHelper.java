package com.glodblock.github.extendedae.xmod.emi;

import com.glodblock.github.extendedae.container.ContainerExCraftingTerminal;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import appeng.api.stacks.AEItemKey;
import appeng.core.AELog;
import appeng.core.sync.network.NetworkHandler;
import appeng.core.sync.packets.FillCraftingGridFromRecipePacket;
import appeng.integration.modules.jeirei.EncodingHelper;
import appeng.menu.me.common.GridInventoryEntry;
import appeng.menu.me.common.MEStorageMenu;
import appeng.util.CraftingRecipeUtil;

import it.unimi.dsi.fastutil.objects.Reference2IntMaps;

import java.util.Comparator;

public class ExCraftingHelper {

    private static final Comparator<GridInventoryEntry> ENTRY_COMPARATOR = Comparator.comparingLong(GridInventoryEntry::getStoredAmount);

    public static void performTransfer(ContainerExCraftingTerminal menu, Recipe<?> recipe, int recipeSize, boolean craftMissing) {
        // We send the items in the recipe in any case to serve as a fallback in case the recipe is transient
        var templateItems = findGoodTemplateItems(recipe, recipeSize, menu);
        var recipeId = recipe.getId();
        // Don't transmit a recipe id to the server in case the recipe is not actually resolvable
        // Recipe viewers can synthesize recipes that do not exist on the server.
        if (menu.getPlayer().level().getRecipeManager().byKey(recipe.getId()).isEmpty()) {
            AELog.debug("Cannot send recipe id %s to server because it's transient", recipeId);
            recipeId = null;
        }
        NetworkHandler.instance().sendToServer(new FillCraftingGridFromRecipePacket(recipeId, templateItems, craftMissing));
    }

    private static NonNullList<ItemStack> findGoodTemplateItems(Recipe<?> recipe, int recipeSize, MEStorageMenu menu) {
        var ingredientPriorities = EncodingHelper.getIngredientPriorities(menu, ENTRY_COMPARATOR);
        var templateItems = NonNullList.withSize(recipeSize, ItemStack.EMPTY);
        var ingredients = CraftingRecipeUtil.ensure3by3CraftingMatrix(recipe);
        for (int i = 0; i < Math.min(ingredients.size(), recipeSize); i++) {
            var ingredient = ingredients.get(i);
            if (!ingredient.isEmpty()) {
                // Try to find the best item. In case the ingredient is a tag, it might contain versions the
                // player doesn't actually have
                AEItemKey bestKey = null;
                int bestPriority = Integer.MIN_VALUE;
                for (var iterator = Reference2IntMaps.fastIterator(ingredientPriorities); iterator.hasNext();) {
                    var entry = iterator.next();
                    if (entry.getIntValue() > bestPriority && entry.getKey() instanceof AEItemKey itemKey && itemKey.matches(ingredient)) {
                        bestKey = itemKey;
                        bestPriority = entry.getIntValue();
                    }
                }
                templateItems.set(i, bestKey == null ? ingredient.getItems()[0] : bestKey.toStack());
            }
        }
        return templateItems;
    }
}
