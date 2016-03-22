package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelDesertLizard;
import fr.elias.morecreeps.common.entity.CREEPSEntityCaveman;
import fr.elias.morecreeps.common.entity.CREEPSEntityDesertLizard;

public class CREEPSRenderDesertLizard extends RenderLiving
{
    public CREEPSRenderDesertLizard(CREEPSModelDesertLizard creepsmodeldesertlizard, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodeldesertlizard, f);
    }

    protected void fattenup(CREEPSEntityDesertLizard creepsentitydesertlizard, float f)
    {
        GL11.glScalef(creepsentitydesertlizard.modelsize, creepsentitydesertlizard.modelsize, creepsentitydesertlizard.modelsize);
    }
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        fattenup((CREEPSEntityDesertLizard)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityDesertLizard entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityDesertLizard) entity);
	}
}
