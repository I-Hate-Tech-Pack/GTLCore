package org.gtlcore.gtlcore.mixin.gtm;

import org.gtlcore.gtlcore.api.gui.PatternPreviewWidget;

import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.integration.emi.multipage.MultiblockInfoEmiRecipe;

import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Supplier;

@Mixin(MultiblockInfoEmiRecipe.class)
public abstract class MultiblockInfoEmiRecipeMixin {

    @ModifyArg(
               method = "<init>",
               at = @At(
                        value = "INVOKE",
                        target = "Lcom/lowdragmc/lowdraglib/emi/ModularEmiRecipe;<init>(Ljava/util/function/Supplier;)V"),
               remap = false)
    private static Supplier<WidgetGroup> modifySuperSupplier(Supplier<WidgetGroup> originalSupplier,
                                                             @Local(argsOnly = true) MultiblockMachineDefinition definition) {
        return () -> PatternPreviewWidget.getPatternWidget(definition);
    }
}
