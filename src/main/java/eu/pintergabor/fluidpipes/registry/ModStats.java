package eu.pintergabor.fluidpipes.registry;

import eu.pintergabor.fluidpipes.Global;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;


public final class ModStats {
    public static final Identifier INTERACTIONS = Global.modId("interactions");

    private ModStats() {
        // Static class.
    }

    public static void init() {
        Registry.register(Registries.CUSTOM_STAT, INTERACTIONS, INTERACTIONS);
        Stats.CUSTOM.getOrCreateStat(INTERACTIONS, StatFormatter.DEFAULT);
    }

    /**
     * Increment statistics on the server.
     */
    public static void incStat(ServerPlayerEntity player) {
        player.incrementStat(Stats.CUSTOM.getOrCreateStat(
            ModStats.INTERACTIONS));
    }
}
