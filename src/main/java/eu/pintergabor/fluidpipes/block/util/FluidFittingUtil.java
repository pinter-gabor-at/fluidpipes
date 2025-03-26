package eu.pintergabor.fluidpipes.block.util;

import static eu.pintergabor.fluidpipes.block.BaseBlock.DIRECTIONS;
import static eu.pintergabor.fluidpipes.block.util.FluidUtil.oneSideSourceFluid;
import static eu.pintergabor.fluidpipes.registry.ModProperties.FLUID;

import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.entity.FluidFittingEntity;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;


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
     * @param world         The world.
     * @param pos           Pipe position.
     * @param canCarryWater Enable carrying water.
     * @param canCarryLava  Enable carrying lava.
     * @return The fluid coming from a side.
     */
    public static PipeFluid sideSourceFluid(
        World world, BlockPos pos,
        boolean canCarryWater, boolean canCarryLava) {
        for (Direction d : DIRECTIONS) {
            // Check all directions.
            PipeFluid nFluid = oneSideSourceFluid(
                world, pos, d, canCarryWater, canCarryLava);
            if (nFluid != PipeFluid.NONE) {
                return nFluid;
            }
        }
        return PipeFluid.NONE;
    }

    /**
     * Break the fitting carrying lava with some probability.
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
                // Replace the fitting with fire.
                world.setBlockState(pos,
                    Blocks.FIRE.getDefaultState());
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
        World world, BlockPos pos, BlockState state, FluidFittingEntity entity) {
        PipeFluid newFluid = null;
        // This block.
        PipeFluid pipeFluid = state.get(FLUID);
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
            world.setBlockState(pos, state.with(FLUID, newFluid));
            return true;
        }
        return false;
    }
}
