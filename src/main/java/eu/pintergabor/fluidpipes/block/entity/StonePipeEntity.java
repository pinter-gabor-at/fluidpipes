package eu.pintergabor.fluidpipes.block.entity;

import eu.pintergabor.fluidpipes.block.entity.base.BaseFluidPipeEntity;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;


public class StonePipeEntity extends BaseFluidPipeEntity {

    public StonePipeEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.STONE_PIPE_ENTITY, blockPos, blockState);
    }
}
