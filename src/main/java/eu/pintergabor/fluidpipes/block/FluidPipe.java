package eu.pintergabor.fluidpipes.block;

import static eu.pintergabor.fluidpipes.block.util.FluidDispenseUtil.removeOutflow;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pintergabor.fluidpipes.block.entity.FluidPipeEntity;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;
import eu.pintergabor.fluidpipes.block.util.DripShowUtil;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.registry.properties.ModProperties;
import eu.pintergabor.fluidpipes.tag.ModItemTags;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;


/**
 * A fluid pipe that can carry water or lava.
 */
public class FluidPipe extends BasePipe implements FluidCarryBlock {
	// BlockState properties.
	public static final EnumProperty<PipeFluid> FLUID =
		ModProperties.FLUID;
	public static final BooleanProperty OUTFLOW =
		ModProperties.OUTFLOW;
	// Block properties.
	public final boolean canCarryWater;
	public final boolean canCarryLava;
	public final float wateringProbability;
	public final float fireDripProbability;
	public final float waterDrippingProbability;
	public final float lavaDrippingProbability;
	public final float waterFillingProbability;
	public final float lavaFillingProbability;
	// Matching CODEC.
	public static final MapCodec<FluidPipe> CODEC =
		RecordCodecBuilder.mapCodec((instance) -> instance.group(
			propertiesCodec(),
			Codec.INT.fieldOf("tick_rate")
				.forGetter((p) -> p.tickRate),
			Codec.BOOL.fieldOf("can_carry_water")
				.forGetter((p) -> p.canCarryWater),
			Codec.BOOL.fieldOf("can_carry_lava")
				.forGetter((p) -> p.canCarryLava),
			Codec.FLOAT.fieldOf("watering_probability")
				.forGetter((p) -> p.wateringProbability),
			Codec.FLOAT.fieldOf("fire_drip_probability")
				.forGetter((p) -> p.fireDripProbability),
			Codec.FLOAT.fieldOf("water_dripping_probability")
				.forGetter((p) -> p.waterDrippingProbability),
			Codec.FLOAT.fieldOf("lava_dripping_probability")
				.forGetter((p) -> p.lavaDrippingProbability),
			Codec.FLOAT.fieldOf("water_filling_probability")
				.forGetter((p) -> p.waterFillingProbability),
			Codec.FLOAT.fieldOf("lava_filling_probability")
				.forGetter((p) -> p.lavaFillingProbability)
		).apply(instance, FluidPipe::new));

	/**
	 * Create a pipe as the CODEC requires it.
	 */
	public FluidPipe(
		final @NonNull Properties props,
		final int tickRate,
		final boolean canCarryWater,
		final boolean canCarryLava,
		final float fireDripProbability,
		final float wateringProbability,
		final float waterDrippingProbability,
		final float lavaDrippingProbability,
		final float waterFillingProbability,
		final float lavaFillingProbability
	) {
		super(props, tickRate);
		this.canCarryWater = canCarryWater;
		this.canCarryLava = canCarryLava;
		this.wateringProbability = wateringProbability;
		this.fireDripProbability = fireDripProbability;
		this.waterDrippingProbability = waterDrippingProbability;
		this.lavaDrippingProbability = lavaDrippingProbability;
		this.waterFillingProbability = waterFillingProbability;
		this.lavaFillingProbability = lavaFillingProbability;
		registerDefaultState(defaultBlockState()
			.setValue(FLUID, PipeFluid.NONE)
			.setValue(OUTFLOW, false));
	}

	/**
	 * Create a pipe using {@link FluidBlockSettings}.
	 */
	@SuppressWarnings("unused")
	public FluidPipe(
		final @NonNull Properties props,
		final @NonNull FluidBlockSettings modSettings
	) {
		this(
			props,
			modSettings.tickRate(), modSettings.canCarryWater(), modSettings.canCarryLava(),
			modSettings.wateringProbability(), modSettings.fireDripProbability(),
			modSettings.waterDrippingProbability(), modSettings.lavaDrippingProbability(),
			modSettings.waterFillingProbability(), modSettings.lavaFillingProbability()
		);
	}

	/**
	 * Append FLUID and OUTFLOW to BlockState properties.
	 */
	@Override
	protected void createBlockStateDefinition(
		StateDefinition.@NonNull Builder<Block, BlockState> builder
	) {
		super.createBlockStateDefinition(builder);
		builder.add(FLUID, OUTFLOW);
	}

	/**
	 * Create a block entity.
	 */
	@Override
	public BlockEntity newBlockEntity(
		final @NonNull BlockPos pos,
		final @NonNull BlockState state
	) {
		return new FluidPipeEntity(pos, state);
	}

