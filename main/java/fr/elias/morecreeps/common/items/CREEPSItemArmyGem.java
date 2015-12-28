package fr.elias.morecreeps.common.items;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.Reference;
import fr.elias.morecreeps.common.entity.CREEPSEntityArmyGuy;

public class CREEPSItemArmyGem extends Item
{
    public static Random random = new Random();

    public CREEPSItemArmyGem()
    {
        super();
        maxStackSize = 1;
        setMaxDamage(16);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        entityplayer.swingItem();
        world.playSoundAtEntity(entityplayer, "morecreeps:armygem", 1.0F, 1.0F);
        double d = -MathHelper.sin((entityplayer.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((entityplayer.rotationYaw * (float)Math.PI) / 180F);
        CREEPSEntityArmyGuy creepsentityarmyguy = new CREEPSEntityArmyGuy(world);
        creepsentityarmyguy.setLocationAndAngles(entityplayer.posX + d * 1.0D, entityplayer.posY + 1.0D, entityplayer.posZ + d1 * 1.0D, entityplayer.rotationYaw, 0.0F);
        creepsentityarmyguy.loyal = true;
        creepsentityarmyguy.texture = new ResourceLocation(Reference.MOD_ID, 
        		Reference.TEXTURE_PATH_ENTITES + Reference.TEXTURE_ARMY_GUY_LOYAL);
        creepsentityarmyguy.setOwnerId(EntityPlayer.getUUID(entityplayer.getGameProfile()).toString());
        world.spawnEntityInWorld(creepsentityarmyguy);
        itemstack.damageItem(2, entityplayer);
        return itemstack;
    }
}
