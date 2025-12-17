package eu.pintergabor.fluidpipes.block.properties;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import net.minecraft.util.StringRepresentable;


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
	public @NotNull String getSerializedName() {
		return name;
	}
}
