package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelZombie extends ModelBiped {
    public ModelZombie(float p171_0, boolean p171_1) {
        super(p171_0, 0.0F, 64, p171_1 ? 32 : 64);
    }

    public void setRotationAngles(
            float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        float f6 = MathHelper.sin(this.swingProgress * (float) Math.PI);
        float f7 =
                MathHelper.sin(
                        (1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress))
                                * (float) Math.PI);
        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;
        this.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
        this.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
        this.bipedRightArm.rotateAngleX = (float) (-Math.PI / 2);
        this.bipedLeftArm.rotateAngleX = (float) (-Math.PI / 2);
        this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
        this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
        this.bipedRightArm.rotateAngleZ =
                this.bipedRightArm.rotateAngleZ + (MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F);
        this.bipedLeftArm.rotateAngleZ =
                this.bipedLeftArm.rotateAngleZ - (MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F);
        this.bipedRightArm.rotateAngleX =
                this.bipedRightArm.rotateAngleX + MathHelper.sin(f2 * 0.067F) * 0.05F;
        this.bipedLeftArm.rotateAngleX =
                this.bipedLeftArm.rotateAngleX - MathHelper.sin(f2 * 0.067F) * 0.05F;
    }

    protected ModelZombie(float p171_0, float p171_1, int p171_2, int p171_3) {
        super(p171_0, p171_1, p171_2, p171_3);
    }

    public ModelZombie() {
        this(0.0F, false);
    }
}
