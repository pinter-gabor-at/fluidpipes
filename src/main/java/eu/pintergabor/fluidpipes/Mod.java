package eu.pintergabor.fluidpipes;

import eu.pintergabor.fluidpipes.block.entity.leaking.LeakingPipeDripBehaviors;
import eu.pintergabor.fluidpipes.block.entity.leaking.LeakingPipeManager;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.registry.ModBlocks;
import eu.pintergabor.fluidpipes.registry.ModCreativeInventorySorting;
import eu.pintergabor.fluidpipes.registry.ModProperties;
import eu.pintergabor.fluidpipes.registry.ModStats;
import eu.pintergabor.fluidpipes.registry.ModSoundEvents;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;


public class Mod implements ModInitializer {

    @Override
    public void onInitialize() {
        ModProperties.init();
        ModBlocks.init();
        ModBlockEntities.init();
        ModSoundEvents.init();
        ModStats.init();
        LeakingPipeDripBehaviors.init();
        ModCreativeInventorySorting.init();
        ServerLifecycleEvents.SERVER_STOPPED.register(
            server -> LeakingPipeManager.clearAll());
        ServerTickEvents.START_SERVER_TICK.register(
            listener -> LeakingPipeManager.switchAndClear());
    }
}
