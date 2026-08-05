package eu.pintergabor.fluidpipesmini.block.util;

import eu.pintergabor.fluidpipesmini.block.CanCarryFluid;
import eu.pintergabor.fluidpipesmini.block.properties.PipeFluid;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;


/**
 * Watering from pipes and fittings.
 */
public final class WateringUtil {

	private WateringUtil() {
		// Static class.
	}

	/**
	 * @return true if the block at {@code pos} is affected by the water carrying pipe or fitting.
	 */
	private static boolean isLeakingWater(
		final @NonNull Level level,
		final @NonNull BlockPos pos
	) {
		final BlockState state = level.getBlockState(pos);
		if (state.getBlock() instanceof CanCarryFluid block &&
			CanCarryFluid.getFluid(state) == PipeFluid.WATER) {
			return level.getRandom().nextFloat() < block.getWateringProbability();
		}
		return false;
	}

	/**
	 * Check if there is a leaking water pipe within range.
	 * <p>
	 * Y range is fixed [0..12].
	 *
	 * @param level Level
	 * @param pos   Target position
	 * @param range X and Z range [-range..+range]
	 * @return true if there is a leaking water pipe or fitting in range.
	 */
	public static boolean isWaterPipeNearby(
		final @NonNull Level level,
		final @NonNull BlockPos pos,
		final int range
	) {
		// Search for a leaking water carrying pipe or fitting in range
		// [-range..+range, 0..12, -range..+range] of the target block.
		for (BlockPos p : BlockPos.betweenClosed(
			pos.offset(-range, 0, -range), pos.offset(range, 12, range))) {
			if (isLeakingWater(level, p)) {
				return true;
			}
		}
		return false;
	}
}
