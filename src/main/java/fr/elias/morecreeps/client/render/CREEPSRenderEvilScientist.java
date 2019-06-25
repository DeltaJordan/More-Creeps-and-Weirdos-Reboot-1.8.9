package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelEvilScientist;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilPig;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilScientist;

public class CREEPSRenderEvilScientist extends RenderLiving
{
    protected CREEPSModelEvilScientist modelBipedMain;

    public CREEPSRenderEvilScientist(CREEPSModelEvilScientist creepsmodelevilscientist, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelevilscientist, f);
        modelBipedMain = creepsmodelevilscientist;
    }

    protected void fattenup(CREEPSEntityEvilScientist creepsentityevilscientist, float f)
    {
        GL11.glScalef(creepsentityevilscientist.modelsize, creepsentityevilscientist.modelsize, creepsentityevilscientist.modelsize);
    }
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        fattenup((CREEPSEntityEvilScientist)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityEvilScientist entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityEvilScientist) entity);
	}
}
