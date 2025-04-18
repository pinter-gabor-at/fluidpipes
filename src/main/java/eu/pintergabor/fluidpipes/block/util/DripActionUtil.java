package eu.pintergabor.fluidpipes.block.util;

import eu.pintergabor.fluidpipes.block.CanCarryFluid;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.Shapes;


/**
 * More utilities common to fluid pipes and fittings.
 * <p>
 * Drips.
 */
public final class DripActionUtil {

	private DripActionUtil() {
		// Static class.
	}

	/**
	 * Drip water on, or push water into, a cauldron.
	 *
	 * @param level The world.
	 * @param pos   Position of the cauldron.
	 * @param state BlockState of the cauldron.
	 * @return true if state changed.
	 */
	@SuppressWarnings("unused")
	private static boolean dripWaterOnCauldron(
		ServerLevel level, BlockPos pos, BlockState state
	) {
		// Start filling an empty cauldron with water.
		level.setBlockAndUpdate(pos,
			Blocks.WATER_CAULDRON.defaultBlockState()
				.setValue(BlockStateProperties.LEVEL_CAULDRON, 1));
		return true;
	}

	/**
	 * Drip lava on, or push lava into, a cauldron.
	 *
	 * @param level The world.
	 * @param pos   Position of the cauldron.
	 * @param state BlockState of the cauldron.
	 * @return true if state changed.
	 */
	@SuppressWarnings("unused")
	private static boolean dripLavaOnCauldron(
		ServerLevel level, BlockPos pos, BlockState state
	) {
		// Fill an empty cauldron with lava.
		level.setBlockAndUpdate(pos,
			Blocks.LAVA_CAULDRON.defaultBlockState());
		return true;
	}

	/**
	 * Drip water on, or push water into,
	 * a partially filled water cauldron.
	 *
	 * @param level The world.
	 * @param pos   Position of the cauldron.
	 * @param state BlockState of the cauldron.
	 * @return true if state changed.
	 */
	@SuppressWarnings("unused")
	private static boolean dripWaterOnWaterCauldron(
		ServerLevel level, BlockPos pos, BlockState state
	) {
		// Continue filling a water cauldron.
		level.setBlockAndUpdate(pos,
			state.cycle(BlockStateProperties.LEVEL_CAULDRON));
		return true;
	}

	/**
	 * Drip water on, or push water into, a dirt block.
	 *
	 * @param level The world.
	 * @param pos   Position of the block.
	 * @param state BlockState of the block.
	 * @return true if state changed.
	 */
	@SuppressWarnings("unused")
	private static boolean dripWaterOnDirt(
		ServerLevel level, BlockPos pos, BlockState state
	) {
		// Water dripping on dirt changes it to mud.
		level.setBlockAndUpdate(pos,
			Blocks.MUD.defaultBlockState());
		return true;
	}

	/**
	 * Drip water on, or push water into, a fire block.
	 *
	 * @param world The world.
	 * @param pos   Position of the block.
	 * @param state BlockState of the block.
	 * @return true if state changed.
	 */
	@SuppressWarnings("unused")
	private static boolean dripWaterOnFire(
		ServerLevel world, BlockPos pos, BlockState state) {
		// Water dripping on fire extinguishes the fire.
		world.destroyBlock(pos, true);
		return true;
	}

	/**
	 * Drip water on, or push water into, a block.
	 *
	 * @param level The world.
	 * @param pos   Position of the block.
	 * @param state BlockState of the block.
	 * @return true if state changed.
	 */
	public static boolean dripWaterOnBlock(
		ServerLevel level, BlockPos pos, BlockState state
	) {
		Block block = state.getBlock();
		if (block == Blocks.CAULDRON) {
			// Cauldron.
			return dripWaterOnCauldron(level, pos, state);
		} else if (block == Blocks.WATER_CAULDRON) {
			// Partially filled water cauldron.
			return state.getValue(BlockStateProperties.LEVEL_CAULDRON) < 3 &&
				dripWaterOnWaterCauldron(level, pos, state);
		} else if (block == Blocks.DIRT) {
			// Dirt.
			return dripWaterOnDirt(level, pos, state);
		} else if (block == Blocks.FIRE) {
			// Fire.
			return dripWaterOnFire(level, pos, state);
		}
		return false;
	}

