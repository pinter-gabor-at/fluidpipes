package eu.pintergabor.fluidpipes.block.entity;

import static eu.pintergabor.fluidpipes.block.BaseBlock.getTickPos;
import static eu.pintergabor.fluidpipes.block.util.FluidFittingUtil.*;
import static eu.pintergabor.fluidpipes.block.util.DripActionUtil.dripDown;
import static eu.pintergabor.fluidpipes.block.util.FluidUtil.clog;
import static eu.pintergabor.fluidpipes.block.util.TickUtil.TickPos;
import static eu.pintergabor.fluidpipes.registry.ModProperties.FLUID;

import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class FluidFittingEntity extends BaseFittingEntity {

    public FluidFittingEntity(
        BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.FLUID_FITTING_ENTITY, blockPos, blockState);
    }

    /**
     * Pull fluid from any pipe pointing to this fitting.
     *
     * @return true if the state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    protected static boolean pull(
        World world, BlockPos pos, BlockState state, FluidFittingEntity entity) {
        boolean changed = false;
        // This block.
        PipeFluid pipeFluid = state.get(FLUID);
        FluidFitting block = (FluidFitting) state.getBlock();
        boolean canCarryWater = block.canCarryWater();
        boolean canCarryLava = block.canCarryLava();
        // Find a pipe pointing to this pipe from any side.
        boolean sideSourcing = false;
        PipeFluid sideFluid = sideSourceFluid(
            world, pos,
            canCarryWater, canCarryLava);
        if (sideFluid != PipeFluid.NONE) {
            // Water or lava is coming from the side.
            sideSourcing = true;
            if (pipeFluid != sideFluid) {
                pipeFluid = sideFluid;
                changed = true;
            }
        }
        if (!sideSourcing && pipeFluid != PipeFluid.NONE) {
            // No source from any side.
            pipeFluid = PipeFluid.NONE;
            changed = true;
        }
        if (changed) {
            world.setBlockState(pos, state.with(FLUID, pipeFluid));
        }
        return changed;
    }

    /**
     * Called at every tick on the server.
     */
    public static void serverTick(
        World world, BlockPos pos, BlockState state, FluidFittingEntity entity) {
        TickPos tickPos = getTickPos(world, state);
        if (tickPos == TickPos.START) {
            // Pull fluid.
            pull(world, pos, state, entity);
            // Clogging.
            clog(world, pos, state);
        }
        if (tickPos == TickPos.MIDDLE) {
            boolean powered = state.get(Properties.POWERED, false);
            if (!powered) {
                // Drip.
                dripDown((ServerWorld) world, pos, state);
                // Break.
                breakFire((ServerWorld) world, pos, state);
            }
        }
    }
}
