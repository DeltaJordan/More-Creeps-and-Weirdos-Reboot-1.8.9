package fr.elias.morecreeps.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CREEPSModelArmyGuyArm extends ModelBase
{
    public ModelRenderer bipedRightArm;

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
    public float modelsize;

    public CREEPSModelArmyGuyArm()
    {
        this(0.0F);
    }

    public CREEPSModelArmyGuyArm(float f)
    {
        this(f, 0.0F);
    }

    public CREEPSModelArmyGuyArm(float f, float f1)
    {
        taildirection = 1;
        f1 = 18F;
        bipedRightArm = new ModelRenderer(this, 40, 16);
        bipedRightArm.addBox(-2F, -6F, -2F, 4, 12, 4, f);
        bipedRightArm.setRotationPoint(0.0F, 20F, 0.0F);
        bipedRightArm.rotateAngleX = -((float)Math.PI / 2F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        bipedRightArm.render(f5);
    }

    /**
     * Sets the models various rotation angles.
     */
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
    }
}
