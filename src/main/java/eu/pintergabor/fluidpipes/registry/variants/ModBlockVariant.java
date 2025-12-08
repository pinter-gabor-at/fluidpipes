package eu.pintergabor.fluidpipes.registry.variants;

import java.util.function.Function;

import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.registry.ModRegistries;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;


/**
 * Base class for registering all pipes and fittings.
 *
 * @param <T> {@link FluidPipe} or {@link FluidFitting}
 */
public abstract class ModBlockVariant<T extends Block> {
	protected DeferredBlock<Block> block;
	protected DeferredItem<BlockItem> item;

	/**
	 * Create and register a {@link Block} and the corresponding {@link BlockItem}
	 * <p>
	 * See block registration in {@link Blocks} for details.
	 *
	 * @param path    The name of the block, without modid.
	 * @param factory The constructor of the block.
	 * @param props   Initial settings of the block.
	 */
	public ModBlockVariant(
		@NotNull String path,
		@NotNull Function<BlockBehaviour.Properties, T> factory,
		@NotNull BlockBehaviour.Properties props
	) {
		// Register the block.
		block = ModRegistries.BLOCKS.register(path, id ->
			factory.apply(props.setId(ResourceKey.create(Registries.BLOCK, id))));
		// Register the item.
		item = ModRegistries.ITEMS.registerSimpleBlockItem(block);
	}

	/**
	 * Allow public access to the registered block.
	 *
	 * @return the registered {@link Block}
	 */
	public Block getBlock() {
		return block.get();
	}

	/**
	 * Allow public access to the registered item.
	 *
	 * @return the registered {@link Item}
	 */
	public BlockItem getItem() {
		return item.get();
	}
}
