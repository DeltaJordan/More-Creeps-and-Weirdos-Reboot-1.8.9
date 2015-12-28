package fr.elias.morecreeps.common.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import fr.elias.morecreeps.common.entity.CREEPSEntityBabyMummy;

public class EntityBabyMummyAI extends EntityAIBase
{
	CREEPSEntityBabyMummy bmummy;
	public EntityBabyMummyAI(CREEPSEntityBabyMummy babyMummy)
	{
		bmummy = babyMummy;
	}
	
	@Override
	public boolean shouldExecute()
	{
		EntityLivingBase entitylivingbase = this.bmummy.getAttackTarget();
		return entitylivingbase != null && entitylivingbase.isEntityAlive();
	}
	
	public void updateTask()
    {
    	--bmummy.attackTime;
        EntityLivingBase entitylivingbase = this.bmummy.getAttackTarget();
        double d0 = this.bmummy.getDistanceSqToEntity(entitylivingbase);

        if (d0 < 4.0D)
        {
            if (bmummy.attackTime <= 0)
            {
            	bmummy.attackTime = 20;
                this.bmummy.attackEntityAsMob(entitylivingbase);
            }
            
            this.bmummy.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
        }
        else if (d0 < 256.0D)
        {
            // ATTACK ENTITY GOES HERE
        	bmummy.attackEntity(entitylivingbase, (float)d0);
            this.bmummy.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
        }
        else
        {
            this.bmummy.getNavigator().clearPathEntity();
            this.bmummy.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 0.5D);
        }
    }
}
