package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityManDog extends EntityMob
{
    private boolean foundplayer;
    private PathEntity pathToEntity;

    /** The Entity this EntityCreature is set to attack. */
    protected Entity entityToAttack;

    /**
     * returns true if a creature has attacked recently only used for creepers and skeletons
     */
    protected boolean hasAttacked;
    protected ItemStack stolengood;
    private float distance;
    public int frisbeetime;
    public boolean chase;
    protected Entity frisbeeent;
    protected ItemStack frisbeestack;
    public boolean fetch;
    public boolean tamed;
    public int tamedfood;
    public int attempts;
    public double dist;
    public double prevdist;
    public int facetime;
    public boolean frisbeehold;
    public boolean superdog;
    public int superstate;
    public int supertimer;
    public double superX;
    public double superY;
    public double superZ;
    public boolean flapswitch;
    public boolean superfly;
    public int superdistance;
    public int superdistancetimer;
    public float speed;
    public double wayX;
    public double wayY;
    public double wayZ;
    public int waypoint;
    public int wayvert;
    public double distcheck;
    public double prevdistcheck;
    public boolean superflag;
    public double wayXa;
    public double wayYa;
    public double wayZa;
    public double wayXb;
    public double wayYb;
    public double wayZb;
    public float modelsize;

    public String texture;
    
    public CREEPSEntityManDog(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/mandog.png";
        hasAttacked = false;
        foundplayer = false;
        frisbeetime = 0;
        chase = false;
        fetch = false;
        tamed = false;
        tamedfood = rand.nextInt(3) + 1;
        attempts = 0;
        dist = 0.0D;
        prevdist = 0.0D;
        facetime = 0;
        frisbeehold = false;
        superdog = false;
        superstate = 0;
        supertimer = 0;
        superdistance = rand.nextInt(10) + 5;
        superdistancetimer = rand.nextInt(100) + 50;
        frisbeestack = new ItemStack(Items.stick, 1);
        modelsize = 1.0F;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new AIAttackEntity());
    }

    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(45D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.666D);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1D);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("Attempts", attempts);
        nbttagcompound.setBoolean("Tamed", tamed);
        nbttagcompound.setBoolean("FrisbeeHold", frisbeehold);
        nbttagcompound.setBoolean("Chase", chase);
        nbttagcompound.setBoolean("Fetch", fetch);
        nbttagcompound.setBoolean("FoundPlayer", foundplayer);
        nbttagcompound.setInteger("TamedFood", tamedfood);
        nbttagcompound.setString("Texture", texture);
        nbttagcompound.setFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        attempts = nbttagcompound.getInteger("Attempts");
        tamed = nbttagcompound.getBoolean("Tamed");
        frisbeehold = nbttagcompound.getBoolean("FrisbeeHold");
        chase = nbttagcompound.getBoolean("Chase");
        fetch = nbttagcompound.getBoolean("Fetch");
        foundplayer = nbttagcompound.getBoolean("FoundPlayer");
        tamedfood = nbttagcompound.getInteger("TamedFood");
        texture = nbttagcompound.getString("Texture");
        modelsize = nbttagcompound.getFloat("ModelSize");
    }

    /**
     * Checks if this entity is inside of an opaque block
     */
    public boolean isEntityInsideOpaqueBlock()
    {
        if (superflag)
        {
            return false;
        }
        else
        {
            return super.isEntityInsideOpaqueBlock();
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (tamed)
        {
            frisbeetime++;

            if (frisbeetime >= 20 && !isDead && !chase && !fetch)
            {
                frisbeeent = null;
                List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(25D, 25D, 25D));

                for (int i = 0; i < list.size(); i++)
                {
                    Entity entity = (Entity)list.get(i);

                    if (entity instanceof CREEPSEntityFrisbee)
                    {
                        frisbeeent = entity;
                        faceEntity(frisbeeent, 360F, 0.0F);
                        entityToAttack = frisbeeent;
                        chase = true;
                        attempts = 0;
                    }
                }
            }

            if (chase && (frisbeeent == null || frisbeeent.isDead))
            {
                chase = false;
                frisbeetime = 0;
            }

            if (chase)
            {
                if (frisbeeent != null)
                {
                    entityToAttack = frisbeeent;

                    if (Math.abs(posY - frisbeeent.posY) < 2D)
                    {
                        faceEntity(frisbeeent, 360F, 0.0F);
                    }
                }

                moveEntityWithHeading((float)motionX, (float)motionZ);
                fallDistance = -25F;
                entityToAttack = frisbeeent;
                prevdist = dist;
                dist = frisbeeent.posX - posX;

                if (dist == prevdist)
                {
                    if (rand.nextInt(2) == 0)
                    {
                        motionX += 0.75D;
                        motionZ += 0.75D;
                    }
                    else
                    {
                        motionX -= 0.75D;
                        motionZ -= 0.75D;
                    }
                }

                if (Math.abs(frisbeeent.posX - posX) < 1.0D && Math.abs(frisbeeent.posY - posY) < 1.0D && Math.abs(frisbeeent.posZ - posZ) < 1.0D)
                {
                    worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    frisbeestack = new ItemStack(MoreCreepsAndWeirdos.frisbee, 1, 0);
                    frisbeeent.setDead();
                    chase = false;
                    fetch = true;
                    frisbeehold = true;
                    EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 32D);

                    if (entityplayer != null)
                    {
                        frisbeeent = entityplayer;
                        faceEntity(entityplayer, 360F, 0.0F);
                    }
                }

                double d = frisbeeent.posX - posX;
                double d2 = frisbeeent.posZ - posZ;
                float f = MathHelper.sqrt_double(d * d + d2 * d2);
                motionX = (d / (double)f) * 0.40000000000000002D * 0.50000000192092897D + motionX * 0.18000000098023225D;
                motionZ = (d2 / (double)f) * 0.40000000000000002D * 0.40000000192092894D + motionZ * 0.18000000098023225D;

                if (onGround)
                {
                    double d4 = (frisbeeent.posY - posY) * 0.18000000098023225D;

                    if (d4 > 0.5D)
                    {
                        d4 = 0.5D;
                    }

                    motionY = d4;
                }

                if (Math.abs(frisbeeent.posX - posX) < 5D && Math.abs(frisbeeent.posZ - posZ) < 5D && frisbeeent.motionX == 0.0D)
                {
                    attempts++;

                    if (attempts > 100)
                    {
                        chase = false;
                        frisbeetime = 0;
                        frisbeeent = null;
                        fetch = true;
                        frisbeehold = false;
                        EntityPlayer entityplayer1 = worldObj.getClosestPlayerToEntity(this, 50D);

                        if (entityplayer1 != null)
                        {
                            frisbeeent = entityplayer1;
                            faceEntity(entityplayer1, 360F, 0.0F);
                        }
                    }
                }
            }

            if (fetch && frisbeeent != null)
            {
                if (Math.abs(frisbeeent.posX - posX) < 2D && Math.abs(frisbeeent.posY - posY) < 2D && Math.abs(frisbeeent.posZ - posZ) < 2D)
                {
                    worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);

                    if (frisbeehold)
                    {
                        dropItem(MoreCreepsAndWeirdos.frisbee, 1);
                    }

                    chase = false;
                    fetch = false;
                }

                fallDistance = -25F;
                double d1 = frisbeeent.posX - posX;
                double d3 = frisbeeent.posZ - posZ;
                float f1 = MathHelper.sqrt_double(d1 * d1 + d3 * d3);
                motionX = (d1 / (double)f1) * 0.40000000000000002D * 0.50000000192092897D + motionX * 0.18000000098023225D;
                motionZ = (d3 / (double)f1) * 0.40000000000000002D * 0.40000000192092894D + motionZ * 0.18000000098023225D;

                if (onGround)
                {
                    double d5 = (frisbeeent.posY - posY) * 0.18000000098023225D;

                    if (d5 > 0.5D)
                    {
                        d5 = 0.5D;
                    }

                    motionY = d5;
                }
            }
        }
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity entity, float f)
    {
    	double d = entity.posX - posX;
    	double d1 = entity.posZ - posZ;
    	float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
    	motionX = (d / (double)f1) * 0.40000000000000002D * 0.30000000192092896D + motionX * 0.18000000098023225D;
    	motionZ = (d1 / (double)f1) * 0.40000000000000002D * 0.44000000192092897D + motionZ * 0.18000000098023225D;
    }
    class AIAttackEntity extends EntityAIBase
    {
    	public AIAttackEntity()
    	{
    		CREEPSEntityManDog.this.entityToAttack = CREEPSEntityManDog.this.getAttackTarget();
    	}
    	
		@Override
		public boolean shouldExecute()
		{
			return !(CREEPSEntityManDog.this.entityToAttack instanceof EntityPlayer) || !CREEPSEntityManDog.this.tamed;
		}
		
		public void updateTask()
		{
			try {
				   float f = CREEPSEntityManDog.this.getDistanceToEntity(getAttackTarget());
				   if(f < 256F)
				   {
				    attackEntity(CREEPSEntityManDog.this.getAttackTarget(), f);
				    CREEPSEntityManDog.this.getLookHelper().setLookPositionWithEntity(CREEPSEntityManDog.this.entityToAttack, 10.0F, 10.0F);
				    CREEPSEntityManDog.this.getNavigator().clearPathEntity();
				    CREEPSEntityManDog.this.getMoveHelper().setMoveTo(CREEPSEntityManDog.this.entityToAttack.posX, CREEPSEntityManDog.this.entityToAttack.posY, CREEPSEntityManDog.this.entityToAttack.posZ, 0.5D);
				   }
				   if(f < 1F)
				   {
				    CREEPSEntityManDog.this.attackEntityAsMob(CREEPSEntityManDog.this.entityToAttack);
				   }
				}
				catch (NullPointerException ex)
				{
				ex.printStackTrace();
				}
		}
    }
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        Object obj = damagesource.getEntity();

        if (obj != null && (obj instanceof CREEPSEntityRocket))
        {
            obj = worldObj.getClosestPlayerToEntity(this, 30D);
        }

        super.attackEntityFrom(DamageSource.causeMobDamage(this), i);
        return true;
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected boolean findPlayerToAttack()
    {
        if (tamed)
        {
            entityToAttack = null;
            return false;
        }
        else
        {
            return false;
        }
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if (itemstack == null && tamed)
        {
            chase = false;
            fetch = false;
            frisbeeent = null;
        }

        if (itemstack != null)
        {
            if (itemstack.getItem() == Items.cooked_porkchop)
            {
                tamedfood--;
                smoke();

                if (tamedfood < 1)
                {
                    smoke();
                    worldObj.playSoundAtEntity(this, "morecreeps:mandogtamed", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    tamed = true;
                    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(45D);
                    texture = "morecreeps:textures/entity/mandogtamed.png";
                }
            }

            if (itemstack.getItem() == Items.bone)
            {
            	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(45D);
            }
        }

        return true;
    }

    private void smoke()
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                double d = rand.nextGaussian() * 0.059999999999999998D;
                double d1 = rand.nextGaussian() * 0.059999999999999998D;
                double d2 = rand.nextGaussian() * 0.059999999999999998D;
                worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height) + (double)i, (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2, new int[0]);
            }
        }
    }

    /**
     * Plays living's sound at its position
     */
    public void playLivingSound()
    {
        String s = getLivingSound();

        if (s != null)
        {
            worldObj.playSoundAtEntity(this, s, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (1.0F - modelsize) * 2.0F);
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        if (superdog)
        {
            return "morecreeps:superdogname";
        }
        else
        {
            return "morecreeps:mandog";
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:mandoghurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:mandogdeath";
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
        return i1 != Blocks.sand && i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.double_stone_slab && i1 != Blocks.stone_slab && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).size() == 0 && worldObj.canSeeSky(bp) && rand.nextInt(25) == 0; //&& l > 10;
    }
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        if (tamed)
        {
            isDead = false;
            deathTime = 0;
            return;
        }
        else
        {
            super.setDead();
            return;
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
        if (tamed)
        {
            return;
        }
        else
        {
            smoke();
            if(!worldObj.isRemote)
            {
                dropItem(Items.bone, rand.nextInt(5));
            }
            super.onDeath(damagesource);
            return;
        }
    }
}
