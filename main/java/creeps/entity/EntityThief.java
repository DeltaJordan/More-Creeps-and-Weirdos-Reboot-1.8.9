package creeps.entity;

import creeps.CreepMain;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.World;

public class EntityThief extends EntityMob{


    public EntityThief(World worldIn)
    {
        super(worldIn);
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, this.field_175455_a);
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
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
    
    
    
    protected String getLivingSound()
    {
        return "creeps:mummy";
    }

/**
* Returns the sound this mob makes when it is hurt.
*/
    protected String getHurtSound()
    {
        return "creeps:mummyhurt";
    }

/**
* Returns the sound this mob makes on death.
*/
    protected String getDeathSound()
    {
        return "creeps:mummydeath";
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


}
