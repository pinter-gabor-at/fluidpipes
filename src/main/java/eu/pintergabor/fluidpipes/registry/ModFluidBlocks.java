package eu.pintergabor.fluidpipes.registry;

import static eu.pintergabor.fluidpipes.registry.util.ModBlocksRegister.*;

import java.util.Arrays;
import java.util.stream.Stream;

import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;

import net.minecraft.world.level.material.MapColor;


public final class ModFluidBlocks {
	// Wooden pipes.
	public static final FluidPipe OAK_PIPE =
		registerWoodenPipe("oak_pipe", MapColor.WOOD,
			1F, 1F, FluidBlockSettings.UNSTABLE_UNI);
	public static final FluidPipe SPRUCE_PIPE =
		registerWoodenPipe("spruce_pipe", MapColor.PODZOL,
			1F, 1F, FluidBlockSettings.FLAMMABLE_UNI);
	public static final FluidPipe BIRCH_PIPE =
		registerWoodenPipe("birch_pipe", MapColor.SAND,
			1F, 1F, FluidBlockSettings.AVERAGE_WATER);
	public static final FluidPipe JUNGLE_PIPE =
		registerWoodenPipe("jungle_pipe", MapColor.DIRT,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final FluidPipe ACACIA_PIPE =
		registerWoodenPipe("acacia_pipe", MapColor.COLOR_ORANGE,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final FluidPipe CHERRY_PIPE =
		registerWoodenPipe("cherry_pipe", MapColor.TERRACOTTA_WHITE,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final FluidPipe DARK_OAK_PIPE =
		registerWoodenPipe("dark_oak_pipe", MapColor.COLOR_BROWN,
			1F, 1F, FluidBlockSettings.STABLE_UNI);
	public static final FluidPipe PALE_OAK_PIPE =
		registerWoodenPipe("pale_oak_pipe", MapColor.QUARTZ,
			1F, 1F, FluidBlockSettings.UNSTABLE_UNI);
	public static final FluidPipe MANGROVE_PIPE =
		registerWoodenPipe("mangrove_pipe", MapColor.COLOR_RED,
			1F, 1F, FluidBlockSettings.DRIPPING_WATER);
	public static final FluidPipe BAMBOO_PIPE =
		registerWoodenPipe("bamboo_pipe", MapColor.COLOR_YELLOW,
			0.5F, 0.5F, FluidBlockSettings.GOOD_WATER);
	public static final FluidPipe[] WOODEN_PIPES = {
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
	// Wooden fittings.
	public static final FluidFitting OAK_FITTING =
		registerFitting("oak_fitting", OAK_PIPE);
	public static final FluidFitting SPRUCE_FITTING =
		registerFitting("spruce_fitting", SPRUCE_PIPE);
	public static final FluidFitting BIRCH_FITTING =
		registerFitting("birch_fitting", BIRCH_PIPE);
	public static final FluidFitting JUNGLE_FITTING =
		registerFitting("jungle_fitting", JUNGLE_PIPE);
	public static final FluidFitting ACACIA_FITTING =
		registerFitting("acacia_fitting", ACACIA_PIPE);
	public static final FluidFitting CHERRY_FITTING =
		registerFitting("cherry_fitting", CHERRY_PIPE);
	public static final FluidFitting DARK_OAK_FITTING =
		registerFitting("dark_oak_fitting", DARK_OAK_PIPE);
	public static final FluidFitting PALE_OAK_FITTING =
		registerFitting("pale_oak_fitting", PALE_OAK_PIPE);
	public static final FluidFitting MANGROVE_FITTING =
		registerFitting("mangrove_fitting", MANGROVE_PIPE);
	public static final FluidFitting BAMBOO_FITTING =
		registerFitting("bamboo_fitting", BAMBOO_PIPE);
	public static final FluidFitting[] WOODEN_FITTINGS = {
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
	// Stone pipes.
	public static final FluidPipe STONE_PIPE =
		registerStonePipe("stone_pipe", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.USELESS_UNI);
	public static final FluidPipe DEEPSLATE_PIPE =
		registerStonePipe("deepslate_pipe", MapColor.DEEPSLATE,
			1.8F, 6F, FluidBlockSettings.GOOD_LAVA);
	public static final FluidPipe ANDESITE_PIPE =
		registerStonePipe("andesite_pipe", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.BAD_LAVA);
	public static final FluidPipe DIORITE_PIPE =
		registerStonePipe("diorite_pipe", MapColor.QUARTZ,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final FluidPipe GRANITE_PIPE =
		registerStonePipe("granite_pipe", MapColor.DIRT,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final FluidPipe BASALT_PIPE =
		registerStonePipe("basalt_pipe", MapColor.COLOR_BLACK,
			0.75F, 3F, FluidBlockSettings.GOOD_LAVA);
	public static final FluidPipe SANDSTONE_PIPE =
		registerStonePipe("sandstone_pipe", MapColor.SAND,
			0.75F, 3F, FluidBlockSettings.USELESS_UNI);
	public static final FluidPipe TUFF_PIPE =
		registerStonePipe("tuff_pipe", MapColor.TERRACOTTA_GRAY,
			0.75F, 3F, FluidBlockSettings.DRIPPING_LAVA);
	public static final FluidPipe OBSIDIAN_PIPE =
		registerStonePipe("obsidian_pipe", MapColor.COLOR_BLACK,
			25F, 1200F, FluidBlockSettings.GOOD_LAVA);
	public static final FluidPipe NETHERRACK_PIPE =
		registerStonePipe("netherrack_pipe", MapColor.NETHER,
			0.2F, 0.4F, FluidBlockSettings.FLAMMABLE_LAVA);
	public static final FluidPipe[] STONE_PIPES = {
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
	// Stone fittings.
	public static final FluidFitting STONE_FITTING =
		registerFitting("stone_fitting", STONE_PIPE);
	public static final FluidFitting DEEPSLATE_FITTING =
		registerFitting("deepslate_fitting", DEEPSLATE_PIPE);
	public static final FluidFitting ANDESITE_FITTING =
		registerFitting("andesite_fitting", ANDESITE_PIPE);
	public static final FluidFitting DIORITE_FITTING =
		registerFitting("diorite_fitting", DIORITE_PIPE);
	public static final FluidFitting GRANITE_FITTING =
		registerFitting("granite_fitting", GRANITE_PIPE);
	public static final FluidFitting BASALT_FITTING =
		registerFitting("basalt_fitting", BASALT_PIPE);
	public static final FluidFitting SANDSTONE_FITTING =
		registerFitting("sandstone_fitting", SANDSTONE_PIPE);
	public static final FluidFitting TUFF_FITTING =
		registerFitting("tuff_fitting", TUFF_PIPE);
	public static final FluidFitting OBSIDIAN_FITTING =
		registerFitting("obsidian_fitting", OBSIDIAN_PIPE);
	public static final FluidFitting NETHERRACK_FITTING =
		registerFitting("netherrack_fitting", NETHERRACK_PIPE);
	public static final FluidFitting[] STONE_FITTINGS = {
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
	// All pipes.
	public static final FluidPipe[] PIPES =
		Stream.concat(
			Arrays.stream(WOODEN_PIPES), Arrays.stream(STONE_PIPES)
		).toArray(FluidPipe[]::new);
	// All fittings.
	public static final FluidFitting[] FITTINGS =
		Stream.concat(
			Arrays.stream(WOODEN_FITTINGS), Arrays.stream(STONE_FITTINGS)
		).toArray(FluidFitting[]::new);

	/**
	 * Create and register everything that was not done by static initializers
	 */
	public static void init() {
		// Everything has been done by static initializers.
	}
}
