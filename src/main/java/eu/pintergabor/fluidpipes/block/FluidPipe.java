package eu.pintergabor.fluidpipes.block;

import static eu.pintergabor.fluidpipes.block.util.FluidDispenseUtil.removeOutflow;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pintergabor.fluidpipes.block.entity.FluidPipeEntity;
import eu.pintergabor.fluidpipes.block.util.DripShowUtil;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.registry.ModProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;


/**
 * A fluid pipe can carry water or lava.
 */
public non-sealed class FluidPipe extends BasePipe implements FluidCarryBlock {
    // BlockState properties.
    public static final EnumProperty<PipeFluid> FLUID =
        ModProperties.FLUID;
    public static final BooleanProperty OUTFLOW =
        ModProperties.OUTFLOW;
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
    public static final MapCodec<FluidPipe> CODEC =
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
        ).apply(instance, FluidPipe::new));

    /**
     * Create pipe as the CODEC requires it.
     */
    public FluidPipe(
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
            .with(FLUID, PipeFluid.NONE)
            .with(OUTFLOW, false));
    }

    /**
     * Create pipe using {@link FluidBlockSettings}.
     */
    @SuppressWarnings("unused")
    public FluidPipe(Settings settings, FluidBlockSettings modSettings) {
        this(
            settings,
            modSettings.tickRate(), modSettings.canCarryWater(), modSettings.canCarryLava(),
            modSettings.cloggingProbability(), modSettings.fireBreakProbability(),
            modSettings.fireDripProbability(), modSettings.wateringProbability(),
            modSettings.waterDrippingProbability(), modSettings.lavaDrippingProbability(),
            modSettings.waterFillingProbability(), modSettings.lavaFillingProbability()
        );
    }

    /**
     * Append FLUID and OUTFLOW to BlockState properties.
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FLUID, OUTFLOW);
    }

    /**
     * Create a block entity.
     */
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FluidPipeEntity(pos, state);
    }

    /**
     * Check if this fluid block is an outflow from a pipe.
     *
     * @return true if it is an outflow.
     */
    public static boolean isOutflow(
        WorldAccess world,
        BlockPos pos,
        FlowableFluid fluid
    ) {
        // Look around to find a fluid pipe that is supplying fluid to this block.
        for (Direction d : DIRECTIONS) {
            // The neighbouring block.
            BlockPos nPos = pos.offset(d);
            BlockState nState = world.getBlockState(nPos);
            Block nBlock = nState.getBlock();
            // Logic.
            if (nBlock instanceof FluidPipe) {
                // If it is next to a fluid pipe ...
                boolean outflow = nState.get(ModProperties.OUTFLOW);
                Direction facing = nState.get(Properties.FACING);
                if (outflow && facing == d.getOpposite()) {
                    // ... which is facing the right way and supplying fluid.
                    PipeFluid pipeFluid = nState.get(ModProperties.FLUID);
                    if (pipeFluid == PipeFluid.WATER && fluid == Fluids.WATER) {
                        return true;
                    }
                    if (pipeFluid == PipeFluid.LAVA && fluid == Fluids.LAVA) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Dripping visualization.
     */
    @Override
    public void randomDisplayTick(
        @NotNull BlockState state, @NotNull World world, @NotNull BlockPos pos, Random random) {
        // This block.
        Direction facing = state.get(FACING);
        if (!(facing == Direction.DOWN || facing == Direction.UP)) {
            DripShowUtil.showDrip(world, pos, state, 0.1);
        }
//        BlockPos offsetPos = pos.offset(facing);
//        BlockState offsetState = world.getBlockState(offsetPos);
//        FluidState fluidState = offsetState.getFluidState();
//        boolean canWater = state.get(FLUID) == PipeFluid.WATER && facing != Direction.UP;
//        boolean canLava = state.get(FLUID) == PipeFluid.LAVA && facing != Direction.UP &&
//            random.nextInt(2) == 0;
//        boolean canWaterOrLava = canWater || canLava;
//        if (canWaterOrLava) {
//            double outX = pos.getX() + getDripX(facing, random);
//            double outY = pos.getY() + getDripY(facing, random);
//            double outZ = pos.getZ() + getDripZ(facing, random);
//            if ((fluidState.isEmpty() || ((fluidState.getHeight(world, offsetPos)) + (double) offsetPos.getY()) < outY)) {
//                world.addParticle(canWater ? ParticleTypes.DRIPPING_WATER : ParticleTypes.DRIPPING_LAVA,
//                    outX, outY, outZ, 0, 0, 0);
//            }
//            if ((!offsetState.isAir() && fluidState.isEmpty())) {
//                double x = pos.getX() + getDripX(facing, random);
//                double y = pos.getY() + getDripY(facing, random);
//                double z = pos.getZ() + getDripZ(facing, random);
//                if (facing == Direction.DOWN) {
//                    world.addParticle(canWater ? ParticleTypes.DRIPPING_WATER : ParticleTypes.DRIPPING_LAVA,
//                        x, y, z, 0, 0, 0);
//                }
//            }
//        }
//        // If the pipe is in water.
//        if (fluidState.isIn(FluidTags.WATER))
//            if (random.nextFloat() < 0.1F || offsetState.getCollisionShape(world, offsetPos).isEmpty()) {
//                world.addParticle(ParticleTypes.BUBBLE,
//                    pos.getX() + getDripX(facing, random),
//                    pos.getY() + getDripY(facing, random),
//                    pos.getZ() + getDripZ(facing, random),
//                    facing.getOffsetX() * 0.7D,
//                    facing.getOffsetY() * 0.7D,
//                    facing.getOffsetZ() * 0.7D);
//                if (canLava && random.nextFloat() < 0.5F) {
//                    world.addParticle(ParticleTypes.SMOKE,
//                        pos.getX() + getDripX(facing, random),
//                        pos.getY() + getDripY(facing, random),
//                        pos.getZ() + getDripZ(facing, random),
//                        facing.getOffsetX() * 0.05D,
//                        facing.getOffsetY() * 0.05D,
//                        facing.getOffsetZ() * 0.05D);
//                }
//            }
    }

    @Override
    protected BlockState beforeTurning(World world, BlockPos pos, BlockState state) {
        // Stop the outflow.
        removeOutflow(world, pos, state);
        // And return the sate without outflow.
        return super.beforeTurning(world, pos, state)
            .with(ModProperties.OUTFLOW, false);
    }

    /**
     * The pipe was removed or its state changed.
     */
    @Override
    public void onStateReplaced(
        BlockState oldState, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!oldState.isOf(newState.getBlock())) {
            // Remove outflow.
            removeOutflow(world, pos, oldState);
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
    public float getCloggingProbability() {
        return cloggingProbability;
    }

    @Override
    public float getFireBreakProbability() {
        return fireBreakProbability;
    }

    @Override
    public float getFireDripProbability() {
        return fireDripProbability;
    }

    @Override
    public float getWateringProbability() {
        return wateringProbability;
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
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }
}
