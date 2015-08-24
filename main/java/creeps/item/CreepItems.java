package creeps.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import creeps.item.ItemBlorpCola;

public class CreepItems {
	
	public static Item ItemBlorpCola;
	public static Item ItemEmptyCan;
	
	public static void createItems() {
		GameRegistry.registerItem(ItemBlorpCola = new ItemBlorpCola("blorp_cola"), "blorp_cola");
		GameRegistry.registerItem(ItemEmptyCan = new ItemEmptyCan("empty_can"), "empty_can");
    }
}
