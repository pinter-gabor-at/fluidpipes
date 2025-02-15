package eu.pintergabor.fluidpipes.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import eu.pintergabor.fluidpipes.block.base.BasePipe;
import eu.pintergabor.fluidpipes.registry.ModSoundEvents;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;


@Mixin(HoeItem.class)
public abstract class HoeItemMixin {

    @Inject(
        method = "useOnBlock",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/item/HoeItem;TILLING_ACTIONS:Ljava/util/Map;",
            opcode = Opcodes.GETSTATIC,
            ordinal = 0
        ),
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void useOnBlock(
        ItemUsageContext context, CallbackInfoReturnable<ActionResult> info,
        @Local World world, @Local BlockPos blockPos
    ) {
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof BasePipe) {
            // If the hoe is used on a pipe block.
            PlayerEntity playerEntity = context.getPlayer();
            ItemStack itemStack = context.getStack();
            if (playerEntity instanceof ServerPlayerEntity player) {
                // Increase statistics on the server.
                Criteria.ITEM_USED_ON_BLOCK.trigger(player, blockPos, itemStack);
            }
            Direction face = context.getSide();
            if (face != blockState.get(BasePipe.FACING)) {
                // Turn the pipe, if it is facing any other direction.
                BlockState state = blockState
                    .with(BasePipe.FACING, face)
                    .with(BasePipe.BACK_CONNECTED, BasePipe.needBackExtension(world, blockPos, face))
                    .with(BasePipe.FRONT_CONNECTED, BasePipe.needFrontExtension(world, blockPos, face))
                    .with(BasePipe.SMOOTH, BasePipe.isSmooth(world, blockPos, face));
                world.setBlockState(blockPos, state);
                ModSoundEvents.playTurnSound(world, blockPos);
                if (playerEntity != null) {
                    // Damage the hoe.
                    context.getStack()
                        .damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
                }
            }
            info.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
