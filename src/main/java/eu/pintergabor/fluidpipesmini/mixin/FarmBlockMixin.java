package eu.pintergabor.fluidpipesmini.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import eu.pintergabor.fluidpipesmini.block.util.WateringUtil;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FarmlandBlock;


@Mixin(FarmlandBlock.class)
public abstract class FarmBlockMixin {

	/**
	 * The farmland is wet, if water is dripping on it.
	 */
	@ModifyReturnValue(at = @At("RETURN"), method = "isNearWater")
	private static boolean isNearWater(
		final boolean original,
		final @NonNull LevelReader level,
		final @NonNull BlockPos pos
	) {
		return original ||
			(level instanceof Level level1 &&
				WateringUtil.isWaterPipeNearby(level1, pos, 6));
	}
}
