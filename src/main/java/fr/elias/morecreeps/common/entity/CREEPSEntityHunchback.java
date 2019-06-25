package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityHunchback extends EntityMob
{
	EntityPlayer entityplayer;
	World world;
    public boolean tamed;
    public int basehealth;
    public int weapon;
    public boolean used;
    public int interest;
    public String ss;
    public float distance;
    public int caketimer;
    public String basetexture;
    public float modelsize;
    public String texture;
    public double moveSpeed;
    public double attackStrength;
    public double health;

    public CREEPSEntityHunchback(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/hunchback.png";
        basetexture = texture;
        moveSpeed = 0.51F;
        attackStrength = 1;
        health = rand.nextInt(15) + 5;
        tamed = false;
        basehealth = rand.nextInt(30) + 10;
        health = basehealth;
        caketimer = 0;
        modelsize = 1.0F;
        
    }
    
    

    

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.getAttackTarget() instanceof CREEPSEntityHunchbackSkeleton)
        {
            this.setAttackTarget(null);
        }

        if (basetexture != texture)
        {
            texture = basetexture;
        }

        if (tamed && caketimer > 0 && rand.nextInt(10) == 0)
        {
            caketimer--;

            if (caketimer == 0)
            {
                tamed = false;
                texture = "/mob/creeps/hunchback.png";
                basetexture = texture;
                caketimer = rand.nextInt(700) + 300;
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
        if (tamed)
        {
            EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 16D);

            if (entityplayer != null && canEntityBeSeen(entityplayer))
            {
                distance = getDistanceToEntity(entityplayer);

                if (distance < 5F)
                {
                    this.setAttackTarget(null);
                    return null;
                }
                else
                {
                    return entityplayer;
                }
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
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        Entity entity = damagesource.getEntity();
        hurtTime = maxHurtTime = 10;

        if (entity != null)
        {
            double d = -MathHelper.sin((entity.rotationYaw * (float)Math.PI) / 180F);
            double d1 = MathHelper.cos((entity.rotationYaw * (float)Math.PI) / 180F);

            if ((entity instanceof EntityPlayer) && tamed)
            {
                motionY = rand.nextFloat() * 0.9F;
                motionZ = d1 * 0.40000000596046448D;
                motionX = d * 0.5D;
                worldObj.playSoundAtEntity(this, "morecreeps:hunchbackthanks", 2.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                super.attackEntityFrom(damagesource, i / 6);
            }
            else if (i > 0 && entity != null)
            {
                motionY = (rand.nextFloat() - rand.nextFloat()) * 0.3F;
                motionZ = d1 * 0.40000000596046448D;
                motionX = d * 0.5D;
                worldObj.playSoundAtEntity(this, "morecreeps:hunchhurt", 2.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                setBeenAttacked();
                this.setAttackTarget((EntityLivingBase) entity);
                super.attackEntityFrom(damagesource, i);
            }
        }

        return true;
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity entity, float f)
    {
        if (!(this.getAttackTarget() instanceof CREEPSEntityHunchbackSkeleton) && !(this.getAttackTarget() instanceof CREEPSEntityGuineaPig) && !(this.getAttackTarget() instanceof CREEPSEntityHotdog))
        {
            if (onGround)
            {
                double d = entity.posX - posX;
                double d1 = entity.posZ - posZ;
                float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
                motionX = (d / (double)f1) * 0.5D * 0.80000001192092896D + motionX * 0.20000000298023224D;
                motionZ = (d1 / (double)f1) * 0.5D * 0.80000001192092896D + motionZ * 0.20000000298023224D;
                motionY = 0.20000000596046447D;
                super.attackEntityAsMob(entity);
            }
            else if (tamed)
            {
                super.attackEntityAsMob(entity);
            }
        }

        if ((double)f < 16D && (getAttackTarget() instanceof EntityPlayer) && tamed)
        {
        	this.setAttackTarget(null);
        }
        else
        {
            super.attackEntityAsMob(entity);
        }
    }

    public EntityLiving getClosestTarget(Entity entity, double d)
    {
        double d1 = -1D;
        EntityLiving entityliving = null;

        for (int i = 0; i < worldObj.loadedEntityList.size(); i++)
        {
            Entity entity1 = (Entity)worldObj.loadedEntityList.get(i);

            if (!(entity1 instanceof EntityLiving) || entity1 == entity || entity1 == entity.riddenByEntity || entity1 == entity.ridingEntity || (entity1 instanceof EntityPlayer) || (entity1 instanceof EntityMob) || (entity1 instanceof CREEPSEntityHunchbackSkeleton))
            {
                continue;
            }

            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);

            if ((d < 0.0D || d2 < d * d) && (d1 == -1D || d2 < d1) && ((EntityLiving)entity1).canEntityBeSeen(entity))
            {
                d1 = d2;
                entityliving = (EntityLiving)entity1;
            }
        }

        return entityliving;
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        used = false;

        if (itemstack != null)
        {
            if (tamed && itemstack.getItem() == Items.bone)
            {
                smoke();
                used = true;
                smoke();
                worldObj.playSoundAtEntity(this, "morecreeps:ggpigarmor", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                worldObj.playSoundAtEntity(this, "morecreeps:huncharmy", 2.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);

                for (int i = 0; i < 5; i++)
                {
                    CREEPSEntityHunchbackSkeleton creepsentityhunchbackskeleton = new CREEPSEntityHunchbackSkeleton(worldObj);
                    creepsentityhunchbackskeleton.setLocationAndAngles(posX + 3D, posY, posZ + (double)i, rotationYaw, 0.0F);
                    creepsentityhunchbackskeleton.modelsize = modelsize;
                    worldObj.spawnEntityInWorld(creepsentityhunchbackskeleton);
                }
            }

            if (itemstack.getItem() == Items.cake || Item.getIdFromItem(itemstack.getItem()) == 92)
            {
                
                    worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                    entityplayer.addStat(MoreCreepsAndWeirdos.achievehunchback, 1);
                    confetti();
                

                texture = "/mob/creeps/hunchbackcake.png";
                basetexture = texture;
                worldObj.playSoundAtEntity(this, "morecreeps:hunchthankyou", 2.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                used = true;
                tamed = true;
                interest += 15;
                health += 2;
                smoke();
                getLivingSound();

                if (caketimer < 4000)
                {
                    caketimer += rand.nextInt(500) + 250;
                }
            }

            if (health > basehealth)
            {
                health = basehealth;
            }

            if (used)
            {
                if (itemstack.stackSize - 1 == 0)
                {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }
                else
                {
                    itemstack.stackSize--;
                }
            }
        }

        return false;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Tamed", tamed);
        nbttagcompound.setInteger("CakeTimer", caketimer);
        nbttagcompound.setString("BaseTexture", basetexture);
        nbttagcompound.setFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        tamed = nbttagcompound.getBoolean("Tamed");
        basetexture = nbttagcompound.getString("BaseTexture");
        caketimer = nbttagcompound.getInteger("CakeTimer");
        modelsize = nbttagcompound.getFloat("ModelSize");
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        int l = worldObj.getBlockLightOpacity(new BlockPos(i, j, k));
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        return i1 != Blocks.cobblestone && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).size() == 0 && worldObj.checkBlockCollision(getEntityBoundingBox()) && worldObj.canBlockSeeSky(new BlockPos(i, j, k)) && rand.nextInt(10) == 0 && l > 10;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    public void confetti()
    {
        
        
        double d = -MathHelper.sin((((EntityPlayer)(entityplayer)).rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((((EntityPlayer)(entityplayer)).rotationYaw * (float)Math.PI) / 180F);
        CREEPSEntityTrophy creepsentitytrophy = new CREEPSEntityTrophy(world);
        creepsentitytrophy.setLocationAndAngles(((EntityPlayer)(entityplayer)).posX + d * 3D, ((EntityPlayer)(entityplayer)).posY - 2D, ((EntityPlayer)(entityplayer)).posZ + d1 * 3D, ((EntityPlayer)(entityplayer)).rotationYaw, 0.0F);
        world.spawnEntityInWorld(creepsentitytrophy);
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
        if (tamed)
        {
            return "morecreeps:hunchquiet";
        }

        if (rand.nextInt(3) == 0)
        {
            return "morecreeps:hunchcake";
        }
        else
        {
            return "morecreeps:hunchquiet";
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:hunchhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:hunchdeath";
    }

    private void smoke()
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
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
        if (tamed && health > 0)
        {
            return;
        }

        if (rand.nextInt(5) == 0)
        {
            dropItem(Items.porkchop, rand.nextInt(3) + 1);
        }
        else
        {
            dropItem(Item.getItemFromBlock(Blocks.sand), rand.nextInt(3) + 1);
        }

        super.onDeath(damagesource);
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        if (tamed && health > 0)
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
}
