package us.mcsw.minerad.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import us.mcsw.core.ItemMR;

public class ItemUraniumNugget extends ItemMR {

	public ItemUraniumNugget() {
		super("nuggetUranium");
	}
	
	@Override
	public EnumRarity getRarity(ItemStack it) {
		return EnumRarity.uncommon;
	}
	
}
