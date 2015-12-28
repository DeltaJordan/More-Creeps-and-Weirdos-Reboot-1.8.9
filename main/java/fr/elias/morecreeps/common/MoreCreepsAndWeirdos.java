package fr.elias.morecreeps.common;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.elias.morecreeps.client.config.CREEPSConfig;
import fr.elias.morecreeps.client.gui.handler.CREEPSGuiHandler;
import fr.elias.morecreeps.client.render.CREEPSRenderArmyGuy;
import fr.elias.morecreeps.client.render.CREEPSRenderArmyGuyArm;
import fr.elias.morecreeps.client.render.CREEPSRenderAtom;
import fr.elias.morecreeps.client.render.CREEPSRenderBabyMummy;
import fr.elias.morecreeps.client.render.CREEPSRenderBigBaby;
import fr.elias.morecreeps.client.render.CREEPSRenderBlackSoul;
import fr.elias.morecreeps.client.render.CREEPSRenderBlorp;
import fr.elias.morecreeps.client.render.CREEPSRenderBubbleScum;
import fr.elias.morecreeps.client.render.CREEPSRenderBum;
import fr.elias.morecreeps.client.render.CREEPSRenderCamel;
import fr.elias.morecreeps.client.render.CREEPSRenderCamelJockey;
import fr.elias.morecreeps.client.render.CREEPSRenderCastleCritter;
import fr.elias.morecreeps.client.render.CREEPSRenderCastleGuard;
import fr.elias.morecreeps.client.render.CREEPSRenderCastleKing;
import fr.elias.morecreeps.client.render.CREEPSRenderCaveman;
import fr.elias.morecreeps.client.render.CREEPSRenderDesertLizard;
import fr.elias.morecreeps.client.render.CREEPSRenderDigBug;
import fr.elias.morecreeps.client.render.CREEPSRenderDoghouse;
import fr.elias.morecreeps.client.render.CREEPSRenderEvilChicken;
import fr.elias.morecreeps.client.render.CREEPSRenderEvilCreature;
import fr.elias.morecreeps.client.render.CREEPSRenderEvilLight;
import fr.elias.morecreeps.client.render.CREEPSRenderEvilPig;
import fr.elias.morecreeps.client.render.CREEPSRenderEvilScientist;
import fr.elias.morecreeps.client.render.CREEPSRenderEvilSnowman;
import fr.elias.morecreeps.client.render.CREEPSRenderFloob;
import fr.elias.morecreeps.client.render.CREEPSRenderFloobShip;
import fr.elias.morecreeps.client.render.CREEPSRenderG;
import fr.elias.morecreeps.client.render.CREEPSRenderGooGoat;
import fr.elias.morecreeps.client.render.CREEPSRenderGuineaPig;
import fr.elias.morecreeps.client.render.CREEPSRenderHippo;
import fr.elias.morecreeps.client.render.CREEPSRenderHorseHead;
import fr.elias.morecreeps.client.render.CREEPSRenderHotdog;
import fr.elias.morecreeps.client.render.CREEPSRenderHunchback;
import fr.elias.morecreeps.client.render.CREEPSRenderHunchbackSkeleton;
import fr.elias.morecreeps.client.render.CREEPSRenderInvisibleMan;
import fr.elias.morecreeps.client.render.CREEPSRenderKid;
import fr.elias.morecreeps.client.render.CREEPSRenderLawyerFromHell;
import fr.elias.morecreeps.client.render.CREEPSRenderLolliman;
import fr.elias.morecreeps.client.render.CREEPSRenderManDog;
import fr.elias.morecreeps.client.render.CREEPSRenderNonSwimmer;
import fr.elias.morecreeps.client.render.CREEPSRenderPreacher;
import fr.elias.morecreeps.client.render.CREEPSRenderPrisoner;
import fr.elias.morecreeps.client.render.CREEPSRenderPyramidGuardian;
import fr.elias.morecreeps.client.render.CREEPSRenderRatMan;
import fr.elias.morecreeps.client.render.CREEPSRenderRobotTed;
import fr.elias.morecreeps.client.render.CREEPSRenderRobotTodd;
import fr.elias.morecreeps.client.render.CREEPSRenderRockMonster;
import fr.elias.morecreeps.client.render.CREEPSRenderRocketGiraffe;
import fr.elias.morecreeps.client.render.CREEPSRenderSchlump;
import fr.elias.morecreeps.client.render.CREEPSRenderSneakySal;
import fr.elias.morecreeps.client.render.CREEPSRenderSnowDevil;
import fr.elias.morecreeps.client.render.CREEPSRenderSquimp;
import fr.elias.morecreeps.client.render.CREEPSRenderThief;
import fr.elias.morecreeps.client.render.CREEPSRenderTombstone;
import fr.elias.morecreeps.client.render.CREEPSRenderTowel;
import fr.elias.morecreeps.client.render.CREEPSRenderTrophy;
import fr.elias.morecreeps.client.render.CREEPSRenderZebra;
import fr.elias.morecreeps.common.entity.CREEPSEntityArmyGuy;
import fr.elias.morecreeps.common.entity.CREEPSEntityArmyGuyArm;
import fr.elias.morecreeps.common.entity.CREEPSEntityAtom;
import fr.elias.morecreeps.common.entity.CREEPSEntityBabyMummy;
import fr.elias.morecreeps.common.entity.CREEPSEntityBigBaby;
import fr.elias.morecreeps.common.entity.CREEPSEntityBlackSoul;
import fr.elias.morecreeps.common.entity.CREEPSEntityBlorp;
import fr.elias.morecreeps.common.entity.CREEPSEntityBubbleScum;
import fr.elias.morecreeps.common.entity.CREEPSEntityBum;
import fr.elias.morecreeps.common.entity.CREEPSEntityCamel;
import fr.elias.morecreeps.common.entity.CREEPSEntityCamelJockey;
import fr.elias.morecreeps.common.entity.CREEPSEntityCastleCritter;
import fr.elias.morecreeps.common.entity.CREEPSEntityCastleGuard;
import fr.elias.morecreeps.common.entity.CREEPSEntityCastleKing;
import fr.elias.morecreeps.common.entity.CREEPSEntityCaveman;
import fr.elias.morecreeps.common.entity.CREEPSEntityDesertLizard;
import fr.elias.morecreeps.common.entity.CREEPSEntityDigBug;
import fr.elias.morecreeps.common.entity.CREEPSEntityDoghouse;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilChicken;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilCreature;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilLight;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilPig;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilScientist;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilSnowman;
import fr.elias.morecreeps.common.entity.CREEPSEntityFloob;
import fr.elias.morecreeps.common.entity.CREEPSEntityFloobShip;
import fr.elias.morecreeps.common.entity.CREEPSEntityG;
import fr.elias.morecreeps.common.entity.CREEPSEntityGooGoat;
import fr.elias.morecreeps.common.entity.CREEPSEntityGuineaPig;
import fr.elias.morecreeps.common.entity.CREEPSEntityHippo;
import fr.elias.morecreeps.common.entity.CREEPSEntityHorseHead;
import fr.elias.morecreeps.common.entity.CREEPSEntityHotdog;
import fr.elias.morecreeps.common.entity.CREEPSEntityHunchback;
import fr.elias.morecreeps.common.entity.CREEPSEntityHunchbackSkeleton;
import fr.elias.morecreeps.common.entity.CREEPSEntityInvisibleMan;
import fr.elias.morecreeps.common.entity.CREEPSEntityKid;
import fr.elias.morecreeps.common.entity.CREEPSEntityLawyerFromHell;
import fr.elias.morecreeps.common.entity.CREEPSEntityLolliman;
import fr.elias.morecreeps.common.entity.CREEPSEntityManDog;
import fr.elias.morecreeps.common.entity.CREEPSEntityNonSwimmer;
import fr.elias.morecreeps.common.entity.CREEPSEntityPreacher;
import fr.elias.morecreeps.common.entity.CREEPSEntityPrisoner;
import fr.elias.morecreeps.common.entity.CREEPSEntityPyramidGuardian;
import fr.elias.morecreeps.common.entity.CREEPSEntityRatMan;
import fr.elias.morecreeps.common.entity.CREEPSEntityRobotTed;
import fr.elias.morecreeps.common.entity.CREEPSEntityRobotTodd;
import fr.elias.morecreeps.common.entity.CREEPSEntityRockMonster;
import fr.elias.morecreeps.common.entity.CREEPSEntityRocketGiraffe;
import fr.elias.morecreeps.common.entity.CREEPSEntitySchlump;
import fr.elias.morecreeps.common.entity.CREEPSEntitySneakySal;
import fr.elias.morecreeps.common.entity.CREEPSEntitySnowDevil;
import fr.elias.morecreeps.common.entity.CREEPSEntitySquimp;
import fr.elias.morecreeps.common.entity.CREEPSEntityThief;
import fr.elias.morecreeps.common.entity.CREEPSEntityTombstone;
import fr.elias.morecreeps.common.entity.CREEPSEntityTowel;
import fr.elias.morecreeps.common.entity.CREEPSEntityTrophy;
import fr.elias.morecreeps.common.entity.CREEPSEntityZebra;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.stats.Achievement;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid="morecreeps", name="More Creeps And Weirdos Unofficial", version="1.0.0")
public class MoreCreepsAndWeirdos
{
	
