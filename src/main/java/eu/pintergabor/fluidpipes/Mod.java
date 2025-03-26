package eu.pintergabor.fluidpipes;

import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.registry.ModBlocks;
import eu.pintergabor.fluidpipes.registry.ModBlocksRegister;
import eu.pintergabor.fluidpipes.registry.ModCreativeInventorySorting;
import eu.pintergabor.fluidpipes.registry.ModProperties;
import eu.pintergabor.fluidpipes.registry.ModSoundEvents;
import eu.pintergabor.fluidpipes.registry.ModStats;

import net.fabricmc.api.ModInitializer;


public final class Mod implements ModInitializer {

    @Override
    public void onInitialize() {
        ModProperties.init();
        ModBlocks.init();
        ModBlocksRegister.init();
        ModBlockEntities.init();
        ModSoundEvents.init();
        ModStats.init();
        ModCreativeInventorySorting.init();
    }
}
