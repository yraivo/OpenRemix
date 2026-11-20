package net.minecraft.util;

public class Vec3 {
    public double x;
    public double z;
    private static String d;

    public double y;
    private static String[] packedTextFragments;

    public Vec3 subtractReverse(Vec3 vec3) {
        return new net.minecraft.util.Vec3(vec3.x - this.x, vec3.y - this.y, vec3.z - this.z);
    }

    // region String decoding support

    // endregion

    public Vec3 add(Vec3 vec3) {
        return this.addVector(vec3.x, vec3.y, vec3.z);
    }

    public Vec3 rotateYaw(float f) {
        float f1 = MathHelper.cos(f);
        float f2 = MathHelper.sin(f);
        double d0 = this.x * f1 + this.z * f2;
        double d1 = this.y;
        double d2 = this.z * f1 - this.x * f2;
        return new Vec3(d0, d1, d2);
    }

    public Vec3 getIntermediateWithZValue(Vec3 vec3, double d0) {
        double d1 = vec3.x - this.x;
        double d2 = vec3.y - this.y;
        double d3 = vec3.z - this.z;
        if (d3 * d3 < 1.0E-7F) {
            return null;
        }

        double d4 = (d0 - this.z) / d3;
        return d4 >= 0.0 && d4 <= 1.0
                ? new Vec3(this.x + d1 * d4, this.y + d2 * d4, this.z + d3 * d4)
                : null;
    }

    public Vec3 normalize() {
        double d0 = MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return d0 < 1.0E-4
                ? new Vec3(0.0, 0.0, 0.0)
                : new Vec3(this.x / d0, this.y / d0, this.z / d0);
    }

    public Vec3 getIntermediateWithYValue(Vec3 vec3, double d0) {
        double d1 = vec3.x - this.x;
        double d2 = vec3.y - this.y;
        double d3 = vec3.z - this.z;
        if (d2 * d2 < 1.0E-7F) {
            return null;
        }

        double d4 = (d0 - this.y) / d2;
        return d4 >= 0.0 && d4 <= 1.0
                ? new Vec3(this.x + d1 * d4, this.y + d2 * d4, this.z + d3 * d4)
                : null;
    }

    public double dotProduct(Vec3 vec3) {
        return this.x * vec3.x + this.y * vec3.y + this.z * vec3.z;
    }

    // region Static initialization and string tables
    static {
        net.minecraft.util.Vec3.packedTextFragments = new String[4];
        net.minecraft.util.Vec3.packedTextFragments[0] = new String("(");
        net.minecraft.util.Vec3.packedTextFragments[1] = new String(")");
        net.minecraft.util.Vec3.packedTextFragments[2] = new String("Q4Pejc");
        String s2 = packedTextFragments[2];
        b(s2);
    }

    // endregion

    public double lengthVector() {
        return MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public static void b(String text) {
        d = text;
    }

    @Override
    public String toString() {
        return packedTextFragments[0]
                + this.x
                + ", "
                + this.y
                + ", "
                + this.z
                + packedTextFragments[1];
    }

    public Vec3 subtract(double p171_0, double p171_1, double p171_2) {
        return this.addVector(-p171_0, -p171_1, -p171_2);
    }

    public double distanceTo(Vec3 vec3) {
        double d0 = vec3.x - this.x;
        double d1 = vec3.y - this.y;
        double d2 = vec3.z - this.z;
        return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public Vec3 getIntermediateWithXValue(Vec3 vec3, double d0) {
        double d1 = vec3.x - this.x;
        double d2 = vec3.y - this.y;
        double d3 = vec3.z - this.z;
        if (d1 * d1 < 1.0E-7F) {
            return null;
        }

        double d4 = (d0 - this.x) / d1;
        return d4 >= 0.0 && d4 <= 1.0
                ? new Vec3(this.x + d1 * d4, this.y + d2 * d4, this.z + d3 * d4)
                : null;
    }

    public Vec3 addVector(double p171_0, double p171_1, double p171_2) {
        return new net.minecraft.util.Vec3(this.x + p171_0, this.y + p171_1, this.z + p171_2);
    }

    public static String method_cee993844cce4b97ed62() {
        return d;
    }

    public Vec3(double p171_0, double p171_1, double p171_2) {
        if (p171_0 == -0.0) {
            p171_0 = 0.0;
        }

        if (p171_1 == -0.0) {
            p171_1 = 0.0;
        }

        if (p171_2 == -0.0) {
            p171_2 = 0.0;
        }

        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
    }

    public Vec3 crossProduct(Vec3 vec3) {
        return new net.minecraft.util.Vec3(
                this.y * vec3.z - this.z * vec3.y,
                this.z * vec3.x - this.x * vec3.z,
                this.x * vec3.y - this.y * vec3.x);
    }

    public Vec3(BlockPos blockPos) {
        this(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public double squareDistanceTo(Vec3 vec3) {
        double d0 = vec3.x - this.x;
        double d1 = vec3.y - this.y;
        double d2 = vec3.z - this.z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public Vec3 subtract(Vec3 vec3) {
        return this.subtract(vec3.x, vec3.y, vec3.z);
    }

    public Vec3 rotatePitch(float f) {
        float f1 = MathHelper.cos(f);
        float f2 = MathHelper.sin(f);
        double d0 = this.x;
        double d1 = this.y * f1 + this.z * f2;
        double d2 = this.z * f1 - this.y * f2;
        return new Vec3(d0, d1, d2);
    }
}
