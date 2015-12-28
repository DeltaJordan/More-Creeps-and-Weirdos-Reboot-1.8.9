package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityPreacher extends EntityMob
{
    public boolean rideable;
    protected double attackRange;
    private int angerLevel;
    private int value;
    private boolean ritual;
    private Entity targetedEntity;
    private Entity victimEntity;
    public int raise;
    public boolean getvictim;
    
    private float victimspeed;
    private int waittime;
    private int raiselevel;
    public int revenge;
    
    public String texture;

    public CREEPSEntityPreacher(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/preacher0.png";
        angerLevel = 0;
        attackRange = 16D;
        ritual = false;
        getvictim = false;
        raise = 0;
        victimspeed = 0.0F;
        waittime = rand.nextInt(500) + 500;
        raiselevel = 0;
        revenge = 0;
        ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntitySheep.class, true));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPig.class, true));
    }
    
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(75D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.getAttackTarget() != null ? 0.6D : 0.4D);

        if (rand.nextInt(4) == 0)
        {
            texture = (new StringBuilder()).append("morecreeps:textures/entity/preacher").append(String.valueOf(rand.nextInt(3))).append(".png").toString();
        }

        super.onUpdate();

        if (handleLavaMovement())
        {
            if (rand.nextInt(25) == 0)
            {
                worldObj.playSoundAtEntity(this, "morecreeps:preacherburn", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            }

            setOnFireFromLava();
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (inWater || isEntityInsideOpaqueBlock())
        {
            int i = MathHelper.floor_double(posX);
            int l = MathHelper.floor_double(getEntityBoundingBox().minY);
            int j1 = MathHelper.floor_double(posZ);
            Block l1 = worldObj.getBlockState(new BlockPos(i, l + 2, j1)).getBlock();

            if (l1 != Blocks.air)
            {
                worldObj.setBlockToAir(new BlockPos(i, l + 2, j1));
                motionY += 0.5D;
            }
        }

        if (getvictim)
        {
            motionX = 0.0D;
            motionY = 0.0D;
            motionZ = 0.0D;

            if (raise++ > raiselevel)
            {
                getvictim = false;
                ritual = false;
                victimEntity.motionY = -0.80000001192092896D;
                raise = 0;
                waittime = rand.nextInt(500) + 500;
            }
            else
            {
                int j = MathHelper.floor_double(victimEntity.posX);
                int i1 = MathHelper.floor_double(victimEntity.getEntityBoundingBox().minY);
                int k1 = MathHelper.floor_double(victimEntity.posZ);
                Block i2 = worldObj.getBlockState(new BlockPos(j, i1 + 2, k1)).getBlock();

                if (i2 != Blocks.air && (victimEntity instanceof EntityPlayer))
                {
                    worldObj.setBlockToAir(new BlockPos(j, i1 + 2, k1));
                }

                victimEntity.motionY = 0.20000000298023224D;
                waittime = 1000;
                smokevictim(victimEntity);
                smoke();

                if (rand.nextInt(10) == 0)
                {
                    victimEntity.motionX = rand.nextFloat() * 0.85F - 0.5F;
                }
                else if (rand.nextInt(10) == 0)
                {
                    victimEntity.motionZ = rand.nextFloat() * 0.8F - 0.5F;
                }
            }
        }

        if (ritual && !getvictim)
        {
        	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
            targetedEntity = null;

            for (int k = 0; k < worldObj.loadedEntityList.size(); k++)
            {
                targetedEntity = (Entity)worldObj.loadedEntityList.get(k);

                if ((targetedEntity instanceof EntitySheep) || (targetedEntity instanceof EntityPig))
                {
                    getvictim = true;
                    victimEntity = targetedEntity;
                    victimEntity.motionX = 0.0D;
                    victimEntity.motionY = 0.0D;
                    victimEntity.motionZ = 0.0D;
                    raiselevel = rand.nextInt(50) + 50;
                    worldObj.playSoundAtEntity(this, "morecreeps:preacherraise", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                }
            }

            if (targetedEntity == null)
            {
                ritual = false;
                getvictim = false;
            	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
            }
        }
        else if (rand.nextInt(2) == 0 && waittime-- < 1)
        {
            ritual = true;
            waittime = 1000;
            getvictim = false;
        }

        super.onLivingUpdate();
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setShort("Anger", (short)angerLevel);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        angerLevel = nbttagcompound.getShort("Anger");
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
        return i1 != Blocks.sand && i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.double_stone_slab && i1 != Blocks.stone_slab && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).size() == 0 && worldObj.canSeeSky(new BlockPos(i, j, k)) && rand.nextInt(25) == 0;// && l > 10;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        Entity obj = damagesource.getEntity();

        if (!handleLavaMovement())
        {
            worldObj.playSoundAtEntity(this, "morecreeps:preacherhurt", 1.0F, 1.0F);
        }

        if (getvictim && obj != null && !(obj instanceof CREEPSEntityRocket))
        {
            obj.motionX += rand.nextFloat() * 1.98F;
            obj.motionY += rand.nextFloat() * 1.98F;
            obj.motionZ += rand.nextFloat() * 1.98F;
            return true;
        }

        if (obj != null && (obj instanceof CREEPSEntityRocket))
        {
            obj = worldObj.getClosestPlayerToEntity(this, 30D);

            if ((obj != null) & (obj instanceof EntityPlayer))
            {
                ((Entity)(obj)).mountEntity(null);
                getvictim = true;
                victimEntity = ((Entity)(obj));
                victimEntity.motionX = 0.0D;
                victimEntity.motionY = 0.0D;
                victimEntity.motionZ = 0.0D;
                raiselevel = rand.nextInt(50) + 50;
                worldObj.playSoundAtEntity(this, "morecreeps:preacherraise", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F);
            }
        }

        if (obj != null)
        {
            if (i < 1)
            {
                i = 1;
            }

            if ((obj instanceof CREEPSEntityGooDonut) || (obj instanceof CREEPSEntityRocket) || (obj instanceof CREEPSEntityBullet) || (obj instanceof CREEPSEntityRay))
            {
                i = 2;
            }

            //wtf is that ?
            //health = (health - rand.nextInt(i)) + 1;
            raise = 1;
            waittime = 0;
            smoke();
            getvictim = true;
            victimEntity = ((Entity)(obj));
            victimEntity.motionX = 0.0D;
            victimEntity.motionY = 0.0D;
            victimEntity.motionZ = 0.0D;
            raiselevel = rand.nextInt(50) + 50;
            worldObj.playSoundAtEntity(this, "morecreeps:preacherraise", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F);
        }

        return true;
    }

    /**
     * knocks back this entity
     */
    public void knockBack(Entity entity, float i, double d, double d1)
    {
        motionX *= 1.5D;
        motionZ *= 1.5D;
        motionY += 0.5D;
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        return null;
    }

    private void smoke()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, ((posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width) + (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2, new int[0]);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width - (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2, new int[0]);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F) + (double)i) - (double)width, d, d1, d2, new int[0]);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)i - (double)width, d, d1, d2, new int[0]);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, ((posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width) + (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F) + (double)i) - (double)width, d, d1, d2, new int[0]);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width - (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)i - (double)width, d, d1, d2, new int[0]);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, ((posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width) + (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F) + (double)i) - (double)width, d, d1, d2, new int[0]);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width - (double)i, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)i - (double)width, d, d1, d2, new int[0]);
            }
        }
    }

    private void smokevictim(Entity entity)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (entity.posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, (entity.posY + (double)(rand.nextFloat() * height) + (double)i) - 2D, (entity.posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2, new int[0]);
            }
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "morecreeps:preacher";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:preacherhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:preacherdeath";
    }

    public void confetti(EntityPlayer player)
    {
        double d = -MathHelper.sin((player.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((player.rotationYaw * (float)Math.PI) / 180F);
        CREEPSEntityTrophy creepsentitytrophy = new CREEPSEntityTrophy(worldObj);
        creepsentitytrophy.setLocationAndAngles(player.posX + d * 3D, player.posY - 2D, player.posZ + d1 * 3D, player.rotationYaw, 0.0F);
        if(!worldObj.isRemote)
        worldObj.spawnEntityInWorld(creepsentitytrophy);
    }

    /**
     * Will get destroyed next tick.
     */
    
    public void onDeath(DamageSource damagesource)
    {
    	super.onDeath(damagesource);
    	EntityPlayerMP player = (EntityPlayerMP) damagesource.getEntity();
    	if(player != null)
    	{
        	if (!player.getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievegotohell))
        	{
        		
        		worldObj.playSoundAtEntity(player, "morecreeps:achievement", 1.0F, 1.0F);
        		player.addStat(MoreCreepsAndWeirdos.achievegotohell, 1);
        		confetti(player);
        	}
    	}

    	if(!worldObj.isRemote)
    	{
        	if (rand.nextInt(50) == 0)
        	{
        		dropItem(Items.diamond, rand.nextInt(2) + 1);
        	}

        	if (rand.nextInt(50) == 0)
        	{
        		entityDropItem(new ItemStack(Items.dye, 1, 4), 1.0F);
        	}

        	if (rand.nextInt(50) == 0)
        	{
        		entityDropItem(new ItemStack(Items.dye, 1, 3), 1.0F);
        	}

        	if (rand.nextInt(50) == 0)
        	{
        		entityDropItem(new ItemStack(Items.dye, 1, 1), 1.0F);
        	}

        	if (rand.nextInt(2) == 0)
        	{
        		dropItem(Items.gold_ingot, rand.nextInt(5) + 2);
        	}
        	else
        	{
        		dropItem(Items.book, 1);
        		dropItem(Items.apple, 1);
        	}
    	}
    }
}
