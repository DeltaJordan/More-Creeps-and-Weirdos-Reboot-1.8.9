package creeps.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import creeps.CreepMain;

public final class CreepItemRender {
	
	public static void registerItemRender(){
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(CreepMain.ItemBlorpCola, 0, new ModelResourceLocation("creeps:blorp_cola", "inventory"));
		
	}
	

	}

