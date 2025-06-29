package eu.pintergabor.fluidpipes.block;

import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;


public interface FluidCarryBlock extends CanCarryFluid {

	/**
	 * How fast is this block?
	 * Higher value means slower operation.
	 * Min 2.
	 */
	default int getTickRate() {
		return 10;
	}

	/**
	 * See {@link FluidBlockSettings}.
	 */
	default FluidBlockSettings getFluidBlockSettings() {
		return new FluidBlockSettings(
			getTickRate(), canCarryWater(), canCarryLava(),
			getCloggingProbability(), getFireBreakProbability(),
			getFireDripProbability(), getWateringProbability(),
			getWaterDrippingProbability(), getLavaDrippingProbability(),
			getWaterFillingProbability(), getLavaFillingProbability());
	}
}
