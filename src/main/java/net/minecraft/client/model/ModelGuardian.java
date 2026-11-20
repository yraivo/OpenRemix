package net.minecraft.client.model;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class ModelGuardian extends ModelBase {
    private ModelRenderer[] guardianTail;
    private ModelRenderer guardianBody;
    private ModelRenderer[] guardianSpines;
    private ModelRenderer guardianEye;

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.guardianBody.render(f5);
    }

    public void setRotationAngles(
            float f6, float f7, float f, float f1, float f2, float f8, Entity entity) {
        EntityGuardian entityGuardian = (EntityGuardian) entity;
        float f3 = f - entityGuardian.ticksExisted;
        this.guardianBody.rotateAngleY = f1 / (180.0F / (float) Math.PI);
        this.guardianBody.rotateAngleX = f2 / (180.0F / (float) Math.PI);
        float[] afloat =
                new float[] {
                    1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F
                };
        float[] afloat1 =
                new float[] {
                    0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F
                };
        float[] afloat2 =
                new float[] {
                    0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F
                };
        float[] afloat3 =
                new float[] {
                    0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F
                };
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        float[] afloat4 =
                new float[] {
                    -8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F
                };
        String[] astring = astring1;
        float[] afloat5 =
                new float[] {
                    8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F
                };
        float f4 = (1.0F - entityGuardian.func_175469_o(f3)) * 0.55F;
        int i = 0;

        while (i < 12) {
            this.guardianSpines[i].rotateAngleX = (float) Math.PI * afloat[i];
            this.guardianSpines[i].rotateAngleY = (float) Math.PI * afloat1[i];
            this.guardianSpines[i].rotateAngleZ = (float) Math.PI * afloat2[i];
            this.guardianSpines[i].rotationPointX =
                    afloat3[i] * (1.0F + MathHelper.cos(f * 1.5F + i) * 0.01F - f4);
            this.guardianSpines[i].rotationPointY =
                    16.0F + afloat4[i] * (1.0F + MathHelper.cos(f * 1.5F + i) * 0.01F - f4);
            this.guardianSpines[i].rotationPointZ =
                    afloat5[i] * (1.0F + MathHelper.cos(f * 1.5F + i) * 0.01F - f4);
            i++;
            if (astring != null) {
                break;
            }
        }

        this.guardianEye.rotationPointZ = -8.25F;
        Object renderViewEntity = MinecraftClient.getMinecraft().getRenderViewEntity();
        if (entityGuardian.hasTargetedEntity()) {
            renderViewEntity = entityGuardian.getTargetedEntity();
        }

        if (renderViewEntity != null) {
            Vec3 vec3;
            Vec3 vec3Value;
            {
                vec3 = ((Entity) renderViewEntity).getPositionEyes(0.0F);
                vec3Value = entity.getPositionEyes(0.0F);
                double d0 = vec3.y - vec3Value.y;
                if (d0 > 0.0) {
                    this.guardianEye.rotationPointY = 0.0F;
                    if (!(astring == null)) {

                        this.guardianEye.rotationPointY = 1.0F;
                    }
                } else {

                    this.guardianEye.rotationPointY = 1.0F;
                }
            }

            Vec3 vec3Value2 = entity.getLook(0.0F);
            vec3Value2 = new Vec3(vec3Value2.x, 0.0, vec3Value2.z);
            Vec3 vec3Value3 =
                    new Vec3(vec3Value.x - vec3.x, 0.0, vec3Value.z - vec3.z)
                            .normalize()
                            .rotateYaw((float) (Math.PI / 2));
            double d1 = vec3Value2.dotProduct(vec3Value3);
            this.guardianEye.rotationPointX =
                    MathHelper.sqrt((float) Math.abs(d1)) * 2.0F * (float) Math.signum(d1);
        }

        this.guardianEye.showModel = true;
        float f5 = entityGuardian.func_175471_a(f3);
        this.guardianTail[0].rotateAngleY = MathHelper.sin(f5) * (float) Math.PI * 0.05F;
        this.guardianTail[1].rotateAngleY = MathHelper.sin(f5) * (float) Math.PI * 0.1F;
        this.guardianTail[1].rotationPointX = -1.5F;
        this.guardianTail[1].rotationPointY = 0.5F;
        this.guardianTail[1].rotationPointZ = 14.0F;
        this.guardianTail[2].rotateAngleY = MathHelper.sin(f5) * (float) Math.PI * 0.15F;
        this.guardianTail[2].rotationPointX = 0.5F;
        this.guardianTail[2].rotationPointY = 0.5F;
        this.guardianTail[2].rotationPointZ = 6.0F;
    }

    public ModelGuardian() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.guardianSpines = new ModelRenderer[12];
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        this.guardianBody = new ModelRenderer(this);
        this.guardianBody.setTextureOffset(0, 0).addBox(-6.0F, 10.0F, -8.0F, 12, 12, 16);
        this.guardianBody.setTextureOffset(0, 28).addBox(-8.0F, 10.0F, -6.0F, 2, 12, 12);
        this.guardianBody.setTextureOffset(0, 28).addBox(6.0F, 10.0F, -6.0F, 2, 12, 12, true);
        this.guardianBody.setTextureOffset(16, 40).addBox(-6.0F, 8.0F, -6.0F, 12, 2, 12);
        String[] astring = astring1;
        this.guardianBody.setTextureOffset(16, 40).addBox(-6.0F, 22.0F, -6.0F, 12, 2, 12);
        int i = 0;

        while (i < this.guardianSpines.length) {
            this.guardianSpines[i] = new ModelRenderer(this, 0, 0);
            this.guardianSpines[i].addBox(-1.0F, -4.5F, -1.0F, 2, 9, 2);
            this.guardianBody.addChild(this.guardianSpines[i]);
            i++;
            if (astring != null) {
                break;
            }
        }

        this.guardianEye = new ModelRenderer(this, 8, 0);
        this.guardianEye.addBox(-1.0F, 15.0F, 0.0F, 2, 2, 1);
        this.guardianBody.addChild(this.guardianEye);
        this.guardianTail = new ModelRenderer[3];
        this.guardianTail[0] = new ModelRenderer(this, 40, 0);
        this.guardianTail[0].addBox(-2.0F, 14.0F, 7.0F, 4, 4, 8);
        this.guardianTail[1] = new ModelRenderer(this, 0, 54);
        this.guardianTail[1].addBox(0.0F, 14.0F, 0.0F, 3, 3, 7);
        this.guardianTail[2] = new ModelRenderer(this);
        this.guardianTail[2].setTextureOffset(41, 32).addBox(0.0F, 14.0F, 0.0F, 2, 2, 6);
        this.guardianTail[2].setTextureOffset(25, 19).addBox(1.0F, 10.5F, 3.0F, 1, 9, 9);
        this.guardianBody.addChild(this.guardianTail[0]);
        this.guardianTail[0].addChild(this.guardianTail[1]);
        this.guardianTail[1].addChild(this.guardianTail[2]);
    }

    public int method_9b417d27949cba33a0d0() {
        return 54;
    }
}
