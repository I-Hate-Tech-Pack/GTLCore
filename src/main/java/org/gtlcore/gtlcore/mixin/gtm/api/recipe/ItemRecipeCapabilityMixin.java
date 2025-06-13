package org.gtlcore.gtlcore.mixin.gtm.api.recipe;

import org.gtlcore.gtlcore.api.machine.trait.IDistinctMachine;

import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.utils.GTHashMaps;
import com.gregtechceu.gtceu.utils.ItemStackHashStrategy;

import net.minecraft.world.item.ItemStack;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(ItemRecipeCapability.class)
public class ItemRecipeCapabilityMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    private Object2IntMap<ItemStack> getIngredientStacks(IRecipeCapabilityHolder holder) {
        Object2IntMap<ItemStack> map = new Object2IntOpenCustomHashMap<>(ItemStackHashStrategy.comparingAllButCount());
        Object2IntMap<ItemStack> result = new Object2IntOpenHashMap<>();
        if (holder instanceof IDistinctMachine iDistinctMachine && iDistinctMachine.isDistinct() && iDistinctMachine.getDistinctHatch() != null) {
            List<IRecipeHandler<?>> distinctIMultiPart = iDistinctMachine.getDistinctHatch().allHandles().get(ItemRecipeCapability.CAP);
            for (IRecipeHandler<?> handler : distinctIMultiPart) {
                for (Object o : handler.getContents()) {
                    if (o instanceof ItemStack itemStack) {
                        result.computeInt(itemStack, (k, v) -> v == null ? itemStack.getCount() : v + itemStack.getCount());
                    }
                }
            }
            return result;
        }
        List<IRecipeHandler<?>> recipeHandlerList = Objects.requireNonNullElseGet(holder.getCapabilitiesProxy().get(IO.IN, ItemRecipeCapability.CAP),
                Collections::<IRecipeHandler<?>>emptyList).stream().filter(handler -> !handler.isProxy()).toList();
        for (IRecipeHandler<?> container : recipeHandlerList) {
            Object2IntOpenCustomHashMap<ItemStack> itemMap = container.getContents().stream().filter(ItemStack.class::isInstance)
                    .map(ItemStack.class::cast).flatMap(con -> GTHashMaps.fromItemStackCollection(Collections.singleton(con)).object2IntEntrySet()
                            .stream())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum,
                            () -> new Object2IntOpenCustomHashMap<>(ItemStackHashStrategy.comparingAllButCount())));
            if (container.isDistinct()) {
                result.putAll(itemMap);
            } else {
                for (Object2IntMap.Entry<ItemStack> obj : itemMap.object2IntEntrySet()) {
                    map.computeInt(obj.getKey(), (k, v) -> v == null ? obj.getIntValue() : v + obj.getIntValue());
                }
            }
        }
        result.putAll(map);
        return result;
    }
}
