package eu.pintergabor.fluidpipes.datagen.tag;

import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipes.registry.ModBlocks;
import eu.pintergabor.fluidpipes.tag.ModBlockTags;

import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;


public final class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(
        FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, registries);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup provider) {
        // Remove pipes and fittings only with a pickaxe,
        // and wooden pipes with an axe too.
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
            .addOptionalTag(ModBlockTags.WOODEN_PIPES)
            .addOptionalTag(ModBlockTags.WOODEN_FITTINGS);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
            .addOptionalTag(ModBlockTags.WOODEN_PIPES)
            .addOptionalTag(ModBlockTags.WOODEN_FITTINGS);
        // Wooden pipes.
        getOrCreateTagBuilder(ModBlockTags.WOODEN_PIPES)
            .add(ModBlocks.WOODEN_PIPES);
        // Wooden pipes.
        getOrCreateTagBuilder(ModBlockTags.WOODEN_FITTINGS)
            .add(ModBlocks.WOODEN_FITTINGS);
    }
}
