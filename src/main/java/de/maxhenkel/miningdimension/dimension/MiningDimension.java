package de.maxhenkel.miningdimension.dimension;

import de.maxhenkel.miningdimension.Main;
import net.minecraft.world.World;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.OverworldGenSettings;

public class MiningDimension extends OverworldDimension {

    public MiningDimension(World world, DimensionType type) {
        super(world, type);
    }

    @Override
    public ChunkGenerator<? extends GenerationSettings> createChunkGenerator() {
        SingleBiomeProviderSettings settings = new SingleBiomeProviderSettings(world.getWorldInfo());
        settings.setBiome(Main.MINING_BIOME);
        SingleBiomeProvider provider = new SingleBiomeProvider(settings);
        OverworldGenSettings overworldGenSettings = new OverworldGenSettings();
        return new ChunkGeneratorMining(world, provider, overworldGenSettings);
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }
}
