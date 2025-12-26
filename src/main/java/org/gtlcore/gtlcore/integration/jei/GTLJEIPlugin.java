package org.gtlcore.gtlcore.integration.jei;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.common.data.machines.MultiBlockMachineA;

import com.gregtechceu.gtceu.common.data.GTItems;

import com.lowdragmc.lowdraglib.LDLib;

import net.minecraft.resources.ResourceLocation;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class GTLJEIPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return GTLCore.id("jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        if (LDLib.isReiLoaded() || LDLib.isEmiLoaded()) return;
        registration.addRecipeCatalyst(MultiBlockMachineA.ADVANCED_MULTI_SMELTER.asStack(), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(MultiBlockMachineA.DIMENSIONALLY_TRANSCENDENT_STEAM_OVEN.asStack(), RecipeTypes.SMELTING);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.useNbtForSubtypes(GTItems.TURBINE_ROTOR.asItem());
        registration.useNbtForSubtypes(GTItems.INTEGRATED_CIRCUIT.asItem());
    }
}
