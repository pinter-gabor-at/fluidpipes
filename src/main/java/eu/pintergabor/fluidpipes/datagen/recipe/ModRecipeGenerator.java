package eu.pintergabor.fluidpipes.datagen.recipe;

import eu.pintergabor.fluidpipes.registry.ModBlocks;

import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;


class ModRecipeGenerator extends RecipeGenerator {

    public ModRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        super(registryLookup, exporter);
    }

    /**
     * Create a pipe recipe.
     */
    @SuppressWarnings("SameParameterValue")
    private void createPipeRecipe(
        RecipeCategory recipeCategory, ItemConvertible input, ItemConvertible output, int outputCount) {
        createShaped(recipeCategory, output, outputCount)
            .input('#', input)
            .pattern("###")
            .pattern("   ")
            .pattern("###")
            .criterion(hasItem(input), conditionsFromItem(input))
            .offerTo(exporter);
    }

    /**
     * Create a fitting recipe.
     */
    @SuppressWarnings("SameParameterValue")
    private void createFittingRecipe(
        RecipeCategory recipeCategory, ItemConvertible input, ItemConvertible output, int outputCount) {
        createShaped(recipeCategory, output, outputCount)
            .input('#', input)
            .pattern("###")
            .pattern("# #")
            .pattern("###")
            .criterion(hasItem(input), conditionsFromItem(input))
            .offerTo(exporter);
    }

    /**
     * Create wooden pipe recipes.
     */
    private void createWoodenPipeRecipes() {
        final ItemConvertible[] WOODEN_PLANKS = {
            Items.OAK_PLANKS,
            Items.SPRUCE_PLANKS,
            Items.BIRCH_PLANKS,
            Items.JUNGLE_PLANKS,
            Items.ACACIA_PLANKS,
            Items.CHERRY_PLANKS,
            Items.DARK_OAK_PLANKS,
            Items.PALE_OAK_PLANKS,
            Items.MANGROVE_PLANKS,
            Items.BAMBOO_PLANKS,
        };
        for (int i = 0; i < ModBlocks.WOODEN_PIPES.length; i++) {
            createPipeRecipe(RecipeCategory.MISC, WOODEN_PLANKS[i],
                ModBlocks.WOODEN_PIPES[i], 6);
        }
    }

    /**
     * Create wooden fitting recipes.
     */
    private void createWoodenFittingRecipes() {
        for (int i = 0; i < ModBlocks.WOODEN_PIPES.length; i++) {
            createFittingRecipe(RecipeCategory.MISC, ModBlocks.WOODEN_PIPES[i],
                ModBlocks.WOODEN_FITTINGS[i], 8);
        }
    }

    /**
     * Generate all recipes.
     */
    @Override
    public void generate() {
        // Wooden pipes.
        createWoodenPipeRecipes();
        // Wooden fittings.
        createWoodenFittingRecipes();
    }
}
