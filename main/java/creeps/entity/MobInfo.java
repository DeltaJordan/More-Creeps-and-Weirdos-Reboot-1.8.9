package creeps.entity;

import net.minecraft.entity.EntityLiving;
import creeps.config.Config;

// Makes it easier to register mobs

public enum MobInfo {

    /**
     * Method: MOBNAME(MOBCLASS.class, "MOBNAME", Config.MOBENABLED,
     * Config.MOBMAXHEALTH,Config.MOBMAXDAMAGE), MOBNAME'(...);
     */
    MUMMY(EntityMummy.class, "Mummy", Config.mummyEnabled,
	    Config.mummyMaxHealth, Config.mummyMaxDamage), GROWBOTGREGG(
	    EntityGrowbotgregg.class, "Growbot Gregg",
	    Config.growbotgreggEnabled, Config.growbotgreggMaxHealth,
	    Config.growbotgreggMaxDamage), OLDLADY(EntityOldLady.class,
	    "OldLady", Config.oldLadyEnabled, Config.oldLadyMaxHealth, 0), SNEAKYSAL(
	    EntitySneakySal.class, "sneakysal", Config.sneakySalEnabled,
	    Config.sneakySalMaxHealth, Config.sneakySalMaxDamage), THIEF(
	    EntityThief.class, "Thief", Config.thiefEnabled,
	    Config.thiefMaxHealth, Config.thiefMaxDamage);

    final Class<? extends EntityLiving> clz;
    final String name;
    final boolean enabled;
    final double maxHlth;
    final double atkDmg;

    private MobInfo(Class<? extends EntityLiving> clz, String name,
	    boolean enabled, double maxHlth, double atkDmg) {
	this.atkDmg = atkDmg;
	this.clz = clz;
	this.enabled = enabled;
	this.maxHlth = maxHlth;
	this.name = name;
    }

    public Class<? extends EntityLiving> getClz() {
	return clz;
    }

    public String getName() {
	return name;
    }

    public boolean isEnabled() {
	return enabled;
    }

    public double getMaxHealth() {
	return maxHlth;
    }

    public double getMaxDamage() {
	return atkDmg;
    }

}
