package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModelBuilder;
import net.minecraft.client.resources.model.WeightedBakedModel;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import optifine.OptiFineConfig;
import optifine.Reflector;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.render.XrayModule;
import shadersmod.client.SVertexBuilder;

public class BlockRendererDispatcher implements IResourceManagerReloadListener {

    private BlockModelShapes blockModelShapes;
    private BlockFluidRenderer fluidRenderer;
    private final BlockModelRenderer blockModelRenderer = new BlockModelRenderer();
    private GameSettings gameSettings;
    private final ChestRenderer chestRenderer = new ChestRenderer();

    public BlockModelShapes getBlockModelShapes() {
        return this.blockModelShapes;
    }

    public void renderBlockBrightness(BlockState blockState, float f) {
        int k = WorldRenderer.getLegacyRenderFlowToken();
        int j = blockState.getBlock().getRenderType();
        int i = k;
        if (j != -1) {
            switch (j) {
                case 1:
                default:
                    if (i == 0) {
                        break;
                    }
                case 2:
                    this.chestRenderer.renderChestBrightness(blockState.getBlock(), f);
                    if (i == 0) {
                        break;
                    }
                case 3:
                    IBakedModel iBakedModel = this.getBakedModel(blockState, (BlockPos) null);
                    this.blockModelRenderer.renderModelBrightness(iBakedModel, blockState, f, true);
            }
        }
    }

    public void renderBlockDamage(
            BlockState blockState,
            BlockPos blockPos,
            TextureAtlasSprite textureAtlasSprite,
            IBlockAccess iBlockAccess) {
        int k = WorldRenderer.getLegacyRenderFlowMarker();
        Block block = blockState.getBlock();
        int i = k;
        int j = block.getRenderType();
        if (j == 3) {
            blockState = block.getActualState(blockState, iBlockAccess, blockPos);
            IBakedModel iBakedModel = this.blockModelShapes.getModelForState(blockState);
            if (Reflector.Gk.isInstance(iBakedModel)) {
                BlockState blockStateValue =
                        (BlockState)
                                Reflector.call(
                                        block,
                                        Reflector.Ht,
                                        new Object[] {blockState, iBlockAccess, blockPos});

                for (EnumWorldBlockLayer enumWorldBlockLayer : EnumWorldBlockLayer.values()) {
                    if (Reflector.callBoolean(
                            block, Reflector.forgeCanRenderBlockInLayer, new Object[] {enumWorldBlockLayer})) {
                        Reflector.callVoid(Reflector.forgeSetRenderLayer, new Object[] {enumWorldBlockLayer});
                        IBakedModel iBakedModelValue =
                                (IBakedModel)
                                        Reflector.call(
                                                iBakedModel,
                                                Reflector.IZ,
                                                new Object[] {blockStateValue});
                        IBakedModel iBakedModelValue2 =
                                new SimpleBakedModelBuilder(iBakedModelValue, textureAtlasSprite)
                                        .makeBakedModel();
                        this.blockModelRenderer.renderModel(
                                iBlockAccess,
                                iBakedModelValue2,
                                blockState,
                                blockPos,
                                Tessellator.getInstance().getWorldRenderer());
                    }

                    if (i == 0) {
                        break;
                    }
                }

                return;
            }

            IBakedModel iBakedModelValue3 =
                    new SimpleBakedModelBuilder(iBakedModel, textureAtlasSprite).makeBakedModel();
            this.blockModelRenderer.renderModel(
                    iBlockAccess,
                    iBakedModelValue3,
                    blockState,
                    blockPos,
                    Tessellator.getInstance().getWorldRenderer());
        }
    }

    public void onResourceManagerReload(IResourceManager iResourceManager) {
        this.fluidRenderer.initAtlasSprites();
    }

    private IBakedModel getBakedModel(BlockState blockState, BlockPos blockPos) {
        IBakedModel iBakedModel = this.blockModelShapes.getModelForState(blockState);
        if (blockPos != null
                && this.gameSettings.allowBlockAlternatives
                && iBakedModel instanceof WeightedBakedModel) {
            iBakedModel =
                    ((WeightedBakedModel) iBakedModel)
                            .getAlternativeModel(MathHelper.positionRandom(blockPos));
        }

        return iBakedModel;
    }

