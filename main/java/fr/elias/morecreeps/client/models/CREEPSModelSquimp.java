package fr.elias.morecreeps.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CREEPSModelSquimp extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;

    public CREEPSModelSquimp()
    {
        this(0.0F);
    }

    public CREEPSModelSquimp(float f)
    {
        this(f, 0.0F);
    }

    public CREEPSModelSquimp(float f, float f1)
    {
        byte byte0 = 10;
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-4F, -4F, -4F, 8, 8, 8, f);
        head.setRotationPoint(0.0F, 18 - byte0, 0.0F);
        body = new ModelRenderer(this, 28, 8);
        body.addBox(-3F, -3F, -3F, 6, 6, 3, f);
        body.setRotationPoint(0.0F, 22 - byte0, 0.0F);
        leg1 = new ModelRenderer(this, 0, 16);
        leg1.addBox(-2F, 0.0F, -2F, 2, byte0, 2, f);
        leg1.setRotationPoint(-1F, 24 - byte0, 3F);
        leg2 = new ModelRenderer(this, 0, 16);
        leg2.addBox(-2F, 0.0F, -2F, 2, byte0, 2, f);
        leg2.setRotationPoint(3F, 24 - byte0, 3F);
        leg3 = new ModelRenderer(this, 0, 16);
        leg3.addBox(-2F, 0.0F, -2F, 2, byte0, 2, f);
        leg3.setRotationPoint(-1F, 24 - byte0, -1F);
        leg4 = new ModelRenderer(this, 0, 16);
        leg4.addBox(-2F, 0.0F, -2F, 2, byte0, 2, f);
        leg4.setRotationPoint(3F, 24 - byte0, -1F);
        leg1.rotateAngleX = 0.5F;
        leg1.rotateAngleY = -0.5F;
        leg2.rotateAngleX = 0.5F;
        leg2.rotateAngleY = 0.5F;
        leg3.rotateAngleX = -0.5F;
        leg3.rotateAngleY = 0.5F;
        leg4.rotateAngleX = -0.5F;
        leg4.rotateAngleY = -0.5F;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        head.render(f5);
        body.render(f5);
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
        head.rotateAngleX = -(f4 / (180F / (float)Math.PI));
        head.rotateAngleY = f3 / (180F / (float)Math.PI);
        body.rotateAngleX = ((float)Math.PI / 2F);
    }
}
