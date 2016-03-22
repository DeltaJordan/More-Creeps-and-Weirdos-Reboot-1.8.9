package fr.elias.morecreeps.common.items;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.entity.CREEPSEntityRay;

public class CREEPSItemRayGun extends Item
{
    public static Random rand = new Random();

    public CREEPSItemRayGun()
    {
        super();
        maxStackSize = 1;
        setMaxDamage(64);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        world.playSoundAtEntity(entityplayer, "morecreeps:raygun", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote)
        {
            CREEPSEntityRay creepsentityray = new CREEPSEntityRay(world, entityplayer);
            itemstack.damageItem(2, entityplayer);
            world.spawnEntityInWorld(creepsentityray);
        }
        return itemstack;
    }
}
