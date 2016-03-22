package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityDoghouse extends EntityAnimal
{
    public float modelsize;
    public boolean houseoccupied;
    public String texture;

    public CREEPSEntityDoghouse(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/doghouse.png";
        modelsize = 2.5F;
        setSize(width * modelsize, height * modelsize);
        houseoccupied = false;
    }

    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        if (ridingEntity instanceof CREEPSEntityHotdog)
        {
            return - (double)1.1F;
        }
        else
        {
            return super.getYOffset();
        }
    }

    public void updateRiderPosition()
    {
        if (riddenByEntity == null)
        {
            return;
        }

        if (riddenByEntity instanceof CREEPSEntityHotdog)
        {
            riddenByEntity.setPosition(posX, posY, posZ);
            return;
        }
        else
        {
            return;
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (rand.nextInt(10) == 0 && riddenByEntity != null)
        {
        	if(worldObj.isRemote)
        		MoreCreepsAndWeirdos.proxy.bubbleDoghouse(worldObj, this);
        }

        if (inWater)
        {
            setDead();
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        ignoreFrustumCheck = true;
        super.onUpdate();
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return getHealth() < 1;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if (riddenByEntity == null)
        {
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(16D, 16D, 16D));
            int i = 0;

            do
            {
                if (i >= list.size())
                {
                    break;
                }

                Entity entity = (Entity)list.get(i);

                if ((entity instanceof CREEPSEntityHotdog) && entity.ridingEntity == null)
                {
                    CREEPSEntityHotdog creepsentityhotdog = (CREEPSEntityHotdog)entity;

                    if (creepsentityhotdog.tamed)
                    {
                        creepsentityhotdog.mountEntity(this);
                        houseoccupied = true;
                        worldObj.playSoundAtEntity(this, "morecreeps:hotdogpickup", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                        break;
                    }
                }

                i++;
            }
            while (true);
        }
        else
        {
            riddenByEntity.fallDistance = -10F;
            worldObj.playSoundAtEntity(this, "morecreeps:hotdogputdown", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            houseoccupied = false;
            riddenByEntity.mountEntity(null);
        }

        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (i < 1)
        {
            i = 1;
        }

        hurtTime = maxHurtTime = 10;
        smoke();

        if (getHealth() <= 0)
        {
            worldObj.playSoundAtEntity(this, getDeathSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            onDeath(damagesource);
        }
        else
        {
            worldObj.playSoundAtEntity(this, getHurtSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
        }

        super.attackEntityFrom(damagesource, i);
        return true;
    }

    public void loadHouse()
    {
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(16D, 16D, 16D));

        for (int i = 0; i < list.size(); i++)
        {
            Entity entity = (Entity)list.get(i);

            if (entity != null && (entity instanceof CREEPSEntityHotdog) && ((CREEPSEntityHotdog)entity).tamed)
            {
                entity.mountEntity(this);
                houseoccupied = true;
                return;
            }
        }

        houseoccupied = false;
    }

    /**
     * Checks if this entity is inside of an opaque block
     */
    public boolean isEntityInsideOpaqueBlock()
    {
        return getHealth() <= 0;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setFloat("ModelSize", modelsize);
        nbttagcompound.setBoolean("Occupied", houseoccupied);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        modelsize = nbttagcompound.getFloat("ModelSize");
        houseoccupied = nbttagcompound.getBoolean("Occupied");
        loadHouse();
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    private void smoke()
    {
    	if(worldObj.isRemote)
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, ((posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width) + (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width - (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F) + (double)i) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)i - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, ((posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width) + (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F) + (double)i) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width - (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)i - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, ((posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width) + (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F) + (double)i) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width - (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)i - (double)width, d, d1, d2);
            }
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return null;
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return null;
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return null;
    }

    public void confetti()
    {
    	List list = worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(posX, posY, posZ, posX + 1, posY + 1, posZ + 1).expand(8D, 4D, 8D));
    	for(int i = 0; i < list.size(); i++)
    	{
    		Entity entity = (Entity) list.get(i);
    		float f = this.getDistanceToEntity(entity);
    		if(f < 6F)
    		{
    			if(!worldObj.isRemote)
    			{
        	    	MoreCreepsAndWeirdos.proxy.confettiA((EntityPlayer) entity, worldObj);
    			}
    		}
    	}
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        smoke();
        worldObj.playSoundAtEntity(this, getDeathSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
        super.setDead();
    }

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new CREEPSEntityDoghouse(worldObj);
	}
}
