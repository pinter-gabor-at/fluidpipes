package eu.pintergabor.fluidpipes.registry;

import eu.pintergabor.fluidpipes.Global;
import org.jetbrains.annotations.NotNull;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class ModSoundEvents {
    public static final SoundEvent TURN = register("block.pipe.turn");

    @NotNull
    public static SoundEvent register(@NotNull String path) {
        Identifier id = Global.modId(path);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void init() {
        // Everything has been done by static initializers.
    }

    /**
     * Play dispensing sound.
     */
    public static void playTurnSound(World world, BlockPos soundPos) {
        world.playSound(null, soundPos, ModSoundEvents.TURN,
            SoundCategory.BLOCKS, 0.5F, 1F);
    }
}
