package eu.pintergabor.fluidpipes;

import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;
import eu.pintergabor.fluidpipes.registry.util.ModBlocksRegister;
import eu.pintergabor.fluidpipes.registry.ModCreativeInventorySorting;
import eu.pintergabor.fluidpipes.registry.util.ModProperties;
import eu.pintergabor.fluidpipes.registry.ModSoundEvents;
import eu.pintergabor.fluidpipes.registry.ModStats;

import net.fabricmc.api.ModInitializer;


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
