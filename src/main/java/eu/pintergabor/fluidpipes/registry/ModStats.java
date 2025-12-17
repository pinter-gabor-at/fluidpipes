package eu.pintergabor.fluidpipes.registry;

import eu.pintergabor.fluidpipes.Global;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;


public final class ModStats {
	public static final Stat<Identifier> INTERACTIONS = register("interactions");

	private ModStats() {
		// Static class.
	}

	/**
	 * Register statistics.
	 */
	@SuppressWarnings("SameParameterValue")
	private static Stat<Identifier> register(@NotNull String path) {
		Identifier id = Global.modId(path);
		return Stats.CUSTOM.get(
			Registry.register(BuiltInRegistries.CUSTOM_STAT, id, id),
			StatFormatter.DEFAULT);
	}

	/**
	 * Create and register everything that was not done by static initializers.
	 */
	public static void init() {
		// Everything has been done by static initializers.
	}
}
