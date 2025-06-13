package org.gtlcore.gtlcore.api.machine.trait;

import org.gtlcore.gtlcore.api.recipe.RecipeRunner;

import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.fancy.ConfiguratorPanel;
import com.gregtechceu.gtceu.api.gui.fancy.IFancyConfiguratorButton;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * 部分代码参考自gto
 * &#064;line <a href="https://github.com/GregTech-Odyssey/GTOCore">...</a>
 */

public interface IDistinctMachine {

    default List<RecipeRunner.RecipeHandlePart> getRecipeHandleParts() {
        return List.of();
    }

    default void setRecipeHandleParts(List<RecipeRunner.RecipeHandlePart> recipeHandleParts) {}

    default RecipeRunner.RecipeHandlePart getDistinctHatch() {
        return null;
    }

    default void setDistinctHatch(RecipeRunner.RecipeHandlePart hatch) {}

    default ResourceLocation getRecipeId() {
        return null;
    }

    default void setRecipeId(ResourceLocation recipeId) {}

    default void upDate() {}

    default boolean isDistinct() {
        return false;
    }

    default void setDistinct(boolean isDistinct) {}

    static void attachConfigurators(ConfiguratorPanel configuratorPanel, WorkableElectricMultiblockMachine machine) {
        if (machine instanceof IDistinctMachine distinctMachine) {
            configuratorPanel.attachConfigurators(new IFancyConfiguratorButton.Toggle(
                    GuiTextures.BUTTON_DISTINCT_BUSES.getSubTexture(0, 0.5, 1, 0.5),
                    GuiTextures.BUTTON_DISTINCT_BUSES.getSubTexture(0, 0, 1, 0.5),
                    distinctMachine::isDistinct, (clickData, pressed) -> {
                        distinctMachine.setDistinct(pressed);
                        distinctMachine.upDate();
                    })
                    .setTooltipsSupplier(pressed -> List.of(
                            Component.translatable("gtceu.multiblock.universal.distinct.all").setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW))
                                    .append(Component.translatable(pressed ? "gtceu.multiblock.universal.distinct.yes" : "gtceu.multiblock.universal.distinct.no")))));
        }
    }
}
