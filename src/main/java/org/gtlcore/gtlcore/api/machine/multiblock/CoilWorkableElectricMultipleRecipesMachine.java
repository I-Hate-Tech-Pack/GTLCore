package org.gtlcore.gtlcore.api.machine.multiblock;

import org.gtlcore.gtlcore.common.machine.trait.MultipleRecipesLogic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;

import org.jetbrains.annotations.NotNull;

import static org.gtlcore.gtlcore.common.data.GTLRecipeModifiers.getHatchParallel;

/**
 * @author EasterFG on 2024/12/5
 */
public class CoilWorkableElectricMultipleRecipesMachine extends CoilWorkableElectricMultiblockMachine implements ParallelMachine {

    public CoilWorkableElectricMultipleRecipesMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    protected @NotNull RecipeLogic createRecipeLogic(Object @NotNull... args) {
        return new MultipleRecipesLogic(this);
    }

    @NotNull
    @Override
    public MultipleRecipesLogic getRecipeLogic() {
        return (MultipleRecipesLogic) super.getRecipeLogic();
    }

    public int getMaxParallel() {
        return getHatchParallel(this);
    }
}
