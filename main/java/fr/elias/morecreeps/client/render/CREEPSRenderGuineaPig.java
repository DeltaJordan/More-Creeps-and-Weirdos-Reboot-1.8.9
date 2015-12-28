package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.common.entity.CREEPSEntityGooGoat;
import fr.elias.morecreeps.common.entity.CREEPSEntityGuineaPig;

public class CREEPSRenderGuineaPig extends RenderLiving
{
    protected ModelBiped modelBipedMain;

    public CREEPSRenderGuineaPig(ModelBase modelbase, ModelBase modelbase1, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), modelbase, f);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        super.doRender(entityliving, d, d1, d2, f, f1);
        float f2 = 1.6F;
        float f3 = 0.01666667F * f2;
        float f4 = entityliving.getDistanceToEntity(renderManager.livingPlayer);
        String s = "";

        if (((CREEPSEntityGuineaPig)entityliving).speedboost > 0)
        {
            s = "\2473* \247f";
        }

        s = (new StringBuilder()).append(s).append(((CREEPSEntityGuineaPig)entityliving).name).toString();
        String s1 = String.valueOf(((CREEPSEntityGuineaPig)entityliving).level);

        if (((CREEPSEntityGuineaPig)entityliving).getHealth() < ((CREEPSEntityGuineaPig)entityliving).getMaxHealth() / 2 && s.length() > 0)
        {
            s = (new StringBuilder()).append(s).append(" \247c * WOUNDED *").toString();
        }

        if (f4 < 20F && s.length() > 0)
        {
            s = (new StringBuilder()).append(s).append(" \2475<\2476").append(s1).append("\2475>").toString();
            FontRenderer fontrenderer = getFontRendererFromRenderManager();
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d + 0.0F, (float)d1 + 1.1F, (float)d2);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(-f3, -f3, f3);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthMask(false);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();
            float f5 = (1.0F - ((CREEPSEntityGuineaPig)entityliving).modelsize) * 20F;
            int i = 10 + (int)f5;
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            worldRenderer.startDrawingQuads();
            int j = fontrenderer.getStringWidth(s) / 2;
            worldRenderer.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
            worldRenderer.addVertex(-j - 1, -1 + i, 0.0D);
            worldRenderer.addVertex(-j - 1, 8 + i, 0.0D);
            worldRenderer.addVertex(j + 1, 8 + i, 0.0D);
            worldRenderer.addVertex(j + 1, -1 + i, 0.0D);
            float f6 = ((CREEPSEntityGuineaPig)entityliving).getHealth();
            float f7 = ((CREEPSEntityGuineaPig)entityliving).getMaxHealth();
            float f8 = f6 / f7;
            float f9 = 50F * f8;
            worldRenderer.setColorRGBA_F(1.0F, 0.0F, 0.0F, 1.0F);
            worldRenderer.addVertex(-25F + f9, -10 + i, 0.0D);
            worldRenderer.addVertex(-25F + f9, -6 + i, 0.0D);
            worldRenderer.addVertex(25D, -6 + i, 0.0D);
            worldRenderer.addVertex(25D, -10 + i, 0.0D);
            worldRenderer.setColorRGBA_F(0.0F, 1.0F, 0.0F, 1.0F);
            worldRenderer.addVertex(-25D, -10 + i, 0.0D);
            worldRenderer.addVertex(-25D, -6 + i, 0.0D);
            worldRenderer.addVertex(f9 - 25F, -6 + i, 0.0D);
            worldRenderer.addVertex(f9 - 25F, -10 + i, 0.0D);
            tessellator.draw();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, i, 0x20ffffff);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, i, -1);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        }
    }

    protected void fattenup(CREEPSEntityGuineaPig creepsentityguineapig, float f)
    {
        GL11.glScalef(creepsentityguineapig.modelsize, creepsentityguineapig.modelsize, creepsentityguineapig.modelsize);
    }
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        fattenup((CREEPSEntityGuineaPig)entityliving, f);
    }
    
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        doRenderLiving((EntityLiving)entity, d, d1, d2, f, f1);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityGuineaPig entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityGuineaPig) entity);
	}
}
