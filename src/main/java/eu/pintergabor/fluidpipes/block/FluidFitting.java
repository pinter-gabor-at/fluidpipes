package eu.pintergabor.fluidpipes.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pintergabor.fluidpipes.block.entity.FluidFittingEntity;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;
import eu.pintergabor.fluidpipes.block.util.DripShowUtil;
import eu.pintergabor.fluidpipes.registry.ModFluidBlockEntities;
import eu.pintergabor.fluidpipes.registry.util.ModProperties;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;


/**
 * A fluid fitting.
 */
public class FluidFitting extends BaseFitting implements FluidCarryBlock {
	public static final EnumProperty<PipeFluid> FLUID =
		ModProperties.FLUID;
	// Block properties.
	public final float cloggingProbability;
	public final boolean canCarryWater;
	public final boolean canCarryLava;
	public final float fireBreakProbability;
	public final float fireDripProbability;
	public final float wateringProbability;
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
			Codec.FLOAT.fieldOf("clogging_probability")
				.forGetter((p) -> p.cloggingProbability),
			Codec.FLOAT.fieldOf("fire_break_probability")
				.forGetter((p) -> p.fireBreakProbability),
			Codec.FLOAT.fieldOf("fire_drip_probability")
				.forGetter((p) -> p.fireDripProbability),
			Codec.FLOAT.fieldOf("watering_probability")
				.forGetter((p) -> p.wateringProbability),
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
		Properties props,
		int tickRate, boolean canCarryWater, boolean canCarryLava,
		float cloggingProbability, float fireBreakProbability,
		float fireDripProbability, float wateringProbability,
		float waterDrippingProbability, float lavaDrippingProbability,
		float waterFillingProbability, float lavaFillingProbability
	) {
		super(props, tickRate);
		this.canCarryWater = canCarryWater;
		this.canCarryLava = canCarryLava;
		this.cloggingProbability = cloggingProbability;
		this.fireBreakProbability = fireBreakProbability;
		this.fireDripProbability = fireDripProbability;
		this.wateringProbability = wateringProbability;
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
	public FluidFitting(Properties props, @NonNull FluidBlockSettings modSettings) {
		this(
			props,
			modSettings.tickRate(), modSettings.canCarryWater(), modSettings.canCarryLava(),
			modSettings.cloggingProbability(), modSettings.fireBreakProbability(),
			modSettings.fireDripProbability(), modSettings.wateringProbability(),
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
	public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
		return new FluidFittingEntity(pos, state);
	}

	/**
	 * Dripping visualization.
	 */
	@Override
	public void animateTick(
		@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos,
		@NonNull RandomSource random
	) {
		super.animateTick(state, level, pos, random);
		DripShowUtil.showDrip(level, pos, state, 0.0);
	}


	/**
	 * The fitting was removed.
	 */
	@Override
	protected void affectNeighborsAfterRemoval(
		@NonNull BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos,
		boolean moved
	) {
		level.removeBlockEntity(pos);
	}

	/**
	 * Create a ticker, which will be called at every tick both on the client and on the server.
	 */
	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
		@NonNull Level level, @NonNull BlockState state,
		@NonNull BlockEntityType<T> blockEntityType
	) {
		if (!level.isClientSide()) {
			// Need a tick only on the server to implement the pipe logic.
			return createTickerHelper(
				blockEntityType, ModFluidBlockEntities.FLUID_FITTING_ENTITY,
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
	public float getCloggingProbability() {
		return cloggingProbability;
	}

	@Override
	public float getFireBreakProbability() {
		return fireBreakProbability;
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
