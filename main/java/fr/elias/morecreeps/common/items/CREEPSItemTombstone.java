package fr.elias.morecreeps.common.items;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class CREEPSItemTombstone extends Item
{
    public int zPosition;
    public static Random random = new Random();

    public CREEPSItemTombstone(int j)
    {
        super();
        maxStackSize = 1;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        entityplayer.swingItem();

        if (itemstack.stackSize < 1)
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            itemstack.stackSize = 0;
        }

        world.playSoundAtEntity(entityplayer, "morecreeps:evileggcluck", 1.0F, 1.0F);

        if (world.isRemote);

        return itemstack;
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setInteger("TileZ", zPosition);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        zPosition = nbttagcompound.getInteger("TileZ");
    }
}
