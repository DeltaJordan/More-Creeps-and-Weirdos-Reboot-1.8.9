package creeps;

import java.util.ArrayList;
import java.util.List;

import creeps.item.ItemBlorpCola;
import creeps.proxys.CommonProxy;
import creeps.tabs.CreepCreativeTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID,
name = Reference.MOD_NAME,
useMetadata = true,
version = Reference.MOD_VERSION
)

public class CreepMain {
	@SidedProxy(clientSide = "creeps.proxys.ClientProxy", serverSide = "creeps.proxys.CommonProxy")
	public static CommonProxy proxy;
	public static Item ItemBlorpCola;
	public static Item ItemEmptyCan;
	public static List<Item> namesList = new ArrayList<Item>();
	public static CreativeTabs creepTab = new CreepCreativeTab(CreativeTabs.getNextID(), "creepTab");
    
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
	    CreepMain.proxy.preInit(e);
	    
	    
	    ItemBlorpCola = new ItemBlorpCola().setUnlocalizedName("blorp_cola");
	    
	    GameRegistry.registerItem(ItemBlorpCola = new ItemBlorpCola(), "blorp_cola");
	    
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
	    CreepMain.proxy.init(e);
	    
	    ItemBlorpCola.setCreativeTab(creepTab);
	   
	    
	    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ItemBlorpCola, 0, new ModelResourceLocation("MODID:blorp_cola", "inventory"));
	    
	    }
	    
	    
	

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	    CreepMain.proxy.postInit(e);
	    System.out.println("Building Creeps and Installing Weirdos");
	}
}