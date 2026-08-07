package eu.pintergabor.fluidpipes.datagen.tag;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.block.BaseBlock;
import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;
import eu.pintergabor.fluidpipes.registry.variants.ModBlockVariant;
import eu.pintergabor.fluidpipes.tag.ModItemTags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;


/**
 * Item tag generator.
 */
public final class ModItemTagProvider extends ItemTagsProvider {

	@SuppressWarnings("unused")
	public ModItemTagProvider(
		final @NonNull PackOutput output,
		final @NonNull CompletableFuture<HolderLookup.Provider> lookupProvider,
		final @NonNull CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider
	) {
		super(output, lookupProvider, Global.MODID);
	}

	/**
	 * Add an array of blocks as items to an item tag.
	 */
	private void add(
		final @NonNull TagKey<Item> key,
		final @NonNull ModBlockVariant<BaseBlock>[] blocks
	) {
		final TagAppender<Item> builder = tag(key);
		builder.addAll(Arrays.stream(blocks)
			.map(b -> b.item.getKey()));
	}

	/**
	 * Create all item tags.
	 */
	@Override
	protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
		// Pipes.
		add(ModItemTags.WOODEN_PIPES, ModFluidBlocks.WOODEN_PIPES);
		add(ModItemTags.STONE_PIPES, ModFluidBlocks.STONE_PIPES);
		// Fittings.
		add(ModItemTags.WOODEN_FITTINGS, ModFluidBlocks.WOODEN_FITTINGS);
		add(ModItemTags.STONE_FITTINGS, ModFluidBlocks.STONE_FITTINGS);
		// All pipes and fittings.
		tag(ModItemTags.FLUID_PIPES_AND_FITTINGS)
			.addTag(ModItemTags.WOODEN_PIPES)
			.addTag(ModItemTags.WOODEN_FITTINGS)
			.addTag(ModItemTags.STONE_PIPES)
			.addTag(ModItemTags.STONE_FITTINGS);
	}
}
