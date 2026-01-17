package eu.pintergabor.fluidpipes.registry;

import com.mojang.serialization.MapCodec;
import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.ModCommon;
import eu.pintergabor.fluidpipes.registry.properties.ModProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;


/**
 * Create and register everything.
 */
public final class ModRegistries {
	// Registries.
	public static final DeferredRegister.Items ITEMS =
		DeferredRegister.createItems(Global.MODID);
	public static final DeferredRegister<MapCodec<? extends Block>> BLOCK_TYPES =
		DeferredRegister.create(Registries.BLOCK_TYPE, Global.MODID);
	public static final DeferredRegister.Blocks BLOCKS =
		DeferredRegister.createBlocks(Global.MODID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
		DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Global.MODID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
		DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Global.MODID);
	public static final DeferredRegister<Identifier> STATS =
		DeferredRegister.create(Registries.CUSTOM_STAT, Global.MODID);

	private ModRegistries() {
		// Static class.
	}

	/**
	 * Called from {@link ModCommon}.
	 */
	public static void init(IEventBus modEventBus) {
		// Properties.
		ModProperties.init();
		// Items and blocks.
		ModBlockTypes.init();
		ModFluidBlocks.init();
		ModBlockEntities.init();
		BLOCK_TYPES.register(modEventBus);
		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
		BLOCK_ENTITY_TYPES.register(modEventBus);
		// Sounds.
		ModSoundEvents.init();
		SOUND_EVENTS.register(modEventBus);
		// Statistics.
		ModStats.init();
		STATS.register(modEventBus);
	}
}
