package fr.elias.morecreeps.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CREEPSItemPopsicle extends Item
{
    private int healAmount;

    public CREEPSItemPopsicle()
    {
        super();
        healAmount = 4;
        maxStackSize = 16;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack itemstack)
    {
        return EnumAction.EAT;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        entityplayer.swingItem();
        world.playSoundAtEntity(entityplayer, "morecreeps:lick", 1.0F, 1.0F);
        itemstack.stackSize--;
        entityplayer.heal(healAmount);
        return itemstack;
    }
}
