package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.client.config.CREEPSConfig;
import fr.elias.morecreeps.client.particles.CREEPSFxBlood;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityGuineaPig extends EntityMob
{
	EntityPlayer entityplayer;
	Entity entitymain;
	World world;
    public boolean rideable;
    public int interest;
    private boolean primed;
    public boolean tamed;
    public int basehealth;
    private float distance;
    public Item armor;
    public String basetexture;
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
    public float modelsize;
    public int unmounttimer;
    public int skillattack;
    public int skilldefend;
    public int skillhealing;
    public int skillspeed;
    public int firenum;
    public int firepower;
    public int healtimer;
    public EntityLivingBase owner;
    public int criticalHitCooldown;
    public String name;
    public String texture;
    public double health;
    public double attackStrength;
    public double moveSpeed;
    
    static final String Names[] =
    {
        "Sugar", "Clover", "CoCo", "Sprinkles", "Mr. Rabies", "Stinky", "The Incredible Mr. CoCoPants", "Butchie", "Lassie", "Fuzzy",
        "Nicholas", "Natalie", "Pierre", "Priscilla", "Mrs. McGillicutty", "Dr. Tom Jones", "Peter the Rat", "Wiskers", "Penelope", "Sparky",
        "Tinkles", "Ricardo", "Jimothy", "Captain Underpants", "CoCo Van Gough", "Chuck Norris", "PeeWee", "Quasimodo", "ZSA ZSA", "Yum Yum",
        "Deputy Dawg", "Henrietta Pussycat", "Bob Dog", "King Friday", "Jennifer", "The Situation", "Prince Charming", "Sid", "Sunshine", "Bubbles",
        "Carl", "Snowy", "Dorf", "Chilly Willy", "Angry Bob", "George W. Bush", "Ted Lange from The Love Boat", "Notch", "Frank", "A Very Young Pig",
        "Blaster", "Darwin", "Ruggles", "Chang", "Spaz", "Fluffy", "Fuzzy", "Charrlotte", "Tootsie", "Mary",
        "Caroline", "Michelle", "Sandy", "Peach", "Scrappy", "Roxanne", "James the Pest", "Lucifer", "Shaniqua", "Wendy",
        "Zippy", "Prescott Pig", "Pimpin' Pig", "Big Daddy", "Little Butchie", "The Force", "The Handler", "Little Louie", "Satin", "Sparkly Puff",
        "Dr. Chews", "Pickles", "Longtooth", "Jeffry", "Pedro the Paunchy", "Wee Willy Wiskers", "Tidy Smith", "Johnson", "Big Joe", "Tiny Mackeral",
        "Wonderpig", "Wee Wonderpig", "The Polish Baron", "Inconceivable", "Double Danny Dimples", "Jackie Jones", "Pistol", "Tiny Talker", "Strum", "Disco the Pig",
        "Banjo", "Fingers", "Clean Streak", "Little Sweet", "Fern", "Youngblood", "Lazy Cottonball", "Foxy", "SlyFoxHound"
    };
    static final String pigTextures[] =
    {
        "morecreeps:textures/entity/ggpig1.png", "morecreeps:textures/entity/ggpig2.png", "morecreeps:textures/entity/ggpig3.png", "morecreeps:textures/entity/ggpig4.png", "morecreeps:textures/entity/ggpig5.png", "morecreeps:textures/entity/ggpig6.png", "/mob/creeps/ggpig7.png", "morecreeps:textures/entity/ggpig8.png", "morecreeps:textures/entity/ggpig9.png", "morecreeps:textures/entity/ggpiga.png"
    };
    public static final int leveldamage[] =
    {
        0, 200, 600, 1000, 1500, 2000, 2700, 3500, 4400, 5400,
        6600, 7900, 9300, 10800, 12400, 14100, 15800, 17600, 19500, 21500,
        25000, 30000
    };
    public static final String levelname[] =
    {
        "Guinea Pig", "A nothing pig", "An inexperienced pig", "Trainee", "Private", "Private First Class", "Corporal", "Sergeant", "Staff Sergeant", "Sergeant First Class",
        "Master Sergeant", "First Sergeant", "Sergeant Major", "Command Sergeant Major", "Second Lieutenant", "First Lieutenant", "Captain", "Major", "Lieutenant Colonel", "Colonel",
        "General of the Pig Army", "General of the Pig Army"
    };

    public CREEPSEntityGuineaPig(World world)
    {
        super(world);
        primed = false;
        basetexture = pigTextures[rand.nextInt(pigTextures.length)];
        texture = basetexture;
        setSize(0.6F, 0.6F);
        rideable = false;
        basehealth = rand.nextInt(5) + 5;
        health = basehealth;
        attackStrength = 1;
        tamed = false;
        name = "";
        pigstack = 0;
        level = 1;
        totaldamage = 0.0F;
        alt = 1;
        hotelbuilt = false;
        wanderstate = 0;
        baseSpeed = 0.6F;
        moveSpeed = baseSpeed;
        speedboost = 0;
        totalexperience = 0;
        fallDistance = -25F;
        modelsize = 1.0F;
        unmounttimer = 0;
        skillattack = 0;
        skilldefend = 0;
        skillhealing = 0;
        skillspeed = 0;
        firepower = 0;
        criticalHitCooldown = 5;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAILookIdle(this));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(health);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(attackStrength);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(attackStrength);
    }

    protected void updateAITick()
    {
        super.updateEntityActionState();

        if (!this.attackEntityAsMob(entitymain) && !hasPath() && tamed && ridingEntity == null && wanderstate != 2)
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
            this.attackEntity(entityplayer, (float) attackStrength);
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

                if (!(entity instanceof EntityPlayer) || wanderstate != 0)
                {
                    continue;
                }

                EntityPlayer entityplayer = (EntityPlayer)entity;

                if (entityplayer == null || obj != null && !(obj instanceof EntityPlayer))
                {
                    continue;
                }

                distance = getDistanceToEntity(entityplayer);

                if (distance < 6F)
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
        if (!(this.getAttackTarget() instanceof EntityPlayer) && !(this.getAttackTarget() instanceof CREEPSEntityGuineaPig) && !(this.getAttackTarget() instanceof CREEPSEntityHotdog) && ridingEntity == null)
        {
            if (onGround && tamed)
            {
                double d = entity.posX - posX;
                double d2 = entity.posZ - posZ;
                float f2 = MathHelper.sqrt_double(d * d + d2 * d2);
                motionX = (d / (double)f2) * 0.5D * 0.80000001192092896D + motionX * 0.20000000298023224D;
                motionZ = (d2 / (double)f2) * 0.5D * 0.80000001192092896D + motionZ * 0.20000000298023224D;
                motionY = 0.40000000596046448D;
            }
            else if (tamed && (double)f < 2.5D)
            {
                if (rand.nextInt(5) == 0)
                {
                    worldObj.playSoundAtEntity(this, "morecreeps:ggpigangry", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }

                double d1 = 1.0D;
                d1 += level * 5 + skillattack * 4;

                if (d1 < 5D)
                {
                    d1 = 5D;
                }

                super.attackEntityAsMob(entity);

                if ((double)rand.nextInt(100) > 100D - d1)
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

                    if (skillattack > 1 && rand.nextInt(100) > 100 - skillattack * 2 && criticalHitCooldown-- < 1)
                    {
                        totaldamage += 25F;
                        totalexperience += 25;

                        if (f1 < (float)((EntityLiving)entity).getHealth())
                        {
                            f1 = ((EntityLiving)entity).getHealth();
                        }

                        ((EntityLiving)entity).setHealth(0);
                        worldObj.playSoundAtEntity(entity, "morecreeps:guineapigcriticalhit", 1.0F, 1.0F);
                        criticalHitCooldown = 50 - skillattack * 8;
                    }

                    if ((float)((EntityLiving)entity).getHealth() - f1 <= 0.0F)
                    {
                        worldObj.playSoundAtEntity(entity, "morecreeps:ggpigangry", 1.0F, 1.0F);
                    }

                    ((EntityLiving)entity).attackEntityFrom(DamageSource.causeThrownDamage(this, (EntityLiving)entity), (int)f1);
                    totaldamage += (int)((double)f1 * 1.5D + (double)skillattack);
                    totalexperience += (int)((double)f1 * 1.5D + (double)skillattack);
                }

                if (totaldamage > (float)leveldamage[level] && level < 20)
                {
                    level++;
                    totaldamage = 0.0F;
                    boolean flag = false;

                    if (level == 5)
                    {
                        flag = true;
                        confetti();
                        entityplayer.addStat(MoreCreepsAndWeirdos.achievepiglevel5, 1);
                    }

                    if (level == 10)
                    {
                        flag = true;
                        confetti();
                        entityplayer.addStat(MoreCreepsAndWeirdos.achievepiglevel10, 1);
                    }

                    if (level == 20)
                    {
                        flag = true;
                        confetti();
                        entityplayer.addStat(MoreCreepsAndWeirdos.achievepiglevel20, 1);
                    }

                    if (flag)
                    {
                        worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                    }

                    MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("\247b").append(name).append(" \247fincreased to level \2476").append(String.valueOf(level)).append("!").toString());
                    worldObj.playSoundAtEntity(this, "morecreeps:ggpiglevelup", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    baseSpeed += 0.025F;
                    basehealth += rand.nextInt(4) + 1;
                    attackStrength++;
                }

                super.setAttackTarget((EntityLivingBase) entity);
            }
        }

        if ((double)f < 16D && (this.getAttackTarget() instanceof EntityPlayer))
        {
            this.setAttackTarget(null);
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        Entity entity = damagesource.getEntity();

        if (entity != this.getAttackTarget())
        {
            this.setAttackTarget((EntityLivingBase) entity);
        }

        return super.attackEntityFrom(DamageSource.causeMobDamage(this), i);
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

        if (ridingEntity != null && !(ridingEntity instanceof EntityPlayer) && !(ridingEntity instanceof CREEPSEntityGuineaPig) && !(ridingEntity instanceof CREEPSEntityHotdog))
        {
            mountEntity(null);
            unmounttimer = 20;
        }

        if (speedboost-- == 0 && name.length() > 0)
        {
            worldObj.playSoundAtEntity(this, "morecreeps:guineapigspeeddown", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("\247b").append(name).append("\2476 has run out of speedboost.").toString());

            if (wanderstate != 1)
            {
                moveSpeed = baseSpeed;
            }
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
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle(EnumParticleTypes.HEART, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
            }
        }

        if (handleWaterMovement())
        {
            motionY += 0.028799999505281448D;
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
        nbttagcompound.setBoolean("HotelBuilt", hotelbuilt);
        nbttagcompound.setInteger("AttackStrength", (int) attackStrength);
        nbttagcompound.setInteger("WanderState", wanderstate);
        nbttagcompound.setInteger("SpeedBoost", speedboost);
        nbttagcompound.setInteger("TotalExperience", totalexperience);
        nbttagcompound.setFloat("BaseSpeed", baseSpeed);
        nbttagcompound.setFloat("ModelSize", modelsize);
        nbttagcompound.setInteger("SkillAttack", skillattack);
        nbttagcompound.setInteger("SkillDefense", skilldefend);
        nbttagcompound.setInteger("SkillHealing", skillhealing);
        nbttagcompound.setInteger("SkillSpeed", skillspeed);
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
        hotelbuilt = nbttagcompound.getBoolean("HotelBuilt");
        attackStrength = nbttagcompound.getInteger("AttackStrength");
        wanderstate = nbttagcompound.getInteger("WanderState");
        speedboost = nbttagcompound.getInteger("SpeedBoost");
        totalexperience = nbttagcompound.getInteger("TotalExperience");
        baseSpeed = nbttagcompound.getFloat("BaseSpeed");
        modelsize = nbttagcompound.getFloat("ModelSize");
        skillattack = nbttagcompound.getInteger("SkillAttack");
        skilldefend = nbttagcompound.getInteger("SkillDefense");
        skillhealing = nbttagcompound.getInteger("SkillHealing");
        skillspeed = nbttagcompound.getInteger("SkillSpeed");
        texture = basetexture;

        if (wanderstate == 1)
        {
            moveSpeed = 0.0F;
        }
        else
        {
            moveSpeed = speedboost <= 0 ? baseSpeed : baseSpeed + 0.75F;
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
                creepsentitytombstone.hotelbuilt = hotelbuilt;
                creepsentitytombstone.attackStrength = (int) attackStrength;
                creepsentitytombstone.wanderstate = wanderstate;
                creepsentitytombstone.speedboost = speedboost;
                creepsentitytombstone.totalexperience = totalexperience;
                creepsentitytombstone.baseSpeed = baseSpeed;
                creepsentitytombstone.modelsize = modelsize;
                creepsentitytombstone.skillattack = skillattack;
                creepsentitytombstone.skilldefend = skilldefend;
                creepsentitytombstone.skillhealing = skillhealing;
                creepsentitytombstone.skillspeed = skillspeed;
                creepsentitytombstone.deathtype = "GuineaPig";
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
        int l = worldObj.getBlockLightOpacity(getPosition());
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        return i1 != Blocks.cobblestone && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0 && worldObj.checkBlockCollision(getBoundingBox()) && worldObj.canBlockSeeSky(getPosition()) && rand.nextInt(5) == 0 && l > 8;
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
            return (double)this.getYOffset();
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
            entityplayer.openGui(MoreCreepsAndWeirdos.instance, 3, world, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
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
                    worldObj.playSoundAtEntity(this, "morecreeps:ggpigmount", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
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

                    if ((Entity)obj instanceof CREEPSEntityGuineaPig)
                    {
                        ((CREEPSEntityGuineaPig)obj).unmounttimer = 20;
                    }

                    worldObj.playSoundAtEntity(this, "morecreeps:ggpigunmount", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }
            }
        }

        if (itemstack != null && health > 0)
        {
            if ((itemstack.getItem() == Items.book || itemstack.getItem() == Items.paper || itemstack.getItem() == MoreCreepsAndWeirdos.guineapigradio) && tamed)
            {
            	entityplayer.openGui(MoreCreepsAndWeirdos.instance, 4, world, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
            }

            if (itemstack.getItem() == Items.diamond && tamed)
            {
                if (ridingEntity != null)
                {
                	MoreCreepsAndWeirdos.proxy.addChatMessage("Put your Guinea Pig down before building the Guinea Pig Hotel!");
                }
                else if (!hotelbuilt)
                {
                    if (level >= 20)
                    {
                        
                            worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                            entityplayer.addStat(MoreCreepsAndWeirdos.achievepighotel, 1);
                        

                        worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 0.5F);
                        int i = MathHelper.floor_double(entityplayer.posX);
                        int i1 = MathHelper.floor_double(entityplayer.getBoundingBox().minY);
                        int j1 = MathHelper.floor_double(entityplayer.posZ);
                        createDisco(entityplayer, i + 2, i1, j1 + 2);
                    }
                    else
                    {
                    	MoreCreepsAndWeirdos.proxy.addChatMessage("Your Guinea Pig must be level 20 to build a Hotel.");
                    	MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("\247b").append(name).append(" is only level \247f").append(String.valueOf(level)).toString());
                    }
                }
                else
                {
                	MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("\247b").append(name).append("\247f has already built a Hotel.").toString());
                }
            }

            if (itemstack.getItem() == Item.getItemFromBlock(Blocks.red_flower) && tamed || itemstack.getItem() == Item.getItemFromBlock(Blocks.yellow_flower) && tamed)
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

            if (tamed && texture.length() == 23 + 14)
            {
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
                armor = itemstack.getItem();
                smoke();
                int k1 = 0;

                if (armor == Items.leather_boots || armor == Items.leather_chestplate || armor == Items.leather_helmet || armor == Items.leather_leggings)
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

                if (armor == Items.golden_boots || armor == Items.golden_chestplate || armor == Items.golden_helmet || armor == Items.golden_leggings)
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

                if (armor == Items.iron_boots || armor == Items.iron_chestplate || armor == Items.iron_helmet || armor == Items.iron_leggings)
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

                if (armor == Items.diamond_boots || armor == Items.diamond_chestplate || armor == Items.diamond_helmet || armor == Items.diamond_leggings)
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

            if (itemstack.getItem() == Items.wheat)
            {
                worldObj.playSoundAtEntity(this, "morecreeps:ggpigeat", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                used = true;
                interest += 15;
                health += 10;
                isDead = false;
                smoke();
            }

            if (itemstack.getItem() == Items.cookie)
            {
                worldObj.playSoundAtEntity(this, "morecreeps:ggpigeat", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                used = true;
                interest += 30;
                health += 15;
                isDead = false;
                smoke();
            }

            if (itemstack.getItem() == Items.apple)
            {
                worldObj.playSoundAtEntity(this, "morecreeps:ggpigeat", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                used = true;
                interest += 55;
                health += 25;
                isDead = false;
                smoke();
            }

            if (itemstack.getItem() == Items.golden_apple)
            {
                worldObj.playSoundAtEntity(this, "morecreeps:ggpigeat", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                used = true;
                interest += 111;
                health += 75;
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
                    entityplayer.addStat(MoreCreepsAndWeirdos.achievepigtaming, 1);
                

                if (used)
                {
                    smoke();
                }

                tamed = true;

                if (name.length() < 1)
                {
                    name = Names[rand.nextInt(Names.length)];
                }

                worldObj.playSoundAtEntity(this, "morecreeps:ggpigfull", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                interest = 100;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public void createDisco(EntityPlayer entityplayer, int i, int j, int k)
    {
        byte byte0 = 16;
        byte byte1 = 6;
        byte byte2 = 16;
        alt = 1;
        int l = 0;

        for (int i1 = 0; i1 < byte1 + 4; i1++)
        {
            for (int k3 = -2; k3 < byte0 + 2; k3++)
            {
                for (int i5 = -2; i5 < byte2 + 2; i5++)
                {
                	
                    if (worldObj.getBlockState(new BlockPos(i + k3, j + i1, k + i5)).getBlock() != Blocks.air)
                    {
                        l++;
                    }
                }
            }
        }

        if (l < 900)
        {
            used = true;
            hotelbuilt = true;
            worldObj.playSoundAtEntity(this, "morecreeps:guineapighotel", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            MoreCreepsAndWeirdos.proxy.addChatMessage("GUINEA PIG HOTEL BUILT!");

            for (int j1 = 0; j1 < byte1 + 4; j1++)
            {
                for (int l3 = -2; l3 < byte0 + 2; l3++)
                {
                    for (int j5 = -2; j5 < byte2 + 2; j5++)
                    {
                    	//Minecraft fucking changing things so much... 
                    	//It went from one line to four!!!!!
                        //old code (for reference)
                    	//worldObj.setBlockWithNotify(i + l3, j + j1, k + j5, 0);
                    	Block blk = Blocks.air;
                    	BlockPos pos0 = new BlockPos(i + l3, j + j1, k + j5);
                    	IBlockState state0 = blk.getDefaultState();
                    	world.setBlockState(pos0, state0);
                    }
                }
            }

            for (int k1 = 0; k1 < byte1; k1++)
            {
                for (int i4 = 0; i4 < byte2; i4++)
                {
                    alt *= -1;

                    for (int k5 = 0; k5 < byte0; k5++)
                    {
                        //worldObj.setBlockWithNotify(i + i4, j + k1, k + 0, 35);
                    	Block blk = Blocks.wool;
                    	BlockPos pos0 = new BlockPos(i + i4, j + k1, k + 0);
                    	IBlockState state0 = blk.getDefaultState();
                    	world.setBlockState(pos0, state0);
                        //worldObj.setBlockWithNotify(i + i4, j + k1, (k + byte0) - 1, 35);
                    	BlockPos pos1 = new BlockPos(i + i4, j + k1, (k + byte0) - 1);
                    	IBlockState state1 = blk.getDefaultState();
                    	world.setBlockState(pos1, state1);
                        //worldObj.setBlockAndMetadataWithNotify(i + 0, j + k1, k + k5, Block.cloth.blockID, 1);
                    	ItemStack orngwool = new ItemStack(Blocks.wool, 1, 1);
                    	Item itemowool = orngwool.getItem();
                    	Block blkowool = Block.getBlockFromItem(itemowool);
                    	BlockPos pos2 = new BlockPos(i + i4, j + k1, (k + byte0) - 1);
                    	IBlockState state2 = blkowool.getDefaultState();
                    	world.setBlockState(pos2, state2);
                        //worldObj.setBlockAndMetadataWithNotify(i + byte2, j + k1, k + k5, Block.cloth.blockID, 1);
                    	BlockPos pos3 = new BlockPos(i + byte2, j + k1, k + k5);
                    	world.setBlockState(pos3, state2);
                    	
                        alt *= -1;

                        if (alt > 0)
                        {
                            //worldObj.setBlockAndMetadataWithNotify(i + i4, j, k + k5, Block.cloth.blockID, 10);
                        	ItemStack prplwool = new ItemStack(Blocks.wool, 1, 10);
                        	Item itempwool = prplwool.getItem();
                        	Block blkpwool = Block.getBlockFromItem(itempwool);
                        	BlockPos pos4 = new BlockPos(i + i4, j, k + k5);
                        	IBlockState state3 = blkpwool.getDefaultState();
                        	world.setBlockState(pos4, state3);
                        	
                        }
                        else
                        {
                            //worldObj.setBlockAndMetadataWithNotify(i + i4, j, k + k5, Block.cloth.blockID, 11);
                        	ItemStack wooltype = new ItemStack(Blocks.wool, 1, 11);
                        	Item itemwool = wooltype.getItem();
                        	Block blkwool = Block.getBlockFromItem(itemwool);
                        	BlockPos pos4 = new BlockPos(i + i4, j, k + k5);
                        	IBlockState state3 = blkwool.getDefaultState();
                        	world.setBlockState(pos4, state3);
                        }

                        //worldObj.setBlockWithNotify(i + i4, j + byte1, k + k5, Block.glass.blockID);
                    	Block blk1 = Blocks.glass;
                    	BlockPos pos4 = new BlockPos(i + i4, j + byte1, k + k5);
                    	IBlockState state3 = blk1.getDefaultState();
                    	world.setBlockState(pos4, state3);
                    }
                }
            }

            //worldObj.setBlockWithNotify(i + 7, j, k - 1, 43);
            Block blk = Blocks.double_stone_slab;
        	BlockPos pos0 = new BlockPos(i + 7, j, k - 1);
        	IBlockState state0 = blk.getDefaultState();
        	world.setBlockState(pos0, state0);
            //worldObj.setBlockWithNotify(i + 10, j, k - 1, 43);
        	BlockPos pos1 = new BlockPos(i + 10, j, k - 1);
        	world.setBlockState(pos1, state0);
            //worldObj.setBlock(i + 7, j + 2, k - 1, Block.torchWood.blockID);
        	Block blk2 = Blocks.torch;
        	BlockPos pos2 = new BlockPos(i + 7, j + 2, k - 1);
        	IBlockState state1 = blk2.getDefaultState();
        	world.setBlockState(pos2, state1);
            //worldObj.setBlock(i + 10, j + 2, k - 1, Block.torchWood.blockID);
        	BlockPos pos3 = new BlockPos(i + 10, j + 2, k - 1);
        	world.setBlockState(pos3, state1);
            //worldObj.setBlockWithNotify(i + 8, j, k - 1, 44);
        	Block blk3 = Blocks.stone_slab;
        	BlockPos pos4 = new BlockPos(i + 7, j, k - 1);
        	IBlockState state2 = blk3.getDefaultState();
        	world.setBlockState(pos4, state2);
            //worldObj.setBlockWithNotify(i + 9, j, k - 1, 44);
        	BlockPos pos5 = new BlockPos(i + 9, j, k - 1);
        	world.setBlockState(pos5, state2);
            //worldObj.setBlockWithNotify(i + 8, j + 1, k, Block.doorWood.blockID);
        	
        	Block doorgetter[] = {
        			Blocks.acacia_door, Blocks.birch_door, Blocks.dark_oak_door, Blocks.jungle_door, Blocks.oak_door, Blocks.spruce_door
        	};
        	Block blk4 = doorgetter[rand.nextInt(doorgetter.length)];
        	BlockPos pos6 = new BlockPos(i + 7, j, k - 1);
        	IBlockState state3 = blk4.getDefaultState();
        	world.setBlockState(pos6, state3);
            //worldObj.setBlockMetadataWithNotify(i + 8, j + 1, k, 0);
        	Block blk1 = Blocks.air;
        	BlockPos pos7 = new BlockPos(i + 8, j + 1, k);
        	IBlockState state4 = blk1.getDefaultState();
        	world.setBlockState(pos7, state4);
            //worldObj.setBlockWithNotify(i + 8, j + 2, k, Block.doorWood.blockID);
        	Block blk5 = doorgetter[rand.nextInt(doorgetter.length)];
        	BlockPos pos8 = new BlockPos(i + 8, j + 2, k);
        	IBlockState state5 = blk5.getDefaultState();
        	world.setBlockState(pos8, state5);
            //worldObj.setBlockMetadataWithNotify(i + 8, j + 2, k, 8);
        	Block blk6 = Blocks.flowing_water;
        	BlockPos pos9 = new BlockPos(i + 8, j + 1, k);
        	IBlockState state6 = blk6.getDefaultState();
        	world.setBlockState(pos9, state6);
            //worldObj.setBlockWithNotify(i + 9, j + 1, k, Block.doorWood.blockID);
        	BlockPos pos10 = new BlockPos(i + 9, j + 1, k);
        	world.setBlockState(pos10, state5);
            //worldObj.setBlockMetadataWithNotify(i + 9, j + 1, k, 1);
        	Block blk7 = Blocks.stone;
        	BlockPos pos11 = new BlockPos(i + 9, j + 1, k);
        	IBlockState state7 = blk7.getDefaultState();
        	world.setBlockState(pos11, state7);
            //worldObj.setBlockWithNotify(i + 9, j + 2, k, Block.doorWood.blockID);
        	BlockPos pos12 = new BlockPos(i + 9, j + 2, k);
        	world.setBlockState(pos12, state5);
            //worldObj.setBlockMetadataWithNotify(i + 9, j + 2, k, 9);
        	Block blk8 = Blocks.water;
        	BlockPos pos13 = new BlockPos(i + 9, j + 2, k);
        	IBlockState state8 = blk8.getDefaultState();
        	world.setBlockState(pos13, state8);
            //worldObj.setBlockWithNotify(i + 8, j + 1, k + 5, Block.sandStone.blockID);
        	Block blk9 = Blocks.sandstone;
        	BlockPos pos14 = new BlockPos(i + 9, j + 2, k);
        	IBlockState state9 = blk9.getDefaultState();
        	world.setBlockState(pos14, state9);
            //worldObj.setBlockWithNotify(i + 9, j + 1, k + 5, Block.sandStone.blockID);
        	//Wow found out there is an easier way :(
        	world.setBlockState(new BlockPos(i + 9, j + 1, k + 5), Blocks.double_stone_slab.getDefaultState());
            //worldObj.setBlock(i + 8, j + 2, k + 5, Block.torchWood.blockID);
        	world.setBlockState(new BlockPos(i + 8, j + 2, k + 5), Blocks.torch.getDefaultState());
            //worldObj.setBlock(i + 9, j + 2, k + 5, Block.torchWood.blockID);
        	world.setBlockState(new BlockPos(i + 9, j + 2, k + 5), Blocks.torch.getDefaultState());

            for (int l1 = 4; l1 < byte2 - 4; l1 += 3)
            {
                //worldObj.setBlock(i + 1, j + 4, k + l1, Block.torchWood.blockID);
            	world.setBlockState(new BlockPos(i + 1, j + 4, k + l1), Blocks.torch.getDefaultState());
                //worldObj.setBlock((i + byte2) - 1, j + 4, k + l1, Block.torchWood.blockID);
            	world.setBlockState(new BlockPos((i + byte2) - 1, j + 4, k + l1), Blocks.torch.getDefaultState());
                //worldObj.setBlock(i + l1 + 2, j + 4, (k + byte0) - 2, Block.torchWood.blockID);
            	world.setBlockState(new BlockPos(i + l1 + 2, j + 4, (k + byte0) - 2), Blocks.torch.getDefaultState());
            }

            for (int i2 = 0; i2 < 9; i2++)
            {
                for (int j4 = 1; j4 < byte2; j4++)
                {
                    //worldObj.setBlockWithNotify(i + j4, j + 1, k + i2 + 6, Block.dirt.blockID);
                	world.setBlockState(new BlockPos(i + j4, j + 1, k + i2 + 6), Blocks.dirt.getDefaultState());
                }
            }

            for (int j2 = 0; j2 < 5; j2++)
            {
                for (int k4 = 1; k4 < byte2; k4++)
                {
                    //worldObj.setBlockWithNotify(i + k4, j + 2, k + j2 + 10, Block.dirt.blockID);
                	world.setBlockState(new BlockPos(i + k4, j + 2, k + j2 + 10), Blocks.dirt.getDefaultState());
                }
            }

            for (int k2 = 3; k2 < byte2 - 3; k2++)
            {
                //worldObj.setBlockWithNotify(i + k2, j + 1, k + 6, 0);
            	world.setBlockState(new BlockPos(i + k2, j + 1, k + 6), Blocks.air.getDefaultState());
            }

            for (int l2 = 7; l2 < byte2 - 4; l2++)
            {
                //worldObj.setBlockWithNotify(i + l2, j + 1, k + 7, 0);
            	world.setBlockState(new BlockPos(i + l2, j + 1, k + 7), Blocks.air.getDefaultState());
            }

            for (int i3 = 7; i3 < 12; i3++)
            {
                //worldObj.setBlockWithNotify(i + 1, j + 2, k + i3, 37);
            	world.setBlockState(new BlockPos(i + 1, j + 2, k + i3), Blocks.yellow_flower.getDefaultState());
                //worldObj.setBlockWithNotify(i + 2, j + 2, k + i3, 37);
            	world.setBlockState(new BlockPos(i + 2, j + 2, k + i3), Blocks.yellow_flower.getDefaultState());
                //worldObj.setBlockWithNotify(i + 14, j + 2, k + i3, 38);
            	world.setBlockState(new BlockPos(i + 14, j + 2, k + i3), Blocks.red_flower.getDefaultState());
                //worldObj.setBlockWithNotify(i + 15, j + 2, k + i3, 38);
            	world.setBlockState(new BlockPos(i + 15, j + 2, k + i3), Blocks.red_flower.getDefaultState());
            }

            for (int j3 = 0; j3 < 3; j3++)
            {
                for (int l4 = 6; l4 < byte2 - 3; l4++)
                {
                    ///worldObj.setBlockWithNotify(i + l4, j + 2, k + j3 + 11, 8);
                	world.setBlockState(new BlockPos(i + 9, j + 1, k + 5), Blocks.double_stone_slab.getDefaultState());
                    ///worldObj.setBlockWithNotify(i + l4, j + 1, k + j3 + 11, 8);
                	world.setBlockState(new BlockPos(i + 9, j + 1, k + 5), Blocks.double_stone_slab.getDefaultState());
                }
            }

            //worldObj.setBlock(i + 5, j + 2, k + 12, 8);
            world.setBlockState(new BlockPos(i + 5, j + 2, k + 12), Blocks.flowing_water.getDefaultState());
            //worldObj.setBlock(i + 5, j + 2, k + 13, 8);
            world.setBlockState(new BlockPos(i + 5, j + 2, k + 13), Blocks.flowing_water.getDefaultState());
            //worldObj.setBlockWithNotify(i + 9, j + 1, k + 8, 2);
            world.setBlockState(new BlockPos(i + 9, j + 1, k + 8), Blocks.grass.getDefaultState());
            //worldObj.setBlockWithNotify(i + 5, j + 3, k, 20);
            world.setBlockState(new BlockPos(i + 5, j + 3, k), Blocks.glass.getDefaultState());
            //worldObj.setBlockWithNotify(i + 5, j + 2, k, 20);
            world.setBlockState(new BlockPos(i + 5, j + 2, k), Blocks.glass.getDefaultState());
            //worldObj.setBlockWithNotify(i + 4, j + 3, k, 20);
            world.setBlockState(new BlockPos(i + 4, j + 3, k), Blocks.glass.getDefaultState());
            //worldObj.setBlockWithNotify(i + 4, j + 2, k, 20);
            world.setBlockState(new BlockPos(i + 4, j + 2, k), Blocks.glass.getDefaultState());
            //worldObj.setBlockWithNotify(i + 13, j + 3, k, 20);
            world.setBlockState(new BlockPos(i + 13, j + 3, k), Blocks.glass.getDefaultState());
            //worldObj.setBlockWithNotify(i + 13, j + 2, k, 20);
            world.setBlockState(new BlockPos(i + 13, j + 2, k), Blocks.glass.getDefaultState());
            //worldObj.setBlockWithNotify(i + 12, j + 3, k, 20);
            world.setBlockState(new BlockPos(i + 12, j + 3, k), Blocks.glass.getDefaultState());
            //worldObj.setBlockWithNotify(i + 12, j + 2, k, 20);
            world.setBlockState(new BlockPos(i + 12, j + 2, k), Blocks.glass.getDefaultState());
            //worldObj.setBlock(i + 1, j + 1, k + 3, 54);
            world.setBlockState(new BlockPos(i + 1, j + 1, k + 3), Blocks.chest.getDefaultState());
            TileEntityChest tileentitychest = new TileEntityChest();
            //setBlockTileEntity is about the same as setBlockState except is a tile entity, like a chest's
            //worldObj.setBlockTileEntity(i + 1, j + 1, k + 3, tileentitychest);
            world.setTileEntity(new BlockPos(i + 1, j + 1, k + 3), tileentitychest);
            //worldObj.setBlock(i + 1, j + 1, k + 4, 54);
            world.setBlockState(new BlockPos(i + 9, j + 1, k + 5), Blocks.double_stone_slab.getDefaultState());
            TileEntityChest tileentitychest1 = new TileEntityChest();
            //worldObj.setBlockTileEntity(i + 1, j + 1, k + 4, tileentitychest1);
            world.setTileEntity(new BlockPos(i + 1, j + 1, k + 4), tileentitychest1);

            for (int l5 = 0; l5 < tileentitychest.getSizeInventory(); l5++)
            {
                if (rand.nextInt(10) == 0)
                {
                    tileentitychest.setInventorySlotContents(l5, new ItemStack(Items.golden_apple, 1, 0));
                    tileentitychest1.setInventorySlotContents(l5, new ItemStack(Items.golden_apple, 1, 0));
                }
                else
                {
                    tileentitychest.setInventorySlotContents(l5, new ItemStack(Items.apple, 1, 0));
                    tileentitychest1.setInventorySlotContents(l5, new ItemStack(Items.wheat, rand.nextInt(16), 0));
                }
            }

            //worldObj.setBlock((i + byte2) - 1, j + 1, k + 3, 54);
            world.setBlockState(new BlockPos((i + byte2) - 1, j + 1, k + 3), Blocks.glass.getDefaultState());
            TileEntityChest tileentitychest2 = new TileEntityChest();
            //worldObj.setBlockTileEntity((i + byte2) - 1, j + 1, k + 3, tileentitychest2);
            world.setTileEntity(new BlockPos((i + byte2) - 1, j + 1, k + 3),tileentitychest2);
            //worldObj.setBlock((i + byte2) - 1, j + 1, k + 4, 54);
            world.setBlockState(new BlockPos((i + byte2) - 1, j + 1, k + 4), Blocks.glass.getDefaultState());
            TileEntityChest tileentitychest3 = new TileEntityChest();
            //worldObj.setBlockTileEntity((i + byte2) - 1, j + 1, k + 4, tileentitychest3);
            world.setTileEntity(new BlockPos((i + byte2) - 1, j + 1, k + 4),tileentitychest3);

            for (int i6 = 0; i6 < tileentitychest1.getSizeInventory(); i6++)
            {
                if (rand.nextInt(15) == 0)
                {
                    tileentitychest2.setInventorySlotContents(i6, new ItemStack(Items.golden_apple, 1, 0));
                    tileentitychest3.setInventorySlotContents(i6, new ItemStack(Items.apple, 1, 0));
                }
                else
                {
                    tileentitychest2.setInventorySlotContents(i6, new ItemStack(Items.apple, 1, 0));
                    tileentitychest3.setInventorySlotContents(i6, new ItemStack(Items.wheat, rand.nextInt(16), 0));
                }
            }
        }
        else
        {
        	MoreCreepsAndWeirdos.proxy.addChatMessage("Too many obstructions, choose another spot!");
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

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        if (ridingEntity == null)
        {
            if (rand.nextInt(5) == 0)
            {
                return "morecreeps:ggpig";
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
        return "morecreeps:ggpigangry";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:ggpigdeath";
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

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return !tamed;
    }
}
