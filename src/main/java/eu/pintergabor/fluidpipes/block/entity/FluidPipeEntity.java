package eu.pintergabor.fluidpipes.block.entity;

import static eu.pintergabor.fluidpipes.block.BaseBlock.getTickPos;
import static eu.pintergabor.fluidpipes.block.util.TickUtil.TickPos;

import eu.pintergabor.fluidpipes.block.util.DripActionUtil;
import eu.pintergabor.fluidpipes.block.util.FluidDispenseUtil;
import eu.pintergabor.fluidpipes.block.util.FluidPullUtil;
import eu.pintergabor.fluidpipes.block.util.FluidPushUtil;
import eu.pintergabor.fluidpipes.block.util.FluidUtil;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class FluidPipeEntity extends BasePipeEntity {

    public FluidPipeEntity(
        BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLUID_PIPE_ENTITY, pos, state);
    }

    /**
     * Called at every tick on the server.
     */
    public static void serverTick(
        World world, BlockPos pos, BlockState state, FluidPipeEntity entity) {
        TickPos tickPos = getTickPos(world, state);
        if (tickPos == TickPos.START) {
            // Pull fluid.
            FluidPullUtil.pull(world, pos, state, entity);
            // Clogging.
            FluidUtil.clog(world, pos, state);
        }
        if (tickPos == TickPos.MIDDLE) {
            // Push fluid into blocks that are not capable of pulling it.
            FluidPushUtil.push((ServerWorld) world, pos, state);
            // Dispense fluid.
            FluidDispenseUtil.dispense(world, pos, state);
            // Drip.
            DripActionUtil.dripDown((ServerWorld) world, pos, state);
            // Break.
            FluidDispenseUtil.breakFire((ServerWorld) world, pos, state);
        }
    }
}
