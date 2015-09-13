package creeps.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import creeps.CreepMain;
import creeps.Reference;

public class ItemWelcome extends Item {

    private final String name = "welcome";

    public ItemWelcome() {

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
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn,
	    EntityPlayer playerIn) {

	worldIn.playSoundAtEntity(playerIn, "creeps:welcome", 1.0F, 1.0F);

	return itemStackIn;
    }
}
