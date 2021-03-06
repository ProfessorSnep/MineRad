package us.mcsw.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import us.mcsw.minerad.init.AchievementsInit;
import us.mcsw.minerad.tiles.TileFissionReactor;
import us.mcsw.minerad.tiles.TileFusionReactor;

public abstract class TileMultiblock extends TileMRInventory {

	public TileMultiblock(int size) {
		super(size);
	}

	private boolean isMaster, hasMaster;
	private int masterX, masterY, masterZ;

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!worldObj.isRemote) {
			if (hasMaster()) {
				if (isMaster() && checkMultiblockForm()) {
					onUpdate();
				}
			} else {
				if (checkMultiblockForm()) {
					EntityPlayer pl = worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 15);
					if (pl != null) {
						if (this instanceof TileFissionReactor) {
							pl.addStat(AchievementsInit.fissionReactor, 1);
						} else if (this instanceof TileFusionReactor) {
							pl.addStat(AchievementsInit.fusionReactor, 1);
						}
					}
					setupStructure();
				}
			}
		}
	}

	public abstract void onUpdate();

	public abstract boolean checkMultiblockForm();

	public abstract void setupStructure();

	public void reset() {
		masterX = 0;
		masterY = 0;
		masterZ = 0;
		isMaster = false;
		hasMaster = false;
	}

	public boolean checkForMaster() {
		TileEntity tile = worldObj.getTileEntity(masterX, masterY, masterZ);
		return (tile != null && (tile instanceof TileMultiblock));
	}

	public TileMultiblock getMaster() {
		if (checkForMaster()) {
			TileEntity tile = worldObj.getTileEntity(masterX, masterY, masterZ);
			if (tile != null && tile instanceof TileMultiblock) {
				return (TileMultiblock) tile;
			}
		}
		return null;
	}

	public abstract void resetStructure();

	public abstract void masterWriteToNBT(NBTTagCompound tag);

	public abstract void masterWriteSyncable(NBTTagCompound data);

	public abstract void masterReadFromNBT(NBTTagCompound tag);

	public abstract void masterReadSyncable(NBTTagCompound data);

	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);

		writeSyncable(data);

		if (hasMaster() && isMaster())
			masterWriteToNBT(data);
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);

		readSyncable(data);

		if (hasMaster() && isMaster())
			masterReadFromNBT(data);
	}

	private void writeSyncable(NBTTagCompound data) {
		data.setInteger("masterX", masterX);
		data.setInteger("masterY", masterY);
		data.setInteger("masterZ", masterZ);
		data.setBoolean("isMaster", isMaster);
		data.setBoolean("hasMaster", hasMaster);
		if (hasMaster() && isMaster())
			masterWriteSyncable(data);
	}

	private void readSyncable(NBTTagCompound data) {
		masterX = data.getInteger("masterX");
		masterY = data.getInteger("masterY");
		masterZ = data.getInteger("masterZ");
		isMaster = data.getBoolean("isMaster");
		hasMaster = data.getBoolean("hasMaster");
		if (hasMaster() && isMaster())
			masterReadSyncable(data);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound syncData = new NBTTagCompound();
		writeSyncable(syncData);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, syncData);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readSyncable(pkt.func_148857_g());
	}

	public boolean hasMaster() {
		return hasMaster;
	}

	public boolean isMaster() {
		return isMaster;
	}

	public int getMasterX() {
		return masterX;
	}

	public int getMasterY() {
		return masterY;
	}

	public int getMasterZ() {
		return masterZ;
	}

	public void setIsMaster(boolean bool) {
		isMaster = bool;
	}
	
	public void setHasMaster(boolean bool) {
		hasMaster = bool;
	}

	public void setMasterCoords(int x, int y, int z) {
		masterX = x;
		masterY = y;
		masterZ = z;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (hasMaster() && !isMaster()) {
			return getMaster().getStackInSlot(i);
		}
		return super.getStackInSlot(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int a) {
		if (hasMaster() && !isMaster()) {
			return getMaster().decrStackSize(i, a);
		}
		return super.decrStackSize(i, a);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (hasMaster() && !isMaster()) {
			return getMaster().getStackInSlotOnClosing(i);
		}
		return super.getStackInSlotOnClosing(i);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack it) {
		if (hasMaster() && !isMaster()) {
			getMaster().setInventorySlotContents(i, it);
			return;
		}
		super.setInventorySlotContents(i, it);
	}
}
