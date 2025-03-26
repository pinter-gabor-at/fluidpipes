package eu.pintergabor.fluidpipes.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import eu.pintergabor.fluidpipes.block.util.WateringUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;


@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public abstract boolean removeCommandTag(String tag);

    @Unique
    private boolean fluidPipes$hadWaterPipeNearby;

    @Inject(at = @At("HEAD"), method = "updateWaterState")
    private void updateInWaterState(CallbackInfoReturnable<Boolean> info) {
        if (!getWorld().isClient) {
            Entity that = (Entity) (Object) this;
            fluidPipes$hadWaterPipeNearby =
                WateringUtil.isWaterPipeNearby(that, 2);
        }
    }

    @ModifyReturnValue(at = @At("RETURN"), method = "isBeingRainedOn")
    private boolean isBeingRainedOn(boolean original) {
        return original ||
            fluidPipes$hadWaterPipeNearby;
    }

    @Shadow
    public World getWorld() {
        return null;
    }
}
