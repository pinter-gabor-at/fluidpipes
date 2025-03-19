package eu.pintergabor.fluidpipes.block.entity;

import eu.pintergabor.fluidpipes.block.entity.base.BaseFluidPipeEntity;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;


public class WoodenPipeEntity extends BaseFluidPipeEntity {

    public WoodenPipeEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.WOODEN_PIPE_ENTITY, blockPos, blockState);
    }
}
