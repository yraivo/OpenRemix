package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderFallingBlock extends Render {
    protected ResourceLocation getEntityTexture(EntityFallingBlock entityFallingBlock) {
        return TextureMap.locationBlocksTexture;
    }

    public void doRender(
            EntityFallingBlock entityFallingBlock,
            double d0,
            double d1,
            double d2,
            float f,
            float f1) {
        if (entityFallingBlock.getBlock() != null) {
            this.bindTexture(TextureMap.locationBlocksTexture);
            BlockState blockState = entityFallingBlock.getBlock();
            Block block = blockState.getBlock();
            BlockPos blockPos = new BlockPos(entityFallingBlock);
            World world = entityFallingBlock.getWorld();
            if (blockState != world.getBlockState(blockPos)
                    && block.getRenderType() != -1
                    && block.getRenderType() == 3) {
                GlStateManager.pushMatrix();
                GlStateManager.translate((float) d0, (float) d1, (float) d2);
                GlStateManager.disableLighting();
                Tessellator instance = Tessellator.getInstance();
                WorldRenderer worldRenderer = instance.getWorldRenderer();
                worldRenderer.startDrawingQuads();
                worldRenderer.setVertexFormat(DefaultVertexFormats.BLOCK);
                int x = blockPos.getX();
                int j = blockPos.getY();
                int k = blockPos.getZ();
                worldRenderer.setTranslation(-x - 0.5F, -j, -k - 0.5F);
                BlockRendererDispatcher blockRendererDispatcher =
                        MinecraftClient.getMinecraft().getBlockRendererDispatcher();
                IBakedModel iBakedModel =
                        blockRendererDispatcher.getModelFromBlockState(
                                blockState, world, (BlockPos) null);
                blockRendererDispatcher
                        .getBlockModelRenderer()
                        .renderBlockModel(
                                world, iBakedModel, blockState, blockPos, worldRenderer, false);
                worldRenderer.setTranslation(0.0, 0.0, 0.0);
                instance.draw();
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
                super.doRender(entityFallingBlock, d0, d1, d2, f, f1);
            }
        }
    }

    public RenderFallingBlock(RenderManager renderManagerValue) {
        super(renderManagerValue);
        this.shadowSize = 0.5F;
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityFallingBlock) entity);
    }

    public void doRender(
            Entity entity,
            double p171_1,
            double p171_2,
            double p171_3,
            float p171_4,
            float p171_5) {
        this.doRender((net.minecraft.entity.item.EntityFallingBlock) entity, p171_1, p171_2, p171_3, p171_4, p171_5);
    }
}
