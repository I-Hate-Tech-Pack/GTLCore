package org.gtlcore.gtlcore.mixin.gtm.api.recipe;

import org.gtlcore.gtlcore.api.recipe.ingredient.LongIngredient;

import com.gregtechceu.gtceu.utils.IngredientEquality;

import net.minecraft.world.item.crafting.Ingredient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IngredientEquality.IngredientHashStrategy.class)
public class IngredientHashStrategyMixin {

    @Inject(method = "hashCode(Lnet/minecraft/world/item/crafting/Ingredient;)I",
            at = @At("HEAD"),
            remap = false,
            cancellable = true)
    public void hashCode(Ingredient o, CallbackInfoReturnable<Integer> cir) {
        if (o instanceof LongIngredient li) cir.setReturnValue(li.getInnerHashCode());
    }

    @Inject(method = "equals(Lnet/minecraft/world/item/crafting/Ingredient;Lnet/minecraft/world/item/crafting/Ingredient;)Z",
            at = @At("HEAD"),
            remap = false,
            cancellable = true)
    public void equals(Ingredient a, Ingredient b, CallbackInfoReturnable<Boolean> cir) {
        if (a instanceof LongIngredient l1)
            if (b instanceof LongIngredient l2)
                cir.setReturnValue(l1.getInnerHashCode() == l2.getInnerHashCode());
    }
}
