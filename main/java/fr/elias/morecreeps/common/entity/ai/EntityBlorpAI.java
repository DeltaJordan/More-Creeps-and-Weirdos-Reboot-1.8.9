package fr.elias.morecreeps.common.entity.ai;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import fr.elias.morecreeps.common.entity.CREEPSEntityBlorp;

public class EntityBlorpAI extends EntityAIBase {

	public CREEPSEntityBlorp blorp;
	public Random rand = new Random();
	public EntityBlorpAI(CREEPSEntityBlorp creepsblorp)
	{
		blorp = creepsblorp;
	}
	@Override
	public boolean shouldExecute()
	{
		return true;
	}
	
	public void updateTask()
    {
		findPlayerToAttack();
		EntityLivingBase entityToAttack = blorp.getAttackTarget();
		double d0 = this.blorp.getDistanceSqToEntity(entityToAttack);

        if (d0 < 4.0D)
        {
            if (blorp.attackTime <= 0)
            {
            	blorp.attackTime = 20;
                this.blorp.attackEntityAsMob(entityToAttack);
            }
            
            this.blorp.getMoveHelper().setMoveTo(entityToAttack.posX, entityToAttack.posY, entityToAttack.posZ, 1.0D);
        }
        else if (d0 < 256.0D)
        {
            // ATTACK ENTITY GOES HERE
        	blorp.attackEntity(entityToAttack, (float)d0);
            this.blorp.getLookHelper().setLookPositionWithEntity(entityToAttack, 10.0F, 10.0F);
        }
        else
        {
            this.blorp.getNavigator().clearPathEntity();
            this.blorp.getMoveHelper().setMoveTo(entityToAttack.posX, entityToAttack.posY, entityToAttack.posZ, 0.5D);
        }
        
    }
    protected Entity findPlayerToAttack()
    {
        float f = blorp.getBrightness(1.0F);

        if (f < 0.0F || blorp.angry)
        {
            EntityPlayer entityplayer = blorp.worldObj.getClosestPlayerToEntity(blorp, blorp.attackrange);

            if (entityplayer != null)
            {
                return entityplayer;
            }
        }

        if (rand.nextInt(10) == 0)
        {
            EntityLivingBase entityliving = getClosestTarget(blorp, 6D);
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
        EntityLivingBase entityliving = null;

        for (int i = 0; i < blorp.worldObj.loadedEntityList.size(); i++)
        {
            Entity entity1 = (Entity)blorp.worldObj.loadedEntityList.get(i);

            if (!(entity1 instanceof EntityLivingBase) || entity1 == entity || entity1 == entity.riddenByEntity || entity1 == entity.ridingEntity || (entity1 instanceof EntityPlayer) || (entity1 instanceof EntityMob) || (entity1 instanceof EntityAnimal) && !(entity1 instanceof CREEPSEntityBlorp))
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
}
