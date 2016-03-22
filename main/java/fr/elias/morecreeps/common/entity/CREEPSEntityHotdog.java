package fr.elias.morecreeps.common.entity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.client.config.CREEPSConfig;
import fr.elias.morecreeps.client.gui.CREEPSGUIHotdog;
import fr.elias.morecreeps.client.particles.CREEPSFxBlood;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityHotdog extends EntityMob
{
	EntityPlayer entityplayer;
    public boolean rideable;
    public String texture;
    public int interest;
    private boolean primed;
    public boolean tamed;
    public int basehealth;
    private float distance;
    public int armor;
    public String basetexture;
    public boolean used;
    public boolean grab;
    public List piglist;
    public int pigstack;
    public int level;
    public float totaldamage;
    public boolean alt;
    public boolean heavenbuilt;
    public int wanderstate;
    public int speedboost;
    public int totalexperience;
    public float baseSpeed;
    public Entity preventity;
    public boolean angrydog;
    public int skillattack;
    public int skilldefend;
    public int skillhealing;
    public int skillspeed;
    public int firenum;
    public int firepower;
    public int healtimer;
    public EntityPlayer owner;
    public int health;
    public double moveSpeed;

    /** How much damage this mob's attacks deal */
    public int attackStrength;
    public float dogsize;
    public int unmounttimer;
    public boolean chunky;
    public String name;
    static final String Names[] =
    {
        "Pogo", "Spot", "King", "Prince", "Bosco", "Ralph", "Wendy", "Trixie", "Bowser", "The Heat",
        "Weiner", "Wendon the Weiner", "Wallace the Weiner", "William the Weiner", "Terrance", "Elijah", "Good Boy", "Boy", "Girl", "Tennis Shoe",
        "Rusty", "Mean Joe Green", "Lawrence", "Foxy", "SlyFoxHound", "Leroy Brown"
    };
    static final int firechance[] =
    {
        0, 20, 30, 40, 60, 90
    };
    static final int firedamage[] =
    {
        0, 15, 25, 50, 100, 200
    };
    static final int firenumber[] =
    {
        0, 1, 1, 2, 3, 4
    };
    static final String dogTextures[] =
    {
        "morecreeps:textures/entity/hotdg1.png", "morecreeps:textures/entity/hotdg2.png", "morecreeps:textures/entity/hotdg3.png"
    };
    public static final int leveldamage[] =
    {
        0, 50, 100, 250, 500, 800, 1200, 1700, 2200, 2700,
        3300, 3900, 4700, 5400, 6200, 7000, 7900, 8800, 9750, 10750,
        12500, 17500, 22500, 30000, 40000, 50000, 60000
    };
    public static final String levelname[] =
    {
        "Just A Pup", "Hotdog", "A Dirty Dog", "An Alley Dog", "Scrapyard Puppy", "Army Dog", "Private", "Private First Class", "Corporal", "Sergeant",
        "Staff Sergeant", "Sergeant First Class", "Master Segeant", "First Sergeant", "Sergeant Major", "Command Sergeant Major", "Second Lieutenant", "First Lieutenant", "Captain", "Major",
        "Lieutenant Colonel", "Colonel", "General of the Hotdog Army", "General of the Hotdog Army", "Sparky the Wonder Pooch", "Sparky the Wonder Pooch"
    };

    public CREEPSEntityHotdog(World world)
    {
        super(world);
        primed = false;
        basetexture = dogTextures[rand.nextInt(dogTextures.length)];
        texture = basetexture;
        setSize(0.5F, 0.75F);
        rideable = false;
        basehealth = rand.nextInt(15) + 5;
        health = basehealth;
        attackStrength = 1;
        tamed = false;
        name = "";
        level = 1;
        totaldamage = 0.0F;
        alt = false;
        heavenbuilt = false;
        wanderstate = 0;
        baseSpeed = 0.76F;
        moveSpeed = baseSpeed;
        speedboost = 0;
        totalexperience = 0;
        fallDistance = -25F;
        isImmuneToFire = true;
        angrydog = false;
        unmounttimer = 0;
        skillattack = 0;
        skilldefend = 0;
        skillhealing = 0;
        skillspeed = 0;
        firepower = 0;
        healtimer = 600;
        dogsize = 0.6F;
        chunky = false;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAILookIdle(this));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(basehealth);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(attackStrength);
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
        super.updateEntityActionState();
        setFire(0);

        if (tamed && wanderstate == 0)
        {
            firenum = 0;
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(16D, 16D, 16D));

            for (int i = 0; i < list.size(); i++)
            {
                Entity entity = (Entity)list.get(i);

                if (!(entity instanceof EntityCreature))
                {
                    continue;
                }

                EntityCreature entitycreature = (EntityCreature)entity;

                if (!(entitycreature.getAttackTarget() instanceof EntityPlayer) || (entitycreature instanceof CREEPSEntityHotdog) || (entitycreature instanceof CREEPSEntityHunchback) || (entitycreature instanceof CREEPSEntityGuineaPig) || (entitycreature instanceof CREEPSEntityArmyGuy) && ((CREEPSEntityArmyGuy)entitycreature).loyal)
                {
                    continue;
                }

                if (rand.nextInt(100) < firechance[skillattack] && firenum < firenumber[skillattack] && firepower >= 25)
                {
                    float f1 = getDistanceToEntity(entitycreature);

                    if (f1 < (float)(skillattack + 1) && rand.nextInt(100) < firechance[skillattack] && entitycreature.isBurning())
                    {
                        firepower -= 25;

                        if (firepower < 0)
                        {
                            firepower = 0;
                        }

                        entitycreature.setFire(firedamage[skillattack]);
                        firenum++;
                    }
                }

                this.getAttackTarget();
            }
        }

        if (!hasPath() && tamed && ridingEntity == null && wanderstate != 2)
        {
            

            if (entityplayer != null)
            {
                float f = entityplayer.getDistanceToEntity(this);

                if (f <= 5F);
            }
        }

        if (this.getAttackTarget() instanceof EntityPlayer)
        {

            if (getDistanceToEntity(entityplayer) < 6F)
            {
                this.setAttackTarget(null);
            }
        }

        if ((float)health < (float)basehealth * (0.1F * (float)skillhealing) && skillhealing > 0)
        {
            this.setAttackTarget(entityplayer);
        }
    }

    public float getEyeHeight()
    {
        return height * 0.5F;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (riddenByEntity != null && ridingEntity == null)
        {
            if (ridingEntity != null)
            {
                ridingEntity.mountEntity(null);
            }

            if (riddenByEntity != null)
            {
                riddenByEntity.mountEntity(null);
            }

            mountEntity(null);
        }

        if (ridingEntity != null && !(ridingEntity instanceof EntityPlayer) && !(ridingEntity instanceof CREEPSEntityGuineaPig) && !(ridingEntity instanceof CREEPSEntityHotdog) && !(ridingEntity instanceof CREEPSEntityDoghouse))
        {
            mountEntity(null);
            unmounttimer = 20;
        }

        if (healtimer-- < 1 && health < basehealth && skillhealing > 0)
        {
            healtimer = (6 - skillhealing) * 200;
            health += skillhealing;

            if (health > basehealth)
            {
                health = basehealth;
            }

            for (int i = 0; i < skillhealing; i++)
            {
                double d1 = rand.nextGaussian() * 0.02D;
                double d3 = rand.nextGaussian() * 0.02D;
                double d5 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle(EnumParticleTypes.HEART, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d1, d3, d5);
            }
        }

        if (handleWaterMovement())
        {
            motionY += 0.22879999876022339D;
            double d = (double)skillspeed / 2.5D;

            if (d < 1.0D)
            {
                d = 1.0D;
            }

            motionX *= d;
            motionZ *= d;
        }

        if (firepower >= 25 && tamed && prevPosX != posX)
        {
            int j = rand.nextInt(4);
            double d2 = (float)posX + 0.125F;
            double d4 = (float)posY + 0.4F;
            double d6 = (float)posZ + 0.5F;
            double d7 = 0.2199999988079071D;
            double d8 = 0.27000001072883606D;

            if (j == 1)
            {
                worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d2 - d8, d4 + d7, d6, 0.0D, 0.0D, 0.0D);
                worldObj.spawnParticle(EnumParticleTypes.FLAME, d2 - d8, d4 + d7, d6, 0.0D, 0.0D, 0.0D);
            }
            else if (j != 2 && j == 3)
            {
                worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d2, d4 + d7, d6 - d8, 0.0D, 0.0D, 0.0D);
                worldObj.spawnParticle(EnumParticleTypes.FLAME, d2, d4 + d7, d6 - d8, 0.0D, 0.0D, 0.0D);
            }
        }

        if (wanderstate == 0 && !hasPath() && angrydog)
        {
            EntityLivingBase entityliving = getAITarget();

            if (entityliving instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityliving;
                ItemStack itemstack = entityplayer.inventory.getCurrentItem();

                if (itemstack != null && tamed)
                {
                    if (itemstack.getItem() != Item.getItemFromBlock(Blocks.torch));
                }
            }
        }
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        Object obj = null;

        if (tamed && wanderstate == 0)
        {
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(16D, 16D, 16D));

            for (int i = 0; i < list.size(); i++)
            {
                Entity entity = (Entity)list.get(i);

                if (entity instanceof EntityCreature)
                {
                    EntityCreature entitycreature = (EntityCreature)entity;

                    if ((entitycreature.getAttackTarget() instanceof EntityPlayer) && !(entitycreature instanceof CREEPSEntityHotdog) && !(entitycreature instanceof CREEPSEntityHunchback) && !(entitycreature instanceof CREEPSEntityGuineaPig) && (!(entitycreature instanceof CREEPSEntityArmyGuy) || !((CREEPSEntityArmyGuy)entitycreature).loyal))
                    {
                        obj = entitycreature;
                    }
                }

                
				if (!(entity instanceof EntityPlayer) || wanderstate != 0 || (getAttackTarget() instanceof EntityPlayer))
                {
                    continue;
                }

                EntityPlayer entityplayer = (EntityPlayer)entity;

                if (entityplayer == null || obj != null && !(obj instanceof EntityPlayer))
                {
                    continue;
                }

                distance = getDistanceToEntity(entityplayer);

                if (distance < 8F)
                {
                    obj = null;
                }
                else
                {
                    obj = entityplayer;
                }
            }
        }

        return ((Entity)(obj));
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity entity, float f)
    {
        if (wanderstate == 0 && !(getAttackTarget() instanceof EntityPlayer) && !(getAttackTarget() instanceof CREEPSEntityGuineaPig) && ridingEntity == null)
        {
            if (onGround && tamed)
            {
                double d = entity.posX - posX;
                double d2 = entity.posZ - posZ;
                float f2 = MathHelper.sqrt_double(d * d + d2 * d2);
                motionX = (d / (double)f2) * 0.5D * 0.88000001192092892D + motionX * 0.20000000298023224D;
                motionZ = (d2 / (double)f2) * 0.5D * 0.88000001192092892D + motionZ * 0.20000000298023224D;
                motionY = 0.4200000059604645D;
            }
            else if (tamed && (double)f < 3.3999999999999999D)
            {
                if (rand.nextInt(5) == 0)
                {
                    worldObj.playSoundAtEntity(this, "morecreeps:hotdogattack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }

                double d1 = 1.0D;
                d1 += level * 5 + skillattack * 4;

                if (d1 < 5D)
                {
                    d1 = 5D;
                }

                super.attackEntityAsMob(entity);

                if ((double)rand.nextInt(100) < d1)
                {
                    if (CREEPSConfig.Blood)
                    {
                        for (int i = 0; i < 10; i++)
                        {
                            CREEPSFxBlood creepsfxblood = new CREEPSFxBlood(worldObj, entity.posX, entity.posY + 1.0D, entity.posZ, MoreCreepsAndWeirdos.partRed, 0.255F);
                            creepsfxblood.renderDistanceWeight = 20D;
                            Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxblood);
                        }
                    }

                    float f1 = (float)attackStrength * 0.25F;

                    if (f1 < 1.0F)
                    {
                        f1 = 1.0F;
                    }

                    if ((float)((EntityLiving)entity).getHealth() - f1 <= 0.0F)
                    {
                        worldObj.playSoundAtEntity(entity, "morecreeps:hotdogkill", 1.0F, 1.0F);
                    }

                    ((EntityLiving)entity).attackEntityFrom(DamageSource.causeThrownDamage(this, (EntityLiving)entity), (int)f1);
                    totaldamage += (int)((double)f1 * 1.5D + (double)skillattack);
                    totalexperience += (int)((double)f1 * 1.5D + (double)skillattack);
                }

                if (totaldamage > (float)leveldamage[level] && level < 25)
                {
                    level++;
                    totaldamage = 0.0F;
                    dogsize += 0.05F;

                    if ((double)dogsize > 1.5D)
                    {
                        dogsize = 1.5F;
                    }

                    
                    boolean flag = false;

                    if (level == 5)
                    {
                        flag = true;
                        confetti();
                        entityplayer.addStat(MoreCreepsAndWeirdos.achievehotdoglevel5, 1);
                    }

                    if (level == 10)
                    {
                        flag = true;
                        confetti();
                        entityplayer.addStat(MoreCreepsAndWeirdos.achievehotdoglevel10, 1);
                    }

                    if (level == 20)
                    {
                        flag = true;
                        confetti();
                        entityplayer.addStat(MoreCreepsAndWeirdos.achievehotdoglevel25, 1);
                    }

                    if (flag)
                    {
                        worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                        double d4 = -MathHelper.sin((((EntityPlayer)(entityplayer)).rotationYaw * (float)Math.PI) / 180F);
                        double d6 = MathHelper.cos((((EntityPlayer)(entityplayer)).rotationYaw * (float)Math.PI) / 180F);
                        CREEPSEntityTrophy creepsentitytrophy = new CREEPSEntityTrophy(worldObj);
                        creepsentitytrophy.setLocationAndAngles(((EntityPlayer)(entityplayer)).posX + d4 * 3D, ((EntityPlayer)(entityplayer)).posY - 2D, ((EntityPlayer)(entityplayer)).posZ + d6 * 3D, ((EntityPlayer)(entityplayer)).rotationYaw, 0.0F);
                        worldObj.spawnEntityInWorld(creepsentitytrophy);
                    }

                    MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("\247b").append(name).append(" \247fincreased to level \2476").append(String.valueOf(level)).append("!").toString());
                    worldObj.playSoundAtEntity(this, "morecreeps:ggpiglevelup", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    baseSpeed += 0.025F;
                    basehealth += rand.nextInt(4) + 1;
                    attackStrength++;
                }

                double d3 = -MathHelper.sin((rotationYaw * (float)Math.PI) / 180F);
                double d5 = MathHelper.cos((rotationYaw * (float)Math.PI) / 180F);
                entity.motionX = d3 * 0.15000000596046448D;
                entity.motionZ = d5 * 0.15000000596046448D;
            }
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        Entity entity = damagesource.getEntity();

        if (entity != getAttackTarget())
        {
            this.setAttackTarget((EntityLivingBase) entity);
        }

        if (rand.nextInt(100) < skilldefend * 10)
        {
            i--;

            if (skilldefend == 5)
            {
                i--;
            }

            
            worldObj.playSoundAtEntity(entityplayer, "morecreeps:hotdogwhoosh", 1.0F, 1.0F);
        }

        if (i < 1)
        {
            i = 1;
        }

        if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
        {
            i = (i + 1) / 2;
        }

        if (super.attackEntityFrom(DamageSource.causeMobDamage(this), i))
        {
            if (!tamed && !angrydog)
            {
                if (entity instanceof EntityPlayer)
                {
                    angrydog = true;
                    this.setAttackTarget((EntityLivingBase) entity);
                }

                if ((entity instanceof EntityArrow) && ((EntityArrow)entity).shootingEntity != null)
                {
                    entity = ((EntityArrow)entity).shootingEntity;
                }

                if (entity instanceof EntityLiving)
                {
                    List list = worldObj.getEntitiesWithinAABB(CREEPSEntityHotdog.class, AxisAlignedBB.fromBounds(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
                    Iterator iterator = list.iterator();

                    do
                    {
                        if (!iterator.hasNext())
                        {
                            break;
                        }

                        Entity entity1 = (Entity)iterator.next();
                        CREEPSEntityHotdog creepsentityhotdog = (CREEPSEntityHotdog)entity1;

                        if (!creepsentityhotdog.tamed && creepsentityhotdog.getAttackTarget() == null)
                        {
                           entity = creepsentityhotdog.getAttackTarget();

                            if (entity instanceof EntityPlayer)
                            {
                                creepsentityhotdog.angrydog = true;
                            }
                        }
                    }
                    while (true);
                }
            }
            else if (entity != this && entity != null)
            {
                if (tamed && (entity instanceof EntityPlayer))
                {
                    return true;
                }

                entity = getAttackTarget();
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Checks if this entity is inside of an opaque block
     */
    public boolean isEntityInsideOpaqueBlock()
    {
        if (ridingEntity != null || unmounttimer-- > 0)
        {
            return false;
        }
        else
        {
            return super.isEntityInsideOpaqueBlock();
        }
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
        nbttagcompound.setInteger("Level", level);
        nbttagcompound.setString("BaseTexture", basetexture);
        nbttagcompound.setFloat("TotalDamage", totaldamage);
        nbttagcompound.setBoolean("heavenbuilt", heavenbuilt);
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
        level = nbttagcompound.getInteger("Level");
        totaldamage = nbttagcompound.getFloat("TotalDamage");
        heavenbuilt = nbttagcompound.getBoolean("heavenbuilt");
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

        if (dogsize < 0.7F)
        {
            dogsize = 0.7F;
        }

        texture = basetexture;

        if (wanderstate == 1)
        {
            moveSpeed = 0.0F;
        }
        else
        {
            moveSpeed = speedboost <= 0 ? baseSpeed : baseSpeed + 0.5F;
        }
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        if (interest == 0 || health <= 0)
        {
            if (tamed)
            {
                CREEPSEntityTombstone creepsentitytombstone = new CREEPSEntityTombstone(worldObj);
                creepsentitytombstone.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
                creepsentitytombstone.interest = interest;
                creepsentitytombstone.tamed = tamed;
                creepsentitytombstone.name = name;
                creepsentitytombstone.basehealth = basehealth;

                if (level > 1)
                {
                    level--;
                }

                creepsentitytombstone.level = level;
                creepsentitytombstone.basetexture = basetexture;
                creepsentitytombstone.totaldamage = 0.0F;
                creepsentitytombstone.heavenbuilt = heavenbuilt;
                creepsentitytombstone.attackStrength = attackStrength;
                creepsentitytombstone.wanderstate = wanderstate;
                creepsentitytombstone.speedboost = speedboost;
                creepsentitytombstone.totalexperience = totalexperience;
                creepsentitytombstone.baseSpeed = baseSpeed;
                creepsentitytombstone.skillattack = skillattack;
                creepsentitytombstone.skilldefend = skilldefend;
                creepsentitytombstone.skillhealing = skillhealing;
                creepsentitytombstone.skillspeed = skillspeed;
                creepsentitytombstone.firepower = firepower;
                creepsentitytombstone.dogsize = dogsize;
                creepsentitytombstone.deathtype = "Hotdog";
                worldObj.spawnEntityInWorld(creepsentitytombstone);
            }

            super.setDead();
        }
        else
        {
            isDead = false;
            deathTime = 0;
            return;
        }
    }

    private void explode()
    {
        float f = 2.0F;
        worldObj.createExplosion(null, posX, posY, posZ, f, true);
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
        int j = MathHelper.floor_double(this.getBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        int l = worldObj.getBlockLightOpacity(new BlockPos(i, j, k));
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        return i1 != Blocks.sand && i1 != Blocks.cobblestone && worldObj.checkBlockCollision(getBoundingBox()) && worldObj.canBlockSeeSky(new BlockPos(i, j, k)) && rand.nextInt(5) == 0 && l > 8;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        if (ridingEntity instanceof EntityPlayer)
        {
            return (double)(this.getYOffset() - 1.15F);
        }
        else
        {
            return (double)getYOffset();
        }
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
            entityplayer.openGui(MoreCreepsAndWeirdos.instance, 2, worldObj, (int)this.posX, (int)this.posY, (int)this.posZ);
            return true;
        }

        if (itemstack == null && tamed && health > 0)
        {
            rotationYaw = entityplayer.rotationYaw;
            Object obj = entityplayer;

            if (ridingEntity != obj)
            {
                int k;

                for (k = 0; ((Entity)obj).riddenByEntity != null && k < 20; k++)
                {
                    obj = ((Entity)obj).riddenByEntity;
                }

                if (k < 20)
                {
                    rotationYaw = ((Entity)obj).rotationYaw;
                    mountEntity((Entity)obj);
                    worldObj.playSoundAtEntity(this, "morecreeps:hotdogpickup", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }
            }
            else
            {
                int l;

                for (l = 0; ((Entity)obj).riddenByEntity != null && l < 20; l++)
                {
                    obj = ((Entity)obj).riddenByEntity;
                }

                if (l < 20)
                {
                    rotationYaw = ((Entity)obj).rotationYaw;
                    ((Entity)obj).fallDistance = -25F;
                    ((Entity)obj).mountEntity(null);

                    if ((Entity)obj instanceof CREEPSEntityHotdog)
                    {
                        ((CREEPSEntityHotdog)obj).unmounttimer = 20;
                    }

                    worldObj.playSoundAtEntity(this, "morecreeps:hotdogputdown", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }
            }
        }

        if (itemstack != null && health > 0)
        {
            if (itemstack.getItem() == Items.redstone && tamed)
            {
                firepower += 250;
                worldObj.playSoundAtEntity(this, "morecreeps:hotdogredstone", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            }
            
            

            if ((itemstack.getItem() == Items.book || itemstack.getItem() == Items.paper || itemstack.getItem() == MoreCreepsAndWeirdos.guineapigradio) && tamed)
            {
                entityplayer.openGui(MoreCreepsAndWeirdos.instance, 3, worldObj, (int)this.posX, (int)this.posY, (int)this.posZ);
            }

            if (itemstack.getItem() == Items.diamond && tamed)
            {
                if (!heavenbuilt)
                {
                    if (level >= 25)
                    {
                        int i = MathHelper.floor_double(entityplayer.posX);
                        int i1 = MathHelper.floor_double(entityplayer.getBoundingBox().minY);
                        int j1 = MathHelper.floor_double(entityplayer.posZ);
                        buildHeaven(entityplayer, i + 1, i1, j1 + 1);

                        if (heavenbuilt)
                        {
                            confetti();
                            worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                            entityplayer.addStat(MoreCreepsAndWeirdos.achievehotdogheaven, 1);
                        }
                    }
                    else
                    {
                        worldObj.playSoundAtEntity(this, "morecreeps:hotdoglevelneeded", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                        MoreCreepsAndWeirdos.proxy.addChatMessage("Your Hotdog must be level 25 to build Hot Dog Heaven.");
                        MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("\247b").append(name).append(" is only level \247f").append(String.valueOf(level)).toString());
                    }
                }
                else
                {
                	MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("\247b").append(name).append("\247f has already built Hot Dog Heaven.").toString());
                }
            }

            if (Item.getIdFromItem(itemstack.getItem()) == 37 && tamed || Item.getIdFromItem(itemstack.getItem()) == 38 && tamed)
            {
                smokePlain();

                if (wanderstate == 0)
                {
                	MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("\2473").append(name).append("\2476 will \2474STAY\2476 right here.").toString());
                    wanderstate = 1;
                    moveSpeed = 0.0F;
                }
                else if (wanderstate == 1)
                {
                	MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("\2473").append(name).append("\2476 will \247dWANDER\2476 around and have fun.").toString());
                    wanderstate = 2;
                    moveSpeed = speedboost <= 0 ? baseSpeed : baseSpeed + 0.5F;
                }
                else if (wanderstate == 2)
                {
                	MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("\2473").append(name).append("\2476 will \2472FIGHT\2476 and follow you!").toString());
                    wanderstate = 0;
                    moveSpeed = speedboost <= 0 ? baseSpeed : baseSpeed + 0.5F;
                }
            }

            if (itemstack.getItem() == Items.reeds && tamed)
            {
                smokePlain();
                used = true;

                if (speedboost < 0)
                {
                    speedboost = 0;
                }

                speedboost += 13000;

                if (wanderstate != 1)
                {
                    moveSpeed = baseSpeed + 0.5F;
                }

                int j = speedboost / 21;
                j /= 60;
                String s = "";

                if (j < 0)
                {
                    j = 0;
                }

                if (j > 1)
                {
                    s = "s";
                }

                worldObj.playSoundAtEntity(this, "morecreeps:guineapigspeedup", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("\2473").append(name).append("\247f ").append(String.valueOf(j)).append("\2476 minute").append(s).append(" of speedboost left.").toString());
            }

            if (itemstack.getItem() == Items.egg)
            {
                used = true;
                worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 0.5F);
                setLocationAndAngles(entityplayer.posX, entityplayer.posY + (double)entityplayer.getEyeHeight(), entityplayer.posZ, entityplayer.rotationYaw, entityplayer.rotationPitch);
                motionX = -MathHelper.sin((rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float)Math.PI);
                motionZ = MathHelper.cos((rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float)Math.PI);
                double d = motionX / 100D;
                double d1 = motionZ / 100D;

                for (int l1 = 0; l1 < 2000; l1++)
                {
                    moveEntity(d, 0.0D, d1);
                    double d2 = rand.nextGaussian() * 0.02D;
                    double d3 = rand.nextGaussian() * 0.02D;
                    double d4 = rand.nextGaussian() * 0.02D;
                    worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d2, d3, d4);
                }

                worldObj.createExplosion(null, posX, posY, posZ, 1.1F, true);
                interest = 0;
                health = 0;
                setDead();
            }
            else
            {
                primed = false;
            }

            byte byte0 = 0;
            byte byte1 = 0;

            if (tamed && texture.length() == 37)
            {
            	//Add 14 because texture string is longer
                String s1 = basetexture.substring(18 + 14, 19 + 14);
                char c = s1.charAt(0);

                if (c == 'L')
                {
                    byte0 = 5;
                    byte1 = 1;
                }

                if (c == 'I')
                {
                    byte0 = 9;
                    byte1 = 2;
                }

                if (c == 'G')
                {
                    byte0 = 15;
                    byte1 = 3;
                }

                if (c == 'D')
                {
                    byte0 = 22;
                    byte1 = 6;
                }
            }

            if (tamed)
            {
                armor = Item.getIdFromItem(itemstack.getItem());
                smoke();
                int k1 = 0;

                if (armor > 297 + k1 && armor < 302 + k1)
                {
                    used = true;
                    basehealth += 5 - byte0;
                    attackStrength += 1 - byte1;
                    health = basehealth;
                    String s2 = basetexture.substring(0, 18 + 14);
                    s2 = (new StringBuilder()).append(s2).append("L.png").toString();
                    texture = s2;
                    basetexture = s2;
                    smoke();
                    worldObj.playSoundAtEntity(this, "morecreeps:ggpigarmor", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }

                if (armor > 313 + k1 && armor < 318 + k1)
                {
                    used = true;
                    basehealth += 15 - byte0;
                    attackStrength += 3 - byte1;
                    health = basehealth;
                    String s3 = basetexture.substring(0, 18 + 14);
                    s3 = (new StringBuilder()).append(s3).append("G.png").toString();
                    texture = s3;
                    basetexture = s3;
                    smoke();
                    worldObj.playSoundAtEntity(this, "morecreeps:ggpigarmor", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }

                if (armor > 305 + k1 && armor < 310 + k1)
                {
                    used = true;
                    basehealth += 9 - byte0;
                    attackStrength += 2 - byte1;
                    health = basehealth;
                    String s4 = basetexture.substring(0, 18 + 14);
                    s4 = (new StringBuilder()).append(s4).append("I.png").toString();
                    texture = s4;
                    basetexture = s4;
                    smoke();
                    worldObj.playSoundAtEntity(this, "morecreeps:ggpigarmor", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }

                if (armor > 309 + k1 && armor < 314 + k1)
                {
                    used = true;
                    basehealth += 22 - byte0;
                    attackStrength += 6 - byte1;
                    health = basehealth;
                    String s5 = basetexture.substring(0, 18 + 14);
                    s5 = (new StringBuilder()).append(s5).append("D.png").toString();
                    texture = s5;
                    basetexture = s5;
                    smoke();
                    worldObj.playSoundAtEntity(this, "morecreeps:ggpigarmor", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }
            }

            if (itemstack.getItem() == Items.bone)
            {
                worldObj.playSoundAtEntity(this, "morecreeps:hotdogeat", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                used = true;
                interest += 15;
                health += 10;
                isDead = false;
                smoke();
            }

            if (itemstack.getItem() == Items.porkchop)
            {
                worldObj.playSoundAtEntity(this, "morecreeps:hotdogeat", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                used = true;
                interest += 30;
                health += 15;
                isDead = false;
                smoke();
            }

            if (itemstack.getItem() == Items.cooked_porkchop)
            {
                worldObj.playSoundAtEntity(this, "morecreeps:hotdogeat", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                used = true;
                interest += 55;
                health += 25;
                isDead = false;
                smoke();
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

            if (!primed && interest > 100)
            {
                
                    confetti();
                    worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                    entityplayer.addStat(MoreCreepsAndWeirdos.achievehotdogtaming, 1);
                

                if (used)
                {
                    smoke();
                }

                tamed = true;
                
                owner = entityplayer;

                if (name.length() < 1)
                {
                    name = Names[rand.nextInt(Names.length)];
                }

                worldObj.playSoundAtEntity(this, "morecreeps:hotdogtamed", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                interest = 100;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public void buildHeaven(EntityPlayer entityplayer, int i, int j, int k)
    {
        if (j > 95)
        {
        	MoreCreepsAndWeirdos.proxy.addChatMessage("You are too far up to build Hotdog Heaven!");
            return;
        }

        byte byte0 = 40;
        byte byte1 = 40;
        boolean flag = false;
        int l = (105 - j) / 2;
        int i1 = 0;

        for (int j1 = 0; j1 < l * 2; j1++)
        {
            for (int i5 = -2; i5 < byte0 + 2; i5++)
            {
                for (int j7 = -2; j7 < byte1 + 2; j7++)
                {
                    if (Block.getIdFromBlock(worldObj.getBlockState(new BlockPos(i + i5, j + j1, k + j7)).getBlock()) != 0)
                    {
                        i1++;
                    }
                }
            }
        }

        if (worldObj.isBlockLoaded(new BlockPos(i - byte0 / 2, j, k - byte1 / 2)) && worldObj.isBlockLoaded(new BlockPos(i + byte0, j, k)) && worldObj.isBlockLoaded(new BlockPos(i + byte0, j, k + byte1)) && worldObj.isBlockLoaded(new BlockPos(i, j, k - byte1)))
        {
            chunky = true;
        }

        if (i1 < 3000 && chunky)
        {
            worldObj.playSoundAtEntity(this, "morecreeps:hotdogheaven", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            used = true;
            heavenbuilt = true;
            worldObj.playSoundAtEntity(this, "morecreeps:guineapighotel", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            MoreCreepsAndWeirdos.proxy.addChatMessage("HOT DOG HEAVEN HAS BEEN BUILT!");
            //worldObj.setBlockWithNotify(i, j, k, Block.planks.blockID);
            worldObj.setBlockState(new BlockPos(i, j, k), Blocks.planks.getDefaultState());
            //worldObj.setBlockWithNotify(i, j + 1, k, Block.torchWood.blockID);
            worldObj.setBlockState(new BlockPos(i, j + 1, k), Blocks.torch.getDefaultState());
            //worldObj.setBlockWithNotify(i + 5, j, k, Block.planks.blockID);
            worldObj.setBlockState(new BlockPos(i + 5, j, k), Blocks.planks.getDefaultState());
            //worldObj.setBlockWithNotify(i + 5, j + 1, k, Block.torchWood.blockID);
            worldObj.setBlockState(new BlockPos(i + 5, j + 1, k), Blocks.planks.getDefaultState());

            for (int k1 = 0; k1 < l; k1++)
            {
                for (int j5 = 0; j5 < 4; j5++)
                {
                    //worldObj.setBlockWithNotify(i + j5 + 1, j + k1, k + k1, Block.stairCompactPlanks.blockID);
                	worldObj.setBlockState(new BlockPos(i + j5 + 1, j + k1, k + k1), Blocks.oak_stairs.getDefaultState());
                    //worldObj.setBlockMetadataWithNotify(i + j5 + 1, j + k1, k + k1, 2);
                	worldObj.setBlockState(new BlockPos(i + j5 + 1, j + k1, k + k1), Blocks.grass.getDefaultState());
                }
            }

            for (int l1 = 0; l1 < l - 1; l1++)
            {
                for (int k5 = 0; k5 < 4; k5++)
                {
                    //worldObj.setBlockWithNotify(i - k5, j + l + l1, (k + l) - l1, Block.stairCompactPlanks.blockID);
                	worldObj.setBlockState(new BlockPos(i - k5, j + l + l1, (k + l) - l1), Blocks.oak_stairs.getDefaultState());
                    //worldObj.setBlockMetadataWithNotify(i - k5, j + l + l1, (k + l) - l1, 3);
                	worldObj.setBlockState(new BlockPos(i - k5, j + l + l1, (k + l) - l1), Blocks.dirt.getDefaultState());
                }
            }

            for (int i2 = 0; i2 < 10; i2++)
            {
                //worldObj.setBlockWithNotify((i - i2) + 5, j + l, k + l + 6, Block.fence.blockID);
            	worldObj.setBlockState(new BlockPos((i - i2) + 5, j + l, k + l + 6), Blocks.oak_fence.getDefaultState());

                for (int l5 = 0; l5 < 7; l5++)
                {
                    //worldObj.setBlockWithNotify(i + 5, j + l, k + l + l5, Block.fence.blockID);
                	worldObj.setBlockState(new BlockPos(i + 5, j + l, k + l + l5), Blocks.oak_fence.getDefaultState());
                    //worldObj.setBlockWithNotify(i - 4, j + l, k + l + l5, Block.fence.blockID);
                	worldObj.setBlockState(new BlockPos(i - 4, j + l, k + l + l5), Blocks.oak_fence.getDefaultState());
                    flag = !flag;

                    if (flag)
                    {
                        //worldObj.setBlockWithNotify(i + 5, j + l + 1, k + l + l5, Block.torchWood.blockID);
                    	worldObj.setBlockState(new BlockPos(i + 5, j + l + 1, k + l + l5), Blocks.torch.getDefaultState());
                    }

                    if (flag)
                    {
                        //worldObj.setBlockWithNotify(i - 4, j + l + 1, k + l + l5, Block.torchWood.blockID);
                    	worldObj.setBlockState(new BlockPos(i - 4, j + l + 1, k + l + l5), Blocks.torch.getDefaultState());
                    }

                    //worldObj.setBlockWithNotify((i - i2) + 5, (j + l) - 1, k + l + l5, Block.planks.blockID);
                    worldObj.setBlockState(new BlockPos((i - i2) + 5, (j + l) - 1, k + l + l5), Blocks.planks.getDefaultState());
                }
            }

            for (int j2 = 0; j2 < byte0; j2++)
            {
                for (int i6 = 0; i6 < byte1; i6++)
                {
                    for (int k7 = 0 - rand.nextInt(3) - 2; k7 < 1; k7++)
                    {
                        if (k7 < 0)
                        {
                            //worldObj.setBlockWithNotify((i + j2) - byte0 / 2, (j + l * 2 + k7) - 2, ((k + i6) - byte1) + 2, Block.dirt.blockID);
                        	worldObj.setBlockState(new BlockPos((i + j2) - byte0 / 2, (j + l * 2 + k7) - 2, ((k + i6) - byte1)), Blocks.dirt.getDefaultState());
                        }
                        else
                        {
                            //worldObj.setBlockWithNotify((i + j2) - byte0 / 2, (j + l * 2 + k7) - 2, ((k + i6) - byte1) + 2, Block.grass.blockID);
                        	worldObj.setBlockState(new BlockPos((i + j2) - byte0 / 2, (j + l * 2 + k7) - 2, ((k + i6) - byte1) + 2), Blocks.grass.getDefaultState());
                        }
                    }
                }
            }

            for (int k2 = 0; k2 < rand.nextInt(10) + 2; k2++)
            {
                //worldObj.setBlockWithNotify((i + rand.nextInt(byte0 - 10)) - byte0 / 2, (j + l * 2) - 1, (k + rand.nextInt(byte1 - 6)) - byte1, 32);
            	worldObj.setBlockState(new BlockPos((i + rand.nextInt(byte0 - 10)) - byte0 / 2, (j + l * 2) - 1, (k + rand.nextInt(byte1 - 6))), Blocks.deadbush.getDefaultState());
            }

            for (int l2 = 0; l2 < rand.nextInt(10) + 2; l2++)
            {
                //worldObj.setBlockWithNotify((i + rand.nextInt(byte0 - 10)) - byte0 / 2, (j + l * 2) - 1, (k + rand.nextInt(byte1 - 6)) - byte1, 37);
            	worldObj.setBlockState(new BlockPos((i + rand.nextInt(byte0 - 10)) - byte0 / 2, (j + l * 2) - 1, (k + rand.nextInt(byte1 - 6))), Blocks.yellow_flower.getDefaultState());
            }

            for (int i3 = 0; i3 < rand.nextInt(10) + 2; i3++)
            {
                //worldObj.setBlockWithNotify((i + rand.nextInt(byte0 - 10)) - byte0 / 2, (j + l * 2) - 1, (k + rand.nextInt(byte1 - 6)) - byte1, 38);
            	worldObj.setBlockState(new BlockPos((i + rand.nextInt(byte0 - 10)) - byte0 / 2, (j + l * 2) - 1, (k + rand.nextInt(byte1 - 6)) - byte1), Blocks.red_flower.getDefaultState());
            }

            for (int j3 = 0; j3 < rand.nextInt(30) + 2; j3++)
            {
                int j6 = rand.nextInt(byte0 - 12);
                int l7 = rand.nextInt(byte1 - 8);

                if (Block.getIdFromBlock(worldObj.getBlockState(new BlockPos((i + j6) - byte0 / 2, (j + l * 2) - 1, (k + l7) - byte1)).getBlock()) == 0)
                {
                    //worldObj.setBlockWithNotify((i + j6) - byte0 / 2, (j + l * 2) - 1, (k + l7) - byte1, 31);
                	worldObj.setBlockState(new BlockPos((i + j6) - byte0 / 2, (j + l * 2) - 1, (k + l7) - byte1), Blocks.deadbush.getDefaultState());
                    //worldObj.setBlockMetadataWithNotify((i + j6) - byte0 / 2, (j + l * 2) - 1, (k + l7) - byte1, 2);
                	worldObj.setBlockState(new BlockPos((i + j6) - byte0 / 2, (j + l * 2) - 1, (k + l7) - byte1), Blocks.grass.getDefaultState());
                }
            }

            for (int k3 = 0; k3 < rand.nextInt(50) + 2; k3++)
            {
                int k6 = rand.nextInt(byte0 - 12);
                int i8 = rand.nextInt(byte1 - 8);

                if (Block.getIdFromBlock(worldObj.getBlockState(new BlockPos((i + k6) - byte0 / 2, (j + l * 2) - 1, (k + i8) - byte1)).getBlock()) == 0)
                {
                    //worldObj.setBlockWithNotify((i + k6) - byte0 / 2, (j + l * 2) - 1, (k + i8) - byte1, 31);
                	worldObj.setBlockState(new BlockPos((i + k6) - byte0 / 2, (j + l * 2) - 1, (k + i8) - byte1), Blocks.deadbush.getDefaultState());
                    //worldObj.setBlockMetadataWithNotify((i + k6) - byte0 / 2, (j + l * 2) - 1, (k + i8) - byte1, 1);
                	worldObj.setBlockState(new BlockPos((i + k6) - byte0 / 2, (j + l * 2) - 1, (k + i8) - byte1), Blocks.stone.getDefaultState());
                }
            }

            for (int l3 = 1; l3 < byte0 - 1; l3++)
            {
                //worldObj.setBlockWithNotify((i + l3) - byte0 / 2, (j + l * 2) - 1, (k - byte1) + 3, Block.fence.blockID);
            	worldObj.setBlockState(new BlockPos((i + l3) - byte0 / 2, (j + l * 2) - 1, (k - byte1) + 3), Blocks.oak_fence.getDefaultState());
                //worldObj.setBlockWithNotify((i + l3) - byte0 / 2, (j + l * 2) - 1, k, Block.fence.blockID);
            	worldObj.setBlockState(new BlockPos((i + l3) - byte0 / 2, (j + l * 2) - 1, k), Blocks.oak_fence.getDefaultState());
                flag = !flag;

                if (flag)
                {
                    //worldObj.setBlockWithNotify((i + l3) - byte0 / 2, j + l * 2, (k - byte1) + 3, Block.torchWood.blockID);
                	worldObj.setBlockState(new BlockPos((i + l3) - byte0 / 2, j + l * 2, (k - byte1) + 3), Blocks.torch.getDefaultState());
                }

                if (flag)
                {
                    //worldObj.setBlockWithNotify((i + l3) - byte0 / 2, j + l * 2, k, Block.torchWood.blockID);
                	worldObj.setBlockState(new BlockPos((i + l3) - byte0 / 2, j + l * 2, k), Blocks.torch.getDefaultState());
                }
            }

            for (int i4 = 4; i4 < byte1; i4++)
            {
                //worldObj.setBlockWithNotify((i - byte0 / 2) + 1, (j + l * 2) - 1, (k + i4) - byte1, Block.fence.blockID);
            	worldObj.setBlockState(new BlockPos((i - byte0 / 2) + 1, (j + l * 2) - 1, (k + i4) - byte1), Blocks.oak_fence.getDefaultState());
                //worldObj.setBlockWithNotify((i + byte0) - byte0 / 2 - 2, (j + l * 2) - 1, (k + i4) - byte1, Block.fence.blockID);
            	worldObj.setBlockState(new BlockPos((i + byte0) - byte0 / 2 - 2, (j + l * 2) - 1, (k + i4) - byte1), Blocks.oak_fence.getDefaultState());
                flag = !flag;

                if (flag)
                {
                    //worldObj.setBlockWithNotify((i - byte0 / 2) + 1, j + l * 2, (k + i4) - byte1, Block.torchWood.blockID);
                	worldObj.setBlockState(new BlockPos((i - byte0 / 2) + 1, j + l * 2, (k + i4) - byte1), Blocks.torch.getDefaultState());
                }

                if (flag)
                {
                    //worldObj.setBlockWithNotify((i + byte0) - byte0 / 2 - 2, j + l * 2, (k + i4) - byte1, Block.torchWood.blockID);
                	worldObj.setBlockState(new BlockPos((i + byte0) - byte0 / 2 - 2, j + l * 2, (k + i4) - byte1), Blocks.torch.getDefaultState());
                }
            }

            //worldObj.setBlockWithNotify(i - 1, (j + l * 2) - 1, k, 107);
            worldObj.setBlockState(new BlockPos(i - 1, (j + l * 2) - 1, k), Blocks.oak_fence_gate.getDefaultState());
            //worldObj.setBlockWithNotify(i - 2, (j + l * 2) - 1, k, 107);
            worldObj.setBlockState(new BlockPos(i - 2, (j + l * 2) - 1, k), Blocks.oak_fence_gate.getDefaultState());

            for (int j4 = 0; j4 < 6; j4++)
            {
                CREEPSEntityDoghouse creepsentitydoghouse = new CREEPSEntityDoghouse(worldObj);
                creepsentitydoghouse.setLocationAndAngles(i + 15, (j + l * 2) - 1, k - 7 - j4 * 5, 90F, 0.0F);
                worldObj.spawnEntityInWorld(creepsentitydoghouse);
            }

            for (int k4 = 0; k4 < rand.nextInt(15) + 5; k4++)
            {
                int l6 = rand.nextInt(byte0 - 10) + 3;
                int j8 = rand.nextInt(byte1 - 6) + 3;
                //worldObj.setBlockWithNotify((i + l6) - byte0 / 2, (j + l * 2) - 1, (k + j8) - byte1, Block.sapling.blockID);
                worldObj.setBlockState(new BlockPos((i + l6) - byte0 / 2, (j + l * 2) - 1, (k + j8) - byte1), Blocks.sapling.getDefaultState());
                ((BlockSapling)Blocks.sapling).generateTree(worldObj, new BlockPos((i + l6) - byte0 / 2, (j + l * 2) - 1, (k + j8) - byte1), Block.getStateById(1),worldObj.rand);
            }

            for (int l4 = (byte0 / 2 + rand.nextInt(8)) - 8; l4 < ((byte0 / 2 + rand.nextInt(10)) - 5) + 8; l4++)
            {
                for (int i7 = (byte1 / 2 + rand.nextInt(8)) - 8; i7 < ((byte1 / 2 + rand.nextInt(10)) - 5) + 8; i7++)
                {
                    //worldObj.setBlockWithNotify((i + l4) - byte0 / 2, (j + l * 2) - 2, (k + i7) - byte1, Block.waterStill.blockID);
                	worldObj.setBlockState(new BlockPos((i + l4) - byte0 / 2, (j + l * 2) - 2, (k + i7) - byte1), Blocks.water.getDefaultState());
                    //worldObj.setBlockWithNotify((i + l4) - byte0 / 2, (j + l * 2) - 3, (k + i7) - byte1, Block.waterStill.blockID);
                	worldObj.setBlockState(new BlockPos((i + l4) - byte0 / 2, (j + l * 2) - 3, (k + i7) - byte1), Blocks.water.getDefaultState());
                }
            }

            //worldObj.setBlock(i + 7, (j + l * 2) - 1, k - 5, 54);
            worldObj.setBlockState(new BlockPos(i, j, k), Blocks.chest.getDefaultState());
            TileEntityChest tileentitychest = new TileEntityChest();
            //worldObj.setBlockTileEntity(i + 7, (j + l * 2) - 1, k - 5, tileentitychest);
            worldObj.setTileEntity(new BlockPos(i + 7, (j + l * 2) - 1, k - 5), tileentitychest);
            //worldObj.setBlockMetadataWithNotify(i + 7, (j + l * 2) - 1, k - 5, 4);
            worldObj.setBlockState(new BlockPos(i + 7, (j + l * 2) - 1, k - 5), Blocks.cobblestone.getDefaultState());
            //worldObj.setBlock(i + 7, (j + l * 2) - 1, k - 6, 54);
            worldObj.setBlockState(new BlockPos(i + 7, (j + l * 2) - 1, k - 6), Blocks.chest.getDefaultState());
            TileEntityChest tileentitychest1 = new TileEntityChest();
            //worldObj.setBlockTileEntity(i + 7, (j + l * 2) - 1, k - 6, tileentitychest1);
            worldObj.setTileEntity(new BlockPos(i + 7, (j + l * 2) - 1, k - 6), tileentitychest1);
            //worldObj.setBlockMetadataWithNotify(i + 7, (j + l * 2) - 1, k - 6, 4);
            worldObj.setBlockState(new BlockPos(i + 7, (j + l * 2) - 1, k - 6), Blocks.cobblestone.getDefaultState());

            for (int k8 = 0; k8 < tileentitychest.getSizeInventory() - 9; k8++)
            {
                tileentitychest.setInventorySlotContents(k8, new ItemStack(Items.bone, 32, 0));
                tileentitychest1.setInventorySlotContents(k8, new ItemStack(Items.redstone, 32, 0));
            }

            for (int l8 = tileentitychest.getSizeInventory() - 9; l8 < tileentitychest.getSizeInventory(); l8++)
            {
                tileentitychest.setInventorySlotContents(l8, new ItemStack(Items.golden_helmet, 1, 0));
                tileentitychest1.setInventorySlotContents(l8, new ItemStack(Items.gold_ingot, 1, 0));
            }

            //worldObj.setBlock(i - 7, (j + l * 2) - 1, k - 5, 54);
            worldObj.setBlockState(new BlockPos(i - 7, (j + l * 2) - 1, k - 5), Blocks.chest.getDefaultState());
            TileEntityChest tileentitychest2 = new TileEntityChest();
            //worldObj.setBlockTileEntity(i - 7, (j + l * 2) - 1, k - 5, tileentitychest2);
            worldObj.setTileEntity(new BlockPos(i - 7, (j + l * 2) - 1, k - 5), tileentitychest2);
            //worldObj.setBlock(i - 7, (j + l * 2) - 1, k - 6, 54);
            worldObj.setBlockState(new BlockPos(i - 7, (j + l * 2) - 1, k - 6), Blocks.chest.getDefaultState());
            TileEntityChest tileentitychest3 = new TileEntityChest();
            ///worldObj.setBlockTileEntity(i - 7, (j + l * 2) - 1, k - 6, tileentitychest3);
            worldObj.setTileEntity(new BlockPos(i - 7, (j + l * 2) - 1, k - 6), tileentitychest3);

            for (int i9 = 0; i9 < tileentitychest2.getSizeInventory() - 9; i9++)
            {
                tileentitychest2.setInventorySlotContents(i9, new ItemStack(Items.bone, 32, 0));
                tileentitychest3.setInventorySlotContents(i9, new ItemStack(Items.redstone, 32, 0));
            }

            for (int j9 = tileentitychest2.getSizeInventory() - 9; j9 < tileentitychest2.getSizeInventory(); j9++)
            {
                tileentitychest2.setInventorySlotContents(j9, new ItemStack(Items.diamond_helmet, 1, 0));
                tileentitychest3.setInventorySlotContents(j9, new ItemStack(Items.diamond, 1, 0));
            }
        }
        else
        {
            MoreCreepsAndWeirdos.proxy.addChatMessage("Hotdog Heaven cannot be built here, choose another spot!");
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        if (ridingEntity == null)
        {
            if (rand.nextInt(5) == 0)
            {
                return "morecreeps:hotdog";
            }
            else
            {
                return null;
            }
        }

        if (angrydog)
        {
            return "mob.wolf.growl";
        }

        if (rand.nextInt(10) == 0)
        {
            return "mob.wolf.panting";
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
        return "morecreeps:hotdoghurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:hotdogdeath";
    }

    public void confetti()
    {
        
        World world = Minecraft.getMinecraft().theWorld;
        double d = -MathHelper.sin((((EntityPlayer)(entityplayer)).rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((((EntityPlayer)(entityplayer)).rotationYaw * (float)Math.PI) / 180F);
        CREEPSEntityTrophy creepsentitytrophy = new CREEPSEntityTrophy(world);
        creepsentitytrophy.setLocationAndAngles(((EntityPlayer)(entityplayer)).posX + d * 3D, ((EntityPlayer)(entityplayer)).posY - 2D, ((EntityPlayer)(entityplayer)).posZ + d1 * 3D, ((EntityPlayer)(entityplayer)).rotationYaw, 0.0F);
        world.spawnEntityInWorld(creepsentitytrophy);
    }

    public void onDeath(Entity entity)
    {
        if (tamed)
        {
            return;
        }
        else
        {
            super.setDead();
            dropItem(Items.porkchop, 1);
            return;
        }
    }
}
