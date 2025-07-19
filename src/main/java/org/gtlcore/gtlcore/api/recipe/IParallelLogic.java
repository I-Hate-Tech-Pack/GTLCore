package org.gtlcore.gtlcore.api.recipe;

import org.gtlcore.gtlcore.api.machine.trait.IRecipeCapabilityMachine;
import org.gtlcore.gtlcore.api.recipe.ingredient.IngredientStackHashStrategy;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.api.recipe.ingredient.IntProviderIngredient;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import com.gregtechceu.gtceu.utils.ItemStackHashStrategy;

import com.lowdragmc.lowdraglib.side.fluid.FluidStack;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import com.hepdd.gtmthings.common.block.machine.trait.CatalystFluidStackHandler;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import it.unimi.dsi.fastutil.objects.Object2LongOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;

public interface IParallelLogic {

    static long getParallel(IRecipeCapabilityHolder holder, GTRecipe recipe, long parallelAmount) {
        if (parallelAmount <= 1) return parallelAmount;
        long maxParallel = getMaxParallel(holder, recipe, parallelAmount);
        if (maxParallel == 0L) return 0L;
        return getMinParallel(holder, recipe, maxParallel);
    }

    static long getMaxParallel(IRecipeCapabilityHolder holder, GTRecipe recipe, long parallelAmount) {
        for (var cap : recipe.inputs.keySet()) {
            if (cap == ItemRecipeCapability.CAP) {
                parallelAmount = Math.min(parallelAmount, getInputItemParallel(holder, recipe, parallelAmount));
                if (parallelAmount == 0L) break;
            } else if (cap == FluidRecipeCapability.CAP) {
                parallelAmount = Math.min(parallelAmount, getInputFluidParallel(holder, recipe, parallelAmount));
                if (parallelAmount == 0L) break;
            }
        }
        return parallelAmount;
    }

    static long getMinParallel(IRecipeCapabilityHolder holder, GTRecipe recipe, long parallelAmount) {
        for (var entry : recipe.outputs.entrySet()) {
            if (entry.getKey() == ItemRecipeCapability.CAP && holder instanceof IRecipeLogicMachine machine && !machine.canVoidRecipeOutputs(ItemRecipeCapability.CAP)) {
                parallelAmount = Math.min(parallelAmount, getOutputItemParallel(holder, recipe, entry.getValue(), parallelAmount));
                if (parallelAmount == 0L) break;
            } else if (entry.getKey() == FluidRecipeCapability.CAP && holder instanceof IRecipeLogicMachine machine && !machine.canVoidRecipeOutputs(FluidRecipeCapability.CAP)) {
                parallelAmount = Math.min(parallelAmount, getOutputFluidParallel(holder, recipe, entry.getValue(), parallelAmount));
                if (parallelAmount == 0L) break;
            }
        }
        return parallelAmount;
    }

