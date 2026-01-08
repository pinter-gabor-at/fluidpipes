package eu.pintergabor.fluidpipes.block.properties;

import org.jspecify.annotations.NonNull;

import net.minecraft.util.StringRepresentable;


/**
 * All fluids handled by this mod.
 */
public enum PipeFluid implements StringRepresentable {
	NONE("none"),
	WATER("water"),
	LAVA("lava");

	private final String name;

	PipeFluid(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public @NonNull String getSerializedName() {
		return name;
	}
}
