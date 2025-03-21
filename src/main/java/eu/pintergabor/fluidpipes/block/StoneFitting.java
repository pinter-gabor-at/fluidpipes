package eu.pintergabor.fluidpipes.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pintergabor.fluidpipes.block.base.BaseFluidFitting;
import eu.pintergabor.fluidpipes.block.entity.StoneFittingEntity;
import eu.pintergabor.fluidpipes.block.entity.WoodenFittingEntity;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;

import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

import org.jetbrains.annotations.Nullable;


public class StoneFitting extends BaseFluidFitting {
    public static final MapCodec<StoneFitting> CODEC =
        RecordCodecBuilder.mapCodec((instance) -> instance.group(
            createSettingsCodec(),
            Codec.INT.fieldOf("tick_rate")
                .forGetter((pipe) -> pipe.tickRate)
        ).apply(instance, StoneFitting::new));

    public StoneFitting(Settings settings, int tickRate) {
        super(settings, tickRate);
    }

    /**
     * Create a block entity.
     */
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StoneFittingEntity(pos, state);
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
                blockEntityType, ModBlockEntities.STONE_FITTING_ENTITY,
                StoneFittingEntity::serverTick);
        }
        return null;
    }

    @Override
    protected @NotNull MapCodec<? extends StoneFitting> getCodec() {
        return CODEC;
    }
}
