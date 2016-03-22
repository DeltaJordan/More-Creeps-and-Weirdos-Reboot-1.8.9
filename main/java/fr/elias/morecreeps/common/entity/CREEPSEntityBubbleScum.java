package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.client.particles.CREEPSFxBubbles;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import fr.elias.morecreeps.common.entity.ai.EntityBlorpAI;

public class CREEPSEntityBubbleScum extends EntityCreature
{
    public boolean rideable;
    public int interest;
    private boolean primed;
    public boolean tamed;
    private float distance;
    public int armor;
    public boolean used;
    public boolean grab;
    public List piglist;
    public int pigstack;
    public int level;
    public float totaldamage;
    public int alt;
    public boolean hotelbuilt;
    public int wanderstate;
    public int speedboost;
    public int totalexperience;
    public int part;
    public boolean tossed;
    public float modelsize;
    public int attackTime;
    public String texture;
    static final int leveldamage[] =
    {
        0, 200, 600, 1000, 1500, 2000, 2700, 3500, 4400, 5400,
        6600, 7900, 9300, 10800, 12400, 14100, 15800, 17600, 19500, 21500,
        25000
    };
    static final String levelname[] =
    {
        "Guinea Pig", "A nothing pig", "An inexperienced pig", "Trainee", "Private", "Private First Class", "Corporal", "Sergeant", "Staff Sergeant", "Sergeant First Class",
        "Master Segeant", "First Sergeant", "Sergeant Major", "Command Sergeant Major", "Second Lieutenant", "First Lieutenant", "Captain", "Major", "Lieutenant Colonel", "Colonel",
        "General of the Pig Army", "General of the Pig Army"
    };

