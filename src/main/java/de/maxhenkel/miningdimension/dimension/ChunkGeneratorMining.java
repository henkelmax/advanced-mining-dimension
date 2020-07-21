package de.maxhenkel.miningdimension.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;

import java.util.Collections;
import java.util.Optional;

public class ChunkGeneratorMining extends ChunkGenerator {

    public static final Codec<ChunkGeneratorMining> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(BiomeProvider.field_235202_a_.fieldOf("biome_source").forGetter((generator) -> generator.biomeProvider)
            ).apply(instance, instance.stable(ChunkGeneratorMining::new))
    );

    private int height;

    public ChunkGeneratorMining(BiomeProvider biomeProvider) {
        super(biomeProvider, new DimensionStructuresSettings(Optional.empty(), Collections.emptyMap()));
        height = 255;
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return CODEC;
    }

    @Override
    public ChunkGenerator func_230349_a_(long l) {
        return this;
    }

    @Override
    public void generateSurface(WorldGenRegion genRegion, IChunk chunk) {
        BlockPos.Mutable pos = new BlockPos.Mutable();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBlockState(pos.setPos(x, height, z), Blocks.BEDROCK.getDefaultState(), false);
            }
        }
    }

    @Override
    public void func_230352_b_(IWorld world, StructureManager structureManager, IChunk chunk) {
        BlockPos.Mutable pos = new BlockPos.Mutable();

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
    public int func_222529_a(int i1, int i2, Heightmap.Type heightmapType) {
        return 0;
    }

    @Override
    public IBlockReader func_230348_a_(int i1, int i2) {
        return new Blockreader(new BlockState[]{Blocks.STONE.getDefaultState()});
    }

    @Override
    public int getGroundHeight() {
        return height;
    }

}
