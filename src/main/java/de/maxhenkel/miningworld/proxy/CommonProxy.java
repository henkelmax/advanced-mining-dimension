package de.maxhenkel.miningworld.proxy;

import de.maxhenkel.miningworld.Registry;
import de.maxhenkel.miningworld.block.ModBlocks;
import de.maxhenkel.miningworld.dimension.DimensionTypes;
import de.maxhenkel.miningworld.tileentity.TileentityTeleporter;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preinit(FMLPreInitializationEvent event) {
        DimensionManager.registerDimension(DimensionTypes.MINING_DIMENSION.getId(), DimensionTypes.MINING_DIMENSION);
    }

	public void init(FMLInitializationEvent event) {
		
		
	}

	public void postinit(FMLPostInitializationEvent event) {

	}

}
