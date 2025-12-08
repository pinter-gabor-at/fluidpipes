package eu.pintergabor.fluidpipes.registry.variants;

import eu.pintergabor.fluidpipes.block.FluidPipe;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.level.block.state.BlockBehaviour;


public class FluidPipeVariant extends ModBlockVariant<FluidPipe> {

	public FluidPipeVariant(
		@NotNull String path,
		@NotNull FluidBlockSettings modSettings,
		@NotNull BlockBehaviour.Properties props
	) {
		super(path,
			(props1) -> new FluidPipe(props1, modSettings),
			props);
	}
}
