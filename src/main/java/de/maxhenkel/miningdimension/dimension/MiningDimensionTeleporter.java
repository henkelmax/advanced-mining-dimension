package de.maxhenkel.miningdimension.dimension;

import de.maxhenkel.miningdimension.Main;
import de.maxhenkel.miningdimension.tileentity.TileentityTeleporter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class MiningDimensionTeleporter implements ITeleporter {

    private BlockPos pos;

    public MiningDimensionTeleporter(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        Entity e = repositionEntity.apply(false);
        if (!(e instanceof ServerPlayerEntity)) {
            return e;
        }
        ServerPlayerEntity player = (ServerPlayerEntity) e;
        Chunk chunk = (Chunk) destWorld.getChunk(pos);
        BlockPos teleporterPos = findPortalInChunk(chunk);

        if (teleporterPos == null) {
            if (destWorld.getDimensionKey().equals(Main.MINING_DIMENSION)) {
                teleporterPos = placeTeleporterMining(destWorld, chunk);
            } else {
                teleporterPos = placeTeleporterOverworld(destWorld, chunk);
            }
        }
        if (teleporterPos == null) {
            return e;
        }

        player.addExperienceLevel(0);
        player.setPositionAndUpdate(teleporterPos.getX() + 0.5D, teleporterPos.getY() + 1D, teleporterPos.getZ() + 0.5D);
        return e;
    }

    private BlockPos findPortalInChunk(Chunk chunk) {
        for (TileEntity tile : chunk.getTileEntityMap().values()) {
            if (tile instanceof TileentityTeleporter) {
                BlockPos pos = tile.getPos();
                if (chunk.getBlockState(pos.up()).isAir()) {
                    return pos;
                }
            }
        }
        return null;
    }

    private BlockPos placeTeleporterMining(ServerWorld world, Chunk chunk) {
        BlockPos.Mutable pos = new BlockPos.Mutable();
        for (int y = 0; y < 255; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    pos.setPos(x, y, z);
                    if (chunk.getBlockState(pos).isAir() && chunk.getBlockState(pos.up(1)).isAir() && chunk.getBlockState(pos.up(2)).isAir()) {
                        BlockPos absolutePos = chunk.getPos().asBlockPos().add(pos.getX(), pos.getY(), pos.getZ());
                        world.setBlockState(absolutePos, Main.TELEPORTER.getDefaultState());
                        return absolutePos;
                    }
                }
            }
        }

        for (int y = 0; y < 255; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    pos.setPos(x, y, z);
                    if (isAirOrStone(chunk, pos) && isAirOrStone(chunk, pos.up(1)) && isAirOrStone(chunk, pos.up(2))) {
                        BlockPos absolutePos = chunk.getPos().asBlockPos().add(pos.getX(), pos.getY(), pos.getZ());
                        if (isReplaceable(world, absolutePos.up(3)) &&
                                isReplaceable(world, absolutePos.up(1).offset(Direction.NORTH)) &&
                                isReplaceable(world, absolutePos.up(1).offset(Direction.NORTH)) &&
                                isReplaceable(world, absolutePos.up(1).offset(Direction.SOUTH)) &&
                                isReplaceable(world, absolutePos.up(1).offset(Direction.EAST)) &&
                                isReplaceable(world, absolutePos.up(1).offset(Direction.WEST)) &&
                                isReplaceable(world, absolutePos.up(2).offset(Direction.NORTH)) &&
                                isReplaceable(world, absolutePos.up(2).offset(Direction.SOUTH)) &&
                                isReplaceable(world, absolutePos.up(2).offset(Direction.EAST)) &&
                                isReplaceable(world, absolutePos.up(2).offset(Direction.WEST))
                        ) {
                            world.setBlockState(absolutePos, Main.TELEPORTER.getDefaultState());
                            world.setBlockState(absolutePos.up(1), Blocks.AIR.getDefaultState());
                            world.setBlockState(absolutePos.up(2), Blocks.AIR.getDefaultState());
                            world.setBlockState(absolutePos.up(3), Blocks.STONE.getDefaultState());
                            world.setBlockState(absolutePos.up(1).offset(Direction.NORTH), Blocks.STONE.getDefaultState());
                            world.setBlockState(absolutePos.up(1).offset(Direction.SOUTH), Blocks.STONE.getDefaultState());
                            world.setBlockState(absolutePos.up(1).offset(Direction.EAST), Blocks.STONE.getDefaultState());
                            world.setBlockState(absolutePos.up(1).offset(Direction.WEST), Blocks.STONE.getDefaultState());
                            world.setBlockState(absolutePos.up(2).offset(Direction.NORTH), Blocks.STONE.getDefaultState());
                            world.setBlockState(absolutePos.up(2).offset(Direction.SOUTH), Blocks.STONE.getDefaultState());
                            world.setBlockState(absolutePos.up(2).offset(Direction.EAST), Blocks.STONE.getDefaultState());
                            world.setBlockState(absolutePos.up(2).offset(Direction.WEST), Blocks.STONE.getDefaultState());
                            return absolutePos;
                        }
                    }
                }
            }
        }

        return null;
    }

    private boolean isAirOrStone(Chunk chunk, BlockPos pos) {
        BlockState state = chunk.getBlockState(pos);
        return state.getBlock().equals(Blocks.STONE) || state.isAir();
    }

    private boolean isReplaceable(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock().equals(Blocks.STONE) ||
                state.getBlock().equals(Blocks.GRANITE) ||
                state.getBlock().equals(Blocks.ANDESITE) ||
                state.getBlock().equals(Blocks.DIORITE) ||
                state.getBlock().equals(Blocks.DIRT) ||
                state.getBlock().equals(Blocks.GRAVEL) ||
                state.getBlock().equals(Blocks.LAVA) ||
                state.isAir();
    }

    private BlockPos placeTeleporterOverworld(ServerWorld world, Chunk chunk) {
        BlockPos.Mutable pos = new BlockPos.Mutable();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 63; y < 255; y++) {
                    pos.setPos(x, y, z);
                    if (chunk.getBlockState(pos).isAir() && chunk.getBlockState(pos.up(1)).isAir()) {
                        BlockPos absolutePos = chunk.getPos().asBlockPos().add(pos.getX(), pos.getY(), pos.getZ());
                        world.setBlockState(absolutePos, Main.TELEPORTER.getDefaultState());
                        return absolutePos;
                    }
                }
            }
        }
        return null;
    }

}
