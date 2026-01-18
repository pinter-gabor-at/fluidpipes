package eu.pintergabor.fluidpipesmini.block.entity;

import static eu.pintergabor.fluidpipesmini.block.BaseBlock.getTickPos;
import static eu.pintergabor.fluidpipesmini.block.util.TickUtil.TickPos;

import eu.pintergabor.fluidpipesmini.block.util.DripActionUtil;
import eu.pintergabor.fluidpipesmini.block.util.FluidDispenseUtil;
import eu.pintergabor.fluidpipesmini.block.util.FluidPullUtil;
import eu.pintergabor.fluidpipesmini.block.util.FluidPushUtil;
import eu.pintergabor.fluidpipesmini.registry.ModBlockEntities;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;


/**
 * Fluid push/pull mechanism for fluid pipes.
 */
public class FluidPipeEntity extends BasePipeEntity {

	public FluidPipeEntity(
		@NonNull BlockPos pos, @NonNull BlockState state
	) {
		super(ModBlockEntities.FLUID_PIPE_ENTITY.get(), pos, state);
	}

	/**
	 * Called at every tick on the server.
	 */
	public static void serverTick(
		@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state,
		@NonNull FluidPipeEntity entity
	) {
		final TickPos tickPos = getTickPos(level, state);
		final ServerLevel serverLevel = (ServerLevel) level;
		if (tickPos == TickPos.START) {
			// Pull fluid.
			FluidPullUtil.pull(serverLevel, pos, state, entity);
		}
		if (tickPos == TickPos.MIDDLE) {
			// Push fluid into blocks that are not capable of pulling it.
			FluidPushUtil.push(serverLevel, pos, state);
			// Dispense fluid.
			FluidDispenseUtil.dispense(level, pos, state);
			// Drip.
			DripActionUtil.dripDown(serverLevel, pos, state);
		}
	}
}
