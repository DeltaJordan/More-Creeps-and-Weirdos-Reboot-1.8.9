package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class CREEPSEntityEvilScientist extends EntityMob
{
    //public int basehealth;
    public int weapon;
    public boolean used;
    public int interest;
    public String ss;
    public float distance;
    public String basetexture;
    public int experimenttimer;
    public boolean experimentstart;
    public int stage;
    public int towerX;
    public int towerY;
    public int towerZ;
    public int towerHeight;
    public boolean water;
    public Block area;
    public int numexperiments;
    public boolean trulyevil;
    public boolean towerBuilt;
    public float modelsize;
    public String texture;

    public CREEPSEntityEvilScientist(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/evilscientist.png";
        basetexture = texture;
        experimenttimer = rand.nextInt(100) + 100;
        experimentstart = false;
        stage = 0;
        water = false;
        trulyevil = false;
        towerBuilt = false;
        numexperiments = rand.nextInt(3) + 1;
        isImmuneToFire = true;
        modelsize = 1.0F;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(4, new CREEPSEntityEvilScientist.AITargetingSystem());
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.5D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        if(trulyevil)
        {
            this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        }
    }
    
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1D);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("ExperimentTimer", experimenttimer);
        nbttagcompound.setBoolean("ExperimentStart", experimentstart);
        nbttagcompound.setInteger("Stage", stage);
        nbttagcompound.setBoolean("Water", water);
        nbttagcompound.setInteger("NumExperiments", numexperiments);
        nbttagcompound.setInteger("TowerX", towerX);
        nbttagcompound.setInteger("TowerY", towerY);
        nbttagcompound.setInteger("TowerZ", towerZ);
        nbttagcompound.setInteger("TowerHeight", towerHeight);
        nbttagcompound.setBoolean("TrulyEvil", trulyevil);
        nbttagcompound.setBoolean("TowerBuilt", towerBuilt);
        nbttagcompound.setFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        experimenttimer = nbttagcompound.getInteger("ExperimentTimer");
        experimentstart = nbttagcompound.getBoolean("ExperimentStart");
        stage = nbttagcompound.getInteger("Stage");
        water = nbttagcompound.getBoolean("Water");
        numexperiments = nbttagcompound.getInteger("NumExperiments");
        towerX = nbttagcompound.getInteger("TowerX");
        towerY = nbttagcompound.getInteger("TowerY");
        towerZ = nbttagcompound.getInteger("TowerZ");
        towerHeight = nbttagcompound.getInteger("TowerHeight");
        trulyevil = nbttagcompound.getBoolean("TrulyEvil");
        towerBuilt = nbttagcompound.getBoolean("TowerBuilt");
        modelsize = nbttagcompound.getFloat("ModelSize");

        if (trulyevil)
        {
            texture = "morecreeps:textures/entity/evilscientistblown.png";
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
    	if(trulyevil)
    	{
    		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
    	}else{
    		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
    	}
        fallDistance = 0.0F;

        if (stage == 3 && posY + 3D < (double)(towerY + towerHeight))
        {
            stage = 2;
        }

        if (stage == 0)
        {
            if (experimenttimer > 0 && !experimentstart)
            {
                experimenttimer--;
            }

            if (experimenttimer == 0)
            {
                experimentstart = true;
                stage = 1;
                experimenttimer = rand.nextInt(5000) + 100;
            }
        }

        if (stage == 1 && onGround && experimentstart && posY > 63D)
        {
            towerX = MathHelper.floor_double(posX) + 2;
            towerY = MathHelper.floor_double(posY);
            towerZ = MathHelper.floor_double(posZ) + 2;
            towerHeight = rand.nextInt(20) + 10;
            area = Blocks.air;

            /*for (int i = 0; i < towerHeight; i++)
            {
                for (int i2 = 0; i2 < 3; i2++)
                {
                    for (int l3 = 0; l3 < 3; l3++)
                    {
                        area += worldObj.getBlockState(new BlockPos(towerX + l3, towerY + i, towerZ + i2 + 1)).getBlock();
                        area += worldObj.getBlockState(new BlockPos(towerX + i2 + 1, towerY + i, towerZ + l3)).getBlock();
                    }
                }
            }*/

            for (int i = 0; i < towerHeight; i++)
            {
                for (int i2 = 0; i2 < 3; i2++)
                {
                    for (int l3 = 0; l3 < 3; l3++)
                    {
                        area = worldObj.getBlockState(new BlockPos(towerX + l3, towerY + i, towerZ + i2 + 1)).getBlock();
                        area = worldObj.getBlockState(new BlockPos(towerX + i2 + 1, towerY + i, towerZ + l3)).getBlock();
                    }
                }
            }
            
            if (posY > 63D && area == Blocks.air && worldObj.getBlockState(new BlockPos(towerX + 2, towerY - 1, towerZ + 2)).getBlock() != Blocks.air && worldObj.getBlockState(new BlockPos(towerX + 2, towerY - 1, towerZ + 2)).getBlock() != Blocks.water && worldObj.getBlockState(new BlockPos(towerX + 2, towerY - 1, towerZ + 2)).getBlock() != Blocks.flowing_water)
            {
                towerBuilt = true;

                for (int j = 0; j < towerHeight; j++)
                {
                    for (int j2 = 0; j2 < 3; j2++)
                    {
                        for (int i4 = 0; i4 < 3; i4++)
                        {
                        	//previously called "byte byte0"
                            Block byte0 = Blocks.cobblestone;

                            if (rand.nextInt(5) == 0)
                            {
                                byte0 = Blocks.mossy_cobblestone;
                            }

                            worldObj.setBlockState(new BlockPos(towerX + i4, towerY + j, towerZ + j2 + 1), byte0.getDefaultState());
                            byte0 = Blocks.cobblestone;

                            if (rand.nextInt(5) == 0)
                            {
                                byte0 = Blocks.mossy_cobblestone;
                            }

                            worldObj.setBlockState(new BlockPos(towerX + j2 + 1, towerY + j, towerZ + i4), byte0.getDefaultState());
                        }
                    }
                }

                worldObj.setBlockState(new BlockPos(towerX + 2, towerY + towerHeight, towerZ + 2), Blocks.crafting_table.getDefaultState());

                for (int k = 0; k < towerHeight; k++)
                {
                    worldObj.setBlockState(new BlockPos(towerX, towerY + k, towerZ), Blocks.ladder.getDefaultState());
                    //TODO : Fix this !
                    /*Blocks.ladder.onBlockPlaced(worldObj, new BlockPos(towerX, towerY + k, towerZ), 65);
                    onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer)*/
                    Blocks.ladder.onBlockPlaced(worldObj, new BlockPos(towerX, towerY + k, towerZ), EnumFacing.EAST, 0, 0, 0, 0, this);

                }

                stage = 2;
            }
            else
            {
                stage = 0;
                experimenttimer = rand.nextInt(100) + 50;
                experimentstart = false;
            }
        }

        if (stage == 2)
        {
            if (posX < (double)towerX)
            {
                motionX = 0.20000000298023224D;
            }

            if (posZ < (double)towerZ)
            {
                motionZ = 0.20000000298023224D;
            }

            if (Math.abs(posX - (double)towerX) < 0.40000000596046448D && Math.abs(posZ - (double)towerZ) < 0.40000000596046448D)
            {
                motionX = 0.0D;
                motionZ = 0.0D;
                motionY = 0.30000001192092896D;
                int l = MathHelper.floor_double(posX);
                int k2 = MathHelper.floor_double(getEntityBoundingBox().minY);
                int j4 = MathHelper.floor_double(posZ);
                worldObj.setBlockToAir(new BlockPos(l, k2 + 2, j4));

                if (posY > (double)(towerY + towerHeight))
                {
                    motionY = 0.0D;
                    posZ++;
                    posX++;
                    stage = 3;
                    experimenttimer = rand.nextInt(1000) + 500;
                    experimentstart = false;
                }
            }
        }

        if (stage == 3)
        {
            posY = towerY + towerHeight;
            posX = towerX + 2;
            posZ = towerZ + 2;
            setPosition(towerX + 2, towerY + towerHeight, towerZ + 2);
            motionX = 0.0D;
            motionY = 0.0D;
            motionZ = 0.0D;
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);

            if (experimenttimer > 0)
            {
                experimenttimer--;
            }

            if (rand.nextInt(200) == 0)
            {
                int i1 = MathHelper.floor_double(posX);
                int l2 = MathHelper.floor_double(getEntityBoundingBox().minY);
                int k4 = MathHelper.floor_double(posZ);
                worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, i1, l2 + 3, k4));
            }

            if (rand.nextInt(150) == 0 && !water)
            {
                worldObj.setBlockState(new BlockPos(towerX + 2, towerY + towerHeight, towerZ + 1), Blocks.flowing_water.getDefaultState());
                worldObj.setBlockState(new BlockPos(towerX + 3, towerY + towerHeight, towerZ + 2), Blocks.flowing_water.getDefaultState());
                worldObj.setBlockState(new BlockPos(towerX + 1, towerY + towerHeight, towerZ + 2), Blocks.flowing_water.getDefaultState());
                worldObj.setBlockState(new BlockPos(towerX + 2, towerY + towerHeight, towerZ + 3), Blocks.flowing_water.getDefaultState());
                water = true;
            }

            if (rand.nextInt(8) == 0)
            {
                CREEPSEntityEvilLight creepsentityevillight = new CREEPSEntityEvilLight(worldObj);
                creepsentityevillight.setLocationAndAngles(towerX, towerY + towerHeight, towerZ, rotationYaw, 0.0F);
                creepsentityevillight.motionX = rand.nextFloat() * 2.0F - 1.0F;
                creepsentityevillight.motionZ = rand.nextFloat() * 2.0F - 1.0F;
                if(!worldObj.isRemote)
                worldObj.spawnEntityInWorld(creepsentityevillight);
            }

            if (rand.nextInt(10) == 0)
            {
                for (int j1 = 0; j1 < 4; j1++)
                {
                    for (int i3 = 0; i3 < 10; i3++)
                    {
                        double d = rand.nextGaussian() * 0.02D;
                        double d2 = rand.nextGaussian() * 0.02D;
                        double d4 = rand.nextGaussian() * 0.02D;
                        worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, ((double)(2.0F + (float)towerX) + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, (double)(1.0F + (float)towerY + (float)towerHeight) + (double)(rand.nextFloat() * height) + 2D, (2D + ((double)towerZ + (double)(rand.nextFloat() * width * 2.0F))) - (double)width, d, d2, d4);
                    }
                }
            }

            if (!experimentstart)
            {
                for (int k1 = 0; k1 < 4; k1++)
                {
                    for (int j3 = 0; j3 < 10; j3++)
                    {
                        double d1 = rand.nextGaussian() * 0.02D;
                        double d3 = rand.nextGaussian() * 0.02D;
                        double d5 = rand.nextGaussian() * 0.02D;
                        worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, ((double)towerX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, (double)(towerY + towerHeight) + (double)(rand.nextFloat() * height) + 2D, ((double)towerZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d1, d3, d5);
                    }
                }

                CREEPSEntityEvilLight creepsentityevillight1 = new CREEPSEntityEvilLight(worldObj);
                creepsentityevillight1.setLocationAndAngles(towerX, towerY + towerHeight + 10, towerZ, rotationYaw, 0.0F);
                worldObj.spawnEntityInWorld(creepsentityevillight1);
                experimentstart = true;
            }

            if (experimenttimer == 0)
            {
                worldObj.createExplosion(null, towerX + 2, towerY + towerHeight + 4, towerZ + 2, 2.0F, true);
                experimentstart = false;
                stage = 4;
            }
        }

        if (stage == 4)
        {
            int l1 = MathHelper.floor_double(posX);
            int k3 = MathHelper.floor_double(getEntityBoundingBox().minY);
            int l4 = MathHelper.floor_double(posZ);

            for (int i5 = 0; i5 < rand.nextInt(5) + 1; i5++)
            {
                worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, (l1 + rand.nextInt(4)) - 2, k3 + 6, (l4 + rand.nextInt(4)) - 2));
            }

            worldObj.playSoundAtEntity(this, "morecreeps:evilexperiment", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            texture = "morecreeps:textures/entity/evilscientistblown.png";
            trulyevil = true;
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(90D);

            for (int j5 = 0; j5 < rand.nextInt(4) + 1; j5++)
            {
                int k5 = rand.nextInt(4);

                if (k5 == 0)
                {
                    for (int l5 = 0; l5 < rand.nextInt(8) + 2; l5++)
                    {
                        CREEPSEntityEvilSnowman creepsentityevilsnowman = new CREEPSEntityEvilSnowman(worldObj);
                        creepsentityevilsnowman.setLocationAndAngles(towerX + rand.nextInt(3), towerY + towerHeight + 1, towerZ + rand.nextInt(3), rotationYaw, 0.0F);
                        creepsentityevilsnowman.motionX = rand.nextFloat() * 0.3F;
                        creepsentityevilsnowman.motionY = rand.nextFloat() * 0.4F;
                        creepsentityevilsnowman.motionZ = rand.nextFloat() * 0.4F;
                        creepsentityevilsnowman.fallDistance = -35F;
                        if(!worldObj.isRemote)
                        worldObj.spawnEntityInWorld(creepsentityevilsnowman);
                    }
                }

                if (k5 == 1)
                {
                    CREEPSEntityEvilCreature creepsentityevilcreature = new CREEPSEntityEvilCreature(worldObj);
                    creepsentityevilcreature.setLocationAndAngles(towerX, towerY + towerHeight + 1, towerZ, rotationYaw, 0.0F);
                    creepsentityevilcreature.fallDistance = -35F;
                    if(!worldObj.isRemote)
                    worldObj.spawnEntityInWorld(creepsentityevilcreature);
                }

                if (k5 == 2)
                {
                    CREEPSEntityEvilPig creepsentityevilpig = new CREEPSEntityEvilPig(worldObj);
                    creepsentityevilpig.setLocationAndAngles(towerX, towerY + towerHeight + 1, towerZ, rotationYaw, 0.0F);
                    creepsentityevilpig.fallDistance = -35F;
                    if(!worldObj.isRemote)
                    worldObj.spawnEntityInWorld(creepsentityevilpig);
                }

                if (k5 == 3)
                {
                    for (int i6 = 0; i6 < rand.nextInt(4) + 1; i6++)
                    {
                        CREEPSEntityEvilChicken creepsentityevilchicken = new CREEPSEntityEvilChicken(worldObj);
                        creepsentityevilchicken.setLocationAndAngles(towerX + rand.nextInt(3), towerY + towerHeight + 1, towerZ + rand.nextInt(3), rotationYaw, 0.0F);
                        creepsentityevilchicken.motionX = rand.nextFloat() * 0.3F;
                        creepsentityevilchicken.motionY = rand.nextFloat() * 0.4F;
                        creepsentityevilchicken.motionZ = rand.nextFloat() * 0.4F;
                        creepsentityevilchicken.fallDistance = -35F;
                        if(!worldObj.isRemote)
                        worldObj.spawnEntityInWorld(creepsentityevilchicken);
                    }
                }

                if (k5 != 4)
                {
                    continue;
                }

                for (int j6 = 0; j6 < rand.nextInt(8) + 2; j6++)
                {
                    CREEPSEntityEvilSnowman creepsentityevilsnowman1 = new CREEPSEntityEvilSnowman(worldObj);
                    creepsentityevilsnowman1.setLocationAndAngles(towerX + rand.nextInt(3), towerY + towerHeight + 1, towerZ + rand.nextInt(3), rotationYaw, 0.0F);
                    creepsentityevilsnowman1.motionX = rand.nextFloat() * 0.3F;
                    creepsentityevilsnowman1.motionY = rand.nextFloat() * 0.4F;
                    creepsentityevilsnowman1.motionZ = rand.nextFloat() * 0.4F;
                    creepsentityevilsnowman1.fallDistance = -35F;
                    if(!worldObj.isRemote)
                    worldObj.spawnEntityInWorld(creepsentityevilsnowman1);
                }
            }

            numexperiments--;

            if (numexperiments <= 0)
            {
                numexperiments = rand.nextInt(4) + 1;
                stage = 5;
            }
            else
            {
                stage = 3;
                experimenttimer = rand.nextInt(500) + 500;
                experimentstart = false;
            }
        }

        if (stage == 5)
        {
            tearDownTower();
            stage = 6;
        }

        if (stage == 6)
        {
            experimenttimer = rand.nextInt(5000) + 100;
            stage = 0;
        }

        super.onLivingUpdate();
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer entityplayer)
    {
        return true;
    }

    public void tearDownTower()
    {
        if (towerBuilt)
        {
            for (int i = 0; i < towerHeight + 1; i++)
            {
                worldObj.setBlockToAir(new BlockPos(towerX, towerY + i, towerZ));

                for (int j = 0; j < 3; j++)
                {
                    for (int k = 0; k < 3; k++)
                    {
                        for (int l = 0; l < 4; l++)
                        {
                            for (int i1 = 0; i1 < 10; i1++)
                            {
                                double d = rand.nextGaussian() * 0.02D;
                                double d1 = rand.nextGaussian() * 0.02D;
                                double d2 = rand.nextGaussian() * 0.02D;
                                worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, ((double)(2.0F + (float)towerX) + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, (double)(1.0F + (float)towerY + (float)i) + (double)(rand.nextFloat() * height) + 2D, (2D + ((double)towerZ + (double)(rand.nextFloat() * width * 2.0F))) - (double)width, d, d1, d2);
                            }
                        }

                        worldObj.setBlockToAir(new BlockPos(towerX + k, towerY + i, towerZ + j + 1));
                        worldObj.setBlockToAir(new BlockPos(towerX + j + 1, towerY + i, towerZ + k));
                    }
                }
            }
        }
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity entity, float f)
    {
        if (trulyevil)
        {
            double d = entity.posX - posX;
            double d1 = entity.posZ - posZ;
            float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
            motionX = (d / (double)f1) * 0.40000000000000002D * 0.20000000192092895D + motionX * 0.18000000098023225D;
            motionZ = (d1 / (double)f1) * 0.40000000000000002D * 0.14000000192092896D + motionZ * 0.18000000098023225D;

            if (onGround)
            {
                motionY = 0.44000000196046446D;
            }

            /*if ((double)f < 2D - (1.0D - (double)modelsize) && entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY && entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY)
            {
                attackTime = 10;
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), attackStrength);
            }*/
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        Entity entity = damagesource.getEntity();

        if (entity != null)
        {
            double d = -MathHelper.sin((entity.rotationYaw * (float)Math.PI) / 180F);
            double d1 = MathHelper.cos((entity.rotationYaw * (float)Math.PI) / 180F);
            motionX = d * 4D;
            motionZ = d1 * 4D;
        }

        if (super.attackEntityFrom(DamageSource.causeMobDamage(this), i))
        {
            if (riddenByEntity == entity || ridingEntity == entity)
            {
                return true;
            }

            if (entity != this && worldObj.getDifficulty() != EnumDifficulty.PEACEFUL)
            {
                setRevengeTarget((EntityLivingBase) entity);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        if (trulyevil)
        {
            EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 16D);

            if (entityplayer != null && canEntityBeSeen(entityplayer))
            {
                return entityplayer;
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

    class AITargetingSystem extends EntityAIBase
    {
    	public CREEPSEntityEvilScientist evilscientist = CREEPSEntityEvilScientist.this;
    	public int attackTime;
    	public AITargetingSystem() {}
    	
		@Override
		public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.evilscientist.getAttackTarget();
            return trulyevil && entitylivingbase != null && entitylivingbase.isEntityAlive();
		}
        public void updateTask()
        {
        	try{
        	--attackTime;
            EntityLivingBase entitylivingbase = this.evilscientist.getAttackTarget();
            double d0 = this.evilscientist.getDistanceSqToEntity(entitylivingbase);
            evilscientist.attackEntity(entitylivingbase, (float)d0);
            /*if (d0 < 4.0D)
            {
                if (this.attackTime <= 0)
                {
                    this.attackTime = 20;
                    this.evilcreature.attackEntityAsMob(entitylivingbase);
                }
                
                this.evilcreature.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            }
            else if (d0 < 256.0D)
            {
                // ATTACK ENTITY GOES HERE
                this.evilcreature.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
            }
            else*/
            //{
                this.evilscientist.getNavigator().clearPathEntity();
                this.evilscientist.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 0.5D);
            //}
        	}
        	catch (NullPointerException ex)
			{
			ex.printStackTrace();
			}
        }
    }
    
    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        //int l = worldObj.getFullBlockLightValue(i, j, k);
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        int j1 = worldObj.countEntities(CREEPSEntityEvilScientist.class);
        return i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.stone_slab && i1 != Blocks.double_stone_slab && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).size() == 0 && worldObj.canSeeSky(new BlockPos(i, j, k)) && rand.nextInt(45) == 0 && /*l > 10 &&*/ j1 < 3;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 1;
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
        if (stage < 3)
        {
            return "morecreeps:evillaugh";
        }
        else
        {
            return "morecreeps:evilexplosion";
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:evilhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:evilexperiment";
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
        tearDownTower();

        if(!worldObj.isRemote)
        {
	        if (rand.nextInt(5) == 0)
	        {
	            dropItem(Items.cooked_porkchop, rand.nextInt(3) + 1);
	        }
	        else
	        {
	            dropItem(Item.getItemFromBlock(Blocks.sand), rand.nextInt(3) + 1);
	        }
        }
        super.onDeath(damagesource);
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        tearDownTower();
        super.setDead();
    }
}
