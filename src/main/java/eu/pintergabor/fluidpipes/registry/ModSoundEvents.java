package eu.pintergabor.fluidpipes.registry;

import eu.pintergabor.fluidpipes.Global;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;


public final class ModSoundEvents {
	private static final SoundEvent TURN = register("block.pipe.turn");

	private ModSoundEvents() {
		// Static class.
	}

	@NotNull
	public static SoundEvent register(@NotNull String path) {
		final ResourceLocation id = Global.modId(path);
		return Registry.register(
			BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	public static void init() {
		// Everything has been done by static initializers.
	}

	/**
	 * Play pipe turn sound.
	 */
	public static void playTurnSound(@NotNull Level level, @NotNull BlockPos soundPos) {
		level.playSound(null, soundPos, TURN,
			SoundSource.BLOCKS, 0.5F, 1F);
	}
}
