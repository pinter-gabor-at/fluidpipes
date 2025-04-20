package eu.pintergabor.fluidpipes;

import static net.minecraft.world.item.CreativeModeTab.TabVisibility;

import eu.pintergabor.fluidpipes.registry.ModBlocks;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;


public final class CreativeTabs {

	private CreativeTabs() {
		// Static class.
	}

	/**
	 * Add one or more items to creative tabs.
	 */
	private static void add(
		BuildCreativeModeTabContentsEvent event, ItemLike... items
	) {
		// Insert all items in the list after the cauldron
		// in the same order as in the list.
		ItemStack previous = new ItemStack(Items.CAULDRON);
		for (ItemLike item : items) {
			ItemStack current = new ItemStack(item);
			event.insertAfter(previous, current,
				TabVisibility.PARENT_AND_SEARCH_TABS);
			previous = current;
		}
	}

	/**
	 * Add items to creative tabs.
	 */
	public static void listener(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
			add(event, ModBlocks.STONE_FITTINGS);
			add(event, ModBlocks.STONE_PIPES);
			add(event, ModBlocks.WOODEN_FITTINGS);
			add(event, ModBlocks.WOODEN_PIPES);
		}
	}
}
