package de.maxhenkel.miningdimension;

import de.maxhenkel.corelib.config.ConfigBase;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class ServerConfig extends ConfigBase {

    private final ForgeConfigSpec.ConfigValue<String> overworldDimensionSpec;
    public final ForgeConfigSpec.DoubleValue cavePercentage;
    public final ForgeConfigSpec.DoubleValue canyonPercentage;
    public final ForgeConfigSpec.BooleanValue generateLavaLakes;
    public final ForgeConfigSpec.BooleanValue generateSpawners;
    public final ForgeConfigSpec.BooleanValue generateStoneVariants;
    public final ForgeConfigSpec.BooleanValue generateOres;
    public final ForgeConfigSpec.BooleanValue generateLava;

    public RegistryKey<World> overworldDimension;

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        super(builder);
        overworldDimensionSpec = builder.comment("The dimension from where you can teleport to the mining dimension and back").define("overworld_dimension", "minecraft:overworld");
        cavePercentage = builder.defineInRange("world_generation.cave_percentage", 0.3D, 0D, 1D);
        canyonPercentage = builder.defineInRange("world_generation.canyon_percentage", 0.02D, 0D, 1D);
        generateLavaLakes = builder.define("world_generation.lava_lakes", true);
        generateSpawners = builder.define("world_generation.spawners", true);
        generateStoneVariants = builder.define("world_generation.stone_variants", true);
        generateOres = builder.define("world_generation.ores", true);
        generateLava = builder.comment("If lava should be generated in caves below level 11").define("world_generation.lava", true);
    }

    @Override
    public void onReload(ModConfig.ModConfigEvent event) {
        super.onReload(event);
        overworldDimension = RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation(overworldDimensionSpec.get()));
        Main.MINING_BIOME.initializeFeatures();
    }

}