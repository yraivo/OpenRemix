package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElementEnumUseage;

import optifine.OptiFineConfig;
import optifine.Reflector;

import org.lwjgl.opengl.GL11;

import shadersmod.client.SVertexBuilder;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

public class WorldVertexBufferUploader {
    public int draw(WorldRenderer worldRenderer, int i) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        if (i > 0) {
            VertexFormat vertexFormat = worldRenderer.getVertexFormat();
            int k = vertexFormat.getNextOffset();
            ByteBuffer bytebuffer = worldRenderer.getByteBuffer();
            List elements = vertexFormat.getElements();
            Iterator iterator = elements.iterator();
            boolean flag = Reflector.FU.exists();
            boolean flag1 = Reflector.Iu.exists();

            while (iterator.hasNext()) {
                VertexFormatElement vertexFormatElement = (VertexFormatElement) iterator.next();
                VertexFormatElementEnumUseage vertexFormatElementEnumUseage =
                        vertexFormatElement.getUsage();
                if (flag) {
                    Reflector.callVoid(
                            vertexFormatElementEnumUseage,
                            Reflector.FU,
                            new Object[] {vertexFormatElement, k, bytebuffer});
                    if (j == 0) {
                        continue;
                    }
                }

                int l = vertexFormatElement.getType().getGlConstant();
                int i1 = vertexFormatElement.getIndex();
                switch (vertexFormatElementEnumUseage) {
                    case POSITION:
                        ((Buffer) bytebuffer).position(vertexFormatElement.getByteOffset());
                        GL11.glVertexPointer(
                                vertexFormatElement.getElementCount(), l, k, bytebuffer);
                        GL11.glEnableClientState(32884);
                        if (j == 0) {
                            break;
                        }
                    case UV:
                        ((Buffer) bytebuffer).position(vertexFormatElement.getByteOffset());
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + i1);
                        GL11.glTexCoordPointer(
                                vertexFormatElement.getElementCount(), l, k, bytebuffer);
                        GL11.glEnableClientState(32888);
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                        if (j == 0) {
                            break;
                        }
                    case COLOR:
                        ((Buffer) bytebuffer).position(vertexFormatElement.getByteOffset());
                        GL11.glColorPointer(
                                vertexFormatElement.getElementCount(), l, k, bytebuffer);
                        GL11.glEnableClientState(32886);
                        if (j == 0) {
                            break;
                        }
                    case NORMAL:
                        ((Buffer) bytebuffer).position(vertexFormatElement.getByteOffset());
                        GL11.glNormalPointer(l, k, bytebuffer);
                        GL11.glEnableClientState(32885);
                }

                if (j != 0) {
                    break;
                }
            }

            label78:
            {
                if (worldRenderer.isMultiTexture()) {
                    worldRenderer.drawMultiTexture();
                    if (j == 0) {
                        break label78;
                    }
                }

                if (OptiFineConfig.isShaders()) {
                    SVertexBuilder.drawArrays(
                            worldRenderer.getDrawMode(),
                            0,
                            worldRenderer.getVertexCount(),
                            worldRenderer);
                    if (j == 0) {
                        break label78;
                    }
                }

                GL11.glDrawArrays(worldRenderer.getDrawMode(), 0, worldRenderer.getVertexCount());
            }

            for (java.util.Iterator<?> __recoveryIterator171_4223 = (elements).iterator();
                    (__recoveryIterator171_4223).hasNext(); ) {
                VertexFormatElement vertexFormatElementValue =
                        (VertexFormatElement) (__recoveryIterator171_4223).next();
                VertexFormatElementEnumUseage vertexFormatElementEnumUseageValue =
                        vertexFormatElementValue.getUsage();
                if (flag1) {
                    Reflector.callVoid(
                            vertexFormatElementEnumUseageValue,
                            Reflector.Iu,
                            new Object[] {vertexFormatElementValue, k, bytebuffer});
                    if (j == 0) {
                        continue;
                    }
                }

                int j1 = vertexFormatElementValue.getIndex();
                switch (vertexFormatElementEnumUseageValue) {
                    case POSITION:
                        GL11.glDisableClientState(32884);
                        if (j == 0) {
                            break;
                        }
                    case UV:
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + j1);
                        GL11.glDisableClientState(32888);
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                        if (j == 0) {
                            break;
                        }
                    case COLOR:
                        GL11.glDisableClientState(32886);
                        GlStateManager.resetColor();
                        if (j == 0) {
                            break;
                        }
                    case NORMAL:
                        GL11.glDisableClientState(32885);
                }

                if (j != 0) {
                    break;
                }
            }
        }

        worldRenderer.reset();
        return i;
    }
}
