package eu.pintergabor.fluidpipes.block;

import eu.pintergabor.fluidpipes.tag.ModItemTags;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;


/**
 * Base fitting.
 * <p>
 * All fittings have the same shape, rotating them is meaningless,
 * and there are special rules for connecting them to pipes.
 * <p>
 * Fittings can receive redstone power.
 */
public abstract non-sealed class BaseFitting extends BaseBlock {
	private static final VoxelShape FITTING_SHAPE =
		Block.box(2.5D, 2.5D, 2.5D, 13.5D, 13.5D, 13.5D);
	public static final BooleanProperty POWERED =
		BlockStateProperties.POWERED;

	protected BaseFitting(Properties props, int tickRate) {
		super(props, tickRate);
		registerDefaultState(getStateDefinition().any()
			.setValue(POWERED, false));
	}

	@Override
	protected void createBlockStateDefinition(
		StateDefinition.@NonNull Builder<Block, BlockState> builder
	) {
		super.createBlockStateDefinition(builder);
		builder.add(POWERED);
	}

	/**
	 * Use item on a fitting.
	 * <p>
	 * If it is another piece of pipe or fitting then place it,
	 * otherwise continue with the default action.
	 */
	@Override
	protected @NonNull InteractionResult useItemOn(
		@NonNull ItemStack stack,
		@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos,
		@NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hit
	) {
		if (stack.is(ModItemTags.PIPES_AND_FITTINGS)) {
			// Allow placing fittings next to pipes and fittings.
			return InteractionResult.PASS;
		}
		return InteractionResult.TRY_WITH_EMPTY_HAND;
	}

	@Override
	public @NonNull VoxelShape getShape(
		@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos,
		@NonNull CollisionContext context
	) {
		return FITTING_SHAPE;
	}

	@Override
	public @NonNull VoxelShape getInteractionShape(
		@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos
	) {
		return FITTING_SHAPE;
	}

	/**
	 * Check if the pipe is receiving redstone power from any direction.
	 *
	 * @param level    The world
	 * @param blockPos Position of this pipe
	 * @return true if the pipe is receiving redstone power.
	 */
	public static boolean isReceivingRedstonePower(
		@NonNull Level level, @NonNull BlockPos blockPos
	) {
		for (Direction d : DIRECTIONS) {
			final BlockPos nPos = blockPos.relative(d);
			if (0 < level.getSignal(nPos, d)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determine the initial state of the pipe based on its surroundings.
	 *
	 * @return the initial state of the block
	 */
	@Override
	public @Nullable BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
		final BlockState state = super.getStateForPlacement(context);
		if (state != null) {
			final BlockPos pos = context.getClickedPos();
			final Level level = context.getLevel();
			return state
				.setValue(POWERED, isReceivingRedstonePower(level, pos));
		}
		return null;
	}

	/**
	 * Handle side effects when the neighboring block's state changes.
	 */
	protected void neighborChanged(
		@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos,
		@NonNull Block neighborBlock, @Nullable Orientation orientation,
		boolean movedByPiston
	) {
		final boolean powered = isReceivingRedstonePower(level, pos);
		if (powered != state.getValue(POWERED)) {
			level.setBlockAndUpdate(pos, state.setValue(POWERED, powered));
		}
	}
}
