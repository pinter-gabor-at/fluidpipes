package eu.pintergabor.fluidpipes.block.entity.leaking;

import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import org.jetbrains.annotations.NotNull;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;


public final class DripUtil {

    private DripUtil() {
        // Static class.
    }

    /**
     * @return a random number in the range of [-0.25…+0.25]
     */
    private static double getDripRnd(Random random) {
        return random.nextDouble() / 2.0 - 0.25;
    }

    /**
     * Utility for calculating drip point for dripping pipes.
     *
     * @return random drip point X.
     */
    public static double getDripX(@NotNull Direction direction, Random random) {
        return switch (direction) {
            case DOWN, SOUTH, NORTH -> 0.5 + getDripRnd(random);
            case UP -> 0.5;
            case EAST -> 1.05;
            case WEST -> -0.05;
        };
    }

    /**
     * Utility for calculating drip point for dripping pipes.
     *
     * @return random drip point Y.
     */
    public static double getDripY(@NotNull Direction direction, Random random) {
        return switch (direction) {
            case DOWN -> -0.05;
            case UP -> 1.05;
            case NORTH, WEST, EAST, SOUTH -> 0.4375 + getDripRnd(random);
        };
    }

    /**
     * Utility for calculating drip point for dripping pipes.
     *
     * @return random drip point Z.
     */
    public static double getDripZ(@NotNull Direction direction, Random random) {
        return switch (direction) {
            case DOWN, EAST, WEST -> 0.5 + getDripRnd(random);
            case UP -> 0.5;
            case NORTH -> -0.05;
            case SOUTH -> 1.05;
        };
    }

    /**
     * @return true if the {@code entity} is affected by the water carrying pipe of fitting.
     */
    private static boolean isLeakingWater(Entity entity, Vec3d blockCenterPos) {
        Vec3d entityPos = entity.getPos();
        World world = entity.getWorld();
        BlockHitResult hitResult = world.raycast(
            new RaycastContext(entityPos,
                blockCenterPos,
                RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof CanCarryFluid pipeBlock) {
            PipeFluid fluid = pipeBlock.getLeakingFluid(state);
            return fluid == PipeFluid.WATER;
        }
        return false;
    }

    /**
     * Check if there is a leaking water pipe within range.
     * <p>
     * Y range is fixed [0..12].
     *
     * @param entity Target entity
     * @param range  X and Z range [-range..+range]
     * @return true if there is a leaking water pipe or fitting in range.
     */
    public static boolean isWaterPipeNearby(Entity entity, int range) {
        // Target coordinates.
        int targetX = entity.getBlockX();
        int targetY = entity.getBlockY();
        int targetZ = entity.getBlockZ();
        // Search for a leaking water carrying pipe or fitting in range
        // [-range..+range, 0..12, -range..+range] of the target block.
        for (int y = targetY; y <= targetY + 12; y++) {
            for (int x = targetX - range; x <= targetX + range; x++) {
                for (int z = targetZ - range; z <= targetZ + range; z++) {
                    if (isLeakingWater(entity, new Vec3d(x + 0.5, y + 0.5, z + 0.5))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return true if the block at {@code blockPos} is affected by the water carrying pipe of fitting.
     */
    private static boolean isLeakingWater(BlockView world, BlockPos blockPos) {
        BlockState state = world.getBlockState(blockPos);
        Block block = state.getBlock();
        if (block instanceof CanCarryFluid pipeBlock) {
            PipeFluid fluid = pipeBlock.getLeakingFluid(state);
            return fluid == PipeFluid.WATER;
        }
        return false;
    }

    /**
     * Check if there is a leaking water pipe within range.
     * <p>
     * Y range is fixed [0..12].
     *
     * @param world World
     * @param pos   Target position
     * @param range X and Z range [-range..+range]
     * @return true if there is a leaking water pipe or fitting in range.
     */
    public static boolean isWaterPipeNearby(BlockView world, BlockPos pos, int range) {
        // Target coordinates.
        int targetX = pos.getX();
        int targetY = pos.getY();
        int targetZ = pos.getZ();
        // Search for a leaking water carrying pipe or fitting in range
        // [-range..+range, 0..12, -range..+range] of the target block.
        for (int y = targetY; y <= targetY + 12; y++) {
            for (int x = targetX - range; x <= targetX + range; x++) {
                for (int z = targetZ - range; z <= targetZ + range; z++) {
                    if (isLeakingWater(world, new BlockPos(x, y, z))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
