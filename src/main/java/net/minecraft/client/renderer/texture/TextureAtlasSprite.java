package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

import optifine.OptiFineConfig;

import shadersmod.client.ShadersTex;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextureAtlasSprite {
    public int yK;
    protected boolean rotated;
    public int glSpriteTextureId;

    protected int[][] interpolatedFrameData;
    public float baseV;
    private static String locationNameClock;
    private float maxV;
    protected int width;
    private float minV;
    private AnimationMetadataSection animationMetadata;
    protected int originX;

    protected int jM;
    protected int rC;
    public float baseU;
    private int indexInMap;
    public boolean xQ;
    public TextureAtlasSprite aAo;
    protected List framesTextureData = Lists.newArrayList();
    protected int height;
    private String iconName;

    public int sheetWidth;
    private float maxU;
    private float minU;
    public int sheetHeight;
    private static String locationNameCompass;
    protected int originY;

    public double getSpriteU16(float p171_0) {
        float f1 = this.maxU - this.minU;
        return (p171_0 - this.minU) / f1 * 16.0F;
    }

    public String getIconName() {
        return this.iconName;
    }

    public float getMaxV() {
        return this.maxV;
    }

    public void setFramesTextureData(List framesTextureDataValue) {
        this.framesTextureData = framesTextureDataValue;
        if (this.aAo != null) {
            this.aAo.setFramesTextureData(framesTextureDataValue);
        }
    }

    private void np() {
        try {
            this.animationMetadata = null;
            this.setFramesTextureData(com.google.common.collect.Lists.newArrayList());
            this.rC = 0;
            this.jM = 0;
            if (this.aAo != null) {
                this.aAo.np();
            }
        } catch (java.lang.RuntimeException runtimeexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.client.renderer.texture.TextureAtlasSprite
                            .method_b7e52da193241b30710d(runtimeexception));
        }
    }

    public int getIconHeight() {
        return this.height;
    }

    public void b(int widthValue) {
        try {
            this.width = widthValue;
            if (this.aAo != null) {
                this.aAo.b(this.width);
            }
        } catch (java.lang.RuntimeException runtimeexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.client.renderer.texture.TextureAtlasSprite
                            .method_b7e52da193241b30710d(runtimeexception));
        }
    }

    private void fixTransparentColor(int[] aint) {
        int i = TextureManager.method_7eec03da329a017ec143();
        if (aint != null) {

            long j = 0L;

            long k = 0L;

            long l = 0L;

            long i1 = 0L;
            int j1 = 0;

            while (j1 < aint.length) {
                int k1 = aint[j1];
                int l1 = k1 >> 24 & 0xFF;
                if (l1 >= 16) {
                    int i2 = k1 >> 16 & 0xFF;
                    int j2 = k1 >> 8 & 0xFF;
                    int k2 = k1 & 0xFF;
                    j += i2;
                    k += j2;
                    l += k2;

                    i1 += 1L;
                }

                j1++;
                if (i == 0) {
                    break;
                }
            }

            if (i1 > 0L) {
                j1 = (int) (j / i1);
                int i3 = (int) (k / i1);
                int j3 = (int) (l / i1);
                int k3 = j1 << 16 | i3 << 8 | j3;
                int l3 = 0;

                while (l3 < aint.length) {
                    int i4 = aint[l3];
                    int l2 = i4 >> 24 & 0xFF;
                    if (l2 <= 16) {
                        aint[l3] = k3;
                    }

                    l3++;
                    if (i == 0) {
                        break;
                    }
                }
            }
        }
    }

    public float getMaxU() {
        return this.maxU;
    }

    public float toSingleU(float f) {
        f -= this.baseU;
        float f1 = (float) this.sheetWidth / this.width;
        return f * f1;
    }

    private void allocateFrameTextureData(int i) {
        int j = TextureManager.method_beea3e16fe3ec0995eb9();
        if (this.framesTextureData.size() <= i) {
            int k = this.framesTextureData.size();

            while (k <= i) {
                this.framesTextureData.add(null);
                k++;
                if (j != 0) {
                    break;
                }
            }
        }

        if (this.aAo != null) {
            this.aAo.allocateFrameTextureData(i);
        }
    }

    public void iJ() {
        try {
            this.framesTextureData.clear();
            if (this.aAo != null) {
                this.aAo.iJ();
            }
        } catch (java.lang.RuntimeException runtimeexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.client.renderer.texture.TextureAtlasSprite
                            .method_b7e52da193241b30710d(runtimeexception));
        }
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public boolean dL() {
        try {
            if (this.animationMetadata != null) {
                return true;
            }
        } catch (java.lang.RuntimeException runtimeexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.client.renderer.texture.TextureAtlasSprite
                            .method_b7e52da193241b30710d(runtimeexception));
        }

        return false;
    }

    public double getSpriteV16(float p171_0) {
        float f1 = this.maxV - this.minV;
        return (p171_0 - this.minV) / f1 * 16.0F;
    }

    public void initSprite(int i, int j, int k, int l, boolean rotatedValue) {
        this.originX = k;
        this.originY = l;
        this.rotated = rotatedValue;
        int j1 = TextureManager.method_7eec03da329a017ec143();
        float fx = (float) (0.01F / i);
        float f1 = (float) (0.01F / j);
        this.minU = k / (float) i + fx;
        this.maxU = (k + this.width) / (float) i - fx;
        this.minV = (float) l / j + f1;
        this.maxV = (float) (l + this.height) / j - f1;
        this.baseU = Math.min(this.minU, this.maxU);
        int i1 = j1;
        this.baseV = Math.min(this.minV, this.maxV);
        if (this.aAo != null) {
            this.aAo.initSprite(this.width, this.height, 0, 0, false);
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            TextureManager.b(++i1);
        }
    }

    public void updateAnimation() {
        int i1 = TextureManager.method_7eec03da329a017ec143();
        this.jM++;
        int i = i1;
        if (this.jM >= this.animationMetadata.method_46b7d87f71f96e2a0dba(this.rC)) {
            int j = this.animationMetadata.getFrameIndex(this.rC);
            int k =
                    this.animationMetadata.getFrameCount() == 0
                            ? this.framesTextureData.size()
                            : this.animationMetadata.getFrameCount();
            this.rC = (this.rC + 1) % k;
            this.jM = 0;
            int l = this.animationMetadata.getFrameIndex(this.rC);
            byte b0 = 0;
            boolean flag = this.xQ;
            if (j != l && l >= 0 && l < this.framesTextureData.size()) {
                if (OptiFineConfig.isShaders()) {
                    ShadersTex.uploadTexSub(
                            (int[][]) this.framesTextureData.get(l),
                            this.width,
                            this.height,
                            this.originX,
                            this.originY,
                            remix.compat.compiler.JvmBooleanSlots.fromInt(b0),
                            flag);
                    if (!(i != 0)) {

                        TextureUtil.uploadTextureMipmap(
                                (int[][]) this.framesTextureData.get(l),
                                this.width,
                                this.height,
                                this.originX,
                                this.originY,
                                remix.compat.compiler.JvmBooleanSlots.fromInt(b0),
                                flag);
                    }
                } else {

                    TextureUtil.uploadTextureMipmap(
                            (int[][]) this.framesTextureData.get(l),
                            this.width,
                            this.height,
                            this.originX,
                            this.originY,
                            remix.compat.compiler.JvmBooleanSlots.fromInt(b0),
                            flag);
                }
            }

            if (i != 0) {
                return;
            }
        }

        if (this.animationMetadata.isInterpolate()) {
            this.iQ();
        }
    }

    public void copyFrom(TextureAtlasSprite textureAtlasSprite) {
        try {
            this.originX = textureAtlasSprite.originX;
            this.originY = textureAtlasSprite.originY;
            this.width = textureAtlasSprite.width;
            this.height = textureAtlasSprite.height;
            this.rotated = textureAtlasSprite.rotated;
            this.minU = textureAtlasSprite.minU;
            this.maxU = textureAtlasSprite.maxU;
            this.minV = textureAtlasSprite.minV;
            this.maxV = textureAtlasSprite.maxV;
            if (this.aAo != null) {
                this.aAo.initSprite(this.width, this.height, 0, 0, false);
            }
        } catch (java.lang.RuntimeException runtimeexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.client.renderer.texture.TextureAtlasSprite
                            .method_b7e52da193241b30710d(runtimeexception));
        }
    }

    public void ns() {
        try {
            if (this.glSpriteTextureId >= 0) {
                net.minecraft.client.renderer.texture.TextureUtil.deleteTexture(
                        this.glSpriteTextureId);
                this.glSpriteTextureId = -1;
            }
        } catch (java.lang.RuntimeException runtimeexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.client.renderer.texture.TextureAtlasSprite
                            .method_b7e52da193241b30710d(runtimeexception));
        }
    }

    public boolean method_39e557b24f43f8bcfd13(
            IResourceManager iResourceManager, ResourceLocation resourceLocation) {
        return false;
    }

    protected static TextureAtlasSprite makeAtlasSprite(ResourceLocation resourceLocation) {
        String text = resourceLocation.toString();
        return (TextureAtlasSprite)
                (net.minecraft.client.renderer.texture.TextureAtlasSprite.locationNameClock.equals(
                                text)
                        ? new TextureClock(text)
                        : (net.minecraft.client.renderer.texture.TextureAtlasSprite
                                        .locationNameCompass
                                        .equals(text)
                                ? new TextureCompass(text)
                                : new TextureAtlasSprite(text)));
    }

    public void loadSprite(BufferedImage[] abufferedimage, AnimationMetadataSection aGQValue)
            throws IOException {
        this.np();
        int j = abufferedimage[0].getWidth();
        int l2 = TextureManager.method_beea3e16fe3ec0995eb9();
        int k = abufferedimage[0].getHeight();
        this.width = j;
        this.height = k;
        int[][] aint = new int[abufferedimage.length][];
        int i = l2;

        for (int l = 0; l < abufferedimage.length; l++) {
            BufferedImage bufferedimage = abufferedimage[l];
            if (bufferedimage != null) {
                if (l > 0
                        && (bufferedimage.getWidth() != j >> l
                                || bufferedimage.getHeight() != k >> l)) {
                    throw new RuntimeException(
                            String.format(
                                    "Unable to load miplevel: %d, image is size: %dx%d, expected"
                                        + " %dx%d",
                                    l,
                                    bufferedimage.getWidth(),
                                    bufferedimage.getHeight(),
                                    j >> l,
                                    k >> l));
                }

                aint[l] = new int[bufferedimage.getWidth() * bufferedimage.getHeight()];
                bufferedimage.getRGB(
                        0,
                        0,
                        bufferedimage.getWidth(),
                        bufferedimage.getHeight(),
                        aint[l],
                        0,
                        bufferedimage.getWidth());
            }
            break;
        }

        label106:
        {
            if (aGQValue == null) {
                if (k != j) {
                    throw new RuntimeException("broken aspect ratio and not an animation");
                }

                this.framesTextureData.add(aint);
                if (i == 0) {
                    break label106;
                }
            }

            int k1 = k / j;
            int i1 = j;
            int j1 = j;
            this.height = this.width;
            if (aGQValue.getFrameCount() > 0) {
                for (java.util.Iterator<?> __recoveryIterator171_13532 =
                                (aGQValue.getFrameIndexSet()).iterator();
                        (__recoveryIterator171_13532).hasNext(); ) {
                    int l1 = ((java.lang.Integer) (__recoveryIterator171_13532).next()).intValue();
                    if (l1 >= k1) {
                        throw new RuntimeException("invalid frameindex " + l1);
                    }

                    this.allocateFrameTextureData(l1);
                    this.framesTextureData.set(l1, getFrameTextureData(aint, i1, j1, l1));
                    if (i != 0) {
                        break;
                    }
                }

                this.animationMetadata = aGQValue;
                if (i == 0) {
                    break label106;
                }
            }

            ArrayList arrayList = Lists.newArrayList();
            int i2 = 0;

            while (i2 < k1) {
                this.framesTextureData.add(getFrameTextureData(aint, i1, j1, i2));
                arrayList.add(new AnimationFrame(i2, -1));
                i2++;
                if (i != 0) {
                    break;
                }
            }

            this.animationMetadata =
                    new AnimationMetadataSection(
                            arrayList,
                            this.width,
                            this.height,
                            aGQValue.getFrameTime(),
                            aGQValue.isInterpolate());
        }

        int k2 = 0;

        while (k2 < this.framesTextureData.size()) {
            int[][] aint1 = (int[][]) this.framesTextureData.get(k2);
            if (aint1 != null && !this.iconName.startsWith("minecraft:blocks/leaves_")) {
                int j2 = 0;

                while (j2 < aint1.length) {
                    int[] aint2 = aint1[j2];
                    this.fixTransparentColor(aint2);
                    j2++;
                    if (i != 0) {
                        break;
                    }
                }
            }

            k2++;
            if (i != 0) {
                break;
            }
        }

        if (this.aAo != null) {
            this.aAo.loadSprite(abufferedimage, aGQValue);
        }
    }

    public int getOriginX() {
        return this.originX;
    }

    public int getIndexInMap() {
        return this.indexInMap;
    }

    public float toSingleV(float f) {
        f -= this.baseV;
        float f1 = (float) this.sheetHeight / this.height;
        return f * f1;
    }

    protected TextureAtlasSprite(String text) {
        // Registration assigns a distinct atlas index only to unregistered sprites.
        this.indexInMap = -1;
        this.glSpriteTextureId = -1;
        this.iconName = text;
        if (optifine.OptiFineConfig.isMultiTexture()) {
            this.aAo = new net.minecraft.client.renderer.texture.TextureAtlasSprite(this);
        }
    }

    public float getMinV() {
        return this.minV;
    }

    public int getOriginY() {
        return this.originY;
    }

    public static void setLocationNameClock(String text) {
        net.minecraft.client.renderer.texture.TextureAtlasSprite.locationNameClock = text;
    }

    public void setIndexInMap(int indexInMapValue) {
        this.indexInMap = indexInMapValue;
    }

    public void method_571f52593fafd8dfba61(int heightValue) {
        try {
            this.height = heightValue;
            if (this.aAo != null) {
                this.aAo.method_571f52593fafd8dfba61(this.height);
            }
        } catch (java.lang.RuntimeException runtimeexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.client.renderer.texture.TextureAtlasSprite
                            .method_b7e52da193241b30710d(runtimeexception));
        }
    }

    @Override
    public String toString() {
        return "TextureAtlasSprite{name='"
                + this.iconName
                + '\''
                + ", frameCount="
                + this.framesTextureData.size()
                + ", rotated="
                + this.rotated
                + ", x="
                + this.originX
                + ", y="
                + this.originY
                + ", height="
                + this.height
                + ", width="
                + this.width
                + ", u0="
                + this.minU
                + ", u1="
                + this.maxU
                + ", v0="
                + this.minV
                + ", v1="
                + this.maxV
                + '}';
    }

    private static int[][] getFrameTextureData(int[][] aint, int i, int j, int k) {
        int[][] aint1 = new int[aint.length][];
        int j1 = TextureManager.method_beea3e16fe3ec0995eb9();
        int i1 = 0;
        int l = j1;

        while (i1 < aint.length) {
            int[] aint2 = aint[i1];
            if (aint2 != null) {
                aint1[i1] = new int[(i >> i1) * (j >> i1)];
                System.arraycopy(aint2, k * aint1[i1].length, aint1[i1], 0, aint1[i1].length);
            }

            i1++;
            if (l != 0) {
                break;
            }
        }

        return aint1;
    }

    static {
        net.minecraft.client.renderer.texture.TextureAtlasSprite.locationNameClock =
                "builtin/clock";
        net.minecraft.client.renderer.texture.TextureAtlasSprite.locationNameCompass =
                "builtin/compass";
    }

    public float getMinU() {
        return this.minU;
    }

    public float getInterpolatedV(double p171_0) {
        float f = this.maxV - this.minV;
        return this.minV + f * ((float) p171_0 / 16.0F);
    }

    private void iQ() {
        int l2 = TextureManager.method_beea3e16fe3ec0995eb9();
        double d0 =
                1.0
                        - (double) this.jM
                                / this.animationMetadata.method_46b7d87f71f96e2a0dba(this.rC);
        int j = this.animationMetadata.getFrameIndex(this.rC);
        int i = l2;
        int k =
                this.animationMetadata.getFrameCount() == 0
                        ? this.framesTextureData.size()
                        : this.animationMetadata.getFrameCount();
        int l = this.animationMetadata.getFrameIndex((this.rC + 1) % k);
        if (j != l && l >= 0 && l < this.framesTextureData.size()) {
            int[][] aint = (int[][]) this.framesTextureData.get(j);
            int[][] aint1 = (int[][]) this.framesTextureData.get(l);
            if (this.interpolatedFrameData == null
                    || this.interpolatedFrameData.length != aint.length) {
                this.interpolatedFrameData = new int[aint.length][];
            }

            int i1 = 0;

            while (i1 < aint.length) {
                if (this.interpolatedFrameData[i1] == null) {
                    this.interpolatedFrameData[i1] = new int[aint[i1].length];
                }

                if (i1 < aint1.length && aint1[i1].length == aint[i1].length) {
                    int j1 = 0;

                    while (j1 < aint[i1].length) {
                        int k1 = aint[i1][j1];
                        int l1 = aint1[i1][j1];
                        int i2 =
                                (int)
                                        (((k1 & 0xFF0000) >> 16) * d0
                                                + ((l1 & 0xFF0000) >> 16) * (1.0 - d0));
                        int j2 =
                                (int)
                                        (((k1 & 0xFF00) >> 8) * d0
                                                + ((l1 & 0xFF00) >> 8) * (1.0 - d0));
                        int k2 = (int) ((k1 & 0xFF) * d0 + (l1 & 0xFF) * (1.0 - d0));
                        this.interpolatedFrameData[i1][j1] =
                                k1 & 0xFF000000 | i2 << 16 | j2 << 8 | k2;
                        j1++;
                        if (i != 0) {
                            break;
                        }
                    }
                }

                i1++;
                if (i != 0) {
                    break;
                }
            }

            TextureUtil.uploadTextureMipmap(
                    this.interpolatedFrameData,
                    this.width,
                    this.height,
                    this.originX,
                    this.originY,
                    false,
                    false);
        }
    }

    public int[][] getFrameTextureData(int p171_0) {
        return (int[][]) this.framesTextureData.get(p171_0);
    }

    public int getFrameCount() {
        return this.framesTextureData.size();
    }

    public boolean method_379bd37280898b7acc5d(
            IResourceManager iResourceManager, ResourceLocation resourceLocation) {
        return true;
    }

    public void nG() {
        try {
            if (this.glSpriteTextureId < 0) {
                this.glSpriteTextureId =
                        net.minecraft.client.renderer.texture.TextureUtil.glGenTextures();
                net.minecraft.client.renderer.texture.TextureUtil.allocateTextureImpl(
                        this.glSpriteTextureId, this.yK, this.width, this.height);
                optifine.TextureUtils.applyAnisotropicLevel();
            }
        } catch (java.lang.RuntimeException runtimeexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.client.renderer.texture.TextureAtlasSprite
                            .method_b7e52da193241b30710d(runtimeexception));
        }

        optifine.TextureUtils.bindTexture(this.glSpriteTextureId);
    }

    public int getIconWidth() {
        return this.width;
    }

    public static void setLocationNameCompass(String text) {
        net.minecraft.client.renderer.texture.TextureAtlasSprite.locationNameCompass = text;
    }

    public void generateMipmaps(int i) {
        int l = TextureManager.method_7eec03da329a017ec143();
        ArrayList arrayList = Lists.newArrayList();
        int j = l;
        int k = 0;

        while (k < this.framesTextureData.size()) {
            int[][] aint = (int[][]) this.framesTextureData.get(k);
            if (aint != null) {
                try {
                    arrayList.add(TextureUtil.generateMipmapData(i, this.width, aint));
                } catch (Throwable throwable) {
                    CrashReport crashReport =
                            CrashReport.makeCrashReport(throwable, "Generating mipmaps for frame");
                    CrashReportCategory crashReportCategory =
                            crashReport.makeCategory("Frame being iterated");
                    crashReportCategory.addCrashSection("Frame index", k);
                    crashReportCategory.addCrashSectionCallable(
                            "Frame sizes", new TextureAtlasSpriteFrameCallable(this, aint));
                    throw new ReportedException(crashReport);
                }
            }

            k++;
            if (j == 0) {
                break;
            }
        }

        this.setFramesTextureData(arrayList);
        if (this.aAo != null) {
            this.aAo.generateMipmaps(i);
        }
    }

    public float getInterpolatedU(double p171_0) {
        float f = this.maxU - this.minU;
        return this.minU + f * (float) p171_0 / 16.0F;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    private TextureAtlasSprite(TextureAtlasSprite textureAtlasSprite) {
        super();
        this.indexInMap = -1;
        this.glSpriteTextureId = -1;
        this.aAo = null;
        this.xQ = false;
        this.yK = 0;
        this.iconName = textureAtlasSprite.iconName;
        this.xQ = true;
    }
}
