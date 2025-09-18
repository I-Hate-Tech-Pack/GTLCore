package org.gtlcore.gtlcore.mixin.ae2.stacks;

import appeng.api.stacks.AEKey;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import org.spongepowered.asm.mixin.Mixin;

import static it.unimi.dsi.fastutil.HashCommon.arraySize;

@Mixin(targets = "appeng.api.stacks.AEKey2LongMap$OpenHashMap", remap = false)
public abstract class AEKey2LongMapMixin extends Object2LongOpenHashMap<AEKey> {

    @Override
    public long addTo(AEKey k, long incr) {
        if (incr == 0) return defRetValue;
        int pos;
        if (k == null) {
            if (containsNullKey) return addToValue(n, incr);
            pos = n;
            containsNullKey = true;
        } else {
            AEKey curr;
            var key = this.key;
            // The starting point.
            if (!((curr = key[pos = (HashCommon.mix(k.hashCode())) & mask]) == null)) {
                if (curr.equals(k)) return addToValue(pos, incr);
                while (!((curr = key[pos = (pos + 1) & mask]) == null)) if (curr.equals(k)) return addToValue(pos, incr);
            }
        }
        key[pos] = k;
        value[pos] = defRetValue + incr;
        if (size++ >= maxFill) rehash(arraySize(size + 1, f));
        return defRetValue;
    }

    private long addToValue(int pos, long incr) {
        long oldValue = value[pos];
        long newValue = oldValue + incr;

        // Check for overflow using bitwise operations (faster than bounds checking)
        // Overflow occurs when operands have same sign but result has different sign
        if (((oldValue ^ newValue) & (incr ^ newValue)) < 0) {
            newValue = incr > 0 ? Long.MAX_VALUE : Long.MIN_VALUE;
        }

        value[pos] = newValue;
        return oldValue;
    }
}
