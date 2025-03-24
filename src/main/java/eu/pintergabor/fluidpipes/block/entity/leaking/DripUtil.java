package eu.pintergabor.fluidpipes.block.entity.leaking;

import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;

import net.minecraft.particle.ParticleTypes;

import org.jetbrains.annotations.NotNull;

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
    private static float getDripRnd(Random random) {
        return random.nextFloat() / 2F - 0.25F;
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
                    if (world instanceof World w) {
                        if (isLeakingWater(w, new BlockPos(x, y, z))) {
                            return true;
                        }
                    } else {
                        Global.LOGGER.error("There is no world ...");
                    }
                }
            }
        }
        return false;
    }

    public static void showDrip(
        @NotNull World world, @NotNull BlockPos pos, @NotNull BlockState state,
        double yOffset) {
        // This block.
        CanCarryFluid block = (CanCarryFluid) state.getBlock();
        PipeFluid fluid = CanCarryFluid.getFluid(state);
        boolean waterDripping =
            world.random.nextFloat() <
                block.getWaterDrippingProbability() + block.getWateringProbability() * 0.2F;
        boolean lavaDripping =
            world.random.nextFloat() <
                block.getLavaDrippingProbability() * 100F;
        if ((waterDripping && fluid == PipeFluid.WATER) ||
            lavaDripping && fluid == PipeFluid.LAVA) {
            // Particle position.
            float rx = getDripRnd(world.random);
            float rz = getDripRnd(world.random);
            Vec3d pPos = Vec3d.add(pos, 0.5 + rx, yOffset, 0.5 + rz);
            world.addParticle(
                fluid == PipeFluid.WATER ? ParticleTypes.DRIPPING_WATER : ParticleTypes.DRIPPING_LAVA,
                pPos.getX(), pPos.getY(), pPos.getZ(),
                0.0, 0.0, 0.0);
        }
    }
}