	@SidedProxy(clientSide="fr.elias.morecreeps.proxy.ClientProxy", serverSide="fr.elias.morecreeps.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@Mod.Instance("morecreeps")
	public static MoreCreepsAndWeirdos instance;
	
	public static Random rand = new Random();
	static int localID = 0;
	
	private static Logger log = Logger.getLogger(Reference.NAME);
	
	private static ArrayList<String> mobs = new ArrayList<String>();
	
    static CREEPSRenderArmyGuy creepsarmyrender;
    static CREEPSRenderArmyGuyArm creepsarmyarmrender;
    static CREEPSRenderAtom creepsatomrender;
    static CREEPSRenderBabyMummy creepsbmummyrender;
    static CREEPSRenderBigBaby creepsbbabyrender;
    static CREEPSRenderBlackSoul creepsbsoulrender;
    static CREEPSRenderBlorp creepsblorprender;
    static CREEPSRenderBubbleScum creepsbscumrender;
    static CREEPSRenderBum creepsbumrender;
    static CREEPSRenderCamel creepscamelrender;
    static CREEPSRenderCamelJockey creepscjockeyrender;
    static CREEPSRenderCastleCritter creepsccritterrender;
    static CREEPSRenderCastleGuard creepscguardrender;
    static CREEPSRenderCastleKing creepsckingrender;
    static CREEPSRenderCaveman creepscmanrender;
    static CREEPSRenderDesertLizard creepsdlizardrender;
    static CREEPSRenderDigBug creepsdbugrender;
    static CREEPSRenderDoghouse creepsdhouserender;
    static CREEPSRenderEvilChicken creepsechickenrender;
    static CREEPSRenderEvilCreature creepsecreaturerender;
    static CREEPSRenderEvilLight creepselightrender;
    static CREEPSRenderEvilPig creepsepigrender;
    static CREEPSRenderEvilScientist creepsescientistrender;
    static CREEPSRenderEvilSnowman creepsesnowmanrender;
    static CREEPSRenderFloob creepsfloobrender;
    static CREEPSRenderFloobShip creepsfshiprender;
    static CREEPSRenderG creepsgrender;
    static CREEPSRenderGooGoat creepsggoatrender;
    static CREEPSRenderGuineaPig creepsgpigrender;
    static CREEPSRenderHippo creepshipporender;
    static CREEPSRenderHorseHead creepshheadrender;
    static CREEPSRenderHotdog creepshdogrender;
    static CREEPSRenderHunchback creepshbackrender;
    static CREEPSRenderHunchbackSkeleton creepshbacksrender;
    static CREEPSRenderInvisibleMan creepsimanrender;
    static CREEPSRenderKid creepskidrender;
    static CREEPSRenderLawyerFromHell creepslawyerrender;
    static CREEPSRenderLolliman creepslmanrender;
    static CREEPSRenderManDog creepsmdogrender;
    static CREEPSRenderNonSwimmer creepsnonswimmerrender;
    static CREEPSRenderPreacher creepspreacherrender;
    static CREEPSRenderPrisoner creepsprisonerrender;
    static CREEPSRenderPyramidGuardian creepspguardrender;
    static CREEPSRenderRatMan creepsrmanrender;
    static CREEPSRenderRobotTodd creepsrtoddrender;
    static CREEPSRenderRobotTed creepsrtedrender;
    static CREEPSRenderRocketGiraffe creepsrgirafferender;
    static CREEPSRenderRockMonster creepsrmonsterrender;
    static CREEPSRenderSchlump creepsshlumprender;
    static CREEPSRenderSneakySal creepsssalrender;
    static CREEPSRenderSnowDevil creepssdevilrender;
    static CREEPSRenderSquimp creepssquimprender;
    static CREEPSRenderThief creepsthiefrender;
    static CREEPSRenderTombstone creepststonerender;
    static CREEPSRenderTowel creepstowelrender;
    static CREEPSRenderTrophy creepstrophyrender;
    static CREEPSRenderZebra creepszebrarender;
    
    
    
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
    {return MoreCreepsAndWeirdos.money;}};
    
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
		
