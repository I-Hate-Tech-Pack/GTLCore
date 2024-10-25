package org.gtlcore.gtlcore.common.machine.multiblock;

import org.gtlcore.gtlcore.common.machine.multiblock.electric.StorageMachine;

import com.gregtechceu.gtceu.api.block.IMachineBlock;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.feature.IMachineModifyDrops;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.api.pattern.BlockPattern;
import com.gregtechceu.gtceu.client.renderer.MultiblockInWorldPreviewRenderer;
import com.gregtechceu.gtceu.config.ConfigHolder;

import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author EasterFG on 2024/10/22
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SimulationMachine extends StorageMachine implements IMachineModifyDrops {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            SimulationMachine.class, StorageMachine.MANAGED_FIELD_HOLDER);

    // private static final ExecutorService POLL = Executors.newFixedThreadPool(1);

    // private State formedState = State.IDLE;
    // private PatternError error;

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    public SimulationMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, 1);
    }

    @Override
    protected boolean filter(@NotNull ItemStack itemStack) {
        if (itemStack.getItem() instanceof MetaMachineItem item) {
            return item.getDefinition() instanceof MultiblockMachineDefinition;
        }
        return false;
    }

    @Override
    public boolean checkPattern() {
        return false;
    }

    @Override
    public void asyncCheckPattern(long periodID) {
        // nothing
    }

    @Override
    public BlockPattern getPattern() {
        var stack = this.getMachineStorageItem();
        if (stack.isEmpty()) {
            return super.getPattern();
        }
        if (stack.getItem() instanceof MetaMachineItem item) {
            if (item.getDefinition() instanceof MultiblockMachineDefinition definition) {
                return definition.getPatternFactory().get();
            }
        }
        return super.getPattern();
    }

    @Override
    public boolean isFormed() {
        return false;
    }

    private MultiblockControllerMachine getRealController() {
        var stack = this.getMachineStorageItem();
        if (stack.isEmpty()) {
            return self();
        }
        MetaMachineBlockEntity be = null;
        MetaMachineItem machineItem = (MetaMachineItem) stack.getItem();
        if (machineItem.getBlock() instanceof IMachineBlock machineBlock) {
            var bs = machineBlock.self().withPropertiesOf(getBlockState());
            // 旋转虚拟控制器
            be = (MetaMachineBlockEntity) machineBlock.newBlockEntity(getPos(), bs);
        }
        if (be == null) return self();
        return (MultiblockControllerMachine) be.getMetaMachine().self();
    }

    // @Override
    // public void addDisplayText(List<Component> textList) {
    // super.addDisplayText(textList);
    // textList.add(Component.translatable("gtceu.machine.simulation_machine.tooltip.1"));
    // textList.add(Component.translatable("gtceu.machine.simulation_machine.tooltip.3",
    // Component.literal(formedState.getName()).withStyle(formedState.getColor())
    // .append(ComponentPanelWidget.withButton(
    // Component.translatable("gtceu.machine.simulation_machine.tooltip.2"), "check"))
    // ));
    // if (error != null) {
    // if (error instanceof PatternError) {
    // textList.add(Component.translatable("gtceu.machine.simulation_machine.tooltip.4", error.getPos()));
    // textList.addAll(getError(error));
    // } else {
    // textList.add(error.getErrorInfo());
    // }
    // }
    // }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.isShiftKeyDown() && player.getItemInHand(hand).isEmpty()) {
            if (world.isClientSide()) {
                MultiblockInWorldPreviewRenderer.showPreview(pos, getRealController(),
                        ConfigHolder.INSTANCE.client.inWorldPreviewDuration * 20);
            }
            return InteractionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    // @Override
    // public void handleDisplayClick(String componentData, ClickData clickData) {
    // if ("check".equals(componentData)) {
    // if (getMachineStorageItem().isEmpty()) return;
    // POLL.submit(() -> {
    // error = null;
    // var state = new MultiblockState(getLevel(), getPos()) {
    // @Override
    // public IMultiController getController() {
    // return getRealController();
    // }
    // };
    // formedState = State.RUNNING;
    // var result = getPattern().checkPatternAt(state, false);
    // formedState = result ? State.SUCCESS : State.FAILED;
    // if (!result) {
    // error = state.error;
    // }
    // });
    // }
    // }

    // @Getter
    // @AllArgsConstructor
    // enum State {
    // IDLE("未检测", ChatFormatting.GRAY),
    // RUNNING("检测中", ChatFormatting.YELLOW),
    // FAILED("未成型", ChatFormatting.RED),
    // SUCCESS("已成型", ChatFormatting.GREEN);
    // private final String name;
    // private final ChatFormatting color;
    // }
}
