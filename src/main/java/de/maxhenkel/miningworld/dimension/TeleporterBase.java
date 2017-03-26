package de.maxhenkel.miningworld.dimension;

import net.minecraft.world.chunk.Chunk;
import de.maxhenkel.miningworld.tileentity.TileentityTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraft.world.Teleporter;

public class TeleporterBase extends Teleporter {
	protected final WorldServer world;

	public TeleporterBase(WorldServer world) {
		super(world);
		this.world = world;
	}
	
	public TileentityTeleporter findPortalInChunk(Chunk chunk) {
		for (TileEntity tile : chunk.getTileEntityMap().values()) {
			if (tile instanceof TileentityTeleporter) {
				return (TileentityTeleporter) tile;
			}
		}
		return null;
	}

	public TileentityTeleporter findPortalInChunk(int x, int z) {
		Chunk chunk = world.getChunkFromChunkCoords(x, z);
		return findPortalInChunk(chunk);
	}
	
	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw) {
		
	}
	
	@Override
	public boolean makePortal(Entity entityIn) {
		return false;
	}
	
	@Override
	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
		return false;
	}
	
	@Override
	public void removeStalePortalLocations(long worldTime) {
		
	}
}