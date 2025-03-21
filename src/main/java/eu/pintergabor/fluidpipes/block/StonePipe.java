package eu.pintergabor.fluidpipes.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pintergabor.fluidpipes.block.base.BaseFluidPipe;
import eu.pintergabor.fluidpipes.block.entity.StonePipeEntity;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class StonePipe extends BaseFluidPipe {
    public static final MapCodec<StonePipe> CODEC =
        RecordCodecBuilder.mapCodec((instance) -> instance.group(
            createSettingsCodec(),
            Codec.INT.fieldOf("tick_rate")
                .forGetter((pipe) -> pipe.tickRate)
        ).apply(instance, StonePipe::new));

    public StonePipe(Settings settings, int tickRate) {
        super(settings, tickRate);
    }

    /**
     * Create a block entity.
     */
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StonePipeEntity(pos, state);
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
                blockEntityType, ModBlockEntities.STONE_PIPE_ENTITY,
                StonePipeEntity::serverTick);
        }
        return null;
    }

    @Override
    protected @NotNull MapCodec<? extends StonePipe> getCodec() {
        return CODEC;
    }
}
