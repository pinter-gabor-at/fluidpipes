package eu.pintergabor.fluidpipes.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pintergabor.fluidpipes.block.base.BaseFluidFitting;
import eu.pintergabor.fluidpipes.block.entity.StoneFittingEntity;
import org.jetbrains.annotations.NotNull;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;


public class StoneFitting extends BaseFluidFitting {
    public static final MapCodec<StoneFitting> CODEC =
        RecordCodecBuilder.mapCodec((instance) -> instance.group(
            createSettingsCodec()
        ).apply(instance, StoneFitting::new));

    public StoneFitting(Settings settings) {
        super(settings);
    }

    /**
     * Create a block entity.
     */
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StoneFittingEntity(pos, state);
    }

    @Override
    protected @NotNull MapCodec<? extends StoneFitting> getCodec() {
        return CODEC;
    }
}