        zebrahelmet = (new CREEPSItemArmorZebra(zebraARMOR, 5, 0)).setUnlocalizedName("ZebraHelmet");
        zebrabody = (new CREEPSItemArmorZebra(zebraARMOR, 5, 1)).setUnlocalizedName("ZebraBody");
        zebralegs = (new CREEPSItemArmorZebra(zebraARMOR, 5, 2)).setUnlocalizedName("ZebraLegs");
        zebraboots = (new CREEPSItemArmorZebra(zebraARMOR, 5, 3)).setUnlocalizedName("ZebraBoots");
        GameRegistry.registerItem(zebrahelmet, "ZebraHelmet");
        GameRegistry.registerItem(zebrabody, "ZebraBody");
        GameRegistry.registerItem(zebralegs, "ZebraLegs");
        GameRegistry.registerItem(zebraboots, "ZebraBoots");
        
		a_hell = new Item().setUnlocalizedName("HellAchievement");
		a_pig =  new Item().setUnlocalizedName("GuineaPigAchievement");
		a_pyramid = new Item().setUnlocalizedName("PyramidAchievement");
		a_floob = new Item().setUnlocalizedName("FloobicideAchievement");
		a_rockmonster = new Item().setUnlocalizedName("RockMonsterAchievement");
		a_bubble = new Item().setUnlocalizedName("BubbleAchievement");
		a_hotdog = new Item().setUnlocalizedName("HotdogAchievement");
		a_camel = new Item().setUnlocalizedName("CamelAchievement");
		a_zebra = new Item().setUnlocalizedName("ZebraAchievement");
		a_nonswimmer = new Item().setUnlocalizedName("NonswimmerAchievement");
		a_caveman = new Item().setUnlocalizedName("CavemanAchievement");
		GameRegistry.registerItem(a_hell, "HellAchievement");
		GameRegistry.registerItem(a_pig, "GuineaPigAchievement");
		GameRegistry.registerItem(a_pyramid, "PyramidAchievement");
		GameRegistry.registerItem(a_floob, "FloobicideAchievement");
		GameRegistry.registerItem(a_rockmonster, "RockMonsterAchievement");
		GameRegistry.registerItem(a_bubble, "BubbleAchievement");
		GameRegistry.registerItem(a_hotdog, "HotdogAchievement");
		GameRegistry.registerItem(a_camel, "CamelAchievement");
		GameRegistry.registerItem(a_zebra, "ZebraAchievement");
		GameRegistry.registerItem(a_nonswimmer, "NonswimmerAchievement");
		GameRegistry.registerItem(a_caveman, "CavemanAchievement");
		
