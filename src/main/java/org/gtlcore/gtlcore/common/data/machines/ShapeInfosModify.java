package org.gtlcore.gtlcore.common.data.machines;

import org.gtlcore.gtlcore.utils.Registries;
import org.gtlcore.gtlcore.utils.StructureSlicer;

import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.ArrayList;
import java.util.List;

import static com.gregtechceu.gtceu.api.GTValues.HV;
import static com.gregtechceu.gtceu.common.data.GTBlocks.CASING_STAINLESS_CLEAN;
import static com.gregtechceu.gtceu.common.data.GTMachines.*;
import static org.gtlcore.gtlcore.common.data.GTLMachines.ME_DUAL_HATCH_STOCK_PART_MACHINE;

public class ShapeInfosModify {

    public static void init() {
        DISTILLATION_TOWER.setShapes(() -> {
            List<MultiblockShapeInfo> shapeInfos = new ArrayList<>();
            var builder = MultiblockShapeInfo.builder()
                    .where('C', DISTILLATION_TOWER, Direction.NORTH)
                    .where('S', CASING_STAINLESS_CLEAN.getDefaultState())
                    .where('X', ITEM_EXPORT_BUS[HV], Direction.NORTH)
                    .where('I', FLUID_IMPORT_HATCH[HV], Direction.NORTH)
                    .where('E', ENERGY_INPUT_HATCH[HV], Direction.SOUTH)
                    .where('#', Blocks.AIR.defaultBlockState())
                    .where('F', FLUID_EXPORT_HATCH[HV], Direction.SOUTH);
            List<String> front = new ArrayList<>(15);
            front.add("XCI");
            front.add("SSS");
            List<String> middle = new ArrayList<>(15);
            middle.add("SSS");
            middle.add("SSS");
            List<String> back = new ArrayList<>(15);
            back.add("SES");
            back.add("SFS");
            for (int i = 1; i <= 11; ++i) {
                front.add("SSS");
                middle.add(1, "S#S");
                back.add("SFS");
                var copy = builder.shallowCopy()
                        .aisle(front.toArray(String[]::new))
                        .aisle(middle.toArray(String[]::new))
                        .aisle(back.toArray(String[]::new));
                shapeInfos.add(copy.build());
            }
            return shapeInfos;
        });
        DISTILLATION_TOWER.setAllowExtendedFacing(false);

        ((MultiblockMachineDefinition) EVAPORATION_PLANT).setShapes(() -> {
            final var shapeInfos = new ObjectArrayList<MultiblockShapeInfo>();
            final var builder = MultiblockShapeInfo.builder()
                    .where('~', EVAPORATION_PLANT, Direction.SOUTH)
                    .where('A', Registries.getBlock("gtceu:aluminium_frame"))
                    .where('B', ENERGY_INPUT_HATCH[HV], Direction.SOUTH)
                    .where('F', ITEM_EXPORT_BUS[HV], Direction.DOWN)
                    .where('E', FLUID_EXPORT_HATCH[HV], Direction.EAST)
                    .where('C', Registries.getBlock("gtceu:stainless_evaporation_casing"))
                    .where('G', ME_DUAL_HATCH_STOCK_PART_MACHINE, Direction.EAST);

            final var arrays = new String[][] {
                    new String[] { "ACA", "CCC", "CCC", "CCC", " C " },
                    new String[] { "CFG", "C C", "C C", "C C", "CCC" },
                    new String[] { "ABA", "C~C", "CCE", "CCE", " C " }
            };

            for (String[][] array : StructureSlicer.sliceAndInsert(arrays, 2, 5, 3, 2, 5)) {
                var copy = builder.shallowCopy();
                for (String[] string : array) {
                    copy = copy.aisle(string);
                }
                shapeInfos.add(copy.build());
            }

            return shapeInfos;
        });
        ((MultiblockMachineDefinition) EVAPORATION_PLANT).setAllowExtendedFacing(false);
    }
}
