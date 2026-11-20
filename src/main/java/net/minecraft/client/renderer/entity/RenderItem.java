package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirtDirtType;
import net.minecraft.block.BlockDoublePlantEnumPlantType;
import net.minecraft.block.BlockFlowerEnumFlowerType;
import net.minecraft.block.BlockHugeMushroomEnumType;
import net.minecraft.block.BlockPlanksEnumType;
import net.minecraft.block.BlockPrismarineEnumType;
import net.minecraft.block.BlockQuartzEnumType;
import net.minecraft.block.BlockRedSandstoneEnumType;
import net.minecraft.block.BlockSandEnumType;
import net.minecraft.block.BlockSandStoneEnumType;
import net.minecraft.block.BlockSilverfishEnumType;
import net.minecraft.block.BlockStoneBrickEnumType;
import net.minecraft.block.BlockStoneEnumType;
import net.minecraft.block.BlockStoneSlabEnumType;
import net.minecraft.block.BlockStoneSlabNewEnumType;
import net.minecraft.block.BlockTallGrassEnumType;
import net.minecraft.block.BlockWallEnumType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransformsTransformType;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFoodFishType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3i;

import optifine.CustomColors;
import optifine.CustomItems;
import optifine.OptiFineConfig;
import optifine.Reflector;

import java.util.List;

public class RenderItem implements IResourceManagerReloadListener {
    private TextureManager textureManager;
    public static float itemScaleOffsetY;

    public static float itemScaleOffsetX;
    public static float itemTranslationOffsetX;
    private ModelResourceLocation modelLocation;
    private ItemModelMesher itemModelMesher;
    public static float itemRotationOffsetY;
    private static ResourceLocation RES_ITEM_GLINT;

    public static float itemRotationOffsetX;
    public float zLevel;
    public static float itemTranslationOffsetY;
    public static float itemTranslationOffsetZ;
    public static float itemScaleOffsetZ;
    public static float itemRotationOffsetZ;
    private boolean field_175058_l;

    protected void renderItemModelTransform(
            ItemStack itemStack,
            IBakedModel iBakedModel,
            ItemCameraTransformsTransformType itemCameraTransformsTransformType) {
        this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
        this.textureManager
                .getTexture(TextureMap.locationBlocksTexture)
                .setBlurMipmap(false, false);
        Render.getLegacyEntityRenderState();

        this.preTransform(itemStack);
        GlStateManager.enableRescaleNormal();

        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        if (Reflector.He.exists()) {
            iBakedModel =
                    (IBakedModel)
                            Reflector.call(
                                    Reflector.He,
                                    new Object[] {iBakedModel, itemCameraTransformsTransformType});

        } else {
            switch (itemCameraTransformsTransformType) {
                case NONE:
                default:
                    break;

                case THIRD_PERSON:
                    this.applyModelCameraTransform(
                            iBakedModel.getItemCameraTransforms().thirdPerson);

                    break;

                case FIRST_PERSON:
                    this.applyModelCameraTransform(
                            iBakedModel.getItemCameraTransforms().firstPerson);

                    break;

                case HEAD:
                    this.applyModelCameraTransform(iBakedModel.getItemCameraTransforms().head);

                    break;

                case GUI:
                    this.applyModelCameraTransform(iBakedModel.getItemCameraTransforms().gui);
            }
        }

        this.renderBakedItemWithEffects(itemStack, iBakedModel);
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
        this.textureManager.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
    }

    static {
        net.minecraft.client.renderer.entity.RenderItem.RES_ITEM_GLINT =
                new ResourceLocation("textures/misc/enchanted_item_glint.png");
        net.minecraft.client.renderer.entity.RenderItem.itemTranslationOffsetX = 0.0F;
        net.minecraft.client.renderer.entity.RenderItem.itemTranslationOffsetY = 0.0F;
        net.minecraft.client.renderer.entity.RenderItem.itemTranslationOffsetZ = 0.0F;
        net.minecraft.client.renderer.entity.RenderItem.itemRotationOffsetX = 0.0F;
        net.minecraft.client.renderer.entity.RenderItem.itemRotationOffsetY = 0.0F;
        net.minecraft.client.renderer.entity.RenderItem.itemRotationOffsetZ = 0.0F;
        net.minecraft.client.renderer.entity.RenderItem.itemScaleOffsetX = 0.0F;
        net.minecraft.client.renderer.entity.RenderItem.itemScaleOffsetY = 0.0F;
        net.minecraft.client.renderer.entity.RenderItem.itemScaleOffsetZ = 0.0F;
    }

    protected void applyModelCameraTransform(ItemTransformVec3f itemTransformVec3f) {
        applyItemTransformWithOffsets(itemTransformVec3f);
    }

    private void drawDurabilityBarQuad(
            WorldRenderer worldRenderer, int i, int j, int k, int l, int i1) {
        worldRenderer.startDrawingQuads();
        worldRenderer.setOpaqueVertexColorFromRgb(i1);
        worldRenderer.addVertex(i, j, 0.0);
        worldRenderer.addVertex(i, j + l, 0.0);
        worldRenderer.addVertex(i + k, j + l, 0.0);
        worldRenderer.addVertex(i + k, j, 0.0);
        Tessellator.getInstance().draw();
    }

    public void renderItemOverlays(FontRenderer fontRenderer, ItemStack itemStack, int i, int j) {
        this.renderItemOverlayIntoGUI(fontRenderer, itemStack, i, j, null);
    }

    public static void forgeHooksClient_putQuadColor(
            WorldRenderer worldRenderer, BakedQuad bakedQuad, int i) {
        Render.getLegacyEntityRenderState();

        float f = i & 0xFF;
        float f1 = i >>> 8 & 0xFF;
        float f2 = i >>> 16 & 0xFF;
        float f3 = i >>> 24 & 0xFF;

        int[] aint = bakedQuad.getVertexData();
        int j = aint.length / 4;
        int k = 0;

        while (k < 4) {
            int l = aint[3 + j * k];
            float f4 = l & 0xFF;
            float f5 = l >>> 8 & 0xFF;
            float f6 = l >>> 16 & 0xFF;
            float f7 = l >>> 24 & 0xFF;
            int i1 = Math.min(255, (int) (f * f4 / 255.0F));
            int j1 = Math.min(255, (int) (f1 * f5 / 255.0F));
            int k1 = Math.min(255, (int) (f2 * f6 / 255.0F));
            int l1 = Math.min(255, (int) (f3 * f7 / 255.0F));
            worldRenderer.putColorRGBA(worldRenderer.getColorIndex(4 - k), i1, j1, k1, l1);
            k++;
        }
    }

    public void renderItemIntoGUI(ItemStack itemStack, int i, int j) {
        IBakedModel itemModel = this.itemModelMesher.getItemModel(itemStack);
        GlStateManager.pushMatrix();
        this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
        this.textureManager
                .getTexture(TextureMap.locationBlocksTexture)
                .setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.setupGuiTransform(i, j, itemModel.isGui3d());
        this.applyModelCameraTransform(itemModel.getItemCameraTransforms().gui);
        this.renderItem(itemStack, itemModel);
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
        this.textureManager.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
    }

    private void registerBlock(Block block, String text) {
        this.registerBlock(block, 0, text);
    }

