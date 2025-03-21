package eu.pintergabor.fluidpipes.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pintergabor.fluidpipes.block.base.BaseFluidFitting;
import eu.pintergabor.fluidpipes.block.entity.WoodenFittingEntity;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class WoodenFitting extends BaseFluidFitting {
    public static final MapCodec<WoodenFitting> CODEC =
        RecordCodecBuilder.mapCodec((instance) -> instance.group(
            createSettingsCodec(),
            Codec.INT.fieldOf("tick_rate")
                .forGetter((pipe) -> pipe.tickRate)
        ).apply(instance, WoodenFitting::new));

    public WoodenFitting(Settings settings, int tickRate) {
        super(settings, tickRate);
    }

    /**
     * Create a block entity.
     */
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WoodenFittingEntity(pos, state);
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
                blockEntityType, ModBlockEntities.WOODEN_FITTING_ENTITY,
                WoodenFittingEntity::serverTick);
        }
        return null;
    }

    @Override
    protected @NotNull MapCodec<? extends WoodenFitting> getCodec() {
        return CODEC;
    }
}
