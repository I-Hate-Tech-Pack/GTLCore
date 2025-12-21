package org.gtlcore.gtlcore.common.data;

import org.gtlcore.gtlcore.api.event.SourceTooltipRegistrationEvent;
import org.gtlcore.gtlcore.config.ConfigHolder;
import org.gtlcore.gtlcore.utils.datastructure.TooltipEntry;

import net.minecraft.ChatFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GTLSourceTooltips {

    public static void init(SourceTooltipRegistrationEvent event) {
        // RawOre
        registerRawOreTooltips(event);

        // Fluid
        registerFluidTooltips(event);

        // SpaceMining
        registerSpaceMiningTooltips(event);

        // RareEarth
        registerRareEarthTooltips(event);
    }

    private static void registerRawOreTooltips(SourceTooltipRegistrationEvent event) {
        if (ConfigHolder.INSTANCE.enableSkyBlokeMode) rawOreFromFragmentWorld(event);
    }

    private static void rawOreFromFragmentWorld(SourceTooltipRegistrationEvent event) {
        event.addItemTooltipEntry("gtceu:raw_red_garnet", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 19 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_yellow_garnet", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 19 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_amethyst", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 19 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_opal", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 19 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_basaltic_mineral_sand", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 20 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_granitic_mineral_sand", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 20 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_fullers_earth", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 20 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_gypsum", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 20 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_lazurite", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 17 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_sodalite", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 17 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_lapis", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 17 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_calcite", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 17 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_kyanite", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 18 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_mica", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 18 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_pollucite", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 18 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_coal", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 11, 15 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:raw_magnetite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 13, 16 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_venus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_io", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_enceladus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_vanadium_magnetite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 16 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("minecraft:raw_gold", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 16 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 9 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 9 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_bentonite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 13 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_venus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_io", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_enceladus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_olivine", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 13 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_venus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_io", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_enceladus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_glauconite_sand", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 13, 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_venus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_io", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_enceladus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:raw_almandine", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 14 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_pyrope", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 14 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_sapphire", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 14 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_green_sapphire", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 14 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_graphite", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 11 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_diamond", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 11 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:raw_garnierite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 12 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mercury", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_pluto", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_nickel", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 12 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mercury", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_pluto", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_cobaltite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 12 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mercury", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_pluto", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_pentlandite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 12, 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mercury", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_pluto", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:raw_cassiterite_sand", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 22 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_garnet_sand", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 22 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_asbestos", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 22 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:raw_diatomite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 22 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:raw_oilsands", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 23 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_rock_salt", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 21 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_salt", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 21 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_lepidolite", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 21 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_spodumene", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 21 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:raw_goethite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 9 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_yellow_limonite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 9 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_hematite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 9 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:raw_malachite", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_soapstone", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_talc", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_chalcopyrite", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5, 6 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("minecraft:raw_iron", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:raw_pyrite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_venus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 12 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_io", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("minecraft:raw_copper", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 8 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_pluto", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:raw_galena", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_silver", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_lead", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:raw_grossular", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:raw_spessartine", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:raw_pyrolusite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_tantalite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:raw_zeolite", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_cassiterite", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5, 8 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_realgar", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:raw_apatite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 10 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mars", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_enceladus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_tricalcium_phosphate", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 10 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mars", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_enceladus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:raw_tin", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 8 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:raw_redstone", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 9 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_ruby", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 9 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_cinnabar", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 9 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_nether_quartz", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 11 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_barite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2, 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_quartzite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2, 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 11, 7 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_beryllium", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_emerald", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_blue_topaz", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 10 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_topaz", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 10 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_sulfur", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_venus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 12 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_io", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_sphalerite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_venus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 12 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_io", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_stibnite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 8 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_pluto", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_saltpeter", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_electrotine", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_alunite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_certus_quartz", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_wulfenite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 8 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_molybdenite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 8 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_molybdenum", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 8 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_powellite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_reactor", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 8 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_uraninite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_moon", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1, 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_pluto", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1, 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_thorium", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_moon", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1, 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_pluto", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1, 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_plutonium", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_moon", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_io", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_pluto", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1, 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1, 4 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_bauxite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_moon", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_ilmenite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_moon", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_aluminium", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_moon", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_bastnasite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_moon", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_monazite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_moon", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_neodymium", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_moon", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_pitchblende", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_moon", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_pluto", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_bornite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mars", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 10 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_enceladus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_cooperite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mars", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_io", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_enceladus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4, 6 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_platinum", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mars", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_enceladus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_palladium", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mars", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_enceladus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_scheelite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mars", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_tungstate", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mars", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_lithium", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mars", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_pyrochlore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mars", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_enceladus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_magnesite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_venus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mercury", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 8 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_desh", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_venus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_cobalt", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mercury", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 8 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_calorite", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mercury", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 8 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:raw_tetrahedrite", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 8 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:raw_chalcocite", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 10 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:raw_ostrum", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 9 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_naquadah", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_io", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_pluto", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_end", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_trona", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_io", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_celestine", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_io", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 1 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_glacio", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:raw_zircon", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 2 ]", ChatFormatting.GRAY))));
    }

    private static void registerFluidTooltips(SourceTooltipRegistrationEvent event) {
        if (ConfigHolder.INSTANCE.enableSkyBlokeMode) fluidFromFragmentWorld(event);
        fluidFromSpaceDrilling(event);
    }

    private static void fluidFromFragmentWorld(SourceTooltipRegistrationEvent event) {
        event.addFluidTooltipEntry("gtceu:salt_water", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 24 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:oil_heavy", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 25 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:oil_medium", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 26 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:oil", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 27 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:oil_light", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 28 ]", ChatFormatting.GRAY)));

        event.addFluidTooltipEntries("gtceu:natural_gas", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_overworld", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 29 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 14 ]", ChatFormatting.GRAY))));

        event.addFluidTooltipEntry("minecraft:lava", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_nether", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 13 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:helium_3", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_moon", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:helium", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_moon", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)));

        event.addFluidTooltipEntries("gtceu:radon", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mars", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY))));

        event.addFluidTooltipEntry("gtceu:sulfuric_acid", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_venus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:deuterium", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_mercury", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 3 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:xenon", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 8 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:krypton", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:neon", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ceres", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:coal_gas", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_io", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:hydrochloric_acid", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_ganymede", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:nitric_acid", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_pluto", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:chlorine", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_enceladus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 4 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:fluorine", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_enceladus", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:benzene", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 5 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:methane", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 6 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:fluorine", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_titan", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 7 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:benzene", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 0 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:unknowwater", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.gtlcore.world_fragments_barnarda", ChatFormatting.GREEN), TooltipEntry.literalWithColor(" [ 8 ]", ChatFormatting.GRAY)));
    }

    private static void fluidFromSpaceDrilling(SourceTooltipRegistrationEvent event) {
        event.addFluidTooltipEntry("gtceu:hydrogen", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 1 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:helium", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 2 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:nitrogen", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 3 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:methane", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 4 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:sulfur_dioxide", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 5 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:carbon_dioxide", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 6 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:nitrogen_dioxide", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 7 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:ammonia", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 8 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:chlorine", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 9 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:fluorine", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 10 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:carbon_monoxide", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 11 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:oxygen", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 12 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:unknowwater", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk2", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 13 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:neon", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk2", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 14 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:argon", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk2", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 15 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:krypton", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk2", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 16 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:xenon", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk2", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 17 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:radon", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk2", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 18 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:helium_3", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk2", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 19 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:deuterium", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 20 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:tritium", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 21 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:heavy_fuel", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 22 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:light_fuel", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 23 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:naphtha", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 24 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:refinery_gas", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 25 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:coal_gas", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 26 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:bromine", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 27 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:barnarda_air", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 28 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:white_dwarf_mtter", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk6", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 29 ]", ChatFormatting.GRAY)));
        event.addFluidTooltipEntry("gtceu:black_dwarf_mtter", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk6", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 30 ]", ChatFormatting.GRAY)));
    }

    private static void registerSpaceMiningTooltips(SourceTooltipRegistrationEvent event) {
        event.addItemTooltipEntries("gtceu:redstone_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 5, 17 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 27 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:ruby_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 5, 17 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:cinnabar_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 6, 17 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:nether_quartz_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 17 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:quartzite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 13, 17 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("minecraft:ancient_debris", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 17 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:lazurite_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 10 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk6", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 32 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:sapphire_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk6", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 32 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:starmetal_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk6", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 32 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:green_sapphire_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk6", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 32 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:yellow_garnet_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 14 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk6", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 32 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:pollucite_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 12 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk6", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 32 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:goethite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 3, 12 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:yellow_limonite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 3, 12 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:kyanite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 12 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:mica_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 12 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:bauxite_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 12, 20 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk5", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 30 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:chalcopyrite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 6, 8 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:zeolite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 6 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:cassiterite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 6, 13 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:realgar_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 6 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("ae2:sky_stone_block", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 6 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:apatite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 18 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:tricalcium_phosphate_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 18 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:pyrochlore_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 18 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:sulfur_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 18 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:pyrite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 8, 18 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:sphalerite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 18 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:silver_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 11 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk5", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 31 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:andesite_platinum_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk5", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 31 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:tartarite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk5", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 31 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:vibranium_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk5", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 31 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:aluminium_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 20 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk5", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 31 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:iron_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 8 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 26 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk5", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 31 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:saltpeter_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 7 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:diatomite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 7, 19 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:electrotine_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 7 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:alunite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 7 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:coal_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 7, 21 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:rubidium_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 7 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:galena_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 11 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:lead_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 11 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:molybdenite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 11 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:molybdenum_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 3, 11 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:powellite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 11 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:tetrahedrite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 1 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:copper_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 1, 8 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:bentonite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 1 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:magnetite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 1, 9, 19 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:olivine_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 1 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:glauconite_sand_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 1, 4 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:sodalite_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 10 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk5", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 30 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:lapis_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 10 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk5", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 30 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:calcite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 10 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:wulfenite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 10 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:calorite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 10 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:blue_topaz_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 15 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:topaz_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 15 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk2", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 25 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:basaltic_mineral_sand_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 15 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:granitic_mineral_sand_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 15 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:fullers_earth_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 15 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:gypsum_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 15 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:grossular_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 5, 9 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:pyrolusite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 5, 9 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:tantalite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 5, 9 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:vanadium_magnetite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 9, 19 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:gold_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 9, 13, 20 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:barite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 14 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:red_garnet_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 14 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk2", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 25 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 27 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:amethyst_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 14 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk2", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 25 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:opal_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 14 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:alien_algae_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 14 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:cassiterite_sand_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 19 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:garnet_sand_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 19 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:asbestos_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 19 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:beryllium_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 8 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:emerald_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 8 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk2", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 25 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:almandine_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 27 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:pyrope_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 27 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:stibnite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 2 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:uraninite_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 2 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk4", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 29 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:rock_salt_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 16 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:salt_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 16 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk4", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 29 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:lepidolite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 16 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:spodumene_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 16 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:chalcocite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 16 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:bornite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 16, 21 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:certus_quartz_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 13 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:zircon_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 13 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:hematite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 3, 13 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:enderium_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk5", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 30 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:celestine_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk2", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 25 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk5", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 30 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:pitchblende_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 23 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk5", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 30 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntries("gtceu:bastnasite_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 3 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk4", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 28 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:malachite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 3 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:soapstone_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 4 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:talc_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 4 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:pentlandite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 4, 22 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:neodymium_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 4 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:monazite_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 4 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk4", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 28 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:spessartine_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 5 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:earth_crystal_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk4", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 29 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:ignis_crystal_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk4", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 29 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:orichalcum_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk4", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 29 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:mithril_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk4", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 29 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:naquadah_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 24 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk4", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 28 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:adamantine_compounds_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk4", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 28 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:rare_earth_metal_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk4", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 28 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:enriched_naquadah_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 24 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk4", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 28 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:bloodstone_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 27 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:gravel_ruby_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 27 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:tin_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 26 ]", ChatFormatting.GRAY)));

        event.addItemTooltipEntries("gtceu:nickel_ore", List.of(
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 22 ]", ChatFormatting.GRAY)),
                TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 26 ]", ChatFormatting.GRAY))));

        event.addItemTooltipEntry("gtceu:uruium_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 26 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:force_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 26 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:cobalt_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk3", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 26 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:jasper_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk2", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 25 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:chromite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 24 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:plutonium_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 24 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:trinium_compound_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 24 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:indium_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 24 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:scheelite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 23 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:tungstate_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 23 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:lithium_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 23 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:tellurium_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 23 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:tungsten_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 23 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:garnierite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 22 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:cobaltite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 22 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:platinum_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 22 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:palladium_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 22 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:cooperite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 21 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:graphite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 21 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:diamond_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 21 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:titanium_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 21 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:oilsands_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 20 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:infused_gold_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 20 ]", ChatFormatting.GRAY)));
        event.addItemTooltipEntry("gtceu:ilmenite_ore", TooltipEntry.combined(TooltipEntry.translatableWithColor("item.kubejs.space_drone_mk1", ChatFormatting.AQUA), TooltipEntry.literalWithColor("+ [ 20 ]", ChatFormatting.GRAY)));
    }

    private static void registerRareEarthTooltips(SourceTooltipRegistrationEvent event) {
        String[] materials = {
                "lanthanum", "cerium", "neodymium", "promethium", "samarium", "europium",
                "praseodymium", "gadolinium", "terbium", "dysprosium", "holmium", "erbium",
                "thulium", "ytterbium", "scandium", "lutetium", "yttrium"
        };

        for (String material : materials) {
            event.addItemTooltipEntry("gtceu:small_" + material + "_dust", TooltipEntry.translatableWithColor("gtceu.rare_earth_centrifugal", ChatFormatting.LIGHT_PURPLE));
            event.addItemTooltipEntry("gtceu:" + material + "_dust", TooltipEntry.translatableWithColor("gtceu.rare_earth_centrifugal", ChatFormatting.LIGHT_PURPLE));
            event.addItemTooltipEntry("gtceu:small_" + material + "_oxide_dust", TooltipEntry.translatableWithColor("gtceu.rare_earth_centrifugal", ChatFormatting.LIGHT_PURPLE));
            event.addItemTooltipEntry("gtceu:" + material + "_oxide_dust", TooltipEntry.translatableWithColor("gtceu.rare_earth_centrifugal", ChatFormatting.LIGHT_PURPLE));
        }
    }
}
