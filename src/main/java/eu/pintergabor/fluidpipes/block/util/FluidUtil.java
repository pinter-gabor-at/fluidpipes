package eu.pintergabor.fluidpipes.block.util;

import static eu.pintergabor.fluidpipes.block.BasePipe.FACING;

import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.properties.ModProperties;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


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
		final @NonNull ServerLevel level,
		final @NonNull BlockPos pos,
		final @NonNull BlockState state
	) {
		final RandomSource random = level.getRandom();
		// This block.
		final CanCarryFluid block = (CanCarryFluid) state.getBlock();
		final PipeFluid fluid = state.getValueOrElse(ModProperties.FLUID, PipeFluid.NONE);
		if (fluid != PipeFluid.NONE) {
			final boolean clogging =
				random.nextFloat() < block.getCloggingProbability();
			if (clogging) {
				level.setBlockAndUpdate(pos, state
					.setValue(ModProperties.FLUID, PipeFluid.NONE));
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the fluid coming from a pipe in direction {@code d}.
	 *
	 * @param level         The world.
	 * @param pos           Pipe position.
	 * @param dir           Direction to check.
	 * @param canCarryWater Enable carrying water.
	 * @param canCarryLava  Enable carrying lava.
	 * @return The fluid coming from side {@code d}.
	 */
	public static PipeFluid oneSideSourceFluid(
		final @NonNull Level level,
		final @NonNull BlockPos pos,
		final @NonNull Direction dir,
		final boolean canCarryWater,
		final boolean canCarryLava
	) {
		final BlockState nState = level.getBlockState(pos.relative(dir));
		final Block nBlock = nState.getBlock();
		if (nBlock instanceof FluidPipe &&
			nState.getValue(FACING) == dir.getOpposite()) {
			final PipeFluid nFluid = nState.getValue(ModProperties.FLUID);
			if ((canCarryWater && nFluid == PipeFluid.WATER) ||
				(canCarryLava && nFluid == PipeFluid.LAVA)) {
				// Water or lava is coming from the side.
				return nFluid;
			}
		}
		return PipeFluid.NONE;
	}

	/**
	 * Break the pipe or fitting carrying lava with some probability.
	 *
	 * @param level The world.
	 * @param pos   Position of the block.
	 * @param state BlockState of the block.
	 * @return true if state changed.
	 */
	@SuppressWarnings("UnusedReturnValue")
	public static boolean breakFire(
		final @NonNull ServerLevel level,
		final @NonNull BlockPos pos,
		final @NonNull BlockState state
	) {
		final RandomSource random = level.getRandom();
		// This block.
		final PipeFluid fluid = state.getValue(ModProperties.FLUID);
		final boolean waterlogged = state.getValueOrElse(BlockStateProperties.WATERLOGGED, false);
		if (!waterlogged && fluid == PipeFluid.LAVA) {
			final CanCarryFluid block = (CanCarryFluid) state.getBlock();
			final boolean fire =
				random.nextFloat() < block.getFireBreakProbability();
			if (fire) {
				// Replace the pipe or fitting with fire.
				level.setBlockAndUpdate(pos,
					Blocks.FIRE.defaultBlockState());
				return true;
			}
		}
		return false;
	}
}
