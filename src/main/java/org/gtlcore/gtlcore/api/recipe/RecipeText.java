package org.gtlcore.gtlcore.api.recipe;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class RecipeText {

    public static MutableComponent getRecipeInputText(GTRecipe recipe) {
        return getIngredientText(recipe, true);
    }

    public static MutableComponent getRecipeOutputText(GTRecipe recipe) {
        return getIngredientText(recipe, false);
    }

    private static MutableComponent getIngredientText(GTRecipe recipe, boolean io) {
        if (recipe != null) {
            MutableComponent buffer = io ? Component.translatable("gtceu.top.recipe_input") : Component.translatable("gtceu.top.recipe_output");
            var items = io ? recipe.getInputContents(ItemRecipeCapability.CAP) : recipe.getOutputContents(ItemRecipeCapability.CAP);
            var fluids = io ? recipe.getInputContents(FluidRecipeCapability.CAP) : recipe.getOutputContents(FluidRecipeCapability.CAP);
            for (var item : items) {
                var stacks = ItemRecipeCapability.CAP.of(item.content).getItems();
                if (stacks.length == 0) continue;
                var stack = stacks[0];
                int count = stack.getCount();
                float chance = 100.0F * (float) item.chance / (float) item.maxChance;
                String percent = FormattingUtil.formatPercent(chance);
                if (chance == 100.0F) {
                    buffer.append(Component.translatable("gtceu.machine.lockrecipe.line.0", stack.getHoverName(), count).getString());
                } else if (chance == 0.0F) {
                    buffer.append(Component.translatable("gtceu.machine.lockrecipe.line.2", stack.getHoverName(), count).getString());
                } else {
                    buffer.append(Component.translatable("gtceu.machine.lockrecipe.line.1", stack.getHoverName(), count, percent).getString());
                }
            }
            for (var fluid : fluids) {
                var stacks = FluidRecipeCapability.CAP.of(fluid.content).getStacks();
                if (stacks.length == 0) continue;
                var stack = stacks[0];
                double amount = (double) stack.getAmount();
                String s;
                if (amount >= 1000000.0) {
                    amount /= 1000000.0;
                    s = FormattingUtil.DECIMAL_FORMAT_1F.format((float) amount) + "KB";
                } else if (amount >= 1000.0) {
                    amount /= 1000.0;
                    s = FormattingUtil.DECIMAL_FORMAT_1F.format((float) amount) + "B";
                } else {
                    s = amount + "mB";
                }
                float chance = 100.0F * (float) fluid.chance / (float) fluid.maxChance;
                String percent = FormattingUtil.formatPercent(chance);
                if (chance == 100.0F) {
                    buffer.append(Component.translatable("gtceu.machine.lockrecipe.line.0", stack.getDisplayName(), s).getString());
                } else if (chance == 0.0F) {
                    buffer.append(Component.translatable("gtceu.machine.lockrecipe.line.2", stack.getDisplayName(), s).getString());
                } else {
                    buffer.append(Component.translatable("gtceu.machine.lockrecipe.line.1", stack.getDisplayName(), s, percent).getString());
                }
            }
            return buffer;
        }
        return null;
    }
}
