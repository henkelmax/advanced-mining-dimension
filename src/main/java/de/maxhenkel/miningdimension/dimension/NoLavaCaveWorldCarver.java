package de.maxhenkel.miningdimension.dimension;

import com.mojang.serialization.Codec;
import de.maxhenkel.miningdimension.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class NoLavaCaveWorldCarver extends CaveWorldCarver {

    public NoLavaCaveWorldCarver(Codec<ProbabilityConfig> codec, int maxHeight) {
        super(codec, maxHeight);
        setRegistryName(new ResourceLocation(Main.MODID, "cave_no_lava"));
    }

    @Override
    protected boolean func_230358_a_(IChunk chunk, Function<BlockPos, Biome> biome, BitSet bitSet, Random random, BlockPos.Mutable blockPos, BlockPos.Mutable blockPos1, BlockPos.Mutable blockPos2, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, MutableBoolean mutableBoolean) {
        boolean b = super.func_230358_a_(chunk, biome, bitSet, random, blockPos, blockPos1, blockPos2, i, i1, i2, i3, i4, i5, i6, i7, mutableBoolean);
        if (b) {
            chunk.setBlockState(blockPos, CAVE_AIR, false);
        }
        return b;
    }

}
