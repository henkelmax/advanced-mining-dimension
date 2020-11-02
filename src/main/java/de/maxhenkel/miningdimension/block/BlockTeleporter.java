package de.maxhenkel.miningdimension.block;

import de.maxhenkel.corelib.block.IItemBlock;
import de.maxhenkel.miningdimension.Main;
import de.maxhenkel.miningdimension.dimension.MiningDimensionTeleporter;
import de.maxhenkel.miningdimension.tileentity.TileentityTeleporter;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class BlockTeleporter extends Block implements ITileEntityProvider, IItemBlock {

    public BlockTeleporter() {
        super(Properties.create(Material.WOOD).hardnessAndResistance(3F).sound(SoundType.WOOD));
        setRegistryName(new ResourceLocation(Main.MODID, "teleporter"));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(getRegistryName());
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (player instanceof ServerPlayerEntity) {
            transferPlayer((ServerPlayerEntity) player, pos);
        }
        return ActionResultType.SUCCESS;
    }

    public boolean transferPlayer(ServerPlayerEntity player, BlockPos pos) {
        if (player.getRidingEntity() != null || player.isBeingRidden()) {
            return false;
        }

        if (player.world.getDimensionKey().equals(Main.MINING_DIMENSION)) {
            ServerWorld teleportWorld = player.server.getWorld(Main.SERVER_CONFIG.overworldDimension);
            if (teleportWorld == null) {
                Main.LOGGER.error("Could not find overworld dimension '{}'.", Main.SERVER_CONFIG.overworldDimension.getRegistryName());
                return false;
            }
            player.changeDimension(teleportWorld, new MiningDimensionTeleporter(pos));
        } else if (player.world.getDimensionKey().equals(Main.SERVER_CONFIG.overworldDimension)) {
            ServerWorld teleportWorld = player.server.getWorld(Main.MINING_DIMENSION);
            if (teleportWorld == null) {
                Main.LOGGER.error("Could not find mining dimension.");
                sendBugMessage(player);
                return false;
            }
            player.changeDimension(teleportWorld, new MiningDimensionTeleporter(pos));
        } else {
            player.sendStatusMessage(new TranslationTextComponent("message.wrong_dimension"), true);
        }

        return true;
    }

    private void sendBugMessage(PlayerEntity player) {
        player.sendMessage(new StringTextComponent("The Mining Dimension hasn't been created yet. This is a ").append(TextComponentUtils.wrapWithSquareBrackets(new StringTextComponent("BUG")).modifyStyle((style) -> style
                .applyFormatting(TextFormatting.RED)
                .setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://bugs.mojang.com/browse/MC-195468"))
                .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent("https://bugs.mojang.com/browse/MC-195468")))
        )).appendString(". "), Util.DUMMY_UUID);
        player.sendMessage(new StringTextComponent("A workaround can be found ").append(TextComponentUtils.wrapWithSquareBrackets(new StringTextComponent("HERE"))
                .modifyStyle((style) -> style
                        .applyFormatting(TextFormatting.GREEN)
                        .setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/henkelmax/advanced-mining-dimension/issues/14#issuecomment-707924771"))
                        .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent("https://github.com/henkelmax/advanced-mining-dimension/issues/14#issuecomment-707924771")))
                )).appendString("."), Util.DUMMY_UUID);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader world) {
        return new TileentityTeleporter();
    }

}
