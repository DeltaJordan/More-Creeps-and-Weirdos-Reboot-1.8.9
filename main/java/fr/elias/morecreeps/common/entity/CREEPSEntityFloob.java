package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityFloob extends EntityMob
{
    private boolean foundplayer;
    private boolean stolen;
    private PathEntity pathToEntity;
    protected Entity playerToAttack;

    /**
     * returns true if a creature has attacked recently only used for creepers and skeletons
     */
    protected boolean hasAttacked;
    protected ItemStack heldObj;
    private double goX;
    private double goZ;
    private float distance;
    public int itemnumber;
    public int stolenamount;
    public int rayTime;
    private Entity targetedEntity;
    public float modelsize;
    public String texture;

    public CREEPSEntityFloob(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/floob.png";
        stolen = false;
        hasAttacked = false;
        foundplayer = false;
        heldObj = new ItemStack(MoreCreepsAndWeirdos.raygun, 1);
        rayTime = rand.nextInt(50) + 50;
        isImmuneToFire = true;
        modelsize = 1.0F;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
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
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.getAttackTarget() instanceof CREEPSEntityFloob)
        {
        	this.setAttackTarget(null);
        }

        targetedEntity = worldObj.getClosestPlayerToEntity(this, 3D);

        if (targetedEntity != null && (targetedEntity instanceof EntityPlayer) && canEntityBeSeen(targetedEntity))
        {
            float f = rotationYaw;

            for (int i = 0; i < 360; i++)
            {
                rotationYaw = i;
            }

            if (rand.nextInt(4) == 0)
            {
                setAttackTarget((EntityLivingBase) targetedEntity);
            }
        }

        if (rayTime-- < 1)
        {
            rayTime = rand.nextInt(50) + 25;
            double d = 64D;
            targetedEntity = worldObj.getClosestPlayerToEntity(this, 30D);

            if (targetedEntity != null && canEntityBeSeen(targetedEntity) && targetedEntity == getAttackTarget() && (targetedEntity instanceof EntityPlayer) && !isDead)
            {
                double d1 = targetedEntity.getDistanceSqToEntity(this);

                if (d1 < d * d && d1 > 3D)
                {
                    double d2 = targetedEntity.posX - posX;
                    double d3 = (targetedEntity.getBoundingBox().minY + (double)(targetedEntity.height / 2.0F)) - (posY + (double)(height / 2.0F));
                    double d4 = targetedEntity.posZ - posZ;
                    renderYawOffset = rotationYaw = (-(float)Math.atan2(d2, d4) * 180F) / (float)Math.PI;
                    worldObj.playSoundAtEntity(this, "morecreeps:raygun", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    CREEPSEntityRay creepsentityray = new CREEPSEntityRay(worldObj, this);

                    if (creepsentityray != null && getHealth() > 0)
                    {
                    	if(!worldObj.isRemote)
                        worldObj.spawnEntityInWorld(creepsentityray);
                    }
                }
            }
        }

        super.onLivingUpdate();
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 20D);

        if (entityplayer != null && canEntityBeSeen(entityplayer))
        {
            return entityplayer;
        }
        else
        {
            return null;
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
        return "morecreeps:floob";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:floobhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:floobdeath";
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(getBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        //int l = worldObj.getFullBlockLightValue(i, j, k);
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        return i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.stone_slab && i1 != Blocks.double_stone_slab && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0 && worldObj.canSeeSky(new BlockPos(i, j, k)) && rand.nextInt(5) == 0;// && l > 10;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    /**
     * Returns the item that this EntityLiving is holding, if any.
     */
    public ItemStack getHeldItem()
    {
        return heldObj;
    }

    public void confetti()
    {
    	MoreCreepsAndWeirdos.proxy.confettiA(this, worldObj);
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
        Object obj = damagesource.getEntity();

        EntityPlayer player = (EntityPlayer) damagesource.getEntity();
        
        if ((obj instanceof CREEPSEntityRocket) && ((CREEPSEntityRocket)obj).owner != null)
        {
            obj = ((CREEPSEntityRocket)obj).owner;
        }

        if (obj instanceof EntityPlayer)
        {
            MoreCreepsAndWeirdos.floobcount++;

            if (!((EntityPlayerMP)player).getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievefloobkill))
            {
                worldObj.playSoundAtEntity(player, "morecreeps:achievement", 1.0F, 1.0F);
                player.addStat(MoreCreepsAndWeirdos.achievefloobkill, 1);
                confetti();
            }

            if (!((EntityPlayerMP)player).getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievefloobicide) && MoreCreepsAndWeirdos.floobcount >= 20)
            {
                worldObj.playSoundAtEntity(player, "morecreeps:achievement", 1.0F, 1.0F);
                player.addStat(MoreCreepsAndWeirdos.achievefloobicide, 1);
                confetti();
            }
        }

        if (rand.nextInt(6) == 0)
        {
            dropItem(MoreCreepsAndWeirdos.raygun, 1);
        }

        super.onDeath(damagesource);
    }
}
