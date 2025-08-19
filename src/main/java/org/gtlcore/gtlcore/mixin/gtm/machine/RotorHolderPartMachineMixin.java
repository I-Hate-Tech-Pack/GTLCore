package org.gtlcore.gtlcore.mixin.gtm.machine;

import org.gtlcore.gtlcore.api.recipe.RecipeResult;

import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IRotorHolderMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.machine.multiblock.part.RotorHolderPartMachine;

import net.minecraft.network.chat.Component;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(RotorHolderPartMachine.class)
public abstract class RotorHolderPartMachineMixin implements IRotorHolderMachine {

    @Override
    public GTRecipe modifyRecipe(GTRecipe recipe) {
        GTRecipe modifiedRecipe = this.isFrontFaceFree() && this.hasRotor() ? recipe : null;
        if (modifiedRecipe != null) return modifiedRecipe;
        RecipeResult.of((IRecipeLogicMachine) this.getControllers().get(0),
                RecipeResult.fail(Component.translatable("gtceu.multiblock.universal.rotor_obstructed")));
        return null;
    }
}
