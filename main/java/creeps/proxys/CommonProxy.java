package creeps.proxys;

import creeps.Reference;
import creeps.api.ILocation;
import creeps.network.PacketHandler;
import creeps.network.packet.SoundPacket;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	protected static final String DEFAULT_RESOURCELOCATION_MOD = "minecraft";
	

	    public void preInit(FMLPreInitializationEvent e) {
	    
	    	
	    }

	    public void init(FMLInitializationEvent e) {

	    }

	    public void postInit(FMLPostInitializationEvent e) {

	    }
	
	
	
	public void playSoundMinecraft(ILocation location, String sound, float volume, float frequency) {
    	int[] c = location.getCoordinates();
    	playSoundMinecraft(c[0], c[1], c[2], sound, volume, frequency);
    }
    
    /**
     * Play a minecraft sound, will do nothing serverside, use {@link CommonProxy#sendSound(double,
     * double, double, String, float, float, String)} for this.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param z The Z coordinate.
     * @param sound The sound name to play.
     * @param volume The volume of the sound.
     * @param frequency The pitch of the sound.
     */
    public void playSoundMinecraft(double x, double y, double z, String sound, float volume, float frequency) {
    	playSound(x, y, z, sound, volume, frequency, DEFAULT_RESOURCELOCATION_MOD);
    }
    
    /**
     * Play a sound, will do nothing serverside, use {@link CommonProxy#sendSound(double,
     * double, double, String, float, float, String)} for this.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param z The Z coordinate.
     * @param sound The sound name to play.
     * @param volume The volume of the sound.
     * @param frequency The pitch of the sound.
     * @param mod The mod that has this sound.
     */
    public void playSound(double x, double y, double z, String sound, float volume, float frequency,
    		String mod) {
    	// No implementation server-side.
    }
    
    /**
     * Play an evilcraft sound, will do nothing serverside, use {@link CommonProxy#sendSound(double,
     * double, double, String, float, float, String)} for this.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param z The Z coordinate.
     * @param sound The sound name to play.
     * @param volume The volume of the sound.
     * @param frequency The pitch of the sound.
     */
    public void playSound(double x, double y, double z, String sound, float volume, float frequency) {
    	playSound(x, y, z, sound, volume, frequency, Reference.MOD_ID);
    }
    
    /**
     * Send a minecraft sound packet.
     * @param location The location.
     * @param sound The sound name to play.
     * @param volume The volume of the sound.
     * @param frequency The pitch of the sound.
     */
    public void sendSoundMinecraft(ILocation location, String sound, float volume, float frequency) {
    	int[] c = location.getCoordinates();
		sendSound(c[0], c[1], c[2], sound, volume, frequency, DEFAULT_RESOURCELOCATION_MOD);
    }
    
    /**
     * Send a minecraft sound packet.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param z The Z coordinate.
     * @param sound The sound name to play.
     * @param volume The volume of the sound.
     * @param frequency The pitch of the sound.
     */
    public void sendSoundMinecraft(double x, double y, double z, String sound, float volume, float frequency) {
		sendSound(x, y, z, sound, volume, frequency, DEFAULT_RESOURCELOCATION_MOD);
    }
    
    /**
     * Send a sound packet.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param z The Z coordinate.
     * @param sound The sound name to play.
     * @param volume The volume of the sound.
     * @param frequency The pitch of the sound.
     * @param mod The mod id that has this sound.
     */
    public void sendSound(double x, double y, double z, String sound, float volume, float frequency,
    		String mod) {
    	SoundPacket packet = new SoundPacket(x, y, z, sound, volume, frequency, mod);
		PacketHandler.sendToServer(packet);
    }
    
    /**
     * Send an evilcraft sound packet.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param z The Z coordinate.
     * @param sound The sound name to play.
     * @param volume The volume of the sound.
     * @param frequency The pitch of the sound.
     */
    public void sendSound(double x, double y, double z, String sound, float volume, float frequency) {
    	sendSound(x, y, z, sound, volume, frequency, Reference.MOD_ID);
    }
}

