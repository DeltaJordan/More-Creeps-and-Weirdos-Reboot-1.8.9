package fr.elias.morecreeps.common.items;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilEgg;

public class CREEPSItemEvilEgg extends Item
{
    public static Random random = new Random();

    public CREEPSItemEvilEgg()
    {
        super();
        maxStackSize = 44;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        itemstack.stackSize--;
        entityplayer.swingItem();

        if (itemstack.stackSize < 1)
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            itemstack.stackSize = 0;
        }

        world.playSoundAtEntity(entityplayer, "morecreeps:evileggcluck", 1.0F, 1.0F);

        if (!world.isRemote)
        {
            world.spawnEntityInWorld(new CREEPSEntityEvilEgg(world, entityplayer));
        }

        return itemstack;
    }
}
