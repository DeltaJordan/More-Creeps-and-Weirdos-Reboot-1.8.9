package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelArmyGuy;
import fr.elias.morecreeps.client.render.layers.LayerHeldItemArmyGuy;
import fr.elias.morecreeps.common.Reference;
import fr.elias.morecreeps.common.entity.CREEPSEntityArmyGuy;

public class CREEPSRenderArmyGuy extends RenderLiving
{
    protected CREEPSModelArmyGuy modelBipedMain;
    public static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.TEXTURE_PATH_ENTITES + Reference.TEXTURE_ARMY_GUY_DEFAULT);
    public static final ResourceLocation texture_loyal = new ResourceLocation(Reference.MOD_ID, Reference.TEXTURE_PATH_ENTITES + Reference.TEXTURE_ARMY_GUY_LOYAL);
    
    public CREEPSRenderArmyGuy(CREEPSModelArmyGuy creepsmodelarmyguy, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelarmyguy, f);
        modelBipedMain = creepsmodelarmyguy;
        this.addLayer(new LayerHeldItemArmyGuy(this));
    }

    protected void preRenderCallback(CREEPSEntityArmyGuy entityliving, float f)
    {
        CREEPSEntityArmyGuy creepsentityarmyguy = (CREEPSEntityArmyGuy)entityliving;
        modelBipedMain.armright = creepsentityarmyguy.armright;
        modelBipedMain.armleft = creepsentityarmyguy.armleft;
        modelBipedMain.legright = creepsentityarmyguy.legright;
        modelBipedMain.legleft = creepsentityarmyguy.legleft;
        modelBipedMain.helmet = creepsentityarmyguy.helmet;
        modelBipedMain.head = creepsentityarmyguy.head;
        modelBipedMain.modelsize = creepsentityarmyguy.modelsize;
        if (creepsentityarmyguy.legleft && creepsentityarmyguy.legright && creepsentityarmyguy.head)
        {
        	GlStateManager.translate(0.0D, 1.4D, 0.0D);
        }else if (creepsentityarmyguy.legleft && creepsentityarmyguy.legright)
        {
        	GlStateManager.translate(0.0D, 0.75D, 0.0D);
        }
        else
        {
        	GlStateManager.translate(0.0D, 0.0D, 0.0D);
        }
        fattenup(entityliving, f);
    }
    protected void fattenup(CREEPSEntityArmyGuy creepsentityarmyguy, float f)
    {
        GL11.glScalef(creepsentityarmyguy.modelsize, creepsentityarmyguy.modelsize, creepsentityarmyguy.modelsize);
    }

    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
    {
        this.preRenderCallback((CREEPSEntityArmyGuy)p_77041_1_, p_77041_2_);
    }
    
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        doRender((CREEPSEntityArmyGuy)entity, d, d1, d2, f, f1);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityArmyGuy entity) {
		return !entity.loyal ? texture : texture_loyal;
	}
    
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return getEntityTexture((CREEPSEntityArmyGuy) entity);
	}
}
