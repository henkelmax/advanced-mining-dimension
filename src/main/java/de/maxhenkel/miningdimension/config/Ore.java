package de.maxhenkel.miningdimension.config;

import com.electronwill.nightconfig.core.conversion.Path;
import com.electronwill.nightconfig.core.conversion.SpecIntInRange;
import de.maxhenkel.corelib.block.BlockUtils;
import de.maxhenkel.miningdimension.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class Ore {

    protected transient BlockState oreBlock;

    @Path("enabled")
    protected boolean enabled;

    @Path("max_height")
    @SpecIntInRange(min = 1, max = 256)
    protected int maxHeight;

    @Path("count")
    @SpecIntInRange(min = 1, max = 1024)
    protected int count;

    @Path("size")
    @SpecIntInRange(min = 1, max = 128)
    protected int size;

    public Ore(String oreBlock, boolean enabled, int maxHeight, int count, int size) {
        this(oreBlock);
        this.enabled = enabled;
        this.maxHeight = maxHeight;
        this.count = count;
        this.size = size;
    }

    public Ore(String oreBlock) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(oreBlock));
        if (block == null || BlockUtils.isAir(block)) {
            Main.LOGGER.error("Could not find block with the id '" + oreBlock + "'. This block will not get added to the mining world.");
        } else {
            this.oreBlock = block.getDefaultState();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getCount() {
        return count;
    }

    public int getSize() {
        return size;
    }

    @Nullable
    public BlockState getOreBlock() {
        return oreBlock;
    }

}
