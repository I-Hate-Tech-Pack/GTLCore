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

import net.minecraft.resources.ResourceLocation;

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

    @Setter
    @Getter
    private List<RecipeRunner.RecipeHandlePart> recipeHandleParts = new ObjectArrayList<>();
    @Getter
    @Setter
    private RecipeRunner.RecipeHandlePart distinctHatch;
    @Getter
    @Setter
    private ResourceLocation recipeId;

    public WorkableMultiblockMachineMixin(IMachineBlockEntity holder) {
        super(holder);
    }

    @Inject(method = "onStructureFormed", at = @At("TAIL"), remap = false)
    public void onStructureFormed(CallbackInfo ci) {
        this.upDate();
    }

    @Inject(method = "onStructureInvalid", at = @At("TAIL"), remap = false)
    public void onStructureInvalid(CallbackInfo ci) {
        this.recipeHandleParts.clear();
    }

    @Inject(method = "onPartUnload", at = @At("TAIL"), remap = false)
    public void onPartUnload(CallbackInfo ci) {
        this.recipeHandleParts.clear();
    }

    public void upDate() {
        recipeHandleParts.clear();
        distinctHatch = null;
        recipeId = null;
        Iterator<IMultiPart> parts = this.getParts().iterator();
        Object2ObjectOpenHashMap<RecipeCapability<?>, List<IRecipeHandler<?>>> NodistinctParts = new Object2ObjectOpenHashMap<>();
        Object2ObjectOpenHashMap<RecipeCapability<?>, List<IRecipeHandler<?>>> outputParts = new Object2ObjectOpenHashMap<>();
        while (parts.hasNext()) {
            IMultiPart part = parts.next();
            if (part instanceof FluidHatchPartMachine || part instanceof IDistinctPart) {
                List<IRecipeHandler<?>> itemPart = new ObjectArrayList<>();
                List<IRecipeHandler<?>> fluidPart = new ObjectArrayList<>();
                boolean isOutput = false;
                for (var v : part.getRecipeHandlers()) {
                    if (!v.isProxy()) {
                        if (v.getHandlerIO() == IO.IN) {
                            if (v.getCapability() == ItemRecipeCapability.CAP) itemPart.add(v);
                            else if (v.getCapability() == FluidRecipeCapability.CAP) fluidPart.add(v);
                        } else if (v.getHandlerIO() == IO.OUT) {
                            isOutput = true;
                            if (v.getCapability() == ItemRecipeCapability.CAP) itemPart.add(v);
                            else if (v.getCapability() == FluidRecipeCapability.CAP) fluidPart.add(v);
                        }
                    }
                }
                if (isOutput) {
                    outputParts.computeIfAbsent(ItemRecipeCapability.CAP, k -> new ArrayList<>()).addAll(itemPart);
                    outputParts.computeIfAbsent(FluidRecipeCapability.CAP, k -> new ArrayList<>()).addAll(fluidPart);
                } else {
                    if (part instanceof IDistinctPart iDistinctPart && iDistinctPart.isDistinct()) {
                        Object2ObjectOpenHashMap<RecipeCapability<?>, List<IRecipeHandler<?>>> distinctParts = new Object2ObjectOpenHashMap<>();
                        distinctParts.put(ItemRecipeCapability.CAP, itemPart);
                        distinctParts.put(FluidRecipeCapability.CAP, fluidPart);
                        recipeHandleParts.add(new RecipeRunner.RecipeHandlePart(IO.IN, distinctParts));
                    } else {
                        NodistinctParts.computeIfAbsent(ItemRecipeCapability.CAP, k -> new ArrayList<>()).addAll(itemPart);
                        NodistinctParts.computeIfAbsent(FluidRecipeCapability.CAP, k -> new ArrayList<>()).addAll(fluidPart);
                    }
                }
            }
        }
        if (!NodistinctParts.isEmpty()) recipeHandleParts.add(new RecipeRunner.RecipeHandlePart(IO.IN, NodistinctParts));
        if (!outputParts.isEmpty()) recipeHandleParts.add(new RecipeRunner.RecipeHandlePart(IO.OUT, outputParts));
    }
}
