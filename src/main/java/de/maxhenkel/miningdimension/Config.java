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
        }
    }

}
