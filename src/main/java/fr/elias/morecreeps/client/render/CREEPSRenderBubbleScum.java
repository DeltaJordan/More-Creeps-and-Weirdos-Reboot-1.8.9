package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelBubbleScum;
import fr.elias.morecreeps.common.entity.CREEPSEntityBubbleScum;

public class CREEPSRenderBubbleScum extends RenderLiving
{
    public CREEPSRenderBubbleScum(CREEPSModelBubbleScum creepsmodelbubblescum, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), new CREEPSModelBubbleScum(), 1.0F);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        fattenup((CREEPSEntityBubbleScum)entityliving, f);
    }

    protected void fattenup(CREEPSEntityBubbleScum creepsentitybubblescum, float f)
    {
        GL11.glScalef(creepsentitybubblescum.modelsize, creepsentitybubblescum.modelsize, creepsentitybubblescum.modelsize);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        doRender((CREEPSEntityBubbleScum)entity, d, d1, d2, f, f1);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityBubbleScum entity) {
		
		return new ResourceLocation(entity.texture);
	}
    
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return getEntityTexture((CREEPSEntityBubbleScum) entity);
	}
}
