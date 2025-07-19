package org.gtlcore.gtlcore.api.recipe;

import net.minecraft.resources.ResourceLocation;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public interface IGTRecipeEUTier {

    default Object2IntOpenHashMap<ResourceLocation> getRecipeTierMap() {
        return null;
    }

    default void setRecipeTierMap(ResourceLocation recipe, int tier) {}
}
