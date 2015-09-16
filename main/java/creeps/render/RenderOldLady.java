package creeps.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import creeps.Reference;
import creeps.models.ModelOldLady;

public class RenderOldLady extends RenderLiving {

    private static float shadowSize;
    private ResourceLocation texture = new ResourceLocation(Reference.MOD_ID,
	    Reference.TEXTURE_PATH_ENTITIES + Reference.OLD_LADY_TEXTURE_NAME);

    public RenderOldLady() {
	super(Minecraft.getMinecraft().getRenderManager(), new ModelOldLady(),
		shadowSize);
    }

    @Override
    public void doRender(EntityLiving e, double x, double y, double z, float f,
	    float f1) {
	super.doRender(e, x, y, z, f, f1);
	GL11.glPopMatrix();
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
	return texture;
    }

}
