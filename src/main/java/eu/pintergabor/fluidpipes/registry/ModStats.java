package eu.pintergabor.fluidpipes.registry;

import eu.pintergabor.fluidpipes.Global;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.resources.ResourceLocation;


public final class ModStats {
	public static final DeferredHolder<ResourceLocation, ResourceLocation> INTERACTIONS =
		ModRegistries.STATS.register("interactions", () -> Global.modId("interactions"));

	private ModStats() {
		// Static class.
	}

	public static void init() {
		// Everything has been done by static initializers.
	}
}
