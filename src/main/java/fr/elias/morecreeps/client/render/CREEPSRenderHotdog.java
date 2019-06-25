package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelHotdog;
import fr.elias.morecreeps.common.entity.CREEPSEntityHorseHead;
import fr.elias.morecreeps.common.entity.CREEPSEntityHotdog;

public class CREEPSRenderHotdog extends RenderLiving
{
    protected CREEPSModelHotdog modelBipedMain;

    public CREEPSRenderHotdog(CREEPSModelHotdog creepsmodelhotdog, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelhotdog, f);
        modelBipedMain = creepsmodelhotdog;
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        super.doRender(entityliving, d, d1, d2, f, f1);
        float f2 = 1.6F;
        float f3 = 0.01666667F * f2;
        float f4 = entityliving.getDistanceToEntity(renderManager.livingPlayer);
        String s = "";

        if (((CREEPSEntityHotdog)entityliving).speedboost > 0)
        {
            s = "\2473* \247f";
        }

        s = (new StringBuilder()).append(s).append(((CREEPSEntityHotdog)entityliving).name).toString();
        String s1 = String.valueOf(((CREEPSEntityHotdog)entityliving).level);

        if (((CREEPSEntityHotdog)entityliving).getHealth() < ((CREEPSEntityHotdog)entityliving).getMaxHealth() / 2 && s.length() > 0)
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
            float f5 = (1.0F - ((CREEPSEntityHotdog)entityliving).dogsize) * 35F;
            int i = -20 + (int)f5;
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            worldRenderer.startDrawingQuads();
            int j = fontrenderer.getStringWidth(s) / 2;
            worldRenderer.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
            worldRenderer.addVertex(-j - 1, -1 + i, 0.0D);
            worldRenderer.addVertex(-j - 1, 8 + i, 0.0D);
            worldRenderer.addVertex(j + 1, 8 + i, 0.0D);
            worldRenderer.addVertex(j + 1, -1 + i, 0.0D);
            float f6 = ((CREEPSEntityHotdog)entityliving).getHealth();
            float f7 = ((CREEPSEntityHotdog)entityliving).getMaxHealth();
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

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        doRenderLiving((EntityLiving)entity, d, d1, d2, f, f1);

        if (((CREEPSEntityHotdog)entity).firepower > 0)
        {
            burnDog((EntityLiving)entity, d, d1, d2, 3F);
        }
    }

    protected void fattenup(CREEPSEntityHotdog creepsentityhotdog, float f)
    {
        GL11.glScalef(creepsentityhotdog.dogsize, creepsentityhotdog.dogsize, creepsentityhotdog.dogsize + 0.25F);
    }

    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        fattenup((CREEPSEntityHotdog)entityliving, f);
    }

    public void burnDog(Entity entity, double d, double d1, double d2, float f)
    {
        int i = 0;
        GL11.glDisable(GL11.GL_LIGHTING);
        int j = Blocks.fire.getBlockColor();
        int k = (j & 0xf) << 4;
        int l = j & 0xf0;
        float f1 = (float)k / 256F;
        float f2 = ((float)k + 15.99F) / 256F;
        float f3 = (float)l / 256F;
        float f4 = ((float)l + 15.99F) / 256F;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        float f5 = entity.width;
        GL11.glScalef(f5 * 0.3F, f5 * 0.5F, f5 * 0.5F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(Minecraft.getMinecraft().getTextureMapBlocks().locationBlocksTexture);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        float f6 = 1.0F;
        float f7 = 0.5F;
        float f8 = -0.75F;
        float f9 = entity.height / entity.width;
        GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, 0.0F, -0.2F + (float)(int)f9 * 0.102F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        worldRenderer.startDrawingQuads();

        while (f9 > 0.0F)
        {
        	worldRenderer.addVertexWithUV(f6 - f7, (0.0F - f8) + (float)i, 0.0D, f2, f4);
        	worldRenderer.addVertexWithUV(0.0F - f7, (0.0F - f8) + (float)i, 0.0D, f1, f4);
        	worldRenderer.addVertexWithUV(0.0F - f7, (1.4F - f8) + (float)i, 0.0D, f1, f3);
        	worldRenderer.addVertexWithUV(f6 - f7, (1.4F - f8) + (float)i, 0.0D, f2, f3);
            f9--;
            f8--;
            f6 *= 0.9F;
            GL11.glTranslatef(0.0F, 0.0F, -0.04F);
        }

        tessellator.draw();
        GL11.glRotatef(-renderManager.playerViewY, 1.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, 0.0F, -0.2F + (float)(int)f9 * 0.102F);
        GL11.glColor4f(0.0F, 0.0F, 1.0F, 1.0F);
        worldRenderer.startDrawingQuads();

        while (f9 > 0.0F)
        {
        	worldRenderer.addVertexWithUV(f6 - f7++, (0.0F - f8) + (float)i, 0.0D, f2 + 6F, f4);
        	worldRenderer.addVertexWithUV(0.0F - f7++, (0.0F - f8) + (float)i, 0.0D, f1 + 6F, f4);
        	worldRenderer.addVertexWithUV(0.0F - f7++, (1.4F - f8) + (float)i, 0.0D, f1 + 6F, f3);
        	worldRenderer.addVertexWithUV(f6 - f7++, (1.4F - f8) + (float)i, 0.0D, f2 + 6F, f3);
            f9--;
            f8--;
            f6 *= 0.9F;
            GL11.glTranslatef(0.0F, 0.0F, -0.04F);
        }

        tessellator.draw();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityHotdog entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityHotdog) entity);
	}
}
