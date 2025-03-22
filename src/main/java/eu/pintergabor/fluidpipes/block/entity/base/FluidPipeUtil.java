package eu.pintergabor.fluidpipes.block.entity.base;

import eu.pintergabor.fluidpipes.block.properties.PipeFluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


/**
 * More utilities for fluid pipes.
 * <p>
 * Dispense.
 */
public class FluidPipeUtil extends FluidPipeUtil1 {

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
}
