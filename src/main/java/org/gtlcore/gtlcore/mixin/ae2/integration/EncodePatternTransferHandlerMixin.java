package org.gtlcore.gtlcore.mixin.ae2.integration;

import com.gregtechceu.gtceu.integration.jei.multipage.MultiblockInfoWrapper;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;

import appeng.api.stacks.GenericStack;
import appeng.integration.modules.jei.transfer.EncodePatternTransferHandler;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Arrays;
import java.util.List;

import static org.gtlcore.gtlcore.config.ConfigHolder.INSTANCE;

@Mixin(EncodePatternTransferHandler.class)
@SuppressWarnings("all")
public class EncodePatternTransferHandlerMixin {

    @ModifyArg(method = "transferRecipe(Lappeng/menu/me/items/PatternEncodingTermMenu;Ljava/lang/Object;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;Lnet/minecraft/world/entity/player/Player;ZZ)Lmezz/jei/api/recipe/transfer/IRecipeTransferError;",
               at = @At(value = "INVOKE",
                        target = "Lappeng/integration/modules/jeirei/EncodingHelper;encodeProcessingRecipe(Lappeng/menu/me/items/PatternEncodingTermMenu;Ljava/util/List;Ljava/util/List;)V"),
               index = 1,
               remap = false)
    public List<List<GenericStack>> multiBlockInputFilter(List<List<GenericStack>> genericIngredients, @Local(name = "recipeBase") Object recipeBase) {
        if (!(recipeBase instanceof MultiblockInfoWrapper) || INSTANCE.filterHatch.length == 0) return genericIngredients;
        var newList = new ObjectArrayList<List<GenericStack>>();
        for (var l : genericIngredients) {
            var list = l.stream().filter(g -> Arrays.stream(INSTANCE.filterHatch).noneMatch(s -> g.what().getId().toString().contains(s))).toList();
            if (!list.isEmpty()) newList.add(l);
        }
        return newList;
    }

    @ModifyArg(method = "transferRecipe(Lappeng/menu/me/items/PatternEncodingTermMenu;Ljava/lang/Object;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;Lnet/minecraft/world/entity/player/Player;ZZ)Lmezz/jei/api/recipe/transfer/IRecipeTransferError;",
               at = @At(value = "INVOKE",
                        target = "Lappeng/integration/modules/jeirei/EncodingHelper;encodeProcessingRecipe(Lappeng/menu/me/items/PatternEncodingTermMenu;Ljava/util/List;Ljava/util/List;)V"),
               index = 2,
               remap = false)
    public List<GenericStack> multiBlockOutputImport(List<GenericStack> genericIngredients, @Local(name = "recipeBase") Object recipeBase) {
        if (!(recipeBase instanceof MultiblockInfoWrapper miw)) return genericIngredients;
        var g = GenericStack.fromItemStack(Items.WRITTEN_BOOK.getDefaultInstance().kjs$withName(Component.translatable(miw.definition.getId().toLanguageKey("block")).withStyle(style -> style.withColor(16536828))));
        return g == null ? List.of() : List.of(g);
    }
}
