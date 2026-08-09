package eu.pintergabor.fluidpipes.datagen.tag;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.block.BaseBlock;
import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;
import eu.pintergabor.fluidpipes.registry.variants.ModBlockVariant;
import eu.pintergabor.fluidpipes.tag.ModBlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;


/**
 * Block tag generator.
 */
public final class ModBlockTagProvider extends BlockTagsProvider {

	public ModBlockTagProvider(
		PackOutput output,
		CompletableFuture<HolderLookup.Provider> lookupProvider
	) {
		super(output, lookupProvider, Global.MODID);
	}

	private void add(
		TagKey<Block> key,
		ModBlockVariant<BaseBlock>[] blocks
	) {
		final TagAppender<Block, Block> tag = tag(key);
		Arrays.stream(blocks)
			.forEach(b -> tag.add(b.block.get()));
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
		// Wooden pipes.
		add(ModBlockTags.WOODEN_PIPES, ModFluidBlocks.WOODEN_PIPES);
		// Wooden fittings.
		add(ModBlockTags.WOODEN_FITTINGS, ModFluidBlocks.WOODEN_FITTINGS);
		// Stone pipes.
		add(ModBlockTags.STONE_PIPES, ModFluidBlocks.STONE_PIPES);
		// Stone fittings.
		add(ModBlockTags.STONE_FITTINGS, ModFluidBlocks.STONE_FITTINGS);
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
