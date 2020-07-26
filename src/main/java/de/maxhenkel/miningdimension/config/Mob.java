package de.maxhenkel.miningdimension.config;

import com.electronwill.nightconfig.core.conversion.Path;
import com.electronwill.nightconfig.core.conversion.SpecIntInRange;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class Mob {

    private transient EntityType<?> entityType;

    @Path("enabled")
    protected boolean enabled;

    @Path("weight")
    @SpecIntInRange(min = 0, max = Short.MAX_VALUE)
    protected int weight;

    @Path("min_group_count")
    @SpecIntInRange(min = 1, max = Short.MAX_VALUE)
    protected int minGroupCount;

    @Path("max_group_count")
    @SpecIntInRange(min = 1, max = Short.MAX_VALUE)
    protected int maxGroupCount;

    public Mob(String entityType, boolean enabled, int weight, int minGroupCount, int maxGroupCount) {
        this(entityType);
        this.enabled = enabled;
        this.weight = weight;
        this.minGroupCount = minGroupCount;
        this.maxGroupCount = maxGroupCount;
    }

    public Mob(String entityType) {
        ResourceLocation resourceLocation = new ResourceLocation(entityType);
        if (ForgeRegistries.ENTITIES.containsKey(resourceLocation)) {
            this.entityType = ForgeRegistries.ENTITIES.getValue(resourceLocation);
        }
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getWeight() {
        return weight;
    }

    public int getMinGroupCount() {
        return minGroupCount;
    }

    public int getMaxGroupCount() {
        return maxGroupCount;
    }

}
