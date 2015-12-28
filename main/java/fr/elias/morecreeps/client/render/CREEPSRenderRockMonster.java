package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelRockMonster;
import fr.elias.morecreeps.common.entity.CREEPSEntityRockMonster;
import fr.elias.morecreeps.common.entity.CREEPSEntityRocketGiraffe;

public class CREEPSRenderRockMonster extends RenderLiving
{
    protected CREEPSModelRockMonster modelBipedMain;

    public CREEPSRenderRockMonster(CREEPSModelRockMonster creepsmodelrockmonster, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelrockmonster, f);
        modelBipedMain = creepsmodelrockmonster;
    }

    /**
     * sets the scale for the slime based on getSlimeSize in EntitySlime
     */
    protected void scaleSlime(CREEPSEntityRockMonster creepsentityrockmonster, float f)
    {
        GL11.glScalef(creepsentityrockmonster.modelsize, creepsentityrockmonster.modelsize, creepsentityrockmonster.modelsize);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        scaleSlime((CREEPSEntityRockMonster)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityRockMonster entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityRockMonster) entity);
	}
}
