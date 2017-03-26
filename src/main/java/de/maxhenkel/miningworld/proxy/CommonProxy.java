package de.maxhenkel.miningworld.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preinit(FMLPreInitializationEvent event) {
		/*Configuration c = null;
		try {
			c = new Configuration(event.getSuggestedConfigurationFile());
			Config.init(c);
		} catch (Exception e) {
			Log.w("Could not create config file: " + e.getMessage());
		}*/

	}

	public void init(FMLInitializationEvent event) {
		//MinecraftForge.EVENT_BUS.register();

	}

	public void postinit(FMLPostInitializationEvent event) {

	}

}
