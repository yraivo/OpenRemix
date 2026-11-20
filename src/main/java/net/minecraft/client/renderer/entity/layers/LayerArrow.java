package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;

import java.util.Random;

public class LayerArrow implements LayerRenderer {
    private RendererLivingEntity field_177168_a;

    public void doRenderLayer(
            LivingEntity livingEntity,
            float f8,
            float f9,
            float f,
            float f10,
            float f11,
            float f12,
            float f13) {
        int[] aint1 = LayerArmorBase.method_650532ba484532b3efb0();
        int arrowCountInEntity = livingEntity.getArrowCountInEntity();
        int[] aint = aint1;
        if (arrowCountInEntity > 0) {
            EntityArrow entityArrow =
                    new EntityArrow(
                            livingEntity.world,
                            livingEntity.posX,
                            livingEntity.posY,
                            livingEntity.posZ);
            Random random = new Random(livingEntity.getEntityId());
            RenderHelper.disableStandardItemLighting();
            int j = 0;

            while (j < arrowCountInEntity) {
                GlStateManager.pushMatrix();
                ModelRenderer randomModelBox =
                        this.field_177168_a.getMainModel().getRandomModelBox(random);
                ModelBox modelBox =
                        (ModelBox)
                                randomModelBox.cubeList.get(
                                        random.nextInt(randomModelBox.cubeList.size()));
                randomModelBox.postRender(0.0625F);
                float f1 = random.nextFloat();
                float f2 = random.nextFloat();
                float f3 = random.nextFloat();
                float f4 = (modelBox.posX1 + (modelBox.posX2 - modelBox.posX1) * f1) / 16.0F;
                float f5 = (modelBox.posY1 + (modelBox.posY2 - modelBox.posY1) * f2) / 16.0F;
                float f6 = (modelBox.posZ1 + (modelBox.posZ2 - modelBox.posZ1) * f3) / 16.0F;
                GlStateManager.translate(f4, f5, f6);
                f1 = f1 * 2.0F - 1.0F;
                f2 = f2 * 2.0F - 1.0F;
                f3 = f3 * 2.0F - 1.0F;
                f1 *= -1.0F;
                f2 *= -1.0F;
                f3 *= -1.0F;
                float f7 = MathHelper.sqrt(f1 * f1 + f3 * f3);
                entityArrow.prevRotationYaw =
                        entityArrow.rotationYaw = (float) (Math.atan2(f1, f3) * 180.0 / Math.PI);
                entityArrow.prevRotationPitch =
                        entityArrow.rotationPitch = (float) (Math.atan2(f2, f7) * 180.0 / Math.PI);
                double d0 = 0.0;
                double d1 = 0.0;
                double d2 = 0.0;
                this.field_177168_a
                        .getRenderManager()
                        .renderEntityWithPosYaw(entityArrow, d0, d1, d2, 0.0F, f);
                GlStateManager.popMatrix();
                j++;
                if (aint != null) {
                    break;
                }
            }

            RenderHelper.enableStandardItemLighting();
        }
    }

    public LayerArrow(RendererLivingEntity aCYValue) {
        this.field_177168_a = aCYValue;
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
