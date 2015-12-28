package fr.elias.morecreeps.client.render.layers;

import java.util.Random;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import fr.elias.morecreeps.client.render.CREEPSRenderSchlump;
import fr.elias.morecreeps.common.entity.CREEPSEntitySchlump;

public class LayerSchlumpNight implements LayerRenderer
{
	public static Random rand = new Random();
	public static ResourceLocation layer_texture = new ResourceLocation("morecreeps:textures/entity/schlumpnight.png");
	public CREEPSRenderSchlump schlumpRender;

	public LayerSchlumpNight(CREEPSRenderSchlump schlumpRenderer) {
		schlumpRender = schlumpRenderer;
	}

	public void doRenderLayerSchlump(CREEPSEntitySchlump p_177141_1_,float p_177141_2_, float p_177141_3_, float p_177141_4_,float p_177141_5_, float p_177141_6_, float p_177141_7_,float p_177141_8_)
	{
        this.schlumpRender.bindTexture(layer_texture);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(1, 1);

        if (p_177141_1_.isInvisible())
        {
            GlStateManager.depthMask(false);
        }
        else
        {
            GlStateManager.depthMask(true);
        }

        char c0 = 61680;
        int i = c0 % 65536;
        int j = c0 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)i / 1.0F, (float)j / 1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.schlumpRender.getMainModel().render(p_177141_1_, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
        int k = p_177141_1_.getBrightnessForRender(p_177141_4_);
        i = k % 65536;
        j = k / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)i / 1.0F, (float)j / 1.0F);
        this.schlumpRender.func_177105_a(p_177141_1_, p_177141_4_);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
	}

	public boolean shouldCombineTextures() {
		return false;
	}

	public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_,float p_177141_3_, float p_177141_4_, float p_177141_5_,float p_177141_6_, float p_177141_7_, float p_177141_8_)
	{
		doRenderLayerSchlump((CREEPSEntitySchlump) p_177141_1_, p_177141_2_,p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_,	p_177141_7_, p_177141_8_);
	}
}