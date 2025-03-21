package eu.pintergabor.fluidpipes.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import eu.pintergabor.fluidpipes.block.entity.leaking.DripUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin {

    // The farmland is wet, if water is dripping on it.
    @ModifyReturnValue(at = @At("RETURN"), method = "isWaterNearby")
    private static boolean isWaterNearby(
        boolean original, WorldView view, BlockPos pos) {
        return original ||
            DripUtil.isWaterPipeNearby(view, pos, 6);
    }
}
