package eu.pintergabor.fluidpipes.block;

import eu.pintergabor.fluidpipes.block.util.TickUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

import static eu.pintergabor.fluidpipes.registry.ModStats.incStat;


/**
 * Base block of the mod.
 * <p>
 * Pipes and fittings are rendered normally, they do not block light,
 * and entities cannot walk through them.
 */
public sealed abstract class BaseBlock extends BlockWithEntity implements Waterloggable
    permits BaseFitting, BasePipe {

    // Common BlockState properties.
    public static final BooleanProperty WATERLOGGED =
        Properties.WATERLOGGED;
    public final int tickRate;
    // All directions in pull priority order.
    public static final Direction[] DIRECTIONS = {
        Direction.UP, Direction.NORTH, Direction.EAST,
        Direction.SOUTH, Direction.WEST, Direction.DOWN};

    protected BaseBlock(Settings settings, int tickRate) {
        super(settings);
        this.tickRate = tickRate;
        setDefaultState(getStateManager().getDefaultState()
            .with(WATERLOGGED, false));
    }

    @Override
    public void onPlaced(
        World world, BlockPos pos, BlockState state,
        @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        // Increment statistics.
        if (!world.isClient() &&
            placer instanceof ServerPlayerEntity serverPlayer) {
            incStat(serverPlayer);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED);
    }

    /**
     * Determine the initial state of the pipe based on its surroundings.
     *
     * @return the initial state of the block
     */
    @Override
    public BlockState getPlacementState(@NotNull ItemPlacementContext context) {
        BlockState state = super.getPlacementState(context);
        if (state != null) {
            BlockPos pos = context.getBlockPos();
            World world = context.getWorld();
            return state
                .with(WATERLOGGED,
                    world.getFluidState(pos).getFluid() == Fluids.WATER);
        }
        return null;
    }

    /**
     * Handle state changes when the neighboring block's state changes.
     *
     * @return the state of the pipe after a neighboring block's state changes.
     */
    @Override
    protected @NotNull BlockState getStateForNeighborUpdate(
        @NotNull BlockState blockState,
        WorldView world,
        ScheduledTickView scheduledTickAccess,
        BlockPos pos,
        Direction direction,
        BlockPos neighborPos,
        BlockState neighborState,
        Random random) {
        if (blockState.get(WATERLOGGED)) {
            scheduledTickAccess.scheduleFluidTick(
                pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return blockState;
    }

    /**
     * Pipes and fittings do not block light.
     *
     * @return true
     */
    @Override
    protected boolean isTransparent(@NotNull BlockState blockState) {
        return true;
    }

    /**
     * Entities cannot walk through a pipe or a fitting.
     *
     * @return false
     */
    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType navType) {
        return false;
    }

    /**
     * Pipes and fittings are rendered normally.
     */
    @Override
    @NotNull
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    @NotNull
    public FluidState getFluidState(@NotNull BlockState blockState) {
        if (blockState.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
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
    public static TickUtil.TickPos getTickPos(World world, BlockState state) {
        BaseBlock block = (BaseBlock) state.getBlock();
        int rate = block.getTickRate();
        return TickUtil.getTickPos(world, rate);
    }
}
