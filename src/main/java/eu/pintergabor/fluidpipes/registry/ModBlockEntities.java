package eu.pintergabor.fluidpipes.registry;

import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.block.entity.FluidFittingEntity;
import eu.pintergabor.fluidpipes.block.entity.FluidPipeEntity;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;


/**
 * Register and store entities associated with fluid pipes and fittings.
 */
public final class ModBlockEntities {
	// Wooden and stone pipes.
	public static final BlockEntityType<FluidPipeEntity> FLUID_PIPE_ENTITY = register(
		"fluid_pipe",
		FluidPipeEntity::new,
		ModFluidBlocks.FLUID_PIPES);
	// Wooden and stone fittings.
	public static final BlockEntityType<FluidFittingEntity> FLUID_FITTING_ENTITY = register(
		"fluid_fitting",
		FluidFittingEntity::new,
		ModFluidBlocks.FLUID_FITTINGS);

	private ModBlockEntities() {
		// Static class.
	}

	private static @NonNull <T extends BlockEntity> BlockEntityType<T> register(
		final @NonNull String path,
		final FabricBlockEntityTypeBuilder.@NonNull Factory<T> blockEntity,
		final @NonNull Block... blocks
	) {
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Global.modId(path),
			FabricBlockEntityTypeBuilder.create(blockEntity, blocks).build());
	}

	/**
	 * Create and register everything that was not done by static initializers.
	 */
	public static void init() {
		// Everything has been done by static initializers.
	}
}
