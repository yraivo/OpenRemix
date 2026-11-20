package shadersmod.client;

import net.minecraft.block.state.BlockState;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class SVertexBuilder {
    int offsetUV;
    boolean hasUV;
    int vertexSize;
    int offsetUVCenter;
    boolean hasUVCenter;
    long[] entityData = new long[10];
    int offsetNormal;
    boolean hasNormal;
    int entityDataIndex = 0;
    boolean hasTangent;

    public static void endAddVertex(WorldRenderer worldRenderer) {
        shadersmod.client.SVertexBuilder aAs = worldRenderer.sVertexBuilder;
        if (aAs.vertexSize == 14) {
            if (worldRenderer.drawMode == 7 && worldRenderer.vertexCount % 4 == 0) {
                aAs.calcNormal(
                        worldRenderer, worldRenderer.rawBufferIndex - 4 * aAs.vertexSize);
            }

            long i = aAs.entityData[aAs.entityDataIndex];
            int j = worldRenderer.rawBufferIndex - 14 + 12;
            worldRenderer.rawIntBuffer.put(j, (int) i);
            worldRenderer.rawIntBuffer.put(j + 1, (int) (i >> 32));
        }
    }

    public static void drawArrays(
            int i, int j, int k, WorldRenderer worldRenderer) {
        int[] aint = ShaderOption.lz();
        if (k != 0) {
            VertexFormat vertexFormat = worldRenderer.getVertexFormat();
            int l = vertexFormat.getNextOffset();
            if (l == 56) {
                ByteBuffer bytebuffer = worldRenderer.getByteBuffer();
                ((Buffer) bytebuffer).position(32);
                GL20.glVertexAttribPointer(
                        Shaders.midTexCoordAttrib, 2, 5126, false, l, bytebuffer);
                ((Buffer) bytebuffer).position(40);
                GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, l, bytebuffer);
                ((Buffer) bytebuffer).position(48);
                GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, l, bytebuffer);
                ((Buffer) bytebuffer).position(0);
                GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
                GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
                GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
                GL11.glDrawArrays(i, j, k);
                GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
                GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
                GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
                if (aint == null) {
                    return;
                }
            }

            GL11.glDrawArrays(i, j, k);
        }
    }

    public void calcNormal(WorldRenderer worldRenderer, int i) {
        ShaderOption.lz();
        FloatBuffer floatbuffer = worldRenderer.rawFloatBuffer;
        IntBuffer intbuffer = worldRenderer.rawIntBuffer;
        int j = worldRenderer.rawBufferIndex;
        float fx = floatbuffer.get(i + 0 * this.vertexSize);
        float f1 = floatbuffer.get(i + 0 * this.vertexSize + 1);
        float f2 = floatbuffer.get(i + 0 * this.vertexSize + 2);
        float f3 = floatbuffer.get(i + 0 * this.vertexSize + this.offsetUV);
        float f4 = floatbuffer.get(i + 0 * this.vertexSize + this.offsetUV + 1);
        float f5 = floatbuffer.get(i + 1 * this.vertexSize);
        float f6 = floatbuffer.get(i + 1 * this.vertexSize + 1);
        float f7 = floatbuffer.get(i + 1 * this.vertexSize + 2);
        float f8 = floatbuffer.get(i + 1 * this.vertexSize + this.offsetUV);
        float f9 = floatbuffer.get(i + 1 * this.vertexSize + this.offsetUV + 1);
        float f10 = floatbuffer.get(i + 2 * this.vertexSize);
        float f11 = floatbuffer.get(i + 2 * this.vertexSize + 1);
        float f12 = floatbuffer.get(i + 2 * this.vertexSize + 2);
        float f13 = floatbuffer.get(i + 2 * this.vertexSize + this.offsetUV);
        float f14 = floatbuffer.get(i + 2 * this.vertexSize + this.offsetUV + 1);
        float f15 = floatbuffer.get(i + 3 * this.vertexSize);
        float f16 = floatbuffer.get(i + 3 * this.vertexSize + 1);
        float f17 = floatbuffer.get(i + 3 * this.vertexSize + 2);
        float f18 = floatbuffer.get(i + 3 * this.vertexSize + this.offsetUV);
        float f19 = floatbuffer.get(i + 3 * this.vertexSize + this.offsetUV + 1);
        float f20 = f10 - fx;
        float f21 = f11 - f1;
        float f22 = f12 - f2;
        float f23 = f15 - f5;
        float f24 = f16 - f6;
        float f25 = f17 - f7;
        float f26 = f21 * f25 - f24 * f22;
        float f27 = f22 * f23 - f25 * f20;
        float f28 = f20 * f24 - f23 * f21;
        float f29 = f26 * f26 + f27 * f27 + f28 * f28;
        float f30 = f29 != 0.0 ? (float) (1.0 / Math.sqrt(f29)) : 1.0F;
        f26 *= f30;
        f27 *= f30;
        f28 *= f30;
        f20 = f5 - fx;
        f21 = f6 - f1;
        f22 = f7 - f2;
        float f31 = f8 - f3;
        float f32 = f9 - f4;
        f23 = f10 - fx;
        f24 = f11 - f1;
        f25 = f12 - f2;
        float f33 = f13 - f3;
        float f34 = f14 - f4;
        float f35 = f31 * f34 - f33 * f32;
        float f36 = f35 != 0.0F ? 1.0F / f35 : 1.0F;
        float f37 = (f34 * f20 - f32 * f23) * f36;
        float f38 = (f34 * f21 - f32 * f24) * f36;
        float f39 = (f34 * f22 - f32 * f25) * f36;
        float f40 = (f31 * f23 - f33 * f20) * f36;
        float f41 = (f31 * f24 - f33 * f21) * f36;
        float f42 = (f31 * f25 - f33 * f22) * f36;
        f29 = f37 * f37 + f38 * f38 + f39 * f39;
        f30 = f29 != 0.0 ? (float) (1.0 / Math.sqrt(f29)) : 1.0F;
        f37 *= f30;
        f38 *= f30;
        f39 *= f30;
        f29 = f40 * f40 + f41 * f41 + f42 * f42;
        f30 = f29 != 0.0 ? (float) (1.0 / Math.sqrt(f29)) : 1.0F;
        f40 *= f30;
        f41 *= f30;
        f42 *= f30;
        float f43 = f28 * f38 - f27 * f39;
        float f44 = f26 * f39 - f28 * f37;
        float f45 = f27 * f37 - f26 * f38;
        float f46 = f40 * f43 + f41 * f44 + f42 * f45 < 0.0F ? -1.0F : 1.0F;
        int k = (int) (f26 * 127.0F) & 0xFF;
        int l = (int) (f27 * 127.0F) & 0xFF;
        int i1 = (int) (f28 * 127.0F) & 0xFF;
        int j1 = (i1 << 16) + (l << 8) + k;
        intbuffer.put(i + 0 * this.vertexSize + this.offsetNormal, j1);
        intbuffer.put(i + 1 * this.vertexSize + this.offsetNormal, j1);
        intbuffer.put(i + 2 * this.vertexSize + this.offsetNormal, j1);
        intbuffer.put(i + 3 * this.vertexSize + this.offsetNormal, j1);
        int k1 = ((int) (f37 * 32767.0F) & 65535) + (((int) (f38 * 32767.0F) & 65535) << 16);
        int l1 = ((int) (f39 * 32767.0F) & 65535) + (((int) (f46 * 32767.0F) & 65535) << 16);
        intbuffer.put(i + 0 * this.vertexSize + 10, k1);
        intbuffer.put(i + 0 * this.vertexSize + 10 + 1, l1);
        intbuffer.put(i + 1 * this.vertexSize + 10, k1);
        intbuffer.put(i + 1 * this.vertexSize + 10 + 1, l1);
        intbuffer.put(i + 2 * this.vertexSize + 10, k1);
        intbuffer.put(i + 2 * this.vertexSize + 10 + 1, l1);
        intbuffer.put(i + 3 * this.vertexSize + 10, k1);
        intbuffer.put(i + 3 * this.vertexSize + 10 + 1, l1);
        float f47 = (f3 + f8 + f13 + f18) / 4.0F;
        float f48 = (f4 + f9 + f14 + f19) / 4.0F;
        floatbuffer.put(i + 0 * this.vertexSize + 8, f47);
        floatbuffer.put(i + 0 * this.vertexSize + 8 + 1, f48);
        floatbuffer.put(i + 1 * this.vertexSize + 8, f47);
        floatbuffer.put(i + 1 * this.vertexSize + 8 + 1, f48);
        floatbuffer.put(i + 2 * this.vertexSize + 8, f47);
        floatbuffer.put(i + 2 * this.vertexSize + 8 + 1, f48);
        floatbuffer.put(i + 3 * this.vertexSize + 8, f47);
        floatbuffer.put(i + 3 * this.vertexSize + 8 + 1, f48);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ShaderOption.b(new int[2]);
        }
    }

    public static void method_52776da4508bcea23b22(WorldRenderer worldRenderer) {
        worldRenderer.setVertexFormat(SVertexFormat.defVertexFormatTextured);
    }

    public static void endAddVertexData(WorldRenderer worldRenderer) {
        shadersmod.client.SVertexBuilder aAs = worldRenderer.sVertexBuilder;
        if (aAs.vertexSize == 14
                && worldRenderer.drawMode == 7
                && worldRenderer.vertexCount % 4 == 0) {
            aAs.calcNormal(worldRenderer, worldRenderer.rawBufferIndex - 4 * aAs.vertexSize);
        }
    }

    public static void initVertexBuilder(WorldRenderer worldRenderer) {
        worldRenderer.sVertexBuilder = new shadersmod.client.SVertexBuilder();
    }

    public static void endSetVertexFormat(WorldRenderer worldRenderer) {
        shadersmod.client.SVertexBuilder aAs = worldRenderer.sVertexBuilder;
        net.minecraft.client.renderer.vertex.VertexFormat vertexFormat = worldRenderer.getVertexFormat();
        aAs.vertexSize = vertexFormat.getNextOffset() / 4;
        aAs.hasNormal = vertexFormat.hasNormal();
        aAs.hasTangent = aAs.hasNormal;
        aAs.hasUV = vertexFormat.hasUvOffset(0);
        aAs.offsetNormal = aAs.hasNormal ? vertexFormat.getNormalOffset() / 4 : 0;
        aAs.offsetUV = aAs.hasUV ? vertexFormat.getUvOffsetById(0) / 4 : 0;
        aAs.offsetUVCenter = 8;
    }

    public void pushEntity(long p171_0) {
        this.entityDataIndex++;
        this.entityData[this.entityDataIndex] = p171_0;
    }

    public static void beginAddVertexData(WorldRenderer worldRenderer, int[] aint) {
        int[] aint1 = ShaderOption.lz();
        if (worldRenderer.vertexCount == 0) {
            endSetVertexFormat(worldRenderer);
        }

        SVertexBuilder aAs = worldRenderer.sVertexBuilder;
        if (aAs.vertexSize == 14) {
            long i = aAs.entityData[aAs.entityDataIndex];
            byte b0 = 12;

            while (b0 + 1 < aint.length) {
                aint[b0] = (int) i;
                aint[b0 + 1] = (int) (i >> 32);
                b0 += 14;
                if (aint1 != null) {
                    break;
                }
            }
        }
    }

    public void popEntity() {

        this.entityData[this.entityDataIndex] = 0L;
        this.entityDataIndex--;
    }

    public SVertexBuilder() {

        this.entityData[this.entityDataIndex] = 0L;
    }

    public static boolean popEntity(boolean p171_0, WorldRenderer worldRenderer) {
        worldRenderer.sVertexBuilder.popEntity();
        return p171_0;
    }

    public static void pushEntity(
            BlockState blockState,
            BlockPos blockPos,
            IBlockAccess iBlockAccess,
            WorldRenderer worldRenderer) {
        net.minecraft.block.Block block = blockState.getBlock();
        int idFromBlock = net.minecraft.block.Block.getIdFromBlock(block);
        int j = block.getRenderType();
        int k = block.getMetaFromState(blockState);
        int l = ((j & 65535) << 16) + (idFromBlock & 65535);
        int i1 = k & 65535;
        worldRenderer.sVertexBuilder.pushEntity(((long) i1 << 32) + l);
    }

    public static void beginAddVertex(WorldRenderer worldRenderer) {
        if (worldRenderer.vertexCount == 0) {
            shadersmod.client.SVertexBuilder.endSetVertexFormat(worldRenderer);
        }
    }

    public static void calcNormalChunkLayer(WorldRenderer worldRenderer) {
        int[] aint = ShaderOption.lz();
        if (worldRenderer.getVertexFormat().hasNormal()
                && worldRenderer.drawMode == 7
                && worldRenderer.vertexCount % 4 == 0) {
            SVertexBuilder aAs = worldRenderer.sVertexBuilder;
            endSetVertexFormat(worldRenderer);
            int i = worldRenderer.vertexCount * aAs.vertexSize;
            int j = 0;

            while (j < i) {
                aAs.calcNormal(worldRenderer, j);
                j += aAs.vertexSize * 4;
                if (aint != null) {
                    break;
                }
            }
        }
    }

    public static void popEntity(WorldRenderer worldRenderer) {
        worldRenderer.sVertexBuilder.popEntity();
    }
}
