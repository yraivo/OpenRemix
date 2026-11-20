package net.minecraft.util;

public enum FacingAxisDirection {
    POSITIVE("POSITIVE", 0, "POSITIVE", 0, 1, "Towards positive"),
    NEGATIVE("NEGATIVE", 1, "NEGATIVE", 1, -1, "Towards negative");

    private static FacingAxisDirection[] AXIS_DIRECTION_VALUES;

    private String description;

    private int offset;

    FacingAxisDirection(String text, int k, String textValue, int l, int j, String textValue2) {
        this.offset = j;
        this.description = textValue2;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    @Override
    public String toString() {
        return this.description;
    }

    // region Static initialization and string tables
    static {
        net.minecraft.util.FacingAxisDirection.AXIS_DIRECTION_VALUES = new FacingAxisDirection[] {POSITIVE, NEGATIVE};
    }

    // endregion

    public int getOffset() {
        return this.offset;
    }
}