		blorpcola = new CREEPSItemBlorpCola().setCreativeTab(creepsTab).setUnlocalizedName("blorpcola");
		bandaid = new CREEPSItemBandAid().setCreativeTab(creepsTab).setUnlocalizedName("bandaid");
		goodonut = new CREEPSItemGooDonut().setCreativeTab(creepsTab).setUnlocalizedName("goodonut");
		money = new CREEPSItemMoney().setCreativeTab(creepsTab).setUnlocalizedName("money");
		raygun = new CREEPSItemRayGun().setCreativeTab(creepsTab).setUnlocalizedName("raygun");
		shrinkray = new CREEPSItemShrinkRay().setCreativeTab(creepsTab).setUnlocalizedName("shrinkray");
		shrinkshrink = new Item().setCreativeTab(creepsTab).setUnlocalizedName("shrinkshrink");
		limbs = new CREEPSItemLimbs().setCreativeTab(creepsTab).setUnlocalizedName("limbs");
		babyjarempty = new CREEPSItemBabyJarEmpty().setCreativeTab(creepsTab).setUnlocalizedName("babyjarempty");
		babyjarfull = new CREEPSItemBabyJarFull().setCreativeTab(creepsTab).setUnlocalizedName("babyjarfull");
		mobilephone = new CREEPSItemMobilePhone().setCreativeTab(creepsTab).setUnlocalizedName("mobilephone");
		growray = new CREEPSItemGrowRay().setCreativeTab(creepsTab).setUnlocalizedName("growray");
		frisbee = new CREEPSItemFrisbee().setCreativeTab(creepsTab).setUnlocalizedName("frisbee");
		rayray = new CREEPSItemRayRay().setCreativeTab(creepsTab).setUnlocalizedName("rayray");
		guineapigradio = new CREEPSItemGuineaPigRadio().setCreativeTab(creepsTab).setUnlocalizedName("guineapigradio");
		evilegg = new CREEPSItemEvilEgg().setCreativeTab(creepsTab).setUnlocalizedName("evilegg");
		rocket = new Item().setCreativeTab(creepsTab).setUnlocalizedName("rocket");
		atompacket = new CREEPSItemAtom().setCreativeTab(creepsTab).setUnlocalizedName("atompacket");
		ram16k = new Item().setCreativeTab(creepsTab).setUnlocalizedName("ram16k");
		battery = new CREEPSItemBattery().setCreativeTab(creepsTab).setUnlocalizedName("battery");
		horseheadgem = new CREEPSItemHorseHeadGem().setCreativeTab(creepsTab).setUnlocalizedName("horseheadgem");
		armygem = new CREEPSItemArmyGem().setCreativeTab(creepsTab).setUnlocalizedName("armygem");
		gun = new CREEPSItemGun().setCreativeTab(creepsTab).setUnlocalizedName("gun");
		bullet = new CREEPSItemBullet().setCreativeTab(creepsTab).setUnlocalizedName("bullet");
		lifegem = new CREEPSItemLifeGem().setCreativeTab(creepsTab).setUnlocalizedName("lifegem");
		lolly = new CREEPSItemLolly().setCreativeTab(creepsTab).setUnlocalizedName("lolly");
		armsword = new CREEPSItemArmSword().setCreativeTab(creepsTab).setUnlocalizedName("armsword");
		donut = new CREEPSItemDonut().setCreativeTab(creepsTab).setUnlocalizedName("donut");
		extinguisher = new CREEPSItemExtinguisher().setCreativeTab(creepsTab).setUnlocalizedName("extinguisher");
		zebrahide = new Item().setCreativeTab(creepsTab).setUnlocalizedName("zebrahide");
		firegem = new CREEPSItemFireGem().setCreativeTab(creepsTab).setUnlocalizedName("firegem");
		earthgem = new CREEPSItemEarthGem().setCreativeTab(creepsTab).setUnlocalizedName("earthgem");
		mininggem = new CREEPSItemEarthGem().setCreativeTab(creepsTab).setUnlocalizedName("mininggem");
		healinggem = new CREEPSItemHealingGem().setCreativeTab(creepsTab).setUnlocalizedName("healinggem");
		skygem = new CREEPSItemSkyGem().setCreativeTab(creepsTab).setUnlocalizedName("skygem");
		gemsword = new CREEPSItemGemSword().setCreativeTab(creepsTab).setUnlocalizedName("gemsword");
		moopsworm = new CREEPSItemMoopsWorm().setCreativeTab(creepsTab).setUnlocalizedName("moopsworm");
		cavemanclub = new CREEPSItemCavemanClub().setCreativeTab(creepsTab).setUnlocalizedName("cavemanclub");
		popsicle = new CREEPSItemPopsicle().setCreativeTab(creepsTab).setUnlocalizedName("popsicle");

