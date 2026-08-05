package eu.pintergabor.fluidpipesmini.datagen;

import eu.pintergabor.fluidpipesmini.datagen.loot.ModBlockLootProvider;
import eu.pintergabor.fluidpipesmini.datagen.model.ModModelProvider;
import eu.pintergabor.fluidpipesmini.datagen.recipe.ModRecipeRunner;
import eu.pintergabor.fluidpipesmini.datagen.tag.ModBlockTagProvider;
import eu.pintergabor.fluidpipesmini.datagen.tag.ModItemTagProvider;
import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;


/**
 * Data generator main entry point.
 */
public final class ModDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(
		final @NonNull FabricDataGenerator dataGenerator
	) {
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();
		// Assets.
		pack.addProvider(ModModelProvider::new);
		// Data.
		pack.addProvider(ModBlockLootProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModRecipeRunner::new);
	}
}
