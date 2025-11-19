package org.gtlcore.gtlcore.common.item;

import org.gtlcore.gtlcore.common.machine.multiblock.part.ae.MEPatternBufferPartMachine;

import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import com.gregtechceu.gtceu.common.item.TooltipBehavior;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.item.tool.ToolHelper.getBehaviorsTag;

public class MEPatternBufferCutBehavior extends TooltipBehavior implements IInteractionItem {

    public static final MEPatternBufferCutBehavior INSTANCE = new MEPatternBufferCutBehavior((list -> {
        list.add(Component.translatable("tooltip.gtlcore.cut_pattern_buffer_sneak_right_click"));
        list.add(Component.translatable("tooltip.gtlcore.apply_cut_pattern_buffer_right_click"));
    }));

    public MEPatternBufferCutBehavior(@NotNull Consumer<List<Component>> tooltips) {
        super(tooltips);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack itemStack, UseOnContext context) {
        if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof MetaMachineBlockEntity machineBlock) {
            if (machineBlock.getMetaMachine() instanceof MEPatternBufferPartMachine partMachine) {
                if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
                    var tags = getBehaviorsTag(itemStack);
                    if (!serverPlayer.isShiftKeyDown()) {
                        if (tags.isEmpty() || !tags.contains("cut")) serverPlayer.displayClientMessage(Component.translatable("message.gtlcore.pattern_buffer_not_cut"), true);
                        else {
                            if (!partMachine.pasteFromTag(tags.getCompound("cut"))) {
                                serverPlayer.displayClientMessage(Component.translatable("message.gtlcore.pattern_buffer_paste_failed"), true);
                                return InteractionResult.CONSUME;
                            } else tags.remove("cut");
                        }
                    } else {
                        tags = partMachine.cutToTag(tags);
                        if (tags.isEmpty() || !tags.contains("cut") || tags.getCompound("cut").isEmpty()) serverPlayer.displayClientMessage(Component.translatable("message.gtlcore.pattern_buffer_cut_failed"), true);
                        else serverPlayer.displayClientMessage(Component.translatable("message.gtlcore.pattern_buffer_cut"), true);
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }
}
