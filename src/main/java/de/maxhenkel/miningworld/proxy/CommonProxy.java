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
		/*Configuration c = null;
		try {
			c = new Configuration(event.getSuggestedConfigurationFile());
			Config.init(c);e
		} catch (Exception e) {
			Log.w("Could not create config file: " + e.getMessage());
		}*/
		Registry.registerItemBlock(ModBlocks.TELEPORTER);
		GameRegistry.registerTileEntity(TileentityTeleporter.class, "teleporter");
		
		DimensionManager.registerDimension(DimensionTypes.MINING_DIMENSION.getId(), DimensionTypes.MINING_DIMENSION);
		
		GameRegistry.addRecipe(new ItemStack(ModBlocks.TELEPORTER), new Object[] { "LPL", "PIP", "LPL",
				Character.valueOf('L'), Blocks.LOG, Character.valueOf('P'), Blocks.PLANKS, Character.valueOf('I'), Items.STONE_PICKAXE });
		
		GameRegistry.addRecipe(new ItemStack(ModBlocks.TELEPORTER), new Object[] { "LPL", "PIP", "LPL",
				Character.valueOf('L'), Blocks.LOG2, Character.valueOf('P'), Blocks.PLANKS, Character.valueOf('I'), Items.STONE_PICKAXE });
	}

	public void init(FMLInitializationEvent event) {
		
		
	}

	public void postinit(FMLPostInitializationEvent event) {

	}

}
