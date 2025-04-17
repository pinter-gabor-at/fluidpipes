package eu.pintergabor.fluidpipes.datagen;

import eu.pintergabor.fluidpipes.datagen.loot.ModBlockLootProvider;
import eu.pintergabor.fluidpipes.datagen.model.ModModelProvider;
import eu.pintergabor.fluidpipes.datagen.recipe.ModRecipeRunner;
import eu.pintergabor.fluidpipes.datagen.tag.ModBlockTagProvider;
import eu.pintergabor.fluidpipes.datagen.tag.ModItemTagProvider;
import org.jetbrains.annotations.NotNull;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class ModDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(@NotNull FabricDataGenerator dataGenerator) {
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
