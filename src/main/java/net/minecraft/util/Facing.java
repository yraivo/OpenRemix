package net.minecraft.util;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Random;

public enum Facing implements StringSerializable {
    DOWN(
            "DOWN",
            0,
            0,
            1,
            -1,
            "down",
            FacingAxisDirection.NEGATIVE,
            FacingAxis.Y,
            new Vec3i(0, -1, 0)),
    UP("UP", 1, 1, 0, -1, "up", FacingAxisDirection.POSITIVE, FacingAxis.Y, new Vec3i(0, 1, 0)),
    NORTH(
            "NORTH",
            2,
            2,
            3,
            2,
            "north",
            FacingAxisDirection.NEGATIVE,
            FacingAxis.Z,
            new Vec3i(0, 0, -1)),
    SOUTH(
            "SOUTH",
            3,
            3,
            2,
            0,
            "south",
            FacingAxisDirection.POSITIVE,
            FacingAxis.Z,
            new Vec3i(0, 0, 1)),
    WEST(
            "WEST",
            4,
            4,
            5,
            1,
            "west",
            FacingAxisDirection.NEGATIVE,
            FacingAxis.X,
            new Vec3i(-1, 0, 0)),
    EAST(
            "EAST",
            5,
            5,
            4,
            3,
            "east",
            FacingAxisDirection.POSITIVE,
            FacingAxis.X,
            new Vec3i(1, 0, 0));
    private static Facing[] HORIZONTALS;

    private static Facing[] ENUM_VALUES;

    public static Facing[] VALUES;

    private FacingAxis axis;
    private static int mE;
    private static Map NAME_LOOKUP;
    private int index;

    private String name;
    private int horizontalIndex;

    private int opposite;
    private Vec3i directionVec;
    private FacingAxisDirection axisDirection;

    @Override
    public String toString() {
        return this.name;
    }

    public static Facing byName(String text) {
        return text == null
                ? null
                : (Facing) net.minecraft.util.Facing.NAME_LOOKUP.get(text.toLowerCase());
    }

    public int getFrontOffsetX() {
        return this.axis == FacingAxis.X ? this.axisDirection.getOffset() : 0;
    }

    public static Facing getHorizontal(int i) {
        return net.minecraft.util.Facing.HORIZONTALS[
                MathHelper.abs(i % net.minecraft.util.Facing.HORIZONTALS.length)];
    }

    public static Facing getFront(int i) {
        return net.minecraft.util.Facing.VALUES[
                MathHelper.abs(i % net.minecraft.util.Facing.VALUES.length)];
    }

    private Facing pq() {
        switch (this) {
            case NORTH:
                return DOWN;
            case EAST:
            case WEST:
            default:
                throw new IllegalStateException("Unable to get X-rotated facing of " + this);
            case SOUTH:
                return UP;
            case UP:
                return NORTH;
            case DOWN:
                return SOUTH;
        }
    }

    public static Facing fromAngle(double d0) {
        return getHorizontal(MathHelper.floor(d0 / 90.0 + 0.5) & 3);
    }

    // region Static initialization and string tables
    static {
        net.minecraft.util.Facing.VALUES = new Facing[6];
        net.minecraft.util.Facing.HORIZONTALS = new Facing[4];
        net.minecraft.util.Facing.ENUM_VALUES = new Facing[] {DOWN, UP, NORTH, SOUTH, WEST, EAST};
        net.minecraft.util.Facing.NAME_LOOKUP = Maps.newHashMap();
        for (Facing class_e7161461ff05092e1340 : values()) {
            net.minecraft.util.Facing.VALUES[class_e7161461ff05092e1340.index] =
                    class_e7161461ff05092e1340;
            if (class_e7161461ff05092e1340.getAxis().isHorizontal()) {
                net.minecraft.util.Facing.HORIZONTALS[class_e7161461ff05092e1340.horizontalIndex] =
                        class_e7161461ff05092e1340;
            }

            net.minecraft.util.Facing.NAME_LOOKUP.put(
                    class_e7161461ff05092e1340.getName2().toLowerCase(),
                    class_e7161461ff05092e1340);
        }
    }

    // endregion

    public FacingAxis getAxis() {
        return this.axis;
    }

    public static int jG() {
        jH();

        return (13);
    }

    public int getFrontOffsetY() {
        return this.axis == FacingAxis.Y ? this.axisDirection.getOffset() : 0;
    }

    public String getName2() {
        return this.name;
    }

    private Facing ps() {
        switch (this) {
            case EAST:
                return DOWN;
            case SOUTH:
            default:
                throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
            case WEST:
                return UP;
            case UP:
                return EAST;
            case DOWN:
                return WEST;
        }
    }

    public static Facing getFacingFromVector(float f, float f1, float f2) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        Facing facing = NORTH;
        float MIN_VALUE = Float.MIN_VALUE;
        Facing[] facingArray = values();
        String textValue = text;

        for (Facing facingValue : facingArray) {
            float f4 =
                    f * facingValue.directionVec.getX()
                            + f1 * facingValue.directionVec.getY()
                            + f2 * facingValue.directionVec.getZ();
            if (f4 > MIN_VALUE) {
                MIN_VALUE = f4;
                facing = facingValue;
            }

            if (textValue == null) {
                break;
            }
        }

        return facing;
    }

    public FacingAxisDirection getAxisDirection() {
        return this.axisDirection;
    }

    public int getIndex() {
        return this.index;
    }

    public Facing rotateYCCW() {
        switch (this) {
            case NORTH:
                return WEST;
            case EAST:
                return NORTH;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
            default:
                throw new IllegalStateException("Unable to get CCW facing of " + this);
        }
    }

    public String getName() {
        return this.name;
    }

    public Facing rotateY() {
        switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
        }
    }

    public Facing rotateAround(FacingAxis facingAxis) {
        switch (facingAxis) {
            case X:
                if (this != WEST && this != EAST) {
                    return this.pq();
                }

                return this;
            case Y:
                if (this != UP && this != DOWN) {
                    return this.rotateY();
                }

                return this;
            case Z:
                if (this != NORTH && this != SOUTH) {
                    return this.ps();
                }

                return this;
            default:
                throw new IllegalStateException("Unable to get CW facing for axis " + facingAxis);
        }
    }

    // region String decoding support

    // endregion

    public static void method_571f52593fafd8dfba61(int i) {
        mE = i;
    }

    public int getHorizontalIndex() {
        return this.horizontalIndex;
    }

    public static int jH() {
        return mE;
    }

    private static IllegalStateException method_b3985d2d68c259fdadde(
            IllegalStateException illegalstateexception) {
        return illegalstateexception;
    }

    public Vec3i getDirectionVec() {
        return this.directionVec;
    }

    Facing(
            String text,
            int i1,
            int j,
            int k,
            int l,
            String textValue,
            FacingAxisDirection facingAxisDirection,
            FacingAxis facingAxis,
            Vec3i vec3i) {
        this.index = j;
        this.horizontalIndex = l;
        this.opposite = k;
        this.name = textValue;
        this.axis = facingAxis;
        this.axisDirection = facingAxisDirection;
        this.directionVec = vec3i;
    }

    // region String decoding support

    // endregion

    public int getFrontOffsetZ() {
        return this.axis == FacingAxis.Z ? this.axisDirection.getOffset() : 0;
    }

    public Facing getOpposite() {
        return net.minecraft.util.Facing.VALUES[this.opposite];
    }

    public static Facing random(Random random) {
        return values()[random.nextInt(values().length)];
    }
}
