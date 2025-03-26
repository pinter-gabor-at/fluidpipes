package eu.pintergabor.fluidpipes.block.util;

import static eu.pintergabor.fluidpipes.registry.ModProperties.OUTFLOW;
import static net.minecraft.state.property.Properties.FACING;

import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;


/**
 * More utilities for fluid pipes.
 * <p>
 * Dispense.
 */
public final class FluidDispenseUtil {

    private FluidDispenseUtil() {
        // Static class.
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
    public static boolean startDispense(
        World world, BlockPos frontPos, BlockState frontState, PipeFluid pipeFluid) {
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
    public static boolean stopDispense(
        World world, BlockPos frontPos, BlockState frontState, PipeFluid pipeFluid) {
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
     * Remove the outflow prior to breaking or turning the block.
     * <p>
     * Do not update the state, because it may be called before the break,
     * and updating is not allowed there.
     *
     * @param world The world.
     * @param pos   Position of the block.
     * @param state BlockState of the block.
     */
    public static void removeOutflow(
        World world, BlockPos pos, BlockState state) {
        // This block.
        Direction facing = state.get(FACING);
        PipeFluid fluid = state.get(ModProperties.FLUID);
        boolean outflow = state.get(OUTFLOW);
        // The block in front of this.
        BlockPos frontPos = pos.offset(facing);
        BlockState frontState = world.getBlockState(frontPos);
        if (outflow) {
            if (frontState.isOf(Blocks.WATER)) {
                if (fluid == PipeFluid.WATER) {
                    // If the block in front of the pipe is water, and the pipe
                    // is carrying water then remove the block.
                    world.setBlockState(frontPos, Blocks.AIR.getDefaultState());
                }
            } else if (frontState.isOf(Blocks.LAVA)) {
                if (fluid == PipeFluid.LAVA) {
                    // If the block in front of the pipe is lava, and the pipe
                    // is carrying lava then remove the block.
                    world.setBlockState(frontPos, Blocks.AIR.getDefaultState());
                }
            }
        }
        // outflow is false and FLUID is NONE, but do not update the state.
    }

    /**
     * Break the pipe carrying lava with some probability.
     *
     * @param world The world.
     * @param pos   Position of the block.
     * @param state BlockState of the block.
     * @return true if state changed.
     */
    @SuppressWarnings("UnusedReturnValue")
    public static boolean breakFire(
        ServerWorld world, BlockPos pos, BlockState state) {
        PipeFluid fluid = state.get(ModProperties.FLUID);
        boolean waterlogged = state.get(Properties.WATERLOGGED, false);
        if (!waterlogged && fluid == PipeFluid.LAVA) {
            CanCarryFluid block = (CanCarryFluid) state.getBlock();
            boolean fire =
                world.random.nextFloat() < block.getFireBreakProbability();
            if (fire) {
                // Replace the pipe with fire.
                world.setBlockState(pos,
                    Blocks.FIRE.getDefaultState());
                return true;
            }
        }
        return false;
    }

    /**
     * Dispense fluid into the world.
     *
     * @return true if state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    public static boolean dispense(
        World world, BlockPos pos, BlockState state) {
        boolean changed = false;
        // This block.
        Direction facing = state.get(FACING);
        boolean outflow = state.get(OUTFLOW);
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
            world.setBlockState(pos, state.with(OUTFLOW, outflow));
        }
        return changed;
    }
}
