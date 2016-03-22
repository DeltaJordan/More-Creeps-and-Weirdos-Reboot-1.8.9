package fr.elias.morecreeps.common.entity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.client.particles.CREEPSFxSmoke;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityCastleKing extends EntityMob
{
    private boolean foundplayer;
    private PathEntity pathToEntity;
    protected Entity playerToAttack;
    EntityPlayer entityplayer;

    /**
     * returns true if a creature has attacked recently only used for creepers and skeletons
     */
    protected boolean hasAttacked;
    public int intrudercheck;
    public ItemStack gem;
    public String texture;
    public double moveSpeed;
    public float attackStrength;
    public double health;
    private static ItemStack defaultHeldItem;
    public static Random random = new Random();
    public float hammerswing;

    public CREEPSEntityCastleKing(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/castleking.png";
        moveSpeed = 0.0;
        attackStrength = 4;
        health = rand.nextInt(60) + 60;
        setSize(2.0F, 1.6F);
        foundplayer = false;
        intrudercheck = 25;
        hammerswing = 0.0F;
    }
    
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(health);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(moveSpeed);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(attackStrength);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (hammerswing < 0.0F)
        {
            hammerswing += 0.45F;
        }
        else
        {
            hammerswing = 0.0F;
        }

        double d = -MathHelper.sin((rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((rotationYaw * (float)Math.PI) / 180F);
        CREEPSFxSmoke creepsfxsmoke = new CREEPSFxSmoke(worldObj, (posX + random.nextGaussian() * 0.5D) - random.nextGaussian() * 0.5D, ((posY - 1.0D) + random.nextGaussian() * 0.5D) - random.nextGaussian() * 0.5D, (posZ + random.nextGaussian() * 0.5D) - random.nextGaussian() * 0.5D, MoreCreepsAndWeirdos.partBlue, 0.55F, 0);
        creepsfxsmoke.renderDistanceWeight = 20D;
        Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxsmoke);

        if (intrudercheck-- < 0 && this.attackEntityAsMob(null))
        {
            intrudercheck = 25;
            EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 10D);

            if (entityplayer != null && canEntityBeSeen(entityplayer))
            {
                moveSpeed = 0.222F;
            }
        }
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity entity, float f)
    {
        if (onGround)
        {
            double d = entity.posX - posX;
            double d2 = entity.posZ - posZ;
            float f1 = MathHelper.sqrt_double(d * d + d2 * d2);
            motionX = (d / (double)f1) * 0.40000000000000002D * 0.20000000192092895D + motionX * 0.18000000098023225D;
            motionZ = (d2 / (double)f1) * 0.40000000000000002D * 0.17000000192092896D + motionZ * 0.18000000098023225D;
        }

        if ((double)f < 6D)
        {
            double d1 = entity.posX - posX;
            double d3 = entity.posZ - posZ;
            float f2 = MathHelper.sqrt_double(d1 * d1 + d3 * d3);
            motionX = (d1 / (double)f2) * 0.40000000000000002D * 0.20000000192092895D + motionX * 0.18000000098023225D;
            motionZ = (d3 / (double)f2) * 0.40000000000000002D * 0.070000001920928964D + motionZ * 0.18000000098023225D;
            rotationPitch = 90F;
        }

        if ((double)f < 3.2000000000000002D && entity.getBoundingBox().maxY > this.getBoundingBox().minY && entity.getBoundingBox().minY < this.getBoundingBox().maxY)
        {
            if (hammerswing == 0.0F)
            {
                hammerswing = -2.6F;
            }

            //attackTime = 10;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), attackStrength);
        }

        super.attackEntityAsMob(entity);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("FoundPlayer", foundplayer);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        foundplayer = nbttagcompound.getBoolean("FoundPlayer");
        attackStrength = 8;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "morecreeps:castleking";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:castlekinghurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:castlekingdeath";
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
        return i1 != Blocks.cobblestone && worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0 && worldObj.checkBlockCollision(getBoundingBox()) && worldObj.canBlockSeeSky(getPosition()) && rand.nextInt(5) == 0 && l > 10;
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
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

    private void smoke()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, ((posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width) + (double)((float)i * 0.5F), posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width - (double)((float)i * 0.5F), posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F) + (double)((float)i * 0.5F)) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)((float)i * 0.5F) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, ((posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width) + (double)((float)i * 0.5F), posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F) + (double)((float)i * 0.5F)) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width - (double)((float)i * 0.5F), posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)((float)i * 0.5F) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, ((posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width) + (double)((float)i * 0.5F), posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F) + (double)((float)i * 0.5F)) - (double)width, d, d1, d2);
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width - (double)((float)i * 0.5F), posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)((float)i * 0.5F) - (double)width, d, d1, d2);
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

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
        int i = 0;
        gem = new ItemStack(MoreCreepsAndWeirdos.skygem, 1);

        if (checkGem(gem))
        {
            i++;
        }

        gem = new ItemStack(MoreCreepsAndWeirdos.earthgem, 1);

        if (checkGem(gem))
        {
            i++;
        }

        gem = new ItemStack(MoreCreepsAndWeirdos.firegem, 1);

        if (checkGem(gem))
        {
            i++;
        }

        gem = new ItemStack(MoreCreepsAndWeirdos.healinggem, 1);

        if (checkGem(gem))
        {
            i++;
        }

        gem = new ItemStack(MoreCreepsAndWeirdos.mininggem, 1);

        if (checkGem(gem))
        {
            i++;
        }

        if (i == 5)
        {
            smoke();
            smoke();
            dropItem(MoreCreepsAndWeirdos.gemsword, 1);
            dropItem(MoreCreepsAndWeirdos.money, rand.nextInt(100) + 50);
        }
        else
        {
            dropItem(Items.iron_sword, 1);
            dropItem(Items.book, 1);
        }

        smoke();
        super.onDeath(damagesource);
    }

    public boolean checkGem(ItemStack itemstack)
    {
        
        Object obj = null;
        ItemStack aitemstack[] = ((EntityPlayer)(entityplayer)).inventory.mainInventory;
        boolean flag = false;
        int i = 0;

        do
        {
            if (i >= aitemstack.length)
            {
                break;
            }

            ItemStack itemstack1 = aitemstack[i];

            if (itemstack1 != null && itemstack1 == itemstack)
            {
                flag = true;
                break;
            }

            i++;
        }
        while (true);

        return flag;
    }

    static
    {
        defaultHeldItem = new ItemStack(MoreCreepsAndWeirdos.gemsword, 1);
    }
}
