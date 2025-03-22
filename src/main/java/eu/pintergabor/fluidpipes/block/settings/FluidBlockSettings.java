package eu.pintergabor.fluidpipes.block.settings;

/**
 * Mod specific settings.
 * <p>
 * It would be an ovekill to define different settings for each and every block,
 * so a few common value sets are also included.
 *
 * @param tickRate                 Fluid carrying speed.
 *                                 (20 = one action per second. 2<= tickRate!)
 * @param canCarryWater            True if the block can carry water.
 * @param canCarryLava             True if the block can carry lava.
 * @param cloggingProbability      Clogging probability.
 * @param fireBreakProbability     Fire break probability.
 * @param waterDrippingProbability Water dripping probability.
 *                                 (Both visual and triggered actions.)
 * @param lavaDrippingProbability  Lava dripping probability.
 *                                 (Both visual and triggered actions.)
 * @param waterFillingProbability  Water cauldron fitting probability.
 * @param lavaFillingProbability   Lava cauldron fitting probability.
 */
public record FluidBlockSettings(
    int tickRate, boolean canCarryWater, boolean canCarryLava,
    float cloggingProbability, float fireBreakProbability,
    float waterDrippingProbability, float lavaDrippingProbability,
    float waterFillingProbability, float lavaFillingProbability
) {
    /**
     * Good for carrying lava.
     */
    public static final FluidBlockSettings GOOD_LAVA = new FluidBlockSettings(
        10, false, true, 0F, 0F, 0F, 0.001F, 0F, 0.01F);
    /**
     * Avarage, for lava only.
     */
    public static final FluidBlockSettings AVERAGE_LAVA = new FluidBlockSettings(
        20, false, true, 0.2F, 0F, 0F, 0.01F, 0F, 0.001F);
    /**
     * Bad for carrying lava.
     */
    public static final FluidBlockSettings BAD_LAVA = new FluidBlockSettings(
        60, false, true, 0.9F, 0F, 0F, 0.01F, 0F, 0F);
    /**
     * Slow, and dripping lava.
     */
    public static final FluidBlockSettings DRIPPING_LAVA = new FluidBlockSettings(
        20, false, true, 0.1F, 0F, 0F, 0.1F, 0F, 0F);
    /**
     * Flammable, useless.
     */
    public static final FluidBlockSettings FLAMMABLE_LAVA = new FluidBlockSettings(
        50, false, true, 0.5F, 0.2F, 0F, 0.001F, 0F, 0.001F);
    /**
     * Good for carrying water.
     */
    public static final FluidBlockSettings GOOD_WATER = new FluidBlockSettings(
        5, true, false, 0F, 0F, 0.001F, 0F, 0.1F, 0F);
    /**
     * Average, for water only.
     */
    public static final FluidBlockSettings AVERAGE_WATER = new FluidBlockSettings(
        10, true, false, 0.2F, 0F, 0.01F, 0F, 0.01F, 0F);
    /**
     * Bad for carrying water.
     */
    public static final FluidBlockSettings BAD_WATER = new FluidBlockSettings(
        40, true, false, 0.9F, 0F, 0.01F, 0F, 0F, 0F);
    /**
     * Slow and dripping water. For irrigation.
     */
    public static final FluidBlockSettings DRIPPING_WATER = new FluidBlockSettings(
        20, true, false, 0.1F, 0F, 0.1F, 0F, 0F, 0F);
    /**
     * Good for anything, but flammable.
     */
    public static final FluidBlockSettings UNSTABLE_UNI = new FluidBlockSettings(
        10, true, true, 0F, 0.1F, 0.01F, 0.01F, 0.01F, 0.01F);
    /**
     * Not so good for everything, but less flammable.
     */
    public static final FluidBlockSettings STABLE_UNI = new FluidBlockSettings(
        20, true, true, 0.1F, 0.001F, 0.01F, 0.01F, 0.01F, 0.01F);
    /**
     * Flammable, useless.
     */
    public static final FluidBlockSettings FLAMMABLE_UNI = new FluidBlockSettings(
        50, true, true, 0.5F, 0.2F, 0.001F, 0.001F, 0.001F, 0.001F);
    /**
     * Useless.
     */
    public static final FluidBlockSettings USELESS_UNI = new FluidBlockSettings(
        50, true, true, 0.5F, 0F, 0.001F, 0.001F, 0.001F, 0.001F);
}
