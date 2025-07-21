package org.gtlcore.gtlcore.common.machine.trait;

import org.gtlcore.gtlcore.api.machine.multiblock.ParallelMachine;
import org.gtlcore.gtlcore.api.machine.trait.ILockRecipe;
import org.gtlcore.gtlcore.api.recipe.IGTRecipeEUTier;
import org.gtlcore.gtlcore.api.recipe.IParallelLogic;
import org.gtlcore.gtlcore.api.recipe.RecipeResult;

import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import net.minecraft.nbt.CompoundTag;

import com.google.common.primitives.Ints;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import static org.gtlcore.gtlcore.api.recipe.RecipeRunnerHelper.*;

@Getter
public class MultipleRecipesLogic extends RecipeLogic implements ILockRecipe {

    private final ParallelMachine parallel;

    private final BiPredicate<CompoundTag, IRecipeLogicMachine> dataCheck;

    private static final int MAX_THREADS = 64;

    public MultipleRecipesLogic(ParallelMachine machine) {
        this(machine, null);
    }

    public MultipleRecipesLogic(ParallelMachine machine, BiPredicate<CompoundTag, IRecipeLogicMachine> dataCheck) {
        super((IRecipeLogicMachine) machine);
        this.parallel = machine;
        this.dataCheck = dataCheck;
    }

    @Override
    public WorkableElectricMultiblockMachine getMachine() {
        return (WorkableElectricMultiblockMachine) super.getMachine();
    }

    @Override
    public void findAndHandleRecipe() {
        lastRecipe = null;
        var match = getRecipe();
        if (match != null) {
            if (matchRecipeOutput(machine, match)) {
                setupRecipe(match);
            }
        }
    }

    @Nullable
    private GTRecipe getRecipe() {
        if (!machine.hasProxies()) return null;
        GTRecipe output = GTRecipeBuilder.ofRaw().buildRawRecipe();
        output.outputs.put(ItemRecipeCapability.CAP, new ArrayList<>());
        output.outputs.put(FluidRecipeCapability.CAP, new ArrayList<>());
        long maxEUt = getMachine().getOverclockVoltage();
        long totalEu = 0;
        long remain = (long) this.parallel.getMaxParallel() * MAX_THREADS;
        while (remain > 0) {
            GTRecipe match = lookupRecipe();
            if (match == null) break;
            long p = IParallelLogic.getMaxParallel(machine, match, remain);
            if (p > 1) match = match.copy(ContentModifier.multiplier(p), false);
            totalEu += RecipeHelper.getInputEUt(match) * match.duration;
            if (totalEu / maxEUt > 1200) break;
            match.parallels = Ints.saturatedCast(p);
            IParallelLogic.getRecipeOutputChance(machine, match);
            remain -= p;
            if (handleRecipeInput(machine, match)) {
                var item = match.outputs.get(ItemRecipeCapability.CAP);
                if (item != null) output.outputs.get(ItemRecipeCapability.CAP).addAll(item);
                var fluid = match.outputs.get(FluidRecipeCapability.CAP);
                if (fluid != null) output.outputs.get(FluidRecipeCapability.CAP).addAll(fluid);
            }
        }
        if (output.outputs.get(ItemRecipeCapability.CAP).isEmpty() && output.outputs.get(FluidRecipeCapability.CAP).isEmpty()) {
            if (totalEu / maxEUt > 1200) RecipeResult.of(machine, RecipeResult.FAIL_NO_ENOUGH_EU);
            return null;
        }
        double d = (double) totalEu / maxEUt;
        long eut = d > 20 ? maxEUt : (long) (maxEUt * d / 20);
        output.tickInputs.put(EURecipeCapability.CAP,
                List.of(new Content(eut, 10000, 10000, 0, null, null)));
        output.duration = (int) Math.max(d, 20);
        return output;
    }

    private GTRecipe lookupRecipe() {
        if (this.isLock()) {
            if (this.getLockRecipe() == null) this.setLockRecipe(machine.getRecipeType().getLookup()
                    .find(machine, this::checkRecipe));
            else if (!checkRecipe(this.getLockRecipe())) return null;
            return this.getLockRecipe();
        } else return machine.getRecipeType().getLookup().find(machine, this::checkRecipe);
    }

    private boolean checkRecipe(GTRecipe recipe) {
        int tier = -1;
        if (recipe.recipeType instanceof IGTRecipeEUTier euTier)
            tier = euTier.getRecipeTierMap().getOrDefault(recipe.id, -1);
        if (tier == -1) tier = RecipeHelper.getRecipeEUtTier(recipe);
        return matchRecipe(machine, recipe) && tier <= getMachine().getTier() &&
                (dataCheck == null || dataCheck.test(recipe.data, machine));
    }

    @Override
    public void onRecipeFinish() {
        machine.afterWorking();
        if (lastRecipe != null) {
            lastRecipe.postWorking(this.machine);
            handleRecipeOutput(this.machine, lastRecipe);
        }
        var match = getRecipe();
        if (match != null) {
            if (matchRecipeOutput(machine, match)) {
                setupRecipe(match);
                return;
            }
        }
        setStatus(Status.IDLE);
        progress = 0;
        duration = 0;
    }
}
