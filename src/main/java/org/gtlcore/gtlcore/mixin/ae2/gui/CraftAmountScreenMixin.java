package org.gtlcore.gtlcore.mixin.ae2.gui;

import appeng.client.gui.me.crafting.CraftAmountScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CraftAmountScreen.class)
public class CraftAmountScreenMixin {

    @ModifyArg(method = "<init>",
               at = @At(value = "INVOKE",
                        target = "Lappeng/client/gui/widgets/NumberEntryWidget;setMaxValue(J)V"),
               remap = false)
    public long CraftAmountScreen(long maxValue) {
        return Long.MAX_VALUE >> 16;
    }
}
