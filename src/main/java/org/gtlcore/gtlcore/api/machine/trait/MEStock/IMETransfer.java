package org.gtlcore.gtlcore.api.machine.trait.MEStock;

import appeng.api.stacks.GenericStack;
import org.jetbrains.annotations.Nullable;

public interface IMETransfer {

    @Nullable
    GenericStack extractGenericStack(long amount, boolean simulate, boolean notifyChanges);
}
