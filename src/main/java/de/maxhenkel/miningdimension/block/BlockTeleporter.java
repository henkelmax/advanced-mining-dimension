package de.maxhenkel.miningdimension.block;

import de.maxhenkel.corelib.block.IItemBlock;
import de.maxhenkel.miningdimension.Main;
import de.maxhenkel.miningdimension.dimension.MiningDimensionTeleporter;
import de.maxhenkel.miningdimension.tileentity.TileentityTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class BlockTeleporter extends Block implements EntityBlock, IItemBlock {

    public BlockTeleporter() {
        super(Properties.of(Material.WOOD).strength(3F).sound(SoundType.WOOD));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties()/*.tab(CreativeModeTab.TAB_DECORATIONS)*/); // TODO Fix creative tab
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (player instanceof ServerPlayer) {
            transferPlayer((ServerPlayer) player, pos);
        }
        return InteractionResult.SUCCESS;
    }

    public boolean transferPlayer(ServerPlayer player, BlockPos pos) {
        if (player.getVehicle() != null || player.isVehicle()) {
            return false;
        }

        if (player.level.dimension().equals(Main.MINING_DIMENSION)) {
            ServerLevel teleportWorld = player.server.getLevel(Main.SERVER_CONFIG.overworldDimension);
            if (teleportWorld == null) {
                Main.LOGGER.error("Could not find overworld dimension '{}'.", Main.SERVER_CONFIG.overworldDimension.registry());
                return false;
            }
            player.changeDimension(teleportWorld, new MiningDimensionTeleporter(pos));
        } else if (player.level.dimension().equals(Main.SERVER_CONFIG.overworldDimension)) {
            ServerLevel teleportWorld = player.server.getLevel(Main.MINING_DIMENSION);
            if (teleportWorld == null) {
                Main.LOGGER.error("Could not find mining dimension.");
                return false;
            }
            player.changeDimension(teleportWorld, new MiningDimensionTeleporter(pos));
        } else {
            player.displayClientMessage(Component.translatable("message.wrong_dimension"), true);
        }

        return true;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileentityTeleporter(pos, state);
    }
}
