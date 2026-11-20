package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.EnumFaceing;
import net.minecraft.client.renderer.EnumFaceingConstants;
import net.minecraft.client.renderer.EnumFaceingVertexInformation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.entity.Entity;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;

import optifine.OptiFineConfig;
import optifine.Reflector;

import remix.compat.render.ITransformation;
import remix.compat.vecmath.AxisAngle4d;
import remix.compat.vecmath.Matrix4d;
import remix.compat.vecmath.Vector3d;
import remix.compat.vecmath.Vector3f;

import shadersmod.client.Shaders;

public class FaceBakery {
    private static final double RESCALE_AT_22_5_DEGREES = 1.0 / Math.cos(Math.PI / 8) - 1.0;
    private static final double RESCALE_AT_45_DEGREES = 1.0 / Math.cos(Math.PI / 4) - 1.0;

    private void storeVertexData(
            int[] aint,
            int i,
            int j,
            Vector3d vector3d,
            int k,
            TextureAtlasSprite textureAtlasSprite,
            BlockFaceUV blockFaceUV) {
        int l = aint.length / 4;
        int i1 = i * l;
        aint[i1] = Float.floatToRawIntBits((float) vector3d.x);
        aint[i1 + 1] = Float.floatToRawIntBits((float) vector3d.y);
        aint[i1 + 2] = Float.floatToRawIntBits((float) vector3d.z);
        aint[i1 + 3] = k;
        aint[i1 + 4] =
                Float.floatToRawIntBits(
                        textureAtlasSprite.getInterpolatedU(blockFaceUV.getVertexU(j)));
        aint[i1 + 4 + 1] =
                Float.floatToRawIntBits(
                        textureAtlasSprite.getInterpolatedV(blockFaceUV.getVertexV(j)));
    }

    private Matrix4d getMatrixIdentity() {
        remix.compat.vecmath.Matrix4d matrix4d = new remix.compat.vecmath.Matrix4d();
        matrix4d.setIdentity();
        return matrix4d;
    }

    private void lockVertexUv(
            int i,
            int[] aint,
            Facing facing,
            BlockFaceUV blockFaceUV,
            TextureAtlasSprite textureAtlasSprite) {
        int k = aint.length / 4;
        int l = k * i;
        float f = Float.intBitsToFloat(aint[l]);
        float f1 = Float.intBitsToFloat(aint[l + 1]);
        int j1 = BakedQuad.cM();
        float f2 = Float.intBitsToFloat(aint[l + 2]);
        int j = j1;
        if (f < -0.1F || f >= 1.1F) {
            f -= MathHelper.floor(f);
        }

        if (f1 < -0.1F || f1 >= 1.1F) {
            f1 -= MathHelper.floor(f1);
        }

        if (f2 < -0.1F || f2 >= 1.1F) {
            f2 -= MathHelper.floor(f2);
        }

        float f3 = 0.0F;
        float f4 = 0.0F;
        switch (facing) {
            case DOWN:
                f3 = f * 16.0F;
                f4 = (1.0F - f2) * 16.0F;
                if (j != 0) {
                    break;
                }
            case UP:
                f3 = f * 16.0F;
                f4 = f2 * 16.0F;
                if (j != 0) {
                    break;
                }
            case NORTH:
                f3 = (1.0F - f) * 16.0F;
                f4 = (1.0F - f1) * 16.0F;
                if (j != 0) {
                    break;
                }
            case SOUTH:
                f3 = f * 16.0F;
                f4 = (1.0F - f1) * 16.0F;
                if (j != 0) {
                    break;
                }
            case WEST:
                f3 = f2 * 16.0F;
                f4 = (1.0F - f1) * 16.0F;
                if (j != 0) {
                    break;
                }
            case EAST:
                f3 = (1.0F - f2) * 16.0F;
                f4 = (1.0F - f1) * 16.0F;
        }

        int i1 = blockFaceUV.getReverseRotatedVertexIndex(i) * k;
        aint[i1 + 4] = Float.floatToRawIntBits(textureAtlasSprite.getInterpolatedU(f3));
        aint[i1 + 4 + 1] = Float.floatToRawIntBits(textureAtlasSprite.getInterpolatedV(f4));
    }

