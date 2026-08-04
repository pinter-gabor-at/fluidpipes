package eu.pintergabor.fluidpipes.registry;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;


public final class ModCreativeInventorySorting {

	private ModCreativeInventorySorting() {
		// Static class.
	}

	/**
	 * Create and register everything that was not done by static initializers.
	 */
	public static void init() {
		// Creative tabs, functional item group.
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS)
			.register(entries -> {
				// Add pipes and fittings after the cauldron.
				entries.insertAfter(Blocks.CAULDRON,
					ModFluidBlocks.STONE_FITTINGS);
				entries.insertAfter(Blocks.CAULDRON,
					ModFluidBlocks.STONE_PIPES);
				entries.insertAfter(Blocks.CAULDRON,
					ModFluidBlocks.WOODEN_FITTINGS);
				entries.insertAfter(Blocks.CAULDRON,
					ModFluidBlocks.WOODEN_PIPES);
			});
	}
}
