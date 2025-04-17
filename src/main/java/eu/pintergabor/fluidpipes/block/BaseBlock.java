package eu.pintergabor.fluidpipes.block;

import static eu.pintergabor.fluidpipes.registry.ModStats.incStat;

import eu.pintergabor.fluidpipes.block.util.TickUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;


/**
 * Base block of the mod.
 * <p>
 * Pipes and fittings are rendered normally, they do not block light,
 * and entities cannot walk through them.
 */
public sealed abstract class BaseBlock extends BaseEntityBlock implements SimpleWaterloggedBlock
    permits BaseFitting, BasePipe {

    // Common BlockState properties.
    public static final BooleanProperty WATERLOGGED =
        BlockStateProperties.WATERLOGGED;
    public final int tickRate;
    // All directions in pull priority order.
    public static final Direction[] DIRECTIONS = {
        Direction.UP, Direction.NORTH, Direction.EAST,
        Direction.SOUTH, Direction.WEST, Direction.DOWN};

    protected BaseBlock(Properties props, int tickRate) {
        super(props);
        this.tickRate = tickRate;
        registerDefaultState(getStateDefinition().any()
            .setValue(WATERLOGGED, false));
    }

    @Override
    public void setPlacedBy(
        Level level, BlockPos pos, BlockState state,
        @Nullable LivingEntity placer, ItemStack itemStack
    ) {
        super.setPlacedBy(level, pos, state, placer, itemStack);
        // Increment statistics.
        if (!level.isClientSide &&
            placer instanceof ServerPlayer serverPlayer) {
            incStat(serverPlayer);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
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
            Level level = context.getLevel();
            return state
                .setValue(WATERLOGGED,
                    level.getFluidState(pos).is(Fluids.WATER));
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
        @NotNull BlockState blockState,
        LevelReader level,
        ScheduledTickAccess scheduledTickAccess,
        BlockPos pos,
        Direction direction,
        BlockPos neighborPos,
        BlockState neighborState,
        RandomSource random
    ) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(
                pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return blockState;
    }

    /**
     * Pipes and fittings do not block light.
     *
     * @return true
     */
    @Override
    protected boolean propagatesSkylightDown(@NotNull BlockState blockState) {
        return true;
    }

    /**
     * Entities cannot walk through a pipe or a fitting.
     *
     * @return false
     */
    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    /**
     * Pipes and fittings are rendered normally.
     */
    @Override
    @NotNull
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    @NotNull
    public FluidState getFluidState(@NotNull BlockState blockState) {
        if (blockState.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(blockState);
    }

    /**
     * How fast is this block?
     * Higher value means slower operation.
     * Min 2.
     */
    public int getTickRate() {
        return tickRate;
    }

    /**
     * Return {@link TickUtil.TickPos#START} and {@link TickUtil.TickPos#MIDDLE} once in every {@code 1 / rate} time
     */
    public static TickUtil.TickPos getTickPos(Level level, BlockState state) {
        BaseBlock block = (BaseBlock) state.getBlock();
        int rate = block.getTickRate();
        return TickUtil.getTickPos(level, rate);
    }
}