    public static Facing getFacingFromVertexData(int[] aint) {
        int j = aint.length / 4;
        int k1 = BakedQuad.cM();
        int k = j * 2;
        int l = j * 3;
        int i = k1;
        Vector3f vector3f =
                new Vector3f(
                        Float.intBitsToFloat(aint[0]),
                        Float.intBitsToFloat(aint[1]),
                        Float.intBitsToFloat(aint[2]));
        Vector3f vector3fValue =
                new Vector3f(
                        Float.intBitsToFloat(aint[j]),
                        Float.intBitsToFloat(aint[j + 1]),
                        Float.intBitsToFloat(aint[j + 2]));
        Vector3f vector3fValue2 =
                new Vector3f(
                        Float.intBitsToFloat(aint[k]),
                        Float.intBitsToFloat(aint[k + 1]),
                        Float.intBitsToFloat(aint[k + 2]));
        Vector3f vector3fValue3 = new Vector3f();
        Vector3f vector3fValue4 = new Vector3f();
        Vector3f vector3fValue5 = new Vector3f();
        vector3fValue3.sub(vector3f, vector3fValue);
        vector3fValue4.sub(vector3fValue2, vector3fValue);
        vector3fValue5.cross(vector3fValue4, vector3fValue3);
        vector3fValue5.normalize();
        Facing facing = null;
        float f = 0.0F;
        Facing[] facingArray = Facing.values();
        int length = facingArray.length;
        int j1 = 0;

        while (j1 < length) {
            Facing facingValue = facingArray[j1];
            Vec3i vec3i = facingValue.getDirectionVec();
            Vector3f vector3fValue6 = new Vector3f(vec3i.getX(), vec3i.getY(), vec3i.getZ());
            float f1 = vector3fValue5.dot(vector3fValue6);
            if (f1 >= 0.0F && f1 > f) {
                f = f1;
                facing = facingValue;
            }

            j1++;
            if (i == 0) {
                Entity.setLegacyGlobalFlowMarker(new String[3]);
                break;
            }
        }

        if (f < 0.719F) {
            if (facing != Facing.EAST
                    && facing != Facing.WEST
                    && facing != Facing.NORTH
                    && facing != Facing.SOUTH) {
                facing = Facing.UP;
                if (i != 0) {
                    return facing == null ? Facing.UP : facing;
                }
            }

            facing = Facing.NORTH;
        }

        return facing == null ? Facing.UP : facing;
    }

