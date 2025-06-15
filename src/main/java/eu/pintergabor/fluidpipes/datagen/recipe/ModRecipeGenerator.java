package eu.pintergabor.fluidpipes.datagen.recipe;

import java.util.stream.IntStream;

import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;

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
		ItemLike input, ItemLike result, int resultCount
	) {
		return shaped(RecipeCategory.MISC, result, resultCount)
			.define('#', input)
			.pattern("###")
			.pattern("   ")
			.pattern("###")
			.unlockedBy(getHasName(input), has(input));
	}

	/**
	 * Create and register a pipe recipe.
	 */
	@SuppressWarnings("SameParameterValue")
	private void registerPipeRecipe(
		ItemLike input, ItemLike result, int resultCount
	) {
		createPipeRecipe(input, result, resultCount)
			.save(output);
	}

	/**
	 * Create and register a pipe recipe.
	 */
	@SuppressWarnings("SameParameterValue")
	private void registerPipeRecipe(
		ItemLike input, ItemLike result, int resultCount,
		String suffix
	) {
		createPipeRecipe(input, result, resultCount)
			.save(output, RecipeBuilder.
				getDefaultRecipeId(result) + suffix);
	}

	/**
	 * Create a fitting recipe.
	 */
	@SuppressWarnings("SameParameterValue")
	private ShapedRecipeBuilder createFittingRecipe(
		ItemLike input, ItemLike result, int resultCount
	) {
		return shaped(RecipeCategory.MISC, result, resultCount)
			.define('#', input)
			.pattern("###")
			.pattern("# #")
			.pattern("###")
			.unlockedBy(getHasName(input), has(input));
	}

	/**
	 * Create and register a fitting recipe.
	 */
	@SuppressWarnings("SameParameterValue")
	private void registerFittingRecipe(
		ItemLike input, ItemLike result, int resultCount
	) {
		createFittingRecipe(input, result, resultCount)
			.save(output);
	}

	/**
	 * Create wooden pipe recipes.
	 */
	private void registerWoodenPipeRecipes() {
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
		IntStream.range(0, ModFluidBlocks.WOODEN_PIPES.length).forEach(i ->
			createPipeRecipe(WOODEN_PLANKS[i],
				ModFluidBlocks.WOODEN_PIPES[i], 6)
				.save(output));
	}

	/**
	 * Create wooden fitting recipes.
	 */
	private void registerWoodenFittingRecipes() {
		IntStream.range(0, ModFluidBlocks.WOODEN_PIPES.length).forEach(i ->
			createFittingRecipe(ModFluidBlocks.WOODEN_PIPES[i],
				ModFluidBlocks.WOODEN_FITTINGS[i], 8)
				.save(output));
	}

	/**
	 * Create stone pipe recipes.
	 */
	private void registerStonePipeRecipes() {
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
			registerPipeRecipe(STONES1[i],
				ModFluidBlocks.STONE_PIPES[i], 6));
		IntStream.range(0, STONES2.length).forEach(i ->
			registerPipeRecipe(STONES2[i],
				ModFluidBlocks.STONE_PIPES[i], 6, "2"));
	}

	/**
	 * Create stone fitting recipes.
	 */
	private void registerStoneFittingRecipes() {
		IntStream.range(0, ModFluidBlocks.STONE_PIPES.length).forEach(i ->
			registerFittingRecipe(ModFluidBlocks.STONE_PIPES[i],
				ModFluidBlocks.STONE_FITTINGS[i], 8));
	}

	/**
	 * Generate all recipes.
	 */
	@Override
	public void buildRecipes() {
		// Wooden pipes.
		registerWoodenPipeRecipes();
		// Wooden fittings.
		registerWoodenFittingRecipes();
		// Stone pipes.
		registerStonePipeRecipes();
		// Stone fittings.
		registerStoneFittingRecipes();
	}
}
