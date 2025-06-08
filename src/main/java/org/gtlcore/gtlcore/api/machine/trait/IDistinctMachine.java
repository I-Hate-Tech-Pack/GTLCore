package org.gtlcore.gtlcore.api.machine.trait;

import org.gtlcore.gtlcore.api.recipe.RecipeRunner;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * 部分代码参考自gto
 * &#064;line <a href="https://github.com/GregTech-Odyssey/GTOCore">...</a>
 */

public interface IDistinctMachine {

    default List<RecipeRunner.RecipeHandlePart> getRecipeHandleParts() {
        return List.of();
    }

    default void setRecipeHandleParts(List<RecipeRunner.RecipeHandlePart> recipeHandleParts) {}

    default RecipeRunner.RecipeHandlePart getDistinctHatch() {
        return null;
    }

    default void setDistinctHatch(RecipeRunner.RecipeHandlePart hatch) {}

    default ResourceLocation getRecipeId() {
        return null;
    }

    default void setRecipeId(ResourceLocation recipeId) {}

    default void upDate() {}
}
