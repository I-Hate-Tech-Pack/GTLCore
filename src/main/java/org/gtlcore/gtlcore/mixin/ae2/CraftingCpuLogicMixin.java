package org.gtlcore.gtlcore.mixin.ae2;

import net.minecraft.world.level.Level;

import appeng.api.networking.energy.IEnergyService;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.GenericStack;
import appeng.crafting.execution.CraftingCpuLogic;
import appeng.crafting.execution.ExecutingCraftingJob;
import appeng.crafting.inv.ListCraftingInventory;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import appeng.me.service.CraftingService;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ CraftingCpuLogic.class })
public abstract class CraftingCpuLogicMixin {

    @Shadow(remap = false)
    @Final
    CraftingCPUCluster cluster;
    @Shadow(remap = false)
    private boolean cantStoreItems;
    @Shadow(remap = false)
    private ExecutingCraftingJob job;
    @Shadow(remap = false)
    @Final
    private ListCraftingInventory inventory;
    @Shadow(remap = false)
    @Final
    private int[] usedOps;

    @Shadow(remap = false)
    public abstract void storeItems();

    @Shadow(remap = false)
    public abstract void cancel();

    @Shadow(remap = false)
    public abstract int executeCrafting(int maxPatterns, CraftingService craftingService, IEnergyService energyService, Level level);

    @Shadow(remap = false)
    public abstract @Nullable GenericStack getFinalJobOutput();

    @Shadow(remap = false)
    protected abstract void finishJob(boolean success);

    @Shadow(remap = false)
    public abstract long getWaitingFor(AEKey template);

    /**
     * @author .
     * @reason .
     */
    @Overwrite(
               remap = false)
    public void tickCraftingLogic(IEnergyService eg, CraftingService cc) {
        if (this.cluster.isActive()) {
            this.cantStoreItems = false;
            if (this.job == null) {
                this.storeItems();
                if (!this.inventory.list.isEmpty()) {
                    this.cantStoreItems = true;
                }
            } else if (((ExecutingCraftingJobAccessor) this.job).getLink().isCanceled()) {
                this.cancel();
            } else {
                int remainingOperations = Math.min(256, this.cluster.getCoProcessors() + 1 - (this.usedOps[0] + this.usedOps[1] + this.usedOps[2]));
                int started;
                int pushedPatterns;
                for (started = remainingOperations; remainingOperations > 0; remainingOperations -= pushedPatterns) {
                    pushedPatterns = this.executeCrafting(remainingOperations, cc, eg, this.cluster.getLevel());
                    if (pushedPatterns <= 0) {
                        GenericStack stack = this.getFinalJobOutput();
                        if (stack != null) {
                            if (stack.what() instanceof AEItemKey itemKey)
                                if (this.getWaitingFor(itemKey) > 0L) this.finishJob(true);
                        }
                        break;
                    }
                }
                this.usedOps[2] = this.usedOps[1];
                this.usedOps[1] = this.usedOps[0];
                this.usedOps[0] = started - remainingOperations;
            }
        }
    }
}
