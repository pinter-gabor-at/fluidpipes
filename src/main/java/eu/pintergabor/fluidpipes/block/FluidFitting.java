package eu.pintergabor.fluidpipes.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pintergabor.fluidpipes.block.entity.FluidFittingEntity;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;
import eu.pintergabor.fluidpipes.block.util.DripShowUtil;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.registry.properties.ModProperties;
import eu.pintergabor.fluidpipes.tag.ModItemTags;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;


/**
 * A fluid fitting.
 */
public class FluidFitting extends BaseFitting implements FluidCarryBlock {
	public static final EnumProperty<PipeFluid> FLUID =
		ModProperties.FLUID;
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
	public static final MapCodec<FluidFitting> CODEC =
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
		).apply(instance, FluidFitting::new));

	/**
	 * Create a fitting as the CODEC requires it.
	 */
	public FluidFitting(
		final @NonNull Properties props,
		final int tickRate,
		final boolean canCarryWater,
		final boolean canCarryLava,
		final float wateringProbability,
		final float fireDripProbability,
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
			.setValue(FLUID, PipeFluid.NONE));
	}

	/**
	 * Create a fitting using {@link FluidBlockSettings}.
	 */
	public FluidFitting(
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

	@Override
	protected void createBlockStateDefinition(
		StateDefinition.@NonNull Builder<Block, BlockState> builder
	) {
		super.createBlockStateDefinition(builder);
		builder.add(FLUID);
	}

	/**
	 * Create a block entity.
	 */
	@Override
	public BlockEntity newBlockEntity(
		final @NonNull BlockPos pos,
		final @NonNull BlockState state
	) {
		return new FluidFittingEntity(pos, state);
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
		DripShowUtil.showDrip(level, pos, state, 0.0);
	}

	/**
	 * Use item on a fitting.
	 * <p>
	 * If it is another piece of pipe or fitting then place it,
	 * otherwise continue with the default action.
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
			// Allow placing fittings next to pipes and fittings.
			return InteractionResult.PASS;
		}
		return InteractionResult.TRY_WITH_EMPTY_HAND;
	}

	/**
	 * The fitting was removed.
	 */
	@Override
	protected void affectNeighborsAfterRemoval(
		final @NonNull BlockState state,
		final @NonNull ServerLevel level,
		final @NonNull BlockPos pos,
		final boolean moved
	) {
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
				blockEntityType, ModBlockEntities.FLUID_FITTING_ENTITY.get(),
				FluidFittingEntity::serverTick);
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
	protected @NonNull MapCodec<? extends FluidFitting> codec() {
		return CODEC;
	}
}
