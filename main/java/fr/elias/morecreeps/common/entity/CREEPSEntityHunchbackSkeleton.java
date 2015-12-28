package fr.elias.morecreeps.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CREEPSEntityHunchbackSkeleton extends EntityMob
{
    private static final ItemStack defaultHeldItem;
    public boolean hasAttacked;
    public int weapon;
    public int timeleft;
    public String ss;
    public float modelsize;
    public String texture;
    public double health;

    public CREEPSEntityHunchbackSkeleton(World world)
    {
        super(world);
        texture = "/mob/creeps/hunchbackskeleton1.png";
        health = rand.nextInt(10) + 10;
        timeleft = rand.nextInt(500) + 200;
        modelsize = 1.0F;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.45D, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
    }
    
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(health);
    }

    

    public CREEPSEntityHunchbackSkeleton(World world, Entity entity, double d, double d1, double d2, boolean flag)
    {
        this(world);
        setPosition(d, d1, d2);
    }

    /**
     * Plays living's sound at its position
     */
    public void playLivingSound()
    {
        String s = getLivingSound();

        if (s != null)
        {
            worldObj.playSoundAtEntity(this, s, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (1.0F - modelsize) * 2.0F);
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "mob.skeleton";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.skeletonhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.skeletonhurt";
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (rand.nextInt(2) == 0)
        {
            timeleft--;
        }

        if (timeleft < 500 && timeleft > 300)
        {
            texture = "/mob/creeps/hunchbackskeleton2.png";
        }
        else if (timeleft < 300 && timeleft > 200)
        {
            texture = "/mob/creeps/hunchbackskeleton3.png";
        }
        else if (timeleft < 200 && timeleft > 100)
        {
            texture = "/mob/creeps/hunchbackskeleton4.png";
        }
        else if (timeleft < 100 && timeleft > 1)
        {
            texture = "/mob/creeps/hunchbackskeleton5.png";
        }
        else if (timeleft < 1)
        {
            smoke();
            health = 0;
            setDead();
        }
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        EntityLiving entityliving = getClosestTarget(this, 16D);

        if ((this.getAttackTarget() instanceof EntityPlayer) || (this.getAttackTarget() instanceof CREEPSEntityHunchbackSkeleton))
        {
            return null;
        }
        else
        {
            return entityliving;
        }
    }

    public EntityLiving getClosestTarget(Entity entity, double d)
    {
        double d1 = -1D;
        EntityLiving entityliving = null;

        for (int i = 0; i < worldObj.loadedEntityList.size(); i++)
        {
            Entity entity1 = (Entity)worldObj.loadedEntityList.get(i);

            if (!(entity1 instanceof EntityLiving) || entity1 == entity || entity1 == entity.riddenByEntity || entity1 == entity.ridingEntity || (entity1 instanceof EntityPlayer) || (entity1 instanceof CREEPSEntityGuineaPig) || (entity1 instanceof CREEPSEntitySnowDevil) || (entity1 instanceof CREEPSEntityHunchbackSkeleton) || (entity1 instanceof CREEPSEntityHunchback))
            {
                continue;
            }

            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);

            if ((d < 0.0D || d2 < d * d) && (d1 == -1D || d2 < d1) && ((EntityLiving)entity1).canEntityBeSeen(entity))
            {
                d1 = d2;
                entityliving = (EntityLiving)entity1;
            }
        }

        return entityliving;
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity entity, float f)
    {
        if ((entity instanceof EntityPlayer) || (entity instanceof CREEPSEntityHunchback) || (entity instanceof CREEPSEntityHunchbackSkeleton))
        {
            this.setAttackTarget(null);
            return;
        }

        if (f < 10F)
        {
            double d = entity.posX - posX;
            double d1 = entity.posZ - posZ;

            
                EntityArrow entityarrow = new EntityArrow(worldObj, this, 1.0F);
                entityarrow.posY += 1.3999999761581421D;
                double d2 = entity.posY - 0.20000000298023224D - entityarrow.posY;
                float f1 = MathHelper.sqrt_double(d * d + d1 * d1) * 0.2F;
                worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
                worldObj.spawnEntityInWorld(entityarrow);
                entityarrow.setThrowableHeading(d, d2 + (double)f1, d1, 0.6F, 12F);
            

            rotationYaw = (float)((Math.atan2(d1, d) * 180D) / Math.PI) - 90F;
            hasAttacked = true;
        }
    }

    private void smoke()
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height) + (double)i, (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
            }
        }
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
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        return Item.getIdFromItem(Items.arrow);
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems()
    {
        if (rand.nextInt(10) == 0)
        {
            if (rand.nextInt(2) == 0)
            {
                dropItem(Items.arrow, rand.nextInt(3));
            }

            if (rand.nextInt(2) == 0)
            {
                dropItem(Items.bone, rand.nextInt(2));
            }
        }
    }

    /**
     * Returns the item that this EntityLiving is holding, if any.
     */
    public ItemStack getHeldItem()
    {
        return defaultHeldItem;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return true;
    }

    static
    {
        defaultHeldItem = new ItemStack(Items.bow, 1);
    }
}
