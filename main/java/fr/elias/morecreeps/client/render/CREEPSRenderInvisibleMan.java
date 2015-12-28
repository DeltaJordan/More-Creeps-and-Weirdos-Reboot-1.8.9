package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.common.entity.CREEPSEntityHippo;
import fr.elias.morecreeps.common.entity.CREEPSEntityInvisibleMan;

public class CREEPSRenderInvisibleMan extends RenderLiving
{
    public CREEPSRenderInvisibleMan(ModelBiped modelbiped, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), modelbiped, f);
    }

    protected void fattenup(CREEPSEntityInvisibleMan creepsentityinvisibleman, float f)
    {
        GL11.glScalef(creepsentityinvisibleman.modelsize, creepsentityinvisibleman.modelsize, creepsentityinvisibleman.modelsize);
    }

    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        fattenup((CREEPSEntityInvisibleMan)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityInvisibleMan entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityInvisibleMan) entity);
	}
}
