package fr.elias.morecreeps.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class CREEPSModelHippo extends ModelBase
{
    public ModelRenderer headHippo;
    public ModelRenderer snout;
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg4;
    public ModelRenderer leg3;
    public ModelRenderer noseR;
    public ModelRenderer noseL;
    public ModelRenderer earR;
    public ModelRenderer earL;
    public ModelRenderer toothL;
    public ModelRenderer toothR;
    public ModelRenderer eyeL;
    public ModelRenderer eyeR;
    public ModelRenderer mouth;
    public float tailwag;
    public int taildirection;

    public CREEPSModelHippo()
    {
        this(0.0F);
    }

    public CREEPSModelHippo(float f)
    {
        this(f, 0.0F);
    }

    public CREEPSModelHippo(float f, float f1)
    {
        taildirection = 1;
        float f2 = 0.0F;
        headHippo = new ModelRenderer(this, 16, 20);
        headHippo.addBox(-3F, -3F, -5F, 6, 7, 5, f2);
        headHippo.setRotationPoint(1.0F, 13F, -7F);
        headHippo.rotateAngleY = 0.0F;
        snout = new ModelRenderer(this, 38, 22);
        snout.addBox(-4F, -1F, -10F, 8, 5, 5, f2);
        snout.setRotationPoint(1.0F, 13F, -7F);
        body = new ModelRenderer(this, 12, 0);
        body.addBox(-5F, -4F, -8F, 10, 8, 16, 1.35F);
        body.setRotationPoint(1.0F, 14F, 2.0F);
        leg1 = new ModelRenderer(this, 0, 23);
        leg1.addBox(-2F, 0.0F, -2F, 4, 5, 4, f2);
        leg1.setRotationPoint(-2F, 19F, -4F);
        leg2 = new ModelRenderer(this, 0, 23);
        leg2.addBox(-2F, 0.0F, -2F, 4, 5, 4, f2);
        leg2.setRotationPoint(4F, 19F, -4F);
        leg4 = new ModelRenderer(this, 0, 23);
        leg4.addBox(-2F, 0.0F, -2F, 4, 5, 4, f2);
        leg4.setRotationPoint(4F, 19F, 8F);
        leg3 = new ModelRenderer(this, 0, 23);
        leg3.addBox(-2F, 0.0F, -2F, 4, 5, 4, f2);
        leg3.setRotationPoint(-2F, 19F, 8F);
        noseR = new ModelRenderer(this, 8, 0);
        noseR.addBox(2.0F, -1.7F, -9F, 1, 1, 1, f2);
        noseR.setRotationPoint(1.0F, 13F, -7F);
        noseL = new ModelRenderer(this, 4, 0);
        noseL.addBox(-3F, -1.7F, -9F, 1, 1, 1, f2);
        noseL.setRotationPoint(1.0F, 13F, -7F);
        earR = new ModelRenderer(this, 0, 6);
        earR.addBox(2.0F, -5F, -3F, 1, 2, 2, f2);
        earR.setRotationPoint(1.0F, 13F, -7F);
        earL = new ModelRenderer(this, 0, 2);
        earL.addBox(-3F, -5F, -3F, 1, 2, 2, f2);
        earL.setRotationPoint(1.0F, 13F, -7F);
        toothL = new ModelRenderer(this, 0, 16);
        toothL.addBox(-3F, 4F, -10F, 1, 2, 1, f2);
        toothL.setRotationPoint(1.0F, 13F, -7F);
        toothR = new ModelRenderer(this, 0, 16);
        toothR.addBox(2.0F, 4F, -10F, 1, 2, 1, f2);
        toothR.setRotationPoint(1.0F, 13F, -7F);
        eyeL = new ModelRenderer(this, 0, 0);
        eyeL.addBox(-3.5F, -1.7F, -4F, 1, 1, 1, f2);
        eyeL.setRotationPoint(1.0F, 13F, -7F);
        eyeR = new ModelRenderer(this, 0, 0);
        eyeR.addBox(2.5F, -1.7F, -4F, 1, 1, 1, f2);
        eyeR.setRotationPoint(1.0F, 13F, -7F);
        mouth = new ModelRenderer(this, 8, 0);
        mouth.addBox(-2.5F, 1.0F, -10F, 5, 1, 5, f2);
        mouth.setRotationPoint(1.0F, 13F, -7F);
        mouth.rotateAngleX = 0.45203F;
        mouth.rotateAngleY = 0.0F;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        headHippo.render(f5);
        snout.render(f5);
        body.render(f5);
        leg1.render(f5);
        leg2.render(f5);
        leg4.render(f5);
        leg3.render(f5);
        noseR.render(f5);
        noseL.render(f5);
        earR.render(f5);
        earL.render(f5);
        toothL.render(f5);
        toothR.render(f5);
        eyeL.render(f5);
        eyeR.render(f5);
        mouth.render(f5);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        headHippo.rotateAngleY = f3 / (180F / (float)Math.PI);
        headHippo.rotateAngleX = f4 / (180F / (float)Math.PI);
        snout.rotateAngleY = noseR.rotateAngleY = noseL.rotateAngleY = eyeR.rotateAngleY = eyeL.rotateAngleY = toothL.rotateAngleY = toothR.rotateAngleY = earL.rotateAngleY = earR.rotateAngleY = headHippo.rotateAngleY;
        snout.rotateAngleX = noseR.rotateAngleX = noseL.rotateAngleX = eyeR.rotateAngleX = eyeL.rotateAngleX = toothL.rotateAngleX = toothR.rotateAngleX = earL.rotateAngleX = earR.rotateAngleX = headHippo.rotateAngleX;

        if (taildirection > 0)
        {
            tailwag += 0.0002F;

            if (tailwag > 0.067F)
            {
                taildirection = taildirection * -1;
            }
        }
        else
        {
            tailwag -= 0.0002F;

            if ((double)tailwag < -0.067000000000000004D)
            {
                taildirection = taildirection * -1;
            }
        }

        mouth.rotateAngleX = headHippo.rotateAngleX + 0.45203F + tailwag;
        mouth.rotateAngleY = headHippo.rotateAngleY;
        leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
        leg3.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
        leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }
}
