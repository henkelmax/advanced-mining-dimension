package de.maxhenkel.miningdimension.mixin;

import de.maxhenkel.miningdimension.Main;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class MixinConnector implements IMixinConnector {

    @Override
    public void connect() {
        Mixins.addConfiguration("assets/" + Main.MODID + "/mining_dimension.mixins.json");
    }

}

