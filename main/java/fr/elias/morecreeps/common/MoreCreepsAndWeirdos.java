package fr.elias.morecreeps.common;

import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.stats.Achievement;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import fr.elias.morecreeps.client.config.CREEPSConfig;
import fr.elias.morecreeps.client.gui.handler.CREEPSGuiHandler;
import fr.elias.morecreeps.common.entity.CREEPSEntityArmyGuy;
import fr.elias.morecreeps.common.entity.CREEPSEntityArmyGuyArm;
import fr.elias.morecreeps.common.entity.CREEPSEntityBullet;
import fr.elias.morecreeps.common.entity.CREEPSEntityFoam;
import fr.elias.morecreeps.common.entity.CREEPSEntityFrisbee;
import fr.elias.morecreeps.common.entity.CREEPSEntityGooDonut;
import fr.elias.morecreeps.common.entity.CREEPSEntityGrow;
import fr.elias.morecreeps.common.entity.CREEPSEntityMoney;
import fr.elias.morecreeps.common.entity.CREEPSEntityRay;
import fr.elias.morecreeps.common.entity.CREEPSEntityShrink;
import fr.elias.morecreeps.common.items.CREEPSItemArmSword;
import fr.elias.morecreeps.common.items.CREEPSItemArmorZebra;
import fr.elias.morecreeps.common.items.CREEPSItemArmyGem;
import fr.elias.morecreeps.common.items.CREEPSItemAtom;
import fr.elias.morecreeps.common.items.CREEPSItemBabyJarEmpty;
import fr.elias.morecreeps.common.items.CREEPSItemBabyJarFull;
import fr.elias.morecreeps.common.items.CREEPSItemBandAid;
import fr.elias.morecreeps.common.items.CREEPSItemBattery;
import fr.elias.morecreeps.common.items.CREEPSItemBlorpCola;
import fr.elias.morecreeps.common.items.CREEPSItemBullet;
import fr.elias.morecreeps.common.items.CREEPSItemCavemanClub;
import fr.elias.morecreeps.common.items.CREEPSItemDonut;
import fr.elias.morecreeps.common.items.CREEPSItemEarthGem;
import fr.elias.morecreeps.common.items.CREEPSItemEvilEgg;
import fr.elias.morecreeps.common.items.CREEPSItemExtinguisher;
import fr.elias.morecreeps.common.items.CREEPSItemFireGem;
import fr.elias.morecreeps.common.items.CREEPSItemFrisbee;
import fr.elias.morecreeps.common.items.CREEPSItemGemSword;
import fr.elias.morecreeps.common.items.CREEPSItemGooDonut;
import fr.elias.morecreeps.common.items.CREEPSItemGrowRay;
import fr.elias.morecreeps.common.items.CREEPSItemGuineaPigRadio;
import fr.elias.morecreeps.common.items.CREEPSItemGun;
import fr.elias.morecreeps.common.items.CREEPSItemHealingGem;
import fr.elias.morecreeps.common.items.CREEPSItemHorseHeadGem;
import fr.elias.morecreeps.common.items.CREEPSItemLifeGem;
import fr.elias.morecreeps.common.items.CREEPSItemLimbs;
import fr.elias.morecreeps.common.items.CREEPSItemLolly;
import fr.elias.morecreeps.common.items.CREEPSItemMobilePhone;
import fr.elias.morecreeps.common.items.CREEPSItemMoney;
import fr.elias.morecreeps.common.items.CREEPSItemMoopsWorm;
import fr.elias.morecreeps.common.items.CREEPSItemPopsicle;
import fr.elias.morecreeps.common.items.CREEPSItemRayGun;
import fr.elias.morecreeps.common.items.CREEPSItemRayRay;
import fr.elias.morecreeps.common.items.CREEPSItemShrinkRay;
import fr.elias.morecreeps.common.items.CREEPSItemSkyGem;
import fr.elias.morecreeps.common.recipes.CREEPSRecipeHandler;
import fr.elias.morecreeps.common.world.WorldGenStructures;
import fr.elias.morecreeps.proxy.CommonProxy;

