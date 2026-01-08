package eu.pintergabor.fluidpipes.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


/**
 * The parent of all fitting entities.
 */
public abstract non-sealed class BaseFittingEntity extends BaseBlockEntity {

	public BaseFittingEntity(
		BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState
	) {
		super(blockEntityType, blockPos, blockState);
	}
}
