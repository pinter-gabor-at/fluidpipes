package eu.pintergabor.fluidpipes.block;

import static eu.pintergabor.fluidpipes.block.entity.leaking.DripUtil.*;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pintergabor.fluidpipes.block.base.BaseFluidFitting;
import eu.pintergabor.fluidpipes.block.entity.WoodenFittingEntity;
import eu.pintergabor.fluidpipes.block.entity.base.BaseFluidFittingEntity;
import eu.pintergabor.fluidpipes.block.entity.leaking.LeakingPipeDripBehaviors;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.tag.ModItemTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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


public class WoodenFitting extends BaseFluidFitting {
    public static final MapCodec<WoodenFitting> CODEC =
        RecordCodecBuilder.mapCodec((instance) -> instance.group(
            createSettingsCodec()
        ).apply(instance, WoodenFitting::new));

    public WoodenFitting(Settings settings) {
        super(settings);
    }

    /**
     * Create a block entity.
     */
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WoodenFittingEntity(pos, state);
    }

    @Override
    protected @NotNull MapCodec<? extends WoodenFitting> getCodec() {
        return CODEC;
    }
}
