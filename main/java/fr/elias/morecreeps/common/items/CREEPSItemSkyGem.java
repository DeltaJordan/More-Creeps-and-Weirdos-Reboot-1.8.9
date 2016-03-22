package fr.elias.morecreeps.common.items;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import org.lwjgl.input.Mouse;

import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSItemSkyGem extends Item
{
    public double floatcycle;
    public int floatdir;
    public double floatmaxcycle;
    public int jumpdelay;
    public float jumpboost;
    public int usage;
    public static Random random = new Random();

    public CREEPSItemSkyGem()
    {
        super();
        maxStackSize = 1;
        floatdir = 1;
        floatcycle = 0.0D;
        floatmaxcycle = 0.2199999988079071D;
        jumpdelay = 0;
        jumpboost = 0.0F;
        usage = 100;
        setMaxDamage(32);
    }
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        world.playSoundAtEntity(entityplayer, "morecreeps:skygemup", 1.0F, 1.0F);
        usage -= 10;

        if (usage < 0)
        {
            usage = 200;
            itemstack.damageItem(1, entityplayer);
        }

        jumpboost = 1.0F;
        floatcycle = 0.0D;
        floatdir = 1;
        double d = -MathHelper.sin((entityplayer.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((entityplayer.rotationYaw * (float)Math.PI) / 180F);
        entityplayer.motionX += d * 0.5D;
        entityplayer.motionZ += d1 * 0.5D;
        entityplayer.fallDistance = -25F;
        return itemstack;
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
    {
        EntityPlayer player = (EntityPlayer) entity;

        if (player.onGround)
        {
            player.fallDistance = 0.0F;
        }

        super.onUpdate(itemstack, world, entity, i, flag);

        if (flag)
        {
            player.fallDistance = -25F;

            if (player.isSwingInProgress || Mouse.isButtonDown(0))
            {
                if (!player.isSwingInProgress)
                {
                    player.swingItem();
                }

                world.playSoundAtEntity(player, "morecreeps:skygemdown", 0.6F, 1.0F);
                jumpboost = -0.35F;
                player.fallDistance = -15F;
            }

            if(world.isRemote)
            {
            	MoreCreepsAndWeirdos.proxy.smoke(world, player, random);
            }
            
            boolean jumping = ObfuscationReflectionHelper.getPrivateValue(EntityLivingBase.class, player, "isJumping", "field_70703_bu");
            if (player.onGround && jumping)
            {
                floatdir = 1;
                floatcycle = 0.0D;
            }

            if (usage-- < 0)
            {
                usage = 200;
                itemstack.damageItem(1, player);
            }

            if (floatdir > 0)
            {
                floatcycle += 0.026499999687075615D;

                if (floatcycle > floatmaxcycle)
                {
                    floatdir = floatdir * -1;
                    player.fallDistance += -1.5F;
                }
            }
            else
            {
                floatcycle -= 0.0099999997764825821D;

                if (floatcycle < -floatmaxcycle)
                {
                    floatdir = floatdir * -1;
                    player.fallDistance += -1.5F;
                }
            }

            player.posY -= floatcycle;
            player.motionY = floatcycle + (double)jumpboost;

            if (jumpboost > 0.0F)
            {
                jumpboost -= 0.055F;
            }

            if (jumpboost < 0.0F)
            {
                jumpboost += 0.055F;
            }
        }
    }
}
