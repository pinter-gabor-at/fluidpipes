package eu.pintergabor.fluidpipesmini;

import eu.pintergabor.fluidpipesmini.registry.ModCreativeInventorySorting;
import eu.pintergabor.fluidpipesmini.registry.ModBlockEntities;
import eu.pintergabor.fluidpipesmini.registry.ModFluidBlocks;
import eu.pintergabor.fluidpipesmini.registry.ModSoundEvents;
import eu.pintergabor.fluidpipesmini.registry.ModStats;
import eu.pintergabor.fluidpipesmini.registry.ModBlocksRegister;
import eu.pintergabor.fluidpipesmini.registry.properties.ModProperties;

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
		ModBlockEntities.init();
		ModSoundEvents.init();
		ModStats.init();
		ModCreativeInventorySorting.init();
	}
}
