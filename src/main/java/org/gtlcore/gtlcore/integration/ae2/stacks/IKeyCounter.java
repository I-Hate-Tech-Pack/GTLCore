package org.gtlcore.gtlcore.integration.ae2.stacks;

import appeng.api.stacks.KeyCounter;
import it.unimi.dsi.fastutil.objects.*;

/**
 * 代码参考自gto
 * &#064;line <a href="https://github.com/GregTech-Odyssey/GTOCore">...</a>
 */

public interface IKeyCounter {

    Object2ObjectOpenHashMap<Object, VariantCounter> getLists();

    VariantCounter getVariantCounter();

    void removeEmptySubmaps();

    static IKeyCounter of(KeyCounter keyCounter) {
        return (IKeyCounter) (Object) keyCounter;
    }
}
