package eu.pintergabor.fluidpipes.mixin;

import eu.pintergabor.fluidpipes.block.util.WateringUtil;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;


@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin {

	/**
	 *  The sugarcane is placable where water is dripping from a pipe.
	 */
	@Inject(
		method = "canSurvive",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/BlockPos;below()Lnet/minecraft/core/BlockPos;",
			ordinal = 1
		),
		cancellable = true
	)
	private void canSurvive(
		final @NonNull BlockState state,
		final @NonNull LevelReader level,
		final @NonNull BlockPos pos,
		final @NonNull CallbackInfoReturnable<Boolean> cir
	) {
		if (level instanceof Level level1 &&
			WateringUtil.isWaterPipeNearby(level1, pos, 1)) {
			cir.setReturnValue(true);
		}
	}
}
