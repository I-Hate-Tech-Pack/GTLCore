package org.gtlcore.gtlcore.mixin.gtm.api.recipe;

import org.gtlcore.gtlcore.api.machine.trait.IDistinctMachine;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.IntProviderIngredient;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import com.gregtechceu.gtceu.utils.ItemStackHashStrategy;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import com.google.common.primitives.Ints;
import it.unimi.dsi.fastutil.objects.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.*;

import static com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability.CAP;

@Mixin(ItemRecipeCapability.class)
public class ItemRecipeCapabilityMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public int getMaxParallelRatio(IRecipeCapabilityHolder holder, GTRecipe recipe, int parallelAmount) {
        if (holder instanceof IDistinctMachine iDistinctMachine) {
            if (iDistinctMachine.getRecipeHandleParts().isEmpty()) return 0;
            Object2LongOpenCustomHashMap<ItemStack> ingredientStacks = new Object2LongOpenCustomHashMap<>(ItemStackHashStrategy.comparingAllButCount());
            if (iDistinctMachine.isDistinct() && iDistinctMachine.getDistinctHatch() != null) {
                List<IRecipeHandler<?>> distinctIMultiPart = iDistinctMachine.getDistinctHatch().getHandlerMap().getOrDefault(CAP, Collections.emptyList());
                for (IRecipeHandler<?> handler : distinctIMultiPart) {
                    for (Object o : handler.getContents()) {
                        if (o instanceof ItemStack itemStack) {
                            ingredientStacks.computeLong(itemStack, (k, v) -> v == null ? itemStack.getCount() : v + itemStack.getCount());
                        }
                    }
                }
            } else {
                Object2LongOpenCustomHashMap<ItemStack> map = new Object2LongOpenCustomHashMap<>(ItemStackHashStrategy.comparingAllButCount());
                List<IRecipeHandler<?>> recipeHandlerList = iDistinctMachine.getCapabilitiesFlat(IO.IN, CAP);
                for (IRecipeHandler<?> container : recipeHandlerList) {
                    Object2LongOpenCustomHashMap<ItemStack> itemMap = new Object2LongOpenCustomHashMap<>(ItemStackHashStrategy.comparingAllButCount());
                    for (Object o : container.getContents()) {
                        if (o instanceof ItemStack itemStack) {
                            itemMap.computeLong(itemStack, (k, v) -> v == null ? itemStack.getCount() : v + itemStack.getCount());
                        }
                    }
                    if (container.isDistinct()) {
                        for (Object2LongOpenCustomHashMap.Entry<ItemStack> obj : itemMap.object2LongEntrySet()) {
                            ingredientStacks.computeLong(obj.getKey(), (k, v) -> v == null ? obj.getLongValue() : v + obj.getLongValue());
                        }
                    } else {
                        for (Object2LongOpenCustomHashMap.Entry<ItemStack> obj : itemMap.object2LongEntrySet()) {
                            map.computeLong(obj.getKey(), (k, v) -> v == null ? obj.getLongValue() : v + obj.getLongValue());
                        }
                    }
                }
                for (Object2LongOpenCustomHashMap.Entry<ItemStack> obj : map.object2LongEntrySet()) {
                    ingredientStacks.computeLong(obj.getKey(), (k, v) -> v == null ? obj.getLongValue() : v + obj.getLongValue());
                }
            }
            long minMultiplier = Integer.MAX_VALUE;
            Object2IntOpenHashMap<Ingredient> notConsumableMap = new Object2IntOpenHashMap<>();
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
                if (content.chance == 0) {
                    notConsumableMap.computeIfPresent(recipeIngredient, (k, v) -> v + ingredientCount);
                    notConsumableMap.putIfAbsent(recipeIngredient, ingredientCount);
                } else {
                    countableMap.computeIfPresent(recipeIngredient, (k, v) -> v + ingredientCount);
                    countableMap.putIfAbsent(recipeIngredient, ingredientCount);
                }
            }
            for (var recipeInputEntry : notConsumableMap.object2IntEntrySet()) {
                long needed = recipeInputEntry.getIntValue();
                long available = 0;
                for (var inventoryEntry : ingredientStacks.object2LongEntrySet()) {
                    if (recipeInputEntry.getKey().test(inventoryEntry.getKey())) {
                        available = inventoryEntry.getLongValue();
                        if (available > needed) {
                            inventoryEntry.setValue(available - needed);
                            needed -= available;
                            break;
                        } else {
                            inventoryEntry.setValue(0);
                            recipeInputEntry.setValue((int) (needed - available));
                            needed -= available;
                        }
                    }
                }
                if (needed >= available) {
                    return 0;
                }
            }
            if (countableMap.isEmpty() && !notConsumableMap.isEmpty()) {
                return parallelAmount;
            }
            for (var recipeInputEntry : countableMap.object2IntEntrySet()) {
                long needed = recipeInputEntry.getIntValue();
                long available = 0;
                for (var inventoryEntry : ingredientStacks.object2LongEntrySet()) {
                    if (recipeInputEntry.getKey().test(inventoryEntry.getKey())) {
                        available += inventoryEntry.getLongValue();
                        break;
                    }
                }
                if (available >= needed) {
                    long ratio = Math.min(parallelAmount, available / needed);
                    if (ratio < minMultiplier) {
                        minMultiplier = ratio;
                    }
                } else {
                    return 0;
                }
            }
            return Ints.saturatedCast(minMultiplier);
        }
        return 1;
    }
}
