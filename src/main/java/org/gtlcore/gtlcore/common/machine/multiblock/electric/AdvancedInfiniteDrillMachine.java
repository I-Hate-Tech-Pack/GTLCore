package org.gtlcore.gtlcore.common.machine.multiblock.electric;

import org.gtlcore.gtlcore.common.data.GTLMaterials;
import org.gtlcore.gtlcore.common.machine.trait.AdvancedInfiniteDrillLogic;
import org.gtlcore.gtlcore.utils.MachineIO;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockDisplayText;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author EasterFG on 2024/10/26
 *         <p>
 *         1.2 * heat
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class AdvancedInfiniteDrillMachine extends StorageMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            AdvancedInfiniteDrillMachine.class, StorageMachine.MANAGED_FIELD_HOLDER);

    protected static final Map<Material, Integer> HEAT_MAP = Map.of(
            GTMaterials.Neutronium, 1,
            GTLMaterials.Vibranium, 2,
            GTLMaterials.Bedrock, 3);

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    public static final int NORMAL_HEAT = 300;

    public static final int RUNNING_HEAT = 2000;

    public static final int MAX_HEAT = 10000;

    @Persisted
    @Getter
    protected int currentHeat = NORMAL_HEAT;

    protected ConditionalSubscriptionHandler heatSubs;

    public AdvancedInfiniteDrillMachine(IMachineBlockEntity holder) {
        super(holder, 1);
        this.heatSubs = new ConditionalSubscriptionHandler(this, this::heatUpdate, this::isFormed);
    }

    @Override
    protected RecipeLogic createRecipeLogic(Object... args) {
        return new AdvancedInfiniteDrillLogic(this, 5);
    }

    protected void heatUpdate() {
        if (getOffsetTimer() % 5 != 0) return;
        if (isEmpty()) return;
        if (currentHeat > MAX_HEAT) {
            currentHeat = 0;
            this.machineStorage.setStackInSlot(0, ItemStack.EMPTY);
            this.getRecipeLogic().interruptRecipe();
            return;
        }

        int heat = 0;
        if (getRecipeLogic().isWorking()) {
            heat += (int) Math.ceil((currentHeat - RUNNING_HEAT) / 2000D);
        }

        if (inputCooling(GTMaterials.DistilledWater)) {
            if (currentHeat <= 3000) {
                heat--;
            }
        } else if (inputCooling(GTMaterials.Nitrogen)) {
            if (currentHeat > 3000 && currentHeat < 7000) {
                heat -= 2;
            }
        } else if (inputCooling(GTMaterials.Helium)) {
            if (currentHeat > 7000 && currentHeat <= 10000) {
                heat -= 4;
            }
        } else if (inputBlast()) {
            heat++;
        }
        this.currentHeat += heat;
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        this.heatSubs.initialize(getLevel());
    }

    @Override
    protected boolean filter(ItemStack itemStack) {
        return ChemicalHelper.getPrefix(itemStack.getItem()) == TagPrefix.toolHeadDrill;
    }

    @Override
    public AdvancedInfiniteDrillLogic getRecipeLogic() {
        return (AdvancedInfiniteDrillLogic) super.getRecipeLogic();
    }

    @Override
    public void onWaiting() {
        super.onWaiting();
        getRecipeLogic().resetRecipeLogic();
    }

    @Override
    public void addDisplayText(List<Component> tooltips) {
        if (isFormed()) {
            tooltips.add(ComponentPanelWidget.withButton(Component.literal("+10"), "+10"));
            tooltips.add(ComponentPanelWidget.withButton(Component.literal("-10"), "-10"));
            MultiblockDisplayText.builder(tooltips, true)
                    .setWorkingStatus(isWorkingEnabled(), isActive())
                    .addEnergyUsageLine(energyContainer);

            if (isEmpty()) {
                tooltips.add(Component.translatable("gtceu.machine.advanced_infinite_driller.not_fluid_head")
                        .withStyle(ChatFormatting.RED));
            } else {
                tooltips.add(Component.translatable("gtceu.machine.advanced_infinite_driller.range",
                        Component.literal("5x5").withStyle(ChatFormatting.GOLD)));
                tooltips.add(Component.translatable("gtceu.machine.advanced_infinite_driller.heat", MAX_HEAT, RUNNING_HEAT));
                tooltips.add(Component.translatable("gtceu.machine.advanced_infinite_driller.current_heat", currentHeat));
                var fluids = getRecipeLogic().getVeinFluids();
                if (!fluids.isEmpty()) {
                    fluids.forEach((fluid, produced) -> {
                        Component fluidInfo = fluid.getFluidType().getDescription().copy()
                                .withStyle(ChatFormatting.GREEN);
                        Component amountInfo = Component.literal(FormattingUtil.formatNumbers(
                                produced * getRate()) +
                                " mB/s").withStyle(ChatFormatting.BLUE);
                        tooltips.add(Component.translatable("gtceu.machine.advanced_infinite_driller.drilled_fluid",
                                fluidInfo, amountInfo));
                    });
                } else {
                    Component noFluid = Component.translatable("gtceu.multiblock.fluid_rig.no_fluid_in_area")
                            .withStyle(ChatFormatting.RED);
                    tooltips.add(Component.translatable("gtceu.multiblock.fluid_rig.drilled_fluid", noFluid)
                            .withStyle(ChatFormatting.GRAY));
                }
            }
        } else {
            Component tooltip = Component.translatable("gtceu.multiblock.invalid_structure.tooltip")
                    .withStyle(ChatFormatting.GRAY);
            tooltips.add(Component.translatable("gtceu.multiblock.invalid_structure")
                    .withStyle(Style.EMPTY.withColor(ChatFormatting.RED)
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip))));
        }
    }

    public int getRate() {
        return (int) Math.max(1, (currentHeat - RUNNING_HEAT) * getDrillHeadTier() * 0.75);
    }

    public int getDrillHeadTier() {
        ItemStack itemStack = getMachineStorageItem();
        if (!itemStack.isEmpty()) {
            MaterialStack ms = ChemicalHelper.getMaterial(itemStack);
            if (ms != null) {
                Material material = ms.material();
                Integer result = HEAT_MAP.get(material);
                if (result != null) return result;
            }
        }
        return 0;
    }

    @Override
    public void handleDisplayClick(String componentData, ClickData clickData) {
        if (isFormed()) {
            if (componentData.equals("+10")) {
                currentHeat += 10;
            } else if (componentData.equals("-10")) {
                currentHeat -= 10;
            }
        }
    }

    protected boolean inputBlast() {
        return MachineIO.inputFluid(this, GTMaterials.Blaze.getFluid(getFluidConsume()));
    }

    // protected boolean inputWater() {
    // if (currentHeat <= 0) return false;
    // long heat = (long) Math.pow(currentHeat, 1.5);
    // var success = MachineIO.inputFluid(this, GTMaterials.DistilledWater.getFluid(heat));
    // if (success) currentHeat--;
    // return success;
    // }
    //
    // protected boolean inputLiquidNitrogen() {
    // if (currentHeat <= 0) return false;
    // long heat = (long) Math.pow(currentHeat, 1.5);
    // var success = MachineIO.inputFluid(this, GTMaterials.Nitrogen.getFluid(heat));
    // if (success) currentHeat -= 2;
    // return success;
    // }
    //
    // protected boolean inputLiquidHelium() {
    // if (currentHeat <= 0) return false;
    // var success = MachineIO.inputFluid(this, GTMaterials.Helium.getFluid(getFluidConsume()));
    // if (success) currentHeat -= 4;
    // return success;
    // }

    protected boolean inputCooling(Material material) {
        if (currentHeat <= 0) return false;
        return MachineIO.inputFluid(this, material.getFluid(30000L));
    }

    protected long getFluidConsume() {
        return (long) Math.pow(currentHeat, 1.5);
    }

    public boolean canRunnable() {
        return currentHeat >= RUNNING_HEAT;
    }
}
