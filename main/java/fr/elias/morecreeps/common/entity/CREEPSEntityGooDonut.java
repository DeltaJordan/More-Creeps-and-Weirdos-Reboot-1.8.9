package fr.elias.morecreeps.common.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityGooDonut extends EntityThrowable
{
    protected double initialVelocity;
    double bounceFactor;
    int fuse;
    boolean exploded;

    public CREEPSEntityGooDonut(World world)
    {
        super(world);
        setSize(0.25F, 0.25F);
        initialVelocity = 1.0D;
        bounceFactor = 0.84999999999999998D;
        exploded = false;
        fuse = 30;
    }

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    public boolean isInRangeToRenderDist(double d)
    {
        double d1 = getEntityBoundingBox().getAverageEdgeLength() * 4D;
        d1 *= 64D;
        return d < d1 * d1;
    }

    public CREEPSEntityGooDonut(World world, Entity entity)
    {
        this(world);
        setRotation(entity.rotationYaw, 0.0F);
        double d = -MathHelper.sin((entity.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((entity.rotationYaw * (float)Math.PI) / 180F);
        motionX = 0.69999999999999996D * d * (double)MathHelper.cos((entity.rotationPitch / 180F) * (float)Math.PI);
        motionY = -0.80000000000000004D * (double)MathHelper.sin((entity.rotationPitch / 180F) * (float)Math.PI);
        motionZ = 0.69999999999999996D * d1 * (double)MathHelper.cos((entity.rotationPitch / 180F) * (float)Math.PI);
        setPosition(entity.posX + d * 0.80000000000000004D, entity.posY, entity.posZ + d1 * 0.80000000000000004D);
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        fuse = 30;
    }

    public CREEPSEntityGooDonut(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
    }

    public void func_20048_a(double d, double d1, double d2, float f, float f1)
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
    }

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    public void setVelocity(double d, double d1, double d2)
    {
        motionX = d;
        motionY = d1;
        motionZ = d2;

        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(d * d + d2 * d2);
            prevRotationYaw = rotationYaw = (float)((Math.atan2(d, d2) * 180D) / Math.PI);
            prevRotationPitch = rotationPitch = (float)((Math.atan2(d1, f) * 180D) / Math.PI);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        double d = motionX;
        double d1 = motionY;
        double d2 = motionZ;
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        moveEntity(motionX, motionY, motionZ);

        if (motionX != d)
        {
            motionX = -bounceFactor * d;
        }

        if (motionY != d1)
        {
            motionY = -bounceFactor * d1;
        }

        if (motionY != d1)
        {
            motionY = -bounceFactor * d1;
        }
        else
        {
            motionY -= 0.040000000000000001D;
        }

        motionX *= 0.97999999999999998D;
        motionY *= 0.995D;
        motionZ *= 0.97999999999999998D;

        if (fuse-- <= 0)
        {
            explode();
        }

        if (handleWaterMovement())
        {
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 10; j++)
                {
                    float f = 0.85F;
                    worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX - 0.25D * (double)f, posY - motionY - 0.25D * (double)f, posZ - motionZ - 0.25D * (double)f, motionX, motionY, motionZ);
                }
            }

            setDead();
            if(!worldObj.isRemote)
            dropItem(MoreCreepsAndWeirdos.goodonut, 1);
        }

        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
    }

    private void explode()
    {
        if (!exploded)
        {
            exploded = true;
            if(!worldObj.isRemote)
            worldObj.createExplosion(null, posX, posY, posZ, 3.85F, true);
            setDead();
        }
    }

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		// TODO Auto-generated method stub
		
	}
}
