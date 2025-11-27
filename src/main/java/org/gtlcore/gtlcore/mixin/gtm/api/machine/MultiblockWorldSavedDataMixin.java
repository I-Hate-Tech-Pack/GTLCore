package org.gtlcore.gtlcore.mixin.gtm.api.machine;

import org.gtlcore.gtlcore.api.pattern.INewMultiblockWorldSavedData;

import com.gregtechceu.gtceu.api.pattern.MultiblockState;
import com.gregtechceu.gtceu.api.pattern.MultiblockWorldSavedData;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

import org.spongepowered.asm.mixin.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Implements(@Interface(
                       iface = INewMultiblockWorldSavedData.class,
                       prefix = "gTLCore$"))
@Mixin(MultiblockWorldSavedData.class)
public abstract class MultiblockWorldSavedDataMixin {

    @Shadow(remap = false)
    @Final
    public Map<BlockPos, MultiblockState> mapping;
    @Shadow(remap = false)
    @Final
    public Map<ChunkPos, Set<MultiblockState>> chunkPosMapping;

    /**
     * @author screret
     * @reason Performance and thread-safety
     */
    @Overwrite(remap = false)
    public void addMapping(MultiblockState state) {
        this.mapping.put(state.controllerPos, state);
        for (BlockPos blockPos : state.getCache()) {
            chunkPosMapping.computeIfAbsent(new ChunkPos(blockPos), c -> ConcurrentHashMap.newKeySet()).add(state);
        }
    }

    /**
     * @author screret
     * @reason Performance
     */
    @Overwrite(remap = false)
    public void removeMapping(MultiblockState state) {
        this.mapping.remove(state.controllerPos);
        for (Set<MultiblockState> set : chunkPosMapping.values()) {
            set.remove(state);
        }
    }

    /**
     * @author screret
     * @reason Performance
     */
    @Overwrite(remap = false)
    public MultiblockState[] getControllerInChunk(ChunkPos chunkPos) {
        return new MultiblockState[0];
    }

    @Unique
    public Set<MultiblockState> gTLCore$getControllersInChunk(ChunkPos chunkPos) {
        return chunkPosMapping.getOrDefault(chunkPos, Collections.emptySet());
    }
}
