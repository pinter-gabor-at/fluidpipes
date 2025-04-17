package eu.pintergabor.fluidpipes.block.util;

import static eu.pintergabor.fluidpipes.block.BasePipe.FACING;
import static eu.pintergabor.fluidpipes.block.util.DripActionUtil.dripLavaOnBlock;
import static eu.pintergabor.fluidpipes.block.util.DripActionUtil.dripWaterOnBlock;
import static net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity.SLOT_FUEL;
import static net.minecraft.world.level.block.entity.HopperBlockEntity.getContainerAt;

import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;

import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;


/**
 * More utilities for fluid pipes.
 * <p>
 * Push.
 */
public final class FluidPushUtil {

    private FluidPushUtil() {
        // Static class.
    }

    /**
     * Fuel a furnace.
     *
     * @param level The world.
     * @param pos   Position of the block in front of the pipe.
     * @param state BlockState of the block in front of the pipe.
     * @return true if state changed.
     */
    @SuppressWarnings("unused")
    private static boolean fuelFurnace(
        ServerLevel level, BlockPos pos, BlockState state
    ) {
        Block block = state.getBlock();
        if (block instanceof AbstractFurnaceBlock) {
            // If it is a furnace ...
            Container inventory = getContainerAt(level, pos);
            if (inventory != null) {
                ItemStack stack = inventory.getItem(SLOT_FUEL);
                if (stack.is(Items.BUCKET)) {
                    // ... and has an empty bucket in its fuel slot,
                    // then replace the emtpy bucket with a lava bucket.
                    inventory.setItem(SLOT_FUEL,
                        new ItemStack(Items.LAVA_BUCKET));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Push water into any block that can accept it.
     *
     * @param world The world.
     * @param pos   Position of the block in front of the pipe.
     * @param state BlockState of the block in front of the pipe.
     * @return true if state changed.
     */
    public static boolean pushWaterToBlock(
        ServerLevel world, BlockPos pos, BlockState state) {
        // Same as drip.
        return dripWaterOnBlock(world, pos, state);
    }

    /**
     * Push lava into any block that can accept it.
     *
     * @param world The world.
     * @param pos   Position of the block in front of the pipe.
     * @param state BlockState of the block in front of the pipe.
     * @return true if state changed.
     */
    public static boolean pushLavaToBlock(
        ServerLevel world, BlockPos pos, BlockState state) {
        // Same as drip + Fuel a furnace.
        return dripLavaOnBlock(world, pos, state) ||
            fuelFurnace(world, pos, state);
    }

    /**
     * Push fluid into the block at the front of the pipe.
     *
     * @return true if the state is changed.
     */
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    public static boolean push(
        ServerLevel world, BlockPos pos, BlockState state) {
        boolean changed = false;
        // This block.
        Direction facing = state.getValue(FACING);
        Direction opposite = facing.getOpposite();
        PipeFluid pipeFluid = state.getValue(ModProperties.FLUID);
        FluidPipe block = (FluidPipe) state.getBlock();
        // The block in front of this.
        BlockPos frontPos = pos.relative(facing);
        BlockState frontState = world.getBlockState(frontPos);
        Block frontBlock = frontState.getBlock();
        // Logic.
        if (pipeFluid != PipeFluid.NONE) {
            float rnd = world.random.nextFloat();
            boolean waterFilling = rnd < block.getWaterFillingProbability();
            boolean lavaFilling = rnd < block.getLavaFillingProbability();
            // Try to push into the block in front of the pipe.
            if (lavaFilling && pipeFluid == PipeFluid.LAVA) {
                return pushLavaToBlock(world, frontPos, frontState);
            }
            if (waterFilling && pipeFluid == PipeFluid.WATER) {
                return pushWaterToBlock(world, frontPos, frontState);
            }
        }
        return changed;
    }
}
