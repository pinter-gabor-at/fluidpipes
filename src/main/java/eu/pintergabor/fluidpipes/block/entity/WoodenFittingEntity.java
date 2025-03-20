package eu.pintergabor.fluidpipes.block.entity;

import static eu.pintergabor.fluidpipes.block.entity.base.TickUtil.getTickPos;

import eu.pintergabor.fluidpipes.block.WoodenFitting;
import eu.pintergabor.fluidpipes.block.entity.base.BaseFluidFittingEntity;
import eu.pintergabor.fluidpipes.block.entity.base.TickUtil.TickPos;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class WoodenFittingEntity extends BaseFluidFittingEntity {

    public WoodenFittingEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.WOODEN_FITTING_ENTITY, blockPos, blockState);
    }

    /**
     * Called at every tick on the server.
     */
    public static void serverTick(
        World world, BlockPos pos, BlockState state, WoodenFittingEntity entity) {
        WoodenFitting block = (WoodenFitting) state.getBlock();
        int rate = block.getTickRate();
        TickPos tickPos = getTickPos(world, rate);
        if (tickPos == TickPos.START) {
            // Pull fluid.
            pull(world, pos, state, entity);
        }
    }
}