@Mod(modid="morecreeps", name="More Creeps And Weirdos Unofficial", version="1.0.0")
public class MoreCreepsAndWeirdos
{
	
	@SidedProxy(clientSide="fr.elias.morecreeps.proxy.ClientProxy", serverSide="fr.elias.morecreeps.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@Mod.Instance("morecreeps")
	public static MoreCreepsAndWeirdos instance;
	
	public static Random rand = new Random();
	
    private int count;
    
    public int spittime = 500;
    
    public int currentJailX;
    public int currentJailY;
    public int currentJailZ;
    public boolean jailBuilt;
    
    public int currentfine;
    
    public int creepsTimer;
    
    public static int prisonercount = 0;
    public static int colacount = 0;
    public static int rocketcount = 0;
    public static int floobcount = 0;
    public static int goatcount = 0;
    public static int preachercount = 0;
    public static int cavemancount = 0;
    public static boolean cavemanbuilding = false;
    
	//BELOW : This motherfucking particle system of Minecraft... -_-
	public static Item partBubble, partWhite, partRed, partBlack, partYellow, partBlue, partShrink, partBarf;

    public static Item a_hell,
    				   a_pig, 
    				   a_pyramid,
    				   a_floob,
    				   a_rockmonster,
    				   a_bubble, 
    				   a_hotdog,
    				   a_camel, 
    				   a_zebra, 
    				   a_nonswimmer, 
    				   a_caveman;
    
    public static Item blorpcola,
    				   bandaid,
    				   goodonut,
    				   money,
    				   raygun,
    				   shrinkray,
    				   shrinkshrink,
    				   limbs,
    				   babyjarempty,
    				   babyjarfull,
    				   mobilephone,
    				   growray,
    				   frisbee,
    				   rayray,
    				   guineapigradio,
    				   evilegg,
    				   rocket,
    				   atompacket,
    				   ram16k,
    				   battery,
    				   horseheadgem,
    				   armygem,
    				   gun,
    				   bullet,
    				   lifegem,
    				   lolly,
    				   armsword,
    				   donut,
    				   extinguisher,
    				   zebrahide,
    				   firegem,
    				   earthgem,
    				   mininggem,
    				   healinggem,
    				   skygem,
    				   gemsword,
    				   moopsworm,
    				   cavemanclub,
    				   popsicle;
    
    public static ArmorMaterial zebraARMOR = EnumHelper.addArmorMaterial("zebraARMOR", "morecreeps:zebraarmor", 25, new int[] {2, 6, 4, 2}, 5);
    public static Item zebrahelmet,zebrabody,zebralegs,zebraboots;
    
    public static int aX;
    public static int aY;
    public static Achievement achievefrisbee;
    public static Achievement achieveradio;
    public static Achievement achievegotohell;
    public static Achievement achievechugcola;
    public static Achievement achievepigtaming;
    public static Achievement achievepiglevel5;
    public static Achievement achievepiglevel10;
    public static Achievement achievepiglevel20;
    public static Achievement achieverocketgiraffe;
    public static Achievement achieverocket;
    public static Achievement achieverocketrampage;
    public static Achievement achievepyramid;
    public static Achievement achievefloobkill;
    public static Achievement achievefloobicide;
    public static Achievement achievegookill;
    public static Achievement achievegookill10;
    public static Achievement achievegookill25;
    public static Achievement achievesnowdevil;
    public static Achievement achievehunchback;
    public static Achievement achieverockmonster;
    public static Achievement achievebumflower;
    public static Achievement achievebumpot;
    public static Achievement achievebumlava;
    public static Achievement achieve100bucks;
    public static Achievement achieve500bucks;
    public static Achievement achieve1000bucks;
    public static Achievement achievepighotel;
    public static Achievement achieve10bubble;
    public static Achievement achieve25bubble;
    public static Achievement achieve50bubble;
    public static Achievement achieve100bubble;
    public static Achievement achievesnow;
    public static Achievement achievesnowtiny;
    public static Achievement achievesnowtall;
    public static Achievement achievehotdoglevel5;
    public static Achievement achievehotdoglevel10;
    public static Achievement achievehotdoglevel25;
    public static Achievement achievehotdogheaven;
    public static Achievement achievehotdogtaming;
    public static Achievement achieveram128;
    public static Achievement achieveram512;
    public static Achievement achieveram1024;
    public static Achievement achievefalseidol;
    public static Achievement achievecamel;
    public static Achievement achievelolliman;
    public static Achievement achievezebra;
    public static Achievement achieveschlump;
    public static Achievement achievenonswimmer;
    public static Achievement achieveprisoner;
    public static Achievement achieve5prisoner;
    public static Achievement achieve10prisoner;
    public static Achievement achieve1caveman;
    public static Achievement achieve10caveman;
    public static Achievement achieve50caveman;
    
    public static CreativeTabs creepsTab = new CreativeTabs("creepsTab"){public Item getTabIconItem()
    {return MoreCreepsAndWeirdos.a_floob;}};
    
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		
		//Loads config
		CREEPSConfig.preInit(event);
		
		
		partBubble = new Item().setUnlocalizedName("partBubble");
		partWhite = new Item().setUnlocalizedName("partWhite");
		partRed = new Item().setUnlocalizedName("partRed");
		partBlack = new Item().setUnlocalizedName("partBlack");
		partYellow = new Item().setUnlocalizedName("partYellow");
		partBlue = new Item().setUnlocalizedName("partBlue");
		partShrink = new Item().setUnlocalizedName("partShrink");
		partBarf = new Item().setUnlocalizedName("partBarf");
		GameRegistry.registerItem(partBubble, "partBubble");
		GameRegistry.registerItem(partWhite, "partWhite");
		GameRegistry.registerItem(partRed, "partRed");
		GameRegistry.registerItem(partBlack, "partBlack");
		GameRegistry.registerItem(partYellow, "partYellow");
		GameRegistry.registerItem(partBlue, "partBlue");
		GameRegistry.registerItem(partShrink, "partShrink");
		GameRegistry.registerItem(partBarf, "partBarf");
		
        zebrahelmet = (new CREEPSItemArmorZebra(zebraARMOR, 5, 0)).setUnlocalizedName("zebraHelmet");
        zebrabody = (new CREEPSItemArmorZebra(zebraARMOR, 5, 1)).setUnlocalizedName("zebraBody");
        zebralegs = (new CREEPSItemArmorZebra(zebraARMOR, 5, 2)).setUnlocalizedName("zebraLegs");
        zebraboots = (new CREEPSItemArmorZebra(zebraARMOR, 5, 3)).setUnlocalizedName("zebraBoots");
        GameRegistry.registerItem(zebrahelmet, "zebraHelmet");
        GameRegistry.registerItem(zebrabody, "zebraBody");
        GameRegistry.registerItem(zebralegs, "zebraLegs");
        GameRegistry.registerItem(zebraboots, "zebraBoots");
        
		a_hell = new Item().setUnlocalizedName("a_hell");
		a_pig =  new Item().setUnlocalizedName("a_pig");
		a_pyramid = new Item().setUnlocalizedName("a_pyramid");
		a_floob = new Item().setUnlocalizedName("a_floob");
		a_rockmonster = new Item().setUnlocalizedName("a_rockmonster");
		a_bubble = new Item().setUnlocalizedName("a_bubble");
		a_hotdog = new Item().setUnlocalizedName("a_hotdog");
		a_camel = new Item().setUnlocalizedName("a_camel");
		a_zebra = new Item().setUnlocalizedName("a_zebra");
		a_nonswimmer = new Item().setUnlocalizedName("a_nonswimmer");
		a_caveman = new Item().setUnlocalizedName("a_caveman");
		GameRegistry.registerItem(a_hell, "a_hell");
		GameRegistry.registerItem(a_pig, "a_pig");
		GameRegistry.registerItem(a_pyramid, "a_pyramid");
		GameRegistry.registerItem(a_floob, "a_floob");
		GameRegistry.registerItem(a_rockmonster, "a_rockmonster");
		GameRegistry.registerItem(a_bubble, "a_bubble");
		GameRegistry.registerItem(a_hotdog, "a_hotdog");
		GameRegistry.registerItem(a_camel, "a_camel");
		GameRegistry.registerItem(a_zebra, "a_zebra");
		GameRegistry.registerItem(a_nonswimmer, "a_nonswimmer");
		GameRegistry.registerItem(a_caveman, "a_caveman");
		
		blorpcola = new CREEPSItemBlorpCola().setCreativeTab(creepsTab).setUnlocalizedName("blorpCola");
		bandaid = new CREEPSItemBandAid().setCreativeTab(creepsTab).setUnlocalizedName("bandAid");
		goodonut = new CREEPSItemGooDonut().setCreativeTab(creepsTab).setUnlocalizedName("gooDonut");
		money = new CREEPSItemMoney().setCreativeTab(creepsTab).setUnlocalizedName("money");
		raygun = new CREEPSItemRayGun().setCreativeTab(creepsTab).setUnlocalizedName("raygun");
		shrinkray = new CREEPSItemShrinkRay().setCreativeTab(creepsTab).setUnlocalizedName("shrinkray");
		shrinkshrink = new Item().setCreativeTab(creepsTab).setUnlocalizedName("shrinkshrink");
		limbs = new CREEPSItemLimbs().setCreativeTab(creepsTab).setUnlocalizedName("limbs");
		babyjarempty = new CREEPSItemBabyJarEmpty().setCreativeTab(creepsTab).setUnlocalizedName("babyJarEmpty");
		babyjarfull = new CREEPSItemBabyJarFull().setCreativeTab(creepsTab).setUnlocalizedName("babyJarFull");
		mobilephone = new CREEPSItemMobilePhone().setCreativeTab(creepsTab).setUnlocalizedName("mobilephone");
		growray = new CREEPSItemGrowRay().setCreativeTab(creepsTab).setUnlocalizedName("growray");
		frisbee = new CREEPSItemFrisbee().setCreativeTab(creepsTab).setUnlocalizedName("frisbee");
		rayray = new CREEPSItemRayRay().setCreativeTab(creepsTab).setUnlocalizedName("rayray");
		guineapigradio = new CREEPSItemGuineaPigRadio().setCreativeTab(creepsTab).setUnlocalizedName("guineapigRadio");
		evilegg = new CREEPSItemEvilEgg().setCreativeTab(creepsTab).setUnlocalizedName("evilEgg");
		rocket = new Item().setCreativeTab(creepsTab).setUnlocalizedName("rocket");
		atompacket = new CREEPSItemAtom().setCreativeTab(creepsTab).setUnlocalizedName("atomPacket");
		ram16k = new Item().setCreativeTab(creepsTab).setUnlocalizedName("ram16k");
		battery = new CREEPSItemBattery().setCreativeTab(creepsTab).setUnlocalizedName("battery");
		horseheadgem = new CREEPSItemHorseHeadGem().setCreativeTab(creepsTab).setUnlocalizedName("horseHeadGem");
		armygem = new CREEPSItemArmyGem().setCreativeTab(creepsTab).setUnlocalizedName("armyGem");
		gun = new CREEPSItemGun().setCreativeTab(creepsTab).setUnlocalizedName("gun");
		bullet = new CREEPSItemBullet().setCreativeTab(creepsTab).setUnlocalizedName("bullet");
		lifegem = new CREEPSItemLifeGem().setCreativeTab(creepsTab).setUnlocalizedName("lifeGem");
		lolly = new CREEPSItemLolly().setCreativeTab(creepsTab).setUnlocalizedName("lolly");
		armsword = new CREEPSItemArmSword().setCreativeTab(creepsTab).setUnlocalizedName("armSword");
		donut = new CREEPSItemDonut().setCreativeTab(creepsTab).setUnlocalizedName("donut");
		extinguisher = new CREEPSItemExtinguisher().setCreativeTab(creepsTab).setUnlocalizedName("extinguisher");
		zebrahide = new Item().setCreativeTab(creepsTab).setUnlocalizedName("zebrahide");
		firegem = new CREEPSItemFireGem().setCreativeTab(creepsTab).setUnlocalizedName("fireGem");
		earthgem = new CREEPSItemEarthGem().setCreativeTab(creepsTab).setUnlocalizedName("earthGem");
		mininggem = new CREEPSItemEarthGem().setCreativeTab(creepsTab).setUnlocalizedName("miningGem");
		healinggem = new CREEPSItemHealingGem().setCreativeTab(creepsTab).setUnlocalizedName("healingGem");
		skygem = new CREEPSItemSkyGem().setCreativeTab(creepsTab).setUnlocalizedName("skyGem");
		gemsword = new CREEPSItemGemSword().setCreativeTab(creepsTab).setUnlocalizedName("gemSword");
		moopsworm = new CREEPSItemMoopsWorm().setCreativeTab(creepsTab).setUnlocalizedName("moopsWorm");
		cavemanclub = new CREEPSItemCavemanClub().setCreativeTab(creepsTab).setUnlocalizedName("cavemanClub");
		popsicle = new CREEPSItemPopsicle().setCreativeTab(creepsTab).setUnlocalizedName("popsicle");


		GameRegistry.registerItem(blorpcola, "blorpCola", "morecreeps");
		GameRegistry.registerItem(bandaid, "bandAid", "morecreeps");
		GameRegistry.registerItem(goodonut, "gooDonut", "morecreeps");
		GameRegistry.registerItem(money, "money", "morecreeps");
		GameRegistry.registerItem(raygun, "raygun", "morecreeps");
		GameRegistry.registerItem(shrinkray, "shrinkray", "morecreeps");
		GameRegistry.registerItem(shrinkshrink, "shrinkshrink", "morecreeps");
		GameRegistry.registerItem(limbs, "limbs", "morecreeps");
		GameRegistry.registerItem(babyjarempty, "babyJarEmpty", "morecreeps");
		GameRegistry.registerItem(babyjarfull, "babyJarFull", "morecreeps");
		GameRegistry.registerItem(mobilephone, "mobilephone", "morecreeps");
		GameRegistry.registerItem(growray, "growray", "morecreeps");
		GameRegistry.registerItem(frisbee, "frisbee", "morecreeps");
		GameRegistry.registerItem(rayray, "rayray", "morecreeps");
		GameRegistry.registerItem(guineapigradio, "guineapigRadio", "morecreeps");
		GameRegistry.registerItem(evilegg, "evilEgg", "morecreeps");
		GameRegistry.registerItem(rocket, "rocket", "morecreeps");
		GameRegistry.registerItem(atompacket, "atomPacket", "morecreeps");
		GameRegistry.registerItem(ram16k, "ram16k", "morecreeps");
		GameRegistry.registerItem(battery, "battery", "morecreeps");
		GameRegistry.registerItem(horseheadgem, "horseHeadGem", "morecreeps");
		GameRegistry.registerItem(armygem, "armyGem", "morecreeps");
		GameRegistry.registerItem(gun, "gun", "morecreeps");
		GameRegistry.registerItem(bullet, "bullet", "morecreeps");
		GameRegistry.registerItem(lifegem, "lifeGem", "morecreeps");
		GameRegistry.registerItem(lolly, "lolly", "morecreeps");
		GameRegistry.registerItem(armsword, "armSword", "morecreeps");
		GameRegistry.registerItem(donut, "donut", "morecreeps");
		GameRegistry.registerItem(extinguisher, "extinguisher", "morecreeps");
		GameRegistry.registerItem(zebrahide, "zebrahide", "morecreeps");
		GameRegistry.registerItem(firegem, "fireGem", "morecreeps");
		GameRegistry.registerItem(earthgem, "earthGem", "morecreeps");
		GameRegistry.registerItem(mininggem, "miningGem", "morecreeps");
		GameRegistry.registerItem(healinggem, "healingGem", "morecreeps");
		GameRegistry.registerItem(skygem, "skyGem", "morecreeps");
		GameRegistry.registerItem(gemsword, "gemSword", "morecreeps");
		GameRegistry.registerItem(moopsworm, "moopsWorm", "morecreeps");
		GameRegistry.registerItem(cavemanclub, "cavemanClub", "morecreeps");
		GameRegistry.registerItem(popsicle, "popsicle", "morecreeps");
		
        aX = -2;
        aY = 15;
		achievefrisbee = (new Achievement("frisbee", "frisbee", aX, aY, frisbee, null)).func_180788_c();
        achievechugcola = (new Achievement("chugcola", "chugcola", aX + 2, aY, blorpcola, null)).func_180788_c();
        achieveradio = (new Achievement("guineapigradio", "guineapigradio", aX + 4, aY, guineapigradio, null)).func_180788_c();
        achievepyramid = (new Achievement("pyramid", "pyramid", aX + 6, aY, a_pyramid, null)).func_180788_c();
        achievelolliman = (new Achievement("lolliman", "lolliman", aX + 8, aY, lolly, null)).func_180788_c();
        achievesnowdevil = (new Achievement("snowdevil", "snowdevil", aX + 2, aY + 2, Blocks.ice, null)).func_180788_c();
        achievehunchback = (new Achievement("hunchback", "hunchback", aX + 4, aY + 2, Items.cake, null)).func_180788_c();
        achievecamel = (new Achievement("camel", "camel", aX + 6, aY + 2, a_camel, achievecamel)).func_180788_c();
        achievezebra = (new Achievement("zebra", "zebra", aX + 8, aY + 2, a_zebra, achievezebra)).func_180788_c();
        achieverockmonster = (new Achievement("rockmonster", "rockmonster", aX, aY + 2, a_rockmonster, null)).func_180788_c();
        achieveschlump = (new Achievement("schlump", "schlump", aX, aY + 4, babyjarfull, achieveschlump)).func_180788_c();
        achievenonswimmer = (new Achievement("nonswimmer", "nonswimmer", aX + 2, aY + 4, a_nonswimmer, achievenonswimmer)).func_180788_c();
        achievepigtaming = (new Achievement("pigtaming", "pigtaming", aX, aY + 6, a_pig, null)).func_180788_c();
        achievepiglevel5 = (new Achievement("level5", "level5", aX + 2, aY + 6, a_pig, achievepigtaming)).func_180788_c();
        achievepiglevel10 = (new Achievement("level10", "level10", aX + 4, aY + 6, a_pig, achievepiglevel5)).func_180788_c();
        achievepiglevel20 = (new Achievement("level20", "level20", aX + 6, aY + 6, a_pig, achievepiglevel10)).func_180788_c();
        achievepighotel = (new Achievement("pighotel", "pighotel", aX + 8, aY + 6, a_pig, achievepiglevel20)).func_180788_c();
        achieverocketgiraffe = (new Achievement("rocketgiraffe", "rocketgiraffe", aX, aY + 8, rocket, null)).func_180788_c();
        achieverocket = (new Achievement("rocket", "rocket", aX + 2, aY + 8, rocket, achieverocketgiraffe)).func_180788_c();
        achieverocketrampage = (new Achievement("rocketrampage", "rocketrampage", aX + 4, aY + 8, rocket, achieverocket)).func_180788_c();
        achievefloobkill = (new Achievement("floobkill", "floobkill", aX, aY + 10, a_floob, null)).func_180788_c();
        achievefloobicide = (new Achievement("floobicide", "floobicide", aX + 2, aY + 10, a_floob, achievefloobkill)).func_180788_c();
        achievegookill = (new Achievement("gookill", "gookill", aX, aY + 12, goodonut, null)).func_180788_c();
        achievegookill10 = (new Achievement("gookill10", "gookill10", aX + 2, aY + 12, goodonut, achievegookill)).func_180788_c();
        achievegookill25 = (new Achievement("gookill25", "gookill25", aX + 4, aY + 12, goodonut, achievegookill10)).func_180788_c();
        achievebumflower = (new Achievement("bumflower", "bumflower", aX, aY + 14, Blocks.yellow_flower, null)).func_180788_c();
        achievebumpot = (new Achievement("bumpot", "bumpot", aX + 2, aY + 14, Items.bucket, achievebumflower)).func_180788_c();
        achievebumlava = (new Achievement("bumlava", "bumlava", aX + 4, aY + 14, Items.lava_bucket, achievebumpot)).func_180788_c();
        achieve100bucks = (new Achievement("achieve100bucks", "achieve100bucks", aX, aY + 16, money, null)).func_180788_c();
        achieve500bucks = (new Achievement("achieve500bucks", "achieve500bucks", aX + 2, aY + 16, money, achieve100bucks)).func_180788_c();
        achieve1000bucks = (new Achievement("achieve1000bucks", "achieve1000bucks", aX + 4, aY + 16, money, achieve500bucks)).func_180788_c();
        achieve10bubble = (new Achievement("achieve10bubble", "achieve10bubble", aX, aY + 18, a_bubble, null)).func_180788_c();
        achieve25bubble = (new Achievement("achieve25bubble", "achieve25bubble", aX + 2, aY + 18, a_bubble, achieve10bubble)).func_180788_c();
        achieve50bubble = (new Achievement("achieve50bubble", "achieve50bubble", aX + 4, aY + 18, a_bubble, achieve25bubble)).func_180788_c();
        achieve100bubble = (new Achievement("achieve100bubble", "achieve100bubble", aX + 6, aY + 18, a_bubble, achieve50bubble)).func_180788_c();
        achievesnow = (new Achievement("achievesnow", "achievesnow", aX, aY + 20, Items.snowball, null)).func_180788_c();
        achievesnowtiny = (new Achievement("achievesnowtiny", "achievesnowtiny", aX + 2, aY + 20, Items.snowball, null)).func_180788_c();
        achievesnowtall = (new Achievement("achievesnowtall", "achievesnowtall", aX + 4, aY + 20, Items.snowball, null)).func_180788_c();
        achievehotdogtaming = (new Achievement("hotdogtaming", "hotdogtaming", aX, aY + 22, a_hotdog, null)).func_180788_c();
        achievehotdoglevel5 = (new Achievement("hotdoglevel5", "level5", aX + 2, aY + 22, a_hotdog, achievehotdogtaming)).func_180788_c();
        achievehotdoglevel10 = (new Achievement("hotdoglevel10", "level10", aX + 4, aY + 22, a_hotdog, achievehotdoglevel5)).func_180788_c();
        achievehotdoglevel25 = (new Achievement("hotdoglevel25", "level25", aX + 6, aY + 22, a_hotdog, achievehotdoglevel10)).func_180788_c();
        achievehotdogheaven = (new Achievement("hotdogheaven", "hotdogheaven", aX + 8, aY + 22, a_hotdog, achievehotdoglevel25)).func_180788_c();
        achieveram128 = (new Achievement("ram128", "ram128", aX, aY + 24, ram16k, null)).func_180788_c();
        achieveram512 = (new Achievement("ram512", "ram512", aX + 2, aY + 24, ram16k, achieveram128)).func_180788_c();
        achieveram1024 = (new Achievement("ram1024", "ram1024", aX + 4, aY + 24, ram16k, achieveram512)).func_180788_c();
        achievegotohell = (new Achievement("gotohell", "gotohell", aX, aY + 26, a_hell, null)).func_180788_c();
        achievefalseidol = (new Achievement("falseidol", "falseidol", aX + 2, aY + 26, a_hell, achievegotohell)).func_180788_c();
        achieveprisoner = (new Achievement("achieveprisoner", "achieveprisoner", aX, aY + 28, Blocks.iron_bars, null)).func_180788_c();
        achieve5prisoner = (new Achievement("achieve5prisoner", "achieve5prisoner", aX + 2, aY + 28, Blocks.iron_bars, achieveprisoner)).func_180788_c();
        achieve10prisoner = (new Achievement("achieve10prisoner", "achieve10prisoner", aX + 4, aY + 28, Blocks.iron_bars, achieve5prisoner)).func_180788_c();
        achieve1caveman = (new Achievement("achieve1caveman", "achieve1caveman", aX, aY + 30, a_caveman, null)).func_180788_c();
        achieve10caveman = (new Achievement("achieve10caveman", "achieve10caveman", aX + 2, aY + 30, a_caveman, achieve1caveman)).func_180788_c();
        achieve50caveman = (new Achievement("achieve50caveman", "achieve50caveman", aX + 4, aY + 30, a_caveman, achieve10caveman)).func_180788_c();
        
        GameRegistry.registerWorldGenerator(new WorldGenStructures(), 0);
        MinecraftForge.EVENT_BUS.register(new CraftingHandlerEvent());
    }
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(MoreCreepsAndWeirdos.instance, new CREEPSGuiHandler());
		// projectiles registry
		EntityRegistry.registerModEntity(CREEPSEntityShrink.class, "ShrinkEnt", CREEPSConfig.shrink_projectile_ID, this, 40, 1, true);
		EntityRegistry.registerModEntity(CREEPSEntityRay.class, "RayEnt", CREEPSConfig.ray_projectile_ID, this, 40, 1, true);
		EntityRegistry.registerModEntity(CREEPSEntityMoney.class, "MoneyEnt", CREEPSConfig.money_projectile_ID, this, 40, 1, true);
		EntityRegistry.registerModEntity(CREEPSEntityBullet.class, "BulletEnt", CREEPSConfig.bullet_projectile_ID, this, 40, 1, true);
		EntityRegistry.registerModEntity(CREEPSEntityGrow.class, "GrowEnt", CREEPSConfig.grow_projectile_ID, this, 40, 1, true);
		EntityRegistry.registerModEntity(CREEPSEntityGooDonut.class, "GooDonutEnt", CREEPSConfig.gdonut_projectile_ID, this, 40, 1, true);
		EntityRegistry.registerModEntity(CREEPSEntityFrisbee.class, "FrisbeeEnt", CREEPSConfig.frisbee_projectile_ID, this, 40, 1, true);
		EntityRegistry.registerModEntity(CREEPSEntityFoam.class, "FoamEnt", CREEPSConfig.foam_projectile_ID, this, 40, 1, true);
		////////////////////////
		EntityRegistry.registerModEntity(CREEPSEntityArmyGuyArm.class, "ArmyGuyArm", CREEPSConfig.armyguyArm_ID, this, 40, 1, true);
		
