package eu.pintergabor.fluidpipes.datagen.loot;

import java.util.Arrays;
import java.util.Set;

import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;
import eu.pintergabor.fluidpipes.registry.ModRegistries;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;


public final class ModBlockLootProvider extends BlockLootSubProvider {

	public ModBlockLootProvider(
		HolderLookup.Provider lookupProvider
	) {
		super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
	}

	/**
	 * See <a href="https://docs.neoforged.net/docs/resources/server/loottables/#blocklootsubprovider">
	 * Loottables in NeoForged docs</a>.
	 */
	@Override
	protected @NotNull Iterable<Block> getKnownBlocks() {
		return ModRegistries.BLOCKS.getEntries()
			.stream()
			.map(e -> (Block) e.get())
			.toList();
	}

	/**
	 * Generate drops for an array of simple blocks.
	 */
	private void generateSimpleDrops(DeferredBlock<? extends Block>[] blocks) {
		Arrays.stream(blocks).map(DeferredHolder::get).forEach(this::dropSelf);
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
