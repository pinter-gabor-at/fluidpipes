package eu.pintergabor.fluidpipes.registry;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroups;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;


public class ModCreativeInventorySorting {

    public static void init() {
        // Creative tabs, functional item group.
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(
            entries -> {
                // Add wooden pipes and fittings after the cauldron.
                entries.addAfter(Blocks.CAULDRON,
                    ModBlocks.WOODEN_FITTINGS);
                entries.addAfter(Blocks.CAULDRON,
                    ModBlocks.WOODEN_PIPES);
            });
    }
}
