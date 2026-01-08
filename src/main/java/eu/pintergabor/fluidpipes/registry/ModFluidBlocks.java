package eu.pintergabor.fluidpipes.registry;

import java.util.Arrays;
import java.util.stream.Stream;

import eu.pintergabor.fluidpipes.block.BaseBlock;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;
import eu.pintergabor.fluidpipes.registry.variants.ModBlockVariant;
import eu.pintergabor.fluidpipes.registry.variants.StoneFluidFittingVariant;
import eu.pintergabor.fluidpipes.registry.variants.StoneFluidPipeVariant;
import eu.pintergabor.fluidpipes.registry.variants.WoodenFluidFittingVariant;
import eu.pintergabor.fluidpipes.registry.variants.WoodenFluidPipeVariant;

import net.minecraft.world.level.material.MapColor;


/**
 * Register and store all fluid pipes and fittings.
 */
public final class ModFluidBlocks {
	// Wooden pipes.
	public static final WoodenFluidPipeVariant OAK_PIPE =
		new WoodenFluidPipeVariant("oak_pipe", MapColor.WOOD,
			1F, 1F, FluidBlockSettings.UNSTABLE_UNI);
	public static final WoodenFluidPipeVariant SPRUCE_PIPE =
		new WoodenFluidPipeVariant("spruce_pipe", MapColor.PODZOL,
			1F, 1F, FluidBlockSettings.FLAMMABLE_UNI);
	public static final WoodenFluidPipeVariant BIRCH_PIPE =
		new WoodenFluidPipeVariant("birch_pipe", MapColor.SAND,
			1F, 1F, FluidBlockSettings.AVERAGE_WATER);
	public static final WoodenFluidPipeVariant JUNGLE_PIPE =
		new WoodenFluidPipeVariant("jungle_pipe", MapColor.DIRT,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final WoodenFluidPipeVariant ACACIA_PIPE =
		new WoodenFluidPipeVariant("acacia_pipe", MapColor.COLOR_ORANGE,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final WoodenFluidPipeVariant CHERRY_PIPE =
		new WoodenFluidPipeVariant("cherry_pipe", MapColor.TERRACOTTA_WHITE,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final WoodenFluidPipeVariant DARK_OAK_PIPE =
		new WoodenFluidPipeVariant("dark_oak_pipe", MapColor.COLOR_BROWN,
			1F, 1F, FluidBlockSettings.STABLE_UNI);
	public static final WoodenFluidPipeVariant PALE_OAK_PIPE =
		new WoodenFluidPipeVariant("pale_oak_pipe", MapColor.QUARTZ,
			1F, 1F, FluidBlockSettings.UNSTABLE_UNI);
	public static final WoodenFluidPipeVariant MANGROVE_PIPE =
		new WoodenFluidPipeVariant("mangrove_pipe", MapColor.COLOR_RED,
			1F, 1F, FluidBlockSettings.DRIPPING_WATER);
	public static final WoodenFluidPipeVariant BAMBOO_PIPE =
		new WoodenFluidPipeVariant("bamboo_pipe", MapColor.COLOR_YELLOW,
			0.5F, 0.5F, FluidBlockSettings.GOOD_WATER);
	public static ModBlockVariant<BaseBlock>[] WOODEN_PIPES;
	// Wooden fittings.
	public static final WoodenFluidFittingVariant OAK_FITTING =
		new WoodenFluidFittingVariant("oak_fitting", MapColor.WOOD,
			1F, 1F, FluidBlockSettings.UNSTABLE_UNI);
	public static final WoodenFluidFittingVariant SPRUCE_FITTING =
		new WoodenFluidFittingVariant("spruce_fitting", MapColor.PODZOL,
			1F, 1F, FluidBlockSettings.FLAMMABLE_UNI);
	public static final WoodenFluidFittingVariant BIRCH_FITTING =
		new WoodenFluidFittingVariant("birch_fitting", MapColor.SAND,
			1F, 1F, FluidBlockSettings.AVERAGE_WATER);
	public static final WoodenFluidFittingVariant JUNGLE_FITTING =
		new WoodenFluidFittingVariant("jungle_fitting", MapColor.DIRT,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final WoodenFluidFittingVariant ACACIA_FITTING =
		new WoodenFluidFittingVariant("acacia_fitting", MapColor.COLOR_ORANGE,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final WoodenFluidFittingVariant CHERRY_FITTING =
		new WoodenFluidFittingVariant("cherry_fitting", MapColor.TERRACOTTA_WHITE,
			1F, 1F, FluidBlockSettings.BAD_WATER);
	public static final WoodenFluidFittingVariant DARK_OAK_FITTING =
		new WoodenFluidFittingVariant("dark_oak_fitting", MapColor.COLOR_BROWN,
			1F, 1F, FluidBlockSettings.STABLE_UNI);
	public static final WoodenFluidFittingVariant PALE_OAK_FITTING =
		new WoodenFluidFittingVariant("pale_oak_fitting", MapColor.QUARTZ,
			1F, 1F, FluidBlockSettings.UNSTABLE_UNI);
	public static final WoodenFluidFittingVariant MANGROVE_FITTING =
		new WoodenFluidFittingVariant("mangrove_fitting", MapColor.COLOR_RED,
			1F, 1F, FluidBlockSettings.DRIPPING_WATER);
	public static final WoodenFluidFittingVariant BAMBOO_FITTING =
		new WoodenFluidFittingVariant("bamboo_fitting", MapColor.COLOR_YELLOW,
			0.5F, 0.5F, FluidBlockSettings.GOOD_WATER);
	public static ModBlockVariant<BaseBlock>[] WOODEN_FITTINGS;
	// Stone pipes.
	public static final StoneFluidPipeVariant STONE_PIPE =
		new StoneFluidPipeVariant("stone_pipe", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.USELESS_UNI);
	public static final StoneFluidPipeVariant DEEPSLATE_PIPE =
		new StoneFluidPipeVariant("deepslate_pipe", MapColor.DEEPSLATE,
			1.8F, 6F, FluidBlockSettings.GOOD_LAVA);
	public static final StoneFluidPipeVariant ANDESITE_PIPE =
		new StoneFluidPipeVariant("andesite_pipe", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.BAD_LAVA);
	public static final StoneFluidPipeVariant DIORITE_PIPE =
		new StoneFluidPipeVariant("diorite_pipe", MapColor.QUARTZ,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final StoneFluidPipeVariant GRANITE_PIPE =
		new StoneFluidPipeVariant("granite_pipe", MapColor.DIRT,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final StoneFluidPipeVariant BASALT_PIPE =
		new StoneFluidPipeVariant("basalt_pipe", MapColor.COLOR_BLACK,
			0.75F, 3F, FluidBlockSettings.GOOD_LAVA);
	public static final StoneFluidPipeVariant SANDSTONE_PIPE =
		new StoneFluidPipeVariant("sandstone_pipe", MapColor.SAND,
			0.75F, 3F, FluidBlockSettings.USELESS_UNI);
	public static final StoneFluidPipeVariant TUFF_PIPE =
		new StoneFluidPipeVariant("tuff_pipe", MapColor.TERRACOTTA_GRAY,
			0.75F, 3F, FluidBlockSettings.DRIPPING_LAVA);
	public static final StoneFluidPipeVariant OBSIDIAN_PIPE =
		new StoneFluidPipeVariant("obsidian_pipe", MapColor.COLOR_BLACK,
			25F, 1200F, FluidBlockSettings.GOOD_LAVA);
	public static final StoneFluidPipeVariant NETHERRACK_PIPE =
		new StoneFluidPipeVariant("netherrack_pipe", MapColor.NETHER,
			0.2F, 0.4F, FluidBlockSettings.FLAMMABLE_LAVA);
	public static ModBlockVariant<BaseBlock>[] STONE_PIPES;
	// Stone fittings.
	public static final StoneFluidFittingVariant STONE_FITTING =
		new StoneFluidFittingVariant("stone_fitting", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.USELESS_UNI);
	public static final StoneFluidFittingVariant DEEPSLATE_FITTING =
		new StoneFluidFittingVariant("deepslate_fitting", MapColor.DEEPSLATE,
			1.8F, 6F, FluidBlockSettings.GOOD_LAVA);
	public static final StoneFluidFittingVariant ANDESITE_FITTING =
		new StoneFluidFittingVariant("andesite_fitting", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.BAD_LAVA);
	public static final StoneFluidFittingVariant DIORITE_FITTING =
		new StoneFluidFittingVariant("diorite_fitting", MapColor.QUARTZ,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final StoneFluidFittingVariant GRANITE_FITTING =
		new StoneFluidFittingVariant("granite_fitting", MapColor.DIRT,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final StoneFluidFittingVariant BASALT_FITTING =
		new StoneFluidFittingVariant("basalt_fitting", MapColor.COLOR_BLACK,
			0.75F, 3F, FluidBlockSettings.GOOD_LAVA);
	public static final StoneFluidFittingVariant SANDSTONE_FITTING =
		new StoneFluidFittingVariant("sandstone_fitting", MapColor.SAND,
			0.75F, 3F, FluidBlockSettings.USELESS_UNI);
	public static final StoneFluidFittingVariant TUFF_FITTING =
		new StoneFluidFittingVariant("tuff_fitting", MapColor.TERRACOTTA_GRAY,
			0.75F, 3F, FluidBlockSettings.DRIPPING_LAVA);
	public static final StoneFluidFittingVariant OBSIDIAN_FITTING =
		new StoneFluidFittingVariant("obsidian_fitting", MapColor.COLOR_BLACK,
			25F, 1200F, FluidBlockSettings.GOOD_LAVA);
	public static final StoneFluidFittingVariant NETHERRACK_FITTING =
		new StoneFluidFittingVariant("netherrack_fitting", MapColor.NETHER,
			0.2F, 0.4F, FluidBlockSettings.FLAMMABLE_LAVA);
	public static ModBlockVariant<BaseBlock>[] STONE_FITTINGS;
	// All pipes.
	public static ModBlockVariant<BaseBlock>[] PIPES;
	// All fittings.
	public static ModBlockVariant<BaseBlock>[] FITTINGS;

	@SuppressWarnings("unchecked")
	private static void initPipes() {
		WOODEN_PIPES = new ModBlockVariant[]{
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
		STONE_PIPES = new ModBlockVariant[]{
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
		).toArray(ModBlockVariant[]::new);
	}

	@SuppressWarnings("unchecked")
	private static void initFittings() {
		WOODEN_FITTINGS = new ModBlockVariant[]{
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
		STONE_FITTINGS = new ModBlockVariant[]{
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
		).toArray(ModBlockVariant[]::new);
	}

	/**
	 * Create and register everything that was not done by static initializers.
	 */
	public static void init() {
		initPipes();
		initFittings();
	}
}
