package creeps.item;

import creeps.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBattery extends Item{
	
	private final String name = "battery";
	public ItemBattery(){
	setUnlocalizedName(Reference.MOD_ID + "_" + name);
	}
	
	public String getName(){
		
	return name;
	
	}
	
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 2;
    }
	
	public ItemStack onItemRightClick(World worldIn, ItemStack itemStackIn, EntityPlayer playerIn)
    {
		 playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
		if (!playerIn.capabilities.isCreativeMode)
        {
            --itemStackIn.stackSize;
        }
        
        
        	if (playerIn.getHealth() <= 5){
    	        playerIn.inventory.clear();
    	        playerIn.setHealth(playerIn.getHealth()-playerIn.getHealth());
    	        }
    	        else{
    	        playerIn.setHealth(playerIn.getHealth()-5);
    	        }
        

		return itemStackIn;
		
    }
	
}
