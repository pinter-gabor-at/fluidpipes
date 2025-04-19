package eu.pintergabor.fluidpipes.registry;

import com.mojang.serialization.MapCodec;

import eu.pintergabor.fluidpipes.Global;

import eu.pintergabor.fluidpipes.ModCommon;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModRegistries {
	// Registries.
	public static final DeferredRegister.Items ITEMS =
		DeferredRegister.createItems(Global.MODID);
	public static final DeferredRegister<MapCodec<? extends Block>> BLOCK_TYPES =
		DeferredRegister.create(Registries.BLOCK_TYPE, Global.MODID);
	public static final DeferredRegister.Blocks BLOCKS =
		DeferredRegister.createBlocks(Global.MODID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
		DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Global.MODID);
	public static final DeferredRegister<ResourceLocation> STATS =
		DeferredRegister.create(Registries.CUSTOM_STAT, Global.MODID);

	/**
	 * Called from {@link ModCommon}.
	 */
	public static void init(IEventBus modEventBus) {
		ModProperties.init();
		ModBlocks.init();
		ModBlocksRegister.init();
		ModBlockEntities.init();
		ModSoundEvents.init();
		// Items and blocks.
		ModBlocks.init();
		BLOCK_TYPES.register(modEventBus);
		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
		BLOCK_ENTITY_TYPES.register(modEventBus);
		// Recipes.
//		CrusherRecipe.init();
//		CompressorRecipe.init();
//		RECIPE_BOOK_CATEGORIES.register(modEventBus);
//		RECIPE_TYPES.register(modEventBus);
//		RECIPE_SERIALIZERS.register(modEventBus);
		// Statistics.
		ModStats.init();
		STATS.register(modEventBus);
	}
}
