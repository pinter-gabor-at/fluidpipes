package eu.pintergabor.fluidpipes.block.util;

import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import org.jetbrains.annotations.NotNull;

import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;


public final class DripShowUtil {

    private DripShowUtil() {
        // Static class.
    }

    /**
     * @return a random number in the range of [-0.25…+0.25]
     */
    private static float getDripRnd(Random random) {
        return random.nextFloat() / 2F - 0.25F;
    }

    /**
     * Show dripping particles.
     *
     * @param world   The world.
     * @param pos     Pipe of fitting position.
     * @param state   Pipe of fitting state
     * @param yOffset Y offset of the dripping particle
     *                from the center bottom of the pipe or fitting.
     */
    public static void showDrip(
        @NotNull World world, @NotNull BlockPos pos, @NotNull BlockState state,
        double yOffset) {
        Random random = world.random;
        // This block.
        CanCarryFluid block = (CanCarryFluid) state.getBlock();
        PipeFluid fluid = CanCarryFluid.getFluid(state);
        boolean waterDripping =
            random.nextFloat() <
                block.getWaterDrippingProbability() + block.getWateringProbability() * 0.2F;
        boolean lavaDripping =
            random.nextFloat() <
                block.getLavaDrippingProbability() * 100F;
        if ((waterDripping && fluid == PipeFluid.WATER) ||
            lavaDripping && fluid == PipeFluid.LAVA) {
            // Particle position.
            float rx = getDripRnd(random);
            float rz = getDripRnd(random);
            Vec3d pPos = Vec3d.add(pos, 0.5 + rx, yOffset, 0.5 + rz);
            world.addParticle(
                fluid == PipeFluid.WATER ? ParticleTypes.DRIPPING_WATER : ParticleTypes.DRIPPING_LAVA,
                pPos.getX(), pPos.getY(), pPos.getZ(),
                0.0, 0.0, 0.0);
        }
    }
}
