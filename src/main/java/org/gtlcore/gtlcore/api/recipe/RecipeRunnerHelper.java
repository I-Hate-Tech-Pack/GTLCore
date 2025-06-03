package org.gtlcore.gtlcore.api.recipe;

import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.machine.WorkableTieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.steam.SteamWorkableMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;

import it.unimi.dsi.fastutil.objects.Object2IntMap;

import java.util.*;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RecipeRunnerHelper {

    public static boolean matchRecipe(IRecipeCapabilityHolder holder, GTRecipe recipe) {
        return matchRecipeInput(holder, recipe) && matchRecipeOutput(holder, recipe);
    }

    public static boolean matchRecipeInput(IRecipeCapabilityHolder holder, GTRecipe recipe) {
        if (recipe.inputs.isEmpty()) return true;
        return handleRecipe(IO.IN, holder, recipe.inputs, Collections.emptyMap(), false, recipe, true).isSuccess();
    }

    public static boolean matchRecipeOutput(IRecipeCapabilityHolder holder, GTRecipe recipe) {
        if (recipe.outputs.isEmpty()) return true;
        return handleRecipe(IO.OUT, holder, recipe.outputs, Collections.emptyMap(), false, recipe, true).isSuccess();
    }

    public static boolean handleRecipeIO(IRecipeLogicMachine holder, GTRecipe recipe) {
        return handleRecipeInput(holder, recipe) && handleRecipeOutput(holder, recipe);
    }

    public static boolean handleRecipeInput(IRecipeLogicMachine holder, GTRecipe recipe) {
        return handleRecipe(IO.IN, holder, recipe.inputs, holder.getRecipeLogic().getChanceCaches(), true, recipe, false).isSuccess();
    }

    public static boolean handleRecipeOutput(IRecipeLogicMachine holder, GTRecipe recipe) {
        return handleRecipe(IO.OUT, holder, recipe.outputs, holder.getRecipeLogic().getChanceCaches(), true, recipe, false).isSuccess();
    }

    public static GTRecipe.ActionResult handleRecipe(IO io, IRecipeCapabilityHolder holder, Map<RecipeCapability<?>, List<Content>> contents,
                                                     Map<RecipeCapability<?>, Object2IntMap<?>> chanceCaches, boolean isTick, GTRecipe recipe, boolean isSimulate) {
        if (holder instanceof WorkableTieredMachine || holder instanceof SteamWorkableMachine) {
            if (isSimulate) return recipe.matchRecipe(holder);
            else return recipe.handleRecipe(io, holder, isTick, contents, chanceCaches) ? GTRecipe.ActionResult.SUCCESS : GTRecipe.ActionResult.fail(null);
        }
        RecipeRunner runner = new RecipeRunner(recipe, io, isTick, holder, chanceCaches, isSimulate);
        if (isSimulate && io == IO.IN) return runner.simulatedHandle() ? GTRecipe.ActionResult.SUCCESS : GTRecipe.ActionResult.fail(null);
        if (runner.handle(contents).isSuccess()) {
            return GTRecipe.ActionResult.SUCCESS;
        }
        return GTRecipe.ActionResult.fail(null);
    }
}
