package eu.pintergabor.fluidpipes.block.util;

import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;


/**
 * More utilities common to fluid pipes and fittings.
 * <p>
 * Drips.
 */
public final class DripActionUtil {

    private DripActionUtil() {
        // Static class.
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
     * Start a fire above the block, because lava is dripping on it.
     *
     * @param world The world.
     * @param pos   Position of the block.
     * @param state BlockState of the block.
     * @return true if state changed.
     */
    @SuppressWarnings("unused")
    public static boolean dripLavaStartFire(
        ServerWorld world, BlockPos pos, BlockState state) {
        BlockPos uPos = pos.up();
        BlockState uState = world.getBlockState(uPos);
        if (uState.isAir()) {
            world.setBlockState(uPos,
                Blocks.FIRE.getDefaultState());
            return true;
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
        boolean waterDripping =
            world.random.nextFloat() < block.getWaterDrippingProbability();
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
        boolean lavaDripping =
            world.random.nextFloat() < block.getLavaDrippingProbability();
        // Search down.
        for (int dy = 1; dy <= 12; dy++) {
            BlockPos nPos = pos.down(dy);
            BlockState nState = world.getBlockState(nPos);
            if (!world.getFluidState(nPos).isEmpty()) {
                // A block containing any liquid stops the drip.
                return false;
            }
            if (lavaDripping) {
                if (dripLavaOnBlock(world, nPos, nState)) {
                    // A block that reacts with the drip stops the drip.
                    return true;
                }
            }
            if (nState.getCollisionShape(world, nPos) != VoxelShapes.empty()) {
                // A solid block stops the drip, but may start a fire.
                boolean startFire =
                    world.random.nextFloat() < block.getFireDripProbability();
                return startFire && dripLavaStartFire(world, nPos, nState);
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
