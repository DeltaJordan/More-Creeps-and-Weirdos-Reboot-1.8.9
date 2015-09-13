package creeps.proxys;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import creeps.Log;
import creeps.config.Config;
import creeps.entity.EntityGrowbotgregg;
import creeps.entity.EntityMummy;
import creeps.recipes.CraftingHandler;
import creeps.render.RenderGrowbotGregg;
import creeps.render.RenderMummy;

public class ClientProxy extends CommonProxy {

    @Override
    public void postInit(FMLPostInitializationEvent e) {
	super.postInit(e);
	CraftingHandler.Recipes();
	Log.info("Crafting Handler Loaded");
	Log.info("Added 1 recipe: Medicine");
    }

    @Override
    public void load() {
	super.load();

	/**
	 * Method: if(Config.MODEnabled){
	 * RenderRegistry.registerEntityRenderingHandler(MOBCLASS.class, new
	 * MOBRENDER()); }
	 */

	if (Config.mummyEnabled) {
	    RenderingRegistry.registerEntityRenderingHandler(EntityMummy.class,
		    new RenderMummy());
	}
	if (Config.growbotgreggEnabled) {
	    RenderingRegistry.registerEntityRenderingHandler(
		    EntityGrowbotgregg.class, new RenderGrowbotGregg());
	}
    }

}
