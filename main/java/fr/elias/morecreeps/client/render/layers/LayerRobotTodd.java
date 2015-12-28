package fr.elias.morecreeps.client.render.layers;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.render.CREEPSRenderRobotTodd;
import fr.elias.morecreeps.common.entity.CREEPSEntityRobotTodd;

public class LayerRobotTodd implements LayerRenderer {

    public static Random rand = new Random();
	public static ResourceLocation layer_texture = new ResourceLocation("/armor/power.png");
	public CREEPSRenderRobotTodd robotToddRender;
	public LayerRobotTodd(CREEPSRenderRobotTodd renderRTodd)
	{
		robotToddRender = renderRTodd;
	}
	
	public void doRenderLayerTodd(CREEPSEntityRobotTodd p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
	{
        if (p_177141_1_.hurtTime > 0)
        {
                float f1 = rand.nextInt(30);
                Minecraft.getMinecraft().getTextureManager().bindTexture(layer_texture);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                float f2 = f1 * 0.01F;
                float f3 = f1 * 0.01F;
                GL11.glTranslatef(f2, f3, 0.0F);
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_BLEND);
                float f4 = 0.5F;
                GL11.glColor4f(f4, f4, f4, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
        }
	}
	public boolean shouldCombineTextures() {return false;}

	public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) 
	{
		doRenderLayerTodd((CREEPSEntityRobotTodd)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
	}
}
