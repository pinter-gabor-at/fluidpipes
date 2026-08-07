package eu.pintergabor.fluidpipes.datagen.recipe;

import java.util.concurrent.CompletableFuture;

import eu.pintergabor.fluidpipes.Global;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;


/**
 * Boilerplate for recipe generation.
 */
public final class ModRecipeRunner extends RecipeProvider.Runner {

	public ModRecipeRunner(
		final @NonNull PackOutput output,
		final @NonNull CompletableFuture<HolderLookup.Provider> registries
	) {
		super(output, registries);
	}

	@Contract("_, _ -> new")
	@Override
	protected @NonNull RecipeProvider createRecipeProvider(
		final HolderLookup.@NonNull Provider registryLookup,
		final @NonNull RecipeOutput output
	) {
		return new ModRecipeGenerator(registryLookup, output);
	}

	@Contract(pure = true)
	@Override
	public @NonNull String getName() {
		return Global.MODID + " recipes";
	}
}
