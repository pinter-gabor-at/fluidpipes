package eu.pintergabor.fluidpipes.block.entity.base;

import eu.pintergabor.fluidpipes.block.base.BaseBlock;
import eu.pintergabor.fluidpipes.block.base.BaseFluidPipe;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;


public abstract class BaseFluidFittingEntity extends BaseFittingEntity {

    public BaseFluidFittingEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    /**
     * Pull fluid from any pipe pointing to this fitting.
     *
     * @return true if state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    protected static boolean pull(
        World world, BlockPos pos, BlockState state, BaseFluidFittingEntity entity) {
        boolean changed = false;
        PipeFluid pipeFluid = state.get(ModProperties.FLUID);
        // Find a pipe pointing to this pipe from any side.
        boolean sideSourcing = false;
        for (Direction d : BaseBlock.DIRECTIONS) {
            BlockState nState = world.getBlockState(pos.offset(d));
            Block nBlock = nState.getBlock();
            if (nBlock instanceof BaseFluidPipe &&
                nState.get(Properties.FACING) == d.getOpposite()) {
                PipeFluid nFluid = nState.get(ModProperties.FLUID);
                if (nFluid != PipeFluid.NONE) {
                    // Water or lava is coming from the side.
                    sideSourcing = true;
                    if (pipeFluid != nFluid) {
                        pipeFluid = nFluid;
                        changed = true;
                    }
                    break;
                }
            }
        }
        if (!sideSourcing && pipeFluid != PipeFluid.NONE) {
            // No source from any side.
            pipeFluid = PipeFluid.NONE;
            changed = true;
        }
        if (changed) {
            world.setBlockState(pos, state.with(ModProperties.FLUID, pipeFluid));
        }
        return changed;
    }

}
