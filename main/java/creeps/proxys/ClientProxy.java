package creeps.proxys;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
	
	private static final String SOUND_NONE = "none";
	
	
	public class ServerProxy extends CommonProxy {

	    @Override
	    public void preInit(FMLPreInitializationEvent e) {
	        super.preInit(e);
	    }

	    @Override
	    public void init(FMLInitializationEvent e) {
	        super.init(e);
	    }

	    @Override
	    public void postInit(FMLPostInitializationEvent e) {
	        super.postInit(e);
	    }
	
	
	@Override
    public void playSound(double x, double y, double z, String sound, float volume, float frequency,
    		String mod) {
    	if(!SOUND_NONE.equals(sound)) {
	    	ResourceLocation soundLocation = new ResourceLocation(mod, sound);
	    	PositionedSoundRecord record = new PositionedSoundRecord(soundLocation,
					volume, frequency, (float) x, (float) y, (float) z);
	    	
	    	// If we notice this sound is no mod sound, relay it to the default MC sound system.
	    	if(!mod.equals(DEFAULT_RESOURCELOCATION_MOD) && FMLClientHandler.instance().getClient()
	    			.getSoundHandler().getSound(record.getSoundLocation()) == null) {
	    		playSoundMinecraft(x, y, z, sound, volume, frequency);
	    	} else {
		    	FMLClientHandler.instance().getClient().getSoundHandler()
		    		.playSound(record);
	    		}
    		}
		}
	}
}