    private void applyFacing(int[] aint, Facing facing) {
        int[] aint1 = new int[aint.length];
        System.arraycopy(aint, 0, aint1, 0, aint.length);
        int i2 = BakedQuad.eR();
        float[] afloat = new float[Facing.values().length];
        afloat[EnumFaceingConstants.WEST_INDEX] = 999.0F;
        int i = i2;
        afloat[EnumFaceingConstants.DOWN_INDEX] = 999.0F;
        afloat[EnumFaceingConstants.NORTH_INDEX] = 999.0F;
        afloat[EnumFaceingConstants.EAST_INDEX] = -999.0F;
        afloat[EnumFaceingConstants.UP_INDEX] = -999.0F;
        afloat[EnumFaceingConstants.SOUTH_INDEX] = -999.0F;
        int j = aint.length / 4;
        int l = 0;

        while (l < 4) {
            int k = j * l;
            float f1 = Float.intBitsToFloat(aint1[k]);
            float f2 = Float.intBitsToFloat(aint1[k + 1]);
            float f = Float.intBitsToFloat(aint1[k + 2]);
            if (f1 < afloat[EnumFaceingConstants.WEST_INDEX]) {
                afloat[EnumFaceingConstants.WEST_INDEX] = f1;
            }

            if (f2 < afloat[EnumFaceingConstants.DOWN_INDEX]) {
                afloat[EnumFaceingConstants.DOWN_INDEX] = f2;
            }

            if (f < afloat[EnumFaceingConstants.NORTH_INDEX]) {
                afloat[EnumFaceingConstants.NORTH_INDEX] = f;
            }

            if (f1 > afloat[EnumFaceingConstants.EAST_INDEX]) {
                afloat[EnumFaceingConstants.EAST_INDEX] = f1;
            }

            if (f2 > afloat[EnumFaceingConstants.UP_INDEX]) {
                afloat[EnumFaceingConstants.UP_INDEX] = f2;
            }

            if (f > afloat[EnumFaceingConstants.SOUTH_INDEX]) {
                afloat[EnumFaceingConstants.SOUTH_INDEX] = f;
            }

            l++;
            if (i != 0) {
                break;
            }
        }

        EnumFaceing facingValue = EnumFaceing.getFacing(facing);
        int k1 = 0;

        while (k1 < 4) {
            int l1 = j * k1;
            EnumFaceingVertexInformation enumFaceingVertexInformation = facingValue.ap(k1);
            float f8 = afloat[enumFaceingVertexInformation.a];
            float f3 = afloat[enumFaceingVertexInformation.cz];
            float f4 = afloat[enumFaceingVertexInformation.c];
            aint[l1] = Float.floatToRawIntBits(f8);
            aint[l1 + 1] = Float.floatToRawIntBits(f3);
            aint[l1 + 2] = Float.floatToRawIntBits(f4);
            int i1 = 0;

            while (true) {
                if (i1 < 4) {
                    int j1 = j * i1;
                    float f5 = Float.intBitsToFloat(aint1[j1]);
                    float f6 = Float.intBitsToFloat(aint1[j1 + 1]);
                    float f7 = Float.intBitsToFloat(aint1[j1 + 2]);
                    if (MathHelper.epsilonEquals(f8, f5)
                            && MathHelper.epsilonEquals(f3, f6)
                            && MathHelper.epsilonEquals(f4, f7)) {
                        aint[l1 + 4] = aint1[j1 + 4];
                        aint[l1 + 4 + 1] = aint1[j1 + 4 + 1];
                    }

                    i1++;
                    if (i == 0) {
                        continue;
                    }
                }

                k1++;
                if (i != 0) {
                    return;
                }
                break;
            }
        }
    }

    private void fillVertexData(
            int[] aint,
            int i,
            Facing facing,
            BlockPartFace blockPartFace,
            float[] afloat,
            TextureAtlasSprite textureAtlasSprite,
            ITransformation iTransformation,
            BlockPartRotation blockPartRotation,
            boolean flag,
            boolean flag1) {
        Facing facingValue = iTransformation.rotate(facing);
        int j = flag1 ? this.getFaceShadeColor(facingValue) : -1;
        EnumFaceingVertexInformation enumFaceingVertexInformation =
                EnumFaceing.getFacing(facing).ap(i);
        Vector3d vector3d =
                new Vector3d(
                        afloat[enumFaceingVertexInformation.a],
                        afloat[enumFaceingVertexInformation.cz],
                        afloat[enumFaceingVertexInformation.c]);
        this.rotatePart(vector3d, blockPartRotation);
        int k = this.rotateVertex(vector3d, facing, i, iTransformation, flag);
        this.storeVertexData(
                aint, k, i, vector3d, j, textureAtlasSprite, blockPartFace.blockFaceUV);
    }

    private Matrix4d createAxisRotationMatrix(AxisAngle4d axisAngle4d) {
        Matrix4d matrixIdentity = this.getMatrixIdentity();
        matrixIdentity.setRotation(axisAngle4d);
        return matrixIdentity;
    }

    private void rotateScale(Vector3d p171_0, Vector3d p171_1, Matrix4d p171_2, Vector3d p171_3) {
        p171_0.sub(p171_1);
        p171_2.transform(p171_0);
        p171_0.x = p171_0.x * p171_3.x;
        p171_0.y = p171_0.y * p171_3.y;
        p171_0.z = p171_0.z * p171_3.z;
        p171_0.add(p171_1);
    }

