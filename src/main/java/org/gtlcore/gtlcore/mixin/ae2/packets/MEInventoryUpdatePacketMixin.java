package org.gtlcore.gtlcore.mixin.ae2.packets;

import appeng.core.AELog;
import appeng.core.sync.packets.MEInventoryUpdatePacket;
import appeng.menu.me.common.GridInventoryEntry;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.gtlcore.gtlcore.integration.ae2.common.IConfirmStartMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mixin(MEInventoryUpdatePacket.class)
public class MEInventoryUpdatePacketMixin {
    @Shadow(remap = false)
    private int containerId;

    @Shadow(remap = false)
    private boolean fullUpdate;

    @Final
    @Shadow(remap = false)
    private List<GridInventoryEntry> entries;

    @Inject(method = "clientPacketData(Lnet/minecraft/world/entity/player/Player;)V", at = @At("HEAD"), remap = false, cancellable = true)
    private void clientPacketData(Player player, CallbackInfo ci) {
        if (player.containerMenu.containerId != containerId) return;
        if (!(player.containerMenu instanceof IConfirmStartMenu menu)) return;
        var clientRepo = menu.gtlcore$getClientRepo();
        if (clientRepo == null) {
            AELog.info("Ignoring ME inventory update packet because no client repo is available.");
            return;
        }
        clientRepo.handleUpdate(fullUpdate, entries);
        ci.cancel();
    }
}
