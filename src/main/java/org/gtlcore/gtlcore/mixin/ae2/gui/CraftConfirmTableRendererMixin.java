package org.gtlcore.gtlcore.mixin.ae2.gui;

import org.gtlcore.gtlcore.integration.ae2.common.IConfirmStartMenu;
import org.gtlcore.gtlcore.integration.ae2.crafting.ICraftingPlanSummaryEntry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import appeng.api.stacks.KeyCounter;
import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.me.crafting.AbstractTableRenderer;
import appeng.client.gui.me.crafting.CraftConfirmTableRenderer;
import appeng.menu.me.crafting.CraftingPlanSummaryEntry;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(CraftConfirmTableRenderer.class)
public abstract class CraftConfirmTableRendererMixin extends AbstractTableRenderer<CraftingPlanSummaryEntry> {

    protected CraftConfirmTableRendererMixin(AEBaseScreen<?> screen, int x, int y, int rows) {
        super(screen, x, y, rows);
    }

    @Nullable
    @Unique
    private KeyCounter gtlcore$cachedKeys = null;

    @Inject(method = "getEntryDescription(Lappeng/menu/me/crafting/CraftingPlanSummaryEntry;)Ljava/util/List;", at = @At("TAIL"), cancellable = true, remap = false)
    private void getEntryDescription(CraftingPlanSummaryEntry entry, CallbackInfoReturnable<List<Component>> cir) {
        var lines = cir.getReturnValue();

        var craftTimes = ((ICraftingPlanSummaryEntry) entry).gtlcore$getCraftTimes();
        if (craftTimes > 1) {
            var color = craftTimes < 500 ? ChatFormatting.DARK_GREEN :
                    craftTimes < 2000 ? ChatFormatting.GOLD :
                            craftTimes < 6000 ? ChatFormatting.RED :
                                    ChatFormatting.DARK_RED;
            var timesText = Component.literal(String.format("%d", craftTimes)).withStyle(color);
            lines.add(Component.translatable("gtlcore.ae.craft.create_times", timesText));
        }

        if (gtlcore$cachedKeys != null && entry.getMissingAmount() == 0) {
            var storedTotal = gtlcore$cachedKeys.get(entry.getWhat());
            if (storedTotal > 0) {
                var storedPercent = Math.min((float) entry.getStoredAmount() / storedTotal, 1.0f);
                var color = storedPercent < 0.25f ? ChatFormatting.DARK_GREEN :
                        storedPercent < 0.5f ? ChatFormatting.GOLD :
                                storedPercent < 0.75f ? ChatFormatting.RED :
                                        ChatFormatting.DARK_RED;
                var percentText = Component.literal(gtlcore$formatNumber2Places(storedPercent * 100) + "%").withStyle(color);
                lines.add(Component.translatable("gtlcore.ae.craft.used_percent", percentText));
            }
        }

        cir.setReturnValue(lines);
    }

    @WrapMethod(method = "getEntryDescription(Lappeng/menu/me/crafting/CraftingPlanSummaryEntry;)Ljava/util/List;", remap = false)
    private List<Component> warpGetEntryDescription(CraftingPlanSummaryEntry entry, Operation<List<Component>> original) {
        var repo = (screen.getMenu()) instanceof IConfirmStartMenu me ? me.gtlcore$getClientRepo() : null;
        if (repo == null) return original.call(entry);

        gtlcore$cachedKeys = new KeyCounter();
        repo.getAllEntries().stream().filter(e -> e.getWhat() != null && e.getStoredAmount() > 0)
                .forEach(e -> gtlcore$cachedKeys.add(e.getWhat(), e.getStoredAmount()));

        var lines = original.call(entry);
        gtlcore$cachedKeys = null;
        return lines;
    }

    @Unique
    private String gtlcore$formatNumber2Places(double number) {
        if (number == (long) number) {
            return String.format("%d", (long) number);
        } else {
            return String.format("%.2f", number);
        }
    }
}
