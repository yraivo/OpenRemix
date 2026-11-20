package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.renderer.block.model.ItemCameraTransformsTransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;

import optifine.DynamicLights;
import optifine.OptiFineConfig;
import optifine.Reflector;

import org.lwjgl.opengl.GL11;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.render.BlockAnimationMode;
import remix.module.render.BlockAnimationModule;
import remix.module.render.CameraModule;

import shadersmod.client.Shaders;

public class ItemRenderer {
    private RenderItem itemRenderer;

    private float equippedProgress;
    private RenderManager renderManager;
    private MinecraftClient mc;
    private ItemStack itemToRender;

    private static ResourceLocation RES_UNDERWATER_OVERLAY;
    public boolean forceBlockAnimation;
    private float prevEquippedProgress;
    private static ResourceLocation RES_MAP_BACKGROUND;
    private int equippedItemSlot = -1;

    private void setLightMapFromPlayer(AbstractClientPlayer abstractClientPlayer) {
        int combinedLight =
                this.mc.world.getCombinedLight(
                        new BlockPos(
                                abstractClientPlayer.posX,
                                abstractClientPlayer.posY + abstractClientPlayer.getEyeHeight(),
                                abstractClientPlayer.posZ),
                        0);
        if (OptiFineConfig.isDynamicLights()) {
            combinedLight =
                    DynamicLights.getCombinedLight(this.mc.getRenderViewEntity(), combinedLight);
        }

        float f = combinedLight & 65535;
        float f1 = combinedLight >> 16;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
    }

