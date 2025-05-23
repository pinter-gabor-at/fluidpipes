package eu.pintergabor.fluidpipes.datagen.recipe;

import java.util.stream.IntStream;

import eu.pintergabor.fluidpipes.registry.ModBlocks;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;


public final class ModRecipeGenerator extends RecipeProvider {

	public ModRecipeGenerator(HolderLookup.Provider registries, RecipeOutput output) {
		super(registries, output);
	}

	/**
	 * Create a pipe recipe.
	 */
	@SuppressWarnings("SameParameterValue")
	private ShapedRecipeBuilder createPipeRecipe(
		RecipeCategory recipeCategory, ItemLike input, ItemLike output, int outputCount
	) {
		return shaped(recipeCategory, output, outputCount)
			.define('#', input)
			.pattern("###")
			.pattern("   ")
			.pattern("###")
			.unlockedBy(getHasName(input), has(input));
	}

	/**
	 * Create a fitting recipe.
	 */
	@SuppressWarnings("SameParameterValue")
	private ShapedRecipeBuilder createFittingRecipe(
		RecipeCategory recipeCategory, ItemLike input, ItemLike output, int outputCount
	) {
		return shaped(recipeCategory, output, outputCount)
			.define('#', input)
			.pattern("###")
			.pattern("# #")
			.pattern("###")
			.unlockedBy(getHasName(input), has(input));
	}

	/**
	 * Create wooden pipe recipes.
	 */
	private void createWoodenPipeRecipes() {
		final ItemLike[] WOODEN_PLANKS = {
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
		IntStream.range(0, ModBlocks.WOODEN_PIPES.length).forEach(i ->
			createPipeRecipe(RecipeCategory.MISC, WOODEN_PLANKS[i],
			ModBlocks.WOODEN_PIPES[i], 6)
			.save(output));
	}

	/**
	 * Create wooden fitting recipes.
	 */
	private void createWoodenFittingRecipes() {
		IntStream.range(0, ModBlocks.WOODEN_PIPES.length).forEach(i ->
			createFittingRecipe(RecipeCategory.MISC, ModBlocks.WOODEN_PIPES[i],
				ModBlocks.WOODEN_FITTINGS[i], 8)
				.save(output));
	}

	/**
	 * Create stone pipe recipes.
	 */
	private void createStonePipeRecipes() {
		final ItemLike[] STONES1 = {
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
		final ItemLike[] STONES2 = {
			Items.COBBLESTONE,
			Items.COBBLED_DEEPSLATE,
		};
		IntStream.range(0, STONES1.length).forEach(i ->
			createPipeRecipe(RecipeCategory.MISC, STONES1[i],
				ModBlocks.STONE_PIPES[i], 6)
				.save(output));
		IntStream.range(0, STONES2.length).forEach(i ->
			createPipeRecipe(RecipeCategory.MISC, STONES2[i],
				ModBlocks.STONE_PIPES[i], 6)
				.save(output, RecipeBuilder.
					getDefaultRecipeId(ModBlocks.STONE_PIPES[i]) + "2"));
	}

	/**
	 * Create stone fitting recipes.
	 */
	private void createStoneFittingRecipes() {
		IntStream.range(0, ModBlocks.STONE_PIPES.length).forEach(i ->
			createFittingRecipe(RecipeCategory.MISC, ModBlocks.STONE_PIPES[i],
				ModBlocks.STONE_FITTINGS[i], 8)
				.save(output));
	}

	/**
	 * Generate all recipes.
	 */
	@Override
	public void buildRecipes() {
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
