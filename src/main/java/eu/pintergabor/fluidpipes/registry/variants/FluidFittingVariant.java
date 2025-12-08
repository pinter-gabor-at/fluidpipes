package eu.pintergabor.fluidpipes.registry.variants;

import eu.pintergabor.fluidpipes.block.FluidFitting;
import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.level.block.state.BlockBehaviour;


public class FluidFittingVariant extends ModBlockVariant<FluidFitting> {

	public FluidFittingVariant(
		@NotNull String path,
		@NotNull FluidBlockSettings modSettings,
		@NotNull BlockBehaviour.Properties props
	) {
		super(path,
			(props1) -> new FluidFitting(props1, modSettings),
			props);
	}
}
