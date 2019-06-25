package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

import com.sun.javafx.scene.control.behavior.KeyBinding;

import fr.elias.morecreeps.client.gui.CREEPSGUIGiraffename;
import fr.elias.morecreeps.client.particles.CREEPSFxDirt;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import fr.elias.morecreeps.common.entity.CREEPSEntityRobotTodd.AIAttackEntity;

public class CREEPSEntityRocketGiraffe extends EntityCreature
{
	EntityPlayer entityplayer;
	World world;
	EntityPlayerMP playermp;
    private boolean foundplayer;
    private PathEntity pathToEntity;
    protected Entity playerToAttack;

    /**
     * returns true if a creature has attacked recently only used for creepers and skeletons
     */
    protected boolean hasAttacked;
    private float distance;
    public boolean used;
    public boolean tamed;
    public int basehealth;
    public int tamedfood;
    public int attempts;
    public double dist;
    public double prevdist;
    public int facetime;
    public String basetexture;
    public int rockettime;
    public int rocketcount;
    public int galloptime;
    public float modelsize;
    public double floatcycle;
    public int floatdir;
    public double floatmaxcycle;
    private Entity targetedEntity;
    public String name;
    public String texture;
    public double moveSpeed;
    public double health;
    static final String Names[] =
    {
        "Rory", "Stan", "Clarence", "FirePower", "Lightning", "Rocket Jockey", "Rocket Ralph", "Tim"
    };

