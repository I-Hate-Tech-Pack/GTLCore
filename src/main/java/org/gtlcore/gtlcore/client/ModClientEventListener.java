package org.gtlcore.gtlcore.client;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.api.event.SourceTooltipRegistrationEvent;
import org.gtlcore.gtlcore.common.data.GTLSourceTooltips;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GTLCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class ModClientEventListener {

    @SubscribeEvent
    public static void onSourceTooltipRegistration(SourceTooltipRegistrationEvent event) {
        GTLSourceTooltips.init(event);
    }
}