		registerItem(blorpcola);
		registerItem(bandaid);
		registerItem(goodonut);
		registerItem(money);
		registerItem(raygun);
		registerItem(shrinkray);
		registerItem(shrinkshrink);
		registerItem(limbs);
		registerItem(babyjarempty);
		registerItem(babyjarfull);
		registerItem(mobilephone);
		registerItem(growray);
		registerItem(frisbee);
		registerItem(rayray);
		registerItem(guineapigradio);
		registerItem(evilegg);
		registerItem(rocket);
		registerItem(atompacket);
		registerItem(ram16k);
		registerItem(battery);
		registerItem(horseheadgem);
		registerItem(armygem);
		registerItem(gun);
		registerItem(bullet);
		registerItem(lifegem);
		registerItem(lolly);
		registerItem(armsword);
		registerItem(donut);
		registerItem(extinguisher);
		registerItem(zebrahide);
		registerItem(firegem);
		registerItem(earthgem);
		registerItem(mininggem);
		registerItem(healinggem);
		registerItem(skygem);
		registerItem(gemsword);
		registerItem(moopsworm);
		registerItem(cavemanclub);
		registerItem(popsicle);
		
		registerCreepsMob(CREEPSEntityArmyGuy.class, creepsarmyrender);
		registerCreepsNonMob(CREEPSEntityArmyGuyArm.class, creepsarmyrender);
		registerCreepsNonMob(CREEPSEntityAtom.class, creepsatomrender);
		registerCreepsMob(CREEPSEntityBabyMummy.class, creepsbmummyrender);
		registerCreepsMob(CREEPSEntityBigBaby.class, creepsbbabyrender);
		registerCreepsMob(CREEPSEntityBlackSoul.class, creepsbsoulrender);
		registerCreepsMob(CREEPSEntityBlorp.class, creepsblorprender);
		registerCreepsMob(CREEPSEntityBubbleScum.class, creepsbscumrender);
		registerCreepsMob(CREEPSEntityBum.class, creepsbumrender);
		registerCreepsMob(CREEPSEntityCamel.class, creepscamelrender);
		registerCreepsMob(CREEPSEntityCamelJockey.class, creepscjockeyrender);
		registerCreepsMob(CREEPSEntityCastleCritter.class, creepsccritterrender);
		registerCreepsMob(CREEPSEntityCastleGuard.class, creepscguardrender);
		registerCreepsMob(CREEPSEntityCastleKing.class, creepsckingrender);
		registerCreepsMob(CREEPSEntityCaveman.class, creepscmanrender);
		registerCreepsMob(CREEPSEntityDesertLizard.class, creepsdlizardrender);
		registerCreepsMob(CREEPSEntityDigBug.class, creepsdbugrender);
		registerCreepsNonMob(CREEPSEntityDoghouse.class, creepsdhouserender);
		registerCreepsMob(CREEPSEntityEvilChicken.class, creepsechickenrender);
		registerCreepsMob(CREEPSEntityEvilCreature.class, creepsecreaturerender);
		registerCreepsMob(CREEPSEntityEvilLight.class, creepselightrender);
		registerCreepsMob(CREEPSEntityEvilPig.class, creepsepigrender);
		registerCreepsMob(CREEPSEntityEvilScientist.class, creepsescientistrender);
		registerCreepsMob(CREEPSEntityEvilSnowman.class, creepsesnowmanrender);
		registerCreepsMob(CREEPSEntityFloob.class, creepsfloobrender);
		registerCreepsMob(CREEPSEntityFloobShip.class, creepsfshiprender);
		registerCreepsMob(CREEPSEntityG.class, creepsgrender);
		registerCreepsMob(CREEPSEntityGooGoat.class, creepsggoatrender);
		registerCreepsMob(CREEPSEntityGuineaPig.class, creepsgpigrender);
		registerCreepsMob(CREEPSEntityHippo.class, creepshipporender);
		registerCreepsMob(CREEPSEntityHorseHead.class, creepshheadrender);
		registerCreepsMob(CREEPSEntityHotdog.class, creepshdogrender);
		registerCreepsMob(CREEPSEntityHunchback.class, creepshbackrender);
		registerCreepsMob(CREEPSEntityHunchbackSkeleton.class, creepshbacksrender);
		registerCreepsMob(CREEPSEntityInvisibleMan.class, creepsimanrender);
		registerCreepsMob(CREEPSEntityKid.class, creepskidrender);
		registerCreepsMob(CREEPSEntityLawyerFromHell.class, creepslawyerrender);
		registerCreepsMob(CREEPSEntityLolliman.class, creepslmanrender);
		registerCreepsMob(CREEPSEntityManDog.class, creepsmdogrender);
		registerCreepsMob(CREEPSEntityNonSwimmer.class, creepsnonswimmerrender); 
		registerCreepsMob(CREEPSEntityPreacher.class, creepspreacherrender);
		registerCreepsMob(CREEPSEntityPrisoner.class, creepsprisonerrender);
		registerCreepsMob(CREEPSEntityPyramidGuardian.class, creepspguardrender);
		registerCreepsMob(CREEPSEntityRatMan.class, creepsrmanrender);
		registerCreepsMob(CREEPSEntityRobotTed.class, creepsrtedrender);
		registerCreepsMob(CREEPSEntityRobotTodd.class, creepsrtoddrender);
		registerCreepsMob(CREEPSEntityRocketGiraffe.class, creepsrgirafferender);
		registerCreepsMob(CREEPSEntityRockMonster.class, creepsrmonsterrender);
		registerCreepsMob(CREEPSEntitySchlump.class, creepsshlumprender);
		registerCreepsMob(CREEPSEntitySneakySal.class, creepsssalrender);
		registerCreepsMob(CREEPSEntitySnowDevil.class, creepssdevilrender);
		registerCreepsMob(CREEPSEntitySquimp.class, creepssquimprender);
		registerCreepsMob(CREEPSEntityThief.class, creepsthiefrender);
		registerCreepsMob(CREEPSEntityTombstone.class, creepststonerender);
		registerCreepsNonMob(CREEPSEntityTowel.class, creepstowelrender);
		registerCreepsNonMob(CREEPSEntityTrophy.class, creepstrophyrender);
		registerCreepsMob(CREEPSEntityZebra.class, creepszebrarender);
		
		
		
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
		// it's better like this :)
		NetworkRegistry.INSTANCE.registerGuiHandler(MoreCreepsAndWeirdos.instance, new CREEPSGuiHandler());
		