	/**
	 * Check if this fluid block is an outflow from a pipe in a direction.
	 *
	 * @param dir in this direction.
	 * @return true if it is an outflow.
	 */
	private static boolean isOutFlowInDir(
		final @NonNull BlockGetter level,
		final @NonNull BlockPos pos,
		final @NonNull FlowingFluid fluid,
		final @NonNull Direction dir
	) {
		// The neighboring block.
		final BlockPos nPos = pos.relative(dir);
		final BlockState nState = level.getBlockState(nPos);
		final Block nBlock = nState.getBlock();
		// Logic.
		if (nBlock instanceof FluidPipe) {
			// If it is next to a fluid pipe ...
			final boolean outflow = nState.getValue(ModProperties.OUTFLOW);
			final Direction facing = nState.getValue(BlockStateProperties.FACING);
			final PipeFluid pipeFluid = nState.getValue(ModProperties.FLUID);
			// ... which is facing the right way and supplying fluid.
			return outflow && facing == dir.getOpposite() &&
				(pipeFluid == PipeFluid.WATER && fluid == Fluids.WATER ||
					pipeFluid == PipeFluid.LAVA && fluid == Fluids.LAVA);
		}
		return false;
	}

	/**
	 * Check if this fluid block is an outflow from a pipe.
	 *
	 * @return true if it is an outflow.
	 */
	public static boolean isOutflow(
		final @NonNull BlockGetter level,
		final @NonNull BlockPos pos,
		final @NonNull FlowingFluid fluid
	) {
		// Look around to find a fluid pipe that is supplying fluid to this block.
		for (Direction dir : DIRECTIONS) {
			if (isOutFlowInDir(level, pos, fluid, dir)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Dripping visualization.
	 */
	@Override
	public void animateTick(
		final @NonNull BlockState state,
		final @NonNull Level level,
		final @NonNull BlockPos pos,
		final @NonNull RandomSource random
	) {
		super.animateTick(state, level, pos, random);
		// This block.
		Direction facing = state.getValue(FACING);
		if (!(facing == Direction.DOWN || facing == Direction.UP)) {
			DripShowUtil.showDrip(level, pos, state, 0.1);
		}
	}

	@Override
	protected BlockState beforeTurning(
		final @NonNull Level level,
		final @NonNull BlockPos pos,
		@NonNull BlockState state
	) {
		// Stop the outflow.
		removeOutflow(level, pos, state);
		// And return the state without outflow.
		return super.beforeTurning(level, pos, state)
			.setValue(ModProperties.OUTFLOW, false);
	}

	/**
	 * Use item on a pipe.
	 * <p>
	 * If it is another piece of pipe or fitting then place it,
	 * if it is a hoe, turn it, otherwise continue with the default action.
	 */
	@Override
	protected @NonNull InteractionResult useItemOn(
		final @NonNull ItemStack stack,
		final @NonNull BlockState state,
		final @NonNull Level level,
		final @NonNull BlockPos pos,
		final @NonNull Player player,
		final @NonNull InteractionHand hand,
		final @NonNull BlockHitResult hit
	) {
		if (stack.is(ModItemTags.FLUID_PIPES_AND_FITTINGS)) {
			// Allow placing pipes next to pipes and fittings.
			return InteractionResult.PASS;
		}
		if (stack.is(ItemTags.HOES)) {
			// Turn pipes with a hoe.
			turnWithTool(level, pos, state, player, hand, hit, stack);
			return InteractionResult.SUCCESS;
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}

	/**
	 * The pipe was removed or its state changed.
	 */
	@Override
	protected void affectNeighborsAfterRemoval(
		@NonNull BlockState state,
		final @NonNull ServerLevel level,
		final @NonNull BlockPos pos,
		final boolean moved
	) {
		// Remove outflow.
		removeOutflow(level, pos, state);
		// Remove block and block entity.
		level.removeBlockEntity(pos);
	}

	/**
	 * Create a ticker, which will be called at every tick both on the client and on the server.
	 */
	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
		final @NonNull Level level,
		final @NonNull BlockState state,
		final @NonNull BlockEntityType<T> blockEntityType
	) {
		if (!level.isClientSide()) {
			// Need a tick only on the server to implement the pipe logic.
			return createTickerHelper(
				blockEntityType, ModBlockEntities.FLUID_PIPE_ENTITY,
				FluidPipeEntity::serverTick);
		}
		return null;
	}

	@Override
	public boolean canCarryWater() {
		return canCarryWater;
	}

	@Override
	public boolean canCarryLava() {
		return canCarryLava;
	}

	@Override
	public float getWateringProbability() {
		return wateringProbability;
	}

	@Override
	public float getFireDripProbability() {
		return fireDripProbability;
	}

	@Override
	public float getWaterDrippingProbability() {
		return waterDrippingProbability;
	}

	@Override
	public float getLavaDrippingProbability() {
		return lavaDrippingProbability;
	}

	@Override
	public float getWaterFillingProbability() {
		return waterFillingProbability;
	}

	@Override
	public float getLavaFillingProbability() {
		return lavaFillingProbability;
	}

	@Override
	public FluidBlockSettings getFluidBlockSettings() {
		return FluidCarryBlock.super.getFluidBlockSettings();
	}

	@Override
	protected @NonNull MapCodec<? extends FluidPipe> codec() {
		return CODEC;
	}
}
