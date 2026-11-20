package net.minecraft.client.util;

import net.minecraft.entity.Entity;

import java.nio.FloatBuffer;
import java.util.Comparator;

public class QuadComparator implements Comparator {
    private float gw;
    private int ef;
    private float hW;
    private FloatBuffer uS;
    private float hX;

    public int compare(Integer integer, Integer integer1) {
        float f = this.uS.get(integer) - this.gw;
        float f1 = this.uS.get(integer + 1) - this.hX;
        float f2 = this.uS.get(integer + 2) - this.hW;
        float f3 = this.uS.get(integer + this.ef + 0) - this.gw;
        String[] astring1 = JsonException.b();
        float f4 = this.uS.get(integer + this.ef + 1) - this.hX;
        float f5 = this.uS.get(integer + this.ef + 2) - this.hW;
        float f6 = this.uS.get(integer + this.ef * 2 + 0) - this.gw;
        float f7 = this.uS.get(integer + this.ef * 2 + 1) - this.hX;
        float f8 = this.uS.get(integer + this.ef * 2 + 2) - this.hW;
        float f9 = this.uS.get(integer + this.ef * 3 + 0) - this.gw;
        float f10 = this.uS.get(integer + this.ef * 3 + 1) - this.hX;
        float f11 = this.uS.get(integer + this.ef * 3 + 2) - this.hW;
        float f12 = this.uS.get(integer1) - this.gw;
        float f13 = this.uS.get(integer1 + 1) - this.hX;
        float f14 = this.uS.get(integer1 + 2) - this.hW;
        float f15 = this.uS.get(integer1 + this.ef + 0) - this.gw;
        float f16 = this.uS.get(integer1 + this.ef + 1) - this.hX;
        String[] astring = astring1;
        float f17 = this.uS.get(integer1 + this.ef + 2) - this.hW;
        float f18 = this.uS.get(integer1 + this.ef * 2 + 0) - this.gw;
        float f19 = this.uS.get(integer1 + this.ef * 2 + 1) - this.hX;
        float f20 = this.uS.get(integer1 + this.ef * 2 + 2) - this.hW;
        float f21 = this.uS.get(integer1 + this.ef * 3 + 0) - this.gw;
        float f22 = this.uS.get(integer1 + this.ef * 3 + 1) - this.hX;
        float f23 = this.uS.get(integer1 + this.ef * 3 + 2) - this.hW;
        float f24 = (f + f3 + f6 + f9) * 0.25F;
        float f25 = (f1 + f4 + f7 + f10) * 0.25F;
        float f26 = (f2 + f5 + f8 + f11) * 0.25F;
        float f27 = (f12 + f15 + f18 + f21) * 0.25F;
        float f28 = (f13 + f16 + f19 + f22) * 0.25F;
        float f29 = (f14 + f17 + f20 + f23) * 0.25F;
        float f30 = f24 * f24 + f25 * f25 + f26 * f26;
        float f31 = f27 * f27 + f28 * f28 + f29 * f29;
        int i = Float.compare(f31, f30);
        if (astring != null) {
            Entity.setLegacyGlobalFlowMarker(new String[4]);
        }

        return i;
    }

    @Override
    public int compare(Object object, Object object1) {
        return this.compare((Integer) object, (Integer) object1);
    }

    public QuadComparator(FloatBuffer floatbuffer, float f, float f1, float f2, int i) {
        this.uS = floatbuffer;
        this.gw = f;
        this.hX = f1;
        this.hW = f2;
        this.ef = i;
    }
}
