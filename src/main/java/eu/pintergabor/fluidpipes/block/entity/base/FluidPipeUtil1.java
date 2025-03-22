package eu.pintergabor.fluidpipes.block.entity.base;

import eu.pintergabor.fluidpipes.block.properties.PipeFluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


/**
 * More utilities for fluid pipes.
 * <p>
 * Push.
 */
public class FluidPipeUtil1 extends FluidPipeUtil0 {

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
     * Push {@code pipeFluid} into any block that can accept it.
     *
     * @param world        The world.
     * @param frontPos     Position of the block in front of the pipe.
     * @param frontState   BlockState of the block in front of the pipe.
     * @param pipeFluid    Fluid to push.
     * @param waterFilling Enable filling with water.
     * @param lavaFilling  Enable filling with lava.
     * @return true if state changed.
     */
    @SuppressWarnings("RedundantIfStatement")
    public static boolean pushToBlock(
        World world, BlockPos frontPos, BlockState frontState, PipeFluid pipeFluid,
        boolean waterFilling, boolean lavaFilling) {
        // Check the block in front of the pipe.
        Block frontBlock = frontState.getBlock();
        if (frontBlock == Blocks.CAULDRON) {
            // Cauldron.
            if (pushToCauldron(
                world, frontPos, frontState,
                pipeFluid, waterFilling, lavaFilling)) {
                return true;
            }
        } else if (frontBlock == Blocks.WATER_CAULDRON &&
            frontState.get(Properties.LEVEL_3) < 3) {
            // Partially filled water cauldron.
            if (pushToWaterCauldron(
                world, frontPos, frontState,
                pipeFluid, waterFilling)) {
                return true;
            }
        }
        return false;
    }
}
