package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;

public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer {
    public void renderTileEntityAt(
            TileEntity tileEntity,
            double p171_1,
            double p171_2,
            double p171_3,
            float p171_4,
            int p171_5) {
        this.method_e31ea536c43ad3e41ebe(
                (net.minecraft.tileentity.TileEntityMobSpawner) tileEntity, p171_1, p171_2, p171_3, p171_4, p171_5);
    }

    public void method_e31ea536c43ad3e41ebe(
            TileEntityMobSpawner tileEntityMobSpawner,
            double d0,
            double d1,
            double d2,
            float f,
            int i) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) d0 + 0.5F, (float) d1, (float) d2 + 0.5F);
        renderMob(tileEntityMobSpawner.getSpawnerBaseLogic(), d0, d1, d2, f);
        GlStateManager.popMatrix();
    }

    public static void renderMob(
            MobSpawnerBaseLogic mobSpawnerBaseLogic, double d0, double d1, double d2, float f) {
        Entity entity = mobSpawnerBaseLogic.func_180612_a(mobSpawnerBaseLogic.getSpawnerWorld());
        if (entity != null) {
            float f1 = 0.4375F;
            GlStateManager.translate(0.0F, 0.4F, 0.0F);
            GlStateManager.rotate(
                    (float)
                                    (mobSpawnerBaseLogic.getPrevMobRotation()
                                            + (mobSpawnerBaseLogic.getMobRotation()
                                                            - mobSpawnerBaseLogic
                                                                    .getPrevMobRotation())
                                                    * f)
                            * 10.0F,
                    0.0F,
                    1.0F,
                    0.0F);
            GlStateManager.rotate(-30.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.4F, 0.0F);
            GlStateManager.scale(f1, f1, f1);
            entity.setLocationAndAngles(d0, d1, d2, 0.0F, 0.0F);
            MinecraftClient.getMinecraft()
                    .getRenderManager()
                    .renderEntityWithPosYaw(entity, 0.0, 0.0, 0.0, 0.0F, f);
        }
    }
}
