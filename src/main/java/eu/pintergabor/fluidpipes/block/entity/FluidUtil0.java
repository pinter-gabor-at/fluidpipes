package eu.pintergabor.fluidpipes.block.entity;

import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;


/**
 * Utilities common to fluid pipes and fittings.
 */
public sealed abstract class FluidUtil0
    permits FluidUtil {

    /**
     * Clog the pipe or fitting with some probability.
     *
     * @return true if the state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    public static boolean clog(
        World world, BlockPos pos, BlockState state) {
        CanCarryFluid block = (CanCarryFluid) state.getBlock();
        PipeFluid fluid = state.get(ModProperties.FLUID, PipeFluid.NONE);
        if (fluid != PipeFluid.NONE) {
            boolean clogging =
                world.random.nextFloat() < block.getCloggingProbability();
            if (clogging) {
                world.setBlockState(pos,
                    state.with(ModProperties.FLUID, PipeFluid.NONE));
                return true;
            }
        }
        return false;
    }

    /**
     * Get the fluid coming from a pipe in direction {@code d}.
     *
     * @param world         The world.
     * @param pos           Pipe position.
     * @param d             Direction to check.
     * @param canCarryWater Enable carrying water.
     * @param canCarryLava  Enable carrying lava.
     * @return The fluid coming from side {@code d}.
     */
    protected static PipeFluid oneSideSourceFluid(
        World world, BlockPos pos, Direction d,
        boolean canCarryWater, boolean canCarryLava) {
        BlockState nState = world.getBlockState(pos.offset(d));
        Block nBlock = nState.getBlock();
        if (nBlock instanceof FluidPipe &&
            nState.get(Properties.FACING) == d.getOpposite()) {
            PipeFluid nFluid = nState.get(ModProperties.FLUID);
            if ((canCarryWater && nFluid == PipeFluid.WATER) ||
                (canCarryLava && nFluid == PipeFluid.LAVA)) {
                // Water or lava is coming from the side.
                return nFluid;
            }
        }
        return PipeFluid.NONE;
    }
}
