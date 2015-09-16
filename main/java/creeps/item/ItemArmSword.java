package creeps.item;

import net.minecraft.item.ItemSword;
import creeps.CreepMain;
import creeps.Reference;

public class ItemArmSword extends ItemSword {

    private final String name = "arm_sword";

    public ItemArmSword(ToolMaterial material) {

	super(material);
	this.setCreativeTab(CreepMain.creepTab);
	setUnlocalizedName(Reference.MOD_ID + "_" + name);

    }

    public String getName() {

	return name;

    }

}
