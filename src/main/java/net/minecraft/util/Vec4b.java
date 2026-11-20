package net.minecraft.util;

public class Vec4b {
    private byte field_176114_d;
    private byte field_176115_b;
    private byte field_176117_a;
    private byte field_176116_c;

    public Vec4b(byte p171_0, byte p171_1, byte p171_2, byte p171_3) {
        this.field_176117_a = p171_0;
        this.field_176115_b = p171_1;
        this.field_176116_c = p171_2;
        this.field_176114_d = p171_3;
    }

    public byte func_176113_c() {
        return this.field_176116_c;
    }

    @Override
    public int hashCode() {
        byte b0 = this.field_176117_a;
        int i = 31 * b0 + this.field_176115_b;
        i = 31 * i + this.field_176116_c;
        return 31 * i + this.field_176114_d;
    }

    public byte func_176112_b() {
        return this.field_176115_b;
    }

    public byte func_176110_a() {
        return this.field_176117_a;
    }

    @Override
    public boolean equals(Object p171_0) {
        if (this == p171_0) {
            return true;
        }

        if (!(p171_0 instanceof net.minecraft.util.Vec4b)) {
            return false;
        }

        net.minecraft.util.Vec4b vec4b = (net.minecraft.util.Vec4b) p171_0;
        return this.field_176117_a != vec4b.field_176117_a
                ? false
                : (this.field_176114_d != vec4b.field_176114_d
                        ? false
                        : (this.field_176115_b != vec4b.field_176115_b
                                ? false
                                : this.field_176116_c == vec4b.field_176116_c));
    }

    public byte func_176111_d() {
        return this.field_176114_d;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Vec4b(Vec4b vec4b) {
        super();
        this.field_176117_a = vec4b.field_176117_a;
        this.field_176115_b = vec4b.field_176115_b;
        this.field_176116_c = vec4b.field_176116_c;
        this.field_176114_d = vec4b.field_176114_d;
    }
}
