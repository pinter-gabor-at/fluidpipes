package eu.pintergabor.fluidpipes.tag;

import eu.pintergabor.fluidpipes.Global;
import org.jetbrains.annotations.NotNull;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;


public class ModItemTags {
    public static final TagKey<Item> WOODEN_PIPES = bind("wooden_pipes");
    public static final TagKey<Item> WOODEN_FITTINGS = bind("wooden_fittings");
    public static final TagKey<Item> COPPER_PIPES = bind("copper_pipes");
    public static final TagKey<Item> COPPER_FITTINGS = bind("copper_fittings");
    public static final TagKey<Item> PIPES_AND_FITTINGS = bind("ignores_copper_pipe_menu");

    @NotNull
    private static TagKey<Item> bind(@NotNull String path) {
        return TagKey.of(Registries.ITEM.getKey(), Global.modId(path));
    }
}
