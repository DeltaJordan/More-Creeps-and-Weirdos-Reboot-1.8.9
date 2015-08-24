package creeps;

import creeps.item.CreepItems;
import creeps.item.ItemBlorpCola;
import creeps.item.ItemEmptyCan;
import creeps.proxys.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.MOD_ID,
name = Reference.MOD_NAME,
useMetadata = true,
version = Reference.MOD_VERSION
)
@SuppressWarnings("static-access")
public class CreepMain {
	@SidedProxy(clientSide = "creeps.proxys.ClientProxy", serverSide = "creeps.proxys.CommonProxy")
	public static CommonProxy proxy;
	public static Item ItemBlorpCola;
	public static Item ItemEmptyCan;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
	    this.proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
	    this.proxy.init(e);
	    ItemBlorpCola = new ItemBlorpCola().setUnlocalizedName("blorp_cola");
	    ItemEmptyCan = new ItemEmptyCan().setUnlocalizedName("blorp_cola");
	    
	    
	    
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	    this.proxy.postInit(e);
CreativeTabs creepsItemsTab = new CreativeTabs("creepsItemsTab") {
	    	
	    	@Override
	        @SideOnly(Side.CLIENT)
	        public Item getTabIconItem() {
	            return ItemBlorpCola;
	        }
	    };
	    ItemBlorpCola.setCreativeTab(creepsItemsTab);
	    ItemEmptyCan.setCreativeTab(creepsItemsTab);
	}
}