package eu.pintergabor.fluidpipes.registry;

import eu.pintergabor.fluidpipes.Global;
import org.jspecify.annotations.NonNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;


/**
 * Register and store sounds associated with pipes and fittings.
 */
public final class ModSoundEvents {
	private static final SoundEvent TURN = register("block.pipe.turn");

	private ModSoundEvents() {
		// Static class.
	}

	@NonNull
	public static SoundEvent register(final @NonNull String path) {
		final Identifier id = Global.modId(path);
		return Registry.register(
			BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	/**
	 * Create and register everything that was not done by static initializers.
	 */
	public static void init() {
		// Everything has been done by static initializers.
	}

	/**
	 * Play pipe turn sound.
	 */
	public static void playTurnSound(
		final @NonNull Level level,
		final @NonNull BlockPos soundPos
	) {
		level.playSound(null, soundPos, TURN,
			SoundSource.BLOCKS, 0.5F, 1F);
	}
}
