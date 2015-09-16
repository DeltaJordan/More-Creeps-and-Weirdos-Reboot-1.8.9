package creeps.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import creeps.Log;
import creeps.Reference;

public class Config {

    private Config() {
    }

    public static class Sec {
	public final String name;
	public final String lang;

	public Sec(String name, String lang) {
	    this.name = name;
	    this.lang = lang;
	    register();
	}

	private void register() {
	    secs.add(this);
	}

	public String lc() {
	    return name.toLowerCase();
	}
    }

    public static final List<Sec> secs;

    static {
	secs = new ArrayList<Sec>();
    }

    public static File configDir;
    public static Configuration config;

    public static final Sec secDifficulty = new Sec("Difficulty", "difficulty");
    public static boolean moCreepsDifModifierEnabled = true;

    public static double moCreepsEasyHealthModifier = 0.9;
    public static double moCreepsEasyAttackModifier = 0.9;
    public static double moCreepsNormalHealthModifier = 1;
    public static double moCreepsNormalAttackModifier = 1;
    public static double moCreepsHardHealthModifier = 1.1;
    public static double moCreepsHardAttackModifier = 1.1;

    /**
     * Method: Sec secMOB public static boolean mummyEnabled = true; public
     * static int MOBMaxDamage = 5; public static int MOBMaxHealth = 20; public
     * static boolean MOBSpawnsLitAereas = false; public static boolean
     * MOBSpawnsOnlySand = true; public static int MOBMinYSpawn = 0;
     */

    public static final Sec secMummy = new Sec("Mummy", "mummy");
    public static boolean mummyEnabled = true;
    public static int mummyMaxDamage = 5;
    public static int mummyMaxHealth = 20;
    public static boolean mummySpawnsLitAereas = false;
    public static boolean mummyAttacksPlayerOnSight = false;

    public static final Sec secThief = new Sec("Thief", "thief");
    public static boolean thiefEnabled = true;
    public static int thiefMaxDamage = 5;
    public static int thiefMaxHealth = 20;
    public static boolean thiefSpawnsLitAereas = false;
    public static boolean thiefAttacksPlayerOnSight = false;

    public static final Sec secGrowbotgregg = new Sec("GrowbotGregg",
	    "growbotgregg");
    public static boolean growbotgreggEnabled = true;
    public static int growbotgreggMaxDamage = 5;
    public static int growbotgreggMaxHealth = 20;
    public static boolean growbotgreggAttacksPlayerOnSight = false;
    public static boolean growbotgreggSpawnsLitAereas = false;

    public static final Sec secOldlady = new Sec("OldLady", "oldlady");
    public static boolean oldLadyEnabled = true;
    public static int oldLadyMaxHealth = 20;
    public static boolean oldLadySpawnsLitAereas = false;

    public static final Sec secSneakySal = new Sec("SneakySal", "sneakySal");
    public static boolean sneakySalEnabled = true;
    public static int sneakySalMaxDamage = 5;
    public static int sneakySalMaxHealth = 20;
    public static boolean sneakySalSpawnsLitAereas = false;

