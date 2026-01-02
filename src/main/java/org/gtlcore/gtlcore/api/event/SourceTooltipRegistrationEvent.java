package org.gtlcore.gtlcore.api.event;

import org.gtlcore.gtlcore.utils.GTLSourceTooltipHelper;

import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SourceTooltipRegistrationEvent extends Event implements IModBusEvent {

    public void addItemTooltip(String id, Component... components) {
        GTLSourceTooltipHelper.addItemTooltip(id, List.of(components));
    }

    public void addFluidTooltip(String id, Component... components) {
        GTLSourceTooltipHelper.addFluidTooltip(id, List.of(components));
    }
}
