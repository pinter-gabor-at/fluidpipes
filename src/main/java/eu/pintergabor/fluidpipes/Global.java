package eu.pintergabor.fluidpipes;

import net.minecraft.resources.Identifier;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;


public final class Global {

	private Global() {
		// Static class.
	}

	/**
	 * Used for logging and registration.
	 */
	public static final String MODID = "fluidpipes";

	/**
	 * This logger is used to write text to the console and the log file.
	 */
	@SuppressWarnings("unused")
	public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MODID);

	/**
	 * Create a mod specific name.
	 *
	 * @param path Name without {@link #MODID}.
	 */
	@Contract(pure = true)
	@SuppressWarnings("unused")
	public static @NotNull String modName(@NotNull String path) {
		return MODID + ":" + path;
	}

	/**
	 * Create a mod specific identifier.
	 *
	 * @param path Name without {@link #MODID}.
	 */
	@Contract("_ -> new")
	@SuppressWarnings("unused")
	public static @NotNull Identifier modId(@NotNull String path) {
		return Identifier.fromNamespaceAndPath(MODID, path);
	}
}
