package eu.pintergabor.fluidpipesmini.block.entity;

import static eu.pintergabor.fluidpipesmini.block.BaseBlock.getTickPos;
import static eu.pintergabor.fluidpipesmini.block.util.TickUtil.TickPos;

import eu.pintergabor.fluidpipesmini.block.util.DripActionUtil;
import eu.pintergabor.fluidpipesmini.block.util.FluidFittingUtil;
import eu.pintergabor.fluidpipesmini.registry.ModBlockEntities;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


/**
 * Fluid push/pull mechanism for fluid fittings
 */
public class FluidFittingEntity extends BaseFittingEntity {

	public FluidFittingEntity(
		@NonNull BlockPos pos, @NonNull BlockState state
	) {
		super(ModBlockEntities.FLUID_FITTING_ENTITY, pos, state);
	}

	/**
	 * Called at every tick on the server.
	 */
	public static void serverTick(
		@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state,
		@NonNull FluidFittingEntity entity
	) {
		final TickPos tickPos = getTickPos(level, state);
		final ServerLevel serverLevel = (ServerLevel) level;
		if (tickPos == TickPos.START) {
			// Pull fluid.
			FluidFittingUtil.pull(serverLevel, pos, state, entity);
		} else if (tickPos == TickPos.MIDDLE) {
			final boolean powered = state.getValueOrElse(BlockStateProperties.POWERED, false);
			if (!powered) {
				// Drip.
				DripActionUtil.dripDown(serverLevel, pos, state);
			}
		}
	}
}
