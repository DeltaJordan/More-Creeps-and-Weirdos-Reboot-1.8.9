package fr.elias.morecreeps.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityFrisbee extends EntityThrowable implements IProjectile
{
    private int field_20056_b;
    private int field_20055_c;
    private int field_20054_d;
    private int field_20053_e;
    public int field_20057_a;
    private EntityLivingBase field_20051_g;
    private int field_20050_h;
    private int field_20049_i;
    protected double initialVelocity;
    double bounceFactor;
    public int lifespan;

    public CREEPSEntityFrisbee(World world)
    {
        super(world);
        setSize(0.25F, 0.25F);
        initialVelocity = 1.0D;
        bounceFactor = 0.14999999999999999D;
        lifespan = 120;
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

    public CREEPSEntityFrisbee(World world, Entity entity)
    {
        this(world);
        setRotation(entity.rotationYaw, 0.0F);
        double d = -MathHelper.sin((entity.rotationYaw * (float)Math.PI) / 180F);
        double d1 = MathHelper.cos((entity.rotationYaw * (float)Math.PI) / 180F);
        motionX = 0.59999999999999998D * d * (double)MathHelper.cos((entity.rotationPitch / 180F) * (float)Math.PI);
        motionY = -0.69999999999999996D * (double)MathHelper.sin((entity.rotationPitch / 180F) * (float)Math.PI);
        motionZ = 0.59999999999999998D * d1 * (double)MathHelper.cos((entity.rotationPitch / 180F) * (float)Math.PI);
        setPosition(entity.posX + d * 0.80000000000000004D, entity.posY, entity.posZ + d1 * 0.80000000000000004D + (3D * d1 + d));
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
    }

    public CREEPSEntityFrisbee(World world, double d, double d1, double d2)
    {
        this(world);
        setPosition(d, d1, d2);
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
        field_20050_h = 0;
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

        if (handleWaterMovement())
        {
            motionY += 0.0087999999523162842D;
            motionX *= 0.97999999999999998D;
            motionZ *= 0.68000000000000005D;
        }

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
        else if (!handleWaterMovement())
        {
            motionY -= 0.0050000000000000001D;
        }

        motionX *= 0.97999999999999998D;
        motionY *= 0.999D;
        motionZ *= 0.97999999999999998D;

        if (isCollidedVertically)
        {
            motionX *= 0.25D;
            motionZ *= 0.25D;
        }
		if(onGround && lifespan--<0)
		{
			if(!worldObj.isRemote)
			this.dropItem(MoreCreepsAndWeirdos.frisbee, 1);
			setDead();
		}
    }

    /*public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
        if (onGround && field_20051_g == entityplayer && field_20057_a <= 0 && !worldObj.isRemote)
        {
            worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            setDead();
            entityplayer.onItemPickup(this, 1);
            entityplayer.inventory.addItemStackToInventory(new ItemStack(MoreCreepsAndWeirdos.frisbee, 1, 0));
        }
    }*/

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)field_20056_b);
        nbttagcompound.setShort("yTile", (short)field_20055_c);
        nbttagcompound.setShort("zTile", (short)field_20054_d);
        nbttagcompound.setByte("inTile", (byte)field_20053_e);
        nbttagcompound.setByte("shake", (byte)field_20057_a);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        field_20056_b = nbttagcompound.getShort("xTile");
        field_20055_c = nbttagcompound.getShort("yTile");
        field_20054_d = nbttagcompound.getShort("zTile");
        field_20053_e = nbttagcompound.getByte("inTile") & 0xff;
        field_20057_a = nbttagcompound.getByte("shake") & 0xff;
    }
	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_)
	{
	}
}
