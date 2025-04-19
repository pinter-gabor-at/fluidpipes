package eu.pintergabor.fluidpipes;

import eu.pintergabor.fluidpipes.datagen.DataGen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;


/**
 * Client side startup.
 */
@Mod(value = Global.MODID, dist = Dist.CLIENT)
public final class ModClient {

	@SuppressWarnings("unused")
	public ModClient(IEventBus modEventBus, ModContainer modContainer) {
		// Creative tabs.
		modEventBus.addListener(CreativeTabs::listener);
		// Data generator.
		modEventBus.addListener(DataGen::listener);
	}
}
