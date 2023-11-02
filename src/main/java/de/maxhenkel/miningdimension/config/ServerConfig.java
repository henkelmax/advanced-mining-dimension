package de.maxhenkel.miningdimension.config;

import de.maxhenkel.corelib.config.ConfigBase;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig extends ConfigBase {

    private final ModConfigSpec.ConfigValue<String> overworldDimensionSpec;
    public final ModConfigSpec.BooleanValue spawnDeep;

    public ResourceKey<Level> overworldDimension;

    public ServerConfig(ModConfigSpec.Builder builder) {
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
        overworldDimension = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(overworldDimensionSpec.get()));
    }

}