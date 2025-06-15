package eu.pintergabor.fluidpipes.tag;

import eu.pintergabor.fluidpipes.Global;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;


public final class ModItemTags {
	public static final TagKey<Item> WOODEN_PIPES = register("wooden_pipes");
	public static final TagKey<Item> WOODEN_FITTINGS = register("wooden_fittings");
	public static final TagKey<Item> STONE_PIPES = register("stone_pipes");
	public static final TagKey<Item> STONE_FITTINGS = register("stone_fittings");
	public static final TagKey<Item> PIPES_AND_FITTINGS = register("pipes_and_fittings");

	private ModItemTags() {
		// Static class.
	}

	@NotNull
	private static TagKey<Item> register(@NotNull String path) {
		return TagKey.create(Registries.ITEM, Global.modId(path));
	}
}
