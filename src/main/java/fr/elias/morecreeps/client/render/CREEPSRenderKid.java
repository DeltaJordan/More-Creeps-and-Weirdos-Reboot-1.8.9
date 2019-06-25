package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelKid;
import fr.elias.morecreeps.common.entity.CREEPSEntityHunchback;
import fr.elias.morecreeps.common.entity.CREEPSEntityKid;

public class CREEPSRenderKid extends RenderLiving
{
    protected CREEPSModelKid modelBipedMain;

    public CREEPSRenderKid(CREEPSModelKid creepsmodelkid, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelkid, f);
        modelBipedMain = creepsmodelkid;
    }

    protected void fattenup(CREEPSEntityKid creepsentitykid, float f)
    {
        GL11.glScalef(creepsentitykid.modelsize, creepsentitykid.modelsize, creepsentitykid.modelsize);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        fattenup((CREEPSEntityKid)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityKid entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityKid) entity);
	}
}
