package org.gtlcore.gtlcore.mixin.gtm.api.machine;

import org.gtlcore.gtlcore.api.machine.trait.IDistinctMachine;
import org.gtlcore.gtlcore.api.recipe.RecipeRunner;

import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDistinctPart;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.FluidHatchPartMachine;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import lombok.Setter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mixin(WorkableMultiblockMachine.class)
public abstract class WorkableMultiblockMachineMixin extends MultiblockControllerMachine implements IDistinctMachine {

    @Getter
    private List<RecipeRunner.RecipeHandlePart> recipeHandleParts = new ObjectArrayList<>();
    @Getter
    @Setter
    private RecipeRunner.RecipeHandlePart distinctHatch;

    public WorkableMultiblockMachineMixin(IMachineBlockEntity holder) {
        super(holder);
    }

    @Inject(method = "onStructureFormed", at = @At(value = "INVOKE", target = "Lcom/gregtechceu/gtceu/api/machine/multiblock/MultiblockControllerMachine;onStructureFormed()V", shift = At.Shift.AFTER), remap = false)
    public void onStructureFormed(CallbackInfo ci) {
        this.recipeHandleParts.clear();

        Iterator<IMultiPart> parts = this.getParts().iterator();
        Object2ObjectOpenHashMap<RecipeCapability<?>, List<IRecipeHandler<?>>> noDistinctParts = new Object2ObjectOpenHashMap<>();

        label1:
        while (parts.hasNext()) {
            IMultiPart part = parts.next();
            if (part instanceof IDistinctPart iDistinctPart) {
                Object2ObjectOpenHashMap<RecipeCapability<?>, List<IRecipeHandler<?>>> distinctParts = new Object2ObjectOpenHashMap<>();
                List<IRecipeHandler<?>> itemPart = new ObjectArrayList<>();
                List<IRecipeHandler<?>> fluidPart = new ObjectArrayList<>();
                for (var v : iDistinctPart.getRecipeHandlers()) {
                    if (v.getHandlerIO() != IO.IN) continue label1;
                    else if (v.getCapability() instanceof ItemRecipeCapability) itemPart.add(v);
                    else if (v.getCapability() instanceof FluidRecipeCapability) fluidPart.add(v);
                }
                distinctParts.put(ItemRecipeCapability.CAP, itemPart);
                distinctParts.put(FluidRecipeCapability.CAP, fluidPart);
                if (iDistinctPart.isDistinct()) {
                    recipeHandleParts.add(new RecipeRunner.RecipeHandlePart(distinctParts));
                } else {
                    noDistinctParts.putAll(distinctParts);
                }
            } else if (part instanceof FluidHatchPartMachine fluid) {
                List<IRecipeHandler<?>> fluidHandlers = new ArrayList<>();
                // only fluid
                for (var handler : fluid.getRecipeHandlers()) {
                    if (handler.getHandlerIO() != IO.IN) continue label1;
                    if (handler.getCapability() instanceof FluidRecipeCapability) {
                        fluidHandlers.add(handler);
                    }
                }

                if (!fluidHandlers.isEmpty()) {
                    noDistinctParts.put(FluidRecipeCapability.CAP, fluidHandlers);
                }
            }
        }
        if (!noDistinctParts.isEmpty()) recipeHandleParts.add(new RecipeRunner.RecipeHandlePart(noDistinctParts));
    }

    @Inject(method = "onPartUnload", at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V", shift = At.Shift.AFTER), remap = false)
    public void onPartUnload(CallbackInfo ci) {
        this.recipeHandleParts.clear();
    }

    @Inject(method = "onStructureInvalid", at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V"), remap = false)
    public void onStructureInvalid(CallbackInfo ci) {
        this.recipeHandleParts.clear();
    }
}
