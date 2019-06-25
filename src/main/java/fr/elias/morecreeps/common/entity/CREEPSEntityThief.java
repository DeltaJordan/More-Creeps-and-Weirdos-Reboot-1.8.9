package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CREEPSEntityThief extends EntityMob
{
    private boolean foundplayer;
    private boolean stolen;
    private PathEntity pathToEntity;

    /**
     * returns true if a creature has attacked recently only used for creepers and skeletons
     */
    protected boolean hasAttacked;
    protected ItemStack stolengood;
    private double goX;
    private double goZ;
    private float distance;
    public int itemnumber;
    public int stolenamount;
    public int tempDamage;
    public float modelsize;
    public String texture;

    public CREEPSEntityThief(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/thief.png";
        stolen = false;
        hasAttacked = false;
        foundplayer = false;
        tempDamage = 0;
        modelsize = 1.0F;
        ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(2, new AIThief()); 
        tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
        tasks.addTask(5, new EntityAIWander(this, 0.35D));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 16F));
        tasks.addTask(7, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
    }

    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.65D);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1D);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        modelsize = nbttagcompound.getFloat("ModelSize");
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected EntityPlayer findPlayerToAttack()
    {
        if (!stolen)
        {
            EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 16D);

            if (entityplayer != null)
            {
                Object obj = null;
                ItemStack aitemstack[] = entityplayer.inventory.mainInventory;
                itemnumber = -1;

                for (int i = 0; i < aitemstack.length; i++)
                {
                    ItemStack itemstack1 = aitemstack[i];

                    if (itemstack1 != null)
                    {
                        ItemStack itemstack = itemstack1;
                        itemnumber = i;
                    }
                }

                if (itemnumber >= 0)
                {
                    if (!foundplayer)
                    {
                        if (rand.nextInt(2) == 0)
                        {
                            worldObj.playSoundAtEntity(entityplayer, "morecreeps:thieffindplayer", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.5F);
                        }

                        foundplayer = true;
                    }

                    return entityplayer;
                }
            }
        }

        return null;
    }

    /**
     * Returns the item that this EntityLiving is holding, if any.
     */
    public ItemStack getHeldItem()
    {
        return stolengood;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (handleWaterMovement())
        {
            goX *= -1D;
            goZ *= -1D;
            motionX += goX * 0.10000000149011612D;
            motionZ += goZ * 0.10000000149011612D;
            motionY += 0.25D;
        }

        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 25D);

        if (entityplayer != null && !stolen)
        {
            distance = getDistanceToEntity(entityplayer);
            findPlayerToAttack();            
        }
        else
        {
            distance = 999F;
            //entityToAttack = null;
            this.setAttackTarget(null);
        }

        if (stolen)
        {
            //entityToAttack = null;
        	this.setAttackTarget(null);
        	
            if (isJumping)
            {
                motionX += goX * 0.30000001192092896D;
                motionZ += goZ * 0.30000001192092896D;
            }
            else
            {
                motionX += goX;
                motionZ += goZ;
            }

            moveEntityWithHeading((float)motionX, (float)motionZ);

            if (prevPosY / posY == 1.0D)
            {
                if (rand.nextInt(25) == 0)
                {
                    motionX -= goX;
                }
                else
                {
                    motionX += goX;
                }

                if (rand.nextInt(25) == 0)
                {
                    motionZ -= goZ;
                }
                else
                {
                    motionZ += goZ;
                }
            }

            if (prevPosX == posX && rand.nextInt(50) == 0)
            {
                goX *= -1D;
            }

            if (prevPosZ == posZ && rand.nextInt(50) == 0)
            {
                goZ *= -1D;
            }

            if (rand.nextInt(500) == 0)
            {
                goX *= -1D;
            }

            if (rand.nextInt(700) == 0)
            {
                goZ *= -1D;
            }
        }

        if (entityplayer != null && !stolen && distance < 4F && canEntityBeSeen(entityplayer) && getHealth() > 0)
        {
            ItemStack itemstack = null;
            ItemStack aitemstack[] = entityplayer.inventory.mainInventory;
            itemnumber = -1;

            for (int i = 0; i < aitemstack.length; i++)
            {
                ItemStack itemstack1 = aitemstack[i];

                if (itemstack1 == null)
                {
                    continue;
                }

                itemstack = itemstack1;
                itemnumber = i;

                if (rand.nextInt(4) == 0)
                {
                    break;
                }
            }

            if (itemstack == null)
            {
                //entityToAttack = null;
            	this.setAttackTarget(null);
            }

            if (itemstack != null && !stolen)
            {
                stolengood = itemstack;
                worldObj.playSoundAtEntity(this, "random.pop", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 6.2F + 1.0F);
                stolenamount = rand.nextInt(itemstack.stackSize) + 1;

                if (stolenamount > itemstack.stackSize)
                {
                    stolenamount = itemstack.stackSize;
                }

                if (stolenamount == 1 && itemstack.isItemDamaged())
                {
                    tempDamage = itemstack.getItemDamage();
                }

                if (stolenamount == itemstack.stackSize)
                {
                    entityplayer.inventory.mainInventory[itemnumber] = null;
                }
                else
                {
                    itemstack.stackSize -= stolenamount;
                }

                stolen = true;
                worldObj.playSoundAtEntity(this, "morecreeps:thiefsteal", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                //entityToAttack = null;
                this.setAttackTarget(null);
                goX = 0.044999999999999998D;
                goZ = 0.044999999999999998D;

                if (rand.nextInt(5) == 0)
                {
                    goX *= -1D;
                }

                if (rand.nextInt(5) == 0)
                {
                    goZ *= -1D;
                }

                for (int j = 0; j < 10; j++)
                {
                    double d = rand.nextGaussian() * 0.02D;
                    double d1 = rand.nextGaussian() * 0.02D;
                    double d2 = rand.nextGaussian() * 0.02D;
                    worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2, new int[0]);
                }
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
        return "morecreeps:thief";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:thiefhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:thiefdeath";
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        int l = worldObj.getBlockLightOpacity(new BlockPos(i, j, k));
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();

        if (j < 50)
        {
            return true;
        }
        else
        {
            return i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.double_stone_slab && i1 != Blocks.stone_slab && worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).size() == 0 && worldObj.canSeeSky(new BlockPos(i, j, k)) && rand.nextInt(10) == 0 && l > 7;
        }
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    public int getItemDamage()
    {
        return 25;
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
    	if(!worldObj.isRemote)
    	{
            if (getHeldItem() != null)
            {
                if (tempDamage > 0)
                {
                    ItemStack itemstack = getHeldItem().copy();
                    entityDropItem(itemstack, 0.0F);
                    stolengood = null;
                }
                else
                {
                    dropItem(getHeldItem().getItem(), stolenamount);
                }
            }
    	}

        super.onDeath(damagesource);
    }
    
    class AIThief extends EntityAIBase {

    	public CREEPSEntityThief thief = CREEPSEntityThief.this;
    	public AIThief() {}
    	
		@Override
		public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.thief.getAttackTarget();
            return entitylivingbase != null && !this.thief.stolen;
		}
        public void updateTask()
        {
            EntityLivingBase entitylivingbase = this.thief.getAttackTarget();
            double d0 = this.thief.getDistanceSqToEntity(entitylivingbase);

            if (d0 < 4.0D)
            {
                this.thief.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            }
            else if (d0 < 256.0D)
            {
                this.thief.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
            }
            else
            {
                this.thief.getNavigator().clearPathEntity();
                this.thief.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 0.5D);
            	
                this.thief.findPlayerToAttack();
            }
        }
    }
}
