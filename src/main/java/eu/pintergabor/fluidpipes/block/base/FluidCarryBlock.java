package eu.pintergabor.fluidpipes.block.base;

import eu.pintergabor.fluidpipes.block.settings.FluidBlockSettings;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;


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
     * See {@link FluidBlockSettings}
     */
    default FluidBlockSettings getFluidBlockSettings() {
        return new FluidBlockSettings(
            getTickRate(), canCarryWater(), canCarryLava(),
            getCloggingProbability(), getFireBreakProbability(),
            getWaterDrippingProbability(), getLavaDrippingProbability(),
            getWaterFillingProbability(), getLavaFillingProbability());
    }

    /**
     * Only descendant of {@link Block} will implement this interface
     * and they already have this method.
     */
    AbstractBlock.Settings getSettings();
}
