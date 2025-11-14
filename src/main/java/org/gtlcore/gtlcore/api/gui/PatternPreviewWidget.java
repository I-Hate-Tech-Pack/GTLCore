package org.gtlcore.gtlcore.api.gui;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.pattern.BlockPattern;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;
import com.gregtechceu.gtceu.api.pattern.TraceabilityPredicate;
import com.gregtechceu.gtceu.api.pattern.predicates.SimplePredicate;
import com.gregtechceu.gtceu.config.ConfigHolder;

import com.lowdragmc.lowdraglib.gui.editor.ColorPattern;
import com.lowdragmc.lowdraglib.gui.texture.*;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.*;
import com.lowdragmc.lowdraglib.jei.IngredientIO;
import com.lowdragmc.lowdraglib.utils.BlockInfo;
import com.lowdragmc.lowdraglib.utils.CycleItemStackHandler;
import com.lowdragmc.lowdraglib.utils.ItemStackKey;
import com.lowdragmc.lowdraglib.utils.TrackedDummyWorld;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.longs.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author KilaBash
 * @date 2023/3/5
 * @implNote PatterShapeInfoWidget
 */
@OnlyIn(Dist.CLIENT)
public class PatternPreviewWidget extends WidgetGroup {

    private static TrackedDummyWorld LEVEL;
    private static BlockPos LAST_POS = new BlockPos(0, 50, 0);
    private static final Map<MultiblockMachineDefinition, MBPattern[]> CACHE = new Object2ObjectOpenHashMap<>();
    private final SceneWidget sceneWidget;
    private final DraggableScrollableWidgetGroup scrollableWidgetGroup;
    private final MultiblockMachineDefinition controllerDefinition;
    private final MBPattern[] patterns;
    private final MBPattern[] modulePatterns;
    private final List<SimplePredicate> predicates;
    private int index;
    private int moduleIndex;
    private int layer;
    private SlotWidget[] slotWidgets;
    private SlotWidget[] candidates;
    private boolean isModuleMode = false;

    protected PatternPreviewWidget(MultiblockMachineDefinition controllerDefinition) {
        super(0, 0, 160, 160);
        try {
            setClientSideWidget();
            this.controllerDefinition = controllerDefinition;
            predicates = new ObjectArrayList<>();
            layer = -1;

            addWidget(sceneWidget = new SceneWidget(3, 3, 150, 150, LEVEL)
                    .setOnSelected(this::onPosSelected)
                    .setRenderFacing(false)
                    .setRenderFacing(false));

            scrollableWidgetGroup = new DraggableScrollableWidgetGroup(3, 132, 154, 22)
                    .setXScrollBarHeight(4)
                    .setXBarStyle(GuiTextures.SLIDER_BACKGROUND, GuiTextures.BUTTON)
                    .setScrollable(true)
                    .setDraggable(true);
            scrollableWidgetGroup.setScrollWheelDirection(DraggableScrollableWidgetGroup.ScrollWheelDirection.HORIZONTAL);
            scrollableWidgetGroup.setScrollYOffset(0);
            addWidget(scrollableWidgetGroup);

            if (ConfigHolder.INSTANCE.client.useVBO) {
                if (!RenderSystem.isOnRenderThread()) {
                    RenderSystem.recordRenderCall(sceneWidget::useCacheBuffer);
                } else {
                    sceneWidget.useCacheBuffer();
                }
            }

            addWidget(new ImageWidget(3, 3, 160, 10,
                    new TextTexture(controllerDefinition.getDescriptionId(), -1)
                            .setType(TextTexture.TextType.ROLL)
                            .setWidth(170)
                            .setDropShadow(true)));

            var mbPatternList = controllerDefinition.getMatchingShapes().stream()
                    .map(this::initializePattern).toList();
            int nullIndex = mbPatternList.indexOf(null);
            if (nullIndex == -1) {
                this.patterns = CACHE.computeIfAbsent(controllerDefinition,
                        definition -> mbPatternList.toArray(MBPattern[]::new));
                this.modulePatterns = null;
            } else {
                this.patterns = CACHE.computeIfAbsent(controllerDefinition,
                        definition -> mbPatternList.subList(0, nullIndex).toArray(MBPattern[]::new));
                this.modulePatterns = mbPatternList.subList(nullIndex + 1, mbPatternList.size()).toArray(MBPattern[]::new);
            }

            addWidget(new ButtonWidget(138, 30, 18, 18, new GuiTextureGroup(
                    ColorPattern.T_GRAY.rectTexture(),
                    new TextTexture("1").setSupplier(() -> "P:" + index)),
                    (x) -> setPage((index + 1 >= patterns.length) ? 0 : index + 1, x))
                    .setHoverBorderTexture(1, -1));

            addWidget(new ButtonWidget(138, 50, 18, 18, new GuiTextureGroup(
                    ColorPattern.T_GRAY.rectTexture(),
                    new TextTexture("1").setSupplier(() -> layer >= 0 ? "L:" + layer : "ALL")),
                    this::updateLayer)
                    .setHoverBorderTexture(1, -1));

            if (this.modulePatterns != null) {
                addWidget(new ButtonWidget(138, 70, 18, 18, new GuiTextureGroup(
                        ColorPattern.T_GRAY.rectTexture(),
                        new TextTexture("1").setSupplier(() -> "M:" + moduleIndex)),
                        (x) -> setModulePage((moduleIndex + 1 >= modulePatterns.length) ? 0 : moduleIndex + 1))
                        .setHoverBorderTexture(1, -1)
                        .setHoverTooltips(Component.translatable("gui.gtlcore.module.show")));
            }
            setPage(0, null);
        } catch (Exception e) {
            throw new IllegalStateException("The jei preview creation for the Multi Block Machine [" + controllerDefinition.getId().toString() + "] failed! ");
        }
    }

