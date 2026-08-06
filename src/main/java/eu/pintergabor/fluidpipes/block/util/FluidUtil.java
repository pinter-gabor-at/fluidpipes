package eu.pintergabor.fluidpipes.block.util;

import static eu.pintergabor.fluidpipes.block.BasePipe.FACING;

import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.properties.ModProperties;
import org.jspecify.annotations.NonNull;

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
}
