package eu.pintergabor.fluidpipes;

import static net.minecraft.world.item.CreativeModeTab.TabVisibility;

import java.util.stream.IntStream;

import eu.pintergabor.fluidpipes.block.BaseBlock;
import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;
import eu.pintergabor.fluidpipes.registry.variants.ModBlockVariant;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.jspecify.annotations.NonNull;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;


/**
 * Add items to creative tabs.
 */
public final class CreativeTabs {

	private CreativeTabs() {
		// Static class.
	}

	/**
	 * Add one or more items to creative tabs.
	 */
	private static void add(
		@NonNull BuildCreativeModeTabContentsEvent event,
		ModBlockVariant<BaseBlock> @NonNull [] items
	) {
		// Insert all items in the list after the cauldron
		// in the same order as in the list.
		final ItemStack mark = new ItemStack(Items.CAULDRON);
		IntStream.rangeClosed(1, items.length)
			.mapToObj(i -> items[items.length - i].getItem())
			.forEach(item -> event.insertAfter(
				mark, new ItemStack(item), TabVisibility.PARENT_AND_SEARCH_TABS));
	}

	/**
	 * Add items to creative tabs.
	 */
	public static void listener(@NonNull BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
			add(event, ModFluidBlocks.STONE_FITTINGS);
			add(event, ModFluidBlocks.STONE_PIPES);
			add(event, ModFluidBlocks.WOODEN_FITTINGS);
			add(event, ModFluidBlocks.WOODEN_PIPES);
		}
	}
}
