package fr.elias.morecreeps.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CREEPSItemLolly extends Item
{
    private int healAmount;

    public CREEPSItemLolly()
    {
        super();
        healAmount = 2;
        maxStackSize = 32;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        world.playSoundAtEntity(entityplayer, "morecreeps:lolly", 1.0F, 1.0F);
        itemstack.stackSize--;
        entityplayer.heal(healAmount);
        return itemstack;
    }
}
