package creeps.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import creeps.Reference;
import creeps.entity.EntityMummy;
import creeps.models.ModelMummy;

public class RenderMummy extends RenderLiving {

    private static float shadowSize;
    final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID,
	    Reference.TEXTURE_PATH_ENTITIES + Reference.MUMMY_TEXTURE_NAME);

    public RenderMummy() {
	super(Minecraft.getMinecraft().getRenderManager(), new ModelMummy(),
		shadowSize);
    }

    public void renderMummy(EntityMummy entityMummy, double d, double d1,
	    double d2, float f, float f1) {
	super.doRender(entityMummy, d, d1, d2, f, f1);
    }

    @Override
    public void doRender(EntityLiving entity, double d, double d1, double d2,
	    float f, float f1) {
	renderMummy((EntityMummy) entity, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
	return texture;
    }

}
