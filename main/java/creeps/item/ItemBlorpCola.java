package creeps.item;

import creeps.CreepMain;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraft.entity.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;

public class ItemBlorpCola extends Item{
	
	
	
	public ItemBlorpCola()
    {
        this.maxStackSize = 24;
        this.setUnlocalizedName(getUnlocalizedName());
        
        
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
            playerIn.heal(1);
        }
        
        
        playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return stack.stackSize <= 0 ? new ItemStack(CreepMain.ItemEmptyCan) : stack;
        
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }

   public ItemStack onItemRightClick(World worldIn, EntityPlayer playerIn, ItemStack itemStackIn){
	   worldIn.playSoundAtEntity(playerIn, "creeps:blorpcola", 1.0F, 0.6F);
	   return itemStackIn;
   }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
        
        return itemStackIn;
    }
}