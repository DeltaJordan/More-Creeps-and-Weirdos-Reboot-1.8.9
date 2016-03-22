package fr.elias.morecreeps.common.entity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;

public class CREEPSEntityPyramidGuardian extends EntityMob
{
    public static Random rand = new Random();
    private int count;
    private int pyramids;
    public int rows;
    public int columns;
    public int maze[][];
    public static int backgroundCode;
    public static int wallCode;
    public static int pathCode;
    public static int emptyCode;
    public static int visitedCode;
    public TileEntityChest chest;
    public int alternate;
    public boolean found;
    public int bedrockcounter;
    public String texture;

    public CREEPSEntityPyramidGuardian(World world)
    {
        super(world);
        texture = "morecreeps:textures/entity/pyramidguardian.png";
        found = false;
        rotationYaw = 0.0F;
        setSize(0.4F, 0.4F);
        alternate = 1;
        bedrockcounter = 0;
        ((PathNavigateGround)this.getNavigator()).func_179688_b(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.4D, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }
    public void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
    	this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1D);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Found", found);
        nbttagcompound.setInteger("BedrockCounter", bedrockcounter);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        found = nbttagcompound.getBoolean("Found");
        bedrockcounter = nbttagcompound.getInteger("BedrockCounter");
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        super.onLivingUpdate();
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        Entity entity = damagesource.getEntity();

