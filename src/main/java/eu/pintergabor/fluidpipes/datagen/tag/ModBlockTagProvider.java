package eu.pintergabor.fluidpipes.datagen.tag;

import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;
import eu.pintergabor.fluidpipes.tag.ModBlockTags;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;


public final class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

	public ModBlockTagProvider(
		FabricDataOutput output,
		CompletableFuture<HolderLookup.Provider> registriesFuture
	) {
		super(output, registriesFuture);
	}

	/**
	 * Create all block tags.
	 */
	@Override
	protected void addTags(@NotNull HolderLookup.Provider wrapperLookup) {
		// Remove pipes and fittings only with a pickaxe,
		// and wooden pipes with an axe too.
		getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
			.addOptionalTag(ModBlockTags.WOODEN_PIPES)
			.addOptionalTag(ModBlockTags.WOODEN_FITTINGS);
		getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
			.addOptionalTag(ModBlockTags.STONE_PIPES)
			.addOptionalTag(ModBlockTags.STONE_FITTINGS)
			.addOptionalTag(ModBlockTags.WOODEN_PIPES)
			.addOptionalTag(ModBlockTags.WOODEN_FITTINGS);
		// Wooden pipes.
		getOrCreateTagBuilder(ModBlockTags.WOODEN_PIPES)
			.add(ModFluidBlocks.WOODEN_PIPES);
		// Wooden fittings.
		getOrCreateTagBuilder(ModBlockTags.WOODEN_FITTINGS)
			.add(ModFluidBlocks.WOODEN_FITTINGS);
		// Stone pipes.
		getOrCreateTagBuilder(ModBlockTags.STONE_PIPES)
			.add(ModFluidBlocks.STONE_PIPES);
		// Stone fittings.
		getOrCreateTagBuilder(ModBlockTags.STONE_FITTINGS)
			.add(ModFluidBlocks.STONE_FITTINGS);
	}
}
