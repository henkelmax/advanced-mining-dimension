package de.maxhenkel.miningdimension.config;

import de.maxhenkel.corelib.config.ConfigBase;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig extends ConfigBase {

    public final ModConfigSpec.BooleanValue showCustomWorldWarning;

    public ClientConfig(ModConfigSpec.Builder builder) {
        super(builder);
        showCustomWorldWarning = builder
                .comment("If the game should show the custom world warning when loading a world")
                .define("show_custom_world_warning", false);
    }

}