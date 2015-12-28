package fr.elias.morecreeps.common.items;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class CREEPSItemCavemanClub extends ItemSword
{
    public static Random random = new Random();
    public int drumsound;

    public CREEPSItemCavemanClub()
    {
        super(ToolMaterial.WOOD);
        setMaxDamage(64);
    }

    /**
     * Returns the damage against a given entity.
     */
    public int getDamageVsEntity(Entity entity)
    {
        return 4;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        return itemstack;
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack itemstack, Block block)
    {
        return 3F;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1)
    {
        itemstack.damageItem(1, entityliving1);
        return true;
    }

    public boolean onBlockDestroyed(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving)
    {
        itemstack.damageItem(2, entityliving);
        return true;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block block)
    {
        return false;
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     */
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
    {
    	EntityPlayer player = (EntityPlayer)entity;
        super.onUpdate(itemstack, world, entity, i, flag);

        if (flag && drumsound-- < 0)
        {
            drumsound = random.nextInt(200) + 150;
            world.playSoundAtEntity(player, "morecreeps:cavedrums", 0.65F, 0.9F);
        }
    }
}
