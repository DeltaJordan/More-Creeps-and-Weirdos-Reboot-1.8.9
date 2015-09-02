package creeps.item;

import creeps.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemDonut extends Item{
	
		private final String name = "donut";
		public ItemDonut(){
			
		
		setUnlocalizedName(Reference.MOD_ID + "_" + name);
		}
		
		public String getName(){
			
		return name;
		
		}
		
		
		public EnumAction getItemUseAction(ItemStack stack)
	    {
	        return EnumAction.EAT;
	    }
		

	    /**
	     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
	     * the Item before the action is complete.
	     */
		
	    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
	    {
	        if (!playerIn.capabilities.isCreativeMode)
	        {
	            --stack.stackSize;
	        }
	        
	        

	        if (!worldIn.isRemote)
	        {
	            playerIn.addPotionEffect(new PotionEffect(Potion.saturation.id, 4, 0));
	        }
	     
	        
	        playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
	        return stack;
	        
	    }

	    /**
	     * How long it takes to use or consume an item
	     */
	    public int getMaxItemUseDuration(ItemStack stack)
	    {
	        return 32;
	    }

	  

	    /**
	     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	     */
	    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	    {
	        playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
	        worldIn.playSoundAtEntity(playerIn, "creeps:chew", 1.0F, 1.0F);
	        
	        return itemStackIn;
	    }
	}


