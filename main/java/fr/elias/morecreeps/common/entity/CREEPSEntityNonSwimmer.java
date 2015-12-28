package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
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

public class CREEPSEntityNonSwimmer extends EntityAnimal
{
    protected double attackRange;
    private int waittime;
    public boolean swimming;
    public float modelsize;
    public boolean saved;
    public int timeonland;
    public boolean towel;
    public boolean wet;
    public String texture;

    public CREEPSEntityNonSwimmer(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/nonswimmer.png";
        attackRange = 16D;
        timeonland = 0;
        wet = false;
        swimming = false;
        saved = false;
        towel = false;
        waittime = rand.nextInt(1500) + 500;
        modelsize = 1.0F;
        ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(3, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
    }
    
    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityAnimal createChild(EntityAgeable entityanimal)
    {
        return new CREEPSEntityNonSwimmer(worldObj);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (inWater)
        {
        	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.05D);
            swimming = true;
            wet = true;
        }
        else
        {
        	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
            int i = MathHelper.floor_double(posX);
            int k = MathHelper.floor_double(getEntityBoundingBox().minY);
            int i1 = MathHelper.floor_double(posZ);
            BlockPos bp = new BlockPos(i, k, i1);
            Block k1 = worldObj.getBlockState(bp).getBlock();

            if (k1 != Blocks.flowing_water && k1 != Blocks.water)
            {
                swimming = false;
                EntityPlayer entityplayersp = worldObj.getClosestPlayerToEntity(this, 3F);

                if (entityplayersp != null)
                {
                    float f = entityplayersp.getDistanceToEntity(this);

                    if (f < 4F && !saved && timeonland++ > 155 && wet)
                    {
                        giveReward((EntityPlayerMP)entityplayersp);
                    }
                }
            }
        }

        if (saved && rand.nextInt(100) == 0 && !towel && onGround)
        {
            int j = MathHelper.floor_double(posX);
            int l = MathHelper.floor_double(getEntityBoundingBox().minY);
            int j1 = MathHelper.floor_double(posZ);
            BlockPos bp = new BlockPos(j, l, j1);
            Block l1 = worldObj.getBlockState(bp).getBlock();

            if (l1 != Blocks.water && l1 != Blocks.flowing_water)
            {
                towel = true;
                CREEPSEntityTowel creepsentitytowel = new CREEPSEntityTowel(worldObj);
                creepsentitytowel.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
                int i2 = rand.nextInt(6);
                creepsentitytowel.texture = (new StringBuilder()).append("morecreeps:textures/entity/towel").append(String.valueOf(i2)).append(".png").toString();
                //creepsentitytowel.basetexture = (new StringBuilder()).append("/mob/creeps/towel").append(String.valueOf(i2)).append(".png").toString();
                worldObj.spawnEntityInWorld(creepsentitytowel);
                mountEntity(creepsentitytowel);
            }
        }

        super.onLivingUpdate();
    }

    public void giveReward(EntityPlayerMP entityplayersp)
    {
        if (!entityplayersp.getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievenonswimmer))
        {
            confetti(entityplayersp);
            worldObj.playSoundAtEntity(entityplayersp, "morecreeps:achievement", 1.0F, 1.0F);
            entityplayersp.addStat(MoreCreepsAndWeirdos.achievenonswimmer, 1);
        }

        if (rand.nextInt(5) == 0)
        {
            worldObj.playSoundAtEntity(this, "morecreeps:nonswimmersorry", 1.0F, 1.0F);
            return;
        }

        worldObj.playSoundAtEntity(this, "morecreeps:nonswimmerreward", 1.0F, 1.0F);
        saved = true;
        int i = rand.nextInt(5) + 1;
        faceEntity(entityplayersp, 0.0F, 0.0F);