    public CREEPSEntityBubbleScum(World world)
    {
        super(world);
        primed = false;
        texture = "morecreeps:textures/entity/bubblescum.png";
        setSize(0.6F, 0.6F);
        rideable = false;
        pigstack = 0;
        level = 1;
        totaldamage = 0.0F;
        alt = 1;
        hotelbuilt = false;
        wanderstate = 0;
        speedboost = 0;
        totalexperience = 0;
        fallDistance = -5F;
        tossed = false;
        modelsize = 1.0F;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(5, new EntityAIHurtByTarget(this, true, new Class[0]));
    }
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
    }
    //Don't need this anymore...
    /*protected void attackEntity(Entity entity, float f)
    {
        double d = entity.posX - posX;
        double d1 = entity.posZ - posZ;
        float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
        motionX = (d / (double)f1) * 0.40000000000000002D * 0.16000000192092895D + motionX * 0.18000000098023225D;
        motionZ = (d1 / (double)f1) * 0.40000000000000002D * 0.12000000192092895D + motionZ * 0.18000000098023225D;

        if ((double)f < 2D && entity.getBoundingBox().maxY > getBoundingBox().minY && entity.getBoundingBox().minY < getBoundingBox().maxY)
        {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
        }
    }*/

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        Entity entity = damagesource.getEntity();
        knockBack(this, i, 3D, 5D);
        EntityLivingBase entityToAttack = getAttackTarget();
        entityToAttack = (EntityLivingBase) entity;
        return super.attackEntityFrom(damagesource, i);
    }

    /**finally, i will not use this..**/
    /*public EntityPlayer getPlayer()
    {
        EntityPlayer entityplayer = null;

        for (int i = 0; i < worldObj.playerEntities.size(); ++i)
        {
            EntityPlayer entityplayer1 = (EntityPlayer)worldObj.playerEntities.get(i);

            if (IEntitySelector.NOT_SPECTATING.apply(entityplayer1))
            {
            	entityplayer = entityplayer1;
            }
        }

        return entityplayer;
    }*/
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (modelsize > 1.0F)
        {
            ignoreFrustumCheck = true;
        }
        EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 1);
        float f = fallDistance;

        //TODO find another player instance...
        if (f > 10F && tossed)
        {
            confetti();
            tossed = false;
            worldObj.playSoundAtEntity(player, "morecreeps:achievement", 1.0F, 1.0F);
            player.addStat(MoreCreepsAndWeirdos.achieve10bubble, 1);
        }

        if (f > 25F && tossed)
        {
            confetti();
            tossed = false;
            worldObj.playSoundAtEntity(player, "morecreeps:achievement", 1.0F, 1.0F);
            player.addStat(MoreCreepsAndWeirdos.achieve25bubble, 1);
        }

        if (f > 50F && tossed)
        {
            confetti();
            tossed = false;
            worldObj.playSoundAtEntity(player, "morecreeps:achievement", 1.0F, 1.0F);
            player.addStat(MoreCreepsAndWeirdos.achieve50bubble, 1);
        }

        if (f > 100F && tossed)
        {
            confetti();
            tossed = false;
            worldObj.playSoundAtEntity(player, "morecreeps:achievement", 1.0F, 1.0F);
            player.addStat(MoreCreepsAndWeirdos.achieve100bubble, 1);
        }

        if (rand.nextInt(3) == 0)
        {
        	if(worldObj.isRemote)
        	{
        		MoreCreepsAndWeirdos.proxy.bubble(worldObj, this);
        	}
        }

        if (onGround)
        {
            tossed = false;
        }
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

    private void smoke()
    {
        for (int i = 0; i < 7; i++)
        {
            double d = rand.nextGaussian() * 0.02D;
            double d2 = rand.nextGaussian() * 0.02D;
            double d4 = rand.nextGaussian() * 0.02D;
            worldObj.spawnParticle(EnumParticleTypes.HEART, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d2, d4);
        }

        for (int j = 0; j < 4; j++)
        {
            for (int k = 0; k < 10; k++)
            {
                double d1 = rand.nextGaussian() * 0.02D;
                double d3 = rand.nextGaussian() * 0.02D;
                double d5 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height) + (double)j, (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d1, d3, d5);
            }
        }
    }

    private void smokePlain()
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height) + (double)i, (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
            }
        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(getBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        return worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0 && rand.nextInt(5) == 0;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 12;
    }

    public double getYOffset()
    {
        if (ridingEntity instanceof EntityPlayer)
        {
            float f = 1.0F - modelsize;

            if (modelsize > 1.0F)
            {
                f *= 0.55F;
            }
            
            return (double)((getMountedYOffset() - 1.5F) + f * 0.6F);
        }
        else
        {
            return (double)getMountedYOffset();
        }
    }

    public void updateRiderPosition()
    {
        riddenByEntity.setPosition(posX, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ);
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset()
    {
        return 0.5D;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        used = false;

        if (itemstack == null && !(getAttackTarget() instanceof EntityPlayer))
        {
            rotationYaw = entityplayer.rotationYaw;
            Object obj = entityplayer;

            if (ridingEntity != obj)
            {
                int i;

                for (i = 0; ((Entity)obj).riddenByEntity != null && i < 20; i++)
                {
                    obj = ((Entity)obj).riddenByEntity;
                }

                if (i < 20)
                {
                    rotationYaw = ((Entity)obj).rotationYaw;
                    mountEntity((Entity)obj);
                    worldObj.playSoundAtEntity(this, "morecreeps:bubblescumpickup", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }
            }
            else
            {
                int j;

                for (j = 0; ((Entity)obj).riddenByEntity != null && j < 20; j++)
                {
                    obj = ((Entity)obj).riddenByEntity;
                }

                if (j < 20)
                {
                    rotationYaw = ((Entity)obj).rotationYaw;
                    ((Entity)obj).fallDistance = -5F;
                    ((Entity)obj).mountEntity(null);
                    double d = -MathHelper.sin((entityplayer.rotationYaw * (float)Math.PI) / 180F);
                    double d1 = MathHelper.cos((entityplayer.rotationYaw * (float)Math.PI) / 180F);
                    double d2 = -MathHelper.sin((entityplayer.rotationPitch / 180F) * (float)Math.PI);
                    ((Entity)obj).motionX = 1.0D * d;
                    ((Entity)obj).motionZ = 1.0D * d1;
                    ((Entity)obj).motionY = 1.0D * d2;
                    tossed = true;
                    worldObj.playSoundAtEntity(this, "morecreeps:bubblescumputdown", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }
            }
        }

        return true;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        if (ridingEntity == null)
        {
            if (rand.nextInt(1) == 0)
            {
                return "morecreeps:bubblescum";
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:bubblescumhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:bubblescumdeath";
    }

    public void setDead()
    {
        smokePlain();
        super.setDead();

        for (int i = 0; i < 25; i++)
        {
        	//fixed error and decommeted (Is there a better word?) it
            double d = -MathHelper.sin((rotationYaw * (float)Math.PI) / 180F);
            double d1 = MathHelper.cos((rotationYaw * (float)Math.PI) / 180F);
            CREEPSFxBubbles creepsfxbubbles = new CREEPSFxBubbles(worldObj, posX + d * 0.5D, posY, posZ + d1 * 0.5D, MoreCreepsAndWeirdos.partRed, 0.5F);
            creepsfxbubbles.renderDistanceWeight = 10D;
            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxbubbles);
        }
        
        if(!worldObj.isRemote)
        {
            if (rand.nextInt(25) == 0)
            {
                dropItem(Items.cookie, 1);
            }
        }
    }

    public void confetti()
    {
    	MoreCreepsAndWeirdos.proxy.confettiA(this, worldObj);
    }
}
