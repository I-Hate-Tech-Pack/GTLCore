package org.gtlcore.gtlcore.network;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.network.packet.SStructureDetectHighight;

import com.glodblock.github.glodium.network.NetworkHandler;

public class GTLNetworkHandler extends NetworkHandler {

    public static final GTLNetworkHandler INSTANCE = new GTLNetworkHandler();

    public GTLNetworkHandler() {
        super(GTLCore.MOD_ID);
    }

    public void init() {
        registerPacket(SStructureDetectHighight.class, SStructureDetectHighight::new);
    }
}