        if (entity != null && (entity instanceof EntityPlayer))
        {
            return super.attackEntityFrom(DamageSource.causeMobDamage(this), i);
        }
        else
        {
            return false;
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource damagesource)
    {
        if (damagesource.getEntity() != null && damagesource.getEntity() instanceof EntityPlayer)
        {
        	EntityPlayerMP playerMPAchievement = (EntityPlayerMP) damagesource.getEntity();
            if (!playerMPAchievement.getStatFile().hasAchievementUnlocked(MoreCreepsAndWeirdos.achievepyramid))
            {
                worldObj.playSoundAtEntity(playerMPAchievement, "morecreeps:achievement", 1.0F, 1.0F);
                playerMPAchievement.addStat(MoreCreepsAndWeirdos.achievepyramid, 1);
                confetti(playerMPAchievement);
            }
            if(!worldObj.isRemote)
            {
                int i = MathHelper.floor_double(posX);
                int j = MathHelper.floor_double(getBoundingBox().minY);
                int k = MathHelper.floor_double(posZ);
                byte byte0 = 35;
                byte byte1 = 35;

                if (posY > 60D)
                {
                    for (int l = -4; l < 6; l++)
                    {
                        for (int k2 = -40; k2 < 40; k2++)
                        {
                            for (int j3 = -40; j3 < 40; j3++)
                            {
                                if (worldObj.getBlockState(new BlockPos(i + k2, j + l, k + j3)).getBlock() == Blocks.bedrock)
                                {
                                    bedrockcounter++;
                                    worldObj.setBlockState(new BlockPos(i + k2, j + l, k + j3), Blocks.sandstone.getDefaultState());
                                }
                            }
                        }
                    }
                }

                if (bedrockcounter > 50)
                {
                    for (int i1 = 3; i1 < 11; i1++)
                    {
                        for (int l2 = 9; l2 < 24; l2++)
                        {
                            for (int k3 = 9; k3 < 25; k3++)
                            {
                                worldObj.setBlockToAir(new BlockPos(i - l2, j + i1, k - k3));
                            }
                        }
                    }

                    worldObj.setBlockState(new BlockPos(i - 2, j, k - 2), Blocks.sandstone.getDefaultState());
                    worldObj.setBlockToAir(new BlockPos(i - 2, j + 1, k - 1));
                    worldObj.setBlockToAir(new BlockPos(i - 2, j + 1, k - 2));
                    worldObj.setBlockToAir(new BlockPos(i - 2, j + 2, k - 1));
                    worldObj.setBlockToAir(new BlockPos(i - 2, j + 2, k - 2));
                    worldObj.setBlockState(new BlockPos(i - 2, j + 1, k - 3), Blocks.sandstone.getDefaultState());
                    worldObj.setBlockToAir(new BlockPos(i - 2, j + 2, k - 3));
                    worldObj.setBlockState(new BlockPos(i - 2, j + 2, k - 4), Blocks.sandstone.getDefaultState());
                    worldObj.setBlockToAir(new BlockPos(i - 2, j + 3, k - 4));

                    for (int j1 = 2; j1 < 18; j1++)
                    {
                        worldObj.setBlockToAir(new BlockPos(i - 2, j + 3, k - j1));
                        worldObj.setBlockToAir(new BlockPos(i - 2, j + 4, k - j1));
                        alternate *= -1;

                        if (alternate > 0)
                        {
                            worldObj.setBlockState(new BlockPos(i - 2, j + 4, k - j1), Blocks.torch.getDefaultState());
                        }
                    }

                    for (int k1 = 2; k1 < 20; k1++)
                    {
                        worldObj.setBlockToAir(new BlockPos(i - k1, j + 3, k - 17));
                        worldObj.setBlockToAir(new BlockPos(i - k1, j + 4, k - 17));
                    }

                    for (int l1 = 9; l1 < 24; l1++)
                    {
                        alternate *= -1;

                        if (alternate > 0)
                        {
                            worldObj.setBlockState(new BlockPos(i - 8, j + 8, k - l1), Blocks.torch.getDefaultState());
                            worldObj.setBlockState(new BlockPos(i - 24, j + 8, k - l1), Blocks.torch.getDefaultState());
                        }

                        worldObj.setBlockState(new BlockPos(i - l1, j + 8, k - 9), Blocks.torch.getDefaultState());
                        worldObj.setBlockState(new BlockPos(i - l1, j + 8, k - 24), Blocks.torch.getDefaultState());
                    }

                    for (int i2 = 0; i2 < rand.nextInt(2) + 2; i2++)
                    {
                        CREEPSEntityEvilCreature creepsentityevilcreature = new CREEPSEntityEvilCreature(worldObj);
                        creepsentityevilcreature.setLocationAndAngles(i - 15, j + 8, k - 10 - i2, rotationYaw, 0.0F);
                        worldObj.spawnEntityInWorld(creepsentityevilcreature);
                    }

                    for (int j2 = 0; j2 < rand.nextInt(7) + 2; j2++)
                    {
                        CREEPSEntityMummy creepsentitymummy = new CREEPSEntityMummy(worldObj);
                        creepsentitymummy.setLocationAndAngles(i - 15, j + 8, k - 13 - j2, rotationYaw, 0.0F);
                        worldObj.spawnEntityInWorld(creepsentitymummy);
                    }

                    worldObj.setBlockState(new BlockPos(i - 14, j + 3, k - 15), Blocks.sea_lantern.getDefaultState());
                    worldObj.setBlockState(new BlockPos(i - 16, j + 3, k - 15), Blocks.sea_lantern.getDefaultState());
                    BlockBed blockbed = (BlockBed)Blocks.bed;
                    int i3 = 0;
                    int l3 = 1;
                    int i4 = 0;
                    worldObj.setBlockState(new BlockPos(i - 15, j + 3, k - 15), blockbed.getDefaultState(), i4);
                    worldObj.setBlockState(new BlockPos((i - 15) + i3, j + 3, (k + l3) - 15), blockbed.getDefaultState(), i4 + 8);
                }
            }
        }
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer entityplayer)
    {
        worldObj.playSoundAtEntity(this, "morecreeps:pyramidcurse", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
        return true;
    }

    public void makeMaze()
    {
        int l1 = 0;
        int i2 = 0;
        int ai[] = new int[(rows * columns) / 2];
        int ai1[] = new int[(rows * columns) / 2];

        for (int i = 0; i < rows; i++)
        {
            for (int i1 = 0; i1 < columns; i1++)
            {
                maze[i][i1] = wallCode;
            }
        }

        for (int j = 1; j < rows - 1; j += 2)
        {
            for (int j1 = 1; j1 < columns - 1; j1 += 2)
            {
                l1++;
                maze[j][j1] = -l1;

                if (j < rows - 2)
                {
                    ai[i2] = j + 1;
                    ai1[i2] = j1;
                    i2++;
                }

                if (j1 < columns - 2)
                {
                    ai[i2] = j;
                    ai1[i2] = j1 + 1;
                    i2++;
                }
            }
        }

        for (int k = i2 - 1; k > 0; k--)
        {
            int j2 = (int)(Math.random() * (double)k);

            if (ai[j2] % 2 == 1 && maze[ai[j2]][ai1[j2] - 1] != maze[ai[j2]][ai1[j2] + 1])
            {
                fill(ai[j2], ai1[j2] - 1, maze[ai[j2]][ai1[j2] - 1], maze[ai[j2]][ai1[j2] + 1]);
                maze[ai[j2]][ai1[j2]] = maze[ai[j2]][ai1[j2] + 1];
            }
            else if (ai[j2] % 2 == 0 && maze[ai[j2] - 1][ai1[j2]] != maze[ai[j2] + 1][ai1[j2]])
            {
                fill(ai[j2] - 1, ai1[j2], maze[ai[j2] - 1][ai1[j2]], maze[ai[j2] + 1][ai1[j2]]);
                maze[ai[j2]][ai1[j2]] = maze[ai[j2] + 1][ai1[j2]];
            }

            ai[j2] = ai[k];
            ai1[j2] = ai1[k];
        }

        for (int l = 1; l < rows - 1; l++)
        {
            for (int k1 = 1; k1 < columns - 1; k1++)
            {
                if (maze[l][k1] < 0)
                {
                    maze[l][k1] = emptyCode;
                }
            }
        }
    }

    public void tearDown(int i, int j)
    {
        if (i % 2 == 1 && maze[i][j - 1] != maze[i][j + 1])
        {
            fill(i, j - 1, maze[i][j - 1], maze[i][j + 1]);
            maze[i][j] = maze[i][j + 1];
        }
        else if (i % 2 == 0 && maze[i - 1][j] != maze[i + 1][j])
        {
            fill(i - 1, j, maze[i - 1][j], maze[i + 1][j]);
            maze[i][j] = maze[i + 1][j];
        }
    }

    public void fill(int i, int j, int k, int l)
    {
        if (i < 0)
        {
            i = 0;
        }

        if (j < 0)
        {
            i = 0;
        }

        if (i > rows)
        {
            i = rows;
        }

        if (j > columns)
        {
            j = columns;
        }

        if (maze[i][j] == k)
        {
            maze[i][j] = l;
            fill(i + 1, j, k, l);
            fill(i - 1, j, k, l);
            fill(i, j + 1, k, l);
            fill(i, j - 1, k, l);
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
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return "morecreeps:pyramid";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "morecreeps:pyramidhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "morecreeps:pyramiddeath";
    }

    public boolean getCanSpawnHere()
    {
        return true;
    }
}