package eu.pintergabor.fluidpipes.registry.variants;

import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;
import org.jspecify.annotations.NonNull;

import net.minecraft.world.level.block.state.BlockBehaviour;


public class FluidFittingVariant extends ModBlockVariant<FluidFitting> {

	public FluidFittingVariant(
		@NonNull String path,
		@NonNull FluidBlockSettings modSettings,
		BlockBehaviour.@NonNull Properties props
	) {
		super(path,
			(props1) -> new FluidFitting(props1, modSettings),
			props);
	}
}
