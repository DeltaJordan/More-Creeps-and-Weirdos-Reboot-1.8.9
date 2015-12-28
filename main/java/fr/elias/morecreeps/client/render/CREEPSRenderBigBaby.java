package fr.elias.morecreeps.client.render;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelBigBaby;
import fr.elias.morecreeps.common.entity.CREEPSEntityBigBaby;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class CREEPSRenderBigBaby extends RenderLiving
{
    protected CREEPSModelBigBaby modelbigbabymain;

    public CREEPSRenderBigBaby(CREEPSModelBigBaby creepsmodelbigbaby, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelbigbaby, f);
        modelbigbabymain = creepsmodelbigbaby;
    }

    protected void fattenup(CREEPSEntityBigBaby creepsentitybigbaby, float f)
    {
        GL11.glScalef(creepsentitybigbaby.modelsize, creepsentitybigbaby.modelsize, creepsentitybigbaby.modelsize);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        CREEPSEntityBigBaby creepsentitybigbaby = (CREEPSEntityBigBaby)entityliving;
        modelbigbabymain.hammerswing = creepsentitybigbaby.hammerswing;
        fattenup((CREEPSEntityBigBaby)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityBigBaby entity) {
		return entity.texture;
	}
    
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return getEntityTexture((CREEPSEntityBigBaby) entity);
	}
}
