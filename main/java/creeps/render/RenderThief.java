package creeps.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import creeps.Reference;
import creeps.models.ModelThief;

public class RenderThief extends RenderLiving {

    private static float shadowSize;
    private ResourceLocation texture = new ResourceLocation(Reference.MOD_ID,
	    Reference.TEXTURE_PATH_ENTITIES + Reference.THIEF_TEXTURE_NAME);

    public RenderThief() {
	super(Minecraft.getMinecraft().getRenderManager(), new ModelThief(),
		shadowSize);
    }

    @Override
    public void doRender(Entity e, double x, double y, double z, float f,
	    float f1) {
	super.doRender(e, x, y, z, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
	return texture;
    }
}
