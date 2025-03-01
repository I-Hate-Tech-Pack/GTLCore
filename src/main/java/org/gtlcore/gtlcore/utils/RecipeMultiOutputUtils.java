package org.gtlcore.gtlcore.utils;

import org.gtlcore.gtlcore.config.ConfigHolder;

import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EasterFG on 2024/12/10
 */
public class RecipeMultiOutputUtils {

    public static List<Content> copyAsList(Content content, RecipeCapability<?> capability, @Nullable ContentModifier modifier, boolean input) {
        if (modifier == null || content.chance == 0) {
            return List.of(new Content(capability.copyContent(content.content), content.chance, content.maxChance, content.tierChanceBoost, content.slotName, content.uiName));
        } else {
            var fluidIngredients = copyWithModifier((FluidIngredient) content.content, modifier, input);
            List<Content> list = new ArrayList<>();
            for (var fluidIngredient : fluidIngredients) {
                list.add(new Content(fluidIngredient, content.chance, content.maxChance, content.tierChanceBoost, content.slotName, content.uiName));
            }
            return list;
        }
    }

    public static List<FluidIngredient> copyWithModifier(FluidIngredient content, ContentModifier modifier, boolean input) {
        if (content.isEmpty()) return List.of(content.copy());
        List<FluidIngredient> list = new ArrayList<>();
        FluidIngredient copy = content.copy();
        long amount = modifier.apply(copy.getAmount()).longValue();
        if (amount > Integer.MAX_VALUE) {
            long times = (long) Math.min(Math.ceil((double) amount / Integer.MAX_VALUE),
                    ConfigHolder.INSTANCE.recipeMultiMax);
            times = input ? 32 : times;
            for (; times > 0; times--) {
                var cp = content.copy();
                if (times == 1) {
                    cp.setAmount(amount % Integer.MAX_VALUE);
                } else {
                    cp.setAmount(Integer.MAX_VALUE);
                }
                list.add(cp);
            }
        } else {
            copy.setAmount(amount);
            list.add(copy);
        }
        return list;
    }
}
