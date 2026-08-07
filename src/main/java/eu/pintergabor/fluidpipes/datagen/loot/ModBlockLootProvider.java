package eu.pintergabor.fluidpipes.datagen.loot;

import java.util.Arrays;
import java.util.Set;

import eu.pintergabor.fluidpipes.block.BaseBlock;
import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;
import eu.pintergabor.fluidpipes.registry.ModRegistries;
import eu.pintergabor.fluidpipes.registry.variants.ModBlockVariant;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;


/**
 * Loot generator.
 */
public final class ModBlockLootProvider extends BlockLootSubProvider {

	public ModBlockLootProvider(
		final HolderLookup.@NonNull Provider lookupProvider
	) {
		super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
	}

	/**
	 * See <a href="https://docs.neoforged.net/docs/resources/server/loottables/#blocklootsubprovider">
	 * Loottables in NeoForged docs</a>.
	 */
	@Override
	protected @NonNull @Unmodifiable Iterable<Block> getKnownBlocks() {
		return ModRegistries.BLOCKS.getEntries()
			.stream()
			.map(b -> (Block) b.get())
			.toList();
	}

	/**
	 * Generate drops for an array of simple blocks.
	 */
	private void generateSimpleDrops(final @NonNull ModBlockVariant<BaseBlock>[] blocks) {
		Arrays.stream(blocks)
			.map(b -> b.block.get())
			.forEach(this::dropSelf);
	}

	/**
	 * Generate all drops.
	 */
	@Override
	public void generate() {
		// Pipes.
		generateSimpleDrops(ModFluidBlocks.PIPES);
		// Fittings.
		generateSimpleDrops(ModFluidBlocks.FITTINGS);
	}
}
