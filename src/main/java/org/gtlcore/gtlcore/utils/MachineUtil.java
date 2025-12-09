package org.gtlcore.gtlcore.utils;

import org.gtlcore.gtlcore.api.recipe.RecipeResult;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.ingredient.IntCircuitIngredient;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import com.lowdragmc.lowdraglib.side.fluid.FluidStack;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.Objects;

import static org.gtlcore.gtlcore.api.recipe.RecipeRunnerHelper.*;
import static org.gtlcore.gtlcore.utils.Registries.getItem;

public class MachineUtil {

    public static final BlockPos[] EMPTY_POS_ARRAY = new BlockPos[0];

    private static final Lazy<Map<Item, EquipmentSlot>> armorMap = Lazy.of(() -> Map.of(
            getItem("kubejs:magnetohydrodynamicallyconstrainedstarmatter_boots"), EquipmentSlot.FEET,
            getItem("kubejs:magnetohydrodynamicallyconstrainedstarmatter_leggings"), EquipmentSlot.LEGS,
            getItem("kubejs:magnetohydrodynamicallyconstrainedstarmatter_chestplate"), EquipmentSlot.CHEST,
            getItem("kubejs:magnetohydrodynamicallyconstrainedstarmatter_helmet"), EquipmentSlot.HEAD));

    private MachineUtil() {
        throw new IllegalAccessError();
    }

    public static BlockPos getOffsetPos(int a, int b, Direction facing, BlockPos pos) {
        int x = 0, z = 0;
        switch (facing) {
            case NORTH -> z = a;
            case SOUTH -> z = -a;
            case WEST -> x = a;
            case EAST -> x = -a;
        }
        return pos.offset(x, b, z);
    }

    public static boolean inputItem(WorkableMultiblockMachine machine, ItemStack item) {
        GTRecipe recipe = new GTRecipeBuilder(item.kjs$getIdLocation(), GTRecipeTypes.DUMMY_RECIPES).inputItems(item).buildRawRecipe();
        if (matchRecipeInputNocache(machine, recipe)) {
            return handleRecipeInputNocache(machine, recipe);
        } else RecipeResult.of(machine, RecipeResult.fail(Component.translatable("gtceu.recipe.fail.no.input.item", item.getDisplayName())));
        return false;
    }

    public static boolean outputItem(WorkableMultiblockMachine machine, ItemStack item) {
        if (!item.isEmpty()) {
            GTRecipe recipe = new GTRecipeBuilder(item.kjs$getIdLocation(), GTRecipeTypes.DUMMY_RECIPES).outputItems(item).buildRawRecipe();
            if (matchRecipeOutput(machine, recipe)) {
                return handleRecipeOutput(machine, recipe);
            }
        }
        return false;
    }

    public static boolean notConsumableItem(WorkableMultiblockMachine machine, ItemStack item) {
        return new GTRecipeBuilder(item.kjs$getIdLocation(), GTRecipeTypes.DUMMY_RECIPES).inputItems(item).buildRawRecipe().matchRecipe(machine).isSuccess();
    }

    public static boolean notConsumableCircuit(WorkableMultiblockMachine machine, int configuration) {
        return new GTRecipeBuilder(GTCEu.id(String.valueOf(configuration)), GTRecipeTypes.DUMMY_RECIPES).inputItems(IntCircuitIngredient.circuitInput(configuration)).buildRawRecipe()
                .matchRecipe(machine).isSuccess();
    }

    public static boolean inputFluid(WorkableMultiblockMachine machine, FluidStack fluid) {
        GTRecipe recipe = new GTRecipeBuilder(Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(fluid.getFluid())), GTRecipeTypes.DUMMY_RECIPES).inputFluids(fluid).buildRawRecipe();
        if (matchRecipeInputNocache(machine, recipe)) {
            return handleRecipeInputNocache(machine, recipe);
        } else RecipeResult.of(machine, RecipeResult.fail(Component.translatable("gtceu.recipe.fail.no.input.fluid", fluid.getDisplayName())));
        return false;
    }

    public static boolean outputFluid(WorkableMultiblockMachine machine, FluidStack fluid) {
        if (!fluid.isEmpty()) {
            GTRecipe recipe = new GTRecipeBuilder(Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(fluid.getFluid())), GTRecipeTypes.DUMMY_RECIPES).outputFluids(fluid).buildRawRecipe();
            if (matchRecipeOutput(machine, recipe)) {
                return handleRecipeOutput(machine, recipe);
            }
        }
        return false;
    }

    public static boolean inputEU(WorkableMultiblockMachine machine, long eu) {
        GTRecipe recipe = new GTRecipeBuilder(GTCEu.id(String.valueOf(eu)), GTRecipeTypes.DUMMY_RECIPES).EUt(eu).buildRawRecipe();
        if (recipe.matchTickRecipe(machine).isSuccess()) {
            return recipe.handleTickRecipeIO(IO.IN, machine, machine.recipeLogic.getChanceCaches());
        }
        return false;
    }

    public static void createItemEntity(ServerLevel level, double x, double y, double z, ItemStack itemStack) {
        final var newItem = new ItemEntity(level, x, y, z, itemStack);
        newItem.setDeltaMovement(0.0, 0.2, 0.0);
        newItem.setPickUpDelay(10);
        level.addFreshEntity(newItem);
    }

    public static boolean hasFullArmorSet(ServerPlayer player) {
        return armorMap.get().entrySet().stream()
                .allMatch(entry -> player.getItemBySlot(entry.getValue()).is(entry.getKey()));
    }
}
