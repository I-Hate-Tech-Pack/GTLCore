package org.gtlcore.gtlcore.api.data;

import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

/**
 * 代码参考自gto
 * &#064;line <a href="https://github.com/GregTech-Odyssey/GTOCore">...</a>
 */

public interface IFluid {

    @NotNull
    ResourceLocation getResourceLocation();

    String getIdString();
}
