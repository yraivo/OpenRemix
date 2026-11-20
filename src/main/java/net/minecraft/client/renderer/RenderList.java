package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumWorldBlockLayer;
import optifine.OptiFineConfig;

import org.lwjgl.opengl.GL11;

public class RenderList extends ChunkRenderContainer {
    public void renderChunkLayer(EnumWorldBlockLayer enumWorldBlockLayer) {
        int i = WorldRenderer.getLegacyRenderFlowToken();
        if (this.initialized) {
            if (this.renderChunks.size() == 0) {
                return;
            }

            for (java.util.Iterator<?> __recoveryIterator171_420 = (this.renderChunks).iterator();
                    (__recoveryIterator171_420).hasNext(); ) {
                RenderChunk renderChunk = (RenderChunk) (__recoveryIterator171_420).next();
                ListedRenderChunk listedRenderChunk =
                        (ListedRenderChunk) renderChunk;
                GlStateManager.pushMatrix();
                this.preRenderChunk(renderChunk);
                GL11.glCallList(
                        listedRenderChunk.getDisplayList(
                                enumWorldBlockLayer,
                                listedRenderChunk.getCompiledChunk()));
                GlStateManager.popMatrix();
                if (i != 0) {
                    break;
                }
            }

            if (OptiFineConfig.isMultiTexture()) {
                GlStateManager.bindCurrentTexture();
            }

            GlStateManager.resetColor();
            this.renderChunks.clear();
        }
    }
}
