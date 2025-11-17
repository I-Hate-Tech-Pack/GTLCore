package org.gtlcore.gtlcore.api.pattern;

import com.gregtechceu.gtceu.api.pattern.MultiblockState;

import net.minecraft.world.level.ChunkPos;

import java.util.Set;

@FunctionalInterface
public interface INewMultiblockWorldSavedData {

    Set<MultiblockState> getControllersInChunk(ChunkPos chunkPos);
}
