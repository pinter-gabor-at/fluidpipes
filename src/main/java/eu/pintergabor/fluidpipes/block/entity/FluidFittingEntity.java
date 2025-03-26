package eu.pintergabor.fluidpipes.block.entity;

import static eu.pintergabor.fluidpipes.block.BaseBlock.getTickPos;
import static eu.pintergabor.fluidpipes.block.util.TickUtil.TickPos;

import eu.pintergabor.fluidpipes.block.util.DripActionUtil;
import eu.pintergabor.fluidpipes.block.util.FluidFittingUtil;
import eu.pintergabor.fluidpipes.block.util.FluidUtil;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class FluidFittingEntity extends BaseFittingEntity {

    public FluidFittingEntity(
        BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLUID_FITTING_ENTITY, pos, state);
    }

    /**
     * Called at every tick on the server.
     */
    public static void serverTick(
        World world, BlockPos pos, BlockState state, FluidFittingEntity entity) {
        TickPos tickPos = getTickPos(world, state);
        if (tickPos == TickPos.START) {
            // Pull fluid.
            FluidFittingUtil.pull(world, pos, state, entity);
            // Clogging.
            FluidUtil.clog(world, pos, state);
        }
        if (tickPos == TickPos.MIDDLE) {
            boolean powered = state.get(Properties.POWERED, false);
            if (!powered) {
                // Drip.
                DripActionUtil.dripDown((ServerWorld) world, pos, state);
                // Break.
                FluidFittingUtil.breakFire((ServerWorld) world, pos, state);
            }
        }
    }
}
