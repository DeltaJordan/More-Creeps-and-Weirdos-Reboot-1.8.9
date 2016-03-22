package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CREEPSEntityDesertLizard extends EntityMob
{
    public int fireballTime;
    private Entity targetedEntity;
    public float modelsize;
    static final String lizardTextures[] =
    {
        "morecreeps:textures/entity/desertlizard1.png", "morecreeps:textures/entity/desertlizard2.png", "morecreeps:textures/entity/desertlizard3.png", "morecreeps:textures/entity/desertlizard4.png", "morecreeps:textures/entity/desertlizard5.png"
    };
    public String texture;
    public CREEPSEntityDesertLizard(World world)
    {
        super(world);
        texture = lizardTextures[rand.nextInt(lizardTextures.length)];
        setSize(2.0F, 1.5F);
        fireballTime = rand.nextInt(300) + 250;
        modelsize = 1.0F;
        this.targetTasks.addTask(0, new CREEPSEntityDesertLizard.AIAttackEntity());
    }
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.55D);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1D);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        modelsize = nbttagcompound.getFloat("ModelSize");
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(getBoundingBox().minY);
        int k = MathHelper.floor_double(posZ);
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        return (i1 == Blocks.sand || i1 == Blocks.gravel) && i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.double_stone_slab && i1 != Blocks.stone_slab && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0 && worldObj.canSeeSky(new BlockPos(i, j, k)) && rand.nextInt(5) == 0; //&& l > 10;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 3;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (fireballTime-- < 1)
        {
            fireballTime = rand.nextInt(300) + 250;
            double d = 64D;
            targetedEntity = worldObj.getClosestPlayerToEntity(this, 30D);

            if (targetedEntity != null && canEntityBeSeen(targetedEntity) && (targetedEntity instanceof EntityPlayer))
            {
                double d1 = targetedEntity.getDistanceSqToEntity(this);

                if (d1 < d * d && d1 > 10D)
                {
                    double d2 = targetedEntity.posX - posX;
                    double d3 = (targetedEntity.getBoundingBox().minY + (double)(targetedEntity.height / 2.0F)) - (posY + (double)(height / 2.0F));
                    double d4 = (targetedEntity.posZ - posZ) + 0.5D;
                    renderYawOffset = rotationYaw = (-(float)Math.atan2(d2, d4) * 180F) / (float)Math.PI;
                    worldObj.playSoundAtEntity(this, "morecreeps:desertlizardfireball", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    CREEPSEntityDesertLizardFireball creepsentitydesertlizardfireball = new CREEPSEntityDesertLizardFireball(worldObj, this, d2, d3, d4);
                    double d5 = 4D;
                    Vec3 vec3d = getLook(1.0F);
                    creepsentitydesertlizardfireball.posX = posX + vec3d.xCoord * d5;
                    creepsentitydesertlizardfireball.posY = posY + (double)(height / 2.0F) + 0.5D;
                    creepsentitydesertlizardfireball.posZ = posZ + vec3d.zCoord * d5;
                    worldObj.spawnEntityInWorld(creepsentitydesertlizardfireball);
                }
            }
        }

        super.onLivingUpdate();
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
        return "morecreeps:desertlizard";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:desertlizardhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:desertlizarddeath";
    }

    
    protected void attackEntity(Entity entity, float f)
    {
        if (f > 2.0F && f < 6F && rand.nextInt(10) == 0 && onGround)
        {
            double d = entity.posX - posX;
            double d1 = entity.posZ - posZ;
            float f2 = MathHelper.sqrt_double(d * d + d1 * d1);
            motionX = (d / (double)f2) * 0.5D * 0.8000000019209289D + motionX * 0.20000000098023224D;
            motionZ = (d1 / (double)f2) * 0.5D * 0.70000000192092893D + motionZ * 0.20000000098023224D;
            motionY = 0.40000000196046448D;
        }
    }
    class AIAttackEntity extends EntityAINearestAttackableTarget
    {
        public AIAttackEntity()
        {
            super(CREEPSEntityDesertLizard.this, EntityPlayer.class, true);
        }
        
        public void updateTask()
        {
        	EntityLivingBase target = CREEPSEntityDesertLizard.this.getAttackTarget();
        	float f = getDistanceToEntity(target);
        	attackEntity(target, f);
        }
        
        public boolean shouldExecute()
        {
        	EntityLivingBase target = CREEPSEntityDesertLizard.this.getAttackTarget();
            return target != null && super.shouldExecute();
        }
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
                dropItem(Items.cooked_porkchop, rand.nextInt(3) + 1);
            }
            else
            {
                dropItem(Item.getItemFromBlock(Blocks.sand), rand.nextInt(3) + 1);
            }
    	}

        super.onDeath(damagesource);
    }
}