    public CREEPSEntityRocketGiraffe(World world)
    {
        super(world);
        basetexture = "/mob/creeps/rocketgiraffe.png";
        texture = basetexture;
        moveSpeed = 0.65F;
        basehealth = rand.nextInt(50) + 30;
        health = basehealth;
        hasAttacked = false;
        foundplayer = false;
        setSize(1.5F, 4F);
        tamedfood = rand.nextInt(8) + 5;
        rockettime = rand.nextInt(10) + 5;
        tamed = false;
        name = "";
        modelsize = 1.0F;
        floatdir = 1;
        floatcycle = 0.0D;
        floatmaxcycle = 0.10499999672174454D;
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
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(health);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(moveSpeed);
    }

    

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("BaseHealth", basehealth);
        nbttagcompound.setInteger("Attempts", attempts);
        nbttagcompound.setBoolean("Tamed", tamed);
        nbttagcompound.setBoolean("FoundPlayer", foundplayer);
        nbttagcompound.setInteger("TamedFood", tamedfood);
        nbttagcompound.setString("BaseTexture", basetexture);
        nbttagcompound.setString("Name", name);
        nbttagcompound.setFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        basehealth = nbttagcompound.getInteger("BaseHealth");
        attempts = nbttagcompound.getInteger("Attempts");
        tamed = nbttagcompound.getBoolean("Tamed");
        foundplayer = nbttagcompound.getBoolean("FoundPlayer");
        tamedfood = nbttagcompound.getInteger("TamedFood");
        basetexture = nbttagcompound.getString("BaseTexture");
        name = nbttagcompound.getString("Name");
        texture = basetexture;
        modelsize = nbttagcompound.getFloat("ModelSize");
    }

    protected void updateAITick()
    {
        if (modelsize > 1.0F)
        {
            ignoreFrustumCheck = true;
        }

        moveSpeed = 0.35F;

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

            moveStrafing = (float) ((entityplayer.moveStrafing / f) * moveSpeed * 1.95F);
            moveForward = (float) ((entityplayer.moveForward / f) * moveSpeed * 1.95F);

            if (onGround && (moveStrafing != 0.0F || moveForward != 0.0F))
            {
                motionY += 0.16100040078163147D;
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
                    worldObj.playSoundAtEntity(this, "morecreeps:giraffesplash", getSoundVolume(), 1.0F);
                }
                else
                {
                    worldObj.playSoundAtEntity(this, "morecreeps:giraffegallop", getSoundVolume(), 1.0F);
                }
            }

            if (onGround && !isJumping)
            {
                isJumping = Minecraft.getMinecraft().gameSettings.keyBindJump.isPressed();

                if (isJumping)
                {
                    motionY += 0.38999998569488525D;
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

                motionX *= 5.9500000000000002D;
                motionZ *= 5.9500000000000002D;
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
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        Entity entity = damagesource.getEntity();

        if (super.attackEntityFrom(DamageSource.causeMobDamage(this), i))
        {
            if (riddenByEntity == entity || ridingEntity == entity)
            {
                return true;
            }

            if (entity != this && !(entity instanceof CREEPSEntityRocket))
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
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity entity, float f)
    {
        this.setAttackTarget(null);

        if (!(entity instanceof EntityPlayer))
        {
            double d = entity.posX - posX;
            double d1 = entity.posZ - posZ;
            float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
            motionX = (d / (double)f1) * 0.40000000000000002D * 0.10000000192092896D + motionX * 0.18000000098023225D;
            motionZ = (d1 / (double)f1) * 0.40000000000000002D * 0.14000000192092896D + motionZ * 0.18000000098023225D;

            if ((double)f < 2D - (2D - (double)modelsize) && entity.getEntityBoundingBox().maxY > this.getEntityBoundingBox().minY && entity.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY)
            {
                //attackTime = 10;
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
            }

            super.attackEntityAsMob(entity);
        }
    }

    public void updateRiderPosition()
    {
        if (riddenByEntity == null)
        {
            return;
        }

        double d = Math.cos(((double)rotationYaw * Math.PI) / 180D) * 0.20000000000000001D;
        double d1 = Math.sin(((double)rotationYaw * Math.PI) / 180D) * 0.20000000000000001D;
        float f = 3.35F - (1.0F - modelsize) * 2.0F;

        if (modelsize > 1.0F)
        {
            f *= 1.1F;
        }

        riddenByEntity.setPosition(posX + d, (posY + (double)f) - floatcycle, posZ + d1);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        used = false;

        if (tamed && entityplayer.isSneaking())
        {
        	entityplayer.openGui(MoreCreepsAndWeirdos.instance, 5, worldObj, (int)this.posX, (int)this.posY, (int)this.posZ);
        }

        if (itemstack == null && tamed)
        {
            if (entityplayer.riddenByEntity == null && modelsize > 0.5F)
            {
                rotationYaw = entityplayer.rotationYaw;
                rotationPitch = entityplayer.rotationPitch;
                entityplayer.fallDistance = -5F;
                entityplayer.mountEntity(this);

                // Dead code
//                if (this == null)
//                {
//                    double d = -MathHelper.sin((rotationYaw * (float)Math.PI) / 180F);
//                    entityplayer.motionX += 1.5D * d;
//                    entityplayer.motionZ -= 0.5D;
//                }
            }
            else if (modelsize < 0.5F && tamed)
            {
            	MoreCreepsAndWeirdos.proxy.addChatMessage("Your Rocket Giraffe is too small to ride!");
            }
            else
            {
            	MoreCreepsAndWeirdos.proxy.addChatMessage("Unmount all creatures before riding your Rocket Giraffe.");
            }
        }

        if (itemstack != null && riddenByEntity == null && itemstack.getItem() == Items.cookie)
        {
            used = true;
            tamedfood--;
            String s = "";

            if (tamedfood > 1)
            {
                s = "s";
            }

            if (tamedfood > 0)
            {
            	MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("You need \2476").append(String.valueOf(tamedfood)).append(" cookie").append(String.valueOf(s)).append(" \247fto tame this Rocket Giraffe.").toString());
            }

            if (itemstack.stackSize - 1 == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            else
            {
                itemstack.stackSize--;
            }

            worldObj.playSoundAtEntity(this, "morecreeps:giraffechew", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);

            for (int i = 0; i < 35; i++)
            {
                double d2 = -MathHelper.sin((rotationYaw * (float)Math.PI) / 180F);
                double d4 = MathHelper.cos((rotationYaw * (float)Math.PI) / 180F);
                CREEPSFxDirt creepsfxdirt = new CREEPSFxDirt(worldObj, posX + d2 * 0.40000000596046448D, posY + 4.5D, posZ + d4 * 0.40000000596046448D, Item.getItemById(12));
                creepsfxdirt.renderDistanceWeight = 6D;
                creepsfxdirt.setParticleTextureIndex(12);
                Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxdirt);
            }

            if (tamedfood < 1)
            {
            	if (world.isRemote){
            		if (!Minecraft.getMinecraft().thePlayer.getStatFileWriter().hasAchievementUnlocked(MoreCreepsAndWeirdos.achieverocketgiraffe))
            		{
                    	confetti();
                    	worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                    	entityplayer.addStat(MoreCreepsAndWeirdos.achieverocketgiraffe, 1);
                		}
                
            	}
            	
            	if (!world.isRemote){
                    if (!playermp.getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achieverocketgiraffe))
                    {
                        confetti();
                        worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                        playermp.addStat(MoreCreepsAndWeirdos.achieverocketgiraffe, 1);
                    }
            	}
            
                smoke();
                worldObj.playSoundAtEntity(this, "morecreeps:giraffetamed", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                tamed = true;

                if (name.length() < 1)
                {
                    name = Names[rand.nextInt(Names.length)];
                }

                MoreCreepsAndWeirdos.proxy.addChatMessage("");
                MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("\2476").append(String.valueOf(name)).append(" \247fhas been tamed!").toString());
                health = basehealth;
                basetexture = "/mob/creeps/rocketgiraffetamed.png";
                texture = basetexture;
            }
        }

        String s1 = "";
        		

        if (tamedfood > 1)
        {
            s1 = "s";
        }

        if (!used && !tamed)
        {
        	MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("You need \2476").append(String.valueOf(tamedfood)).append(" cookie").append(String.valueOf(s1)).append(" \247fto tame this Rocket Giraffe.").toString());
        }

        if (itemstack != null && riddenByEntity != null && (riddenByEntity instanceof EntityLiving) && itemstack.getItem() == MoreCreepsAndWeirdos.rocket)
        {
            if (itemstack.stackSize - 1 == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            else
            {
                itemstack.stackSize--;
            }

            double d1 = -MathHelper.sin((entityplayer.rotationYaw * (float)Math.PI) / 180F);
            double d3 = MathHelper.cos((entityplayer.rotationYaw * (float)Math.PI) / 180F);
            double d5 = 0.0D;
            double d6 = 0.0D;
            double d7 = 0.012999999999999999D;
            double d8 = 4D;
            worldObj.playSoundAtEntity(this, "morecreeps:rocketfire", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            CREEPSEntityRocket creepsentityrocket = new CREEPSEntityRocket(worldObj, entityplayer, 0.0F);

            if (creepsentityrocket != null)
            {
                worldObj.spawnEntityInWorld(creepsentityrocket);
            }
        }

        return true;
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        if (ridingEntity instanceof EntityPlayer)
        {
            return (double)(this.getYOffset() - 1.1F);
        }
        else
        {
            return (double)this.getYOffset();
        }
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
                worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height) + (double)i, (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
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
        if (rand.nextInt(10) == 0)
        {
            return "morecreeps:giraffe";
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
        return "morecreeps:giraffehurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:giraffedead";
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
        return i1 != Blocks.snow && i1 != Blocks.cobblestone && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).size() == 0 && worldObj.checkBlockCollision(getEntityBoundingBox()) && worldObj.canBlockSeeSky(new BlockPos(i, j, k)) && rand.nextInt(15) == 0 && l > 8;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return !tamed;
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
        smoke();

        if (rand.nextInt(10) == 0)
        {
            dropItem(MoreCreepsAndWeirdos.rocket, rand.nextInt(5) + 1);
        }

        super.onDeath(damagesource);
    }

    public void confetti()
    {
        double d = -MathHelper.sin((((EntityPlayer)(entityplayer)).rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((((EntityPlayer)(entityplayer)).rotationYaw * (float)Math.PI) / 180F);
        CREEPSEntityTrophy creepsentitytrophy = new CREEPSEntityTrophy(world);
        creepsentitytrophy.setLocationAndAngles(((EntityPlayer)(entityplayer)).posX + d * 3D, ((EntityPlayer)(entityplayer)).posY - 2D, ((EntityPlayer)(entityplayer)).posZ + d1 * 3D, ((EntityPlayer)(entityplayer)).rotationYaw, 0.0F);
        world.spawnEntityInWorld(creepsentitytrophy);
    }
}
