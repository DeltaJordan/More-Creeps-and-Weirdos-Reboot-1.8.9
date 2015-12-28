package fr.elias.morecreeps.client.render;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelEvilCreature;
import fr.elias.morecreeps.common.entity.CREEPSEntityEvilCreature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class CREEPSRenderEvilCreature extends RenderLiving
{
    protected CREEPSModelEvilCreature modelBipedMain;
    public boolean scaled;
    private ModelBase scaleAmount;

    public CREEPSRenderEvilCreature(CREEPSModelEvilCreature creepsmodelevilcreature, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelevilcreature, f);
        modelBipedMain = creepsmodelevilcreature;
        setScaleAmount(creepsmodelevilcreature);
    }

    /**
     * sets the scale for the slime based on getSlimeSize in EntitySlime
     */
    protected void scaleSlime(CREEPSEntityEvilCreature creepsentityevilcreature, float f)
    {
        GL11.glScalef(creepsentityevilcreature.modelsize, creepsentityevilcreature.modelsize, creepsentityevilcreature.modelsize);
    }
    
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        scaleSlime((CREEPSEntityEvilCreature)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityEvilCreature entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityEvilCreature) entity);
	}

	public ModelBase getScaleAmount() {
		return scaleAmount;
	}

	public void setScaleAmount(ModelBase scaleAmount) {
		this.scaleAmount = scaleAmount;
	}
}
