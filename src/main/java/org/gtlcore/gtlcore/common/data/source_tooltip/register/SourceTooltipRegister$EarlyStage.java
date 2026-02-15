package org.gtlcore.gtlcore.common.data.source_tooltip.register;

import org.gtlcore.gtlcore.api.event.SourceTooltipRegistrationEvent;
import org.gtlcore.gtlcore.common.data.source_tooltip.SpecialComponent;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class SourceTooltipRegister$EarlyStage {

    public static void register(SourceTooltipRegistrationEvent event) {
        register_dust(event);
        register_ingot(event);
    }

    private static final Component component_chemical_bath = SpecialComponent.create(Component.translatable("gtceu.chemical_bath").withStyle(SpecialComponent.color_recipe_type), SpecialComponent.component_early_stage);

    private static void register_ingot(SourceTooltipRegistrationEvent event) {
        String[] items = { "gtceu:silicon_ingot", "gtceu:kanthal_ingot" };
        for (var item : items) {
            var tooltip = event.register$item(item);
            if (tooltip == null) continue;
            tooltip.get_or_create$control(SpecialComponent.component_default$control)
                    .add(component_chemical_bath);
        }
    }

    private static void register_dust(SourceTooltipRegistrationEvent event) {
        {
            var tooltip = event.register$item("gtceu:plutonium_241_dust");
            if (tooltip != null)
                tooltip.get_or_create$control(SpecialComponent.component_default$control)
                        .add(SpecialComponent.create(Component.translatable("gtceu.centrifuge").withStyle(SpecialComponent.color_recipe_type), SpecialComponent.component_early_stage));
        }
        {
            var tooltip = event.register$item("gtceu:iodine_dust");
            if (tooltip != null)
                tooltip.get_or_create$control(SpecialComponent.component_default$control)
                        .add(SpecialComponent.create(Component.translatable("gtceu.centrifuge").withStyle(SpecialComponent.color_recipe_type), SpecialComponent.component_early_stage));
        }
        String[] a = { "gtceu:neptunium_dust", "gtceu:protactinium_dust", "gtceu:polonium_dust" };
        var component_a = SpecialComponent.create(Component.translatable("gtlcore.source_tooltip.productionline.nuclear_waste").withStyle(ChatFormatting.GOLD), SpecialComponent.component_early_stage);
        for (var item : a) {
            var tooltip = event.register$item(item);
            if (tooltip == null) continue;
            tooltip.get_or_create$control(SpecialComponent.component_default$control)
                    .add(component_a);
        }

        String[] b = { "gtceu:palladium_dust", "gtceu:ruthenium_dust", "gtceu:iridium_dust", "gtceu:rhodium_dust", "gtceu:osmium_dust" };
        var component_b = SpecialComponent.create(Component.translatable("gtlcore.source_tooltip.series.platinum_group_precess").withStyle(SpecialComponent.color_recipe_type), SpecialComponent.component_early_stage);
        for (var item : b) {
            var tooltip = event.register$item(item);
            if (tooltip == null) continue;
            tooltip.get_or_create$control(SpecialComponent.component_default$control)
                    .add(component_b);
        }
    }
}
