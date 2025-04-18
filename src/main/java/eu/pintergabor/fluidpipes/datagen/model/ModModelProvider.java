package eu.pintergabor.fluidpipes.datagen.model;

import static net.minecraft.client.data.models.BlockModelGenerators.ROTATION_FACING;
import static net.minecraft.client.data.models.BlockModelGenerators.plainVariant;

import java.util.Optional;

import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.block.BasePipe;
import eu.pintergabor.fluidpipes.registry.ModBlocks;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;


public final class ModModelProvider extends FabricModelProvider {

	// Templates.
	private static final ModelTemplate PIPE_MODEL = new ModelTemplate(
		Optional.of(Global.modId("block/template_pipe")),
		Optional.empty(),
		TextureSlot.SIDE,
		TextureSlot.FRONT
	);
	private static final ModelTemplate PIPE_MODEL_BACK = new ModelTemplate(
		Optional.of(Global.modId("block/template_pipe_back_extension")),
		Optional.empty(),
		TextureSlot.SIDE,
		TextureSlot.FRONT
	);
	private static final ModelTemplate PIPE_MODEL_BACK_SMOOTH = new ModelTemplate(
		Optional.of(Global.modId("block/template_pipe_back_smooth")),
		Optional.empty(),
		TextureSlot.SIDE
	);
	private static final ModelTemplate PIPE_MODEL_DOUBLE_EXTENSION = new ModelTemplate(
		Optional.of(Global.modId("block/template_pipe_double_extension")),
		Optional.empty(),
		TextureSlot.SIDE
	);
	private static final ModelTemplate PIPE_MODEL_FRONT_EXTENSION = new ModelTemplate(
		Optional.of(Global.modId("block/template_pipe_front_extension")),
		Optional.empty(),
		TextureSlot.SIDE,
		TextureSlot.FRONT
	);
	private static final ModelTemplate PIPE_MODEL_SMOOTH = new ModelTemplate(
		Optional.of(Global.modId("block/template_pipe_smooth")),
		Optional.empty(),
		TextureSlot.SIDE,
		TextureSlot.FRONT
	);
	private static final ModelTemplate FITTING_MODEL = new ModelTemplate(
		Optional.of(Global.modId("block/template_fitting")),
		Optional.empty(),
		TextureSlot.TEXTURE
	);

	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	/**
	 * Create models for one derived type of pipe.
	 */
	private static void createPipe(
		@NotNull BlockModelGenerators generators,
		Block pipeBlock, Block outputPipeBlock) {
		ResourceLocation model = ModelLocationUtils
			.getModelLocation(pipeBlock);
		ResourceLocation frontExtensionModel = ModelLocationUtils
			.getModelLocation(pipeBlock, "_front_extension");
		ResourceLocation doubleExtensionModel = ModelLocationUtils
			.getModelLocation(pipeBlock, "_double_extension");
		ResourceLocation backExtensionModel = ModelLocationUtils
			.getModelLocation(pipeBlock, "_back_extension");
		ResourceLocation smoothModel = ModelLocationUtils
			.getModelLocation(pipeBlock, "_smooth");
		ResourceLocation backSmoothModel = ModelLocationUtils
			.getModelLocation(pipeBlock, "_back_smooth");
		generators.registerSimpleItemModel(outputPipeBlock, model);
		generators.blockStateOutput
			.accept(
				MultiVariantGenerator.dispatch(outputPipeBlock)
					.with(
						PropertyDispatch.initial(BasePipe.FRONT_CONNECTED, BasePipe.BACK_CONNECTED, BasePipe.SMOOTH)
							.select(false, false, false,
								BlockModelGenerators.plainVariant(model))
							.select(false, false, true,
								BlockModelGenerators.plainVariant(smoothModel))
							.select(false, true, false,
								BlockModelGenerators.plainVariant(backExtensionModel))
							.select(false, true, true,
								BlockModelGenerators.plainVariant(backSmoothModel))
							.select(true, false, false,
								BlockModelGenerators.plainVariant(frontExtensionModel))
							.select(true, false, true,
								BlockModelGenerators.plainVariant(frontExtensionModel))
							.select(true, true, false,
								BlockModelGenerators.plainVariant(doubleExtensionModel))
							.select(true, true, true,
								BlockModelGenerators.plainVariant(doubleExtensionModel))
					)
					.with(ROTATION_FACING));
	}

	/**
	 * Create models for one base type of pipe.
	 */
	private static void createPipe(
		@NotNull BlockModelGenerators generators, Block pipeBlock
	) {
		// Create base type.
		TextureMapping pipeTextureMapping = new TextureMapping();
		pipeTextureMapping.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(pipeBlock));
		pipeTextureMapping.put(TextureSlot.FRONT, TextureMapping.getBlockTexture(pipeBlock, "_front"));
		PIPE_MODEL.create(pipeBlock, pipeTextureMapping, generators.modelOutput);
		PIPE_MODEL_BACK.createWithSuffix(pipeBlock, "_back_extension", pipeTextureMapping, generators.modelOutput);
		PIPE_MODEL_BACK_SMOOTH.createWithSuffix(pipeBlock, "_back_smooth", pipeTextureMapping, generators.modelOutput);
		PIPE_MODEL_DOUBLE_EXTENSION.createWithSuffix(pipeBlock, "_double_extension", pipeTextureMapping, generators.modelOutput);
		PIPE_MODEL_FRONT_EXTENSION.createWithSuffix(pipeBlock, "_front_extension", pipeTextureMapping, generators.modelOutput);
		PIPE_MODEL_SMOOTH.createWithSuffix(pipeBlock, "_smooth", pipeTextureMapping, generators.modelOutput);
		// Create derived types.
		createPipe(generators, pipeBlock, pipeBlock);
	}

	/**
	 * Create models for one derived type of fitting.
	 */
	private static void createFitting(
		@NotNull BlockModelGenerators generators, Block fittingBlock, Block outputFittingBlock) {
		ResourceLocation model = ModelLocationUtils.getModelLocation(fittingBlock);
		generators.registerSimpleItemModel(outputFittingBlock, model);
		generators.blockStateOutput.accept(
			BlockModelGenerators.createSimpleBlock(outputFittingBlock, plainVariant(model)));
	}

	/**
	 * Create models for one base type of fitting.
	 */
	private static void createFitting(
		@NotNull BlockModelGenerators generators, Block fittingBlock) {
		// Create base type.
		TextureMapping fittingTextureMapping = new TextureMapping();
		fittingTextureMapping.put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(fittingBlock));
		FITTING_MODEL.create(fittingBlock, fittingTextureMapping, generators.modelOutput);
		// Create derived types.
		createFitting(generators, fittingBlock, fittingBlock);
	}

	private static void generateWoodenPipes(BlockModelGenerators generator) {
		for (Block b : ModBlocks.WOODEN_PIPES) {
			createPipe(generator, b);
		}
	}

	private static void generateWoodenFittings(BlockModelGenerators generator) {
		for (Block b : ModBlocks.WOODEN_FITTINGS) {
			createFitting(generator, b);
		}
	}

	private static void generateStonePipes(BlockModelGenerators generator) {
		for (Block b : ModBlocks.STONE_PIPES) {
			createPipe(generator, b);
		}
	}

	private static void generateStoneFittings(BlockModelGenerators generator) {
		for (Block b : ModBlocks.STONE_FITTINGS) {
			createFitting(generator, b);
		}
	}

	/**
	 * Generate block models.
	 */
	@Override
	public void generateBlockStateModels(BlockModelGenerators generator) {
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
	public void generateItemModels(@NotNull ItemModelGenerators generator) {
	}
}
