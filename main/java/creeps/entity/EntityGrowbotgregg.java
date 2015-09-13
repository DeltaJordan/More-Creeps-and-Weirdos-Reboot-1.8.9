package creeps.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;

public class EntityGrowbotgregg extends EntityMob {

    public EntityGrowbotgregg(World w) {
	super(w);
	((PathNavigateGround) this.getNavigator()).func_179688_b(true);
	this.tasks.addTask(0, new EntityAISwimming(this));
	this.tasks.addTask(2, new EntityAIAttackOnCollide(this,
		EntityPlayer.class, 1.0D, false));
	this.tasks.addTask(2, this.field_175455_a);
	this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
	this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
	this.tasks.addTask(8, new EntityAIWatchClosest(this,
		EntityPlayer.class, 8.0F));
	this.tasks.addTask(8, new EntityAILookIdle(this));
	this.applyEntityAI();
	this.setSize(0.6F, 1.95F);
    }

    protected void applyEntityAI() {
	this.tasks.addTask(4, new EntityAIAttackOnCollide(this,
		EntityVillager.class, 1.0D, true));
	this.tasks.addTask(4, new EntityAIAttackOnCollide(this,
		EntityIronGolem.class, 1.0D, true));
	this.tasks
		.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true,
		new Class[] { EntityPigZombie.class }));
	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this,
		EntityPlayer.class, true));
	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this,
		EntityVillager.class, false));
	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this,
		EntityIronGolem.class, true));
    }

    protected void applyEntityAttributes() {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.followRange)
		.setBaseValue(35.0D);
	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
		.setBaseValue(0.23000000417232513D);
	this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
		.setBaseValue(3.0D);
    }

    protected void entityInit() {
	super.entityInit();
	this.getDataWatcher().addObject(12, Byte.valueOf((byte) 0));
	this.getDataWatcher().addObject(13, Byte.valueOf((byte) 0));
	this.getDataWatcher().addObject(14, Byte.valueOf((byte) 0));
    }
}
