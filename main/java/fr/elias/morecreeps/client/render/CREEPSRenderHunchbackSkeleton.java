package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.common.entity.CREEPSEntityHunchbackSkeleton;
import fr.elias.morecreeps.common.entity.CREEPSEntityInvisibleMan;

public class CREEPSRenderHunchbackSkeleton extends RenderLiving
{
    protected ModelBiped modelBipedMain;

    public CREEPSRenderHunchbackSkeleton(ModelBiped modelbiped, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), modelbiped, f);
        modelBipedMain = modelbiped;
    }

    protected void fattenup(CREEPSEntityHunchbackSkeleton creepsentityhunchbackskeleton, float f)
    {
        GL11.glScalef(creepsentityhunchbackskeleton.modelsize, creepsentityhunchbackskeleton.modelsize, creepsentityhunchbackskeleton.modelsize);
    }

    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        fattenup((CREEPSEntityHunchbackSkeleton)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityHunchbackSkeleton entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityHunchbackSkeleton) entity);
	}
}
