package eu.pintergabor.fluidpipesmini.registry.variants;

import eu.pintergabor.fluidpipesmini.block.settings.FluidBlockSettings;
import org.jspecify.annotations.NonNull;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;


public class WoodenFluidFittingVariant extends FluidFittingVariant {

	/**
	 * Create and register a wooden fitting {@link Block} and its corresponding {@link Item}
	 *
	 * @param path          The name of the block, without modid.
	 * @param mapColor      How it will be rendered on generated maps.
	 * @param hardness      Hardness (aka destroy time).
	 * @param resistance    Explosion resistance.
	 * @param modProperties See {@link FluidBlockSettings}.
	 */
	public WoodenFluidFittingVariant(
		@NonNull String path, @NonNull MapColor mapColor,
		float hardness, float resistance,
		@NonNull FluidBlockSettings modProperties
	) {
		super(path, modProperties,
			BlockBehaviour.Properties.of()
				.mapColor(mapColor)
				.requiresCorrectToolForDrops()
				.strength(hardness, resistance)
				.sound(SoundType.WOOD)
				.ignitedByLava());
	}
}
