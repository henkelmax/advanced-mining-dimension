package de.maxhenkel.miningdimension.dimension;

import com.google.common.collect.ImmutableList;
import de.maxhenkel.miningdimension.Main;
import de.maxhenkel.miningdimension.config.Ore;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class MiningBiome extends Biome {

    public MiningBiome() {
        super(new Builder()
                .surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)
                .precipitation(RainType.NONE)
                .category(Category.PLAINS)
                .depth(0.125F)
                .scale(0.05F)
                .temperature(0.8F)
                .downfall(0.4F)
                .func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_())
                .parent(null)
                .func_235098_a_(ImmutableList.of(new Biome.Attributes(0.0F, 0.0F, 0.0F, 0.0F, 1.0F)))
        );

        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.WITCH, 5, 1, 1));
    }

    public void initializeOreFeatures() {
        Main.LOGGER.info("Reloading mining biome ore features");

        features.values().forEach(features -> {
            features.removeIf(this::isOreFeature);
        });

        Main.ORE_CONFIG.getOres().stream().filter(ore -> ore.getOreBlock() != null).filter(Ore::isEnabled).forEach(ore -> {
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ore.getOreBlock(), ore.getSize())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(ore.getCount(), 0, 0, ore.getMaxHeight()))));
        });

    }

    public void initializeFeatures() {
        Main.LOGGER.info("Reloading mining biome features");

        carvers.values().forEach(carvers -> {
            carvers.removeIf(configuredCarver -> {
                if (configuredCarver.carver instanceof CaveWorldCarver) {
                    return true;
                } else if (configuredCarver.carver instanceof CanyonWorldCarver) {
                    return true;
                }
                return false;
            });
        });

        features.values().forEach(features -> {
            features.removeIf(feature -> feature.feature instanceof LakesFeature || feature.feature instanceof DungeonsFeature);
        });

        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(Main.CAVE_CARVER, new ProbabilityConfig(Main.SERVER_CONFIG.cavePercentage.get().floatValue())));

        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(Main.CANYON_CARVER, new ProbabilityConfig(Main.SERVER_CONFIG.canyonPercentage.get().floatValue())));

        if (Main.SERVER_CONFIG.generateLavaLakes.get()) {
            addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.LAVA.getDefaultState())).withPlacement(Placement.LAVA_LAKE.configure(new ChanceConfig(Main.SERVER_CONFIG.lavaLakeChance.get()))));
        }

        if (Main.SERVER_CONFIG.generateSpawners.get()) {
            addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Feature.MONSTER_ROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.DUNGEONS.configure(new ChanceConfig(Main.SERVER_CONFIG.spawnerChance.get()))));
        }
    }

    private boolean isOreFeature(ConfiguredFeature<?, ?> feature) {
        if (feature.config instanceof DecoratedFeatureConfig) {
            DecoratedFeatureConfig config = (DecoratedFeatureConfig) feature.config;
            if (config.feature.config instanceof OreFeatureConfig) {
                return true;
            }
        }
        return false;
    }

}
