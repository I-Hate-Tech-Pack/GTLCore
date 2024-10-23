package org.gtlcore.gtlcore.common.machine.multiblock;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.feature.IMachineModifyDrops;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.pattern.BlockPattern;

import com.lowdragmc.lowdraglib.gui.widget.SlotWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author EasterFG on 2024/10/22
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SimulationMachine extends WorkableElectricMultiblockMachine implements IMachineModifyDrops {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            SimulationMachine.class, WorkableElectricMultiblockMachine.MANAGED_FIELD_HOLDER);

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Persisted
    protected NotifiableItemStackHandler inventory;

    public SimulationMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
        this.inventory = createMachineStorage();
    }

    @Override
    public boolean checkPattern() {
        return false;
    }

    @Override
    public void asyncCheckPattern(long periodID) {
        // nothing
    }

    @Override
    public BlockPattern getPattern() {
        if (inventory.isEmpty()) {
            return super.getPattern();
        }
        ItemStack stack = inventory.storage.getStackInSlot(0);
        if (stack.getItem() instanceof MetaMachineItem item) {
            if (item.getDefinition() instanceof MultiblockMachineDefinition definition) {
                return definition.getPatternFactory().get();
            }
        }
        return super.getPattern();
    }

    @Override
    public boolean isFormed() {
        return false;
    }

    protected NotifiableItemStackHandler createMachineStorage() {
        var handler = new NotifiableItemStackHandler(this, 1, IO.NONE, IO.BOTH, slots -> new ItemStackTransfer(1) {

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        });
        handler.setFilter(slot -> {
            if (slot.getItem() instanceof MetaMachineItem item) {
                return item.getDefinition() instanceof MultiblockMachineDefinition;
            }
            return false;
        });
        return handler;
    }

    @Override
    public void onDrops(List<ItemStack> drops) {
        clearInventory(inventory.storage);
    }

    @Override
    public Widget createUIWidget() {
        var widget = super.createUIWidget();
        if (widget instanceof WidgetGroup group) {
            var size = group.getSize();
            group.addWidget(
                    new SlotWidget(inventory.storage, 0, size.width - 30, size.height - 30, true, true)
                            .setBackground(GuiTextures.SLOT));
        }
        return widget;
    }
}
