package creeps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import creeps.api.CreepEventHandler;
import creeps.config.Config;
import creeps.entity.EntityGrowbotgregg;
import creeps.entity.EntityMummy;
import creeps.entity.MobInfo;
import creeps.item.ItemArmSword;
import creeps.item.ItemBandAid;
import creeps.item.ItemBattery;
import creeps.item.ItemBlorpCola;
import creeps.item.ItemDonut;
import creeps.item.ItemMedicine;
import creeps.item.ItemWelcome;
import creeps.proxys.CommonProxy;
import creeps.tabs.CreepCreativeTab;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, useMetadata = true, version = Reference.MOD_VERSION)
public class CreepMain {
    @SidedProxy(clientSide = "creeps.proxys.ClientProxy", serverSide = "creeps.proxys.CommonProxy")
    public static CommonProxy proxy;

    // defines Items
    public static Item ItemBlorpCola;
    public static Item ItemBattery;
    public static Item ItemArmSword;
    public static Item ItemWelcome;
    public static Item ItemBandAid;
    public static Item ItemDonut;
    public static Item ItemMedicine;

    // Tool Materials
    public static ToolMaterial FLESH = EnumHelper.addToolMaterial("FLESH", 0,
	    32, 1.0F, 2.0F, 22);

    // CreativeTabs
    public static CreativeTabs creepTab = new CreepCreativeTab(
	    CreativeTabs.getNextID(), "creepTab");

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
	// CreepMain.proxy.preInit(e);

	Config.load(e);
	// register mobs
	for (MobInfo mob : MobInfo.values()) {
	    regEntity(mob);
	}

	// CreepEventHandler; still debugging
	if (e.getSide() == Side.CLIENT) {
	    FMLCommonHandler.instance().bus().register(new CreepEventHandler());
	    Log.info("Event Handler Initialized");
	}

	// Register Items
	regItems();

    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
	proxy.load();

	if (e.getSide() == Side.CLIENT) {
	    renderItems();
	}
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
	CreepMain.proxy.postInit(e);
	// Add Entity Spawn Biomes
	// Debuged values
	// gotta add spawns for OldLady SneakySal and Thief
	EntityRegistry.addSpawn(EntityMummy.class, 50, 10, 120,
		EnumCreatureType.MONSTER, BiomeGenBase.desert);
	EntityRegistry.addSpawn(EntityMummy.class, 50, 10, 120,
		EnumCreatureType.MONSTER, BiomeGenBase.beach);
	EntityRegistry.addSpawn(EntityMummy.class, 50, 10, 120,
		EnumCreatureType.MONSTER, BiomeGenBase.desertHills);
	EntityRegistry.addSpawn(EntityGrowbotgregg.class, 50, 10, 120,
		EnumCreatureType.MONSTER, BiomeGenBase.desert);
	EntityRegistry.addSpawn(EntityGrowbotgregg.class, 50, 10, 120,
		EnumCreatureType.MONSTER, BiomeGenBase.beach);
	EntityRegistry.addSpawn(EntityGrowbotgregg.class, 50, 10, 120,
		EnumCreatureType.MONSTER, BiomeGenBase.desertHills);

	Log.info("Its time to Creep out the world! At least right after its been Weirded out...");
    }

    private void regEntity(MobInfo m) {
	if (!m.isEnabled()) { return; }
	int entityID = EntityRegistry.findGlobalUniqueEntityId();
	EntityRegistry
		.registerGlobalEntityID(m.getClz(), m.getName(), entityID);
	EntityRegistry.registerModEntity(m.getClz(), m.getName(), entityID,
		this, 64, 3, true);
    }

    private void regItems() {
	GameRegistry.registerItem(ItemBlorpCola = new ItemBlorpCola(),
		"blorp_cola");
	GameRegistry.registerItem(ItemBattery = new ItemBattery(), "battery");
	GameRegistry.registerItem(ItemArmSword = new ItemArmSword(FLESH),
		"arm_sword");
	GameRegistry.registerItem(ItemWelcome = new ItemWelcome(), "welcome");
	GameRegistry.registerItem(ItemBandAid = new ItemBandAid(), "bandaid");
	GameRegistry.registerItem(ItemDonut = new ItemDonut(), "donut");
	GameRegistry
		.registerItem(ItemMedicine = new ItemMedicine(), "medicine");
    }

    private void renderItems() {
	RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
	renderItem.getItemModelMesher().register(
		ItemBlorpCola,
		0,
		new ModelResourceLocation(Reference.MOD_ID + ":"
			+ ((ItemBlorpCola) ItemBlorpCola).getName(),
			"inventory"));
	renderItem.getItemModelMesher().register(
		ItemBattery,
		0,
		new ModelResourceLocation(Reference.MOD_ID + ":"
			+ ((ItemBattery) ItemBattery).getName(), "inventory"));
	renderItem.getItemModelMesher()
		.register(
			ItemArmSword,
			0,
			new ModelResourceLocation(Reference.MOD_ID + ":"
				+ ((ItemArmSword) ItemArmSword).getName(),
				"inventory"));
	renderItem.getItemModelMesher().register(
		ItemWelcome,
		0,
		new ModelResourceLocation(Reference.MOD_ID + ":"
			+ ((ItemWelcome) ItemWelcome).getName(), "inventory"));
	renderItem.getItemModelMesher().register(
		ItemBandAid,
		0,
		new ModelResourceLocation(Reference.MOD_ID + ":"
			+ ((ItemBandAid) ItemBandAid).getName(), "inventory"));
	renderItem.getItemModelMesher().register(
		ItemDonut,
		0,
		new ModelResourceLocation(Reference.MOD_ID + ":"
			+ ((ItemDonut) ItemDonut).getName(), "inventory"));
	renderItem.getItemModelMesher()
		.register(
			ItemMedicine,
			0,
			new ModelResourceLocation(Reference.MOD_ID + ":"
				+ ((ItemMedicine) ItemMedicine).getName(),
				"inventory"));
    }
}