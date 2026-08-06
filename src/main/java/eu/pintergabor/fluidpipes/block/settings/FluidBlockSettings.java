package eu.pintergabor.fluidpipes.block.settings;

/**
 * Mod specific settings.
 * <p>
 * It would be an overkill to define different settings for each and every block,
 * so a few common value sets are also included.
 *
 * @param tickRate                 Block operating speed.<br>
 *                                 (20 = one action per second. 2<= tickRate!)
 * @param canCarryWater            True if the block can carry water.
 * @param canCarryLava             True if the block can carry lava.
 * @param wateringProbability      Watering probability.<br>
 *                                 (Water sensitive blocks and entities regard the pipe as water source.)<br>
 *                                 (0 = not watering; 0.1 ... 0.3 = unreliable; 0.5 = acceptable; 1 = for irrigation)
 * @param fireDripProbability      Fire drip probability.<br>
 *                                 (Fire erupts when lava is dripping on a block.)<br>
 *                                 (0 = never causes fire; 0.1 = uncomfortable; 0.3 = dangerous)
 * @param waterDrippingProbability Water dripping probability.<br>
 *                                 (Both visual and triggered actions.)<br>
 *                                 (0 = not dripping; 0 ... 0.002 = slow; 0.005 = good)
 * @param lavaDrippingProbability  Lava dripping probability.<br>
 *                                 (Both visual and triggered actions.)<br>
 *                                 (0 = not dripping; 0 ... 0.002 = slow; 0.005 = good)
 * @param waterFillingProbability  Water cauldron filling probability.<br>
 *                                 (0 = not filling; 0 ... 0.005 = slow; 0.01 = good)
 * @param lavaFillingProbability   Lava cauldron filling probability.<br>
 *                                 (0 = not filling; 0.001 = slow; 0.002 = good)
 */
public record FluidBlockSettings(
	int tickRate, boolean canCarryWater, boolean canCarryLava,
	float wateringProbability, float fireDripProbability,
	float waterDrippingProbability, float lavaDrippingProbability,
	float waterFillingProbability, float lavaFillingProbability
) {

	/**
	 * Good for carrying lava.
	 */
	public static final FluidBlockSettings GOOD_LAVA = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 20, false, true,
		/* watering, fire                  */ 0F, 0F,
		/* waterDrip, lavaDrip             */ 0F, 0.000F,
		/* waterFill, lavaFill             */ 0F, 0.005F);

	/**
	 * Average, for lava only.
	 */
	public static final FluidBlockSettings AVERAGE_LAVA = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 40, false, true,
		/* watering, fire                  */ 0F, 0.1F,
		/* waterDrip, lavaDrip             */ 0F, 0.001F,
		/* waterFill, lavaFill             */ 0F, 0.002F);

	/**
	 * Good for carrying water.
	 */
	public static final FluidBlockSettings GOOD_WATER = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 10, true, false,
		/* watering, fire                  */ 0F, 0F,
		/* waterDrip, lavaDrip             */ 0.000F, 0F,
		/* waterFill, lavaFill             */ 0.050F, 0F);

	/**
	 * Average, for water only.
	 */
	public static final FluidBlockSettings AVERAGE_WATER = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 20, true, false,
		/* watering, fire                  */ 1F, 0F,
		/* waterDrip, lavaDrip             */ 0.005F, 0F,
		/* waterFill, lavaFill             */ 0.010F, 0F);
}
