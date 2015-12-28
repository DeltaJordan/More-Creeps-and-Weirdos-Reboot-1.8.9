package fr.elias.morecreeps.common.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import fr.elias.morecreeps.common.entity.CREEPSEntityPyramidGuardian;

public class CREEPSWorldGenPyramid extends WorldGenerator
{
    public static Random rand = new Random();
    public boolean chunky;
    public boolean sandy;
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
    public int maxObstruct;

    public CREEPSWorldGenPyramid()
    {
    }

    public boolean blockExists(World parWorld, int x, int y, int z) 
    {
    	IBlockState state = parWorld.getBlockState(new BlockPos(x, y, z));
    	if (state != null)
    	{
        	return true;
    	}
    	else
    	{
        	return false;
    	}
    }
    
    public boolean generate(World world, Random random, int i, int j, int k)
    {
        pyramids = 298;
        rows = 35;
        columns = 35;
        backgroundCode = 0;
        wallCode = 7;
        pathCode = 15;
        emptyCode = 0;
        visitedCode = 4;
        maze = new int[rows + 1][columns + 1];
        maxObstruct = 100;
        int l = 0;
        int i1 = 0;
        label0:

        do
        {
            if (i1 >= 20)
            {
                break;
            }

            for (int j3 = -2 + i1; j3 < (rows - i1) + 2; j3 += 2)
            {
                for (int j6 = -2 + i1; j6 < (columns - i1) + 2; j6 += 2)
                {
                    if (world.getBlockState(new BlockPos(i + j3, j + i1, k + j6)) != Blocks.air.getDefaultState())
                    {
                        l++;
                    }

                    if (l > maxObstruct)
                    {
                        break label0;
                    }
                }
            }

            i1 += 2;
        }
        while (true);

        chunky = false;
        sandy = false;

        if (world.getBlockState(new BlockPos(i + random.nextInt(16), j - 1, k + random.nextInt(16))).getBlock() == Blocks.sand)
        {
            sandy = true;
        }

        if (blockExists(world, i, j, k) && blockExists(world, i + 35, j, k) && blockExists(world, i, j, k + 35) && blockExists(world, i + 35, j, k + 35))
        {
            chunky = true;
        }

        if (sandy && chunky && l < maxObstruct)
        {
            makeMaze();

            for (int j1 = 0; j1 < 3; j1++)
            {
                for (int k3 = 0; k3 < 3; k3++)
                {
                    maze[33 - j1][33 - k3] = 0;
                }
            }

            for (int k1 = -2; k1 < 21; k1++)
            {
                for (int l3 = -2 + k1; l3 < (rows - k1) + 2; l3++)
                {
                    for (int k6 = -2 + k1; k6 < (columns - k1) + 2; k6++)
                    {
                        world.setBlockState(new BlockPos(i + l3, j + k1, k + k6), Blocks.sandstone.getDefaultState());
                    }
                }
            }

            for (int l1 = 0; l1 < rows; l1++)
            {
                for (int i4 = 0; i4 < columns; i4++)
                {
                	//TODO fix this
                    //world.setBlockState(new BlockPos(i + l1, j, k + i4), maze[l1][i4]);
                    //world.setBlockState(new BlockPos(i + l1, j - 1, k + i4), maze[l1][i4]);
                    world.setBlockState(new BlockPos(i + l1, j - 2, k + i4), Blocks.bedrock.getDefaultState());
                }
            }

            for (int i2 = 0; i2 < 30; i2++)
            {
                int j4 = rand.nextInt(rows - 6) + 3;
                int l6 = rand.nextInt(columns - 6) + 3;

                if (maze[j4][l6] == 7)
                {
                    world.setBlockState(new BlockPos(i + j4, j, k + l6), Blocks.glass.getDefaultState());
                }
            }

            for (int j2 = 0; j2 < 15; j2++)
            {
                int k4 = rand.nextInt(rows - 6) + 3;
                int i7 = rand.nextInt(columns - 6) + 3;

                if (maze[k4][i7] == 7)
                {
                    world.setBlockState(new BlockPos(i + k4, j - 1, k + i7), Blocks.sandstone.getDefaultState());
                    world.setBlockState(new BlockPos(i + k4, j, k + i7), Blocks.sandstone.getDefaultState());
                }
            }

            for (int k2 = 0; k2 < 20; k2++)
            {
                int l4 = rand.nextInt(rows - 3) + 3;
                int j7 = rand.nextInt(columns - 3) + 3;

                if (maze[l4][j7] != 0)
                {
                    continue;
                }

                world.setBlockState(new BlockPos(i + l4, j - 1, k + j7), Block.getBlockById(30).getDefaultState());

                if (rand.nextInt(4) == 0)
                {
                    world.setBlockState(new BlockPos(i + l4, j, k + j7), Blocks.web.getDefaultState());
                }
            }

            for (int l2 = 0; l2 < 30; l2++)
            {
                int i5 = rand.nextInt(rows - 6) + 3;
                int k7 = rand.nextInt(columns - 6) + 3;

                if (maze[i5][k7] == 7)
                {
                    world.setBlockState(new BlockPos(i + i5, j, k + k7), Blocks.torch.getDefaultState());
                }
            }

            int i3 = 0;

            do
            {
                if (i3 >= 6)
                {
                    break;
                }

                int j5 = rand.nextInt(rows - 3);
                int l7 = rand.nextInt(columns - 3);
                j5++;
                l7++;

                if (maze[j5][l7] == wallCode)
                {
                    i3++;
                    world.setBlockToAir(new BlockPos(i + j5, j - 1, k + l7));
                    world.setBlockToAir(new BlockPos(i + j5, j, k + l7));
                    world.setBlockState(new BlockPos(i + j5, j - 1, k + l7), Blocks.mob_spawner.getDefaultState());
                    TileEntityMobSpawner tileentitymobspawner = new TileEntityMobSpawner();
                    world.setTileEntity(new BlockPos(i + j5, j - 1, k + l7), tileentitymobspawner);
                    int i8 = rand.nextInt(5);

                    if (i8 == 0)
                    {
                        tileentitymobspawner.getSpawnerBaseLogic().setEntityName("BlackSoul");
                    }

                    if (i8 == 1)
                    {
                        tileentitymobspawner.getSpawnerBaseLogic().setEntityName("BabyMummy");
                    }

                    if (i8 > 1)
                    {
                        tileentitymobspawner.getSpawnerBaseLogic().setEntityName("Mummy");
                    }
                }
            }
            while (true);

            world.setBlockToAir(new BlockPos(i + 1, j - 1, k));
            world.setBlockToAir(new BlockPos(i + 1, j, k));

            for (int k5 = 0; k5 < 5; k5++)
            {
                world.setBlockToAir(new BlockPos(i + 1, j - 1, k - k5));
                world.setBlockToAir(new BlockPos(i + 1, j, k - k5));
                world.setBlockState(new BlockPos(i + 1, j, k - k5), Blocks.torch.getDefaultState());
            }

            world.setBlockState(new BlockPos(i - 1, j, k - 5), Blocks.torch.getDefaultState());
            world.setBlockState(new BlockPos(i + 1, j, k - 5), Blocks.torch.getDefaultState());
            world.setBlockState(new BlockPos(i, j, k - 5), Blocks.torch.getDefaultState());

            for (int l5 = 1; l5 < 25; l5++)
            {
                world.setBlockToAir(new BlockPos(i - 1, j + l5, k - 5));
                world.setBlockToAir(new BlockPos(i + 1, j + l5, k - 5));
                world.setBlockToAir(new BlockPos(i, j + l5, k - 5));
            }

            world.setBlockState(new BlockPos(i, j + 26, k - 5), Blocks.torch.getDefaultState());
            Item i6 = Items.bone;
            float f = 0.7F;
            double d = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.20000000000000001D + 0.59999999999999998D;
            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, ((double)i + d) - 2D, (double)j + d1, ((double)k + d2) - 2D, new ItemStack(i6, 5, 0));
            world.spawnEntityInWorld(entityitem);
            world.setBlockState(new BlockPos((i + rows) - 2, j - 1, (k + columns) - 2), Block.getBlockById(54).getDefaultState());
            TileEntityChest tileentitychest = new TileEntityChest();
            world.setTileEntity(new BlockPos((i + rows) - 2, j - 1, (k + columns) - 2), tileentitychest);

            for (int j8 = 1; j8 < tileentitychest.getSizeInventory(); j8++)
            {
                int k8 = rand.nextInt(50);

                if (k8 == 0)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(MoreCreepsAndWeirdos.goodonut, rand.nextInt(15) + 1, 0));
                }

