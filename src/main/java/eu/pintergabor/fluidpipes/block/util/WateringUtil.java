package eu.pintergabor.fluidpipes.block.util;

import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;


public final class WateringUtil {

    private WateringUtil() {
        // Static class.
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
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.NONE,
                entity));
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof CanCarryFluid block) {
            boolean watering =
                world.random.nextFloat() < block.getWateringProbability();
            PipeFluid fluid = CanCarryFluid.getFluid(state);
            return watering && fluid == PipeFluid.WATER;
        }
        return false;
    }

    /**
     * @return true if the block at {@code blockPos} is affected by the water carrying pipe of fitting.
     */
    private static boolean isLeakingWater(World world, BlockPos blockPos) {
        BlockState state = world.getBlockState(blockPos);
        if (state.getBlock() instanceof CanCarryFluid block) {
            boolean watering =
                world.random.nextFloat() < block.getWateringProbability();
            PipeFluid fluid = CanCarryFluid.getFluid(state);
            return watering && fluid == PipeFluid.WATER;
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
     * Check if there is a leaking water pipe within range.
     * <p>
     * Y range is fixed [0..12].
     *
     * @param world World
     * @param pos   Target position
     * @param range X and Z range [-range..+range]
     * @return true if there is a leaking water pipe or fitting in range.
     */
    public static boolean isWaterPipeNearby(World world, BlockPos pos, int range) {
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
