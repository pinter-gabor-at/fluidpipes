package eu.pintergabor.fluidpipesmini.block;

import eu.pintergabor.fluidpipesmini.registry.ModSoundEvents;
import eu.pintergabor.fluidpipesmini.registry.ModStats;
import eu.pintergabor.fluidpipesmini.registry.properties.ModProperties;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


/**
 * Base pipe.
 * <p>
 * All pipes have the same shape, they can be rotated to any direction,
 * and there are special rules for connecting them.
 */
public abstract class BasePipe extends BaseBlock {
	// Properties.
	public static final EnumProperty<@NonNull Direction> FACING =
		BlockStateProperties.FACING;
	public static final BooleanProperty FRONT_CONNECTED =
		ModProperties.FRONT_CONNECTED;
	public static final BooleanProperty BACK_CONNECTED =
		ModProperties.BACK_CONNECTED;
	public static final BooleanProperty SMOOTH =
		ModProperties.SMOOTH;
	// Shapes.
	private static final VoxelShape UP_SHAPE =
		Shapes.or(Block.box(4D, 0D, 4D, 12D, 14D, 12D),
			Block.box(3D, 14D, 3D, 13D, 16D, 13D));
	private static final VoxelShape DOWN_SHAPE =
		Shapes.or(Block.box(4D, 2D, 4D, 12D, 16D, 12D),
			Block.box(3D, 0D, 3D, 13D, 2D, 13D));
	private static final VoxelShape NORTH_SHAPE =
		Shapes.or(Block.box(4D, 4D, 2D, 12D, 12D, 16D),
			Block.box(3D, 3D, 0.D, 13D, 13D, 2D));
	private static final VoxelShape SOUTH_SHAPE =
		Shapes.or(Block.box(4D, 4D, 0D, 12D, 12D, 14D),
			Block.box(3D, 3D, 14.D, 13D, 13D, 16D));
	private static final VoxelShape EAST_SHAPE =
		Shapes.or(Block.box(0D, 4D, 4D, 14D, 12D, 12D),
			Block.box(14D, 3D, 3D, 16D, 13D, 13D));
	private static final VoxelShape WEST_SHAPE =
		Shapes.or(Block.box(2D, 4D, 4D, 16D, 12D, 12D),
			Block.box(0D, 3D, 3D, 2D, 13D, 13D));
	private static final VoxelShape DOWN_FRONT =
		Shapes.or(Block.box(4D, -2D, 4D, 12D, 16D, 12D),
			Block.box(3D, -4D, 3D, 13D, -2D, 13D));
	private static final VoxelShape EAST_FRONT =
		Shapes.or(Block.box(0D, 4D, 4D, 18D, 12D, 12D),
			Block.box(18D, 3D, 3D, 20D, 13D, 13D));
	private static final VoxelShape NORTH_FRONT =
		Shapes.or(Block.box(4D, 4D, -2D, 12D, 12D, 16D),
			Block.box(3D, 3D, -4D, 13D, 13D, -2D));
	private static final VoxelShape SOUTH_FRONT =
		Shapes.or(Block.box(4D, 4D, 0D, 12D, 12D, 18D),
			Block.box(3D, 3D, 18.D, 13D, 13D, 20D));
	private static final VoxelShape WEST_FRONT =
		Shapes.or(Block.box(-2D, 4D, 4D, 16D, 12D, 12D),
			Block.box(-4D, 3D, 3D, -2D, 13D, 13D));
	private static final VoxelShape UP_FRONT =
		Shapes.or(Block.box(4D, 0D, 4D, 12D, 18D, 12D),
			Block.box(3D, 18D, 3D, 13D, 20D, 13D));
	private static final VoxelShape DOWN_BACK =
		Shapes.or(Block.box(4D, 2D, 4D, 12D, 20D, 12D),
			Block.box(3D, 0D, 3D, 13D, 2D, 13D));
	private static final VoxelShape EAST_BACK =
		Shapes.or(Block.box(-4D, 4D, 4D, 14D, 12D, 12D),
			Block.box(14D, 3D, 3D, 16D, 13D, 13D));
	private static final VoxelShape NORTH_BACK =
		Shapes.or(Block.box(4D, 4D, 2D, 12D, 12D, 20D),
			Block.box(3D, 3D, 0.D, 13D, 13D, 2D));
	private static final VoxelShape SOUTH_BACK =
		Shapes.or(Block.box(4D, 4D, -4D, 12D, 12D, 14D),
			Block.box(3D, 3D, 14.D, 13D, 13D, 16D));
	private static final VoxelShape WEST_BACK =
		Shapes.or(Block.box(2D, 4D, 4D, 20D, 12D, 12D),
			Block.box(0D, 3D, 3D, 2D, 13D, 13D));
	private static final VoxelShape UP_BACK =
		Shapes.or(Block.box(4D, -4D, 4D, 12D, 14D, 12D),
			Block.box(3D, 14D, 3D, 13D, 16D, 13D));
	private static final VoxelShape DOWN_DOUBLE =
		Block.box(4D, -4D, 4D, 12D, 20D, 12D);
	private static final VoxelShape NORTH_DOUBLE =
		Block.box(4D, 4D, -4D, 12D, 12D, 20D);
	private static final VoxelShape EAST_DOUBLE =
		Block.box(-4D, 4D, 4D, 20D, 12D, 12D);
	private static final VoxelShape DOWN_SMOOTH =
		Block.box(4D, 0D, 4D, 12D, 16D, 12D);
	private static final VoxelShape NORTH_SMOOTH =
		Block.box(4D, 4D, 0D, 12D, 12D, 16D);
	private static final VoxelShape EAST_SMOOTH =
		Block.box(0D, 4D, 4D, 16D, 12D, 12D);
	private static final VoxelShape DOWN_BACK_SMOOTH =
		Block.box(4D, 0D, 4D, 12D, 20D, 12D);
	private static final VoxelShape NORTH_BACK_SMOOTH =
		Block.box(4D, 4D, 0D, 12D, 12D, 20D);
	private static final VoxelShape SOUTH_BACK_SMOOTH =
		Block.box(4D, 4D, -4D, 12D, 12D, 16D);
	private static final VoxelShape EAST_BACK_SMOOTH =
		Block.box(-4D, 4D, 4D, 16D, 12D, 12D);
	private static final VoxelShape WEST_BACK_SMOOTH =
		Block.box(0D, 4D, 4D, 20D, 12D, 12D);
	private static final VoxelShape UP_BACK_SMOOTH =
		Block.box(4D, -4D, 4D, 12D, 16D, 12D);

