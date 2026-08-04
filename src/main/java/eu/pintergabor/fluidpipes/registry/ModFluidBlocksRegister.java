package eu.pintergabor.fluidpipes.registry;

import eu.pintergabor.fluidpipes.block.FluidCarryBlock;
import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;
import org.jspecify.annotations.NonNull;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;


/**
 * Utilities for registering fluid pipes and fittings.
 */
public final class ModFluidBlocksRegister {

	private ModFluidBlocksRegister() {
		// Static class.
	}

	/**
	 * Create and register a pipe and its corresponding {@link Item}
	 *
	 * @param path        The name of the block, without modid.
	 * @param modSettings Mod specific settings, like speed, capabilities and probabilities.
	 * @param props       Generic settings, like color, hardness and resistance.
	 * @return The registered block.
	 */
	private static @NonNull FluidPipe registerPipe(
		final @NonNull String path,
		final @NonNull FluidBlockSettings modSettings,
		final BlockBehaviour.@NonNull Properties props
	) {
		return ModBlocksRegister.registerBlockAndItem(path,
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
	public static @NonNull FluidFitting registerFitting(
		final @NonNull String path,
		final @NonNull FluidCarryBlock pipeBlock
	) {
		return ModBlocksRegister.registerBlockAndItem(path,
			(props1) -> new FluidFitting(
				props1, pipeBlock.getFluidBlockSettings()),
			BlockBehaviour.Properties.ofFullCopy((BlockBehaviour) pipeBlock));
	}

	/**
	 * Common part of {@link #registerWoodenPipe(String, MapColor, float, float, FluidBlockSettings)}
	 * and {@link #registerStonePipe(String, MapColor, float, float, FluidBlockSettings)}.
	 */
	private static BlockBehaviour.@NonNull Properties commonProperties(
		final @NonNull MapColor mapColor,
		final float hardness,
		final float resistance
	) {
		return BlockBehaviour.Properties.of()
			.mapColor(mapColor)
			.requiresCorrectToolForDrops()
			.strength(hardness, resistance);
	}

	/**
	 * Create and register a wooden pipe and its corresponding {@link Item}
	 *
	 * @param path     The name of the block, without modid.
	 * @param mapColor How it will be rendered on generated maps.
	 * @return The registered block.
	 */
	public static @NonNull FluidPipe registerWoodenPipe(
		final @NonNull String path,
		final @NonNull MapColor mapColor,
		final float hardness,
		final float resistance,
		final @NonNull FluidBlockSettings modProperties
	) {
		final BlockBehaviour.Properties props =
			commonProperties(mapColor, hardness, resistance)
				.sound(SoundType.WOOD)
				.ignitedByLava();
		return registerPipe(path, modProperties, props);
	}

	/**
	 * Create and register a stone pipe and its corresponding {@link Item}
	 *
	 * @param path     The name of the block, without modid.
	 * @param mapColor How it will be rendered on generated maps.
	 * @return The registered block.
	 */
	public static @NonNull FluidPipe registerStonePipe(
		final @NonNull String path,
		final @NonNull MapColor mapColor,
		final float hardness,
		final float resistance,
		final @NonNull FluidBlockSettings modProperties
	) {
		BlockBehaviour.Properties props =
			commonProperties(mapColor, hardness, resistance)
				.sound(SoundType.STONE);
		return registerPipe(path, modProperties, props);
	}
}
