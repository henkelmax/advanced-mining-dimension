package de.maxhenkel.miningdimension.config;

import de.maxhenkel.corelib.config.DynamicConfig;
import de.maxhenkel.miningdimension.Main;
import net.minecraft.entity.EntityType;

import java.util.List;
import java.util.stream.Collectors;

public class MobConfig extends DynamicConfig {

    @Override
    protected void setDefaults() {
        super.setDefaults();

        addMob(EntityType.SPIDER, true, 100, 4, 4);
        addMob(EntityType.ZOMBIE, true, 95, 4, 4);
        addMob(EntityType.ZOMBIE_VILLAGER, true, 5, 1, 1);
        addMob(EntityType.SKELETON, true, 100, 4, 4);
        addMob(EntityType.CREEPER, true, 100, 4, 4);
        addMob(EntityType.ENDERMAN, true, 10, 1, 4);
        addMob(EntityType.WITCH, true, 5, 1, 1);
    }

    private void addMob(EntityType<?> entityType, boolean enabled, int weight, int minGroupCount, int maxGroupCount) {
        String name = entityType.getRegistryName().toString();
        setObject(name, new Mob(name, enabled, weight, minGroupCount, maxGroupCount));
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        Main.MINING_BIOME.initializeMobFeatures();
    }

    public List<Mob> getMobs() {
        return getSubValues().stream().map(s -> getObject(s, () -> new Mob(s))).collect(Collectors.toList());
    }

}
