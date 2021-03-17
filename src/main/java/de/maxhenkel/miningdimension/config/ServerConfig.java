package de.maxhenkel.miningdimension.config;

import de.maxhenkel.corelib.config.ConfigBase;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class ServerConfig extends ConfigBase {

    private final ForgeConfigSpec.ConfigValue<String> overworldDimensionSpec;

    public RegistryKey<World> overworldDimension;

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        super(builder);
        overworldDimensionSpec = builder
                .comment("The dimension from where you can teleport to the mining dimension and back")
                .define("overworld_dimension", "minecraft:overworld");
    }

    @Override
    public void onReload(ModConfig.ModConfigEvent event) {
        super.onReload(event);
        overworldDimension = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(overworldDimensionSpec.get()));
    }

}