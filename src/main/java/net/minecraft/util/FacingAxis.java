package net.minecraft.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import java.util.Map;

public enum FacingAxis implements Predicate, StringSerializable {
    X(
            initializeConstantState(),
            0,
            constantTextFragment(2),
            0,
            constantTextFragment(3),
            net.minecraft.util.FacingPlane.HORIZONTAL),
    Y(
            constantTextFragment(5),
            1,
            constantTextFragment(6),
            1,
            constantTextFragment(7),
            net.minecraft.util.FacingPlane.VERTICAL),
    Z(
            constantTextFragment(9),
            2,
            constantTextFragment(10),
            2,
            constantTextFragment(11),
            net.minecraft.util.FacingPlane.HORIZONTAL);
    private static FacingAxis[] Yg;

    private static FacingAxis[] Ya;

    private static FacingAxis[] XZ;

    private static String[] packedTextFragments;
    private static Map NAME_LOOKUP;
    private FacingPlane plane;
    private String name;

    public String getName() {
        return this.name;
    }

    // region String decoding support

    // endregion

    FacingAxis(
            String text,
            int j,
            String textValue,
            int k,
            String textValue2,
            FacingPlane facingPlane) {
        this.name = textValue2;
        this.plane = facingPlane;
    }

    // region String decoding support

    // endregion

    public static FacingAxis byName(String text) {
        return text == null
                ? null
                : (FacingAxis) net.minecraft.util.FacingAxis.NAME_LOOKUP.get(text.toLowerCase());
    }

    public boolean apply(Object object) {
        return this.apply((Facing) object);
    }

    public boolean isVertical() {
        return this.plane == FacingPlane.VERTICAL;
    }

    // region String decoding support

    // endregion

    public String getName2() {
        return this.name;
    }

    public boolean apply(Facing facing) {
        return facing != null && facing.getAxis() == this;
    }

    // region String decoding support

    // endregion

    // region Static initialization and string tables
    static {
        Yg = new FacingAxis[] {X, Y, Z};
        net.minecraft.util.FacingAxis.NAME_LOOKUP = Maps.newHashMap();
        XZ = new FacingAxis[] {X, Y, Z};
        Ya = new FacingAxis[] {X, Y, Z};
        for (FacingAxis class_16e40c82f81071960597 : values()) {
            net.minecraft.util.FacingAxis.NAME_LOOKUP.put(
                    class_16e40c82f81071960597.getName2().toLowerCase(),
                    class_16e40c82f81071960597);
        }
    }

    // endregion

    @Override
    public String toString() {
        return this.name;
    }

    public boolean isHorizontal() {
        return this.plane == FacingPlane.HORIZONTAL;
    }

    public FacingPlane getPlane() {
        return this.plane;
    }

    private static FacingAxis[] __recovery_values171() {
        return Yg.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        net.minecraft.util.FacingAxis.packedTextFragments = new String[12];
        net.minecraft.util.FacingAxis.packedTextFragments[0] = new String("X");
        net.minecraft.util.FacingAxis.packedTextFragments[1] = new String("X");
        net.minecraft.util.FacingAxis.packedTextFragments[2] = new String("X");
        net.minecraft.util.FacingAxis.packedTextFragments[3] = new String("x");
        net.minecraft.util.FacingAxis.packedTextFragments[4] = new String("Y");
        net.minecraft.util.FacingAxis.packedTextFragments[5] = new String("Y");
        net.minecraft.util.FacingAxis.packedTextFragments[6] = new String("Y");
        net.minecraft.util.FacingAxis.packedTextFragments[7] = new String("y");
        net.minecraft.util.FacingAxis.packedTextFragments[8] = new String("Z");
        net.minecraft.util.FacingAxis.packedTextFragments[9] = new String("Z");
        net.minecraft.util.FacingAxis.packedTextFragments[10] = new String("Z");
        net.minecraft.util.FacingAxis.packedTextFragments[11] = new String("z");
        return net.minecraft.util.FacingAxis.packedTextFragments[1];
    }

    private static String constantTextFragment(int index) {
        return packedTextFragments[index];
    }
}
