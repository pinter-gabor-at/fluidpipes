package eu.pintergabor.fluidpipes.tag;

import eu.pintergabor.fluidpipes.Global;
import org.jetbrains.annotations.NotNull;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;


public class ModBlockTags {
    public static final TagKey<Block> WOODEN_PIPES = bind("wooden_pipes");
    public static final TagKey<Block> WOODEN_FITTINGS = bind("wooden_fittings");
    public static final TagKey<Block> STONE_PIPES = bind("stone_pipes");
    public static final TagKey<Block> STONE_FITTINGS = bind("stone_fittings");

    @NotNull
    private static TagKey<Block> bind(@NotNull String path) {
        return TagKey.of(Registries.BLOCK.getKey(), Global.modId(path));
    }
}