    static long getInputItemParallel(IRecipeCapabilityHolder holder, GTRecipe recipe, long parallelAmount) {
        if (parallelAmount <= 1) return parallelAmount;
        if (holder instanceof IRecipeCapabilityMachine machine) {
            if (machine.getRecipeHandleParts().isEmpty()) return 0;
            Object2LongOpenCustomHashMap<Ingredient> countableMap = new Object2LongOpenCustomHashMap<>(IngredientStackHashStrategy.comparing());
            for (Content content : recipe.getInputContents(ItemRecipeCapability.CAP)) {
                Ingredient recipeIngredient = ItemRecipeCapability.CAP.of(content.content);
                long ingredientCount;
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
            Object2LongOpenCustomHashMap<ItemStack> ingredientStacks = new Object2LongOpenCustomHashMap<>(ItemStackHashStrategy.comparingAllButCount());
            if (machine.getRecipeHandleMap().get(recipe) != null) {
                for (var it = machine.getRecipeHandleMap().get(recipe).getContent(ItemRecipeCapability.CAP).object2LongEntrySet().fastIterator(); it.hasNext();) {
                    var entry = it.next();
                    ingredientStacks.computeLong((ItemStack) entry.getKey(), (k, v) -> v == null ? entry.getLongValue() : v + entry.getLongValue());
                }
            } else {
                Object2LongOpenCustomHashMap<ItemStack> map = new Object2LongOpenCustomHashMap<>(ItemStackHashStrategy.comparingAllButCount());
                for (var it : machine.getCapabilities().get(IO.IN)) {
                    for (var obj = it.getContent(ItemRecipeCapability.CAP).object2LongEntrySet().fastIterator(); obj.hasNext();) {
                        var entry = obj.next();
                        map.computeLong((ItemStack) entry.getKey(), (k, v) -> v == null ? entry.getLongValue() : v + entry.getLongValue());
                    }
                }
                for (var obj : map.object2LongEntrySet()) {
                    ingredientStacks.computeLong(obj.getKey(), (k, v) -> v == null ? obj.getLongValue() : v + obj.getLongValue());
                }
            }
            long needed;
            long available;
            for (var it = Object2LongMaps.fastIterator(countableMap); it.hasNext(); parallelAmount = Math.min(parallelAmount, available / needed)) {
                var entry = it.next();
                needed = entry.getLongValue();
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

    static long getInputFluidParallel(IRecipeCapabilityHolder holder, GTRecipe recipe, long parallelAmount) {
        if (parallelAmount <= 1) return parallelAmount;
        if (holder instanceof IRecipeCapabilityMachine machine) {
            if (machine.getRecipeHandleParts().isEmpty()) return 0;
            Object2LongOpenHashMap<FluidIngredient> fluidCountMap = new Object2LongOpenHashMap<>();
            for (Content content : recipe.getInputContents(FluidRecipeCapability.CAP)) {
                FluidIngredient fluidInput = FluidRecipeCapability.CAP.of(content.content);
                if (content.chance > 0) {
                    fluidCountMap.addTo(fluidInput, fluidInput.getAmount());
                }
            }
            if (fluidCountMap.isEmpty()) return parallelAmount;
            Object2LongOpenHashMap<FluidStack> ingredientStacks = new Object2LongOpenHashMap<>();
            if (machine.isDistinct() && machine.getRecipeHandleMap().get(recipe) != null) {
                for (var it = machine.getRecipeHandleMap().get(recipe).getContent(FluidRecipeCapability.CAP).object2LongEntrySet().fastIterator(); it.hasNext();) {
                    var entry = it.next();
                    ingredientStacks.computeLong((FluidStack) entry.getKey(), (k, v) -> v == null ? entry.getLongValue() : v + entry.getLongValue());
                }
            } else {
                for (var container : machine.getCapabilitiesFlat(IO.IN, FluidRecipeCapability.CAP)) {
                    if (container instanceof CatalystFluidStackHandler) continue;
                    for (var object : container.getContents()) {
                        if (object instanceof FluidStack fs) {
                            ingredientStacks.computeLong(fs, (k, v) -> v == null ? fs.getAmount() : v + fs.getAmount());
                        }
                    }
                }
            }
            long needed;
            long available;
            for (var it = Object2LongMaps.fastIterator(fluidCountMap); it.hasNext(); parallelAmount = Math.min(parallelAmount, available / needed)) {
                var entry = it.next();
                needed = entry.getLongValue();
                available = 0;
                for (var iter = Object2LongMaps.fastIterator(ingredientStacks); iter.hasNext();) {
                    var inputFluid = iter.next();
                    if (entry.getKey().test(inputFluid.getKey())) {
                        available += inputFluid.getLongValue();
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

    static long getOutputItemParallel(IRecipeCapabilityHolder holder, GTRecipe recipe, List<Content> contents, long multiplier) {
        if (multiplier <= 1L || contents.isEmpty()) return multiplier;
        if (holder instanceof IRecipeCapabilityMachine machine) {
            if (machine.isMEOutPutDual() || machine.isMEOutPutBus()) return multiplier;
            long minMultiplier = 0L;
            long maxMultiplier = multiplier;
            long maxCount = 0L;
            List<Ingredient> ingredients = new ObjectArrayList<>(contents.size());
            for (Content content : contents) {
                Ingredient recipeIngredient = ItemRecipeCapability.CAP.of(content.content);
                long ingredientCount;
                if (recipeIngredient instanceof SizedIngredient sizedIngredient) {
                    ingredientCount = sizedIngredient.getAmount();
                } else if (recipeIngredient instanceof IntProviderIngredient intProviderIngredient) {
                    ingredientCount = intProviderIngredient.getSampledCount(GTValues.RNG);
                } else {
                    ingredientCount = 1;
                }
                maxCount = Math.max(maxCount, ingredientCount);
                ingredients.add(recipeIngredient);
            }
            if (maxCount == 0L) return multiplier;
            var handlers = machine.getCapabilitiesFlat(IO.OUT, ItemRecipeCapability.CAP);
            if (handlers.isEmpty()) return 0L;
            while (minMultiplier != maxMultiplier) {
                List<Ingredient> copied = new ObjectArrayList<>(ingredients.size());
                for (var ing : ingredients) {
                    copied.add(ItemRecipeCapability.CAP.copyWithModifier(ing, ContentModifier.multiplier(multiplier)));
                }
                for (var handler : handlers) {
                    copied = (List<Ingredient>) handler.handleRecipe(IO.OUT, recipe, copied, null, true);
                    if (copied == null || copied.isEmpty()) break;
                    if (multiplier == 1L) return 0L;
                }
                long[] bin = adjustMultiplier(copied == null, minMultiplier, multiplier, maxMultiplier);
                minMultiplier = bin[0];
                multiplier = bin[1];
                maxMultiplier = bin[2];
            }
            return multiplier;
        }
        return 1;
    }

    static long getOutputFluidParallel(IRecipeCapabilityHolder holder, GTRecipe recipe, List<Content> contents, long multiplier) {
        if (multiplier <= 1L || contents.isEmpty()) return multiplier;
        if (holder instanceof IRecipeCapabilityMachine machine) {
            if (machine.isMEOutPutDual() || machine.isMEOutPutHatch()) return multiplier;
            long minMultiplier = 0L;
            long maxMultiplier = multiplier;
            long maxCount = 0L;
            List<FluidIngredient> ingredients = new ObjectArrayList<>(contents.size());
            for (var content : contents) {
                FluidIngredient recipeIngredient = FluidRecipeCapability.CAP.of(content.content);
                maxCount = Math.max(maxCount, recipeIngredient.getAmount());
                ingredients.add(recipeIngredient);
            }
            if (maxCount == 0L) return multiplier;
            List<IRecipeHandler<?>> handlers = machine.getCapabilitiesFlat(IO.OUT, FluidRecipeCapability.CAP);
            if (handlers.isEmpty()) return 0L;
            while (minMultiplier != maxMultiplier) {
                List<FluidIngredient> copied = new ObjectArrayList<>(ingredients.size());
                for (FluidIngredient ing : ingredients) {
                    copied.add(FluidRecipeCapability.CAP.copyWithModifier(ing, ContentModifier.multiplier((double) multiplier)));
                }
                for (IRecipeHandler<?> handler : handlers) {
                    copied = (List<FluidIngredient>) handler.handleRecipe(IO.OUT, recipe, copied, null, true);
                    if (copied == null || copied.isEmpty()) break;
                    if (multiplier == 1L) return 0L;
                }
                long[] bin = adjustMultiplier(copied == null, minMultiplier, multiplier, maxMultiplier);
                minMultiplier = bin[0];
                multiplier = bin[1];
                maxMultiplier = bin[2];
            }
            return multiplier;

        }
        return 1;
    }

    private static long[] adjustMultiplier(boolean mergedAll, long minMultiplier, long multiplier, long maxMultiplier) {
        if (mergedAll) {
            minMultiplier = multiplier;
            long remainder = (maxMultiplier - multiplier) % 2L;
            multiplier = multiplier + remainder + (maxMultiplier - multiplier) / 2L;
        } else {
            maxMultiplier = multiplier;
            multiplier = (multiplier + minMultiplier) / 2L;
        }
        if (maxMultiplier - minMultiplier <= 1L) {
            maxMultiplier = minMultiplier;
            multiplier = minMultiplier;
        }
        return new long[] { minMultiplier, multiplier, maxMultiplier };
    }
}
