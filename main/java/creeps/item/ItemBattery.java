package creeps.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import creeps.CreepMain;
import creeps.Reference;

public class ItemBattery extends Item {

    private final String name = "battery";

    public ItemBattery() {
	setUnlocalizedName(Reference.MOD_ID + "_" + name);
	this.setCreativeTab(CreepMain.creepTab);
    }

    public String getName() {

	return name;

    }

    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack stack, World worldIn,
	    EntityPlayer playerIn) {

	if (!worldIn.isRemote) {
	    playerIn.setHealth(playerIn.getHealth() - 1);
	}

	playerIn.triggerAchievement(StatList.objectUseStats[Item
		.getIdFromItem(this)]);
	worldIn.playSoundAtEntity(playerIn, "creeps:mininggembad", 1.0F, 1.0F);
	return stack;

    }

}
