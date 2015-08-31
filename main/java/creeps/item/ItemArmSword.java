package creeps.item;

import creeps.Reference;
import net.minecraft.item.ItemSword;

public class ItemArmSword extends ItemSword{

	private final String name = "arm_sword";
	
	public ItemArmSword(ToolMaterial material){
		
		super(material);
		
		setUnlocalizedName(Reference.MOD_ID + "_" + name);
	
	}
	
	public String getName(){
		
	return name;
	
	}

	
}