    private void renderWaterOverlayTexture(float f) {
        this.mc
                .getTextureManager()
                .bindTexture(net.minecraft.client.renderer.ItemRenderer.RES_UNDERWATER_OVERLAY);
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        float brightness = this.mc.player.getBrightness(f);
        GlStateManager.color(brightness, brightness, brightness, 0.5F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        float f2 = 4.0F;
        float f3 = -1.0F;
        float f4 = 1.0F;
        float f5 = -1.0F;
        float f6 = 1.0F;
        float f7 = -0.5F;
        float f8 = -this.mc.player.rotationYaw / 64.0F;
        float f9 = this.mc.player.rotationPitch / 64.0F;
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(f3, f5, f7, f2 + f8, f2 + f9);
        worldRenderer.addVertexWithUV(f4, f5, f7, 0.0F + f8, f2 + f9);
        worldRenderer.addVertexWithUV(f4, f6, f7, 0.0F + f8, 0.0F + f9);
        worldRenderer.addVertexWithUV(f3, f6, f7, f2 + f8, 0.0F + f9);
        instance.draw();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
    }

    private void renderRightArm(RenderPlayer renderPlayer) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(54.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(64.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-62.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(0.25F, -0.85F, 0.75F);
        renderPlayer.renderRightArm(this.mc.player);
        GlStateManager.popMatrix();
    }

    private void renderPlayerArms(AbstractClientPlayer abstractClientPlayer) {
        this.mc.getTextureManager().bindTexture(abstractClientPlayer.getLocationSkin());
        Render entityRenderObject = this.renderManager.getEntityRenderObject(this.mc.player);
        RenderPlayer renderPlayer = (RenderPlayer) entityRenderObject;
        if (!abstractClientPlayer.isInvisible()) {
            this.renderRightArm(renderPlayer);
            this.renderLeftArm(renderPlayer);
        }
    }

    // region Static initialization and string tables
    static {
        net.minecraft.client.renderer.ItemRenderer.RES_MAP_BACKGROUND =
                new ResourceLocation("textures/map/map_background.png");
        net.minecraft.client.renderer.ItemRenderer.RES_UNDERWATER_OVERLAY =
                new ResourceLocation("textures/misc/underwater.png");
    }

    // endregion

    private void rotateWithPlayerArm(LocalPlayer localPlayer, float f) {
        float f1 =
                localPlayer.prevRenderArmPitch
                        + (localPlayer.renderArmPitch - localPlayer.prevRenderArmPitch) * f;
        float f2 =
                localPlayer.prevRenderArmYaw
                        + (localPlayer.renderArmYaw - localPlayer.prevRenderArmYaw) * f;
        GlStateManager.rotate((localPlayer.rotationPitch - f1) * 0.1F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((localPlayer.rotationYaw - f2) * 0.1F, 0.0F, 1.0F, 0.0F);
    }

    private boolean isBlockTranslucent(Block block) {
        return block != null && block.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT;
    }

    private void renderLeftArm(RenderPlayer renderPlayer) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(41.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(-0.3F, -1.1F, 0.45F);
        renderPlayer.renderLeftArm(this.mc.player);
        GlStateManager.popMatrix();
    }

    private void renderBlockOverlay(float f10, TextureAtlasSprite textureAtlasSprite) {
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        float f = 0.1F;
        GlStateManager.color(f, f, f, 0.5F);
        GlStateManager.pushMatrix();
        float f1 = -1.0F;
        float f2 = 1.0F;
        float f3 = -1.0F;
        float f4 = 1.0F;
        float f5 = -0.5F;
        float minU = textureAtlasSprite.getMinU();
        float maxU = textureAtlasSprite.getMaxU();
        float minV = textureAtlasSprite.getMinV();
        float maxV = textureAtlasSprite.getMaxV();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(f1, f3, f5, maxU, maxV);
        worldRenderer.addVertexWithUV(f2, f3, f5, minU, maxV);
        worldRenderer.addVertexWithUV(f2, f4, f5, minU, minV);
        worldRenderer.addVertexWithUV(f1, f4, f5, maxU, minV);
        instance.draw();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public ItemRenderer(MinecraftClient aAWValue) {
        this.mc = aAWValue;
        this.renderManager = aAWValue.getRenderManager();
        this.itemRenderer = aAWValue.getRenderItem();
    }

    private void renderFireInFirstPerson(float f10) {
        int k = WorldRenderer.getLegacyRenderFlowMarker();
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        int i = k;
        CameraModule camera = ClientServices.moduleManager.camera;
        if (!camera.isEnabled() || !camera.noBurnEffectSetting.asBoolean()) {
            Tessellator instance = Tessellator.getInstance();
            WorldRenderer worldRenderer = instance.getWorldRenderer();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
            GlStateManager.depthFunc(519);
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            float f = 1.0F;
            TextureAtlasSprite atlasSprite =
                    this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
            int j = 0;

            while (j < 2) {
                GlStateManager.pushMatrix();
                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                float minU = atlasSprite.getMinU();
                float maxU = atlasSprite.getMaxU();
                float minV = atlasSprite.getMinV();
                float maxV = atlasSprite.getMaxV();
                float f5 = (0.0F - f) / 2.0F;
                float f6 = f5 + f;
                float f7 = 0.0F - f / 2.0F;
                float f8 = f7 + f;
                float f9 = -0.5F;
                GlStateManager.translate(-(j * 2 - 1) * 0.24F, -0.3F, 0.0F);
                GlStateManager.rotate((j * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
                worldRenderer.startDrawingQuads();
                worldRenderer.addVertexWithUV(f5, f7, f9, maxU, maxV);
                worldRenderer.addVertexWithUV(f6, f7, f9, minU, maxV);
                worldRenderer.addVertexWithUV(f6, f8, f9, minU, minV);
                worldRenderer.addVertexWithUV(f5, f8, f9, maxU, minV);
                instance.draw();
                GlStateManager.popMatrix();
                j++;
                if (i == 0) {
                    break;
                }
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            GlStateManager.depthFunc(515);
        }
    }

    private void rotateLightingWithView(float f, float f1) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(f, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    private void renderItemMap(
            AbstractClientPlayer abstractClientPlayer, float f, float f1, float f2) {
        float f3 = -0.4F * MathHelper.sin(MathHelper.sqrt(f2) * (float) Math.PI);
        float f4 = 0.2F * MathHelper.sin(MathHelper.sqrt(f2) * (float) Math.PI * 2.0F);
        float f5 = -0.2F * MathHelper.sin(f2 * (float) Math.PI);
        GlStateManager.translate(f3, f4, f5);
        float mapAngleFromPitch = this.getMapAngleFromPitch(f);
        GlStateManager.translate(0.0F, 0.04F, -0.72F);
        GlStateManager.translate(0.0F, f1 * -1.2F, 0.0F);
        GlStateManager.translate(0.0F, mapAngleFromPitch * -0.5F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(mapAngleFromPitch * -85.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
        this.renderPlayerArms(abstractClientPlayer);
        float f7 = MathHelper.sin(f2 * f2 * (float) Math.PI);
        float f8 = MathHelper.sin(MathHelper.sqrt(f2) * (float) Math.PI);
        GlStateManager.rotate(f7 * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f8 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f8 * -80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.38F, 0.38F, 0.38F);
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-1.0F, -1.0F, 0.0F);
        GlStateManager.scale(0.015625F, 0.015625F, 0.015625F);
        this.mc
                .getTextureManager()
                .bindTexture(net.minecraft.client.renderer.ItemRenderer.RES_MAP_BACKGROUND);
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        GL11.glNormal3f(0.0F, 0.0F, -1.0F);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(-7.0, 135.0, 0.0, 0.0, 1.0);
        worldRenderer.addVertexWithUV(135.0, 135.0, 0.0, 1.0, 1.0);
        worldRenderer.addVertexWithUV(135.0, -7.0, 0.0, 1.0, 0.0);
        worldRenderer.addVertexWithUV(-7.0, -7.0, 0.0, 0.0, 0.0);
        instance.draw();
        MapData mapData = Items.filled_map.getMapData(this.itemToRender, this.mc.world);
        if (mapData != null) {
            this.mc.entityRenderer.getMapItemRenderer().renderMap(mapData, false);
        }
    }

    private void applyConsumeTransform(AbstractClientPlayer abstractClientPlayer, float f) {
        float f1 = abstractClientPlayer.getItemInUseCount() - f + 1.0F;
        float f2 = f1 / this.itemToRender.getMaxItemUseDuration();
        float f3 = MathHelper.abs(MathHelper.cos(f1 / 4.0F * (float) Math.PI) * 0.1F);
        if (f2 >= 0.8F) {
            f3 = 0.0F;
        }

        GlStateManager.translate(0.0F, f3, 0.0F);
        float f4 = 1.0F - (float) Math.pow(f2, 27.0);
        GlStateManager.translate(f4 * 0.6F, f4 * -0.5F, f4 * 0.0F);
        GlStateManager.rotate(f4 * 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f4 * 10.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f4 * 30.0F, 0.0F, 0.0F, 1.0F);
    }

    public void updateEquippedItem() {
        this.prevEquippedProgress = this.equippedProgress;
        LocalPlayer player = this.mc.player;
        ItemStack heldItem = player.inventory.getCurrentItem();
        boolean itemChanged;
        if (this.itemToRender != null && heldItem != null) {
            itemChanged = !this.itemToRender.getIsItemStackEqual(heldItem);
            if (itemChanged
                    && Reflector.forgeShouldCauseReequipAnimation.exists()
                    && !Reflector.callBoolean(
                            this.itemToRender.getItem(),
                            Reflector.forgeShouldCauseReequipAnimation,
                            new Object[] {
                                this.itemToRender,
                                heldItem,
                                this.equippedItemSlot != player.inventory.currentItem
                            })) {
                this.itemToRender = heldItem;
                this.equippedItemSlot = player.inventory.currentItem;
                return;
            }
        } else {
            itemChanged = this.itemToRender != null || heldItem != null;
        }

        float targetProgress = itemChanged ? 0.0F : 1.0F;
        this.equippedProgress +=
                MathHelper.clamp(targetProgress - this.equippedProgress, -0.4F, 0.4F);
        if (this.equippedProgress < 0.1F) {
            if (OptiFineConfig.isShaders()) {
                Shaders.shaderHeldItem = heldItem;
            }
            this.itemToRender = heldItem;
            this.equippedItemSlot = player.inventory.currentItem;
        }
    }

    private float getMapAngleFromPitch(float f) {
        float f1 = 1.0F - f / 45.0F + 0.1F;
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        return -MathHelper.cos(f1 * (float) Math.PI) * 0.5F + 0.5F;
    }

    private void applySwingTranslation(float f) {
        float f1 = -0.4F * MathHelper.sin(MathHelper.sqrt(f) * (float) Math.PI);
        float f2 = 0.2F * MathHelper.sin(MathHelper.sqrt(f) * (float) Math.PI * 2.0F);
        float f3 = -0.2F * MathHelper.sin(f * (float) Math.PI);
        GlStateManager.translate(f1, f2, f3);
    }

    private void applyBlockingTransform() {
        GlStateManager.translate(-0.5F, 0.2F, 0.0F);
        GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
    }

    public void renderItem(
            LivingEntity livingEntity,
            ItemStack itemStack,
            ItemCameraTransformsTransformType itemCameraTransformsTransformType) {
        if (itemStack != null) {
            Item item = itemStack.getItem();
            Block block = Block.getBlockFromItem(item);
            GlStateManager.pushMatrix();
            RenderItem aATValue = this.itemRenderer;
            if (aATValue.shouldRenderItemIn3D(itemStack)) {
                GlStateManager.scale(2.0F, 2.0F, 2.0F);
                if (this.isBlockTranslucent(block)) {
                    GlStateManager.depthMask(false);
                }
            }

            aATValue.renderItemModelForEntity(
                    itemStack, livingEntity, itemCameraTransformsTransformType);
            if (this.isBlockTranslucent(block)) {
                GlStateManager.depthMask(true);
            }

            GlStateManager.popMatrix();
        }
    }

    public void renderItemInFirstPerson(float partialTicks) {
        float equipProgress =
                1.0F
                        - (this.prevEquippedProgress
                                + (this.equippedProgress - this.prevEquippedProgress)
                                        * partialTicks);
        boolean exclusiveItemActions = WorldRenderer.getLegacyRenderFlowMarker() != 0;
        LocalPlayer player = this.mc.player;
        float swingProgress = player.getSwingProgress(partialTicks);
        float pitch =
                player.prevRotationPitch
                        + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
        float yaw =
                player.prevRotationYaw
                        + (player.rotationYaw - player.prevRotationYaw) * partialTicks;
        this.rotateLightingWithView(pitch, yaw);
        this.setLightMapFromPlayer(player);
        this.rotateWithPlayerArm(player, partialTicks);
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();

        ItemStack heldStack = this.itemToRender;
        if (heldStack != null) {
            this.transformHeldItem(
                    player,
                    heldStack,
                    partialTicks,
                    pitch,
                    equipProgress,
                    swingProgress,
                    exclusiveItemActions);
            this.renderItem(player, heldStack, ItemCameraTransformsTransformType.FIRST_PERSON);
        }
        if ((heldStack == null || !exclusiveItemActions) && !player.isInvisible()) {
            this.renderEmptyHand(player, equipProgress, swingProgress);
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
    }

    private void transformHeldItem(
            LocalPlayer player,
            ItemStack stack,
            float partialTicks,
            float pitch,
            float equipProgress,
            float swingProgress,
            boolean exclusiveItemActions) {
        Item item = stack.getItem();
        if (item instanceof ItemMap) {
            this.renderItemMap(player, pitch, equipProgress, swingProgress);
            if (exclusiveItemActions) {
                return;
            }
        }

        if (player.getItemInUseCount() > 0 || this.forceBlockAnimation) {
            EnumAction action = stack.getItemUseAction();
            if (this.forceBlockAnimation && item instanceof SwordItem) {
                action = EnumAction.BLOCK;
            }
            this.applyItemUseTransform(
                    player,
                    action,
                    partialTicks,
                    equipProgress,
                    swingProgress,
                    exclusiveItemActions);
            if (exclusiveItemActions) {
                return;
            }
        }

        this.applySwingTranslation(swingProgress);
        this.transformFirstPersonItem(equipProgress, swingProgress);
    }

    private void applyItemUseTransform(
            LocalPlayer player,
            EnumAction action,
            float partialTicks,
            float equipProgress,
            float swingProgress,
            boolean exclusiveItemActions) {
        // The zero marker intentionally continues through later stages of the recovered switch.
        switch (action) {
            case NONE: // NONE
                this.transformFirstPersonItem(equipProgress, 0.0F);
                if (exclusiveItemActions) {
                    return;
                }
            case EAT: // EAT
            case DRINK: // DRINK
                this.applyConsumeTransform(player, partialTicks);
                this.transformFirstPersonItem(equipProgress, 0.0F);
                if (exclusiveItemActions) {
                    return;
                }
            case BLOCK: // BLOCK
                this.applyBlockAnimation(equipProgress, swingProgress, exclusiveItemActions);
                if (exclusiveItemActions) {
                    return;
                }
            case BOW: // BOW
                this.transformFirstPersonItem(equipProgress, 0.0F);
                this.applyBowTransform(partialTicks, player);
                return;
            default:
        }
    }

    private void applyBlockAnimation(
            float equipProgress, float swingProgress, boolean exclusiveItemActions) {
        float swingCurve = MathHelper.sin(MathHelper.sqrt(swingProgress) * 3.0F);
        RemixClient.INSTANCE.getClientServices();
        BlockAnimationModule animation = ClientServices.moduleManager.blockAnimation;
        GL11.glTranslated(
                animation.xSetting.asDouble(),
                animation.ySetting.asDouble(),
                animation.zSetting.asDouble());
        BlockAnimationMode mode = (BlockAnimationMode) animation.modeSetting.getValue();

        switch (mode) {
            case ONEDOTSEVEN: // ONEDOTSEVEN
                this.transformFirstPersonItem(-0.1F, swingProgress);
                this.applyBlockingTransform();
                if (exclusiveItemActions) {
                    return;
                }
            case SLIDE: // SLIDE
                this.transformFirstPersonItem(equipProgress, 0.0F);
                this.applyBlockingTransform();
                GlStateManager.rotate(-swingCurve * 35.0F, 4.0F, -swingCurve * -0.5F, 8.5F);
                if (exclusiveItemActions) {
                    return;
                }
            case EXHIBITION: // EXHIBITION
                this.transformFirstPersonItem(equipProgress / 3.0F, 0.0F);
                GlStateManager.rotate(-swingCurve * 40.0F / 2.0F, swingCurve / 2.0F, -0.0F, 4.0F);
                GlStateManager.rotate(-swingCurve * 30.0F, 1.0F, swingCurve / 2.0F, -0.0F);
                this.applyBlockingTransform();
                if (exclusiveItemActions) {
                    return;
                }
            case MADOORY: // MADOORY
                this.transformFirstPersonItem(equipProgress, swingProgress);
                this.applyBlockingTransform();
                if (exclusiveItemActions) {
                    return;
                }
            case JURI: // JURI
                GlStateManager.rotate(swingCurve * 15.0F, 4.0F, swingCurve * -0.5F, 8.5F);
                this.transformFirstPersonItem(equipProgress, 0.0F);
                this.applyBlockingTransform();
                if (exclusiveItemActions) {
                    return;
                }
            case NONE: // NONE
                this.transformFirstPersonItem(equipProgress, 0.0F);
                this.applyBlockingTransform();
                return;
            default:
        }
    }

    public void resetEquippedProgress() {
        this.equippedProgress = 0.0F;
    }

    private void renderEmptyHand(AbstractClientPlayer abstractClientPlayer, float f, float f1) {
        float f2 = -0.3F * MathHelper.sin(MathHelper.sqrt(f1) * (float) Math.PI);
        float f3 = 0.4F * MathHelper.sin(MathHelper.sqrt(f1) * (float) Math.PI * 2.0F);
        float f4 = -0.4F * MathHelper.sin(f1 * (float) Math.PI);
        GlStateManager.translate(f2, f3, f4);
        GlStateManager.translate(0.64000005F, -0.6F, -0.71999997F);
        GlStateManager.translate(0.0F, f * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f5 = MathHelper.sin(f1 * f1 * (float) Math.PI);
        float f6 = MathHelper.sin(MathHelper.sqrt(f1) * (float) Math.PI);
        GlStateManager.rotate(f6 * 70.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f5 * -20.0F, 0.0F, 0.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(abstractClientPlayer.getLocationSkin());
        GlStateManager.translate(-1.0F, 3.6F, 3.5F);
        GlStateManager.rotate(120.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.translate(5.6F, 0.0F, 0.0F);
        Render entityRenderObject = this.renderManager.getEntityRenderObject(this.mc.player);
        RenderPlayer renderPlayer = (RenderPlayer) entityRenderObject;
        renderPlayer.renderRightArm(this.mc.player);
    }

    private void transformFirstPersonItem(float f, float f1) {
        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, f * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f2 = MathHelper.sin(f1 * f1 * (float) Math.PI);
        float f3 = MathHelper.sin(MathHelper.sqrt(f1) * (float) Math.PI);
        GlStateManager.rotate(f2 * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f3 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f3 * -80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }

    private void applyBowTransform(float f, AbstractClientPlayer abstractClientPlayer) {
        GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-0.9F, 0.2F, 0.0F);
        float f1 =
                this.itemToRender.getMaxItemUseDuration()
                        - (abstractClientPlayer.getItemInUseCount() - f + 1.0F);
        float f2 = f1 / 20.0F;
        f2 = (f2 * f2 + f2 * 2.0F) / 3.0F;
        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        if (f2 > 0.1F) {
            float f3 = MathHelper.sin((f1 - 0.1F) * 1.3F);
            float f4 = f2 - 0.1F;
            float f5 = f3 * f4;
            GlStateManager.translate(f5 * 0.0F, f5 * 0.01F, f5 * 0.0F);
        }

        GlStateManager.translate(f2 * 0.0F, f2 * 0.0F, f2 * 0.1F);
        GlStateManager.scale(1.0F, 1.0F, 1.0F + f2 * 0.2F);
    }

    public void renderOverlays(float f) {
        int k = WorldRenderer.getLegacyRenderFlowMarker();
        GlStateManager.disableAlpha();
        ClientWorld world = this.mc.world;
        int i = k;
        LocalPlayer player = this.mc.player;
        if (player.isEntityInsideOpaqueBlock()) {
            BlockPos blockPos = new BlockPos(player);
            BlockState blockState = world.getBlockState(blockPos);
            int j = 0;

            while (j < 8) {
                double d0 = player.posX + ((j >> 0) % 2 - 0.5F) * player.width * 0.8F;
                double d1 = player.posY + ((j >> 1) % 2 - 0.5F) * 0.1F;
                double d2 = player.posZ + ((j >> 2) % 2 - 0.5F) * player.width * 0.8F;
                blockPos = new BlockPos(d0, d1 + player.getEyeHeight(), d2);
                BlockState blockStateValue = world.getBlockState(blockPos);
                if (blockStateValue.getBlock().isVisuallyOpaque()) {
                    blockState = blockStateValue;
                }

                j++;
                if (i == 0) {
                    break;
                }
            }

            if (blockState.getBlock().getRenderType() != -1) {
                Object fieldValue = Reflector.getFieldValue(Reflector.Id);
                if (!Reflector.callBoolean(
                        Reflector.IN, new Object[] {player, f, fieldValue, blockState, blockPos})) {
                    this.renderBlockOverlay(
                            f,
                            this.mc
                                    .getBlockRendererDispatcher()
                                    .getBlockModelShapes()
                                    .getTexture(blockState));
                }
            }
        }

        if (!player.isSpectator()) {
            if (player.isInsideOfMaterial(Material.water)
                    && !Reflector.callBoolean(Reflector.IM, new Object[] {player, f})) {
                this.renderWaterOverlayTexture(f);
            }

            if (player.isBurning()
                    && !Reflector.callBoolean(Reflector.Gs, new Object[] {player, f})) {
                this.renderFireInFirstPerson(f);
            }
        }

        GlStateManager.enableAlpha();
    }

    public void resetEquippedProgressAfterBlockUse() {
        this.equippedProgress = 0.0F;
    }
}
