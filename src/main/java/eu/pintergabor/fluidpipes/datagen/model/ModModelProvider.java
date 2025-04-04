package eu.pintergabor.fluidpipes.datagen.model;

import java.util.Optional;

import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.block.BasePipe;
import eu.pintergabor.fluidpipes.registry.ModBlocks;
import org.jetbrains.annotations.NotNull;

import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariant;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.data.VariantSettings;
import net.minecraft.client.data.VariantsBlockStateSupplier;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;


public final class ModModelProvider extends FabricModelProvider {

    // Templates.
    private static final Model PIPE_MODEL = new Model(
        Optional.of(Global.modId("block/template_pipe")),
        Optional.empty(),
        TextureKey.SIDE,
        TextureKey.FRONT
    );
    private static final Model PIPE_MODEL_BACK = new Model(
        Optional.of(Global.modId("block/template_pipe_back_extension")),
        Optional.empty(),
        TextureKey.SIDE,
        TextureKey.FRONT
    );
    private static final Model PIPE_MODEL_BACK_SMOOTH = new Model(
        Optional.of(Global.modId("block/template_pipe_back_smooth")),
        Optional.empty(),
        TextureKey.SIDE
    );
    private static final Model PIPE_MODEL_DOUBLE_EXTENSION = new Model(
        Optional.of(Global.modId("block/template_pipe_double_extension")),
        Optional.empty(),
        TextureKey.SIDE
    );
    private static final Model PIPE_MODEL_FRONT_EXTENSION = new Model(
        Optional.of(Global.modId("block/template_pipe_front_extension")),
        Optional.empty(),
        TextureKey.SIDE,
        TextureKey.FRONT
    );
    private static final Model PIPE_MODEL_SMOOTH = new Model(
        Optional.of(Global.modId("block/template_pipe_smooth")),
        Optional.empty(),
        TextureKey.SIDE,
        TextureKey.FRONT
    );
    private static final Model FITTING_MODEL = new Model(
        Optional.of(Global.modId("block/template_fitting")),
        Optional.empty(),
        TextureKey.TEXTURE
    );

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    /**
     * Create models for one derived type of pipe.
     */
    private static void createPipe(
        @NotNull BlockStateModelGenerator generators,
        Block pipeBlock, Block outputPipeBlock) {
        Identifier model = ModelIds.getBlockModelId(pipeBlock);
        Identifier frontExtensionModel = ModelIds.getBlockSubModelId(pipeBlock, "_front_extension");
        Identifier doubleExtensionModel = ModelIds.getBlockSubModelId(pipeBlock, "_double_extension");
        Identifier backExtensionModel = ModelIds.getBlockSubModelId(pipeBlock, "_back_extension");
        Identifier smoothModel = ModelIds.getBlockSubModelId(pipeBlock, "_smooth");
        Identifier backSmoothModel = ModelIds.getBlockSubModelId(pipeBlock, "_back_smooth");
        generators.registerParentedItemModel(outputPipeBlock, model);
        generators.blockStateCollector
            .accept(
                VariantsBlockStateSupplier.create(outputPipeBlock)
                    .coordinate(BlockStateModelGenerator.createNorthDefaultRotationStates())
                    .coordinate(
                        BlockStateVariantMap.create(
                                BasePipe.FRONT_CONNECTED, BasePipe.BACK_CONNECTED, BasePipe.SMOOTH)
                            .register(false, false, false,
                                BlockStateVariant.create().put(VariantSettings.MODEL, model))
                            .register(false, false, true,
                                BlockStateVariant.create().put(VariantSettings.MODEL, smoothModel))
                            .register(false, true, false,
                                BlockStateVariant.create().put(VariantSettings.MODEL, backExtensionModel))
                            .register(false, true, true,
                                BlockStateVariant.create().put(VariantSettings.MODEL, backSmoothModel))
                            .register(true, false, false,
                                BlockStateVariant.create().put(VariantSettings.MODEL, frontExtensionModel))
                            .register(true, false, true,
                                BlockStateVariant.create().put(VariantSettings.MODEL, frontExtensionModel))
                            .register(true, true, false,
                                BlockStateVariant.create().put(VariantSettings.MODEL, doubleExtensionModel))
                            .register(true, true, true,
                                BlockStateVariant.create().put(VariantSettings.MODEL, doubleExtensionModel))
                    )
            );
    }

