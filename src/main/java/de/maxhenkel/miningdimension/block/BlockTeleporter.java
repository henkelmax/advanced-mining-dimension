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
        super(Properties.of(Material.WOOD).strength(3F).sound(SoundType.WOOD));
        setRegistryName(new ResourceLocation(Main.MODID, "teleporter"));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)).setRegistryName(getRegistryName());
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (player instanceof ServerPlayerEntity) {
            transferPlayer((ServerPlayerEntity) player, pos);
        }
        return ActionResultType.SUCCESS;
    }

    public boolean transferPlayer(ServerPlayerEntity player, BlockPos pos) {
        if (player.getVehicle() != null || player.isVehicle()) {
            return false;
        }

        if (player.level.dimension().equals(Main.MINING_DIMENSION)) {
            ServerWorld teleportWorld = player.server.getLevel(Main.SERVER_CONFIG.overworldDimension);
            if (teleportWorld == null) {
                Main.LOGGER.error("Could not find overworld dimension '{}'.", Main.SERVER_CONFIG.overworldDimension.getRegistryName());
                return false;
            }
            player.changeDimension(teleportWorld, new MiningDimensionTeleporter(pos));
        } else if (player.level.dimension().equals(Main.SERVER_CONFIG.overworldDimension)) {
            ServerWorld teleportWorld = player.server.getLevel(Main.MINING_DIMENSION);
            if (teleportWorld == null) {
                Main.LOGGER.error("Could not find mining dimension.");
                sendBugMessage(player);
                return false;
            }
            player.changeDimension(teleportWorld, new MiningDimensionTeleporter(pos));
        } else {
            player.displayClientMessage(new TranslationTextComponent("message.wrong_dimension"), true);
        }

        return true;
    }

    private void sendBugMessage(PlayerEntity player) {
        player.sendMessage(new StringTextComponent("The Mining Dimension hasn't been created yet. This is a ").append(TextComponentUtils.wrapInSquareBrackets(new StringTextComponent("BUG")).withStyle((style) -> style
                .applyFormat(TextFormatting.RED)
                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://bugs.mojang.com/browse/MC-195468"))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent("https://bugs.mojang.com/browse/MC-195468")))
        )).append(". "), Util.NIL_UUID);
        player.sendMessage(new StringTextComponent("A workaround can be found ").append(TextComponentUtils.wrapInSquareBrackets(new StringTextComponent("HERE"))
                .withStyle((style) -> style
                        .applyFormat(TextFormatting.GREEN)
                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/henkelmax/advanced-mining-dimension/issues/14#issuecomment-707924771"))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent("https://github.com/henkelmax/advanced-mining-dimension/issues/14#issuecomment-707924771")))
                )).append("."), Util.NIL_UUID);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader world) {
        return new TileentityTeleporter();
    }

}
