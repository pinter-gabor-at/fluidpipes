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
 * @param cloggingProbability      Clogging probability.<br>
 *                                 (0 = not clogging; 0.1 = normal; 0.2 = bad; 0.5 = almost useless.)
 * @param fireBreakProbability     Fire break probability.<br>
 *                                 (Pipe breaks and is replaced by fire.)<br>
 *                                 (0 = never breaks; 0.01 = rarely breaks; 0.1 = annoyingly bad)
 * @param fireDripProbability      Fire drip probability.<br>
 *                                 (Fire erupts when lava is dripping on a block.)<br>
 *                                 (0 = never causes fire; 0.1 = uncomfortable; 0.3 = dangerous)
 * @param wateringProbability      Watering probability.<br>
 *                                 (Water sensitive blocks and entities regard the pipe as water source.)<br>
 *                                 (0 = not watering; 0.1 ... 0.3 = unreliable; 0.5 = acceptable; 1 = for irrigation)
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
	float cloggingProbability, float fireBreakProbability,
	float fireDripProbability, float wateringProbability,
	float waterDrippingProbability, float lavaDrippingProbability,
	float waterFillingProbability, float lavaFillingProbability
) {

	/**
	 * Good for carrying lava.
	 */
	public static final FluidBlockSettings GOOD_LAVA = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 20, false, true,
		/* clogging, break, drip, watering */ 0F, 0F, 0F, 0F,
		/* waterDrip, lavaDrip             */ 0F, 0.001F,
		/* waterFill, lavaFill             */ 0F, 0.002F);

	/**
	 * Avarage, for lava only.
	 */
	public static final FluidBlockSettings AVERAGE_LAVA = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 40, false, true,
		/* clogging, break, drip, watering */ 0.1F, 0F, 0F, 0F,
		/* waterDrip, lavaDrip             */ 0F, 0.001F,
		/* waterFill, lavaFill             */ 0F, 0.002F);

	/**
	 * Bad for carrying lava.
	 */
	public static final FluidBlockSettings BAD_LAVA = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 120, false, true,
		/* clogging, break, drip, watering */ 0.2F, 0F, 0.2F, 0F,
		/* waterDrip, lavaDrip             */ 0F, 0.001F,
		/* waterFill, lavaFill             */ 0F, 0.002F);

	/**
	 * Slow, and dripping lava.
	 */
	public static final FluidBlockSettings DRIPPING_LAVA = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 40, false, true,
		/* clogging, break, drip, watering */ 0.1F, 0F, 0.3F, 0F,
		/* waterDrip, lavaDrip             */ 0F, 0.001F,
		/* waterFill, lavaFill             */ 0F, 0.002F);

	/**
	 * Flammable, useless.
	 */
	public static final FluidBlockSettings FLAMMABLE_LAVA = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 100, false, true,
		/* clogging, break, drip, watering */ 0.1F, 0.1F, 0.3F, 0F,
		/* waterDrip, lavaDrip             */ 0F, 0.001F,
		/* waterFill, lavaFill             */ 0F, 0.002F);

	/**
	 * Good for carrying water.
	 */
	public static final FluidBlockSettings GOOD_WATER = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 10, true, false,
		/* clogging, break, drip, watering */ 0F, 0F, 0F, 0.1F,
		/* waterDrip, lavaDrip             */ 0.005F, 0F,
		/* waterFill, lavaFill             */ 0.01F, 0F);

	/**
	 * Average, for water only.
	 */
	public static final FluidBlockSettings AVERAGE_WATER = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 20, true, false,
		/* clogging, break, drip, watering */ 0.1F, 0F, 0F, 0.5F,
		/* waterDrip, lavaDrip             */ 0.005F, 0F,
		/* waterFill, lavaFill             */ 0.01F, 0F);

	/**
	 * Bad for carrying water.
	 */
	public static final FluidBlockSettings BAD_WATER = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 40, true, false,
		/* clogging, break, drip, watering */ 0.2F, 0F, 0F, 0.1F,
		/* waterDrip, lavaDrip             */ 0.001F, 0F,
		/* waterFill, lavaFill             */ 0.002F, 0F);

	/**
	 * Slow and dripping water. For irrigation.
	 */
	public static final FluidBlockSettings DRIPPING_WATER = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 20, true, false,
		/* clogging, break, drip, watering */ 0F, 0F, 0F, 1F,
		/* waterDrip, lavaDrip             */ 0.001F, 0F,
		/* waterFill, lavaFill             */ 0.002F, 0F);

	/**
	 * Good for anything, but flammable.
	 */
	public static final FluidBlockSettings UNSTABLE_UNI = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 20, true, true,
		/* clogging, break, drip, watering */ 0F, 0.1F, 0.2F, 0.9F,
		/* waterDrip, lavaDrip             */ 0.002F, 0.0005F,
		/* waterFill, lavaFill             */ 0.005F, 0.001F);

	/**
	 * Not so good for everything, but less flammable.
	 */
	public static final FluidBlockSettings STABLE_UNI = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 40, true, true,
		/* clogging, break, drip, watering */ 0.1F, 0.001F, 0.1F, 0.8F,
		/* waterDrip, lavaDrip             */ 0.004F, 0.001F,
		/* waterFill, lavaFill             */ 0.010F, 0.002F);

	/**
	 * Flammable, useless.
	 */
	public static final FluidBlockSettings FLAMMABLE_UNI = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 100, true, true,
		/* clogging, break, drip, watering */ 0.1F, 0.1F, 0.5F, 0.1F,
		/* waterDrip, lavaDrip             */ 0.0005F, 0.0005F,
		/* waterFill, lavaFill             */ 0.0010F, 0.0010F);

	/**
	 * Useless.
	 */
	public static final FluidBlockSettings USELESS_UNI = new FluidBlockSettings(
		/* tick, canWater, canLava,        */ 100, true, true,
		/* clogging, break, drip, watering */ 0.5F, 0F, 0.5F, 0.1F,
		/* waterDrip, lavaDrip             */ 0.0005F, 0.0005F,
		/* waterFill, lavaFill             */ 0.0010F, 0.0010F);
}
