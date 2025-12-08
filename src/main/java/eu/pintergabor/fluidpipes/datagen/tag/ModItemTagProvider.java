package eu.pintergabor.fluidpipes.datagen.tag;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;
import eu.pintergabor.fluidpipes.tag.ModItemTags;

import net.minecraft.world.item.Items;

import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.KeyTagProvider;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;


public final class ModItemTagProvider extends KeyTagProvider<Item> {

	@SuppressWarnings("unused")
	public ModItemTagProvider(
		PackOutput output,
		CompletableFuture<HolderLookup.Provider> lookupProvider,
		CompletableFuture<TagLookup<Block>> blockTagProvider
	) {
		super(output, Registries.ITEM, lookupProvider, Global.MODID);
	}

	/**
	 * Add an array of blocks as items to an item tag.
	 */
	private void add(TagKey<Item> key, DeferredBlock<? extends Block>[] blocks) {
		final TagAppender<ResourceKey<Item>, Item> builder = tag(key);
		Arrays.stream(blocks).forEach(b -> builder.add(Item.byBlock(b.get())));
		builder.addAll(Arrays.stream(blocks).map(deferredBlock -> (ResourceKey<Item>) deferredBlock.getKey()));
		Arrays.stream(blocks).map(deferredBlock -> deferredBlock.get()).forEach(builder::add);
	}

	/**
	 * Create all item tags.
	 */
	@Override
	protected void addTags(@NotNull HolderLookup.Provider wrapperLookup) {
		// Pipes.
		tag(ModItemTags.WOODEN_PIPES).add(Items.ANDESITE.builtInRegistryHolder().key());   //ModFluidBlocks.ACACIA_PIPE.asItem());
		final TagAppender<ResourceKey<Item>, Item> builder = tag(ModItemTags.WOODEN_PIPES);
		builder.add(ModFluidBlocks.ACACIA_PIPE);
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
