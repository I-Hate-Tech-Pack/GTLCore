package org.gtlcore.gtlcore.mixin.gtm.client;

import org.gtlcore.gtlcore.utils.SourceTooltipHelper;

import com.gregtechceu.gtceu.client.TooltipsHandler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

@Mixin(TooltipsHandler.class)
public abstract class TooltipsHandlerMixin {

    @Inject(method = "appendTooltips",
            at = @At("HEAD"),
            remap = false)
    private static void appendCustomItemTooltips(ItemStack stack, TooltipFlag flag, List<Component> tooltips, CallbackInfo ci) {
        tooltips.addAll(1, SourceTooltipHelper.getItemTooltipComponents(stack.getItem()));
    }

    @Inject(method = "appendFluidTooltips",
            at = @At("RETURN"),
            remap = false)
    private static void appendCustomFluidTooltips(Fluid fluid, long amount, Consumer<Component> tooltips, TooltipFlag flag, CallbackInfo ci) {
        SourceTooltipHelper.getFluidTooltipComponents(fluid).forEach(tooltips);
    }
}
