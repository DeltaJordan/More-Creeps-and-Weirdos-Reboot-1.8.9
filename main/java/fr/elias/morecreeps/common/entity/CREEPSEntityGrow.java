package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import fr.elias.morecreeps.client.config.CREEPSConfig;
import fr.elias.morecreeps.client.particles.CREEPSFxSmoke;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityGrow extends EntityItem implements IProjectile
{
    public int hurtTime;
    public int maxHurtTime;
    protected int hitX;
    protected int hitY;
    protected int hitZ;
    protected Block blockHit;

    /** Light value one block more in z axis */
    protected boolean aoLightValueZPos;

    /** Light value of the block itself */
    public EntityLivingBase lightValueOwn;

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
    protected float shrinksize;
    protected int vibrate;
    public EntityPlayer playerOwnTheProjectile;

    public CREEPSEntityGrow(World world)
    {
        super(world);
        hitX = -1;
        hitY = -1;
        hitZ = -1;
        blockHit = Blocks.air;
        aoLightValueZPos = false;
        aoLightValueScratchXYNN = 0;
        setSize(0.0325F, 0.01125F);
        playerFire = false;
        shrinksize = 1.0F;
        vibrate = 1;
    }

    public CREEPSEntityGrow(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
        aoLightValueScratchXYZNNN = true;
    }

    public CREEPSEntityGrow(World world, EntityLivingBase entityliving, float f)
    {
        this(world);
        playerOwnTheProjectile = (EntityPlayer) entityliving;
        lightValueOwn = entityliving;
        damage = 4;
        setLocationAndAngles(entityliving.posX, entityliving.posY + (double)entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * (float)Math.PI) * 0.16F;
        posY += 0.20000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * (float)Math.PI) * 0.16F;

        if (entityliving instanceof EntityPlayer)
        {
            posY -= 0.40000000596046448D;
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

        setThrowableHeading(motionX, motionY, motionZ, (float)(2.5D + ((double)worldObj.rand.nextFloat() - 0.5D)), f1);
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

    public void setThrowableHeading(double d, double d1, double d2, float f, float f1)
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
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (aoLightValueScratchXYNN == 5)
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
                motionZ *= rand.nextFloat() * 0.2F;
                aoLightValueScratchXYZNNP = 0;
                aoLightValueScratchXYNN = 0;
            }
            else
            {
                aoLightValueScratchXYZNNP++;

                if (aoLightValueScratchXYZNNP == 5)
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
            vec3d1 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        Entity entity = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;

        for (int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);

            if (!entity1.canBeCollidedWith() || (entity1 == lightValueOwn || lightValueOwn != null && entity1 == lightValueOwn.ridingEntity) && aoLightValueScratchXYNN < 5 || aoLightValueScratchXYZNNN)
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
                continue;
            }

            double d2 = vec3d.distanceTo(movingobjectposition1.hitVec);

            if (d2 < d || d == 0.0D)
            {
                entity = entity1;
                d = d2;
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
                if (movingobjectposition.entityHit instanceof EntityLiving)
                {
                    boolean flag = false;

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityKid) && ((CREEPSEntityKid)movingobjectposition.entityHit).modelsize < 5F)
                    {
                        ((CREEPSEntityKid)movingobjectposition.entityHit).modelsize += 0.15F;
                        ((CREEPSEntityKid)movingobjectposition.entityHit).setEntitySize(((CREEPSEntityKid)movingobjectposition.entityHit).modelsize, ((CREEPSEntityKid)movingobjectposition.entityHit).modelsize);
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityBigBaby) && ((CREEPSEntityBigBaby)movingobjectposition.entityHit).modelsize < 8F)
                    {
                        ((CREEPSEntityBigBaby)movingobjectposition.entityHit).modelsize += 0.25F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityRatMan) && ((CREEPSEntityRatMan)movingobjectposition.entityHit).modelsize < 3F)
                    {
                        ((CREEPSEntityRatMan)movingobjectposition.entityHit).modelsize += 0.2F;
                        ((CREEPSEntityRatMan)movingobjectposition.entityHit).modelspeed += 0.15F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityGuineaPig) && ((CREEPSEntityGuineaPig)movingobjectposition.entityHit).modelsize < 5F)
                    {
                        ((CREEPSEntityGuineaPig)movingobjectposition.entityHit).modelsize += 0.15F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityHotdog) && ((CREEPSEntityHotdog)movingobjectposition.entityHit).dogsize < 5F)
                    {
                        ((CREEPSEntityHotdog)movingobjectposition.entityHit).dogsize += 0.15F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityRobotTed) && ((CREEPSEntityRobotTed)movingobjectposition.entityHit).robotsize < 6F)
                    {
                        ((CREEPSEntityRobotTed)movingobjectposition.entityHit).robotsize += 0.25F;
                        ((CREEPSEntityRobotTed)movingobjectposition.entityHit).modelspeed += 0.15F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityRobotTodd) && ((CREEPSEntityRobotTodd)movingobjectposition.entityHit).robotsize < 4F)
                    {
                        ((CREEPSEntityRobotTodd)movingobjectposition.entityHit).robotsize += 0.25F;
                        ((CREEPSEntityRobotTodd)movingobjectposition.entityHit).modelspeed += 0.05F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityGooGoat) && ((CREEPSEntityGooGoat)movingobjectposition.entityHit).goatsize < 4F)
                    {
                        ((CREEPSEntityGooGoat)movingobjectposition.entityHit).goatsize += 0.24F;
                        ((CREEPSEntityGooGoat)movingobjectposition.entityHit).modelspeed += 0.15F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityLolliman) && ((CREEPSEntityLolliman)movingobjectposition.entityHit).modelsize < 6F)
                    {
                        ((CREEPSEntityLolliman)movingobjectposition.entityHit).modelsize += 0.24F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityCastleCritter) && ((CREEPSEntityCastleCritter)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityCastleCritter)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntitySneakySal) && ((CREEPSEntitySneakySal)movingobjectposition.entityHit).modelsize < 6F)
                    {
                        ((CREEPSEntitySneakySal)movingobjectposition.entityHit).modelsize += 0.2F;
                        ((CREEPSEntitySneakySal)movingobjectposition.entityHit).dissedmax = 0;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityArmyGuy) && ((CREEPSEntityArmyGuy)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityArmyGuy)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityEvilChicken) && ((CREEPSEntityEvilChicken)movingobjectposition.entityHit).modelsize < 5F)
                    {
                        ((CREEPSEntityEvilChicken)movingobjectposition.entityHit).modelsize += 0.2F;
                        ((CREEPSEntityEvilChicken)movingobjectposition.entityHit).getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D + 0.015D);
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityBum) && ((CREEPSEntityBum)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityBum)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityBubbleScum) && ((CREEPSEntityBubbleScum)movingobjectposition.entityHit).modelsize < 3F)
                    {
                        ((CREEPSEntityBubbleScum)movingobjectposition.entityHit).modelsize += 0.15F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityLawyerFromHell) && ((CREEPSEntityLawyerFromHell)movingobjectposition.entityHit).modelsize < 5F)
                    {
                        ((CREEPSEntityLawyerFromHell)movingobjectposition.entityHit).modelsize += 0.2F;
                        MoreCreepsAndWeirdos.instance.currentfine += 50;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityG) && ((CREEPSEntityG)movingobjectposition.entityHit).modelsize < 5F)
                    {
                        ((CREEPSEntityG)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityRockMonster) && ((CREEPSEntityRockMonster)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityRockMonster)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityBabyMummy) && ((CREEPSEntityBabyMummy)movingobjectposition.entityHit).babysize < 4F)
                    {
                        ((CREEPSEntityBabyMummy)movingobjectposition.entityHit).babysize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityBlackSoul) && ((CREEPSEntityBlackSoul)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityBlackSoul)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityBlorp) && ((CREEPSEntityBlorp)movingobjectposition.entityHit).blorpsize < (float)CREEPSConfig.sblorpmaxsize)
                    {
                        ((CREEPSEntityBlorp)movingobjectposition.entityHit).blorpsize += 0.25F;
                        setSize(((CREEPSEntityBlorp)movingobjectposition.entityHit).blorpsize + 1.0F, ((CREEPSEntityBlorp)movingobjectposition.entityHit).blorpsize * 2.0F + 2.0F);
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityCamel) && ((CREEPSEntityCamel)movingobjectposition.entityHit).modelsize < 5F)
                    {
                        ((CREEPSEntityCamel)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityCamelJockey) && ((CREEPSEntityCamelJockey)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityCamelJockey)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityCastleGuard) && ((CREEPSEntityCastleGuard)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityCastleGuard)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityCaveman) && ((CREEPSEntityCaveman)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityCaveman)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityDesertLizard) && ((CREEPSEntityDesertLizard)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityDesertLizard)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityDigBug) && ((CREEPSEntityDigBug)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityDigBug)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityEvilCreature) && ((CREEPSEntityEvilCreature)movingobjectposition.entityHit).modelsize < 7F)
                    {
                        ((CREEPSEntityEvilCreature)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityEvilPig) && ((CREEPSEntityEvilPig)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityEvilPig)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityEvilScientist) && ((CREEPSEntityEvilScientist)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityEvilScientist)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityEvilSnowman) && ((CREEPSEntityEvilSnowman)movingobjectposition.entityHit).snowsize < 7F)
                    {
                        ((CREEPSEntityEvilSnowman)movingobjectposition.entityHit).snowsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityFloob) && ((CREEPSEntityFloob)movingobjectposition.entityHit).modelsize < 5F)
                    {
                        ((CREEPSEntityFloob)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityHippo) && ((CREEPSEntityHippo)movingobjectposition.entityHit).modelsize < 5F)
                    {
                        ((CREEPSEntityHippo)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityHunchback) && ((CREEPSEntityHunchback)movingobjectposition.entityHit).modelsize < 5F)
                    {
                        ((CREEPSEntityHunchback)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityHunchbackSkeleton) && ((CREEPSEntityHunchbackSkeleton)movingobjectposition.entityHit).modelsize < 5F)
                    {
                        ((CREEPSEntityHunchbackSkeleton)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityInvisibleMan) && ((CREEPSEntityInvisibleMan)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityInvisibleMan)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityManDog) && ((CREEPSEntityManDog)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityManDog)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityNonSwimmer) && ((CREEPSEntityNonSwimmer)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityNonSwimmer)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityRocketGiraffe) && ((CREEPSEntityRocketGiraffe)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityRocketGiraffe)movingobjectposition.entityHit).modelsize += 0.15F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntitySnowDevil) && ((CREEPSEntitySnowDevil)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntitySnowDevil)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityThief) && ((CREEPSEntityThief)movingobjectposition.entityHit).modelsize < 4F)
                    {
                        ((CREEPSEntityThief)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if ((movingobjectposition.entityHit instanceof CREEPSEntityZebra) && ((CREEPSEntityZebra)movingobjectposition.entityHit).modelsize < 5F)
                    {
                        ((CREEPSEntityZebra)movingobjectposition.entityHit).modelsize += 0.2F;
                    }

                    if (flag)
                    {
                        smoke();
                        worldObj.playSoundAtEntity(this, "morecreeps:shrinkkill", 1.0F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
                        setDead();
                    }
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
                blockHit = worldObj.getBlockState(new BlockPos(hitX, hitY, hitZ)).getBlock();
                motionX = (float)(movingobjectposition.hitVec.xCoord - posX);
                motionY = (float)(movingobjectposition.hitVec.yCoord - posY);
                motionZ = (float)(movingobjectposition.hitVec.zCoord - posZ);
                float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
                posX -= (motionX / (double)f1) * 0.05000000074505806D;
                posY -= (motionY / (double)f1) * 0.05000000074505806D;
                posZ -= (motionZ / (double)f1) * 0.05000000074505806D;
                aoLightValueZPos = true;
                setDead();

                if (blockHit == Blocks.ice)
                {
                    worldObj.setBlockState(new BlockPos(hitX, hitY, hitZ), Blocks.flowing_water.getDefaultState());
                }

                setDead();
            }

            worldObj.playSoundAtEntity(this, "morecreeps:raygun", 0.2F, 1.0F / (rand.nextFloat() * 0.1F + 0.95F));
            setDead();
        }

        
        if(worldObj.isRemote)
        	MoreCreepsAndWeirdos.proxy.growParticle(worldObj, this);

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
        playerOwnTheProjectile.posY += rand.nextGaussian() * 0.34999999999999998D * (double)vibrate;
        vibrate *= -1;
        double d1 = -MathHelper.sin((playerOwnTheProjectile.rotationYaw * (float)Math.PI) / 180F);
        double d3 = MathHelper.cos((playerOwnTheProjectile.rotationYaw * (float)Math.PI) / 180F);
        playerOwnTheProjectile.posX += (double)((float)vibrate * 0.25F) * d1;
        playerOwnTheProjectile.posZ += (double)((float)vibrate * 0.25F) * d3;

        if (handleWaterMovement())
        {
            for (int l = 0; l < 4; l++)
            {
                float f7 = 0.25F;
                worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * (double)f7, posY - motionY * (double)f7, posZ - motionZ * (double)f7, motionX, motionY, motionZ);
            }

            f3 = 0.8F;
            float f6 = 0.03F;
            setDead();
        }

        motionX *= f3;
        motionZ *= f3;
        setPosition(posX, posY, posZ);
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

    public void blast()
    {
    	if(worldObj.isRemote)
    		MoreCreepsAndWeirdos.proxy.shrinkParticle(worldObj, this);
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        blast();
        super.setDead();
        lightValueOwn = null;
    }

    private void smoke()
    {
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                for (int k = 0; k < 5; k++)
                {
                    double d = rand.nextGaussian() * 0.12D;
                    double d1 = rand.nextGaussian() * 0.12D;
                    double d2 = rand.nextGaussian() * 0.12D;
                    worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
                }
            }
        }
    }
    public ItemStack getEntityItem()
    {
    	return new ItemStack(MoreCreepsAndWeirdos.shrinkshrink, 1, 0);
    }
}
