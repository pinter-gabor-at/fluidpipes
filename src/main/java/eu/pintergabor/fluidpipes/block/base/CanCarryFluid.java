package eu.pintergabor.fluidpipes.block.base;

import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.block.BlockState;


/**
 * A pipe or fitting capable of storing or carrying water or lava.
 */
public interface CanCarryFluid {

    /**
     * Get the fluid in the pipe.
     *
     * @param state {@link BlockState} of the pipe.
     */
    default PipeFluid getFluid(BlockState state) {
        return state.get(ModProperties.FLUID, PipeFluid.NONE);
    }

    /**
     * Get the fluid leaking from the pipe.
     * <p>
     * Non-leaking pipes and fittings override this to return {@link PipeFluid#NONE}.
     *
     * @param state {@link BlockState} of the pipe.
     */
    default PipeFluid getLeakingFluid(BlockState state) {
        return getFluid(state);
    }
}
