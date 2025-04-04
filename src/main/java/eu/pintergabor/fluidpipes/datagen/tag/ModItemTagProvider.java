package eu.pintergabor.fluidpipes.datagen.tag;

import static net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;

import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipes.registry.ModBlocks;
import eu.pintergabor.fluidpipes.tag.ModItemTags;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;


public final class ModItemTagProvider extends ItemTagProvider {

    public ModItemTagProvider(
        FabricDataOutput output,
        CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, registries);
    }

    /**
     * Add an array of blocks as items to an item tag.
     */
    private void add(TagKey<Item> key, Block[] blocks) {
        FabricTagBuilder builder = getOrCreateTagBuilder(key);
        for (Block b : blocks) {
            builder.add(b.asItem());
        }
    }

    /**
     * Create all item tags.
     */
    @Override
    protected void configure(RegistryWrapper.WrapperLookup provider) {
        // Wooden pipes.
        add(ModItemTags.WOODEN_PIPES, ModBlocks.WOODEN_PIPES);
        // Wooden fittings.
        add(ModItemTags.WOODEN_FITTINGS, ModBlocks.WOODEN_FITTINGS);
        // All pipes and fittings.
        getOrCreateTagBuilder(ModItemTags.PIPES_AND_FITTINGS)
            .addOptionalTag(ModItemTags.WOODEN_PIPES)
            .addOptionalTag(ModItemTags.WOODEN_FITTINGS)
            .addOptionalTag(ModItemTags.STONE_PIPES)
            .addOptionalTag(ModItemTags.STONE_FITTINGS);
    }
}
