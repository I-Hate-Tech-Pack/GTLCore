package org.gtlcore.gtlcore.mixin.adastra;

import org.gtlcore.gtlcore.common.data.GTLMaterials;

import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

import java.util.function.BiPredicate;

@Mixin(Rocket.class)
public abstract class RocketMixin extends Entity {

    @Shadow(remap = false)
    @Final
    private Rocket.RocketProperties properties;

    public RocketMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyArg(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;Learth/terrarium/adastra/common/entities/vehicles/Rocket$RocketProperties;)V",
               at = @At(value = "INVOKE",
                        target = "Learth/terrarium/botarium/common/fluid/impl/SimpleFluidContainer;<init>(JILjava/util/function/BiPredicate;)V"),
               index = 2,
               remap = false)
    public BiPredicate<Integer, FluidHolder> modifyRocketFluidFilter(BiPredicate<Integer, FluidHolder> fluidFilter) {
        return (amount, fluid) -> switch (properties.tier()) {
            case 1 -> fluid.is(GTMaterials.RocketFuel.getFluid());
            case 2 -> fluid.is(GTLMaterials.RocketFuelRp1.getFluid());
            case 3 -> fluid.is(GTLMaterials.DenseHydrazineFuelMixture.getFluid());
            case 4 -> fluid.is(GTLMaterials.RocketFuelCn3h7o3.getFluid());
            case 5 -> fluid.is(GTLMaterials.RocketFuelH8n4c2o4.getFluid());
            case 6 -> fluid.is(ModFluids.CRYO_FUEL.get());
            case 7 -> fluid.is(GTLMaterials.StellarEnergyRocketFuel.getFluid());
            default -> throw new IllegalStateException("Unexpected value: " + properties.tier());
        };
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;Learth/terrarium/adastra/common/entities/vehicles/Rocket$RocketProperties;)V", remap = false, constant = @Constant(longValue = 3000L))
    private long modifyContainer(long constant) {
        return 16000L;
    }

    @ModifyArg(method = "consumeFuel",
               at = @At(value = "INVOKE",
                        target = "Learth/terrarium/botarium/common/fluid/FluidConstants;fromMillibuckets(J)J"),
               remap = false)
    public long consumeFuel(long millibuckets) {
        return 16000L;
    }
}