        if(!worldObj.isRemote)
        {
            if (entityplayersp != null)
            {
                EntityItem entityitem = null;

                switch (i)
                {
                    case 1:
                        entityitem = entityDropItem(new ItemStack(MoreCreepsAndWeirdos.lolly, rand.nextInt(2) + 1, 0), 1.0F);
                        break;

                    case 2:
                        entityitem = entityDropItem(new ItemStack(MoreCreepsAndWeirdos.lolly, 1, 0), 1.0F);
                        break;

                    case 3:
                        entityitem = entityDropItem(new ItemStack(MoreCreepsAndWeirdos.money, rand.nextInt(10) + 1, 0), 1.0F);
                        break;

                    case 4:
                        entityitem = entityDropItem(new ItemStack(MoreCreepsAndWeirdos.money, rand.nextInt(30) + 1, 0), 1.0F);
                        break;

                    case 5:
                        entityitem = entityDropItem(new ItemStack(Items.gold_ingot, 1, 0), 1.0F);
                        break;

                    default:
                        entityitem = entityDropItem(new ItemStack(MoreCreepsAndWeirdos.money, rand.nextInt(3) + 1, 0), 1.0F);
                        break;
                }

                double d = -MathHelper.sin((entityplayersp.rotationYaw * (float)Math.PI) / 180F);
                double d1 = MathHelper.cos((entityplayersp.rotationYaw * (float)Math.PI) / 180F);
                entityitem.posX = entityplayersp.posX + d * 0.5D;
                entityitem.posY = entityplayersp.posY + 0.5D;
                entityitem.posZ = entityplayersp.posZ + d1 * 0.5D;
            }
        }
    }

    /*public int[] findTree(Entity entity, Double double1)
    {
        AxisAlignedBB axisalignedbb = entity.boundingBox.expand(double1.doubleValue(), double1.doubleValue(), double1.doubleValue());
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);

        for (int k1 = i; k1 < j; k1++)
        {
            for (int l1 = k; l1 < l; l1++)
            {
                for (int i2 = i1; i2 < j1; i2++)
                {
                    int j2 = worldObj.getBlockId(k1, l1, i2);

                    if (j2 != 0 && (j2 == Block.waterStill || j2 == Block.waterMoving))
                    {
                        return (new int[]
                                {
                                    k1, l1, i2
                                });
                    }
                }
            }
        }

        return (new int[]
                {
                    -1, 0, 0
                });
    }*/

    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
    public float func_180484_a(BlockPos bp)
    {
        if (worldObj.getBlockState(bp.down()).getBlock() == Blocks.water || worldObj.getBlockState(bp.down()) == Blocks.flowing_water)
        {
            return 10F;
        }
        else
        {
            return -(float)bp.getY();
        }
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        if (ridingEntity instanceof CREEPSEntityTowel)
        {
            return - 1.85D;
        }
        else
        {
            return 0.0D;
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
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        Entity entity = damagesource.getEntity();

        if (entity instanceof EntityPlayer)
        {
            motionY = 0.25D;

            if (i == 1)
            {
                i = 0;
            }
        }

        mountEntity(null);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.55D);
        super.attackEntityFrom(DamageSource.causeMobDamage(this), i);
        return true;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setFloat("modelsize", modelsize);
        nbttagcompound.setBoolean("saved", saved);
        nbttagcompound.setBoolean("towel", towel);
        nbttagcompound.setBoolean("wet", wet);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        modelsize = nbttagcompound.getFloat("modelsize");
        saved = nbttagcompound.getBoolean("saved");
        towel = nbttagcompound.getBoolean("towel");
        wet = nbttagcompound.getBoolean("wet");
    }

    /**
     * knocks back this entity
     */
    public void knockBack(Entity entity, float i, double d, double d1)
    {
    	if(entity instanceof EntityPlayer)
    	{
            double d2 = -MathHelper.sin((entity.rotationYaw * (float)Math.PI) / 180F);
            double d3 = MathHelper.cos((entity.rotationYaw * (float)Math.PI) / 180F);
            motionX = d2 * 0.5D;
            motionZ = d3 * 0.5D;
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
        BlockPos bp = new BlockPos(i, j, k);
        Block i1 = worldObj.getBlockState(bp.down()).getBlock();
        int j1 = worldObj.countEntities(CREEPSEntityNonSwimmer.class);
        return (i1 == Blocks.flowing_water || i1 == Blocks.water) && i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.double_stone_slab && i1 != Blocks.stone_slab && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).size() == 0 && worldObj.canSeeSky(bp) && rand.nextInt(25) == 0 && /*l > 9 && */j1 < 4;
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
        if (swimming)
        {
            return "morecreeps:nonswimmer";
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
        return "morecreeps:nonswimmerhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:nonswimmerdeath";
    }

    public void confetti(EntityPlayer player)
    {
        double d = -MathHelper.sin((player.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((player.rotationYaw * (float)Math.PI) / 180F);
        CREEPSEntityTrophy creepsentitytrophy = new CREEPSEntityTrophy(worldObj);
        creepsentitytrophy.setLocationAndAngles(player.posX + d * 3D, player.posY - 2D, player.posZ + d1 * 3D, player.rotationYaw, 0.0F);
        worldObj.spawnEntityInWorld(creepsentitytrophy);
    }
}
