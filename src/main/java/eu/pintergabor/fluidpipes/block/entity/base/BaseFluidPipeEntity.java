package eu.pintergabor.fluidpipes.block.entity.base;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;


public abstract class BaseFluidPipeEntity extends BasePipeEntity {

    public BaseFluidPipeEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }
}
