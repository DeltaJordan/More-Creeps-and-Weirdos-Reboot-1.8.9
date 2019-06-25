package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityTombstone extends EntityAnimal
{
    public int interest;
    private boolean primed;
    public boolean tamed;
    public int basehealth;
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
    public float baseSpeed;
    public int attackStrength;
    public float modelsize;
    public boolean heavenbuilt;
    public boolean angrydog;
    public int firenum;
    public int firepower;
    public int healtimer;
    public EntityLiving owner;
    public float dogsize;
    public String name;
    public int skillattack;
    public int skilldefend;
    public int skillhealing;
    public int skillspeed;
    public String deathtype;
    public String basetexture;
    public String texture;

    public CREEPSEntityTombstone(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/tombstone.png";
        basetexture = "";
        interest = 0;
        primed = false;
        tamed = false;
        basehealth = 0;
        used = false;
        grab = false;
        pigstack = 0;
        level = 0;
        totaldamage = 0.0F;
        hotelbuilt = false;
        wanderstate = 0;
        speedboost = 0;
        totalexperience = 0;
        baseSpeed = 0.0F;
        modelsize = 1.0F;
        heavenbuilt = false;
        angrydog = false;
        firenum = 0;
        firepower = 0;
        healtimer = 0;
        owner = null;
        attackStrength = 0;
        dogsize = 0.0F;
        name = "";
        skillattack = 0;
        skilldefend = 0;
        skillhealing = 0;
        skillspeed = 0;
        deathtype = "";
    }
    
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
    }


    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityAnimal createChild(EntityAgeable entityanimal)
    {
        return new CREEPSEntityTombstone(worldObj);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        used = false;

        if (itemstack == null)
        {
            entityplayer.addChatMessage(new ChatComponentText("Use a \2474LifeGem\247f on this tombstone to bring your pet back to life!"));
            return false;
        }

        if (itemstack != null && itemstack.getItem() != MoreCreepsAndWeirdos.lifegem)
        {
        	entityplayer.addChatMessage(new ChatComponentText("Use a \2474LifeGem\247f on this tombstone to bring your pet back to life!"));
            return false;
        }

        if (itemstack != null && itemstack.getItem() == MoreCreepsAndWeirdos.lifegem)
        {
            itemstack.stackSize--;
            entityplayer.swingItem();

            if (itemstack.stackSize < 1)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                itemstack.stackSize = 0;
            }

            smoke();

            if (deathtype.equals("GuineaPig"))
            {
                CREEPSEntityGuineaPig creepsentityguineapig = new CREEPSEntityGuineaPig(worldObj);
                creepsentityguineapig.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
                creepsentityguineapig.interest = interest;
                creepsentityguineapig.tamed = tamed;
                creepsentityguineapig.name = name;
                creepsentityguineapig.basehealth = basehealth;
                creepsentityguineapig.level = level;
                creepsentityguineapig.basetexture = basetexture;
                creepsentityguineapig.totaldamage = totaldamage;
                creepsentityguineapig.hotelbuilt = hotelbuilt;
                creepsentityguineapig.attackStrength = attackStrength;
                creepsentityguineapig.wanderstate = wanderstate;
                creepsentityguineapig.speedboost = speedboost;
                creepsentityguineapig.totalexperience = totalexperience;
                creepsentityguineapig.baseSpeed = baseSpeed;
                creepsentityguineapig.health = 5;
                creepsentityguineapig.modelsize = modelsize;
                creepsentityguineapig.skillattack = skillattack;
                creepsentityguineapig.skilldefend = skilldefend;
                creepsentityguineapig.skillhealing = skillhealing;
                creepsentityguineapig.skillspeed = skillspeed;
                creepsentityguineapig.texture = basetexture;

                if (wanderstate == 1)
                {
                    creepsentityguineapig.moveSpeed = 0.0F;
                }
                else
                {
                    creepsentityguineapig.moveSpeed = speedboost <= 0 ? baseSpeed : baseSpeed + 0.5F;
                }

                worldObj.spawnEntityInWorld(creepsentityguineapig);
                setDead();
            }

            if (deathtype.equals("Hotdog"))
            {
                CREEPSEntityHotdog creepsentityhotdog = new CREEPSEntityHotdog(worldObj);
                creepsentityhotdog.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
                creepsentityhotdog.interest = interest;
                creepsentityhotdog.tamed = tamed;
                creepsentityhotdog.name = name;
                creepsentityhotdog.basehealth = basehealth;
                creepsentityhotdog.level = level;
                creepsentityhotdog.basetexture = basetexture;
                creepsentityhotdog.totaldamage = totaldamage;
                creepsentityhotdog.heavenbuilt = heavenbuilt;
                creepsentityhotdog.attackStrength = attackStrength;
                creepsentityhotdog.wanderstate = wanderstate;
                creepsentityhotdog.speedboost = speedboost;
                creepsentityhotdog.totalexperience = totalexperience;
                creepsentityhotdog.baseSpeed = baseSpeed;
                creepsentityhotdog.skillattack = skillattack;
                creepsentityhotdog.skilldefend = skilldefend;
                creepsentityhotdog.skillhealing = skillhealing;
                creepsentityhotdog.skillspeed = skillspeed;
                creepsentityhotdog.firepower = firepower;
                creepsentityhotdog.dogsize = dogsize;
                creepsentityhotdog.health = 5;
                creepsentityhotdog.texture = basetexture;

                if (wanderstate == 1)
                {
                    creepsentityhotdog.moveSpeed = 0.0F;
                }
                else
                {
                    creepsentityhotdog.moveSpeed = speedboost <= 0 ? baseSpeed : baseSpeed + 0.5F;
                }

                if(!worldObj.isRemote)
                worldObj.spawnEntityInWorld(creepsentityhotdog);
                
                setDead();
            }
        }

        return true;
    }

    private void smoke()
    {
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 30; j++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                double d4 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle(EnumParticleTypes.HEART, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d2, d4, new int[0]);
            }

            for (int k = 0; k < 4; k++)
            {
                for (int l = 0; l < 10; l++)
                {
                    double d1 = rand.nextGaussian() * 0.02D;
                    double d3 = rand.nextGaussian() * 0.02D;
                    double d5 = rand.nextGaussian() * 0.02D;
                    worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height) + (double)k, (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d1, d3, d5, new int[0]);
                }
            }
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return true;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        if (rand.nextInt(10) == 0)
        {
            return "morecreeps:tombstone";
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
        return null;
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return null;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setString("DeathType", deathtype);
        nbttagcompound.setInteger("Interest", interest);
        nbttagcompound.setBoolean("Tamed", tamed);
        nbttagcompound.setString("Name", name);
        nbttagcompound.setInteger("BaseHealth", basehealth);
        nbttagcompound.setInteger("Level", level);
        nbttagcompound.setString("BaseTexture", basetexture);
        nbttagcompound.setFloat("TotalDamage", totaldamage);
        nbttagcompound.setBoolean("heavenbuilt", heavenbuilt);
        nbttagcompound.setBoolean("hotelbuilt", hotelbuilt);
        nbttagcompound.setInteger("AttackStrength", attackStrength);
        nbttagcompound.setInteger("WanderState", wanderstate);
        nbttagcompound.setInteger("SpeedBoost", speedboost);
        nbttagcompound.setInteger("TotalExperience", totalexperience);
        nbttagcompound.setFloat("BaseSpeed", baseSpeed);
        nbttagcompound.setInteger("SkillAttack", skillattack);
        nbttagcompound.setInteger("SkillDefense", skilldefend);
        nbttagcompound.setInteger("SkillHealing", skillhealing);
        nbttagcompound.setInteger("SkillSpeed", skillspeed);
        nbttagcompound.setInteger("FirePower", firepower);
        nbttagcompound.setFloat("DogSize", dogsize);
        nbttagcompound.setFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        deathtype = nbttagcompound.getString("DeathType");
        interest = nbttagcompound.getInteger("Interest");
        tamed = nbttagcompound.getBoolean("Tamed");
        name = nbttagcompound.getString("Name");
        basetexture = nbttagcompound.getString("BaseTexture");
        basehealth = nbttagcompound.getInteger("BaseHealth");
        level = nbttagcompound.getInteger("Level");
        totaldamage = nbttagcompound.getFloat("TotalDamage");
        heavenbuilt = nbttagcompound.getBoolean("heavenbuilt");
        hotelbuilt = nbttagcompound.getBoolean("hotelbuilt");
        attackStrength = nbttagcompound.getInteger("AttackStrength");
        wanderstate = nbttagcompound.getInteger("WanderState");
        speedboost = nbttagcompound.getInteger("SpeedBoost");
        totalexperience = nbttagcompound.getInteger("TotalExperience");
        baseSpeed = nbttagcompound.getFloat("BaseSpeed");
        skillattack = nbttagcompound.getInteger("SkillAttack");
        skilldefend = nbttagcompound.getInteger("SkillDefense");
        skillhealing = nbttagcompound.getInteger("SkillHealing");
        skillspeed = nbttagcompound.getInteger("SkillSpeed");
        firepower = nbttagcompound.getInteger("FirePower");
        dogsize = nbttagcompound.getFloat("DogSize");
        modelsize = nbttagcompound.getFloat("ModelSize");
    }

    public void onDeath(Entity entity)
    {
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return false;
    }

    /**
     * Checks if this entity is inside of an opaque block
     */
    public boolean isEntityInsideOpaqueBlock()
    {
        return false;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }
}
