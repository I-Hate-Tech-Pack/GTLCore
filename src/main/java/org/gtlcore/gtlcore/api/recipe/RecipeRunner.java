package org.gtlcore.gtlcore.api.recipe;

import org.gtlcore.gtlcore.api.machine.trait.IDistinctMachine;

import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.chance.boost.ChanceBoostFunction;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * 部分代码参考自gto
 * &#064;line <a href="https://github.com/GregTech-Odyssey/GTOCore">...</a>
 */

public class RecipeRunner {

    private final GTRecipe recipe;
    private final RecipeHandlePart recipeHandlePart;
    private final IO io;
    private final boolean isTick;
    private final IRecipeCapabilityHolder holder;
    private final Map<RecipeCapability<?>, Object2IntMap<?>> chanceCaches;
    private final boolean simulated;
    private Object2ObjectOpenHashMap<RecipeCapability<?>, List<Object>> recipeContent;

    public RecipeRunner(GTRecipe recipe, IO io, boolean isTick, IRecipeCapabilityHolder holder,
                        Map<RecipeCapability<?>, Object2IntMap<?>> chanceCaches, boolean simulated) {
        RecipeHandlePart recipeHandlePart = null;
        if (io == IO.IN && holder instanceof IDistinctMachine iDistinctMachine) {
            if (recipe.id.equals(iDistinctMachine.getRecipeId())) {
                recipeHandlePart = iDistinctMachine.getDistinctHatch();
            } else {
                iDistinctMachine.setRecipeId(recipe.id);
                iDistinctMachine.setDistinctHatch(null);
            }
        }
        this.recipeHandlePart = recipeHandlePart;
        this.recipe = recipe;
        this.io = io;
        this.isTick = isTick;
        this.holder = holder;
        this.chanceCaches = chanceCaches;
        this.recipeContent = new Object2ObjectOpenHashMap<>();
        this.simulated = simulated;
    }

    public GTRecipe.ActionResult handle(Map<RecipeCapability<?>, List<Content>> entry) {
        this.fillContent(entry);
        if (this.recipeContent.isEmpty()) return GTRecipe.ActionResult.fail(null);
        return this.handleContents();
    }

    private void fillContent(Map<RecipeCapability<?>, List<Content>> entries) {
        for (var entry : entries.entrySet()) {
            RecipeCapability<?> cap = entry.getKey();
            if (!cap.doMatchInRecipe()) continue;
            if (entry.getValue().isEmpty()) continue;
            List<Content> chancedContents = new ArrayList<>();
            var contentList = this.recipeContent.computeIfAbsent(cap, c -> new ObjectArrayList<>());
            for (Content cont : entry.getValue()) {
                if (simulated) {
                    contentList.add(cont.content);
                } else {
                    if (cont.chance >= cont.maxChance) {
                        contentList.add(cont.content);
                    } else {
                        chancedContents.add(cont.copy(cap, ContentModifier.multiplier(1.0 / recipe.parallels)));
                    }
                }
            }
            if (!chancedContents.isEmpty()) {
                ChanceBoostFunction function = recipe.getType().getChanceFunction();
                ChanceLogic logic = recipe.getChanceLogicForCapability(cap, this.io, this.isTick);
                int recipeTier = RecipeHelper.getPreOCRecipeEuTier(recipe);
                int holderTier = holder.getChanceTier();
                var cache = this.chanceCaches.get(cap);
                chancedContents = logic.roll(chancedContents, function, recipeTier, holderTier, cache, recipe.parallels, cap);
                if (chancedContents == null) return;
                for (Content cont : chancedContents) {
                    contentList.add(cont.content);
                }
            }
            if (contentList.isEmpty()) recipeContent.remove(cap);
        }
    }

    private GTRecipe.@NotNull ActionResult handleContents() {
        return this.handleContentsInternal(this.io) ? GTRecipe.ActionResult.SUCCESS : GTRecipe.ActionResult.fail(null);
    }

