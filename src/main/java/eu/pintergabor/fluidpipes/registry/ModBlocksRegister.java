package eu.pintergabor.fluidpipes.registry;

import java.util.function.Function;

import eu.pintergabor.fluidpipes.block.FluidCarryBlock;
import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;
import net.neoforged.neoforge.registries.DeferredBlock;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;


public final class ModBlocksRegister {

	private ModBlocksRegister() {
		// Static class.
	}

	/**
	 * Create and register a {@link Block} without {@link Item}
	 * <p>
	 * See block registration in {@link Blocks} for details.
	 *
	 * @param path    The name of the block, without modid.
	 * @param factory The constructor of the block.
	 * @param props   Initial settings of the block.
	 * @param <T>     The returned block type.
	 * @return The registered block.
	 */
	private static <T extends Block> DeferredBlock<T> registerBlock(
		String path,
		Function<Properties, T> factory,
		Properties props
	) {
		return ModRegistries.BLOCKS.register(path, id ->
			factory.apply(props.setId(ResourceKey.create(Registries.BLOCK, id))));
	}

	/**
	 * Create and register a {@link Block} and the corresponding {@link Item}
	 * <p>
	 * See {@link #registerBlock(String, Function, Properties)} for details.
	 */
	private static <T extends Block> DeferredBlock<T> registerBlockAndItem(
		String path,
		Function<Properties, T> factory,
		Properties props
	) {
		// Register the block.
		DeferredBlock<T> registered = registerBlock(path, factory, props);
		// Register the item.
		ModRegistries.ITEMS.registerSimpleBlockItem(registered);
		return registered;
	}

	/**
	 * Create and register a pipe and its corresponding {@link Item}
	 *
	 * @param path        The name of the block, without modid.
	 * @param modSettings Mod specific settings, like speed, capabilities and probabilities.
	 * @param props       Generic settings, like color, hardness and resistance.
	 * @return The registered block.
	 */
	private static DeferredBlock<FluidPipe> registerPipe(
		String path,
		FluidBlockSettings modSettings,
		Properties props
	) {
		return registerBlockAndItem(path,
			(props1) -> new FluidPipe(
				props1, modSettings),
			props);
	}

	/**
	 * Create and register a fitting and its corresponding {@link Item},
	 * matching {@code pipeBlock}
	 *
	 * @param path      The name of the block, without modid.
	 * @param pipeBlock The matching pipe.
	 * @return The registered block.
	 */
	public static DeferredBlock<FluidFitting> registerFitting(
		String path, DeferredBlock<FluidPipe> pipeBlock
	) {
		return registerBlockAndItem(path,
			(props) -> new FluidFitting(
				Properties.ofFullCopy(pipeBlock.get()), pipeBlock.get().getFluidBlockSettings()),
			null);
	}

	/**
	 * Create and register a wooden pipe and its corresponding {@link Item}
	 *
	 * @param path     The name of the block, without modid.
	 * @param mapColor How it will be rendered on generated maps.
	 * @return The registered block.
	 */
	public static DeferredBlock<FluidPipe> registerWoodenPipe(
		String path, MapColor mapColor,
		float hardness, float resistance,
		FluidBlockSettings modProperties
	) {
		return registerPipe(
			path, modProperties,
			Properties.of()
				.mapColor(mapColor)
				.requiresCorrectToolForDrops()
				.strength(hardness, resistance)
				.sound(SoundType.WOOD)
				.ignitedByLava());
	}

	/**
	 * Create and register a stone pipe and its corresponding {@link Item}
	 *
	 * @param path     The name of the block, without modid.
	 * @param mapColor How it will be rendered on generated maps.
	 * @return The registered block.
	 */
	public static DeferredBlock<FluidPipe> registerStonePipe(
		String path, MapColor mapColor,
		float hardness, float resistance,
		FluidBlockSettings modProperties
	) {
		return registerPipe(
			path, modProperties,
			Properties.of()
				.mapColor(mapColor)
				.requiresCorrectToolForDrops()
				.strength(hardness, resistance)
				.sound(SoundType.STONE));
	}

	/**
	 * Create and register everything that was not done by static initializers
	 */
	public static void init() {
		// Everything has been done by static initializers.
	}
}
