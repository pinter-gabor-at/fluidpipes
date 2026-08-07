package eu.pintergabor.fluidpipes.block.util;

import static eu.pintergabor.fluidpipes.block.BaseBlock.DIRECTIONS;
import static eu.pintergabor.fluidpipes.block.util.FluidUtil.oneSideSourceFluid;
import static eu.pintergabor.fluidpipes.registry.properties.ModProperties.FLUID;

import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.entity.FluidFittingEntity;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.properties.ModProperties;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


/**
 * Utilities for fluid fittings.
 */
public final class FluidFittingUtil {

	private FluidFittingUtil() {
		// Static class.
	}

	/**
	 * Get the fluid coming from pipes pointing towards this fitting.
	 *
	 * @param level         The world.
	 * @param pos           Pipe position.
	 * @param canCarryWater Enable carrying water.
	 * @param canCarryLava  Enable carrying lava.
	 * @return The fluid coming from a side.
	 */
	public static PipeFluid sideSourceFluid(
		final @NonNull Level level,
		final @NonNull BlockPos pos,
		final boolean canCarryWater,
		final boolean canCarryLava
	) {
		for (Direction d : DIRECTIONS) {
			// Check all directions.
			final PipeFluid nFluid = oneSideSourceFluid(
				level, pos, d, canCarryWater, canCarryLava);
			if (nFluid != PipeFluid.NONE) {
				return nFluid;
			}
		}
		return PipeFluid.NONE;
	}

	/**
	 * Pull fluid from any pipe pointing to this fitting.
	 *
	 * @return true if the state is changed.
	 */
	@SuppressWarnings({"UnusedReturnValue", "unused"})
	public static boolean pull(
		final @NonNull Level level,
		final @NonNull BlockPos pos,
		final @NonNull BlockState state,
		final @NonNull FluidFittingEntity entity
	) {
		// This block.
		final PipeFluid pipeFluid = state.getValue(FLUID);
		final FluidFitting block = (FluidFitting) state.getBlock();
		final boolean canCarryWater = block.canCarryWater();
		final boolean canCarryLava = block.canCarryLava();
		// Find a pipe pointing to this pipe from any side.
		PipeFluid sideFluid = sideSourceFluid(
			level, pos,
			canCarryWater, canCarryLava);
		if (sideFluid != PipeFluid.NONE) {
			// Water or lava is coming from the side.
			if (pipeFluid != sideFluid) {
				level.setBlockAndUpdate(pos, state.setValue(FLUID, sideFluid));
				return true;
			}
		} else if (pipeFluid != PipeFluid.NONE) {
			// No source from any side.
			level.setBlockAndUpdate(pos, state.setValue(FLUID, PipeFluid.NONE));
			return true;
		}
		return false;
	}
}
