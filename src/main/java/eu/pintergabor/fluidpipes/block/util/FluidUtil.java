package eu.pintergabor.fluidpipes.block.util;

import static eu.pintergabor.fluidpipes.block.BasePipe.FACING;

import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;


/**
 * Utilities common to fluid pipes and fittings.
 */
public final class FluidUtil {

	private FluidUtil() {
		// Static class.
	}

	/**
	 * Clog the pipe or fitting with some probability.
	 *
	 * @return true if the state is changed.
	 */
	@SuppressWarnings({"UnusedReturnValue", "unused"})
	public static boolean clog(
		Level world, BlockPos pos, BlockState state) {
		CanCarryFluid block = (CanCarryFluid) state.getBlock();
		PipeFluid fluid = state.getValueOrElse(ModProperties.FLUID, PipeFluid.NONE);
		if (fluid != PipeFluid.NONE) {
			boolean clogging =
				world.random.nextFloat() < block.getCloggingProbability();
			if (clogging) {
				world.setBlockAndUpdate(pos, state
					.setValue(ModProperties.FLUID, PipeFluid.NONE));
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
	public static PipeFluid oneSideSourceFluid(
		Level world, BlockPos pos, Direction d,
		boolean canCarryWater, boolean canCarryLava) {
		BlockState nState = world.getBlockState(pos.relative(d));
		Block nBlock = nState.getBlock();
		if (nBlock instanceof FluidPipe &&
			nState.getValue(FACING) == d.getOpposite()) {
			PipeFluid nFluid = nState.getValue(ModProperties.FLUID);
			if ((canCarryWater && nFluid == PipeFluid.WATER) ||
				(canCarryLava && nFluid == PipeFluid.LAVA)) {
				// Water or lava is coming from the side.
				return nFluid;
			}
		}
		return PipeFluid.NONE;
	}
}
