package fr.elias.morecreeps.client.render.layers;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import fr.elias.morecreeps.client.render.CREEPSRenderAtom;
import fr.elias.morecreeps.common.entity.CREEPSEntityAtom;

public class LayerAtom implements LayerRenderer {

	public CREEPSRenderAtom atomRenderer;
    public float sparkle;
    public LayerAtom(CREEPSRenderAtom p_i46112_1_)
    {
        this.atomRenderer = p_i46112_1_;
    }
	
	public void doRenderLayer(CREEPSEntityAtom p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
	{
		func_179_a(p_177141_1_, 2, 0F);
	}
    protected int func_179_a(CREEPSEntityAtom creepsentityatom, int i, float f)
    {
        if (i == 1)
        {
            Random random = new Random(10842L);
            sparkle += random.nextFloat();

            if (sparkle > 30F)
            {
                sparkle = 0.0F;
            }

            sparkle = random.nextInt(30);
            float f1 = sparkle;
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("armor/power.png"));
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glLoadIdentity();
            float f2 = f1 * 0.01F;
            float f3 = f1 * 0.01F;
            GL11.glTranslatef(f2, f3, 0.0F);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glEnable(GL11.GL_BLEND);
            float f4 = 0.5F;
            GL11.glColor4f(f4, f4, f4, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            return 1;
        }

        if (i == 2)
        {
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
        }

        return -1;
    }

    protected int func_179_aaaaaaaaaaa(CREEPSEntityAtom creepsentityatom, int i, float f)
    {
        if (i == 0)
        {
            GL11.glEnable(GL11.GL_NORMALIZE);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            return 1;
        }

        if (i == 1)
        {
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }

        return -1;
    }
	@Override
	public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
	{
		this.doRenderLayer((CREEPSEntityAtom)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
	}

	@Override
	public boolean shouldCombineTextures() 
	{
		return true;
	}

}
