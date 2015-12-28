package fr.elias.morecreeps.common.items;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CREEPSItemMiningGem extends Item
{
    public static Random random = new Random();

    public CREEPSItemMiningGem()
    {
        super();
        maxStackSize = 1;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        entityplayer.swingItem();
        float f = 1.0F;
        float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)f;
        double d3 = (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double)f + 1.6200000000000001D) - (double)entityplayer.getYOffset();
        double d6 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)f;
        Vec3 vec3d = new Vec3(d, d3, d6);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d9 = 5D;
        Vec3 vec3d1 = vec3d.addVector((double)f7 * d9, (double)f8 * d9, (double)f9 * d9);
        MovingObjectPosition movingobjectposition = world.rayTraceBlocks(vec3d, vec3d1, true);

        if (movingobjectposition == null)
        {
            return itemstack;
        }

        if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            int i = movingobjectposition.getBlockPos().getX();
            int j = movingobjectposition.getBlockPos().getY();
            int k = movingobjectposition.getBlockPos().getZ();
            Block l = world.getBlockState(new BlockPos(i, j, k)).getBlock();

            if (l == Blocks.stone || l == Blocks.cobblestone || l == Blocks.mossy_cobblestone || l == Blocks.gravel)
            {
                itemstack.damageItem(1, entityplayer);
                world.playSoundAtEntity(entityplayer, "morecreeps:mininggem", 1.0F, 1.0F);

                for (int i1 = 0; i1 < 20; i1++)
                {
                    double d1 = random.nextGaussian() * 0.02D;
                    double d4 = random.nextGaussian() * 0.02D;
                    double d7 = random.nextGaussian() * 0.02D;
                    double d10 = -MathHelper.sin((entityplayer.rotationYaw * (float)Math.PI) / 180F);
                    double d12 = MathHelper.cos((entityplayer.rotationYaw * (float)Math.PI) / 180F);
                    world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (double)i + (double)(random.nextFloat() * 1.5F), (double)((float)j + 0.5F) + (double)(random.nextFloat() * 2.5F), (double)k + (double)(random.nextFloat() * 1.5F), d1, d4, d7);
                }

                int j1 = random.nextInt(7);

                switch (j1)
                {
                    case 1:
                        if (random.nextInt(7) == 0)
                        {
                            world.setBlockState(new BlockPos(i, j, k), Blocks.gold_ore.getDefaultState());
                        }

                        break;

                    case 2:
                        if (random.nextInt(5) == 0)
                        {
                            world.setBlockState(new BlockPos(i, j, k), Blocks.iron_ore.getDefaultState());
                        }

                        break;

                    case 3:
                        if (random.nextInt(1) == 0)
                        {
                            world.setBlockState(new BlockPos(i, j, k), Blocks.coal_ore.getDefaultState());
                        }

                        break;

                    case 4:
                        if (random.nextInt(5) == 0)
                        {
                            world.setBlockState(new BlockPos(i, j, k), Blocks.lapis_ore.getDefaultState());
                        }

                        break;

                    case 5:
                        if (random.nextInt(10) == 0)
                        {
                            world.setBlockState(new BlockPos(i, j, k), Blocks.diamond_ore.getDefaultState());
                        }

                        break;

                    case 6:
                        if (random.nextInt(3) == 0)
                        {
                            world.setBlockState(new BlockPos(i, j, k), Blocks.redstone_ore.getDefaultState());
                        }

                        break;

                    case 7:
                        if (random.nextInt(3) == 0)
                        {
                            world.setBlockState(new BlockPos(i, j, k), Blocks.redstone_ore.getDefaultState());
                        }

                        break;

                    default:
                        world.setBlockState(new BlockPos(i, j, k), Blocks.air.getDefaultState());
                        break;
                }
            }
            else
            {
                world.playSoundAtEntity(entityplayer, "morecreeps:mininggembad", 1.0F, 1.0F);

                for (int k1 = 0; k1 < 20; k1++)
                {
                    double d2 = random.nextGaussian() * 0.02D;
                    double d5 = random.nextGaussian() * 0.02D;
                    double d8 = random.nextGaussian() * 0.02D;
                    double d11 = -MathHelper.sin((entityplayer.rotationYaw * (float)Math.PI) / 180F);
                    double d13 = MathHelper.cos((entityplayer.rotationYaw * (float)Math.PI) / 180F);
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)i + (double)(random.nextFloat() * 1.5F), (double)((float)j + 0.5F) + (double)(random.nextFloat() * 2.5F), (double)k + (double)(random.nextFloat() * 1.5F), d2, d5, d8);
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
        return 64;
    }
}
