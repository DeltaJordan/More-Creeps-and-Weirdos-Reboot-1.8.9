package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CREEPSEntityKid extends EntityAnimal
{
    protected double attackrange;
    protected int attack;
    public float modelsize;
    public int checktimer;
    public String texture;

    public CREEPSEntityKid(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/kid.png";
        attack = 1;
        attackrange = 16D;
        checktimer = 0;
        modelsize = 0.6F;
        setEntitySize(width * modelsize, height * modelsize);
        ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new CREEPSEntityKid.AIAttackEntity());
        this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
    }

    public void setEntitySize(float width, float height)
    {
    	setSize(width, height);
    }
    
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.55D);
    }

    public float getShadowSize()
    {
        return 0.6F - modelsize;
    }

    /**
     * Checks if this entity is inside of an opaque block
     */
    public boolean isEntityInsideOpaqueBlock()
    {
        if (ridingEntity != null)
        {
            return false;
        }
        else
        {
            return super.isEntityInsideOpaqueBlock();
        }
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        if (ridingEntity instanceof EntityPlayer)
        {
            float f = 0.6F - modelsize;

            if (modelsize > 1.0F)
            {
                f *= 0.55F;
            }

            return (double)1.5F + f;
        }

        if (ridingEntity instanceof CREEPSEntityLolliman)
        {
            return (double)((2.6F + (0.6F - modelsize)) - (2.0F - ((CREEPSEntityLolliman)ridingEntity).modelsize) * 2.75F);
        }
        else
        {
            return (double)1.0;
        }
    }

    public void updateRiderPosition()
    {
        riddenByEntity.setPosition(posX, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ);
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset()
    {
        return 1.75D;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (modelsize > 1.0F)
        {
            ignoreFrustumCheck = true;
        }

        if (handleWaterMovement())
        {
            motionY = 0.15999999642372131D;
        }

        if (ridingEntity != null && checktimer-- < 0)
        {
            checktimer = 60;
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(8D, 8D, 8D));

            for (int i = 0; i < list.size(); i++)
            {
                Entity entity = (Entity)list.get(i);

                if (entity instanceof CREEPSEntityLolliman)
                {
                    worldObj.playSoundAtEntity(this, "morecreeps:kidfind", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (0.6F - modelsize) * 2.0F);
                }
            }
        }
    }

    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
    public float func_180484_a(BlockPos bp)
    {
        if (worldObj.getBlockState(bp.down()).getBlock() == Blocks.sand || worldObj.getBlockState(bp.down()).getBlock() == Blocks.gravel)
        {
            return 10F;
        }
        else
        {
            return -(float)bp.getY();
        }
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer entityplayer)
    {
        if (!(getAttackTarget() instanceof EntityPlayer))
        {
            rotationYaw = entityplayer.rotationYaw;
            Object obj = entityplayer;

            if (ridingEntity != obj)
            {
                rotationYaw = ((Entity)obj).rotationYaw;
                mountEntity((Entity)obj);
                worldObj.playSoundAtEntity(this, "morecreeps:kidup", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (0.6F - modelsize) * 2.0F);
            }
            else
            {
                obj = ((Entity)obj).riddenByEntity;
                rotationYaw = ((Entity)obj).rotationYaw;
                ((Entity)obj).fallDistance = -25F;
                ((Entity)obj).mountEntity(null);
                double d = -MathHelper.sin((entityplayer.rotationYaw * (float)Math.PI) / 180F);
                double d1 = MathHelper.cos((entityplayer.rotationYaw * (float)Math.PI) / 180F);
                ((Entity)obj).motionX = d * 0.60000002384185791D;
                ((Entity)obj).motionZ = d1 * 0.60000002384185791D;
                worldObj.playSoundAtEntity(this, "morecreeps:kiddown", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (0.6F - modelsize) * 2.0F);
            }
        }
        else
        {
            worldObj.playSoundAtEntity(this, "morecreeps:kidnontpickup", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (0.6F - modelsize) * 2.0F);
        }

        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        Entity entity = damagesource.getEntity();
        setRevengeTarget((EntityLivingBase) entity);
        return super.attackEntityFrom(damagesource, i);
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity entity, float f)
    {
        if (onGround)
        {
            double d = entity.posX - posX;
            double d1 = entity.posZ - posZ;
            float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
            motionX = (d / (double)f1) * 0.20000000000000001D * (0.850000011920929D + motionX * 0.20000000298023224D);
            motionZ = (d1 / (double)f1) * 0.20000000000000001D * (0.80000001192092896D + motionZ * 0.20000000298023224D);
            motionY = 0.10000000596246449D;
            fallDistance = -25F;
        }
    }

    class AIAttackEntity extends EntityAIBase
    {
		@Override
		public boolean shouldExecute()
		{
			return CREEPSEntityKid.this.getAttackTarget() != null;
		}
		
		public void updateTask()
		{
			float f = CREEPSEntityKid.this.getDistanceToEntity(getAttackTarget());
			if(f < 256F)
			{
				attackEntity(CREEPSEntityKid.this.getAttackTarget(), f);
				CREEPSEntityKid.this.getLookHelper().setLookPositionWithEntity(CREEPSEntityKid.this.getAttackTarget(), 10.0F, 10.0F);
				CREEPSEntityKid.this.getNavigator().clearPathEntity();
				CREEPSEntityKid.this.getMoveHelper().setMoveTo(CREEPSEntityKid.this.getAttackTarget().posX, CREEPSEntityKid.this.getAttackTarget().posY, CREEPSEntityKid.this.getAttackTarget().posZ, 0.5D);
			}
			if(f < 1F)
			{
				CREEPSEntityKid.this.attackEntityAsMob(CREEPSEntityKid.this.getAttackTarget());
			}
		}
    }
    
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        //int l = worldObj.getFullBlockLightValue(i, j, k);
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        return (i1 == Blocks.grass || i1 == Blocks.sand || i1 == Blocks.dirt || i1 == Blocks.gravel) && i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.double_stone_slab && i1 != Blocks.stone_slab && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).size() == 0 && worldObj.canSeeSky(new BlockPos(i, j, k)) && rand.nextInt(15) == 0;// && l > 6;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        modelsize = nbttagcompound.getFloat("ModelSize");
    }

    /**
     * Plays living's sound at its position
     */
    public void playLivingSound()
    {
        String s = getLivingSound();

        if (s != null)
        {
            worldObj.playSoundAtEntity(this, s, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (0.6F - modelsize) * 2.0F);
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        if (getAttackTarget() instanceof EntityPlayer)
        {
            return null;
        }

        if (rand.nextInt(5) == 0)
        {
            int i = MathHelper.floor_double(posX);
            int j = MathHelper.floor_double(getEntityBoundingBox().minY);
            int k = MathHelper.floor_double(posZ);
            Block l = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
            Block i1 = worldObj.getBlockState(new BlockPos(i, j, k)).getBlock();

            if (i1 == Blocks.snow || l == Blocks.snow || l == Blocks.ice)
            {
                return "morecreeps:kidcold";
            }
        }

        if (ridingEntity != null)
        {
            return "morecreeps:kidride";
        }
        else
        {
            return "morecreeps:kid";
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:kidhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:kiddeath";
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
    	if(!worldObj.isRemote)
    	{
            if (rand.nextInt(10) == 0)
            {
                dropItem(Items.porkchop, rand.nextInt(3) + 1);
            }

            if (rand.nextInt(10) == 0)
            {
                dropItem(Items.wheat_seeds, rand.nextInt(3) + 1);
            }
    	}

        super.onDeath(damagesource);
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

	@Override
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		return new CREEPSEntityKid(worldObj);
	}
}
