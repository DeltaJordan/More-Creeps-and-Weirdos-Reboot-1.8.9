package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelTombstone;
import fr.elias.morecreeps.common.entity.CREEPSEntityThief;
import fr.elias.morecreeps.common.entity.CREEPSEntityTombstone;

public class CREEPSRenderTombstone extends RenderLiving
{
    public CREEPSRenderTombstone(CREEPSModelTombstone creepsmodeltombstone, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodeltombstone, f);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        super.doRender(entityliving, d, d1, d2, f, f1);
        float f2 = 1.6F;
        float f3 = 0.01666667F * f2;
        float f4 = entityliving.getDistanceToEntity(renderManager.livingPlayer);
        String s = "";
        String s1 = "";
        String s2 = String.valueOf(((CREEPSEntityTombstone)entityliving).name);
        String s3 = String.valueOf(((CREEPSEntityTombstone)entityliving).level);
        String s4 = String.valueOf(((CREEPSEntityTombstone)entityliving).deathtype);
        s = (new StringBuilder()).append("\247fHere lies \2476").append(s2).toString();
        s1 = (new StringBuilder()).append("\247f a level \2476").append(s3).append(" \247f").append(s4).toString();

        if (f4 < 4F && s.length() > 0)
        {
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
            byte byte0 = -25;
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            worldRenderer.startDrawingQuads();
            int i = fontrenderer.getStringWidth(s);

            if (fontrenderer.getStringWidth(s1) > i)
            {
                i = fontrenderer.getStringWidth(s1);
            }

            int j = i / 2;
            worldRenderer.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
            worldRenderer.addVertex(-j - 2, -1 + byte0, 0.0D);
            worldRenderer.addVertex(-j - 2, 22 + byte0, 0.0D);
            worldRenderer.addVertex(j + 2, 22 + byte0, 0.0D);
            worldRenderer.addVertex(j + 2, -1 + byte0, 0.0D);
            tessellator.draw();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0 + 2, 0x20ffffff);
            fontrenderer.drawString(s1, -fontrenderer.getStringWidth(s1) / 2, byte0 + 12, 0x20ffffff);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0 + 2, -1);
            fontrenderer.drawString(s1, -fontrenderer.getStringWidth(s1) / 2, byte0 + 12, -1);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        }
    }
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        doRenderLiving((EntityLiving)entity, d, d1, d2, f, f1);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityTombstone entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityTombstone) entity);
	}
}
