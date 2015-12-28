package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.common.entity.CREEPSEntitySnowDevil;
import fr.elias.morecreeps.common.entity.CREEPSEntityThief;

public class CREEPSRenderThief extends RenderLiving
{
    protected ModelBiped modelBipedMain;

    public CREEPSRenderThief(ModelBiped modelbiped, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), modelbiped, f);
        modelBipedMain = modelbiped;
    }

    protected void fattenup(CREEPSEntityThief creepsentitythief, float f)
    {
        GL11.glScalef(creepsentitythief.modelsize, creepsentitythief.modelsize, creepsentitythief.modelsize);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        fattenup((CREEPSEntityThief)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityThief entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityThief) entity);
	}
}
