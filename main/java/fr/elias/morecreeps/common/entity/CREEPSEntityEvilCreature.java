package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CREEPSEntityEvilCreature extends EntityMob
{
    public boolean jumping;
    public float modelsize;
    public String texture;

    public CREEPSEntityEvilCreature(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/evilcreature.png";
        setSize(width * 3F, height * 3F);
        jumping = false;
        isImmuneToFire = true;
        modelsize = 3F;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(4, new CREEPSEntityEvilCreature.AIAttackEntity());
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(110D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3D);
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
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return true;
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity entity, float f)
    {
        if (onGround && jumping)
        {
            jumping = false;
            worldObj.playSoundAtEntity(this, "morecreeps:evilcreaturejump", 1.0F * (modelsize / 3F), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (3F - modelsize) * 2.0F);
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(18D, 18D, 18D));

            for (int i = 0; i < list.size(); i++)
            {
                Entity entity1 = (Entity)list.get(i);

                if ((entity1 instanceof EntityLiving) && !entity1.handleWaterMovement() && entity1.onGround)
                {
                    double d2 = getDistanceToEntity(entity1);
                    entity1.motionY += (17D - d2) * 0.067057996988296509D * (double)(modelsize / 3F);
                }
            }
        }

        if (onGround)
        {
            double d = entity.posX - posX;
            double d1 = entity.posZ - posZ;
            float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
            motionX = (d / (double)f1) * 0.5D * 0.40000000192092894D + motionX * 0.20000000098023224D;
            motionZ = (d1 / (double)f1) * 0.5D * 0.30000000192092896D + motionZ * 0.20000000098023224D;
            motionY = 0.35000000196046449D;
            jumping = true;
        }

        int attackTime = 20;
        if(attackTime-- < 0)
        {
            if ((double)f < 3D - (2D - (double)modelsize) && entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY && entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY)
            {
            	attackTime = 20;
                entity.motionY += 0.76999998092651367D;
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
            }
        }
    }

    class AIAttackEntity extends EntityAIBase
    {
    	public CREEPSEntityEvilCreature evilcreature = CREEPSEntityEvilCreature.this;
    	public int attackTime;
    	public AIAttackEntity() {}
    	
		@Override
		public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.evilcreature.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
		}
        public void updateTask()
        {
        	try{
        	--attackTime;
            EntityLivingBase entitylivingbase = this.evilcreature.getAttackTarget();
            double d0 = this.evilcreature.getDistanceSqToEntity(entitylivingbase);
        	evilcreature.attackEntity(entitylivingbase, (float)d0);
            this.evilcreature.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
            /*if (d0 < 4.0D)
            {
                if (this.attackTime <= 0)
                {
                    this.attackTime = 20;
                    this.evilcreature.attackEntityAsMob(entitylivingbase);
                }
                
                this.evilcreature.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            }
            else if (d0 < 256.0D)
            {
                // ATTACK ENTITY GOES HERE
                this.evilcreature.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
            }
            else*/
            //{
                this.evilcreature.getNavigator().clearPathEntity();
                this.evilcreature.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 0.5D);
            //}
        	}
        	catch (NullPointerException ex)
			{
			ex.printStackTrace();
			}
        }
    }
    
    /**
     * Plays living's sound at its position
     */
    public void playLivingSound()
    {
        String s = getLivingSound();

        if (s != null)
        {
            worldObj.playSoundAtEntity(this, s, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (3F - modelsize) * 2.0F);
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "morecreeps:evilcreature";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:evilcreaturehurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:evilcreaturedeath";
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    public void fall(float distance, float damageMultiplier)
    {
    }

    public float getShadowSize()
    {
        return 2.9F;
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
    	if(!worldObj.isRemote)
    	{
            if (rand.nextInt(5) == 0)
            {
                dropItem(Items.bread, rand.nextInt(3) + 1);
            }
            else
            {
                dropItem(Items.fish, rand.nextInt(3) + 1);
            }
    	}
        super.onDeath(damagesource);
    }
}
