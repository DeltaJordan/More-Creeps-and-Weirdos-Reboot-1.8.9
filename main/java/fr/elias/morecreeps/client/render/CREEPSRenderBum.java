package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelBum;
import fr.elias.morecreeps.common.entity.CREEPSEntityBum;

public class CREEPSRenderBum extends RenderLiving
{
	
    protected CREEPSModelBum modelBipedMain;

    public CREEPSRenderBum(CREEPSModelBum creepsmodelbum, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelbum, f);
        modelBipedMain = creepsmodelbum;
    }
    public void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        CREEPSEntityBum creepsentitybum = (CREEPSEntityBum)entityliving;
        modelBipedMain.modelsize = creepsentitybum.modelsize;
        fattenup((CREEPSEntityBum)entityliving, f);
    }

    protected void fattenup(CREEPSEntityBum creepsentitybum, float f)
    {
        GL11.glScalef(creepsentitybum.modelsize, creepsentitybum.modelsize, creepsentitybum.modelsize);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        doRender((CREEPSEntityBum)entity, d, d1, d2, f, f1);
    }
    protected ResourceLocation getEntityTexture(CREEPSEntityBum entity) {
		
		return entity.texture;
	}
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		return getEntityTexture((CREEPSEntityBum) entity);
	}
}
