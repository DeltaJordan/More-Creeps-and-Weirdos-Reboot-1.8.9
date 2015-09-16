package creeps.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import creeps.Reference;
import creeps.models.Modelgrowbotgregg;

public class RenderGrowbotGregg extends RenderLiving {
    private static float shadowSize;
    final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID,
	    Reference.TEXTURE_PATH_ENTITIES
		    + Reference.GROWBOTGREGG_TEXTURE_NAME);

    public RenderGrowbotGregg() {
	super(Minecraft.getMinecraft().getRenderManager(),
		new Modelgrowbotgregg(), shadowSize);
    }

    @Override
    public void doRender(EntityLiving e, double x, double y, double z, float f,
	    float f1) {
	super.doRender(e, x, y, z, f, f1);
	GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
	return texture;
    }

}
