package eu.pintergabor.fluidpipes.block.entity;

import org.jspecify.annotations.NonNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


/**
 * The parent of all fitting entities.
 */
public abstract non-sealed class BaseFittingEntity extends BaseBlockEntity {

	public BaseFittingEntity(
		final @NonNull BlockEntityType<?> blockEntityType,
		final @NonNull BlockPos blockPos,
		final @NonNull BlockState blockState
	) {
		super(blockEntityType, blockPos, blockState);
	}
}
