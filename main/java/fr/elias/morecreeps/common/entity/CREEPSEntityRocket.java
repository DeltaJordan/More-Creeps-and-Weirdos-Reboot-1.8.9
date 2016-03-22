package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import fr.elias.morecreeps.client.particles.CREEPSFxSmoke;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityRocket extends Entity
{
    protected int hitX;
    protected int hitY;
    protected int hitZ;
    protected IBlockState blockHit;

    /** Light value one block more in z axis */
    protected boolean aoLightValueZPos;

    /**
     * Used as a scratch variable for ambient occlusion on the north/bottom/west corner.
     */
    protected int aoLightValueScratchXYZNNP;

    /**
     * Used as a scratch variable for ambient occlusion between the bottom face and the north face.
     */
    protected int aoLightValueScratchXYNN;
    protected int damage;

    /**
     * Used as a scratch variable for ambient occlusion on the north/bottom/east corner.
     */
    protected boolean aoLightValueScratchXYZNNN;
    protected boolean playerFire;
    protected Entity playerToAttack;
    public boolean playerAttack;
    public int explodedelay;
    public EntityLivingBase owner;
    public Entity shootingEntity;

    public CREEPSEntityRocket(World world)
    {
        super(world);
        hitX = -1;
        hitY = -1;
        hitZ = -1;
        blockHit = null;
        aoLightValueZPos = false;
        aoLightValueScratchXYNN = 0;
        setSize(0.325F, 0.1425F);
        playerFire = false;
        explodedelay = 30;
    }

    public CREEPSEntityRocket(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
        aoLightValueScratchXYZNNN = true;
    }

    public CREEPSEntityRocket(World world, EntityLivingBase entityliving, float f)
    {
        this(world);
        shootingEntity = entityliving;
        owner = entityliving;
        damage = 15;
        setLocationAndAngles(entityliving.posX, entityliving.posY + (double)entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * (float)Math.PI) * 0.16F;
        posY += 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * (float)Math.PI) * 0.66F;

        if (entityliving instanceof EntityPlayer)
        {
            posY -= 1.3999999761581421D;
        }

        setPosition(posX, posY, posZ);
        motionX = -MathHelper.sin((rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float)Math.PI);
        motionZ = MathHelper.cos((rotationYaw / 180F) * (float)Math.PI) * MathHelper.cos((rotationPitch / 180F) * (float)Math.PI);
        motionY = -MathHelper.sin((rotationPitch / 180F) * (float)Math.PI);
        float f1 = 1.0F;

        if (entityliving instanceof EntityPlayer)
        {
            playerFire = true;
            float f2 = 0.3333333F;
            float f3 = f2 / 0.1F;

            if (f3 > 0.0F)
            {
                f1 = (float)((double)f1 * (1.0D + 2D / (double)f3));
            }
        }

        if (Math.abs(entityliving.motionX) > 0.10000000000000001D || Math.abs(entityliving.motionY) > 0.10000000000000001D || Math.abs(entityliving.motionZ) > 0.10000000000000001D)
        {
            f1 *= 2.0F;
        }

        if (entityliving.onGround);

        func_22374_a(motionX, motionY, motionZ, (float)(1.1499999761581421D + ((double)worldObj.rand.nextFloat() - 0.5D)), f1);
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
    }

    protected void entityInit()
    {
    }

    public void func_22374_a(double d, double d1, double d2, float f, float f1)
    {
        float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        d /= f2;
        d1 /= f2;
        d2 /= f2;
        d += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d1 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d2 += rand.nextGaussian() * 0.0074999998323619366D * (double)f1;
        d *= f;
        d1 *= f;
        d2 *= f;
        motionX = d;
        motionY = d1;
        motionZ = d2;
        float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
        prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / Math.PI);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f3) * 180D) / Math.PI);
        aoLightValueScratchXYZNNP = 0;
    }

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    public boolean isInRangeToRenderDist(double d)
    {
        return true;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return explodedelay <= 0;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (explodedelay > 0)
        {
            explodedelay--;
        }
        if(worldObj.isRemote)
        {
        	MoreCreepsAndWeirdos.proxy.rocketSmoke(worldObj, this, MoreCreepsAndWeirdos.partWhite); // or partBlack ?
        }
        double d = rand.nextGaussian() * 0.02D;
        double d1 = rand.nextGaussian() * 0.02D;
        double d2 = rand.nextGaussian() * 0.02D;
        worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2, new int[0]);

        if (aoLightValueScratchXYNN == 100)
        {
            setDead();
        }

        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            prevRotationYaw = rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / Math.PI);
            prevRotationPitch = rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / Math.PI);
        }

        if (aoLightValueZPos)
        {
            Block i = worldObj.getBlockState(new BlockPos(hitX, hitY, hitZ)).getBlock();

            if (i != blockHit)
            {
                aoLightValueZPos = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                aoLightValueScratchXYZNNP = 0;
                aoLightValueScratchXYNN = 0;
            }
            else
            {
                aoLightValueScratchXYZNNP++;

                if (aoLightValueScratchXYZNNP == 100)
                {
                    setDead();
                }

                return;
            }
        }
        else
        {
            aoLightValueScratchXYNN++;
        }

        Vec3 vec3d = new Vec3(posX, posY, posZ);
        Vec3 vec3d1 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition movingobjectposition = worldObj.rayTraceBlocks(vec3d, vec3d1);
        vec3d = new Vec3(posX, posY, posZ);
        vec3d1 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
       
        if (movingobjectposition != null)
        {
            vec3d1 =  new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        Entity entity = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
        double d3 = 0.0D;

        for (int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);

            if (!entity1.canBeCollidedWith() || (entity1 == shootingEntity || shootingEntity != null && entity1 == shootingEntity.ridingEntity) && aoLightValueScratchXYNN < 5 || aoLightValueScratchXYZNNN)
            {
                if (motionZ != 0.0D || !((motionX == 0.0D) & (motionY == 0.0D)))
                {
                    continue;
                }

                setDead();
                break;
            }

            float f4 = 0.3F;
            AxisAlignedBB axisalignedbb = entity1.getBoundingBox().expand(f4, f4, f4);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);

            if (movingobjectposition1 == null)
            {
            	if(!worldObj.isRemote)
            	{
                    worldObj.createExplosion(null, posX, posY, posZ, 1.0F, true);
            	}
                checkSplashDamage();
                setDead();
                continue;
            }

            double d4 = vec3d.distanceTo(movingobjectposition1.hitVec);

            if (d4 < d3 || d3 == 0.0D)
            {
            	if(!worldObj.isRemote)
            	{
                    worldObj.createExplosion(null, posX, posY, posZ, 1.0F, true);
            	}
                checkSplashDamage();
                entity = entity1;
                d3 = d4;
            }
        }

        if (entity != null)
        {
            movingobjectposition = new MovingObjectPosition(entity);
        }

        if (movingobjectposition != null)
        {
            if (movingobjectposition.entityHit != null)
            {
                if (movingobjectposition.entityHit instanceof EntityPlayer)
                {
                    int k = damage;

                    if (worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
                    {
                        k = 0;
                    }

                    if (worldObj.getDifficulty() == EnumDifficulty.EASY)
                    {
                        k = k / 3 + 1;
                    }

                    if (worldObj.getDifficulty() == EnumDifficulty.HARD)
                    {
                        k = (k * 3) / 2;
                    }
                }

                if ((movingobjectposition.entityHit instanceof EntityLiving) && !(movingobjectposition.entityHit instanceof CREEPSEntityRocketGiraffe))
                {
                    if (movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, (EntityLiving)entity), damage));

                    worldObj.playSoundAtEntity(this, "morecreeps:rocketexplode", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                    if(!worldObj.isRemote)
                    worldObj.createExplosion(null, posX, posY, posZ, 1.5F, true);
                    setDead();
                }
                else
                {
                    setDead();
                }
            }
            else
            {
                hitX = movingobjectposition.getBlockPos().getX();
                hitY = movingobjectposition.getBlockPos().getY();
                hitZ = movingobjectposition.getBlockPos().getZ();
                blockHit = worldObj.getBlockState(new BlockPos(hitX, hitY, hitZ));
                motionX = (float)(movingobjectposition.hitVec.xCoord - posX);
                motionY = (float)(movingobjectposition.hitVec.yCoord - posY);
                motionZ = (float)(movingobjectposition.hitVec.zCoord - posZ);
                float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                posX -= (motionX / (double)f1) * 0.05000000074505806D;
                posY -= (motionY / (double)f1) * 0.05000000074505806D;
                posZ -= (motionZ / (double)f1) * 0.05000000074505806D;
                aoLightValueZPos = true;
                worldObj.playSoundAtEntity(this, "morecreeps:rocketexplode", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
                if(!worldObj.isRemote)
                worldObj.createExplosion(null, posX, posY, posZ, 1.5F, true);
                checkSplashDamage();
                setDead();
            }

            worldObj.playSoundAtEntity(this, "morecreeps:raygun", 0.2F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
            setDead();
        }

        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / Math.PI);

        for (rotationPitch = (float)((Math.atan2(motionY, f2) * 180D) / Math.PI); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }

        for (; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }

        for (; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }

        for (; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }

        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f3 = 0.99F;
        float f5 = 0.0F;

        if (handleWaterMovement())
        {
            for (int l = 0; l < 4; l++)
            {
                float f7 = 0.25F;
                worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * (double)f7, posY - motionY * (double)f7, posZ - motionZ * (double)f7, motionX, motionY, motionZ, new int[0]);
            }

            f3 = 0.8F;
            float f6 = 0.03F;
            setDead();
        }

        motionX *= f3;
        motionZ *= f3;
        setPosition(posX, posY, posZ);
    }

    public void checkSplashDamage()
    {
        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 50D);
        List list = null;
        list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(5D, 5D, 5D));

        for (int i = 0; i < list.size(); i++)
        {
            Entity entity = (Entity)list.get(i);

            if (!((entity != null) & (entity instanceof EntityCreature)) || (entity instanceof CREEPSEntityRocketGiraffe))
            {
                continue;
            }

            if (entityplayer != null)
            {
                ((EntityCreature)entity).setRevengeTarget(entityplayer);
            }

            playerAttack = true;
            entity.velocityChanged = true;
            entity.motionY += 0.20000000298023224D;
            entity.attackEntityFrom(DamageSource.generic, 10);

            if (((EntityLivingBase)entity).getHealth() <= 0 && !entity.isDead && !((EntityPlayerMP)entityplayer).getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achieverocketrampage) && MoreCreepsAndWeirdos.rocketcount >= 50)
            {
                worldObj.playSoundAtEntity(entityplayer, "morecreeps:achievement", 1.0F, 1.0F);
                entityplayer.addStat(MoreCreepsAndWeirdos.achieverocketrampage, 1);
                confetti(entityplayer);
            }
        }
    }

    public void goBoom()
    {
    	if(worldObj.isRemote)
    	{
    		MoreCreepsAndWeirdos.proxy.rocketGoBoom(worldObj, this, rand);
    	}
    }

    public void confetti(EntityPlayer player)
    {
        double d = -MathHelper.sin((player.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((player.rotationYaw * (float)Math.PI) / 180F);
        CREEPSEntityTrophy creepsentitytrophy = new CREEPSEntityTrophy(worldObj);
        creepsentitytrophy.setLocationAndAngles(player.posX + d * 3D, player.posY - 2D, player.posZ + d1 * 3D, player.rotationYaw, 0.0F);
        worldObj.spawnEntityInWorld(creepsentitytrophy);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)hitX);
        nbttagcompound.setShort("yTile", (short)hitY);
        nbttagcompound.setShort("zTile", (short)hitZ);
        nbttagcompound.setByte("inGround", (byte)(aoLightValueZPos ? 1 : 0));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        hitX = nbttagcompound.getShort("xTile");
        hitY = nbttagcompound.getShort("yTile");
        hitZ = nbttagcompound.getShort("zTile");
        aoLightValueZPos = nbttagcompound.getByte("inGround") == 1;
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        super.setDead();
        goBoom();
        shootingEntity = null;
    }
}
