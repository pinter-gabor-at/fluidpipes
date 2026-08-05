package eu.pintergabor.fluidpipesmini.block.util;

import org.jspecify.annotations.NonNull;

import net.minecraft.world.level.Level;


/**
 * Divide tick into smaller time slots.
 */
public final class TickUtil {

	private TickUtil() {
		// Static class.
	}

	/**
	 * @param rate min. 2.
	 * @return {@link TickPos#START} and {@link TickPos#MIDDLE} once in every {@code 1 / rate} time.
	 */
	public static @NonNull TickPos getTickPos(
		final @NonNull Level level,
		final int rate
	) {
		// Offset the gametime a little to make it better distributed.
		final int timeSlot = Math.floorMod(level.getGameTime() + 11, rate);
		if (timeSlot == 0) {
			return TickPos.START;
		}
		if (timeSlot == rate / 2) {
			return TickPos.MIDDLE;
		}
		return TickPos.NONE;
	}

	/**
	 * Output of {@link TickUtil#getTickPos(Level, int)}.
	 */
	public enum TickPos {
		NONE, START, MIDDLE
	}
}
