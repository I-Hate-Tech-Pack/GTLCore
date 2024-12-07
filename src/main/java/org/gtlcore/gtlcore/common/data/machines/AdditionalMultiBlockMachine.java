package org.gtlcore.gtlcore.common.data.machines;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.common.data.GTLBlocks;
import org.gtlcore.gtlcore.common.data.GTLMachines;
import org.gtlcore.gtlcore.common.data.GTLRecipeTypes;
import org.gtlcore.gtlcore.utils.Registries;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import static com.gregtechceu.gtceu.common.data.GTRecipeModifiers.ELECTRIC_OVERCLOCK;
import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;

/**
 * @author EasterFG on 2024/12/6
 */
@SuppressWarnings("unused")
public class AdditionalMultiBlockMachine {

    public static void init() {}

    public final static MultiblockMachineDefinition ADVANCED_RARE_EARTH_CENTRIFUGAL = REGISTRATE.multiblock("advanced_rare_earth_centrifugal", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTLRecipeTypes.RARE_EARTH_CENTRIFUGAL_RECIPES)
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.multiblock.laser.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.rare_earth_centrifugal")))
            .tooltipBuilder(GTLMachines.GTL_ADD)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
            .appearanceBlock(GTLBlocks.SPS_CASING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("   AAAAAA     AAAAAA   ", "     B           B     ", "     B           B     ", "   CCCCC       CCCCC   ", "     B           B     ", "     B           B     ", "   CCCCC       CCCCC   ", "     B           B     ", "     B           B     ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("  AADDDDA     ADDDDAA  ", "                       ", "                       ", "  C     C     C     C  ", "                       ", "                       ", "  C     C     C     C  ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ")
                    .aisle(" AADDDDDAA   AADDDDDAA ", "  B  D  B     B  D  B  ", "  B  E  B     B  E  B  ", " CB EEE BC   CB EEE BC ", "  B EEE B     B EEE B  ", "  B EEE B     B EEE B  ", " CB EEE BC   CB EEE BC ", "  B EEE B     B EEE B  ", "  B  E  B     B  E  B  ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("AADDDDDDDAAAAADDDDDDDAA", "     D           D     ", "   EEEEE       EEEEE   ", "C  EEEEE  C C  EEEEE  C", "   E F E       E F E   ", "   E F E       E F E   ", "C  E F E  C C  E F E  C", "   EEEEE       EEEEE   ", "   EEEEE       EEEEE   ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("ADDDDDDDDDDDDDDDDDDDDDA", "    DDD         DDD    ", "   EEEEE       EEEEE   ", "C EEGGGEE C C EEGGGEE C", "  E H H E     E H H E  ", "  E H H E     E H H E  ", "C E H H E C C E H H E C", "  EEGGGEE     EEGGGEE  ", "   EEEEE       EEEEE   ", "    DDD         DDD    ", "     III       III     ", "       IIIIIIIII       ", "                       ")
                    .aisle("ADDDDDDDDDDDDDDDDDDDDDA", "B DDDDDDD B B DDDDDDD B", "B EEEEEEE B B EEEEEEE B", "C EEGGGEE C C EEGGGEE C", "B EF G FE B B EF G FE B", "B EF G FE B B EF G FE B", "C EF G FE C C EF G FE C", "B EEGGGEE B B EEGGGEE B", "B EEEGEEE B B EEEGEEE B", "    D A         A D    ", "     AAA       AAA     ", "       AAAAIAAAA       ", "          DDD          ")
                    .aisle("ADDDDDDDDDDDDDDDDDDDDDA", "    DDD         DDD    ", "   EEEEE       EEEEE   ", "C EEGGGEE C C EEGGGEE C", "  E H H E     E H H E  ", "  E H H E     E H H E  ", "C E H H E C C E H H E C", "  EEGGGEE     EEGGGEE  ", "   EEEEE       EEEEE   ", "    DDD         DDD    ", "     III       III     ", "       IIIAIAIII       ", "          DDD          ")
                    .aisle("ADDDDDDDDDDDDDDDDDDDDDA", "     D           D     ", "   EEEEE       EEEEE   ", "C  EEEEE  C C  EEEEE  C", "   E F E       E F E   ", "   E F E       E F E   ", "C  E F E  C C  E F E  C", "   EEEEE       EEEEE   ", "   EEEEE       EEEEE   ", "                       ", "                       ", "          AIA          ", "          DDD          ")
                    .aisle("ADDDDDDDDDDDDDDDDDDDDDA", "  B  D  B  A  B  D  B  ", "  B     B  A  B     B  ", " CB EEE BC A CB EEE BC ", "  B EEE B  A  B EEE B  ", "  B EEE B  A  B EEE B  ", " CB EEE BC A CB EEE BC ", "  B EEE B  A  B EEE B  ", "  B  E  B  A  B  E  B  ", "           A           ", "           A           ", "          AAA          ", "          DDD          ")
                    .aisle("AADDDDDDDDDDDDDDDDDDDAA", "          AAA          ", "          AAA          ", "  C     C AAA C     C  ", "          AAA          ", "          AAA          ", "  C     C AAA C     C  ", "          AAA          ", "          AAA          ", "          AAA          ", "          AAA          ", "          AAA          ", "          DDD          ")
                    .aisle(" AAAADDDDDDDDDDDDDAAAA ", "     B     A     B     ", "     B     A     B     ", "   CCCCC   A   CCCCC   ", "     B     A     B     ", "     B     A     B     ", "   CCCCC   A   CCCCC   ", "     B     A     B     ", "     B     A     B     ", "           A           ", "           A           ", "          IAI          ", "          DDD          ")
                    .aisle("    AADDDDDDDDDDDAA    ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "          IAI          ", "          DDD          ")
                    .aisle("     ADDDDDDDDDDDA     ", "           B           ", "           B           ", "         CCCCC         ", "           B           ", "           B           ", "         CCCCC         ", "           B           ", "           B           ", "                       ", "                       ", "          IAI          ", "          DDD          ")
                    .aisle("     AADDDDDDDDDAA     ", "                       ", "                       ", "        C     C        ", "                       ", "                       ", "        C     C        ", "                       ", "                       ", "                       ", "                       ", "          IAI          ", "                       ")
                    .aisle("      AADDDDDDDAA      ", "        B  D  B        ", "        B  E  B        ", "       CB EEE BC       ", "        B EEE B        ", "        B EEE B        ", "       CB EEE BC       ", "        B EEE B        ", "        B  E  B        ", "                       ", "                       ", "          IAI          ", "                       ")
                    .aisle("      ADDDDDDDDDA      ", "           D           ", "         EEEEE         ", "      C  EEEEE  C      ", "         E F E         ", "         E F E         ", "      C  E F E  C      ", "         EEEEE         ", "         EEEEE         ", "                       ", "          IAI          ", "          IAI          ", "                       ")
                    .aisle("     AADDDDDDDDDAA     ", "          DDD          ", "         EEEEE         ", "      C EEGGGEE C      ", "        E H H E        ", "        E H H E        ", "      C E H H E C      ", "        EEGGGEE        ", "         EEEEE         ", "          DAD          ", "          IAI          ", "                       ", "                       ")
                    .aisle("     ADDDDDDDDDDDA     ", "      B DDDDDDD B      ", "      B EEEEEEE B      ", "      C EEGGGEE C      ", "      B EF G FE B      ", "      B EF G FE B      ", "      C EF G FE C      ", "      B EEGGGEE B      ", "      B EEEGEEE B      ", "          DGD          ", "          IAI          ", "                       ", "                       ")
                    .aisle("     ADDDDDDDDDDDA     ", "          DDD          ", "         EEEEE         ", "      C EEGGGEE C      ", "        E H H E        ", "        E H H E        ", "      C E H H E C      ", "        EEGGGEE        ", "         EEEEE         ", "          DDD          ", "                       ", "                       ", "                       ")
                    .aisle("     ADDDDDDDDDDDA     ", "           D           ", "         EEEEE         ", "      C  EEEEE  C      ", "         E F E         ", "         E F E         ", "      C  E F E  C      ", "         EEEEE         ", "         EEEEE         ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("     AADDDDDDDDDAA     ", "        B  D  B        ", "        B     B        ", "       CB EEE BC       ", "        B EEE B        ", "        B EEE B        ", "       CB EEE BC       ", "        B EEE B        ", "        B  E  B        ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("      AAADDDDDAAA      ", "                       ", "                       ", "        C     C        ", "                       ", "                       ", "        C     C        ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("        AAAAAAA        ", "          ABA          ", "          ABA          ", "         CCCCC         ", "           B           ", "           B           ", "         CCCCC         ", "           B           ", "           B           ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("          AAA          ", "          AJA          ", "          AAA          ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ")
                    .where("J", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("A", Predicates.blocks(GTLBlocks.SPS_CASING.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.INPUT_LASER).setExactLimit(1)))
                    .where("B", Predicates.blocks(GTLBlocks.DEGENERATE_RHENIUM_CONSTRAINED_CASING.get()))
                    .where("C", Predicates.blocks(GTLBlocks.ADVANCED_FUSION_COIL.get()))
                    .where("D", Predicates.blocks(GTLBlocks.HYPER_MECHANICAL_CASING.get()))
                    .where("E", Predicates.blocks(GTBlocks.CASING_HSSE_STURDY.get()))
                    .where("F", Predicates.blocks(GTLBlocks.HYPER_CORE.get()))
                    .where("G", Predicates.blocks(Registries.getBlock("kubejs:neutronium_gearbox")))
                    .where("H", Predicates.blocks(Registries.getBlock("kubejs:neutronium_pipe_casing")))
                    .where("I", Predicates.blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.HastelloyX)))
                    .where(" ", Predicates.any())
                    .build())
            .workableCasingRenderer(GTLCore.id("block/casings/sps_casing"), GTCEu.id("block/multiblock/gcym/large_centrifuge"))
            .register();

    public final static MultiblockMachineDefinition ADVANCED_VACUUM_DRYING_FURNACE = REGISTRATE.multiblock("advanced_vacuum_drying_furnace", CoilWorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .recipeType(GTLRecipeTypes.VACUUM_DRYING_RECIPES)
            .recipeType(GTLRecipeTypes.DEHYDRATOR_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.a"))
            .tooltips(Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.2"))
            .tooltips(Component.translatable("gtceu.machine.perfect_oc"))
            .tooltips(Component.translatable("gtceu.multiblock.laser.tooltip"))
            .tooltips(Component.translatable("gtceu.multiblock.coil_parallel"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_2.tooltip",
                    Component.translatable("gtceu.vacuum_drying"), Component.translatable("gtceu.dehydrator")))
            .tooltipBuilder(GTLMachines.GTL_ADD)
            .appearanceBlock(GTBlocks.HIGH_POWER_CASING)
            .recipeModifiers((machine, recipe, params, result) -> {
                if (machine instanceof CoilWorkableElectricMultiblockMachine coilMachine) {
                    GTRecipe recipe1 = GTRecipeModifiers.accurateParallel(coilMachine, recipe, (int) Math.min(2147483647, Math.pow(2, coilMachine.getCoilType().getCoilTemperature() / 900D)), false).getFirst();
                    if (recipe1 != null) {
                        return RecipeHelper.applyOverclock(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK, recipe1, coilMachine.getOverclockVoltage(), params, result);
                    }
                }
                return null;
            }, GTRecipeModifiers::ebfOverclock)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("          AAAAAAA  ", "   BBBBBBBBBBBBBB  ", "   B         C     ", "           CCCCC   ", "          CCCCCCC  ", "         CCCCCCCCC ", "         CCCDDDCCC ", "        CCCCDDDCCCC", "         CCCDDDCCC ", "         CCCCCCCCC ", "          CCCCCCC  ", "           CCCCC   ", "             C     ", "                   ")
                    .aisle("CCCCCCC   A CCC A  ", "  GGG       CCC B  ", "  GBG      FFFFF   ", "  GGG     FFFFFFF  ", "  GGG    FFFFFFFFF ", "  GGG   FFFFFFFFFFF", "  GGG   FFFFFFFFFFF", "  GGG   FFFFFFFFFFF", "  GGG   FFFFFFFFFFF", "   C    FFFFFFFFFFF", "         FFFFFFFFF ", "          FFFFFFF  ", "           FFFFF   ", "                   ")
                    .aisle("CCCCCCC   A CCC A  ", " GGGGG      CCC B  ", " G B G     GGGGG   ", " G   G    GGHIHGG  ", " G   G   GG     GG ", " G   G  GG       GG", " G   G  GH       HG", " G   G  GI       IG", " G   G  GH       HG", " CCCCC  GG       GG", "   C     GG     GG ", "          GGHIHGG  ", "           GGGGG   ", "                   ")
                    .aisle("CCCCCCC   A CCC A  ", "GGGGGGG     CCC B  ", "G  B  G    GGGGG   ", "G     G   GGHIHGG  ", "G     G  GG     GG ", "G     G GG       GG", "G     G GH       HG", "G     G GI       IG", "G     G GH       HG", " C   C  GG       GG", "  CCC    GG     GG ", "  JJJ     GGHIHGGA ", "           GGGGG A ", "          AAAAAAAA ")
                    .aisle("CCCCCCC   A CCC A  ", "GGGGGGG     CCC B  ", "G  B  G    GGGGG   ", "G  B  G   GGHIHGG  ", "G  B  G  GG     GG ", "G  B  G GG       GG", "G  B  G GH       HG", "G  B  G GI       IG", "G  B  G GH       HG", "CC B CC GG       GG", " CCBCC   GG     GG ", "  JBJ     GGHIHGGA ", "   BBBBBBBBGGGGG   ", "          A        ")
                    .aisle("CCCCCCC   A CCC A  ", "GGGGGGG     CCC B  ", "G  B  G    GGGGG   ", "G  B  G   GGHIHGG  ", "G  B  G  GG     GG ", "G  B  G GG       GG", "G  B  G GH       HG", "G  B  G GI       IG", "G  B  G GH       HG", "CC B CC GG       GG", " CCBCC   GG     GG ", "  JBJ     GGHIHGGA ", "   BBBBBBBBGGGGG A ", "          AAAAAAAA ")
                    .aisle("CCCCCCC   A CCC A  ", "GGGGGGG     CCC B  ", "G     G    GGGGG   ", "G     G   GGHIHGG  ", "G     G  GG     GG ", "G     G GG       GG", "G     G GH       HG", "G     G GI       IG", "G     G GH       HG", " C   C  GG       GG", "  CCC    GG     GG ", "  JJJ     GGHIHGGA ", "          BGGGGG   ", "          A        ")
                    .aisle("CCCCCCC   A CCC A  ", " GGGGG      CCC B  ", " G   G     GGGGG   ", " G   G    GGHIHGG  ", " G   G   GG     GG ", " G   G  GG       GG", " G   G  GH       HG", " G   G  GI       IG", " G   G  GH       HG", " CCCCC  GG       GG", "   C     GG     GG ", "          GGHIHGGA ", "          BGGGGG A ", "          AAAAAAAA ")
                    .aisle("CCCCCCC   A CCC A  ", "  GGG       CCC B  ", "  GGG      GGGGGB  ", "  GGG     GGHIHGB  ", "  GGG    GG     BG ", "  GGG   GG       GG", "  GGG   GH       HG", "  GGG   GI       IG", "  GGG   GH       HG", "   C    GG       GG", "         GG     GG ", "          GGHIHGGA ", "          BGGGGG   ", "          A        ")
                    .aisle("          A CCC A  ", "            CCC    ", "           GGGGG   ", "          GGHIHGG  ", "         GG     GG ", "        GG       GG", "        GH       HG", "        GI       IG", "        GH       HG", "        GG       GG", "         GG     GG ", "          GGHIHGGA ", "          BGGGGG A ", "          AAAAAAAA ")
                    .aisle("          A CCC A  ", "            CCC    ", "           GGGGG   ", "          GGHIHGG  ", "         GG     GG ", "        GG       GG", "        GH       HG", "        GI       IG", "        GH       HG", "        GG       GG", "         GG     GG ", "          GGHIHGGA ", "          BGGGGG   ", "          A        ")
                    .aisle("CCCCCCC   A CCC A  ", "GGGGGGG     CCC    ", "GGGGGGG    GGGGG   ", "GGGGGGG   GGHIHGG  ", "GGGGGGG  GG     GG ", "GGGGGGG GG       GG", "GGGGGGG HH       HH", "GGGGGGG GI       IG", "GGGGGGG HH       HH", "GGGGGGG GG       GG", "         GG     GG ", "          GGHIHGGA ", "          BGGGGG A ", "          AAAAAAAA ")
                    .aisle("CCCCCCC   A CCC A  ", "GGGGGGG     CCC A  ", "G     G    GGGGGA  ", "G     G   GGHIHGA  ", "G     G  GG     AG ", "G     G GG       GG", "G     G HH       HH", "G     G GI       IG", "G     G HH       HH", "GGGGGGG GG       AG", "  JJJ    GG     GA ", "          GGHIHGGA ", "          BGGGGG   ", "          A        ")
                    .aisle("CCCCCCC   A CCC    ", "GGGGGGG     CCC    ", "G     G    GGGGG   ", "G     G   GGHIHGG  ", "G     G  GG     GG ", "G     G GG       GG", "G     G HH       HH", "G     G GI       IG", "G     G HH       HH", "GGGAGGG GG       GG", "  JAJ    GG     GG ", "   A      GGHIHGG  ", "   A      BGGGGG   ", "   AAAAAAAA        ")
                    .aisle("CCCCCCCAAAA CCC    ", "GGGGGGGA    CCC    ", "G   AAAA   FFFFF   ", "G     G   FGHIHGF  ", "G     G  FG     GF ", "G     G FG       GF", "G     G FH       HF", "G     G FI       IF", "G     G FH       HF", "GGGGGGG FG       GF", "  JJJ    FG     GF ", "          FGHIHGF  ", "          BFFFFF   ", "          A        ")
                    .aisle("CCCCCCC  CCCCCCCCC ", "GGGGGGG  CCCCCCCCC ", "G     G  CCGGGGGCC ", "G     G  CGGHIHGGC ", "G     G  GG     GG ", "G     G GG       GG", "G     G HH       HH", "G     G GI       IG", "G     G HH       HH", "GGGAGGG GG       GG", "  JAJ    GG     GG ", "   A      GGHIHGG  ", "   A      BGGGGG   ", "   AAAAAAAA        ")
                    .aisle("CCCCCCC  CCCCCCCCC ", "GGGGGGG  CCCCCCCCC ", "G     G  CCGGGGGCC ", "G     G  CGGHIHGGC ", "G     G  GG     GG ", "G     G GG       GG", "G     G HH       HH", "G     G GI       IG", "G     G HH       HH", "GGGGGGG GG       GG", "  JJJ    GG     GG ", "          GGHIHGG  ", "          BGGGGG   ", "                   ")
                    .aisle("CCCCCCC  CCCCCCCCC ", "GGGGGGG  CCCCCCCCC ", "GGGGGGG  CCGGGGGCC ", "GGGGGGG  CGGHIHGGC ", "GGGGGGG  GG     GG ", "GGGGGGG GG       GG", "GGGGGGG HH       HH", "GGGGGGG GI       IG", "GGGGGGG HH       HH", "GGGGGGG GG       GG", "         GG     GG ", "          GGHIHGG  ", "          BGGGGG   ", "                   ")
                    .aisle("         CCCCCCCCC ", "         CCCCCCCCC ", "         CCGGGGGCC ", "         CGGHIHGGC ", "         GG     GG ", "        GG       GG", "        GH       HG", "        GI       IG", "        GH       HG", "        GG       GG", "         GG     GG ", "          GGHIHGG  ", "          BGGGGG   ", "                   ")
                    .aisle("         CCCCCCCCC ", "         CCCCCCCCC ", "         CCGGGGGCC ", "         CGGHIHGGC ", "         GG     GG ", "        GG       GG", "        GH       HG", "        GI       IG", "        GH       HG", "        GG       GG", "         GG     GG ", "          GGHIHGG  ", "          BGGGGG   ", "          BBBB     ")
                    .aisle("         CCCCCCCCC ", "         CCCCCCCCC ", "         CCGGGGGCC ", "         CGGHIHGGC ", "         GG     GG ", "        GG       GG", "        GH       HG", "        GI       IG", "        GH       HG", "        GG       GG", "         GG     GG ", "          GGHIHGG  ", "           GGGGG   ", "             B     ")
                    .aisle("         CCCCCCCCC ", "         CCCCCCCCC ", "         CCGGGGGCC ", "         CGGHIHGGC ", "         GG     GG ", "        GG       GG", "        GH       HG", "        GI       IG", "        GH       HG", "        GG       GG", "         GG     GG ", "          GGHIHGG  ", "           GGGGG   ", "             B     ")
                    .aisle("  CCCCC  CCCCCCCCC ", "  KKKKK  CCCCCCCCC ", "  KKKKK  CCGGGGGCC ", "  KKKKK  CGGHIHGGC ", "  KKKKK  GG     GG ", "  KKKKK GG       GG", "  KKKKK GH       HG", "        GI       IG", "        GH       HG", "        GG       GG", "         GG     GG ", "          GGHIHGG  ", "           GGGGG   ", "             B     ")
                    .aisle("  CCCCC  CCCCCCCCC ", "  KKKKK  CCCCCCCCC ", "  K   K  CCGGGGGCC ", "  L   L  CGGHIHGGC ", "  L   L  GG H H GG ", "  L   L GG  H H  GG", "  KKKKK GHHHHHHHHHG", "        GI  H H  IG", "        GHHHHHHHHHG", "        GG  H H  GG", "         GG H H GG ", "          GGHIHGG  ", "           GGBGG   ", "             B     ")
                    .aisle("  CCCCC            ", "  KKKKK            ", "  KMMMK    FFFFF   ", "  KMNMK   FFOOOFF  ", "  KMMMK  FFO   OFF ", "  KMMMK FFO     OFF", "  KKKKK PO       OF", "        PO       OF", "        PO       OF", "        FFO     OFF", "         FFO   OFF ", "          FFOOOFF  ", "           FFFFF   ", "                   ")
                    .aisle("                   ", "                   ", "            F F    ", "           CCCCC   ", "          CCCCCCC  ", "         CCCCCCCCC ", "        MCCCQQQCCCF", "        MCCCQQQCCC ", "        MCCCQQQCCCF", "         CCCCCCCCC ", "          CCCCCCC  ", "           CCCCC   ", "            F F    ", "                   ")
                    .aisle("                   ", "                   ", "                   ", "                   ", "                   ", "                   ", "        MMM        ", "        MMM        ", "        MMM        ", "                   ", "                   ", "                   ", "                   ", "                   ")
                    .where("N", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("A", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_TURBINE.get()))
                    .where("B", Predicates.blocks(GTBlocks.CASING_STAINLESS_TURBINE.get()))
                    .where("C", Predicates.blocks(GTLBlocks.IRIDIUM_CASING.get()))
                    .where("D", Predicates.blocks(GTBlocks.CASING_GRATE.get()))
                    .where("E", Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()))
                    .where("F", Predicates.blocks(Registries.getBlock("kubejs:red_steel_casing")))
                    .where("G", Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()))
                    .where("H", Predicates.heatingCoils())
                    .where("I", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where("J", Predicates.blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.Tungsten)))
                    .where("K", Predicates.blocks(GTBlocks.COMPUTER_CASING.get()))
                    .where("L", Predicates.blocks(GTBlocks.COMPUTER_HEAT_VENT.get()))
                    .where("M", Predicates.blocks(GTBlocks.HIGH_POWER_CASING.get())
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(4))
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(4))
                            .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.INPUT_LASER).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("O", Predicates.blocks(GTBlocks.HERMETIC_CASING_LuV.get()))
                    .where("P", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX.get()))
                    .where("Q", Predicates.blocks(GTBlocks.FILTER_CASING.get()))
                    .build())
            .additionalDisplay((controller, components) -> {
                if (controller instanceof CoilWorkableElectricMultiblockMachine coilMachine && controller.isFormed()) {
                    components.add(Component.translatable("gtceu.multiblock.parallel", Component.literal(FormattingUtil.formatNumbers(Math.min(2147483647, (int) Math.pow(2, ((double) coilMachine.getCoilType().getCoilTemperature() / 900))))).withStyle(ChatFormatting.DARK_PURPLE)).withStyle(ChatFormatting.GRAY));
                    components.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature",
                            Component.translatable(FormattingUtil.formatNumbers(coilMachine.getCoilType().getCoilTemperature() + 100L * Math.max(0, coilMachine.getTier() - GTValues.MV)) + "K")
                                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))));
                }
            })
            .workableCasingRenderer(GTCEu.id("block/casings/hpca/high_power_casing"), GTCEu.id("block/multiblock/fusion_reactor"))
            .register();
}
