package fr.elias.morecreeps.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CREEPSItemBandAid extends Item
{
    private int healAmount;

    public CREEPSItemBandAid()
    {
        super();
        healAmount = 4;
        maxStackSize = 24;
    }
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        if (entityplayer.getHealth() < 20)
        {
            world.playSoundAtEntity(entityplayer, "morecreeps:bandaid", 1.0F, 1.0F);
            itemstack.stackSize--;
            entityplayer.heal(healAmount);
        }

        return itemstack;
    }
}
