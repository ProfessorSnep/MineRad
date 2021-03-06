package us.mcsw.core;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import us.mcsw.core.util.ItemUtil;
import us.mcsw.core.util.LogUtil;
import us.mcsw.minerad.MineRad;
import us.mcsw.minerad.ref.TextureReference;

public abstract class BlockMRMachine extends BlockMR implements ITileEntityProvider {

	int id;

	public BlockMRMachine(int id, Material mat, String unloc) {
		super(mat, unloc);
		this.id = id;
		isBlockContainer = true;
	}

	public static IIcon side = null, top = null;

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		side = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "machineSide");
		top = reg.registerIcon(TextureReference.RESOURCE_PREFIX + "machineTop");
	}

	@Override
	public IIcon getIcon(IBlockAccess ba, int x, int y, int z, int s) {
		if (s == 0 || s == 1) {
			return top;
		}
		if (s == ba.getBlockMetadata(x, y, z)) {
			return super.getIcon(ba, x, y, z, s);
		}
		return side;
	}

	@Override
	public IIcon getIcon(int s, int m) {
		if (s == 0 || s == 1) {
			return top;
		}
		if (s == (m == 0 ? 3 : m)) {
			return super.getIcon(s, m);
		}
		return side;
	}

	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase e, ItemStack it) {
		int l = MathHelper.floor_double((double) (e.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l == 0) {
			w.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if (l == 1) {
			w.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if (l == 2) {
			w.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (l == 3) {
			w.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}

	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		setDefaultOrientation(w, x, y, z);
		super.onBlockAdded(w, x, y, z);
	}

	public void setDefaultOrientation(World w, int x, int y, int z) {
		if (!w.isRemote) {
			Block b1 = w.getBlock(x, y, z - 1);
			Block b2 = w.getBlock(x, y, z + 1);
			Block b3 = w.getBlock(x - 1, y, z);
			Block b4 = w.getBlock(x + 1, y, z);
			byte b0 = 3;

			if (b1.func_149730_j() && !b2.func_149730_j()) {
				b0 = 3;
			}

			if (b2.func_149730_j() && !b1.func_149730_j()) {
				b0 = 2;
			}

			if (b3.func_149730_j() && !b4.func_149730_j()) {
				b0 = 5;
			}

			if (b4.func_149730_j() && !b3.func_149730_j()) {
				b0 = 4;
			}

			w.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer pl, int m, float cx, float cy,
			float cz) {
		TileEntity te = w.getTileEntity(x, y, z);
		if (te == null || pl.isSneaking())
			return false;

		pl.openGui(MineRad.ins, id, w, x, y, z);
		return true;
	}

	@Override
	public void breakBlock(World w, int x, int y, int z, Block b, int m) {
		TileEntity te = w.getTileEntity(x, y, z);
		ItemUtil.dropItemsFromInventory(te);
		super.breakBlock(w, x, y, z, b, m);
	}

}
