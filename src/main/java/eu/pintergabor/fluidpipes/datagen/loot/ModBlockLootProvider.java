package eu.pintergabor.fluidpipes.datagen.loot;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;


/**
 * Loot generator.
 */
public final class ModBlockLootProvider extends FabricBlockLootTableProvider {

	public ModBlockLootProvider(
		FabricDataOutput dataOutput,
		CompletableFuture<HolderLookup.Provider> registryLookup
	) {
		super(dataOutput, registryLookup);
	}

	/**
	 * Generate drops for an array of simple blocks.
	 */
	private void generateSimpleDrops(Block[] blocks) {
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
