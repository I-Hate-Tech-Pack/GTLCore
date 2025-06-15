package org.gtlcore.gtlcore.mixin.gtm.api.recipe;

import org.gtlcore.gtlcore.api.machine.trait.IDistinctMachine;

import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;

import com.lowdragmc.lowdraglib.side.fluid.FluidStack;

import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import org.spongepowered.asm.mixin.*;

import java.util.*;

import static com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability.CAP;

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
            if (iDistinctMachine.getRecipeHandleParts().isEmpty()) return 0;
            Object2LongOpenHashMap<FluidStack> ingredientStacks = new Object2LongOpenHashMap<>();
            if (iDistinctMachine.isDistinct() && iDistinctMachine.getDistinctHatch() != null) {
                List<IRecipeHandler<?>> distinctIMultiPart = iDistinctMachine.getDistinctHatch().getHandlerMap().getOrDefault(CAP, Collections.emptyList());
                for (IRecipeHandler<?> handler : distinctIMultiPart) {
                    for (Object o : handler.getContents()) {
                        if (o instanceof FluidStack fluidStack) {
                            ingredientStacks.computeLong(fluidStack, (k, v) -> v == null ? fluidStack.getAmount() : v + fluidStack.getAmount());
                        }
                    }
                }
            } else {
                Object2LongOpenHashMap<FluidStack> map = new Object2LongOpenHashMap<>();
                for (var container : iDistinctMachine.getCapabilitiesFlat(IO.IN, CAP)) {
                    for (Object object : container.getContents()) {
                        if (object instanceof FluidStack fluidStack) {
                            map.computeLong(fluidStack, (k, v) -> v == null ? fluidStack.getAmount() : v + fluidStack.getAmount());
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
            for (Content content : recipe.getInputContents(CAP)) {
                FluidIngredient fluidInput = CAP.of(content.content);
                long fluidAmount = fluidInput.getAmount();
                if (content.chance == 0) {
                    notConsumableMap.computeIfPresent(fluidInput, (k, v) -> v + fluidAmount);
                    notConsumableMap.putIfAbsent(fluidInput, fluidAmount);
                } else {
                    fluidCountMap.computeIfPresent(fluidInput, (k, v) -> v + fluidAmount);
                    fluidCountMap.putIfAbsent(fluidInput, fluidAmount);
                }
            }
            for (var notConsumableFluid : notConsumableMap.object2LongEntrySet()) {
                long needed = notConsumableFluid.getLongValue();
                long available = 0;
                for (var inputFluid : ingredientStacks.object2LongEntrySet()) {
                    if (notConsumableFluid.getKey().test(
                            FluidStack.create(inputFluid.getKey().getFluid(), inputFluid.getLongValue(), inputFluid.getKey().getTag()))) {
                        available = inputFluid.getLongValue();
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
            for (var fs : fluidCountMap.object2LongEntrySet()) {
                long needed = fs.getLongValue();
                long available = 0;
                for (var inputFluid : ingredientStacks.object2LongEntrySet()) {
                    if (fs.getKey().test(
                            FluidStack.create(inputFluid.getKey().getFluid(), inputFluid.getLongValue(), inputFluid.getKey().getTag()))) {
                        available += inputFluid.getLongValue();
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
        return 1;
    }
}
