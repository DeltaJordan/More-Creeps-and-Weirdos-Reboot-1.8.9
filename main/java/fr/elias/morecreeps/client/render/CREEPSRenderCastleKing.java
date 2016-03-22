package fr.elias.morecreeps.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.client.models.CREEPSModelCastleKing;
import fr.elias.morecreeps.common.entity.CREEPSEntityCastleGuard;
import fr.elias.morecreeps.common.entity.CREEPSEntityCastleKing;

public class CREEPSRenderCastleKing extends RenderLiving
{
    protected CREEPSModelCastleKing modelcastlekingmain;

    public CREEPSRenderCastleKing(CREEPSModelCastleKing creepsmodelcastleking, float f)
    {
        super(Minecraft.getMinecraft().getRenderManager(), creepsmodelcastleking, f);
        modelcastlekingmain = creepsmodelcastleking;
    }

    protected void fattenup(CREEPSEntityCastleKing creepsentitycastleking, float f)
    {
        GL11.glScalef(2.0F, 1.5F, 2.0F);
    }
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        CREEPSEntityCastleKing creepsentitycastleking = (CREEPSEntityCastleKing)entityliving;
        modelcastlekingmain.hammerswing = creepsentitycastleking.hammerswing;
        fattenup((CREEPSEntityCastleKing)entityliving, f);
    }

    protected ResourceLocation getEntityTexture(CREEPSEntityCastleKing entity)
    {
		return new ResourceLocation(entity.texture);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {

		return getEntityTexture((CREEPSEntityCastleKing) entity);
	}
}
