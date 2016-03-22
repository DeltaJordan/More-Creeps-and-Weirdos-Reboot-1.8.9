package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityHorseHead extends EntityAnimal
{
    public int galloptime;
    public double floatcycle;
    public int floatdir;
    public double floatmaxcycle;
    public int blastoff;
    public int blastofftimer;
    public String texture;

    public CREEPSEntityHorseHead(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/horsehead.png";
        setSize(0.6F, 2.0F);
        floatdir = 1;
        floatcycle = 0.0D;
        floatmaxcycle = 0.10499999672174454D;
        blastoff = rand.nextInt(500) + 400;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAILookIdle(this));
    }

    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
    }
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	if(worldObj.isRemote){
            for (int i = 0; i < 5; i++)
            {
        		MoreCreepsAndWeirdos.proxy.smokeHorseHead(worldObj, this, rand);
            }
    	}

        if (isEntityInsideOpaqueBlock())
        {
            posY += 2.5D;
            floatdir = 1;
            floatcycle = 0.0D;
        }

        fallDistance = -100F;

        if (riddenByEntity == null && blastoff-- < 0)
        {
            motionY += 0.15049999952316284D;
            double d = -MathHelper.sin((rotationYaw * (float)Math.PI) / 180F);
            double d1 = MathHelper.cos((rotationYaw * (float)Math.PI) / 180F);
            motionX += d * 0.10999999940395355D;
            motionZ += d1 * 0.10999999940395355D;

            if(worldObj.isRemote)
            {
                for (int j = 0; j < 25; j++)
                {
            		MoreCreepsAndWeirdos.proxy.smokeHorseHead(worldObj, this, rand);
                }
            }

            if (posY > 100D)
            {
                setDead();
            }
        }

        if (riddenByEntity == null && blastoff > 0 && worldObj.getBlockState(new BlockPos((int)posX, (int)posY - 1, (int)posZ)) == Blocks.air)
        {
            posY -= 0.25D;
        }

        ignoreFrustumCheck = true;

        if (floatdir > 0)
        {
            floatcycle += 0.017999999225139618D;

            if (floatcycle > floatmaxcycle)
            {
                floatdir = floatdir * -1;
                fallDistance += -1.5F;
            }
        }
        else
        {
            floatcycle -= 0.0094999996945261955D;

            if (floatcycle < -floatmaxcycle)
            {
                floatdir = floatdir * -1;
                fallDistance += -1.5F;
            }
        }

        if (riddenByEntity != null && (riddenByEntity instanceof EntityPlayer))
        {
            blastoff++;

            if (blastoff > 50000)
            {
                blastoff = 50000;
            }
        }

        super.onUpdate();
    }

    protected void updateAITasks()
    {
        motionY *= 0.80000001192092896D;
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.95D);

        if (riddenByEntity != null && (riddenByEntity instanceof EntityPlayer))
        {
            moveForward = 0.0F;
            moveStrafing = 0.0F;
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.95D);
            riddenByEntity.lastTickPosY = 0.0D;
            prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
            prevRotationPitch = rotationPitch = 0.0F;
            EntityPlayer entityplayer = (EntityPlayer)riddenByEntity;
            float f = 1.0F;

            if (entityplayer.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() > 0.01D && entityplayer.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() < 10D)
            {
                f = (float) getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue();
            }

            moveStrafing = (entityplayer.moveStrafing / f) * (float) getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() * 4.95F;
            moveForward = (entityplayer.moveForward / f) * (float) getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue() * 4.95F;

            if (onGround && (moveStrafing != 0.0F || moveForward != 0.0F))
            {
                motionY += 0.16100040078163147D;
            }

            if (moveStrafing == 0.0F && moveForward == 0.0F)
            {
                isJumping = false;
                galloptime = 0;
            }

            if (moveForward != 0.0F && galloptime++ > 10)
            {
                galloptime = 0;

                if (handleWaterMovement())
                {
                    worldObj.playSoundAtEntity(this, "morecreeps:giraffesplash", getSoundVolume(), 1.2F);
                }
                else
                {
                    worldObj.playSoundAtEntity(this, "morecreeps:giraffegallop", getSoundVolume(), 1.2F);
                }
            }

            if (onGround && !isJumping)
            {
                isJumping = ObfuscationReflectionHelper.getPrivateValue(EntityLivingBase.class, entityplayer, 40);
                if (isJumping)
                {
                    motionY += 0.37000000476837158D;
                }
            }

            if (onGround && isJumping)
            {
                double d = Math.abs(Math.sqrt(motionX * motionX + motionZ * motionZ));

                if (d > 0.13D)
                {
                    double d2 = 0.13D / d;
                    motionX = motionX * d2;
                    motionZ = motionZ * d2;
                }

                motionX *= 6.9500000000000002D;
                motionZ *= 6.9500000000000002D;
            }

            if (MoreCreepsAndWeirdos.proxy.isJumpKeyDown() && posY < 120D)
            {
                motionY += 0.15049999952316284D;
                double d1 = -MathHelper.sin((entityplayer.rotationYaw * (float)Math.PI) / 180F);
                double d3 = MathHelper.cos((entityplayer.rotationYaw * (float)Math.PI) / 180F);
                motionX += d1 * 0.15999999642372131D;
                motionZ += d3 * 0.15999999642372131D;

                if (blastofftimer-- < 0)
                {
                    worldObj.playSoundAtEntity(this, "morecreeps:horseheadblastoff", 1.0F, 1.0F);
                    blastofftimer = 10;
                }

                if(worldObj.isRemote)
                {
                    for (int i = 0; i < 25; i++)
                    {
                    	MoreCreepsAndWeirdos.proxy.smokeHorseHead(worldObj, this, rand);
                    }
                }
            }

            return;
        }
        else
        {
            //super.updateEntityActionState();
            return;
        }
    }

    public void updateRiderPosition()
    {
        if (riddenByEntity == null)
        {
            return;
        }

        if (riddenByEntity instanceof EntityPlayer)
        {
            riddenByEntity.setPosition(posX, (posY + 2.5D) - floatcycle, posZ);
            return;
        }
        else
        {
            return;
        }
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer entityplayer)
    {
        if (entityplayer.riddenByEntity == null)
        {
            rotationYaw = entityplayer.rotationYaw;
            rotationPitch = entityplayer.rotationPitch;
            entityplayer.fallDistance = -15F;
            entityplayer.mountEntity(this);
            blastoff += rand.nextInt(500) + 200;
            motionX = 0.0D;
            motionY = 0.0D;
            motionZ = 0.0D;

            if (this == null)
            {
                double d = -MathHelper.sin((rotationYaw * (float)Math.PI) / 180F);
                entityplayer.motionX += 1.5D * d;
                entityplayer.motionZ -= 0.5D;
            }
        }
        else
        {
            MoreCreepsAndWeirdos.proxy.addChatMessage("Unmount all creatures before riding your Horse Head");
        }

        return false;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(getBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        int l = worldObj.getBlockLightOpacity(new BlockPos(i, j, k));
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        return (i1 == Blocks.sand || i1 == Blocks.grass || i1 == Blocks.dirt) && i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.stone_slab && i1 != Blocks.double_stone_slab && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0 && worldObj.canSeeSky(new BlockPos(i, j, k)) && rand.nextInt(25) == 0 && l > 7;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("Blastoff", blastoff);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        blastoff = nbttagcompound.getInteger("Blastoff");
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "morecreeps:horsehead";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:hippohurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:hippodeath";
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
    	if(!worldObj.isRemote)
    	{
            if (rand.nextInt(10) == 0)
            {
                dropItem(Items.porkchop, rand.nextInt(3) + 1);
            }

            if (rand.nextInt(10) == 0)
            {
                dropItem(Items.wheat_seeds, rand.nextInt(3) + 1);
            }
    	}
        super.onDeath(damagesource);
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

	@Override
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		return new CREEPSEntityHorseHead(worldObj);
	}
}
