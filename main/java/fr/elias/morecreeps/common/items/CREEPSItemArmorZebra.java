package fr.elias.morecreeps.common.items;

import fr.elias.morecreeps.common.MoreCreepsAndWeirdos;
import net.minecraft.item.ItemArmor;

public class CREEPSItemArmorZebra extends ItemArmor
{
    public CREEPSItemArmorZebra(ArmorMaterial enumarmormaterial, int j, int k)
    {
        super(enumarmormaterial, j, k);
        this.setCreativeTab(MoreCreepsAndWeirdos.creepsTab);
    }
}
