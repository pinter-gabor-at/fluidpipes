package eu.pintergabor.fluidpipesmini.datagen.loot;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipesmini.registry.ModFluidBlocks;
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
	private void generateSimpleDrops(final @NonNull Block[] blocks) {
		Arrays.stream(blocks).forEach(this::dropSelf);
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
