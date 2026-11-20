package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public abstract class TileEntitySpecialRenderer {
    protected static ResourceLocation[] DESTROY_STAGES;
    private static int[] legacyTileRenderState;

    protected TileEntityRendererDispatcher rendererDispatcher;

    public abstract void renderTileEntityAt(
            TileEntity tileEntity, double d0, double d1, double d2, float f, int i);

    // region String decoding support

    // endregion

    public static void setLegacyTileRendererState(int[] aint) {
        net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer.legacyTileRenderState = aint;
    }

    public static int[] getLegacyTileRendererState() {
        return net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer.legacyTileRenderState;
    }

    protected void bindTexture(ResourceLocation resourceLocation) {
        TextureManager aFy = this.rendererDispatcher.renderEngine;
        if (aFy != null) {
            aFy.bindTexture(resourceLocation);
        }
    }

    protected void beginDestroyStageTexture(int damageStage, float scaleX, float scaleY) {
        this.bindTexture(DESTROY_STAGES[damageStage]);
        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GlStateManager.pushMatrix();
        GlStateManager.scale(scaleX, scaleY, 1.0F);
        GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
    }

    protected static void endDestroyStageTexture() {
        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
    }

    protected static void translateChestToPivot(double x, double y, double z) {
        GlStateManager.translate((float) x, (float) y + 1.0F, (float) z + 1.0F);
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        GlStateManager.translate(0.5F, 0.5F, 0.5F);
    }

    protected static void rotateChestAtPivot(int metadata) {
        float yaw;
        switch (metadata) {
            case 2: yaw = 180.0F; break;
            case 4: yaw = 90.0F; break;
            case 5: yaw = -90.0F; break;
            default: yaw = 0.0F;
        }
        GlStateManager.rotate(yaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
    }

    protected static float chestLidRotation(float opening) {
        float remaining = 1.0F - opening;
        float eased = 1.0F - remaining * remaining * remaining;
        return -(eased * (float) Math.PI / 2.0F);
    }

    // region String decoding support

    // endregion

    public FontRenderer getFontRenderer() {
        return this.rendererDispatcher.getFontRenderer();
    }

    public void setRendererDispatcher(TileEntityRendererDispatcher aFzValue) {
        this.rendererDispatcher = aFzValue;
    }

    // region String decoding support

    // endregion

    static {
        int[] aint = new int[4];
        setLegacyTileRendererState(aint);
        net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer.DESTROY_STAGES =
                new ResourceLocation[] {
                    new ResourceLocation("textures/blocks/destroy_stage_0.png"),
                    new ResourceLocation("textures/blocks/destroy_stage_1.png"),
                    new ResourceLocation("textures/blocks/destroy_stage_2.png"),
                    new ResourceLocation("textures/blocks/destroy_stage_3.png"),
                    new ResourceLocation("textures/blocks/destroy_stage_4.png"),
                    new ResourceLocation("textures/blocks/destroy_stage_5.png"),
                    new ResourceLocation("textures/blocks/destroy_stage_6.png"),
                    new ResourceLocation("textures/blocks/destroy_stage_7.png"),
                    new ResourceLocation("textures/blocks/destroy_stage_8.png"),
                    new ResourceLocation("textures/blocks/destroy_stage_9.png")
                };
    }

    protected World getWorld() {
        return this.rendererDispatcher.worldObj;
    }
}
