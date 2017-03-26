package de.maxhenkel.miningworld.dimension;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.IChunkGenerator;

public class WorldProviderMining extends WorldProvider {

	public WorldProviderMining() {
		this.isHellWorld=false;
	}
	
	@Override
	protected void createBiomeProvider() {
		this.biomeProvider = new BiomeProviderSingle(Biomes.PLAINS);
	}

	@Override
	public DimensionType getDimensionType() {
		return DimensionTypes.MINING_DIMENSION;
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkProviderMining(worldObj, worldObj.getSeed());
	}

	@Override
	public Biome getBiomeForCoords(BlockPos pos) {
		return Biomes.PLAINS;
	}
	
}
