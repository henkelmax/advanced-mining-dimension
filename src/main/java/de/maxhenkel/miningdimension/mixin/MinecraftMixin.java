package de.maxhenkel.miningdimension.mixin;

import com.mojang.serialization.Lifecycle;
import de.maxhenkel.miningdimension.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Redirect(method = "doLoadLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/WorldData;worldGenSettingsLifecycle()Lcom/mojang/serialization/Lifecycle;"))
    public Lifecycle isOldCustomizedWorld(WorldData data) {
        if (!Main.CLIENT_CONFIG.showCustomWorldWarning.get()) {
            return Lifecycle.stable();
        }
        return data.worldGenSettingsLifecycle();
    }

}
