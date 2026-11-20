package optifine;

import net.minecraft.util.Vec3;

public class CustomColorFader {
    private Vec3 color = null;
    private long timeUpdate = System.currentTimeMillis();

    public Vec3 getColor(double d0, double d1, double d2) {
        if (this.color == null) {
            this.color = new Vec3(d0, d1, d2);
            return this.color;
        }

        long timeUpdateValue = System.currentTimeMillis();
        long j = timeUpdateValue - this.timeUpdate;

        if (j == 0L) {
            return this.color;
        }

        this.timeUpdate = timeUpdateValue;
        if (Math.abs(d0 - this.color.x) < 0.004
                && Math.abs(d1 - this.color.y) < 0.004
                && Math.abs(d2 - this.color.z) < 0.004) {
            return this.color;
        }

        double d3 = j * 0.001;
        d3 = OptiFineConfig.limit(d3, 0.0, 1.0);
        double d4 = d0 - this.color.x;
        double d5 = d1 - this.color.y;
        double d6 = d2 - this.color.z;
        double d7 = this.color.x + d4 * d3;
        double d8 = this.color.y + d5 * d3;
        double d9 = this.color.z + d6 * d3;
        this.color = new Vec3(d7, d8, d9);
        return this.color;
    }
}
