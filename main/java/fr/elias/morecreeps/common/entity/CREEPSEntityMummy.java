package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityMummy extends EntityMob
{
	public String texture;
    public CREEPSEntityMummy(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/mummy.png";
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.4D, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2D);
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
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        //int l = worldObj.getFullBlockLightValue(i, j, k);
        BlockPos bp = new BlockPos(i, j, k);
        Block i1 = worldObj.getBlockState(bp.down()).getBlock();
        int j1 = worldObj.countEntities(CREEPSEntityMummy.class);
        return (i1 == Blocks.stone || i1 == Blocks.sand || i1 == Blocks.gravel || i1 == Blocks.bedrock || i1 == Blocks.double_stone_slab || i1 == Blocks.stone_slab) && i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).size() == 0 && /*l < 10 && */j1 < 15;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (worldObj.isDaytime())
        {
            float f = getBrightness(1.0F);

            if (f > 0.5F && worldObj.canSeeSky(new BlockPos(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ))) && rand.nextFloat() * 30F < (f - 0.4F) * 2.0F)
            {
                setFire(10);
            }
        }

        super.onLivingUpdate();
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "morecreeps:mummy";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:mummyhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:mummydeath";
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
    	if(!worldObj.isRemote)
    	{
            if (rand.nextInt(5) == 0)
            {
                dropItem(Item.getItemFromBlock(Blocks.sand), rand.nextInt(6) + 1);
                dropItem(Item.getItemFromBlock(Blocks.sandstone), rand.nextInt(3) + 1);
            }
            else if (rand.nextInt(5) == 0)
            {
                dropItem(MoreCreepsAndWeirdos.bandaid, rand.nextInt(8) + 1);
            }
            else
            {
                dropItem(Item.getItemFromBlock(Blocks.sand), rand.nextInt(2) + 1);
            }
    	}

        super.onDeath(damagesource);
    }
}
