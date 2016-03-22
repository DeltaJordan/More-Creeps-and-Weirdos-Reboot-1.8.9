package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CREEPSEntityCamelJockey extends EntityMob
{
    protected double attackrange;
    public boolean bone;
    protected int attack;
    public String texture;
    public float goatsize;

    /** Entity motion Y */
    public double motionY;
    public int eaten;
    public boolean hungry;
    public int hungrytime;
    public int goatlevel;
    public double waterX;
    public double waterY;
    public double waterZ;
    public boolean findwater;
    public int spittimer;
    public float modelsize;
    public double health;
    public double speed;
    public double strength;

    public CREEPSEntityCamelJockey(World world)
    {
        super(world);
        bone = false;
        texture = "morecreeps:textures/entity/jockey.png";
        setSize(width * 0.6F, height * 0.6F);
        attackrange = 16D;
        hungry = false;
        findwater = false;
        hungrytime = rand.nextInt(100) + 10;
        goatlevel = 1;
        spittimer = 50;
        modelsize = 0.6F;
        health = 25;
        speed = 0.55;
        strength = 1; 
    }

    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(health);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(strength);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (ridingEntity == null)
        {
            for (int i = 0; i < worldObj.loadedEntityList.size(); i++)
            {
                Entity entity = (Entity)worldObj.loadedEntityList.get(i);

                if (((entity instanceof CREEPSEntityCamel) || entity.riddenByEntity == null) && (entity instanceof CREEPSEntityCamel) && entity.riddenByEntity == null && !((CREEPSEntityCamel)entity).tamed)
                {
                    double d = entity.getDistance(posX, posY, posZ);
                    CREEPSEntityCamel creepsentitycamel = (CREEPSEntityCamel)entity;

                    if (d < 4D && entity.riddenByEntity == null)
                    {
                        mountEntity(entity);
                    }

                    creepsentitycamel.interest = 0;
                    creepsentitycamel.tamed = false;
                    creepsentitycamel.name = "";

                    if (d < 16D && creepsentitycamel.canEntityBeSeen(this))
                    {
                        this.attackEntity(creepsentitycamel, 0);
                    }
                }
            }
        }
        else
        {
            rotationYaw = ridingEntity.rotationYaw;
        }
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        if (ridingEntity instanceof CREEPSEntityCamel)
        {
            return (double)(this.getYOffset() + 1.5F);
        }
        else
        {
            return (double)this.getYOffset();
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
        return 0.5D;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (handleWaterMovement())
        {
            motionY = 0.15999999642372131D;
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
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        Entity entity = damagesource.getEntity();
        hungry = false;

        if (super.attackEntityFrom(DamageSource.causeMobDamage(this), i))
        {
            if (riddenByEntity == entity || ridingEntity == entity)
            {
                return true;
            }

            if (entity != this && worldObj.getDifficulty().getDifficultyId() > 0)
            {
                this.attackEntityAsMob(entity);
            }

            return true;
        }
        else
        {
            return false;
        }
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

        if ((double)f < 2D && entity.getBoundingBox().maxY > this.getBoundingBox().minY && entity.getBoundingBox().minY < this.getBoundingBox().maxY && !(entity instanceof CREEPSEntityCamel))
        {
            //attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), attack);
        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(this.getBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        int l = worldObj.getBlockLightOpacity(getPosition());
        Block i1 = worldObj.getBlockState(new BlockPos(getPosition())).getBlock();

        if (j < 50)
        {
            return true;
        }
        else
        {
            return (i1 == Blocks.sand || i1 == Blocks.dirt || i1 == Blocks.gravel) && i1 != Blocks.cobblestone && worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0 && worldObj.checkBlockCollision(getBoundingBox()) && worldObj.canBlockSeeSky(getPosition()) && l > 6;
        }
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
            worldObj.playSoundAtEntity(this, s, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (0.6F - modelsize) * 2.0F);
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        if (ridingEntity != null)
        {
            return "morecreeps:cameljockeyget";
        }
        else
        {
            return "morecreeps:cameljockey";
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:cameljockeyhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:cameljockeydeath";
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
        if (rand.nextInt(10) == 0)
        {
            dropItem(Items.porkchop, rand.nextInt(3) + 1);
        }

        if (rand.nextInt(10) == 0)
        {
            dropItem(Items.reeds, rand.nextInt(3) + 1);
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
