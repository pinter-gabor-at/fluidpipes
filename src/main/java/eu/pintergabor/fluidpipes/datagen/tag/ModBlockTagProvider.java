package eu.pintergabor.fluidpipes.datagen.tag;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.registry.ModBlockHolder;
import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;
import eu.pintergabor.fluidpipes.tag.ModBlockTags;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

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
	 * Add a list of blocks to a tag.
	 *
	 * @param <T> {@link FluidPipe} or {@link FluidFitting}
	 */
	private <T extends Block> void add(
		final @NonNull TagAppender<Block> tag,
		final @NonNull List<ModBlockHolder<T>> blockHolders
	) {
		blockHolders.forEach(bh ->
			tag.add(bh.blockKey()));
	}

	/**
	 * Create all block tags.
	 */
	@Override
	public void addTags(final HolderLookup.@NonNull Provider registries) {
		// Remove all pipes and fittings with a pickaxe,
		// and wooden pipes with an axe too.
		tag(BlockTags.MINEABLE_WITH_AXE)
			.addOptionalTag(ModBlockTags.WOODEN_PIPES)
			.addOptionalTag(ModBlockTags.WOODEN_FITTINGS);
		tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.addOptionalTag(ModBlockTags.STONE_PIPES)
			.addOptionalTag(ModBlockTags.STONE_FITTINGS)
			.addOptionalTag(ModBlockTags.WOODEN_PIPES)
			.addOptionalTag(ModBlockTags.WOODEN_FITTINGS);
		// Wooden pipes.
		add(tag(ModBlockTags.WOODEN_PIPES), ModFluidBlocks.WOODEN_PIPES);
		// Wooden fittings.
		add(tag(ModBlockTags.WOODEN_FITTINGS), ModFluidBlocks.WOODEN_FITTINGS);
		// Stone pipes.
		add(tag(ModBlockTags.STONE_PIPES), ModFluidBlocks.STONE_PIPES);
		// Stone fittings.
		add(tag(ModBlockTags.STONE_FITTINGS), ModFluidBlocks.STONE_FITTINGS);
	}

}
