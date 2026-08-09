package eu.pintergabor.fluidpipes.registry.variants;

import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;
import org.jspecify.annotations.NonNull;

import net.minecraft.world.level.block.state.BlockBehaviour;


public class FluidPipeVariant extends ModBlockVariant<FluidPipe> {

	public FluidPipeVariant(
		final @NonNull String path,
		final @NonNull FluidBlockSettings modSettings,
		final BlockBehaviour.@NonNull Properties props
	) {
		super(
			path,
			(props1) -> new FluidPipe(props1, modSettings),
			props
		);
	}
}
