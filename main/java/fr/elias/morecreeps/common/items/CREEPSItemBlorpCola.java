package fr.elias.morecreeps.common.items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSItemBlorpCola extends Item
{
    private int healAmount;

    public CREEPSItemBlorpCola()
    {
        super();
        healAmount = 2;
        maxStackSize = 24;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        entityplayer.swingItem();
        world.playSoundAtEntity(entityplayer, "morecreeps:blorpcola", 1.0F, 1.0F);
        itemstack.stackSize--;
        
       
        if (MoreCreepsAndWeirdos.colacount++ >= 10 && MoreCreepsAndWeirdos.colacount < 12 )
        {
            world.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
            entityplayer.addStat(MoreCreepsAndWeirdos.achievechugcola, 1);
            MoreCreepsAndWeirdos.proxy.confettiA(entityplayer, world);
        }
	
        
        
        entityplayer.heal(healAmount);
        return itemstack;
    }
}
