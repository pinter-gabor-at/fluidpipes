package eu.pintergabor.fluidpipesmini.block.entity;

import org.jspecify.annotations.NonNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


/**
 * The parent of all pipe entities.
 */
public abstract non-sealed class BasePipeEntity extends BaseBlockEntity {

	public BasePipeEntity(
		@NonNull BlockEntityType<?> blockEntityType,
		@NonNull BlockPos blockPos,
		@NonNull BlockState blockState
	) {
		super(blockEntityType, blockPos, blockState);
	}
}
