package eu.pintergabor.fluidpipes;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.util.Identifier;


public final class Global {

    // Used for logging and registration.
    public static final String MOD_ID = "fluidpipes";

    // This logger is used to write text to the console and the log file.
    @SuppressWarnings("unused")
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private Global() {
        // Static class.
    }

    /**
     * Create a mod specific identifier
     *
     * @param path Name, as in lang/*.json files without "*.modid." prefix
     */
    @Contract("_ -> new")
    public static @NotNull Identifier modId(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
