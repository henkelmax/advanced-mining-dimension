package de.maxhenkel.miningdimension;

import de.maxhenkel.corelib.CommonRegistry;
import de.maxhenkel.miningdimension.block.BlockTeleporter;
import de.maxhenkel.miningdimension.config.ClientConfig;
import de.maxhenkel.miningdimension.config.ServerConfig;
import de.maxhenkel.miningdimension.events.CreativeTabEvents;
import de.maxhenkel.miningdimension.tileentity.TileentityTeleporter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "mining_dimension";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    private static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK, Main.MODID);
    private static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(BuiltInRegistries.ITEM, Main.MODID);
    public static final DeferredHolder<Block, BlockTeleporter> TELEPORTER = BLOCK_REGISTER.register("teleporter", BlockTeleporter::new);
    public static final DeferredHolder<Item, Item> TELEPORTER_ITEM = ITEM_REGISTER.register("teleporter", () -> TELEPORTER.get().toItem());

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Main.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TileentityTeleporter>> TELEPORTER_TILEENTITY = BLOCK_ENTITY_REGISTER.register("teleporter", () -> BlockEntityType.Builder.of(TileentityTeleporter::new, TELEPORTER.get()).build(null));

    public static ResourceKey<Level> MINING_DIMENSION;

    public static ServerConfig SERVER_CONFIG;
    public static ClientConfig CLIENT_CONFIG;

    public Main(IEventBus eventBus) {
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(CreativeTabEvents::onCreativeModeTabBuildContents);

        SERVER_CONFIG = CommonRegistry.registerConfig(MODID, ModConfig.Type.SERVER, ServerConfig.class, true);
        CLIENT_CONFIG = CommonRegistry.registerConfig(MODID, ModConfig.Type.CLIENT, ClientConfig.class);

        BLOCK_REGISTER.register(eventBus);
        ITEM_REGISTER.register(eventBus);
        BLOCK_ENTITY_REGISTER.register(eventBus);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        MINING_DIMENSION = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(Main.MODID, "mining"));
    }


}