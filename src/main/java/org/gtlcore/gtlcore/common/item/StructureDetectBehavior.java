package org.gtlcore.gtlcore.common.item;

import org.gtlcore.gtlcore.client.renderer.BlockHighlightHandler;

import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import com.gregtechceu.gtceu.api.item.tool.behavior.IToolBehavior;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.pattern.MultiblockState;
import com.gregtechceu.gtceu.api.pattern.error.PatternError;
import com.gregtechceu.gtceu.api.pattern.error.PatternStringError;
import com.gregtechceu.gtceu.api.pattern.error.SinglePredicateError;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author EasterFG on 2024/10/25
 */
public class StructureDetectBehavior implements IToolBehavior, IInteractionItem {

    public static final StructureDetectBehavior INSTANCE = new StructureDetectBehavior();

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null) {
            Level level = context.getLevel();
            BlockPos blockPos = context.getClickedPos();
            if (MetaMachine.getMachine(level, blockPos) instanceof IMultiController controller) {
                if (controller.isFormed()) {
                    player.sendSystemMessage(Component.literal("已成型").withStyle(ChatFormatting.GREEN));
                } else {
                    MultiblockState multiblockState = controller.getMultiblockState();
                    PatternError error = multiblockState.error;
                    if (error != null) {
                        if (error instanceof PatternStringError) {
                            return InteractionResult.PASS;
                        }
                        Component show;
                        if (error instanceof SinglePredicateError se) {
                            show = getSingleError(se);
                        } else {
                            show = getError(error);
                        }
                        player.sendSystemMessage(show);
                        BlockHighlightHandler.highlight(error.getPos(), error.getWorld().dimension(), System.currentTimeMillis() + (600 * 5));
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        IToolBehavior.super.addInformation(stack, world, tooltip, flag);
        tooltip.add(Component.translatable("structure_detect.tooltip.0"));
    }

    private Component getError(PatternError error) {
        var root = Component.translatable("structure_detect.error", blockPos(error.getPos()));
        List<List<ItemStack>> candidates = error.getCandidates();
        for (List<ItemStack> candidate : candidates) {
            if (!candidate.isEmpty()) {
                root.append(" - ").append(candidate.get(0).getHoverName()).append("\n");
            }
        }
        return root;
    }

    private Component getSingleError(SinglePredicateError error) {
        List<List<ItemStack>> candidates = error.getCandidates();
        var root = candidates.get(0).get(0).getHoverName();
        return Component.literal(" - ")
                .append(Component.translatable("structure_detect.error.2", blockPos(error.getPos())))
                .append(root).append(error.getErrorInfo());
    }

    public String blockPos(BlockPos pos) {
        return "(" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")";
    }
}
