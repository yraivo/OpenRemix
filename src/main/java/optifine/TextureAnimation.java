package optifine;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Properties;

public class TextureAnimation {
    byte[] srcData;

    private ByteBuffer imageData;
    private int activeFrame;
    private int frameHeight;
    ResourceLocation dstTexLoc;

    private int dstY;
    private int dstTextId;
    private String b;
    private int frameWidth;
    private String a;
    private TextureAnimationFrame[] frames;

    private int dstX;

    public boolean updateTexture() {
        if (this.dstTextId < 0) {
            ITextureObject iTextureObject = TextureUtils.getTexture(this.dstTexLoc);
            if (iTextureObject == null) {
                return false;
            }

            this.dstTextId = iTextureObject.getGlTextureId();
        }

        if (this.imageData == null) {
            this.imageData = GLAllocation.createDirectByteBuffer(this.srcData.length);
            this.imageData.put(this.srcData);
            this.srcData = null;
        }

        if (!this.nextFrame()) {
            return false;
        }

        int k = this.frameWidth * this.frameHeight * 4;
        int activeFrameIndex = this.getActiveFrameIndex();
        int j = k * activeFrameIndex;
        if (j + k > this.imageData.capacity()) {
            return false;
        }

        ((Buffer) this.imageData).position(j);
        GlStateManager.bindTexture(this.dstTextId);
        GL11.glTexSubImage2D(
                3553,
                0,
                this.dstX,
                this.dstY,
                this.frameWidth,
                this.frameHeight,
                6408,
                5121,
                this.imageData);
        return true;
    }

    public ResourceLocation getDstTexLoc() {
        return this.dstTexLoc;
    }

    public TextureAnimation(
            String text,
            byte[] abyte,
            String textValue,
            ResourceLocation resourceLocation,
            int dstXValue,
            int j,
            int k,
            int l,
            Properties properties,
            int i1) {
        int j3 = ReflectorMethod.cR();
        this.a = null;
        this.b = null;
        int j1 = j3;
        this.dstTexLoc = null;
        this.dstTextId = -1;
        this.dstX = 0;
        this.dstY = 0;
        this.frameWidth = 0;
        this.frameHeight = 0;
        this.frames = null;
        this.activeFrame = 0;
        this.srcData = null;
        this.imageData = null;
        this.a = text;
        this.b = textValue;
        this.dstTexLoc = resourceLocation;
        this.dstX = dstXValue;
        this.dstY = j;
        this.frameWidth = k;
        this.frameHeight = l;
        int k1 = k * l * 4;
        if (abyte.length % k1 != 0) {
            OptiFineConfig.warn(
                    "Invalid animated texture length: "
                            + abyte.length
                            + ", frameWidth: "
                            + k
                            + ", frameHeight: "
                            + l);
        }

        this.srcData = abyte;
        int l1 = abyte.length / k1;
        if (properties.get("tile.0") != null) {
            int i2 = 0;

            while (properties.get("tile." + i2) != null) {
                l1 = i2 + 1;
                i2++;
                if (j1 == 0) {
                    break;
                }
            }
        }

        String textValue2 = (String) properties.get("duration");
        int j2 = OptiFineConfig.parseInt(textValue2, i1);
        this.frames = new TextureAnimationFrame[l1];
        int k2 = 0;

        while (k2 < this.frames.length) {
            String textValue3 = (String) properties.get("tile." + k2);
            int l2 = OptiFineConfig.parseInt(textValue3, k2);
            String textValue4 = (String) properties.get("duration." + k2);
            int i3 = OptiFineConfig.parseInt(textValue4, j2);
            TextureAnimationFrame textureAnimationFrame = new TextureAnimationFrame(l2, i3);
            this.frames[k2] = textureAnimationFrame;
            k2++;
            if (j1 == 0) {
                break;
            }
        }
    }


    public int getFrameCount() {
        return this.frames.length;
    }

    public int getActiveFrameIndex() {
        if (this.frames.length <= 0) {
            return 0;
        }

        if (this.activeFrame >= this.frames.length) {
            this.activeFrame = 0;
        }

        TextureAnimationFrame textureAnimationFrame = this.frames[this.activeFrame];
        return textureAnimationFrame.index;
    }

    public String q() {
        return this.a;
    }

    public String fg() {
        return this.b;
    }

    public boolean nextFrame() {
        if (this.frames.length <= 0) {
            return false;
        }

        if (this.activeFrame >= this.frames.length) {
            this.activeFrame = 0;
        }

        TextureAnimationFrame textureAnimationFrame = this.frames[this.activeFrame];
        textureAnimationFrame.counter++;
        if (textureAnimationFrame.counter < textureAnimationFrame.duration) {
            return false;
        }

        textureAnimationFrame.counter = 0;
        this.activeFrame++;
        if (this.activeFrame >= this.frames.length) {
            this.activeFrame = 0;
        }

        return true;
    }
}
