package org.gtlcore.gtlcore.mixin.gtm.api.recipe;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.lookup.AbstractMapIngredient;
import com.gregtechceu.gtceu.api.recipe.lookup.RecipeIterator;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.function.Predicate;

@Mixin(RecipeIterator.class)
public interface RecipeIteratorAccessor {

    @Accessor(value = "ingredients", remap = false)
    List<List<AbstractMapIngredient>> getIngredients();

    @Accessor(value = "recipeMap", remap = false)
    @NotNull
    GTRecipeType getRecipeMap();

    @Accessor(value = "canHandle", remap = false)
    @NotNull
    Predicate<GTRecipe> getCanHandle();
}
