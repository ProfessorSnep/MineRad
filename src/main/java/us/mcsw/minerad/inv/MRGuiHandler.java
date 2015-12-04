package us.mcsw.minerad.inv;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import us.mcsw.minerad.tiles.TileMRMachine;
import us.mcsw.minerad.tiles.TileUraniumInfuser;

public class MRGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileUraniumInfuser) {
			return new ContainerUraniumInfuser(player.inventory, (TileUraniumInfuser) te);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileUraniumInfuser) {
			return new GuiUraniumInfuser(player.inventory, (TileUraniumInfuser) te);
		}
		return null;
	}

}
