package fr.elias.morecreeps.client.render.layers;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import fr.elias.morecreeps.client.render.CREEPSRenderGooGoat;
import fr.elias.morecreeps.common.entity.CREEPSEntityGooGoat;

public class LayerGooGoat implements LayerRenderer
{
	public CREEPSRenderGooGoat gooGoatR;
	private Random rand = new Random();
	public LayerGooGoat(CREEPSRenderGooGoat renderGooGoat)
	{
		gooGoatR = renderGooGoat;
	}
	
	public void doRenderLayerGooGoat(CREEPSEntityGooGoat p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
	{
		int i = rand.nextInt(1);
		if(i == 0)
		{
            GL11.glEnable(GL11.GL_NORMALIZE);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		
		if(i == 1)
		{
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	public boolean shouldCombineTextures() {return false;}

	public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) 
	{
		doRenderLayerGooGoat((CREEPSEntityGooGoat)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
	}
}
