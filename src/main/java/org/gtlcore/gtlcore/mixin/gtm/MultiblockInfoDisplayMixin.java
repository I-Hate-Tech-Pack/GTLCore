package org.gtlcore.gtlcore.mixin.gtm;

import org.gtlcore.gtlcore.api.gui.PatternPreviewWidget;

import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.integration.rei.multipage.MultiblockInfoDisplay;

import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Supplier;

@Mixin(MultiblockInfoDisplay.class)
public abstract class MultiblockInfoDisplayMixin {

    @ModifyArg(
               method = "<init>",
               at = @At(
                        value = "INVOKE",
                        target = "Lcom/lowdragmc/lowdraglib/rei/ModularDisplay;<init>(Ljava/util/function/Supplier;Lme/shedaniel/rei/api/common/category/CategoryIdentifier;)V"),
               index = 0,
               remap = false)
    private static Supplier<WidgetGroup> gtl$replaceWidgetSupplier(
                                                                   Supplier<WidgetGroup> original,
                                                                   @Local(argsOnly = true) MultiblockMachineDefinition definition) {
        return () -> PatternPreviewWidget.getPatternWidget(definition);
    }
}
