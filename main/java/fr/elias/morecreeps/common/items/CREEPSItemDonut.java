package fr.elias.morecreeps.common.items;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.entity.CREEPSEntityTrophy;

public class CREEPSItemDonut extends Item
{
    private int healAmount;

    public CREEPSItemDonut()
    {
        super();
        healAmount = 2;
        maxStackSize = 64;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        entityplayer.swingItem();
        world.playSoundAtEntity(entityplayer, "morecreeps:chew", 1.0F, 1.0F);
        itemstack.stackSize--;
        entityplayer.heal(healAmount);
        return itemstack;
    }
}
