package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.EnumWorldBlockLayer;
import optifine.OptiFineConfig;
import shadersmod.client.ShadersRender;

import org.lwjgl.opengl.GL11;

public class VboRenderList extends ChunkRenderContainer {
    private void setupArrayPointers() {
        int i = WorldRenderer.getLegacyRenderFlowMarker();
        if (OptiFineConfig.isShaders()) {
            ShadersRender.setupArrayPointersVbo();
            if (i != 0) {
                return;
            }
        }

        GL11.glVertexPointer(3, 5126, 28, 0L);

        GL11.glColorPointer(4, 5121, 28, 12L);

        GL11.glTexCoordPointer(2, 5126, 28, 16L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);

        GL11.glTexCoordPointer(2, 5122, 28, 24L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public void renderChunkLayer(EnumWorldBlockLayer enumWorldBlockLayer) {
        int i = WorldRenderer.getLegacyRenderFlowToken();
        if (this.initialized) {
            for (java.util.Iterator<?> __recoveryIterator171_1301 = (this.renderChunks).iterator();
                    (__recoveryIterator171_1301).hasNext(); ) {
                RenderChunk renderChunk = (RenderChunk) (__recoveryIterator171_1301).next();
                VertexBuffer vertexBuffer =
                        renderChunk.getVertexBufferByLayer(enumWorldBlockLayer.ordinal());
                GlStateManager.pushMatrix();
                this.preRenderChunk(renderChunk);
                renderChunk.multModelviewMatrix();
                vertexBuffer.bindBuffer();
                this.setupArrayPointers();
                vertexBuffer.drawArrays(7);
                GlStateManager.popMatrix();
                if (i != 0) {
                    break;
                }
            }

            OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
            GlStateManager.resetColor();
            this.renderChunks.clear();
        }
    }
}
