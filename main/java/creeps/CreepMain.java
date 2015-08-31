package creeps;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import creeps.item.ItemBlorpCola;
import creeps.item.ItemBattery;
import creeps.item.ItemArmSword;
import creeps.item.ItemWelcome;
import creeps.item.ItemBandAid;
import creeps.item.ItemDonut;
import creeps.proxys.CommonProxy;
import creeps.tabs.CreepCreativeTab;
import creeps.api.CreepEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID,
name = Reference.MOD_NAME,
useMetadata = true,
version = Reference.MOD_VERSION
)

public class CreepMain {
	@SidedProxy(clientSide = "creeps.proxys.ClientProxy", serverSide = "creeps.proxys.CommonProxy")
	public static CommonProxy proxy;
	
	//defines Items
	public static Item ItemBlorpCola;
	public static Item ItemBattery;
	public static Item ItemArmSword;
	public static Item ItemWelcome;
	public static Item ItemBandAid;
	public static Item ItemDonut;
	
	//define MOD_ID
	public static final String modid = "More Creeps and Weirdos";
	
	//Tool Materials
    public static ToolMaterial FLESH = EnumHelper.addToolMaterial("FLESH", 0, 32, 1.0F, 2.0F, 22);
	
	//I forgot...
	public static List<Item> namesList = new ArrayList<Item>();
	
	//CreativeTabs
	public static CreativeTabs creepTab = new CreepCreativeTab(CreativeTabs.getNextID(), "creepTab");
    
	//Logger
	public static final Logger logger = LogManager.getLogger(modid);
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
	    CreepMain.proxy.preInit(e);
	    
	    //CreepEventHandler; still debugging
	    if(e.getSide() == Side.CLIENT){
	    FMLCommonHandler.instance().bus().register(new CreepEventHandler());
    	System.out.println("More Creeps and Weirdos Event Handler Initialized");
	    }
	    
	    //Register Items
	    GameRegistry.registerItem(ItemBlorpCola = new ItemBlorpCola(), "blorp_cola");
	    GameRegistry.registerItem(ItemBattery = new ItemBattery(), "battery");
	    GameRegistry.registerItem(ItemArmSword = new ItemArmSword(FLESH), "arm_sword");
	    GameRegistry.registerItem(ItemWelcome = new ItemWelcome(), "welcome");
	    GameRegistry.registerItem(ItemBandAid = new ItemBandAid(), "bandaid");
	    GameRegistry.registerItem(ItemDonut = new ItemDonut(), "donut");
	    
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
	    CreepMain.proxy.init(e);
	    
	    if(e.getSide() == Side.CLIENT)
	    {
	    	
	        	RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
	        		
	        	renderItem.getItemModelMesher().register(ItemBlorpCola, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + ((ItemBlorpCola) ItemBlorpCola).getName(), "inventory"));
	        	renderItem.getItemModelMesher().register(ItemBattery, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + ((ItemBattery) ItemBattery).getName(), "inventory"));
	        	renderItem.getItemModelMesher().register(ItemArmSword, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + ((ItemArmSword) ItemArmSword).getName(), "inventory"));
	        	renderItem.getItemModelMesher().register(ItemWelcome, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + ((ItemWelcome) ItemWelcome).getName(), "inventory"));
	        	renderItem.getItemModelMesher().register(ItemBandAid, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + ((ItemBandAid) ItemBandAid).getName(), "inventory"));
	        	renderItem.getItemModelMesher().register(ItemDonut, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + ((ItemDonut) ItemDonut).getName(), "inventory"));
	    }
	    
	    
	    
	    
	    //CreativeTab Items
	    ItemBlorpCola.setCreativeTab(creepTab);
	    ItemBattery.setCreativeTab(creepTab);
	    ItemArmSword.setCreativeTab(creepTab);
	    ItemWelcome.setCreativeTab(creepTab);
	    ItemBandAid.setCreativeTab(creepTab);
	    ItemDonut.setCreativeTab(creepTab);
	   
	    //CreativeTab Blocks
	    
	    
	    //CreativeTab Armor
	    
	    
	    //CreativeTab Tools
	    
	    
	    //CreativeTab Misc.
	    
	    
	    }
	    
	    
	

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	    CreepMain.proxy.postInit(e);
	    logger.info("Its time to Creep out the world! At least right after its been Weirded out...");
	}
}