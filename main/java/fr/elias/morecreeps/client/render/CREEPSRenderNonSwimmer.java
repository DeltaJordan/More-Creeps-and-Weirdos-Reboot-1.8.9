package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelNonSwimmer;
import fr.elias.morecreeps.common.entity.CREEPSEntityManDog;
import fr.elias.morecreeps.common.entity.CREEPSEntityNonSwimmer;

public class CREEPSRenderNonSwimmer extends RenderLiving
{
    protected CREEPSModelNonSwimmer modelBipedMain;

    public CREEPSRenderNonSwimmer(CREEPSModelNonSwimmer creepsmodelnonswimmer, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelnonswimmer, f);
        modelBipedMain = creepsmodelnonswimmer;
    }

    protected void fattenup(CREEPSEntityNonSwimmer creepsentitynonswimmer, float f)
    {
        GL11.glScalef(creepsentitynonswimmer.modelsize, creepsentitynonswimmer.modelsize, creepsentitynonswimmer.modelsize);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        CREEPSEntityNonSwimmer creepsentitynonswimmer = (CREEPSEntityNonSwimmer)entityliving;
        modelBipedMain.modelsize = creepsentitynonswimmer.modelsize;
        modelBipedMain.swimming = creepsentitynonswimmer.swimming;
        fattenup((CREEPSEntityNonSwimmer)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityNonSwimmer entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityNonSwimmer) entity);
	}
}
