package eu.pintergabor.fluidpipesmini.tag;

import eu.pintergabor.fluidpipesmini.Global;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;


/**
 * Register and store block tags.
 */
public final class ModBlockTags {
	public static final TagKey<Block> WOODEN_PIPES = register("wooden_pipes");
	public static final TagKey<Block> WOODEN_FITTINGS = register("wooden_fittings");
	public static final TagKey<Block> STONE_PIPES = register("stone_pipes");
	public static final TagKey<Block> STONE_FITTINGS = register("stone_fittings");

	private ModBlockTags() {
		// Static class.
	}

	private static @NonNull TagKey<Block> register(@NonNull String path) {
		return TagKey.create(Registries.BLOCK, Global.modId(path));
	}
}
