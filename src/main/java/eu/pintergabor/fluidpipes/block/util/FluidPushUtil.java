package eu.pintergabor.fluidpipes.block.util;

import static eu.pintergabor.fluidpipes.block.util.DripActionUtil.dripLavaOnBlock;
import static eu.pintergabor.fluidpipes.block.util.DripActionUtil.dripWaterOnBlock;
import static net.minecraft.block.entity.AbstractFurnaceBlockEntity.FUEL_SLOT_INDEX;
import static net.minecraft.block.entity.HopperBlockEntity.getInventoryAt;
import static net.minecraft.state.property.Properties.FACING;

import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModProperties;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;


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
     * @param world The world.
     * @param pos   Position of the block in front of the pipe.
     * @param state BlockState of the block in front of the pipe.
     * @return true if state changed.
     */
    @SuppressWarnings("unused")
    private static boolean fuelFurnace(
        ServerWorld world, BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        if (block instanceof AbstractFurnaceBlock) {
            // If it is a furnace ...
            Inventory inventory = getInventoryAt(world, pos);
            if (inventory != null) {
                ItemStack stack = inventory.getStack(FUEL_SLOT_INDEX);
                if (stack.isOf(Items.BUCKET)) {
                    // ... and has an empty bucket in its fuel slot,
                    // then replace the emtpy bucket with a lava bucket.
                    inventory.setStack(FUEL_SLOT_INDEX,
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
        ServerWorld world, BlockPos pos, BlockState state) {
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
        ServerWorld world, BlockPos pos, BlockState state) {
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
