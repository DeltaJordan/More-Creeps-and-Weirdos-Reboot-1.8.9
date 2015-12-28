package fr.elias.morecreeps.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CREEPSModelAtom extends ModelBase
{
    public ModelRenderer box1;
    public ModelRenderer box2;
    public ModelRenderer box3;
    public ModelRenderer head;

    /**
     * Records whether the model should be rendered holding an item in the left hand, and if that item is a block.
     */
    public boolean heldItemLeft;

    /**
     * Records whether the model should be rendered holding an item in the right hand, and if that item is a block.
     */
    public boolean heldItemRight;
    public boolean isSneak;
    public float robotsize;
    public float tailwag;
    public int taildirection;

    public CREEPSModelAtom()
    {
        this(0.0F);
    }

    public CREEPSModelAtom(float f)
    {
        this(f, 0.0F);
    }

    public CREEPSModelAtom(float f, float f1)
    {
        taildirection = 1;
        float f2 = 0.0F;
        box1 = new ModelRenderer(this, 0, 18);
        box1.addBox(0.0F, 7F, 0.0F, 2, 2, 2, f2);
        box1.setRotationPoint(0.0F, 16F, 0.0F);
        box1.rotateAngleX = 0.52184F;
        box1.rotateAngleY = -0.67804F;
        box1.rotateAngleZ = 0.0452F;
        box2 = new ModelRenderer(this, 0, 6);
        box2.addBox(0.0F, -7F, 0.0F, 2, 2, 2, f2);
        box2.setRotationPoint(0.0F, 15F, 0.0F);
        box3 = new ModelRenderer(this, 0, 12);
        box3.addBox(5F, -2F, 0.0F, 2, 2, 2, f2);
        box3.setRotationPoint(0.0F, 15F, 0.0F);
        box3.rotateAngleX = (float)Math.PI;
        box3.rotateAngleY = 0.58764F;
        box3.rotateAngleZ = 0.27122F;
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-1F, -1F, -1F, 3, 3, 3, f2);
        head.setRotationPoint(0.0F, 15F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        box1.render(f5);
        box2.render(f5);
        box3.render(f5);
        head.render(f5);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        head.rotateAngleX += 0.055F;
        head.rotateAngleZ += 0.055F;
        box1.rotateAngleX += 0.5F;
        box2.rotateAngleX += 0.25F;
        box3.rotateAngleX += 0.15F;
        box1.rotateAngleZ += 0.5F;
        box2.rotateAngleZ += 0.25F;
        box3.rotateAngleZ += 0.15F;
    }
}
