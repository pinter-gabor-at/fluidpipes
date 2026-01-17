package eu.pintergabor.fluidpipesmini.datagen.recipe;

import java.util.stream.IntStream;

import eu.pintergabor.fluidpipesmini.registry.ModFluidBlocks;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;


/**
 * Recipe generator.
 */
public final class ModRecipeGenerator extends RecipeProvider {

	public ModRecipeGenerator(HolderLookup.Provider registries, RecipeOutput output) {
		super(registries, output);
	}

	/**
	 * Create a pipe recipe.
	 */
	@SuppressWarnings("SameParameterValue")
	private @NonNull ShapedRecipeBuilder createPipeRecipe(
		@NonNull ItemLike input, @NonNull ItemLike result, int resultCount
	) {
		return shaped(RecipeCategory.MISC, result, resultCount)
			.define('#', input)
			.pattern("###")
			.pattern("   ")
			.pattern("###")
			.unlockedBy(getHasName(input), has(input));
	}

	/**
	 * Create a pipe recipe.
	 */
	@SuppressWarnings("SameParameterValue")
	private @NonNull ShapedRecipeBuilder createPipeRecipe(
		@NonNull TagKey<Item> input, @NonNull ItemLike result, int resultCount
	) {
		return shaped(RecipeCategory.MISC, result, resultCount)
			.define('#', input)
			.pattern("###")
			.pattern("   ")
			.pattern("###")
			.unlockedBy("has_" + input.location(), has(input));
	}

	/**
	 * Create a fitting recipe.
	 */
	@SuppressWarnings("SameParameterValue")
	private @NonNull ShapedRecipeBuilder createFittingRecipe(
		@NonNull ItemLike input, @NonNull ItemLike result, int resultCount
	) {
		return shaped(RecipeCategory.MISC, result, resultCount)
			.define('#', input)
			.pattern("###")
			.pattern("# #")
			.pattern("###")
			.unlockedBy(getHasName(input), has(input));
	}

	/**
	 * Create wooden pipe recipes.
	 */
	private void registerWoodenPipeRecipes() {
		createPipeRecipe(ItemTags.PLANKS,
			ModFluidBlocks.WOOD_PIPE, 6)
			.save(output);
		createPipeRecipe(Items.BAMBOO_PLANKS,
			ModFluidBlocks.BAMBOO_PIPE, 6)
			.save(output);
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
		createPipeRecipe(Items.STONE,
			ModFluidBlocks.STONE_PIPE, 6)
			.save(output);
		createPipeRecipe(Items.STONE,
			ModFluidBlocks.STONE_PIPE, 6)
			.save(output, RecipeBuilder.getDefaultRecipeId(ModFluidBlocks.STONE_PIPE) + "2");
		createPipeRecipe(Items.OBSIDIAN,
			ModFluidBlocks.OBSIDIAN_PIPE, 6)
			.save(output);
	}

	/**
	 * Create stone fitting recipes.
	 */
	private void registerStoneFittingRecipes() {
		IntStream.range(0, ModFluidBlocks.STONE_PIPES.length).forEach(i ->
			createFittingRecipe(ModFluidBlocks.STONE_PIPES[i],
				ModFluidBlocks.STONE_FITTINGS[i], 8)
				.save(output));
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
