package org.gtlcore.gtlcore.mixin.gtm.api.recipe;

import org.gtlcore.gtlcore.api.machine.trait.IDistinctMachine;
import org.gtlcore.gtlcore.api.recipe.RecipeRunner;
import org.gtlcore.gtlcore.api.recipe.RecipeRunnerHelper;

import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.machine.WorkableTieredMachine;
import com.gregtechceu.gtceu.api.machine.steam.SteamWorkableMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.lookup.*;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.research.ResearchStationMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.primitive.PrimitiveWorkableMachine;

import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import java.util.*;
import java.util.function.Predicate;

@Mixin(GTRecipeLookup.class)
public abstract class GTRecipeLookupMixin implements IDistinctMachine {

    @Unique
    private IRecipeCapabilityHolder gtlcore$machine;

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public @Nullable GTRecipe findRecipe(IRecipeCapabilityHolder holder) {
        return this.find(holder, (recipe) -> RecipeRunnerHelper.matchRecipe(holder, recipe));
    }

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    protected @Nullable List<List<AbstractMapIngredient>> prepareRecipeFind(@NotNull IRecipeCapabilityHolder holder) {
        this.gtlcore$machine = holder;
        if (holder instanceof ResearchStationMachine || holder instanceof WorkableTieredMachine ||
                holder instanceof SteamWorkableMachine || holder instanceof PrimitiveWorkableMachine) {
            List<List<AbstractMapIngredient>> list = new ObjectArrayList<>(2);
            list.addAll(fromHolder(holder));
            if (list.isEmpty()) return null;
            return list;
        } else if (holder instanceof IDistinctMachine iDistinctMachine) {
            if (iDistinctMachine.getRecipeHandleParts().isEmpty()) return null;
            List<List<AbstractMapIngredient>> list = new ObjectArrayList<>(iDistinctMachine.getRecipeHandleParts().size());
            list.addAll(this.gtlcore$fromHolder(iDistinctMachine));
            if (list.isEmpty()) {
                return null;
            }
            return list;
        }
        return null;
    }

    @Unique
    protected @NotNull List<List<AbstractMapIngredient>> gtlcore$fromHolder(@NotNull IDistinctMachine r) {
        List<List<AbstractMapIngredient>> list;
        List<RecipeRunner.RecipeHandlePart> recipeHandleParts = r.getRecipeHandleParts().stream().filter(h -> h.io() == IO.IN).toList();
        list = new ObjectArrayList<>(recipeHandleParts.size());
        for (var part : recipeHandleParts) {
            ObjectArrayList<AbstractMapIngredient> ingredients = new ObjectArrayList<>();
            for (var it = part.allHandles().object2ObjectEntrySet().fastIterator(); it.hasNext();) {
                var next = it.next();
                var cap = next.getKey();
                for (var handler : next.getValue()) {
                    List<Object> compressed = cap.compressIngredients(handler.getContents());
                    for (Object content : compressed) {
                        ingredients.addAll(cap.convertToMapIngredient(content).stream().sorted(Comparator.comparing(i -> !i.isSpecialIngredient())).toList());
                    }
                }
            }
            list.add(ingredients);
        }
        return list;
    }

    /**
     * @author Adonis
     * @reason .
     */
    @Overwrite(remap = false)
    public @Nullable GTRecipe recurseIngredientTreeFindRecipe(@NotNull List<List<AbstractMapIngredient>> ingredients,
                                                              @NotNull Branch branchMap, @NotNull Predicate<GTRecipe> canHandle, int index, int count, long skip) {
        if (count == ingredients.size()) {
            return null;
        } else if (this.gtlcore$machine instanceof ResearchStationMachine || this.gtlcore$machine instanceof WorkableTieredMachine ||
                this.gtlcore$machine instanceof SteamWorkableMachine || this.gtlcore$machine instanceof PrimitiveWorkableMachine) {
                    for (AbstractMapIngredient obj : ingredients.get(index)) {
                        Map<AbstractMapIngredient, Either<GTRecipe, Branch>> targetMap = determineRootNodes(obj, branchMap);
                        Either<GTRecipe, Branch> result = targetMap.get(obj);
                        if (result != null) {
                            GTRecipe r = result.map(potentialRecipe -> canHandle.test(potentialRecipe) ? potentialRecipe : null,
                                    potentialBranch -> diveIngredientTreeFindRecipe(ingredients, potentialBranch, canHandle, index, count, skip));
                            if (r != null) {
                                return r;
                            }
                        }
                    }
                } else
            if (this.gtlcore$machine instanceof IDistinctMachine) {
                List<AbstractMapIngredient> ingredient = new ObjectArrayList<>(ingredients.get(index));
                return this.gtlcore$diveIngredientTreeFindRecipe(ingredient, branchMap, canHandle);
            }
        return null;
    }

    @Unique
    private @Nullable GTRecipe gtlcore$diveIngredientTreeFindRecipe(@NotNull List<AbstractMapIngredient> ingredients, @NotNull Branch branchMap,
                                                                    @NotNull Predicate<GTRecipe> canHandle) {
        if (ingredients.isEmpty()) return null;
        for (var o : ingredients) {
            Map<AbstractMapIngredient, Either<GTRecipe, Branch>> targetMap = determineRootNodes(o, branchMap);
            Either<GTRecipe, Branch> result = targetMap.get(o);
            if (result != null) {
                GTRecipe r = result.map((potentialRecipe) -> canHandle.test(potentialRecipe) ? potentialRecipe : null,
                        (potentialBranch) -> this.gtlcore$diveIngredientTreeFindRecipe(ingredients, potentialBranch, canHandle));
                if (r != null) {
                    return r;
                }
            }
        }
        return null;
    }

    @Shadow(remap = false)
    protected abstract @NotNull List<List<AbstractMapIngredient>> fromHolder(@NotNull IRecipeCapabilityHolder r);

    @Shadow(remap = false)
    public abstract @Nullable GTRecipe find(@NotNull IRecipeCapabilityHolder holder, @NotNull Predicate<GTRecipe> canHandle);

    @Shadow(remap = false)
    private @Nullable GTRecipe diveIngredientTreeFindRecipe(@NotNull List<List<AbstractMapIngredient>> ingredients, @NotNull Branch map, @NotNull Predicate<GTRecipe> canHandle, int currentIndex, int count, long skip) {
        return null;
    }

    @Shadow(remap = false)
    protected static @NotNull Map<AbstractMapIngredient, Either<GTRecipe, Branch>> determineRootNodes(@NotNull AbstractMapIngredient ingredient, @NotNull Branch branchMap) {
        throw new RuntimeException();
    }
}
