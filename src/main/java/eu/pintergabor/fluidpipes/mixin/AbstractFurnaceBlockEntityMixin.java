package eu.pintergabor.fluidpipes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;


@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    /**
     * Remove the fuel slot from the list of bottom slots.
     */
    @Shadow
    @SuppressWarnings("unused")
    private static final int[] BOTTOM_SLOTS = new int[]{2};
}
