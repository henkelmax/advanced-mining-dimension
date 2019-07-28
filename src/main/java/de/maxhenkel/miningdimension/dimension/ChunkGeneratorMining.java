package de.maxhenkel.miningdimension.dimension;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.OverworldGenSettings;

public class ChunkGeneratorMining extends ChunkGenerator {

    private int height;

    public ChunkGeneratorMining(IWorld world, BiomeProvider biomeProvider, OverworldGenSettings settings) {
        super(world, biomeProvider, settings);
        height = 255;
    }

    @Override
    public void generateSurface(IChunk chunk) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBlockState(pos.setPos(x, height, z), Blocks.BEDROCK.getDefaultState(), false);
            }
        }
    }

    @Override
    public int getGroundHeight() {
        return height;
    }

    @Override
    public void makeBase(IWorld world, IChunk chunk) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBlockState(pos.setPos(x, 0, z), Blocks.BEDROCK.getDefaultState(), false);
            }
        }

        for (int y = 1; y < height; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunk.setBlockState(pos.setPos(x, y, z), Blocks.STONE.getDefaultState(), false);
                }
            }
        }
    }

    @Override
    public int func_222529_a(int i, int i1, Heightmap.Type type) {
        return 0;
    }
}
