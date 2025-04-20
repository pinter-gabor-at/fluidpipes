package eu.pintergabor.fluidpipes.block.entity;

import static eu.pintergabor.fluidpipes.block.BaseBlock.getTickPos;
import static eu.pintergabor.fluidpipes.block.util.TickUtil.TickPos;

import eu.pintergabor.fluidpipes.block.util.DripActionUtil;
import eu.pintergabor.fluidpipes.block.util.FluidDispenseUtil;
import eu.pintergabor.fluidpipes.block.util.FluidPullUtil;
import eu.pintergabor.fluidpipes.block.util.FluidPushUtil;
import eu.pintergabor.fluidpipes.block.util.FluidUtil;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;


public class FluidPipeEntity extends BasePipeEntity {

	public FluidPipeEntity(
		BlockPos pos, BlockState state
	) {
		super(ModBlockEntities.FLUID_PIPE_ENTITY.get(), pos, state);
	}

	/**
	 * Called at every tick on the server.
	 */
	public static void serverTick(
		Level level, BlockPos pos, BlockState state, FluidPipeEntity entity) {
		TickPos tickPos = getTickPos(level, state);
		if (tickPos == TickPos.START) {
			// Pull fluid.
			FluidPullUtil.pull(level, pos, state, entity);
			// Clogging.
			FluidUtil.clog(level, pos, state);
		}
		if (tickPos == TickPos.MIDDLE) {
			// Push fluid into blocks that are not capable of pulling it.
			FluidPushUtil.push((ServerLevel) level, pos, state);
			// Dispense fluid.
			FluidDispenseUtil.dispense(level, pos, state);
			// Drip.
			DripActionUtil.dripDown((ServerLevel) level, pos, state);
			// Break.
			FluidDispenseUtil.breakFire((ServerLevel) level, pos, state);
		}
	}
}
