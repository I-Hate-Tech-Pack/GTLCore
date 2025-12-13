package org.gtlcore.gtlcore.mixin.gtm.ae.slot;

import org.gtlcore.gtlcore.api.machine.trait.MEStock.IMETransfer;
import org.gtlcore.gtlcore.api.machine.trait.MEStock.IOptimizedMEList;
import org.gtlcore.gtlcore.api.recipe.ingredient.LongIngredient;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import com.gregtechceu.gtceu.integration.ae2.slot.ExportOnlyAEItemList;
import com.gregtechceu.gtceu.integration.ae2.slot.ExportOnlyAEItemSlot;

import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.GenericStack;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import java.util.*;
import java.util.function.Function;

@Implements(@Interface(
                       iface = IOptimizedMEList.class,
                       prefix = "gTLCore$"))
@Mixin(ExportOnlyAEItemList.class)
public abstract class ExportOnlyAEItemListMixin extends NotifiableItemStackHandler {

    @Unique
    protected final Object2LongOpenHashMap<ItemStack> gTLCore$itemMap = new Object2LongOpenHashMap<>();
    @Unique
    protected boolean gTLCore$changed = true;

    @Shadow(remap = false)
    protected ExportOnlyAEItemSlot[] inventory;

    public ExportOnlyAEItemListMixin(MetaMachine machine, int slots, @NotNull IO handlerIO, @NotNull IO capabilityIO, Function<Integer, ItemStackTransfer> transferFactory) {
        super(machine, slots, handlerIO, capabilityIO, transferFactory);
    }

    @Unique
    public Object2LongOpenHashMap<ItemStack> gTLCore$getItemMap() {
        return gTLCore$itemMap;
    }

    @Unique
    public Object2LongMap<ItemStack> gTLCore$getMEItemMap() {
        if (gTLCore$changed) {
            gTLCore$changed = false;
            gTLCore$itemMap.clear();
            for (var slot : inventory) {
                GenericStack stock = slot.getStock();
                if (stock != null && stock.amount() != 0L && stock.what() instanceof AEItemKey itemKey) {
                    var stack = itemKey.toStack();
                    if (!stack.isEmpty()) this.gTLCore$itemMap.addTo(stack, stock.amount());
                }
            }
        }
        return gTLCore$itemMap.isEmpty() ? null : gTLCore$itemMap;
    }

    @Unique
    public boolean gTLCore$getChanged() {
        return gTLCore$changed;
    }

    @Unique
    public void gTLCore$setChanged(boolean gTLCore$changed) {
        this.gTLCore$changed = gTLCore$changed;
    }

    @Override
    public List<Ingredient> handleRecipeInner(IO io, GTRecipe recipe, List<Ingredient> left, @Nullable String slotName, boolean simulate) {
        if (io == IO.IN) {
            boolean changed = false;
            var listIterator = left.listIterator();
            while (listIterator.hasNext()) {
                Ingredient ingredient = listIterator.next();
                if (ingredient.isEmpty()) {
                    listIterator.remove();
                } else {
                    long amount = ingredient instanceof LongIngredient li ? li.getActualAmount() : ingredient instanceof SizedIngredient si ? si.getAmount() : 1;

                    if (amount <= 0) listIterator.remove();
                    else {
                        for (ExportOnlyAEItemSlot i : this.inventory) {
                            GenericStack stored = i.getStock();
                            if (stored != null && stored.amount() != 0 && stored.what() instanceof AEItemKey itemKey) {
                                if (itemKey.matches(ingredient)) {
                                    GenericStack extracted = ((IMETransfer) i).extractGenericStack(amount, simulate, !simulate);
                                    if (extracted != null && extracted.amount() > 0) {
                                        changed = true;
                                        amount -= extracted.amount();
                                        if (amount <= 0L) {
                                            listIterator.remove();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!simulate && changed) {
                this.gTLCore$changed = true;
                this.onContentsChanged();
            }
        }
        return left.isEmpty() ? null : left;
    }

    @Override
    public void onContentsChanged() {
        super.onContentsChanged();
        this.gTLCore$changed = true;
    }

    @Override
    public List<Object> getContents() {
        var itemMap = this.gTLCore$getMEItemMap();
        if (itemMap == null) return Collections.emptyList();
        return Arrays.asList(itemMap.keySet().toArray());
    }
}
