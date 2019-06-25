package fr.elias.morecreeps.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CREEPSModelTowel extends ModelBase
{
    public ModelRenderer body;

    public CREEPSModelTowel()
    {
        this(0.0F);
    }

    public CREEPSModelTowel(float f)
    {
        this(f, 0.0F);
    }

    public CREEPSModelTowel(float f, float f1)
    {
        float f2 = 0.0F;
        body = new ModelRenderer(this, 0, 0);
        body.addBox(-9F, 0.0F, -13F, 18, 1, 25, 0.0F);
        body.setRotationPoint(0.0F, 23F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        body.render(f5);
    }
}