                if (k8 == 1)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(MoreCreepsAndWeirdos.bandaid, rand.nextInt(15) + 1, 0));
                }

                if (k8 == 2)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(MoreCreepsAndWeirdos.raygun, 1, 0));
                }

                if (k8 == 3)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(MoreCreepsAndWeirdos.money, rand.nextInt(15) + 1, 0));
                }

                if (k8 == 4)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(MoreCreepsAndWeirdos.blorpcola, rand.nextInt(10) + 5, 0));
                }

                if (k8 == 5)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.bread, 1, 0));
                }

                if (k8 == 6)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.golden_apple, 1, 0));
                }

                if (k8 == 7)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.gold_ingot, rand.nextInt(3) + 2, 0));
                }

                if (k8 == 8)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.iron_ingot, rand.nextInt(5) + 2, 0));
                }

                if (k8 == 9)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.gunpowder, rand.nextInt(4) + 2, 0));
                }

                if (k8 == 10)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.egg, rand.nextInt(3) + 1, 0));
                }

                if (k8 == 11)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.wheat, rand.nextInt(12) + 2, 0));
                }

                if (k8 == 12)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(MoreCreepsAndWeirdos.evilegg, rand.nextInt(15) + 1, 0));
                }

                if (k8 == 13)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.diamond, rand.nextInt(2) + 1, 0));
                }

                if (k8 == 14)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.iron_sword, 1, 0));
                }

                if (k8 == 15)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.diamond_sword, 1, 0));
                }

                if (k8 == 16)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.bow, 1, 0));
                }

                if (k8 == 17)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.arrow, rand.nextInt(30) + 10, 0));
                }

                if (k8 == 18)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.stick, 1, 0));
                }

                if (k8 == 19)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.milk_bucket, 1, 0));
                }

                if (k8 == 20)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.reeds, rand.nextInt(6) + 6, 0));
                }

                if (k8 == 21)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.paper, rand.nextInt(16) + 6, 0));
                }

                if (k8 == 22)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.book, 1, 0));
                }

                if (k8 == 23)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.cookie, rand.nextInt(6) + 6, 0));
                }

                if (k8 == 24)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.cake, rand.nextInt(6) + 6, 0));
                }

                if (k8 == 25)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.bucket, rand.nextInt(6) + 6, 0));
                }

                if (k8 == 26)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(MoreCreepsAndWeirdos.evilegg, rand.nextInt(40) + 1, 0));
                }

                if (k8 == 16)
                {
                    tileentitychest.setInventorySlotContents(j8, new ItemStack(Items.bone, 1, 0));
                }
            }

            CREEPSEntityPyramidGuardian creepsentitypyramidguardian = new CREEPSEntityPyramidGuardian(world);
            creepsentitypyramidguardian.setLocationAndAngles((i + rows) - 2, j - 1, (k + columns) - 3, 300F, 0.0F);
            creepsentitypyramidguardian.motionX = 0.0D;
            creepsentitypyramidguardian.motionY = 0.0D;
            creepsentitypyramidguardian.motionZ = 0.0D;
            world.spawnEntityInWorld(creepsentitypyramidguardian);

            for (int l8 = 0; l8 < rows; l8++)
            {
                for (int i9 = 0; i9 < columns; i9++)
                {
                    world.setBlockState(new BlockPos(i + l8, j + 1, k + i9), Block.getBlockById(7).getDefaultState());
                }
            }

            pyramids = 0;
            return true;
        }
        else
        {
            return false;
        }
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

	@Override
	public boolean generate(World worldIn, Random p_180709_2_, BlockPos bp) 
	{
		return generate(worldIn, p_180709_2_, bp.getX(), bp.getY(), bp.getZ());
	}
}
