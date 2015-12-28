package fr.elias.morecreeps.common.entity.ai;

import java.util.List;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import fr.elias.morecreeps.common.entity.CREEPSEntityArmyGuy;
import fr.elias.morecreeps.common.entity.CREEPSEntityGuineaPig;
import fr.elias.morecreeps.common.entity.CREEPSEntityHotdog;
import fr.elias.morecreeps.common.entity.CREEPSEntityHunchback;

public class EntityArmyGuyAI extends EntityAIBase {

	public CREEPSEntityArmyGuy armyGuy;
	public EntityArmyGuyAI(CREEPSEntityArmyGuy creepsarmyguy)
	{
		armyGuy = creepsarmyguy;
	}
	@Override
	public boolean shouldExecute()
	{
		EntityLivingBase entityToAttack = armyGuy.getAttackTarget();
		return entityToAttack != null && entityToAttack.isEntityAlive();
	}
	
	public void updateTask()
    {
		findPlayerToAttack();
		EntityLivingBase entityToAttack = armyGuy.getAttackTarget();
		double d0 = this.armyGuy.getDistanceSqToEntity(entityToAttack);
        if (armyGuy.loyal && entityToAttack != null && (entityToAttack instanceof CREEPSEntityArmyGuy) && ((CREEPSEntityArmyGuy)entityToAttack).loyal)
        {
            entityToAttack = null;
        }
        if (!armyGuy.hasPath() && armyGuy.loyal && armyGuy.ridingEntity == null)
        {
            EntityPlayer entityplayersp = (EntityPlayer) armyGuy.getOwnerEntity();

            if (entityplayersp != null)
            {
                float f = entityplayersp.getDistanceToEntity(armyGuy);

                if (f <= 5F);
            }
        }
        if ((entityToAttack instanceof EntityPlayer) && armyGuy.loyal)
        {
        	EntityPlayer entityplayersp1 = (EntityPlayer) armyGuy.getOwnerEntity();

            if (armyGuy.getDistanceToEntity(entityplayersp1) < 6F)
            {
                entityToAttack = null;
            }
        }
        if (d0 < 4.0D)
        {
            if (armyGuy.attackTime <= 0)
            {
            	armyGuy.attackTime = 20;
                this.armyGuy.attackEntityAsMob(entityToAttack);
            }
            
            this.armyGuy.getMoveHelper().setMoveTo(entityToAttack.posX, entityToAttack.posY, entityToAttack.posZ, 1.0D);
        }
        else if (d0 < 256.0D)
        {
            // ATTACK ENTITY GOES HERE
        	armyGuy.attackEntity(entityToAttack, (float)d0);
            this.armyGuy.getLookHelper().setLookPositionWithEntity(entityToAttack, 10.0F, 10.0F);
        }
        else
        {
            this.armyGuy.getNavigator().clearPathEntity();
            this.armyGuy.getMoveHelper().setMoveTo(entityToAttack.posX, entityToAttack.posY, entityToAttack.posZ, 0.5D);
        }
        
    }
    protected Entity findPlayerToAttack()
    {
        Object obj = null;

        if (armyGuy.loyal)
        {
            List list = armyGuy.worldObj.getEntitiesWithinAABBExcludingEntity(armyGuy, armyGuy.getBoundingBox().expand(16D, 16D, 16D));

            for (int i = 0; i < list.size(); i++)
            {
                Entity entity = (Entity)list.get(i);

                if (entity instanceof EntityCreature)
                {
                    EntityCreature entitycreature = (EntityCreature)entity;

                    if ((entitycreature.getAttackTarget() instanceof EntityPlayer) && !(entitycreature instanceof CREEPSEntityHotdog) && !(entitycreature instanceof CREEPSEntityHunchback) && !(entitycreature instanceof CREEPSEntityGuineaPig) && (!(entitycreature instanceof CREEPSEntityArmyGuy) || !((CREEPSEntityArmyGuy)entitycreature).loyal))
                    {
                        obj = entitycreature;
                    }
                }

                if (!(entity instanceof EntityPlayer) || (armyGuy.getAttackTarget() instanceof EntityPlayer))
                {
                    continue;
                }

                EntityPlayer entityplayer = (EntityPlayer)entity;

                if (entityplayer == null || obj != null && !(obj instanceof EntityPlayer))
                {
                    continue;
                }

                armyGuy.distance = armyGuy.getDistanceToEntity(entityplayer);

                if (armyGuy.distance < 8F)
                {
                    obj = null;
                }
                else
                {
                    obj = entityplayer;
                }
            }
        }
        return ((Entity)(obj));
    }
}
