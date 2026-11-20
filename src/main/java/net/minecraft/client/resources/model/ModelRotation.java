package net.minecraft.client.resources.model;

import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.util.MathHelper;
import optifine.Reflector;
import remix.compat.render.ITransformation;
import remix.compat.invocation.ModelRotationIndirectionMarker;
import remix.compat.render.TRSRTransformation;
import remix.compat.render.TRSRTransformationFactory;
import remix.compat.vecmath.AxisAngle4d;
import remix.compat.vecmath.Matrix4d;
import remix.compat.vecmath.Matrix4f;

import com.google.common.collect.Maps;

import java.util.Map;

public enum ModelRotation implements TRSRTransformationFactory, ITransformation {
    X0_Y0("X0_Y0", 0, 0, 0),
    X0_Y90("X0_Y90", 1, 0, 90),
    X0_Y180("X0_Y180", 2, 0, 180),
    X0_Y270("X0_Y270", 3, 0, 270),
    X90_Y0("X90_Y0", 4, 90, 0),
    X90_Y90("X90_Y90", 5, 90, 90),
    X90_Y180("X90_Y180", 6, 90, 180),
    X90_Y270("X90_Y270", 7, 90, 270),
    X180_Y0("X180_Y0", 8, 180, 0),
    X180_Y90("X180_Y90", 9, 180, 90),
    X180_Y180("X180_Y180", 10, 180, 180),
    X180_Y270("X180_Y270", 11, 180, 270),
    X270_Y0("X270_Y0", 12, 270, 0),
    X270_Y90("X270_Y90", 13, 270, 90),
    X270_Y180("X270_Y180", 14, 270, 180),
    X270_Y270("X270_Y270", 15, 270, 270);

    private static ModelRotation[] VALUES;

    private static Map mapRotations;
    private int combinedXY;
    private int quartersX;
    private Matrix4d matrix4d;

    private int quartersY;

    public Facing rotate(Facing facing) {
        return this.rotateFace(facing);
    }

    private static int combineXY(int i, int j) {
        return i * 360 + j;
    }

    public static ModelRotation getModelRotation(int i, int j) {
        return (ModelRotation)
                net.minecraft.client.resources.model.ModelRotation.mapRotations.get(
                        combineXY(
                                MathHelper.positiveModulo(i, 360),
                                MathHelper.positiveModulo(j, 360)));
    }

    static {
        net.minecraft.client.resources.model.ModelRotation.VALUES =
                new ModelRotation[] {
                    net.minecraft.client.resources.model.ModelRotation.X0_Y0,
                    net.minecraft.client.resources.model.ModelRotation.X0_Y90,
                    net.minecraft.client.resources.model.ModelRotation.X0_Y180,
                    net.minecraft.client.resources.model.ModelRotation.X0_Y270,
                    net.minecraft.client.resources.model.ModelRotation.X90_Y0,
                    net.minecraft.client.resources.model.ModelRotation.X90_Y90,
                    net.minecraft.client.resources.model.ModelRotation.X90_Y180,
                    net.minecraft.client.resources.model.ModelRotation.X90_Y270,
                    net.minecraft.client.resources.model.ModelRotation.X180_Y0,
                    net.minecraft.client.resources.model.ModelRotation.X180_Y90,
                    net.minecraft.client.resources.model.ModelRotation.X180_Y180,
                    net.minecraft.client.resources.model.ModelRotation.X180_Y270,
                    net.minecraft.client.resources.model.ModelRotation.X270_Y0,
                    net.minecraft.client.resources.model.ModelRotation.X270_Y90,
                    net.minecraft.client.resources.model.ModelRotation.X270_Y180,
                    net.minecraft.client.resources.model.ModelRotation.X270_Y270
                };
        net.minecraft.client.resources.model.ModelRotation.mapRotations = Maps.newHashMap();
        for (ModelRotation class_4650d78a5cf04199a255 : values()) {
            net.minecraft.client.resources.model.ModelRotation.mapRotations.put(
                    class_4650d78a5cf04199a255.combinedXY, class_4650d78a5cf04199a255);
        }
    }

    // region String decoding support

    // endregion

    ModelRotation(String text, int l, int j, int k) {
        this.combinedXY = combineXY(j, k);
        this.matrix4d = new Matrix4d();
        Matrix4d matrix4dValue = new Matrix4d();
        matrix4dValue.setIdentity();
        matrix4dValue.setRotation(
                new AxisAngle4d(1.0, 0.0, 0.0, -j * (float) (Math.PI / 180.0)));
        this.quartersX = MathHelper.abs(j / 90);
        Matrix4d matrix4dValue2 = new Matrix4d();
        matrix4dValue2.setIdentity();
        matrix4dValue2.setRotation(
                new AxisAngle4d(0.0, 1.0, 0.0, -k * (float) (Math.PI / 180.0)));
        this.quartersY = MathHelper.abs(k / 90);
        this.matrix4d.mul(
                matrix4dValue2, matrix4dValue);
    }

    public ModelRotationIndirectionMarker method_6009ff2421d54d6c7d37(Object object) {
        return (ModelRotationIndirectionMarker)
                this.method_00c3e6b65c4edd64ae7e((ModelRotationIndirectionMarker) object);
    }

    public Facing rotateFace(Facing facing) {
        Facing facingValue = facing;
        int k = ModelResourceLocation.aW();
        int j = 0;
        int i = k;

        while (j < this.quartersX) {
            facingValue = facingValue.rotateAround(FacingAxis.X);
            j++;
            if (i != 0) {
                break;
            }
        }

        if (facingValue.getAxis() != FacingAxis.Y) {
            j = 0;

            while (j < this.quartersY) {
                facingValue = facingValue.rotateAround(FacingAxis.Y);
                j++;
                if (i != 0) {
                    break;
                }
            }
        }

        return facingValue;
    }

    public int rotateVertex(Facing facing, int i) {
        int i1 = ModelResourceLocation.aW();
        int k = i;
        int j = i1;
        if (facing.getAxis() == FacingAxis.X) {
            k = (i + this.quartersX) % 4;
        }

        Facing facingValue = facing;
        int l = 0;

        while (l < this.quartersX) {
            facingValue = facingValue.rotateAround(FacingAxis.X);
            l++;
            if (j != 0) {
                break;
            }
        }

        if (facingValue.getAxis() == FacingAxis.Y) {
            k = (k + this.quartersY) % 4;
        }

        return k;
    }

    public TRSRTransformation method_00c3e6b65c4edd64ae7e(
            ModelRotationIndirectionMarker modelRotationIndirectionMarker) {
        return new TRSRTransformation(this.getMatrix());
    }

    public Matrix4d getMatrix4d() {
        return this.matrix4d;
    }

    public Matrix4f getMatrix() {
        return Reflector.ForgeHooksClient_getMatrix.exists()
                ? (Matrix4f) Reflector.call(Reflector.ForgeHooksClient_getMatrix, new Object[] {this})
                : new Matrix4f(this.getMatrix4d());
    }

    public int transformVertexIndex(Facing facing, int i) {
        return this.rotateVertex(facing, i);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