    private boolean handleContentsInternal(IO capIO) {
        if (this.recipeContent.isEmpty()) return true;
        if (holder instanceof IDistinctMachine iDistinctMachine) {
            if (this.recipeHandlePart != null) {
                var result = this.handleRecipe(this.recipeHandlePart, this.recipeHandlePart.io, true, false);
                return result.isEmpty();
            } else if (!iDistinctMachine.getRecipeHandleParts().isEmpty()) {
                for (var recipeHandlePart : iDistinctMachine.getRecipeHandleParts().stream().filter(h -> h.io == capIO).toList()) {
                    var result = this.handleRecipe(recipeHandlePart, capIO, true, false);
                    if (result.isEmpty()) {
                        if (!this.simulated) {
                            this.recipeContent = this.handleRecipe(recipeHandlePart, capIO, false, true);
                            return this.recipeContent.isEmpty();
                        } else {
                            this.recipeContent.clear();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private Object2ObjectOpenHashMap<RecipeCapability<?>, List<Object>> handleRecipe(RecipeHandlePart handlePart, IO io, boolean isSimulate, boolean isProcess) {
        if (handlePart.allHandles().isEmpty()) return this.recipeContent;
        var copy = isProcess ? this.recipeContent : new Object2ObjectOpenHashMap<>(this.recipeContent);
        for (var entry = copy.object2ObjectEntrySet().fastIterator(); entry.hasNext();) {
            var content = entry.next();
            List left = content.getValue();
            var handlerList = handlePart.allHandles().get(content.getKey());
            if (handlerList != null) {
                for (IRecipeHandler<?> proxy : handlerList) {
                    left = proxy.handleRecipe(io, recipe, left, null, isSimulate);
                    if (left == null || left.isEmpty()) {
                        entry.remove();
                        break;
                    }
                }
            }
        }
        return copy;
    }

    public boolean simulatedHandle() {
        if (this.holder instanceof IDistinctMachine iDistinctMachine) {
            if (iDistinctMachine.getRecipeHandleParts().isEmpty()) return false;
            List<RecipeHandlePart> recipeHandlingResultList = iDistinctMachine.getRecipeHandleParts().stream().filter(h -> h.io == IO.IN).toList();
            this.fillContent(this.recipe.inputs);
            List<Object> itemContent = this.recipeContent.computeIfAbsent(ItemRecipeCapability.CAP, k -> new ObjectArrayList<>());
            List<Object> fluidContent = this.recipeContent.computeIfAbsent(FluidRecipeCapability.CAP, k -> new ObjectArrayList<>());
            if (itemContent.isEmpty() && fluidContent.isEmpty()) return false;
            if (this.recipeHandlePart != null) {
                return this.recipeHandlePart.testRecipeHandle(iDistinctMachine, this.recipe, itemContent, fluidContent);
            }
            for (RecipeHandlePart recipeHandlePart : recipeHandlingResultList) {
                if (recipeHandlePart.testRecipeHandle(iDistinctMachine, this.recipe, itemContent, fluidContent)) {
                    return true;
                }
            }
        }
        return false;
    }

    public record RecipeHandlePart(IO io, Object2ObjectOpenHashMap<RecipeCapability<?>, List<IRecipeHandler<?>>> allHandles) {

        private boolean testRecipeHandle(IDistinctMachine iDistinctMachine, GTRecipe recipe, List<Object> itemContent, List<Object> fluidContent) {
            if (itemContent.isEmpty()) {
                List<?> copyFluid = new ObjectArrayList<>(fluidContent);
                for (var handle : this.allHandles.get(FluidRecipeCapability.CAP)) {
                    copyFluid = handle.handleRecipe(IO.IN, recipe, copyFluid, null, true);
                    if (copyFluid == null) {
                        iDistinctMachine.setDistinctHatch(this);
                        return true;
                    }
                }
            } else if (fluidContent.isEmpty()) {
                List<?> copyItem = new ObjectArrayList<>(itemContent);
                for (var handle : this.allHandles.get(ItemRecipeCapability.CAP)) {
                    copyItem = handle.handleRecipe(IO.IN, recipe, copyItem, null, true);
                    if (copyItem == null) {
                        iDistinctMachine.setDistinctHatch(this);
                        return true;
                    }
                }
            } else {
                List<?> copyItem = new ObjectArrayList<>(itemContent);
                for (var handle : this.allHandles.get(ItemRecipeCapability.CAP)) {
                    copyItem = handle.handleRecipe(IO.IN, recipe, copyItem, null, true);
                    if (copyItem == null) {
                        List<?> copyFluid = new ObjectArrayList<>(fluidContent);
                        for (var h : this.allHandles.get(FluidRecipeCapability.CAP)) {
                            copyFluid = h.handleRecipe(IO.IN, recipe, copyFluid, null, true);
                            if (copyFluid == null) {
                                iDistinctMachine.setDistinctHatch(this);
                                return true;
                            }
                        }
                        copyItem = new ObjectArrayList<>(itemContent);
                    }
                }
            }
            return false;
        }
    }
}
