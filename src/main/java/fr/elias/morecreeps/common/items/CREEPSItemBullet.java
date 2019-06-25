package fr.elias.morecreeps.common.items;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class CREEPSItemBullet extends Item
{
    public static Random rand = new Random();
    public float speed;
    public double wayX;
    public double wayY;
    public double wayZ;
    public int waypoint;
    public int wayvert;
    public double distcheck;
    public double prevdistcheck;
    public boolean superflag;

    public CREEPSItemBullet()
    {
        super();
        maxStackSize = 1;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        wayvert = rand.nextInt(8) + 4;
        int i = rand.nextInt(30) + 5;
        int j = rand.nextInt(10) - 5;
        int k = 0;
        wayX = entityplayer.posX - (double)i;
        wayZ = entityplayer.posZ + (double)j;
        k = getHeightValue(world, (int)wayX, (int)wayZ);
        wayY = k + wayvert;
        world.setBlockState(new BlockPos((int)wayX, (int)wayY, (int)wayZ), Blocks.dirt.getDefaultState());
        wayX = entityplayer.posX + (double)j;
        wayZ = entityplayer.posZ + (double)i;
        k = getHeightValue(world, (int)wayX, (int)wayZ);
        wayY = k + wayvert;
        world.setBlockState(new BlockPos((int)wayX, (int)wayY, (int)wayZ), Blocks.ice.getDefaultState());
        wayX = entityplayer.posX + (double)i;
        wayZ = entityplayer.posZ + (double)j;
        k = getHeightValue(world, (int)wayX, (int)wayZ);
        wayY = k + wayvert;
        world.setBlockState(new BlockPos((int)wayX, (int)wayY, (int)wayZ), Blocks.sandstone.getDefaultState());
        wayX = entityplayer.posX + (double)j;
        wayZ = entityplayer.posZ - (double)i;
        k = getHeightValue(world, (int)wayX, (int)wayZ);
        wayY = k + wayvert;
        world.setBlockState(new BlockPos((int)wayX, (int)wayY, (int)wayZ), Blocks.glass.getDefaultState());
        return itemstack;
    }
    public static int getHeightValue(World world, int x, int z)
    {
        if(x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000)
        {
            if(!world.getChunkProvider().chunkExists(x >> 4, z >> 4))
            {
                return 0;
            }
            Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
            return chunk.getHeight(x & 15, z & 15);
        }
        return 64;
    }
}