    public void renderItemAndEffectIntoGUI(ItemStack itemStack, int i, int j) {
        if (itemStack != null) {
            this.zLevel += 50.0F;

            try {
                this.renderItemModelIntoGUI(itemStack, i, j);
            } catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Rendering item");
                CrashReportCategory crashReportCategory =
                        crashReport.makeCategory("Item being rendered");
                crashReportCategory.addCrashSectionCallable(
                        "Item Type", () -> String.valueOf(itemStack.getItem()));
                crashReportCategory.addCrashSectionCallable(
                        "Item Aux", () -> String.valueOf(itemStack.getItemDamage()));
                crashReportCategory.addCrashSectionCallable(
                        "Item NBT", () -> String.valueOf(itemStack.getTag()));
                crashReportCategory.addCrashSectionCallable(
                        "Item Foil", () -> String.valueOf(itemStack.hasEffect()));
                throw new ReportedException(crashReport);
            }

            this.zLevel -= 50.0F;
        }
    }

    public RenderItem(TextureManager textureManager, ModelManager modelManager) {
        super();
        Render.getLegacyEntityRenderState();

        this.field_175058_l = true;

        this.modelLocation = null;
        this.textureManager = textureManager;
        OptiFineConfig.method_256b565648be898fe3d1(modelManager);
        if (Reflector.Iw.exists()) {
            this.itemModelMesher =
                    (ItemModelMesher)
                            Reflector.newInstance(Reflector.Iw, new Object[] {modelManager});

        } else {
            this.itemModelMesher = new ItemModelMesher(modelManager);
        }

        this.registerItems();
    }

    private void renderQuads(WorldRenderer worldRenderer, List list, int i, ItemStack itemStack) {
        Render.getLegacyEntityRenderState();

        int j = i == -1 && itemStack != null ? 1 : 0;

        for (java.util.Iterator<?> __recoveryIterator171_27772 = (list).iterator();
                (__recoveryIterator171_27772).hasNext(); ) {
            BakedQuad bakedQuad = (BakedQuad) (__recoveryIterator171_27772).next();
            int k = i;
            if (j != 0 && bakedQuad.hasTintIndex()) {
                k = itemStack.getItem().getColorFromItemStack(itemStack, bakedQuad.getTintIndex());
                if (OptiFineConfig.isCustomColors()) {
                    k = CustomColors.getColorFromItemStack(itemStack, bakedQuad.getTintIndex(), k);
                }

                if (EntityRenderer.anaglyphEnable) {
                    k = TextureUtil.anaglyphColor(k);
                }

                k |= -16777216;
            }

            this.renderQuad(worldRenderer, bakedQuad, k);
        }
    }

    public static void applyItemTransformWithOffsets(ItemTransformVec3f itemTransformVec3f) {
        if (itemTransformVec3f != ItemTransformVec3f.DEFAULT) {
            GlStateManager.translate(
                    itemTransformVec3f.translation.x
                            + net.minecraft.client.renderer.entity.RenderItem
                                    .itemTranslationOffsetX,
                    itemTransformVec3f.translation.y
                            + net.minecraft.client.renderer.entity.RenderItem
                                    .itemTranslationOffsetY,
                    itemTransformVec3f.translation.z
                            + net.minecraft.client.renderer.entity.RenderItem
                                    .itemTranslationOffsetZ);
            GlStateManager.rotate(
                    itemTransformVec3f.rotation.y
                            + net.minecraft.client.renderer.entity.RenderItem.itemRotationOffsetY,
                    0.0F,
                    1.0F,
                    0.0F);
            GlStateManager.rotate(
                    itemTransformVec3f.rotation.x
                            + net.minecraft.client.renderer.entity.RenderItem.itemRotationOffsetX,
                    1.0F,
                    0.0F,
                    0.0F);
            GlStateManager.rotate(
                    itemTransformVec3f.rotation.z
                            + net.minecraft.client.renderer.entity.RenderItem.itemRotationOffsetZ,
                    0.0F,
                    0.0F,
                    1.0F);
            GlStateManager.scale(
                    itemTransformVec3f.scale.x
                            + net.minecraft.client.renderer.entity.RenderItem.itemScaleOffsetX,
                    itemTransformVec3f.scale.y
                            + net.minecraft.client.renderer.entity.RenderItem.itemScaleOffsetY,
                    itemTransformVec3f.scale.z
                            + net.minecraft.client.renderer.entity.RenderItem.itemScaleOffsetZ);
        }
    }

    private void preTransform(ItemStack itemStack) {
        IBakedModel itemModel = this.itemModelMesher.getItemModel(itemStack);
        Item item = itemStack.getItem();
        if (item != null) {
            boolean ambientOcclusionEnabled = itemModel.isGui3d();
            if (!ambientOcclusionEnabled) {
                GlStateManager.scale(2.0F, 2.0F, 2.0F);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private void registerItem(Item item, String text) {
        this.registerItem(item, 0, text);
    }

    private void renderQuad(WorldRenderer vertices, BakedQuad quad, int packedColor) {
        Render.getLegacyEntityRenderState();
        if (vertices.isMultiTexture()) {
            vertices.addVertexData(quad.getVertexDataSingle());
            vertices.putSprite(quad.getSprite());
        } else {
            vertices.addVertexData(quad.getVertexData());
        }

        if (Reflector.GV.exists() && Reflector.GV.isInstance(quad)) {
            forgeHooksClient_putQuadColor(vertices, quad, packedColor);
        } else {
            vertices.putPositionFromPacked(packedColor);
        }
        this.putQuadNormal(vertices, quad);
    }

    private void renderModel(IBakedModel iBakedModel, ItemStack itemStack) {
        this.renderModel(iBakedModel, -1, itemStack);
    }

    private void renderModel(IBakedModel iBakedModel, int i, ItemStack itemStack) {
        Render.getLegacyEntityRenderState();

        Tessellator instance = Tessellator.getInstance();

        WorldRenderer worldRenderer = instance.getWorldRenderer();
        boolean flag = MinecraftClient.getMinecraft().getTextureMapBlocks().aR();
        int j = OptiFineConfig.isMultiTexture() && flag ? 1 : 0;
        if (j != 0) {
            worldRenderer.setBlockLayer(EnumWorldBlockLayer.SOLID);
        }

        worldRenderer.startDrawingQuads();
        worldRenderer.setVertexFormat(DefaultVertexFormats.ITEM);

        for (Facing facing : Facing.VALUES) {
            this.renderQuads(worldRenderer, iBakedModel.getFaceQuads(facing), i, itemStack);
        }

        this.renderQuads(worldRenderer, iBakedModel.getGeneralQuads(), i, itemStack);
        instance.draw();
        if (j != 0) {
            worldRenderer.setBlockLayer(null);
            GlStateManager.bindCurrentTexture();
        }
    }

    public void renderItemOverlayIntoGUI(
            FontRenderer fontRenderer, ItemStack itemStack, int i, int j, String text) {
        if (itemStack != null) {
            int k = itemStack.stackSize;
            if (k != 1 || text != null) {
                String textValue = text == null ? String.valueOf(k) : text;
                if (text == null && k < 1) {
                    textValue = EnumChatFormatting.RED + String.valueOf(k);
                }

                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableBlend();
                fontRenderer.drawStringWithShadow(
                        textValue,
                        i + 19 - 2 - fontRenderer.getStringWidth(textValue),
                        j + 6 + 3,
                        16777215);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }

            boolean itemDamaged = itemStack.isItemDamaged();
            if (Reflector.ForgeItem_showDurabilityBar.exists()) {
                itemDamaged =
                        Reflector.callBoolean(
                                itemStack.getItem(),
                                Reflector.ForgeItem_showDurabilityBar,
                                new Object[] {itemStack});
            }

            if (itemDamaged) {
                int l =
                        (int)
                                Math.round(
                                        13.0
                                                - itemStack.getMetadata()
                                                        * 13.0
                                                        / itemStack.getMaxDamage());
                int i1 =
                        (int)
                                Math.round(
                                        255.0
                                                - itemStack.getMetadata()
                                                        * 255.0
                                                        / itemStack.getMaxDamage());
                if (Reflector.IU.exists()) {
                    double d0 =
                            Reflector.callDouble(
                                    itemStack.getItem(), Reflector.IU, new Object[] {itemStack});
                    l = (int) Math.round(13.0 - d0 * 13.0);
                    i1 = (int) Math.round(255.0 - d0 * 255.0);
                }

                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                Tessellator instance = Tessellator.getInstance();
                WorldRenderer worldRenderer = instance.getWorldRenderer();
                int j1 = 255 - i1 << 16 | i1 << 8;
                int k1 = (255 - i1) / 4 << 16 | 16128;
                this.drawDurabilityBarQuad(worldRenderer, i + 2, j + 13, 13, 2, 0);
                this.drawDurabilityBarQuad(worldRenderer, i + 2, j + 13, 12, 1, k1);
                this.drawDurabilityBarQuad(worldRenderer, i + 2, j + 13, l, 1, j1);
                if (!Reflector.FR.exists()) {
                    GlStateManager.enableBlend();
                }

                GlStateManager.enableAlpha();
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }
    }

    private void putQuadNormal(WorldRenderer worldRenderer, BakedQuad bakedQuad) {
        Vec3i vec3i = bakedQuad.getFace().getDirectionVec();
        worldRenderer.putNormal(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    public void onResourceManagerReload(IResourceManager iResourceManager) {
        this.itemModelMesher.rebuildCache();
    }

    public void renderModel(IBakedModel iBakedModel, int i) {
        this.renderModel(iBakedModel, i, null);
    }

    public ItemModelMesher getItemModelMesher() {
        return this.itemModelMesher;
    }

    public boolean shouldRenderItemIn3D(ItemStack itemStack) {
        IBakedModel itemModel = this.itemModelMesher.getItemModel(itemStack);
        return itemModel != null && itemModel.isGui3d();
    }

    public void renderItemModelForEntity(
            ItemStack itemStack,
            LivingEntity livingEntity,
            ItemCameraTransformsTransformType itemCameraTransformsTransformType) {
        IBakedModel itemModel = this.itemModelMesher.getItemModel(itemStack);
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;
            Item item = itemStack.getItem();
            ModelResourceLocation aDxValue = null;
            if (item == Items.fishing_rod && playerEntity.fishEntity != null) {
                aDxValue = new ModelResourceLocation("fishing_rod_cast", "inventory");
            } else if (item == Items.bow && playerEntity.getItemInUse() != null) {
                int i = itemStack.getMaxItemUseDuration() - playerEntity.getItemInUseCount();
                if (i >= 18) {
                    aDxValue = new ModelResourceLocation("bow_pulling_2", "inventory");
                } else if (i > 13) {
                    aDxValue = new ModelResourceLocation("bow_pulling_1", "inventory");
                } else if (i > 0) {
                    aDxValue = new ModelResourceLocation("bow_pulling_0", "inventory");
                }
            } else if (Reflector.Ja.exists()) {
                aDxValue =
                        (ModelResourceLocation)
                                Reflector.call(
                                        item,
                                        Reflector.Ja,
                                        new Object[] {
                                            itemStack,
                                            playerEntity,
                                            playerEntity.getItemInUseCount()
                                        });
            }

            this.modelLocation = aDxValue;
            if (aDxValue != null) {
                itemModel = this.itemModelMesher.getModelManager().getModel(aDxValue);
            }
        }

        this.renderItemModelTransform(itemStack, itemModel, itemCameraTransformsTransformType);
        this.modelLocation = null;
    }

    private void setupGuiTransform(int i, int j, boolean flag) {
        Render.getLegacyEntityRenderState();

        GlStateManager.translate(i, j, 100.0F + this.zLevel);
        GlStateManager.translate(8.0F, 8.0F, 0.0F);

        GlStateManager.scale(1.0F, 1.0F, -1.0F);
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        if (flag) {
            GlStateManager.scale(40.0F, 40.0F, 40.0F);
            GlStateManager.rotate(210.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.enableLighting();

            return;
        }

        GlStateManager.scale(64.0F, 64.0F, 64.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.disableLighting();
    }

    public void renderBakedItemWithEffects(ItemStack itemStack, IBakedModel iBakedModel) {
        GlStateManager.pushMatrix();
        Render.getLegacyEntityRenderState();

        GlStateManager.scale(0.5F, 0.5F, 0.5F);

        if (iBakedModel.isBuiltInRenderer()) {
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5F, -0.5F, -0.5F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableRescaleNormal();
            TileEntityRendererChestHelper.instance.renderByItem(itemStack);

        } else {
            if (OptiFineConfig.isCustomItems()) {
                iBakedModel =
                        CustomItems.method_f331dfdf5561f570d3e1(
                                itemStack, iBakedModel, this.modelLocation);
            }

            GlStateManager.translate(-0.5F, -0.5F, -0.5F);
            this.renderModel(iBakedModel, itemStack);
            if (itemStack.hasEffect()
                    && (!OptiFineConfig.isCustomItems()
                            || !CustomItems.renderCustomEffect(this, itemStack, iBakedModel))) {
                this.renderEffect(iBakedModel);
            }
        }

        GlStateManager.popMatrix();
    }

    private static ReportedException preserveItemRenderException(
            ReportedException reportedException) {
        return reportedException;
    }

    public void renderItemModelIntoGUI(ItemStack itemStack, int i, int j) {
        IBakedModel iBakedModel;
        Render.getLegacyEntityRenderState();

        iBakedModel = this.itemModelMesher.getItemModel(itemStack);
        GlStateManager.pushMatrix();
        this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
        this.textureManager
                .getTexture(TextureMap.locationBlocksTexture)
                .setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();

        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.setupGuiTransform(i, j, iBakedModel.isGui3d());
        if (Reflector.He.exists()) {
            iBakedModel =
                    (IBakedModel)
                            Reflector.call(
                                    Reflector.He,
                                    new Object[] {
                                        iBakedModel, ItemCameraTransformsTransformType.GUI
                                    });

        } else {
            this.applyModelCameraTransform(iBakedModel.getItemCameraTransforms().gui);
        }

        this.renderBakedItemWithEffects(itemStack, iBakedModel);
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
        this.textureManager.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
    }

    private void renderEffect(IBakedModel iBakedModel) {
        if (!OptiFineConfig.isCustomItems() || CustomItems.isUseGlint()) {
            GlStateManager.depthMask(false);
            GlStateManager.depthFunc(514);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(768, 1);
            this.textureManager.bindTexture(
                    net.minecraft.client.renderer.entity.RenderItem.RES_ITEM_GLINT);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(8.0F, 8.0F, 8.0F);
            long systemTime = MinecraftClient.getSystemTime();

            float f = (float) (systemTime % 3000L) / 3000.0F / 8.0F;
            GlStateManager.translate(f, 0.0F, 0.0F);
            GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
            this.renderModel(iBakedModel, -8372020);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(8.0F, 8.0F, 8.0F);
            systemTime = MinecraftClient.getSystemTime();

            float f1 = (float) (systemTime % 4873L) / 4873.0F / 8.0F;
            GlStateManager.translate(-f1, 0.0F, 0.0F);
            GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
            this.renderModel(iBakedModel, -8372020);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.enableLighting();
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
        }
    }

    public void renderItemWithDefaultTransform(ItemStack itemStack) {
        IBakedModel itemModel = this.itemModelMesher.getItemModel(itemStack);
        this.renderItemModelTransform(itemStack, itemModel, ItemCameraTransformsTransformType.NONE);
    }

    public void func_175039_a(boolean flag) {
        this.field_175058_l = flag;
    }

    protected void registerItem(Item item, int i, String text) {
        this.itemModelMesher.register(item, i, new ModelResourceLocation(text, "inventory"));
    }

    private void registerItems() {
        this.registerBlock(Blocks.anvil, "anvil_intact");
        this.registerBlock(Blocks.anvil, 1, "anvil_slightly_damaged");
        this.registerBlock(Blocks.anvil, 2, "anvil_very_damaged");
        this.registerBlock(Blocks.carpet, EnumDyeColor.BLACK.getMetadata(), "black_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.BLUE.getMetadata(), "blue_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.BROWN.getMetadata(), "brown_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.CYAN.getMetadata(), "cyan_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.GRAY.getMetadata(), "gray_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.GREEN.getMetadata(), "green_carpet");
        this.registerBlock(
                Blocks.carpet, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.LIME.getMetadata(), "lime_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.MAGENTA.getMetadata(), "magenta_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.ORANGE.getMetadata(), "orange_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.PINK.getMetadata(), "pink_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.PURPLE.getMetadata(), "purple_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.RED.getMetadata(), "red_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.SILVER.getMetadata(), "silver_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.WHITE.getMetadata(), "white_carpet");
        this.registerBlock(Blocks.carpet, EnumDyeColor.YELLOW.getMetadata(), "yellow_carpet");
        this.registerBlock(
                Blocks.cobblestone_wall,
                BlockWallEnumType.MOSSY.getMetadata(),
                "mossy_cobblestone_wall");
        this.registerBlock(
                Blocks.cobblestone_wall,
                BlockWallEnumType.NORMAL.getMetadata(),
                "cobblestone_wall");
        this.registerBlock(Blocks.dirt, BlockDirtDirtType.COARSE_DIRT.getMetadata(), "coarse_dirt");
        this.registerBlock(Blocks.dirt, BlockDirtDirtType.DIRT.getMetadata(), "dirt");
        this.registerBlock(Blocks.dirt, BlockDirtDirtType.PODZOL.getMetadata(), "podzol");
        this.registerBlock(
                Blocks.double_plant, BlockDoublePlantEnumPlantType.FERN.getMeta(), "double_fern");
        this.registerBlock(
                Blocks.double_plant, BlockDoublePlantEnumPlantType.GRASS.getMeta(), "double_grass");
        this.registerBlock(
                Blocks.double_plant, BlockDoublePlantEnumPlantType.PAEONIA.getMeta(), "paeonia");
        this.registerBlock(
                Blocks.double_plant, BlockDoublePlantEnumPlantType.ROSE.getMeta(), "double_rose");
        this.registerBlock(
                Blocks.double_plant,
                BlockDoublePlantEnumPlantType.SUNFLOWER.getMeta(),
                "sunflower");
        this.registerBlock(
                Blocks.double_plant, BlockDoublePlantEnumPlantType.SYRINGA.getMeta(), "syringa");
        this.registerBlock(Blocks.leaves, BlockPlanksEnumType.BIRCH.getMetadata(), "birch_leaves");
        this.registerBlock(
                Blocks.leaves, BlockPlanksEnumType.JUNGLE.getMetadata(), "jungle_leaves");
        this.registerBlock(Blocks.leaves, BlockPlanksEnumType.OAK.getMetadata(), "oak_leaves");
        this.registerBlock(
                Blocks.leaves, BlockPlanksEnumType.SPRUCE.getMetadata(), "spruce_leaves");
        this.registerBlock(
                Blocks.leaves2, BlockPlanksEnumType.ACACIA.getMetadata() - 4, "acacia_leaves");
        this.registerBlock(
                Blocks.leaves2, BlockPlanksEnumType.DARK_OAK.getMetadata() - 4, "dark_oak_leaves");
        this.registerBlock(Blocks.log, BlockPlanksEnumType.BIRCH.getMetadata(), "birch_log");
        this.registerBlock(Blocks.log, BlockPlanksEnumType.JUNGLE.getMetadata(), "jungle_log");
        this.registerBlock(Blocks.log, BlockPlanksEnumType.OAK.getMetadata(), "oak_log");
        this.registerBlock(Blocks.log, BlockPlanksEnumType.SPRUCE.getMetadata(), "spruce_log");
        this.registerBlock(Blocks.log2, BlockPlanksEnumType.ACACIA.getMetadata() - 4, "acacia_log");
        this.registerBlock(
                Blocks.log2, BlockPlanksEnumType.DARK_OAK.getMetadata() - 4, "dark_oak_log");
        this.registerBlock(
                Blocks.monster_egg,
                BlockSilverfishEnumType.CHISELED_STONEBRICK.getMetadata(),
                "chiseled_brick_monster_egg");
        this.registerBlock(
                Blocks.monster_egg,
                BlockSilverfishEnumType.COBBLESTONE.getMetadata(),
                "cobblestone_monster_egg");
        this.registerBlock(
                Blocks.monster_egg,
                BlockSilverfishEnumType.CRACKED_STONEBRICK.getMetadata(),
                "cracked_brick_monster_egg");
        this.registerBlock(
                Blocks.monster_egg,
                BlockSilverfishEnumType.MOSSY_STONEBRICK.getMetadata(),
                "mossy_brick_monster_egg");
        this.registerBlock(
                Blocks.monster_egg,
                BlockSilverfishEnumType.STONE.getMetadata(),
                "stone_monster_egg");
        this.registerBlock(
                Blocks.monster_egg,
                BlockSilverfishEnumType.STONEBRICK.getMetadata(),
                "stone_brick_monster_egg");
        this.registerBlock(
                Blocks.planks, BlockPlanksEnumType.ACACIA.getMetadata(), "acacia_planks");
        this.registerBlock(Blocks.planks, BlockPlanksEnumType.BIRCH.getMetadata(), "birch_planks");
        this.registerBlock(
                Blocks.planks, BlockPlanksEnumType.DARK_OAK.getMetadata(), "dark_oak_planks");
        this.registerBlock(
                Blocks.planks, BlockPlanksEnumType.JUNGLE.getMetadata(), "jungle_planks");
        this.registerBlock(Blocks.planks, BlockPlanksEnumType.OAK.getMetadata(), "oak_planks");
        this.registerBlock(
                Blocks.planks, BlockPlanksEnumType.SPRUCE.getMetadata(), "spruce_planks");
        this.registerBlock(
                Blocks.prismarine,
                BlockPrismarineEnumType.BRICKS.getMetadata(),
                "prismarine_bricks");
        this.registerBlock(
                Blocks.prismarine, BlockPrismarineEnumType.DARK.getMetadata(), "dark_prismarine");
        this.registerBlock(
                Blocks.prismarine, BlockPrismarineEnumType.ROUGH.getMetadata(), "prismarine");
        this.registerBlock(
                Blocks.quartz_block,
                BlockQuartzEnumType.CHISELED.getMetaFromState(),
                "chiseled_quartz_block");
        this.registerBlock(
                Blocks.quartz_block,
                BlockQuartzEnumType.DEFAULT.getMetaFromState(),
                "quartz_block");
        this.registerBlock(
                Blocks.quartz_block,
                BlockQuartzEnumType.LINES_Y.getMetaFromState(),
                "quartz_column");
        this.registerBlock(Blocks.red_flower, BlockFlowerEnumFlowerType.ALLIUM.getMeta(), "allium");
        this.registerBlock(
                Blocks.red_flower, BlockFlowerEnumFlowerType.BLUE_ORCHID.getMeta(), "blue_orchid");
        this.registerBlock(
                Blocks.red_flower, BlockFlowerEnumFlowerType.HOUSTONIA.getMeta(), "houstonia");
        this.registerBlock(
                Blocks.red_flower,
                BlockFlowerEnumFlowerType.ORANGE_TULIP.getMeta(),
                "orange_tulip");
        this.registerBlock(
                Blocks.red_flower, BlockFlowerEnumFlowerType.OXEYE_DAISY.getMeta(), "oxeye_daisy");
        this.registerBlock(
                Blocks.red_flower, BlockFlowerEnumFlowerType.PINK_TULIP.getMeta(), "pink_tulip");
        this.registerBlock(Blocks.red_flower, BlockFlowerEnumFlowerType.POPPY.getMeta(), "poppy");
        this.registerBlock(
                Blocks.red_flower, BlockFlowerEnumFlowerType.RED_TULIP.getMeta(), "red_tulip");
        this.registerBlock(
                Blocks.red_flower, BlockFlowerEnumFlowerType.WHITE_TULIP.getMeta(), "white_tulip");
        this.registerBlock(Blocks.sand, BlockSandEnumType.RED_SAND.getMetadata(), "red_sand");
        this.registerBlock(Blocks.sand, BlockSandEnumType.SAND.getMetadata(), "sand");
        this.registerBlock(
                Blocks.sandstone,
                BlockSandStoneEnumType.CHISELED.getMetadata(),
                "chiseled_sandstone");
        this.registerBlock(
                Blocks.sandstone, BlockSandStoneEnumType.DEFAULT.getMetadata(), "sandstone");
        this.registerBlock(
                Blocks.sandstone, BlockSandStoneEnumType.SMOOTH.getMetadata(), "smooth_sandstone");
        this.registerBlock(
                Blocks.red_sandstone,
                BlockRedSandstoneEnumType.CHISELED.getMetaFromState(),
                "chiseled_red_sandstone");
        this.registerBlock(
                Blocks.red_sandstone,
                BlockRedSandstoneEnumType.DEFAULT.getMetaFromState(),
                "red_sandstone");
        this.registerBlock(
                Blocks.red_sandstone,
                BlockRedSandstoneEnumType.SMOOTH.getMetaFromState(),
                "smooth_red_sandstone");
        this.registerBlock(
                Blocks.sapling, BlockPlanksEnumType.ACACIA.getMetadata(), "acacia_sapling");
        this.registerBlock(
                Blocks.sapling, BlockPlanksEnumType.BIRCH.getMetadata(), "birch_sapling");
        this.registerBlock(
                Blocks.sapling, BlockPlanksEnumType.DARK_OAK.getMetadata(), "dark_oak_sapling");
        this.registerBlock(
                Blocks.sapling, BlockPlanksEnumType.JUNGLE.getMetadata(), "jungle_sapling");
        this.registerBlock(Blocks.sapling, BlockPlanksEnumType.OAK.getMetadata(), "oak_sapling");
        this.registerBlock(
                Blocks.sapling, BlockPlanksEnumType.SPRUCE.getMetadata(), "spruce_sapling");
        this.registerBlock(Blocks.sponge, 0, "sponge");
        this.registerBlock(Blocks.sponge, 1, "sponge_wet");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.BLACK.getMetadata(), "black_stained_glass");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.BLUE.getMetadata(), "blue_stained_glass");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.BROWN.getMetadata(), "brown_stained_glass");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.CYAN.getMetadata(), "cyan_stained_glass");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.GRAY.getMetadata(), "gray_stained_glass");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.GREEN.getMetadata(), "green_stained_glass");
        this.registerBlock(
                Blocks.stained_glass,
                EnumDyeColor.LIGHT_BLUE.getMetadata(),
                "light_blue_stained_glass");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.LIME.getMetadata(), "lime_stained_glass");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_glass");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.ORANGE.getMetadata(), "orange_stained_glass");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.PINK.getMetadata(), "pink_stained_glass");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.PURPLE.getMetadata(), "purple_stained_glass");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.RED.getMetadata(), "red_stained_glass");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.SILVER.getMetadata(), "silver_stained_glass");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.WHITE.getMetadata(), "white_stained_glass");
        this.registerBlock(
                Blocks.stained_glass, EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_glass");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.BLACK.getMetadata(),
                "black_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.BLUE.getMetadata(),
                "blue_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.BROWN.getMetadata(),
                "brown_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.CYAN.getMetadata(),
                "cyan_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.GRAY.getMetadata(),
                "gray_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.GREEN.getMetadata(),
                "green_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.LIGHT_BLUE.getMetadata(),
                "light_blue_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.LIME.getMetadata(),
                "lime_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.MAGENTA.getMetadata(),
                "magenta_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.ORANGE.getMetadata(),
                "orange_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.PINK.getMetadata(),
                "pink_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.PURPLE.getMetadata(),
                "purple_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.RED.getMetadata(),
                "red_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.SILVER.getMetadata(),
                "silver_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.WHITE.getMetadata(),
                "white_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_glass_pane,
                EnumDyeColor.YELLOW.getMetadata(),
                "yellow_stained_glass_pane");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.BLACK.getMetadata(),
                "black_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.BLUE.getMetadata(),
                "blue_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.BROWN.getMetadata(),
                "brown_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.CYAN.getMetadata(),
                "cyan_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.GRAY.getMetadata(),
                "gray_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.GREEN.getMetadata(),
                "green_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.LIGHT_BLUE.getMetadata(),
                "light_blue_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.LIME.getMetadata(),
                "lime_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.MAGENTA.getMetadata(),
                "magenta_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.ORANGE.getMetadata(),
                "orange_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.PINK.getMetadata(),
                "pink_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.PURPLE.getMetadata(),
                "purple_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.RED.getMetadata(),
                "red_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.SILVER.getMetadata(),
                "silver_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.WHITE.getMetadata(),
                "white_stained_hardened_clay");
        this.registerBlock(
                Blocks.stained_hardened_clay,
                EnumDyeColor.YELLOW.getMetadata(),
                "yellow_stained_hardened_clay");
        this.registerBlock(
                Blocks.stone, BlockStoneEnumType.ANDESITE.getMetaFromState(), "andesite");
        this.registerBlock(
                Blocks.stone,
                BlockStoneEnumType.ANDESITE_SMOOTH.getMetaFromState(),
                "andesite_smooth");
        this.registerBlock(Blocks.stone, BlockStoneEnumType.DIORITE.getMetaFromState(), "diorite");
        this.registerBlock(
                Blocks.stone,
                BlockStoneEnumType.DIORITE_SMOOTH.getMetaFromState(),
                "diorite_smooth");
        this.registerBlock(Blocks.stone, BlockStoneEnumType.GRANITE.getMetaFromState(), "granite");
        this.registerBlock(
                Blocks.stone,
                BlockStoneEnumType.GRANITE_SMOOTH.getMetaFromState(),
                "granite_smooth");
        this.registerBlock(Blocks.stone, BlockStoneEnumType.STONE.getMetaFromState(), "stone");
        this.registerBlock(
                Blocks.stonebrick,
                BlockStoneBrickEnumType.CRACKED.getMetaFromState(),
                "cracked_stonebrick");
        this.registerBlock(
                Blocks.stonebrick,
                BlockStoneBrickEnumType.DEFAULT.getMetaFromState(),
                "stonebrick");
        this.registerBlock(
                Blocks.stonebrick,
                BlockStoneBrickEnumType.CHISELED.getMetaFromState(),
                "chiseled_stonebrick");
        this.registerBlock(
                Blocks.stonebrick,
                BlockStoneBrickEnumType.MOSSY.getMetaFromState(),
                "mossy_stonebrick");
        this.registerBlock(
                Blocks.stone_slab, BlockStoneSlabEnumType.BRICK.getMetadata(), "brick_slab");
        this.registerBlock(
                Blocks.stone_slab,
                BlockStoneSlabEnumType.COBBLESTONE.getMetadata(),
                "cobblestone_slab");
        this.registerBlock(
                Blocks.stone_slab, BlockStoneSlabEnumType.WOOD.getMetadata(), "old_wood_slab");
        this.registerBlock(
                Blocks.stone_slab,
                BlockStoneSlabEnumType.NETHERBRICK.getMetadata(),
                "nether_brick_slab");
        this.registerBlock(
                Blocks.stone_slab, BlockStoneSlabEnumType.QUARTZ.getMetadata(), "quartz_slab");
        this.registerBlock(
                Blocks.stone_slab, BlockStoneSlabEnumType.SAND.getMetadata(), "sandstone_slab");
        this.registerBlock(
                Blocks.stone_slab,
                BlockStoneSlabEnumType.SMOOTHBRICK.getMetadata(),
                "stone_brick_slab");
        this.registerBlock(
                Blocks.stone_slab, BlockStoneSlabEnumType.STONE.getMetadata(), "stone_slab");
        this.registerBlock(
                Blocks.stone_slab2,
                BlockStoneSlabNewEnumType.RED_SANDSTONE.getMetadata(),
                "red_sandstone_slab");
        this.registerBlock(
                Blocks.tallgrass, BlockTallGrassEnumType.DEAD_BUSH.getMeta(), "dead_bush");
        this.registerBlock(Blocks.tallgrass, BlockTallGrassEnumType.FERN.getMeta(), "fern");
        this.registerBlock(Blocks.tallgrass, BlockTallGrassEnumType.GRASS.getMeta(), "tall_grass");
        this.registerBlock(
                Blocks.wooden_slab, BlockPlanksEnumType.ACACIA.getMetadata(), "acacia_slab");
        this.registerBlock(
                Blocks.wooden_slab, BlockPlanksEnumType.BIRCH.getMetadata(), "birch_slab");
        this.registerBlock(
                Blocks.wooden_slab, BlockPlanksEnumType.DARK_OAK.getMetadata(), "dark_oak_slab");
        this.registerBlock(
                Blocks.wooden_slab, BlockPlanksEnumType.JUNGLE.getMetadata(), "jungle_slab");
        this.registerBlock(Blocks.wooden_slab, BlockPlanksEnumType.OAK.getMetadata(), "oak_slab");
        this.registerBlock(
                Blocks.wooden_slab, BlockPlanksEnumType.SPRUCE.getMetadata(), "spruce_slab");
        this.registerBlock(Blocks.wool, EnumDyeColor.BLACK.getMetadata(), "black_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.BLUE.getMetadata(), "blue_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.BROWN.getMetadata(), "brown_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.CYAN.getMetadata(), "cyan_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.GRAY.getMetadata(), "gray_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.GREEN.getMetadata(), "green_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.LIME.getMetadata(), "lime_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.MAGENTA.getMetadata(), "magenta_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.ORANGE.getMetadata(), "orange_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.PINK.getMetadata(), "pink_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.PURPLE.getMetadata(), "purple_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.RED.getMetadata(), "red_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.SILVER.getMetadata(), "silver_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.WHITE.getMetadata(), "white_wool");
        this.registerBlock(Blocks.wool, EnumDyeColor.YELLOW.getMetadata(), "yellow_wool");
        this.registerBlock(Blocks.acacia_stairs, "acacia_stairs");
        this.registerBlock(Blocks.activator_rail, "activator_rail");
        this.registerBlock(Blocks.beacon, "beacon");
        this.registerBlock(Blocks.bedrock, "bedrock");
        this.registerBlock(Blocks.birch_stairs, "birch_stairs");
        this.registerBlock(Blocks.bookshelf, "bookshelf");
        this.registerBlock(Blocks.brick_block, "brick_block");
        this.registerBlock(Blocks.brick_block, "brick_block");
        this.registerBlock(Blocks.brick_stairs, "brick_stairs");
        this.registerBlock(Blocks.brown_mushroom, "brown_mushroom");
        this.registerBlock(Blocks.cactus, "cactus");
        this.registerBlock(Blocks.clay, "clay");
        this.registerBlock(Blocks.coal_block, "coal_block");
        this.registerBlock(Blocks.coal_ore, "coal_ore");
        this.registerBlock(Blocks.cobblestone, "cobblestone");
        this.registerBlock(Blocks.crafting_table, "crafting_table");
        this.registerBlock(Blocks.dark_oak_stairs, "dark_oak_stairs");
        this.registerBlock(Blocks.daylight_detector, "daylight_detector");
        this.registerBlock(Blocks.deadbush, "dead_bush");
        this.registerBlock(Blocks.detector_rail, "detector_rail");
        this.registerBlock(Blocks.diamond_block, "diamond_block");
        this.registerBlock(Blocks.diamond_ore, "diamond_ore");
        this.registerBlock(Blocks.dispenser, "dispenser");
        this.registerBlock(Blocks.dropper, "dropper");
        this.registerBlock(Blocks.emerald_block, "emerald_block");
        this.registerBlock(Blocks.emerald_ore, "emerald_ore");
        this.registerBlock(Blocks.enchanting_table, "enchanting_table");
        this.registerBlock(Blocks.end_portal_frame, "end_portal_frame");
        this.registerBlock(Blocks.end_stone, "end_stone");
        this.registerBlock(Blocks.oak_fence, "oak_fence");
        this.registerBlock(Blocks.spruce_fence, "spruce_fence");
        this.registerBlock(Blocks.birch_fence, "birch_fence");
        this.registerBlock(Blocks.jungle_fence, "jungle_fence");
        this.registerBlock(Blocks.dark_oak_fence, "dark_oak_fence");
        this.registerBlock(Blocks.acacia_fence, "acacia_fence");
        this.registerBlock(Blocks.oak_fence_gate, "oak_fence_gate");
        this.registerBlock(Blocks.spruce_fence_gate, "spruce_fence_gate");
        this.registerBlock(Blocks.birch_fence_gate, "birch_fence_gate");
        this.registerBlock(Blocks.jungle_fence_gate, "jungle_fence_gate");
        this.registerBlock(Blocks.dark_oak_fence_gate, "dark_oak_fence_gate");
        this.registerBlock(Blocks.acacia_fence_gate, "acacia_fence_gate");
        this.registerBlock(Blocks.furnace, "furnace");
        this.registerBlock(Blocks.glass, "glass");
        this.registerBlock(Blocks.glass_pane, "glass_pane");
        this.registerBlock(Blocks.glowstone, "glowstone");
        this.registerBlock(Blocks.golden_rail, "golden_rail");
        this.registerBlock(Blocks.gold_block, "gold_block");
        this.registerBlock(Blocks.gold_ore, "gold_ore");
        this.registerBlock(Blocks.grass, "grass");
        this.registerBlock(Blocks.gravel, "gravel");
        this.registerBlock(Blocks.hardened_clay, "hardened_clay");
        this.registerBlock(Blocks.hay_block, "hay_block");
        this.registerBlock(Blocks.heavy_weighted_pressure_plate, "heavy_weighted_pressure_plate");
        this.registerBlock(Blocks.hopper, "hopper");
        this.registerBlock(Blocks.ice, "ice");
        this.registerBlock(Blocks.iron_bars, "iron_bars");
        this.registerBlock(Blocks.iron_block, "iron_block");
        this.registerBlock(Blocks.iron_ore, "iron_ore");
        this.registerBlock(Blocks.iron_trapdoor, "iron_trapdoor");
        this.registerBlock(Blocks.jukebox, "jukebox");
        this.registerBlock(Blocks.jungle_stairs, "jungle_stairs");
        this.registerBlock(Blocks.ladder, "ladder");
        this.registerBlock(Blocks.lapis_block, "lapis_block");
        this.registerBlock(Blocks.lapis_ore, "lapis_ore");
        this.registerBlock(Blocks.lever, "lever");
        this.registerBlock(Blocks.light_weighted_pressure_plate, "light_weighted_pressure_plate");
        this.registerBlock(Blocks.lit_pumpkin, "lit_pumpkin");
        this.registerBlock(Blocks.melon_block, "melon_block");
        this.registerBlock(Blocks.mossy_cobblestone, "mossy_cobblestone");
        this.registerBlock(Blocks.mycelium, "mycelium");
        this.registerBlock(Blocks.netherrack, "netherrack");
        this.registerBlock(Blocks.nether_brick, "nether_brick");
        this.registerBlock(Blocks.nether_brick_fence, "nether_brick_fence");
        this.registerBlock(Blocks.nether_brick_stairs, "nether_brick_stairs");
        this.registerBlock(Blocks.noteblock, "noteblock");
        this.registerBlock(Blocks.oak_stairs, "oak_stairs");
        this.registerBlock(Blocks.obsidian, "obsidian");
        this.registerBlock(Blocks.packed_ice, "packed_ice");
        this.registerBlock(Blocks.piston, "piston");
        this.registerBlock(Blocks.pumpkin, "pumpkin");
        this.registerBlock(Blocks.quartz_ore, "quartz_ore");
        this.registerBlock(Blocks.quartz_stairs, "quartz_stairs");
        this.registerBlock(Blocks.rail, "rail");
        this.registerBlock(Blocks.redstone_block, "redstone_block");
        this.registerBlock(Blocks.redstone_lamp, "redstone_lamp");
        this.registerBlock(Blocks.redstone_ore, "redstone_ore");
        this.registerBlock(Blocks.redstone_torch, "redstone_torch");
        this.registerBlock(Blocks.red_mushroom, "red_mushroom");
        this.registerBlock(Blocks.sandstone_stairs, "sandstone_stairs");
        this.registerBlock(Blocks.red_sandstone_stairs, "red_sandstone_stairs");
        this.registerBlock(Blocks.sea_lantern, "sea_lantern");
        this.registerBlock(Blocks.slime_block, "slime");
        this.registerBlock(Blocks.snow, "snow");
        this.registerBlock(Blocks.snow_layer, "snow_layer");
        this.registerBlock(Blocks.soul_sand, "soul_sand");
        this.registerBlock(Blocks.spruce_stairs, "spruce_stairs");
        this.registerBlock(Blocks.sticky_piston, "sticky_piston");
        this.registerBlock(Blocks.stone_brick_stairs, "stone_brick_stairs");
        this.registerBlock(Blocks.stone_button, "stone_button");
        this.registerBlock(Blocks.stone_pressure_plate, "stone_pressure_plate");
        this.registerBlock(Blocks.stone_stairs, "stone_stairs");
        this.registerBlock(Blocks.tnt, "tnt");
        this.registerBlock(Blocks.torch, "torch");
        this.registerBlock(Blocks.trapdoor, "trapdoor");
        this.registerBlock(Blocks.tripwire_hook, "tripwire_hook");
        this.registerBlock(Blocks.vine, "vine");
        this.registerBlock(Blocks.waterlily, "waterlily");
        this.registerBlock(Blocks.web, "web");
        this.registerBlock(Blocks.wooden_button, "wooden_button");
        this.registerBlock(Blocks.wooden_pressure_plate, "wooden_pressure_plate");
        this.registerBlock(
                Blocks.yellow_flower, BlockFlowerEnumFlowerType.DANDELION.getMeta(), "dandelion");
        this.registerBlock(Blocks.chest, "chest");
        this.registerBlock(Blocks.trapped_chest, "trapped_chest");
        this.registerBlock(Blocks.ender_chest, "ender_chest");
        this.registerItem(Items.iron_shovel, "iron_shovel");
        this.registerItem(Items.iron_pickaxe, "iron_pickaxe");
        this.registerItem(Items.iron_axe, "iron_axe");
        this.registerItem(Items.flint_and_steel, "flint_and_steel");
        this.registerItem(Items.apple, "apple");
        this.registerItem(Items.bow, 0, "bow");
        this.registerItem(Items.bow, 1, "bow_pulling_0");
        this.registerItem(Items.bow, 2, "bow_pulling_1");
        this.registerItem(Items.bow, 3, "bow_pulling_2");
        this.registerItem(Items.arrow, "arrow");
        this.registerItem(Items.coal, 0, "coal");
        this.registerItem(Items.coal, 1, "charcoal");
        this.registerItem(Items.diamond, "diamond");
        this.registerItem(Items.iron_ingot, "iron_ingot");
        this.registerItem(Items.gold_ingot, "gold_ingot");
        this.registerItem(Items.iron_sword, "iron_sword");
        this.registerItem(Items.wooden_sword, "wooden_sword");
        this.registerItem(Items.wooden_shovel, "wooden_shovel");
        this.registerItem(Items.wooden_pickaxe, "wooden_pickaxe");
        this.registerItem(Items.wooden_axe, "wooden_axe");
        this.registerItem(Items.stone_sword, "stone_sword");
        this.registerItem(Items.stone_shovel, "stone_shovel");
        this.registerItem(Items.stone_pickaxe, "stone_pickaxe");
        this.registerItem(Items.stone_axe, "stone_axe");
        this.registerItem(Items.diamond_sword, "diamond_sword");
        this.registerItem(Items.diamond_shovel, "diamond_shovel");
        this.registerItem(Items.diamond_pickaxe, "diamond_pickaxe");
        this.registerItem(Items.diamond_axe, "diamond_axe");
        this.registerItem(Items.stick, "stick");
        this.registerItem(Items.bowl, "bowl");
        this.registerItem(Items.mushroom_stew, "mushroom_stew");
        this.registerItem(Items.golden_sword, "golden_sword");
        this.registerItem(Items.golden_shovel, "golden_shovel");
        this.registerItem(Items.golden_pickaxe, "golden_pickaxe");
        this.registerItem(Items.golden_axe, "golden_axe");
        this.registerItem(Items.string, "string");
        this.registerItem(Items.feather, "feather");
        this.registerItem(Items.gunpowder, "gunpowder");
        this.registerItem(Items.wooden_hoe, "wooden_hoe");
        this.registerItem(Items.stone_hoe, "stone_hoe");
        this.registerItem(Items.iron_hoe, "iron_hoe");
        this.registerItem(Items.diamond_hoe, "diamond_hoe");
        this.registerItem(Items.golden_hoe, "golden_hoe");
        this.registerItem(Items.wheat_seeds, "wheat_seeds");
        this.registerItem(Items.wheat, "wheat");
        this.registerItem(Items.bread, "bread");
        this.registerItem(Items.leather_helmet, "leather_helmet");
        this.registerItem(Items.leather_chestplate, "leather_chestplate");
        this.registerItem(Items.leather_leggings, "leather_leggings");
        this.registerItem(Items.leather_boots, "leather_boots");
        this.registerItem(Items.chainmail_helmet, "chainmail_helmet");
        this.registerItem(Items.chainmail_chestplate, "chainmail_chestplate");
        this.registerItem(Items.chainmail_leggings, "chainmail_leggings");
        this.registerItem(Items.chainmail_boots, "chainmail_boots");
        this.registerItem(Items.iron_helmet, "iron_helmet");
        this.registerItem(Items.iron_chestplate, "iron_chestplate");
        this.registerItem(Items.iron_leggings, "iron_leggings");
        this.registerItem(Items.iron_boots, "iron_boots");
        this.registerItem(Items.diamond_helmet, "diamond_helmet");
        this.registerItem(Items.diamond_chestplate, "diamond_chestplate");
        this.registerItem(Items.diamond_leggings, "diamond_leggings");
        this.registerItem(Items.diamond_boots, "diamond_boots");
        this.registerItem(Items.golden_helmet, "golden_helmet");
        this.registerItem(Items.golden_chestplate, "golden_chestplate");
        this.registerItem(Items.golden_leggings, "golden_leggings");
        this.registerItem(Items.golden_boots, "golden_boots");
        this.registerItem(Items.flint, "flint");
        this.registerItem(Items.porkchop, "porkchop");
        this.registerItem(Items.cooked_porkchop, "cooked_porkchop");
        this.registerItem(Items.painting, "painting");
        this.registerItem(Items.golden_apple, "golden_apple");
        this.registerItem(Items.golden_apple, 1, "golden_apple");
        this.registerItem(Items.sign, "sign");
        this.registerItem(Items.oak_door, "oak_door");
        this.registerItem(Items.spruce_door, "spruce_door");
        this.registerItem(Items.birch_door, "birch_door");
        this.registerItem(Items.jungle_door, "jungle_door");
        this.registerItem(Items.acacia_door, "acacia_door");
        this.registerItem(Items.dark_oak_door, "dark_oak_door");
        this.registerItem(Items.bucket, "bucket");
        this.registerItem(Items.water_bucket, "water_bucket");
        this.registerItem(Items.lava_bucket, "lava_bucket");
        this.registerItem(Items.minecart, "minecart");
        this.registerItem(Items.saddle, "saddle");
        this.registerItem(Items.iron_door, "iron_door");
        this.registerItem(Items.redstone, "redstone");
        this.registerItem(Items.snowball, "snowball");
        this.registerItem(Items.boat, "boat");
        this.registerItem(Items.leather, "leather");
        this.registerItem(Items.milk_bucket, "milk_bucket");
        this.registerItem(Items.brick, "brick");
        this.registerItem(Items.clay_ball, "clay_ball");
        this.registerItem(Items.reeds, "reeds");
        this.registerItem(Items.paper, "paper");
        this.registerItem(Items.book, "book");
        this.registerItem(Items.slime_ball, "slime_ball");
        this.registerItem(Items.chest_minecart, "chest_minecart");
        this.registerItem(Items.furnace_minecart, "furnace_minecart");
        this.registerItem(Items.egg, "egg");
        this.registerItem(Items.compass, "compass");
        this.registerItem(Items.fishing_rod, "fishing_rod");
        this.registerItem(Items.fishing_rod, 1, "fishing_rod_cast");
        this.registerItem(Items.clock, "clock");
        this.registerItem(Items.glowstone_dust, "glowstone_dust");
        this.registerItem(Items.fish, ItemFishFoodFishType.COD.getMetadata(), "cod");
        this.registerItem(Items.fish, ItemFishFoodFishType.SALMON.getMetadata(), "salmon");
        this.registerItem(Items.fish, ItemFishFoodFishType.CLOWNFISH.getMetadata(), "clownfish");
        this.registerItem(Items.fish, ItemFishFoodFishType.PUFFERFISH.getMetadata(), "pufferfish");
        this.registerItem(Items.cooked_fish, ItemFishFoodFishType.COD.getMetadata(), "cooked_cod");
        this.registerItem(
                Items.cooked_fish, ItemFishFoodFishType.SALMON.getMetadata(), "cooked_salmon");
        this.registerItem(Items.dye, EnumDyeColor.BLACK.getDyeColorDamage(), "dye_black");
        this.registerItem(Items.dye, EnumDyeColor.RED.getDyeColorDamage(), "dye_red");
        this.registerItem(Items.dye, EnumDyeColor.GREEN.getDyeColorDamage(), "dye_green");
        this.registerItem(Items.dye, EnumDyeColor.BROWN.getDyeColorDamage(), "dye_brown");
        this.registerItem(Items.dye, EnumDyeColor.BLUE.getDyeColorDamage(), "dye_blue");
        this.registerItem(Items.dye, EnumDyeColor.PURPLE.getDyeColorDamage(), "dye_purple");
        this.registerItem(Items.dye, EnumDyeColor.CYAN.getDyeColorDamage(), "dye_cyan");
        this.registerItem(Items.dye, EnumDyeColor.SILVER.getDyeColorDamage(), "dye_silver");
        this.registerItem(Items.dye, EnumDyeColor.GRAY.getDyeColorDamage(), "dye_gray");
        this.registerItem(Items.dye, EnumDyeColor.PINK.getDyeColorDamage(), "dye_pink");
        this.registerItem(Items.dye, EnumDyeColor.LIME.getDyeColorDamage(), "dye_lime");
        this.registerItem(Items.dye, EnumDyeColor.YELLOW.getDyeColorDamage(), "dye_yellow");
        this.registerItem(Items.dye, EnumDyeColor.LIGHT_BLUE.getDyeColorDamage(), "dye_light_blue");
        this.registerItem(Items.dye, EnumDyeColor.MAGENTA.getDyeColorDamage(), "dye_magenta");
        this.registerItem(Items.dye, EnumDyeColor.ORANGE.getDyeColorDamage(), "dye_orange");
        this.registerItem(Items.dye, EnumDyeColor.WHITE.getDyeColorDamage(), "dye_white");
        this.registerItem(Items.bone, "bone");
        this.registerItem(Items.sugar, "sugar");
        this.registerItem(Items.cake, "cake");
        this.registerItem(Items.bed, "bed");
        this.registerItem(Items.repeater, "repeater");
        this.registerItem(Items.cookie, "cookie");
        this.registerItem(Items.shears, "shears");
        this.registerItem(Items.melon, "melon");
        this.registerItem(Items.pumpkin_seeds, "pumpkin_seeds");
        this.registerItem(Items.melon_seeds, "melon_seeds");
        this.registerItem(Items.beef, "beef");
        this.registerItem(Items.cooked_beef, "cooked_beef");
        this.registerItem(Items.chicken, "chicken");
        this.registerItem(Items.cooked_chicken, "cooked_chicken");
        this.registerItem(Items.rabbit, "rabbit");
        this.registerItem(Items.cooked_rabbit, "cooked_rabbit");
        this.registerItem(Items.mutton, "mutton");
        this.registerItem(Items.cooked_mutton, "cooked_mutton");
        this.registerItem(Items.rabbit_foot, "rabbit_foot");
        this.registerItem(Items.rabbit_hide, "rabbit_hide");
        this.registerItem(Items.rabbit_stew, "rabbit_stew");
        this.registerItem(Items.rotten_flesh, "rotten_flesh");
        this.registerItem(Items.ender_pearl, "ender_pearl");
        this.registerItem(Items.blaze_rod, "blaze_rod");
        this.registerItem(Items.ghast_tear, "ghast_tear");
        this.registerItem(Items.gold_nugget, "gold_nugget");
        this.registerItem(Items.nether_wart, "nether_wart");
        this.itemModelMesher.register(
                Items.potionitem, new RenderItemRegisterItemsItemMeshDefinition3(this));
        this.registerItem(Items.glass_bottle, "glass_bottle");
        this.registerItem(Items.spider_eye, "spider_eye");
        this.registerItem(Items.fermented_spider_eye, "fermented_spider_eye");
        this.registerItem(Items.blaze_powder, "blaze_powder");
        this.registerItem(Items.magma_cream, "magma_cream");
        this.registerItem(Items.brewing_stand, "brewing_stand");
        this.registerItem(Items.cauldron, "cauldron");
        this.registerItem(Items.ender_eye, "ender_eye");
        this.registerItem(Items.speckled_melon, "speckled_melon");
        this.itemModelMesher.register(
                Items.spawn_egg, new RenderItemRegisterItemsItemMeshDefinition2(this));
        this.registerItem(Items.experience_bottle, "experience_bottle");
        this.registerItem(Items.fire_charge, "fire_charge");
        this.registerItem(Items.writable_book, "writable_book");
        this.registerItem(Items.emerald, "emerald");
        this.registerItem(Items.item_frame, "item_frame");
        this.registerItem(Items.flower_pot, "flower_pot");
        this.registerItem(Items.carrot, "carrot");
        this.registerItem(Items.potato, "potato");
        this.registerItem(Items.baked_potato, "baked_potato");
        this.registerItem(Items.poisonous_potato, "poisonous_potato");
        this.registerItem(Items.map, "map");
        this.registerItem(Items.golden_carrot, "golden_carrot");
        this.registerItem(Items.skull, 0, "skull_skeleton");
        this.registerItem(Items.skull, 1, "skull_wither");
        this.registerItem(Items.skull, 2, "skull_zombie");
        this.registerItem(Items.skull, 3, "skull_char");
        this.registerItem(Items.skull, 4, "skull_creeper");
        this.registerItem(Items.carrot_on_a_stick, "carrot_on_a_stick");
        this.registerItem(Items.nether_star, "nether_star");
        this.registerItem(Items.pumpkin_pie, "pumpkin_pie");
        this.registerItem(Items.firework_charge, "firework_charge");
        this.registerItem(Items.comparator, "comparator");
        this.registerItem(Items.netherbrick, "netherbrick");
        this.registerItem(Items.quartz, "quartz");
        this.registerItem(Items.tnt_minecart, "tnt_minecart");
        this.registerItem(Items.hopper_minecart, "hopper_minecart");
        this.registerItem(Items.armor_stand, "armor_stand");
        this.registerItem(Items.iron_horse_armor, "iron_horse_armor");
        this.registerItem(Items.golden_horse_armor, "golden_horse_armor");
        this.registerItem(Items.diamond_horse_armor, "diamond_horse_armor");
        this.registerItem(Items.lead, "lead");
        this.registerItem(Items.name_tag, "name_tag");
        this.itemModelMesher.register(
                Items.banner, new RenderItemRegisterItemsItemMeshDefinition5(this));
        this.registerItem(Items.record_13, "record_13");
        this.registerItem(Items.record_cat, "record_cat");
        this.registerItem(Items.record_blocks, "record_blocks");
        this.registerItem(Items.record_chirp, "record_chirp");
        this.registerItem(Items.record_far, "record_far");
        this.registerItem(Items.record_mall, "record_mall");
        this.registerItem(Items.record_mellohi, "record_mellohi");
        this.registerItem(Items.record_stal, "record_stal");
        this.registerItem(Items.record_strad, "record_strad");
        this.registerItem(Items.record_ward, "record_ward");
        this.registerItem(Items.record_11, "record_11");
        this.registerItem(Items.record_wait, "record_wait");
        this.registerItem(Items.prismarine_shard, "prismarine_shard");
        this.registerItem(Items.prismarine_crystals, "prismarine_crystals");
        this.itemModelMesher.register(
                Items.enchanted_book, new RenderItemRegisterItemsItemMeshDefinition(this));
        this.itemModelMesher.register(
                Items.filled_map, new RenderItemRegisterItemsItemMeshDefinition4(this));
        this.registerBlock(Blocks.command_block, "command_block");
        this.registerItem(Items.fireworks, "fireworks");
        this.registerItem(Items.command_block_minecart, "command_block_minecart");
        this.registerBlock(Blocks.barrier, "barrier");
        this.registerBlock(Blocks.mob_spawner, "mob_spawner");
        this.registerItem(Items.written_book, "written_book");
        this.registerBlock(
                Blocks.brown_mushroom_block,
                BlockHugeMushroomEnumType.ALL_INSIDE.getMetadata(),
                "brown_mushroom_block");
        this.registerBlock(
                Blocks.red_mushroom_block,
                BlockHugeMushroomEnumType.ALL_INSIDE.getMetadata(),
                "red_mushroom_block");
        this.registerBlock(Blocks.dragon_egg, "dragon_egg");
        if (Reflector.Gl.exists()) {
            Reflector.call(Reflector.Gl, new Object[] {this.itemModelMesher});
        }
    }

    protected void registerBlock(Block block, int i, String text) {
        this.registerItem(Item.getItemFromBlock(block), i, text);
    }

    public void renderItem(ItemStack itemStack, IBakedModel iBakedModel) {
        GlStateManager.pushMatrix();
        Render.getLegacyEntityRenderState();

        GlStateManager.scale(0.5F, 0.5F, 0.5F);

        if (iBakedModel.isBuiltInRenderer()) {
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5F, -0.5F, -0.5F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableRescaleNormal();
            TileEntityRendererChestHelper.instance.renderByItem(itemStack);

        } else {
            GlStateManager.translate(-0.5F, -0.5F, -0.5F);
            this.renderModel(iBakedModel, itemStack);
        }

        GlStateManager.popMatrix();
    }
}
