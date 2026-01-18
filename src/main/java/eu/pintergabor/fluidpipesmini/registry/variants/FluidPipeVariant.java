package eu.pintergabor.fluidpipesmini.registry.variants;

import eu.pintergabor.fluidpipesmini.block.FluidPipe;
import eu.pintergabor.fluidpipesmini.block.settings.FluidBlockSettings;
import org.jspecify.annotations.NonNull;

import net.minecraft.world.level.block.state.BlockBehaviour;


public class FluidPipeVariant extends ModBlockVariant<FluidPipe> {

	public FluidPipeVariant(
		@NonNull String path,
		@NonNull FluidBlockSettings modSettings,
		BlockBehaviour.@NonNull Properties props
	) {
		super(path,
			(props1) -> new FluidPipe(props1, modSettings),
			props);
	}
}
