package eu.pintergabor.fluidpipes.block.entity;

import static net.minecraft.block.entity.AbstractFurnaceBlockEntity.FUEL_SLOT_INDEX;
import static net.minecraft.block.entity.HopperBlockEntity.getInventoryAt;

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;


/**
 * More utilities for fluid pipes.
 * <p>
 * Push.
 */
public sealed abstract class FluidPipeUtil1 extends FluidPipeUtil0
    permits FluidPipeUtil {

    /**
     * Fuel a furnace.
     *
     * @param world       The world.
     * @param pos         Position of the block in front of the pipe.
     * @param state       BlockState of the block in front of the pipe.
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
        return dripLavaOnBlock(world, pos, state) &&
            fuelFurnace(world, pos, state);
    }
}
