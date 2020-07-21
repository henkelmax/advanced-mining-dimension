package de.maxhenkel.miningdimension.dimension;

import de.maxhenkel.miningdimension.Config;
import de.maxhenkel.miningdimension.Main;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
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
                .waterColor(4159204)
                .waterFogColor(329011)
                .parent(null)
        );

        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.WITCH, 5, 1, 1));
    }

    public static final ConfiguredFeature LAKE = Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.LAVA.getDefaultState())).withPlacement(Placement.LAVA_LAKE.configure(new ChanceConfig(80)));
    public static final ConfiguredFeature MONSTER_ROOM = Feature.MONSTER_ROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.DUNGEONS.configure(new ChanceConfig(8)));

    public static final ConfiguredFeature DIRT = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIRT.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 256)));
    public static final ConfiguredFeature GRAVEL = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRAVEL.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 256)));
    public static final ConfiguredFeature GRANITE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRANITE.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 80)));
    public static final ConfiguredFeature DIORITE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIORITE.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 80)));
    public static final ConfiguredFeature ANDESITE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.ANDESITE.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 80)));

    public void initializeFeatures() {
        flowers.clear();
        structures.clear();

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
            features.removeIf(feature -> {
                return feature == LAKE ||
                        feature == MONSTER_ROOM ||
                        feature == DIRT ||
                        feature == GRAVEL ||
                        feature == GRANITE ||
                        feature == DIORITE ||
                        feature == ANDESITE ||
                        isFeature(feature, Blocks.COAL_ORE) ||
                        isFeature(feature, Blocks.IRON_ORE) ||
                        isFeature(feature, Blocks.GOLD_ORE) ||
                        isFeature(feature, Blocks.REDSTONE_ORE) ||
                        isFeature(feature, Blocks.DIAMOND_ORE) ||
                        isFeature(feature, Blocks.LAPIS_ORE);

            });
        });

        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(Main.CAVE_CARVER, new ProbabilityConfig(Config.CAVE_PERCENTAGE.get().floatValue())));

        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(Main.CANYON_CARVER, new ProbabilityConfig(Config.CANYON_PERCENTAGE.get().floatValue())));

        if (Config.GENERATE_LAVA_LAKES.get()) {
            addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, LAKE);
        }

        if (Config.GENERATE_SPAWNERS.get()) {
            addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, MONSTER_ROOM);
        }

        if (Config.GENERATE_STONE_VARIANTS.get()) {
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DIRT);
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GRAVEL);
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GRANITE);
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ANDESITE);
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DIORITE);
        }

        if (Config.GENERATE_ORES.get()) {
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 17)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(Config.COAL_COUNT.get(), 0, 0, Config.COAL_MAX_HEIGHT.get()))));
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.getDefaultState(), 9)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(Config.IRON_COUNT.get(), 0, 0, Config.IRON_MAX_HEIGHT.get()))));
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 9)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(Config.GOLD_COUNT.get(), 0, 0, Config.GOLD_MAX_HEIGHT.get()))));
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(Config.REDSTONE_COUNT.get(), 0, 0, Config.REDSTONE_MAX_HEIGHT.get()))));
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIAMOND_ORE.getDefaultState(), 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(Config.DIAMOND_COUNT.get(), 0, 0, Config.DIAMOND_MAX_HEIGHT.get()))));
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 7)).withPlacement(Placement.COUNT_DEPTH_AVERAGE.configure(new DepthAverageConfig(Config.LAPIS_COUNT.get(), Config.LAPIS_MAX_HEIGHT.get(), 16))));
        }
    }

    private boolean isFeature(ConfiguredFeature feature, Block block) {
        if (feature.config instanceof DecoratedFeatureConfig) {
            DecoratedFeatureConfig config = (DecoratedFeatureConfig) feature.config;
            if (config.feature.config instanceof OreFeatureConfig) {
                OreFeatureConfig c = (OreFeatureConfig) config.feature.config;
                return block == c.state.getBlock();
            }
        }
        return false;
    }


}
