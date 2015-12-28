package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.common.entity.CREEPSEntityPreacher;
import fr.elias.morecreeps.common.entity.CREEPSEntityPrisoner;

public class CREEPSRenderPrisoner extends RenderLiving
{
    protected ModelBiped modelBipedMain;

    public CREEPSRenderPrisoner(ModelBiped modelbiped, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), modelbiped, f);
        modelBipedMain = modelbiped;
    }

    protected void fattenup(CREEPSEntityPrisoner creepsentityprisoner, float f)
    {
        GL11.glScalef(0.75F, 1.0F, 0.9F);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        CREEPSEntityPrisoner creepsentityprisoner = (CREEPSEntityPrisoner)entityliving;
        fattenup((CREEPSEntityPrisoner)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityPrisoner entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityPrisoner) entity);
	}
}
