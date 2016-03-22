package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class CREEPSEntityEvilLight extends EntityLiving
{
    public int lifespan;
    public String texture;

    public CREEPSEntityEvilLight(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/evillight1.png";
        lifespan = 200;
        motionZ = rand.nextFloat() * 2.0F - 1.0F;
        motionX = rand.nextFloat() * 2.0F - 1.0F;
    }
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
    	this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3D);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
    	float health = this.getHealth();
        if (lifespan-- < 1 || handleWaterMovement())
        {
            health = 0;
            setDead();
        }

        Object obj = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;

        for (int i = 0; i < list.size(); i++)
        {
            Entity entity = (Entity)list.get(i);

            if (entity.canBeCollidedWith() && (entity instanceof EntityLivingBase) && !(entity instanceof CREEPSEntityEvilLight) && !(entity instanceof CREEPSEntityEvilScientist) && !(entity instanceof CREEPSEntityEvilChicken) && !(entity instanceof CREEPSEntityEvilCreature) && !(entity instanceof CREEPSEntityEvilPig))
            {
                entity.setFire(3);
                entity.motionX = rand.nextFloat() * 0.7F;
                entity.motionY = rand.nextFloat() * 0.4F;
                entity.motionZ = rand.nextFloat() * 0.7F;
                worldObj.playSoundAtEntity(this, "morecreeps:evillight", 0.2F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
            }
        }

        super.onLivingUpdate();
    }

    public void damageEntity(int i)
    {
    	float health = this.getHealth();
        health -= i;
    }

    public void onCollideWithPlayer(EntityPlayer entityIn)
    {
    	entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
    }

    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "morecreeps:evillight";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:evillight";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:evillight";
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return true;
    }
}
