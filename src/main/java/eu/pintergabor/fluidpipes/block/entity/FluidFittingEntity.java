package eu.pintergabor.fluidpipes.block.entity;

import static eu.pintergabor.fluidpipes.block.BaseBlock.getTickPos;
import static eu.pintergabor.fluidpipes.block.util.TickUtil.TickPos;

import eu.pintergabor.fluidpipes.block.util.DripActionUtil;
import eu.pintergabor.fluidpipes.block.util.FluidFittingUtil;
import eu.pintergabor.fluidpipes.block.util.FluidUtil;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


public class FluidFittingEntity extends BaseFittingEntity {

	public FluidFittingEntity(
		BlockPos pos, BlockState state) {
		super(ModBlockEntities.FLUID_FITTING_ENTITY, pos, state);
	}

	/**
	 * Called at every tick on the server.
	 */
	public static void serverTick(
		Level level, BlockPos pos, BlockState state, FluidFittingEntity entity) {
		TickPos tickPos = getTickPos(level, state);
		if (tickPos == TickPos.START) {
			// Pull fluid.
			FluidFittingUtil.pull(level, pos, state, entity);
			// Clogging.
			FluidUtil.clog(level, pos, state);
		}
		if (tickPos == TickPos.MIDDLE) {
			boolean powered = state.getValueOrElse(BlockStateProperties.POWERED, false);
			if (!powered) {
				// Drip.
				DripActionUtil.dripDown((ServerLevel) level, pos, state);
				// Break.
				FluidFittingUtil.breakFire((ServerLevel) level, pos, state);
			}
		}
	}
}
