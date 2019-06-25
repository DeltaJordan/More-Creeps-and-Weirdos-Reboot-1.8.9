package fr.elias.morecreeps.common.recipes;

import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CREEPSRecipeHandler {
	
	public static void Init(FMLInitializationEvent event){
		
		GameRegistry.addRecipe(new ItemStack(MoreCreepsAndWeirdos.frisbee, 4), new Object[]
                {
                    "###", "#X#", "###", '#', new ItemStack(Items.dye, 1, 4), 'X', Items.clay_ball
                });
        GameRegistry.addRecipe(new ItemStack(MoreCreepsAndWeirdos.guineapigradio, 1), new Object[]
                {
                    "###", "#X#", "###", '#', Items.wheat, 'X', Items.iron_ingot
                });
        GameRegistry.addRecipe(new ItemStack(MoreCreepsAndWeirdos.rocket, 8), new Object[]
                {
                    "#", "X", "X", '#', Items.iron_ingot, 'X', Blocks.torch
                });
        GameRegistry.addRecipe(new ItemStack(MoreCreepsAndWeirdos.atompacket, 8), new Object[]
                {
                    "XXX", "X#X", "XXX", '#', MoreCreepsAndWeirdos.battery, 'X', MoreCreepsAndWeirdos.ram16k
                });
        GameRegistry.addRecipe(new ItemStack(MoreCreepsAndWeirdos.shrinkray, 1), new Object[]
                {
                    " # ", "XXX", "ZZZ", '#', Items.diamond, 'X', MoreCreepsAndWeirdos.ram16k, 'Z', MoreCreepsAndWeirdos.battery
                });
        GameRegistry.addRecipe(new ItemStack(MoreCreepsAndWeirdos.armsword, 1), new Object[]
                {
                    " X ", " X ", " X ", 'X', MoreCreepsAndWeirdos.limbs
                });
        GameRegistry.addShapelessRecipe(new ItemStack(MoreCreepsAndWeirdos.armsword, 1), new Object[]
                {
                	MoreCreepsAndWeirdos.limbs, MoreCreepsAndWeirdos.limbs, MoreCreepsAndWeirdos.limbs, MoreCreepsAndWeirdos.limbs, MoreCreepsAndWeirdos.limbs, MoreCreepsAndWeirdos.limbs, MoreCreepsAndWeirdos.limbs, MoreCreepsAndWeirdos.limbs, MoreCreepsAndWeirdos.limbs
                });
        GameRegistry.addRecipe(new ItemStack(MoreCreepsAndWeirdos.babyjarempty, 1), new Object[]
                {
                    "ZZZ", "XXX", "XXX", 'Z', Items.iron_ingot, 'X', Blocks.glass
                });
        GameRegistry.addRecipe(new ItemStack(MoreCreepsAndWeirdos.mobilephone, 1), new Object[]
                {
                    "ZZZ", "ZYZ", "XXX", 'X', MoreCreepsAndWeirdos.ram16k, 'Z', Blocks.glass, 'Y', MoreCreepsAndWeirdos.battery
                });
        GameRegistry.addRecipe(new ItemStack(MoreCreepsAndWeirdos.zebrabody, 1), new Object[]
                {
                    "* *", "***", "***", '*', MoreCreepsAndWeirdos.zebrahide
                });
        GameRegistry.addRecipe(new ItemStack(MoreCreepsAndWeirdos.zebralegs, 1), new Object[]
                {
                    "***", "* *", "* *", '*', MoreCreepsAndWeirdos.zebrahide
                });
        GameRegistry.addRecipe(new ItemStack(MoreCreepsAndWeirdos.zebrahelmet, 1), new Object[]
                {
                    "***", "* *", '*', MoreCreepsAndWeirdos.zebrahide
                });
        GameRegistry.addRecipe(new ItemStack(MoreCreepsAndWeirdos.zebraboots, 1), new Object[]
                {
                    "* *", "* *", '*', MoreCreepsAndWeirdos.zebrahide
                });
	}

}