		proxy.render();
		
		
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(armygem, 0, new ModelResourceLocation("morecreeps:" 
		+ armygem.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(armsword, 0, new ModelResourceLocation("morecreeps:" 
		+ armsword.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(atompacket, 0, new ModelResourceLocation("morecreeps:" 
		+ atompacket.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.babyjarempty, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.babyjarempty.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.babyjarfull, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.babyjarfull.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.bandaid, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.bandaid.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.battery, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.battery.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.blorpcola, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.blorpcola.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.bullet, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.bullet.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.cavemanclub, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.cavemanclub.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.donut, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.donut.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.earthgem, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.earthgem.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.evilegg, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.evilegg.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.extinguisher, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.extinguisher.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.firegem, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.firegem.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.frisbee, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.frisbee.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.gemsword, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.gemsword.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.goodonut, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.goodonut.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.growray, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.growray.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.guineapigradio, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.guineapigradio.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.gun, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.gun.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.healinggem, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.healinggem.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.horseheadgem, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.horseheadgem.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.lifegem, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.lifegem.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.limbs, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.limbs.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.lolly, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.lolly.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.mininggem, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.mininggem.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.mobilephone, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.mobilephone.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.money, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.money.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.ram16k, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.ram16k.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.raygun, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.raygun.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.rayray, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.rayray.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.rocket, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.rocket.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.shrinkray, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.shrinkray.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.shrinkshrink, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.shrinkshrink.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.skygem, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.skygem.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.zebrabody, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.zebrabody.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.zebraboots, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.zebraboots.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.zebrahelmet, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.zebrahelmet.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.zebrahide, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.zebrahide.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.zebralegs, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.zebralegs.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.a_bubble, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.a_bubble.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.a_camel, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.a_camel.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.a_caveman, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.a_caveman.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.a_floob, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.a_floob.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.a_hell, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.a_hell.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.a_hotdog, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.a_hotdog.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.a_nonswimmer, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.a_nonswimmer.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.a_pig, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.a_pig.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.a_pyramid, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.a_pyramid.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.a_rockmonster, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.a_rockmonster.getUnlocalizedName(), "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
		.register(MoreCreepsAndWeirdos.a_zebra, 0, new ModelResourceLocation("morecreeps:" 
		+ MoreCreepsAndWeirdos.a_zebra.getUnlocalizedName(), "inventory"));
		
		
	    
		
		//Registers Recipes
		CREEPSRecipeHandler.Init(event);
		
	}
	
	public Item registerItem(Item item)
	{
		return GameRegistry.registerItem(item, item.getUnlocalizedName(), "morecreeps");
	}
	
	public static void addMob(String name) {
        mobs.add(name);
    }
	
	//Thanks to DivineRPG creators for making their code public and giving me insight to make this <3
	public static void registerCreepsMob(Class<? extends EntityLiving> entityClass, Render renderClass) {
		
		String entityName = entityClass.toString().substring(19);
		System.out.print(entityName);
		
		int randomId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, randomId);
		EntityRegistry.registerModEntity(entityClass, entityName, randomId, instance, 64, 20, true);
		
		RenderingRegistry.registerEntityRenderingHandler(entityClass, renderClass);
		createEgg(randomId);
		
		if (entityClass != CREEPSEntityArmyGuyArm.class &&  entityClass != CREEPSEntityAtom.class && entityClass != CREEPSEntityCastleCritter.class &&
				entityClass != CREEPSEntityCastleGuard.class && entityClass != CREEPSEntityCastleKing.class &&
				entityClass != CREEPSEntityDoghouse.class && entityClass != CREEPSEntityCastleGuard.class && 
				entityClass != CREEPSEntityPrisoner.class && entityClass != CREEPSEntityPyramidGuardian.class && 
				entityClass != CREEPSEntityRatMan.class && entityClass != CREEPSEntitySchlump.class &&
				entityClass != CREEPSEntityTowel.class && entityClass != CREEPSEntityTrophy.class && 
				entityClass != CREEPSEntityHunchbackSkeleton.class){
		for (int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++) {
			BiomeGenBase biome = BiomeGenBase.getBiomeGenArray()[i];
			if (biome != null){
				if (BiomeDictionary.isBiomeOfType(biome, Type.END)) {
					
					//EntityRegistry.addSpawn(entityClass, 2, 1, 4, EnumCreatureType.monster, biome);
					//Maybe Someday?
					
				} else if (BiomeDictionary.isBiomeOfType(biome, Type.NETHER)) {
					
					//EntityRegistry.addSpawn(entityClass, 50, 1, 1, EnumCreatureType.monster, biome);
					
				}
					if (BiomeDictionary.isBiomeOfType(biome, Type.SNOWY)) {
						
						EntityRegistry.addSpawn(entityClass, 50, 1, 5, EnumCreatureType.CREATURE, biome);
						
					}
					if (BiomeDictionary.isBiomeOfType(biome, Type.SANDY)) {
						
						EntityRegistry.addSpawn(entityClass, 50, 1, 5, EnumCreatureType.CREATURE, biome);
						
					}
					if (biome == BiomeGenBase.ocean || biome == BiomeGenBase.deepOcean) {
						
						EntityRegistry.addSpawn(entityClass, 50, 1, 5, EnumCreatureType.CREATURE, biome);
						
					}
					if (BiomeDictionary.isBiomeOfType(biome, Type.BEACH)) {
						
						EntityRegistry.addSpawn(entityClass, 50, 1, 5, EnumCreatureType.CREATURE, biome);
						
					}
					if (BiomeDictionary.isBiomeOfType(biome, Type.JUNGLE)) {
						
						EntityRegistry.addSpawn(entityClass, 50, 1, 5, EnumCreatureType.CREATURE, biome);
						
					}
					if(BiomeDictionary.isBiomeOfType(biome, Type.PLAINS)) {
						
						EntityRegistry.addSpawn(entityClass, 50, 1, 5, EnumCreatureType.CREATURE, biome);
						
					}
					
					if(BiomeDictionary.isBiomeOfType(biome, Type.FOREST)) {
						
						EntityRegistry.addSpawn(entityClass, 50, 1, 5, EnumCreatureType.CREATURE, biome);
                        
                    }
					
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	private static void createEgg(int randomId){
		EntityList.entityEggs.put(Integer.valueOf(randomId), new EntityList.EntityEggInfo(randomId, 0x000000, 0xFFFFFF));
		
	}
	
	public static void registerCreepsNonMob(Class<? extends EntityLiving> entityClass, Render renderClass) {
		
		String entityName = entityClass.toString().substring(19);
		int randomId = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, randomId);
		EntityRegistry.registerModEntity(entityClass, entityName, randomId, instance, 64, 20, true);
		
		RenderingRegistry.registerEntityRenderingHandler(entityClass, renderClass);
	}
	public static void log(Level logLevel, String message) {
	
		log.log(logLevel, message);
	
	}
}
		

	
	
	
