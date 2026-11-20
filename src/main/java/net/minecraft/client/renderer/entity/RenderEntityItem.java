package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class RenderEntityItem extends Render {
    private Random aBQ = new Random();
    private RenderItem itemRenderer;

    protected ResourceLocation method_47bf2d4174017966ed9a(EntityItem entityItem) {
        return TextureMap.locationBlocksTexture;
    }

    private int method_c86984b387ad5389778f(ItemStack itemStack) {
        Render.getLegacyEntityRenderState();

        byte b0 = 1;

        if (itemStack.stackSize > 48) {
            b0 = 5;

            return b0;
        }

        if (itemStack.stackSize > 32) {
            b0 = 4;

            return b0;
        }

        if (itemStack.stackSize > 16) {
            b0 = 3;

            return b0;
        }

        if (itemStack.stackSize > 1) {
            b0 = 2;
        }

        return b0;
    }

    private int method_4422e1356aafb2e42de8(
            EntityItem entityItem,
            double d0,
            double d1,
            double d2,
            float f,
            IBakedModel iBakedModel) {
        ItemStack itemStack = entityItem.getEntityItem();
        Item item = itemStack.getItem();
        if (item == null) {
            return 0;
        }

        boolean ambientOcclusionEnabled = iBakedModel.isGui3d();
        int i = this.method_c86984b387ad5389778f(itemStack);

        float f2 =
                MathHelper.sin((entityItem.getAge() + f) / 10.0F + entityItem.hoverStart) * 0.1F
                        + 0.1F;
        GlStateManager.translate((float) d0, (float) d1 + f2 + 0.25F, (float) d2);
        if (ambientOcclusionEnabled
                || this.renderManager.options != null && this.renderManager.options.fancyGraphics) {
            float f3 =
                    ((entityItem.getAge() + f) / 20.0F + entityItem.hoverStart)
                            * (180.0F / (float) Math.PI);
            GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
        }

        if (!ambientOcclusionEnabled) {
            float f6 = -0.0F * (i - 1) * 0.5F;
            float f4 = -0.0F * (i - 1) * 0.5F;
            float f5 = -0.046875F * (i - 1) * 0.5F;
            GlStateManager.translate(f6, f4, f5);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        return i;
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_47bf2d4174017966ed9a((EntityItem) entity);
    }

    public RenderEntityItem(RenderManager renderManagerValue, RenderItem aBRValue) {
        super(renderManagerValue);
        this.itemRenderer = aBRValue;
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.renderItemPhysics((EntityItem) entity, d0, d1, d2, f, f1);
    }

    public void renderItemPhysics(
            EntityItem entityItem, double d0, double d1, double d2, float f, float f1) {
        Render.getLegacyEntityRenderState();

        ItemStack itemStack = entityItem.getEntityItem();

        this.aBQ.setSeed(187L);
        byte b0 = 0;
        if (this.bindEntityTexture(entityItem)) {
            this.renderManager
                    .renderEngine
                    .getTexture(this.method_47bf2d4174017966ed9a(entityItem))
                    .setBlurMipmap(false, false);
            b0 = 1;
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        IBakedModel itemModel = this.itemRenderer.getItemModelMesher().getItemModel(itemStack);
        int i = this.method_4422e1356aafb2e42de8(entityItem, d0, d1, d2, f1, itemModel);
        int j = 0;

        while (j < i) {
            {
                if (itemModel.isGui3d()) {
                    GlStateManager.pushMatrix();
                    if (j > 0) {
                        float f2 = (this.aBQ.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        float f3 = (this.aBQ.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        float f4 = (this.aBQ.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        GlStateManager.translate(f2, f3, f4);
                    }

                    GlStateManager.scale(0.5F, 0.5F, 0.5F);
                    this.itemRenderer.renderBakedItemWithEffects(itemStack, itemModel);
                    GlStateManager.popMatrix();

                    
                } else {

                    this.itemRenderer.renderBakedItemWithEffects(itemStack, itemModel);
                    GlStateManager.translate(0.0F, 0.0F, 0.046875F);
                }
            }

            j++;
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.bindEntityTexture(entityItem);
        if (b0 != 0) {
            this.renderManager
                    .renderEngine
                    .getTexture(this.method_47bf2d4174017966ed9a(entityItem))
                    .restoreLastBlurMipmap();
        }

        super.doRender(entityItem, d0, d1, d2, f, f1);
    }
}
