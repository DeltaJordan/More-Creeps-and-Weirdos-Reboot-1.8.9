package fr.elias.morecreeps.common.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import fr.elias.morecreeps.client.particles.CREEPSFxBlood;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import fr.elias.morecreeps.common.Reference;

public class CREEPSEntityArmyGuyArm extends EntityAnimal
{
    protected double attackrange;
    protected int attack;
    public int lifetime;
    public float modelsize;
    public ResourceLocation texture;
    public CREEPSEntityArmyGuyArm(World world)
    {
        super(world);
        setSize(0.6F, 0.3F);
        lifetime = 250;
        modelsize = 1.0F;
        texture = new ResourceLocation(Reference.MOD_ID, 
        		Reference.TEXTURE_PATH_ENTITES + Reference.TEXTURE_ARMY_GUY_ARM);
    }

    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityAnimal spawnBabyAnimal(EntityAnimal entityanimal)
    {
        return new CREEPSEntityArmyGuyArm(worldObj);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (lifetime-- < 0)
        {
            setDead();
        }
    }

    public void blood()
    {
    	if(worldObj.isRemote)
    	{
    		MoreCreepsAndWeirdos.proxy.blood(worldObj, posX, posY, posZ, true);
    	}
    }
    public void setDead()
    {
        blood();
        super.setDead();
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

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
    	if(!worldObj.isRemote)
    	{
            dropItem(MoreCreepsAndWeirdos.limbs, 1);   		
    	}
        super.onDeath(damagesource);
    }

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}
}
