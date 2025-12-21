package org.gtlcore.gtlcore.api.event;

import org.gtlcore.gtlcore.utils.SourceTooltipHelper;
import org.gtlcore.gtlcore.utils.datastructure.TooltipEntry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

import java.util.List;

/**
 * This event is fired on the MOD event bus during mod initialization.
 * Subscribe to this event to register custom source tooltips for items and fluids.
 * <p>
 * <b>Note:</b> This is a client-only event. It will only fire on the client side.
 * <p>
 * Example usage in other mods:
 *
 * <pre>
 * {@code
 *
 * @SubscribeEvent
 * public static void onSourceTooltipRegistration(SourceTooltipRegistrationEvent event) {
 *     // Legacy string-based methods (for backwards compatibility)
 *     event.addItemTooltip("minecraft:diamond", "§6From my mod");
 *     event.addFluidTooltip("gtceu:oxygen", "§bBy special process");
 *
 *     // Recommended: Using translatable keys for i18n support
 *     event.addItemTranslatable("minecraft:emerald", "mymod.emerald_source");
 *     event.addFluidTranslatable("gtceu:hydrogen", "mymod.hydrogen_source", 10, "tank");
 *
 *     // Advanced: Using TooltipEntry for complex tooltips with colors
 *     event.addItemTooltipEntry("minecraft:gold_ingot",
 *             TooltipEntry.combined(
 *                     TooltipEntry.translatableWithColor("mymod.rare_metal", ChatFormatting.GOLD),
 *                     TooltipEntry.literalWithColor(" [Tier 3]", ChatFormatting.GRAY)));
 * }
 * }
 * </pre>
 */
@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused")
public class SourceTooltipRegistrationEvent extends Event implements IModBusEvent {

    public void addItemTooltip(String itemId, String tooltip) {
        SourceTooltipHelper.addItemTooltip(itemId, tooltip);
    }

    public void addItemTooltips(String itemId, List<String> tooltips) {
        SourceTooltipHelper.addItemTooltips(itemId, tooltips);
    }

    public void addItemsTooltip(List<String> itemIds, String tooltip) {
        SourceTooltipHelper.addItemsTooltip(itemIds, tooltip);
    }

    public void addItemsTooltips(List<String> itemIds, List<String> tooltips) {
        SourceTooltipHelper.addItemsTooltips(itemIds, tooltips);
    }

    public void addFluidTooltip(String fluidId, String tooltip) {
        SourceTooltipHelper.addFluidTooltip(fluidId, tooltip);
    }

    public void addFluidTooltips(String fluidId, List<String> tooltips) {
        SourceTooltipHelper.addFluidTooltips(fluidId, tooltips);
    }

    public void addItemTooltipDirect(Item item, String tooltip) {
        SourceTooltipHelper.addItemTooltipDirect(item, tooltip);
    }

    public void addFluidTooltipDirect(Fluid fluid, String tooltip) {
        SourceTooltipHelper.addFluidTooltipDirect(fluid, tooltip);
    }

    public void addItemTooltipEntry(String itemId, TooltipEntry entry) {
        SourceTooltipHelper.addItemTooltipEntry(itemId, entry);
    }

    public void addItemTooltipEntries(String itemId, List<TooltipEntry> entries) {
        SourceTooltipHelper.addItemTooltipEntries(itemId, entries);
    }

    public void addFluidTooltipEntry(String fluidId, TooltipEntry entry) {
        SourceTooltipHelper.addFluidTooltipEntry(fluidId, entry);
    }

    public void addFluidTooltipEntries(String fluidId, List<TooltipEntry> entries) {
        SourceTooltipHelper.addFluidTooltipEntries(fluidId, entries);
    }

    public void addItemTooltipDirect(Item item, TooltipEntry entry) {
        SourceTooltipHelper.addItemTooltipEntryDirect(item, entry);
    }

    public void addFluidTooltipDirect(Fluid fluid, TooltipEntry entry) {
        SourceTooltipHelper.addFluidTooltipEntryDirect(fluid, entry);
    }

    public void addItemTranslatable(String itemId, String key, Object... args) {
        SourceTooltipHelper.addItemTranslatable(itemId, key, args);
    }

    public void addFluidTranslatable(String fluidId, String key, Object... args) {
        SourceTooltipHelper.addFluidTranslatable(fluidId, key, args);
    }
}
