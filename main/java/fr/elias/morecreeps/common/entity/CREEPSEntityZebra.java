package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import fr.elias.morecreeps.client.gui.CREEPSGUIZebraname;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityZebra extends EntityAnimal
{
	EntityPlayerMP playermp;
	EntityPlayer entityplayer;
	World world;
    public int galloptime;
    public boolean tamed;
    public EntityPlayer owner;
    public boolean used;
    public String basetexture;
    protected double attackrange;
    protected int attack;

    public int spittimer;
    public int tamedcookies;
    public int basehealth;
    public double floatcycle;
    public int floatdir;
    public double floatmaxcycle;
    public float modelsize;
    public String name;
    public String texture;
    public double moveSpeed;
    public float health;
    static final String Names[] =
    {
        "Stanley", "Cid", "Hunchy", "The Heat", "Herman the Hump", "Dr. Hump", "Little Lousie", "Spoony G", "Mixmaster C", "The Maestro",
        "Duncan the Dude", "Charlie Camel", "Chip", "Charles Angstrom III", "Mr. Charles", "Cranky Carl", "Carl the Rooster", "Tiny the Peach", "Desert Dan", "Dungby",
        "Doofus"
    };
    static final String camelTextures[] =
    {
        "/mob/creeps/zebra.png"
    };

    public CREEPSEntityZebra(World world)
    {
        super(world);
        basetexture = camelTextures[rand.nextInt(camelTextures.length)];
        texture = basetexture;
        health = 25;
        basehealth = (int) health;
        attack = 2;
        moveSpeed = 0.65F;
        floatdir = 1;
        floatcycle = 0.0D;
        floatmaxcycle = 0.10499999672174454D;
        name = "";
        tamed = false;
        tamedcookies = rand.nextInt(7) + 1;
        modelsize = 2.0F;
        setSize(width * modelsize, height * modelsize);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIBreakDoor(this));
        tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.061D));
        tasks.addTask(5, new EntityAIWander(this, 0.25D));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8F));
        tasks.addTask(7, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }
    
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(basehealth);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(moveSpeed);
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityAnimal spawnBabyAnimal(EntityAnimal entityanimal)
    {
        return new CREEPSEntityZebra(worldObj);
    }

    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
    public float getBlockPathWeight(int i, int j, int k)
    {
        if (worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock() == Blocks.leaves || worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock() == Blocks.grass)
        {
            return 10F;
        }
        else
        {
            return -(float)j;
        }
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        if (ridingEntity instanceof EntityPlayer)
        {
            return (double)(getYOffset() + 1.1F);
        }
        else
        {
            return (double)getYOffset();
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
            riddenByEntity.setPosition(posX, (posY + 3.0999999046325684D) - (double)((2.0F - modelsize) * 1.1F) - floatcycle, posZ);
            return;
        }
        else
        {
            return;
        }
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        if (worldObj.getDifficulty().getDifficultyId() > 0)
        {
            float f = getBrightness(1.0F);

            if (f < 0.0F)
            {
                EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, attackrange);

                if (entityplayer != null)
                {
                    return entityplayer;
                }
            }

            if (rand.nextInt(10) == 0)
            {
                EntityLiving entityliving = func_21018_getClosestTarget(this, 15D);
                return entityliving;
            }
        }

        return null;
    }

    public EntityLiving func_21018_getClosestTarget(Entity entity, double d)
    {
        double d1 = -1D;
        EntityLiving entityliving = null;

        for (int i = 0; i < worldObj.loadedEntityList.size(); i++)
        {
            Entity entity1 = (Entity)worldObj.loadedEntityList.get(i);

            if (!(entity1 instanceof EntityLiving) || entity1 == entity || entity1 == entity.riddenByEntity || entity1 == entity.ridingEntity || (entity1 instanceof EntityPlayer) || (entity1 instanceof EntityMob) || (entity1 instanceof EntityAnimal))
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
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        Entity entity = damagesource.getEntity();

        if (entity != null && (entity instanceof EntityPlayer) && tamed)
        {
            this.setAttackTarget(null);
            return false;
        }

        if (super.attackEntityFrom(DamageSource.causeMobDamage(this), i))
        {
            if (riddenByEntity == entity || ridingEntity == entity)
            {
                return true;
            }

            if (entity != this && worldObj.getDifficulty().getDifficultyId() > 0)
            {
                this.setAttackTarget((EntityLivingBase) entity);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return !tamed;
    }

    protected void updateAITick()
    {
        ignoreFrustumCheck = true;
        moveSpeed = 0.45F;

        if (riddenByEntity != null && (riddenByEntity instanceof EntityPlayer))
        {
            moveForward = 0.0F;
            moveStrafing = 0.0F;
            moveSpeed = 1.95F;
            riddenByEntity.lastTickPosY = 0.0D;
            prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
            prevRotationPitch = rotationPitch = 0.0F;
            EntityPlayer entityplayer = (EntityPlayer)riddenByEntity;
            float f = 1.0F;

            if (entityplayer.getAIMoveSpeed() > 0.01F && entityplayer.getAIMoveSpeed() < 10F)
            {
                f = entityplayer.getAIMoveSpeed();
            }

            moveStrafing = (float) ((entityplayer.moveStrafing / f) * moveSpeed * 4.95F);
            moveForward = (float) ((entityplayer.moveForward / f) * moveSpeed * 4.95F);

            if (onGround && (moveStrafing != 0.0F || moveForward != 0.0F))
            {
                motionY += 0.06100039929151535D;
            }

            if (moveStrafing != 0.0F || moveForward != 0.0F)
            {
                if (floatdir > 0)
                {
                    floatcycle += 0.035999998450279236D;

                    if (floatcycle > floatmaxcycle)
                    {
                        floatdir = floatdir * -1;
                        fallDistance += -1.5F;
                    }
                }
                else
                {
                    floatcycle -= 0.017999999225139618D;

                    if (floatcycle < -floatmaxcycle)
                    {
                        floatdir = floatdir * -1;
                        fallDistance += -1.5F;
                    }
                }
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
                isJumping = Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed();

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
                    double d1 = 0.13D / d;
                    motionX = motionX * d1;
                    motionZ = motionZ * d1;
                }

                motionX *= 2.9500000000000002D;
                motionZ *= 2.9500000000000002D;
            }

            return;
        }
        else
        {
            super.updateEntityActionState();
            return;
        }
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        used = false;

        if (tamed && entityplayer.isSneaking() && riddenByEntity != null)
        {
            entityplayer.mountEntity(null);
        }

        if (tamed && entityplayer.isSneaking() && riddenByEntity == null)
        {
        	entityplayer.openGui(MoreCreepsAndWeirdos.instance, 7, worldObj, (int)this.posX, (int)this.posY, (int)this.posZ);
            return true;
        }

        if (itemstack != null && riddenByEntity == null && itemstack.getItem() == Items.cookie)
        {
            worldObj.playSoundAtEntity(this, "morecreeps:hotdogeat", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            used = true;
            health += 10;

            if (health > basehealth)
            {
                health = basehealth;
                this.setHealth(health);
            }

            tamedcookies--;
            String s = "";

            if (tamedcookies > 1)
            {
                s = "s";
            }

            if (tamedcookies > 0)
            {
            	MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("You need \2476").append(String.valueOf(tamedcookies)).append(" cookie").append(String.valueOf(s)).append(" \247fto tame this speedy Zebra.").toString());
            }

            if (tamedcookies == 0)
            {
                tamed = true;

                if (world.isRemote){
            		if (!Minecraft.getMinecraft().thePlayer.getStatFileWriter().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievezebra))
            		{
                    	confetti();
                    	worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                    	entityplayer.addStat(MoreCreepsAndWeirdos.achievezebra, 1);
                		}
                
            	}
            	
            	if (!world.isRemote){
                    if (!playermp.getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievezebra))
                    {
                        confetti();
                        worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                        playermp.addStat(MoreCreepsAndWeirdos.achievezebra, 1);
                    }
            	}

                owner = entityplayer;

                if (name.length() < 1)
                {
                    name = Names[rand.nextInt(Names.length)];
                }

                MoreCreepsAndWeirdos.proxy.addChatMessage("");
                MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("\2476").append(String.valueOf(name)).append(" \247fhas been tamed!").toString());
                worldObj.playSoundAtEntity(this, "morecreeps:ggpiglevelup", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            }

            smoke();
        }

        if (used)
        {
            smoke();
        }

        String s1 = "";

        if (tamedcookies > 1)
        {
            s1 = "s";
        }

        if (!used && !tamed)
        {
        	MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("You need \2476").append(String.valueOf(tamedcookies)).append(" cookie").append(String.valueOf(s1)).append(" \247fto tame this speedy Zebra.").toString());
        }

        if (itemstack == null && tamed && health > 0)
        {
            if (entityplayer.riddenByEntity == null && modelsize > 1.0F)
            {
                rotationYaw = entityplayer.rotationYaw;
                rotationPitch = entityplayer.rotationPitch;
                entityplayer.fallDistance = -5F;
                entityplayer.mountEntity(this);

                if (this == null)
                {
                    double d = -MathHelper.sin((rotationYaw * (float)Math.PI) / 180F);
                    entityplayer.motionX += 1.5D * d;
                    entityplayer.motionZ -= 0.5D;
                }
            }
            else if (modelsize < 1.0F && tamed)
            {
            	MoreCreepsAndWeirdos.proxy.addChatMessage("Your Zebra is too small to ride!");
            }
            else if (entityplayer.riddenByEntity != null)
            {
            	MoreCreepsAndWeirdos.proxy.addChatMessage("Unmount all creatures before riding your Zebra");
            }
        }

        return true;
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity entity, float f)
    {
        if (onGround)
        {
            double d = entity.posX - posX;
            double d1 = entity.posZ - posZ;
            float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
            motionX = (d / (double)f1) * 0.20000000000000001D * (0.850000011920929D + motionX * 0.20000000298023224D);
            motionZ = (d1 / (double)f1) * 0.20000000000000001D * (0.80000001192092896D + motionZ * 0.20000000298023224D);
            motionY = 0.10000000596246449D;
            fallDistance = -25F;
        }

        if ((double)f < 3.1000000000000001D && entity.getEntityBoundingBox().maxY > this.getEntityBoundingBox().minY && entity.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY)
        {
            //attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), attack);
        }
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
        int j1 = worldObj.countEntities(CREEPSEntityNonSwimmer.class);
        return (i1 == Blocks.grass || i1 == Blocks.dirt) && i1 != Blocks.cobblestone && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).size() == 0 && worldObj.checkBlockCollision(getEntityBoundingBox()) && worldObj.canBlockSeeSky(new BlockPos(i, j, k)) && rand.nextInt(5) == 0 && l > 7 && j1 < 15;
    }

    public void confetti()
    {
        
        double d = -MathHelper.sin((((EntityPlayer)(entityplayer)).rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((((EntityPlayer)(entityplayer)).rotationYaw * (float)Math.PI) / 180F);
        CREEPSEntityTrophy creepsentitytrophy = new CREEPSEntityTrophy(world);
        creepsentitytrophy.setLocationAndAngles(((EntityPlayer)(entityplayer)).posX + d * 3D, ((EntityPlayer)(entityplayer)).posY - 2D, ((EntityPlayer)(entityplayer)).posZ + d1 * 3D, ((EntityPlayer)(entityplayer)).rotationYaw, 0.0F);
        world.spawnEntityInWorld(creepsentitytrophy);
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

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setString("BaseTexture", basetexture);
        nbttagcompound.setString("ZebraName", name);
        nbttagcompound.setBoolean("Tamed", tamed);
        nbttagcompound.setInteger("TamedCookies", tamedcookies);
        nbttagcompound.setInteger("BaseHealth", basehealth);
        nbttagcompound.setFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        basetexture = nbttagcompound.getString("BaseTexture");
        name = nbttagcompound.getString("ZebraName");
        tamed = nbttagcompound.getBoolean("Tamed");

        if (basetexture == null)
        {
            basetexture = "/mob/creeps/zebra.png";
        }

        texture = basetexture;
        basehealth = nbttagcompound.getInteger("BaseHealth");
        tamedcookies = nbttagcompound.getInteger("TamedCookies");
        modelsize = nbttagcompound.getFloat("ModelSize");
    }

    /**
     * Plays living's sound at its position
     */
    public void playLivingSound()
    {
        String s = getLivingSound();

        if (s != null)
        {
            worldObj.playSoundAtEntity(this, s, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (2.0F - modelsize));
        }
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
        if (rand.nextInt(10) == 0)
        {
            dropItem(Items.porkchop, rand.nextInt(3) + 1);
        }

        if (rand.nextInt(2) == 0)
        {
            dropItem(MoreCreepsAndWeirdos.zebrahide, 1);
        }

        super.onDeath(damagesource);
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 5;
    }

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
