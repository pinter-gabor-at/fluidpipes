package eu.pintergabor.fluidpipes.registry.variants;

import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;


public class StoneFluidFittingVariant extends FluidFittingVariant {

	/**
	 * Create and register a stone fitting {@link Block} and its corresponding {@link Item}
	 *
	 * @param path          The name of the block, without modid.
	 * @param mapColor      How it will be rendered on generated maps.
	 * @param hardness      Hardness (aka destroy time).
	 * @param resistance    Explosion resistance.
	 * @param modProperties See {@link FluidBlockSettings}.
	 */
	public StoneFluidFittingVariant(
		@NotNull String path, @NotNull MapColor mapColor,
		float hardness, float resistance,
		@NotNull FluidBlockSettings modProperties
	) {
		super(path, modProperties,
			BlockBehaviour.Properties.of()
				.mapColor(mapColor)
				.requiresCorrectToolForDrops()
				.strength(hardness, resistance)
				.sound(SoundType.STONE));
	}
}
