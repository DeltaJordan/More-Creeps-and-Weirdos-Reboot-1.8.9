package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import fr.elias.morecreeps.client.models.CREEPSModelTowel;
import fr.elias.morecreeps.common.entity.CREEPSEntityTowel;

public class CREEPSRenderTowel extends RenderLiving
{
    protected CREEPSModelTowel modelBipedMain;

    public CREEPSRenderTowel(CREEPSModelTowel creepsmodeltowel, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodeltowel, f);
        modelBipedMain = creepsmodeltowel;
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityTowel entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityTowel) entity);
	}
}
