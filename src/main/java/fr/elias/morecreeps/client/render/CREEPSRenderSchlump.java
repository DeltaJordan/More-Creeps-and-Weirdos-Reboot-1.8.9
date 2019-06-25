package fr.elias.morecreeps.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelSchlump;
import fr.elias.morecreeps.common.entity.CREEPSEntityRockMonster;
import fr.elias.morecreeps.common.entity.CREEPSEntitySchlump;

public class CREEPSRenderSchlump extends RenderLiving
{
    protected CREEPSModelSchlump modelBipedMain;

    public CREEPSRenderSchlump(CREEPSModelSchlump creepsmodelschlump, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelschlump, f);
        modelBipedMain = creepsmodelschlump;
    }

    protected void fattenup(CREEPSEntitySchlump creepsentityschlump, float f)
    {
        GL11.glScalef(creepsentityschlump.modelsize, creepsentityschlump.modelsize, creepsentityschlump.modelsize);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        CREEPSEntitySchlump creepsentityschlump = (CREEPSEntitySchlump)entityliving;
        modelBipedMain.age = creepsentityschlump.age;
        fattenup((CREEPSEntitySchlump)entityliving, f);
    }
    
    /*protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
        return eyeGlow((CREEPSEntitySchlump)entityliving, i, f);
    }*/

    /*protected int eyeGlow(CREEPSEntitySchlump creepsentityschlump, int i, float f)
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
            loadTexture("/mob/creeps/schlumpnight.png");
            float f1 = (1.0F - creepsentityschlump.getBrightness(1.0F)) * 0.5F;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
            return 1;
        }
    }*/

    protected ResourceLocation getEntityTexture(CREEPSEntitySchlump entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntitySchlump) entity);
	}
}
