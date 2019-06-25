package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelDigBug;
import fr.elias.morecreeps.common.entity.CREEPSEntityDesertLizard;
import fr.elias.morecreeps.common.entity.CREEPSEntityDigBug;

public class CREEPSRenderDigBug extends RenderLiving
{
    public CREEPSRenderDigBug(CREEPSModelDigBug creepsmodeldigbug, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodeldigbug, f);
    }

    protected void fattenup(CREEPSEntityDigBug creepsentitydigbug, float f)
    {
        GL11.glScalef(creepsentitydigbug.modelsize, creepsentitydigbug.modelsize, creepsentitydigbug.modelsize);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        fattenup((CREEPSEntityDigBug)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityDigBug entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityDigBug) entity);
	}
}
