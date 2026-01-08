package eu.pintergabor.fluidpipes;

import eu.pintergabor.fluidpipes.registry.ModCreativeInventorySorting;
import eu.pintergabor.fluidpipes.registry.ModFluidBlockEntities;
import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;
import eu.pintergabor.fluidpipes.registry.ModSoundEvents;
import eu.pintergabor.fluidpipes.registry.ModStats;
import eu.pintergabor.fluidpipes.registry.util.ModBlocksRegister;
import eu.pintergabor.fluidpipes.registry.util.ModProperties;

import net.fabricmc.api.ModInitializer;


/**
 * Main entry point of mod initialization.
 */
public final class Mod implements ModInitializer {

	@Override
	public void onInitialize() {
		ModProperties.init();
		ModFluidBlocks.init();
		ModBlocksRegister.init();
		ModFluidBlockEntities.init();
		ModSoundEvents.init();
		ModStats.init();
		ModCreativeInventorySorting.init();
	}
}
