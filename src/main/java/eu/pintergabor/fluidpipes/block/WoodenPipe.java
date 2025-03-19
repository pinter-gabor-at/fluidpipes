package eu.pintergabor.fluidpipes.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pintergabor.fluidpipes.block.base.BaseFluidPipe;
import eu.pintergabor.fluidpipes.block.entity.WoodenPipeEntity;
import org.jetbrains.annotations.NotNull;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;


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

    @Override
    protected @NotNull MapCodec<? extends WoodenPipe> getCodec() {
        return CODEC;
    }
}
