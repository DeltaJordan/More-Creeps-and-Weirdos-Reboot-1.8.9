package fr.elias.morecreeps.common.items;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.entity.CREEPSEntityHorseHead;

public class CREEPSItemHorseHeadGem extends Item
{
    public static Random random = new Random();

    public CREEPSItemHorseHeadGem()
    {
        super();
        maxStackSize = 16;
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

        world.playSoundAtEntity(entityplayer, "morecreeps:horseheadgem", 1.0F, 1.0F);

        if (!world.isRemote)
        {
            double d = -MathHelper.sin((entityplayer.rotationYaw * (float)Math.PI) / 180F);
            double d1 = MathHelper.cos((entityplayer.rotationYaw * (float)Math.PI) / 180F);
            CREEPSEntityHorseHead creepsentityhorsehead = new CREEPSEntityHorseHead(world);
            creepsentityhorsehead.setLocationAndAngles(entityplayer.posX + d * 1.0D, entityplayer.posY + 1.0D, entityplayer.posZ + d1 * 1.0D, entityplayer.rotationYaw, 0.0F);
            world.spawnEntityInWorld(creepsentityhorsehead);
        }

        return itemstack;
    }
}
