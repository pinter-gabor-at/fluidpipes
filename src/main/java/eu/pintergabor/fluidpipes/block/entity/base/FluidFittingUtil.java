package eu.pintergabor.fluidpipes.block.entity.base;

import static eu.pintergabor.fluidpipes.block.base.BaseBlock.DIRECTIONS;

import eu.pintergabor.fluidpipes.block.properties.PipeFluid;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;


/**
 * Utilities for fluid fittings.
 */
public class FluidFittingUtil extends FluidUtil {

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
            PipeFluid nFluid = oneSideSourceFluid(
                world, pos, d, canCarryWater, canCarryLava);
            if (nFluid != PipeFluid.NONE) {
                return nFluid;
            }
        }
        return PipeFluid.NONE;
    }
}
