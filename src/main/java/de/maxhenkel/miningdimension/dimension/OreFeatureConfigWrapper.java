package de.maxhenkel.miningdimension.dimension;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class OreFeatureConfigWrapper extends OreFeatureConfig {

    public OreFeatureConfigWrapper(FillerBlockType target, BlockState state, int size) {
        super(target, state, size);
    }

}
