package de.maxhenkel.miningdimension.mixin;

import de.maxhenkel.miningdimension.Main;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    /*@ModifyVariable(
            method = "loadWorld(Ljava/lang/String;Lnet/minecraft/util/registry/DynamicRegistries$Impl;Ljava/util/function/Function;Lcom/mojang/datafixers/util/Function4;ZLnet/minecraft/client/Minecraft$WorldSelectionType;Z)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft$WorldSelectionType;NONE:Lnet/minecraft/client/Minecraft$WorldSelectionType;", ordinal = 0),
            name = "flag1"
    )
    private boolean setFlag(boolean flag) {
        if (Main.CLIENT_CONFIG.showCustomWorldWarning.get()) {
            return flag;
        }
        return false;
    }*/

}
