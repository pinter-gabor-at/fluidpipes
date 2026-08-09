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
	public static final WoodenFluidPipeVariant WOOD_PIPE =
		new WoodenFluidPipeVariant("wood_pipe", MapColor.WOOD,
			1F, 1F, FluidBlockSettings.GOOD_WATER);
	public static final WoodenFluidPipeVariant BAMBOO_PIPE =
		new WoodenFluidPipeVariant("bamboo_pipe", MapColor.COLOR_YELLOW,
			0.5F, 0.5F, FluidBlockSettings.AVERAGE_WATER);
	public static ModBlockVariant<BaseBlock>[] WOODEN_PIPES;
	// Wooden fittings.
	public static final WoodenFluidFittingVariant WOOD_FITTING =
		new WoodenFluidFittingVariant("wood_fitting", MapColor.WOOD,
			1F, 1F, FluidBlockSettings.GOOD_WATER);
	public static final WoodenFluidFittingVariant BAMBOO_FITTING =
		new WoodenFluidFittingVariant("bamboo_fitting", MapColor.COLOR_YELLOW,
			0.5F, 0.5F, FluidBlockSettings.AVERAGE_WATER);
	public static ModBlockVariant<BaseBlock>[] WOODEN_FITTINGS;
	// Stone pipes.
	public static final StoneFluidPipeVariant STONE_PIPE =
		new StoneFluidPipeVariant("stone_pipe", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final StoneFluidPipeVariant OBSIDIAN_PIPE =
		new StoneFluidPipeVariant("obsidian_pipe", MapColor.COLOR_BLACK,
			25F, 100F, FluidBlockSettings.GOOD_LAVA);
	public static ModBlockVariant<BaseBlock>[] STONE_PIPES;
	// Stone fittings.
	public static final StoneFluidFittingVariant STONE_FITTING =
		new StoneFluidFittingVariant("stone_fitting", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final StoneFluidFittingVariant OBSIDIAN_FITTING =
		new StoneFluidFittingVariant("obsidian_fitting", MapColor.COLOR_BLACK,
			25F, 100F, FluidBlockSettings.GOOD_LAVA);
	public static ModBlockVariant<BaseBlock>[] STONE_FITTINGS;
	// All pipes.
	public static ModBlockVariant<BaseBlock>[] PIPES;
	// All fittings.
	public static ModBlockVariant<BaseBlock>[] FITTINGS;

	@SuppressWarnings("unchecked")
	private static void initPipes() {
		WOODEN_PIPES = new ModBlockVariant[]{
			WOOD_PIPE,
			BAMBOO_PIPE,
		};
		STONE_PIPES = new ModBlockVariant[]{
			STONE_PIPE,
			OBSIDIAN_PIPE,
		};
		PIPES = Stream.concat(
			Arrays.stream(WOODEN_PIPES), Arrays.stream(STONE_PIPES)
		).toArray(ModBlockVariant[]::new);
	}

	@SuppressWarnings("unchecked")
	private static void initFittings() {
		WOODEN_FITTINGS = new ModBlockVariant[]{
			WOOD_FITTING,
			BAMBOO_FITTING,
		};
		STONE_FITTINGS = new ModBlockVariant[]{
			STONE_FITTING,
			OBSIDIAN_FITTING,
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
