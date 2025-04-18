package eu.pintergabor.fluidpipes.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import eu.pintergabor.fluidpipes.block.util.WateringUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;


@Mixin(Entity.class)
public abstract class EntityMixin {

	@Shadow
	public abstract boolean removeTag(String tag);

	@Unique
	private boolean fluidPipes$hadWaterPipeNearby;

	@Inject(at = @At("HEAD"), method = "updateInWaterStateAndDoFluidPushing")
	private void updateInWaterState(CallbackInfoReturnable<Boolean> info) {
		if (!level().isClientSide) {
			Entity that = (Entity) (Object) this;
			fluidPipes$hadWaterPipeNearby =
				WateringUtil.isWaterPipeNearby(that, 2);
		}
	}

	@ModifyReturnValue(at = @At("RETURN"), method = "isInRain")
	private boolean isInRain(boolean original) {
		return original ||
			fluidPipes$hadWaterPipeNearby;
	}

	@Shadow
	public abstract Level level();
}
