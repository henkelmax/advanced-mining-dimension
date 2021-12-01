package de.maxhenkel.miningdimension.config;

import de.maxhenkel.corelib.config.ConfigBase;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class ServerConfig extends ConfigBase {

    private final ForgeConfigSpec.ConfigValue<String> overworldDimensionSpec;
    public final ForgeConfigSpec.BooleanValue spawnDeep;

    public ResourceKey<Level> overworldDimension;

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        super(builder);
        overworldDimensionSpec = builder
                .comment("The dimension from where you can teleport to the mining dimension and back")
                .define("overworld_dimension", "minecraft:overworld");
        spawnDeep = builder
                .comment("If the teleporter should bring you to the lowest free space in the mining dimension")
                .define("spawn_deep", true);
    }

    @Override
    public void onReload(ModConfigEvent event) {
        super.onReload(event);
        overworldDimension = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(overworldDimensionSpec.get()));
    }

}