package fr.elias.morecreeps.common.items;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import fr.elias.morecreeps.common.entity.CREEPSEntityGuineaPig;

public class CREEPSItemGuineaPigRadio extends Item
{
    public boolean pickup;
    public static Random rand = new Random();

    public CREEPSItemGuineaPigRadio()
    {
        super();
        maxStackSize = 1;
        pickup = false;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        if (entityplayer.isSneaking())
        {
            return itemstack;
        }

        if (entityplayer.ridingEntity == null)
        {
            if (pickup)
            {
                pickup = false;
                world.playSoundAtEntity(entityplayer, "morecreeps:ggpigunmount", 1.0F, 1.0F);

                for (int i = 0; i < 21; i++)
                {
                    Object obj = entityplayer;
                    int k;

                    for (k = 0; ((Entity)obj).riddenByEntity != null && k < 20; k++)
                    {
                        obj = ((Entity)obj).riddenByEntity;
                    }

                    if (k < 20)
                    {
                        ((Entity)obj).fallDistance = -25F;
                        ((Entity)obj).mountEntity(null);
                    }
                }
            }
            else
            {
                pickup = true;
                world.playSoundAtEntity(entityplayer, "morecreeps:ggpigradio", 1.0F, 1.0F);
                List list = world.getEntitiesWithinAABB(fr.elias.morecreeps.common.entity.CREEPSEntityGuineaPig.class, new AxisAlignedBB(entityplayer.posX, entityplayer.posY, entityplayer.posZ, entityplayer.posX + 1.0D, entityplayer.posY + 1.0D, entityplayer.posZ + 1.0D).expand(150D, 150D, 150D));

                for (int j = 0; j < list.size(); j++)
                {
                    Entity entity = (Entity)list.get(j);

                    if ((entity instanceof CREEPSEntityGuineaPig) && ((CREEPSEntityGuineaPig)entity).wanderstate == 0 && ((CREEPSEntityGuineaPig)entity).tamed)
                    {
                        Object obj1 = entityplayer;

                        if (entity.ridingEntity != obj1 && entity.ridingEntity == null)
                        {
                            int l;

                            for (l = 0; ((Entity)obj1).riddenByEntity != null && l < 20; l++)
                            {
                                obj1 = ((Entity)obj1).riddenByEntity;
                            }

                            if (l < 20)
                            {
                                entity.rotationYaw = ((Entity)obj1).rotationYaw;
                                entity.mountEntity((Entity)obj1);
                                world.playSoundAtEntity(entityplayer, "morecreeps:ggpigmount", 1.0F, 1.0F);
                            }
                        }
                    }
                }
            }
        }
        else
        {
            MoreCreepsAndWeirdos.proxy.addChatMessage("Get off that creature before using the Guinea Pig Radio");
        }

        return itemstack;
    }
}
