package remix.util.math;

public class Vec3dPoint {

    public double um;

    private static String[] packedTextFragments;
    public double uj;
    public double un;

    public Vec3dPoint fH() {
        double d0 = Math.sqrt(this.uj * this.uj + this.um * this.um + this.un * this.un);
        return new Vec3dPoint(this.uj / d0, this.um / d0, this.un / d0);
    }

    public Vec3dPoint method_63c9b2a07d0a91df1959(
            Vec3dPoint vec3dPoint) {
        return new Vec3dPoint(
                this.uj + vec3dPoint.uj,
                this.um + vec3dPoint.um,
                this.un + vec3dPoint.un);
    }

    public Vec3dPoint method_423ace14eed44bb3fc93(double d0, double d1, double d2) {
        return new Vec3dPoint(this.uj + d0, this.um + d1, this.un + d2);
    }

    public Vec3dPoint method_bd4205a0ec2d78a00f2c(
            Vec3dPoint vec3dPoint) {
        return new Vec3dPoint(
                this.um * vec3dPoint.un - this.un * vec3dPoint.um,
                this.un * vec3dPoint.uj - this.uj * vec3dPoint.un,
                this.uj * vec3dPoint.um
                        - this.um * vec3dPoint.uj);
    }

    public Vec3dPoint method_12a22129b8359e6f8932(double d0, double d1, double d2) {
        this.uj += d0;
        this.um += d1;
        this.un += d2;
        return this;
    }

    public Vec3dPoint method_a576fc0b9a96a3d46161(double d0) {
        this.uj /= d0;
        this.um /= d0;
        this.un /= d0;
        return this;
    }

    public Vec3dPoint method_17d6e66fca902554d4b6(double d0, double d1, double d2) {
        return new Vec3dPoint(this.uj - d0, this.um - d1, this.un - d2);
    }

    public Vec3dPoint method_43ff8c2cd8bde49887f0(double d0) {
        return new Vec3dPoint(this.uj * d0, this.um * d0, this.un * d0);
    }

    public Vec3dPoint method_13d3f2ef1a4ed6c3e98d(double d0, double d1, double d2) {
        this.uj -= d0;
        this.um -= d1;
        this.un -= d2;
        return this;
    }

    public Vec3dPoint(double d0, double d1, double d2) {
        this.uj = d0;
        this.um = d1;
        this.un = d2;
    }

    public Vec3dPoint method_93db01f289d34f9b14ab(
            Vec3dPoint vec3dPoint) {
        this.uj = this.uj - vec3dPoint.uj;
        this.um = this.um - vec3dPoint.um;
        this.un = this.un - vec3dPoint.un;
        return this;
    }

    public Vec3dPoint method_b534797dceda2fbc20b7(
            Vec3dPoint vec3dPoint) {
        this.uj =
                this.um * vec3dPoint.un - this.un * vec3dPoint.um;
        this.um =
                this.un * vec3dPoint.uj - this.uj * vec3dPoint.un;
        this.un =
                this.uj * vec3dPoint.um - this.um * vec3dPoint.uj;
        return this;
    }

    public double cl() {
        return Math.sqrt(this.uj * this.uj + this.um * this.um + this.un * this.un);
    }

    @Override
    public String toString() {
        return "(X: " + this.uj + " Y: " + this.um + " Z: " + this.un + packedTextFragments[0];
    }

    public Vec3dPoint method_6c02c1c4836fa8295119(
            Vec3dPoint vec3dPoint) {
        this.uj = this.uj + vec3dPoint.uj;
        this.um = this.um + vec3dPoint.um;
        this.un = this.un + vec3dPoint.un;
        return this;
    }

    public Vec3dPoint method_6c4720041bd2704ac69d(double d0) {
        return new Vec3dPoint(this.uj / d0, this.um / d0, this.un / d0);
    }

    public Vec3dPoint fJ() {
        double d0 = Math.sqrt(this.uj * this.uj + this.um * this.um + this.un * this.un);
        this.uj /= d0;
        this.um /= d0;
        this.un /= d0;
        return this;
    }

    public double method_42a6b96e357aed37238d(
            Vec3dPoint vec3dPoint) {
        return this.uj * vec3dPoint.uj
                + this.um * vec3dPoint.um
                + this.un * vec3dPoint.un;
    }

    public Vec3dPoint method_f45849c02707e2a1606f(double d0) {
        this.uj *= d0;
        this.um *= d0;
        this.un *= d0;
        return this;
    }

    static {
        remix.util.math.Vec3dPoint.packedTextFragments = new String[2];
        remix.util.math.Vec3dPoint.packedTextFragments[0] = new String(")");
    }

    public Vec3dPoint method_5d57482c89ea29573d32(
            Vec3dPoint vec3dPoint) {
        return new Vec3dPoint(
                this.uj - vec3dPoint.uj,
                this.um - vec3dPoint.um,
                this.un - vec3dPoint.un);
    }
}
