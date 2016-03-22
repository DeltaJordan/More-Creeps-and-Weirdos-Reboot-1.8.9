package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityDigBug extends EntityMob
{
    private static final Item dropItems[];
    protected double attackRange;
    private int angerLevel;
    public int digstage;
    public int digtimer;
    public double holeX;
    public double holeY;
    public double holeZ;
    public double xx;
    public double yy;
    public double zz;
    public int holedepth;
    public int skinframe;
    public int lifespan;
    public int hunger;
    public int waittimer;
    public float modelsize;
    public String texture;
	private int attackTime;

    public CREEPSEntityDigBug(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/digbug0.png";
        angerLevel = 0;
        attackRange = 16D;
        setSize(0.5F, 1.2F);
        hunger = rand.nextInt(3) + 1;
        digstage = 0;
        digtimer = rand.nextInt(500) + 500;
        lifespan = 5000;
        holedepth = 0;
        modelsize = 1.0F;
        this.targetTasks.addTask(0, new CREEPSEntityDigBug.AIFindPlayerToAttack());
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
    }
    
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4D);
    }

    public float getShadowSize()
    {
        return 0.4F;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (lifespan >= 0 && holedepth > 0)
        {
            lifespan--;

            if (lifespan <= 0)
            {
                digtimer = rand.nextInt(20);
                xx = -1D;
                yy = holedepth;
                zz = -1D;
                digstage = 4;
            }
        }
        super.onUpdate();
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return lifespan < 0;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (prevPosX != posX || prevPosY != posY)
        {
            texture = (new StringBuilder()).append("/mob/creeps/digbug").append(String.valueOf(skinframe)).append(".png").toString();
            skinframe++;

            if (skinframe > 3)
            {
                skinframe = 0;
            }
        }

        if (digstage == 0 && posY < 90D && digtimer-- < 1)
        {
            int i = MathHelper.floor_double(posX);
            int i1 = MathHelper.floor_double(getBoundingBox().minY);
            int i2 = MathHelper.floor_double(posZ);
            Block l2 = worldObj.getBlockState(new BlockPos(i, i1 - 1, i2)).getBlock();
            holedepth = rand.nextInt(2) + 3;

            if (l2 == Blocks.grass)
            {
                if (checkHole(i, i1, i2, holedepth))
                {
                    digstage = 1;
                    holeX = i;
                    holeY = i1;
                    holeZ = i2;
                    xx = 0.0D;
                    yy = 1.0D;
                    zz = 0.0D;
                }
                else
                {
                    digtimer = rand.nextInt(200);
                }
            }
        }

        if (digstage == 1)
        {
            int j = MathHelper.floor_double(posX);
            int j1 = MathHelper.floor_double(getBoundingBox().minY);
            int j2 = MathHelper.floor_double(posZ);
            worldObj.setBlockToAir(new BlockPos(j, j1, j2));
            worldObj.setBlockToAir(new BlockPos(j, j1 + 1, j2));

            if (posX < holeX + xx)
            {
                motionX += 0.20000000298023224D;
            }
            else
            {
                motionX -= 0.20000000298023224D;
            }

            if (posZ < holeZ + zz)
            {
                motionZ += 0.20000000298023224D;
            }
            else
            {
                motionZ -= 0.20000000298023224D;
            }
            if(worldObj.isRemote)
            {
            	MoreCreepsAndWeirdos.proxy.dirtDigBug(worldObj, this, rand, 1);
            }

            if (digtimer-- < 1)
            {
                digtimer = rand.nextInt(20);
                setPosition((int)(holeX + xx), (int)(holeY - yy), (int)(holeZ + zz));
                Block i3 = worldObj.getBlockState(new BlockPos((int)(holeX + xx), (int)(holeY - yy), (int)(holeZ + zz))).getBlock();

                if (rand.nextInt(50) == 0)
                {
                    i3 = Blocks.coal_ore;
                }

                if (i3 != Blocks.sand && i3 != Blocks.log)
                {
                    for (int j3 = 0; j3 < rand.nextInt(2) + 1; j3++)
                    {
                        EntityItem entityitem1 = new EntityItem(worldObj, (int)(holeX + xx), (int)((holeY - yy) + 1.0D), (int)(holeZ + zz), new ItemStack(i3, 1, 1));
                        worldObj.spawnEntityInWorld(entityitem1);
                    }
                }

                worldObj.setBlockToAir(new BlockPos((int)(holeX + xx), (int)(holeY - yy), (int)(holeZ + zz)));

                if (zz++ > 1.0D)
                {
                    zz = 0.0D;
                    setPosition((int)(holeX + xx), (int)(holeY - yy), (int)(holeZ + zz));

                    if (xx++ > 1.0D)
                    {
                        xx = 0.0D;
                        setPosition((int)(holeX + xx), (int)(holeY - yy), (int)(holeZ + zz));

                        if (yy++ > (double)holedepth)
                        {
                            for (int k3 = 0; k3 < rand.nextInt(8) + 5; k3++)
                            {
                                int l3 = rand.nextInt(40) + 40;
                                int i4 = rand.nextInt(40) + 40;

                                if (rand.nextInt(1) == 0)
                                {
                                    l3 *= -1;
                                }

                                if (rand.nextInt(1) == 0)
                                {
                                    i4 *= -1;
                                }

                                CREEPSEntityBubbleScum creepsentitybubblescum = new CREEPSEntityBubbleScum(worldObj);
                                creepsentitybubblescum.setLocationAndAngles(posX + (double)l3, posY + (double)holedepth + 2D, posZ + (double)i4, rotationYaw, 0.0F);
                                creepsentitybubblescum.motionX = rand.nextFloat() * 1.5F;
                                creepsentitybubblescum.motionY = rand.nextFloat() * 2.0F;
                                creepsentitybubblescum.motionZ = rand.nextFloat() * 1.5F;
                                creepsentitybubblescum.fallDistance = -25F;
                                if(!worldObj.isRemote)
                                worldObj.spawnEntityInWorld(creepsentitybubblescum);
                            }

                            digstage = 2;
                            double moveSpeed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
                            moveSpeed = 0.0D;
                            lifespan = 5000;
                            motionY = 0.44999998807907104D;
                            setPosition((int)(holeX + 1.0D), (int)(holeY - yy), (int)(holeZ + 1.0D));
                            digtimer = rand.nextInt(5) + 5;
                        }
                    }
                }
            }
        }

        if (digstage == 2 && digtimer-- < 1)
        {
            digtimer = rand.nextInt(20);

            for (int k = 0; k < 20 + digtimer; k++)
            {
                MoreCreepsAndWeirdos.proxy.bubble(worldObj, this);
            }
            

            digtimer = 50;
            List list = null;
            list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(5D, 1.0D, 5D));

            for (int k1 = 0; k1 < list.size(); k1++)
            {
                Entity entity = (Entity)list.get(k1);

                if ((entity != null) & (entity instanceof CREEPSEntityBubbleScum))
                {
                    entity.setDead();
                    double moveSpeed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue();
                    moveSpeed = 0.4F;
                    motionY = 0.60000002384185791D;
                    digstage = 3;
                    digtimer = 50;
                }
            }
        }

        if (digstage == 3)
        {
            int l = rand.nextInt(25) + 15;
            worldObj.playSoundAtEntity(this, "morecreeps:digbugeat", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);

            for (int l1 = 0; l1 < l; l1++)
            {
            	if(worldObj.isRemote)
            	{
                    for (int k2 = 0; k2 < 45; k2++)
                    {
                		MoreCreepsAndWeirdos.proxy.dirtDigBug(worldObj, this, rand, k2);
                    }
            	}

                EntityItem entityitem = entityDropItem(new ItemStack(Items.cookie, 1, 0), 1.0F);
                entityitem.motionY += rand.nextFloat() * 2.0F + 3F;
                entityitem.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.33F;
                entityitem.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.33F;
            }

            if (hunger-- < 1)
            {
                digtimer = rand.nextInt(20);
                xx = -1D;
                yy = holedepth;
                zz = -1D;
                digstage = 4;
                worldObj.playSoundAtEntity(this, "morecreeps:digbugfull", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            }
            else
            {
                digstage = 2;
                digtimer = 50;
            }
        }

        if (digstage == 4)
        {
            if (posX < holeX + xx)
            {
                motionX += 0.20000000298023224D;
            }
            else
            {
                motionX -= 0.20000000298023224D;
            }

            if (posZ < holeZ + zz)
            {
                motionZ += 0.20000000298023224D;
            }
            else
            {
                motionZ -= 0.20000000298023224D;
            }

            if(worldObj.isRemote)
            {
            	MoreCreepsAndWeirdos.proxy.dirtDigBug(worldObj, this, rand, 1);
            }

            if (digtimer-- < 1)
            {
                digtimer = rand.nextInt(10);

                if (worldObj.getBlockState(new BlockPos((int)(holeX + xx), (int)(holeY - yy), (int)(holeZ + zz))).getBlock() == Blocks.air)
                {
                    worldObj.setBlockState(new BlockPos((int)(holeX + xx), (int)(holeY - yy), (int)(holeZ + zz)), Blocks.dirt.getDefaultState());
                }

                if (zz++ > 2D)
                {
                    zz = -1D;
                    setPosition((int)(holeX + xx), (int)(holeY - yy) + 1, (int)(holeZ + zz));

                    if (xx++ > 2D)
                    {
                        xx = -1D;
                        setPosition((int)(holeX + xx), (int)(holeY - yy) + 1, (int)(holeZ + zz));

                        if (yy-- == 1.0D)
                        {
                            digstage = 0;
                            digtimer = rand.nextInt(8000) + 1000;
                            setPosition((int)(holeX + 1.0D), (int)(holeY + yy + 1.0D), (int)(holeZ + 1.0D));
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if this entity is inside of an opaque block
     */
    public boolean isEntityInsideOpaqueBlock()
    {
        if (digstage == 1 || digstage == 4)
        {
            return false;
        }
        else
        {
            return super.isEntityInsideOpaqueBlock();
        }
    }

    public boolean checkHole(int i, int j, int k, int l)
    {
        for (int i1 = 0; i1 < l; i1++)
        {
            for (int j1 = 0; j1 < 3; j1++)
            {
                for (int k1 = 0; k1 < 3; k1++)
                {
                    Block l1 = worldObj.getBlockState(new BlockPos(i + j1, j - i1 - 1, k + k1)).getBlock();

                    if (l1 == Blocks.air)
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity entity, float f)
    {
        double d = entity.posX - posX;
        double d1 = entity.posZ - posZ;
        float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
        motionX = (d / (double)f1) * 0.40000000000000002D * 0.10000000192092896D + motionX * 0.18000000098023225D;
        motionZ = (d1 / (double)f1) * 0.40000000000000002D * 0.070000001920928964D + motionZ * 0.18000000098023225D;

        if ((double)f < 2D - (1.0D - (double)modelsize) && entity.getBoundingBox().maxY > getBoundingBox().minY && entity.getBoundingBox().minY < getBoundingBox().maxY)
        {
            attackTime = 10;
            entity.motionX = -(motionX * 3D);
            entity.motionY = rand.nextFloat() * 2.133F;
            entity.motionZ = -(motionZ * 3D);
            this.attackEntityAsMob(entity);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setShort("Anger", (short)angerLevel);
        nbttagcompound.setInteger("DigStage", digstage);
        nbttagcompound.setInteger("DigTimer", digtimer);
        nbttagcompound.setInteger("LifeSpan", lifespan);
        nbttagcompound.setInteger("HoleDepth", holedepth);
        nbttagcompound.setDouble("XX", xx);
        nbttagcompound.setDouble("YY", yy);
        nbttagcompound.setDouble("ZZ", zz);
        nbttagcompound.setDouble("holeX", holeX);
        nbttagcompound.setDouble("holeY", holeY);
        nbttagcompound.setDouble("holeZ", holeZ);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        angerLevel = nbttagcompound.getShort("Anger");
        digstage = nbttagcompound.getInteger("DigStage");
        digtimer = nbttagcompound.getInteger("DigTimer");
        lifespan = nbttagcompound.getInteger("LifeSpan");
        holedepth = nbttagcompound.getInteger("HoleDepth");
        xx = nbttagcompound.getDouble("XX");
        yy = nbttagcompound.getDouble("YY");
        zz = nbttagcompound.getDouble("ZZ");
        holeX = nbttagcompound.getDouble("holeX");
        holeY = nbttagcompound.getDouble("holeY");
        holeZ = nbttagcompound.getDouble("holeZ");
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
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        int j1 = worldObj.countEntities(CREEPSEntityDigBug.class);
        return (i1 == Blocks.grass || i1 == Blocks.dirt) && i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.double_stone_slab && i1 != Blocks.stone_slab && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0 && worldObj.canSeeSky(new BlockPos(i, j, k)) && rand.nextInt(25) == 0 && /*l > 10 &&*/ j1 < 10;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    public boolean attackEntityFrom(Entity entity, float i)
    {
        if (entity instanceof EntityPlayer)
        {
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(32D, 32D, 32D));

            for (int j = 0; j < list.size(); j++)
            {
                Entity entity1 = (Entity)list.get(j);

                if (entity1 instanceof CREEPSEntityDigBug)
                {
                    CREEPSEntityDigBug creepsentitydigbug = (CREEPSEntityDigBug)entity1;
                    creepsentitydigbug.becomeAngryAt(entity);
                }
            }

            becomeAngryAt(entity);
        }

        return super.attackEntityFrom(DamageSource.causeMobDamage(this), i);
    }

    class AIFindPlayerToAttack extends EntityAINearestAttackableTarget
    {
        public AIFindPlayerToAttack()
        {
            super(CREEPSEntityDigBug.this, EntityPlayer.class, true);
        }
        
        public void updateTask()
        {
        	EntityLivingBase target = CREEPSEntityDigBug.this.getAttackTarget();
        	float f = getDistanceToEntity(target);
        	attackEntity(target, f);
        }
        
        public boolean shouldExecute()
        {
            if (angerLevel > 0)
            {
                angerLevel--;
            }

            if (angerLevel == 0)
            {
                return false;
            }
            else
            {
                return super.shouldExecute();
            }
        }
    }
    
    private void becomeAngryAt(Entity entity)
    {
        setRevengeTarget((EntityLivingBase) entity);
        angerLevel = 400 + rand.nextInt(400);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        if (digstage == 0)
        {
            return "morecreeps:digbug";
        }

        if (digstage == 1 || digstage == 4)
        {
            return "morecreeps:digbugdig";
        }

        if (digstage == 2)
        {
            return "morecreeps:digbugcall";
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
        return "morecreeps:digbughurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:digbugdeath";
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected Item getDropItemId()
    {
        return dropItems[rand.nextInt(dropItems.length)];
    }

    static
    {
        dropItems = (new Item[]
                {
                    Item.getItemFromBlock(Blocks.cobblestone), Item.getItemFromBlock(Blocks.gravel), Item.getItemFromBlock(Blocks.cobblestone), Item.getItemFromBlock(Blocks.gravel), Item.getItemFromBlock(Blocks.iron_ore), Item.getItemFromBlock(Blocks.mossy_cobblestone)
                });
    }
}
