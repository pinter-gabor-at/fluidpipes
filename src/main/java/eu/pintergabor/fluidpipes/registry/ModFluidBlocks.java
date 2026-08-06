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
	public static final ModBlockHolder<FluidPipe> WOOD_PIPE =
		registerWoodenPipe("wood_pipe", MapColor.WOOD,
			1F, 1F, FluidBlockSettings.GOOD_WATER);
	public static final ModBlockHolder<FluidPipe> BAMBOO_PIPE =
		registerWoodenPipe("bamboo_pipe", MapColor.COLOR_YELLOW,
			0.5F, 0.5F, FluidBlockSettings.AVERAGE_WATER);
	public static final List<ModBlockHolder<FluidPipe>> WOODEN_PIPES = List.of(
		WOOD_PIPE,
		BAMBOO_PIPE
	);
	// Wooden fittings.
	public static final ModBlockHolder<FluidFitting> WOOD_FITTING =
		registerFitting("wood_fitting", WOOD_PIPE.block());
	public static final ModBlockHolder<FluidFitting> BAMBOO_FITTING =
		registerFitting("bamboo_fitting", BAMBOO_PIPE.block());
	public static final List<ModBlockHolder<FluidFitting>> WOODEN_FITTINGS = List.of(
		WOOD_FITTING,
		BAMBOO_FITTING
	);
	// Stone pipes.
	public static final ModBlockHolder<FluidPipe> STONE_PIPE =
		registerStonePipe("stone_pipe", MapColor.STONE,
			0.75F, 3F, FluidBlockSettings.AVERAGE_LAVA);
	public static final ModBlockHolder<FluidPipe> OBSIDIAN_PIPE =
		registerStonePipe("obsidian_pipe", MapColor.COLOR_BLACK,
			25F, 100F, FluidBlockSettings.GOOD_LAVA);
	public static final List<ModBlockHolder<FluidPipe>> STONE_PIPES = List.of(
		STONE_PIPE,
		OBSIDIAN_PIPE
	);
	// Stone fittings.
	public static final ModBlockHolder<FluidFitting> STONE_FITTING =
		registerFitting("stone_fitting", STONE_PIPE.block());
	public static final ModBlockHolder<FluidFitting> OBSIDIAN_FITTING =
		registerFitting("obsidian_fitting", OBSIDIAN_PIPE.block());
	public static final List<ModBlockHolder<FluidFitting>> STONE_FITTINGS = List.of(
		STONE_FITTING,
		OBSIDIAN_FITTING
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
