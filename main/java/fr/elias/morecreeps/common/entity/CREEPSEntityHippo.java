package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class CREEPSEntityHippo extends EntityMob
{
    protected double attackrange;
    public boolean bone;
    protected int attack;
    public float goatsize;

    public int eaten;
    public boolean hungry;
    public int hungrytime;
    public int goatlevel;
    public double waterX;
    public double waterY;
    public double waterZ;
    public boolean findwater;
    public float modelsize;
    public boolean hippoHit;
    public String texture;

    public CREEPSEntityHippo(World world)
    {
        super(world);
        bone = false;
        texture = "morecreeps:textures/entity/hippo2.png";
        setSize(2.0F, 1.4F);
        attack = 2;
        attackrange = 16D;
        hungry = false;
        findwater = false;
        hungrytime = rand.nextInt(100) + 10;
        goatlevel = 1;
        modelsize = 2.0F;
        hippoHit = false;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.45D, true));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityMob.class, 0.45D, false));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityAnimal.class, 0.45D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new CREEPSEntityHippo.AIAttackEntity(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new CREEPSEntityHippo.AIAttackEntity(this, EntityMob.class, true));
        this.targetTasks.addTask(2, new CREEPSEntityHippo.AIAttackEntity(this, EntityAnimal.class, true));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
    }

    public float func_180484_a(BlockPos bp)
    {
        if (worldObj.getBlockState(bp.down()).getBlock() == Blocks.water || worldObj.getBlockState(bp.down()) == Blocks.flowing_water)
        {
            return 10F;
        }
        else
        {
            return -(float)bp.getY();
        }
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected EntityLivingBase findPlayerToAttack()
    {
        if (!hippoHit)
        {
            return null;
        }

        if (worldObj.getDifficulty() != EnumDifficulty.PEACEFUL)
        {
            float f = getBrightness(1.0F);

            if (f < 0.0F)
            {
                EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, attackrange);

                if (entityplayer != null)
                {
                    return entityplayer;
                }
            }

            if (rand.nextInt(10) == 0)
            {
                EntityLivingBase entityliving = getClosestTarget(this, 15D);
                return entityliving;
            }
        }

        return null;
    }

    public EntityLivingBase getClosestTarget(Entity entity, double d)
    {
        double d1 = -1D;
        EntityLivingBase entityliving = null;

        for (int i = 0; i < worldObj.loadedEntityList.size(); i++)
        {
            Entity entity1 = (Entity)worldObj.loadedEntityList.get(i);

            if (!(entity1 instanceof EntityLiving) || entity1 == entity || entity1 == entity.riddenByEntity || entity1 == entity.ridingEntity || (entity1 instanceof EntityPlayer) || (entity1 instanceof EntityMob) || (entity1 instanceof EntityAnimal))
            {
                continue;
            }

            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);

            if ((d < 0.0D || d2 < d * d) && (d1 == -1D || d2 < d1) && ((EntityLivingBase)entity1).canEntityBeSeen(entity))
            {
                d1 = d2;
                entityliving = (EntityLivingBase)entity1;
            }
        }

        return entityliving;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        Entity entity = damagesource.getEntity();
        hungry = false;
        hippoHit = true;

        if (entity != null)
        {
            setRevengeTarget((EntityLivingBase) entity);
        }

        return super.attackEntityFrom(DamageSource.causeMobDamage(this), i);
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

    class AIAttackEntity extends EntityAINearestAttackableTarget {

		public AIAttackEntity(EntityCreature p_i45878_1_, Class p_i45878_2_,
				boolean p_i45878_3_) {
			super(p_i45878_1_, p_i45878_2_, p_i45878_3_);
		}

		@Override
		public boolean shouldExecute() {
			return hippoHit && CREEPSEntityHippo.this.getAttackTarget() != null && super.shouldExecute();
		}
		public void startExecuting()
	    {
			CREEPSEntityHippo.this.setAttackTarget(findPlayerToAttack());
	        super.startExecuting();
	    }
		
		public void updateTask()
		{
			double d0 = CREEPSEntityHippo.this.getDistanceSqToEntity(CREEPSEntityHippo.this.getAttackTarget());
			if(d0 < 256D)
			{
				attackEntity(CREEPSEntityHippo.this.getAttackTarget(), (float)d0);
			}
		}

	}
    
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(getBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        //int l = worldObj.getFullBlockLightValue(i, j, k);
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        return (i1 == Blocks.grass || i1 == Blocks.dirt) && i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.stone_slab && i1 != Blocks.double_stone_slab && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0 && worldObj.canSeeSky(new BlockPos(i, j, k)) && rand.nextInt(35) == 0; //&& l > 7;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Hungry", hungry);
        nbttagcompound.setFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        hungry = nbttagcompound.getBoolean("Hungry");
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
            worldObj.playSoundAtEntity(this, s, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (2.0F - modelsize) * 2.0F);
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "morecreeps:hippo";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:hippohurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:hippodeath";
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
        return 2;
    }
}
