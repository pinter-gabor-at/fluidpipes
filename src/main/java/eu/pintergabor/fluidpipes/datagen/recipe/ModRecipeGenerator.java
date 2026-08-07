package eu.pintergabor.fluidpipes.datagen.recipe;

import java.util.stream.IntStream;

import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.registry.ModFluidBlocks;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
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
		IntStream.range(0, WOODEN_PLANKS.length).forEach(i ->
			createPipeRecipe(WOODEN_PLANKS[i],
				ModFluidBlocks.WOODEN_PIPES[i].item.get(), 6)
				.save(output));
	}

	/**
	 * Create wooden fitting recipes.
	 */
	private void registerWoodenFittingRecipes() {
		IntStream.range(0, ModFluidBlocks.WOODEN_PIPES.length).forEach(i ->
			createFittingRecipe(ModFluidBlocks.WOODEN_PIPES[i].item.get(),
				ModFluidBlocks.WOODEN_FITTINGS[i].item.get(), 8)
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
				ModFluidBlocks.STONE_PIPES[i].item.get(), 6));
		IntStream.range(0, STONES2.length).forEach(i ->
			registerPipeRecipe(STONES2[i],
				ModFluidBlocks.STONE_PIPES[i].item.get(), 6, "2"));
	}

	/**
	 * Create stone fitting recipes.
	 */
	private void registerStoneFittingRecipes() {
		IntStream.range(0, ModFluidBlocks.STONE_PIPES.length).forEach(i ->
			registerFittingRecipe(ModFluidBlocks.STONE_PIPES[i].item.get(),
				ModFluidBlocks.STONE_FITTINGS[i].item.get(), 8));
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
