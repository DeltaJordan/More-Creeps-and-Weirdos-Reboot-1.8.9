package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import fr.elias.morecreeps.client.config.CREEPSConfig;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityFloobShip extends EntityFlying
{
    private boolean foundplayer;
    private PathEntity pathToEntity;
    protected Entity playerToAttack;
    protected boolean hasAttacked;
    private double goX;
    private double goZ;
    private float distance;
    public boolean landed;
    public int floobcounter;
    public boolean firstreset;
    public float bump;
    public int lifespan;
    public String texture;

    public CREEPSEntityFloobShip(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/floobship.png";
        hasAttacked = false;
        foundplayer = false;
        landed = false;
        setSize(4F, 3F);
        isCollidedVertically = false;
        floobcounter = rand.nextInt(500) + 400;
        firstreset = false;
        bump = 2.0F;
        motionX = rand.nextFloat() * 0.8F;
        motionZ = rand.nextFloat() * 0.8F;
        lifespan = rand.nextInt(10000) + 1500;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(150D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Landed", landed);
        nbttagcompound.setInteger("FloobCounter", floobcounter);
        nbttagcompound.setBoolean("FirstReset", firstreset);
        nbttagcompound.setInteger("LifeSpan", lifespan);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        landed = nbttagcompound.getBoolean("Landed");
        floobcounter = nbttagcompound.getInteger("FloobCounter");
        firstreset = nbttagcompound.getBoolean("FirstReset");
        lifespan = nbttagcompound.getInteger("LifeSpan");
    }

    public void setAngles(float f, float f1)
    {
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    public void fall(float distance, float damageMultiplier)
    {
    }

    /**
     * returns true if this entity is by a ladder, false otherwise
     */
    public boolean isOnLadder()
    {
        return false;
    }

    /**
     * Sets the rotation of the entity
     */
    protected void setRotation(float f, float f1)
    {
    }

    /**
     * Sets the entity's position and rotation. Args: posX, posY, posZ, yaw, pitch
     */
    public void setPositionAndRotation(double d, double d1, double d2, float f, float f1)
    {
    }

    /**
     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    public void setPositionAndRotation2(double d, double d1, double d2, float f, float f1, int i)
    {
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return false;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        floobcounter--;
        lifespan--;

        if (handleWaterMovement())
        {
            motionY += 0.2800000011920929D;
            motionX += 0.97999999999999998D;
            motionX += 0.97999999999999998D;
        }

        isJumping = false;
        super.onLivingUpdate();

        if (isJumping || landed)
        {
            motionY = 0.0D;
            bump = 0.0F;
        }

        if (!landed || !onGround)
        {
            if (posY < 100D && !firstreset)
            {
                motionY = 4D;
                bump = 4F;
                firstreset = true;
            }

            motionY = -0.2F + bump;
            bump *= 0.95999999999999996D;
            motionX *= 0.97999999999999998D;
            motionZ *= 0.97999999999999998D;

            if (onGround)
            {
                int i = MathHelper.floor_double(posX);
                int k = MathHelper.floor_double(getBoundingBox().minY);
                int l = MathHelper.floor_double(posZ);
                Block i1 = worldObj.getBlockState(new BlockPos(i, k - 1, l)).getBlock();

                if (i1 == Blocks.flowing_water || i1 == Blocks.water || i1 == Blocks.leaves || i1 == Blocks.cactus)
                {
                    thrusters();
                    bump = 3F;
                    motionX = rand.nextFloat() * 2.8F;
                    motionY = rand.nextFloat() * 0.6F;
                    motionZ = rand.nextFloat() * 2.8F;
                }
                else
                {
                    landed = true;
                }
            }
        }
        else if (floobcounter < 1)
        {
            thrusters();
            floobcounter = rand.nextInt(300) + 400;
            worldObj.playSoundAtEntity(this, "morecreeps:floobshipspawn", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);

            for (int j = 0; j < rand.nextInt(4) + 3; j++)
            {
                CREEPSEntityFloob creepsentityfloob = new CREEPSEntityFloob(worldObj);
                creepsentityfloob.setLocationAndAngles(posX + 3D + (double)j, posY + 1.0D, posZ + (double)j, rotationYaw, 0.0F);
                creepsentityfloob.motionX = rand.nextFloat() * 1.5F;
                creepsentityfloob.motionY = rand.nextFloat() * 2.0F;
                creepsentityfloob.motionZ = rand.nextFloat() * 1.5F;
                creepsentityfloob.fallDistance = -25F;
                worldObj.spawnEntityInWorld(creepsentityfloob);
            }
        }
    }

    /**
     * knocks back this entity
     */
    public void knockBack(Entity entity, int i, double d, double d1)
    {
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        Entity entity = damagesource.getEntity();

        if (entity instanceof EntityPlayer)
        {
            thrusters();
            worldObj.playSoundAtEntity(this, "morecreeps:floobshipclang", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
        }

        if (rand.nextInt(10) == 0)
        {
            bump = rand.nextInt(3);
            motionX = rand.nextFloat() * 0.8F;
            motionZ = rand.nextFloat() * 0.8F;
        }

        int j = MathHelper.floor_double(posX);
        int k = MathHelper.floor_double(getBoundingBox().minY);
        int l = MathHelper.floor_double(posZ);
        Block i1 = worldObj.getBlockState(new BlockPos(j, k - 1, l)).getBlock();

        if (i1 == Blocks.flowing_water || i1 == Blocks.water || i1 == Blocks.leaves || i1 == Blocks.cactus)
        {
            thrusters();
            bump = 3F;
            motionY = rand.nextFloat() * 0.8F;
            motionX = rand.nextFloat() * 0.8F;
            motionZ = rand.nextFloat() * 0.8F;
        }

        return super.attackEntityFrom(DamageSource.causeMobDamage(this), i);
    }

    private void thrusters()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, ((posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width) + (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width - (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F) + (double)i) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)i - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, ((posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width) + (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F) + (double)i) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width - (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)i - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, ((posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width) + (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F) + (double)i) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width - (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)i - (double)width, d, d1, d2);
            }
        }
    }

    public void onDeath(Entity entity)
    {
        if (lifespan > 0 && getHealth() > 0)
        {
            return;
        }

        if (lifespan > 0 && CREEPSConfig.floobshipExplode)
        {
            worldObj.playSoundAtEntity(this, "morecreeps:floobshipexplode", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            worldObj.createExplosion(null, posX, posY, posZ, 8F, true);
        }

        setDead();
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        if (lifespan > 0 && getHealth() > 0)
        {
            return;
        }
        else
        {
            super.setDead();
            return;
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "morecreeps:floobship";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:floobship";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:floobshipexplode";
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
        int j1 = worldObj.countEntities(CREEPSEntityFloobShip.class);
        return i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.double_stone_slab && i1 != Blocks.stone_slab && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0 && worldObj.canSeeSky(new BlockPos(i, j, k)) && posY > 100D && rand.nextInt(100) == 0 /*&& l > 10*/ && (worldObj.getDifficulty() != EnumDifficulty.PEACEFUL || j1 >= 2);
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    public float getShadowSize()
    {
        return 2.8F;
    }
}
