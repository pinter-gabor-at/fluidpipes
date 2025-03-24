package eu.pintergabor.fluidpipes.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pintergabor.fluidpipes.block.entity.FluidFittingEntity;
import eu.pintergabor.fluidpipes.block.entity.leaking.DripUtil;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.registry.ModProperties;
import eu.pintergabor.fluidpipes.tag.ModItemTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;


public non-sealed class FluidFitting extends BaseFitting implements FluidCarryBlock {
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
            createSettingsCodec(),
            Codec.INT.fieldOf("tick_rate")
                .forGetter((fitting) -> fitting.tickRate),
            Codec.BOOL.fieldOf("can_carry_water")
                .forGetter((fitting) -> fitting.canCarryWater),
            Codec.BOOL.fieldOf("can_carry_lava")
                .forGetter((fitting) -> fitting.canCarryLava),
            Codec.FLOAT.fieldOf("clogging_probability")
                .forGetter((fitting) -> fitting.cloggingProbability),
            Codec.FLOAT.fieldOf("fire_break_probability")
                .forGetter((fitting) -> fitting.fireBreakProbability),
            Codec.FLOAT.fieldOf("fire_drip_probability")
                .forGetter((fitting) -> fitting.fireDripProbability),
            Codec.FLOAT.fieldOf("watering_probability")
                .forGetter((fitting) -> fitting.wateringProbability),
            Codec.FLOAT.fieldOf("water_dripping_probability")
                .forGetter((fitting) -> fitting.waterDrippingProbability),
            Codec.FLOAT.fieldOf("lava_dripping_probability")
                .forGetter((fitting) -> fitting.lavaDrippingProbability),
            Codec.FLOAT.fieldOf("water_filling_probability")
                .forGetter((fitting) -> fitting.waterFillingProbability),
            Codec.FLOAT.fieldOf("lava_filling_probability")
                .forGetter((fitting) -> fitting.lavaFillingProbability)
        ).apply(instance, FluidFitting::new));

    /**
     * Create fitting as the CODEC requires it.
     */
    public FluidFitting(
        Settings settings,
        int tickRate, boolean canCarryWater, boolean canCarryLava,
        float cloggingProbability, float fireBreakProbability,
        float fireDripProbability, float wateringProbability,
        float waterDrippingProbability, float lavaDrippingProbability,
        float waterFillingProbability, float lavaFillingProbability
    ) {
        super(settings, tickRate);
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
        setDefaultState(getStateManager().getDefaultState()
            .with(FLUID, PipeFluid.NONE));
    }

    /**
     * Create pipe using {@link FluidBlockSettings}.
     */
    public FluidFitting(Settings settings, FluidBlockSettings modSettings) {
        this(
            settings,
            modSettings.tickRate(), modSettings.canCarryWater(), modSettings.canCarryLava(),
            modSettings.cloggingProbability(), modSettings.fireBreakProbability(),
            modSettings.fireDripProbability(), modSettings.wateringProbability(),
            modSettings.waterDrippingProbability(), modSettings.lavaDrippingProbability(),
            modSettings.waterFillingProbability(), modSettings.lavaFillingProbability()
        );
    }

    @Override
    protected void appendProperties(
        StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FLUID);
    }

    /**
     * Create a block entity.
     */
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FluidFittingEntity(pos, state);
    }

    /**
     * Use item on a fitting.
     * <p>
     * If it is another piece of pipe or fitting then place it,
     * otherwise continue with the default action.
     */
    @Override
    protected @NotNull ActionResult onUseWithItem(
        @NotNull ItemStack stack,
        BlockState state, World world, BlockPos pos,
        PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        // Allow placing pipes next to pipes and fittings.
        if (stack.isIn(ModItemTags.PIPES_AND_FITTINGS)) {
            return ActionResult.PASS;
        }
        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    }

    /**
     * Dripping visualization.
     */
    @Override
    public void randomDisplayTick(
        @NotNull BlockState state, @NotNull World world, @NotNull BlockPos pos,
        Random random) {
        DripUtil.showDrip(world, pos, state, 0.0);
    }

    /**
     * The fitting was removed or its state changed.
     */
    @Override
    public void onStateReplaced(
        BlockState oldState, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!oldState.isOf(newState.getBlock())) {
            // Remove block and block entity.
            world.removeBlockEntity(pos);
        }
    }

    /**
     * Create a ticker, which will be called at every tick both on the client and on the server.
     */
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
        @NotNull World world, BlockState state, BlockEntityType<T> blockEntityType) {
        if (!world.isClient()) {
            // Need a tick only on the server to implement the pipe logic.
            return validateTicker(
                blockEntityType, ModBlockEntities.FLUID_FITTING_ENTITY,
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
    protected @NotNull MapCodec<? extends FluidFitting> getCodec() {
        return CODEC;
    }
}
