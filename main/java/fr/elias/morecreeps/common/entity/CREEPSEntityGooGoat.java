package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
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
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityGooGoat extends EntityAnimal
{
	protected double attackrange;
    public boolean bone;
    protected int attack;
    public float goatsize;

    /** Entity motion Y */
    public int eaten;
    public boolean hungry;
    public int hungrytime;
    public int goatlevel;
    public float modelspeed;
    public boolean angry;
    private int angerLevel;
    private int randomSoundDelay;
    public String texture;

    public CREEPSEntityGooGoat(World world)
    {
        super(world);
        bone = false;
        texture = "morecreeps:textures/entity/googoat1.png";
        attack = 1;
        attackrange = 16D;
        goatsize = 0.7F;
        hungry = false;
        angry = false;
        hungrytime = rand.nextInt(100) + 10;
        goatlevel = 1;
        modelspeed = 0.45F;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.45D, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new CREEPSEntityGooGoat.AIAttackEntity(this, EntityPlayer.class, true));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2D);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (modelspeed < 0.05F)
        {
            modelspeed = 0.05F;
        }

        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue((double)modelspeed);
        super.onLivingUpdate();

        if (hungry)
        {
            int i = MathHelper.floor_double(posX);
            int j = MathHelper.floor_double(getBoundingBox().minY);
            int k = MathHelper.floor_double(posZ);
            Block l = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();

            if (l == Blocks.grass && rand.nextInt(10) == 0)
            {
                worldObj.setBlockState(new BlockPos(i, j - 1, k), Blocks.dirt.getDefaultState());
                hungrytime = hungrytime + rand.nextInt(100) + 25;

                if (hungrytime > 300 && goatlevel < 5)
                {
                    hungry = false;
                    hungrytime = 0;
                    goatsize = goatsize + 0.2F;
                    goatlevel++;
                    attack++;
                    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)(15 * goatlevel + 25));
                    texture = (new StringBuilder()).append("morecreeps:textures/entity/googoat").append(goatlevel).append(".png").toString();
                    worldObj.playSoundAtEntity(this, "morecreeps:googoatstretch", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }
            }
        }
        else
        {
            hungrytime--;

            if (hungrytime < 1)
            {
                hungry = true;
                hungrytime = 1;
            }
        }
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    /*protected Entity findPlayerToAttack()
    {
        float f = getBrightness(1.0F);

        if (f < 0.0F || angry)
        {
            EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, attackrange);

            if (entityplayer != null)
            {
                return entityplayer;
            }
        }

        if (rand.nextInt(10) == 0)
        {
            EntityLivingBase entityliving = getClosestTarget(this, 6D);
            return entityliving;
        }
        else
        {
            return null;
        }
    }

    public EntityLivingBase getClosestTarget(Entity entity, double d)
    {
        double d1 = -1D;
        EntityLiving entityliving = null;

        for (int i = 0; i < worldObj.loadedEntityList.size(); i++)
        {
            Entity entity1 = (Entity)worldObj.loadedEntityList.get(i);

            if (!(entity1 instanceof EntityLiving) || entity1 == entity || entity1 == entity.riddenByEntity || entity1 == entity.ridingEntity || (entity1 instanceof EntityPlayer) || (entity1 instanceof EntityMob) || (entity1 instanceof EntityAnimal))
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
    }*/
    class AIAttackEntity extends EntityAINearestAttackableTarget {

		public AIAttackEntity(EntityCreature p_i45878_1_, Class p_i45878_2_,
				boolean p_i45878_3_) {
			super(p_i45878_1_, p_i45878_2_, p_i45878_3_);
		}

		@Override
		public boolean shouldExecute() {
			return angry && CREEPSEntityGooGoat.this.getAttackTarget() != null && super.shouldExecute();
		}
		public void startExecuting()
	    {
	        if (onGround)//TODO move this on updateTask() if isn't working 
	        {
	            double d = targetEntity.posX - posX;
	            double d1 = targetEntity.posZ - posZ;
	            float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
	            motionX = (d / (double)f1) * 0.20000000000000001D * (0.850000011920929D + (double)goatlevel * 0.10000000000000001D) + motionX * 0.20000000298023224D;
	            motionZ = (d1 / (double)f1) * 0.20000000000000001D * (0.80000001192092896D + (double)goatlevel * 0.10000000000000001D) + motionZ * 0.20000000298023224D;
	            motionY = 0.10000000596246449D + (double)goatlevel * 0.070000002559000005D;
	            fallDistance = -25F;
	        }
	        super.startExecuting();
	    }
		/*
		public void updateTask()
		{
		}*/

	}
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        Entity entity = damagesource.getEntity();
        hungry = false;

        if (entity instanceof EntityPlayer)
        {
            angry = true;
        }
        return super.attackEntityFrom(damagesource, i);
    }

    private void becomeAngryAt(Entity entity)
    {
        setRevengeTarget((EntityLivingBase) entity);
        angerLevel = 400 + rand.nextInt(400);
        randomSoundDelay = rand.nextInt(40);
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    /*protected void attackEntity(Entity entity, float f)
    {
        if (onGround)
        {
            double d = entity.posX - posX;
            double d1 = entity.posZ - posZ;
            float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
            motionX = (d / (double)f1) * 0.20000000000000001D * (0.850000011920929D + (double)goatlevel * 0.10000000000000001D) + motionX * 0.20000000298023224D;
            motionZ = (d1 / (double)f1) * 0.20000000000000001D * (0.80000001192092896D + (double)goatlevel * 0.10000000000000001D) + motionZ * 0.20000000298023224D;
            motionY = 0.10000000596246449D + (double)goatlevel * 0.070000002559000005D;
            fallDistance = -25F;
        }

        if ((double)f < 2D + (double)goatlevel * 0.10000000000000001D && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY)
        {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), attack);
        }
    }*/

    /*public int[] findTree(Entity entity, Material material, Double double1)
    {
        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(double1.doubleValue(), double1.doubleValue(), double1.doubleValue());
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);

        for (int k1 = i; k1 < j; k1++)
        {
            for (int l1 = k; l1 < l; l1++)
            {
                for (int i2 = i1; i2 < j1; i2++)
                {
                    int j2 = worldObj.getBlockId(k1, l1, i2);

                    if (j2 != 0 && Block.blocksList[j2].blockMaterial == material)
                    {
                        return (new int[]
                                {
                                    k1, l1, i2
                                });
                    }
                }
            }
        }

        return (new int[]
                {
                    -1, 0, 0
                });
    }*/

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(getBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        int l = worldObj.getBlockLightOpacity(new BlockPos(i, j, k));
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        return (i1 == Blocks.grass || i1 == Blocks.dirt) && i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.double_stone_slab && i1 != Blocks.stone_slab && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0 && worldObj.canSeeSky(new BlockPos(i, j, k)) && rand.nextInt(40) == 0 && l > 7;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Hungry", hungry);
        nbttagcompound.setInteger("GoatLevel", goatlevel);
        nbttagcompound.setFloat("GoatSize", goatsize);
        nbttagcompound.setFloat("ModelSpeed", modelspeed);
        nbttagcompound.setBoolean("Angry", angry);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        hungry = nbttagcompound.getBoolean("Hungry");
        goatlevel = nbttagcompound.getInteger("GoatLevel");
        goatsize = nbttagcompound.getFloat("GoatSize");
        modelspeed = nbttagcompound.getFloat("ModelSpeed");
        angry = nbttagcompound.getBoolean("Angry");
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "morecreeps:googoat";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:googoathurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:googoatdead";
    }

    public void confetti()
    {
    	MoreCreepsAndWeirdos.proxy.confettiA(this, worldObj);
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
        Object obj = damagesource.getEntity();

        if ((obj instanceof CREEPSEntityRocket) && ((CREEPSEntityRocket)obj).owner != null)
        {
            obj = ((CREEPSEntityRocket)obj).owner;
        }

        if (obj instanceof EntityPlayer)
        {
            MoreCreepsAndWeirdos.goatcount++;
            boolean flag = false;
            EntityPlayerMP player = (EntityPlayerMP) obj;

            if (!player.getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievegookill))
            {
                flag = true;
                player.addStat(MoreCreepsAndWeirdos.achievegookill, 1);
                confetti();
            }

            if (!player.getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievegookill10) && MoreCreepsAndWeirdos.goatcount >= 10)
            {
                flag = true;
                player.addStat(MoreCreepsAndWeirdos.achievegookill10, 1);
                confetti();
            }

            if (!player.getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievegookill25) && MoreCreepsAndWeirdos.goatcount >= 25)
            {
                flag = true;
                player.addStat(MoreCreepsAndWeirdos.achievegookill25, 1);
                confetti();
            }

            if (flag)
            {
                worldObj.playSoundAtEntity(player, "morecreeps:achievement", 1.0F, 1.0F);
            }
        }

        int i = (goatlevel - 1) + rand.nextInt(2);

        if (i > 0)
        {
            if(!worldObj.isRemote)
            dropItem(MoreCreepsAndWeirdos.goodonut, i);
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

	@Override
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		return new CREEPSEntityGooGoat(worldObj);
	}
}
