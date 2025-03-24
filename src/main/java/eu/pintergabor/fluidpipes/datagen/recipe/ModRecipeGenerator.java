package eu.pintergabor.fluidpipes.datagen.recipe;

import eu.pintergabor.fluidpipes.registry.ModBlocks;

import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;


public final class ModRecipeGenerator extends RecipeGenerator {

    public ModRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        super(registryLookup, exporter);
    }

    /**
     * Create a pipe recipe.
     */
    @SuppressWarnings("SameParameterValue")
    private ShapedRecipeJsonBuilder createPipeRecipe(
        RecipeCategory recipeCategory, ItemConvertible input, ItemConvertible output, int outputCount) {
        return createShaped(recipeCategory, output, outputCount)
            .input('#', input)
            .pattern("###")
            .pattern("   ")
            .pattern("###")
            .criterion(hasItem(input), conditionsFromItem(input));
    }

    /**
     * Create a fitting recipe.
     */
    @SuppressWarnings("SameParameterValue")
    private ShapedRecipeJsonBuilder createFittingRecipe(
        RecipeCategory recipeCategory, ItemConvertible input, ItemConvertible output, int outputCount) {
        return createShaped(recipeCategory, output, outputCount)
            .input('#', input)
            .pattern("###")
            .pattern("# #")
            .pattern("###")
            .criterion(hasItem(input), conditionsFromItem(input));
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
                ModBlocks.WOODEN_PIPES[i], 6)
                .offerTo(exporter);
        }
    }

    /**
     * Create wooden fitting recipes.
     */
    private void createWoodenFittingRecipes() {
        for (int i = 0; i < ModBlocks.WOODEN_PIPES.length; i++) {
            createFittingRecipe(RecipeCategory.MISC, ModBlocks.WOODEN_PIPES[i],
                ModBlocks.WOODEN_FITTINGS[i], 8)
                .offerTo(exporter);
        }
    }

    /**
     * Create stone pipe recipes.
     */
    private void createStonePipeRecipes() {
        final ItemConvertible[] STONES1 = {
            Items.STONE,
            Items.DEEPSLATE,
            Items.ANDESITE,
            Items.DIORITE,
            Items.GRANITE,
            Items.BASALT,
            Items.SANDSTONE,
            Items.TUFF,
            Items.OBSIDIAN,
            Items.NETHERRACK,
        };
        final ItemConvertible[] STONES2 = {
            Items.COBBLESTONE,
            Items.COBBLED_DEEPSLATE,
        };
        for (int i = 0; i < STONES1.length; i++) {
            createPipeRecipe(RecipeCategory.MISC, STONES1[i],
                ModBlocks.STONE_PIPES[i], 6)
                .offerTo(exporter);
        }
        for (int i = 0; i < STONES2.length; i++) {
            createPipeRecipe(RecipeCategory.MISC, STONES2[i],
                ModBlocks.STONE_PIPES[i], 6)
                .offerTo(exporter, getItemPath(ModBlocks.STONE_PIPES[i]) + "2");
        }
    }

    /**
     * Create stone fitting recipes.
     */
    private void createStoneFittingRecipes() {
        for (int i = 0; i < ModBlocks.STONE_PIPES.length; i++) {
            createFittingRecipe(RecipeCategory.MISC, ModBlocks.STONE_PIPES[i],
                ModBlocks.STONE_FITTINGS[i], 8)
                .offerTo(exporter);
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
        // Stone pipes.
        createStonePipeRecipes();
        // Stone fittings.
        createStoneFittingRecipes();
    }
}
