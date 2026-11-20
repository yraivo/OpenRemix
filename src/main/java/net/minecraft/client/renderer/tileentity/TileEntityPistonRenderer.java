package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonExtensionEnumPistonType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TileEntityPistonRenderer extends TileEntitySpecialRenderer {
    private final BlockRendererDispatcher blockRenderer =
            MinecraftClient.getMinecraft().getBlockRendererDispatcher();

    public void method_a03103d1b16984b37f77(
            TileEntityPiston tileEntityPiston, double d0, double d1, double d2, float f, int i) {
        BlockPos blockPos = tileEntityPiston.getPos();
        TileEntitySpecialRenderer.getLegacyTileRendererState();

        BlockState blockState = tileEntityPiston.getPistonState();
        Block block = blockState.getBlock();

        if (block.getMaterial() != Material.AIR && tileEntityPiston.getProgress(f) < 1.0F) {
            Tessellator tessellator;
            WorldRenderer worldRenderer;
            {
                tessellator = Tessellator.getInstance();
                worldRenderer = tessellator.getWorldRenderer();
                this.bindTexture(TextureMap.locationBlocksTexture);
                RenderHelper.disableStandardItemLighting();
                GlStateManager.blendFunc(770, 771);
                GlStateManager.enableBlend();
                GlStateManager.disableCull();
                if (MinecraftClient.isAmbientOcclusionEnabled()) {
                    GlStateManager.shadeModel(7425);

                    
                } else {

                    GlStateManager.shadeModel(7424);
                }
            }

            {
                worldRenderer.startDrawingQuads();
                worldRenderer.setVertexFormat(DefaultVertexFormats.BLOCK);
                worldRenderer.setTranslation(
                        (float) d0
                                - blockPos.getX()
                                + tileEntityPiston.getOffsetX(f),
                        (float) d1
                                - blockPos.getY()
                                + tileEntityPiston.getOffsetY(f),
                        (float) d2
                                - blockPos.getZ()
                                + tileEntityPiston.getOffsetZ(f));
                worldRenderer.setOpaqueVertexColorFromFloats(1.0F, 1.0F, 1.0F);
                World world = this.getWorld();
                if (block == Blocks.piston_head && tileEntityPiston.getProgress(f) < 0.5F) {
                    blockState = blockState.withProperty(BlockPistonExtension.SHORT, true);
                    this.blockRenderer
                            .getBlockModelRenderer()
                            .renderBlockModel(
                                    world,
                                    this.blockRenderer.getModelFromBlockState(
                                            blockState, world, blockPos),
                                    blockState,
                                    blockPos,
                                    worldRenderer,
                                    true);

                    
                } else {

                    if (tileEntityPiston.shouldPistonHeadBeRendered()
                            && !tileEntityPiston.isExtending()) {
                        BlockPistonExtensionEnumPistonType blockPistonExtensionEnumPistonType =
                                block == Blocks.sticky_piston
                                        ? BlockPistonExtensionEnumPistonType.STICKY
                                        : BlockPistonExtensionEnumPistonType.DEFAULT;
                        BlockState blockStateValue =
                                Blocks.piston_head
                                        .getDefaultState()
                                        .withProperty(
                                                BlockPistonExtension.TYPE,
                                                blockPistonExtensionEnumPistonType)
                                        .withProperty(
                                                BlockPistonExtension.FACING,
                                                blockState.getValue(BlockPistonBase.FACING));
                        blockStateValue =
                                blockStateValue.withProperty(
                                        BlockPistonExtension.SHORT,
                                        tileEntityPiston.getProgress(f) >= 0.5F);
                        this.blockRenderer
                                .getBlockModelRenderer()
                                .renderBlockModel(
                                        world,
                                        this.blockRenderer.getModelFromBlockState(
                                                blockStateValue, world, blockPos),
                                        blockStateValue,
                                        blockPos,
                                        worldRenderer,
                                        true);
                        worldRenderer.setTranslation(
                                (float) d0 - blockPos.getX(),
                                (float) d1 - blockPos.getY(),
                                (float) d2 - blockPos.getZ());
                        blockState.withProperty(BlockPistonBase.EXTENDED, true);
                        this.blockRenderer
                                .getBlockModelRenderer()
                                .renderBlockModel(
                                        world,
                                        this.blockRenderer.getModelFromBlockState(
                                                blockState, world, blockPos),
                                        blockState,
                                        blockPos,
                                        worldRenderer,
                                        true);


                    } else {

                        this.blockRenderer
                                .getBlockModelRenderer()
                                .renderBlockModel(
                                        world,
                                        this.blockRenderer.getModelFromBlockState(
                                                blockState, world, blockPos),
                                        blockState,
                                        blockPos,
                                        worldRenderer,
                                        false);
                    }
                }
            }

            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            tessellator.draw();
            RenderHelper.enableStandardItemLighting();
        }
    }

    public void renderTileEntityAt(
            TileEntity tileEntity,
            double p171_1,
            double p171_2,
            double p171_3,
            float p171_4,
            int p171_5) {
        this.method_a03103d1b16984b37f77(
                (net.minecraft.tileentity.TileEntityPiston) tileEntity, p171_1, p171_2, p171_3, p171_4, p171_5);
    }
}
