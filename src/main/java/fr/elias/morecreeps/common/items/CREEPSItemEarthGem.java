package fr.elias.morecreeps.common.items;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import fr.elias.morecreeps.client.particles.CREEPSFxDirt;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSItemEarthGem extends Item
{
    public static Random random = new Random();

    public CREEPSItemEarthGem()
    {
        super();
        maxStackSize = 1;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        world.playSoundAtEntity(entityplayer, "morecreeps:earthgem", 1.0F, 1.0F);
        itemstack.damageItem(1, entityplayer);
        entityplayer.swingItem();

        for (int k = -2; k < 4; k++)
        {
            for (int l = -3; l < 3; l++)
            {
                for (int i1 = -3; i1 < 3; i1++)
                {
                    Block i = world.getBlockState(new BlockPos((int)(entityplayer.posX + (double)l), (int)((entityplayer.posY - 2D) + (double)k), (int)(entityplayer.posZ + (double)i1))).getBlock();
                    Block j = world.getBlockState(new BlockPos((int)(entityplayer.posX + (double)l), (int)((entityplayer.posY - 1.0D) + (double)k), (int)(entityplayer.posZ + (double)i1))).getBlock();

                    if ((i == Blocks.dirt || i == Blocks.grass) && random.nextInt(5) == 0 && j == Blocks.air)
                    {
                    	MoreCreepsAndWeirdos.proxy.dirt(world, entityplayer, random, l, i1, k);

                        world.setBlockState(new BlockPos((int)(entityplayer.posX + (double)l), (int)((entityplayer.posY - 2D) + (double)k), (int)(entityplayer.posZ + (double)i1)), Blocks.farmland.getDefaultState());
                        world.setBlockState(new BlockPos((int)(entityplayer.posX + (double)l), (int)((entityplayer.posY - 1.0D) + (double)k), (int)(entityplayer.posZ + (double)i1)), Block.getStateById(59));
                    }

                    /*int k1 = world.getBlockMetadata((int)(entityplayer.posX + (double)l), (int)((entityplayer.posY - 1.0D) + (double)k), (int)(entityplayer.posZ + (double)i1));

                    if (k1 < 7 && (i == 59 || i == 60))
                    {
                        world.setBlockMetadataWithNotify((int)(entityplayer.posX + (double)l), (int)((entityplayer.posY - 1.0D) + (double)k), (int)(entityplayer.posZ + (double)i1), k1 + 1);
                    }*/
                }
            }
        }

        return itemstack;
    }

    /**
     * Returns the maximum damage an item can take.
     */
    public int getMaxDamage()
    {
        return 32;
    }
}
