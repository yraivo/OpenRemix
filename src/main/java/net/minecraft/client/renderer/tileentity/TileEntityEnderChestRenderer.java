package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.ResourceLocation;
import remix.client.ClientServices;
import remix.client.RemixClient;

import org.lwjgl.opengl.GL11;

public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer {
    private static ResourceLocation ENDER_CHEST_TEXTURE;
    private ModelChest chestModel = new ModelChest();

    static {
        ENDER_CHEST_TEXTURE = new ResourceLocation("textures/entity/chest/ender.png");
    }

    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z,
                                   float partialTicks, int damageStage) {
        RemixClient.INSTANCE.getClientServices();
        boolean depthOffset = ClientServices.moduleManager.chams.usesStorageDepthOffset();
        if (depthOffset) {
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, -2000000.0F);
        }
        try {
            this.renderEnderChestModel((TileEntityEnderChest) tileEntity, x, y, z, partialTicks, damageStage);
        } finally {
            if (depthOffset) {
                GL11.glPolygonOffset(1.0F, 2000000.0F);
                GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
            }
        }
    }

    public void renderEnderChestModel(TileEntityEnderChest chest, double x, double y, double z,
                                           float partialTicks, int damageStage) {
        TileEntitySpecialRenderer.getLegacyTileRendererState();
        int metadata = chest.hasWorldObj() ? chest.getBlockMetadata() : 0;
        if (damageStage >= 0) {
            this.beginDestroyStageTexture(damageStage, 4.0F, 4.0F);
        } else {
            this.bindTexture(ENDER_CHEST_TEXTURE);
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        try {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            translateChestToPivot(x, y, z);
            rotateChestAtPivot(metadata);
            float opening = chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * partialTicks;
            this.chestModel.chestLid.rotateAngleX = chestLidRotation(opening);
            this.chestModel.renderAll();
        } finally {
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if (damageStage >= 0) {
                endDestroyStageTexture();
            }
        }
    }
}
