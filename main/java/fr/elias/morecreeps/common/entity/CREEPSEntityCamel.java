package fr.elias.morecreeps.common.entity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.client.particles.CREEPSFxSpit;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityCamel extends EntityMob
{
    public int galloptime;
    public boolean tamed;
    public String texture;
    public EntityPlayer owner;
    public boolean used;
    public int interest;
    public String basetexture;
    protected double attackrange;
    protected int attack;

    public int spittimer;
    public int tamedcookies;
    public int basehealth;
    public float modelsize;
    public String name;
    static final String Names[] =
    {
        "Stanley", "Cid", "Hunchy", "The Heat", "Herman the Hump", "Dr. Hump", "Little Lousie", "Spoony G", "Mixmaster C", "The Maestro",
        "Duncan the Dude", "Charlie Camel", "Chip", "Charles Angstrom III", "Mr. Charles", "Cranky Carl", "Carl the Rooster", "Tiny the Peach", "Desert Dan", "Dungby",
        "Doofus"
    };
    static final String camelTextures[] =
    {
        "/mob/creeps/camel.png", "/mob/creeps/camel.png", "/mob/creeps/camel.png", "/mob/creeps/camel.png", "/mob/creeps/camelwhite.png", "/mob/creeps/camelblack.png", "/mob/creeps/camelbrown.png", "/mob/creeps/camelgrey.png", "/mob/creeps/camel.png", "/mob/creeps/camel.png",
        "/mob/creeps/camel.png", "/mob/creeps/camel.png", "/mob/creeps/camelwhite.png"
    };

    public CREEPSEntityCamel(World world)
    {
        super(world);
        basetexture = camelTextures[rand.nextInt(camelTextures.length)];
        texture = basetexture;
        setSize(width * 1.5F, height * 4F);
        attack = 2;
        basehealth = 30;
        attackrange = 16D;
        interest = 0;
        spittimer = 30;
        name = "";
        tamed = false;
        tamedcookies = rand.nextInt(7) + 1;
        modelsize = 1.75F;
    }
    
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2D);
    }

    /**
     * Checks if this entity is inside of an opaque block
     */
    public boolean isEntityInsideOpaqueBlock()
    {
        if (riddenByEntity != null)
        {
            return false;
        }
        else
        {
            return super.isEntityInsideOpaqueBlock();
        }
    }

    /**
     * Takes a coordinate in and returns a weight to determine how likely this creature will try to path to the block.
     * Args: x, y, z
     */
    //getBlockPathHeight in 1.8
    public float func_180484_a(BlockPos bp)
    {
        if (worldObj.getBlockState(bp.down()).getBlock() == Blocks.sand || worldObj.getBlockState(bp.down()).getBlock() == Blocks.gravel)
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
        if (ridingEntity instanceof EntityPlayer)
        {
            return (double)(this.getYOffset() + 1.1F);
        }
        else
        {
            return (double)this.getYOffset();
        }
    }

    public void updateRiderPosition()
    {
        if (riddenByEntity == null)
        {
            return;
        }

        if (riddenByEntity instanceof EntityPlayer)
        {
            riddenByEntity.setPosition(posX, (posY + 4.5D) - (double)((1.75F - modelsize) * 2.0F), posZ);
            return;
        }

        if (riddenByEntity instanceof CREEPSEntityCamelJockey)
        {
            riddenByEntity.setPosition(posX, (posY + 3.1500000953674316D) - (double)((1.75F - modelsize) * 2.0F), posZ);
            return;
        }
        else
        {
            return;
        }
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return !tamed;
    }

    public void onLivingUpdate()
    {
        if (modelsize > 1.75F)
        {
            ignoreFrustumCheck = true;
        }
    }
        public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
        {
            if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase)
            {
                this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
                this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
                p_70612_1_ = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5F;
                p_70612_2_ = ((EntityLivingBase)this.riddenByEntity).moveForward;

                if (p_70612_2_ <= 0.0F)
                {
                    p_70612_2_ *= 0.25F;
                }

                if (this.onGround)
                {
                    p_70612_1_ = 0.0F;
                    p_70612_2_ = 0.0F;
                }

                if (this.onGround)
                {
                    this.motionY = 0.43;

                    if (this.isPotionActive(Potion.jump))
                    {
                        this.motionY += (double)((float)(this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
                    }

                    this.setJumping(true);
                    this.isAirBorne = true;

                    if (p_70612_2_ > 0.0F)
                    {
                        float f2 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F);
                        float f3 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F);
                        this.motionX += (double)(-0.4F * f2);
                        this.motionZ += (double)(0.4F * f3);
                    }

                    net.minecraftforge.common.ForgeHooks.onLivingJump(this);
                }

                this.stepHeight = 1.0F;
                this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

                if (!this.worldObj.isRemote)
                {
                    this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
                    super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
                }

                

                this.prevLimbSwingAmount = this.limbSwingAmount;
                double d1 = this.posX - this.prevPosX;
                double d0 = this.posZ - this.prevPosZ;
                float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

                if (f4 > 1.0F)
                {
                    f4 = 1.0F;
                }

                this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
                this.limbSwing += this.limbSwingAmount;
            }
            else
            {
                this.stepHeight = 0.5F;
                this.jumpMovementFactor = 0.02F;
                super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
            }
    }

    /**
     * Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
     * (Animals, Spiders at day, peaceful PigZombies).
     */
    protected Entity findPlayerToAttack()
    {
        if (worldObj.getDifficulty().getDifficultyId() > 0 && (riddenByEntity instanceof CREEPSEntityCamelJockey))
        {
            EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, attackrange);

            if (entityplayer != null)
            {
                return entityplayer;
            }

            if (rand.nextInt(10) == 0)
            {
                EntityLiving entityliving = func_21018_getClosestTarget(this, 15D);
                return entityliving;
            }
        }

        return null;
    }

    public EntityLiving func_21018_getClosestTarget(Entity entity, double d)
    {
        double d1 = -1D;
        EntityLiving entityliving = null;

        for (int i = 0; i < worldObj.loadedEntityList.size(); i++)
        {
            Entity entity1 = (Entity)worldObj.loadedEntityList.get(i);

            if (!(entity1 instanceof EntityLiving) || entity1 == entity || entity1 == entity.riddenByEntity || entity1 == entity.ridingEntity || (entity1 instanceof EntityPlayer) || (entity1 instanceof EntityMob) || (entity1 instanceof EntityAnimal))
            {
                continue;
            }

            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);

            if ((d < 0.0D || d2 < d * d) && (d1 == -1D || d2 < d1) && ((EntityLiving)entity1).canEntityBeSeen(entity))
            {
                d1 = d2;
                entityliving = (EntityLiving)entity1;
            }
        }

        return entityliving;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        Entity entity = damagesource.getEntity();

        if (super.attackEntityFrom(DamageSource.causeMobDamage(this), i))
        {
            if (riddenByEntity == entity || ridingEntity == entity)
            {
                return true;
            }

            if (entity != this && worldObj.getDifficulty().getDifficultyId() > 0)
            {
                this.attackEntity(entity, 1);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    
    protected void attackEntity(Entity entity, float f)
    {
        if (onGround)
        {
            double d = entity.posX - posX;
            double d2 = entity.posZ - posZ;
            float f1 = MathHelper.sqrt_double(d * d + d2 * d2);
            motionX = (d / (double)f1) * 0.20000000000000001D * (0.850000011920929D + motionX * 0.20000000298023224D);
            motionZ = (d2 / (double)f1) * 0.20000000000000001D * (0.80000001192092896D + motionZ * 0.20000000298023224D);
            motionY = 0.10000000596246449D;
            fallDistance = -25F;
        }

        if ((double)f > 2D && (double)f < 7D && (entity instanceof EntityPlayer) && spittimer-- < 0 && (riddenByEntity instanceof CREEPSEntityCamelJockey))
        {
            worldObj.playSoundAtEntity(this, "morecreeps:camelspits", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            spittimer = 30;
            faceEntity(entity, 360F, 0.0F);
            double d1 = -MathHelper.sin((rotationYaw * (float)Math.PI) / 180F);
            double d3 = MathHelper.cos((rotationYaw * (float)Math.PI) / 180F);

            for (int i = 0; i < 40; i++)
            {
                CREEPSFxSpit creepsfxspit = new CREEPSFxSpit(worldObj, posX + d1 * (double)(4.5F - (2.0F - modelsize)), (posY + 2.4000000953674316D) - (double)(2.0F - modelsize), posZ + d3 * (double)(4.5F - (2.0F - modelsize)), MoreCreepsAndWeirdos.partBubble);
                creepsfxspit.renderDistanceWeight = 10D;
                Minecraft.getMinecraft().effectRenderer.addEffect(creepsfxspit);
            }

            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
        }

        if ((double)f < 3.2999999999999998D - (2D - (double)modelsize) && entity.getBoundingBox().maxY > entity.getBoundingBox().minY && entity.getBoundingBox().minY < entity.getBoundingBox().maxY)
        {
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), attack);
        }
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer entityplayer)
    {
    	
    	
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        used = false;

        if (tamed && entityplayer.isSneaking())
        {
            entityplayer.openGui(MoreCreepsAndWeirdos.instance, 1, worldObj, (int)this.posX, (int)this.posY, (int)this.posZ);
            return true;
        }

        if (itemstack != null && riddenByEntity == null && itemstack.getItem() == Items.cookie)
        {
            if (itemstack.stackSize - 1 == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            else
            {
                itemstack.stackSize--;
            }

            worldObj.playSoundAtEntity(this, "morecreeps:hotdogeat", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            used = true;
            int health = basehealth;
            health += 10;
            this.setHealth(health);

            if (health > basehealth)
            {
                health = basehealth;
            }

            tamedcookies--;
            String s = "";

            if (tamedcookies > 1)
            {
                s = "s";
            }

            if (tamedcookies > 0)
            {
            	MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("You need \2476").append(String.valueOf(tamedcookies)).append(" cookie").append(String.valueOf(s)).append(" \247fto tame this lovely camel.").toString());
            }

            if (tamedcookies == 0)
            {
                tamed = true;

                
                confetti(entityplayer);
                worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                entityplayer.addStat(MoreCreepsAndWeirdos.achievecamel, 1);
                

                owner = entityplayer;

                if (name.length() < 1)
                {
                    name = Names[rand.nextInt(Names.length)];
                }

                MoreCreepsAndWeirdos.proxy.addChatMessage("");
                MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("\2476").append(String.valueOf(name)).append(" \247fhas been tamed!").toString());
                worldObj.playSoundAtEntity(this, "morecreeps:ggpiglevelup", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            }

            smoke();
        }

        if (used)
        {
            smoke();
        }

        String s1 = "";

        if (tamedcookies > 1)
        {
            s1 = "s";
        }

        if (!used && !tamed)
        {
        	MoreCreepsAndWeirdos.proxy.addChatMessage((new StringBuilder()).append("You need \2476").append(String.valueOf(tamedcookies)).append(" cookie").append(String.valueOf(s1)).append(" \247fto tame this lovely camel.").toString());
        }

        if (itemstack == null && tamed && this.getHealth() > 0)
        {
            if (entityplayer.riddenByEntity == null && modelsize > 1.0F)
            {
                rotationYaw = entityplayer.rotationYaw;
                rotationPitch = entityplayer.rotationPitch;
                entityplayer.fallDistance = -5F;
                entityplayer.mountEntity(this);

                if (this == null)
                {
                    double d = -MathHelper.sin((rotationYaw * (float)Math.PI) / 180F);
                    entityplayer.motionX += 1.5D * d;
                    entityplayer.motionZ -= 0.5D;
                }
            }
            else if (modelsize < 1.0F && tamed)
            {
            	MoreCreepsAndWeirdos.proxy.addChatMessage("Your Camel is too small to ride!");
            }
            else
            {
            	MoreCreepsAndWeirdos.proxy.addChatMessage("Unmount all creatures before riding your Camel");
            }
        }

        return true;
    }

    public void confetti(EntityPlayer player)
    {
        double d = -MathHelper.sin((player.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((player.rotationYaw * (float)Math.PI) / 180F);
        CREEPSEntityTrophy creepsentitytrophy = new CREEPSEntityTrophy(worldObj);
        creepsentitytrophy.setLocationAndAngles(player.posX + d * 3D, player.posY - 2D, player.posZ + d1 * 3D, player.rotationYaw, 0.0F);
        if(!worldObj.isRemote)
        {
            worldObj.spawnEntityInWorld(creepsentitytrophy);
        }
    }

    private void smoke()
    {
        for (int i = 0; i < 7; i++)
        {
            double d = rand.nextGaussian() * 0.02D;
            double d2 = rand.nextGaussian() * 0.02D;
            double d4 = rand.nextGaussian() * 0.02D;
            worldObj.spawnParticle(EnumParticleTypes.HEART, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d2, d4);
        }

        for (int j = 0; j < 4; j++)
        {
            for (int k = 0; k < 10; k++)
            {
                double d1 = rand.nextGaussian() * 0.02D;
                double d3 = rand.nextGaussian() * 0.02D;
                double d5 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height) + (double)j, (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d1, d3, d5);
            }
        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        int l = worldObj.getLight(getPosition());
        int j1 = worldObj.countEntities(CREEPSEntityCamel.class);
        Block i1 = worldObj.getBlockState(new BlockPos(getPosition())).getBlock();
        return (i1 == Blocks.sand || i1 == Blocks.dirt || i1 == Blocks.gravel) && i1 != Blocks.cobblestone && i1 != Blocks.planks && i1 != Blocks.carpet && worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0 &&worldObj.getCollidingBoundingBoxes(this, getBoundingBox()).size() == 0 && worldObj.checkBlockCollision(getBoundingBox()) && worldObj.canBlockSeeSky(getPosition()) && l > 6 && rand.nextInt(40) == 0 && j1 < 25;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("SpitTimer", spittimer);
        nbttagcompound.setString("BaseTexture", basetexture);
        nbttagcompound.setString("CamelName", name);
        nbttagcompound.setBoolean("Tamed", tamed);
        nbttagcompound.setInteger("TamedCookies", tamedcookies);
        nbttagcompound.setInteger("BaseHealth", basehealth);
        nbttagcompound.setFloat("ModelSize", modelsize);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        spittimer = nbttagcompound.getInteger("SpitTimer");
        basetexture = nbttagcompound.getString("BaseTexture");
        name = nbttagcompound.getString("CamelName");
        tamed = nbttagcompound.getBoolean("Tamed");

        if (basetexture == null)
        {
            basetexture = "/mob/creeps/camel.png";
        }

        texture = basetexture;
        basehealth = nbttagcompound.getInteger("BaseHealth");
        tamedcookies = nbttagcompound.getInteger("TamedCookies");
        modelsize = nbttagcompound.getFloat("ModelSize");
    }

    /**
     * Plays living's sound at its position
     */
    public void playLivingSound()
    {
        String s = getLivingSound();

        if (s != null)
        {
            worldObj.playSoundAtEntity(this, s, getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F + (2.0F - modelsize));
        }
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "morecreeps:camel";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:camelhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:cameldeath";
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
        if (tamed)
        {
            return;
        }

        if (rand.nextInt(10) == 0)
        {
            dropItem(Items.porkchop, rand.nextInt(3) + 1);
        }

        if (rand.nextInt(10) == 0)
        {
            dropItem(Items.reeds, rand.nextInt(3) + 1);
        }

        super.onDeath(damagesource);
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 4;
    }
}
