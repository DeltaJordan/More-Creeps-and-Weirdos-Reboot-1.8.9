package fr.elias.morecreeps.common.entity;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
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
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import fr.elias.morecreeps.common.Reference;
import fr.elias.morecreeps.common.entity.ai.EntityArmyGuyAI;

public class CREEPSEntityArmyGuy extends EntityMob
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
    public boolean shooting;
    public int shootingdelay;
    public float modelsize;
    private Entity targetedEntity;
    public boolean loyal;
    public float distance;
    public int attack;
    public ResourceLocation texture;
    public int attackTime;

    public CREEPSEntityArmyGuy(World world)
    {
        super(world);
        texture = new ResourceLocation(Reference.MOD_ID, 
        		Reference.TEXTURE_PATH_ENTITES + Reference.TEXTURE_ARMY_GUY_DEFAULT);
        armright = false;
        armleft = false;
        legright = false;
        legleft = false;
        shrunk = false;
        helmet = false;
        head = false;
        shooting = false;
        shootingdelay = 20;
        defaultHeldItem = new ItemStack(MoreCreepsAndWeirdos.gun, 1);
        modelsize = 1.0F;
        loyal = false;
        attack = 1;
        attackTime = 20;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityArmyGuyAI(this));
        //fixed, just needed imported :D
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, true, new Class[0]));
    }
    
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(70D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.65D);
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

    public void entityInit()
    {
    	super.entityInit();
    	this.dataWatcher.addObject(17, "");
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
        	
        	//[needed for 1.8] updated sound reference and added sounds.json
        	//done for all sounds
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
        double health = this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth).getAttributeValue();
        if (shootingdelay-- < 1)
        {
            shooting = false;
        }

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

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	EntityLivingBase entityToAttack = this.getAttackTarget();
    	float yOffset = (float) this.getYOffset();
        if (entityToAttack instanceof CREEPSEntityArmyGuyArm)
        {
            entityToAttack = null;
        }

        if (legleft && legright && head)
        {
            yOffset = -1.4F + (1.0F - modelsize);
        }
        else if (legleft && legright)
        {
            yOffset = -0.75F + (1.0F - modelsize);
        }
        else
        {
            yOffset = 0.0F;
        }

        if (legright)
        {
            isJumping = true;
        }

        super.onUpdate();
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    public void attackEntity(Entity entity, float f)
    {
        if (attackTime-- < 1 && !armright)
        {
            attackTime = rand.nextInt(50) + 35;
            double d = 64D;
            targetedEntity = entity;

            if (targetedEntity != null && canEntityBeSeen(targetedEntity) && (!(targetedEntity instanceof EntityPlayer) || !loyal) && !isDead && !(targetedEntity instanceof CREEPSEntityArmyGuyArm))
            {
                double d1 = targetedEntity.getDistanceSqToEntity(this);

                if (d1 < d * d && d1 > 3D)
                {
                    double d2 = targetedEntity.posX - posX;
                    double d3 = (targetedEntity.getEntityBoundingBox().minY + (double)(targetedEntity.height / 2.0F)) - (posY + (double)(height / 2.0F));
                    double d4 = targetedEntity.posZ - posZ;
                    renderYawOffset = rotationYaw = (-(float)Math.atan2(d2, d4) * 180F) / (float)Math.PI;
                    worldObj.playSoundAtEntity(this, "morecreeps:bullet", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
                    shooting = true;
                    shootingdelay = 20;
                    CREEPSEntityBullet creepsentitybullet = new CREEPSEntityBullet(worldObj, this, 0.0F);

                    if (creepsentitybullet != null)
                    {
                        worldObj.spawnEntityInWorld(creepsentitybullet);
                    }
                }
            }
        }

        if (onGround && legright)
        {
            motionY = 0.25000000596246447D;
        }

        if ((double)f < 1.8D)
        {
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), attack);
        }
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
        if (this.getOwnerId() == null)
        {
        	nbttagcompound.setString("OwnerUUID", "");
        }
        else
        {
        	nbttagcompound.setString("OwnerUUID", this.getOwnerId());
        }
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

        if (loyal)
        {
            texture = new ResourceLocation(Reference.MOD_ID,
            		Reference.TEXTURE_PATH_ENTITES + Reference.TEXTURE_ARMY_GUY_LOYAL);
        }
        
        double health = this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth).getAttributeValue();
        
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
        String s = "";

        if (nbttagcompound.hasKey("OwnerUUID", 8))
        {
            s = nbttagcompound.getString("OwnerUUID");
        }
        else
        {
            String s1 = nbttagcompound.getString("Owner");
            s = PreYggdrasilConverter.func_152719_a(s1);
        }

        if (s.length() > 0)
        {
            this.setOwnerId(s);
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
        if (rand.nextInt(5) == 0)
        {
            dropItem(MoreCreepsAndWeirdos.gun, 1);
        }
        else
        {
            dropItem(Items.apple, rand.nextInt(2));
        }
    }

    /**
     * Returns the item that this EntityLiving is holding, if any.
     */
    public ItemStack getHeldItem()
    {
        return defaultHeldItem;
    }
    
    public String getOwnerId()
    {
        return this.dataWatcher.getWatchableObjectString(17);
    }

    public void setOwnerId(String ownerUuid)
    {
        this.dataWatcher.updateObject(17, ownerUuid);
    }
    
    public EntityLivingBase getOwnerEntity()
    {
        try
        {
            UUID uuid = UUID.fromString(this.getOwnerId());
            return uuid == null ? null : this.worldObj.getPlayerEntityByUUID(uuid);
        }
        catch (IllegalArgumentException illegalargumentexception)
        {
            return null;
        }
    }

    public boolean isOwner(EntityLivingBase entityIn)
    {
        return entityIn == this.getOwnerEntity();
    }
}
