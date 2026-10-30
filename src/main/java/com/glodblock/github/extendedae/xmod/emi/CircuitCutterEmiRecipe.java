package com.glodblock.github.extendedae.xmod.emi;

import com.glodblock.github.extendedae.common.EPPItemAndBlock;
import com.glodblock.github.extendedae.recipe.CircuitCutterRecipe;
import com.glodblock.github.extendedae.recipe.util.FluidIngredient;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class CircuitCutterEmiRecipe extends BasicEmiRecipe {

    private static final ResourceLocation TEXTURE = new ResourceLocation("ae2", "textures/guis/circuit_cutter.png");
    public static final EmiRecipeCategory CATEGORY = new EmiRecipeCategory(CircuitCutterRecipe.TYPE_ID, EmiStack.of(EPPItemAndBlock.CIRCUIT_CUTTER)) {

        @Override
        public Component getName() {
            return EPPItemAndBlock.CIRCUIT_CUTTER.getName();
        }
    };

    public CircuitCutterEmiRecipe(CircuitCutterRecipe recipe) {
        super(CATEGORY, recipe.getId(), 110, 26);
        inputs.add(EmiIngredient.of((Ingredient) recipe.getInput().getIngredient(), recipe.getInput().getAmount()));
        if (!recipe.getFluid().isEmpty()) {
            var fluids = ((FluidIngredient) recipe.getFluid().getIngredient()).getFluid();
            var alternatives = new ObjectArrayList<EmiStack>(fluids.size());
            for (var fluid : fluids) {
                alternatives.add(EmiStack.of(fluid, recipe.getFluid().getAmount()));
            }
            inputs.add(EmiIngredient.of(alternatives, recipe.getFluid().getAmount()));
        }
        outputs.add(EmiStack.of(recipe.output));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE, 0, 0, 110, 26, 27, 32);
        widgets.addAnimatedTexture(TEXTURE, 104, 4, 6, 18, 178, 36, 2000, false, true, false);
        widgets.addSlot(inputs.get(0), 0, 4).drawBack(false);
        if (inputs.size() > 1) {
            widgets.addSlot(inputs.get(1), 18, 4).drawBack(false);
        }
        widgets.addSlot(outputs.get(0), 81, 4).drawBack(false).recipeContext(this);
    }
}
