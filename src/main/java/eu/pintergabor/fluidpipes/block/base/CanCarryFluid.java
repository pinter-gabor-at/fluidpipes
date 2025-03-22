package eu.pintergabor.fluidpipes.block.base;

import eu.pintergabor.fluidpipes.block.properties.PipeFluid;
import eu.pintergabor.fluidpipes.registry.ModProperties;

import net.minecraft.block.Block;
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

    /**
     * @return true if the pipe can carry water.
     */
    default boolean canCarryWater() {
        return true;
    }

    @SuppressWarnings("unused")
    default boolean canCarryWater(Block block) {
        if (block instanceof CanCarryFluid fluidBlock) {
            return fluidBlock.canCarryWater();
        }
        return false;
    }

    /**
     * @return true if the pipe can carry water.
     */
    default boolean canCarryLava() {
        return true;
    }

    @SuppressWarnings("unused")
    default boolean canCarryLava(Block block) {
        if (block instanceof CanCarryFluid fluidBlock) {
            return fluidBlock.canCarryLava();
        }
        return false;
    }

    /**
     * Get clogging probability.
     * @return 0.0 = never clogs, 1.0 = always clogs.
     */
    default float getCloggingProbability() {
        return 0F;
    }

    @SuppressWarnings("unused")
    default float getCloggingProbability(Block block) {
        if (block instanceof CanCarryFluid fluidBlock) {
            return fluidBlock.getCloggingProbability();
        }
        return 0F;
    }
    /**
     * Get fire break probability.
     * @return 0.0 = never breaks.
     */
    default float getFireBreakProbability() {
        return 0F;
    }

    @SuppressWarnings("unused")
    default float getFireBreakProbability(Block block) {
        if (block instanceof CanCarryFluid fluidBlock) {
            return fluidBlock.getFireBreakProbability();
        }
        return 0F;
    }

    /**
     * Get water dripping probability.
     * @return 0.0 = no dripping.
     */
    default float getWaterDrippingProbability() {
        return 1F;
    }

    @SuppressWarnings("unused")
    default float getWaterDrippingProbability(Block block) {
        if (block instanceof CanCarryFluid fluidBlock) {
            return fluidBlock.getWaterDrippingProbability();
        }
        return 0F;
    }

    /**
     * Get lava dripping probability.
     * @return 0.0 = no dripping.
     */
    default float getLavaDrippingProbability() {
        return 1F;
    }

    @SuppressWarnings("unused")
    default float getLavaDrippingProbability(Block block) {
        if (block instanceof CanCarryFluid fluidBlock) {
            return fluidBlock.getLavaDrippingProbability();
        }
        return 0F;
    }

    /**
     * Get water cauldron filling probability.
     * @return 0.0 = no filling.
     */
    default float getWaterFillingProbability() {
        return 1F;
    }

    @SuppressWarnings("unused")
    default float getWaterFillingProbability(Block block) {
        if (block instanceof CanCarryFluid fluidBlock) {
            return fluidBlock.getWaterFillingProbability();
        }
        return 0F;
    }

    /**
     * Get lava cauldron filling probability.
     * @return 0.0 = no filling.
     */
    default float getLavaFillingProbability() {
        return 1F;
    }

    @SuppressWarnings("unused")
    default float getLavaFillingProbability(Block block) {
        if (block instanceof CanCarryFluid fluidBlock) {
            return fluidBlock.getLavaFillingProbability();
        }
        return 0F;
    }
}
