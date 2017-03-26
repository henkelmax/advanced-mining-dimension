package de.maxhenkel.miningworld.dimension;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkProviderOverworld;

public class ChunkProviderMining extends ChunkProviderOverworld{

	private int worldHeight;
	
	public ChunkProviderMining(World worldIn, long seed) {
		super(worldIn, seed, false, new String());
		this.worldHeight=64;
		
	}

	@Override
	public void setBlocksInChunk(int cx, int cz, ChunkPrimer primer) {
		fill(0, worldHeight, STONE, primer);
	}
	
	private void fill(int min, int max, IBlockState state, ChunkPrimer primer){
		for(int x=0; x<16; x++){
			for(int y=min; y<=max&&y<255; y++){
				for(int z=0; z<16; z++){
					primer.setBlockState(x, y, z, state);
				}
			}
		}
	}
	
}
