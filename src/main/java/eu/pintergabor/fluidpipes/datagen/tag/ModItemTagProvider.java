package eu.pintergabor.fluidpipes.datagen.tag;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;
import eu.pintergabor.fluidpipes.tag.ModItemTags;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;


public final class ModItemTagProvider extends ItemTagsProvider {

	public ModItemTagProvider(
		PackOutput output,
		CompletableFuture<HolderLookup.Provider> lookupProvider,
		CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider
	) {
		super(output, lookupProvider, blockTagProvider, Global.MODID);
	}

	/**
	 * Add an array of blocks as items to an item tag.
	 */
	private void add(TagKey<Item> key, DeferredBlock<? extends Block>[] blocks) {
		final IntrinsicTagAppender<Item> builder = tag(key);
		Arrays.stream(blocks).map(DeferredBlock::asItem).forEach(builder::add);
	}

	/**
	 * Create all item tags.
	 */
	@Override
	protected void addTags(@NotNull HolderLookup.Provider wrapperLookup) {
		// Pipes.
		add(ModItemTags.WOODEN_PIPES, ModFluidBlocks.WOODEN_PIPES);
		add(ModItemTags.STONE_PIPES, ModFluidBlocks.STONE_PIPES);
		// Fittings.
		add(ModItemTags.WOODEN_FITTINGS, ModFluidBlocks.WOODEN_FITTINGS);
		add(ModItemTags.STONE_FITTINGS, ModFluidBlocks.STONE_FITTINGS);
		// All pipes and fittings.
		tag(ModItemTags.PIPES_AND_FITTINGS)
			.addTag(ModItemTags.WOODEN_PIPES)
			.addTag(ModItemTags.WOODEN_FITTINGS)
			.addTag(ModItemTags.STONE_PIPES)
			.addTag(ModItemTags.STONE_FITTINGS);
	}
}