    private void updateLayer(ClickData cd) {
        var pattern = isModuleMode ? modulePatterns[moduleIndex] : patterns[index];
        if (layer + 1 >= -1 && layer + 1 <= pattern.maxY - pattern.minY && !cd.isShiftClick) {
            layer += 1;
            if (pattern.controllerBase.isFormed()) {
                onFormedSwitch(false);
            }
        } else {
            layer = -1;
            if (!pattern.controllerBase.isFormed()) {
                onFormedSwitch(true);
            }
        }
        setupScene(pattern);
    }

    private void setupScene(MBPattern pattern) {
        var stream = pattern.blockMap.keySet().stream()
                .filter(pos -> layer == -1 || layer + pattern.minY == pos.getY());
        if (pattern.controllerBase.isFormed()) {
            LongSet set = pattern.controllerBase.getMultiblockState().getMatchContext().getOrDefault("renderMask",
                    LongSets.EMPTY_SET);
            var modelDisabled = set.longStream().mapToObj(BlockPos::of).collect(Collectors.toSet());
            if (!modelDisabled.isEmpty()) {
                sceneWidget.setRenderedCore(
                        stream.filter(pos -> !modelDisabled.contains(pos)).collect(Collectors.toList()), null);
            } else {
                sceneWidget.setRenderedCore(stream.toList(), null);
            }
        } else {
            sceneWidget.setRenderedCore(stream.toList(), null);
        }
    }

    public static PatternPreviewWidget getPatternWidget(MultiblockMachineDefinition controllerDefinition) {
        if (LEVEL == null) {
            if (Minecraft.getInstance().level == null) {
                GTCEu.LOGGER.error("Try to init pattern previews before level load");
                throw new IllegalStateException();
            }
            LEVEL = new TrackedDummyWorld();
        }
        return new PatternPreviewWidget(controllerDefinition);
    }

    public void setPage(int index, ClickData x) {
        if (x != null) {
            if (x.isShiftClick) index = 0;
            else if (x.isCtrlClick) index = patterns.length - 1;
        }
        if (index >= patterns.length || index < 0) return;
        this.index = index;
        this.layer = -1;
        this.isModuleMode = false;
        MBPattern pattern = patterns[index];
        setupScene(pattern);
        if (slotWidgets != null) {
            for (var slotWidget : slotWidgets) {
                scrollableWidgetGroup.removeWidget(slotWidget);
            }
        }
        slotWidgets = new SlotWidget[pattern.parts.size()];
        var itemHandler = new CycleItemStackHandler(pattern.parts);
        for (int i = 0; i < slotWidgets.length; i++) {
            final var itemStack = pattern.parts.get(i).get(0);
            slotWidgets[i] = new SlotWidget(itemHandler, i, 4 + i * 18, 0, false, false)
                    .setBackgroundTexture(ColorPattern.T_GRAY.rectTexture())
                    .setIngredientIO(IngredientIO.INPUT)
                    .setOnAddedTooltips((w, tips) -> tips.add(Component.translatable("gtceu.machine.quantum_chest.items_stored")
                            .withStyle(ChatFormatting.DARK_AQUA)
                            .append(Component.literal(String.valueOf(itemStack.getCount())))));
            scrollableWidgetGroup.addWidget(slotWidgets[i]);
        }
    }

