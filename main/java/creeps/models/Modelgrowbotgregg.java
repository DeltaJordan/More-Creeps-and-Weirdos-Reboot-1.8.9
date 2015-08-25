package creeps.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class Modelgrowbotgregg extends ModelBase
{
  //fields
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape6;
    ModelRenderer Shape9;
    ModelRenderer Shape10;
    ModelRenderer Shape11;
    ModelRenderer Shape12;
    ModelRenderer Shape13;
    ModelRenderer Shape14;
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer rightarm;
    ModelRenderer leftarm;
    ModelRenderer rightleg;
    ModelRenderer leftleg;
    ModelRenderer Shape1;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape7;
    ModelRenderer Shape8;
  
  public Modelgrowbotgregg()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Shape2 = new ModelRenderer(this, 0, 16);
      Shape2.addBox(0F, 0F, 0F, 4, 1, 5);
      Shape2.setRotationPoint(2F, 23F, -3.5F);
      Shape2.setTextureSize(64, 32);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 0, 16);
      Shape3.addBox(0F, 0F, 0F, 4, 1, 5);
      Shape3.setRotationPoint(-6F, 23F, -3.5F);
      Shape3.setTextureSize(64, 32);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 52, 18);
      Shape6.addBox(0F, 0F, 0F, 5, 4, 1);
      Shape6.setRotationPoint(-3.5F, 5F, -6F);
      Shape6.setTextureSize(64, 32);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      Shape9 = new ModelRenderer(this, 24, 9);
      Shape9.addBox(0F, 0F, 0F, 1, 1, 2);
      Shape9.setRotationPoint(-1F, 2F, -6F);
      Shape9.setTextureSize(64, 32);
      Shape9.mirror = true;
      setRotation(Shape9, 0F, 0F, 0F);
      Shape10 = new ModelRenderer(this, 24, 0);
      Shape10.addBox(0F, 0F, 0F, 1, 5, 1);
      Shape10.setRotationPoint(2F, -6F, -1F);
      Shape10.setTextureSize(64, 32);
      Shape10.mirror = true;
      setRotation(Shape10, 0F, 0F, 0F);
      Shape11 = new ModelRenderer(this, 24, 0);
      Shape11.addBox(0F, 0F, 0F, 1, 5, 1);
      Shape11.setRotationPoint(-4F, -6F, -1F);
      Shape11.setTextureSize(64, 32);
      Shape11.mirror = true;
      setRotation(Shape11, 0F, 0F, 0F);
      Shape12 = new ModelRenderer(this, 0, 0);
      Shape12.addBox(0F, 0F, 0F, 1, 1, 1);
      Shape12.setRotationPoint(2F, -6F, -1F);
      Shape12.setTextureSize(64, 32);
      Shape12.mirror = true;
      setRotation(Shape12, 0F, 0F, 0F);
      Shape13 = new ModelRenderer(this, 0, 0);
      Shape13.addBox(0F, 0F, 0F, 1, 1, 1);
      Shape13.setRotationPoint(-4F, -6F, -1F);
      Shape13.setTextureSize(64, 32);
      Shape13.mirror = true;
      setRotation(Shape13, 0F, 0F, 0F);
      Shape14 = new ModelRenderer(this, 8, 28);
      Shape14.addBox(0F, 0F, 0F, 7, 4, 0);
      Shape14.setRotationPoint(-4F, -6F, -0.5F);
      Shape14.setTextureSize(64, 32);
      Shape14.mirror = true;
      setRotation(Shape14, 0F, 0F, 0F);
      head = new ModelRenderer(this, 0, 0);
      head.addBox(0F, 0F, 0F, 6, 6, 6);
      head.setRotationPoint(-3.5F, -2F, -4F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      body = new ModelRenderer(this, 33, 6);
      body.addBox(-4F, 0F, -2F, 7, 6, 6);
      body.setRotationPoint(0F, 10F, -1.5F);
      body.setTextureSize(64, 32);
      body.mirror = true;
      setRotation(body, 0F, 0F, 0F);
      rightarm = new ModelRenderer(this, 0, 23);
      rightarm.addBox(-3F, -2F, -2F, 2, 7, 2);
      rightarm.setRotationPoint(-5F, 8F, 0F);
      rightarm.setTextureSize(64, 32);
      rightarm.mirror = true;
      setRotation(rightarm, 0F, 0F, 0F);
      leftarm = new ModelRenderer(this, 0, 23);
      leftarm.addBox(-1F, -2F, -2F, 2, 7, 2);
      leftarm.setRotationPoint(6F, 8F, 0F);
      leftarm.setTextureSize(64, 32);
      leftarm.mirror = true;
      setRotation(leftarm, 0F, 0F, 0F);
      rightleg = new ModelRenderer(this, 40, 18);
      rightleg.addBox(-2F, 0F, -2F, 3, 7, 3);
      rightleg.setRotationPoint(-3F, 16F, 0F);
      rightleg.setTextureSize(64, 32);
      rightleg.mirror = true;
      setRotation(rightleg, 0F, 0F, 0F);
      leftleg = new ModelRenderer(this, 40, 18);
      leftleg.addBox(-2F, 0F, -2F, 3, 7, 3);
      leftleg.setRotationPoint(4F, 16F, 0F);
      leftleg.setTextureSize(64, 32);
      leftleg.mirror = true;
      setRotation(leftleg, 0F, 0F, 0F);
      Shape1 = new ModelRenderer(this, 32, 0);
      Shape1.addBox(0F, 0F, 0F, 9, 6, 7);
      Shape1.setRotationPoint(-5F, 4F, -4.5F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 0, 12);
      Shape4.addBox(0F, 0F, 0F, 2, 2, 2);
      Shape4.setRotationPoint(3F, 5F, -2F);
      Shape4.setTextureSize(64, 32);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 0, 12);
      Shape5.addBox(0F, 0F, 0F, 2, 2, 2);
      Shape5.setRotationPoint(-6F, 5F, -2F);
      Shape5.setTextureSize(64, 32);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape7 = new ModelRenderer(this, 24, 6);
      Shape7.addBox(0F, 0F, 0F, 2, 2, 1);
      Shape7.setRotationPoint(0.5F, -1F, -5F);
      Shape7.setTextureSize(64, 32);
      Shape7.mirror = true;
      setRotation(Shape7, 0F, 0F, 0F);
      Shape8 = new ModelRenderer(this, 24, 6);
      Shape8.addBox(0F, 0F, 0F, 2, 2, 1);
      Shape8.setRotationPoint(-3.5F, -1F, -5F);
      Shape8.setTextureSize(64, 32);
      Shape8.mirror = true;
      setRotation(Shape8, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape6.render(f5);
    Shape9.render(f5);
    Shape10.render(f5);
    Shape11.render(f5);
    Shape12.render(f5);
    Shape13.render(f5);
    Shape14.render(f5);
    head.render(f5);
    body.render(f5);
    rightarm.render(f5);
    leftarm.render(f5);
    rightleg.render(f5);
    leftleg.render(f5);
    Shape1.render(f5);
    Shape4.render(f5);
    Shape5.render(f5);
    Shape7.render(f5);
    Shape8.render(f5);
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

