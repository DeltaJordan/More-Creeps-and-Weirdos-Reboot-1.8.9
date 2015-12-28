package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.client.gui.CREEPSGUISnowdevilname;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntitySnowDevil extends EntityMob
{
	EntityPlayer entityplayer;
	EntityPlayerMP playermp;
	World world;
    public boolean rideable;
    public int interest;
    private boolean primed;
    public boolean tamed;
    public int basehealth;
    private float distance;
    public int armor;
    public String basetexture;
    public boolean used;
    public float modelsize;
    public String name;
    public double moveSpeed;
    public double attackStrength;
    public float health;
    static final String Names[] =
    {
        "Satan", "The Butcher", "Killer", "Tad", "Death Spanker", "Death Toll", "Bruiser", "Bones", "The Devil", "Little Devil",
        "Skinny", "Death to All", "I Will Hurt You", "Pierre", "Bonecruncher", "Bone Breaker", "Blood 'N Guts", "Kill Kill", "Murder", "The Juicer",
        "Scream", "Bloody Buddy", "Sawblade", "Ripper", "Razor", "Valley Strangler", "Choppy Joe", "Wiconsin Shredder", "Urinal", "Johnny Choke",
        "Annihilation", "Bloodshed", "Destructo", "Rub Out", "Massacre", "Felony", "The Mangler", "Destroyer", "The Marauder", "Wreck",
        "Vaporizer", "Wasteland", "Demolition Duo", "Two Knocks", "Double Trouble", "Thing One & Thing Two", "Wipeout", "Devil Duo", "Two Shot", "Misunderstood",
        "Twice As Nice"
    };
    static final String snowTextures[] =
    {
        "/mob/creeps/snowdevil1.png", "/mob/creeps/snowdevil2.png"
    };
    
    public String texture;

    public CREEPSEntitySnowDevil(World world)
    {
        super(world);
        primed = false;
        basetexture = snowTextures[rand.nextInt(snowTextures.length)];
        texture = basetexture;
        setSize(width * 1.6F, height * 1.6F);
        height = 2.0F;
        width = 2.0F;
        moveSpeed = 0.6F;
        rideable = false;
        basehealth = rand.nextInt(50) + 15;
        health = basehealth;
        attackStrength = 3;
        tamed = false;
        name = "";
        modelsize = 1.0F;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
        tasks.addTask(5, new EntityAIWander(this, 0.35D));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 16F));
        tasks.addTask(7, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
    }
    
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(basehealth);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(moveSpeed);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(attackStrength);
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 15D);

        if (entityplayer != null)
        {
            if (!tamed)
            {
                return entityplayer;
            }

            if (rand.nextInt(10) == 0)
            {
                return entityplayer;
            }
        }

        if (rand.nextInt(6) == 0)
        {
            EntityLiving entityliving = getClosestTarget(this, 10D);
            return entityliving;
        }
        else
        {
            return null;
        }
    }

    public EntityLiving getClosestTarget(Entity entity, double d)
    {
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(20D, 20D, 20D));

        for (int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity)list.get(i);

            if (!(entity1 instanceof EntityCreature))
            {
                continue;
            }

            EntityCreature entitycreature = (EntityCreature)entity1;

            if (entitycreature.getAttackTarget() instanceof EntityPlayer)
            {
                return entitycreature;
            }
        }

        return null;
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity entity, float f)
    {
        if (onGround && !tamed)
        {
            double d = entity.posX - posX;
            double d1 = entity.posZ - posZ;
            float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
            motionX = (d / (double)f1) * 0.5D * 0.80000001192092896D + motionX * 0.20000000298023224D;
            motionZ = (d1 / (double)f1) * 0.5D * 0.80000001192092896D + motionZ * 0.20000000298023224D;
            motionY = 0.40000000596046448D;
        }
        else if (tamed)
        {
            super.attackEntityAsMob(entity);
        }

        if ((getAttackTarget() instanceof EntityPlayer) && tamed)
        {
            this.setAttackTarget(null);
            super.attackEntityAsMob(entity);
        }
        else if ((getAttackTarget() instanceof CREEPSEntitySnowDevil) && tamed)
        {
        	this.setAttackTarget(null);
        }
        else
        {
            super.attackEntityAsMob(entity);
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

            if (entity != this)
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
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("Interest", interest);
        nbttagcompound.setBoolean("Tamed", tamed);
        nbttagcompound.setString("Name", name);
        nbttagcompound.setInteger("BaseHealth", basehealth);
        nbttagcompound.setString("BaseTexture", basetexture);
        nbttagcompound.setFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        interest = nbttagcompound.getInteger("Interest");
        tamed = nbttagcompound.getBoolean("Tamed");
        name = nbttagcompound.getString("Name");
        basetexture = nbttagcompound.getString("BaseTexture");
        basehealth = nbttagcompound.getInteger("BaseHealth");
        modelsize = nbttagcompound.getFloat("ModelSize");
        texture = basetexture;
    }

    private void explode()
    {
        float f = 2.0F;
        worldObj.createExplosion(null, posX, posY, posZ, f, true);
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
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        int l = worldObj.getBlockLightOpacity(new BlockPos(i, j, k));
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        Block j1 = worldObj.getBlockState(new BlockPos(i, j, k)).getBlock();
        return (i1 == Blocks.snow || j1 == Blocks.snow) && i1 != Blocks.cobblestone && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).size() == 0 && worldObj.checkBlockCollision(getEntityBoundingBox()) && worldObj.canBlockSeeSky(new BlockPos(i, j, k)) && rand.nextInt(5) == 0 && l > 6;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 2;
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
        	entityplayer.openGui(MoreCreepsAndWeirdos.instance, 7, world, (int)this.posX, (int)this.posY, (int)this.posZ);
        }

        if (itemstack != null)
        {
            if (tamed && texture.length() == 2222)
            {
                armor = Item.getIdFromItem(itemstack.getItem());
                smoke();

                if (armor > 297 && armor < 302)
                {
                    used = true;
                    basehealth += 5;
                    attackStrength++;
                    health = basehealth;
                    this.setHealth(health);
                    String s = basetexture.substring(0, 18);
                    s = (new StringBuilder()).append(s).append("L.png").toString();
                    texture = s;
                    smoke();
                }

                if (armor > 313 && armor < 318)
                {
                    used = true;
                    basehealth += 10;
                    attackStrength += 2;
                    health = basehealth;
                    this.setHealth(health);
                    String s1 = basetexture.substring(0, 18);
                    s1 = (new StringBuilder()).append(s1).append("G.png").toString();
                    texture = s1;
                    smoke();
                }

                if (armor > 305 && armor < 310)
                {
                    used = true;
                    basehealth += 20;
                    health = basehealth;
                    this.setHealth(health);
                    attackStrength += 4;
                    String s2 = basetexture.substring(0, 18);
                    s2 = (new StringBuilder()).append(s2).append("I.png").toString();
                    texture = s2;
                    smoke();
                }

                if (armor > 309 && armor < 314)
                {
                    smoke();
                    used = true;
                    basehealth += 30;
                    health = basehealth;
                    this.setHealth(health);
                    attackStrength += 10;
                    String s3 = basetexture.substring(0, 18);
                    s3 = (new StringBuilder()).append(s3).append("D.png").toString();
                    texture = s3;
                    smoke();
                }
            }

            if (itemstack.getItem() == Items.snowball)
            {
            	if (!world.isRemote){
            		if (!playermp.getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievesnowdevil))
                	{
                    	worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                    	playermp.addStat(MoreCreepsAndWeirdos.achievesnowdevil, 1);
                    	confetti();
                	}
            	}
            	
            	if(world.isRemote){
            		if (!Minecraft.getMinecraft().thePlayer.getStatFileWriter().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievesnowdevil))
                	{
                    	worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                    	entityplayer.addStat(MoreCreepsAndWeirdos.achievesnowdevil, 1);
                    	confetti();
                	}
            	}
                

                used = true;
                health += 2;
                smoke();
                smoke();
                tamed = true;
                health = basehealth;
                this.setHealth(health);

                if (name.length() < 1)
                {
                    name = Names[rand.nextInt(Names.length)];
                }

                worldObj.playSoundAtEntity(this, "morecreeps:snowdeviltamed", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
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

            return true;
        }
        else
        {
            return false;
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
        return "morecreeps:snowdevil";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:snowdevilhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:snowdevildeath";
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

        super.onDeath(damagesource);

        if (rand.nextInt(10) == 0)
        {
            dropItem(Item.getItemFromBlock(Blocks.ice), rand.nextInt(3) + 1);
            dropItem(Item.getItemFromBlock(Blocks.snow), rand.nextInt(10) + 1);
        }
        else
        {
        	dropItem(Item.getItemFromBlock(Blocks.snow), rand.nextInt(5) + 2);
        }
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

    public void confetti()
    {
        double d = -MathHelper.sin((((EntityPlayer)(entityplayer)).rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((((EntityPlayer)(entityplayer)).rotationYaw * (float)Math.PI) / 180F);
        CREEPSEntityTrophy creepsentitytrophy = new CREEPSEntityTrophy(world);
        creepsentitytrophy.setLocationAndAngles(((EntityPlayer)(entityplayer)).posX + d * 3D, ((EntityPlayer)(entityplayer)).posY - 2D, ((EntityPlayer)(entityplayer)).posZ + d1 * 3D, ((EntityPlayer)(entityplayer)).rotationYaw, 0.0F);
        world.spawnEntityInWorld(creepsentitytrophy);
    }
}
