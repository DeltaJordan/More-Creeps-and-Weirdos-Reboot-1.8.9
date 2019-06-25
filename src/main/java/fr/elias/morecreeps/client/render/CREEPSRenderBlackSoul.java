package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.render.layers.LayerBlackSoulEyes;
import fr.elias.morecreeps.common.Reference;
import fr.elias.morecreeps.common.entity.CREEPSEntityBlackSoul;

public class CREEPSRenderBlackSoul extends RenderLiving
{
	ResourceLocation eyeglow = new ResourceLocation(Reference.MOD_ID, 
			Reference.TEXTURE_PATH_ENTITES + Reference.TEXTURE_BLACK_SOUL_EYES);
	
    public CREEPSRenderBlackSoul(ModelBiped modelbiped, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelBiped(), 1.0F);
        this.addLayer(new LayerBlackSoulEyes(this));
    }

    
    protected int eyeGlow(CREEPSEntityBlackSoul creepsentityblacksoul, int i, float f)
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
        	this.bindTexture(eyeglow);
            float f1 = (1.0F - creepsentityblacksoul.getBrightness(1.0F)) * 0.5F;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
            return 1;
        }
    }

    protected void fattenup(CREEPSEntityBlackSoul creepsentityblacksoul, float f)
    {
        GL11.glScalef(creepsentityblacksoul.modelsize, creepsentityblacksoul.modelsize, creepsentityblacksoul.modelsize);
    }
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        fattenup((CREEPSEntityBlackSoul)entityliving, f);
    }

	protected ResourceLocation getEntityTexture(CREEPSEntityBlackSoul entity) {
		
		return entity.texture;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
	
		return getEntityTexture((CREEPSEntityBlackSoul) entity);
	}
}
