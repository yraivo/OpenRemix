package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElementEnumType;
import net.minecraft.client.renderer.vertex.VertexFormatElementEnumUseage;
import net.minecraft.client.util.QuadComparator;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;

import optifine.OptiFineConfig;
import optifine.TextureUtils;

import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL11;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.compat.legacy.auth.AuthVerificationOutcome;
import remix.compat.legacy.auth.DefaultClientAuthCoordinator;
import remix.module.render.XrayModule;

import shadersmod.client.SVertexBuilder;

import java.awt.Color;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class WorldRenderer {
    private int packedColor;
    private TextureAtlasSprite[] quadSprites;
    public int vertexCount;
    private double xOffset;
    private int packedLightmap;
    public FloatBuffer rawFloatBuffer;
    public int rawBufferIndex;
    private double textureU;
    private double zOffset;

    public IntBuffer rawIntBuffer;
    private static int legacyRenderFlowMarker;
    public int drawMode;
    private boolean colorDisabled;
    private TextureAtlasSprite[] reusableQuadSprites;
    private double yOffset;
    private TextureAtlasSprite currentQuadSprite;
    private int packedNormal;
    private ByteBuffer byteBuffer;
    private boolean[] drawnSpriteFlags;
    private EnumWorldBlockLayer blockLayer = null;
    private boolean isDrawing;
    private int bufferSize;

    private int drawByteCount;
    public SVertexBuilder sVertexBuilder;
    private VertexFormat vertexFormat;
    private double textureV;

    private void drawQuadRange(int startQuad, int endQuad) {
        int count = endQuad - startQuad;
        if (count > 0) {
            GL11.glDrawArrays(this.drawMode, startQuad * 4, count * 4);
        }
    }

    public void setVertexFormat(VertexFormat vertexFormatValue) {
        try {
            this.vertexFormat =
                    new net.minecraft.client.renderer.vertex.VertexFormat(vertexFormatValue);
            if (optifine.OptiFineConfig.isShaders()) {
                shadersmod.client.SVertexBuilder.endSetVertexFormat(this);
            }
        } catch (java.lang.IllegalStateException illegalstateexception) {
            throw illegalstateexception;
        }
    }

    private int getVertexCapacity() {
        return this.rawIntBuffer.capacity() * 4 / (this.vertexFormat.getNextOffset() * 4);
    }

    public void ensureVertexCapacity() {
        try {
            if (this.rawBufferIndex >= this.bufferSize - this.vertexFormat.getNextOffset()) {
                this.growBuffer(2097152);
            }
        } catch (java.lang.IllegalStateException illegalstateexception) {
            throw illegalstateexception;
        }
    }

    public void setOpaqueVertexColorFromFloats(float f, float f1, float f2) {
        this.setOpaqueVertexColor((int) (f * 255.0F), (int) (f1 * 255.0F), (int) (f2 * 255.0F));
    }

    public void putSprite(TextureAtlasSprite textureAtlasSprite) {
        if (this.quadSprites != null) {
            int i = this.vertexCount / 4;
            this.quadSprites[i - 1] = textureAtlasSprite;
        }
    }

    public void startDrawingQuads() {
        this.startDrawing(7);
    }

    public void startDrawing(int drawModeValue) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already building!");
        }

        this.isDrawing = true;
        this.reset();
        this.drawMode = drawModeValue;
        this.colorDisabled = false;
    }

    public int getVertexCount() {
        return this.vertexCount;
    }

    public boolean isMultiTexture() {
        try {
            if (this.quadSprites != null) {
                return true;
            }
        } catch (java.lang.IllegalStateException illegalstateexception) {
            throw illegalstateexception;
        }

        return false;
    }

    public int getColorIndex(int p171_0) {
        return ((this.vertexCount - p171_0) * this.vertexFormat.getNextOffset()
                        + this.vertexFormat.getColorOffset())
                / 4;
    }

    public void setSprite(TextureAtlasSprite aAoValue) {
        try {
            if (this.quadSprites != null) {
                this.currentQuadSprite = aAoValue;
            }
        } catch (java.lang.IllegalStateException illegalstateexception) {
            throw illegalstateexception;
        }
    }

    public void putPosition(double d0, double d1, double d2) {
        int i = getLegacyRenderFlowMarker();
        if (this.rawBufferIndex >= this.bufferSize - this.vertexFormat.getNextOffset()) {
            this.growBuffer(2097152);
        }

        int j = this.vertexFormat.getNextOffset() / 4;
        int k = (this.vertexCount - 4) * j;
        int l = 0;

        while (l < 4) {
            int i1 = k + l * j;
            int j1 = i1 + 1;
            int k1 = j1 + 1;
            this.rawIntBuffer.put(
                    i1,
                    Float.floatToRawIntBits(
                            (float) (d0 + this.xOffset)
                                    + Float.intBitsToFloat(this.rawIntBuffer.get(i1))));
            this.rawIntBuffer.put(
                    j1,
                    Float.floatToRawIntBits(
                            (float) (d1 + this.yOffset)
                                    + Float.intBitsToFloat(this.rawIntBuffer.get(j1))));
            this.rawIntBuffer.put(
                    k1,
                    Float.floatToRawIntBits(
                            (float) (d2 + this.zOffset)
                                    + Float.intBitsToFloat(this.rawIntBuffer.get(k1))));
            l++;
            if (i == 0) {
                break;
            }
        }
    }

    public void reset() {
        boolean keepNewSpriteArray = getLegacyRenderFlowMarker() != 0;
        this.vertexCount = 0;
        this.rawBufferIndex = 0;
        this.vertexFormat.clear();
        this.vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.FLOAT,
                        VertexFormatElementEnumUseage.POSITION,
                        3));
        this.resetQuadSprites(keepNewSpriteArray);
        this.currentQuadSprite = null;
    }

    private void resetQuadSprites(boolean keepNewSpriteArray) {
        if (this.blockLayer != null && OptiFineConfig.isMultiTexture()) {
            if (this.quadSprites == null) {
                this.quadSprites = this.reusableQuadSprites;
            }
            if (this.quadSprites != null && this.quadSprites.length >= this.getVertexCapacity()) {
                return;
            }
            this.quadSprites = new TextureAtlasSprite[this.getVertexCapacity()];
            if (keepNewSpriteArray) {
                return;
            }
        }
        if (this.quadSprites != null) {
            this.reusableQuadSprites = this.quadSprites;
        }
        this.quadSprites = null;
    }

    public static int getLegacyRenderFlowMarker() {
        return net.minecraft.client.renderer.WorldRenderer.legacyRenderFlowMarker;
    }

    public void setVertexColorFromFloats(float p171_0, float p171_1, float p171_2, float p171_3) {
        this.setVertexColor(
                (int) (p171_0 * 255.0F),
                (int) (p171_1 * 255.0F),
                (int) (p171_2 * 255.0F),
                (int) (p171_3 * 255.0F));
    }

    public void disableColor() {
        this.colorDisabled = true;
    }

    public void setOpaqueVertexColorFromRgb(int p171_0) {
        int j = p171_0 >> 16 & 0xFF;
        int k = p171_0 >> 8 & 0xFF;
        int l = p171_0 & 0xFF;
        this.setOpaqueVertexColor(j, k, l);
    }

    public void putColorRGB_F(
            float redMultiplier, float greenMultiplier, float blueMultiplier, int vertexIndex) {
        boolean nativeOrderOnly = getLegacyRenderFlowMarker() != 0;
        int bufferIndex = this.getColorIndex(vertexIndex);
        if (this.colorDisabled) {
            this.rawIntBuffer.put(bufferIndex, -1);
            return;
        }

        int color = this.rawIntBuffer.get(bufferIndex);
        boolean littleEndian = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
        if (littleEndian) {
            color =
                    this.tintLittleEndianVertex(
                            color, redMultiplier, greenMultiplier, blueMultiplier);
        }
        if (!littleEndian || !nativeOrderOnly) {
            int red = (int) ((color >> 24 & 0xFF) * redMultiplier);
            int green = (int) ((color >> 16 & 0xFF) * greenMultiplier);
            int blue = (int) ((color >> 8 & 0xFF) * blueMultiplier);
            color = (color & 0xFF) | red << 24 | green << 16 | blue << 8;
        }
        this.rawIntBuffer.put(bufferIndex, color);
    }

    private int tintLittleEndianVertex(
            int color, float redMultiplier, float greenMultiplier, float blueMultiplier) {
        RemixClient.INSTANCE.getClientServices();
        XrayModule xray = ClientServices.moduleManager.xray;
        float opacity = (float) xray.opacitySetting.asDouble();
        int red = (int) ((color & 0xFF) * redMultiplier);
        int green = (int) ((color >> 8 & 0xFF) * greenMultiplier);
        int blue = (int) ((color >> 16 & 0xFF) * blueMultiplier);
        int alphaMask =
                xray.isEnabled()
                        ? withAlpha(0, opacity == 0.0F ? 0.0F : opacity / 100.0F)
                        : 0xFF000000;
        return (color & alphaMask) | blue << 16 | green << 8 | red;
    }

    public void drawMultiTexture() {
        boolean fullSpritePass = getLegacyRenderFlowMarker() != 0;
        TextureAtlasSprite[] sprites = this.quadSprites;
        if (sprites == null) {
            return;
        }

        int spriteCount =
                OptiFineConfig.getMinecraft().getTextureMapBlocks().getRegisteredSpriteCount();
        if (this.drawnSpriteFlags.length <= spriteCount) {
            this.drawnSpriteFlags = new boolean[spriteCount + 1];
        }
        Arrays.fill(this.drawnSpriteFlags, false);
        boolean[] drawn = this.drawnSpriteFlags;
        int grassOverlayStart = -1;
        int quadCount = this.vertexCount / 4;
        for (int quad = 0; quad < quadCount; quad++) {
            TextureAtlasSprite sprite = sprites[quad];
            if (sprite != null) {
                int spriteIndex = sprite.getIndexInMap();
                if (!drawn[spriteIndex]) {
                    boolean grassOverlay = sprite == TextureUtils.iconGrassSideOverlay;
                    boolean firstGrassOverlay = grassOverlay && grassOverlayStart < 0;
                    if (firstGrassOverlay) {
                        grassOverlayStart = quad;
                    }
                    if (!grassOverlay || (firstGrassOverlay && !fullSpritePass)) {
                        quad = this.drawSpriteRuns(sprite, quad) - 1;
                        if (this.blockLayer != EnumWorldBlockLayer.TRANSLUCENT) {
                            drawn[spriteIndex] = true;
                        }
                    }
                }
            }
            if (!fullSpritePass) {
                break;
            }
        }
        if (grassOverlayStart >= 0) {
            this.drawSpriteRuns(TextureUtils.iconGrassSideOverlay, grassOverlayStart);
        }
    }

    public void putBrightness4(int p171_0, int p171_1, int p171_2, int p171_3) {
        int i1 =
                (this.vertexCount - 4) * (this.vertexFormat.getNextOffset() / 4)
                        + this.vertexFormat.getUvOffsetById(1) / 4;
        int j1 = this.vertexFormat.getNextOffset() >> 2;
        this.rawIntBuffer.put(i1, p171_0);
        this.rawIntBuffer.put(i1 + j1, p171_1);
        this.rawIntBuffer.put(i1 + j1 * 2, p171_2);
        this.rawIntBuffer.put(i1 + j1 * 3, p171_3);
    }

    public void setVertexNormal(float normalX, float normalY, float normalZ) {
        if (!this.vertexFormat.hasNormal()) {
            VertexFormatElement vertexFormatElement =
                    new VertexFormatElement(
                            0,
                            VertexFormatElementEnumType.BYTE,
                            VertexFormatElementEnumUseage.NORMAL,
                            3);
            this.vertexFormat.addElement(vertexFormatElement);
            this.vertexFormat.addElement(
                    new VertexFormatElement(
                            0,
                            VertexFormatElementEnumType.UBYTE,
                            VertexFormatElementEnumUseage.PADDING,
                            1));
        }

        byte x = (byte) (normalX * 127.0F);
        byte y = (byte) (normalY * 127.0F);
        byte z = (byte) (normalZ * 127.0F);
        this.packedNormal = x & 255 | (y & 255) << 8 | (z & 255) << 16;
    }

    public boolean isColorDisabled() {
        return this.colorDisabled;
    }

    static {
        setLegacyRenderFlowMarker(66);
    }

    // region String decoding support

    // endregion

    public static int withAlpha(int i, float f) {
        Color color = new Color(i);
        float f1 = 0.003921569F * color.getRed();
        float f2 = 0.003921569F * color.getGreen();
        float f3 = 0.003921569F * color.getBlue();
        return new Color(f1, f2, f3, f).getRGB();
    }

    private void growBuffer(int i) {
        if (OptiFineConfig.isShaders()) {
            i *= 2;
        }

        LogManager.getLogger()
                .warn(
                        "Needed to grow BufferBuilder buffer: Old size "
                                + this.bufferSize * 4
                                + " bytes, new size "
                                + (this.bufferSize * 4 + i)
                                + " bytes.");
        this.bufferSize += i / 4;
        ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer(this.bufferSize * 4);
        ((Buffer) this.rawIntBuffer).position(0);
        bytebuffer.asIntBuffer().put(this.rawIntBuffer);
        this.byteBuffer = bytebuffer;
        this.rawIntBuffer = this.byteBuffer.asIntBuffer();
        this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
        if (this.quadSprites != null) {
            TextureAtlasSprite[] textureAtlasSpriteArray = this.quadSprites;
            int j = this.getVertexCapacity();
            this.quadSprites = new TextureAtlasSprite[j];
            System.arraycopy(
                    textureAtlasSpriteArray,
                    0,
                    this.quadSprites,
                    0,
                    Math.min(textureAtlasSpriteArray.length, this.quadSprites.length));
            this.reusableQuadSprites = null;
        }
    }

    public void addVertexWithUV(double d0, double d1, double d2, double d3, double d4) {
        double d5 =
                ClientServices.moduleManager != null
                                && DefaultClientAuthCoordinator.lastVerificationOutcome != null
                                && DefaultClientAuthCoordinator.lastVerificationOutcome
                                        != AuthVerificationOutcome.FAILURE_1
                        ? 1.0
                        : 0.0;
        TextureAtlasSprite aAoValue = this.currentQuadSprite;
        if (aAoValue != null && this.quadSprites != null) {
            d3 = aAoValue.toSingleU((float) d3);
            d4 = aAoValue.toSingleV((float) d4);
            this.quadSprites[this.vertexCount / 4] = aAoValue;
        }

        this.setTextureUV(d3 - d5, d4 - d5);
        this.addVertex(d0, d1 + d5, d2 + d5);
    }

    public void setTextureUV(double d0, double d1) {
        if (!this.vertexFormat.hasUvOffset(0) && !this.vertexFormat.hasUvOffset(1)) {
            VertexFormatElement vertexFormatElement =
                    new VertexFormatElement(
                            0,
                            VertexFormatElementEnumType.FLOAT,
                            VertexFormatElementEnumUseage.UV,
                            2);
            this.vertexFormat.addElement(vertexFormatElement);
        }

        this.textureU = d0;
        this.textureV = d1;
    }

    public void setOpaqueVertexColor(int p171_0, int p171_1, int p171_2) {
        this.setVertexColor(p171_0, p171_1, p171_2, 255);
    }

    public void putColorMultiplier(
            float redMultiplier, float greenMultiplier, float blueMultiplier, int vertexIndex) {
        boolean legacyColorOrder = getLegacyRenderFlowToken() != 0;
        int bufferIndex = this.getColorIndex(vertexIndex);
        int color = this.rawIntBuffer.get(bufferIndex);
        boolean littleEndian = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
        if (littleEndian) {
            int red = (int) ((color & 0xFF) * redMultiplier);
            int green = (int) ((color >> 8 & 0xFF) * greenMultiplier);
            int blue = (int) ((color >> 16 & 0xFF) * blueMultiplier);
            color = color & 0xFF000000 | blue << 16 | green << 8 | red;
        }
        if (!littleEndian || legacyColorOrder) {
            int red = (int) ((this.packedColor >> 24 & 0xFF) * redMultiplier);
            int green = (int) ((this.packedColor >> 16 & 0xFF) * greenMultiplier);
            int blue = (int) ((this.packedColor >> 8 & 0xFF) * blueMultiplier);
            color = color & 0xFF | red << 24 | green << 16 | blue << 8;
        }
        this.rawIntBuffer.put(bufferIndex, this.colorDisabled ? -1 : color);
    }

    public VertexFormat getVertexFormat() {
        return this.vertexFormat;
    }

    public void setTranslation(double xOffsetValue, double yOffsetValue, double zOffsetValue) {
        this.xOffset = xOffsetValue;
        this.yOffset = yOffsetValue;
        this.zOffset = zOffsetValue;
    }

    public ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }

    public void addVertex(double d0, double d1, double d2) {
        int k1 = getLegacyRenderFlowMarker();
        boolean shaders = OptiFineConfig.isShaders();
        int i = k1;
        if (shaders) {
            SVertexBuilder.beginAddVertex(this);
        }

        VertexFormat aAtValue = this.vertexFormat;
        if (this.rawBufferIndex >= this.bufferSize - aAtValue.getNextOffset()) {
            this.growBuffer(2097152);
        }

        List elements = aAtValue.getElements();
        int j = elements.size();
        double yOffsetValue = this.yOffset;
        double xOffsetValue = this.xOffset;
        double zOffsetValue = this.zOffset;
        int k = this.rawBufferIndex;
        IntBuffer intbuffer = this.rawIntBuffer;
        int l = 0;

        while (l < j) {
            VertexFormatElement vertexFormatElement = (VertexFormatElement) elements.get(l);
            int i1 = vertexFormatElement.getByteOffset() >> 2;
            int j1 = k + i1;
            switch (vertexFormatElement.getUsage()) {
                case POSITION:
                    intbuffer.put(j1, Float.floatToRawIntBits((float) (d0 + xOffsetValue)));
                    intbuffer.put(j1 + 1, Float.floatToRawIntBits((float) (d1 + yOffsetValue)));
                    intbuffer.put(j1 + 2, Float.floatToRawIntBits((float) (d2 + zOffsetValue)));
                    if (i != 0) {
                        break;
                    }
                case COLOR:
                    intbuffer.put(j1, this.packedColor);
                    if (i != 0) {
                        break;
                    }
                case UV:
                    if (vertexFormatElement.getIndex() == 0) {
                        intbuffer.put(j1, Float.floatToRawIntBits((float) this.textureU));
                        intbuffer.put(j1 + 1, Float.floatToRawIntBits((float) this.textureV));
                        if (i != 0) {
                            break;
                        }
                    }

                    intbuffer.put(j1, this.packedLightmap);
                    if (i != 0) {
                        break;
                    }
                case NORMAL:
                    intbuffer.put(j1, this.packedNormal);
            }

            l++;
            if (i == 0) {
                break;
            }
        }

        this.rawBufferIndex = this.rawBufferIndex + (aAtValue.getNextOffset() >> 2);
        this.vertexCount++;
        if (shaders) {
            SVertexBuilder.endAddVertex(this);
        }
    }

    public WorldRendererState getVertexState(float f, float f1, float f2) {
        int[] aint = new int[this.rawBufferIndex];
        PriorityQueue priorityqueue =
                new PriorityQueue(
                        this.rawBufferIndex,
                        new QuadComparator(
                                this.rawFloatBuffer,
                                (float) (f + this.xOffset),
                                (float) (f1 + this.yOffset),
                                (float) (f2 + this.zOffset),
                                this.vertexFormat.getNextOffset() / 4));
        int l1 = getLegacyRenderFlowToken();
        int j = this.vertexFormat.getNextOffset();
        int i = l1;
        TextureAtlasSprite[] textureAtlasSpriteArray = null;
        int k = this.vertexFormat.getNextOffset() / 4 * 4;
        if (this.quadSprites != null) {
            textureAtlasSpriteArray = new TextureAtlasSprite[this.vertexCount / 4];
        }

        int l = 0;

        while (l < this.rawBufferIndex) {
            priorityqueue.add(l);
            l += j;
            if (i != 0) {
                break;
            }
        }

        l = 0;

        while (!priorityqueue.isEmpty()) {
            int i1 = (Integer) priorityqueue.remove();
            int j1 = 0;

            while (j1 < j) {
                aint[l + j1] = this.rawIntBuffer.get(i1 + j1);
                j1++;
                if (i != 0) {
                    break;
                }
            }

            if (textureAtlasSpriteArray != null) {
                j1 = i1 / k;
                int k1 = l / k;
                textureAtlasSpriteArray[k1] = this.quadSprites[j1];
            }

            l += j;
            if (i != 0) {
                break;
            }
        }

        ((Buffer) this.rawIntBuffer).clear();
        this.rawIntBuffer.put(aint);
        if (this.quadSprites != null) {
            System.arraycopy(
                    textureAtlasSpriteArray,
                    0,
                    this.quadSprites,
                    0,
                    textureAtlasSpriteArray.length);
        }

        return new WorldRendererState(
                this,
                aint,
                this.rawBufferIndex,
                this.vertexCount,
                new VertexFormat(this.vertexFormat),
                textureAtlasSpriteArray);
    }

    public void addVertexData(int[] p171_0) {
        try {
            if (optifine.OptiFineConfig.isShaders()) {
                shadersmod.client.SVertexBuilder.beginAddVertexData(this, p171_0);
            }
        } catch (java.lang.IllegalStateException illegalstateexception1) {
            throw illegalstateexception1;
        }

        int i = this.vertexFormat.getNextOffset() / 4;

        try {
            this.vertexCount += p171_0.length / i;
            ((java.nio.Buffer) this.rawIntBuffer).position(this.rawBufferIndex);
            this.rawIntBuffer.put(p171_0);
            this.rawBufferIndex += p171_0.length;
            if (optifine.OptiFineConfig.isShaders()) {
                shadersmod.client.SVertexBuilder.endAddVertexData(this);
            }
        } catch (java.lang.IllegalStateException illegalstateexception) {
            throw illegalstateexception;
        }
    }

    private int drawSpriteRuns(TextureAtlasSprite sprite, int firstQuad) {
        boolean firstQuadOnly = getLegacyRenderFlowToken() != 0;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.glSpriteTextureId);
        int firstRunEnd = -1;
        int runStart = -1;
        int quadCount = this.vertexCount / 4;
        for (int quad = firstQuad; quad < quadCount; quad++) {
            boolean matchesSprite = this.quadSprites[quad] == sprite;
            boolean closeRun = !matchesSprite;
            if (matchesSprite && runStart < 0) {
                runStart = quad;
                closeRun = firstQuadOnly;
            }
            if (closeRun && runStart >= 0) {
                this.drawQuadRange(runStart, quad);
                if (this.blockLayer == EnumWorldBlockLayer.TRANSLUCENT) {
                    return quad;
                }
                runStart = -1;
                if (firstRunEnd < 0) {
                    firstRunEnd = quad;
                }
            }
            if (firstQuadOnly) {
                break;
            }
        }

        if (runStart >= 0) {
            this.drawQuadRange(runStart, quadCount);
        }
        return firstRunEnd < 0 ? quadCount : firstRunEnd;
    }

    public void putColorRGBA(int i, int j, int k, int l, int i1) {
        int j1 = getLegacyRenderFlowMarker();
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            this.rawIntBuffer.put(i, i1 << 24 | l << 16 | k << 8 | j);
            if (j1 != 0) {
                return;
            }
        }

        this.rawIntBuffer.put(i, j << 24 | k << 16 | l << 8 | i1);
    }

    public int getDrawMode() {
        return this.drawMode;
    }

    public WorldRenderer(int i) {
        this.drawnSpriteFlags = new boolean[256];
        this.quadSprites = null;
        this.reusableQuadSprites = null;
        this.currentQuadSprite = null;
        if (OptiFineConfig.isShaders()) {
            i *= 2;
        }

        this.bufferSize = i;
        this.byteBuffer = GLAllocation.createDirectByteBuffer(i * 4);
        this.rawIntBuffer = this.byteBuffer.asIntBuffer();
        this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
        this.vertexFormat = new VertexFormat();
        this.vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.FLOAT,
                        VertexFormatElementEnumUseage.POSITION,
                        3));
        SVertexBuilder.initVertexBuilder(this);
    }

    public int draw() {
        if (!this.isDrawing) {
            throw new IllegalStateException("Not building!");
        }

        this.isDrawing = false;
        if (this.vertexCount > 0) {
            ((Buffer) this.byteBuffer).position(0);
            ((Buffer) this.byteBuffer).limit(this.rawBufferIndex * 4);
        }

        this.drawByteCount = this.rawBufferIndex * 4;
        return this.drawByteCount;
    }

    public void putPositionFromPacked(int i) {
        int l = getLegacyRenderFlowToken();
        int k = 0;
        int j = l;

        while (k < 4) {
            this.putColor(i, k + 1);
            k++;
            if (j != 0) {
                break;
            }
        }
    }

    public static void setLegacyRenderFlowMarker(int i) {
        net.minecraft.client.renderer.WorldRenderer.legacyRenderFlowMarker = i;
    }

    public void setOpaqueVertexColorFromBytes(byte p171_0, byte p171_1, byte p171_2) {
        this.setOpaqueVertexColor(p171_0 & 255, p171_1 & 255, p171_2 & 255);
    }

    public int getDrawByteCount() {
        return this.drawByteCount;
    }

    public void putOpaqueColorAtVertex(float f, float f1, float f2, int i) {
        int j = this.getColorIndex(i);
        int k = MathHelper.clamp((int) (f * 255.0F), 0, 255);
        int l = MathHelper.clamp((int) (f1 * 255.0F), 0, 255);
        int i1 = MathHelper.clamp((int) (f2 * 255.0F), 0, 255);
        this.putColorRGBA(j, k, l, i1, 255);
    }

    public void putColorRGB_F4(float f, float f1, float f2) {
        int k = getLegacyRenderFlowMarker();
        int j = 0;
        int i = k;

        while (j < 4) {
            this.putOpaqueColorAtVertex(f, f1, f2, j + 1);
            j++;
            if (i == 0) {
                break;
            }
        }
    }

    public void setVertexState(WorldRendererState worldRendererState) {
        int i = getLegacyRenderFlowToken();
        if (worldRendererState.getRawBuffer().length > this.rawIntBuffer.capacity()) {
            this.growBuffer(2097152);
        }

        ((Buffer) this.rawIntBuffer).clear();
        this.rawIntBuffer.put(worldRendererState.getRawBuffer());
        this.rawBufferIndex = worldRendererState.getRawBufferIndex();
        this.vertexCount = worldRendererState.getVertexCount();
        this.vertexFormat = new VertexFormat(worldRendererState.getVertexFormat());
        if (worldRendererState.stateQuadSprites != null) {
            if (this.quadSprites == null) {
                this.quadSprites = this.reusableQuadSprites;
            }

            if (this.quadSprites == null || this.quadSprites.length < this.getVertexCapacity()) {
                this.quadSprites = new TextureAtlasSprite[this.getVertexCapacity()];
            }

            System.arraycopy(
                    worldRendererState.stateQuadSprites,
                    0,
                    this.quadSprites,
                    0,
                    worldRendererState.stateQuadSprites.length);
            if (i == 0) {
                return;
            }
        }

        if (this.quadSprites != null) {
            this.reusableQuadSprites = this.quadSprites;
        }

        this.quadSprites = null;
    }

    public void setPackedLightmap(int i) {
        if (!this.vertexFormat.hasUvOffset(1)) {
            if (!this.vertexFormat.hasUvOffset(0)) {
                this.vertexFormat.addElement(
                        new VertexFormatElement(
                                0,
                                VertexFormatElementEnumType.FLOAT,
                                VertexFormatElementEnumUseage.UV,
                                2));
            }

            VertexFormatElement vertexFormatElement =
                    new VertexFormatElement(
                            1,
                            VertexFormatElementEnumType.SHORT,
                            VertexFormatElementEnumUseage.UV,
                            2);
            this.vertexFormat.addElement(vertexFormatElement);
        }

        this.packedLightmap = i;
    }

    public void setVertexColor(int red, int green, int blue, int alpha) {
        boolean legacyColorOrder = getLegacyRenderFlowToken() != 0;
        if (this.colorDisabled) {
            return;
        }

        red = MathHelper.clamp(red, 0, 255);
        green = MathHelper.clamp(green, 0, 255);
        blue = MathHelper.clamp(blue, 0, 255);
        alpha = MathHelper.clamp(alpha, 0, 255);
        if (!this.vertexFormat.hasColor()) {
            this.vertexFormat.addElement(
                    new VertexFormatElement(
                            0,
                            VertexFormatElementEnumType.UBYTE,
                            VertexFormatElementEnumUseage.COLOR,
                            4));
        }

        boolean littleEndian = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
        this.packedColor =
                littleEndian && !legacyColorOrder
                        ? alpha << 24 | blue << 16 | green << 8 | red
                        : red << 24 | green << 16 | blue << 8 | alpha;
    }

    public void setVertexColorFromRgbAndAlpha(int p171_0, int p171_1) {
        int k = p171_0 >> 16 & 0xFF;
        int l = p171_0 >> 8 & 0xFF;
        int i1 = p171_0 & 0xFF;
        this.setVertexColor(k, l, i1, p171_1);
    }

    private void putColor(int p171_0, int p171_1) {
        int k = this.getColorIndex(p171_1);
        int l = p171_0 >> 16 & 0xFF;
        int i1 = p171_0 >> 8 & 0xFF;
        int j1 = p171_0 & 0xFF;
        int k1 = p171_0 >> 24 & 0xFF;
        this.putColorRGBA(k, l, i1, j1, k1);
    }

    public void setBlockLayer(EnumWorldBlockLayer aArValue) {
        this.blockLayer = aArValue;
        if (aArValue == null) {
            if (this.quadSprites != null) {
                this.reusableQuadSprites = this.quadSprites;
            }

            this.quadSprites = null;
            this.currentQuadSprite = null;
        }
    }

    public void putNormal(float p171_0, float p171_1, float p171_2) {
        byte b0 = (byte) (p171_0 * 127.0F);
        byte b1 = (byte) (p171_1 * 127.0F);
        byte b2 = (byte) (p171_2 * 127.0F);
        int i = this.vertexFormat.getNextOffset() >> 2;
        int j = (this.vertexCount - 4) * i + this.vertexFormat.getNormalOffset() / 4;
        this.packedNormal = b0 & 255 | (b1 & 255) << 8 | (b2 & 255) << 16;
        this.rawIntBuffer.put(j, this.packedNormal);
        this.rawIntBuffer.put(j + i, this.packedNormal);
        this.rawIntBuffer.put(j + i * 2, this.packedNormal);
        this.rawIntBuffer.put(j + i * 3, this.packedNormal);
    }

    public static int getLegacyRenderFlowToken() {
        int i = getLegacyRenderFlowMarker();
        return i == 0 ? 113 : 0;
    }

    // region String decoding support

    // endregion
}
