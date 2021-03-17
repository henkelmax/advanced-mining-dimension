package de.maxhenkel.miningdimension;

import de.maxhenkel.corelib.CommonRegistry;
import de.maxhenkel.miningdimension.block.BlockTeleporter;
import de.maxhenkel.miningdimension.config.ClientConfig;
import de.maxhenkel.miningdimension.config.ServerConfig;
import de.maxhenkel.miningdimension.dimension.NoLavaCanyonWorldCarver;
import de.maxhenkel.miningdimension.dimension.NoLavaCaveWorldCarver;
import de.maxhenkel.miningdimension.tileentity.TileentityTeleporter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
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
    public static TileEntityType<TileentityTeleporter> TELEPORTER_TILEENTITY;
    public static RegistryKey<World> MINING_DIMENSION;

    public static ServerConfig SERVER_CONFIG;
    public static ClientConfig CLIENT_CONFIG;

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, this::registerBlocks);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, this::registerTileEntities);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(WorldCarver.class, this::registerCarvers);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        SERVER_CONFIG = CommonRegistry.registerConfig(ModConfig.Type.SERVER, ServerConfig.class, true);
        CLIENT_CONFIG = CommonRegistry.registerConfig(ModConfig.Type.CLIENT, ClientConfig.class);
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        MINING_DIMENSION = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Main.MODID, "mining"));
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
    public void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
        TELEPORTER_TILEENTITY = TileEntityType.Builder.of(TileentityTeleporter::new, TELEPORTER).build(null);
        TELEPORTER_TILEENTITY.setRegistryName(new ResourceLocation(MODID, "teleporter"));
        event.getRegistry().register(TELEPORTER_TILEENTITY);
    }

    @SubscribeEvent
    public void registerCarvers(RegistryEvent.Register<WorldCarver<?>> event) {
        event.getRegistry().register(new NoLavaCaveWorldCarver(ProbabilityConfig.CODEC, 256));
        event.getRegistry().register(new NoLavaCanyonWorldCarver(ProbabilityConfig.CODEC));
    }

}