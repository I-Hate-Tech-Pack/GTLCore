package org.gtlcore.gtlcore.mixin.gtm.recipe;

import org.gtlcore.gtlcore.config.ConfigHolder;
import org.gtlcore.gtlcore.utils.RecipeMultiOutputUtils;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author EasterFG on 2024/12/10
 */
@Mixin(GTRecipe.class)
public abstract class GTRecipeMixin {

    @Shadow(remap = false)
    public abstract GTRecipe copy();

    @Shadow(remap = false)
    @Final
    public GTRecipeType recipeType;
    @Shadow(remap = false)
    public ResourceLocation id;

    @Shadow(remap = false)
    public abstract Map<RecipeCapability<?>, List<Content>> copyContents(Map<RecipeCapability<?>, List<Content>> contents, @Nullable ContentModifier modifier);

    @Shadow(remap = false)
    @Final
    public Map<RecipeCapability<?>, List<Content>> inputs;
    @Shadow(remap = false)
    public int duration;
    @Shadow(remap = false)
    @Final
    public Map<RecipeCapability<?>, List<Content>> tickInputs;
    @Shadow(remap = false)
    @Final
    public Map<RecipeCapability<?>, List<Content>> tickOutputs;
    @Shadow(remap = false)
    @Final
    public Map<RecipeCapability<?>, ChanceLogic> inputChanceLogics;
    @Shadow(remap = false)
    @Final
    public Map<RecipeCapability<?>, ChanceLogic> outputChanceLogics;
    @Shadow(remap = false)
    @Final
    public Map<RecipeCapability<?>, ChanceLogic> tickInputChanceLogics;
    @Shadow(remap = false)
    @Final
    public Map<RecipeCapability<?>, ChanceLogic> tickOutputChanceLogics;
    @Shadow(remap = false)
    @Final
    public List<RecipeCondition> conditions;
    @Shadow(remap = false)
    @Final
    public List<?> ingredientActions;
    @Shadow(remap = false)
    @NotNull
    public CompoundTag data;

    @Shadow(remap = false)
    public abstract boolean isFuel();

    @Shadow(remap = false)
    @Final
    public Map<RecipeCapability<?>, List<Content>> outputs;

    /**
     * @author me
     * @reason fix output and input can not exceed int
     */
    @Overwrite(remap = false)
    public GTRecipe copy(ContentModifier modifier, boolean modifyDuration) {
        GTRecipe copied;
        if (ConfigHolder.INSTANCE.recipeMultiOutput) {
            copied = new GTRecipe(recipeType, id, gTLCore$copyContentsWhitMore(inputs, modifier, true), gTLCore$copyContentsWhitMore(outputs, modifier, false), copyContents(tickInputs, modifier), copyContents(tickOutputs, modifier), new HashMap<>(inputChanceLogics), new HashMap<>(outputChanceLogics), new HashMap<>(tickInputChanceLogics), new HashMap<>(tickOutputChanceLogics), new ArrayList<>(conditions), new ArrayList<>(ingredientActions), data, duration, isFuel());
        } else {
            copied = new GTRecipe(recipeType, id, gTLCore$copyContentsWhitMore(inputs, modifier, true), copyContents(outputs, modifier), copyContents(tickInputs, modifier), copyContents(tickOutputs, modifier), new HashMap<>(inputChanceLogics), new HashMap<>(outputChanceLogics), new HashMap<>(tickInputChanceLogics), new HashMap<>(tickOutputChanceLogics), new ArrayList<>(conditions), new ArrayList<>(ingredientActions), data, duration, isFuel());
        }
        if (modifyDuration) {
            copied.duration = modifier.apply(this.duration).intValue();
        }
        return copied;
    }

    @Unique
    public Map<RecipeCapability<?>, List<Content>> gTLCore$copyContentsWhitMore(Map<RecipeCapability<?>, List<Content>> contents, @Nullable ContentModifier modifier, boolean input) {
        Map<RecipeCapability<?>, List<Content>> copyContents = new HashMap<>();
        for (var entry : contents.entrySet()) {
            var contentList = entry.getValue();
            var cap = entry.getKey();
            if (contentList != null && !contentList.isEmpty()) {
                List<Content> contentsCopy = new ArrayList<>();
                for (Content content : contentList) {
                    if (cap instanceof FluidRecipeCapability) {
                        List<Content> list = RecipeMultiOutputUtils.copyAsList(content, cap, modifier, input);
                        contentsCopy.addAll(list);
                    } else {
                        contentsCopy.add(content.copy(cap, modifier));
                    }
                }
                copyContents.put(entry.getKey(), contentsCopy);
            }
        }
        return copyContents;
    }
}
