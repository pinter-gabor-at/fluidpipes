package eu.pintergabor.fluidpipes.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pintergabor.fluidpipes.block.base.BaseFluidPipe;
import eu.pintergabor.fluidpipes.block.entity.StonePipeEntity;
import org.jetbrains.annotations.NotNull;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;


public class StonePipe extends BaseFluidPipe {
    public static final MapCodec<StonePipe> CODEC =
        RecordCodecBuilder.mapCodec((instance) -> instance.group(
            createSettingsCodec()
        ).apply(instance, StonePipe::new));

    public StonePipe(Settings settings) {
        super(settings);
    }

    /**
     * Create a block entity.
     */
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StonePipeEntity(pos, state);
    }

    @Override
    protected @NotNull MapCodec<? extends StonePipe> getCodec() {
        return CODEC;
    }
}
