package creeps.entity;

import creeps.CreepMain;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySneakySal extends EntityMob implements IRangedAttackMob{
	
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
	
	public EntitySneakySal(World worldIn)
    {
        super(worldIn);
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
        this.setSize(0.6F, 1.95F);
        
        
    }
	protected void applyEntityAI()
    {
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.getDataWatcher().addObject(12, Byte.valueOf((byte)0));
        this.getDataWatcher().addObject(13, Byte.valueOf((byte)0));
        this.getDataWatcher().addObject(14, Byte.valueOf((byte)0));
    }
    
    public void setAttackTarget(EntityLivingBase p_70624_1_)
    {
        super.setAttackTarget(p_70624_1_);
        this.setAngry(false, worldObj);
        
    }
    
    
    protected String getLivingSound()
    {
        return "creeps:sal";
    }

/**
* Returns the sound this mob makes when it is hurt.
*/
    protected String getHurtSound()
    {
        return "creeps:salhurt";
    }

/**
* Returns the sound this mob makes on death.
*/
    protected String getDeathSound()
    {
        return "creeps:saldeath";
    }

/**
* Returns the volume for the sounds this mob makes.
*/
    protected float getSoundVolume()
    {
        return 1.0F;
    }

    protected void dropFewItems(boolean flag, int num)
    {
    	int l = this.rand.nextInt(2);
        int band = this.rand.nextInt(6);
        int sand = this.rand.nextInt(2);
        int sands = this.rand.nextInt(2);
        int wool = this.rand.nextInt(2);

        if (num > 0)
        {
            l += this.rand.nextInt(num + 1);
        }

        for (int i1 = 0; i1 < l; ++i1)
        {
            this.dropItem(Item.getItemFromBlock(Blocks.wool), wool);
            this.dropItem(Item.getItemFromBlock(Blocks.sand), sand);
            this.dropItem(Item.getItemFromBlock(Blocks.sandstone), sands);
            this.dropItem(CreepMain.ItemBandAid, band);
        }
    	
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!this.worldObj.isRemote && this.getAttackTarget() == null && this.isAngry())
        {
            this.setAngry(false, worldObj);
        }
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
            Entity entity = source.getEntity();

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
            {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }
    
    public boolean attackEntityAsMob(Entity entity)
    {
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue()));

        if (flag)
        {
            this.func_174815_a(this, entity);
        }

        return flag;
    }
    
    public boolean isAngry()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }
    
    public void setAngry(boolean angry, World worldIn)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (angry)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 2)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -3)));            
        }
        
        if (worldIn != null && !worldIn.isRemote) 
        {
            this.setCombatTask();
        }
    }
    
    protected boolean canDespawn()
    {
        return false;
    }
    
    public void setCombatTask()
    {
    	
    	this.tasks.addTask(4, this.aiArrowAttack);
    	
       
    }
    
    public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_){
    	
        EntityArrow entityarrow = new EntityArrow(this.worldObj, this, p_82196_1_, 1.6F, (float)(14 - this.worldObj.getDifficulty().getDifficultyId() * 4));
        entityarrow.setDamage((double)(p_82196_2_ * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.worldObj.getDifficulty().getDifficultyId() * 0.11F));
        this.playSound("creeps:gun", 1.0F, 1.0F);
        this.worldObj.spawnEntityInWorld(entityarrow);
        
    }

}
