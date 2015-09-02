package creeps.render;

import creeps.Reference;
import creeps.entity.EntityMummy;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderMummy extends RenderLiving {	
	
	
	public RenderMummy(RenderManager rm, ModelBase modelbase, float f)
	{
		super(rm, modelbase, f);
	}

	public void renderMummy(EntityMummy entityMummy, double d, double d1, double d2, float f, float f1)
	{
		super.doRender(entityMummy, d, d1, d2, f, f1);
	}

	@Override
	public void doRender(EntityLiving entity, double d, double d1, double d2, float f, float f1)
	{
		renderMummy((EntityMummy) entity, d, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		
			String location = "textures/entities/mummy.png";
		
		
		final ResourceLocation skin = new ResourceLocation(Reference.MOD_ID, location);
		return skin;
		
	}
	
}
