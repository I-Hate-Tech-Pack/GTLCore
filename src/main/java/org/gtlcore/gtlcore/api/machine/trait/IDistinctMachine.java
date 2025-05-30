package org.gtlcore.gtlcore.api.machine.trait;

import org.gtlcore.gtlcore.api.recipe.RecipeRunner;

import java.util.List;

public interface IDistinctMachine {

    default List<RecipeRunner.RecipeHandlePart> getRecipeHandleParts() {
        return List.of();
    }

    RecipeRunner.RecipeHandlePart getDistinctHatch();

    void setDistinctHatch(RecipeRunner.RecipeHandlePart hatch);
}
