package de.maxhenkel.miningdimension.mixin;

import com.mojang.serialization.Lifecycle;
import de.maxhenkel.miningdimension.Main;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldOpenFlows.class)
public class WorldOpenFlowsMixin {

    @ModifyVariable(method = "confirmWorldCreation", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static Lifecycle confirmWorldCreation(Lifecycle lifecycle) {
        if (!Main.CLIENT_CONFIG.showCustomWorldWarning.get()) {
            if (lifecycle.equals(Lifecycle.experimental())) {
                return Lifecycle.stable();
            }
        }
        return lifecycle;
    }

}
