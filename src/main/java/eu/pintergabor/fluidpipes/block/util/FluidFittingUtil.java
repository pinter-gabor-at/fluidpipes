package eu.pintergabor.fluidpipes.block.util;

import static eu.pintergabor.fluidpipes.block.BaseBlock.DIRECTIONS;
import static eu.pintergabor.fluidpipes.block.util.FluidUtil.oneSideSourceFluid;
import static eu.pintergabor.fluidpipes.registry.ModProperties.FLUID;

import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.entity.FluidFittingEntity;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


/**
 * Utilities for fluid fittings.
 */
public final class FluidFittingUtil {

    private FluidFittingUtil() {
        // Static class.
    }

    /**
     * Get the fluid coming from pipes pointing towards this fitting.
     *
     * @param level         The world.
     * @param pos           Pipe position.
     * @param canCarryWater Enable carrying water.
     * @param canCarryLava  Enable carrying lava.
     * @return The fluid coming from a side.
     */
    public static PipeFluid sideSourceFluid(
        Level level, BlockPos pos,
        boolean canCarryWater, boolean canCarryLava) {
        for (Direction d : DIRECTIONS) {
            // Check all directions.
            PipeFluid nFluid = oneSideSourceFluid(
                level, pos, d, canCarryWater, canCarryLava);
            if (nFluid != PipeFluid.NONE) {
                return nFluid;
            }
        }
        return PipeFluid.NONE;
    }

    /**
     * Break the fitting carrying lava with some probability.
     *
     * @param level The world.
     * @param pos   Position of the block.
     * @param state BlockState of the block.
     * @return true if state changed.
     */
    @SuppressWarnings("UnusedReturnValue")
    public static boolean breakFire(
        ServerLevel level, BlockPos pos, BlockState state
    ) {
        PipeFluid fluid = state.getValue(ModProperties.FLUID);
        boolean waterlogged = state.getValueOrElse(BlockStateProperties.WATERLOGGED, false);
        if (!waterlogged && fluid == PipeFluid.LAVA) {
            CanCarryFluid block = (CanCarryFluid) state.getBlock();
            boolean fire =
                level.random.nextFloat() < block.getFireBreakProbability();
            if (fire) {
                // Replace the fitting with fire.
                level.setBlockAndUpdate(pos,
                    Blocks.FIRE.defaultBlockState());
                return true;
            }

        }
        return false;
    }

    /**
     * Pull fluid from any pipe pointing to this fitting.
     *
     * @return true if the state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    public static boolean pull(
        Level world, BlockPos pos, BlockState state, FluidFittingEntity entity) {
        PipeFluid newFluid = null;
        // This block.
        PipeFluid pipeFluid = state.getValue(FLUID);
        FluidFitting block = (FluidFitting) state.getBlock();
        boolean canCarryWater = block.canCarryWater();
        boolean canCarryLava = block.canCarryLava();
        // Find a pipe pointing to this pipe from any side.
        PipeFluid sideFluid = sideSourceFluid(
            world, pos,
            canCarryWater, canCarryLava);
        if (sideFluid != PipeFluid.NONE) {
            // Water or lava is coming from the side.
            if (pipeFluid != sideFluid) {
                newFluid = sideFluid;
            }
        } else if (pipeFluid != PipeFluid.NONE) {
            // No source from any side.
            newFluid = PipeFluid.NONE;
        }
        if (newFluid != null) {
            // Apply changes.
            world.setBlockAndUpdate(pos, state.setValue(FLUID, newFluid));
            return true;
        }
        return false;
    }
}
