package eu.pintergabor.fluidpipes.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;


public abstract sealed class BaseBlockEntity extends BlockEntity
    permits BaseFittingEntity, BasePipeEntity {

    public BaseBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
}