		addMob(CREEPSEntityArmyGuy.class, "ArmyGuy", CREEPSConfig.armyguy_ID, CREEPSConfig.sarmyguy, 1, 4, EnumCreatureType.CREATURE, allBiomes());
		
		proxy.render();
		proxy.renderModelItem();
		//Registers Recipes
		CREEPSRecipeHandler.Init(event);
	}
	
	public void addMob(Class <? extends EntityLiving> classz, String name, int id, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, BiomeGenBase... biomes)
	{
		EntityRegistry.registerGlobalEntityID(classz, name, EntityRegistry.findGlobalUniqueEntityId(), 0x000000, 0xFFFFFF);
		EntityRegistry.registerModEntity(classz, name, id, this, 40, 1, true);
		if(weightedProb > 0)
		{
			EntityRegistry.addSpawn(classz, weightedProb, min, max, typeOfCreature, biomes);
		}
	}
	public BiomeGenBase[] allBiomes()
	{
		return new BiomeGenBase[] {
				BiomeGenBase.plains, 
				BiomeGenBase.desert, 
				BiomeGenBase.extremeHills, 
				BiomeGenBase.forest, 
				BiomeGenBase.taiga, 
				BiomeGenBase.swampland, 
				BiomeGenBase.icePlains, 
				BiomeGenBase.iceMountains, 
				BiomeGenBase.beach, 
				BiomeGenBase.desertHills, 
				BiomeGenBase.forestHills, 
				BiomeGenBase.taigaHills, 
				BiomeGenBase.extremeHillsEdge,
				BiomeGenBase.jungle,
				BiomeGenBase.stoneBeach
		};
	}
}