    /**
     * Create models for one base type of pipe.
     */
    private static void createPipe(
        @NotNull BlockStateModelGenerator generators, Block pipeBlock) {
        // Create base type.
        TextureMap pipeTextureMapping = new TextureMap();
        pipeTextureMapping.put(TextureKey.SIDE, TextureMap.getId(pipeBlock));
        pipeTextureMapping.put(TextureKey.FRONT, TextureMap.getSubId(pipeBlock, "_front"));
        PIPE_MODEL.upload(pipeBlock, pipeTextureMapping, generators.modelCollector);
        PIPE_MODEL_BACK.upload(pipeBlock, "_back_extension", pipeTextureMapping, generators.modelCollector);
        PIPE_MODEL_BACK_SMOOTH.upload(pipeBlock, "_back_smooth", pipeTextureMapping, generators.modelCollector);
        PIPE_MODEL_DOUBLE_EXTENSION.upload(pipeBlock, "_double_extension", pipeTextureMapping, generators.modelCollector);
        PIPE_MODEL_FRONT_EXTENSION.upload(pipeBlock, "_front_extension", pipeTextureMapping, generators.modelCollector);
        PIPE_MODEL_SMOOTH.upload(pipeBlock, "_smooth", pipeTextureMapping, generators.modelCollector);
        // Create derived types.
        createPipe(generators, pipeBlock, pipeBlock);
    }

    /**
     * Create models for one derived type of fitting.
     */
    private static void createFitting(
        @NotNull BlockStateModelGenerator generators, Block fittingBlock, Block outputFittingBlock) {
        Identifier model = ModelIds.getBlockModelId(fittingBlock);
        generators.registerParentedItemModel(outputFittingBlock, model);
        generators.blockStateCollector.accept(
            BlockStateModelGenerator.createSingletonBlockState(outputFittingBlock, model));
    }

    /**
     * Create models for one base type of fitting.
     */
    private static void createFitting(
        @NotNull BlockStateModelGenerator generators, Block fittingBlock) {
        // Create base type.
        TextureMap fittingTextureMapping = new TextureMap();
        fittingTextureMapping.put(TextureKey.TEXTURE, TextureMap.getId(fittingBlock));
        FITTING_MODEL.upload(fittingBlock, fittingTextureMapping, generators.modelCollector);
        // Create derived types.
        createFitting(generators, fittingBlock, fittingBlock);
    }

    private static void generateWoodenPipes(BlockStateModelGenerator generator) {
        for (Block b: ModBlocks.WOODEN_PIPES){
            createPipe(generator, b);
        }
    }

    private static void generateWoodenFittings(BlockStateModelGenerator generator) {
        for (Block b: ModBlocks.WOODEN_FITTINGS){
            createFitting(generator, b);
        }
    }

    private static void generateStonePipes(BlockStateModelGenerator generator) {
        for (Block b: ModBlocks.STONE_PIPES){
            createPipe(generator, b);
        }
    }

    private static void generateStoneFittings(BlockStateModelGenerator generator) {
        for (Block b: ModBlocks.STONE_FITTINGS){
            createFitting(generator, b);
        }
    }

    /**
     * Generate block models.
     */
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        // Wooden pipes.
        generateWoodenPipes(generator);
        // Wooden fittings.
        generateWoodenFittings(generator);
        // Stone pipes.
        generateStonePipes(generator);
        // Stone fittings.
        generateStoneFittings(generator);
    }

    /**
     * There are no item models to create.
     */
    @Override
    public void generateItemModels(@NotNull ItemModelGenerator generator) {
    }
}
