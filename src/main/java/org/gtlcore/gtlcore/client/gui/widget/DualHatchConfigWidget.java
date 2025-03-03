package org.gtlcore.gtlcore.client.gui.widget;

import org.gtlcore.gtlcore.GTLCore;

import com.gregtechceu.gtceu.integration.ae2.gui.widget.AEItemConfigWidget;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.ConfigWidget;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.slot.AEFluidConfigSlotWidget;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.slot.AEItemConfigSlotWidget;
import com.gregtechceu.gtceu.integration.ae2.slot.*;

import appeng.api.stacks.GenericStack;

public class DualHatchConfigWidget extends AEItemConfigWidget {

    private final ExportOnlyAEItemList itemList;
    private final ExportOnlyAEFluidList fluidList;

    public DualHatchConfigWidget(int x, int y, ExportOnlyAEItemList itemList, ExportOnlyAEFluidList fluidList) {
        super(x, y, itemList);
        this.itemList = itemList;
        this.fluidList = fluidList;
        try {
            var field = ConfigWidget.class.getDeclaredField("config");
            field.setAccessible(true);
            field.set(this, merge());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            GTLCore.LOGGER.error("cant create DualHatchConfigWidget", e);
        }
        this.init();
    }

    protected IConfigurableSlot[] merge() {
        IConfigurableSlot[] result = new IConfigurableSlot[16];
        System.arraycopy(itemList.getInventory(), 0, result, 0, 8);
        System.arraycopy(fluidList.getInventory(), 0, result, 8, 8);
        return result;
    }

    protected void init() {
        this.displayList = new IConfigurableSlot[this.config.length];
        this.cached = new IConfigurableSlot[this.config.length];
        // reset widget
        this.clearAllWidgets();
        for (int index = 0; index < 16; index++) {
            int line = index / 8;
            if (line == 0) {
                this.displayList[index] = new ExportOnlyAEItemSlot();
                this.cached[index] = new ExportOnlyAEItemSlot();
                this.addWidget(index, new AEItemConfigSlotWidget(index * 18, 0, this, index));
            } else {
                this.displayList[index] = new ExportOnlyAEFluidSlot();
                this.cached[index] = new ExportOnlyAEFluidSlot();
                this.addWidget(index, new AEFluidConfigSlotWidget((index - 8) * 18, 18 * 2 + 2, this, index));
            }
        }
    }

    @Override
    public boolean hasStackInConfig(GenericStack stack) {
        return itemList.hasStackInConfig(stack, true) ||
                fluidList.hasStackInConfig(stack, true);
    }

    @Override
    public boolean isAutoPull() {
        return false;
    }
}
