package de.maxhenkel.miningdimension;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {

    public static ForgeConfigSpec.ConfigValue<String> OVERWORLD_DIMENSION;
    public static ForgeConfigSpec.DoubleValue CAVE_PERCENTAGE;
    public static ForgeConfigSpec.DoubleValue CANYON_PERCENTAGE;
    public static ForgeConfigSpec.BooleanValue GENERATE_LAVA_LAKES;
    public static ForgeConfigSpec.BooleanValue GENERATE_SPAWNERS;
    public static ForgeConfigSpec.BooleanValue GENERATE_STONE_VARIANTS;
    public static ForgeConfigSpec.BooleanValue GENERATE_ORES;
    public static ForgeConfigSpec.BooleanValue GENERATE_LAVA;
    public static ForgeConfigSpec.BooleanValue BEDROCK_FLOOR;
    public static ForgeConfigSpec.BooleanValue BEDROCK_CEILING;

    public static ForgeConfigSpec.IntValue COAL_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue IRON_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue GOLD_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue REDSTONE_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue DIAMOND_MAX_HEIGHT;
    public static ForgeConfigSpec.IntValue LAPIS_MAX_HEIGHT;

    public static ForgeConfigSpec.IntValue COAL_COUNT;
    public static ForgeConfigSpec.IntValue IRON_COUNT;
    public static ForgeConfigSpec.IntValue GOLD_COUNT;
    public static ForgeConfigSpec.IntValue REDSTONE_COUNT;
    public static ForgeConfigSpec.IntValue DIAMOND_COUNT;
    public static ForgeConfigSpec.IntValue LAPIS_COUNT;


    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        Pair<ServerConfig, ForgeConfigSpec> specPairServer = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = specPairServer.getRight();
        SERVER = specPairServer.getLeft();
    }

    public static class ServerConfig {
        public ServerConfig(ForgeConfigSpec.Builder builder) {
            OVERWORLD_DIMENSION = builder.comment("The dimension from where you can teleport to the mining dimension and back").define("overworld_dimension", "minecraft:overworld");
            CAVE_PERCENTAGE = builder.defineInRange("world_generation.cave_percentage", 0.3D, 0D, 1D);
            CANYON_PERCENTAGE = builder.defineInRange("world_generation.canyon_percentage", 0.02D, 0D, 1D);
            GENERATE_LAVA_LAKES = builder.define("world_generation.lava_lakes", true);
            GENERATE_SPAWNERS = builder.define("world_generation.spawners", true);
            GENERATE_STONE_VARIANTS = builder.define("world_generation.stone_variants", true);
            GENERATE_ORES = builder.define("world_generation.ores", true);
            GENERATE_LAVA = builder.comment("If lava should be generated in caves below level 11").define("world_generation.lava", true);
            BEDROCK_FLOOR = builder
                    .comment("If a bedrock layer should be generated at Y=0")
                    .comment("Note that setting this to false can cause players to fall into the void")
                    .define("world_generation.bedrock_floor", true);
            BEDROCK_CEILING = builder
                    .comment("If a bedrock layer should be generated at Y=255")
                    .comment("Note that setting this to false causes the game to spawn mobs on top of the world")
                    .define("world_generation.bedrock_ceiling", true);

            COAL_MAX_HEIGHT = builder.worldRestart().defineInRange("world_generation.coal_ore.max_height", 128, 1, 256);
            IRON_MAX_HEIGHT = builder.worldRestart().defineInRange("world_generation.iron_ore.max_height", 64, 1, 256);
            GOLD_MAX_HEIGHT = builder.worldRestart().defineInRange("world_generation.gold_ore.max_height", 32, 1, 256);
            REDSTONE_MAX_HEIGHT = builder.worldRestart().defineInRange("world_generation.redstone_ore.max_height", 16, 1, 256);
            DIAMOND_MAX_HEIGHT = builder.worldRestart().defineInRange("world_generation.diamond_ore.max_height", 16, 1, 256);
            LAPIS_MAX_HEIGHT = builder.worldRestart().defineInRange("world_generation.lapis_lazuli_ore.baseline", 16, 1, 256);

            COAL_COUNT = builder.worldRestart().defineInRange("world_generation.coal_ore.count", 20, 1, 512);
            IRON_COUNT = builder.worldRestart().defineInRange("world_generation.iron_ore.count", 20, 1, 512);
            GOLD_COUNT = builder.worldRestart().defineInRange("world_generation.gold_ore.count", 2, 1, 512);
            REDSTONE_COUNT = builder.worldRestart().defineInRange("world_generation.redstone_ore.count", 8, 1, 512);
            DIAMOND_COUNT = builder.worldRestart().defineInRange("world_generation.diamond_ore.count", 1, 1, 512);
            LAPIS_COUNT = builder.worldRestart().defineInRange("world_generation.lapis_lazuli_ore.count", 1, 1, 512);

        }
    }

}
