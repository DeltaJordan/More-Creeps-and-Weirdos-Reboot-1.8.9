package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelEvilLight;
import fr.elias.morecreeps.client.render.layers.LayerEvilLightGlow;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilChicken;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilLight;
import fr.elias.morecreeps.common.entity.CREEPSEntityFloobShip;

public class CREEPSRenderEvilLight extends RenderLiving
{
    public CREEPSRenderEvilLight(CREEPSModelEvilLight creepsmodelevillight, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelevillight, f);
        shadowSize = 0.0F;
        this.addLayer(new LayerEvilLightGlow(this));
    }
    /*protected int glow(CREEPSEntityEvilLight creepsentityevillight, int i, float f)
    {
        if (i != 0)
        {
            return -1;
        }

        if (i != 0)
        {
            return -1;
        }
        else
        {
            loadTexture("/mob/creeps/evillightglow.png");
            float f1 = (1.0F - creepsentityevillight.getBrightness(1.0F)) * 0.5F;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
            return 1;
        }
    }
    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
        return glow((CREEPSEntityEvilLight)entityliving, i, f);
    }*/

    protected ResourceLocation getEntityTexture(CREEPSEntityEvilLight entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityEvilLight) entity);
	}
}
