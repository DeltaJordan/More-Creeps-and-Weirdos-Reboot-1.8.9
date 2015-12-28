package fr.elias.morecreeps.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CREEPSModelEvilLight extends ModelBase
{
    public ModelRenderer bipedHead;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedBody2;

    public CREEPSModelEvilLight()
    {
        this(2.0F);
    }

    public CREEPSModelEvilLight(float f)
    {
        this(f, 0.0F);
    }

    public CREEPSModelEvilLight(float f, float f1)
    {
        bipedHead = new ModelRenderer(this, 0, 0);
        bipedHead.addBox(-5F, 0.0F, 0.0F, 10, 20, 0, f);
        bipedHead.setRotationPoint(0.0F, 0.0F + f1, 2.0F);
        bipedBody = new ModelRenderer(this, 0, 0);
        bipedBody.addBox(-5F, 0.0F, 0.0F, 10, 20, 0, f);
        bipedBody.setRotationPoint(6F, 0.0F + f1, 3F);
        bipedBody2 = new ModelRenderer(this, 0, 0);
        bipedBody2.addBox(-5F, 0.0F, 0.0F, 10, 20, 0, f);
        bipedBody2.setRotationPoint(-6F, -6F + f1, 2.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        bipedHead.render(f5);
        bipedBody.render(f5);
        bipedBody2.render(f5);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        bipedHead.rotateAngleZ = f2 * 2.0F;
        bipedHead.rotateAngleX = f2;
        bipedBody.rotateAngleZ = f2 * 3F;
        bipedBody2.rotateAngleZ = f2 * 5F;
        bipedBody2.rotateAngleX = f2 * 2.0F;
    }
}