    private int[] makeQuadVertexData(
            BlockPartFace blockPartFace,
            TextureAtlasSprite textureAtlasSprite,
            Facing facing,
            float[] afloat,
            ITransformation iTransformation,
            BlockPartRotation blockPartRotation,
            boolean flag,
            boolean flag1) {
        int k = BakedQuad.cM();
        byte b0 = 28;
        int i = k;
        if (OptiFineConfig.isShaders()) {
            b0 = 56;
        }

        int[] aint = new int[b0];
        int j = 0;

        while (j < 4) {
            this.fillVertexData(
                    aint,
                    j,
                    facing,
                    blockPartFace,
                    afloat,
                    textureAtlasSprite,
                    iTransformation,
                    blockPartRotation,
                    flag,
                    flag1);
            j++;
            if (i == 0) {
                break;
            }
        }

        return aint;
    }

    private void rotatePart(Vector3d vector3d, BlockPartRotation blockPartRotation) {
        int i = BakedQuad.cM();
        if (blockPartRotation != null) {
            Matrix4d matrixIdentity = this.getMatrixIdentity();
            Vector3d vector3dValue = new Vector3d(0.0, 0.0, 0.0);
            switch (blockPartRotation.axis) {
                case X:
                    matrixIdentity.mul(
                            this.createAxisRotationMatrix(
                                    new AxisAngle4d(
                                            1.0,
                                            0.0,
                                            0.0,
                                            blockPartRotation.angle * (Math.PI / 180.0))));
                    vector3dValue.set(0.0, 1.0, 1.0);
                    if (i != 0) {
                        break;
                    }
                case Y:
                    matrixIdentity.mul(
                            this.createAxisRotationMatrix(
                                    new AxisAngle4d(
                                            0.0,
                                            1.0,
                                            0.0,
                                            blockPartRotation.angle * (Math.PI / 180.0))));
                    vector3dValue.set(1.0, 0.0, 1.0);
                    if (i != 0) {
                        break;
                    }
                case Z:
                    matrixIdentity.mul(
                            this.createAxisRotationMatrix(
                                    new AxisAngle4d(
                                            0.0,
                                            0.0,
                                            1.0,
                                            blockPartRotation.angle * (Math.PI / 180.0))));
                    vector3dValue.set(1.0, 1.0, 0.0);
            }

            boolean normalRotationFlow = i != 0;
            if (blockPartRotation.rescale) {
                boolean halfStep = Math.abs(blockPartRotation.angle) == 22.5F;
                if (halfStep) {
                    vector3dValue.scale(
                            net.minecraft.client.renderer.block.model.FaceBakery
                                    .RESCALE_AT_22_5_DEGREES);
                }
                if (!halfStep || !normalRotationFlow) {
                    vector3dValue.scale(
                            net.minecraft.client.renderer.block.model.FaceBakery
                                    .RESCALE_AT_45_DEGREES);
                }
                vector3dValue.add(new Vector3d(1.0, 1.0, 1.0));
            }
            if (!blockPartRotation.rescale || !normalRotationFlow) {
                vector3dValue.scale(new Vector3d(1.0, 1.0, 1.0));
            }

            this.rotateScale(
                    vector3d,
                    new Vector3d(blockPartRotation.origin),
                    matrixIdentity,
                    vector3dValue);
        }
    }

    public BakedQuad makeBakedQuad(
            Vector3f vector3f,
            Vector3f vector3fValue,
            BlockPartFace blockPartFace,
            TextureAtlasSprite textureAtlasSprite,
            Facing facing,
            ITransformation iTransformation,
            BlockPartRotation blockPartRotation,
            boolean flag,
            boolean flag1) {
        int[] aint =
                this.makeQuadVertexData(
                        blockPartFace,
                        textureAtlasSprite,
                        facing,
                        this.getPositionsDiv16(vector3f, vector3fValue),
                        iTransformation,
                        blockPartRotation,
                        flag,
                        flag1);
        Facing facingValue = getFacingFromVertexData(aint);
        if (flag) {
            this.lockUv(aint, facingValue, blockPartFace.blockFaceUV, textureAtlasSprite);
        }

        if (blockPartRotation == null) {
            this.applyFacing(aint, facingValue);
        }

        if (Reflector.HR.exists()) {
            Reflector.callVoid(Reflector.HR, new Object[] {aint, facingValue});
        }

        return new BakedQuad(aint, blockPartFace.tintIndex, facingValue, textureAtlasSprite);
    }

