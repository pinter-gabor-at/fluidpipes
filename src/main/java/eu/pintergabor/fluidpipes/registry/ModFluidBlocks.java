package eu.pintergabor.fluidpipes.registry;

import static eu.pintergabor.fluidpipes.registry.ModFluidBlocksRegister.*;

import java.util.List;
import java.util.stream.Stream;

import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;

import net.minecraft.world.level.material.MapColor;


/**
 * Register and store all fluid pipes and fittings.
 */
public final class ModFluidBlocks {
	// Wooden pipes.
	public static final ModBlockHolder<FluidPipe> OAK_PIPE =
		registerWoodenPipe("oak_pipe", MapColor.WOOD,
			1F, 1F, FluidBlockSettings.UNSTABLE_UNI);
	public static final ModBlockHolder<FluidPipe> SPRUCE_PIPE =
		registerWoodenPipe("spruce_pipe", MapColor.PODZOL,
			1F, 1F, FluidBlockSettings.FLAMMABLE_UNI);
	public static final ModBlockHolder<FluidPipe> BIRCH_PIPE =
		registerWoodenPipe("birch_pipe", MapColor.SAND,
			1F, 1F, FluidBlockSettings.AVERAGE_WATER);
	public static final ModBlockHolder<FluidPipe> JUNGLE_PIPE =
		registerWoodenPipe("jungle_pipe", MapColor.DIRT,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final ModBlockHolder<FluidPipe> ACACIA_PIPE =
		registerWoodenPipe("acacia_pipe", MapColor.COLOR_ORANGE,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final ModBlockHolder<FluidPipe> CHERRY_PIPE =
		registerWoodenPipe("cherry_pipe", MapColor.TERRACOTTA_WHITE,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final ModBlockHolder<FluidPipe> DARK_OAK_PIPE =
		registerWoodenPipe("dark_oak_pipe", MapColor.COLOR_BROWN,
			1F, 1F, FluidBlockSettings.STABLE_UNI);
	public static final ModBlockHolder<FluidPipe> PALE_OAK_PIPE =
		registerWoodenPipe("pale_oak_pipe", MapColor.QUARTZ,
			1F, 1F, FluidBlockSettings.UNSTABLE_UNI);
	public static final ModBlockHolder<FluidPipe> MANGROVE_PIPE =
		registerWoodenPipe("mangrove_pipe", MapColor.COLOR_RED,
			1F, 1F, FluidBlockSettings.DRIPPING_WATER);
	public static final ModBlockHolder<FluidPipe> BAMBOO_PIPE =
		registerWoodenPipe("bamboo_pipe", MapColor.COLOR_YELLOW,
			0.5F, 0.5F, FluidBlockSettings.GOOD_WATER);
	public static final List<ModBlockHolder<FluidPipe>> WOODEN_PIPES = List.of(
		OAK_PIPE,
		SPRUCE_PIPE,
		BIRCH_PIPE,
		JUNGLE_PIPE,
		ACACIA_PIPE,
		CHERRY_PIPE,
		DARK_OAK_PIPE,
		PALE_OAK_PIPE,
		MANGROVE_PIPE,
		BAMBOO_PIPE
	);


	// Wooden fittings.
	public static final ModBlockHolder<FluidFitting> OAK_FITTING =
		registerFitting("oak_fitting", OAK_PIPE.block());
	public static final ModBlockHolder<FluidFitting> SPRUCE_FITTING =
		registerFitting("spruce_fitting", SPRUCE_PIPE.block());
	public static final ModBlockHolder<FluidFitting> BIRCH_FITTING =
		registerFitting("birch_fitting", BIRCH_PIPE.block());
	public static final ModBlockHolder<FluidFitting> JUNGLE_FITTING =
		registerFitting("jungle_fitting", JUNGLE_PIPE.block());
	public static final ModBlockHolder<FluidFitting> ACACIA_FITTING =
		registerFitting("acacia_fitting", ACACIA_PIPE.block());
	public static final ModBlockHolder<FluidFitting> CHERRY_FITTING =
		registerFitting("cherry_fitting", CHERRY_PIPE.block());
	public static final ModBlockHolder<FluidFitting> DARK_OAK_FITTING =
		registerFitting("dark_oak_fitting", DARK_OAK_PIPE.block());
	public static final ModBlockHolder<FluidFitting> PALE_OAK_FITTING =
		registerFitting("pale_oak_fitting", PALE_OAK_PIPE.block());
	public static final ModBlockHolder<FluidFitting> MANGROVE_FITTING =
		registerFitting("mangrove_fitting", MANGROVE_PIPE.block());
	public static final ModBlockHolder<FluidFitting> BAMBOO_FITTING =
		registerFitting("bamboo_fitting", BAMBOO_PIPE.block());
	public static final List<ModBlockHolder<FluidFitting>> WOODEN_FITTINGS = List.of(
		OAK_FITTING,
		SPRUCE_FITTING,
		BIRCH_FITTING,
		JUNGLE_FITTING,
		ACACIA_FITTING,
		CHERRY_FITTING,
		DARK_OAK_FITTING,
		PALE_OAK_FITTING,
		MANGROVE_FITTING,
		BAMBOO_FITTING
	);
	// Stone pipes.
	public static final ModBlockHolder<FluidPipe> STONE_PIPE =
		registerStonePipe("stone_pipe", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.USELESS_UNI);
	public static final ModBlockHolder<FluidPipe> DEEPSLATE_PIPE =
		registerStonePipe("deepslate_pipe", MapColor.DEEPSLATE,
			1.8F, 6F, FluidBlockSettings.GOOD_LAVA);
	public static final ModBlockHolder<FluidPipe> ANDESITE_PIPE =
		registerStonePipe("andesite_pipe", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.BAD_LAVA);
	public static final ModBlockHolder<FluidPipe> DIORITE_PIPE =
		registerStonePipe("diorite_pipe", MapColor.QUARTZ,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final ModBlockHolder<FluidPipe> GRANITE_PIPE =
		registerStonePipe("granite_pipe", MapColor.DIRT,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final ModBlockHolder<FluidPipe> BASALT_PIPE =
		registerStonePipe("basalt_pipe", MapColor.COLOR_BLACK,
			0.75F, 3F, FluidBlockSettings.GOOD_LAVA);
	public static final ModBlockHolder<FluidPipe> SANDSTONE_PIPE =
		registerStonePipe("sandstone_pipe", MapColor.SAND,
			0.75F, 3F, FluidBlockSettings.USELESS_UNI);
	public static final ModBlockHolder<FluidPipe> TUFF_PIPE =
		registerStonePipe("tuff_pipe", MapColor.TERRACOTTA_GRAY,
			0.75F, 3F, FluidBlockSettings.DRIPPING_LAVA);
	public static final ModBlockHolder<FluidPipe> OBSIDIAN_PIPE =
		registerStonePipe("obsidian_pipe", MapColor.COLOR_BLACK,
			25F, 1200F, FluidBlockSettings.GOOD_LAVA);
	public static final ModBlockHolder<FluidPipe> NETHERRACK_PIPE =
		registerStonePipe("netherrack_pipe", MapColor.NETHER,
			0.2F, 0.4F, FluidBlockSettings.FLAMMABLE_LAVA);
	public static final List<ModBlockHolder<FluidPipe>> STONE_PIPES = List.of(
		STONE_PIPE,
		DEEPSLATE_PIPE,
		ANDESITE_PIPE,
		DIORITE_PIPE,
		GRANITE_PIPE,
		BASALT_PIPE,
		SANDSTONE_PIPE,
		TUFF_PIPE,
		OBSIDIAN_PIPE,
		NETHERRACK_PIPE
	);
	// Stone fittings.
	public static final ModBlockHolder<FluidFitting> STONE_FITTING =
		registerFitting("stone_fitting", STONE_PIPE.block());
	public static final ModBlockHolder<FluidFitting> DEEPSLATE_FITTING =
		registerFitting("deepslate_fitting", DEEPSLATE_PIPE.block());
	public static final ModBlockHolder<FluidFitting> ANDESITE_FITTING =
		registerFitting("andesite_fitting", ANDESITE_PIPE.block());
	public static final ModBlockHolder<FluidFitting> DIORITE_FITTING =
		registerFitting("diorite_fitting", DIORITE_PIPE.block());
	public static final ModBlockHolder<FluidFitting> GRANITE_FITTING =
		registerFitting("granite_fitting", GRANITE_PIPE.block());
	public static final ModBlockHolder<FluidFitting> BASALT_FITTING =
		registerFitting("basalt_fitting", BASALT_PIPE.block());
	public static final ModBlockHolder<FluidFitting> SANDSTONE_FITTING =
		registerFitting("sandstone_fitting", SANDSTONE_PIPE.block());
	public static final ModBlockHolder<FluidFitting> TUFF_FITTING =
		registerFitting("tuff_fitting", TUFF_PIPE.block());
	public static final ModBlockHolder<FluidFitting> OBSIDIAN_FITTING =
		registerFitting("obsidian_fitting", OBSIDIAN_PIPE.block());
	public static final ModBlockHolder<FluidFitting> NETHERRACK_FITTING =
		registerFitting("netherrack_fitting", NETHERRACK_PIPE.block());
	public static final List<ModBlockHolder<FluidFitting>> STONE_FITTINGS = List.of(
		STONE_FITTING,
		DEEPSLATE_FITTING,
		ANDESITE_FITTING,
		DIORITE_FITTING,
		GRANITE_FITTING,
		BASALT_FITTING,
		SANDSTONE_FITTING,
		TUFF_FITTING,
		OBSIDIAN_FITTING,
		NETHERRACK_FITTING
	);
	// All fluid pipes.
	public static final List<ModBlockHolder<FluidPipe>> FLUID_PIPES =
		Stream.concat(
			WOODEN_PIPES.stream(), STONE_PIPES.stream()
		).toList();
	// All fluid fittings.
	public static final List<ModBlockHolder<FluidFitting>> FLUID_FITTINGS =
		Stream.concat(
			WOODEN_FITTINGS.stream(), STONE_FITTINGS.stream()
		).toList();

	/**
	 * Create and register everything that was not done by static initializers.
	 */
	public static void init() {
		// Everything has been done by static initializers.
	}
}
