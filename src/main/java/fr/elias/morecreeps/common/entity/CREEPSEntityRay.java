package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import fr.elias.morecreeps.client.config.CREEPSConfig;
import fr.elias.morecreeps.client.particles.CREEPSFxSmoke;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityRay extends EntityThrowable
{
    protected int damage;

    protected boolean playerFire;

    public CREEPSEntityRay(World world)
    {
        super(world);
        playerFire = false;
    }

    public CREEPSEntityRay(World world, double d, double d1, double d2)
    {
    	super(world, d, d1, d2);
    }
    public CREEPSEntityRay(World world, EntityLivingBase entityliving)
    {
        super(world, entityliving);
        damage = 4;
    }

    public void onUpdate()
    {
    	super.onUpdate();
    	if(onGround)
    	{
    		if(rand.nextInt(3) == 0)
    		{
    			if(!worldObj.isRemote)
    			{
    				if(CREEPSConfig.rayGunFire){
    					worldObj.setBlockState(getPosition(), Blocks.fire.getDefaultState());
    				}
    				else{
        				worldObj.setBlockToAir(getPosition().down());
    				}
    			}
    		}
    	}
    	if(!worldObj.isRemote)
    	{
    		checkNearBlock(Blocks.ice, Blocks.water, getPosition());
    		checkNearBlock(Blocks.ice, Blocks.water, getPosition().east());
    		checkNearBlock(Blocks.ice, Blocks.water, getPosition().west());
    		checkNearBlock(Blocks.ice, Blocks.water, getPosition().north());
    		checkNearBlock(Blocks.ice, Blocks.water, getPosition().south());
    		
    		if(rand.nextBoolean())
    		{
        		checkNearBlock2(Blocks.air, Blocks.fire, getPosition());
        		checkNearBlock2(Blocks.air, Blocks.fire, getPosition().east());
        		checkNearBlock2(Blocks.air, Blocks.fire, getPosition().west());
        		checkNearBlock2(Blocks.air, Blocks.fire, getPosition().north());
        		checkNearBlock2(Blocks.air, Blocks.fire, getPosition().south());
    		}
    	}
    }
    
    public void checkNearBlock(Block blockToReplace, Block theNewBlock, BlockPos bp)
    {
    	if(worldObj.getBlockState(bp).getBlock() == blockToReplace && CREEPSConfig.rayGunMelt)
    	{
    		worldObj.setBlockState(bp, theNewBlock.getDefaultState());
    	}
    }
    
    public void checkNearBlock2(Block blockToReplace, Block theNewBlock, BlockPos bp)
    {
    	if(worldObj.getBlockState(bp).getBlock() == blockToReplace)
    	{
    		if(CREEPSConfig.rayGunFire)
    		{
        		worldObj.setBlockState(bp, theNewBlock.getDefaultState());
    		}else{
    			worldObj.setBlockToAir(bp);
    		}
    	}
    }
    
    public void blast()
    {
    	if(worldObj.isRemote)
    	{
            for (int i = 0; i < 8; i++)
            {
                byte byte0 = 7;

                if (rand.nextInt(4) == 0)
                {
                    byte0 = 11;
                }
                MoreCreepsAndWeirdos.proxy.smokeRay(worldObj, this, byte0);
            }
    	}
    }

	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition)
	{
		if (movingobjectposition.entityHit != null)
		{
                if (movingobjectposition.entityHit instanceof EntityPlayer)
                {
                    int k = damage;

                    if (worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
                    {
                        k = 0;
                    }

                    if (worldObj.getDifficulty() == EnumDifficulty.EASY)
                    {
                        k = k / 3 + 1;
                    }

                    if (worldObj.getDifficulty() == EnumDifficulty.HARD)
                    {
                        k = (k * 3) / 2;
                    }
                }

                if ((movingobjectposition.entityHit instanceof EntityLivingBase) && playerFire || !(movingobjectposition.entityHit instanceof CREEPSEntityFloob) || playerFire)
                {
                    movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage);
                }
        		
        		if(!worldObj.isRemote)
        		setDead();
		}
		if(worldObj.isRemote)
        blast();
		
		worldObj.playSoundAtEntity(this, "morecreeps:raygun", 0.2F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
	}
    protected float getGravityVelocity()
    {
        return 0.0F;
    }
}
