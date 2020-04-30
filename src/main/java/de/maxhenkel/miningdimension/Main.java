package de.maxhenkel.miningdimension;

import de.maxhenkel.miningdimension.block.ModBlocks;
import de.maxhenkel.miningdimension.dimension.CanyonWorldCarver;
import de.maxhenkel.miningdimension.dimension.CaveWorldCarver;
import de.maxhenkel.miningdimension.dimension.MiningBiome;
import de.maxhenkel.miningdimension.dimension.ModDimensionMining;
import de.maxhenkel.miningdimension.tileentity.TileentityTeleporter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
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

    public static ModDimension MINING_DIMENSION;
    public static DimensionType MINING_DIMENSION_TYPE;
    public static TileEntityType TELEPORTER_TILEENTITY;
    public static MiningBiome MINING_BIOME;
    public static CaveWorldCarver CAVE_CARVER;
    public static CanyonWorldCarver CANYON_CARVER;

    public Main() {

        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, this::registerBlocks);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, this::registerTileEntities);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Biome.class, this::registerBiomes);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(WorldCarver.class, this::registerCarver);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ModDimension.class, this::registerDimension);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(DimensionType.class, this::registerDimensionType);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::configEvent);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);

        MINING_DIMENSION = new ModDimensionMining();
        MINING_DIMENSION.setRegistryName(new ResourceLocation(Main.MODID, "mining_world"));
    }

    @SubscribeEvent
    public void configEvent(ModConfig.ModConfigEvent event) {
        if (event.getConfig().getType() == ModConfig.Type.SERVER) {
            MINING_BIOME.initializeFeatures();
        }
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
        CAVE_CARVER = new CaveWorldCarver(ProbabilityConfig::deserialize, 255);
        CAVE_CARVER.setRegistryName(new ResourceLocation(Main.MODID, "mining_carver"));
        event.getRegistry().register(CAVE_CARVER);

        CANYON_CARVER = new CanyonWorldCarver(ProbabilityConfig::deserialize);
        CANYON_CARVER.setRegistryName(new ResourceLocation(Main.MODID, "canyon_carver"));
        event.getRegistry().register(CANYON_CARVER);
    }

    @SubscribeEvent
    public void registerDimension(RegistryEvent.Register<ModDimension> event) {
        event.getRegistry().register(MINING_DIMENSION);
        addDimensionType();
    }

    public static DimensionType addDimensionType() {
        MINING_DIMENSION_TYPE = DimensionManager.registerDimension(MINING_DIMENSION.getRegistryName(), MINING_DIMENSION, null, true);
        return MINING_DIMENSION_TYPE;
    }

    @SubscribeEvent
    public void registerDimensionType(RegistryEvent.Register<DimensionType> event) {
        event.getRegistry().register(MINING_DIMENSION_TYPE);
    }

    public static DimensionType getMiningDimension() {
        DimensionType type = DimensionType.byName(Main.MINING_DIMENSION.getRegistryName());
        if (type == null) {
            LOGGER.warn("Could not find mining dimension");
            LOGGER.warn("This world may be created without the mining dimension mod installed");
            LOGGER.info("Registering mining dimension");
            type = addDimensionType();
        }
        return type;
    }

    public static DimensionType getOverworldDimension() {
        return DimensionType.byName(new ResourceLocation(Config.OVERWORLD_DIMENSION.get()));
    }
}