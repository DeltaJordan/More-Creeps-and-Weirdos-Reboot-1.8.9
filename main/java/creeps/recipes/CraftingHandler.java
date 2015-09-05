package creeps.recipes;

import creeps.CreepMain;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingHandler {

	public static final void Recipes(){
		
		GameRegistry.addRecipe(new ItemStack(CreepMain.ItemMedicine, 4), new Object[] {"XXX", "###", "###", 'X', Blocks.red_flower, '#', Items.redstone});
		
		
	}
	
}
