package fr.elias.morecreeps.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class CREEPSModelDesertLizard extends ModelBase
{
    public ModelRenderer headDesertlizard;
    public ModelRenderer fin;
    public ModelRenderer body;
    public ModelRenderer mouth;
    public ModelRenderer tail0;
    public ModelRenderer tail1;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;
    public float tailwag;
    public int taildirection;

    public CREEPSModelDesertLizard()
    {
        this(6, 0.0F);
        tailwag = 0.0F;
        taildirection = 1;
    }

    public CREEPSModelDesertLizard(float f)
    {
        this(6, f);
    }

    public CREEPSModelDesertLizard(int i, float f)
    {
        fin = new ModelRenderer(this, 16, 9);
        fin.addBox(0.0F, -8F, -2F, 1, 16, 7, f);
        fin.setRotationPoint(0.0F, 17 - i, 2.0F);
        tail0 = new ModelRenderer(this, 32, 64);
        tail0.addBox(-4F, 12F, -7F, 8, 12, 3, f);
        tail0.setRotationPoint(0.0F, 15 - i, 2.0F);
        tail1 = new ModelRenderer(this, 30, 64);
        tail1.addBox(-3F, 24F, -7F, 6, 10, 2, f);
        tail1.setRotationPoint(0.0F, 14 - i, 2.0F);
        mouth = new ModelRenderer(this, 16, 0);
        mouth.addBox(-2F, 4F, -14F, 4, 2, 6, f);
        mouth.setRotationPoint(0.0F, 18 - i, -6F);
        headDesertlizard = new ModelRenderer(this, 0, 0);
        headDesertlizard.addBox(-3F, 2.0F, -9F, 6, 3, 8, f);
        headDesertlizard.setRotationPoint(0.0F, 18 - i, -6F);
        body = new ModelRenderer(this, 32, 64);
        body.addBox(-5F, -10F, -7F, 10, 22, 5, f);
        body.setRotationPoint(0.0F, 17 - i, 2.0F);
        leg1 = new ModelRenderer(this, 0, 16);
        leg1.addBox(-2F, 0.0F, -2F, 4, i, 4, f);
        leg1.setRotationPoint(-3F, 24 - i, 11F);
        leg2 = new ModelRenderer(this, 0, 16);
        leg2.addBox(-2F, 0.0F, -2F, 4, i, 4, f);
        leg2.setRotationPoint(3F, 24 - i, 11F);
        leg3 = new ModelRenderer(this, 0, 16);
        leg3.addBox(-2F, 0.0F, -2F, 4, i, 4, f);
        leg3.setRotationPoint(-3F, 24 - i, -5F);
        leg4 = new ModelRenderer(this, 0, 16);
        leg4.addBox(-2F, 0.0F, -2F, 4, i, 4, f);
        leg4.setRotationPoint(3F, 24 - i, -5F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        headDesertlizard.render(f5);
        fin.render(f5);
        body.render(f5);
        mouth.render(f5);
        tail0.render(f5);
        tail1.render(f5);
        leg1.render(f5);
        leg2.render(f5);
        leg3.render(f5);
        leg4.render(f5);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        headDesertlizard.rotateAngleX = -(f4 / (180F / (float)Math.PI));
        headDesertlizard.rotateAngleY = f3 / (180F / (float)Math.PI);
        mouth.rotateAngleX = -(f4 / (180F / (float)Math.PI));
        mouth.rotateAngleY = f3 / (180F / (float)Math.PI);
        fin.rotateAngleX = ((float)Math.PI / 2F);
        body.rotateAngleX = ((float)Math.PI / 2F);

        if (taildirection > 0)
        {
            tailwag += 0.0008F;

            if (tailwag > 0.18F)
            {
                taildirection = taildirection * -1;
            }
        }
        else
        {
            tailwag -= 0.0008F;

            if ((double)tailwag < -0.20000000000000001D)
            {
                taildirection = taildirection * -1;
            }
        }

        tail0.rotateAngleY = MathHelper.cos(f * 0.6662F) * tailwag;
        tail1.rotateAngleY = MathHelper.cos(f * 0.6662F) * tailwag;
        tail0.rotateAngleX = 1.440796F;
        tail1.rotateAngleX = 1.380796F;
        leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        leg2.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
        leg3.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
        leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }
}
