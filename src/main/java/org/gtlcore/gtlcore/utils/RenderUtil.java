package org.gtlcore.gtlcore.utils;

import org.gtlcore.gtlcore.mixin.mc.BufferBuilderAccessor;

import com.mojang.blaze3d.vertex.BufferBuilder;

public class RenderUtil {

    private static final int MAX_BATCH_BYTES = 1536 * 1024 * 1024; // 1.5GB
    private static final int MAX_VERTICES_PER_BATCH = 2_097_152;

    public static int getBufferByteSize(BufferBuilder builder) {
        return ((BufferBuilderAccessor) builder).getNextElementByte();
    }

    public static boolean shouldStartNewBatch(BufferBuilder builder, int currentVertexCount, int estimatedAdditionalVertices) {
        int byteSize = getBufferByteSize(builder);

        if (byteSize >= 0) {
            int estimatedBytes = estimatedAdditionalVertices * 40; // padding
            return byteSize + estimatedBytes > MAX_BATCH_BYTES;
        }

        // Fallback check: vertex count estimation
        return currentVertexCount + estimatedAdditionalVertices > MAX_VERTICES_PER_BATCH;
    }
}
