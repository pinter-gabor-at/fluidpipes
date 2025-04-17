package eu.pintergabor.fluidpipes.block;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
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
    protected static final VoxelShape FITTING_SHAPE =
        Block.box(
            2.5D, 2.5D, 2.5D, 13.5D, 13.5D, 13.5D);
    public static final BooleanProperty POWERED =
        BlockStateProperties.POWERED;

    protected BaseFitting(Properties props, int tickRate) {
        super(props, tickRate);
        registerDefaultState(getStateDefinition().any()
            .setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(
        StateDefinition.Builder<Block, BlockState> builder
    ) {
        super.createBlockStateDefinition(builder);
        builder.add(POWERED);
    }

    @Override
    public @NotNull VoxelShape getShape(
        BlockState state, BlockGetter level, BlockPos pos, CollisionContext context
    ) {
        return FITTING_SHAPE;
    }

    @Override
    @NotNull
    public VoxelShape getInteractionShape(
        BlockState state, BlockGetter level, BlockPos pos
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
        Level level, BlockPos blockPos
    ) {
        for (Direction d : DIRECTIONS) {
            BlockPos neighbourPos = blockPos.relative(d);
            if (0 < level.getSignal(neighbourPos, d)) {
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
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            BlockPos pos = context.getClickedPos();
            Level world = context.getLevel();
            return state
                .setValue(POWERED, isReceivingRedstonePower(world, pos));
        }
        return null;
    }

    /**
     * Handle side effects when the neighboring block's state changes.
     */
    protected void neighborChanged(
        BlockState state, Level level,
        BlockPos pos, Block neighborBlock, @Nullable Orientation orientation,
        boolean movedByPiston
    ) {
        boolean powered = isReceivingRedstonePower(level, pos);
        if (powered != state.getValue(POWERED)) {
            level.setBlockAndUpdate(pos, state.setValue(POWERED, powered));
        }
    }
}
