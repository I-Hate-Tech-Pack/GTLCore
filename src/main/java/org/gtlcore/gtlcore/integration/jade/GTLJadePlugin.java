package org.gtlcore.gtlcore.integration.jade;

import org.gtlcore.gtlcore.integration.jade.provider.WirelessOpticalDataHatchProvide;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

/**
 * @author EasterFG on 2024/10/27
 */
@WailaPlugin
public class GTLJadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(new WirelessOpticalDataHatchProvide(),
                BlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(new WirelessOpticalDataHatchProvide(),
                Block.class);
    }
}
