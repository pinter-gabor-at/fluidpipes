package eu.pintergabor.fluidpipes.block.entity;

import static eu.pintergabor.fluidpipes.block.base.BaseBlock.getTickPos;
import static eu.pintergabor.fluidpipes.block.entity.base.FluidPipeUtil.*;
import static eu.pintergabor.fluidpipes.block.entity.base.TickUtil.TickPos;

import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.entity.base.BasePipeEntity;
import eu.pintergabor.fluidpipes.block.entity.base.FluidUtil;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModBlockEntities;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
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
        Direction facing = state.get(Properties.FACING);
        Direction opposite = facing.getOpposite();
        BlockState backState = world.getBlockState(pos.offset(opposite));
        PipeFluid pipeFluid = state.get(ModProperties.FLUID);
        FluidPipe block = (FluidPipe) state.getBlock();
        boolean canCarryWater = block.canCarryWater();
        boolean canCarryLava = block.canCarryLava();
        // Logic.
        if (isWaterSource(backState)) {
            // If a water source from the back is supplying water.
            if (canCarryWater && pipeFluid != PipeFluid.WATER) {
                pipeFluid = PipeFluid.WATER;
                changed = true;
            }
        } else if (isLavaSource(backState)) {
            // If a lava source from the back is supplying lava.
            if (canCarryLava && pipeFluid != PipeFluid.LAVA) {
                pipeFluid = PipeFluid.LAVA;
                changed = true;
            }
        } else {
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
            if (!sideSourcing && pipeFluid != PipeFluid.NONE) {
                // No source from any side.
                pipeFluid = PipeFluid.NONE;
                changed = true;
            }
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
        World world, BlockPos pos, BlockState state, FluidPipeEntity entity) {
        boolean changed = false;
        // This block.
        Direction facing = state.get(Properties.FACING);
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
            // Check the block in front of the pipe.
            if (frontBlock == Blocks.CAULDRON) {
                // Cauldron.
                if (pushToCauldron(
                    world, frontPos, frontState,
                    pipeFluid, waterFilling, lavaFilling)) {
                    changed = true;
                }
            } else if (frontBlock == Blocks.WATER_CAULDRON &&
                frontState.get(Properties.LEVEL_3) < 3) {
                // Partially filled water cauldron.
                if (pushToWaterCauldron(
                    world, frontPos, frontState,
                    pipeFluid, waterFilling)) {
                    changed = true;
                }
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
        World world, BlockPos pos, BlockState state, FluidPipeEntity entity) {
        boolean changed = false;
        // This block.
        Direction facing = state.get(Properties.FACING);
        boolean outflow = state.get(ModProperties.OUTFLOW);
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
            world.setBlockState(pos, state.with(ModProperties.OUTFLOW, outflow));
        }
        return changed;
    }

    /**
     * Called at every tick on the server.
     */
    public static void serverTick(
        World world, BlockPos pos, BlockState state, FluidPipeEntity entity) {
        FluidPipe block = (FluidPipe) state.getBlock();
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
        if (tickPos == TickPos.MIDDLE) {
            // Push fluid into blocks that are not capable of pulling it.
            push(world, pos, state, entity);
            // Dispense fluid.
            dispense(world, pos, state, entity);
        }
    }
}
