package eu.pintergabor.fluidpipes.block;

import static eu.pintergabor.fluidpipes.block.entity.leaking.DripUtil.*;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pintergabor.fluidpipes.block.base.BaseFluidPipe;
import eu.pintergabor.fluidpipes.block.entity.WoodenPipeEntity;
import eu.pintergabor.fluidpipes.block.entity.leaking.LeakingPipeDripBehaviors;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.tag.ModItemTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;


public class WoodenPipe extends BaseFluidPipe {
    public static final MapCodec<WoodenPipe> CODEC =
        RecordCodecBuilder.mapCodec((instance) -> instance.group(
            createSettingsCodec()
        ).apply(instance, WoodenPipe::new));

    public WoodenPipe(AbstractBlock.Settings settings) {
        super(settings);
    }

    /**
     * Create a block entity.
     */
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WoodenPipeEntity(pos, state);
    }

    /**
     * Create a ticker, which will be called at every tick both on the client and on the server.
     */
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
        @NotNull World world, BlockState state, BlockEntityType<T> blockEntityType) {
        if (!world.isClient()) {
            return validateTicker(
                blockEntityType, ModBlockEntities.WOODEN_PIPE_ENTITY,
                WoodenPipeEntity::serverTick);
        }
        return null;
    }

    /**
     * Use item on a pipe.
     * <p>
     * If it is another piece of pipe or fitting then place it,
     * otherwise open the GUI.
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

    @Override
    public void randomTick(@NotNull BlockState blockState, ServerWorld serverLevel, BlockPos blockPos, Random random) {
        Direction direction = blockState.get(FACING);
        boolean isLava = blockState.get(FLUID) == PipeFluid.LAVA;
        boolean isWater = blockState.get(FLUID) == PipeFluid.WATER;
        // Water can drip from any pipe containing water.
        // Lava can drip from any pipe containing lava, except those that face upwards.
        if (isWater || (isLava && direction != Direction.UP)) {
            // Adjust probability.
            if (random.nextFloat() <= (isLava ? 0.05859375F : 0.17578125F) * 2) {
                BlockPos.Mutable mutableBlockPos = blockPos.mutableCopy();
                if (direction != Direction.DOWN) {
                    mutableBlockPos.move(direction);
                }
                // Search down to 12 blocks.
                for (int i = 0; i < 12; i++) {
                    mutableBlockPos.move(Direction.DOWN);
                    BlockState state = serverLevel.getBlockState(mutableBlockPos);
                    if (serverLevel.getFluidState(mutableBlockPos).isEmpty()) {
                        // A block that reacts with the drip stops the drip.
                        LeakingPipeDripBehaviors.DripOn dripOn =
                            LeakingPipeDripBehaviors.getDrip(state.getBlock());
                        if (dripOn != null) {
                            dripOn.dripOn(isLava, serverLevel, mutableBlockPos, state);
                            break;
                        }
                        // A solid block stops the drip.
                        if (state.getCollisionShape(serverLevel, mutableBlockPos) != VoxelShapes.empty()) {
                            break;
                        }
                    } else {
                        // A block containing any liquid stops the drip.
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void randomDisplayTick(
        @NotNull BlockState blockState, @NotNull World world, @NotNull BlockPos blockPos, Random random) {
        Direction direction = blockState.get(FACING);
        BlockPos offsetPos = blockPos.offset(direction);
        BlockState offsetState = world.getBlockState(offsetPos);
        FluidState fluidState = offsetState.getFluidState();
        boolean canWater = blockState.get(FLUID) == PipeFluid.WATER && direction != Direction.UP;
        boolean canLava = blockState.get(FLUID) == PipeFluid.LAVA && direction != Direction.UP &&
            random.nextInt(2) == 0;
        boolean canWaterOrLava = canWater || canLava;
        if (canWaterOrLava) {
            double outX = blockPos.getX() + getDripX(direction, random);
            double outY = blockPos.getY() + getDripY(direction, random);
            double outZ = blockPos.getZ() + getDripZ(direction, random);
            if ((fluidState.isEmpty() || ((fluidState.getHeight(world, offsetPos)) + (double) offsetPos.getY()) < outY)) {
                world.addParticle(canWater ? ParticleTypes.DRIPPING_WATER : ParticleTypes.DRIPPING_LAVA,
                    outX, outY, outZ, 0, 0, 0);
            }
            if ((!offsetState.isAir() && fluidState.isEmpty())) {
                double x = blockPos.getX() + getDripX(direction, random);
                double y = blockPos.getY() + getDripY(direction, random);
                double z = blockPos.getZ() + getDripZ(direction, random);
                if (direction == Direction.DOWN) {
                    world.addParticle(canWater ? ParticleTypes.DRIPPING_WATER : ParticleTypes.DRIPPING_LAVA,
                        x, y, z, 0, 0, 0);
                }
            }
        }
        // If the pipe is in water.
        if (fluidState.isIn(FluidTags.WATER))
            if (random.nextFloat() < 0.1F || offsetState.getCollisionShape(world, offsetPos).isEmpty()) {
                world.addParticle(ParticleTypes.BUBBLE,
                    blockPos.getX() + getDripX(direction, random),
                    blockPos.getY() + getDripY(direction, random),
                    blockPos.getZ() + getDripZ(direction, random),
                    direction.getOffsetX() * 0.7D,
                    direction.getOffsetY() * 0.7D,
                    direction.getOffsetZ() * 0.7D);
                if (canLava && random.nextFloat() < 0.5F) {
                    world.addParticle(ParticleTypes.SMOKE,
                        blockPos.getX() + getDripX(direction, random),
                        blockPos.getY() + getDripY(direction, random),
                        blockPos.getZ() + getDripZ(direction, random),
                        direction.getOffsetX() * 0.05D,
                        direction.getOffsetY() * 0.05D,
                        direction.getOffsetZ() * 0.05D);
                }
            }
    }

    /**
     * The pipe was removed or its state changed.
     */
    @Override
    public void onStateReplaced(
        BlockState oldState, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!oldState.isOf(newState.getBlock())) {
            // Remove block and block entity.
            world.removeBlockEntity(pos);
        }
    }

    @Override
    protected @NotNull MapCodec<? extends WoodenPipe> getCodec() {
        return CODEC;
    }
}
