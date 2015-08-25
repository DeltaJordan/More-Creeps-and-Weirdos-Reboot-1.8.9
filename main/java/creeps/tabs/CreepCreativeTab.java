package creeps.tabs;

import creeps.CreepMain;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

		public final class CreepCreativeTab extends CreativeTabs
		{
		    public CreepCreativeTab(int par1, String par2Str)
		    {
		        super(par1, par2Str);
		    }

        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return CreepMain.ItemBlorpCola;
        }
    
};
	
    

