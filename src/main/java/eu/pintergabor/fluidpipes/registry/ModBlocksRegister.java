package eu.pintergabor.fluidpipes.registry;

import java.util.function.Function;

import eu.pintergabor.fluidpipes.Global;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;


/**
 * Standard ways of registering blocks and items.
 */
public final class ModBlocksRegister {

	private ModBlocksRegister() {
		// Static class.
	}

	/**
	 * Create and register a {@link Block} without {@link BlockItem}
	 * <p>
	 * See <a href="https://docs.fabricmc.net/26.1.2/develop/blocks/first-block">Fabric wiki</a> for details.
	 *
	 * @param factory The constructor of the block.
	 * @param props   Initial settings of the block.
	 * @param <T>     The returned block type.
	 * @return The registered block.
	 */
	public static <T extends Block> @NonNull T registerBlock(
		final @NonNull Identifier id,
		final ResourceKey<Block> key,
		final @NonNull Function<BlockBehaviour.Properties, T> factory,
		final BlockBehaviour.@NonNull Properties props
	) {
		final T block = factory.apply(props.setId(key));
		return Registry.register(BuiltInRegistries.BLOCK, id, block);
	}

	/**
	 * Create and register a {@link Block} without {@link BlockItem}
	 * <p>
	 * See <a href="https://docs.fabricmc.net/26.1.2/develop/blocks/first-block">Fabric wiki</a> for details.
	 *
	 * @param path    The name of the block, without modid.
	 * @param factory The constructor of the block.
	 * @param props   Initial settings of the block.
	 * @param <T>     The returned block type.
	 * @return The registered block.
	 */
	public static <T extends Block> @NonNull T registerBlock(
		final @NonNull String path,
		final @NonNull Function<BlockBehaviour.Properties, T> factory,
		final BlockBehaviour.@NonNull Properties props
	) {
		final Identifier id = Global.modId(path);
		final ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, id);
		return registerBlock(id, key, factory, props);
	}

	/**
	 * Create and register a {@link BlockItem}
	 * <p>
	 * See <a href="https://docs.fabricmc.net/26.1.2/develop/blocks/first-block">Fabric wiki</a> for details.
	 *
	 * @return The registered blockitem.
	 */
	@SuppressWarnings("UnusedReturnValue")
	private static @NonNull BlockItem registerBlockItem(
		final @NonNull Block block,
		final @NonNull Identifier id,
		final @NonNull ResourceKey<Item> key
	) {
		final BlockItem item = new BlockItem(block, new Item.Properties()
			.setId(key).useBlockDescriptionPrefix());
		return Registry.register(BuiltInRegistries.ITEM, id, item);
	}

	/**
	 * Create and register a {@link BlockItem}
	 * <p>
	 * See <a href="https://docs.fabricmc.net/26.1.2/develop/blocks/first-block">Fabric wiki</a> for details.
	 *
	 * @param path The name of the block, without modid.
	 * @return The registered blockitem.
	 */
	@SuppressWarnings("UnusedReturnValue")
	public static @NonNull BlockItem registerBlockItem(
		final @NonNull String path,
		final @NonNull Block block
	) {
		final Identifier id = Global.modId(path);
		final ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
		return registerBlockItem(block, id, key);
	}

	/**
	 * Create and register a {@link Block} and the corresponding {@link Item}
	 */
	public static <T extends Block> @NonNull T registerBlockAndItem(
		final @NonNull String path,
		final @NonNull Function<BlockBehaviour.Properties, T> factory,
		final BlockBehaviour.@NonNull Properties props
	) {
		// Register the block.
		final T registered = registerBlock(path, factory, props);
		// Register the item.
		registerBlockItem(path, registered);
		return registered;
	}

	public static <T extends Block> @NonNull ModBlockHolder<T> registerModBlock(
		final @NonNull String path,
		final @NonNull Function<BlockBehaviour.Properties, T> factory,
		final BlockBehaviour.@NonNull Properties props
	) {
		final Identifier id = Global.modId(path);
		final ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
		final ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
		final T block = registerBlock(id, blockKey, factory, props);
		final Item item = registerBlockItem(block, id, itemKey);
		return new ModBlockHolder<>(blockKey, block, itemKey, item);
	}

	/**
	 * Create and register everything that was not done by static initializers.
	 */
	public static void init() {
		// Everything has been done by static initializers.
	}
}
