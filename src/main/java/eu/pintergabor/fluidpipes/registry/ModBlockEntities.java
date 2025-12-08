package eu.pintergabor.fluidpipes.registry;

import java.util.Arrays;

import eu.pintergabor.fluidpipes.block.BaseBlock;
import eu.pintergabor.fluidpipes.block.entity.FluidFittingEntity;
import eu.pintergabor.fluidpipes.block.entity.FluidPipeEntity;
import eu.pintergabor.fluidpipes.registry.variants.ModBlockVariant;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;


public final class ModBlockEntities {
	// Wooden and stone pipes.
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FluidPipeEntity>> FLUID_PIPE_ENTITY =
		ModRegistries.BLOCK_ENTITY_TYPES.register(
			"fluid_pipe", () ->
				new BlockEntityType<>(FluidPipeEntity::new, unpack(ModFluidBlocks.PIPES)));
	// Wooden and stone fittings.
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FluidFittingEntity>> FLUID_FITTING_ENTITY =
		ModRegistries.BLOCK_ENTITY_TYPES.register(
			"fluid_fitting", () ->
				new BlockEntityType<>(FluidFittingEntity::new, unpack(ModFluidBlocks.FITTINGS)));

	/**
	 * Convert {@link ModBlockVariant} array to {@link Block} array.
	 */
	private static Block @NotNull [] unpack(ModBlockVariant<BaseBlock>[] dBlocks) {
		return Arrays.stream(dBlocks).map(ModBlockVariant::getBlock).toArray(Block[]::new);
	}

	private ModBlockEntities() {
		// Static class.
	}

	public static void init() {
		// Everything has been done by static initializers.
	}
}
