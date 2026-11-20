package net.minecraft.world.gen.structure;

import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.Vec3i;

public class StructureBoundingBox {

    public int minY;
    public int maxY;
    public int minZ;
    public int maxZ;

    public int minX;
    public int maxX;

    public int getZSize() {
        return this.maxZ - this.minZ + 1;
    }

    public static StructureBoundingBox getNewBoundingBox() {
        return new net.minecraft.world.gen.structure.StructureBoundingBox(
                java.lang.Integer.MAX_VALUE,
                java.lang.Integer.MAX_VALUE,
                java.lang.Integer.MAX_VALUE,
                java.lang.Integer.MIN_VALUE,
                java.lang.Integer.MIN_VALUE,
                java.lang.Integer.MIN_VALUE);
    }

    public StructureBoundingBox(
            int minXValue,
            int minYValue,
            int minZValue,
            int maxXValue,
            int maxYValue,
            int maxZValue) {
        this.minX = minXValue;
        this.minY = minYValue;
        this.minZ = minZValue;
        this.maxX = maxXValue;
        this.maxY = maxYValue;
        this.maxZ = maxZValue;
    }

    public StructureBoundingBox() {}

    public static StructureBoundingBox getComponentToAddBoundingBox(
            int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2, Facing facing) {
        switch (facing) {
            case NORTH:
                return new StructureBoundingBox(
                        i + l, j + i1, k - i2 + 1 + j1, i + k1 - 1 + l, j + l1 - 1 + i1, k + j1);
            case SOUTH:
                return new StructureBoundingBox(
                        i + l, j + i1, k + j1, i + k1 - 1 + l, j + l1 - 1 + i1, k + i2 - 1 + j1);
            case WEST:
                return new StructureBoundingBox(
                        i - i2 + 1 + j1, j + i1, k + l, i + j1, j + l1 - 1 + i1, k + k1 - 1 + l);
            case EAST:
                return new StructureBoundingBox(
                        i + j1, j + i1, k + l, i + i2 - 1 + j1, j + l1 - 1 + i1, k + k1 - 1 + l);
            default:
                return new StructureBoundingBox(
                        i + l, j + i1, k + j1, i + k1 - 1 + l, j + l1 - 1 + i1, k + i2 - 1 + j1);
        }
    }

    public void offset(int p171_0, int p171_1, int p171_2) {
        this.minX += p171_0;
        this.minY += p171_1;
        this.minZ += p171_2;
        this.maxX += p171_0;
        this.maxY += p171_1;
        this.maxZ += p171_2;
    }

    public Vec3i getCenter() {
        return new BlockPos(
                this.minX + (this.maxX - this.minX + 1) / 2,
                this.minY + (this.maxY - this.minY + 1) / 2,
                this.minZ + (this.maxZ - this.minZ + 1) / 2);
    }

    public void expandTo(StructureBoundingBox structureBoundingBox) {
        this.minX = java.lang.Math.min(this.minX, structureBoundingBox.minX);
        this.minY = java.lang.Math.min(this.minY, structureBoundingBox.minY);
        this.minZ = java.lang.Math.min(this.minZ, structureBoundingBox.minZ);
        this.maxX = java.lang.Math.max(this.maxX, structureBoundingBox.maxX);
        this.maxY = java.lang.Math.max(this.maxY, structureBoundingBox.maxY);
        this.maxZ = java.lang.Math.max(this.maxZ, structureBoundingBox.maxZ);
    }

    public NBTTagIntArray toNBTTagIntArray() {
        return new net.minecraft.nbt.NBTTagIntArray(
                new int[] {this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ});
    }

    public static StructureBoundingBox func_175899_a(
            int p171_0, int p171_1, int p171_2, int p171_3, int p171_4, int p171_5) {
        return new net.minecraft.world.gen.structure.StructureBoundingBox(
                java.lang.Math.min(p171_0, p171_3),
                java.lang.Math.min(p171_1, p171_4),
                java.lang.Math.min(p171_2, p171_5),
                java.lang.Math.max(p171_0, p171_3),
                java.lang.Math.max(p171_1, p171_4),
                java.lang.Math.max(p171_2, p171_5));
    }

    public int getXSize() {
        return this.maxX - this.minX + 1;
    }

    public boolean intersectsWith(int p171_0, int p171_1, int p171_2, int p171_3) {
        return this.maxX >= p171_0
                && this.minX <= p171_2
                && this.maxZ >= p171_1
                && this.minZ <= p171_3;
    }

    public StructureBoundingBox(int[] p171_0) {
        if (p171_0.length == 6) {
            this.minX = p171_0[0];
            this.minY = p171_0[1];
            this.minZ = p171_0[2];
            this.maxX = p171_0[3];
            this.maxY = p171_0[4];
            this.maxZ = p171_0[5];
        }
    }

    public boolean intersectsWith(StructureBoundingBox structureBoundingBox) {
        return this.maxX >= structureBoundingBox.minX
                && this.minX <= structureBoundingBox.maxX
                && this.maxZ >= structureBoundingBox.minZ
                && this.minZ <= structureBoundingBox.maxZ
                && this.maxY >= structureBoundingBox.minY
                && this.minY <= structureBoundingBox.maxY;
    }

    // region String decoding support

    // endregion

    public boolean isVecInside(Vec3i vec3i) {
        return vec3i.getX() >= this.minX
                && vec3i.getX() <= this.maxX
                && vec3i.getZ() >= this.minZ
                && vec3i.getZ() <= this.maxZ
                && vec3i.getY() >= this.minY
                && vec3i.getY() <= this.maxY;
    }


    public StructureBoundingBox(int minXValue, int minZValue, int maxXValue, int maxZValue) {
        this.minX = minXValue;
        this.minZ = minZValue;
        this.maxX = maxXValue;
        this.maxZ = maxZValue;
        this.minY = 1;
        this.maxY = 512;
    }

    public Vec3i func_175896_b() {
        return new Vec3i(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
    }

    public int getYSize() {
        return this.maxY - this.minY + 1;
    }

    public StructureBoundingBox(Vec3i vec3i, Vec3i vec3iValue) {
        this.minX = Math.min(vec3i.getX(), vec3iValue.getX());
        this.minY = Math.min(vec3i.getY(), vec3iValue.getY());
        this.minZ = Math.min(vec3i.getZ(), vec3iValue.getZ());
        this.maxX = Math.max(vec3i.getX(), vec3iValue.getX());
        this.maxY = Math.max(vec3i.getY(), vec3iValue.getY());
        this.maxZ = Math.max(vec3i.getZ(), vec3iValue.getZ());
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("x0", this.minX)
                .add("y0", this.minY)
                .add("z0", this.minZ)
                .add("x1", this.maxX)
                .add("y1", this.maxY)
                .add("z1", this.maxZ)
                .toString();
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public StructureBoundingBox(StructureBoundingBox structureBoundingBox) {
        super();
        this.minX = structureBoundingBox.minX;
        this.minY = structureBoundingBox.minY;
        this.minZ = structureBoundingBox.minZ;
        this.maxX = structureBoundingBox.maxX;
        this.maxY = structureBoundingBox.maxY;
        this.maxZ = structureBoundingBox.maxZ;
    }
}
