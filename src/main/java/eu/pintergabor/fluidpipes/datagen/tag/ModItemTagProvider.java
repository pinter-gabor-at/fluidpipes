package eu.pintergabor.fluidpipes.datagen.tag;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipes.registry.ModBlockHolder;
import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;
import eu.pintergabor.fluidpipes.tag.ModItemTags;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;


/**
 * Item tag generator.
 */
public final class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

	public ModItemTagProvider(
		final @NonNull FabricPackOutput output,
		final @NonNull CompletableFuture<HolderLookup.Provider> registries
	) {
		super(output, registries);
	}

	/**
	 * Add an array of blocks as items to an item tag.
	 */
	private <T extends Block> void add(
		final @NonNull TagAppender<Item> tag,
		final @NonNull List<ModBlockHolder<T>> blockHolders
	) {
		blockHolders.forEach(bh ->
			tag.add(bh.itemKey()));
	}

	/**
	 * Create all item tags.
	 */
	@Override
	protected void addTags(final HolderLookup.@NonNull Provider registries) {
		// Pipes.
		add(tag(ModItemTags.WOODEN_PIPES), ModFluidBlocks.WOODEN_PIPES);
		add(tag(ModItemTags.STONE_PIPES), ModFluidBlocks.STONE_PIPES);
		// Fittings.
		add(tag(ModItemTags.WOODEN_FITTINGS), ModFluidBlocks.WOODEN_FITTINGS);
		add(tag(ModItemTags.STONE_FITTINGS), ModFluidBlocks.STONE_FITTINGS);
		// All pipes and fittings.
		tag(ModItemTags.FLUID_PIPES_AND_FITTINGS)
			.addOptionalTag(ModItemTags.WOODEN_PIPES)
			.addOptionalTag(ModItemTags.WOODEN_FITTINGS)
			.addOptionalTag(ModItemTags.STONE_PIPES)
			.addOptionalTag(ModItemTags.STONE_FITTINGS);
	}
}