    public static void procConfig(Configuration config) {
	// Mummy Configs
	mummyEnabled = config.getBoolean("mummyEnabled", secMummy.name,
		mummyEnabled, "Defines is Mummy is enabled");
	mummyAttacksPlayerOnSight = config
		.getBoolean(
			"growbotgreggAttacksPlayerOnSight",
			secMummy.name,
			mummyAttacksPlayerOnSight,
			"When true, Mummy will attack a player if it looks at them, otherwise they are neutral mobs.");
	mummyMaxDamage = config.get(secMummy.name, "mummyMaxDamage",
		mummyMaxDamage, "Damage that the Mummy does").getInt(
		mummyMaxDamage);
	mummyMaxHealth = config.get(secMummy.name, "mummyMaxHealth",
		mummyMaxHealth, "Health of a Mummy.").getInt(mummyMaxHealth);
	mummySpawnsLitAereas = config
		.getBoolean(
			"mummySpawnsLitAereas",
			secMummy.name,
			mummySpawnsLitAereas,
			"When true, Mummies will spawn in well lit areas, when false they will only spawn in dark areas.");

	// Gregg Configs
	growbotgreggEnabled = config.getBoolean("growbotgreggEnabled",
		secGrowbotgregg.name, growbotgreggEnabled,
		"Defines is Growbot Gregg is enabled");
	growbotgreggAttacksPlayerOnSight = config
		.getBoolean(
			"growbotgreggAttacksPlayerOnSight",
			secGrowbotgregg.name,
			growbotgreggAttacksPlayerOnSight,
			"When true, Growbot Gregg will attack a player if it looks at them, otherwise they are neutral mobs.");
	growbotgreggMaxDamage = config.get(secGrowbotgregg.name,
		"growbotgreggMaxDamage", growbotgreggMaxDamage,
		"Damage that the Growbot Gregg does").getInt(
		growbotgreggMaxDamage);
	growbotgreggMaxHealth = config.get(secGrowbotgregg.name,
		"growbotgreggMaxHealth", growbotgreggMaxHealth,
		"Health of a Growbot Gregg.").getInt(growbotgreggMaxHealth);
	growbotgreggSpawnsLitAereas = config
		.getBoolean(
			"growbotgreggSpawnsLitAereas",
			secGrowbotgregg.name,
			growbotgreggSpawnsLitAereas,
			"When true, Growbot Gregg will spawn in well lit areas, when false they will only spawn in dark areas.");

	// Oldlady configs
	oldLadyEnabled = config.getBoolean("oldLadyEnabled", secOldlady.name,
		oldLadyEnabled, "Defines is Old Lady is enabled");
	oldLadyMaxHealth = config.get(secOldlady.name, "oldLadyMaxHealth",
		oldLadyMaxHealth, "Health of an Old Lady .").getInt(
		oldLadyMaxHealth);
	oldLadySpawnsLitAereas = config
		.getBoolean(
			"oldLadySpawnsLitAereas",
			secOldlady.name,
			oldLadySpawnsLitAereas,
			"When true, Old Lady will spawn in well lit areas, when false they will only spawn in dark areas.");

	// Sneaky Sal Configs
	sneakySalEnabled = config.getBoolean("sneakySalEnabled",
		secSneakySal.name, sneakySalEnabled,
		"Defines is Sneaky Sal is enabled");
	sneakySalMaxHealth = config.get(secSneakySal.name,
		"sneakySalMaxHealth", sneakySalMaxHealth,
		"Health of Sneaky Sal .").getInt(sneakySalMaxHealth);
	sneakySalSpawnsLitAereas = config
		.getBoolean(
			"sneakySalSpawnsLitAereas",
			secSneakySal.name,
			sneakySalSpawnsLitAereas,
			"When true, Sneaky Sal will spawn in well lit areas, when false they will only spawn in dark areas.");
	sneakySalMaxDamage = config.get(secSneakySal.name,
		"sneakySalMaxDamage", sneakySalMaxDamage,
		"Damage Seaky Sal does").getInt(sneakySalMaxDamage);

	// Thief Configs

	thiefEnabled = config.getBoolean("thief", secThief.name, thiefEnabled,
		"Defines is thiefs are enabled");
	thiefMaxHealth = config.get(secThief.name, "thiefMaxHealth",
		thiefMaxHealth, "Thief's health").getInt(thiefMaxHealth);
	thiefMaxDamage = config.get(secThief.name, "thiefMaxDamage",
		thiefMaxDamage, "Thief's damage").getInt(thiefMaxDamage);
	thiefSpawnsLitAereas = config
		.getBoolean(
			"thiefSpawnsLitAereas",
			secThief.name,
			thiefSpawnsLitAereas,
			"When true, Thiefs will spawn in well lit areas, when false they will only spawn in dark areas.");
    }

    public static void syncConfig() {
	try {
	    Config.procConfig(config);
	}
	catch (Exception e) {
	    Log.error(Reference.MOD_NAME + "had a problem loading its configs");
	    e.printStackTrace();
	}
	finally {
	    if (config.hasChanged()) {
		config.save();
	    }
	}
    }

    public static void load(FMLPreInitializationEvent e) {
	FMLCommonHandler.instance().bus().register(new Config());
	configDir = new File(e.getModConfigurationDirectory(),
		Reference.MOD_ID.toLowerCase());
	if (!configDir.exists()) {
	    configDir.mkdir();
	}

	File configFile = new File(configDir, Reference.MOD_NAME + ".cfg");
	config = new Configuration(configFile);
	syncConfig();
    }
}
