package eu.pintergabor.fluidpipes.registry;

import java.util.function.Function;

import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.block.WoodenFitting;
import eu.pintergabor.fluidpipes.block.WoodenPipe;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;


public final class ModBlocks {
    // Wooden pipes.
    public static final WoodenPipe OAK_PIPE =
        registerWoodenPipe("oak_pipe", MapColor.OAK_TAN);
    public static final WoodenPipe SPRUCE_PIPE =
        registerWoodenPipe("spruce_pipe", MapColor.SPRUCE_BROWN);
    public static final WoodenPipe BIRCH_PIPE =
        registerWoodenPipe("birch_pipe", MapColor.PALE_YELLOW);
    public static final WoodenPipe JUNGLE_PIPE =
        registerWoodenPipe("jungle_pipe", MapColor.DIRT_BROWN);
    public static final WoodenPipe ACACIA_PIPE =
        registerWoodenPipe("acacia_pipe", MapColor.ORANGE);
    public static final WoodenPipe CHERRY_PIPE =
        registerWoodenPipe("cherry_pipe", MapColor.TERRACOTTA_WHITE);
    public static final WoodenPipe DARK_OAK_PIPE =
        registerWoodenPipe("dark_oak_pipe", MapColor.BROWN);
    public static final WoodenPipe PALE_OAK_PIPE =
        registerWoodenPipe("pale_oak_pipe", MapColor.OFF_WHITE);
    public static final WoodenPipe MANGROVE_PIPE =
        registerWoodenPipe("mangrove_pipe", MapColor.RED);
    public static final WoodenPipe BAMBOO_PIPE =
        registerWoodenPipe("bamboo_pipe", MapColor.YELLOW);
    public static final WoodenPipe[] WOODEN_PIPES = {
        OAK_PIPE,
        SPRUCE_PIPE,
        BIRCH_PIPE,
        JUNGLE_PIPE,
        ACACIA_PIPE,
        CHERRY_PIPE,
        DARK_OAK_PIPE,
        PALE_OAK_PIPE,
        MANGROVE_PIPE,
        BAMBOO_PIPE,
    };
    // Wooden fittings.
    public static final WoodenFitting OAK_FITTING =
        registerWoodenFitting("oak_fitting", MapColor.OAK_TAN);
    public static final WoodenFitting SPRUCE_FITTING =
        registerWoodenFitting("spruce_fitting", MapColor.SPRUCE_BROWN);
    public static final WoodenFitting BIRCH_FITTING =
        registerWoodenFitting("birch_fitting", MapColor.PALE_YELLOW);
    public static final WoodenFitting JUNGLE_FITTING =
        registerWoodenFitting("jungle_fitting", MapColor.DIRT_BROWN);
    public static final WoodenFitting ACACIA_FITTING =
        registerWoodenFitting("acacia_fitting", MapColor.ORANGE);
    public static final WoodenFitting CHERRY_FITTING =
        registerWoodenFitting("cherry_fitting", MapColor.TERRACOTTA_WHITE);
    public static final WoodenFitting DARK_OAK_FITTING =
        registerWoodenFitting("dark_oak_fitting", MapColor.BROWN);
    public static final WoodenFitting PALE_OAK_FITTING =
        registerWoodenFitting("pale_oak_fitting", MapColor.OFF_WHITE);
    public static final WoodenFitting MANGROVE_FITTING =
        registerWoodenFitting("mangrove_fitting", MapColor.RED);
    public static final WoodenFitting BAMBOO_FITTING =
        registerWoodenFitting("bamboo_fitting", MapColor.YELLOW);
    public static final WoodenFitting[] WOODEN_FITTINGS = {
        OAK_FITTING,
        SPRUCE_FITTING,
        BIRCH_FITTING,
        JUNGLE_FITTING,
        ACACIA_FITTING,
        CHERRY_FITTING,
        DARK_OAK_FITTING,
        PALE_OAK_FITTING,
        MANGROVE_FITTING,
        BAMBOO_FITTING,
    };

    /**
     * Create and register a {@link Block} without {@link Item}
     * <p>
     * See block registration in {@link Blocks} for details.
     *
     * @param path     The name of the block, without modid.
     * @param factory  The constructor of the block.
     * @param settings Initial settings of the block.
     * @param <R>      The returned block type.
     * @return The registered block.
     */
    private static <R extends Block> R registerBlock(
        String path,
        Function<AbstractBlock.Settings, R> factory,
        AbstractBlock.Settings settings) {
        Identifier id = Global.modId(path);
        /// @see Blocks#keyOf.
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
        /// @see Blocks#register(RegistryKey, Function, AbstractBlock.Settings).
        R block = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.BLOCK, id, block);
    }

    /**
     * Create and register a {@link Block} and the corresponding {@link Item}
     * <p>
     * See {@link #registerBlock(String, Function, AbstractBlock.Settings)} for details.
     */
    private static <T extends Block> T registerBlockAndItem(
        String path,
        Function<AbstractBlock.Settings, T> factory,
        AbstractBlock.Settings settings) {
        // Register the block.
        T registered = registerBlock(path, factory, settings);
        // Register the item.
        Items.register(registered);
        return registered;
    }

    /**
     * Create and register a {@link WoodenPipe} and its corresponding {@link Item}
     *
     * @param path     The name of the block, without modid.
     * @param mapColor How it will be rendered on generated maps.
     * @return The registered block.
     */
    private static WoodenPipe registerWoodenPipe(String path, MapColor mapColor) {
        return registerBlockAndItem(path,
            WoodenPipe::new,
            AbstractBlock.Settings.create()
                .mapColor(mapColor)
                .requiresTool()
                .strength(1.0F)
                .sounds(BlockSoundGroup.WOOD)
                .burnable()
        );
    }

    /**
     * Create and register a {@link WoodenFitting} and its corresponding {@link Item}
     *
     * @param path     The name of the block, without modid.
     * @param mapColor How it will be rendered on generated maps.
     * @return The registered block.
     */
    private static WoodenFitting registerWoodenFitting(String path, MapColor mapColor) {
        return registerBlockAndItem(path,
            WoodenFitting::new,
            AbstractBlock.Settings.create()
                .mapColor(mapColor)
                .requiresTool()
                .strength(1.0F)
                .sounds(BlockSoundGroup.WOOD)
                .burnable()
        );
    }

    /**
     * Create and register everything that was not done by static initializers
     */
    public static void init() {
        // Everything has been done by static initializers.
    }
}
