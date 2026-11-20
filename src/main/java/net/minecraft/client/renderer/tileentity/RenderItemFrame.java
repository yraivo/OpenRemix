package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import optifine.OptiFineConfig;
import optifine.Reflector;
import shadersmod.client.ShadersTex;

import org.lwjgl.opengl.GL11;

public class RenderItemFrame extends Render {

    private static ResourceLocation mapBackgroundTextures;

    private RenderItem itemRenderer;
    private ModelResourceLocation itemFrameModel;
    private ModelResourceLocation mapModel;

    private final MinecraftClient mc = MinecraftClient.getMinecraft();

    public void doRender(
            EntityItemFrame entityItemFrame, double d0, double d1, double d2, float f, float f1) {
        BlockRendererDispatcher blockRendererDispatcher;
        IBakedModel iBakedModel;
        {
            GlStateManager.pushMatrix();
            BlockPos blockPos = entityItemFrame.getHangingPosition();
            double d3 = blockPos.getX() - entityItemFrame.posX + d0;
            TileEntitySpecialRenderer.getLegacyTileRendererState();

            double d4 = blockPos.getY() - entityItemFrame.posY + d1;

            double d5 = blockPos.getZ() - entityItemFrame.posZ + d2;
            GlStateManager.translate(d3 + 0.5, d4 + 0.5, d5 + 0.5);
            GlStateManager.rotate(180.0F - entityItemFrame.rotationYaw, 0.0F, 1.0F, 0.0F);
            this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            blockRendererDispatcher = this.mc.getBlockRendererDispatcher();
            ModelManager modelManager =
                    blockRendererDispatcher.getBlockModelShapes().getModelManager();
            if (entityItemFrame.getDisplayedItem() != null
                    && entityItemFrame.getDisplayedItem().getItem() == Items.filled_map) {
                iBakedModel = modelManager.getModel(this.mapModel);

                
            } else {

                iBakedModel = modelManager.getModel(this.itemFrameModel);
            }
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        blockRendererDispatcher
                .getBlockModelRenderer()
                .renderModelBrightnessColor(iBakedModel, 1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
        GlStateManager.translate(0.0F, 0.0F, 0.4375F);
        this.renderItem(entityItemFrame);
        GlStateManager.popMatrix();
        this.method_cb362faf998abe06d2e2(
                entityItemFrame,
                d0 + entityItemFrame.facingDirection.getFrontOffsetX() * 0.3F,
                d1 - 0.25,
                d2 + entityItemFrame.facingDirection.getFrontOffsetZ() * 0.3F);
    }

    static {
        net.minecraft.client.renderer.tileentity.RenderItemFrame.mapBackgroundTextures =
                new ResourceLocation("textures/map/map_background.png");
    }

    public RenderItemFrame(RenderManager renderManagerValue, RenderItem apqValue) {
        super(renderManagerValue);
        this.itemFrameModel = new ModelResourceLocation("item_frame", "normal");
        this.mapModel = new ModelResourceLocation("item_frame", "map");
        this.itemRenderer = apqValue;
    }

    protected void renderName(Entity entity, double d0, double d1, double d2) {
        this.method_cb362faf998abe06d2e2((EntityItemFrame) entity, d0, d1, d2);
    }

    protected void method_cb362faf998abe06d2e2(
            EntityItemFrame entityItemFrame, double d0, double d1, double d2) {
        TileEntitySpecialRenderer.getLegacyTileRendererState();

        if (MinecraftClient.isGuiEnabled()
                && entityItemFrame.getDisplayedItem() != null
                && entityItemFrame.getDisplayedItem().hasDisplayName()
                && this.renderManager.pointedEntity == entityItemFrame) {
            float f = 1.6F;
            float f1 = 0.016666668F * f;
            double distanceSqToEntity =
                    entityItemFrame.getDistanceSqToEntity(this.renderManager.livingPlayer);
            float f2 = entityItemFrame.isSneaking() ? 32.0F : 64.0F;
            if (distanceSqToEntity < f2 * f2) {
                String text = entityItemFrame.getDisplayedItem().getDisplayName();
                if (entityItemFrame.isSneaking()) {
                    FontRenderer fontRendererFromRenderManager =
                            this.getFontRendererFromRenderManager();
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(
                            (float) d0 + 0.0F,
                            (float) d1 + entityItemFrame.height + 0.5F,
                            (float) d2);
                    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                    GlStateManager.scale(-f1, -f1, f1);
                    GlStateManager.disableLighting();
                    GlStateManager.translate(0.0F, 0.25F / f1, 0.0F);
                    GlStateManager.depthMask(false);
                    GlStateManager.enableBlend();
                    GlStateManager.blendFunc(770, 771);
                    Tessellator instance = Tessellator.getInstance();
                    WorldRenderer worldRenderer = instance.getWorldRenderer();
                    GlStateManager.disableTexture2D();
                    worldRenderer.startDrawingQuads();
                    int i = fontRendererFromRenderManager.getStringWidth(text) / 2;
                    worldRenderer.setVertexColorFromFloats(0.0F, 0.0F, 0.0F, 0.25F);
                    worldRenderer.addVertex(-i - 1, -1.0, 0.0);
                    worldRenderer.addVertex(-i - 1, 8.0, 0.0);
                    worldRenderer.addVertex(i + 1, 8.0, 0.0);
                    worldRenderer.addVertex(i + 1, -1.0, 0.0);
                    instance.draw();
                    GlStateManager.enableTexture2D();
                    GlStateManager.depthMask(true);
                    fontRendererFromRenderManager.drawString(
                            text,
                            -fontRendererFromRenderManager.getStringWidth(text) / 2,
                            0,
                            553648127);
                    GlStateManager.enableLighting();
                    GlStateManager.disableBlend();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.popMatrix();

                    return;
                }

                this.renderLivingLabel(entityItemFrame, text, d0, d1, d2, 64);
            }
        }
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityItemFrame) entity);
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityItemFrame) entity, d0, d1, d2, f, f1);
    }

    private void renderItem(EntityItemFrame entityItemFrame) {
        TileEntitySpecialRenderer.getLegacyTileRendererState();

        ItemStack itemStack = entityItemFrame.getDisplayedItem();

        if (itemStack != null) {
            EntityItem entityItem = new EntityItem(entityItemFrame.world, 0.0, 0.0, 0.0, itemStack);
            Item item = entityItem.getEntityItem().getItem();
            entityItem.getEntityItem().stackSize = 1;
            entityItem.hoverStart = 0.0F;
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            int rotation = entityItemFrame.getRotation();
            if (item instanceof ItemMap) {
                rotation = rotation % 4 * 2;
            }

            GlStateManager.rotate(rotation * 360.0F / 8.0F, 0.0F, 0.0F, 1.0F);
            label39:
            if (!Reflector.postForgeBusEvent(Reflector.Ih, new Object[] {entityItemFrame, this})) {
                if (item instanceof ItemMap) {
                    this.renderManager.renderEngine.bindTexture(
                            net.minecraft.client.renderer.tileentity.RenderItemFrame.mapBackgroundTextures);
                    GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                    float f = 0.0078125F;
                    GlStateManager.scale(f, f, f);
                    GlStateManager.translate(-64.0F, -64.0F, 0.0F);
                    MapData mapData =
                            Items.filled_map.getMapData(
                                    entityItem.getEntityItem(), entityItemFrame.world);
                    GlStateManager.translate(0.0F, 0.0F, -1.0F);
                    if (mapData != null) {
                        this.mc.entityRenderer.getMapItemRenderer().renderMap(mapData, true);
                    }

                    break label39;
                }

                TextureAtlasSprite textureAtlasSprite;
                textureAtlasSprite = null;
                if (item == Items.compass) {
                    {
                        textureAtlasSprite =
                                this.mc
                                        .getTextureMapBlocks()
                                        .getAtlasSprite(TextureCompass.yz);
                        if (OptiFineConfig.isShaders()) {
                            ShadersTex.bindTextureMapForUpdateAndRender(
                                    this.mc.getTextureManager(), TextureMap.locationBlocksTexture);

                            
                        } else {

                            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                        }
                    }

                    if (textureAtlasSprite instanceof TextureCompass) {
                        TextureCompass textureCompass =
                                (TextureCompass) textureAtlasSprite;
                        double aFf = textureCompass.currentAngle;
                        double d1 = textureCompass.angleDelta;
                        textureCompass.currentAngle = 0.0;
                        textureCompass.angleDelta = 0.0;
                        textureCompass.updateCompass(
                                entityItemFrame.world,
                                entityItemFrame.posX,
                                entityItemFrame.posZ,
                                MathHelper.wrapAngleTo180(
                                        180
                                                + entityItemFrame.facingDirection
                                                                .getHorizontalIndex()
                                                        * 90),
                                false,
                                true);
                        textureCompass.currentAngle = aFf;
                        textureCompass.angleDelta = d1;

                        
                    } else {

                        textureAtlasSprite = null;
                    }
                }

                GlStateManager.scale(0.5F, 0.5F, 0.5F);
                if (!this.itemRenderer.shouldRenderItemIn3D(entityItem.getEntityItem())
                        || item instanceof ItemSkull) {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                }

                GlStateManager.pushAttrib();
                RenderHelper.enableStandardItemLighting();
                this.itemRenderer.renderItemWithDefaultTransform(entityItem.getEntityItem());
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popAttrib();
                if (textureAtlasSprite != null && textureAtlasSprite.getFrameCount() > 0) {
                    textureAtlasSprite.updateAnimation();
                }
            }

            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }

        if (OptiFineConfig.isShaders()) {
            ShadersTex.updatingTex = null;
        }
    }

    protected ResourceLocation getEntityTexture(EntityItemFrame entityItemFrame) {
        return null;
    }
}
