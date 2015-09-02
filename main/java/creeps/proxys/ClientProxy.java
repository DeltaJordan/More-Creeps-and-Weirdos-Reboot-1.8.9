package creeps.proxys;

import creeps.entity.EntityMummy;
import creeps.models.ModelMummy;
import creeps.render.RenderMummy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{

	    @Override
	    public void preInit(FMLPreInitializationEvent e) {
	        super.preInit(e);
	    }

	    @Override
	    public void init(FMLInitializationEvent e) {
	        super.init(e);
	     
	    }

	    @Override
	    public void postInit(FMLPostInitializationEvent e) {
	        super.postInit(e);
	    }
	
	    @Override
		public void registerRenderers()
		{
			float shadowSize = 0.0F;
			RenderManager rm = Minecraft.getMinecraft().getRenderManager();
			RenderingRegistry.registerEntityRenderingHandler(EntityMummy.class, new RenderMummy(rm, new ModelMummy(), shadowSize));
		}
	
	}
