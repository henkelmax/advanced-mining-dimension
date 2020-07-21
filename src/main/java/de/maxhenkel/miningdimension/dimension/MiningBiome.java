package de.maxhenkel.miningdimension.dimension;

import com.google.common.collect.ImmutableList;
import de.maxhenkel.miningdimension.Main;
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
import net.minecraft.world.gen.placement.DepthAverageConfig;
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

    public static final ConfiguredFeature LAKE = Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.LAVA.getDefaultState())).withPlacement(Placement.LAVA_LAKE.configure(new ChanceConfig(80)));
    public static final ConfiguredFeature MONSTER_ROOM = Feature.MONSTER_ROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.DUNGEONS.configure(new ChanceConfig(8)));

    public static final ConfiguredFeature DIRT = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIRT.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 256)));
    public static final ConfiguredFeature GRAVEL = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRAVEL.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 256)));
    public static final ConfiguredFeature GRANITE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRANITE.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 80)));
    public static final ConfiguredFeature DIORITE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIORITE.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 80)));
    public static final ConfiguredFeature ANDESITE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.ANDESITE.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 80)));

    public static final ConfiguredFeature COAL_ORE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 17)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 128)));
    public static final ConfiguredFeature IRON_ORE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.getDefaultState(), 9)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 64)));
    public static final ConfiguredFeature GOLD_ORE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 9)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 0, 0, 32)));
    public static final ConfiguredFeature REDSTONE_ORE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 16)));
    public static final ConfiguredFeature DIAMOND_ORE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIAMOND_ORE.getDefaultState(), 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 0, 0, 16)));
    public static final ConfiguredFeature LAPIS_ORE = Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 7)).withPlacement(Placement.COUNT_DEPTH_AVERAGE.configure(new DepthAverageConfig(1, 16, 16)));

    public void initializeFeatures() {
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
                        feature == COAL_ORE ||
                        feature == IRON_ORE ||
                        feature == GOLD_ORE ||
                        feature == REDSTONE_ORE ||
                        feature == DIAMOND_ORE ||
                        feature == LAPIS_ORE;
            });
        });

        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(Main.CAVE_CARVER, new ProbabilityConfig(Main.SERVER_CONFIG.cavePercentage.get().floatValue())));

        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(Main.CANYON_CARVER, new ProbabilityConfig(Main.SERVER_CONFIG.canyonPercentage.get().floatValue())));

        if (Main.SERVER_CONFIG.generateLavaLakes.get()) {
            addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, LAKE);
        }

        if (Main.SERVER_CONFIG.generateSpawners.get()) {
            addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, MONSTER_ROOM);
        }

        if (Main.SERVER_CONFIG.generateStoneVariants.get()) {
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DIRT);
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GRAVEL);
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GRANITE);
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ANDESITE);
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DIORITE);
        }

        if (Main.SERVER_CONFIG.generateOres.get()) {
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, COAL_ORE);
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, IRON_ORE);
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, GOLD_ORE);
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, REDSTONE_ORE);
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DIAMOND_ORE);
            addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, LAPIS_ORE);
        }
    }

}
