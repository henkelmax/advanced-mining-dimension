package de.maxhenkel.miningdimension.tileentity;

import de.maxhenkel.miningdimension.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileentityTeleporter extends BlockEntity {

    public TileentityTeleporter(BlockPos pos, BlockState state) {
        super(Main.TELEPORTER_TILEENTITY, pos, state);
    }

}
