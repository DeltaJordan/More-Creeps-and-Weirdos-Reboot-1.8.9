package creeps.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import creeps.Reference;
import creeps.models.ModelSneakySal;

public class RenderSneakySal extends RenderLiving {
    private static float shadowSize;
    private ResourceLocation texture = new ResourceLocation(Reference.MOD_ID,
	    Reference.TEXTURE_PATH_ENTITIES + Reference.SNEAKY_SAL_TEXTURE_NAME);

    public RenderSneakySal() {
	super(Minecraft.getMinecraft().getRenderManager(),
		new ModelSneakySal(), shadowSize);
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
