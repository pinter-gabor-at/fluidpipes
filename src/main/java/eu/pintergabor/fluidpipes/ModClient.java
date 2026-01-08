package eu.pintergabor.fluidpipes;

import eu.pintergabor.fluidpipes.datagen.ModDataGenerator;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.jspecify.annotations.NonNull;


/**
 * Client side startup.
 */
@Mod(value = Global.MODID, dist = Dist.CLIENT)
public final class ModClient {

	@SuppressWarnings("unused")
	public ModClient(@NonNull IEventBus modEventBus, ModContainer modContainer) {
		// Creative tabs.
		modEventBus.addListener(CreativeTabs::listener);
		// Data generator.
		modEventBus.addListener(ModDataGenerator::listener);
	}
}
