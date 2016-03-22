package fr.elias.morecreeps.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityCastleGuard extends EntityMob
{
    private int angerLevel;
    private int randomSoundDelay;
    public String basetexture;
    public int attackdamage;
    public boolean isSwinging;
    public boolean swingArm;
    public int swingTick;
    public String texture;
    public boolean attacked;
    public float hammerswing;
    public float modelsize;
    static final String guardTextures[] =
    {
        "/mob/creeps/castleguard1.png", "/mob/creeps/castleguard2.png", "/mob/creeps/castleguard3.png", "/mob/creeps/castleguard4.png", "/mob/creeps/castleguard5.png"
    };

    public CREEPSEntityCastleGuard(World world)
    {
        super(world);
        angerLevel = 0;
        randomSoundDelay = 0;
        basetexture = guardTextures[rand.nextInt(guardTextures.length)];
        texture = basetexture;
        attacked = false;
        hammerswing = 0.0F;
        modelsize = 1.0F;
        attackdamage = 1;
    }
    
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1D);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (hammerswing < 0.0F)
        {
            hammerswing += 0.45F;
        }
        else
        {
            hammerswing = 0.0F;
        }

        super.onUpdate();
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
    	AxisAlignedBB x = this.getBoundingBox();
    	
        return worldObj.getDifficulty().getDifficultyId() > 0 && worldObj.checkNoEntityCollision(getBoundingBox()) && worldObj.getCollidingBoundingBoxes(this,  x).size() == 0;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setShort("Anger", (short)angerLevel);
        nbttagcompound.setBoolean("Attacked", attacked);
        nbttagcompound.setString("BaseTexture", basetexture);
        nbttagcompound.setFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        angerLevel = nbttagcompound.getShort("Anger");
        nbttagcompound.setBoolean("Attacked", attacked);
        nbttagcompound.setString("BaseTexture", basetexture);
        modelsize = nbttagcompound.getFloat("ModelSize");
        texture = basetexture;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        Entity entity = damagesource.getEntity();

        if (entity instanceof EntityPlayer)
        {
            attacked = true;
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
            double d2 = entity.posZ - posZ;
            float f1 = MathHelper.sqrt_double(d * d + d2 * d2);
            motionX = (d / (double)f1) * 0.20000000000000001D * (0.58000001192092898D + motionX * 0.20000000298023224D);
            motionZ = (d2 / (double)f1) * 0.20000000000000001D * (0.52200000119209289D + motionZ * 0.20000000298023224D);
            motionY = 0.19500000596246447D;
            fallDistance = -25F;
        }

        if ((double)f > 3D && rand.nextInt(10) == 0)
        {
            double d1 = -MathHelper.sin((rotationYaw * (float)Math.PI) / 180F);
            double d3 = MathHelper.cos((rotationYaw * (float)Math.PI) / 180F);
            motionX += d1 * 0.10999999940395355D;
            motionZ += d3 * 0.10999999940395355D;
            motionY += 0.023000000044703484D;
        }

        if ((double)f < 2.2999999999999998D - (1.0D - (double)modelsize) && entity.getBoundingBox().maxY > entity.getBoundingBox().minY && entity.getBoundingBox().minY < entity.getBoundingBox().maxY && !(entity instanceof CREEPSEntityCastleGuard))
        {
            if (hammerswing == 0.0F)
            {
                hammerswing = -2.6F;
            }

            //attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), attackdamage);
        }
    }

    private void becomeAngryAt(Entity entity)
    {
        this.attackEntity(entity, 1);
        angerLevel = 400 + rand.nextInt(400);
        randomSoundDelay = rand.nextInt(40);
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
        if (attacked && rand.nextInt(5) == 0)
        {
            return "morecreeps:castleguardmad";
        }

        if (rand.nextInt(12) == 0)
        {
            return "morecreeps:castleguard";
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:castleguardhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:castleguarddeath";
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
        super.onDeath(damagesource);

        if (rand.nextInt(3) == 0)
        {
            dropItem(MoreCreepsAndWeirdos.donut, rand.nextInt(2) + 1);
        }
    }
}
