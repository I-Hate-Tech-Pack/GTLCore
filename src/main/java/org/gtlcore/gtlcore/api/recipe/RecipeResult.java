package org.gtlcore.gtlcore.api.recipe;

import org.gtlcore.gtlcore.api.machine.trait.IRecipeStatus;

import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;

import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.Nullable;

public record RecipeResult(boolean isSuccess, @Nullable Component reason) {

    public static void of(IRecipeLogicMachine machine, RecipeResult result) {
        if (machine.getRecipeLogic() instanceof IRecipeStatus status) status.setRecipeStatus(result);
    }

    public static final RecipeResult SUCCESS = new RecipeResult(true, null);
    public static final RecipeResult FAIL_FIND = fail(Component.translatable("gtceu.recipe.fail.find"));
    public static final RecipeResult FAIL_OUTPUT = fail(Component.translatable("gtceu.recipe.fail.Output"));
    public static final RecipeResult FAIL_VOLTAGE_TIER = fail(Component.translatable("gtceu.recipe.fail.voltage.tier"));
    public static final RecipeResult FAIL_NO_ENOUGH_EU = fail(Component.translatable("gtceu.recipe.fail.no.enough.eu"));
    public static final RecipeResult FAIL_NO_ENOUGH_CWU = fail(Component.translatable("gtceu.recipe.fail.no.enough.cwu"));
    public static final RecipeResult FAIL_NO_INPUT = fail(Component.translatable("gtceu.recipe.fail.no.input"));
    public static final RecipeResult FAIL_NO_SKYLIGHT = fail(Component.translatable("gtceu.recipe.fail.no.skylight"));
    public static final RecipeResult FAIL_PROCESSING_PLANT_NO_INPUT = fail(Component.translatable("gtceu.recipe.fail.processing.plant.no.input"));
    public static final RecipeResult FAIL_PROCESSING_PLANT_WRONG_INPUT = fail(Component.translatable("gtceu.recipe.fail.processing.plant.wrong.input"));
    public static final RecipeResult FAIL_NO_FIND_RESEARCHED = fail(Component.translatable("gtceu.recipe.fail.no.find.researched"));
    public static final RecipeResult FAIL_LACK_FLUID = fail(Component.translatable("recipe.condition.rock_breaker.tooltip"));

    public static RecipeResult fail(@Nullable Component reason) {
        return new RecipeResult(false, reason);
    }
}
