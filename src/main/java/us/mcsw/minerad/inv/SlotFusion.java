package us.mcsw.minerad.inv;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import us.mcsw.minerad.init.AchievementsInit;
import us.mcsw.minerad.init.FissionRecipes;
import us.mcsw.minerad.init.FusionRecipes;
import us.mcsw.minerad.init.ModItems;

public class SlotFusion extends Slot {

	public SlotFusion(IInventory inv, int i, int x, int y) {
		super(inv, i, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack it) {
		return FusionRecipes.hasRecipe(it);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

}
