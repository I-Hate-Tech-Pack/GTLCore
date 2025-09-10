package org.gtlcore.gtlcore.mixin.ae2.crafting;

import org.gtlcore.gtlcore.integration.ae2.crafting.FastCraftingCalculation;

import net.minecraft.world.level.Level;

import appeng.api.networking.crafting.ICraftingPlan;
import appeng.crafting.CraftingCalculation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingCalculation.class)
public abstract class CraftingCalculationMixin {

    @Shadow(remap = false)
    @Mutable
    @Final
    private final Level level;

    @Shadow(remap = false)
    protected abstract ICraftingPlan computePlan();

    public CraftingCalculationMixin(Level level) {
        this.level = level;
    }

    /**
     * @author .
     * @reason 改锁
     */
    @Overwrite(remap = false)
    public ICraftingPlan run() {
        synchronized (FastCraftingCalculation.object) {
            return computePlan();
        }
    }

    @Inject(method = "handlePausing", at = @At("HEAD"), cancellable = true, remap = false)
    void handlePausing(CallbackInfo ci) {
        ci.cancel();
    }
}
