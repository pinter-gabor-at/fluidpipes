package eu.pintergabor.fluidpipes.block.entity;

import static eu.pintergabor.fluidpipes.block.base.BaseBlock.getTickPos;
import static eu.pintergabor.fluidpipes.block.entity.base.FluidFittingUtil.clog;
import static eu.pintergabor.fluidpipes.block.entity.base.FluidFittingUtil.sideSourceFluid;
import static eu.pintergabor.fluidpipes.block.entity.base.TickUtil.TickPos;

import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.entity.base.BaseFittingEntity;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.block.BlockState;
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
        PipeFluid pipeFluid = state.get(ModProperties.FLUID);
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
            world.setBlockState(pos, state.with(ModProperties.FLUID, pipeFluid));
        }
        return changed;
    }

    /**
     * Called at every tick on the server.
     */
    public static void serverTick(
        World world, BlockPos pos, BlockState state, FluidFittingEntity entity) {
        FluidFitting block = (FluidFitting) state.getBlock();
        TickPos tickPos = getTickPos(world, state);
        if (tickPos == TickPos.START) {
            // Pull fluid.
            pull(world, pos, state, entity);
            // Clogging.
            float rnd = world.random.nextFloat();
            if (rnd < block.getCloggingProbability()) {
                clog(world, pos, state);
            }
        }
    }
}
