package eu.pintergabor.fluidpipes.registry;

import static eu.pintergabor.fluidpipes.registry.util.ModBlocksRegister.*;

import java.util.Arrays;
import java.util.stream.Stream;

import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.world.level.material.MapColor;


public final class ModBlocks {
	// Wooden pipes.
	public static final DeferredBlock<FluidPipe> OAK_PIPE =
		registerWoodenPipe("oak_pipe", MapColor.WOOD,
			1F, 1F, FluidBlockSettings.UNSTABLE_UNI);
	public static final DeferredBlock<FluidPipe> SPRUCE_PIPE =
		registerWoodenPipe("spruce_pipe", MapColor.PODZOL,
			1F, 1F, FluidBlockSettings.FLAMMABLE_UNI);
	public static final DeferredBlock<FluidPipe> BIRCH_PIPE =
		registerWoodenPipe("birch_pipe", MapColor.SAND,
			1F, 1F, FluidBlockSettings.AVERAGE_WATER);
	public static final DeferredBlock<FluidPipe> JUNGLE_PIPE =
		registerWoodenPipe("jungle_pipe", MapColor.DIRT,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final DeferredBlock<FluidPipe> ACACIA_PIPE =
		registerWoodenPipe("acacia_pipe", MapColor.COLOR_ORANGE,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final DeferredBlock<FluidPipe> CHERRY_PIPE =
		registerWoodenPipe("cherry_pipe", MapColor.TERRACOTTA_WHITE,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final DeferredBlock<FluidPipe> DARK_OAK_PIPE =
		registerWoodenPipe("dark_oak_pipe", MapColor.COLOR_BROWN,
			1F, 1F, FluidBlockSettings.STABLE_UNI);
	public static final DeferredBlock<FluidPipe> PALE_OAK_PIPE =
		registerWoodenPipe("pale_oak_pipe", MapColor.QUARTZ,
			1F, 1F, FluidBlockSettings.UNSTABLE_UNI);
	public static final DeferredBlock<FluidPipe> MANGROVE_PIPE =
		registerWoodenPipe("mangrove_pipe", MapColor.COLOR_RED,
			1F, 1F, FluidBlockSettings.DRIPPING_WATER);
	public static final DeferredBlock<FluidPipe> BAMBOO_PIPE =
		registerWoodenPipe("bamboo_pipe", MapColor.COLOR_YELLOW,
			0.5F, 0.5F, FluidBlockSettings.GOOD_WATER);
	public static DeferredBlock<FluidPipe>[] WOODEN_PIPES;
	// Wooden fittings.
	public static final DeferredBlock<FluidFitting> OAK_FITTING =
		registerWoodenFitting("oak_fitting", MapColor.WOOD,
			1F, 1F, FluidBlockSettings.UNSTABLE_UNI);
	public static final DeferredBlock<FluidFitting> SPRUCE_FITTING =
		registerWoodenFitting("spruce_fitting", MapColor.PODZOL,
			1F, 1F, FluidBlockSettings.FLAMMABLE_UNI);
	public static final DeferredBlock<FluidFitting> BIRCH_FITTING =
		registerWoodenFitting("birch_fitting", MapColor.SAND,
			1F, 1F, FluidBlockSettings.AVERAGE_WATER);
	public static final DeferredBlock<FluidFitting> JUNGLE_FITTING =
		registerWoodenFitting("jungle_fitting", MapColor.DIRT,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final DeferredBlock<FluidFitting> ACACIA_FITTING =
		registerWoodenFitting("acacia_fitting", MapColor.COLOR_ORANGE,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final DeferredBlock<FluidFitting> CHERRY_FITTING =
		registerWoodenFitting("cherry_fitting", MapColor.TERRACOTTA_WHITE,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final DeferredBlock<FluidFitting> DARK_OAK_FITTING =
		registerWoodenFitting("dark_oak_fitting", MapColor.COLOR_BROWN,
			1F, 1F, FluidBlockSettings.STABLE_UNI);
	public static final DeferredBlock<FluidFitting> PALE_OAK_FITTING =
		registerWoodenFitting("pale_oak_fitting", MapColor.QUARTZ,
			1F, 1F, FluidBlockSettings.UNSTABLE_UNI);
	public static final DeferredBlock<FluidFitting> MANGROVE_FITTING =
		registerWoodenFitting("mangrove_fitting", MapColor.COLOR_RED,
			1F, 1F, FluidBlockSettings.DRIPPING_WATER);
	public static final DeferredBlock<FluidFitting> BAMBOO_FITTING =
		registerWoodenFitting("bamboo_fitting", MapColor.COLOR_YELLOW,
			0.5F, 0.5F, FluidBlockSettings.GOOD_WATER);
	public static DeferredBlock<FluidFitting>[] WOODEN_FITTINGS;
	// Stone pipes.
	public static final DeferredBlock<FluidPipe> STONE_PIPE =
		registerStonePipe("stone_pipe", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.USELESS_UNI);
	public static final DeferredBlock<FluidPipe> DEEPSLATE_PIPE =
		registerStonePipe("deepslate_pipe", MapColor.DEEPSLATE,
			1.8F, 6F, FluidBlockSettings.GOOD_LAVA);
	public static final DeferredBlock<FluidPipe> ANDESITE_PIPE =
		registerStonePipe("andesite_pipe", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.BAD_LAVA);
	public static final DeferredBlock<FluidPipe> DIORITE_PIPE =
		registerStonePipe("diorite_pipe", MapColor.QUARTZ,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final DeferredBlock<FluidPipe> GRANITE_PIPE =
		registerStonePipe("granite_pipe", MapColor.DIRT,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final DeferredBlock<FluidPipe> BASALT_PIPE =
		registerStonePipe("basalt_pipe", MapColor.COLOR_BLACK,
			0.75F, 3F, FluidBlockSettings.GOOD_LAVA);
	public static final DeferredBlock<FluidPipe> SANDSTONE_PIPE =
		registerStonePipe("sandstone_pipe", MapColor.SAND,
			0.75F, 3F, FluidBlockSettings.USELESS_UNI);
	public static final DeferredBlock<FluidPipe> TUFF_PIPE =
		registerStonePipe("tuff_pipe", MapColor.TERRACOTTA_GRAY,
			0.75F, 3F, FluidBlockSettings.DRIPPING_LAVA);
	public static final DeferredBlock<FluidPipe> OBSIDIAN_PIPE =
		registerStonePipe("obsidian_pipe", MapColor.COLOR_BLACK,
			25F, 1200F, FluidBlockSettings.GOOD_LAVA);
	public static final DeferredBlock<FluidPipe> NETHERRACK_PIPE =
		registerStonePipe("netherrack_pipe", MapColor.NETHER,
			0.2F, 0.4F, FluidBlockSettings.FLAMMABLE_LAVA);
	public static DeferredBlock<FluidPipe>[] STONE_PIPES;
	// Stone fittings.
	public static final DeferredBlock<FluidFitting> STONE_FITTING =
		registerStoneFitting("stone_fitting", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.USELESS_UNI);
	public static final DeferredBlock<FluidFitting> DEEPSLATE_FITTING =
		registerStoneFitting("deepslate_fitting", MapColor.DEEPSLATE,
			1.8F, 6F, FluidBlockSettings.GOOD_LAVA);
	public static final DeferredBlock<FluidFitting> ANDESITE_FITTING =
		registerStoneFitting("andesite_fitting", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.BAD_LAVA);
	public static final DeferredBlock<FluidFitting> DIORITE_FITTING =
		registerStoneFitting("diorite_fitting", MapColor.QUARTZ,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final DeferredBlock<FluidFitting> GRANITE_FITTING =
		registerStoneFitting("granite_fitting", MapColor.DIRT,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final DeferredBlock<FluidFitting> BASALT_FITTING =
		registerStoneFitting("basalt_fitting", MapColor.COLOR_BLACK,
			0.75F, 3F, FluidBlockSettings.GOOD_LAVA);
	public static final DeferredBlock<FluidFitting> SANDSTONE_FITTING =
		registerStoneFitting("sandstone_fitting", MapColor.SAND,
			0.75F, 3F, FluidBlockSettings.USELESS_UNI);
	public static final DeferredBlock<FluidFitting> TUFF_FITTING =
		registerStoneFitting("tuff_fitting", MapColor.TERRACOTTA_GRAY,
			0.75F, 3F, FluidBlockSettings.DRIPPING_LAVA);
	public static final DeferredBlock<FluidFitting> OBSIDIAN_FITTING =
		registerStoneFitting("obsidian_fitting", MapColor.COLOR_BLACK,
			25F, 1200F, FluidBlockSettings.GOOD_LAVA);
	public static final DeferredBlock<FluidFitting> NETHERRACK_FITTING =
		registerStoneFitting("netherrack_fitting", MapColor.NETHER,
			0.2F, 0.4F, FluidBlockSettings.FLAMMABLE_LAVA);
	public static DeferredBlock<FluidFitting>[] STONE_FITTINGS;
	// All pipes.
	public static DeferredBlock<FluidPipe>[] PIPES;
	// All fittings.
	public static DeferredBlock<FluidFitting>[] FITTINGS;

	@SuppressWarnings("unchecked")
	private static void initPipes() {
		WOODEN_PIPES = new DeferredBlock[]{
			OAK_PIPE,
			SPRUCE_PIPE,
			BIRCH_PIPE,
			JUNGLE_PIPE,
			ACACIA_PIPE,
			CHERRY_PIPE,
			DARK_OAK_PIPE,
			PALE_OAK_PIPE,
			MANGROVE_PIPE,
			BAMBOO_PIPE,
		};
		STONE_PIPES = new DeferredBlock[]{
			STONE_PIPE,
			DEEPSLATE_PIPE,
			ANDESITE_PIPE,
			DIORITE_PIPE,
			GRANITE_PIPE,
			BASALT_PIPE,
			SANDSTONE_PIPE,
			TUFF_PIPE,
			OBSIDIAN_PIPE,
			NETHERRACK_PIPE,
		};
		PIPES = Stream.concat(
			Arrays.stream(WOODEN_PIPES), Arrays.stream(STONE_PIPES)
		).toArray(DeferredBlock[]::new);
	}

	@SuppressWarnings("unchecked")
	private static void initFittings() {
		WOODEN_FITTINGS = new DeferredBlock[]{
			OAK_FITTING,
			SPRUCE_FITTING,
			BIRCH_FITTING,
			JUNGLE_FITTING,
			ACACIA_FITTING,
			CHERRY_FITTING,
			DARK_OAK_FITTING,
			PALE_OAK_FITTING,
			MANGROVE_FITTING,
			BAMBOO_FITTING,
		};
		STONE_FITTINGS = new DeferredBlock[]{
			STONE_FITTING,
			DEEPSLATE_FITTING,
			ANDESITE_FITTING,
			DIORITE_FITTING,
			GRANITE_FITTING,
			BASALT_FITTING,
			SANDSTONE_FITTING,
			TUFF_FITTING,
			OBSIDIAN_FITTING,
			NETHERRACK_FITTING,
		};
		FITTINGS = Stream.concat(
			Arrays.stream(WOODEN_FITTINGS), Arrays.stream(STONE_FITTINGS)
		).toArray(DeferredBlock[]::new);
	}

	/**
	 * Create and register everything that was not done by static initializers.
	 */
	public static void init() {
		initPipes();
		initFittings();
	}
}
