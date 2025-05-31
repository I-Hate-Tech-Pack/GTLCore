package org.gtlcore.gtlcore.mixin.gtm.api.recipe;

import org.gtlcore.gtlcore.api.machine.trait.IDistinctMachine;

import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;

import com.lowdragmc.lowdraglib.side.fluid.FluidStack;

import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import org.spongepowered.asm.mixin.*;

import java.util.*;

@Mixin(FluidRecipeCapability.class)
public class FluidRecipeCapabilityMixin {

    /**
     * @author Adonis
     * @reason 流体输入输出上限改为long
     */
    @Overwrite(remap = false)
    public FluidIngredient copyWithModifier(FluidIngredient content, ContentModifier modifier) {
        if (content.isEmpty()) {
            return content.copy();
        } else {
            FluidIngredient copy = content.copy();
            copy.setAmount(modifier.apply(copy.getAmount()).longValue());
            return copy;
        }
    }

    /**
     * @author Adonis
     * @reason 支持流体隔离
     */
    @Overwrite(remap = false)
    public int getMaxParallelRatio(IRecipeCapabilityHolder holder, GTRecipe recipe, int parallelAmount) {
        if (holder instanceof IDistinctMachine iDistinctMachine) {
            Object2LongOpenHashMap<FluidStack> ingredientStacks = new Object2LongOpenHashMap<>();
            if (iDistinctMachine.getRecipeHandleParts().isEmpty()) return 0;
            if (iDistinctMachine.getDistinctHatch() != null) {
                List<IRecipeHandler<?>> distinctIMultiPart = iDistinctMachine.getDistinctHatch().allHandles().get(FluidRecipeCapability.CAP);
                for (IRecipeHandler<?> handler : distinctIMultiPart) {
                    for (Object o : handler.getContents()) {
                        if (o instanceof FluidStack fluidStack) {
                            ingredientStacks.computeLong(fluidStack, (k, v) -> v == null ? fluidStack.getAmount() : v + fluidStack.getAmount());
                        }
                    }
                }
            } else {
                Object2LongOpenHashMap<FluidStack> map = new Object2LongOpenHashMap<>();
                for (var container : Objects.requireNonNullElseGet(holder.getCapabilitiesProxy().get(IO.IN, FluidRecipeCapability.CAP),
                        Collections::<IRecipeHandler<?>>emptyList).stream().toList()) {
                    Object2LongOpenHashMap<FluidStack> fluidMap = new Object2LongOpenHashMap<>();
                    for (Object object : container.getContents()) {
                        if (object instanceof FluidStack fluidStack) {
                            fluidMap.computeLong(fluidStack, (k, v) -> v == null ? fluidStack.getAmount() : v + fluidStack.getAmount());
                        }
                    }
                    if (container.isDistinct()) {
                        for (var entry : fluidMap.object2LongEntrySet()) {
                            ingredientStacks.computeLong(entry.getKey(), (k, v) -> v == null ? entry.getLongValue() : Math.max(v, entry.getLongValue()));
                        }
                    } else {
                        for (Object2LongMap.Entry<FluidStack> obj : fluidMap.object2LongEntrySet()) {
                            map.computeLong(obj.getKey(), (k, v) -> v == null ? obj.getLongValue() : v + obj.getLongValue());
                        }
                    }
                }
                for (var entry : map.object2LongEntrySet()) {
                    ingredientStacks.computeLong(entry.getKey(), (k, v) -> v == null ? entry.getLongValue() : Math.max(v, entry.getLongValue()));
                }
            }

            int minMultiplier = Integer.MAX_VALUE;
            Object2LongOpenHashMap<FluidIngredient> fluidCountMap = new Object2LongOpenHashMap<>();
            Object2LongOpenHashMap<FluidIngredient> notConsumableMap = new Object2LongOpenHashMap<>();
            for (Content content : recipe.getInputContents(FluidRecipeCapability.CAP)) {
                FluidIngredient fluidInput = FluidRecipeCapability.CAP.of(content.content);
                long fluidAmount = fluidInput.getAmount();
                if (content.chance == 0) {
                    notConsumableMap.computeIfPresent(fluidInput,
                            (k, v) -> v + fluidAmount);
                    notConsumableMap.putIfAbsent(fluidInput, fluidAmount);
                } else {
                    fluidCountMap.computeIfPresent(fluidInput,
                            (k, v) -> v + fluidAmount);
                    fluidCountMap.putIfAbsent(fluidInput, fluidAmount);
                }
            }
            for (Map.Entry<FluidIngredient, Long> notConsumableFluid : notConsumableMap.entrySet()) {
                long needed = notConsumableFluid.getValue();
                long available = 0;
                for (Map.Entry<FluidStack, Long> inputFluid : ingredientStacks.entrySet()) {
                    if (notConsumableFluid.getKey().test(
                            FluidStack.create(inputFluid.getKey().getFluid(), inputFluid.getValue(), inputFluid.getKey().getTag()))) {
                        available = inputFluid.getValue();
                        if (available > needed) {
                            inputFluid.setValue(available - needed);
                            needed -= available;
                            break;
                        } else {
                            inputFluid.setValue(0L);
                            notConsumableFluid.setValue(needed - available);
                            needed -= available;
                        }
                    }
                }
                if (needed >= available) {
                    return 0;
                }
            }
            if (fluidCountMap.isEmpty() && !notConsumableMap.isEmpty()) {
                return parallelAmount;
            }
            for (Map.Entry<FluidIngredient, Long> fs : fluidCountMap.entrySet()) {
                long needed = fs.getValue();
                long available = 0;
                for (Map.Entry<FluidStack, Long> inputFluid : ingredientStacks.entrySet()) {
                    if (fs.getKey().test(
                            FluidStack.create(inputFluid.getKey().getFluid(), inputFluid.getValue(), inputFluid.getKey().getTag()))) {
                        available += inputFluid.getValue();
                    }
                }
                if (available >= needed) {
                    int ratio = (int) Math.min(parallelAmount, available / needed);
                    if (ratio < minMultiplier) {
                        minMultiplier = ratio;
                    }
                } else {
                    return 0;
                }
            }
            return minMultiplier;
        }
        return 0;
    }
}
