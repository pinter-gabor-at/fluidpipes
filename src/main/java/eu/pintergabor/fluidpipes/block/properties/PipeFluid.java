package eu.pintergabor.fluidpipes.block.properties;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import net.minecraft.util.StringRepresentable;


/**
 * All the fluids handled by this mod.
 */
public enum PipeFluid implements StringRepresentable {
	NONE("none"),
	WATER("water"),
	LAVA("lava");

	private final String name;

	@Contract(pure = true)
	PipeFluid(String name) {
		this.name = name;
	}

	@Contract(pure = true)
	@Override
	public String toString() {
		return name;
	}

	@Contract(pure = true)
	@Override
	public @NonNull String getSerializedName() {
		return name;
	}
}
