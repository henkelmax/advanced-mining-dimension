package de.maxhenkel.miningworld.proxy;

import de.maxhenkel.miningworld.Registry;
import de.maxhenkel.miningworld.block.ModBlocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    public void preinit(FMLPreInitializationEvent event) {
        super.preinit(event);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        Registry.addRenderBlock(ModBlocks.TELEPORTER);
    }

    public void postinit(FMLPostInitializationEvent event) {
        super.postinit(event);
    }

}
