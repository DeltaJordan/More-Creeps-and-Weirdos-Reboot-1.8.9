package fr.elias.morecreeps.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class CREEPSItemBattery extends Item
{
    private int healAmount;

    public CREEPSItemBattery()
    {
        super();
        maxStackSize = 16;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        world.playSoundAtEntity(entityplayer, "morecreeps:spark", 1.0F, 1.0F);
        entityplayer.attackEntityFrom(DamageSource.inFire, 1.0F);
        return itemstack;
    }
}
