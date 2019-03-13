package de.maxhenkel.miningworld.dimension;

import de.maxhenkel.miningworld.block.ModBlocks;
import de.maxhenkel.miningworld.tileentity.TileentityTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class TeleporterMiningDimension extends TeleporterBase {

	public TeleporterMiningDimension(final WorldServer world) {
		super(world);
	}

	@Override
	public void placeInPortal(Entity entity, float rotationYaw) {
		if(entity.world.isRemote){
			return;
		}
		BlockPos teleporterPos=new BlockPos(entity.posX, 0, entity.posZ);
		TileentityTeleporter teleporter = findPortalInChunk(world.getChunkFromBlockCoords(teleporterPos));

		if (teleporter == null) {
			BlockPos pos = world.getTopSolidOrLiquidBlock(teleporterPos);

			if (world.isAirBlock(pos)) {
				world.setBlockState(pos, ModBlocks.TELEPORTER.getDefaultState(), 3);
				entity.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
			}
		} else {
			BlockPos pos = teleporter.getPos();
			entity.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
		}
	}

}