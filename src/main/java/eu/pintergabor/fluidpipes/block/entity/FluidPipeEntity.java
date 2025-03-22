package eu.pintergabor.fluidpipes.block.entity;

import static eu.pintergabor.fluidpipes.block.base.BaseBlock.getTickPos;

import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.base.BaseBlock;
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
     * Get the fluid coming from pipes pointing towards this pipe from a side.
     */
    private static PipeFluid sideSourceFluid(
        World world, BlockPos pos, Direction facing, Direction opposite) {
        for (Direction d : BaseBlock.DIRECTIONS) {
            if (d == facing || d == opposite) continue;
            BlockState nState = world.getBlockState(pos.offset(d));
            Block nBlock = nState.getBlock();
            if (nBlock instanceof FluidPipe &&
                nState.get(Properties.FACING) == d.getOpposite()) {
                PipeFluid nFluid = nState.get(ModProperties.FLUID);
                if (nFluid != PipeFluid.NONE) {
                    // Water or lava is coming from the side.
                    return nFluid;
                }
            }
        }
        return PipeFluid.NONE;
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
            PipeFluid sideFluid = sideSourceFluid(world, pos, facing, opposite);
            if (sideFluid != PipeFluid.NONE) {
                // Water or lava is coming from the side.
                sideSourcing = true;
                if (pipeFluid != sideFluid) {
                    pipeFluid = sideFluid;
                    changed = true;
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
     * Push {@code pipeFluid} into a cauldron.
     *
     * @param world        The world.
     * @param frontPos     Position of the cauldron.
     * @param frontState   BlockState of the cauldron.
     * @param pipeFluid    Fluid to push.
     * @param waterFilling Enable filling with water.
     * @param lavaFilling  Enable filling with lava.
     * @return true if state changed.
     */
    @SuppressWarnings("unused")
    private static boolean pushToCauldron(
        World world, BlockPos frontPos, BlockState frontState, PipeFluid pipeFluid,
        boolean waterFilling, boolean lavaFilling) {
        if (waterFilling && pipeFluid == PipeFluid.WATER) {
            // Start filling an empty cauldron with water.
            world.setBlockState(frontPos,
                Blocks.WATER_CAULDRON.getDefaultState()
                    .with(Properties.LEVEL_3, 1));
            return true;
        }
        if (lavaFilling && pipeFluid == PipeFluid.LAVA) {
            // Fill an empty cauldron with lava.
            world.setBlockState(frontPos,
                Blocks.LAVA_CAULDRON.getDefaultState());
            return true;
        }
        return false;
    }

    /**
     * Push {@code pipeFluid} into a partly filled water cauldron.
     *
     * @param world        The world.
     * @param frontPos     Position of the cauldron.
     * @param frontState   BlockState of the cauldron.
     * @param pipeFluid    Fluid to push.
     * @param waterFilling Enable filling with water.
     * @return true if state changed.
     */
    private static boolean pushToWaterCauldron(
        World world, BlockPos frontPos, BlockState frontState, PipeFluid pipeFluid,
        boolean waterFilling) {
        if (waterFilling && pipeFluid == PipeFluid.WATER) {
            // Continue filling a water cauldron.
            world.setBlockState(frontPos,
                frontState.cycle(Properties.LEVEL_3));
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
        FluidPipe block = (FluidPipe) state.getBlock();
        // The block in front of this.
        BlockPos frontPos = pos.offset(facing);
        BlockState frontState = world.getBlockState(frontPos);
        Block frontBlock = frontState.getBlock();
        // Logic.
        if (pipeFluid != PipeFluid.NONE) {
            float rnd = world.random.nextFloat();
            boolean waterFilling = rnd < block.getWaterFillingProbability();
            boolean lavaFilling = rnd < block.getLavaFillingProbability();
            // Check the block in front of the pipe.
            if (frontBlock == Blocks.CAULDRON) {
                // Cauldron.
                if (pushToCauldron(
                    world, frontPos, frontState,
                    pipeFluid, waterFilling, lavaFilling)) {
                    changed = true;
                }
            } else if (frontBlock == Blocks.WATER_CAULDRON &&
                frontState.get(Properties.LEVEL_3) < 3) {
                // Partially filled water cauldron.
                if (pushToWaterCauldron(
                    world, frontPos, frontState,
                    pipeFluid, waterFilling)) {
                    changed = true;
                }
            }
        }
        return changed;
    }

    /**
     * Start dispensing {@code pipeFluid}, if possible.
     *
     * @param world      The world.
     * @param frontPos   Position of the block in front of the pipe.
     * @param frontState BlockState of block in front of the pipe.
     * @param pipeFluid  Fluid to dispense.
     * @return true if state changed.
     */
    private static boolean startDispense(
        World world, BlockPos frontPos, BlockState frontState, PipeFluid pipeFluid
    ) {
        if (frontState.isAir()) {
            // If there is an empty space in front of the pipe ...
            if (pipeFluid == PipeFluid.WATER) {
                // ... and there is water in the pipe then start dispensing water.
                world.setBlockState(frontPos,
                    Blocks.WATER.getDefaultState());
                return true;
            } else {
                if (pipeFluid == PipeFluid.LAVA) {
                    // ... and there is lava in the pipe then start dispensing lava.
                    world.setBlockState(frontPos,
                        Blocks.LAVA.getDefaultState());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Stop dispensing {@code pipeFluid}, if possible.
     *
     * @param world      The world.
     * @param frontPos   Position of the block in front of the pipe.
     * @param frontState BlockState of block in front of the pipe.
     * @param pipeFluid  Fluid to dispense.
     * @return true if state changed.
     */
    private static boolean stopDispense(
        World world, BlockPos frontPos, BlockState frontState, PipeFluid pipeFluid
    ) {
        if (frontState.isOf(Blocks.WATER)) {
            if (pipeFluid != PipeFluid.WATER) {
                // If the block in front of the pipe is water, but the pipe
                // is not carrying water then remove the block and stop dispensing.
                world.setBlockState(frontPos, Blocks.AIR.getDefaultState());
                return true;
            }
        } else if (frontState.isOf(Blocks.LAVA)) {
            if (pipeFluid != PipeFluid.LAVA) {
                // If the block in front of the pipe is lava, but the pipe
                // is not carrying lava then remove the block and stop dispensing.
                world.setBlockState(frontPos, Blocks.AIR.getDefaultState());
                return true;
            }
        } else {
            // If the block in front of the pipe is neither water nor lava,
            // then stop dispensing, but do not change the block.
            return true;
        }
        return false;
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
        // This block.
        Direction facing = state.get(Properties.FACING);
        boolean outflow = state.get(ModProperties.OUTFLOW);
        PipeFluid pipeFluid = state.get(ModProperties.FLUID);
        // The block in front of this.
        BlockPos frontPos = pos.offset(facing);
        BlockState frontState = world.getBlockState(frontPos);
        Block frontBlock = frontState.getBlock();
        // Logic.
        if (!outflow) {
            if (startDispense(world, frontPos, frontState, pipeFluid)) {
                // Start dispensing.
                outflow = true;
                changed = true;
            }
        } else {
            if (stopDispense(world, frontPos, frontState, pipeFluid)) {
                // Stop dispensing.
                outflow = false;
                changed = true;
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
