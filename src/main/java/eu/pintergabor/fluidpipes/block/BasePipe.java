package eu.pintergabor.fluidpipes.block;

import eu.pintergabor.fluidpipes.registry.ModProperties;
import eu.pintergabor.fluidpipes.registry.ModSoundEvents;
import eu.pintergabor.fluidpipes.registry.ModStats;
import eu.pintergabor.fluidpipes.tag.ModItemTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
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
public abstract non-sealed class BasePipe extends BaseBlock {
	// Properties.
	public static final EnumProperty<Direction> FACING =
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
		StateDefinition.Builder<Block, BlockState> builder
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
	public VoxelShape getPipeShape(BlockState state) {
		boolean front = state.getValue(FRONT_CONNECTED);
		boolean back = state.getValue(BACK_CONNECTED);
		boolean smooth = state.getValue(SMOOTH);
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
	public @NotNull VoxelShape getShape(
		BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
	) {
		return getPipeShape(state);
	}

	@Override
	public @NotNull VoxelShape getInteractionShape(
		BlockState state, BlockGetter level, BlockPos pos
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
		@NotNull BlockState otherBlockState, @NotNull Direction direction) {
		// Get the block in front of the pipe.
		Block otherBlock = otherBlockState.getBlock();
		if (otherBlock instanceof BasePipe) {
			Direction facing = otherBlockState.getValue(BasePipe.FACING);
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
	 * @param level     The world
	 * @param blockPos  Position of this pipe
	 * @param direction Direction of this pipe
	 * @return true if an extension is needed.
	 */
	public static boolean needFrontExtension(
		@NotNull LevelReader level,
		@NotNull BlockPos blockPos, Direction direction) {
		// Get the state of the block in front of the pipe.
		BlockState state = level.getBlockState(blockPos.relative(direction));
		// Check if an extension is needed to connect to it.
		return needExtension(state, direction);
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
		@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull Direction facing
	) {
		// Get the state of the block at the back of the pipe.
		Direction opposite = facing.getOpposite();
		BlockPos backPos = pos.relative(opposite);
		BlockState backState = level.getBlockState(backPos);
		// Check if an extension is needed to connect to it.
		return needExtension(backState, facing);
	}

	/**
	 * Check if the front of the pipe shall be rendered smooth.
	 * <p>
	 * The pipe face is smooth, if it is facing this direction, and the front is not connected to anything.
	 */
	public static boolean isSmooth(
		@NotNull LevelReader level, @NotNull BlockPos pos, Direction facing
	) {
		// Get the state of the block in front of the pipe.
		BlockPos frontPos = pos.relative(facing);
		BlockState frontState = level.getBlockState(frontPos);
		Block frontBlock = frontState.getBlock();
		if (frontBlock instanceof BasePipe) {
			Direction frontFacing = frontState.getValue(BasePipe.FACING);
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
	public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		if (state != null) {
			Level level = context.getLevel();
			Direction facing = context.getClickedFace();
			BlockPos pos = context.getClickedPos();
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
	protected @NotNull BlockState updateShape(
		@NotNull BlockState state,
		LevelReader level,
		ScheduledTickAccess tickView,
		BlockPos pos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource random
	) {
		state = super.updateShape(
			state, level, tickView, pos, direction, neighborPos, neighborState, random);
		Direction facing = state.getValue(FACING);
		return state
			.setValue(FRONT_CONNECTED, needFrontExtension(level, pos, facing))
			.setValue(BACK_CONNECTED, needBackExtension(level, pos, facing))
			.setValue(SMOOTH, isSmooth(level, pos, facing));
	}

	/**
	 * @return {@code state} rotated by {@code rotation}
	 */
	@Override
	@NotNull
	public BlockState rotate(@NotNull BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	/**
	 * @return {@code state} mirrored by {@code mirror}
	 */
	@Override
	@NotNull
	public BlockState mirror(@NotNull BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	protected BlockState beforeTurning(
		Level level, BlockPos pos, BlockState state
	) {
		return state;
	}

	private void turnWithTool(
		Level world, BlockPos pos, BlockState state,
		Player player, InteractionHand hand, BlockHitResult hit,
		@NotNull ItemStack stack
	) {
		if (player instanceof ServerPlayer serverPlayer) {
			// Increase the statistics on the server.
			CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
			serverPlayer.awardStat(ModStats.INTERACTIONS);
		}
		Direction playerFacing = hit.getDirection();
		Direction blockFacing = state.getValue(BlockStateProperties.FACING);
		if (playerFacing != blockFacing) {
			// Turn the pipe, if it is facing in any other direction.
			world.setBlockAndUpdate(pos, beforeTurning(world, pos, state)
				.setValue(FACING, playerFacing)
				.setValue(BACK_CONNECTED, needBackExtension(world, pos, playerFacing))
				.setValue(FRONT_CONNECTED, needFrontExtension(world, pos, playerFacing))
				.setValue(SMOOTH, isSmooth(world, pos, playerFacing)));
			ModSoundEvents.playTurnSound(world, pos);
			if (player != null) {
				// Damage the hoe.
				stack.hurtAndBreak(1,
					player, LivingEntity.getSlotForHand(hand));
			}
		}
	}

	/**
	 * Use item on a pipe.
	 * <p>
	 * If it is another piece of pipe or fitting then place it,
	 * if it is a hoe, turn it, otherwise continue with the default action.
	 */
	@Override
	protected @NotNull InteractionResult useItemOn(
		@NotNull ItemStack stack,
		BlockState state, Level world, BlockPos pos,
		Player player, InteractionHand hand, BlockHitResult hit
	) {
		if (stack.is(ModItemTags.PIPES_AND_FITTINGS)) {
			// Allow placing pipes next to pipes and fittings.
			return InteractionResult.PASS;
		}
		if (stack.is(ItemTags.HOES)) {
			// Turn pipes with a hoe.
			turnWithTool(world, pos, state, player, hand, hit, stack);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.TRY_WITH_EMPTY_HAND;
	}
}
