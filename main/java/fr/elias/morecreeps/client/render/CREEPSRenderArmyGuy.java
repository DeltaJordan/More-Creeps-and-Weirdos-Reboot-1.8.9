package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelArmyGuy;
import fr.elias.morecreeps.common.entity.CREEPSEntityArmyGuy;

public class CREEPSRenderArmyGuy extends RenderLiving
{
    protected CREEPSModelArmyGuy modelBipedMain;

    public CREEPSRenderArmyGuy(CREEPSModelArmyGuy creepsmodelarmyguy, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelarmyguy, f);
        //setRenderPassModel(creepsmodelarmyguy);
        modelBipedMain = creepsmodelarmyguy;
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        CREEPSEntityArmyGuy creepsentityarmyguy = (CREEPSEntityArmyGuy)entityliving;
        modelBipedMain.armright = creepsentityarmyguy.armright;
        modelBipedMain.armleft = creepsentityarmyguy.armleft;
        modelBipedMain.legright = creepsentityarmyguy.legright;
        modelBipedMain.legleft = creepsentityarmyguy.legleft;
        modelBipedMain.helmet = creepsentityarmyguy.helmet;
        modelBipedMain.head = creepsentityarmyguy.head;
        modelBipedMain.shooting = creepsentityarmyguy.shooting;
        modelBipedMain.modelsize = creepsentityarmyguy.modelsize;
        fattenup((CREEPSEntityArmyGuy)entityliving, f);
    }

    protected void fattenup(CREEPSEntityArmyGuy creepsentityarmyguy, float f)
    {
        GL11.glScalef(creepsentityarmyguy.modelsize, creepsentityarmyguy.modelsize, creepsentityarmyguy.modelsize);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        doRender((CREEPSEntityArmyGuy)entity, d, d1, d2, f, f1);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityArmyGuy entity) {
		return entity.texture;
	}
    
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return getEntityTexture((CREEPSEntityArmyGuy) entity);
	}
}
