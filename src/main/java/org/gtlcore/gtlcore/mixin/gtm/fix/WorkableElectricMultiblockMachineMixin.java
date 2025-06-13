package org.gtlcore.gtlcore.mixin.gtm.fix;

import org.gtlcore.gtlcore.api.machine.trait.IDistinctMachine;
import org.gtlcore.gtlcore.api.recipe.RecipeRunner;
import org.gtlcore.gtlcore.utils.NumberUtils;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.fancy.ConfiguratorPanel;
import com.gregtechceu.gtceu.api.gui.fancy.IFancyConfiguratorButton;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.fancyconfigurator.OverclockFancyConfigurator;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.IOverclockMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDistinctPart;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.misc.EnergyContainerList;
import com.gregtechceu.gtceu.common.machine.multiblock.part.FluidHatchPartMachine;
import com.gregtechceu.gtceu.utils.GTUtil;

import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import lombok.Setter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mixin(WorkableElectricMultiblockMachine.class)
public abstract class WorkableElectricMultiblockMachineMixin extends WorkableMultiblockMachine implements IFancyUIMachine, IDistinctMachine {

    @Persisted
    @DescSynced
    @Getter
    @Setter
    public boolean isDistinct = false;
    @Setter
    @Getter
    private List<RecipeRunner.RecipeHandlePart> recipeHandleParts = new ObjectArrayList<>();
    @Getter
    @Setter
    private RecipeRunner.RecipeHandlePart distinctHatch;
    @Getter
    @Setter
    private ResourceLocation recipeId;

    public WorkableElectricMultiblockMachineMixin(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    @Inject(method = "onStructureFormed", at = @At("TAIL"), remap = false)
    public void onStructureFormed(CallbackInfo ci) {
        this.upDate();
    }

    @Inject(method = "onStructureInvalid", at = @At("TAIL"), remap = false)
    public void onStructureInvalid(CallbackInfo ci) {
        this.recipeHandleParts.clear();
    }

    @Inject(method = "onPartUnload", at = @At("TAIL"), remap = false)
    public void onPartUnload(CallbackInfo ci) {
        this.recipeHandleParts.clear();
    }

    @Shadow(remap = false)
    protected EnergyContainerList energyContainer;

    @Shadow(remap = false)
    public abstract EnergyContainerList getEnergyContainer();

    /**
     * @author mod_author
     * @reason fix
     */
    @Overwrite(remap = false)
    public long getOverclockVoltage() {
        if (this.energyContainer == null) {
            this.energyContainer = this.getEnergyContainer();
        }
        long voltage;
        long amperage;
        if (energyContainer.getInputVoltage() > energyContainer.getOutputVoltage()) {
            voltage = energyContainer.getInputVoltage();
            amperage = energyContainer.getInputAmperage();
        } else {
            voltage = energyContainer.getOutputVoltage();
            amperage = energyContainer.getOutputAmperage();
        }

        if (amperage == 1) {
            // amperage is 1 when the energy is not exactly on a tier
            // the voltage for recipe search is always on tier, so take the closest lower tier
            if (voltage > Integer.MAX_VALUE) return NumberUtils.getVoltageFromFakeTier(NumberUtils.getFakeVoltageTier(voltage));
            return GTValues.V[GTUtil.getFloorTierByVoltage(voltage)];
        } else {
            // amperage != 1 means the voltage is exactly on a tier
            // ignore amperage, since only the voltage is relevant for recipe search
            // amps are never > 3 in an EnergyContainerList
            return voltage;
        }
    }

    @Override
    public void attachConfigurators(ConfiguratorPanel configuratorPanel) {
        configuratorPanel.attachConfigurators(new IFancyConfiguratorButton.Toggle(
                GuiTextures.BUTTON_POWER.getSubTexture(0, 0, 1, 0.5),
                GuiTextures.BUTTON_POWER.getSubTexture(0, 0.5, 1, 0.5),
                this::isWorkingEnabled, (clickData, pressed) -> this.setWorkingEnabled(pressed))
                .setTooltipsSupplier(pressed -> List.of(
                        Component.translatable(pressed ? "behaviour.soft_hammer.enabled" : "behaviour.soft_hammer.disabled"))));
        if (this instanceof IOverclockMachine overclockMachine) {
            configuratorPanel.attachConfigurators(new OverclockFancyConfigurator(overclockMachine));
        }
        IDistinctMachine.attachConfigurators(configuratorPanel, (WorkableElectricMultiblockMachine) self());
    }

    public void upDate() {
        recipeHandleParts.clear();
        distinctHatch = null;
        recipeId = null;
        Iterator<IMultiPart> parts = this.getParts().iterator();
        Object2ObjectOpenHashMap<RecipeCapability<?>, List<IRecipeHandler<?>>> outputParts = new Object2ObjectOpenHashMap<>();
        while (parts.hasNext()) {
            IMultiPart part = parts.next();
            if (part instanceof FluidHatchPartMachine || part instanceof IDistinctPart) {
                List<IRecipeHandler<?>> itemPart = new ObjectArrayList<>();
                List<IRecipeHandler<?>> fluidPart = new ObjectArrayList<>();
                boolean isOutput = false;
                for (var v : part.getRecipeHandlers()) {
                    if (!v.isProxy()) {
                        if (v.getHandlerIO() == IO.IN) {
                            if (v.getCapability() == ItemRecipeCapability.CAP) itemPart.add(v);
                            else if (v.getCapability() == FluidRecipeCapability.CAP) fluidPart.add(v);
                        } else if (v.getHandlerIO() == IO.OUT) {
                            isOutput = true;
                            if (v.getCapability() == ItemRecipeCapability.CAP) itemPart.add(v);
                            else if (v.getCapability() == FluidRecipeCapability.CAP) fluidPart.add(v);
                        }
                    }
                }
                if (isOutput) {
                    outputParts.computeIfAbsent(ItemRecipeCapability.CAP, k -> new ArrayList<>()).addAll(itemPart);
                    outputParts.computeIfAbsent(FluidRecipeCapability.CAP, k -> new ArrayList<>()).addAll(fluidPart);
                } else {
                    Object2ObjectOpenHashMap<RecipeCapability<?>, List<IRecipeHandler<?>>> distinctParts = new Object2ObjectOpenHashMap<>();
                    distinctParts.computeIfAbsent(ItemRecipeCapability.CAP, k -> new ArrayList<>()).addAll(itemPart);
                    distinctParts.computeIfAbsent(FluidRecipeCapability.CAP, k -> new ArrayList<>()).addAll(fluidPart);
                    recipeHandleParts.add(new RecipeRunner.RecipeHandlePart(IO.IN, distinctParts));
                }
            }
        }
        recipeHandleParts.add(new RecipeRunner.RecipeHandlePart(IO.OUT, outputParts));
    }
}
