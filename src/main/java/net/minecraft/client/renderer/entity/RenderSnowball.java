package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderSnowball extends Render {
    private RenderItem aBg;
    protected Item aBh;

    public RenderSnowball(RenderManager renderManagerValue, Item aBhValue, RenderItem aBgValue) {
        super(renderManagerValue);
        this.aBh = aBhValue;
        this.aBg = aBgValue;
    }

    public ItemStack method_563ca4ec0415e2d82e9d(Entity entity) {
        return new ItemStack(this.aBh, 1, 0);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return TextureMap.locationBlocksTexture;
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) d0, (float) d1, (float) d2);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        this.bindTexture(TextureMap.locationBlocksTexture);
        this.aBg.renderItemWithDefaultTransform(this.method_563ca4ec0415e2d82e9d(entity));
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, d0, d1, d2, f, f1);
    }
}
