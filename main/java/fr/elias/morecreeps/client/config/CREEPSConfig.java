package fr.elias.morecreeps.client.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CREEPSConfig {
	
	public static boolean pyramidGen;
    public static int pyramidRarity;
    public static int castlecount;
    public static boolean castleGen;
    public static int castleRarity;
    public static boolean rayGunFire;
    public static boolean rayGunMelt;
    public static boolean floobshipExplode;
    public static boolean publicUrination;
    public static boolean jailActive;
    public static boolean Blood = true;
    
    public static int pyramids;
    
    public static int numCreeps;
    
    public static int sblorpmaxsize;
	
	public static int sarmyguy;
	public static int sbabymummy;
	public static int sbigbaby;
	public static int sblacksoul;
	public static int sblorp;
	public static int sbubblescum;
	public static int sbum;
	public static int scamel;
	public static int scameljockey;
	public static int scaveman;
	public static int sdesertlizard;
	public static int sdigbug;
	public static int sevilscientist;
	public static int sfloob;
	public static int sfloobship;
	public static int sg;
	public static int sgoogoat;
	public static int sguineapig;
	public static int shippo;
	public static int shorsehead;
	public static int shotdog;
	public static int shunchback;
	public static int sinvisibleman;
	public static int skid;
	public static int slawyerfromhell;
	public static int slolliman;
	public static int smandog;
	public static int smummy;
	public static int snonswimmer;
	public static int spreacher;
	public static int srobotted;
	public static int srobottodd;
	public static int srockmonster;
	public static int srocketgiraffe;
	public static int ssneakysal;
	public static int ssnowdevil;
	public static int sthief;
    public static int szebra;
	
	private static boolean unlimitedSpawn;
	
	public static int shrink_projectile_ID,
					  ray_projectile_ID,
					  money_projectile_ID,
					  bullet_projectile_ID,
					  grow_projectile_ID,
					  gdonut_projectile_ID,
					  frisbee_projectile_ID,
					  foam_projectile_ID;
	
	public static int armyguy_ID,
					  armyguyArm_ID;
	
	public static void preInit(FMLPreInitializationEvent event){
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		String spawnNbr = "Spawn Number";
		String entityID = "Entity";
		String mobProperty = "Mob Property";
		String itemProperty = "Item Property";
		String worldGen = "World Gen";
		String worldGenRarity = "World Gen Rarity";
		try {
			config.load();
			unlimitedSpawn = config.get("Spawn", "Unlimited spawn length", false, "Once activated, you can set what value you want for mob spawning.").getBoolean();
			sarmyguy = config.get(spawnNbr, "Army Guy", 5).getInt();
	        sbabymummy = config.get(spawnNbr, "Baby Mummy", 8).getInt();
	        sbigbaby = config.get(spawnNbr, "Big Baby", 6).getInt();
	        sblacksoul = config.get(spawnNbr, "Black Soul", 5).getInt();
	        sblorp = config.get(spawnNbr, "Blorp", 8).getInt();
	        sbubblescum = config.get(spawnNbr, "Bubble Scum", 8).getInt();
	        sbum = config.get(spawnNbr, "Bum", 8).getInt();
	        scamel = config.get(spawnNbr, "Camel", 8).getInt();
	        scameljockey = config.get(spawnNbr, "Camel Jockey", 6).getInt();
	        scaveman = config.get(spawnNbr, "Caveman", 5).getInt();
	        sdesertlizard = config.get(spawnNbr, "Desert Lizard", 8).getInt();
	        sdigbug = config.get(spawnNbr, "Dig Bug", 7).getInt();
	        sevilscientist = config.get(spawnNbr, "Evil Scientist", 8).getInt();
	        sfloob = config.get(spawnNbr, "Floob", 7).getInt();
	        sfloobship = config.get(spawnNbr, "Floob Ship", 2).getInt();
	        sg = config.get(spawnNbr, "G", 8).getInt();
	        sgoogoat = config.get(spawnNbr, "Goo Goat", 8).getInt();
	        sguineapig = config.get(spawnNbr, "Guinea Pig", 8).getInt();
	        shippo = config.get(spawnNbr, "Hippo", 8).getInt();
	        shorsehead = config.get(spawnNbr, "Horse Head", 8).getInt();
	        shotdog = config.get(spawnNbr, "Hot Dog", 8).getInt();
	        shunchback = config.get(spawnNbr, "Hunchback", 8).getInt();
	        sinvisibleman = config.get(spawnNbr, "Invisibleman", 8).getInt();
	        skid = config.get(spawnNbr, "Kid", 5).getInt();
	        slawyerfromhell = config.get(spawnNbr, "Lawyer from hell", 8).getInt();
	        slolliman = config.get(spawnNbr, "Lolliman", 3).getInt();
	        smandog = config.get(spawnNbr, "Man Dog", 8).getInt();
	        smummy = config.get(spawnNbr, "Mummy", 5).getInt();
	        snonswimmer = config.get(spawnNbr, "Non Swimmer", 6).getInt();
	        spreacher = config.get(spawnNbr, "Preacher", 8).getInt();
	        srobotted = config.get(spawnNbr, "Robot Ted", 8).getInt();
	        srobottodd = config.get(spawnNbr, "Robot Todd", 8).getInt();
	        srockmonster = config.get(spawnNbr, "Rock Monster", 8).getInt();
	        srocketgiraffe = config.get(spawnNbr, "Rocket Giraffe", 7).getInt();
	        ssneakysal = config.get(spawnNbr, "Sneaky Sal", 8).getInt();
	        ssnowdevil = config.get(spawnNbr, "Snow Devil", 8).getInt();
	        sthief = config.get(spawnNbr, "Thief", 8).getInt();
	        szebra = config.get(spawnNbr, "Zebra", 8).getInt();
	        pyramidGen = config.get(worldGen, "Enable Pyramid Gen", true).getBoolean();
	        pyramidRarity = config.get(worldGenRarity, "Pyramid rarity", 500, "By default : 500").getInt();
	        castleGen = config.get(worldGen, "Enable Castle", true).getBoolean();
	        castleRarity = config.get(worldGenRarity, "Castle Rarity", 500, "By default : 500").getInt();
	        rayGunFire = config.get(itemProperty, "Raygun fire", false).getBoolean();
	        rayGunMelt = config.get(itemProperty, "Raygun melt", false).getBoolean();
	        floobshipExplode = config.get(mobProperty, "Allow Floobship Explosion", false).getBoolean();
	        publicUrination = config.get(mobProperty, "Allow Bum Public Urination", true).getBoolean();
	        jailActive = config.get(worldGen, "Enable Jail", true).getBoolean();
	        Blood = config.get(mobProperty, "Enable blood", true).getBoolean();
	        sblorpmaxsize = config.get(mobProperty, "Blorp Max Size", 6).getInt();
	        /////////////////PROJECTILE START/////////////////////////
	        shrink_projectile_ID = config.get(entityID, "Shrink Projectile ID", 925).getInt();
	        ray_projectile_ID = config.get(entityID, "Ray Projectile ID", 926).getInt();
	        money_projectile_ID = config.get(entityID, "Money Projectile ID", 927).getInt();
	        bullet_projectile_ID = config.get(entityID, "Bullet Projectile ID", 928).getInt();
	        grow_projectile_ID = config.get(entityID, "Grow Projectile ID", 929).getInt();
	        gdonut_projectile_ID = config.get(entityID, "Goo Donut Projectile ID", 930).getInt();
	        frisbee_projectile_ID = config.get(entityID, "Frisbee Projectile ID", 931).getInt();
	        foam_projectile_ID = config.get(entityID, "Foam Projectile ID", 932).getInt();
	        /////////////////PROJECTILE END///////////////////////////
	        armyguy_ID = config.get(entityID, "Army Guy ID", 934).getInt();
	        armyguyArm_ID = config.get(entityID, "Army Guy Arm ID", 935).getInt();
			config.save();
		} finally{
			if(config.hasChanged())
			{
				config.save();
			}
		}
		CREEPSConfig.applySpawnLimit();
	}
	
	public static void applySpawnLimit()
	{
		if(!unlimitedSpawn)
		{
	        if (sarmyguy < 0 || sarmyguy > 12)
	        {
	            sarmyguy = 5;
	        }

	        if (sbabymummy < 0 || sbabymummy > 12)
	        {
	            sbabymummy = 7;
	        }

	        if (sbigbaby < 0 || sbigbaby > 12)
	        {
	            sbigbaby = 5;
	        }

	        if (sblacksoul < 0 || sblacksoul > 12)
	        {
	            sblacksoul = 5;
	        }

	        if (sblorp < 0 || sblorp > 12)
	        {
	            sblorp = 7;
	        }

	        if (sbubblescum < 0 || sbubblescum > 12)
	        {
	            sbubblescum = 6;
	        }

	        if (sbum < 0 || sbum > 12)
	        {
	            sbum = 6;
	        }

	        if (scamel < 0 || scamel > 12)
	        {
	            scamel = 7;
	        }

	        if (scameljockey < 0 || scameljockey > 12)
	        {
	            scameljockey = 6;
	        }

	        if (scaveman < 0 || scaveman > 12)
	        {
	            scaveman = 5;
	        }

	        if (sdesertlizard < 0 || sdesertlizard > 12)
	        {
	            sdesertlizard = 7;
	        }

	        if (sdigbug < 0 || sdigbug > 12)
	        {
	            sdigbug = 7;
	        }

	        if (sevilscientist < 0 || sevilscientist > 12)
	        {
	            sevilscientist = 7;
	        }

	        if (sfloob < 0 || sfloob > 12)
	        {
	            sfloob = 7;
	        }

	        if (sfloobship < 0 || sfloobship > 12)
	        {
	            sfloobship = 2;
	        }

	        if (sg < 0 || sg > 12)
	        {
	            sg = 7;
	        }

	        if (sgoogoat < 0 || sgoogoat > 12)
	        {
	            sgoogoat = 7;
	        }

	        if (sguineapig < 0 || sguineapig > 12)
	        {
	            sguineapig = 9;
	        }

	        if (shippo < 0 || shippo > 12)
	        {
	            shippo = 7;
	        }

	        if (shorsehead < 0 || shorsehead > 12)
	        {
	            shorsehead = 7;
	        }

	        if (shotdog < 0 || shotdog > 12)
	        {
	            shotdog = 7;
	        }

	        if (shunchback < 0 || shunchback > 12)
	        {
	            shunchback = 7;
	        }

	        if (sinvisibleman < 0 || sinvisibleman > 12)
	        {
	            sinvisibleman = 7;
	        }

	        if (skid < 0 || skid > 12)
	        {
	            skid = 5;
	        }

	        if (slawyerfromhell < 0 || slawyerfromhell > 12)
	        {
	            slawyerfromhell = 7;
	        }

	        if (slolliman < 0 || slolliman > 12)
	        {
	            slolliman = 3;
	        }

	        if (smandog < 0 || smandog > 12)
	        {
	            smandog = 7;
	        }

	        if (smummy < 0 || smummy > 12)
	        {
	            smummy = 5;
	        }

	        if (snonswimmer < 0 || snonswimmer > 12)
	        {
	            snonswimmer = 5;
	        }

	        if (ssneakysal < 0 || ssneakysal > 12)
	        {
	            ssneakysal = 6;
	        }

	        if (spreacher < 0 || spreacher > 12)
	        {
	            spreacher = 7;
	        }

	        if (srobotted < 0 || srobotted > 12)
	        {
	            srobotted = 7;
	        }

	        if (srobottodd < 0 || srobottodd > 12)
	        {
	            srobottodd = 7;
	        }

	        if (srockmonster < 0 || srockmonster > 12)
	        {
	            srockmonster = 7;
	        }

	        if (srocketgiraffe < 0 || srocketgiraffe > 12)
	        {
	            srocketgiraffe = 7;
	        }

	        if (ssnowdevil < 0 || ssnowdevil > 12)
	        {
	            ssnowdevil = 7;
	        }

	        if (sthief < 0 || sthief > 12)
	        {
	            sthief = 7;
	        }

	        if (szebra < 0 || szebra > 12)
	        {
	            szebra = 6;
	        }
	        if (pyramidRarity < 1 || pyramidRarity > 10)
	        {
	            pyramidRarity = 5;
	        }
	        if (castleRarity < 1 || castleRarity > 10)
	        {
	            castleRarity = 5;
	        }
	        if (sblorpmaxsize < 6 || sblorpmaxsize > 99)
	        {
	            sblorpmaxsize = 6;
	        }
		}
	}
}
