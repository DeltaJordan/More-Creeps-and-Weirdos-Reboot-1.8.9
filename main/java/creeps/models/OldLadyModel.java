package creeps.models;//based on master configuration

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class OldLadyModel {
	
public static class ModelNew extends ModelBase
{
ModelRenderer bob;
ModelRenderer head;
ModelRenderer body;
ModelRenderer rightarm;
ModelRenderer leftarm;
ModelRenderer rightleg;
ModelRenderer leftleg;
ModelRenderer helm;
ModelRenderer cane;

public ModelNew()
{
textureWidth = 64;
textureHeight = 32;

bob = new ModelRenderer(this, 32, 4);
bob.addBox(-2F, -2F, 0F, 5, 4, 2);
bob.setRotationPoint(0F, 8F, -2F);
bob.setTextureSize(64, 32);
bob.mirror = true;
setRotation(bob, 0.3346075F, 0F, 0F);
head = new ModelRenderer(this, 0, 0);
head.addBox(-4F, -8F, -4F, 8, 8, 8);
head.setRotationPoint(0F, 8F, -2F);
head.setTextureSize(64, 32);
head.mirror = true;
setRotation(head, 0.3346075F, 0F, 0F);
body = new ModelRenderer(this, 16, 16);
body.addBox(-3F, 1F, -2F, 6, 8, 4);
body.setRotationPoint(0F, 7F, -3F);
body.setTextureSize(64, 32);
body.mirror = true;
setRotation(body, 0.3346075F, 0F, 0F);
rightarm = new ModelRenderer(this, 40, 16);
rightarm.addBox(-3F, -2F, -2F, 3, 9, 3);
rightarm.setRotationPoint(-3F, 9F, -2F);
rightarm.setTextureSize(64, 32);
rightarm.mirror = true;
setRotation(rightarm, -0.669215F, 0F, 0F);
leftarm = new ModelRenderer(this, 40, 16);
leftarm.addBox(-1F, -2F, -2F, 3, 9, 3);
leftarm.setRotationPoint(4F, 9F, -2F);
leftarm.setTextureSize(64, 32);
leftarm.mirror = true;
setRotation(leftarm, 0F, 0F, 0F);
rightleg = new ModelRenderer(this, 0, 16);
rightleg.addBox(-2F, 0F, -2F, 3, 9, 4);
rightleg.setRotationPoint(-1F, 15F, 0F);
rightleg.setTextureSize(64, 32);
rightleg.mirror = true;
setRotation(rightleg, 0F, 0F, 0F);
leftleg = new ModelRenderer(this, 0, 16);
leftleg.addBox(-2F, 0F, -2F, 3, 9, 4);
leftleg.setRotationPoint(2F, 15F, 0F);
leftleg.setTextureSize(64, 32);
leftleg.mirror = true;
setRotation(leftleg, 0F, 0F, 0F);
helm = new ModelRenderer(this, 25, -10);
helm.addBox(-5F, -5F, -9F, 8, 8, 8);
helm.setRotationPoint(0F, 8F, -2F);
helm.setTextureSize(64, 32);
helm.mirror = true;
setRotation(helm, 2.844164F, 3.141593F, 0F);
cane = new ModelRenderer(this, 54, 17);
cane.addBox(0F, 0F, 0F, 1, 9, 1);
cane.setRotationPoint(-3F, 9F, -2F);
cane.setTextureSize(64, 32);
cane.mirror = true;
setRotation(cane, 0F, 0F, 0F);
}

public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
{
super.render(entity, f, f1, f2, f3, f4, f5);
setRotationAngles(f, f1, f2, f3, f4, f5, entity);

bob.render(f5);
head.render(f5);
body.render(f5);
rightarm.render(f5);
leftarm.render(f5);
rightleg.render(f5);
leftleg.render(f5);
helm.render(f5);
cane.render(f5);
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
this.head.rotateAngleY = f3 / (180F / (float)Math.PI);
this.head.rotateAngleX = f4 / (180F / (float)Math.PI);
this.bob.rotateAngleX = f3 / (180F / (float)Math.PI);
this.bob.rotateAngleY = f4 / (180F / (float)Math.PI);
this.helm.rotateAngleX = f3 / (180F / (float)Math.PI);
this.helm.rotateAngleY = f4 / (180F / (float)Math.PI);
this.leftleg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
this.rightleg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
this.rightarm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f1 * 0.5F;
this.leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
this.cane.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f1 * 0.5F;
}

}

}
