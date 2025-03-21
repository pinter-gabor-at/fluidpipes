package eu.pintergabor.fluidpipes.registry;

import eu.pintergabor.fluidpipes.Global;
import eu.pintergabor.fluidpipes.block.entity.FluidFittingEntity;
import eu.pintergabor.fluidpipes.block.entity.FluidPipeEntity;
import org.jetbrains.annotations.NotNull;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;


public final class ModBlockEntities {
    // Wooden and stone pipes.
    public static final BlockEntityType<FluidPipeEntity> FLUID_PIPE_ENTITY = register(
        "fluid_pipe",
        FluidPipeEntity::new,
        ModBlocks.PIPES);
    // Wooden and stone fittings.
    public static final BlockEntityType<FluidFittingEntity> FLUID_FITTING_ENTITY = register(
        "fluid_fitting",
        FluidFittingEntity::new,
        ModBlocks.FITTINGS);

    @NotNull
    private static <T extends BlockEntity> BlockEntityType<T> register(
        @NotNull String path,
        @NotNull FabricBlockEntityTypeBuilder.Factory<T> blockEntity,
        @NotNull Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Global.modId(path),
            FabricBlockEntityTypeBuilder.create(blockEntity, blocks).build());
    }

    public static void init() {
        // Everything has been done by static initializers.
    }
}
