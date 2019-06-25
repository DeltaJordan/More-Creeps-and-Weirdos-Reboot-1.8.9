package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelFloob;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilSnowman;
import fr.elias.morecreeps.common.entity.CREEPSEntityFloob;

public class CREEPSRenderFloob extends RenderLiving
{
    protected CREEPSModelFloob modelFloobMain;

    public CREEPSRenderFloob(CREEPSModelFloob creepsmodelfloob, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelfloob, f);
        modelFloobMain = creepsmodelfloob;
    }

    protected void fattenup(CREEPSEntityFloob creepsentityfloob, float f)
    {
        GL11.glScalef(creepsentityfloob.modelsize, creepsentityfloob.modelsize, creepsentityfloob.modelsize);
    }
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        fattenup((CREEPSEntityFloob)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityFloob entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityFloob) entity);
	}
}
