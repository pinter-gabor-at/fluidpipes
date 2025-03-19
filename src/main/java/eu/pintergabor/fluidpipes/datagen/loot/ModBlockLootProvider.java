package eu.pintergabor.fluidpipes.datagen.loot;

import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipes.registry.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;


public final class ModBlockLootProvider extends FabricBlockLootTableProvider {

    public ModBlockLootProvider(
        FabricDataOutput dataOutput,
        CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    /**
     * Generate drops for an array of simple blocks.
     */
    private void generateSimpleDrops(Block[] blocks){
        for (Block b: blocks){
            addDrop(b);
        }
    }

    /**
     * Generate all drops.
     */
    @Override
    public void generate() {
        // Wooden pipes.
        generateSimpleDrops(ModBlocks.WOODEN_PIPES);
        // Wooden fittings.
        generateSimpleDrops(ModBlocks.WOODEN_FITTINGS);
        // Stone pipes.
        generateSimpleDrops(ModBlocks.STONE_PIPES);
        // Stone fittings.
        generateSimpleDrops(ModBlocks.STONE_FITTINGS);
    }
}