    public void setModulePage(int index) {
        if (index >= modulePatterns.length || index < 0) return;
        this.moduleIndex = index;
        this.layer = -1;
        this.isModuleMode = true;
        var pattern = modulePatterns[index];
        setupScene(pattern);
        if (slotWidgets != null) {
            for (var slotWidget : slotWidgets) {
                scrollableWidgetGroup.removeWidget(slotWidget);
            }
        }
        slotWidgets = new SlotWidget[pattern.parts.size()];
        var itemHandler = new CycleItemStackHandler(pattern.parts);
        for (int i = 0; i < slotWidgets.length; i++) {
            final var itemStack = pattern.parts.get(i).get(0);
            slotWidgets[i] = new SlotWidget(itemHandler, i, 4 + i * 18, 0, false, false)
                    .setBackgroundTexture(ColorPattern.T_GRAY.rectTexture())
                    .setIngredientIO(IngredientIO.INPUT)
                    .setOnAddedTooltips((w, tips) -> tips.add(Component.translatable("gtceu.machine.quantum_chest.items_stored")
                            .withStyle(ChatFormatting.DARK_AQUA)
                            .append(Component.literal(String.valueOf(itemStack.getCount())))));
            scrollableWidgetGroup.addWidget(slotWidgets[i]);
        }
    }

    private void onFormedSwitch(boolean isFormed) {
        var pattern = isModuleMode ? modulePatterns[moduleIndex] : patterns[index];
        var controllerBase = pattern.controllerBase;
        if (isFormed) {
            this.layer = -1;
            loadControllerFormed(pattern.blockMap.keySet(), controllerBase);
        } else {
            sceneWidget.setRenderedCore(pattern.blockMap.keySet(), null);
            controllerBase.onStructureInvalid();
        }
    }

    private void onPosSelected(BlockPos pos, Direction facing) {
        if (isModuleMode && (moduleIndex >= modulePatterns.length || moduleIndex < 0)) return;
        else if (index >= patterns.length || index < 0) return;
        var predicate = (isModuleMode ? modulePatterns[moduleIndex] : patterns[index]).predicateMap.get(pos);
        if (predicate != null) {
            predicates.clear();
            predicates.addAll(predicate.common);
            predicates.addAll(predicate.limited);
            predicates.removeIf(p -> p == null || p.candidates == null); // why it happens?
            if (candidates != null) {
                for (SlotWidget candidate : candidates) {
                    removeWidget(candidate);
                }
            }
            List<List<ItemStack>> candidateStacks = new ObjectArrayList<>();
            List<List<Component>> predicateTips = new ObjectArrayList<>();
            for (var simplePredicate : predicates) {
                var itemStacks = simplePredicate.getCandidates();
                if (!itemStacks.isEmpty()) {
                    candidateStacks.add(itemStacks);
                    predicateTips.add(simplePredicate.getToolTips(predicate));
                }
            }
            candidates = new SlotWidget[candidateStacks.size()];
            CycleItemStackHandler itemHandler = new CycleItemStackHandler(candidateStacks);
            int maxCol = (160 - (((slotWidgets.length - 1) / 9 + 1) * 18) - 35) % 18;
            for (int i = 0; i < candidateStacks.size(); i++) {
                int finalI = i;
                candidates[i] = new SlotWidget(itemHandler, i, 3 + (i / maxCol) * 18, 3 + (i % maxCol) * 18, false,
                        false)
                        .setIngredientIO(IngredientIO.INPUT)
                        .setBackgroundTexture(new ColorRectTexture(0x4fffffff))
                        .setOnAddedTooltips((slot, list) -> list.addAll(predicateTips.get(finalI)));
                addWidget(candidates[i]);
            }
        }
    }

    public static BlockPos locateNextRegion(int range) {
        BlockPos pos = LAST_POS;
        LAST_POS = LAST_POS.offset(range, 0, range);
        return pos;
    }

    @Override
    public void drawInBackground(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.enableBlend();
        super.drawInBackground(graphics, mouseX, mouseY, partialTicks);
    }

    private MBPattern initializePattern(MultiblockShapeInfo shapeInfo) {
        if (shapeInfo == null) return null;
        Map<BlockPos, BlockInfo> blockMap = new Object2ObjectOpenHashMap<>();
        IMultiController controllerBase = null;
        var multiPos = locateNextRegion(500);

        var blocks = shapeInfo.getBlocks();
        for (int x = 0; x < blocks.length; x++) {
            var aisle = blocks[x];
            for (int y = 0; y < aisle.length; y++) {
                var column = aisle[y];
                for (int z = 0; z < column.length; z++) {
                    var block = column[z];
                    if (block == null) continue;
                    var blockState = block.getBlockState();
                    var pos = multiPos.offset(x, y, z);
                    if (block.getBlockEntity(pos) instanceof IMachineBlockEntity holder &&
                            holder.getMetaMachine() instanceof IMultiController controller &&
                            this.controllerDefinition == controller.self().getDefinition()) {
                        holder.getSelf().setLevel(LEVEL);
                        controllerBase = controller;
                    }
                    blockMap.put(pos, BlockInfo.fromBlockState(blockState));
                }
            }
        }

        LEVEL.addBlocks(blockMap);
        if (controllerBase != null) {
            LEVEL.setInnerBlockEntity(controllerBase.self().holder.getSelf());
        }

        var parts = gatherBlockDrops(blockMap);
        Map<BlockPos, TraceabilityPredicate> predicateMap = new Object2ObjectOpenHashMap<>();
        if (controllerBase != null) {
            loadControllerFormed(predicateMap.keySet(), controllerBase);
            predicateMap = controllerBase.getMultiblockState().getMatchContext().get("predicates");
        }
        return controllerBase == null ? null : new MBPattern(blockMap, parts.values().stream().sorted((one, two) -> {
            if (one.isController) return -1;
            if (two.isController) return +1;
            if (one.isTile && !two.isTile) return -1;
            if (two.isTile && !one.isTile) return +1;
            if (one.blockId != two.blockId) return two.blockId - one.blockId;
            return two.amount - one.amount;
        }).map(PartInfo::getItemStack).filter(list -> !list.isEmpty()).collect(Collectors.toList()), predicateMap,
                controllerBase);
    }

