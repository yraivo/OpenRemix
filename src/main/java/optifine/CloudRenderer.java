package optifine;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class CloudRenderer {
    private boolean updated = false;
    private double updatePlayerZ;
    private int cloudDisplayListId;
    int cloudTickCounter;
    float partialTicks;
    private double updatePlayerY;
    private MinecraftClient mc;
    private int lastBuildCloudTick;
    private double updatePlayerX;
    private boolean renderFancy = false;

    public void renderDisplayList() {
        Entity viewEntity = this.mc.getRenderViewEntity();
        double viewX = viewEntity.prevPosX + (viewEntity.posX - viewEntity.prevPosX) * this.partialTicks;
        double viewY = viewEntity.prevPosY + (viewEntity.posY - viewEntity.prevPosY) * this.partialTicks;
        double viewZ = viewEntity.prevPosZ + (viewEntity.posZ - viewEntity.prevPosZ) * this.partialTicks;
        double elapsedTicks = this.cloudTickCounter - this.lastBuildCloudTick + this.partialTicks;
        boolean applyUnscaledOffset = ReflectorMethod.f() != 0;
        float offsetX = (float) (viewX - this.updatePlayerX + elapsedTicks * 0.03);
        float offsetY = (float) (viewY - this.updatePlayerY);
        float offsetZ = (float) (viewZ - this.updatePlayerZ);

        GlStateManager.pushMatrix();
        if (this.renderFancy) {
            GlStateManager.translate(-offsetX / 12.0F, -offsetY, -offsetZ / 12.0F);
        }
        if (!this.renderFancy || applyUnscaledOffset) {
            GlStateManager.translate(-offsetX, -offsetY, -offsetZ);
        }
        GlStateManager.callList(this.cloudDisplayListId);
        GlStateManager.popMatrix();
        GlStateManager.resetColor();
    }

    public void endUpdateGlList() {
        org.lwjgl.opengl.GL11.glEndList();
        this.lastBuildCloudTick = this.cloudTickCounter;
        this.updatePlayerX = this.mc.getRenderViewEntity().prevPosX;
        this.updatePlayerY = this.mc.getRenderViewEntity().prevPosY;
        this.updatePlayerZ = this.mc.getRenderViewEntity().prevPosZ;
        this.updated = true;
        net.minecraft.client.renderer.GlStateManager.resetColor();
    }

    public void startUpdateGlList() {
        org.lwjgl.opengl.GL11.glNewList(this.cloudDisplayListId, 4864);
    }

    public void prepareToRender(
            boolean renderFancyValue, int cloudTickCounterValue, float partialTicksValue) {
        if (this.renderFancy != renderFancyValue) {
            this.updated = false;
        }

        this.renderFancy = renderFancyValue;
        this.cloudTickCounter = cloudTickCounterValue;
        this.partialTicks = partialTicksValue;
    }

    public boolean shouldRebuildDisplayList() {
        if (!this.updated) {
            return true;
        }

        if (this.cloudTickCounter >= this.lastBuildCloudTick + 20) {
            return true;
        }

        net.minecraft.entity.Entity entity = this.mc.getRenderViewEntity();
        boolean flag =
                (this.updatePlayerY + entity.getEyeHeight()
                        < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0F);
        boolean flag1 =
                (entity.prevPosY + entity.getEyeHeight()
                        < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0F);
        return flag1 != flag;
    }

    public void reset() {
        this.updated = false;
    }

    public CloudRenderer(MinecraftClient minecraftClient) {
        this.cloudDisplayListId = -1;
        this.lastBuildCloudTick = 0;
        this.updatePlayerX = 0.0;
        this.updatePlayerY = 0.0;
        this.updatePlayerZ = 0.0;
        this.mc = minecraftClient;
        this.cloudDisplayListId = GLAllocation.generateDisplayLists(1);
    }
}
