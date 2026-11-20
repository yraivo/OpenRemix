package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelVillager extends ModelBase {
    public ModelRenderer villagerBody;
    public ModelRenderer villagerNose;
    public ModelRenderer leftVillagerLeg;
    public ModelRenderer villagerArms;
    public ModelRenderer villagerHead;
    public ModelRenderer rightVillagerLeg;

    public void setRotationAngles(
            float f, float f1, float f4, float f2, float f3, float f5, Entity entity) {
        this.villagerHead.rotateAngleY = f2 / (180.0F / (float) Math.PI);
        this.villagerHead.rotateAngleX = f3 / (180.0F / (float) Math.PI);
        this.villagerArms.rotationPointY = 3.0F;
        this.villagerArms.rotationPointZ = -1.0F;
        this.villagerArms.rotateAngleX = -0.75F;
        this.rightVillagerLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1 * 0.5F;
        this.leftVillagerLeg.rotateAngleX =
                MathHelper.cos(f * 0.6662F + (float) Math.PI) * 1.4F * f1 * 0.5F;
        this.rightVillagerLeg.rotateAngleY = 0.0F;
        this.leftVillagerLeg.rotateAngleY = 0.0F;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.villagerHead.render(f5);
        this.villagerBody.render(f5);
        this.rightVillagerLeg.render(f5);
        this.leftVillagerLeg.render(f5);
        this.villagerArms.render(f5);
    }

    public ModelVillager(float f) {
        this(f, 0.0F, 64, 64);
    }

    public ModelVillager(float f, float f1, int i, int j) {
        this.villagerHead = new ModelRenderer(this).setTextureSize(i, j);
        this.villagerHead.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.villagerHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, f);
        this.villagerNose = new ModelRenderer(this).setTextureSize(i, j);
        this.villagerNose.setRotationPoint(0.0F, f1 - 2.0F, 0.0F);
        this.villagerNose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, f);
        this.villagerHead.addChild(this.villagerNose);
        this.villagerBody = new ModelRenderer(this).setTextureSize(i, j);
        this.villagerBody.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.villagerBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, f);
        this.villagerBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, f + 0.5F);
        this.villagerArms = new ModelRenderer(this).setTextureSize(i, j);
        this.villagerArms.setRotationPoint(0.0F, 0.0F + f1 + 2.0F, 0.0F);
        this.villagerArms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, f);
        this.villagerArms.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, f);
        this.villagerArms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, f);
        this.rightVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(i, j);
        this.rightVillagerLeg.setRotationPoint(-2.0F, 12.0F + f1, 0.0F);
        this.rightVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, f);
        this.leftVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(i, j);
        this.leftVillagerLeg.mirror = true;
        this.leftVillagerLeg.setRotationPoint(2.0F, 12.0F + f1, 0.0F);
        this.leftVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, f);
    }
}