	/**
	 * Drip lava on, or push lava into, a block.
	 *
	 * @param level The world.
	 * @param pos   Position of the block.
	 * @param state BlockState of the block.
	 * @return true if state changed.
	 */
	public static boolean dripLavaOnBlock(
		ServerLevel level, BlockPos pos, BlockState state
	) {
		Block block = state.getBlock();
		if (block == Blocks.CAULDRON) {
			// Cauldron.
			return dripLavaOnCauldron(level, pos, state);
		}
		return false;
	}

	/**
	 * Start a fire above the block, because lava is dripping on it.
	 *
	 * @param level The world.
	 * @param pos   Position of the block.
	 * @param state BlockState of the block.
	 * @return true if state changed.
	 */
	@SuppressWarnings("unused")
	public static boolean dripLavaStartFire(
		ServerLevel level, BlockPos pos, BlockState state
	) {
		BlockPos uPos = pos.above();
		BlockState uState = level.getBlockState(uPos);
		if (uState.isAir()) {
			level.setBlockAndUpdate(uPos,
				Blocks.FIRE.defaultBlockState());
			return true;
		}
		return false;
	}

	/**
	 * Dripping water action for pipes and fittings.
	 *
	 * @param level The world.
	 * @param pos   Position of the pipe or the fitting.
	 * @param state BlockState of the pipe or the fitting.
	 * @return true if anything changed.
	 */
	@SuppressWarnings("unusedReturnValue")
	public static boolean dripWaterDown(
		ServerLevel level, BlockPos pos, BlockState state
	) {
		CanCarryFluid block = (CanCarryFluid) state.getBlock();
		boolean waterDripping =
			level.random.nextFloat() < block.getWaterDrippingProbability();
		if (waterDripping) {
			// Search down.
			for (int dy = 1; dy <= 12; dy++) {
				BlockPos nPos = pos.below(dy);
				BlockState nState = level.getBlockState(nPos);
				if (!level.getFluidState(nPos).isEmpty()) {
					// A block containing any liquid stops the drip.
					return false;
				}
				if (dripWaterOnBlock(level, nPos, nState)) {
					// A block that reacts with the drip stops the drip.
					return true;
				}
				if (nState.getCollisionShape(level, nPos) != Shapes.empty()) {
					// A solid block stops the drip.
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * Dripping lava action for pipes and fittings.
	 *
	 * @param level The world.
	 * @param pos   Position of the pipe or the fitting.
	 * @param state BlockState of the pipe or the fitting.
	 * @return true if anything changed.
	 */
	@SuppressWarnings("unusedReturnValue")
	public static boolean dripLavaDown(
		ServerLevel level, BlockPos pos, BlockState state
	) {
		CanCarryFluid block = (CanCarryFluid) state.getBlock();
		boolean lavaDripping =
			level.random.nextFloat() < block.getLavaDrippingProbability();
		// Search down.
		for (int dy = 1; dy <= 12; dy++) {
			BlockPos nPos = pos.below(dy);
			BlockState nState = level.getBlockState(nPos);
			if (!level.getFluidState(nPos).isEmpty()) {
				// A block containing any liquid stops the drip.
				return false;
			}
			if (lavaDripping) {
				if (dripLavaOnBlock(level, nPos, nState)) {
					// A block that reacts with the drip stops the drip.
					return true;
				}
			}
			if (nState.getCollisionShape(level, nPos) != Shapes.empty()) {
				// A solid block stops the drip, but may start a fire.
				boolean startFire =
					level.random.nextFloat() < block.getFireDripProbability();
				return startFire && dripLavaStartFire(level, nPos, nState);
			}
		}
		return false;
	}

	/**
	 * Dripping action for pipes and fittings.
	 *
	 * @param world The world.
	 * @param pos   Position of the pipe or the fitting.
	 * @param state BlockState of the pipe or the fitting.
	 * @return true if anything changed.
	 */
	@SuppressWarnings("unusedReturnValue")
	public static boolean dripDown(
		ServerLevel world, BlockPos pos, BlockState state) {
		PipeFluid fluid = state.getValue(ModProperties.FLUID);
		return switch (fluid) {
			case WATER -> dripWaterDown(world, pos, state);
			case LAVA -> dripLavaDown(world, pos, state);
			case NONE -> false;
		};
	}
}
