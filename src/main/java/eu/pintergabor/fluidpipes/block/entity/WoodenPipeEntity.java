package eu.pintergabor.fluidpipes.block.entity;

import static eu.pintergabor.fluidpipes.block.entity.base.TickUtil.getTickPos;

import eu.pintergabor.fluidpipes.block.WoodenPipe;
import eu.pintergabor.fluidpipes.block.entity.base.BaseFluidPipeEntity;
import eu.pintergabor.fluidpipes.block.entity.base.TickUtil.TickPos;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class WoodenPipeEntity extends BaseFluidPipeEntity {

    public WoodenPipeEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.WOODEN_PIPE_ENTITY, blockPos, blockState);
    }

    /**
     * Called at every tick on the server.
     */
    public static void serverTick(
        World world, BlockPos pos, BlockState state, WoodenPipeEntity entity) {
        WoodenPipe block = (WoodenPipe) state.getBlock();
        int rate = block.getTickRate();
        TickPos tickPos = getTickPos(world, rate);
        if (tickPos == TickPos.START) {
            // Pull fluid.
            pull(world, pos, state, entity);
        }
        if (tickPos == TickPos.MIDDLE) {
            // Push fluid into blocks not capable of pulling it.
            push(world, pos, state, entity);
            // Dispense fluid.
            dispense(world, pos, state, entity);
        }
    }
}
