package de.maxhenkel.miningdimension;

import de.maxhenkel.corelib.CommonRegistry;
import de.maxhenkel.miningdimension.block.ModBlocks;
import de.maxhenkel.miningdimension.dimension.CanyonWorldCarver;
import de.maxhenkel.miningdimension.dimension.CaveWorldCarver;
import de.maxhenkel.miningdimension.dimension.ChunkGeneratorMining;
import de.maxhenkel.miningdimension.dimension.MiningBiome;
import de.maxhenkel.miningdimension.tileentity.TileentityTeleporter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
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

    public static CaveWorldCarver CAVE_CARVER;
    public static CanyonWorldCarver CANYON_CARVER;
    public static TileEntityType<TileentityTeleporter> TELEPORTER_TILEENTITY;
    public static MiningBiome MINING_BIOME;
    public static final RegistryKey<World> MINING_DIMENSION = RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation(Main.MODID, "mining_dimension"));

    public static ServerConfig SERVER_CONFIG;

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, this::registerBlocks);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, this::registerTileEntities);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Biome.class, this::registerBiomes);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(WorldCarver.class, this::registerCarver);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        SERVER_CONFIG = CommonRegistry.registerConfig(ModConfig.Type.SERVER, ServerConfig.class, true);

        Registry.register(Registry.field_239690_aB_, new ResourceLocation(Main.MODID, "mining"), ChunkGeneratorMining.CODEC);
        Registry.register(Registry.field_239690_aB_, new ResourceLocation(Main.MODID, "mining"), ChunkGeneratorMining.CODEC);
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                ModBlocks.TELEPORTER.toItem()
        );
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                ModBlocks.TELEPORTER
        );
    }

    @SubscribeEvent
    public void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
        TELEPORTER_TILEENTITY = TileEntityType.Builder.create(TileentityTeleporter::new, ModBlocks.TELEPORTER).build(null);
        TELEPORTER_TILEENTITY.setRegistryName(new ResourceLocation(MODID, "teleporter"));
        event.getRegistry().register(TELEPORTER_TILEENTITY);
    }

    @SubscribeEvent
    public void registerBiomes(RegistryEvent.Register<Biome> event) {
        MINING_BIOME = new MiningBiome();
        MINING_BIOME.setRegistryName(new ResourceLocation(Main.MODID, "mining_biome"));
        event.getRegistry().register(MINING_BIOME);
    }

    @SubscribeEvent
    public void registerCarver(RegistryEvent.Register<WorldCarver<?>> event) {
        CAVE_CARVER = new CaveWorldCarver(ProbabilityConfig.field_236576_b_, 255);
        CAVE_CARVER.setRegistryName(new ResourceLocation(Main.MODID, "mining_carver"));
        event.getRegistry().register(CAVE_CARVER);

        CANYON_CARVER = new CanyonWorldCarver(ProbabilityConfig.field_236576_b_);
        CANYON_CARVER.setRegistryName(new ResourceLocation(Main.MODID, "canyon_carver"));
        event.getRegistry().register(CANYON_CARVER);
    }

}