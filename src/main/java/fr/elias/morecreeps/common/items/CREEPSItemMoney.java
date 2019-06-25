package fr.elias.morecreeps.common.items;

import java.util.Random;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import fr.elias.morecreeps.common.entity.CREEPSEntityMoney;
import fr.elias.morecreeps.common.entity.CREEPSEntityTrophy;

public class CREEPSItemMoney extends Item
{
    public static Random rand = new Random();

    public CREEPSItemMoney()
    {
        super();
        maxStackSize = 50;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        checkAchievements(world, entityplayer);
        itemstack.stackSize--;
        world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote)
        {
            world.spawnEntityInWorld(new CREEPSEntityMoney(world, entityplayer));
        }

        return itemstack;
    }

    public void checkAchievements(World world, EntityPlayer entityplayer)
    {
        Object obj = null;
        ItemStack aitemstack[] = entityplayer.inventory.mainInventory;
        int i = 0;

        for (int j = 0; j < aitemstack.length; j++)
        {
            ItemStack itemstack = aitemstack[j];

            if (itemstack != null && itemstack.getItem() == MoreCreepsAndWeirdos.money)
            {
                i += itemstack.stackSize;
            }
        }

        boolean flag = false;

        if (i > 99)
        {
            flag = true;
            MoreCreepsAndWeirdos.proxy.confettiA(entityplayer, world);
            entityplayer.addStat(MoreCreepsAndWeirdos.achieve100bucks, 1);
        }

        if (i > 499)
        {
            flag = true;
            MoreCreepsAndWeirdos.proxy.confettiA(entityplayer, world);
            entityplayer.addStat(MoreCreepsAndWeirdos.achieve500bucks, 1);
        }

        if (i > 999)
        {
            flag = true;
            MoreCreepsAndWeirdos.proxy.confettiA(entityplayer, world);
            entityplayer.addStat(MoreCreepsAndWeirdos.achieve1000bucks, 1);
        }

        if (flag)
        {
            world.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
        }
    }
}
