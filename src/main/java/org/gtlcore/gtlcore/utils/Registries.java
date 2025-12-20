package org.gtlcore.gtlcore.utils;

import org.gtlcore.gtlcore.GTLCore;

import com.lowdragmc.lowdraglib.side.fluid.FluidStack;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Objects;

public class Registries {

    public static Item getItem(String s) {
        Item i = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s));
        if (i == Items.AIR) {
            GTLCore.LOGGER.atError().log("未找到ID为{}的物品", s);
            return Items.BARRIER;
        }
        return i;
    }

    public static ItemStack getItemStack(String s) {
        return getItemStack(s, 1);
    }

    public static ItemStack getItemStack(String s, int a) {
        return new ItemStack(getItem(s), a);
    }

    public static Block getBlock(String s) {
        Block b = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(s));
        if (b == Blocks.AIR) {
            GTLCore.LOGGER.atError().log("未找到ID为{}的方块", s);
            return Blocks.BARRIER;
        }
        return b;
    }

    public static Fluid getFluid(String s) {
        return getFluid(new ResourceLocation(s));
    }

    public static Fluid getFluid(ResourceLocation resourceLocation) {
        Fluid f = ForgeRegistries.FLUIDS.getValue(resourceLocation);
        if (f == Fluids.EMPTY) {
            GTLCore.LOGGER.atError().log("未找到ID为{}的流体", resourceLocation.toString());
            return Fluids.WATER;
        }
        return f;
    }

    public static ResourceKey<Level> getDimension(String s) {
        return ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION,
                new ResourceLocation(s));
    }

    public static RecipeManager getRecipeManager() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null && Thread.currentThread() == server.getRunningThread()) {
            return server.getRecipeManager();
        } else {
            return Objects.requireNonNull(Minecraft.getInstance().getConnection()).getRecipeManager();
        }
    }

    public static ResourceLocation getResourceKey(ItemStack itemStack) {
        return ForgeRegistries.ITEMS.getKey(itemStack.getItem());
    }

    public static ResourceLocation getResourceKey(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }

    public static ResourceLocation getResourceKey(FluidStack fluidStack) {
        return ForgeRegistries.FLUIDS.getKey(fluidStack.getFluid());
    }

    public static ResourceLocation getResourceKey(Fluid fluid) {
        return ForgeRegistries.FLUIDS.getKey(fluid);
    }

    public static ResourceLocation getResourceKey(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    public static String getItemId(ItemStack itemStack) {
        return getResourceKey(itemStack).toString();
    }

    public static String getItemId(Item item) {
        return getResourceKey(item).toString();
    }

    public static String getFluidId(FluidStack fluidStack) {
        return getResourceKey(fluidStack).toString();
    }

    public static String getBlockId(Block block) {
        return getResourceKey(block).toString();
    }
}
