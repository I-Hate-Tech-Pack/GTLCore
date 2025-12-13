package org.gtlcore.gtlcore.api.gui;

import com.gregtechceu.gtceu.api.gui.fancy.IFancyConfigurator;
import com.gregtechceu.gtceu.api.gui.widget.IntInputWidget;
import com.gregtechceu.gtceu.common.data.GTItems;

import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.gui.texture.ItemStackTexture;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

import net.minecraft.network.chat.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AdvancedMEConfigurator implements IFancyConfigurator {

    private final Consumer<Integer> consumer;
    private final Supplier<Integer> offset;

    public AdvancedMEConfigurator(Consumer<Integer> consumer, Supplier<Integer> offset) {
        this.consumer = consumer;
        this.offset = offset;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gtceu.gui.adv_me_config.title");
    }

    @Override
    public IGuiTexture getIcon() {
        return new ItemStackTexture(GTItems.TOOL_DATA_STICK.asStack());
    }

    @Override
    public Widget createConfigurator() {
        var group = new WidgetGroup(0, 0, 90, 40);

        group.addWidget(new LabelWidget(4, 2, "gtceu.gui.title.adv_me_config.ticks_per_cycle"));
        group.addWidget(new IntInputWidget(4, 12, 81, 14, offset,
                consumer).setMin(0)
                .setHoverTooltips(Component.translatable("gtceu.gui.adv_me_config.ticks_per_cycle")));

        return group;
    }
}
