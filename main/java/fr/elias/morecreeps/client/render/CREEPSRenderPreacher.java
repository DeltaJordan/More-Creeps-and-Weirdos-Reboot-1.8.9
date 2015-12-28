package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import fr.elias.morecreeps.client.models.CREEPSModelPreacher;
import fr.elias.morecreeps.common.entity.CREEPSEntityPreacher;

public class CREEPSRenderPreacher extends RenderLiving
{
    protected CREEPSModelPreacher modelBipedMain;

    public CREEPSRenderPreacher(CREEPSModelPreacher creepsmodelpreacher, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelpreacher, f);
        modelBipedMain = creepsmodelpreacher;
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityPreacher entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityPreacher) entity);
	}

}
