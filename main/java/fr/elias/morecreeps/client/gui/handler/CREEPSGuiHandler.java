package fr.elias.morecreeps.client.gui.handler;

import fr.elias.morecreeps.client.gui.CREEPSGUICamelname;
import fr.elias.morecreeps.client.gui.CREEPSGUIGiraffename;
import fr.elias.morecreeps.client.gui.CREEPSGUIGuineaPig;
import fr.elias.morecreeps.client.gui.CREEPSGUIGuineaPigTraining;
import fr.elias.morecreeps.client.gui.CREEPSGUIHotdog;
import fr.elias.morecreeps.client.gui.CREEPSGUISneakySal;
import fr.elias.morecreeps.client.gui.CREEPSGUIZebraname;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import fr.elias.morecreeps.common.entity.CREEPSEntityGuineaPig;
import fr.elias.morecreeps.common.entity.CREEPSEntityHotdog;
import fr.elias.morecreeps.common.entity.CREEPSEntityRocketGiraffe;
import fr.elias.morecreeps.common.entity.CREEPSEntitySneakySal;
import fr.elias.morecreeps.common.entity.CREEPSEntityZebra;

public class CREEPSGuiHandler  implements IGuiHandler
{
	private CREEPSEntityHotdog hotdog;
	private CREEPSEntityGuineaPig guineapig;
	private CREEPSEntityRocketGiraffe rocketgiraffe;
	private CREEPSEntitySneakySal sneakysal;
	private CREEPSEntityZebra zebra;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
    {
    	if (ID == 1){
    		return new CREEPSGUICamelname();
    	}
    	
    	if (ID == 2){
    		//name hotdog
    		return new CREEPSGUIHotdog(hotdog);
    	}
    	
    	if (ID == 3){
    		return new CREEPSGUIGuineaPigTraining(guineapig);
    	}
    	
    	if (ID == 4){
    		return new CREEPSGUIGuineaPig(guineapig);
    	}
    	
    	if (ID == 5){
    		return new CREEPSGUIGiraffename(rocketgiraffe);
    	}
    	
    	if (ID == 6){
    		return new CREEPSGUISneakySal(sneakysal);
    	}
    	
    	if (ID == 7){
    		return new CREEPSGUIZebraname(zebra);
    	}

            return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
           
    	if (ID == 1){
    		return new CREEPSGUICamelname();
    	}
    	
    	if (ID == 2){
    		//name hotdog
    		return new CREEPSGUIHotdog(hotdog);
    	}
    	
    	if (ID == 3){
    		//gpig stats
    		return new CREEPSGUIGuineaPigTraining(guineapig);
    	}
    	
    	if (ID == 4){
    		//name gpig
    		return new CREEPSGUIGuineaPig(guineapig);
    	}
    	
    	if (ID == 5){
    		//name giraffe
    		return new CREEPSGUIGiraffename(rocketgiraffe);
    	}
    	
    	if (ID == 6){
    		return new CREEPSGUISneakySal(sneakysal);
    	}
    	
    	if (ID == 7){
    		return new CREEPSGUIZebraname(zebra);
    	}

            return null;
    }
}