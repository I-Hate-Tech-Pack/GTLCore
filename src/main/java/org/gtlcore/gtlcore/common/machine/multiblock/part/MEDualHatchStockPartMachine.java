package org.gtlcore.gtlcore.common.machine.multiblock.part;

import org.gtlcore.gtlcore.client.gui.widget.DualHatchConfigWidget;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableFluidTank;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour;
import com.gregtechceu.gtceu.integration.ae2.machine.MEInputBusPartMachine;
import com.gregtechceu.gtceu.integration.ae2.slot.*;
import com.gregtechceu.gtceu.integration.ae2.utils.AEUtil;

import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import com.lowdragmc.lowdraglib.utils.Position;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import appeng.api.config.Actionable;
import appeng.api.networking.IGrid;
import appeng.api.stacks.AEFluidKey;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.GenericStack;
import appeng.api.storage.MEStorage;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author EasterFG on 2025/3/2
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MEDualHatchStockPartMachine extends MEInputBusPartMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(MEDualHatchStockPartMachine.class,
            MEInputBusPartMachine.MANAGED_FIELD_HOLDER);

    protected ExportOnlyAEItemList aeItemHandler;

    protected ExportOnlyAEFluidList aeFluidHandler;

    @Persisted
    protected NotifiableFluidTank fluidTank;

    public MEDualHatchStockPartMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
        fluidTank = createTank();
    }

    @Override
    protected NotifiableItemStackHandler createInventory(Object... args) {
        this.aeItemHandler = new ExportOnlyAEStockingItemList(this, 16);
        return this.aeItemHandler;
    }

    protected NotifiableFluidTank createTank() {
        this.aeFluidHandler = new ExportOnlyAEStockingFluidList(this, 8);
        return this.aeFluidHandler;
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    protected void flushInventory() {
        // no-op
    }

    @Override
    protected void syncME() {
        IGrid grid = this.getMainNode().getGrid();
        if (grid == null) {
            return;
        }
        MEStorage networkInv = grid.getStorageService().getInventory();
        ExportOnlyAEItemSlot[] aeItem = aeItemHandler.getInventory();
        ExportOnlyAEFluidSlot[] aeFluid = aeFluidHandler.getInventory();
        for (int i = 0; i < CONFIG_SIZE; i++) {
            var slot = i < 8 ? aeItem[i] : aeFluid[i - 8];
            var config = slot.getConfig();
            if (config != null) {
                var key = config.what();
                long extracted = networkInv.extract(key, i < 8 ? Integer.MAX_VALUE : Long.MAX_VALUE,
                        Actionable.SIMULATE, actionSource);
                if (extracted > 0) {
                    slot.setStock(new GenericStack(key, extracted));
                    continue;
                }
            }
            slot.setStock(null);
        }
    }

    @Override
    public Widget createUIWidget() {
        WidgetGroup group = new WidgetGroup(new Position(0, 0));
        // ME Network status
        group.addWidget(new LabelWidget(3, 0, () -> this.isOnline ?
                "gtceu.gui.me_network.online" :
                "gtceu.gui.me_network.offline"));

        // Config slots
        group.addWidget(new DualHatchConfigWidget(3, 10, this.aeItemHandler, this.aeFluidHandler));

        return group;
    }

    protected CompoundTag writeConfigToTag() {
        CompoundTag tag = new CompoundTag();
        tag.putByte("GhostCircuit",
                (byte) IntCircuitBehaviour.getCircuitConfiguration(circuitInventory.getStackInSlot(0)));
        return tag;
    }

    protected void readConfigFromTag(CompoundTag tag) {
        if (tag.contains("GhostCircuit")) {
            circuitInventory.setStackInSlot(0, IntCircuitBehaviour.stack(tag.getByte("GhostCircuit")));
        }
    }

    private class ExportOnlyAEStockingItemList extends ExportOnlyAEItemList {

        public ExportOnlyAEStockingItemList(MetaMachine holder, int slots) {
            super(holder, slots, ExportOnlyAEStockingItemSlot::new);
        }

        @Override
        public boolean isStocking() {
            return true;
        }
    }

    private class ExportOnlyAEStockingItemSlot extends ExportOnlyAEItemSlot {

        public ExportOnlyAEStockingItemSlot() {
            super();
        }

        public ExportOnlyAEStockingItemSlot(@Nullable GenericStack config, @Nullable GenericStack stock) {
            super(config, stock);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate, boolean notifyChanges) {
            if (slot == 0 && this.stock != null) {
                if (this.config != null) {
                    // Extract the items from the real net to either validate (simulate)
                    // or extract (modulate) when this is called
                    if (!isOnline()) return ItemStack.EMPTY;
                    MEStorage aeNetwork = getMainNode().getGrid().getStorageService().getInventory();
                    Actionable action = simulate ? Actionable.SIMULATE : Actionable.MODULATE;
                    var key = config.what();
                    long extracted = aeNetwork.extract(key, amount, action, actionSource);
                    if (extracted > 0) {
                        ItemStack resultStack = key instanceof AEItemKey itemKey ? itemKey.toStack((int) extracted) : ItemStack.EMPTY;
                        if (!simulate) {
                            // may as well update the display here
                            this.stock = ExportOnlyAESlot.copy(stock, stock.amount() - extracted);
                            if (this.stock.amount() == 0) {
                                this.stock = null;
                            }
                            if (notifyChanges && this.onContentsChanged != null) {
                                this.onContentsChanged.run();
                            }
                        }
                        return resultStack;
                    }
                }
            }
            return ItemStack.EMPTY;
        }

        @Override
        public ExportOnlyAEStockingItemSlot copy() {
            return new ExportOnlyAEStockingItemSlot(this.config == null ? null : copy(this.config), this.stock == null ? null : copy(this.stock));
        }
    }

    private class ExportOnlyAEStockingFluidList extends ExportOnlyAEFluidList {

        public ExportOnlyAEStockingFluidList(MetaMachine holder, int slots) {
            super(holder, slots, ExportOnlyAEStockingFluidSlot::new);
        }

        @Override
        public boolean isStocking() {
            return true;
        }
    }

    private class ExportOnlyAEStockingFluidSlot extends ExportOnlyAEFluidSlot {

        public ExportOnlyAEStockingFluidSlot() {
            super();
        }

        public ExportOnlyAEStockingFluidSlot(@Nullable GenericStack config, @Nullable GenericStack stock) {
            super(config, stock);
        }

        @Override
        public ExportOnlyAEFluidSlot copy() {
            return new ExportOnlyAEStockingFluidSlot(this.config == null ? null : copy(this.config), this.stock == null ? null : copy(this.stock));
        }

        @Override
        public FluidStack drain(long maxDrain, boolean simulate, boolean notifyChanges) {
            if (this.stock != null && this.config != null) {
                // Extract the items from the real net to either validate (simulate)
                // or extract (modulate) when this is called
                if (!isOnline()) return FluidStack.empty();
                MEStorage aeNetwork = getMainNode().getGrid().getStorageService().getInventory();
                Actionable action = simulate ? Actionable.SIMULATE : Actionable.MODULATE;
                var key = config.what();
                long extracted = aeNetwork.extract(key, maxDrain, action, actionSource);
                if (extracted > 0) {
                    FluidStack resultStack = key instanceof AEFluidKey fluidKey ? AEUtil.toFluidStack(fluidKey, extracted) : FluidStack.empty();
                    if (!simulate) {
                        // may as well update the display here
                        this.stock = ExportOnlyAESlot.copy(stock, stock.amount() - extracted);
                        if (this.stock.amount() == 0) {
                            this.stock = null;
                        }
                        if (notifyChanges && this.onContentsChanged != null) {
                            this.onContentsChanged.run();
                        }
                    }
                    return resultStack;
                }
            }
            return FluidStack.empty();
        }
    }
}
