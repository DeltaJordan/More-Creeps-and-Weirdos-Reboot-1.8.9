package fr.elias.morecreeps.common.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import fr.elias.morecreeps.common.entity.CREEPSEntityBabyMummy;
import fr.elias.morecreeps.common.entity.CREEPSEntityBigBaby;

public class EntityBigBabyAI extends EntityAIBase {
	CREEPSEntityBigBaby bbaby;
	public EntityBigBabyAI(CREEPSEntityBigBaby bigBaby)
	{
		bbaby = bigBaby;
	}
	
	@Override
	public boolean shouldExecute()
	{
		EntityLivingBase entitylivingbase = this.bbaby.getAttackTarget();
		return entitylivingbase != null && entitylivingbase.isEntityAlive();
	}
	
	public void updateTask()
    {
    	--bbaby.attackTime;
        EntityLivingBase entitylivingbase = this.bbaby.getAttackTarget();
        double d0 = this.bbaby.getDistanceSqToEntity(entitylivingbase);

        if (d0 < 4.0D)
        {
            if (bbaby.attackTime <= 0)
            {
            	bbaby.attackTime = 20;
                this.bbaby.attackEntityAsMob(entitylivingbase);
            }
            
            this.bbaby.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
        }
        else if (d0 < 256.0D)
        {
            // ATTACK ENTITY GOES HERE
        	bbaby.attackEntity(entitylivingbase, (float)d0);
            this.bbaby.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
        }
        else
        {
            this.bbaby.getNavigator().clearPathEntity();
            this.bbaby.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 0.5D);
        }
    }
}
