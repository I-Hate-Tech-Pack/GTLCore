package org.gtlcore.gtlcore.mixin.gtm.api.recipe;

import org.gtlcore.gtlcore.api.machine.trait.IDistinctMachine;
import org.gtlcore.gtlcore.api.machine.trait.IMEPartMachine;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.ingredient.IntProviderIngredient;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.utils.ItemStackHashStrategy;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import com.google.common.primitives.Ints;
import it.unimi.dsi.fastutil.objects.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.*;

import static com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability.CAP;

@Mixin(ItemRecipeCapability.class)
public class ItemRecipeCapabilityMixin {

    @Shadow(remap = false)
    public Ingredient copyWithModifier(Ingredient content, ContentModifier modifier) {
        return null;
    }

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public int limitParallel(GTRecipe recipe, IRecipeCapabilityHolder holder, int multiplier) {
        if (holder instanceof IMEPartMachine iMEPartMachine && (iMEPartMachine.isMEOutPutBus() ||
                iMEPartMachine.isMEOutPutDual() || recipe.getOutputContents(CAP).isEmpty())) {
            return multiplier;
        }
        var outputContents = recipe.outputs.get(CAP);
        if (outputContents == null || outputContents.isEmpty()) return multiplier;
        List<IRecipeHandler<?>> handlers = holder.getCapabilitiesProxy().get(IO.OUT, CAP);
        if (handlers == null || handlers.isEmpty()) return 0;
        int minMultiplier = 0;
        int maxMultiplier = multiplier;
        int maxCount = 0;
        List<Ingredient> ingredients = new ArrayList<>(outputContents.size());
        for (var content : outputContents) {
            var ing = CAP.of(content.content);
            int count;
            if (ing instanceof SizedIngredient sized) count = sized.getAmount();
            else if (ing instanceof IntProviderIngredient provider) count = provider.getCountProvider().getMaxValue();
            else count = 1;

            maxCount = Math.max(maxCount, count);
            ingredients.add(ing);
        }
        if (maxCount == 0) return multiplier;
        if (multiplier > Integer.MAX_VALUE / maxCount) {
            maxMultiplier = multiplier = Integer.MAX_VALUE / maxCount;
        }
        while (minMultiplier != maxMultiplier) {
            List<Ingredient> copied = new ArrayList<>();
            for (final var ing : ingredients) {
                copied.add(this.copyWithModifier(ing, ContentModifier.multiplier(multiplier)));
            }
            for (var handler : handlers) {
                copied = (List<Ingredient>) handler.handleRecipe(IO.OUT, recipe, copied, null, true);
                if (copied == null) break;
            }
            int[] bin = ParallelLogic.adjustMultiplier(copied == null, minMultiplier, multiplier, maxMultiplier);
            minMultiplier = bin[0];
            multiplier = bin[1];
            maxMultiplier = bin[2];
        }
        return multiplier;
    }

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public int getMaxParallelRatio(IRecipeCapabilityHolder holder, GTRecipe recipe, int parallelAmount) {
        if (parallelAmount <= 1 || recipe.inputs.get(CAP) == null) return parallelAmount;
        if (holder instanceof IDistinctMachine machine) {
            if (machine.getRecipeHandleParts().isEmpty()) return 0;
            Object2LongOpenCustomHashMap<ItemStack> ingredientStacks = new Object2LongOpenCustomHashMap<>(ItemStackHashStrategy.comparingAllButCount());
            if (machine.isDistinct() && machine.getDistinctHatch() != null) {
                for (var it = machine.getDistinctHatch().getContent(CAP).object2LongEntrySet().fastIterator(); it.hasNext();) {
                    var entry = it.next();
                    ingredientStacks.computeLong((ItemStack) entry.getKey(), (k, v) -> v == null ? entry.getLongValue() : v + entry.getLongValue());
                }
            } else {
                Object2LongOpenCustomHashMap<ItemStack> map = new Object2LongOpenCustomHashMap<>(ItemStackHashStrategy.comparingAllButCount());
                for (var it : machine.getCapabilities().get(IO.IN)) {
                    for (var obj = it.getContent(CAP).object2LongEntrySet().fastIterator(); obj.hasNext();) {
                        var entry = obj.next();
                        map.computeLong((ItemStack) entry.getKey(), (k, v) -> v == null ? entry.getLongValue() : v + entry.getLongValue());
                    }
                }
                for (var obj : map.object2LongEntrySet()) {
                    ingredientStacks.computeLong(obj.getKey(), (k, v) -> v == null ? obj.getLongValue() : v + obj.getLongValue());
                }
            }
            Object2IntOpenHashMap<Ingredient> countableMap = new Object2IntOpenHashMap<>();
            for (Content content : recipe.getInputContents(CAP)) {
                Ingredient recipeIngredient = CAP.of(content.content);
                int ingredientCount;
                if (recipeIngredient instanceof SizedIngredient sizedIngredient) {
                    ingredientCount = sizedIngredient.getAmount();
                } else if (recipeIngredient instanceof IntProviderIngredient intProviderIngredient) {
                    ingredientCount = intProviderIngredient.getSampledCount(GTValues.RNG);
                } else {
                    ingredientCount = 1;
                }
                if (content.chance > 0) {
                    countableMap.addTo(recipeIngredient, ingredientCount);
                }
            }
            if (countableMap.isEmpty()) return parallelAmount;
            long needed;
            long available;
            for (var it = Object2IntMaps.fastIterator(countableMap); it.hasNext(); parallelAmount = Ints.saturatedCast(Math.min(parallelAmount, available / needed))) {
                var entry = it.next();
                needed = entry.getIntValue();
                available = 0;
                for (var iter = Object2LongMaps.fastIterator(ingredientStacks); iter.hasNext();) {
                    var inputItem = iter.next();
                    if (entry.getKey().test(inputItem.getKey())) {
                        available += inputItem.getLongValue();
                        break;
                    }
                }
                if (available < needed) {
                    parallelAmount = 0;
                    break;
                }
            }
            return parallelAmount;
        }
        return 1;
    }
}