    public int rotateVertex(
            Vector3d p171_0,
            Facing facing,
            int p171_2,
            ModelRotation modelRotation,
            boolean p171_4) {
        return this.rotateVertex(p171_0, facing, p171_2, (ITransformation) modelRotation, p171_4);
    }

    private float[] getPositionsDiv16(Vector3f p171_0, Vector3f p171_1) {
        float[] afloat = new float[net.minecraft.util.Facing.values().length];
        afloat[net.minecraft.client.renderer.EnumFaceingConstants.WEST_INDEX] = p171_0.x / 16.0F;
        afloat[net.minecraft.client.renderer.EnumFaceingConstants.DOWN_INDEX] = p171_0.y / 16.0F;
        afloat[net.minecraft.client.renderer.EnumFaceingConstants.NORTH_INDEX] = p171_0.z / 16.0F;
        afloat[net.minecraft.client.renderer.EnumFaceingConstants.EAST_INDEX] = p171_1.x / 16.0F;
        afloat[net.minecraft.client.renderer.EnumFaceingConstants.UP_INDEX] = p171_1.y / 16.0F;
        afloat[net.minecraft.client.renderer.EnumFaceingConstants.SOUTH_INDEX] = p171_1.z / 16.0F;
        return afloat;
    }

    public BakedQuad makeBakedQuad(
            Vector3f vector3f,
            Vector3f vector3fValue,
            BlockPartFace blockPartFace,
            TextureAtlasSprite textureAtlasSprite,
            Facing facing,
            ModelRotation modelRotation,
            BlockPartRotation blockPartRotation,
            boolean flag,
            boolean flag1) {
        return this.makeBakedQuad(
                vector3f,
                vector3fValue,
                blockPartFace,
                textureAtlasSprite,
                facing,
                (ITransformation) modelRotation,
                blockPartRotation,
                flag,
                flag1);
    }

    private int getFaceShadeColor(Facing facing) {
        float faceBrightness = this.getFaceBrightness(facing);
        int i = MathHelper.clamp((int) (faceBrightness * 255.0F), 0, 255);
        return 0xFF000000 | i << 16 | i << 8 | i;
    }

    private float getFaceBrightness(Facing facing) {
        switch (facing) {
            case DOWN:
                if (OptiFineConfig.isShaders()) {
                    return Shaders.blockLightLevel05;
                }

                return 0.5F;
            case UP:
                return 1.0F;
            case NORTH:
            case SOUTH:
                if (OptiFineConfig.isShaders()) {
                    return Shaders.blockLightLevel08;
                }

                return 0.8F;
            case WEST:
            case EAST:
                if (OptiFineConfig.isShaders()) {
                    return Shaders.blockLightLevel06;
                }

                return 0.6F;
            default:
                return 1.0F;
        }
    }

    public void lockUv(
            int[] aint,
            Facing facing,
            BlockFaceUV blockFaceUV,
            TextureAtlasSprite textureAtlasSprite) {
        int k = BakedQuad.eR();
        int j = 0;
        int i = k;

        while (j < 4) {
            this.lockVertexUv(j, aint, facing, blockFaceUV, textureAtlasSprite);
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public int rotateVertex(
            Vector3d vector3d,
            Facing facing,
            int i,
            ITransformation iTransformation,
            boolean flag) {
        int j = BakedQuad.cM();
        if (iTransformation == ModelRotation.X0_Y0) {
            return i;
        }

        if (Reflector.Hs.exists()) {
            Reflector.call(Reflector.Hs, new Object[] {vector3d, iTransformation.getMatrix()});
            if (j != 0) {
                return iTransformation.transformVertexIndex(facing, i);
            }
        }

        this.rotateScale(
                vector3d,
                new Vector3d(0.5, 0.5, 0.5),
                new Matrix4d(iTransformation.getMatrix()),
                new Vector3d(1.0, 1.0, 1.0));
        return iTransformation.transformVertexIndex(facing, i);
    }
}
