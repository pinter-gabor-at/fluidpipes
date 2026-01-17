package eu.pintergabor.fluidpipesmini.registry;

import static eu.pintergabor.fluidpipesmini.registry.ModFluidBlocksRegister.*;

import java.util.Arrays;
import java.util.stream.Stream;

import eu.pintergabor.fluidpipesmini.block.FluidFitting;
import eu.pintergabor.fluidpipesmini.block.FluidPipe;
import eu.pintergabor.fluidpipesmini.block.settings.FluidBlockSettings;

import net.minecraft.world.level.material.MapColor;


/**
 * Register and store all fluid pipes and fittings.
 */
public final class ModFluidBlocks {
	// Wooden pipes.
	public static final FluidPipe WOOD_PIPE =
		registerWoodenPipe("wood_pipe", MapColor.WOOD,
			1F, 1F, FluidBlockSettings.GOOD_WATER);
	public static final FluidPipe BAMBOO_PIPE =
		registerWoodenPipe("bamboo_pipe", MapColor.COLOR_YELLOW,
			0.5F, 0.5F, FluidBlockSettings.AVERAGE_WATER);
	public static final FluidPipe[] WOODEN_PIPES = {
		WOOD_PIPE,
		BAMBOO_PIPE,
	};
	// Wooden fittings.
	public static final FluidFitting WOOD_FITTING =
		registerFitting("wood_fitting", WOOD_PIPE);
	public static final FluidFitting BAMBOO_FITTING =
		registerFitting("bamboo_fitting", BAMBOO_PIPE);
	public static final FluidFitting[] WOODEN_FITTINGS = {
		WOOD_FITTING,
		BAMBOO_FITTING,
	};
	// Stone pipes.
	public static final FluidPipe STONE_PIPE =
		registerStonePipe("stone_pipe", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final FluidPipe OBSIDIAN_PIPE =
		registerStonePipe("obsidian_pipe", MapColor.COLOR_BLACK,
			25F, 100F, FluidBlockSettings.GOOD_LAVA);
	public static final FluidPipe[] STONE_PIPES = {
		STONE_PIPE,
		OBSIDIAN_PIPE,
	};
	// Stone fittings.
	public static final FluidFitting STONE_FITTING =
		registerFitting("stone_fitting", STONE_PIPE);
	public static final FluidFitting OBSIDIAN_FITTING =
		registerFitting("obsidian_fitting", OBSIDIAN_PIPE);
	public static final FluidFitting[] STONE_FITTINGS = {
		STONE_FITTING,
		OBSIDIAN_FITTING,
	};
	// All fluid pipes.
	public static final FluidPipe[] FLUID_PIPES =
		Stream.concat(
			Arrays.stream(WOODEN_PIPES), Arrays.stream(STONE_PIPES)
		).toArray(FluidPipe[]::new);
	// All fluid fittings.
	public static final FluidFitting[] FLUID_FITTINGS =
		Stream.concat(
			Arrays.stream(WOODEN_FITTINGS), Arrays.stream(STONE_FITTINGS)
		).toArray(FluidFitting[]::new);

	/**
	 * Create and register everything that was not done by static initializers.
	 */
	public static void init() {
		// Everything has been done by static initializers.
	}
}