	protected BasePipe(Properties props, int tickRate) {
		super(props, tickRate);
		registerDefaultState(defaultBlockState()
			.setValue(FACING, Direction.DOWN)
			.setValue(SMOOTH, false));
	}

	@Override
	protected void createBlockStateDefinition(
		StateDefinition.@NonNull Builder<Block, @NonNull BlockState> builder
	) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, FRONT_CONNECTED, BACK_CONNECTED, SMOOTH);
	}

	/**
	 * Get pipe shape for drawing the outline and detecting collisions.
	 *
	 * @param state The state of the pipe
	 * @return the shape of the outline.
	 */
	public VoxelShape getPipeShape(final @NonNull BlockState state) {
		final boolean front = state.getValue(FRONT_CONNECTED);
		final boolean back = state.getValue(BACK_CONNECTED);
		final boolean smooth = state.getValue(SMOOTH);
		if (smooth && back) {
			return switch (state.getValue(FACING)) {
				case DOWN -> DOWN_BACK_SMOOTH;
				case UP -> UP_BACK_SMOOTH;
				case NORTH -> NORTH_BACK_SMOOTH;
				case SOUTH -> SOUTH_BACK_SMOOTH;
				case EAST -> EAST_BACK_SMOOTH;
				case WEST -> WEST_BACK_SMOOTH;
			};
		}
		if (smooth) {
			return switch (state.getValue(FACING)) {
				case DOWN, UP -> DOWN_SMOOTH;
				case NORTH, SOUTH -> NORTH_SMOOTH;
				case EAST, WEST -> EAST_SMOOTH;
			};
		}
		if (front && back) {
			return switch (state.getValue(FACING)) {
				case DOWN, UP -> DOWN_DOUBLE;
				case NORTH, SOUTH -> NORTH_DOUBLE;
				case EAST, WEST -> EAST_DOUBLE;
			};
		}
		if (front) {
			return switch (state.getValue(FACING)) {
				case DOWN -> DOWN_FRONT;
				case UP -> UP_FRONT;
				case NORTH -> NORTH_FRONT;
				case SOUTH -> SOUTH_FRONT;
				case EAST -> EAST_FRONT;
				case WEST -> WEST_FRONT;
			};
		}
		if (back) {
			return switch (state.getValue(FACING)) {
				case DOWN -> DOWN_BACK;
				case UP -> UP_BACK;
				case NORTH -> NORTH_BACK;
				case SOUTH -> SOUTH_BACK;
				case EAST -> EAST_BACK;
				case WEST -> WEST_BACK;
			};
		}
		return switch (state.getValue(FACING)) {
			case DOWN -> DOWN_SHAPE;
			case UP -> UP_SHAPE;
			case NORTH -> NORTH_SHAPE;
			case SOUTH -> SOUTH_SHAPE;
			case EAST -> EAST_SHAPE;
			case WEST -> WEST_SHAPE;
		};
	}

	@Override
	public @NonNull VoxelShape getShape(
		final @NonNull BlockState state,
		final @NonNull BlockGetter level,
		final @NonNull BlockPos pos,
		final @NonNull CollisionContext context
	) {
		return getPipeShape(state);
	}

	@Override
	public @NonNull VoxelShape getInteractionShape(
		final @NonNull BlockState state,
		final @NonNull BlockGetter level,
		final @NonNull BlockPos pos
	) {
		return getPipeShape(state);
	}

	/**
	 * Check if a pipe can connect to another without an extension.
	 * <p>
	 * The pipe can connect to another pipe without an extension
	 * if both pipes are facing the same or opposite direction.
	 * The pipe can never connect to a fitting without an extension.
	 *
	 * @param otherBlockState State of the other block
	 * @param direction       Direction of this pipe
	 * @return true if an extension is needed.
	 */
	private static boolean needExtension(
		final @NonNull BlockState otherBlockState,
		final @NonNull Direction direction
	) {
		// Get the block in front of the pipe.
		final Block otherBlock = otherBlockState.getBlock();
		if (otherBlock instanceof BasePipe) {
			final Direction facing = otherBlockState.getValue(BasePipe.FACING);
			// The pipe can connect to another pipe without an extension
			// if both pipes are facing the same or opposite direction.
			return facing != direction.getOpposite() && facing != direction;
		}
		// The pipe can never connect to a fitting without an extension,
		// but the pipe can connect to any other block without an extension.
		return otherBlock instanceof BaseFitting;
	}

	/**
	 * Check if the front of the pipe can connect to another without an extension.
	 *
	 * @param level    The world
	 * @param blockPos Position of this pipe
	 * @param facing   Direction of this pipe
	 * @return true if an extension is needed.
	 */
	public static boolean needFrontExtension(
		final @NonNull LevelReader level,
		final @NonNull BlockPos blockPos,
		final @NonNull Direction facing
	) {
		// Get the state of the block in front of the pipe.
		final BlockState state = level.getBlockState(blockPos.relative(facing));
		// Check if an extension is needed to connect to it.
		return needExtension(state, facing);
	}

	/**
	 * Check if the back of the pipe can connect to another without an extension.
	 *
	 * @param level  The world
	 * @param pos    Position of this pipe
	 * @param facing Direction of this pipe
	 * @return true if an extension is needed.
	 */
	public static boolean needBackExtension(
		final @NonNull LevelReader level,
		final @NonNull BlockPos pos,
		final @NonNull Direction facing
	) {
		// Get the state of the block at the back of the pipe.
		final Direction opposite = facing.getOpposite();
		final BlockPos backPos = pos.relative(opposite);
		final BlockState backState = level.getBlockState(backPos);
		// Check if an extension is needed to connect to it.
		return needExtension(backState, facing);
	}

	/**
	 * Check if the front of the pipe shall be rendered smooth.
	 * <p>
	 * The pipe face is smooth, if it is facing this direction, and the front is not connected to anything.
	 */
	public static boolean isSmooth(
		final @NonNull LevelReader level,
		final @NonNull BlockPos pos,
		final @NonNull Direction facing
	) {
		// Get the state of the block in front of the pipe.
		final BlockPos frontPos = pos.relative(facing);
		final BlockState frontState = level.getBlockState(frontPos);
		final Block frontBlock = frontState.getBlock();
		if (frontBlock instanceof BasePipe) {
			final Direction frontFacing = frontState.getValue(BasePipe.FACING);
			return frontFacing == facing && !needExtension(frontState, facing);
		}
		return false;
	}


	/**
	 * Determine the initial state of the pipe based on its surroundings.
	 *
	 * @return the initial state of the block
	 */
	@Override
	public @Nullable BlockState getStateForPlacement(final @NonNull BlockPlaceContext context) {
		final BlockState state = super.getStateForPlacement(context);
		if (state != null) {
			final Level level = context.getLevel();
			final Direction facing = context.getClickedFace();
			final BlockPos pos = context.getClickedPos();
			return state
				.setValue(FACING, facing)
				.setValue(FRONT_CONNECTED, needFrontExtension(level, pos, facing))
				.setValue(BACK_CONNECTED, needBackExtension(level, pos, facing))
				.setValue(SMOOTH, isSmooth(level, pos, facing));
		}
		return null;
	}

	/**
	 * Handle state changes when the neighboring block's state changes.
	 *
	 * @return the state of the pipe after a neighboring block's state changes.
	 */
	@Override
	protected @NonNull BlockState updateShape(
		@NonNull BlockState state,
		@NonNull LevelReader level,
		@NonNull ScheduledTickAccess tickView,
		@NonNull BlockPos pos,
		@NonNull Direction direction,
		@NonNull BlockPos neighborPos,
		@NonNull BlockState neighborState,
		@NonNull RandomSource random
	) {
		final BlockState superState = super.updateShape(
			state, level, tickView, pos, direction, neighborPos, neighborState, random);
		final Direction facing = superState.getValue(FACING);
		return superState
			.setValue(FRONT_CONNECTED, needFrontExtension(level, pos, facing))
			.setValue(BACK_CONNECTED, needBackExtension(level, pos, facing))
			.setValue(SMOOTH, isSmooth(level, pos, facing));
	}

	/**
	 * @return {@code state} rotated by {@code rotation}.
	 */
	@Override
	public @NonNull BlockState rotate(
		@NonNull BlockState state,
		final @NonNull Rotation rotation
	) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	/**
	 * @return {@code state} mirrored by {@code mirror}.
	 */
	@Override
	public @NonNull BlockState mirror(
		@NonNull BlockState state,
		final @NonNull Mirror mirror
	) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	/**
	 * Hook to allow special handling in derived classes before turning.
	 */
	protected BlockState beforeTurning(
		final @NonNull Level level,
		final @NonNull BlockPos pos,
		final @NonNull BlockState state
	) {
		return state;
	}

	/**
	 * Turn a pipe with a hoe.
	 */
	protected void turnWithTool(
		final @NonNull Level level,
		final @NonNull BlockPos pos,
		@NonNull BlockState state,
		final @NonNull Player player,
		final @NonNull InteractionHand hand,
		final @NonNull BlockHitResult hit,
		final @NonNull ItemStack stack
	) {
		if (player instanceof ServerPlayer serverPlayer) {
			// Increase the statistics on the server.
			CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
			serverPlayer.awardStat(ModStats.INTERACTIONS);
		}
		final Direction playerFacing = hit.getDirection();
		final Direction blockFacing = state.getValue(BlockStateProperties.FACING);
		if (playerFacing != blockFacing) {
			// Turn the pipe, if it is facing in any other direction.
			level.setBlockAndUpdate(pos, beforeTurning(level, pos, state)
				.setValue(FACING, playerFacing)
				.setValue(BACK_CONNECTED, needBackExtension(level, pos, playerFacing))
				.setValue(FRONT_CONNECTED, needFrontExtension(level, pos, playerFacing))
				.setValue(SMOOTH, isSmooth(level, pos, playerFacing)));
			ModSoundEvents.playTurnSound(level, pos);
			// Damage the tool.
			stack.hurtAndBreak(1,
				player, hand.asEquipmentSlot());
		}
	}

}
