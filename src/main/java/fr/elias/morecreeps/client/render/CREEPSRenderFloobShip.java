package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelFloobShip;
import fr.elias.morecreeps.common.entity.CREEPSEntityFloob;
import fr.elias.morecreeps.common.entity.CREEPSEntityFloobShip;

public class CREEPSRenderFloobShip extends RenderLiving
{
    public CREEPSRenderFloobShip(CREEPSModelFloobShip creepsmodelfloobship, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelfloobship, f);
    }

    /**
     * sets the scale for the slime based on getSlimeSize in EntitySlime
     */
    protected void scaleSlime(CREEPSEntityFloobShip creepsentityfloobship, float f)
    {
        GL11.glScalef(4F, 3F, 4F);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        scaleSlime((CREEPSEntityFloobShip)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityFloobShip entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityFloobShip) entity);
	}
}
