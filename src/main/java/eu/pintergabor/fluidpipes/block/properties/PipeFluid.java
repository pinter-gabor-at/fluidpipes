package eu.pintergabor.fluidpipes.block.properties;

import net.minecraft.util.StringIdentifiable;


public enum PipeFluid implements StringIdentifiable {
    NONE("none"),
    WATER("water"),
    LAVA("lava");
    private final String name;

    PipeFluid(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
