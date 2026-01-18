package eu.pintergabor.fluidpipesmini.registry;

import eu.pintergabor.fluidpipesmini.Global;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.resources.Identifier;


/**
 * Register and store statistics.
 */
public final class ModStats {
	public static final DeferredHolder<Identifier, Identifier> INTERACTIONS =
		ModRegistries.STATS.register("interactions", () -> Global.modId("interactions"));

	private ModStats() {
		// Static class.
	}

	public static void init() {
		// Everything has been done by static initializers.
	}
}
