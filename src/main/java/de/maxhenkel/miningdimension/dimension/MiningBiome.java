package de.maxhenkel.miningdimension.dimension;

import com.google.common.collect.Lists;
import de.maxhenkel.miningdimension.Config;
import de.maxhenkel.miningdimension.Main;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.LakeChanceConfig;
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

    public void initializeFeatures() {
        carvers.clear();
        features.clear();
        flowers.clear();
        structures.clear();
        for (GenerationStage.Decoration decoration : GenerationStage.Decoration.values()) {
            features.put(decoration, Lists.newArrayList());
        }

        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(Main.CAVE_CARVER, new ProbabilityConfig(Config.CAVE_PERCENTAGE.get().floatValue())));
        addCarver(GenerationStage.Carving.AIR, Biome.createCarver(Main.CANYON_CARVER, new ProbabilityConfig(Config.CANYON_PERCENTAGE.get().floatValue())));

        if (Config.GENERATE_LAVA_LAKES.get()) {
            addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(Feature.LAKE, new LakesConfig(Blocks.LAVA.getDefaultState()), Placement.LAVA_LAKE, new LakeChanceConfig(80)));
        }

        if (Config.GENERATE_SPAWNERS.get()) {
            DefaultBiomeFeatures.addMonsterRooms(this);
        }

        if (Config.GENERATE_STONE_VARIANTS.get()) {
            DefaultBiomeFeatures.addStoneVariants(this);
        }

        if (Config.GENERATE_ORES.get()) {
            DefaultBiomeFeatures.addOres(this);
        }
    }

}
