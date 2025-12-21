package org.gtlcore.gtlcore.utils;

import org.gtlcore.gtlcore.utils.datastructure.TooltipEntry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class SourceTooltipHelper {

    private static final Map<Item, ObjectList<TooltipEntry>> ITEM_TOOLTIPS = new Reference2ReferenceOpenHashMap<>();
    private static final Map<Fluid, ObjectList<TooltipEntry>> FLUID_TOOLTIPS = new Reference2ReferenceOpenHashMap<>();

    // ==================== Getter Methods ====================

    public static List<TooltipEntry> getItemTooltipEntries(Item item) {
        return ITEM_TOOLTIPS.getOrDefault(item, ObjectLists.emptyList());
    }

    public static List<TooltipEntry> getFluidTooltipEntries(Fluid fluid) {
        return FLUID_TOOLTIPS.getOrDefault(fluid, ObjectLists.emptyList());
    }

    public static List<Component> getItemTooltipComponents(Item item) {
        List<TooltipEntry> entries = ITEM_TOOLTIPS.get(item);
        if (entries == null || entries.isEmpty()) {
            return List.of();
        }
        return entries.stream()
                .map(TooltipEntry::toComponent)
                .collect(Collectors.toList());
    }

    public static List<Component> getFluidTooltipComponents(Fluid fluid) {
        List<TooltipEntry> entries = FLUID_TOOLTIPS.get(fluid);
        if (entries == null || entries.isEmpty()) {
            return Collections.emptyList();
        }
        return entries.stream()
                .map(TooltipEntry::toComponent)
                .collect(Collectors.toList());
    }

    // ==================== Core Methods ====================

    public static void addItemTooltipEntry(String itemId, TooltipEntry entry) {
        Item item = Registries.getItem(itemId);
        if (!item.equals(Items.BARRIER)) {
            ITEM_TOOLTIPS.computeIfAbsent(item, k -> new ObjectArrayList<>()).add(entry);
        }
    }

    public static void addItemTooltipEntries(String itemId, List<TooltipEntry> entries) {
        Item item = Registries.getItem(itemId);
        if (!item.equals(Items.BARRIER)) {
            ITEM_TOOLTIPS.computeIfAbsent(item, k -> new ObjectArrayList<>()).addAll(entries);
        }
    }

    public static void addFluidTooltipEntry(String fluidId, TooltipEntry entry) {
        Fluid fluid = Registries.getFluid(fluidId);
        if (!fluid.equals(Fluids.WATER)) {
            FLUID_TOOLTIPS.computeIfAbsent(fluid, k -> new ObjectArrayList<>()).add(entry);
        }
    }

    public static void addFluidTooltipEntries(String fluidId, List<TooltipEntry> entries) {
        Fluid fluid = Registries.getFluid(fluidId);
        if (!fluid.equals(Fluids.WATER)) {
            FLUID_TOOLTIPS.computeIfAbsent(fluid, k -> new ObjectArrayList<>()).addAll(entries);
        }
    }

    public static void addItemTooltipEntryDirect(Item item, TooltipEntry entry) {
        ITEM_TOOLTIPS.computeIfAbsent(item, k -> new ObjectArrayList<>()).add(entry);
    }

    public static void addFluidTooltipEntryDirect(Fluid fluid, TooltipEntry entry) {
        FLUID_TOOLTIPS.computeIfAbsent(fluid, k -> new ObjectArrayList<>()).add(entry);
    }

    // ==================== String Overload Methods ====================

    public static void addItemTooltip(String itemId, String text) {
        addItemTooltipEntry(itemId, TooltipEntry.literal(text));
    }

    public static void addItemTooltips(String itemId, List<String> texts) {
        addItemTooltipEntries(itemId, texts.stream().map(TooltipEntry::literal).toList());
    }

    public static void addFluidTooltip(String fluidId, String text) {
        addFluidTooltipEntry(fluidId, TooltipEntry.literal(text));
    }

    public static void addFluidTooltips(String fluidId, List<String> texts) {
        addFluidTooltipEntries(fluidId, texts.stream().map(TooltipEntry::literal).toList());
    }

    public static void addItemTooltipDirect(Item item, String text) {
        addItemTooltipEntryDirect(item, TooltipEntry.literal(text));
    }

    public static void addFluidTooltipDirect(Fluid fluid, String text) {
        addFluidTooltipEntryDirect(fluid, TooltipEntry.literal(text));
    }

    public static void addItemsTooltip(List<String> itemIds, String text) {
        TooltipEntry entry = TooltipEntry.literal(text);
        for (String itemId : itemIds) {
            addItemTooltipEntry(itemId, entry);
        }
    }

    public static void addItemsTooltips(List<String> itemIds, List<String> texts) {
        List<TooltipEntry> entries = texts.stream().map(TooltipEntry::literal).toList();
        for (String itemId : itemIds) {
            addItemTooltipEntries(itemId, entries);
        }
    }

    public static void addItemTranslatable(String itemId, String key, Object... args) {
        addItemTooltipEntry(itemId, args.length > 0 ? TooltipEntry.translatable(key, args) : TooltipEntry.translatable(key));
    }

    public static void addFluidTranslatable(String fluidId, String key, Object... args) {
        addFluidTooltipEntry(fluidId, args.length > 0 ? TooltipEntry.translatable(key, args) : TooltipEntry.translatable(key));
    }
}
