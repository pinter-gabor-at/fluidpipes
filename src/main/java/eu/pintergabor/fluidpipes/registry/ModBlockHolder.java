package eu.pintergabor.fluidpipes.registry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;


public record ModBlockHolder<T extends Block>(
	ResourceKey<Block> blockKey,
	T block,
	ResourceKey<Item> itemKey,
	Item item
) {


}
