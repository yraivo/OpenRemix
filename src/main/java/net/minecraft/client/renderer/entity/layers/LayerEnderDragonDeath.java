package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.EntityDragon;

import java.util.Random;

public class LayerEnderDragonDeath implements LayerRenderer {
    public void method_754de71c5858464938fa(
            EntityDragon entityDragon,
            float f5,
            float f6,
            float f,
            float f7,
            float f8,
            float f9,
            float f10) {
        int[] aint = LayerArmorBase.method_650532ba484532b3efb0();
        if (entityDragon.deathTicks > 0) {
            Tessellator instance = Tessellator.getInstance();
            WorldRenderer worldRenderer = instance.getWorldRenderer();
            RenderHelper.disableStandardItemLighting();
            float f1 = (entityDragon.deathTicks + f) / 200.0F;
            float f2 = 0.0F;
            if (f1 > 0.8F) {
                f2 = (f1 - 0.8F) / 0.2F;
            }

            Random random = new Random(432L);
            GlStateManager.disableTexture2D();
            GlStateManager.shadeModel(7425);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 1);
            GlStateManager.disableAlpha();
            GlStateManager.enableCull();
            GlStateManager.depthMask(false);
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, -1.0F, -2.0F);
            int i = 0;

            while (i < (f1 + f1 * f1) / 2.0F * 60.0F) {
                GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
                worldRenderer.startDrawing(6);
                float f3 = random.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
                float f4 = random.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
                worldRenderer.setVertexColorFromRgbAndAlpha(16777215, (int) (255.0F * (1.0F - f2)));
                worldRenderer.addVertex(0.0, 0.0, 0.0);
                worldRenderer.setVertexColorFromRgbAndAlpha(16711935, 0);
                worldRenderer.addVertex(-0.866 * f4, f3, -0.5F * f4);
                worldRenderer.addVertex(0.866 * f4, f3, -0.5F * f4);
                worldRenderer.addVertex(0.0, f3, 1.0F * f4);
                worldRenderer.addVertex(-0.866 * f4, f3, -0.5F * f4);
                instance.draw();
                i++;
                if (aint != null) {
                    break;
                }
            }

            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.disableCull();
            GlStateManager.disableBlend();
            GlStateManager.shadeModel(7424);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
            RenderHelper.enableStandardItemLighting();
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }

    public void doRenderLayer(
            LivingEntity livingEntity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        this.method_754de71c5858464938fa((EntityDragon) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }
}
