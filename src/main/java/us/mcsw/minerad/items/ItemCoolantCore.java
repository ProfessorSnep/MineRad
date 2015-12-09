package us.mcsw.minerad.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import us.mcsw.core.ItemMR;
import us.mcsw.core.util.NumbersUtil;
import us.mcsw.minerad.init.ModItems;

public class ItemCoolantCore extends ItemMR {

	static final int MAX_DMG = 1200;

	public ItemCoolantCore() {
		super("coolantCore");

		setMaxDamage(MAX_DMG);
		setMaxStackSize(1);
		setNoRepair();
	}

	// TODO temp
	@Override
	public ItemStack onItemRightClick(ItemStack it, World w, EntityPlayer pl) {
		return damage(it, pl.isSneaking() ? 100 : 5);
	}

	public ItemStack damage(ItemStack it, int am) {
		if (it.getItemDamage() >= MAX_DMG - am) {
			ItemStack ret = new ItemStack(ModItems.emptyCore);
			return ret;
		} else {
			it.setItemDamage(it.getItemDamage() + am);
		}
		return it;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack it, EntityPlayer p, List list, boolean n) {
		super.addInformation(it, p, list, n);
		list.add("Used to cool down reactors");
		list.add("Capacity: " + NumbersUtil.roundDouble((((double) MAX_DMG - it.getItemDamage()) / (MAX_DMG / 100)), 1)
				+ "%");
	}

}
