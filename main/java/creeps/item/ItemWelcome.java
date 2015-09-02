package creeps.item;

import creeps.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWelcome extends Item{

		
		private final String name = "welcome";
		public ItemWelcome(){
			
		
		setUnlocalizedName(Reference.MOD_ID + "_" + name);
		}
		
		public String getName(){
			
		return name;
		
		}
		
		
		
	  

	    /**
	     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	     */
	    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	    {
	    	
	        worldIn.playSoundAtEntity(playerIn, "creeps:welcome", 1.0F, 1.0F);
	      
	        return itemStackIn;
	    }
	}

