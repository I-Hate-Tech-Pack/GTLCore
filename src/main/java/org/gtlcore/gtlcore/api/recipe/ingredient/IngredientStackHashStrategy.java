package org.gtlcore.gtlcore.api.recipe.ingredient;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import it.unimi.dsi.fastutil.Hash;

import java.util.Objects;

public interface IngredientStackHashStrategy extends Hash.Strategy<Ingredient> {

    static IngredientStackHashStrategyBuilder builder() {
        return new IngredientStackHashStrategyBuilder();
    }

    static IngredientStackHashStrategy comparing() {
        return builder().build();
    }

    class IngredientStackHashStrategyBuilder {

        public IngredientStackHashStrategy build() {
            return new IngredientStackHashStrategy() {

                @Override
                public int hashCode(Ingredient o) {
                    if (o == null || o.isEmpty()) return 0;
                    ItemStack[] items = o.getItems();
                    Object[] obj = new Object[items.length * 2];
                    for (int i = 0; i < items.length; i++) {
                        obj[i * 2] = items[i].getItem();
                        obj[i * 2 + 1] = items[i].getTag();
                    }
                    return Objects.hash(obj);
                }

                @Override
                public boolean equals(Ingredient a, Ingredient b) {
                    if (a != null && !a.isEmpty()) {
                        if (b != null && !b.isEmpty()) for (var i : a.getItems()) if (b.test(i)) return true;
                        return false;
                    } else return b == null || b.isEmpty();
                }
            };
        }
    }
}
