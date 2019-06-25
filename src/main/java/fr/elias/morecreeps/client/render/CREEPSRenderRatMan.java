package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelRatMan;
import fr.elias.morecreeps.common.entity.CREEPSEntityPyramidGuardian;
import fr.elias.morecreeps.common.entity.CREEPSEntityRatMan;

public class CREEPSRenderRatMan extends RenderLiving
{
    protected CREEPSModelRatMan modelBipedMain;

    public CREEPSRenderRatMan(CREEPSModelRatMan creepsmodelratman, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelratman, f);
        modelBipedMain = creepsmodelratman;
    }

    protected void fattenup(CREEPSEntityRatMan creepsentityratman, float f)
    {
        GL11.glScalef(creepsentityratman.modelsize, creepsentityratman.modelsize, creepsentityratman.modelsize);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        CREEPSEntityRatMan creepsentityratman = (CREEPSEntityRatMan)entityliving;
        modelBipedMain.jumper = creepsentityratman.jumper;
        fattenup((CREEPSEntityRatMan)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityRatMan entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityRatMan) entity);
	}
}
