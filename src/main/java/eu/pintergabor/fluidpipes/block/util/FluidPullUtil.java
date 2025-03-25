package eu.pintergabor.fluidpipes.block.util;

import static eu.pintergabor.fluidpipes.block.util.FluidUtil.oneSideSourceFluid;

import eu.pintergabor.fluidpipes.block.BaseBlock;
import eu.pintergabor.fluidpipes.block.BaseFitting;
import eu.pintergabor.fluidpipes.block.BasePipe;
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
 * <p>
 * Pull.
 */
public final class FluidPullUtil {

    private FluidPullUtil() {
        // Static class.
    }

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
     * Get the fluid coming from a pipe or fitting at the back of this pipe.
     *
     * @param backState     BlockState of the block at the back of the pipe.
     * @param pipeFluid     Fluid in the pipe now.
     * @param canCarryWater Enable carrying water.
     * @param canCarryLava  Enable carrying lava.
     * @return The fluid coming from a side.
     */
    @SuppressWarnings("unused")
    public static PipeFluid backSourceFluid(
        BlockState backState, PipeFluid pipeFluid,
        boolean canCarryWater, boolean canCarryLava) {
        if (canCarryLava && isLavaSource(backState)) {
            // If a lava source from the back is supplying lava.
            return PipeFluid.LAVA;
        } else if (canCarryWater && isWaterSource(backState)) {
            // If a water source from the back is supplying water.
            return PipeFluid.WATER;
        }
        return PipeFluid.NONE;
    }
}
