package eu.pintergabor.fluidpipes.block.util;

import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;


/**
 * Show drips on pipes and fittings.
 */
public final class DripShowUtil {

	private DripShowUtil() {
		// Static class.
	}

	/**
	 * Get a random position offset for drip visualization.
	 *
	 * @return a random number in the range of [-0.25…+0.25]
	 */
	private static float getDripRnd(final @NonNull RandomSource random) {
		return random.nextFloat() * 0.5F - 0.25F;
	}

	/**
	 * Show dripping particles.
	 *
	 * @param level   The world.
	 * @param pos     Pipe of fitting position.
	 * @param state   Pipe of fitting state
	 * @param yOffset Y offset of the dripping particle
	 *                from the center bottom of the pipe or fitting.
	 */
	public static void showDrip(
		final @NonNull Level level,
		final @NonNull BlockPos pos,
		final @NonNull BlockState state,
		final double yOffset
	) {
		final RandomSource random = level.getRandom();
		// This block.
		final CanCarryFluid block = (CanCarryFluid) state.getBlock();
		final PipeFluid fluid = CanCarryFluid.getFluid(state);
		final boolean waterDripping =
			random.nextFloat() <
				block.getWaterDrippingProbability() +
					block.getWateringProbability() * 0.2F;
		final boolean lavaDripping =
			random.nextFloat() <
				block.getLavaDrippingProbability() * 100F;
		if ((waterDripping && fluid == PipeFluid.WATER) ||
			lavaDripping && fluid == PipeFluid.LAVA) {
			// Particle position.
			final float rx = getDripRnd(random);
			final float rz = getDripRnd(random);
			final Vec3 pPos = pos.getBottomCenter().add(rx, yOffset, rz);
			final SimpleParticleType particle =
				fluid == PipeFluid.WATER ?
					ParticleTypes.DRIPPING_WATER :
					ParticleTypes.DRIPPING_LAVA;
			level.addParticle(
				particle,
				pPos.x(), pPos.y(), pPos.z(),
				0.0, 0.0, 0.0);
		}
	}
}
