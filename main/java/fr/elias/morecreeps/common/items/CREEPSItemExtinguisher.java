package fr.elias.morecreeps.common.items;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import fr.elias.morecreeps.common.entity.CREEPSEntityFoam;

public class CREEPSItemExtinguisher extends Item
{
    public static Random rand = new Random();

    public CREEPSItemExtinguisher()
    {
        super();
        maxStackSize = 1;
        setMaxDamage(1024);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        world.playSoundAtEntity(entityplayer, "morecreeps:extinguisher", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote)
        {
            CREEPSEntityFoam creepsentityfoam = new CREEPSEntityFoam(world, entityplayer, 0.0F);

            if (creepsentityfoam != null)
            {
                itemstack.damageItem(1, entityplayer);
                world.spawnEntityInWorld(creepsentityfoam);
            }
        }

        if(world.isRemote)
        {
        	MoreCreepsAndWeirdos.proxy.foam(world, entityplayer);
        }
        
        return itemstack;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }
}
