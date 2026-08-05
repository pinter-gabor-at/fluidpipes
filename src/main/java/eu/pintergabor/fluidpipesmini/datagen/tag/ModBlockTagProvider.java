package eu.pintergabor.fluidpipesmini.datagen.tag;

import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipesmini.registry.ModFluidBlocks;
import eu.pintergabor.fluidpipesmini.tag.ModBlockTags;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;


/**
 * Block tag generator.
 */
public final class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {

	public ModBlockTagProvider(
		final @NonNull FabricPackOutput output,
		final @NonNull CompletableFuture<HolderLookup.Provider> registries
	) {
		super(output, registries);
	}

	/**
	 * Create all block tags.
	 */
	@Override
	protected void addTags(final HolderLookup.@NonNull Provider registries) {
		// Remove all pipes and fittings with a pickaxe,
		// and wooden pipes with an axe too.
		valueLookupBuilder(BlockTags.MINEABLE_WITH_AXE)
			.addOptionalTag(ModBlockTags.WOODEN_PIPES)
			.addOptionalTag(ModBlockTags.WOODEN_FITTINGS);
		valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
			.addOptionalTag(ModBlockTags.STONE_PIPES)
			.addOptionalTag(ModBlockTags.STONE_FITTINGS)
			.addOptionalTag(ModBlockTags.WOODEN_PIPES)
			.addOptionalTag(ModBlockTags.WOODEN_FITTINGS);
		// Wooden pipes.
		valueLookupBuilder(ModBlockTags.WOODEN_PIPES)
			.add(ModFluidBlocks.WOODEN_PIPES);
		// Wooden fittings.
		valueLookupBuilder(ModBlockTags.WOODEN_FITTINGS)
			.add(ModFluidBlocks.WOODEN_FITTINGS);
		// Stone pipes.
		valueLookupBuilder(ModBlockTags.STONE_PIPES)
			.add(ModFluidBlocks.STONE_PIPES);
		// Stone fittings.
		valueLookupBuilder(ModBlockTags.STONE_FITTINGS)
			.add(ModFluidBlocks.STONE_FITTINGS);
	}
}
