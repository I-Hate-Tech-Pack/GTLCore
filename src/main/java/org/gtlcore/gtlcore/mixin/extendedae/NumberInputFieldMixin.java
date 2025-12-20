package org.gtlcore.gtlcore.mixin.extendedae;

import org.gtlcore.gtlcore.mixin.ae2.gui.NumberEntryWidgetAccessor;

import appeng.client.gui.NumberEntryType;
import appeng.client.gui.style.ScreenStyle;
import appeng.client.gui.widgets.NumberEntryWidget;
import com.glodblock.github.extendedae.client.gui.widget.NumberInputField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NumberInputField.class)
public abstract class NumberInputFieldMixin extends NumberEntryWidget {

    public NumberInputFieldMixin(ScreenStyle style, NumberEntryType type) {
        super(style, type);
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void changeMaxLength(ScreenStyle style, NumberEntryType type, CallbackInfo ci) {
        ((NumberEntryWidgetAccessor) this).getTextField().setMaxLength(19);
    }

    @Override
    public void setType(NumberEntryType type) {
        super.setType(type);
        ((NumberEntryWidgetAccessor) this).getTextField().setMaxLength(type.amountPerUnit() == 1 ? 19 : 16);
    }
}