    public boolean renderBlock(
            BlockState blockState,
            BlockPos blockPos,
            IBlockAccess iBlockAccess,
            WorldRenderer worldRenderer) {
        try {
            int renderType = blockState.getBlock().getRenderType();
            if (renderType == -1) {
                return false;
            }

            ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
            XrayModule xray = ClientServices.moduleManager.xray;
            if (xray.isEnabled()
                    && !xray.isVisibleBlock(blockState.getBlock())
                    && xray.opacitySetting.asDouble() == 0.0) {
                return false;
            }

            switch (renderType) {
                case 1:
                    if (OptiFineConfig.isShaders()) {
                        SVertexBuilder.pushEntity(
                                blockState, blockPos, iBlockAccess, worldRenderer);
                    }

                    boolean flag1 =
                            this.fluidRenderer.renderFluid(
                                    iBlockAccess, blockState, blockPos, worldRenderer);
                    if (OptiFineConfig.isShaders()) {
                        SVertexBuilder.popEntity(worldRenderer);
                    }

                    return flag1;
                case 2:
                    return false;
                case 3:
                    IBakedModel iBakedModel =
                            this.getModelFromBlockState(blockState, iBlockAccess, blockPos);
                    if (OptiFineConfig.isShaders()) {
                        SVertexBuilder.pushEntity(
                                blockState, blockPos, iBlockAccess, worldRenderer);
                    }

                    boolean flag =
                            this.blockModelRenderer.renderModel(
                                    iBlockAccess, iBakedModel, blockState, blockPos, worldRenderer);
                    if (OptiFineConfig.isShaders()) {
                        SVertexBuilder.popEntity(worldRenderer);
                    }

                    return flag;
                default:
                    return false;
            }
        } catch (Throwable throwable) {
            CrashReport crashReport =
                    CrashReport.makeCrashReport(throwable, "Tesselating block in world");
            CrashReportCategory crashReportCategory =
                    crashReport.makeCategory("Block being tesselated");
            CrashReportCategory.addBlockInfo(
                    crashReportCategory,
                    blockPos,
                    blockState.getBlock(),
                    blockState.getBlock().getMetaFromState(blockState));
            throw new ReportedException(crashReport);
        }
    }

    public boolean isRenderTypeChest(Block block, int j) {
        if (block == null) {
            return false;
        }

        int renderType = block.getRenderType();
        return renderType == 3 ? false : renderType == 2;
    }

    public IBakedModel getModelFromBlockState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        Block block = blockState.getBlock();
        if (iBlockAccess.getWorldType() != WorldType.DEBUG_WORLD) {
            try {
                blockState = block.getActualState(blockState, iBlockAccess, blockPos);
            } catch (Exception exception) {
            }
        }

        IBakedModel iBakedModel = this.blockModelShapes.getModelForState(blockState);
        if (blockPos != null
                && this.gameSettings.allowBlockAlternatives
                && iBakedModel instanceof WeightedBakedModel) {
            iBakedModel =
                    ((WeightedBakedModel) iBakedModel)
                            .getAlternativeModel(MathHelper.positionRandom(blockPos));
        }

        if (Reflector.Gk.isInstance(iBakedModel)) {
            BlockState blockStateValue =
                    (BlockState)
                            Reflector.call(
                                    block,
                                    Reflector.Ht,
                                    new Object[] {blockState, iBlockAccess, blockPos});
            iBakedModel =
                    (IBakedModel)
                            Reflector.call(
                                    iBakedModel, Reflector.IZ, new Object[] {blockStateValue});
        }

        return iBakedModel;
    }


    public BlockModelRenderer getBlockModelRenderer() {
        return this.blockModelRenderer;
    }

    public BlockRendererDispatcher(BlockModelShapes ayMValue, GameSettings ayPValue) {
        this.fluidRenderer = new BlockFluidRenderer();
        this.blockModelShapes = ayMValue;
        this.gameSettings = ayPValue;
    }
}
