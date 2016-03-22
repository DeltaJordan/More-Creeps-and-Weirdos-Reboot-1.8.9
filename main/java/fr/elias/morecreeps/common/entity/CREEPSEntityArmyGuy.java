package fr.elias.morecreeps.common.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import com.google.common.base.Predicate;

import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import fr.elias.morecreeps.common.Reference;

public class CREEPSEntityArmyGuy extends EntityMob implements IRangedAttackMob, IEntityAdditionalSpawnData
{
    public ItemStack defaultHeldItem;
    public int weapon;
    public int timeleft;
    public String ss;
    public boolean armright;
    public boolean armleft;
    public boolean legright;
    public boolean legleft;
    public boolean shrunk;
    public boolean helmet;
    public boolean head;
    public float modelsize;
    private Entity targetedEntity;
    public boolean loyal;
    public float distance;
    public int attack;
    public int attackTime;

    public CREEPSEntityArmyGuy(World world)
    {
        super(world);
        armright = false;
        armleft = false;
        legright = false;
        legleft = false;
        shrunk = false;
        helmet = false;
        head = false;
        defaultHeldItem = new ItemStack(MoreCreepsAndWeirdos.gun, 1);
        modelsize = 1.0F;
        loyal = false;
        attack = 1;
        attackTime = 20;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityCreature.class, 1.0D, false));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F));
        this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new CREEPSEntityArmyGuy.AINearestAttackableTarget(this, EntityCreature.class, 3, false, false, IMob.mobSelector));
    }

	public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(70D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return !loyal;
    }

    public CREEPSEntityArmyGuy(World world, Entity entity, double d, double d1, double d2, boolean flag)
    {
        this(world);
        setPosition(d, d1, d2);
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
        if (rand.nextInt(7) == 0)
        {
            return "morecreeps:army";
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
        return "morecreeps:armyhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:armydeath";
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        float health = getHealth();
        if (health < 60 && health > 50 && !helmet)
        {
        	helmet = true;
            worldObj.playSoundAtEntity(this, "morecreeps:armyhelmet", 1.0F, 0.95F);
            if(worldObj.isRemote)
            {
                MoreCreepsAndWeirdos.proxy.blood(worldObj, posX, posY + 2.5D, posZ, false);
            }
        }
        else if (health < 50 && health > 40 && !armleft)
        {
        	helmet = true;
        	armleft = true;
            worldObj.playSoundAtEntity(this, "morecreeps:armyarm", 1.0F, 0.95F);
            CREEPSEntityArmyGuyArm creepsentityarmyguyarm = new CREEPSEntityArmyGuyArm(worldObj);
            creepsentityarmyguyarm.setLocationAndAngles(posX, posY + 1.0D, posZ, rotationYaw, 0.0F);
            creepsentityarmyguyarm.motionX = 0.25D;
            creepsentityarmyguyarm.motionY = -0.25D;
            creepsentityarmyguyarm.modelsize = modelsize;
            if(!worldObj.isRemote)
            {
                worldObj.spawnEntityInWorld(creepsentityarmyguyarm);
            }
            if(worldObj.isRemote)
            {
                MoreCreepsAndWeirdos.proxy.blood(worldObj, posX - 0.5D, posY + 1.0D, posZ, true);
            }
        }
        else if (health < 40 && health > 30 && !legright)
        {
        	helmet = true;
        	armleft = true;
        	legright = true;
            worldObj.playSoundAtEntity(this, "morecreeps:armyleg", 1.0F, 0.95F);
            CREEPSEntityArmyGuyArm creepsentityarmyguyarm1 = new CREEPSEntityArmyGuyArm(worldObj);
            creepsentityarmyguyarm1.setLocationAndAngles(posX, posY + 1.0D, posZ, rotationYaw, 0.0F);
            creepsentityarmyguyarm1.motionX = 0.25D;
            creepsentityarmyguyarm1.motionY = 0.25D;
            creepsentityarmyguyarm1.texture = new ResourceLocation(Reference.MOD_ID, Reference.TEXTURE_PATH_ENTITES + 
            		Reference.TEXTURE_ARMY_GUY_LEG);
            creepsentityarmyguyarm1.modelsize = modelsize;
            if(!worldObj.isRemote)
            {
            	worldObj.spawnEntityInWorld(creepsentityarmyguyarm1);
            }

            if(worldObj.isRemote)
            {
                MoreCreepsAndWeirdos.proxy.blood(worldObj, posX - 0.5D, posY, posZ, true);
            }
        }
        else if (health < 30 && health > 20 && !legleft)
        {
        	helmet = true;
        	armleft = true;
        	legright = true;
        	legleft = true;
            worldObj.playSoundAtEntity(this, "morecreeps:armybothlegs", 1.0F, 0.95F);
            CREEPSEntityArmyGuyArm creepsentityarmyguyarm2 = new CREEPSEntityArmyGuyArm(worldObj);
            creepsentityarmyguyarm2.setLocationAndAngles(posX, posY + 1.0D, posZ, rotationYaw, 0.0F);
            creepsentityarmyguyarm2.motionX = 0.25D;
            creepsentityarmyguyarm2.motionY = -0.25D;
            creepsentityarmyguyarm2.texture = new ResourceLocation(Reference.MOD_ID, 
            		Reference.TEXTURE_PATH_ENTITES + Reference.TEXTURE_ARMY_GUY_LEG);
            creepsentityarmyguyarm2.modelsize = modelsize;
            if(!worldObj.isRemote)
            {
                worldObj.spawnEntityInWorld(creepsentityarmyguyarm2);
            }
            if(worldObj.isRemote)
            {
                MoreCreepsAndWeirdos.proxy.blood(worldObj, posX + 0.5D, posY, posZ, true);
            }
        }
        else if (health < 20 && health > 10 && !armright)
        {
        	helmet = true;
        	armleft = true;
        	legright = true;
        	legleft = true;
        	armright = true;
            worldObj.playSoundAtEntity(this, "morecreeps:armyarm", 1.0F, 0.95F);
            CREEPSEntityArmyGuyArm creepsentityarmyguyarm3 = new CREEPSEntityArmyGuyArm(worldObj);
            creepsentityarmyguyarm3.setLocationAndAngles(posX, posY + 1.0D, posZ, rotationYaw, 0.0F);
            creepsentityarmyguyarm3.motionX = 0.25D;
            creepsentityarmyguyarm3.motionY = 0.25D;
            creepsentityarmyguyarm3.modelsize = modelsize;
            if(!worldObj.isRemote)
            {
                worldObj.spawnEntityInWorld(creepsentityarmyguyarm3);
                if (rand.nextInt(10) == 0)
                {
                    dropItem(MoreCreepsAndWeirdos.gun, 1);
                }
            }
            defaultHeldItem = null;

            if(worldObj.isRemote)
            {
                MoreCreepsAndWeirdos.proxy.blood(worldObj, posX, posY + 1.0D, posZ, true);
            }
        }
        else if (health < 10 && health > 0 && !head)
        {
        	helmet = true;
        	armleft = true;
        	legright = true;
        	legleft = true;
        	armright = true;
        	head = true;
            defaultHeldItem = null;
            worldObj.playSoundAtEntity(this, "morecreeps:armyhead", 1.0F, 0.95F);

            if(worldObj.isRemote)
            {
                MoreCreepsAndWeirdos.proxy.blood(worldObj, posX, posY, posZ, true);
            }
        }
    }
    public double getYOffset()
    {
    	if (legleft && legright && head)
        {
            return -1.4D + (1.0D - (double)modelsize);
        }
        else if (legleft && legright)
        {
            return -0.75D + (1.0D - (double)modelsize);
        }
        else
        {
            return 0.0D;
        }
    }
    public void onUpdate()
    {
        if (this.getAttackTarget() instanceof CREEPSEntityArmyGuyArm)
        {
           setAttackTarget(null);
        }
        if (loyal && this.getAttackTarget() != null && ((this.getAttackTarget() instanceof CREEPSEntityArmyGuy) || (this.getAttackTarget() instanceof CREEPSEntityGuineaPig) || (this.getAttackTarget() instanceof CREEPSEntityHunchback)) && ((CREEPSEntityArmyGuy)this.getAttackTarget()).loyal)
        {
        	setAttackTarget(null);
        }
        EntityLivingBase livingbase = (EntityLivingBase)this.getAttackTarget();
        if(livingbase != null && (livingbase.getAITarget() instanceof EntityPlayer))
        {
        	setAttackTarget(livingbase);
        }
        if(!loyal && this.getAttackTarget() != null && (this.getAttackTarget() instanceof CREEPSEntityArmyGuy))
        {
        	setAttackTarget(null);
        }
    	System.out.println("[ENTITY] ArmyGuy is loyal :" + loyal);
        if (legright)
        {
        	this.setJumping(true);
        }

        super.onUpdate();
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
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        //fixed light position getter
        int l = worldObj.getBlockLightOpacity(getPosition());
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        return i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.double_stone_slab && i1 != Blocks.stone_slab && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).size() == 0 && rand.nextInt(10) == 0 && l > 8;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("ArmRight", armright);
        nbttagcompound.setBoolean("ArmLeft", armleft);
        nbttagcompound.setBoolean("LegRight", legright);
        nbttagcompound.setBoolean("LegLeft", legleft);
        nbttagcompound.setBoolean("Helmet", helmet);
        nbttagcompound.setBoolean("Head", head);
        nbttagcompound.setFloat("ModelSize", modelsize);
        nbttagcompound.setBoolean("Loyal", loyal);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        armright = nbttagcompound.getBoolean("ArmRight");
        armleft = nbttagcompound.getBoolean("ArmLeft");
        legright = nbttagcompound.getBoolean("LegRight");
        legleft = nbttagcompound.getBoolean("LegLeft");
        helmet = nbttagcompound.getBoolean("Helmet");
        head = nbttagcompound.getBoolean("Head");
        modelsize = nbttagcompound.getFloat("ModelSize");
        loyal = nbttagcompound.getBoolean("Loyal");
        float health = getHealth();
        
        if (helmet)
        {
            health = 60;
        }

        if (armleft)
        {
            health = 50;
        }

        if (legright)
        {
            health = 40;
        }

        if (armright)
        {
            health = 30;
            defaultHeldItem = null;
        }

        if (legleft)
        {
            health = 20;
        }

        if (head)
        {
            health = 10;
        }
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected Item getDropItem()
    {
        return Items.arrow;
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
    	if(!worldObj.isRemote)
    	{
            if (rand.nextInt(5) == 0)
            {
                dropItem(MoreCreepsAndWeirdos.gun, 1);
            }
            else
            {
                dropItem(Items.apple, rand.nextInt(2));
            }
    	}
    }

    /**
     * Returns the item that this EntityLiving is holding, if any.
     */
    public ItemStack getHeldItem()
    {
        return defaultHeldItem;
    }

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
        double d2 = targetedEntity.posX - posX;
        double d3 = (targetedEntity.getEntityBoundingBox().minY + (double)(targetedEntity.height / 2.0F)) - (posY + (double)(height / 2.0F));
        double d4 = targetedEntity.posZ - posZ;
        renderYawOffset = rotationYaw = (-(float)Math.atan2(d2, d4) * 180F) / (float)Math.PI;
        worldObj.playSoundAtEntity(this, "morecreeps:bullet", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
        CREEPSEntityBullet creepsentitybullet = new CREEPSEntityBullet(worldObj, this, 0.0F);
        if(!worldObj.isRemote && !armright)
        worldObj.spawnEntityInWorld(creepsentitybullet);
	}
    static class AINearestAttackableTarget extends EntityAINearestAttackableTarget
    {
    	public CREEPSEntityArmyGuy armyGuy;
        public AINearestAttackableTarget(final CREEPSEntityArmyGuy p_i45858_1_, Class p_i45858_2_, int p_i45858_3_, boolean p_i45858_4_, boolean p_i45858_5_, final Predicate p_i45858_6_)
        {
            super(p_i45858_1_, p_i45858_2_, p_i45858_3_, p_i45858_4_, p_i45858_5_, p_i45858_6_);
            armyGuy = p_i45858_1_;
        }
        public boolean shouldExecute()
        {
        	EntityLivingBase p_180096_1_ = (EntityLivingBase)armyGuy.getAttackTarget();
        	if ((p_180096_1_ instanceof EntityPlayer) || (p_180096_1_ instanceof CREEPSEntityArmyGuy) || (p_180096_1_ instanceof CREEPSEntityHunchback) || (p_180096_1_ instanceof CREEPSEntityGuineaPig))
            {
                return false;
            }else
        	return super.shouldExecute();
        }
    }
	@Override
	public void writeSpawnData(ByteBuf buffer)
	{
		buffer.writeBoolean(loyal);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData)
	{
		loyal = additionalData.readBoolean();
	}
}
