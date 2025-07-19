package org.gtlcore.gtlcore.mixin.gtm.registry;

import org.gtlcore.gtlcore.api.recipe.RecipeResult;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;

import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GTRecipeModifiers.class)
public class GTRecipeModifiersMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public static GTRecipe ebfOverclock(MetaMachine machine, @NotNull GTRecipe recipe, @NotNull OCParams params, @NotNull OCResult result) {
        if (machine instanceof CoilWorkableElectricMultiblockMachine coilMachine) {
            int blastFurnaceTemperature = coilMachine.getCoilType().getCoilTemperature() + 100 * Math.max(0, coilMachine.getTier() - 2);
            if (recipe.data.contains("ebf_temp")) {
                if (recipe.data.getInt("ebf_temp") <= blastFurnaceTemperature) {
                    return RecipeHelper.getRecipeEUtTier(recipe) > coilMachine.getTier() ? null :
                            RecipeHelper.applyOverclock(new OverclockingLogic((p, r, maxVoltage) -> OverclockingLogic.heatingCoilOC(
                                    params, result, maxVoltage, blastFurnaceTemperature, recipe.data.contains("ebf_temp") ? recipe.data.getInt("ebf_temp") : 0)),
                                    recipe, coilMachine.getOverclockVoltage(), params, result);
                } else {
                    RecipeResult.of((IRecipeLogicMachine) machine, RecipeResult.fail(Component.translatable("gtceu.recipe.fail.no.enough.temperature")));
                    return null;
                }
            } else return null;
        } else return null;
    }
}