    private void loadControllerFormed(Collection<BlockPos> poses, IMultiController controllerBase) {
        BlockPattern pattern = controllerBase.getPattern();
        if (pattern != null && pattern.checkPatternAt(controllerBase.getMultiblockState(), true)) {
            controllerBase.onStructureFormed();
        }
        if (controllerBase.isFormed()) {
            LongSet set = controllerBase.getMultiblockState().getMatchContext().getOrDefault("renderMask",
                    LongSets.EMPTY_SET);
            Set<BlockPos> modelDisabled = set.longStream().mapToObj(BlockPos::of).collect(Collectors.toSet());
            if (!modelDisabled.isEmpty()) {
                sceneWidget.setRenderedCore(
                        poses.stream().filter(pos -> !modelDisabled.contains(pos)).collect(Collectors.toList()), null);
            } else {
                sceneWidget.setRenderedCore(poses, null);
            }
        } else {
            GTCEu.LOGGER.warn("Pattern formed checking failed: {}", controllerBase.self().getDefinition());
        }
    }

    private Map<ItemStackKey, PartInfo> gatherBlockDrops(Map<BlockPos, BlockInfo> blocks) {
        Map<ItemStackKey, PartInfo> partsMap = new Object2ObjectOpenHashMap<>();
        for (Map.Entry<BlockPos, BlockInfo> entry : blocks.entrySet()) {
            BlockPos pos = entry.getKey();
            BlockState blockState = LEVEL.getBlockState(pos);
            ItemStack itemStack = blockState.getBlock().getCloneItemStack(LEVEL, pos, blockState);

            if (itemStack.isEmpty() && !blockState.getFluidState().isEmpty()) {
                Fluid fluid = blockState.getFluidState().getType();
                itemStack = fluid.getBucket().getDefaultInstance();
            }

            ItemStackKey itemStackKey = new ItemStackKey(itemStack);
            partsMap.computeIfAbsent(itemStackKey, key -> new PartInfo(key, entry.getValue())).amount++;
        }
        return partsMap;
    }

    private static class PartInfo {

        final ItemStackKey itemStackKey;
        boolean isController = false;
        boolean isTile;
        final int blockId;
        int amount = 0;

        PartInfo(final ItemStackKey itemStackKey, final BlockInfo blockInfo) {
            this.itemStackKey = itemStackKey;
            this.blockId = Block.getId(blockInfo.getBlockState());
            this.isTile = blockInfo.hasBlockEntity();

            if (blockInfo.getBlockState().getBlock() instanceof MetaMachineBlock block) {
                if (block.definition instanceof MultiblockMachineDefinition)
                    this.isController = true;
            }
        }

        public List<ItemStack> getItemStack() {
            return Arrays.stream(itemStackKey.getItemStack())
                    .map(itemStack -> {
                        var item = itemStack.copy();
                        item.setCount(amount);
                        return item;
                    }).filter(item -> !item.isEmpty()).toList();
        }
    }

    private static class MBPattern {

        @NotNull
        final List<List<ItemStack>> parts;
        @NotNull
        final Map<BlockPos, TraceabilityPredicate> predicateMap;
        @NotNull
        final Map<BlockPos, BlockInfo> blockMap;
        @NotNull
        final IMultiController controllerBase;
        final int maxY, minY;

        public MBPattern(@NotNull Map<BlockPos, BlockInfo> blockMap, @NotNull List<List<ItemStack>> parts,
                         @NotNull Map<BlockPos, TraceabilityPredicate> predicateMap,
                         @NotNull IMultiController controllerBase) {
            this.parts = parts;
            this.blockMap = blockMap;
            this.predicateMap = predicateMap;
            this.controllerBase = controllerBase;
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for (BlockPos pos : blockMap.keySet()) {
                min = Math.min(min, pos.getY());
                max = Math.max(max, pos.getY());
            }
            minY = min;
            maxY = max;
        }
    }
}
