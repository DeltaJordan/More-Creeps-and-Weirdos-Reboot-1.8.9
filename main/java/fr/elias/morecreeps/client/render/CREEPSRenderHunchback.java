package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelHunchback;
import fr.elias.morecreeps.common.entity.CREEPSEntityHorseHead;
import fr.elias.morecreeps.common.entity.CREEPSEntityHunchback;

public class CREEPSRenderHunchback extends RenderLiving
{
    protected CREEPSModelHunchback modelBipedMain;

    public CREEPSRenderHunchback(CREEPSModelHunchback creepsmodelhunchback, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelhunchback, f);
        modelBipedMain = creepsmodelhunchback;
    }

    protected void fattenup(CREEPSEntityHunchback creepsentityhunchback, float f)
    {
        GL11.glScalef(creepsentityhunchback.modelsize, creepsentityhunchback.modelsize, creepsentityhunchback.modelsize);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        fattenup((CREEPSEntityHunchback)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityHunchback entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityHunchback) entity);
	}

}
