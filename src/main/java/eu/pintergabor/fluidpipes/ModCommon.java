package eu.pintergabor.fluidpipes;

import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.registry.ModBlocks;
import eu.pintergabor.fluidpipes.registry.ModBlocksRegister;
import eu.pintergabor.fluidpipes.registry.ModCreativeInventorySorting;
import eu.pintergabor.fluidpipes.registry.ModProperties;
import eu.pintergabor.fluidpipes.registry.ModRegistries;
import eu.pintergabor.fluidpipes.registry.ModSoundEvents;
import eu.pintergabor.fluidpipes.registry.ModStats;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;


/**
 * Common startup.
 */
@Mod(Global.MODID)
public final class ModCommon {

	@SuppressWarnings("unused")
	public ModCommon(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
		// Create and register everything.
		ModRegistries.init(modEventBus);
	}
}
