package de.maxhenkel.miningworld.proxy;

import de.maxhenkel.miningworld.Main;
import de.maxhenkel.miningworld.block.ModBlocks;
import de.maxhenkel.miningworld.dimension.DimensionTypes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void preinit(FMLPreInitializationEvent event) {
        DimensionManager.registerDimension(DimensionTypes.MINING_DIMENSION.getId(), DimensionTypes.MINING_DIMENSION);

        GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "teleporter"), null,
                new ItemStack(ModBlocks.TELEPORTER), new Object[]{"LPL", "PIP", "LPL",
                        Character.valueOf('L'), Blocks.LOG, Character.valueOf('P'), Blocks.PLANKS, Character.valueOf('I'), Items.STONE_PICKAXE});
    }

    public void init(FMLInitializationEvent event) {


    }

    public void postinit(FMLPostInitializationEvent event) {

    }

}
