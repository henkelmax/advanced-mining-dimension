package de.maxhenkel.miningdimension;

import de.maxhenkel.corelib.CommonRegistry;
import de.maxhenkel.miningdimension.block.BlockTeleporter;
import de.maxhenkel.miningdimension.config.ClientConfig;
import de.maxhenkel.miningdimension.config.ServerConfig;
import de.maxhenkel.miningdimension.tileentity.TileentityTeleporter;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "mining_dimension";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final BlockTeleporter TELEPORTER = new BlockTeleporter();
    public static BlockEntityType<TileentityTeleporter> TELEPORTER_TILEENTITY;
    public static ResourceKey<Level> MINING_DIMENSION;

    public static ServerConfig SERVER_CONFIG;
    public static ClientConfig CLIENT_CONFIG;

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, this::registerBlocks);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(BlockEntityType.class, this::registerTileEntities);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        SERVER_CONFIG = CommonRegistry.registerConfig(ModConfig.Type.SERVER, ServerConfig.class, true);
        CLIENT_CONFIG = CommonRegistry.registerConfig(ModConfig.Type.CLIENT, ClientConfig.class);
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        MINING_DIMENSION = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Main.MODID, "mining"));
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                TELEPORTER.toItem()
        );
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                TELEPORTER
        );
    }

    @SubscribeEvent
    public void registerTileEntities(RegistryEvent.Register<BlockEntityType<?>> event) {
        TELEPORTER_TILEENTITY = BlockEntityType.Builder.of(TileentityTeleporter::new, TELEPORTER).build(null);
        TELEPORTER_TILEENTITY.setRegistryName(new ResourceLocation(MODID, "teleporter"));
        event.getRegistry().register(TELEPORTER_TILEENTITY);
    }

}