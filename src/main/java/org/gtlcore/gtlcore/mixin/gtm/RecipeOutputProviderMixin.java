package org.gtlcore.gtlcore.mixin.gtm;

import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.integration.jade.provider.CapabilityBlockProvider;
import com.gregtechceu.gtceu.integration.jade.provider.RecipeOutputProvider;
import com.gregtechceu.gtceu.utils.GTUtil;

import com.lowdragmc.lowdraglib.side.fluid.FluidStack;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author EasterFG on 2025/3/8
 */
@Mixin(RecipeOutputProvider.class)
public abstract class RecipeOutputProviderMixin extends CapabilityBlockProvider<RecipeLogic> {

    @Shadow(remap = false)
    protected abstract void addFluidTooltips(ITooltip iTooltip, List<FluidStack> outputFluids);

    @Shadow(remap = false)
    protected abstract void addItemTooltips(ITooltip iTooltip, List<ItemStack> outputItems);

    protected RecipeOutputProviderMixin(ResourceLocation uid) {
        super(uid);
    }

    @Override
    protected void write(CompoundTag data, RecipeLogic recipeLogic) {
        if (recipeLogic.isWorking()) {
            data.putBoolean("Working", recipeLogic.isWorking());
            var recipe = recipeLogic.getLastRecipe();
            if (recipe != null) {
                Set<String> cache = new HashSet<>();
                int count = 0;
                ListTag itemTags = new ListTag();
                for (var stack : RecipeHelper.getOutputItems(recipe)) {
                    if (stack != null && !stack.isEmpty()) {
                        var itemTag = new CompoundTag();

                        GTUtil.saveItemStack(stack, itemTag);
                        String id = itemTag.getString("id");
                        if (cache.add(id)) {
                            itemTags.add(itemTag);
                        } else {
                            count++;
                        }
                    }
                }
                if (!itemTags.isEmpty()) {
                    data.put("OutputItems", itemTags);
                }
                ListTag fluidTags = new ListTag();
                for (var stack : RecipeHelper.getOutputFluids(recipe)) {
                    if (stack != null && !stack.isEmpty()) {
                        var fluidTag = new CompoundTag();
                        stack.saveToTag(fluidTag);
                        String id = fluidTag.getString("FluidName");
                        if (cache.add(id)) {
                            fluidTags.add(fluidTag);
                        } else {
                            count++;
                        }
                    }
                }
                if (!fluidTags.isEmpty()) {
                    data.put("OutputFluids", fluidTags);
                }
                data.putInt("ExtraOutput", count);
            }
        }
    }

    @Override
    protected void addTooltip(CompoundTag capData, ITooltip tooltip, Player player, BlockAccessor block,
                              BlockEntity blockEntity, IPluginConfig config) {
        if (capData.getBoolean("Working")) {
            List<ItemStack> outputItems = new ArrayList<>();
            if (capData.contains("OutputItems", Tag.TAG_LIST)) {
                ListTag itemTags = capData.getList("OutputItems", Tag.TAG_COMPOUND);
                if (!itemTags.isEmpty()) {
                    for (Tag tag : itemTags) {
                        if (tag instanceof CompoundTag tCompoundTag) {
                            var stack = GTUtil.loadItemStack(tCompoundTag);
                            if (!stack.isEmpty()) {
                                outputItems.add(stack);
                            }
                        }
                    }
                }
            }
            List<FluidStack> outputFluids = new ArrayList<>();
            if (capData.contains("OutputFluids", Tag.TAG_LIST)) {
                ListTag fluidTags = capData.getList("OutputFluids", Tag.TAG_COMPOUND);
                for (Tag tag : fluidTags) {
                    if (tag instanceof CompoundTag tCompoundTag) {
                        var stack = FluidStack.loadFromTag(tCompoundTag);
                        if (!stack.isEmpty()) {
                            outputFluids.add(stack);
                        }
                    }
                }
            }
            if (!outputItems.isEmpty() || !outputFluids.isEmpty()) {
                tooltip.add(Component.translatable("gtceu.top.recipe_output"));
            }
            addItemTooltips(tooltip, outputItems);
            addFluidTooltips(tooltip, outputFluids);
            tooltip.add(Component.translatable("gtceu.top.extra_output", capData.getInt("ExtraOutput"))
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}
