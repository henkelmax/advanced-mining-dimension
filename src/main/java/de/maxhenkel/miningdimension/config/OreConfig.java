package de.maxhenkel.miningdimension.config;

import de.maxhenkel.corelib.config.DynamicConfig;
import de.maxhenkel.miningdimension.Main;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.List;
import java.util.stream.Collectors;

public class OreConfig extends DynamicConfig {

    @Override
    protected void setDefaults() {
        super.setDefaults();

        addOre(Blocks.DIRT, true, 256, 10, 33);
        addOre(Blocks.GRAVEL, true, 256, 8, 33);
        addOre(Blocks.GRANITE, true, 80, 10, 33);
        addOre(Blocks.DIORITE, true, 80, 10, 33);
        addOre(Blocks.ANDESITE, true, 80, 10, 33);

        addOre(Blocks.COAL_ORE, true, 128, 20, 17);
        addOre(Blocks.IRON_ORE, true, 64, 20, 9);
        addOre(Blocks.GOLD_ORE, true, 32, 2, 9);
        addOre(Blocks.REDSTONE_ORE, true, 16, 8, 8);
        addOre(Blocks.DIAMOND_ORE, true, 16, 1, 8);
        addOre(Blocks.LAPIS_ORE, true, 16, 1, 7);
        addOre(Blocks.EMERALD_ORE, false, 32, 1, 3);
    }

    private void addOre(Block ore, boolean enabled, int maxHeight, int count, int size) {
        String name = ore.getRegistryName().toString();
        setObject(name, new Ore(name, enabled, maxHeight, count, size));
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        Main.MINING_BIOME.initializeOreFeatures();
    }

    public List<Ore> getOres() {
        return getSubValues().stream().map(s -> getObject(s, () -> new Ore(s))).collect(Collectors.toList());
    }

    public Ore getOre(String ore) {
        return getObject(ore, () -> new Ore(ore));
    }

}
