package eu.pintergabor.fluidpipes.datagen;

import eu.pintergabor.fluidpipes.datagen.loot.ModBlockLootProvider;
import eu.pintergabor.fluidpipes.datagen.model.ModModelProvider;
import eu.pintergabor.fluidpipes.datagen.recipe.ModRecipeRunner;
import eu.pintergabor.fluidpipes.datagen.tag.ModBlockTagProvider;
import eu.pintergabor.fluidpipes.datagen.tag.ModItemTagProvider;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;


@OnlyIn(Dist.CLIENT)
public final class DataGen {

	public DataGen() {
		// Static class.
	}

	public static void listener(GatherDataEvent.Client event) {
		// Assets.
		event.createProvider(ModModelProvider::new);
		// Data.
		event.createBlockAndItemTags(ModBlockTagProvider::new, ModItemTagProvider::new);
		event.createProvider(ModRecipeRunner::new);
		event.createProvider((output, lookupProvider) ->
			new LootTableProvider(output, Set.of(), List.of(
				new LootTableProvider.SubProviderEntry(
					ModBlockLootProvider::new,
					LootContextParamSets.BLOCK)), lookupProvider));
	}
}
