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
    public static boolean mummySpawnsOnlySand = true;
    public static int mummyMinYSpawn = 0;

    public static final Sec secGrowbotgregg = new Sec("GrowbotGregg",
	    "growbotgregg");
    public static boolean growbotgreggEnabled = true;
    public static int growbotgreggMaxDamage = 5;
    public static int growbotgreggMaxHealth = 20;
    public static boolean growbotgreggSpawnsLitAereas = false;
    public static boolean growbotgreggSpawnsOnlySand = true;
    public static int growbotgreggMinYSpawn = 0;

    public static void procConfig(Configuration config) {
	mummyEnabled = config.getBoolean("mummyEnabled", secMummy.name,
		mummyEnabled, "Defines is Mummy is enabled");
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
