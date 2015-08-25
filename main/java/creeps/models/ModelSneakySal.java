package creeps.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSneakySal extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer rightarm;
    ModelRenderer leftarm;
    ModelRenderer rightleg;
    ModelRenderer leftleg;
  
  public ModelSneakySal()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Shape1 = new ModelRenderer(this, 32, 0);
      Shape1.addBox(0F, 0F, 0F, 8, 1, 8);
      Shape1.setRotationPoint(-3F, 3F, -2F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 44, 9);
      Shape2.addBox(0F, 0F, 0F, 5, 2, 5);
      Shape2.setRotationPoint(-1.5F, 1F, -0.5F);
      Shape2.setTextureSize(64, 32);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 0, 10);
      Shape3.addBox(0F, 0F, 0F, 1, 1, 3);
      Shape3.setRotationPoint(1.5F, 6F, -4F);
      Shape3.setTextureSize(64, 32);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0.1858931F);
      Shape4 = new ModelRenderer(this, 8, 10);
      Shape4.addBox(0F, 0F, 0F, 4, 1, 4);
      Shape4.setRotationPoint(-8F, 14F, 0F);
      Shape4.setTextureSize(64, 32);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 0, 16);
      Shape5.addBox(0F, 0F, 0F, 3, 1, 3);
      Shape5.setRotationPoint(-3F, 23F, -3F);
      Shape5.setTextureSize(64, 32);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 0, 16);
      Shape6.addBox(0F, 0F, 0F, 3, 1, 3);
      Shape6.setRotationPoint(3.5F, 23F, -3F);
      Shape6.setTextureSize(64, 32);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      head = new ModelRenderer(this, 0, 0);
      head.addBox(0F, 0F, 0F, 6, 4, 6);
      head.setRotationPoint(-2F, 4F, -1F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      body = new ModelRenderer(this, 28, 16);
      body.addBox(-4F, 0F, -2F, 11, 9, 7);
      body.setRotationPoint(0F, 8F, 0F);
      body.setTextureSize(64, 32);
      body.mirror = true;
      setRotation(body, 0.0872665F, 0F, 0F);
      rightarm = new ModelRenderer(this, 0, 20);
      rightarm.addBox(-3F, -2F, -2F, 4, 8, 4);
      rightarm.setRotationPoint(-5F, 10F, 2F);
      rightarm.setTextureSize(64, 32);
      rightarm.mirror = true;
      setRotation(rightarm, 0F, 0F, 0F);
      leftarm = new ModelRenderer(this, 0, 20);
      leftarm.addBox(-1F, -2F, -2F, 4, 8, 4);
      leftarm.setRotationPoint(8F, 10F, 2F);
      leftarm.setTextureSize(64, 32);
      leftarm.mirror = true;
      setRotation(leftarm, 0F, 0F, 0F);
      rightleg = new ModelRenderer(this, 16, 22);
      rightleg.addBox(-2F, 0F, -2F, 3, 7, 3);
      rightleg.setRotationPoint(-1F, 17F, 2F);
      rightleg.setTextureSize(64, 32);
      rightleg.mirror = true;
      setRotation(rightleg, 0F, 0F, 0F);
      leftleg = new ModelRenderer(this, 16, 22);
      leftleg.addBox(-2F, 0F, -2F, 3, 7, 3);
      leftleg.setRotationPoint(5.5F, 17F, 2F);
      leftleg.setTextureSize(64, 32);
      leftleg.mirror = true;
      setRotation(leftleg, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Shape1.render(f5);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape4.render(f5);
    Shape5.render(f5);
    Shape6.render(f5);
    head.render(f5);
    body.render(f5);
    rightarm.render(f5);
    leftarm.render(f5);
    rightleg.render(f5);
    leftleg.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
  }

}
