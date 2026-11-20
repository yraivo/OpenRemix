package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransformsTransformType;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LayerHeldItem implements LayerRenderer {
    private RendererLivingEntity livingEntityRenderer;

    public boolean shouldCombineTextures() {
        return false;
    }

    public LayerHeldItem(RendererLivingEntity aCYValue) {
        this.livingEntityRenderer = aCYValue;
    }

    public void doRenderLayer(
            LivingEntity livingEntity,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6,
            float f7,
            float f8) {
        ItemStack itemStack = livingEntity.getHeldItem();
        if (itemStack != null) {
            GlStateManager.pushMatrix();
            if (this.livingEntityRenderer.getMainModel().isChild) {
                float f = 0.5F;
                GlStateManager.translate(0.0F, 0.625F, 0.0F);
                GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
                GlStateManager.scale(f, f, f);
            }

            ((ModelBiped) this.livingEntityRenderer.getMainModel()).postRenderHiddenArm(0.0625F);
            GlStateManager.translate(-0.0625F, 0.4375F, 0.0625F);
            if (livingEntity instanceof PlayerEntity
                    && ((PlayerEntity) livingEntity).fishEntity != null) {
                itemStack = new ItemStack(Items.fishing_rod, 0);
            }

            Item item = itemStack.getItem();
            MinecraftClient minecraftClient = MinecraftClient.getMinecraft();
            if (item instanceof ItemBlock && Block.getBlockFromItem(item).getRenderType() == 2) {
                GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
                GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                float f1 = 0.375F;
                GlStateManager.scale(-f1, -f1, f1);
            }

            minecraftClient
                    .getItemRenderer()
                    .renderItem(livingEntity, itemStack, ItemCameraTransformsTransformType.THIRD_PERSON);
            GlStateManager.popMatrix();
        }
    }
}
