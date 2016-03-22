package fr.elias.morecreeps.client.render;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelAtom;
import fr.elias.morecreeps.client.render.layers.LayerAtom;
import fr.elias.morecreeps.common.Reference;
import fr.elias.morecreeps.common.entity.CREEPSEntityAtom;

public class CREEPSRenderAtom extends RenderLiving
{
    private ModelBase scaleAmount;
    protected CREEPSModelAtom modelBipedMain;

    public CREEPSRenderAtom(CREEPSModelAtom creepsmodelatom, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelatom, f);
        modelBipedMain = creepsmodelatom;
        scaleAmount = creepsmodelatom;
        this.addLayer(new LayerAtom(this));
    }

    /**
     * sets the scale for the slime based on getSlimeSize in EntitySlime
     */
    protected void scaleSlime(CREEPSEntityAtom creepsentityatom, float f)
    {
        GL11.glScalef(creepsentityatom.atomsize * 0.3F, creepsentityatom.atomsize * 0.3F, creepsentityatom.atomsize * 0.3F);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        scaleSlime((CREEPSEntityAtom)entityliving, f);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        shadowSize = 0.0F;
        doRender((EntityLivingBase)entity, d, d1, d2, f, f1);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation(Reference.MOD_ID, 
				Reference.TEXTURE_PATH_ENTITES + Reference.TEXTURE_ATOM);
	}
}
