package eu.pintergabor.fluidpipesmini.registry;

import eu.pintergabor.fluidpipesmini.block.FluidFitting;
import eu.pintergabor.fluidpipesmini.block.FluidPipe;


public final class ModBlockTypes {

	private ModBlockTypes() {
		// Static class.
	}

	/**
	 * Initialize block types.
	 */
	public static void init() {
		ModRegistries.BLOCK_TYPES.register(
			"fluid_pipe",
			() -> FluidPipe.CODEC);
		ModRegistries.BLOCK_TYPES.register(
			"fluid_fitting",
			() -> FluidFitting.CODEC);
	}
}
