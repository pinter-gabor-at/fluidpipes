package eu.pintergabor.fluidpipes.block.entity;

import static eu.pintergabor.fluidpipes.block.base.BaseBlock.DIRECTIONS;
import static eu.pintergabor.fluidpipes.block.base.BaseBlock.getTickPos;

import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.entity.base.BasePipeEntity;
import eu.pintergabor.fluidpipes.block.entity.base.FluidUtil;
import eu.pintergabor.fluidpipes.block.entity.base.TickUtil;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;


public class FluidPipeEntity extends BasePipeEntity {

    public FluidPipeEntity(
        BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLUID_PIPE_ENTITY, pos, state);
    }

    /**
     * Pull fluid from the block at the back of the pipe.
     *
     * @return true if the state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    protected static boolean pull(
        World world, BlockPos pos, BlockState state, FluidPipeEntity entity) {
        boolean changed = false;
        Direction facing = state.get(Properties.FACING);
        Direction opposite = facing.getOpposite();
        BlockState backState = world.getBlockState(pos.offset(opposite));
        PipeFluid pipeFluid = state.get(ModProperties.FLUID);
        if (FluidUtil.isWaterSource(backState)) {
            // If a water source from the back is supplying water.
            if (pipeFluid != PipeFluid.WATER) {
                pipeFluid = PipeFluid.WATER;
                changed = true;
            }
        } else if (FluidUtil.isLavaSource(backState)) {
            // If a lava source from the back is supplying lava.
            if (pipeFluid != PipeFluid.LAVA) {
                pipeFluid = PipeFluid.LAVA;
                changed = true;
            }
        } else {
            // Find a pipe pointing to this pipe from any side.
            boolean sideSourcing = false;
            for (Direction d : DIRECTIONS) {
                if (d == facing || d == opposite) continue;
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
                            break;
                        }
                    }
                }
            }
            if (!sideSourcing && pipeFluid != PipeFluid.NONE) {
                // No source from any side.
                pipeFluid = PipeFluid.NONE;
                changed = true;
            }
        }
        if (changed) {
            world.setBlockState(pos, state.with(ModProperties.FLUID, pipeFluid));
        }
        return changed;
    }

    /**
     * Clog pipe.
     * <p>
     * Called randomly, and clears the fiuid in the pipe.
     *
     * @return true if the state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    protected static boolean clog(
        World world, BlockPos pos, BlockState state, FluidPipeEntity entity) {
        PipeFluid pipeFluid = state.get(ModProperties.FLUID);
        if (pipeFluid != PipeFluid.NONE) {
            world.setBlockState(pos, state.with(ModProperties.FLUID, PipeFluid.NONE));
            return true;
        }
        return false;
    }

    /**
     * Push fluid into the block at the front of the pipe.
     *
     * @return true if the state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    protected static boolean push(
        World world, BlockPos pos, BlockState state, FluidPipeEntity entity) {
        boolean changed = false;
        // This block.
        Direction facing = state.get(Properties.FACING);
        Direction opposite = facing.getOpposite();
        PipeFluid pipeFluid = state.get(ModProperties.FLUID);
        // The block in front of this.
        BlockPos frontPos = pos.offset(facing);
        BlockState frontState = world.getBlockState(frontPos);
        Block frontBlock = frontState.getBlock();
        if (pipeFluid != PipeFluid.NONE) {
            int rnd = world.random.nextInt(0x100);
            if (frontBlock == Blocks.CAULDRON) {
                if (pipeFluid == PipeFluid.WATER && rnd < 0x10) {
                    // Start filling an empty cauldron with water.
                    world.setBlockState(frontPos,
                        Blocks.WATER_CAULDRON.getDefaultState()
                            .with(Properties.LEVEL_3, 1));
                    changed = true;
                }
                if (pipeFluid == PipeFluid.LAVA && rnd < 0x04) {
                    // Fill an empty cauldron with lava.
                    world.setBlockState(frontPos,
                        Blocks.LAVA_CAULDRON.getDefaultState());
                    changed = true;
                }
            } else if (frontBlock == Blocks.WATER_CAULDRON &&
                frontState.get(Properties.LEVEL_3) < 3) {
                if (pipeFluid == PipeFluid.WATER && rnd < 0x10) {
                    // Continue filling a water cauldron.
                    world.setBlockState(frontPos,
                        frontState.cycle(Properties.LEVEL_3));
                    changed = true;
                }
            }
        }
        return changed;
    }

    /**
     * Dispense fluid into the world.
     *
     * @return true if state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    protected static boolean dispense(
        World world, BlockPos pos, BlockState state, FluidPipeEntity entity) {
        boolean changed = false;
        boolean outflow = state.get(ModProperties.OUTFLOW);
        PipeFluid pipeFluid = state.get(ModProperties.FLUID);
        Direction facing = state.get(Properties.FACING);
        BlockState frontBlockState = world.getBlockState(pos.offset(facing));
        Block frontBlock = frontBlockState.getBlock();
        if (outflow) {
            if (frontBlock == Blocks.WATER) {
                if (pipeFluid != PipeFluid.WATER) {
                    // If the block in front of the pipe is water, but the pipe
                    // is not carrying water then remove the block and stop dispensing.
                    world.setBlockState(pos.offset(facing), Blocks.AIR.getDefaultState());
                    outflow = false;
                    changed = true;
                }
            } else if (frontBlock == Blocks.LAVA) {
                if (pipeFluid != PipeFluid.LAVA) {
                    // If the block in front of the pipe is lava, but the pipe
                    // is not carrying lava then remove the block and stop dispensing.
                    world.setBlockState(pos.offset(facing), Blocks.AIR.getDefaultState());
                    outflow = false;
                    changed = true;
                }
            } else {
                // If the block in front of the pipe is neither water nor lava,
                // then stop dispensing, but do not change the block.
                outflow = false;
                changed = true;
            }
        } else {
            if (frontBlockState.isAir()) {
                // If there is an empty space in front of the pipe ...
                if (pipeFluid == PipeFluid.WATER) {
                    // ... and there is water in the pipe then start dispensing water.
                    world.setBlockState(pos.offset(facing),
                        Blocks.WATER.getDefaultState());
                    outflow = true;
                    changed = true;
                } else {
                    if (pipeFluid == PipeFluid.LAVA) {
                        // ... and there is lava in the pipe then start dispensing lava.
                        world.setBlockState(pos.offset(facing),
                            Blocks.LAVA.getDefaultState());
                        outflow = true;
                        changed = true;
                    }
                }
            }
        }
        if (changed) {
            world.setBlockState(pos, state.with(ModProperties.OUTFLOW, outflow));
        }
        return changed;
    }

    /**
     * Called at every tick on the server.
     */
    public static void serverTick(
        World world, BlockPos pos, BlockState state, FluidPipeEntity entity) {
        FluidPipe block = (FluidPipe) state.getBlock();
        TickUtil.TickPos tickPos = getTickPos(world, state);
        if (tickPos == TickUtil.TickPos.START) {
            // Pull fluid.
            pull(world, pos, state, entity);
            // Clogging.
            float rnd = world.random.nextFloat();
            if (rnd < block.getCloggingProbability()) {
                clog(world, pos, state, entity);
            }
        }
        if (tickPos == TickUtil.TickPos.MIDDLE) {
            // Push fluid into blocks that are not capable of pulling it.
            push(world, pos, state, entity);
            // Dispense fluid.
            dispense(world, pos, state, entity);
        }
    }
}
