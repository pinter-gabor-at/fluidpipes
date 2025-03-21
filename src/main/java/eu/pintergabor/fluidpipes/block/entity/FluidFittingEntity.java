package eu.pintergabor.fluidpipes.block.entity;

import eu.pintergabor.fluidpipes.block.base.BaseBlock;
import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.entity.base.BaseFittingEntity;
import eu.pintergabor.fluidpipes.block.entity.base.TickUtil;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;


public class FluidFittingEntity extends BaseFittingEntity {

    public FluidFittingEntity(
        BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.FLUID_FITTING_ENTITY, blockPos, blockState);
    }

    /**
     * Pull fluid from any pipe pointing to this fitting.
     *
     * @return true if the state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    protected static boolean pull(
        World world, BlockPos pos, BlockState state, FluidFittingEntity entity) {
        boolean changed = false;
        PipeFluid pipeFluid = state.get(ModProperties.FLUID);
        // Find a pipe pointing to this pipe from any side.
        boolean sideSourcing = false;
        for (Direction d : BaseBlock.DIRECTIONS) {
            BlockState nState = world.getBlockState(pos.offset(d));
            Block nBlock = nState.getBlock();
            if (nBlock instanceof FluidPipe &&
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

    /**
     * Clog the fitting.
     * <p>
     * Called randomly, and clears the fluid in the fitting.
     *
     * @return true if the state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    protected static boolean clog(
        World world, BlockPos pos, BlockState state, FluidFittingEntity entity) {
        PipeFluid pipeFluid = state.get(ModProperties.FLUID);
        if (pipeFluid != PipeFluid.NONE) {
            world.setBlockState(pos, state.with(ModProperties.FLUID, PipeFluid.NONE));
            return true;
        }
        return false;
    }

    /**
     * Called at every tick on the server.
     */
    public static void serverTick(
        World world, BlockPos pos, BlockState state, FluidFittingEntity entity) {
        FluidFitting block = (FluidFitting) state.getBlock();
        TickUtil.TickPos tickPos = BaseBlock.getTickPos(world, state);
        if (tickPos == TickUtil.TickPos.START) {
            // Pull fluid.
            pull(world, pos, state, entity);
            // Clogging.
            float rnd = world.random.nextFloat();
            if (rnd < block.getCloggingProbability()) {
                clog(world, pos, state, entity);
            }
        }
    }
}
