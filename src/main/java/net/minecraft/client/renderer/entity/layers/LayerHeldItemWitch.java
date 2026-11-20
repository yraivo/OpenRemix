package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransformsTransformType;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LayerHeldItemWitch implements LayerRenderer {
    private RenderWitch witchRenderer;

    public void method_45d91e2356e32f307bba(
            EntityWitch entityWitch,
            float f5,
            float f6,
            float f7,
            float f8,
            float f9,
            float f10,
            float f11) {
        int[] aint1 = LayerArmorBase.method_650532ba484532b3efb0();
        ItemStack itemStack = entityWitch.getHeldItem();
        int[] aint = aint1;
        if (itemStack != null) {
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            GlStateManager.pushMatrix();
            if (this.witchRenderer.getMainModel().isChild) {
                GlStateManager.translate(0.0F, 0.625F, 0.0F);
                GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
                float f = 0.5F;
                GlStateManager.scale(f, f, f);
            }

            MinecraftClient minecraftClient;
            label38:
            {
                ((ModelWitch) this.witchRenderer.getMainModel()).villagerNose.postRender(0.0625F);
                GlStateManager.translate(-0.0625F, 0.53125F, 0.21875F);
                Item item = itemStack.getItem();
                minecraftClient = MinecraftClient.getMinecraft();
                if (item instanceof ItemBlock
                        && minecraftClient
                                .getBlockRendererDispatcher()
                                .isRenderTypeChest(
                                        Block.getBlockFromItem(item), itemStack.getItemDamage())) {
                    GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
                    GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                    float f1 = 0.375F;
                    GlStateManager.scale(f1, -f1, f1);
                    if (aint == null) {
                        break label38;
                    }
                }

                if (item == Items.bow) {
                    GlStateManager.translate(0.0F, 0.125F, 0.3125F);
                    GlStateManager.rotate(-20.0F, 0.0F, 1.0F, 0.0F);
                    float f2 = 0.625F;
                    GlStateManager.scale(f2, -f2, f2);
                    GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                    if (aint == null) {
                        break label38;
                    }
                }

                if (item.isFull3D()) {
                    if (item.shouldRotateAroundWhenRendering()) {
                        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                        GlStateManager.translate(0.0F, -0.125F, 0.0F);
                    }

                    this.witchRenderer.transformHeldFull3DItemLayer();
                    float f3 = 0.625F;
                    GlStateManager.scale(f3, -f3, f3);
                    GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                    if (aint == null) {
                        break label38;
                    }
                }

                GlStateManager.translate(0.25F, 0.1875F, -0.1875F);
                float f4 = 0.375F;
                GlStateManager.scale(f4, f4, f4);
                GlStateManager.rotate(60.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(20.0F, 0.0F, 0.0F, 1.0F);
            }

            GlStateManager.rotate(-15.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(40.0F, 0.0F, 0.0F, 1.0F);
            minecraftClient
                    .getItemRenderer()
                    .renderItem(entityWitch, itemStack, ItemCameraTransformsTransformType.THIRD_PERSON);
            GlStateManager.popMatrix();
        }
    }

    public LayerHeldItemWitch(RenderWitch aDWValue) {
        this.witchRenderer = aDWValue;
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
        this.method_45d91e2356e32f307bba((EntityWitch) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
