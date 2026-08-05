package eu.pintergabor.fluidpipesmini.datagen.recipe;

import java.util.stream.IntStream;

import eu.pintergabor.fluidpipesmini.Global;
import eu.pintergabor.fluidpipesmini.registry.ModFluidBlocks;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.HolderLookup;
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

	public ModRecipeGenerator(
		final HolderLookup.@NonNull Provider registries,
		final @NonNull RecipeOutput output
	) {
		super(registries, output);
	}

	/**
	 * Create a pipe recipe.
	 */
	@SuppressWarnings("SameParameterValue")
	private @NonNull ShapedRecipeBuilder createPipeRecipe(
		final @NonNull ItemLike input,
		final @NonNull ItemLike result,
		final int resultCount
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
		final @NonNull TagKey<Item> input,
		final @NonNull ItemLike result,
		final int resultCount
	) {
		return shaped(RecipeCategory.MISC, result, resultCount)
			.define('#', input)
			.pattern("###")
			.pattern("   ")
			.pattern("###")
			.unlockedBy("has_" + input.location(), has(input));
	}

	/**
	 * Create and register a pipe recipe.
	 */
	@SuppressWarnings("SameParameterValue")
	private void registerPipeRecipe(
		final @NonNull ItemLike input,
		final @NonNull ItemLike result,
		final int resultCount
	) {
		final ShapedRecipeBuilder builder = createPipeRecipe(input, result, resultCount);
		builder.save(output);
	}

	/**
	 * Create and register a pipe recipe.
	 */
	@SuppressWarnings("SameParameterValue")
	private void registerPipeRecipe(
		final @NonNull ItemLike input,
		final @NonNull ItemLike result,
		final int resultCount,
		final @NonNull String suffix
	) {
		final ShapedRecipeBuilder builder = createPipeRecipe(input, result, resultCount);
		builder.save(output, Global.modName(getSimpleRecipeName(result.asItem()) + suffix));
	}

	/**
	 * Create a fitting recipe.
	 */
	@SuppressWarnings("SameParameterValue")
	private @NonNull ShapedRecipeBuilder createFittingRecipe(
		final @NonNull ItemLike input,
		final @NonNull ItemLike result,
		final int resultCount
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
		final @NonNull ItemLike input,
		final @NonNull ItemLike result,
		final int resultCount
	) {
		final ShapedRecipeBuilder builder = createFittingRecipe(input, result, resultCount);
		builder.save(output);
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
		registerPipeRecipe(Items.STONE,
			ModFluidBlocks.STONE_PIPE, 6);
		registerPipeRecipe(Items.STONE,
			ModFluidBlocks.STONE_PIPE, 6, "2");
		registerPipeRecipe(Items.OBSIDIAN,
			ModFluidBlocks.OBSIDIAN_PIPE, 6);
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
