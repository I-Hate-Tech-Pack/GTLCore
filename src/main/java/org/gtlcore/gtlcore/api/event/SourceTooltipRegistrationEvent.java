package org.gtlcore.gtlcore.api.event;

import org.gtlcore.gtlcore.common.data.source_tooltip.SourceTooltip;
import org.gtlcore.gtlcore.utils.Registries;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

@OnlyIn(Dist.CLIENT)
public class SourceTooltipRegistrationEvent extends Event implements IModBusEvent {

    public SourceTooltip register$item(String id) {
        var item = Registries.getItem(id);
        if (item == null) return null;
        return SourceTooltip.item_source_tooltips.computeIfAbsent(item, k -> new SourceTooltip());
    }

    public SourceTooltip register$fluid(String id) {
        var fluid = Registries.getFluid(id);
        if (fluid == null) return null;
        return SourceTooltip.fluid_source_tooltips.computeIfAbsent(fluid, k -> new SourceTooltip());
    }

    public SourceTooltip register(Item item) {
        if (item == null) return null;
        return SourceTooltip.item_source_tooltips.computeIfAbsent(item, k -> new SourceTooltip());
    }

    public SourceTooltip register(Fluid fluid) {
        if (fluid == null) return null;
        return SourceTooltip.fluid_source_tooltips.computeIfAbsent(fluid, k -> new SourceTooltip());
    }
}
