package eu.pintergabor.fluidpipes.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


/**
 * The parent of all pipe and fitting entities.
 */
public abstract sealed class BaseBlockEntity extends BlockEntity
	permits BasePipeEntity, BaseFittingEntity {

	public BaseBlockEntity(
		BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState
	) {
		super(blockEntityType, blockPos, blockState);
	}
}
