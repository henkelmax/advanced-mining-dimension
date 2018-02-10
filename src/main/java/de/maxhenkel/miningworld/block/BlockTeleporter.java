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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockTeleporter extends BlockContainer {

	public BlockTeleporter() {
		super(Material.WOOD);
		setHardness(3.0F);
		setUnlocalizedName("teleporter");
		setRegistryName("teleporter");
		setCreativeTab(CreativeTabs.DECORATIONS);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {

			if(playerIn instanceof EntityPlayerMP){
			    new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=3; i>=0; i--){
                            if(i<=0){
                                playerIn.getServer().addScheduledTask(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((EntityPlayerMP) playerIn).world.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.5F, 1.5F);
                                        playerIn.sendStatusMessage(new TextComponentString(" "), true);
                                    }
                                });
                            }else{
                                int finalI = i;
                                playerIn.getServer().addScheduledTask(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((EntityPlayerMP) playerIn).world.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.5F, 1F);
                                        playerIn.sendStatusMessage(new TextComponentString(String.valueOf(finalI)), true);
                                    }
                                });
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {}
                        }
                        playerIn.getServer().addScheduledTask(new Runnable() {
                            @Override
                            public void run() {
                                if(pos.getDistance((int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ)>3D){
                                    playerIn.sendStatusMessage(new TextComponentTranslation("message.too_far"), true);
                                }else{
                                    playerIn.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                                    transferPlayer((EntityPlayerMP) playerIn);
                                }
                            }
                        });
                    }
                }).start();
			    return true;
            }
            return true;
		}else{
			return true;
		}
	}

	public boolean transferPlayer(EntityPlayerMP playerMP) {
		if (playerMP.isRiding() || playerMP.isBeingRidden()) {
			return false;
		}

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
