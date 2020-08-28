package de.maxhenkel.miningdimension.config;

import de.maxhenkel.corelib.config.ConfigBase;
import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig extends ConfigBase {

    public final ForgeConfigSpec.BooleanValue showCustomWorldWarning;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        super(builder);
        showCustomWorldWarning = builder
                .comment("If the game should show the custom world warning when loading a world")
                .define("show_custom_world_warning", false);
    }

}