package eu.pintergabor.fluidpipes.block.entity.base;

import eu.pintergabor.fluidpipes.block.base.BaseBlock;
import eu.pintergabor.fluidpipes.block.base.BaseFitting;
import eu.pintergabor.fluidpipes.block.base.BasePipe;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;


/**
 * Utilities for fluid pipes.
 */
public class FluidPipeUtil extends FluidUtil {

    /**
     * Check if the block can be used as a natural water source.
     *
     * @param state {@link BlockState} (which includes reference to the {@link Block})
     * @return true if it is a water source
     */
    @SuppressWarnings("RedundantIfStatement")
    private static boolean isNaturalWaterSource(BlockState state) {
        Block block = state.getBlock();
        // If it is a still or flowing water block.
        if (block == Blocks.WATER) {
            return true;
        }
        // If it is a full water cauldron.
        if (block == Blocks.WATER_CAULDRON &&
            (state.get(Properties.LEVEL_3) == 3)) {
            return true;
        }
        if (state.get(Properties.WATERLOGGED, false)) {
            // If it is a waterlogged block.
            return true;
        }
        return false;
    }

    /**
     * Check if the block can be used as a water source defined in this mod.
     *
     * @param state {@link BlockState} (which includes reference to the {@link Block})
     * @return true if it is a water source
     */
    @SuppressWarnings("RedundantIfStatement")
    private static boolean isModWaterSource(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof BasePipe) {
            // If it is a pipe carrying water.
            if ((state.get(ModProperties.FLUID, PipeFluid.NONE) == PipeFluid.WATER)) {
                return true;
            }
        }
        if (block instanceof BaseFitting) {
            // If it is an unpowered fitting carrying water.
            if (!state.get(Properties.POWERED, false) &&
                state.get(ModProperties.FLUID, PipeFluid.NONE) == PipeFluid.WATER) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the block can be used as a water source.
     *
     * @param state {@link BlockState} (which includes reference to the {@link Block})
     * @return true if it is a water source
     */
    public static boolean isWaterSource(BlockState state) {
        return isNaturalWaterSource(state) || isModWaterSource(state);
    }

    /**
     * Check if the block can be used as a natural lava source.
     *
     * @param state {@link BlockState} (which includes reference to the {@link Block})
     * @return true if it is a lava source
     */
    @SuppressWarnings("RedundantIfStatement")
    private static boolean isNaturalLavaSource(BlockState state) {
        Block block = state.getBlock();
        // If it is a still or flowing lava block.
        if (block == Blocks.LAVA) {
            return true;
        }
        // If it is a lava cauldron.
        if (block == Blocks.LAVA_CAULDRON) {
            return true;
        }
        return false;
    }

    /**
     * Check if the block can be used as a lava source defined in this mod.
     *
     * @param state {@link BlockState} (which includes reference to the {@link Block})
     * @return true if it is a lava source
     */
    @SuppressWarnings("RedundantIfStatement")
    private static boolean isModLavaSource(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof BasePipe) {
            // If it is a pipe carrying lava.
            if (state.get(ModProperties.FLUID, PipeFluid.NONE) == PipeFluid.LAVA) {
                return true;
            }
        }
        if (block instanceof BaseFitting) {
            // If it is an unpowered fitting carrying lava.
            if (!state.get(Properties.POWERED, false) &&
                state.get(ModProperties.FLUID, PipeFluid.NONE) == PipeFluid.LAVA) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the block can be used as a water source.
     *
     * @param state {@link BlockState} (which includes reference to the {@link Block})
     * @return true if it is a water source
     */
    public static boolean isLavaSource(BlockState state) {
        return isNaturalLavaSource(state) || isModLavaSource(state);
    }

    /**
     * Get the fluid coming from pipes pointing towards this pipe from a side.
     *
     * @param world         The world.
     * @param pos           Pipe position.
     * @param facing        Pipe orientation.
     * @param opposite      = facing.getOpposite();
     * @param canCarryWater Enable carrying water.
     * @param canCarryLava  Enable carrying lava.
     * @return The fluid coming from a side.
     */
    public static PipeFluid sideSourceFluid(
        World world, BlockPos pos, Direction facing, Direction opposite,
        boolean canCarryWater, boolean canCarryLava) {
        for (Direction d : BaseBlock.DIRECTIONS) {
            if (d == facing || d == opposite) continue;
            PipeFluid nFluid = oneSideSourceFluid(
                world, pos, d, canCarryWater, canCarryLava);
            if (nFluid != PipeFluid.NONE) {
                return nFluid;
            }
        }
        return PipeFluid.NONE;
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
    public static boolean pushToCauldron(
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
    public static boolean pushToWaterCauldron(
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
     * Start dispensing {@code pipeFluid}, if possible.
     *
     * @param world      The world.
     * @param frontPos   Position of the block in front of the pipe.
     * @param frontState BlockState of block in front of the pipe.
     * @param pipeFluid  Fluid to dispense.
     * @return true if state changed.
     */
    public static boolean startDispense(
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
    public static boolean stopDispense(
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
}
