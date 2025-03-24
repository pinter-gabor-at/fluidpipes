package eu.pintergabor.fluidpipes.block.entity;

import static eu.pintergabor.fluidpipes.block.BaseBlock.getTickPos;
import static eu.pintergabor.fluidpipes.block.util.FluidDispenseUtil.*;
import static eu.pintergabor.fluidpipes.block.util.FluidPullUtil.backSourceFluid;
import static eu.pintergabor.fluidpipes.block.util.FluidPullUtil.sideSourceFluid;
import static eu.pintergabor.fluidpipes.block.util.FluidPushUtil.pushLavaToBlock;
import static eu.pintergabor.fluidpipes.block.util.FluidPushUtil.pushWaterToBlock;
import static eu.pintergabor.fluidpipes.block.util.DripActionUtil.dripDown;
import static eu.pintergabor.fluidpipes.block.util.FluidUtil.clog;
import static eu.pintergabor.fluidpipes.block.util.TickUtil.TickPos;
import static eu.pintergabor.fluidpipes.registry.ModProperties.OUTFLOW;
import static net.minecraft.state.property.Properties.FACING;

import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;


public class FluidPipeEntity extends BasePipeEntity {

    public FluidPipeEntity(
        BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLUID_PIPE_ENTITY, pos, state);
    }

    /**
     * Pull fluid from the block at the back of the pipe.
     *
     * @return true if the state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    protected static boolean pull(
        World world, BlockPos pos, BlockState state, FluidPipeEntity entity) {
        boolean changed = false;
        // This block.
        Direction facing = state.get(FACING);
        Direction opposite = facing.getOpposite();
        BlockState backState = world.getBlockState(pos.offset(opposite));
        PipeFluid pipeFluid = state.get(ModProperties.FLUID);
        FluidPipe block = (FluidPipe) state.getBlock();
        boolean canCarryWater = block.canCarryWater();
        boolean canCarryLava = block.canCarryLava();
        // Check the block at the back of the pipe.
        boolean backSourcing = false;
        PipeFluid backFluid = backSourceFluid(
            backState, pipeFluid,
            canCarryWater, canCarryLava);
        if (backFluid != PipeFluid.NONE) {
            // Water or lava is coming from the back.
            backSourcing = true;
            if (pipeFluid != backFluid) {
                pipeFluid = backFluid;
                changed = true;
            }
        }
        // Find a pipe pointing to this pipe from any side.
        boolean sideSourcing = false;
        PipeFluid sideFluid = sideSourceFluid(
            world, pos, facing, opposite,
            canCarryWater, canCarryLava);
        if (sideFluid != PipeFluid.NONE) {
            // Water or lava is coming from the side.
            sideSourcing = true;
            if (pipeFluid != sideFluid) {
                pipeFluid = sideFluid;
                changed = true;
            }
        }
        if (!backSourcing && !sideSourcing && pipeFluid != PipeFluid.NONE) {
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
     * Push fluid into the block at the front of the pipe.
     *
     * @return true if the state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    protected static boolean push(
        ServerWorld world, BlockPos pos, BlockState state) {
        boolean changed = false;
        // This block.
        Direction facing = state.get(FACING);
        Direction opposite = facing.getOpposite();
        PipeFluid pipeFluid = state.get(ModProperties.FLUID);
        FluidPipe block = (FluidPipe) state.getBlock();
        // The block in front of this.
        BlockPos frontPos = pos.offset(facing);
        BlockState frontState = world.getBlockState(frontPos);
        Block frontBlock = frontState.getBlock();
        // Logic.
        if (pipeFluid != PipeFluid.NONE) {
            float rnd = world.random.nextFloat();
            boolean waterFilling = rnd < block.getWaterFillingProbability();
            boolean lavaFilling = rnd < block.getLavaFillingProbability();
            // Try to push into the block in front of the pipe.
            if (waterFilling && pipeFluid == PipeFluid.WATER) {
                return pushWaterToBlock(world, frontPos, frontState);
            }
            if (lavaFilling && pipeFluid == PipeFluid.LAVA) {
                return pushLavaToBlock(world, frontPos, frontState);
            }
        }
        return changed;
    }

    /**
     * Dispense fluid into the world.
     *
     * @return true if state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    protected static boolean dispense(
        World world, BlockPos pos, BlockState state) {
        boolean changed = false;
        // This block.
        Direction facing = state.get(FACING);
        boolean outflow = state.get(OUTFLOW);
        PipeFluid pipeFluid = state.get(ModProperties.FLUID);
        // The block in front of this.
        BlockPos frontPos = pos.offset(facing);
        BlockState frontState = world.getBlockState(frontPos);
        Block frontBlock = frontState.getBlock();
        // Logic.
        if (!outflow) {
            if (startDispense(world, frontPos, frontState, pipeFluid)) {
                // Start dispensing.
                outflow = true;
                changed = true;
            }
        } else {
            if (stopDispense(world, frontPos, frontState, pipeFluid)) {
                // Stop dispensing.
                outflow = false;
                changed = true;
            }
        }
        if (changed) {
            world.setBlockState(pos, state.with(OUTFLOW, outflow));
        }
        return changed;
    }

    /**
     * Called at every tick on the server.
     */
    public static void serverTick(
        World world, BlockPos pos, BlockState state, FluidPipeEntity entity) {
        TickPos tickPos = getTickPos(world, state);
        if (tickPos == TickPos.START) {
            // Pull fluid.
            pull(world, pos, state, entity);
            // Clogging.
            clog(world, pos, state);
        }
        if (tickPos == TickPos.MIDDLE) {
            // Push fluid into blocks that are not capable of pulling it.
            push((ServerWorld) world, pos, state);
            // Dispense fluid.
            dispense(world, pos, state);
            // Drip.
            dripDown((ServerWorld) world, pos, state);
            // Break.
            breakFire((ServerWorld) world, pos, state);
        }
    }
}
