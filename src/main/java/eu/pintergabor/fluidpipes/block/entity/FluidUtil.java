package eu.pintergabor.fluidpipes.block.entity;

import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;


/**
 * Utilities common to fluid pipes and fittings.
 */
public sealed abstract class FluidUtil
    permits FluidPipeUtil0, FluidFittingUtil {

    /**
     * Clog the pipe or fitting.
     * <p>
     * Called randomly, and clears the fluid in the pipe or fitting.
     *
     * @return true if the state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    public static boolean clog(
        World world, BlockPos pos, BlockState state) {
        CanCarryFluid block = (CanCarryFluid) state.getBlock();
        PipeFluid fluid = state.get(ModProperties.FLUID, PipeFluid.NONE);
        if (fluid != PipeFluid.NONE) {
            float rnd = world.random.nextFloat();
            boolean clogging = rnd < block.getCloggingProbability();
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


    /**
     * Drip water on, or push water into, a cauldron.
     *
     * @param world The world.
     * @param pos   Position of the cauldron.
     * @param state BlockState of the cauldron.
     * @return true if state changed.
     */
    @SuppressWarnings("unused")
    private static boolean dripWaterOnCauldron(
        ServerWorld world, BlockPos pos, BlockState state) {
        // Start filling an empty cauldron with water.
        world.setBlockState(pos,
            Blocks.WATER_CAULDRON.getDefaultState()
                .with(Properties.LEVEL_3, 1));
        return true;
    }

    /**
     * Drip lava on, or push lava into, a cauldron.
     *
     * @param world The world.
     * @param pos   Position of the cauldron.
     * @param state BlockState of the cauldron.
     * @return true if state changed.
     */
    @SuppressWarnings("unused")
    private static boolean dripLavaOnCauldron(
        ServerWorld world, BlockPos pos, BlockState state) {
        // Fill an empty cauldron with lava.
        world.setBlockState(pos,
            Blocks.LAVA_CAULDRON.getDefaultState());
        return true;
    }

    /**
     * Drip water on, or push water into,
     * a partially filled water cauldron.
     *
     * @param world The world.
     * @param pos   Position of the cauldron.
     * @param state BlockState of the cauldron.
     * @return true if state changed.
     */
    @SuppressWarnings("unused")
    private static boolean dripWaterOnWaterCauldron(
        ServerWorld world, BlockPos pos, BlockState state) {
        // Continue filling a water cauldron.
        world.setBlockState(pos,
            state.cycle(Properties.LEVEL_3));
        return true;
    }

    /**
     * Drip water on, or push water into, a dirt block.
     *
     * @param world The world.
     * @param pos   Position of the block.
     * @param state BlockState of the block.
     * @return true if state changed.
     */
    @SuppressWarnings("unused")
    private static boolean dripWaterOnDirt(
        ServerWorld world, BlockPos pos, BlockState state) {
        // Water dripping on dirt changes it to mud.
        world.setBlockState(pos,
            Blocks.MUD.getDefaultState());
        return true;
    }

    /**
     * Drip water on, or push water into, a fire block.
     *
     * @param world The world.
     * @param pos   Position of the block.
     * @param state BlockState of the block.
     * @return true if state changed.
     */
    @SuppressWarnings("unused")
    private static boolean dripWaterOnFire(
        ServerWorld world, BlockPos pos, BlockState state) {
        // Water dripping on fire extinguishes the fire.
        world.breakBlock(pos, true);
        return true;
    }

    /**
     * Drip water on, or push water into, a block.
     *
     * @param world The world.
     * @param pos   Position of the block.
     * @param state BlockState of the block.
     * @return true if state changed.
     */
    public static boolean dripWaterOnBlock(
        ServerWorld world, BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        if (block == Blocks.CAULDRON) {
            // Cauldron.
            return dripWaterOnCauldron(world, pos, state);
        } else if (block == Blocks.WATER_CAULDRON) {
            // Partially filled water cauldron.
            return state.get(Properties.LEVEL_3) < 3 &&
                dripWaterOnWaterCauldron(world, pos, state);
        } else if (block == Blocks.DIRT) {
            // Dirt.
            return dripWaterOnDirt(world, pos, state);
        } else if (block == Blocks.FIRE) {
            // Fire.
            return dripWaterOnFire(world, pos, state);
        }
        return false;
    }

    /**
     * Drip lava on, or push lava into, a block.
     *
     * @param world The world.
     * @param pos   Position of the block.
     * @param state BlockState of the block.
     * @return true if state changed.
     */
    public static boolean dripLavaOnBlock(
        ServerWorld world, BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        if (block == Blocks.CAULDRON) {
            // Cauldron.
            return dripLavaOnCauldron(world, pos, state);
        }
        return false;
    }

    /**
     * Dripping water action for pipes and fittings.
     *
     * @param world The world.
     * @param pos   Position of the pipe or the fitting.
     * @param state BlockState of the pipe or the fitting.
     * @return true if anything changed.
     */
    @SuppressWarnings("unusedReturnValue")
    public static boolean dripWaterDown(
        ServerWorld world, BlockPos pos, BlockState state) {
        CanCarryFluid block = (CanCarryFluid) state.getBlock();
        float rnd = world.random.nextFloat();
        boolean waterDripping = rnd < block.getWaterDrippingProbability();
        if (waterDripping) {
            // Search down.
            for (int dy = 1; dy <= 12; dy++) {
                BlockPos nPos = pos.down(dy);
                BlockState nState = world.getBlockState(nPos);
                if (!world.getFluidState(nPos).isEmpty()) {
                    // A block containing any liquid stops the drip.
                    return false;
                }
                if (dripWaterOnBlock(world, nPos, nState)) {
                    // A block that reacts with the drip stops the drip.
                    return true;
                }
                if (nState.getCollisionShape(world, nPos) != VoxelShapes.empty()) {
                    // A solid block stops the drip.
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Dripping lava action for pipes and fittings.
     *
     * @param world The world.
     * @param pos   Position of the pipe or the fitting.
     * @param state BlockState of the pipe or the fitting.
     * @return true if anything changed.
     */
    @SuppressWarnings("unusedReturnValue")
    public static boolean dripLavaDown(
        ServerWorld world, BlockPos pos, BlockState state) {
        CanCarryFluid block = (CanCarryFluid) state.getBlock();
        float rnd = world.random.nextFloat();
        boolean lavaDripping = rnd < block.getWaterDrippingProbability();
        if (lavaDripping) {
            // Search down.
            for (int dy = 1; dy <= 12; dy++) {
                BlockPos nPos = pos.down(dy);
                BlockState nState = world.getBlockState(nPos);
                if (!world.getFluidState(nPos).isEmpty()) {
                    // A block containing any liquid stops the drip.
                    return false;
                }
                if (dripLavaOnBlock(world, nPos, nState)) {
                    // A block that reacts with the drip stops the drip.
                    return true;
                }
                if (nState.getCollisionShape(world, nPos) != VoxelShapes.empty()) {
                    // A solid block stops the drip.
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Dripping action for pipes and fittings.
     *
     * @param world The world.
     * @param pos   Position of the pipe or the fitting.
     * @param state BlockState of the pipe or the fitting.
     * @return true if anything changed.
     */
    @SuppressWarnings("unusedReturnValue")
    public static boolean dripDown(
        ServerWorld world, BlockPos pos, BlockState state) {
        PipeFluid fluid = state.get(ModProperties.FLUID);
        return switch (fluid) {
            case WATER -> dripWaterDown(world, pos, state);
            case LAVA -> dripLavaDown(world, pos, state);
            case NONE -> false;
        };
    }
}
