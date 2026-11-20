package net.minecraft.util;

public class Vec3i implements Comparable {
    private static String legacyCoordinateFlowMarker;
    public static final Vec3i NULL_VECTOR = new Vec3i(0, 0, 0);
    private static String[] packedTextFragments;
    private int y;
    private int z;
    private int x;

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Vec3i) object);
    }

    public Vec3i(int i, int j, int k) {
        this.x = i;
        this.y = j;
        this.z = k;
    }

    public static String getLegacyCoordinateFlowMarker() {
        return net.minecraft.util.Vec3i.legacyCoordinateFlowMarker;
    }

    public int getZ() {
        return this.z;
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add(packedTextFragments[0], this.getX())
                .add(packedTextFragments[1], this.getY())
                .add(packedTextFragments[2], this.getZ())
                .toString();
    }

    public static void setLegacyCoordinateFlowMarker(String text) {
        net.minecraft.util.Vec3i.legacyCoordinateFlowMarker = text;
    }

    public Vec3i(double d0, double d1, double d2) {
        this(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2));
    }

    public double distanceSq(Vec3i vec3i) {
        return this.distanceSq(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    // region Static initialization and string tables
    static {
        packedTextFragments = new String[4];
        unpackLiteralFragments();
        String s = packedTextFragments[3];
        setLegacyCoordinateFlowMarker(s);
    }

    // endregion

    public double distanceSq(double d0, double d1, double d2) {
        double d3 = this.getX() - d0;
        double d4 = this.getY() - d1;
        double d5 = this.getZ() - d2;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    @Override
    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    public int compareTo(Vec3i vec3i) {
        return this.getY() == vec3i.getY()
                ? (this.getZ() == vec3i.getZ()
                        ? this.getX() - vec3i.getX()
                        : this.getZ() - vec3i.getZ())
                : this.getY() - vec3i.getY();
    }

    // region String decoding support

    // endregion

    public double distanceSqToCenter(double d0, double d1, double d2) {
        double d3 = this.getX() + 0.5 - d0;
        double d4 = this.getY() + 0.5 - d1;
        double d5 = this.getZ() + 0.5 - d2;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Vec3i)) {
            return false;
        }

        Vec3i vec3i = (Vec3i) object;
        return this.getX() != vec3i.getX()
                ? false
                : (this.getY() != vec3i.getY() ? false : this.getZ() == vec3i.getZ());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    // region String decoding support

    // endregion

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.util.Vec3i.packedTextFragments[0] = new String("x");
        net.minecraft.util.Vec3i.packedTextFragments[1] = new String("y");
        net.minecraft.util.Vec3i.packedTextFragments[2] = new String("z");
        net.minecraft.util.Vec3i.packedTextFragments[3] = new String("iXjo4");
    }

    // endregion

    public Vec3i crossProduct(Vec3i vec3i) {
        return new Vec3i(
                this.getY() * vec3i.getZ() - this.getZ() * vec3i.getY(),
                this.getZ() * vec3i.getX() - this.getX() * vec3i.getZ(),
                this.getX() * vec3i.getY() - this.getY() * vec3i.getX());
    }
}
