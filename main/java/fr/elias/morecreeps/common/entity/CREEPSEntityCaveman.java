package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
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
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityCaveman extends EntityMob
{
    protected double attackrange;
    protected int attack;

    public int eaten;
    public boolean hungry;
    public int hungrytime;
    public float hammerswing;
    public int frozen;
    public float modelsize;
    public float fat;
    public boolean cavegirl;
    public boolean evil;
    public float modelsizebase;
    public int wanderstate;
    public int houseX;
    public int houseY;
    public int houseZ;
    public int housechunk;
    public int area;
    public int talkdelay;

    public String texture;
	public int attackTime;

    public CREEPSEntityCaveman(World world)
    {
        super(world);
        scoreValue = 4;
        attack = 1;
        attackrange = 16D;
        hammerswing = 0.0F;
        hungry = false;
        hungrytime = rand.nextInt(100) + 10;

        if (rand.nextInt(100) > 50)
        {
            cavegirl = true;
        }

        evil = false;
        wanderstate = 0;
        frozen = 5;
        fat = rand.nextFloat() * 1.0F - rand.nextFloat() * 0.55F;
        modelsize = (1.25F + rand.nextFloat() * 1.0F) - rand.nextFloat() * 0.75F;
        modelsizebase = modelsize;
        setSize(width * 0.8F + fat, height * 1.3F + fat);
        setCaveTexture();
        this.targetTasks.addTask(0, new CREEPSEntityCaveman.AIFindPlayerToAttack());
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
    }

    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
    	
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (frozen > 0 && worldObj.getBlockState(new BlockPos(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ))).getBlock() == Blocks.air)
        {
            posY--;
        }

        if (isWet())
        {
            frozen = 0;
        }
        /*double moveSpeed = this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue();
        moveSpeed = frozen >= 1 ? 0.0F : 0.45F;*/

        if (wanderstate == 0 && frozen < 1 && !evil && !MoreCreepsAndWeirdos.cavemanbuilding && rand.nextInt(100) == 0)
        {
            wanderstate = 1;
        }

        if (wanderstate == 1 && rand.nextInt(201) == 200 && !MoreCreepsAndWeirdos.cavemanbuilding && !evil && checkArea())
        {
            wanderstate = 2;
            housechunk = 0;

            for (int i = 0; i < 4; i++)
            {
                if (worldObj.getBlockState(new BlockPos(houseX, houseY, houseZ)).getBlock() == Blocks.air || worldObj.getBlockState(new BlockPos(houseX + 1, houseY, houseZ)).getBlock() == Blocks.air || worldObj.getBlockState(new BlockPos(houseX + 2, houseY, houseZ + 4)) == Blocks.air || worldObj.getBlockState(new BlockPos(houseX, houseY, houseZ + 2)).getBlock() == Blocks.air)
                {
                    houseY--;
                }
            }

            MoreCreepsAndWeirdos.cavemanbuilding = true;
        }

        if (wanderstate == 2)
        {
            posX = houseX - 1;
            //moveSpeed = 0.0F; TODO
            setRotation(45F, rotationPitch);
        }

        if (wanderstate == 2 && rand.nextInt(50) == 0)
        {
            if (housechunk == 0)
            {
                hammerswing = -2.8F;
                worldObj.setBlockState(new BlockPos(houseX + 1, houseY, houseZ), Blocks.snow.getDefaultState());
                housechunk++;
                snowFX(houseX + 1, houseY, houseZ);
            }
            else if (housechunk == 1)
            {
                hammerswing = -2.8F;
                worldObj.setBlockState(new BlockPos(houseX + 1, houseY + 1, houseZ), Blocks.snow.getDefaultState());
                housechunk++;
                snowFX(houseX + 1, houseY + 1, houseZ);
            }
            else if (housechunk == 2)
            {
                hammerswing = -2.8F;
                worldObj.setBlockState(new BlockPos(houseX + 3, houseY, houseZ), Blocks.snow.getDefaultState());
                snowFX(houseX + 3, houseY, houseZ);
                housechunk++;
            }
            else if (housechunk == 3)
            {
                hammerswing = -2.8F;
                worldObj.setBlockState(new BlockPos(houseX + 3, houseY + 1, houseZ), Blocks.snow.getDefaultState());
                snowFX(houseX + 3, houseY + 1, houseZ);
                housechunk++;
            }
            else if (housechunk == 4)
            {
                hammerswing = -2.8F;

                for (int j = 1; j < 4; j++)
                {
                    worldObj.setBlockState(new BlockPos(houseX, houseY, houseZ + j), Blocks.snow.getDefaultState());
                    snowFX(houseX, houseY, houseZ + j);
                }

                housechunk++;
            }
            else if (housechunk == 5)
            {
                hammerswing = -2.8F;

                for (int k = 1; k < 4; k++)
                {
                    worldObj.setBlockState(new BlockPos(houseX, houseY + 1, houseZ + k), Blocks.snow.getDefaultState());
                    snowFX(houseX, houseY + 1, houseZ + k);
                }

                housechunk++;
            }
            else if (housechunk == 6)
            {
                hammerswing = -2.8F;

                for (int l = 1; l < 4; l++)
                {
                    worldObj.setBlockState(new BlockPos(houseX + 4, houseY, houseZ + l), Blocks.snow.getDefaultState());
                    snowFX(houseX + 4, houseY, houseZ + l);
                }

                housechunk++;
            }
            else if (housechunk == 7)
            {
                hammerswing = -2.8F;

                for (int i1 = 1; i1 < 4; i1++)
                {
                    worldObj.setBlockState(new BlockPos(houseX + 4, houseY + 1, houseZ + i1), Blocks.snow.getDefaultState());
                    snowFX(houseX + 4, houseY + 1, houseZ + i1);
                }

                housechunk++;
            }
            else if (housechunk == 8)
            {
                hammerswing = -2.8F;

                for (int j1 = 1; j1 < 4; j1++)
                {
                    worldObj.setBlockState(new BlockPos(houseX + j1, houseY, houseZ + 4), Blocks.snow.getDefaultState());
                    snowFX(houseX + j1, houseY, houseZ + 4);
                }

                housechunk++;
            }
            else if (housechunk == 9)
            {
                hammerswing = -2.8F;

                for (int k1 = 1; k1 < 4; k1++)
                {
                    worldObj.setBlockState(new BlockPos(houseX + k1, houseY + 1, houseZ + 4), Blocks.snow.getDefaultState());
                    snowFX(houseX + k1, houseY + 1, houseZ + 4);
                }

                housechunk++;
            }
            else if (housechunk == 10)
            {
                hammerswing = -2.8F;

                for (int l1 = 1; l1 < 4; l1++)
                {
                    for (int j2 = 1; j2 < 4; j2++)
                    {
                        worldObj.setBlockState(new BlockPos(houseX + j2, houseY + 2, houseZ + l1), Blocks.snow.getDefaultState());
                        snowFX(houseX + j2, houseY + 2, houseZ + l1);
                    }
                }

                housechunk++;
            }
            else if (housechunk == 11)
            {
                hammerswing = -2.8F;
                worldObj.setBlockState(new BlockPos(houseX + 2, houseY + 3, houseZ + 2), Blocks.snow.getDefaultState());
                snowFX(houseX + 2, houseY + 3, houseZ + 2);
                housechunk++;
            }
            else if (housechunk == 12)
            {
                Item i2;

                if (rand.nextInt(5) == 0)
                {
                    i2 = Items.fish;
                }
                else
                {
                    i2 = MoreCreepsAndWeirdos.popsicle;
                }

                if(!worldObj.isRemote)
                {
                    EntityItem entityitem = new EntityItem(worldObj, houseX + 3, houseY, houseZ + 3, new ItemStack(i2, rand.nextInt(4) + 1, 0));
                    worldObj.spawnEntityInWorld(entityitem);
                }
                //moveSpeed = maxspeed;
                MoreCreepsAndWeirdos.cavemanbuilding = false;
                wanderstate = 3;
            }
        }

        if (hammerswing < 0.0F)
        {
            hammerswing += 0.4F;
        }
        else
        {
            hammerswing = 0.0F;
        }

        /*EntityPlayerSP entityplayersp = ModLoader.getMinecraftInstance().thePlayer;
        float f = getDistanceToEntity(entityplayersp);

        if (f < 8F)
        {
            ignoreFrustumCheck = true;
        }
        else
        {
            ignoreFrustumCheck = false;
        }*/
    }
    
    public boolean isMovementBlocked()
    {
    	return frozen >= 1 || this.wanderstate == 2 ? true : false;
    }

    public boolean checkArea()
    {
        houseX = MathHelper.floor_double(posX);
        houseY = MathHelper.floor_double(posY);
        houseZ = MathHelper.floor_double(posZ);

        if (worldObj.getBlockState(new BlockPos(houseX, houseY - 1, houseZ)).getBlock() == Blocks.air)
        {
            houseY--;
        }

        area = 0;

        for (int i = -3; i < 7; i++)
        {
            for (int k = -3; k < 7; k++)
            {
                for (int i1 = 0; i1 < 3; i1++)
                {
                    if (worldObj.getBlockState(new BlockPos(houseX + k, houseY + i1, houseZ + i)).getBlock() == Blocks.air)
                    {
                        area++;
                    }
                }
            }
        }

        if (area < 220)
        {
            return false;
        }

        for (int j = -2; j < 7; j++)
        {
            for (int l = -2; l < 7; l++)
            {
                Block j1 = worldObj.getBlockState(new BlockPos(houseX + l, houseY, houseZ + j)).getBlock();
                Block k1 = worldObj.getBlockState(new BlockPos(houseX + l, houseY - 1, houseZ + j)).getBlock();

                if (j1 == Blocks.snow || j1 == Blocks.ice)
                {
                    area++;
                }

                if (k1 == Blocks.snow || k1 == Blocks.ice)
                {
                    area++;
                }
            }
        }

        return area > 75;
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    /*protected Entity findPlayerToAttack()
    {
        if (!evil || frozen > 0)
        {
            return null;
        }
        else
        {
            return super.findPlayerToAttack();
        }
    }*/

    class AIFindPlayerToAttack extends EntityAINearestAttackableTarget
    {
        public AIFindPlayerToAttack()
        {
            super(CREEPSEntityCaveman.this, EntityPlayer.class, true);
        }
        
        public void updateTask()
        {
        	EntityLivingBase target = CREEPSEntityCaveman.this.getAttackTarget();
        	float f = getDistanceToEntity(target);
        	attackEntity(target, f);
        }
        
        public boolean shouldExecute()
        {
            return !evil || frozen > 0 && super.shouldExecute();
        }
    }
    
    /**
     * knocks back this entity
     */
    public void knockBack(Entity entity, int i, double d, double d1)
    {
        if (frozen < 1)
        {
            super.knockBack(entity, i, d, d1);
        }
    }

    public void updateRiderPosition()
    {
        riddenByEntity.setPosition(posX, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ);
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset()
    {
        return 0.5D;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (frozen < 1)
        {
            super.onLivingUpdate();
        }

        if (handleWaterMovement())
        {
            frozen = 0;
        }

        if (isWet())
        {
            frozen = 0;
        }
    }

    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
    public float func_180484_a(BlockPos bp)
    {
        if (worldObj.getBlockState(bp.down()) == Blocks.gravel.getDefaultState() || worldObj.getBlockState(bp.down()) == Blocks.stone.getDefaultState())
        {
            return 10F;
        }
        else
        {
            return -(float)bp.getY();
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        Entity entity = damagesource.getEntity();
        hungry = false;

        if (frozen < 1)
        {
            evil = true;
            setCaveTexture();

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

        if (entity != null && (entity instanceof EntityPlayer) && frozen > 0)
        {
            worldObj.playSoundAtEntity(this, "morecreeps:cavemanice", 0.5F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);

            if (rand.nextInt(100) > 65)
            {
                frozen--;
            }

            for (int j = 0; j < 35; j++)
            {
                worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, posX, posY + 1.0D, posZ, 0.0D, 0.0D, 0.0D);
            }
        }

        if (frozen > 0)
        {
            hurtTime = 0;
        }

        return false;
    }

    public void snowFX(int i, int j, int k)
    {
        for (int l = 0; l < 40; l++)
        {
            worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, i, (double)j + 0.5D, k, 1.0D, 1.0D, 1.0D);
        }
        if(worldObj.isRemote)
        {
            MoreCreepsAndWeirdos.proxy.foam3(worldObj, this, i, j, k);
        }
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity entity, float f)
    {
        if (frozen < 1)
        {
            if (onGround)
            {
                double d = entity.posX - posX;
                double d1 = entity.posZ - posZ;
                float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
                motionX = (d / (double)f1) * 0.20000000000000001D * (0.45000001192092898D + motionX * 0.20000000298023224D);
                motionZ = (d1 / (double)f1) * 0.20000000000000001D * (0.40000001192092893D + motionZ * 0.20000000298023224D);
                motionY = 0.46000000596246449D;
                fallDistance = -25F;
            }

            if ((double)f < 2.8999999999999999D && entity.getBoundingBox().maxY > getBoundingBox().minY && entity.getBoundingBox().minY < getBoundingBox().maxY)
            {
                if (hammerswing == 0.0F)
                {
                    hammerswing = -2.8F;
                }

                if (talkdelay-- < 0)
                {
                    worldObj.playSoundAtEntity(this, "morecreeps:cavemanevil", 0.5F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    talkdelay = 2;
                }
            }

            if ((double)f < 2.3500000000000001D && entity.getBoundingBox().maxY > getBoundingBox().minY && entity.getBoundingBox().minY < getBoundingBox().maxY)
            {
                attackTime = 20;
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), attack);
            }
        }
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
        IBlockState i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k));
        return (i1 == Blocks.snow.getDefaultState() || i1 == Blocks.ice.getDefaultState() || i1 == Blocks.snow.getDefaultState()) && worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Hungry", hungry);
        nbttagcompound.setFloat("ModelSize", modelsize);
        nbttagcompound.setFloat("ModelSizeBase", modelsizebase);
        nbttagcompound.setFloat("Fat", fat);
        nbttagcompound.setInteger("Frozen", frozen);
        nbttagcompound.setBoolean("Cavegirl", cavegirl);
        nbttagcompound.setBoolean("Evil", evil);
        nbttagcompound.setInteger("WanderState", wanderstate);
        nbttagcompound.setInteger("HouseX", houseX);
        nbttagcompound.setInteger("HouseY", houseY);
        nbttagcompound.setInteger("HouseZ", houseZ);
        nbttagcompound.setInteger("HouseChunk", housechunk);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        hungry = nbttagcompound.getBoolean("Hungry");
        modelsize = nbttagcompound.getFloat("ModelSize");
        modelsizebase = nbttagcompound.getFloat("ModelSizeBase");
        fat = nbttagcompound.getFloat("Fat");
        frozen = nbttagcompound.getInteger("Frozen");
        cavegirl = nbttagcompound.getBoolean("Cavegirl");
        evil = nbttagcompound.getBoolean("Evil");
        wanderstate = nbttagcompound.getInteger("WanderState");
        houseX = nbttagcompound.getInteger("HouseX");
        houseY = nbttagcompound.getInteger("HouseY");
        houseZ = nbttagcompound.getInteger("HouseZ");
        housechunk = nbttagcompound.getInteger("HouseChunk");

        if (wanderstate == 2)
        {
            MoreCreepsAndWeirdos.cavemanbuilding = true;
        }

        setCaveTexture();
    }

    public void setCaveTexture()
    {
        if (evil)
        {
            if (cavegirl)
            {
                texture = "morecreeps:textures/entity/cavemanladyevil.png";
            }
            else
            {
                texture = "morecreeps:textures/entity/cavemanevil.png";
            }
        }
        else if (cavegirl)
        {
            texture = "morecreeps:textures/entity/cavemanlady.png";
        }
        else
        {
            texture = "morecreeps:textures/entity/caveman.png";
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
            worldObj.playSoundAtEntity(this, s, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + (1.0F - (modelsizebase - modelsize) * 2.0F));
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        if (evil)
        {
            return "morecreeps:cavemanevil";
        }

        EntityLivingBase entityToAttack = this.getAttackTarget();
        
        if (entityToAttack != null)
        {
            if (cavegirl)
            {
                return "morecreeps:cavewomanfree";
            }
            else
            {
                return "morecreeps:cavemanfree";
            }
        }

        if (cavegirl)
        {
            if (frozen < 1)
            {
                return "morecreeps:cavewomanfree";
            }
            else
            {
                return "morecreeps:cavewomanfrozen";
            }
        }

        if (frozen < 1)
        {
            return "morecreeps:cavemanfree";
        }
        else
        {
            return "morecreeps:cavemanfrozen";
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        if (frozen > 0)
        {
            return null;
        }

        if (cavegirl)
        {
            return "morecreeps:cavewomanhurt";
        }
        else
        {
            return "morecreeps:cavemanhurt";
        }
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        if (cavegirl)
        {
            return "morecreeps:cavewomandead";
        }
        else
        {
            return "morecreeps:cavemandead";
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
        Object obj = damagesource.getEntity();

        if ((obj instanceof CREEPSEntityRocket) && ((CREEPSEntityRocket)obj).owner != null)
        {
            obj = ((CREEPSEntityRocket)obj).owner;
        }

        EntityPlayer player = (EntityPlayer) damagesource.getEntity();
        if(player != null)
        {
        	MoreCreepsAndWeirdos.cavemancount++;
            if (!((EntityPlayerMP)player).getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achieve1caveman))
            {
                worldObj.playSoundAtEntity(player, "morecreeps:achievement", 1.0F, 1.0F);
                player.addStat(MoreCreepsAndWeirdos.achieve1caveman, 1);
                confetti();
            }

            if (!((EntityPlayerMP)player).getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achieve1caveman) && MoreCreepsAndWeirdos.cavemancount >= 10)
            {
                worldObj.playSoundAtEntity(player, "morecreeps:achievement", 1.0F, 1.0F);
                player.addStat(MoreCreepsAndWeirdos.achieve10caveman, 1);
                confetti();
            }

            if (!((EntityPlayerMP)player).getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achieve10caveman) && MoreCreepsAndWeirdos.cavemancount >= 50)
            {
                worldObj.playSoundAtEntity(player, "morecreeps:achievement", 1.0F, 1.0F);
                player.addStat(MoreCreepsAndWeirdos.achieve50caveman, 1);
                confetti();
            }
        }
        if(!worldObj.isRemote)
        {
            if (rand.nextInt(10) == 0)
            {
                dropItem(Items.porkchop, rand.nextInt(3) + 1);
            }

            if (rand.nextInt(10) == 0)
            {
                dropItem(MoreCreepsAndWeirdos.popsicle, rand.nextInt(3) + 1);
            }

            if (rand.nextInt(8) == 0)
            {
                dropItem(MoreCreepsAndWeirdos.cavemanclub, 1);
            }
        }

        super.onDeath(damagesource);
    }

    public void confetti()
    {
    	MoreCreepsAndWeirdos.proxy.confettiA(this, worldObj);
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getTalkInterval()
    {
        return !evil ? 180 : 120;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }
}
