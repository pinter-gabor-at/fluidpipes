package eu.pintergabor.fluidpipes.datagen.loot;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipes.registry.ModBlockHolder;
import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;


/**
 * Loot generator.
 */
public final class ModBlockLootProvider extends FabricBlockLootSubProvider {

	public ModBlockLootProvider(
		final @NonNull FabricPackOutput dataOutput,
		final @NonNull CompletableFuture<HolderLookup.Provider> registryLookup
	) {
		super(dataOutput, registryLookup);
	}

	/**
	 * Generate drops for an array of simple blocks.
	 */
	private <T extends Block> void generateSimpleDrops(
		final @NonNull List<ModBlockHolder<T>> blockHolders
	) {
		blockHolders.forEach(bh ->
			dropSelf(bh.block()));
	}

	/**
	 * Generate all drops.
	 */
	@Override
	public void generate() {
		// Pipes.
		generateSimpleDrops(ModFluidBlocks.FLUID_PIPES);
		// Fittings.
		generateSimpleDrops(ModFluidBlocks.FLUID_FITTINGS);
	}
}
