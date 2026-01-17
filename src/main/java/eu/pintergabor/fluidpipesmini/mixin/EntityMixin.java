package eu.pintergabor.fluidpipesmini.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import eu.pintergabor.fluidpipesmini.block.util.WateringUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;


@Mixin(Entity.class)
public abstract class EntityMixin {

	@Shadow
	public abstract Level level();

	@Shadow
	public abstract BlockPos blockPosition();

	@Unique
	private boolean fluidPipes$hasWaterPipeNearby = false;

	/**
	 * Calculate and store if there is a water pipe or fitting nearby.
	 */
	@Inject(at = @At("HEAD"), method = "updateInWaterStateAndDoFluidPushing")
	private void updateInWaterState(CallbackInfoReturnable<Boolean> info) {
		if (!level().isClientSide()) {
			fluidPipes$hasWaterPipeNearby =
				WateringUtil.isWaterPipeNearby(level(), blockPosition(), 0);
		}
	}

	/**
	 * A nearby water pipe or fitting creates the same effect as rain.
	 */
	@ModifyReturnValue(at = @At("RETURN"), method = "isInRain")
	private boolean isInRain(boolean original) {
		return original || fluidPipes$hasWaterPipeNearby;
	}
}
