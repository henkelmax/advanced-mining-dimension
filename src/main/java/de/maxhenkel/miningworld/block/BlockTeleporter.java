package de.maxhenkel.miningworld.block;

import de.maxhenkel.miningworld.dimension.DimensionTypes;
import de.maxhenkel.miningworld.dimension.TeleporterMiningDimension;
import de.maxhenkel.miningworld.tileentity.TileentityTeleporter;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTeleporter extends BlockContainer {

	public BlockTeleporter() {
		super(Material.ROCK);
		setHardness(3.0F);
		setUnlocalizedName("teleporter");
		setRegistryName("teleporter");
		setCreativeTab(CreativeTabs.DECORATIONS);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
			return transferPlayer(worldIn, pos, playerIn);
		}else{
			return true;
		}
	}

	public boolean transferPlayer(final World world, BlockPos pos, EntityPlayer player) {
		if (player.isRiding() || player.isBeingRidden() || !(player instanceof EntityPlayerMP)) {
			return false;
		}

		EntityPlayerMP playerMP = (EntityPlayerMP) player;
		
		if (playerMP.dimension == DimensionTypes.MINING_DIMENSION.getId()) {
			playerMP.mcServer.getPlayerList().transferPlayerToDimension(playerMP, 0,
					new TeleporterMiningDimension(playerMP.mcServer.getWorld(0)));
		} else {
			playerMP.mcServer.getPlayerList().transferPlayerToDimension(playerMP,
					DimensionTypes.MINING_DIMENSION.getId(), new TeleporterMiningDimension(
							playerMP.mcServer.getWorld(DimensionTypes.MINING_DIMENSION.getId())));
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileentityTeleporter();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

}
