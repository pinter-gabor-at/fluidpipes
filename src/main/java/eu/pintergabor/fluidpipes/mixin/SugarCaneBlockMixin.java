package eu.pintergabor.fluidpipes.mixin;

import eu.pintergabor.fluidpipes.block.util.WateringUtil;
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

	// The sugarcane is placable where water is dropping from a pipe.
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
		BlockState state, LevelReader view, BlockPos pos, CallbackInfoReturnable<Boolean> cir
	) {
		if (view instanceof Level world &&
			WateringUtil.isWaterPipeNearby(world, pos, 1)) {
			cir.setReturnValue(true);
		}
	}
}
