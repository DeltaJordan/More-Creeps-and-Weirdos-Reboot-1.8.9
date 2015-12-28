package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelLolliman;
import fr.elias.morecreeps.common.entity.CREEPSEntityLawyerFromHell;
import fr.elias.morecreeps.common.entity.CREEPSEntityLolliman;

public class CREEPSRenderLolliman extends RenderLiving
{
    protected CREEPSModelLolliman modelLollimanMain;

    public CREEPSRenderLolliman(CREEPSModelLolliman creepsmodellolliman, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodellolliman, f);
        modelLollimanMain = creepsmodellolliman;
    }

    protected void fattenup(CREEPSEntityLolliman creepsentitylolliman, float f)
    {
        GL11.glScalef(creepsentitylolliman.modelsize, creepsentitylolliman.modelsize, creepsentitylolliman.modelsize);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        CREEPSEntityLolliman creepsentitylolliman = (CREEPSEntityLolliman)entityliving;
        modelLollimanMain.kidmounted = creepsentitylolliman.kidmounted;
        fattenup((CREEPSEntityLolliman)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityLolliman entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityLolliman) entity);
	}
}
