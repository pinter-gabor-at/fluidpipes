package eu.pintergabor.fluidpipes.registry;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.FluidPipe;

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
    public static final FluidPipe OAK_PIPE =
        registerWoodenPipe("oak_pipe", MapColor.OAK_TAN);
    public static final FluidPipe SPRUCE_PIPE =
        registerWoodenPipe("spruce_pipe", MapColor.SPRUCE_BROWN);
    public static final FluidPipe BIRCH_PIPE =
        registerWoodenPipe("birch_pipe", MapColor.PALE_YELLOW);
    public static final FluidPipe JUNGLE_PIPE =
        registerWoodenPipe("jungle_pipe", MapColor.DIRT_BROWN);
    public static final FluidPipe ACACIA_PIPE =
        registerWoodenPipe("acacia_pipe", MapColor.ORANGE);
    public static final FluidPipe CHERRY_PIPE =
        registerWoodenPipe("cherry_pipe", MapColor.TERRACOTTA_WHITE);
    public static final FluidPipe DARK_OAK_PIPE =
        registerWoodenPipe("dark_oak_pipe", MapColor.BROWN);
    public static final FluidPipe PALE_OAK_PIPE =
        registerWoodenPipe("pale_oak_pipe", MapColor.OFF_WHITE);
    public static final FluidPipe MANGROVE_PIPE =
        registerWoodenPipe("mangrove_pipe", MapColor.RED);
    public static final FluidPipe BAMBOO_PIPE =
        registerWoodenPipe("bamboo_pipe", MapColor.YELLOW);
    public static final FluidPipe[] WOODEN_PIPES = {
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
    public static final FluidFitting OAK_FITTING =
        registerWoodenFitting("oak_fitting", MapColor.OAK_TAN);
    public static final FluidFitting SPRUCE_FITTING =
        registerWoodenFitting("spruce_fitting", MapColor.SPRUCE_BROWN);
    public static final FluidFitting BIRCH_FITTING =
        registerWoodenFitting("birch_fitting", MapColor.PALE_YELLOW);
    public static final FluidFitting JUNGLE_FITTING =
        registerWoodenFitting("jungle_fitting", MapColor.DIRT_BROWN);
    public static final FluidFitting ACACIA_FITTING =
        registerWoodenFitting("acacia_fitting", MapColor.ORANGE);
    public static final FluidFitting CHERRY_FITTING =
        registerWoodenFitting("cherry_fitting", MapColor.TERRACOTTA_WHITE);
    public static final FluidFitting DARK_OAK_FITTING =
        registerWoodenFitting("dark_oak_fitting", MapColor.BROWN);
    public static final FluidFitting PALE_OAK_FITTING =
        registerWoodenFitting("pale_oak_fitting", MapColor.OFF_WHITE);
    public static final FluidFitting MANGROVE_FITTING =
        registerWoodenFitting("mangrove_fitting", MapColor.RED);
    public static final FluidFitting BAMBOO_FITTING =
        registerWoodenFitting("bamboo_fitting", MapColor.YELLOW);
    public static final FluidFitting[] WOODEN_FITTINGS = {
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
    // Stone pipes.
    public static final FluidPipe STONE_PIPE =
        registerStonePipe("stone_pipe", MapColor.STONE_GRAY);
    public static final FluidPipe DEEPSLATE_PIPE =
        registerStonePipe("deepslate_pipe", MapColor.DEEPSLATE_GRAY);
    public static final FluidPipe ANDESITE_PIPE =
        registerStonePipe("andesite_pipe", MapColor.STONE_GRAY);
    public static final FluidPipe DIORITE_PIPE =
        registerStonePipe("diorite_pipe", MapColor.OFF_WHITE);
    public static final FluidPipe GRANITE_PIPE =
        registerStonePipe("granite_pipe", MapColor.DIRT_BROWN);
    public static final FluidPipe BASALT_PIPE =
        registerStonePipe("basalt_pipe", MapColor.BLACK);
    public static final FluidPipe SANDSTONE_PIPE =
        registerStonePipe("sandstone_pipe", MapColor.PALE_YELLOW);
    public static final FluidPipe TUFF_PIPE =
        registerStonePipe("tuff_pipe", MapColor.TERRACOTTA_GRAY);
    public static final FluidPipe[] STONE_PIPES = {
        STONE_PIPE,
        DEEPSLATE_PIPE,
        ANDESITE_PIPE,
        DIORITE_PIPE,
        GRANITE_PIPE,
        BASALT_PIPE,
        SANDSTONE_PIPE,
        TUFF_PIPE,
    };
    // Stone fittings.
    public static final FluidFitting STONE_FITTING =
        registerStoneFitting("stone_fitting", MapColor.STONE_GRAY);
    public static final FluidFitting DEEPSLATE_FITTING =
        registerStoneFitting("deepslate_fitting", MapColor.DEEPSLATE_GRAY);
    public static final FluidFitting ANDESITE_FITTING =
        registerStoneFitting("andesite_fitting", MapColor.STONE_GRAY);
    public static final FluidFitting DIORITE_FITTING =
        registerStoneFitting("diorite_fitting", MapColor.OFF_WHITE);
    public static final FluidFitting GRANITE_FITTING =
        registerStoneFitting("granite_fitting", MapColor.DIRT_BROWN);
    public static final FluidFitting BASALT_FITTING =
        registerStoneFitting("basalt_fitting", MapColor.BLACK);
    public static final FluidFitting SANDSTONE_FITTING =
        registerStoneFitting("sandstone_fitting", MapColor.PALE_YELLOW);
    public static final FluidFitting TUFF_FITTING =
        registerStoneFitting("tuff_fitting", MapColor.TERRACOTTA_GRAY);
    public static final FluidFitting[] STONE_FITTINGS = {
        STONE_FITTING,
        DEEPSLATE_FITTING,
        ANDESITE_FITTING,
        DIORITE_FITTING,
        GRANITE_FITTING,
        BASALT_FITTING,
        SANDSTONE_FITTING,
        TUFF_FITTING,
    };
    // All pipes.
    public static final FluidPipe[] PIPES =
        Stream.concat(Arrays.stream(WOODEN_PIPES), Arrays.stream(STONE_PIPES))
            .toArray(FluidPipe[]::new);
    // All fittings.
    public static final FluidFitting[] FITTINGS =
        Stream.concat(Arrays.stream(WOODEN_FITTINGS), Arrays.stream(STONE_FITTINGS))
            .toArray(FluidFitting[]::new);

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
     * Create and register a wooden pipe and its corresponding {@link Item}
     *
     * @param path     The name of the block, without modid.
     * @param mapColor How it will be rendered on generated maps.
     * @return The registered block.
     */
    private static FluidPipe registerWoodenPipe(String path, MapColor mapColor) {
        return registerBlockAndItem(path,
            (settings) -> new FluidPipe(settings, 10),
            AbstractBlock.Settings.create()
                .mapColor(mapColor)
                .requiresTool()
                .strength(1.0F)
                .sounds(BlockSoundGroup.WOOD)
                .burnable()
        );
    }

    /**
     * Create and register a wooden fitting and its corresponding {@link Item}
     *
     * @param path     The name of the block, without modid.
     * @param mapColor How it will be rendered on generated maps.
     * @return The registered block.
     */
    private static FluidFitting registerWoodenFitting(String path, MapColor mapColor) {
        return registerBlockAndItem(path,
            (settings) -> new FluidFitting(settings, 10),
            AbstractBlock.Settings.create()
                .mapColor(mapColor)
                .requiresTool()
                .strength(1.0F)
                .sounds(BlockSoundGroup.WOOD)
                .burnable()
        );
    }

    /**
     * Create and register a stone pipe and its corresponding {@link Item}
     *
     * @param path     The name of the block, without modid.
     * @param mapColor How it will be rendered on generated maps.
     * @return The registered block.
     */
    private static FluidPipe registerStonePipe(String path, MapColor mapColor) {
        return registerBlockAndItem(path,
            (settings) -> new FluidPipe(settings, 10),
            AbstractBlock.Settings.create()
                .mapColor(mapColor)
                .requiresTool()
                .strength(1.0F)
                .sounds(BlockSoundGroup.STONE)
        );
    }

    /**
     * Create and register a stone fitting and its corresponding {@link Item}
     *
     * @param path     The name of the block, without modid.
     * @param mapColor How it will be rendered on generated maps.
     * @return The registered block.
     */
    private static FluidFitting registerStoneFitting(String path, MapColor mapColor) {
        return registerBlockAndItem(path,
            (settings) -> new FluidFitting(settings, 10),
            AbstractBlock.Settings.create()
                .mapColor(mapColor)
                .requiresTool()
                .strength(0.75F, 3F)
                .sounds(BlockSoundGroup.STONE)
        );
    }

    /**
     * Create and register everything that was not done by static initializers
     */
    public static void init() {
        // Everything has been done by static initializers.
    }
}
