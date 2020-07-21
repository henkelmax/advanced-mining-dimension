package de.maxhenkel.miningdimension.dimension;

import com.mojang.serialization.Codec;
import de.maxhenkel.miningdimension.Main;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class CanyonWorldCarver extends net.minecraft.world.gen.carver.CanyonWorldCarver {

    public CanyonWorldCarver(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean func_230358_a_(IChunk chunk, Function<BlockPos, Biome> biome, BitSet carvingMask, Random rand, BlockPos.Mutable pos, BlockPos.Mutable pos1, BlockPos.Mutable pos2, int i1, int i2, int i3, int posX, int posZ, int i6, int posY, int i8, MutableBoolean aBoolean) {
        int i = i6 | i8 << 4 | posY << 8;
        if (carvingMask.get(i)) {
            return false;
        }

        carvingMask.set(i);
        pos.setPos(posX, posY, posZ);
        BlockState state = chunk.getBlockState(pos);
        BlockState stateUp = chunk.getBlockState(pos1.setPos(pos).move(Direction.UP));

        if (!canCarveBlock(state, stateUp)) {
            return false;
        }

        if (posY < 11 && Main.SERVER_CONFIG.generateLava.get()) {
            chunk.setBlockState(pos, LAVA.getBlockState(), false);
        } else {
            chunk.setBlockState(pos, CAVE_AIR, false);
        }

        return true;
    }

}
