package com.glodblock.github.extendedae.xmod.emi;

import com.glodblock.github.extendedae.client.gui.pattern.GuiPattern;
import com.glodblock.github.extendedae.common.EPPItemAndBlock;
import com.glodblock.github.extendedae.container.ContainerExCraftingTerminal;
import com.glodblock.github.extendedae.container.ContainerWirelessExCT;
import com.glodblock.github.extendedae.container.pattern.ContainerPattern;
import com.glodblock.github.extendedae.recipe.CircuitCutterRecipe;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.GenericStack;
import appeng.integration.modules.emi.EmiStackHelper;
import appeng.items.misc.WrappedGenericStack;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.EmiStackInteraction;

@EmiEntrypoint
public class ExtendedAEEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        registry.addRecipeHandler(ContainerExCraftingTerminal.TYPE, new ExCraftingTransferHandler<>(ContainerExCraftingTerminal.class));
        registry.addRecipeHandler(ContainerWirelessExCT.TYPE, new ExCraftingTransferHandler<>(ContainerWirelessExCT.class));
        registry.addCategory(CircuitCutterEmiRecipe.CATEGORY);
        registry.addWorkstation(CircuitCutterEmiRecipe.CATEGORY, EmiStack.of(EPPItemAndBlock.CIRCUIT_CUTTER));
        for (var recipe : registry.getRecipeManager().getAllRecipesFor(CircuitCutterRecipe.TYPE)) {
            registry.addRecipe(new CircuitCutterEmiRecipe(recipe));
        }
        registry.addStackProvider(GuiPattern.class, (screen, x, y) -> {
            var slot = screen.getSlotUnderMouse();
            if (slot instanceof ContainerPattern.DisplayOnlySlot displaySlot && !slot.getItem().isEmpty()) {
                var stack = slot.getItem();
                var key = stack.getItem() instanceof WrappedGenericStack wrapped ? wrapped.unwrapWhat(stack) : AEItemKey.of(stack);
                var amount = stack.getItem() instanceof WrappedGenericStack wrapped ? wrapped.unwrapAmount(stack) : displaySlot.getActualAmount();
                if (key != null && amount > 0) {
                    var ingredient = EmiStackHelper.toEmiStack(new GenericStack(key, amount));
                    if (ingredient != null) {
                        return new EmiStackInteraction(ingredient, null, false);
                    }
                }
            }
            return EmiStackInteraction.EMPTY;
        });
    }
}
