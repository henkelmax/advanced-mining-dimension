package de.maxhenkel.miningworld.dimension;

import net.minecraft.world.DimensionType;

public class DimensionTypes {

	public static final DimensionType MINING_DIMENSION=DimensionType.register("Mining Dimension", "_mining", 17, WorldProviderMining.class, true);
			
}
