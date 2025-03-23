package eu.pintergabor.fluidpipes.registry;

import java.util.function.Function;

import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.FluidCarryBlock;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;

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

import static net.minecraft.block.AbstractBlock.*;


public final class ModBlocksRegister {

    private ModBlocksRegister() {
        // Static class.
    }

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
        Function<Settings, R> factory,
        Settings settings) {
        Identifier id = Global.modId(path);
        /// @see Blocks#keyOf.
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);
        /// @see Blocks#register(RegistryKey, Function, Settings).
        R block = factory.apply(settings.registryKey(key));
        return Registry.register(Registries.BLOCK, id, block);
    }

    /**
     * Create and register a {@link Block} and the corresponding {@link Item}
     * <p>
     * See {@link #registerBlock(String, Function, Settings)} for details.
     */
    private static <T extends Block> T registerBlockAndItem(
        String path,
        Function<Settings, T> factory,
        Settings settings) {
        // Register the block.
        T registered = registerBlock(path, factory, settings);
        // Register the item.
        Items.register(registered);
        return registered;
    }

    /**
     * Create and register a pipe and its corresponding {@link Item}
     *
     * @param path        The name of the block, without modid.
     * @param modSettings Mod specific settings, like speed, capabilities and probabilities.
     * @param settings    Generic settings, like color, hardness and resistance.
     * @return The registered block.
     */
    private static FluidPipe registerPipe(
        String path,
        FluidBlockSettings modSettings,
        Settings settings) {
        return registerBlockAndItem(path,
            (settings1) -> new FluidPipe(
                settings1, modSettings),
            settings);
    }

    /**
     * Create and register a fitting and its corresponding {@link Item},
     * matching {@code pipeBlock}
     *
     * @param path      The name of the block, without modid.
     * @param pipeBlock The matching pipe.
     * @return The registered block.
     */
    public static FluidFitting registerFitting(
        String path, FluidCarryBlock pipeBlock
    ) {
        return registerBlockAndItem(path,
            (settings1) -> new FluidFitting(
                settings1, pipeBlock.getFluidBlockSettings()),
            Settings.copy((AbstractBlock) pipeBlock));
    }

    /**
     * Create and register a wooden pipe and its corresponding {@link Item}
     *
     * @param path     The name of the block, without modid.
     * @param mapColor How it will be rendered on generated maps.
     * @return The registered block.
     */
    public static FluidPipe registerWoodenPipe(
        String path, MapColor mapColor,
        float hardness, float resistance,
        FluidBlockSettings modSettings) {
        return registerPipe(
            path, modSettings,
            Settings.create()
                .mapColor(mapColor)
                .requiresTool()
                .strength(hardness, resistance)
                .sounds(BlockSoundGroup.WOOD)
                .burnable());
    }

    /**
     * Create and register a stone pipe and its corresponding {@link Item}
     *
     * @param path     The name of the block, without modid.
     * @param mapColor How it will be rendered on generated maps.
     * @return The registered block.
     */
    public static FluidPipe registerStonePipe(
        String path, MapColor mapColor,
        float hardness, float resistance,
        FluidBlockSettings modSettings) {
        return registerPipe(
            path, modSettings,
            Settings.create()
                .mapColor(mapColor)
                .requiresTool()
                .strength(hardness, resistance)
                .sounds(BlockSoundGroup.STONE));
    }

    /**
     * Create and register everything that was not done by static initializers
     */
    public static void init() {
        // Everything has been done by static initializers.
    }
}
