package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import fr.elias.morecreeps.client.config.CREEPSConfig;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import fr.elias.morecreeps.common.Reference;

public class CREEPSEntityBum extends EntityMob
{
	ResourceLocation resource;
    public boolean rideable;
    protected double attackRange;
    private int angerLevel;
    private int value;
    private boolean bumgave;
    public int timetopee;
    public float bumrotation;
    public float modelsize;
    public ResourceLocation texture;

    public CREEPSEntityBum(World world)
    {
        super(world);
        //The texture reference
        texture = new ResourceLocation(Reference.MOD_ID,
        	    Reference.TEXTURE_PATH_ENTITES + Reference.TEXTURE_BUM);
        angerLevel = 0;
        attackRange = 16D;
        bumgave = false;
        timetopee = rand.nextInt(900) + 500;
        bumrotation = 999F;
        modelsize = 1.0F;
    }
    public void onUpdate()
    {
        super.onUpdate();
    }
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
    }

    public String pingText()
    {
        return (new StringBuilder()).append("angerLevel ").append(angerLevel).toString();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        double moveSpeed = this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue();
        if (timetopee-- < 0 && !bumgave && CREEPSConfig.publicUrination)
        {
            isJumping = false;

            if (bumrotation == 999F)
            {
                bumrotation = rotationYaw;
            }

            rotationYaw = bumrotation;
            moveSpeed = 0.0F;

            if (!onGround)
            {
                motionY -= 0.5D;
            }
            
            if(worldObj.isRemote)
            {
            	MoreCreepsAndWeirdos.proxy.pee(worldObj, posX, posY, posZ, rotationYaw, modelsize);
            }

            if (timetopee < -200)
            {
                timetopee = rand.nextInt(600) + 600;
                bumrotation = 999F;
                int j = MathHelper.floor_double(posX);
                int k = MathHelper.floor_double(getBoundingBox().minY);
                int l = MathHelper.floor_double(posZ);

                for (int i1 = -1; i1 < 2; i1++)
                {
                    for (int j1 = -1; j1 < 2; j1++)
                    {
                        if (rand.nextInt(3) != 0)
                        {
                            continue;
                        }

                        Block k1 = worldObj.getBlockState(new BlockPos(j + j1, k - 1, l - i1)).getBlock();
                        Block l1 = worldObj.getBlockState(new BlockPos(j + j1, k, l - i1)).getBlock();

                        if (rand.nextInt(2) == 0)
                        {
                            if ((k1 == Blocks.grass || k1 == Blocks.dirt) && l1 == Blocks.air)
                            {
                                worldObj.setBlockState(new BlockPos(j + j1, k, l - i1), Blocks.yellow_flower.getDefaultState());
                            }

                            continue;
                        }

                        if ((k1 == Blocks.grass || k1 == Blocks.dirt) && l1 == Blocks.air)
                        {
                        	worldObj.setBlockState(new BlockPos(j + j1, k, l - i1), Blocks.red_flower.getDefaultState());
                        }
                    }
                }
            }
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        Entity entity = damagesource.getEntity();

        if (entity instanceof EntityPlayer)
        {
        	setRevengeTarget((EntityLivingBase) entity);
            becomeAngryAt(entity);
        }

        timetopee = rand.nextInt(900) + 500;
        bumrotation = 999F;
        super.attackEntityFrom(DamageSource.causeMobDamage(this), i);
        return true;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setShort("Anger", (short)angerLevel);
        nbttagcompound.setBoolean("BumGave", bumgave);
        nbttagcompound.setInteger("TimeToPee", timetopee);
        nbttagcompound.setFloat("BumRotation", bumrotation);
        nbttagcompound.setFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        angerLevel = nbttagcompound.getShort("Anger");
        bumgave = nbttagcompound.getBoolean("BumGave");
        timetopee = nbttagcompound.getInteger("TimeToPee");
        bumrotation = nbttagcompound.getFloat("BumRotation");
        modelsize = nbttagcompound.getFloat("ModelSize");

        if (bumgave)
        {
            texture = new ResourceLocation(Reference.MOD_ID, Reference.TEXTURE_PATH_ENTITES +
            		Reference.TEXTURE_BUM_DRESSED);
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
        int l = worldObj.getBlockLightOpacity(getPosition());
        Block i1 = worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock();
        return i1 != Blocks.cobblestone && i1 != Blocks.log && i1 != Blocks.stone_slab && i1 != Blocks.double_stone_slab && i1 != Blocks.planks && i1 != Blocks.wool && worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0 && worldObj.canSeeSky(new BlockPos(i, j, k)) && rand.nextInt(10) == 0 && l > 8;
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }


    /**TEMPORARILY REMOVED TO FIND AN ALTERNATIVE TO THESE FUNCTIONS**/
    /*protected Entity findPlayerToAttack()
    {
        if (angerLevel == 0)
        {
            return null;EntityPigZombie
        }
        else
        {
            return super.findPlayerToAttack();
        }
    }

    
    
    public boolean canAttackEntity(Entity entity, int i)
    {
        if (entity instanceof EntityPlayer)
        {
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(32D, 32D, 32D));

            for (int j = 0; j < list.size(); j++)
            {
                Entity entity1 = (Entity)list.get(j);

                if (entity1 instanceof CREEPSEntityBum)
                {
                    CREEPSEntityBum creepsentitybum = (CREEPSEntityBum)entity1;
                    creepsentitybum.becomeAngryAt(entity);
                }
            }

            becomeAngryAt(entity);
        }

        return super.attackEntityFrom(DamageSource.causeMobDamage(this), i);
    }

    
    private void becomeAngryAt(Entity entity)
    {
        entityToAttack = entity;
        angerLevel = 400 + rand.nextInt(400);
    }*/

    /**Simple try if it work or not**/
    private void becomeAngryAt(Entity p_70835_1_)
    {
        this.angerLevel = 400 + this.rand.nextInt(400);

        if (p_70835_1_ instanceof EntityLivingBase)
        {
            this.setRevengeTarget((EntityLivingBase)p_70835_1_);
        }
    }
    
    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if (!bumgave && angerLevel == 0)
        {
            if (itemstack != null && (itemstack.getItem() == Items.diamond || itemstack.getItem() == Items.gold_ingot || itemstack.getItem() == Items.iron_ingot))
            {
                if (itemstack.getItem() == Items.iron_ingot)
                {
                    value = rand.nextInt(2) + 1;
                }
                else if (itemstack.getItem() == Items.gold_ingot)
                {
                    value = rand.nextInt(5) + 1;
                }
                else if (itemstack.getItem() == Items.diamond)
                {
                    value = rand.nextInt(10) + 1;
                }

                if (itemstack.stackSize - 1 == 0)
                {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }
                else
                {
                    itemstack.stackSize--;
                }

                for (int i = 0; i < 4; i++)
                {
                    for (int i1 = 0; i1 < 10; i1++)
                    {
                        double d1 = rand.nextGaussian() * 0.02D;
                        double d3 = rand.nextGaussian() * 0.02D;
                        double d6 = rand.nextGaussian() * 0.02D;
                        worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height) + (double)i, (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d1, d3, d6);
                    }
                }

                texture = new ResourceLocation(Reference.MOD_ID, Reference.TEXTURE_PATH_ENTITES +
                		Reference.TEXTURE_BUM_DRESSED);
                angerLevel = 0;
                //findPlayerToAttack();

                if (rand.nextInt(5) == 0)
                {
                    worldObj.playSoundAtEntity(this, "morecreeps:bumsucker", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    bumgave = true;
                }
                else
                {
                    worldObj.playSoundAtEntity(this, "morecreeps:bumthankyou", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    bumgave = true;

                    for (int j = 0; j < 10; j++)
                    {
                        double d = rand.nextGaussian() * 0.02D;
                        double d2 = rand.nextGaussian() * 0.02D;
                        double d5 = rand.nextGaussian() * 0.02D;
                        worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d2, d5);
                    }

                    for (int k = 0; k < value; k++)
                    {
                        dropItem(Item.getItemById(rand.nextInt(95)), 1);
                        dropItem(Items.iron_shovel, 1);
                    }

                    return true;
                }
            }
            else if (itemstack != null)
            {
                if (timetopee > 0)
                {
                    worldObj.playSoundAtEntity(this, "morecreeps:bumdontwant", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }
                else if (itemstack != null && (itemstack.getItem() == Item.getItemFromBlock(Blocks.yellow_flower) || itemstack.getItem() == Item.getItemFromBlock(Blocks.red_flower)))
                {
                	if(!((EntityPlayerMP)entityplayer).getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievebumflower))
                	{
                    	worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                    	entityplayer.addStat(MoreCreepsAndWeirdos.achievebumflower, 1);
                    	confetti(entityplayer);
                	}

                    worldObj.playSoundAtEntity(this, "morecreeps:bumthanks", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    timetopee = rand.nextInt(1900) + 1500;

                    if (itemstack.stackSize - 1 == 0)
                    {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    }
                    else
                    {
                        itemstack.stackSize--;
                    }
                }
                else if (itemstack != null && itemstack.getItem() == Items.bucket)
                {
                    if (!((EntityPlayerMP)entityplayer).getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievebumpot) && ((EntityPlayerMP)entityplayer).getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievebumflower))
                    {
                        worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                        entityplayer.addStat(MoreCreepsAndWeirdos.achievebumpot, 1);
                        confetti(entityplayer);
                    }
                    entityplayer.addStat(MoreCreepsAndWeirdos.achievebumpot, 1);
                    worldObj.playSoundAtEntity(this, "morecreeps:bumthanks", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    timetopee = rand.nextInt(1900) + 1500;

                    if (itemstack.stackSize - 1 == 0)
                    {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    }
                    else
                    {
                        itemstack.stackSize--;
                    }
                }
                else if (itemstack != null && itemstack.getItem() == Items.lava_bucket)
                {
                    if (!((EntityPlayerMP)entityplayer).getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievebumlava) && ((EntityPlayerMP)entityplayer).getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievebumpot))
                    {
                        worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                        entityplayer.addStat(MoreCreepsAndWeirdos.achievebumlava, 1);
                        confetti(entityplayer);
                    }

                    entityplayer.addStat(MoreCreepsAndWeirdos.achievebumpot, 1);
                    worldObj.playSoundAtEntity(this, "morecreeps:bumthanks", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    timetopee = rand.nextInt(1900) + 1500;

                    if (itemstack.stackSize - 1 == 0)
                    {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    }
                    else
                    {
                        itemstack.stackSize--;
                    }

                    int l = (int)posX;
                    int j1 = (int)posY;
                    int k1 = (int)posZ;

                    if (rand.nextInt(4) == 0)
                    {
                        for (int l1 = 0; l1 < rand.nextInt(3) + 1; l1++)
                        {
                            Blocks.obsidian.dropBlockAsItem(worldObj, new BlockPos(l, j1, k1), worldObj.getBlockState(new BlockPos(l, j1, k1)), 0);
                        }
                    }

                    for (int i2 = 0; i2 < 15; i2++)
                    {
                        double d4 = (float)l + worldObj.rand.nextFloat();
                        double d7 = (float)j1 + worldObj.rand.nextFloat();
                        double d8 = (float)k1 + worldObj.rand.nextFloat();
                        double d9 = d4 - posX;
                        double d10 = d7 - posY;
                        double d11 = d8 - posZ;
                        double d12 = MathHelper.sqrt_double(d9 * d9 + d10 * d10 + d11 * d11);
                        d9 /= d12;
                        d10 /= d12;
                        d11 /= d12;
                        double d13 = 0.5D / (d12 / 10D + 0.10000000000000001D);
                        d13 *= worldObj.rand.nextFloat() * worldObj.rand.nextFloat() + 0.3F;
                        d9 *= d13;
                        d10 *= d13;
                        d11 *= d13;
                        worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (d4 + posX * 1.0D) / 2D, (d7 + posY * 1.0D) / 2D + 2D, (d8 + posZ * 1.0D) / 2D, d9, d10, d11);
                        worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d4, d7, d8, d9, d10, d11);
                    }

                    if (rand.nextInt(4) == 0)
                    {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.bucket));
                    }
                }
                else if (!bumgave)
                {
                    worldObj.playSoundAtEntity(this, "morecreeps:bumpee", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                }
            }
        }
        else
        {
            worldObj.playSoundAtEntity(this, "morecreeps:bumleavemealone", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
        }

        return false;
    }

    public void confetti(EntityPlayer player)
    {
    	MoreCreepsAndWeirdos.proxy.confettiA(player, worldObj);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        if (timetopee > 0 || bumgave || !CREEPSConfig.publicUrination)
        {
            return "morecreeps:bum";
        }
        else
        {
            return "morecreeps:bumlivingpee";
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:bumhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:bumdeath";
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
        super.onDeath(damagesource);
        if(!worldObj.isRemote)
        {
            dropItem(Items.cooked_porkchop, 1);
        }
    }
}
