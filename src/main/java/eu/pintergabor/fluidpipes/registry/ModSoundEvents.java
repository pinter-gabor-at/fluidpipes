package eu.pintergabor.fluidpipes.registry;

import org.jspecify.annotations.NonNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;


/**
 * Register and store sounds associated with pipes and fittings.
 */
public final class ModSoundEvents {
	private static final Holder<SoundEvent> TURN = ModRegistries.SOUND_EVENTS.register(
		"block.pipe.turn", SoundEvent::createVariableRangeEvent);

	private ModSoundEvents() {
		// Static class.
	}

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
		level.playSound(null, soundPos, ModSoundEvents.TURN.value(),
			SoundSource.BLOCKS, 0.5F, 1F);
	}
}
