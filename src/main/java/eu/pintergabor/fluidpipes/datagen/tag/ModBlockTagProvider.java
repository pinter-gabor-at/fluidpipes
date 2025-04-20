package eu.pintergabor.fluidpipes.datagen.tag;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.registry.ModBlocks;
import eu.pintergabor.fluidpipes.tag.ModBlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;


public final class ModBlockTagProvider extends BlockTagsProvider {

	public ModBlockTagProvider(
		PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider, Global.MODID);
	}

	private void add(IntrinsicTagAppender<Block> tag, DeferredBlock<? extends Block>[] blocks) {
		Arrays.stream(blocks).forEach(b -> tag.add(b.get()));
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider wrapperLookup) {
		// Wooden pipes.
		add(tag(ModBlockTags.WOODEN_PIPES), ModBlocks.WOODEN_PIPES);
		// Wooden fittings.
		add(tag(ModBlockTags.WOODEN_FITTINGS), ModBlocks.WOODEN_FITTINGS);
		// Stone pipes.
		add(tag(ModBlockTags.STONE_PIPES), ModBlocks.STONE_PIPES);
		// Stone fittings.
		add(tag(ModBlockTags.STONE_FITTINGS), ModBlocks.STONE_FITTINGS);
		// Remove pipes and fittings only with a pickaxe,
		// and wooden pipes with an axe too.
		tag(BlockTags.MINEABLE_WITH_AXE)
			.addTag(ModBlockTags.WOODEN_PIPES)
			.addTag(ModBlockTags.WOODEN_FITTINGS);
		tag(BlockTags.MINEABLE_WITH_PICKAXE)
			.addTag(ModBlockTags.STONE_PIPES)
			.addTag(ModBlockTags.STONE_FITTINGS)
			.addTag(ModBlockTags.WOODEN_PIPES)
			.addTag(ModBlockTags.WOODEN_FITTINGS);
	}
}
